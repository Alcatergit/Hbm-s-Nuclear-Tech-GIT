package com.hbm.util;

import com.hbm.config.GeneralConfig;
import com.hbm.handler.threading.BombForkJoinPool;
import com.hbm.interfaces.BitMask;
import com.hbm.interfaces.ServerThread;
import com.hbm.interfaces.ThreadSafeMethod;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.lib.internal.UnsafeHolder;
import com.hbm.main.MainRegistry;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongCollection;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.BitArray;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.BlockStateContainer;
import net.minecraft.world.chunk.BlockStatePaletteHashMap;
import net.minecraft.world.chunk.BlockStatePaletteLinear;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IBlockStatePalette;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ForkJoinPool;

import static com.hbm.lib.internal.UnsafeHolder.U;

/** Chunk utilities for the parallel explosion executors: a loaded-chunk mirror, copy-on-write sub-chunk edits, the terrain light engine, and the light work queue. */
@Mod.EventBusSubscriber(modid = RefStrings.MODID)
public final class ChunkUtil {

    private static final long ARR_BASE = U.arrayBaseOffset(ExtendedBlockStorage[].class);
    private static final long ARR_SCALE = U.arrayIndexScale(ExtendedBlockStorage[].class);
    private static final long UNLOAD_QUEUED_OFFSET = UnsafeHolder.fieldOffset(Chunk.class, "unloadQueued", "field_189550_d");
    private static final long BSL_STATES_OFFSET = UnsafeHolder.fieldOffset(BlockStatePaletteLinear.class, "states", "field_186042_a");
    private static final long BSL_ARRAY_SIZE_OFFSET = UnsafeHolder.fieldOffset(BlockStatePaletteLinear.class, "arraySize", "field_186045_d");
    private static final long BSHM_MAP_OFFSET = UnsafeHolder.fieldOffset(BlockStatePaletteHashMap.class, "statePaletteMap", "field_186046_a");
    private static final long IIHBM_VALUES_OFFSET = UnsafeHolder.fieldOffset(IntIdentityHashBiMap.class, "values", "field_186818_b");
    private static final long IIHBM_INTKEYS_OFFSET = UnsafeHolder.fieldOffset(IntIdentityHashBiMap.class, "intKeys", "field_186819_c");
    private static final long IIHBM_BYID_OFFSET = UnsafeHolder.fieldOffset(IntIdentityHashBiMap.class, "byId", "field_186820_d");
    private static final long IIHBM_NEXTFREE_OFFSET = UnsafeHolder.fieldOffset(IntIdentityHashBiMap.class, "nextFreeIndex", "field_186821_e");
    private static final long IIHBM_MAPSIZE_OFFSET = UnsafeHolder.fieldOffset(IntIdentityHashBiMap.class, "mapSize", "field_186822_f");
    private static final IBlockState AIR_DEFAULT_STATE = Blocks.AIR.getDefaultState();
    /** 6-direction offsets for light propagation (y first = up/down). */

    private static final int[] DX = { 0, 0, 0, 0, -1, 1 };
    private static final int[] DY = { 1, -1, 0, 0, 0, 0 };
    private static final int[] DZ = { 0, 0, -1, 1, 0, 0 };
    private static final int LIGHT_IN_FLIGHT_LIMIT = 64;
    /** Dimension -> active task count (used to decide when to build/tear down the mirror map). */

    private static final Int2IntOpenHashMap activeTask = new Int2IntOpenHashMap();
    /** Global reference count across all dimensions indicating how many concurrent tasks are active. */

    private static int refCounter = 0;
    /** Dimension -> (chunkPos long key -> Chunk) mirror. Only present while there are active tasks. */

    private static final ConcurrentHashMap<Integer, ConcurrentHashMap<Long, Chunk>> chunkMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Integer, ConcurrentHashMap<Long, LightJob>> LIGHT_QUEUE = new ConcurrentHashMap<>();
    private static final AtomicInteger LIGHT_IN_FLIGHT = new AtomicInteger();
    private static ForkJoinPool lightPool;
    private static final ConcurrentHashMap<Integer, ConcurrentHashMap<Long, Integer>> LIGHT_MASKS = new ConcurrentHashMap<>();

    private ChunkUtil() {
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        if (refCounter == 0) return;
        Chunk chunk = event.getChunk();
        World world = chunk.getWorld();
        if (world.isRemote) return;
        int key = world.provider.getDimension();
        if (activeTask.get(key) == 0) return;
        ConcurrentHashMap<Long, Chunk> dimMap = chunkMap.get(key);
        if (dimMap != null) dimMap.put(ChunkPos.asLong(chunk.x, chunk.z), chunk);
    }

    @SubscribeEvent
    public static void onChunkUnload(ChunkEvent.Unload event) {
        if (refCounter == 0) return;
        Chunk chunk = event.getChunk();
        World world = chunk.getWorld();
        if (world.isRemote) return;
        int key = world.provider.getDimension();
        ConcurrentHashMap<Long, Chunk> dimMap = chunkMap.get(key);
        if (dimMap != null) dimMap.remove(ChunkPos.asLong(chunk.x, chunk.z));
    }

    /** A full chunk packet skips empty sections, so block light stored in one only reaches the client via a partial packet. */
    @SubscribeEvent
    public static void onChunkWatch(ChunkWatchEvent.Watch event) {
        Chunk chunk = event.getChunkInstance();
        if (chunk == null) return;
        World world = chunk.getWorld();
        if (world == null || world.isRemote) return;
        int mask = withLightSections(chunk, 0);
        if (mask == 0) return;
        event.getPlayer().connection.sendPacket(
                new SPacketChunkData(chunk, mask));
    }

    private static int withLightSections(Chunk chunk, int mask) {
        ExtendedBlockStorage[] arr = chunk.getBlockStorageArray();
        if (arr == null) return mask;
        for (int s = 0; s < arr.length && s < 16; s++) {
            ExtendedBlockStorage e = arr[s];
            if (e == null || e == Chunk.NULL_BLOCK_STORAGE) continue;
            if (!e.isEmpty()) continue;
            NibbleArray bl = e.getBlockLight();
            if (bl != null && hasNonZeroNibble(bl.getData())) mask |= 1 << s;
        }
        return mask;
    }

    private static boolean hasNonZeroNibble(byte[] data) {
        for (byte b : data) if (b != 0) return true;
        return false;
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent e) {
        if (e.phase != TickEvent.Phase.END) return;
        if (!(e.world instanceof WorldServer)) return;
        tickLight((WorldServer) e.world);
    }

    @SubscribeEvent
    public static void onWorldUnload(WorldEvent.Unload event) {
        World world = event.getWorld();
        if (world.isRemote) return;
        int key = world.provider.getDimension();
        // Cancel any active bomb jobs before tearing down the mirror map for this dimension.
        BombForkJoinPool.onWorldUnload(world);
        if (GeneralConfig.enableExtendedLogging)
            MainRegistry.logger.info("Dimension {} unloaded with {} active tasks. refCounter = {}",
                    key, activeTask.get(key), refCounter);
        activeTask.put(key, 0);
        chunkMap.remove(key);
        LIGHT_MASKS.remove(key);
    }

    public static void onServerStopped() {
        chunkMap.clear();
        activeTask.clear();
        LIGHT_MASKS.clear();
        if (GeneralConfig.enableExtendedLogging)
            MainRegistry.logger.info("Server stopping with {} active tasks, refCounter = {}",
                    Arrays.stream(activeTask.values().toIntArray()).sum(), refCounter);
        refCounter = 0;
    }

    /**
    * Build (or reference) the mirror map for the given dimension and increment its task count.
    * Must be called from the server thread.
    */

    @ServerThread
    public static ConcurrentHashMap<Long, Chunk> acquireMirrorMap(WorldServer world) {
        int key = world.provider.getDimension();
        ConcurrentHashMap<Long, Chunk> thisDim;
        if (activeTask.addTo(key, 1) == 0) {
            thisDim = new ConcurrentHashMap<>(4096);
            // This parallel traversal assumes the server thread is quiescent for this world's provider
            world.getChunkProvider().loadedChunks.values().parallelStream()
                    .forEach(chunk -> thisDim.put(ChunkPos.asLong(chunk.x, chunk.z), chunk));
            chunkMap.put(key, thisDim);
        } else {
            thisDim = chunkMap.get(key);
        }
        refCounter++;
        if (GeneralConfig.enableExtendedLogging) {
            MainRegistry.logger.info("Acquired mirror map for dimension {}. Active tasks of this dim = {}, refCounter = {}.",
                    key, activeTask.get(key), refCounter);
        }
        return Objects.requireNonNull(thisDim);
    }

    /** Decrement the dimension task count and, if it hits zero, drop the mirror map for that dimension. */

    @ServerThread
    public static void releaseMirrorMap(WorldServer world) {
        int key = world.provider.getDimension();
        if (activeTask.addTo(key, -1) == 1) chunkMap.remove(key);
        refCounter--;
        if (GeneralConfig.enableExtendedLogging) {
            MainRegistry.logger.info("Released mirror map for dimension {}. Active tasks of this dim = {}, refCounter = {}.",
                    key, activeTask.get(key), refCounter);
        }
    }

    /** Lookup a loaded chunk from the mirror map, clearing its unloadQueued flag to reduce unload races. */

    @ThreadSafeMethod
    public static Chunk getLoadedChunk(Map<Long, ? extends Chunk> loaded, long chunkPos) {
        Chunk chunk = loaded.get(chunkPos);
        if (chunk == null) return null;
        U.putBooleanVolatile(chunk, UNLOAD_QUEUED_OFFSET, false);
        return chunk;
    }

    /** Fetch the sub-chunk array for a loaded chunk using the mirror map. */

    @ThreadSafeMethod
    public static ExtendedBlockStorage[] getLoadedEBS(Map<Long, ? extends Chunk> loaded, long chunkPos) {
        Chunk chunk = getLoadedChunk(loaded, chunkPos);
        if (chunk == null) return null;
        return chunk.getBlockStorageArray();
    }

    /** Clear stored contents before replacing a tile entity's block through vanilla's lifecycle. */

    @ServerThread
    public static boolean replaceEmptied(World world, BlockPos pos, IBlockState replacement, int flags) {
        IBlockState old = world.getBlockState(pos);
        if (old == replacement) return false;
        if (old.getBlock().hasTileEntity(old)) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileEntityLockableLoot) {
                TileEntityLockableLoot loot = (TileEntityLockableLoot) tile;
                loot.setLootTable(null, 0);
            }
            if (tile instanceof IInventory) {
                IInventory inventory = (IInventory) tile;
                inventory.clear();
            }
        }
        return world.setBlockState(pos, replacement, flags);
    }

    /** Make a deep copy of a {@link BlockStateContainer}, including its palette and backing bit-storage. */

    @ThreadSafeMethod
    public static BlockStateContainer copyOf(BlockStateContainer srcData) {
        int bits = srcData.bits;
        IBlockStatePalette srcPalette = srcData.palette;
        BlockStateContainer copied = UnsafeHolder.allocateInstance(BlockStateContainer.class);
        copied.bits = bits;

        if (bits <= 4) {
            copied.palette = new BlockStatePaletteLinear(bits, copied);
            int arraySize = U.getInt(srcPalette, BSL_ARRAY_SIZE_OFFSET);
            U.putInt(copied.palette, BSL_ARRAY_SIZE_OFFSET, arraySize);
            IBlockState[] srcStates = (IBlockState[]) U.getReference(srcPalette, BSL_STATES_OFFSET);
            IBlockState[] dstStates = (IBlockState[]) U.getReference(copied.palette, BSL_STATES_OFFSET);
            System.arraycopy(srcStates, 0, dstStates, 0, arraySize);
        } else if (bits <= 8) {
            copied.palette = new BlockStatePaletteHashMap(bits, copied);
            Object srcMap = U.getReference(srcPalette, BSHM_MAP_OFFSET);
            Object dstMap = U.getReference(copied.palette, BSHM_MAP_OFFSET);

            int nextFree = U.getInt(srcMap, IIHBM_NEXTFREE_OFFSET);
            int mapSize = U.getInt(srcMap, IIHBM_MAPSIZE_OFFSET);
            U.putInt(dstMap, IIHBM_NEXTFREE_OFFSET, nextFree);
            U.putInt(dstMap, IIHBM_MAPSIZE_OFFSET, mapSize);

            Object[] srcValues = (Object[]) U.getReference(srcMap, IIHBM_VALUES_OFFSET);
            int[] srcIntKeys = (int[]) U.getReference(srcMap, IIHBM_INTKEYS_OFFSET);
            Object[] srcById = (Object[]) U.getReference(srcMap, IIHBM_BYID_OFFSET);

            U.putReference(dstMap, IIHBM_VALUES_OFFSET, srcValues.clone());
            U.putReference(dstMap, IIHBM_INTKEYS_OFFSET, srcIntKeys.clone());
            U.putReference(dstMap, IIHBM_BYID_OFFSET, srcById.clone());
        } else {
            copied.palette = BlockStateContainer.REGISTRY_BASED_PALETTE;
        }

        BitArray srcStorage = srcData.storage;
        copied.storage = new BitArray(bits, 4096);
        long[] srcLongs = srcStorage.getBackingLongArray();
        long[] dstLongs = copied.storage.getBackingLongArray();
        System.arraycopy(srcLongs, 0, dstLongs, 0, srcLongs.length);
        return copied;
    }

    @ThreadSafeMethod
    public static ExtendedBlockStorage copyOf(ExtendedBlockStorage src) {
        ExtendedBlockStorage dst = UnsafeHolder.allocateInstance(ExtendedBlockStorage.class);
        dst.yBase = src.yBase;
        dst.data = copyOf(src.getData());
        dst.blockLight = new NibbleArray(src.getBlockLight().getData().clone());
        dst.skyLight = src.skyLight != null ? new NibbleArray(src.skyLight.getData().clone()) : null;
        dst.blockRefCount = src.blockRefCount;
        dst.tickRefCount = src.tickRefCount;
        return dst;
    }

    /** Copy only the block-state container (no light arrays). */

    @ThreadSafeMethod
    public static ExtendedBlockStorage copyBlockStates(ExtendedBlockStorage src) {
        ExtendedBlockStorage dst = UnsafeHolder.allocateInstance(ExtendedBlockStorage.class);
        dst.yBase = src.yBase;
        dst.data = copyOf(src.getData());
        // Keep skyLight when blockLight is absent; otherwise the copy reads as black.
        dst.skyLight = src.skyLight != null ? new NibbleArray(src.skyLight.getData().clone()) : null;
        dst.blockRefCount = src.blockRefCount;
        dst.tickRefCount = src.tickRefCount;
        return dst;
    }

    private static boolean checkNeighbor(ConcurrentMap<Long, Chunk> loaded, int chunkX, int chunkZ, int subY, int height,
                                         ExtendedBlockStorage[] srcs, NeighborCache nc, int x, int y, int z, BitMask localMask) {
        if (x >= 0 && x <= 15 && y >= 0 && y <= 15 && z >= 0 && z <= 15) {
            if (localMask != null) {
                int nIdx = Library.packLocal(x, y, z);
                if (localMask.get(nIdx)) return false;
            }
            ExtendedBlockStorage src = srcs[subY];
            return src != null && !src.isEmpty() && src.get(x, y, z).getBlock() != Blocks.AIR;
        }

        if (y < 0) {
            if (subY == 0) return false;
            ExtendedBlockStorage below = srcs[subY - 1];
            return below != null && !below.isEmpty() && below.get(x, 15, z).getBlock() != Blocks.AIR;
        }
        if (y > 15) {
            if (subY >= (height >> 4) - 1) return false;
            ExtendedBlockStorage above = srcs[subY + 1];
            return above != null && !above.isEmpty() && above.get(x, 0, z).getBlock() != Blocks.AIR;
        }
        if (x < 0) {
            if (nc.negX == null) nc.negX = getLoadedEBS(loaded, ChunkPos.asLong(chunkX - 1, chunkZ));
            if (nc.negX != null) {
                ExtendedBlockStorage n = nc.negX[subY];
                return n != null && !n.isEmpty() && n.get(15, y, z).getBlock() != Blocks.AIR;
            }
            return false;
        }
        if (x > 15) {
            if (nc.posX == null) nc.posX = getLoadedEBS(loaded, ChunkPos.asLong(chunkX + 1, chunkZ));
            if (nc.posX != null) {
                ExtendedBlockStorage n = nc.posX[subY];
                return n != null && !n.isEmpty() && n.get(0, y, z).getBlock() != Blocks.AIR;
            }
            return false;
        }
        if (z < 0) {
            if (nc.negZ == null) nc.negZ = getLoadedEBS(loaded, ChunkPos.asLong(chunkX, chunkZ - 1));
            if (nc.negZ != null) {
                ExtendedBlockStorage n = nc.negZ[subY];
                return n != null && !n.isEmpty() && n.get(x, y, 15).getBlock() != Blocks.AIR;
            }
            return false;
        }
        if (nc.posZ == null) nc.posZ = getLoadedEBS(loaded, ChunkPos.asLong(chunkX, chunkZ + 1));
        if (nc.posZ != null) {
            ExtendedBlockStorage n = nc.posZ[subY];
            return n != null && !n.isEmpty() && n.get(x, y, 0).getBlock() != Blocks.AIR;
        }
        return false;
    }

    /**
    * Produce a modified copy of the target sub-chunk by carving out positions marked in the local mask.
    * Tile-entity cells are left in the section for the server-thread block replacement.
    */

    @ThreadSafeMethod
    public static ExtendedBlockStorage copyAndCarveLocal(WorldServer world, int chunkX, int chunkZ, int subY,
                                                         ExtendedBlockStorage[] srcs, BitMask localMask,
                                                         LongCollection edgeOut,
                                                         Long2ObjectMap<IBlockState> modifiedOut,
                                                         Long2ObjectMap<IBlockState> blockEntitiesOut) {
        ExtendedBlockStorage src = getEbsVolatile(srcs, subY);
        if (src == null || src.isEmpty()) return null;
        int height = world.getHeight();
        ExtendedBlockStorage dst = null;
        int airId = -1;
        NeighborCache neighbors = new NeighborCache();
        ConcurrentHashMap<Long, Chunk> loaded = chunkMap.get(world.provider.getDimension());
        int xBase = chunkX << 4, yBase = subY << 4, zBase = chunkZ << 4;
        for (int idx = localMask.nextSetBit(0); idx >= 0 && idx < 4096; idx = localMask.nextSetBit(idx + 1)) {
            int xLocal = Library.getLocalX(idx);
            int yLocal = Library.getLocalY(idx);
            int zLocal = Library.getLocalZ(idx);

            IBlockState old = src.get(xLocal, yLocal, zLocal);
            if (old.getMaterial() != Material.AIR) {
                int xGlobal = xBase | xLocal;
                int yGlobal = yBase | yLocal;
                int zGlobal = zBase | zLocal;
                long position = Library.blockPosToLong(xGlobal, yGlobal, zGlobal);
                if (old.getBlock().hasTileEntity(old)) {
                    blockEntitiesOut.put(position, old);
                    continue;
                }
                if (checkNeighbor(loaded, chunkX, chunkZ, subY, height, srcs, neighbors, xLocal - 1, yLocal, zLocal, localMask)
                        || checkNeighbor(loaded, chunkX, chunkZ, subY, height, srcs, neighbors, xLocal + 1, yLocal, zLocal, localMask)
                        || checkNeighbor(loaded, chunkX, chunkZ, subY, height, srcs, neighbors, xLocal, yLocal - 1, zLocal, localMask)
                        || checkNeighbor(loaded, chunkX, chunkZ, subY, height, srcs, neighbors, xLocal, yLocal + 1, zLocal, localMask)
                        || checkNeighbor(loaded, chunkX, chunkZ, subY, height, srcs, neighbors, xLocal, yLocal, zLocal - 1, localMask)
                        || checkNeighbor(loaded, chunkX, chunkZ, subY, height, srcs, neighbors, xLocal, yLocal, zLocal + 1, localMask)) {
                    edgeOut.add(position);
                }
                if (dst == null) {
                    dst = copyOf(src);
                    airId = dst.data.palette.idFor(AIR_DEFAULT_STATE);
                }
                dst.data.storage.setAt(idx, airId);
                dst.blockRefCount--;
                if (old.getBlock().getTickRandomly()) dst.tickRefCount--;
                Block oldBlock = old.getBlock();
                // These blocks inherit Block.breakBlock, whose only work is tile-entity removal.
                if (oldBlock != Blocks.STONE && oldBlock != Blocks.DIRT && oldBlock != Blocks.GRASS) {
                    modifiedOut.put(position, old);
                }
            }
        }        return dst;
    }

    /** Load a sub-chunk slot using volatile reads. */

    private static ExtendedBlockStorage getEbsVolatile(ExtendedBlockStorage[] arr, int subY) {
        long off = ARR_BASE + ((long) subY) * ARR_SCALE;
        return (ExtendedBlockStorage) U.getReferenceVolatile(arr, off);
    }

    /**
    * Create a modified copy of a sub-chunk by applying local (0..4095) overrides.
    *
    * @param toUpdate map of packed-local index ({@code x | (z << 4) | (y << 8)}) -> new state
    * @param oldStatesOut optional sink of pre-change states keyed by global packed positions
    * @return null for no-op, or a modified copy (an all-air sub-chunk for became-empty)
    */

    @ThreadSafeMethod
    public static Optional<ExtendedBlockStorage> copyAndModify(int chunkX, int chunkZ, int subY, boolean hasSky,
                                                               ExtendedBlockStorage src,
                                                               Int2ObjectMap<IBlockState> toUpdate,
                                                               Long2ObjectMap<IBlockState> oldStatesOut) {
        if (toUpdate.isEmpty()) return null;

        ExtendedBlockStorage dst = null;
        boolean anyChange = false;
        int xBase = chunkX << 4;
        int yBase = subY << 4;
        int zBase = chunkZ << 4;

        for (Int2ObjectMap.Entry<IBlockState> e : toUpdate.int2ObjectEntrySet()) {
            int packedLocal = e.getIntKey();
            int lx = Library.getLocalX(packedLocal);
            int ly = Library.getLocalY(packedLocal);
            int lz = Library.getLocalZ(packedLocal);

            IBlockState newState = e.getValue();
            if (newState == null) throw new NullPointerException("newState");

            IBlockState oldState = (src != null && !src.isEmpty()) ? src.get(lx, ly, lz) : AIR_DEFAULT_STATE;
            if (oldState == newState) continue;

            if (dst == null) {
                if (src != null && !src.isEmpty()) {
                    dst = src.blockLight == null ? copyBlockStates(src) : copyOf(src);
                } else if (src != null && src.skyLight != null) {
                    // Sky-exposed empty sub-chunk: keep its skyLight instead of writing an all-zero copy.
                    dst = copyOf(src);
                } else {
                    if (newState.getBlock() == Blocks.AIR) continue;
                    dst = new ExtendedBlockStorage(yBase, hasSky);
                    // Air above the terrain: skyLight must be 15, not the all-zero default.
                    if (hasSky && dst.skyLight != null) {
                        Arrays.fill(dst.skyLight.getData(), (byte) 0xFF);
                    }
                }
            }

            if (oldStatesOut != null) {
                int xGlobal = xBase | lx;
                int yGlobal = yBase | ly;
                int zGlobal = zBase | lz;
                oldStatesOut.put(Library.blockPosToLong(xGlobal, yGlobal, zGlobal), oldState);
            }

            dst.set(lx, ly, lz, newState);
            anyChange = true;
        }        if (!anyChange) return null;
        return Optional.of(dst);
    }

    public static final class LightResult {
        final LightBuffer buf;
        public final Long2IntOpenHashMap masks = new Long2IntOpenHashMap();

        LightResult(LightBuffer buf) {
            this.buf = buf;
        }
    }

    public static boolean windowComplete(Chunk chunk) {
        World world = chunk.getWorld();
        if (!(world instanceof WorldServer)) return false;
        WorldServer ws = (WorldServer) world;
        ConcurrentHashMap<Long, Chunk> mirror = chunkMap.get(ws.provider.getDimension());
        Long2ObjectMap<Chunk> provider = ws.getChunkProvider().loadedChunks;
        for (int dz = -1; dz <= 1; dz++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dz == 0) continue;
                long cp = ChunkPos.asLong(chunk.x + dx, chunk.z + dz);
                boolean found = (mirror != null && mirror.containsKey(cp)) || provider.containsKey(cp);
                if (!found) return false;
            }
        }
        return true;
    }

    public static LightResult relightWindow(WorldServer world, Chunk center, int requestedMask) {
        LightBuffer buf = new LightBuffer(world, center);
        if (!buf.complete()) {
            return null;
        }
        computeSkylight(buf);
        computeBlockLight(buf);
        LightResult r = new LightResult(buf);
        for (int c = 4; c < 5; c++) {
            Chunk ch = buf.chunks[c];
            int mask = 0;
            for (int s = 0; s < 16; s++) {
                ExtendedBlockStorage e = buf.sec[c][s];
                if (e == null) continue;
                NibbleArray sk = e.getSkyLight();
                if (sk != null && buf.sky[c][s] != null && !Arrays.equals(sk.getData(), buf.sky[c][s])) mask |= 1 << s;
                NibbleArray bl = e.getBlockLight();
                if (bl != null && buf.blk[c][s] != null && !Arrays.equals(bl.getData(), buf.blk[c][s])) mask |= 1 << s;
            }
            if (mask != 0) r.masks.put(ChunkPos.asLong(ch.x, ch.z), mask);
        }
        return r;
    }

    public static int commitLight(LightResult r) {
        if (r == null) return -1;
        LightBuffer buf = r.buf;
        Chunk ch = buf.chunks[4];
        if (ch == null) return -1;
        int mask = r.masks.get(ChunkPos.asLong(ch.x, ch.z));
        ExtendedBlockStorage[] arr = ch.getBlockStorageArray();
        if (mask != 0) {
            for (int s = 0; s < 16; s++) {
                if ((mask & (1 << s)) == 0) continue;
                ExtendedBlockStorage e = arr[s];
                if (e == null || e == Chunk.NULL_BLOCK_STORAGE) continue;
                if (e != buf.sec[4][s]) return -1;
            }
            for (int s = 0; s < 16; s++) {
                if ((mask & (1 << s)) == 0) continue;
                ExtendedBlockStorage e = arr[s];
                if (e == null || e == Chunk.NULL_BLOCK_STORAGE) continue;
                NibbleArray sk = e.getSkyLight();
                if (sk != null && buf.sky[4][s] != null) {
                    System.arraycopy(buf.sky[4][s], 0, sk.getData(), 0, buf.sky[4][s].length);
                }
                NibbleArray bl = e.getBlockLight();
                if (bl != null && buf.blk[4][s] != null) {
                    System.arraycopy(buf.blk[4][s], 0, bl.getData(), 0, buf.blk[4][s].length);
                }
            }
            ch.markDirty();
        }
        ch.queuedLightChecks = 4096;
        return mask;
    }

    private static void computeSkylight(LightBuffer buf) {
        for (int gx = 0; gx < 48; gx++) {
            for (int gz = 0; gz < 48; gz++) {
                int h = buf.hmap[gx * 48 + gz];
                for (int y = 0; y < 256; y++) buf.setSky(gx, y, gz, y >= h ? 15 : 0);
            }
        }
        int[] queue = new int[8192];
        int head = 0, tail = 0;
        for (int gx = 0; gx < 48; gx++) {
            for (int gz = 0; gz < 48; gz++) {
                int h = buf.hmap[gx * 48 + gz];
                if (h < 0 || h > 255) continue;
                if (tail == queue.length) queue = Arrays.copyOf(queue, queue.length << 1);
                queue[tail++] = gx | (h << 6) | (gz << 14);
            }
        }
        for (int gx = 0; gx < 48; gx++) {
            for (int gz = 0; gz < 48; gz++) {
                int h = buf.hmap[gx * 48 + gz];
                for (int d = 0; d < 4; d++) {
                    int nx = gx + (d == 0 ? -1 : d == 1 ? 1 : 0);
                    int nz = gz + (d == 2 ? -1 : d == 3 ? 1 : 0);
                    if (nx < 0 || nx >= 48 || nz < 0 || nz >= 48) continue;
                    int hn = buf.hmap[nx * 48 + nz];
                    if (hn <= h) continue;
                    for (int y = h; y < hn && y < 256; y++) {
                        if (tail == queue.length) queue = Arrays.copyOf(queue, queue.length << 1);
                        queue[tail++] = gx | (y << 6) | (gz << 14);
                    }
                }
            }
        }
        while (head < tail) {
            int packed = queue[head++];
            int gx = packed & 63, y = (packed >>> 6) & 255, gz = (packed >>> 14) & 63;
            int light = buf.skyAt(gx, y, gz);
            if (light <= 1) continue;
            for (int d = 0; d < 6; d++) {
                int nx = gx + DX[d], ny = y + DY[d], nz = gz + DZ[d];
                if (nx < 0 || nx >= 48 || nz < 0 || nz >= 48 || ny < 0 || ny >= 256) continue;
                int op = buf.opacity(nx, ny, nz);
                if (op >= 15) continue;
                int step = (op == 0 && light == 15 && ny < y) ? 0 : Math.max(1, op);
                int nl = light - step;
                if (nl > buf.skyAt(nx, ny, nz)) {
                    buf.setSky(nx, ny, nz, nl);
                    if (tail == queue.length) queue = Arrays.copyOf(queue, queue.length << 1);
                    queue[tail++] = nx | (ny << 6) | (nz << 14);
                }
            }
        }
    }

    /** Rebuilds block light from block states only; light left behind by a removed source cannot survive. */
    private static void computeBlockLight(LightBuffer buf) {
        for (int c = 0; c < 9; c++) {
            for (int s = 0; s < 16; s++) {
                if (buf.blk[c][s] != null) Arrays.fill(buf.blk[c][s], (byte) 0);
            }
        }
        int[] queue = new int[8192];
        int head = 0, tail = 0;
        for (int gx = 0; gx < 48; gx++) {
            for (int gz = 0; gz < 48; gz++) {
                for (int y = 0; y < 256; y++) {
                    if (buf.section(gx, y, gz) == null) continue;
                    int emit = buf.emission(gx, y, gz);
                    if (emit <= 0) continue;
                    buf.setBlock(gx, y, gz, emit);
                    if (tail == queue.length) queue = Arrays.copyOf(queue, queue.length << 1);
                    queue[tail++] = gx | (y << 6) | (gz << 14);
                }
            }
        }
        while (head < tail) {
            int packed = queue[head++];
            int gx = packed & 63, y = (packed >>> 6) & 255, gz = (packed >>> 14) & 63;
            int light = buf.blockAt(gx, y, gz);
            if (light <= 1) continue;
            for (int d = 0; d < 6; d++) {
                int nx = gx + DX[d], ny = y + DY[d], nz = gz + DZ[d];
                if (nx < 0 || nx >= 48 || nz < 0 || nz >= 48 || ny < 0 || ny >= 256) continue;
                int op = buf.opacity(nx, ny, nz);
                if (op >= 15) continue;
                int nl = light - Math.max(1, op);
                if (nl > buf.blockAt(nx, ny, nz)) {
                    buf.setBlock(nx, ny, nz, nl);
                    if (tail == queue.length) queue = Arrays.copyOf(queue, queue.length << 1);
                    queue[tail++] = nx | (ny << 6) | (nz << 14);
                }
            }
        }
    }

    public static void rebuildHeightMap(Chunk chunk, int subY) {
        ExtendedBlockStorage[] storages = chunk.getBlockStorageArray();
        int[] hm = chunk.heightMap;
        int base = subY << 4;
        int bx = chunk.x << 4, bz = chunk.z << 4;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        World world = chunk.getWorld();
        int min = Integer.MAX_VALUE;
        for (int lz = 0; lz < 16; lz++) {
            for (int lx = 0; lx < 16; lx++) {
                int idx = (lz << 4) | lx;
                int old = hm[idx];
                if (old - 1 > base + 15) {
                    if (old < min) min = old;
                    continue;
                }
                int top = 0;
                for (int y = Math.min(old - 1, 255); y >= 0; y--) {
                    ExtendedBlockStorage e = storages[y >> 4];
                    if (e == null || e == Chunk.NULL_BLOCK_STORAGE) continue;
                    IBlockState st = e.get(lx, y & 15, lz);
                    pos.setPos(bx + lx, y, bz + lz);
                    if (st.getBlock().getLightOpacity(st, world, pos) != 0) {
                        top = y + 1;
                        break;
                    }
                }
                hm[idx] = top;
                if (top < min) min = top;
            }
        }
        chunk.heightMapMinimum = min == Integer.MAX_VALUE ? 0 : min;
    }

    private static ConcurrentHashMap<Long, LightJob> queueOf(int dim) {
        return LIGHT_QUEUE.computeIfAbsent(dim, k -> new ConcurrentHashMap<>());
    }

    private static void bumpJob(int dim, long cp) {
        ConcurrentHashMap<Long, LightJob> jobs = queueOf(dim);
        jobs.compute(cp, (k, job) -> {
            if (job == null) job = new LightJob();
            job.version++;
            return job;
        });
    }

    /** Every block-changing path calls this; the chunk and its eight loaded neighbours are re-queued. */
    public static void markBlocksChanged(Chunk chunk) {
        markBlocksChanged(chunk, true);
    }

    public static void markBlocksChanged(Chunk chunk, boolean withNeighbours) {
        World w = chunk.getWorld();
        if (!(w instanceof WorldServer)) return;
        WorldServer ws = (WorldServer) w;
        int dim = ws.provider.getDimension();
        ConcurrentHashMap<Long, Chunk> mirror = chunkMap.get(dim);
        Long2ObjectMap<Chunk> provider = ws.getChunkProvider().loadedChunks;
        int cx = chunk.x, cz = chunk.z;
        bumpJob(dim, ChunkPos.asLong(cx, cz));
        if (!withNeighbours) return;
        for (int dz = -1; dz <= 1; dz++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dz == 0) continue;
                long cp = ChunkPos.asLong(cx + dx, cz + dz);
                boolean loaded = (mirror != null && mirror.containsKey(cp)) || provider.containsKey(cp);
                if (loaded) {
                    bumpJob(dim, cp);
                }
            }
        }
    }

    public static void requestLight(Chunk chunk, int mask) {
        World w = chunk.getWorld();
        if (!(w instanceof WorldServer)) return;
        queueOf(w.provider.getDimension()).compute(ChunkPos.asLong(chunk.x, chunk.z), (k, job) -> {
            if (job == null) {
                job = new LightJob();
            }
            job.mask |= mask;
            return job;
        });
    }

    public static void relightAll(WorldServer world, Long2IntOpenHashMap masks) {
        int dim = world.provider.getDimension();
        Long2ObjectMap<Chunk> loaded = world.getChunkProvider().loadedChunks;
        for (Long2IntMap.Entry e : masks.long2IntEntrySet()) {
            int mask = e.getIntValue();
            if (mask == 0) continue;
            long cp = e.getLongKey();
            Chunk chunk = loaded.get(cp);
            if (chunk == null) continue;
            bumpJob(dim, cp);
            requestLight(chunk, mask);
            int cx = Library.getChunkPosX(cp), cz = Library.getChunkPosZ(cp);
            for (int dz = -1; dz <= 1; dz++) {
                for (int dx = -1; dx <= 1; dx++) {
                    if (dx == 0 && dz == 0) continue;
                    bumpJob(dim, ChunkPos.asLong(cx + dx, cz + dz));
                }
            }
        }
    }

    /** Single main-thread driver for light work, so executors only register and queued work outlives them. */
    public static void tickLight(WorldServer world) {
        int dim = world.provider.getDimension();
        ConcurrentHashMap<Long, LightJob> jobs = queueOf(dim);
        if (jobs.isEmpty()) {
            releaseLightPoolIfIdle();
            return;
        }
        for (Map.Entry<Long, LightJob> e : jobs.entrySet()) {
            if (LIGHT_IN_FLIGHT.get() >= LIGHT_IN_FLIGHT_LIMIT) break;
            long cp = e.getKey().longValue();
            LightJob job = e.getValue();
            if (job.computing) continue;
            Chunk chunk = world.getChunkProvider().loadedChunks.get(cp);
            if (chunk == null) {
                jobs.remove(cp, job);
                continue;
            }
            if (!windowComplete(chunk)) {
                job.attempts++;
                if (job.attempts > 3) {
                    jobs.remove(cp, job);
                    continue;
                }
                int cx = chunk.x, cz = chunk.z;
                for (int dz = -1; dz <= 1; dz++) {
                    for (int dx = -1; dx <= 1; dx++) world.getChunk(cx + dx, cz + dz);
                }
                continue;
            }
            job.computing = true;
            final int expected = job.version;
            LIGHT_IN_FLIGHT.incrementAndGet();
            try {
                submitLightTask(() -> {
                    LightResult r = relightWindow(world, chunk, job.mask);
                    world.addScheduledTask(() -> {
                        try {
                            if (r == null) {
                                job.computing = false;
                                return;
                            }
                            if (job.version != expected || jobs.get(cp) != job) {
                                job.computing = false;
                                return;
                            }
                            int effective = commitLight(r);
                            if (effective < 0) {
                                job.computing = false;
                                job.attempts++;
                                if (job.attempts > 3) jobs.remove(cp, job);
                                return;
                            }
                            recordLightMask(dim, cp, job.mask | effective);
                            jobs.remove(cp, job);
                        } finally {
                            LIGHT_IN_FLIGHT.decrementAndGet();
                            releaseLightPoolIfIdle();
                        }
                    });
                });
            } catch (RuntimeException ex) {
                job.computing = false;
                LIGHT_IN_FLIGHT.decrementAndGet();
                releaseLightPoolIfIdle();
                return;
            }
        }
        if (jobs.isEmpty()) {
            LIGHT_QUEUE.remove(world.provider.getDimension(), jobs);
            releaseLightPoolIfIdle();
        }
    }

    public static boolean hasPendingLight(WorldServer world, long cp) {
        return queueOf(world.provider.getDimension()).get(cp) != null;
    }

    public static boolean anyPendingLight(WorldServer world) {
        return !queueOf(world.provider.getDimension()).isEmpty();
    }

    private static void recordLightMask(int dim, long cp, int mask) {
        if (mask == 0) return;
        LIGHT_MASKS.computeIfAbsent(dim, k -> new ConcurrentHashMap<>()).merge(cp, mask, (a, b) -> a | b);
    }

    public static int takeLightMask(WorldServer world, long cp) {
        ConcurrentHashMap<Long, Integer> m = LIGHT_MASKS.get(world.provider.getDimension());
        if (m == null) return 0;
        Integer v = m.remove(cp);
        return v == null ? 0 : v.intValue();
    }

    private static void submitLightTask(Runnable r) {
        ForkJoinPool p;
        synchronized (ChunkUtil.class) {
            if (lightPool == null || lightPool.isShutdown()) lightPool = BombForkJoinPool.acquire();
            p = lightPool;
        }
        p.submit(r);
    }

    private static void releaseLightPoolIfIdle() {
        synchronized (ChunkUtil.class) {
            if (lightPool == null || LIGHT_IN_FLIGHT.get() != 0) return;
            for (ConcurrentHashMap<Long, LightJob> m : LIGHT_QUEUE.values()) {
                if (!m.isEmpty()) return;
            }
            BombForkJoinPool.release();
            lightPool = null;
        }
    }

    public static void sendChunk(WorldServer world, Chunk chunk, int mask) {
        if (mask == 0) return;
        PlayerChunkMapEntry entry =
                world.getPlayerChunkMap().getEntry(chunk.x, chunk.z);
        if (entry != null) {
            entry.sendPacket(new SPacketChunkData(chunk, mask));
        }
    }

    public static void drainRemaining(WorldServer world) {
        ConcurrentHashMap<Long, Integer> m = LIGHT_MASKS.remove(world.provider.getDimension());
        if (m == null) return;
        for (Map.Entry<Long, Integer> e : m.entrySet()) {
            Chunk chunk = world.getChunkProvider().loadedChunks.get(e.getKey().longValue());
            if (chunk == null) continue;
            sendChunk(world, chunk, e.getValue().intValue());
        }
    }

    private static final class LightBuffer {
        final WorldServer world;
        final int cx, cz;
        final Chunk[] chunks = new Chunk[9];
        final ExtendedBlockStorage[][] sec = new ExtendedBlockStorage[9][16];
        final byte[][][] sky = new byte[9][16][];
        final byte[][][] blk = new byte[9][16][];
        final int[] hmap = new int[48 * 48];
        final boolean hasSky;
        final int minX, minZ;
        final HashMap<Integer, Integer> virtBlk = new HashMap<>();
        final HashMap<Integer, Integer> virtSky = new HashMap<>();
        final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        LightBuffer(WorldServer world, Chunk center) {
            this.world = world;
            this.cx = center.x;
            this.cz = center.z;
            this.hasSky = world.provider.hasSkyLight();
            this.minX = (cx - 1) << 4;
            this.minZ = (cz - 1) << 4;
            ConcurrentHashMap<Long, Chunk> mirror = chunkMap.get(world.provider.getDimension());
            Long2ObjectMap<Chunk> provider = world.getChunkProvider().loadedChunks;
            int i = 0;
            for (int dz = -1; dz <= 1; dz++) {
                for (int dx = -1; dx <= 1; dx++) {
                    if (dx == 0 && dz == 0) {
                        chunks[i++] = center;
                        continue;
                    }
                    long cp = ChunkPos.asLong(cx + dx, cz + dz);
                    Chunk nb = mirror == null ? null : mirror.get(cp);
                    if (nb == null) nb = provider.get(cp);
                    chunks[i++] = nb;
                }
            }
            for (int c = 0; c < 9; c++) {
                Chunk ch = chunks[c];
                if (ch == null) continue;
                ExtendedBlockStorage[] arr = ch.getBlockStorageArray();
                for (int s = 0; s < 16; s++) {
                    ExtendedBlockStorage e = arr[s];
                    if (e == null || e == Chunk.NULL_BLOCK_STORAGE) continue;
                    sec[c][s] = e;
                    NibbleArray sk = e.getSkyLight();
                    if (sk != null) sky[c][s] = Arrays.copyOf(sk.getData(), sk.getData().length);
                    NibbleArray bl = e.getBlockLight();
                    if (bl != null) blk[c][s] = Arrays.copyOf(bl.getData(), bl.getData().length);
                }
            }
            for (int gx = 0; gx < 48; gx++) {
                for (int gz = 0; gz < 48; gz++) {
                    hmap[gx * 48 + gz] = computeHeight(gx, gz);
                }
            }
        }

        boolean complete() {
            for (int i = 0; i < 9; i++) if (chunks[i] == null) return false;
            return true;
        }

        ExtendedBlockStorage section(int gx, int gy, int gz) {
            if (gx < 0 || gx >= 48 || gz < 0 || gz >= 48 || gy < 0 || gy > 255) return null;
            int c = (gx >> 4) + (gz >> 4) * 3;
            if (c < 0 || c > 8) return null;
            return sec[c][gy >> 4];
        }

        int index(int gx, int gy, int gz) {
            return (gx >> 4) + (gz >> 4) * 3;
        }

        int computeHeight(int gx, int gz) {
            for (int y = 255; y >= 0; y--) {
                ExtendedBlockStorage e = section(gx, y, gz);
                if (e == null) continue;
                IBlockState st = e.get(gx & 15, y & 15, gz & 15);
                pos.setPos(minX + gx, y, minZ + gz);
                if (!st.getBlock().isAir(st, world, pos)) return y + 1;
            }
            return 0;
        }

        int opacity(int gx, int gy, int gz) {
            ExtendedBlockStorage e = section(gx, gy, gz);
            if (e == null) return 0;
            IBlockState st = e.get(gx & 15, gy & 15, gz & 15);
            pos.setPos(minX + gx, gy, minZ + gz);
            return st.getBlock().getLightOpacity(st, world, pos);
        }

        int emission(int gx, int gy, int gz) {
            ExtendedBlockStorage e = section(gx, gy, gz);
            if (e == null) return 0;
            IBlockState st = e.get(gx & 15, gy & 15, gz & 15);
            pos.setPos(minX + gx, gy, minZ + gz);
            return st.getBlock().getLightValue(st, world, pos);
        }

        int defaultSky(int gx, int gy, int gz) {
            return (hasSky && gy >= hmap[gx * 48 + gz]) ? 15 : 0;
        }

        int skyAt(int gx, int gy, int gz) {
            ExtendedBlockStorage e = section(gx, gy, gz);
            if (e == null) {
                Integer v = virtSky.get(gx | (gy << 6) | (gz << 14));
                return v != null ? v.intValue() : defaultSky(gx, gy, gz);
            }
            byte[] a = sky[index(gx, gy, gz)][gy >> 4];
            if (a == null) return 0;
            int i = (gy & 15) << 8 | (gz & 15) << 4 | (gx & 15);
            int b = a[i >> 1] & 0xFF;
            return (i & 1) == 0 ? (b & 15) : (b >> 4);
        }

        void setSky(int gx, int gy, int gz, int v) {
            ExtendedBlockStorage e = section(gx, gy, gz);
            if (e == null) {
                if (v != defaultSky(gx, gy, gz)) virtSky.put(gx | (gy << 6) | (gz << 14), v);
                return;
            }
            byte[] a = sky[index(gx, gy, gz)][gy >> 4];
            if (a == null) return;
            int i = (gy & 15) << 8 | (gz & 15) << 4 | (gx & 15);
            if ((i & 1) == 0) a[i >> 1] = (byte) ((a[i >> 1] & 0xF0) | (v & 15));
            else a[i >> 1] = (byte) ((a[i >> 1] & 0x0F) | ((v & 15) << 4));
        }

        int blockAt(int gx, int gy, int gz) {
            ExtendedBlockStorage e = section(gx, gy, gz);
            if (e == null) {
                Integer v = virtBlk.get((gx | (gy << 6) | (gz << 14)));
                return v == null ? 0 : v.intValue();
            }
            byte[] a = blk[index(gx, gy, gz)][gy >> 4];
            if (a == null) return 0;
            int i = (gy & 15) << 8 | (gz & 15) << 4 | (gx & 15);
            int b = a[i >> 1] & 0xFF;
            return (i & 1) == 0 ? (b & 15) : (b >> 4);
        }

        void setBlock(int gx, int gy, int gz, int v) {
            ExtendedBlockStorage e = section(gx, gy, gz);
            if (e == null) {
                virtBlk.put((gx | (gy << 6) | (gz << 14)), Integer.valueOf(v));
                return;
            }
            byte[] a = blk[index(gx, gy, gz)][gy >> 4];
            if (a == null) return;
            int i = (gy & 15) << 8 | (gz & 15) << 4 | (gx & 15);
            if ((i & 1) == 0) a[i >> 1] = (byte) ((a[i >> 1] & 0xF0) | (v & 15));
            else a[i >> 1] = (byte) ((a[i >> 1] & 0x0F) | ((v & 15) << 4));
        }
    }

    private static final class LightJob {
        int mask;
        int version;
        int attempts;
        boolean computing;
    }

    private static final class NeighborCache {
        ExtendedBlockStorage[] negX, posX, negZ, posZ;
    }
}
