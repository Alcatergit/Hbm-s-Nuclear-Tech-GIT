package com.hbm.entity.effect;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockMushHuge;
import com.hbm.blocks.generic.BlockPowder;
import com.hbm.blocks.generic.WasteDirt;
import com.hbm.blocks.generic.WasteLeaves;
import com.hbm.blocks.generic.WasteLog;
import com.hbm.config.BombConfig;
import com.hbm.config.CompatibilityConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.config.VersatileConfig;
import com.hbm.entity.logic.EntityChunky;
import com.hbm.handler.threading.BombForkJoinPool;
import com.hbm.interfaces.ServerThread;
import com.hbm.lib.Library;
import com.hbm.lib.internal.UnsafeHolder;
import com.hbm.main.MainRegistry;
import com.hbm.saveddata.AuxSavedData;
import com.hbm.util.ChunkUtil;
import java.util.concurrent.atomic.AtomicInteger;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLongArray;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.hbm.lib.internal.UnsafeHolder.U;

/** Parallel fallout conversion: radial (underground) and column passes, sampled off-thread and applied on the main thread in copy-on-write sub-chunks. */
public class EntityFalloutRainParallelized extends EntityChunky implements BombForkJoinPool.IJobCancellable {

    private static final DataParameter<Integer> SCALE = EntityDataManager.createKey(EntityFalloutRainParallelized.class, DataSerializers.VARINT);
    private static final Block[] SELLAFIELD = {
            ModBlocks.sellafield_slaked, ModBlocks.sellafield_0, ModBlocks.sellafield_1,
            ModBlocks.sellafield_2, ModBlocks.sellafield_3, ModBlocks.sellafield_4, ModBlocks.sellafield_core
    };
    private static final Set<Block> META_TIERED = new HashSet<>(Arrays.asList(
            ModBlocks.fallout, ModBlocks.waste_earth, ModBlocks.waste_dirt,
            ModBlocks.waste_gravel, ModBlocks.waste_snow, ModBlocks.waste_snow_block,
            ModBlocks.waste_mycelium, ModBlocks.waste_sand, ModBlocks.waste_sand_red,
            ModBlocks.waste_trinitite, ModBlocks.waste_trinitite_red,
            ModBlocks.waste_sandstone, ModBlocks.waste_sandstone_red,
            ModBlocks.waste_terracotta, ModBlocks.waste_grass_tall));
    private static final long OFF_POOL_ACQUIRED = UnsafeHolder.fieldOffset(EntityFalloutRainParallelized.class, "poolAcquired");
    private static final long OFF_JOB_REGISTERED = UnsafeHolder.fieldOffset(EntityFalloutRainParallelized.class, "jobRegistered");
    private static final long OFF_MAP_ACQUIRED = UnsafeHolder.fieldOffset(EntityFalloutRainParallelized.class, "mapAcquired");
    private static final long OFF_COLLECT_FINISHED = UnsafeHolder.fieldOffset(EntityFalloutRainParallelized.class, "collectFinished");
    private static final long OFF_DESTROY_FINISHED = UnsafeHolder.fieldOffset(EntityFalloutRainParallelized.class, "destroyFinished");
    private static final long OFF_ACTIVE_WORKER_TASKS = UnsafeHolder.fieldOffset(EntityFalloutRainParallelized.class, "activeWorkerTasks");
    private static final long OFF_PENDING_CHUNKS = UnsafeHolder.fieldOffset(EntityFalloutRainParallelized.class, "pendingChunks");
    private static final long OFF_FINISH_QUEUED = UnsafeHolder.fieldOffset(EntityFalloutRainParallelized.class, "finishQueued");
    private static final long OFF_CANCEL_CLEANUP = UnsafeHolder.fieldOffset(EntityFalloutRainParallelized.class, "cancelCleanup");
    private final WorldServer worldServer;
    public boolean doFallout = false;
    public boolean doFlood = false;
    public int waterLevel = 0;
    // Column-phase thresholds.

    private double s0, s1, s2, s3, s4, s5, s6, s7;
    // Radial-phase thresholds.

    private double rS0, rS1, rS2, rS3, rS4, rS5, rS6, rS7;
    private int fallingRadius;
    private int radialRadius;
    private boolean doDrop;
    private final List<Long> chunksToProcess = new ArrayList<>();
    private final List<Long> outerChunksToProcess = new ArrayList<>();
    private final ConcurrentHashMap<Long, ConcurrentMap<Long, IBlockState>> replacements = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, ConcurrentMap<Long, IBlockState>> sampledSources = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, ConcurrentLinkedQueue<FallColumn>> fallColumns = new ConcurrentHashMap<>();
    // Positions requiring a neighbour notification (flag 3), keyed by chunk.

    private final ConcurrentHashMap<Long, Set<Long>> notifyPositions = new ConcurrentHashMap<>();
    private final ConcurrentLinkedQueue<Long> chunkLoadQueue = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Long> priorityLoadQueue = new ConcurrentLinkedQueue<>();
    private final ConcurrentHashMap<Long, ConcurrentLinkedQueue<double[]>> radialWaitingRoom = new ConcurrentHashMap<>();
    private final PriorityBlockingQueue<Long> applyQueue = new PriorityBlockingQueue<>(64, (a, b) ->
            Double.compare(chunkDistSq(a.longValue()), chunkDistSq(b.longValue())));
    private final Long2IntOpenHashMap sectionMaskByChunk = new Long2IntOpenHashMap();
    private final LinkedHashMap<Long, Integer> pendingSend = new LinkedHashMap<>();
    // Chunks actually converted. The margin chunks outside it must not be re-scanned, or pendingChunks goes negative.

    private final Set<Long> conversionChunks = ConcurrentHashMap.newKeySet();
    // Chunks whose column scan has run; a re-scan after a reload must not decrement pendingChunks again.

    private final Set<Long> scannedChunks = ConcurrentHashMap.newKeySet();
    // Terrain-populated chunks. isTerrainPopulated survives unload, so the neighbour gate uses this instead of loadedChunks.

    private final Set<Long> populatedChunks = ConcurrentHashMap.newKeySet();
    private final Set<Long> reloadQueued = ConcurrentHashMap.newKeySet();
    private List<Long> allChunksForColumn;
    private int columnGrain;
    private int radialIdleTicks;
    private final AtomicInteger radialPending = new AtomicInteger();
    private volatile boolean radialComplete;
    private volatile boolean columnSubmitted;
    private volatile Throwable failure;
    private volatile UUID detonator;
    private int jobDimension = Integer.MIN_VALUE;
    private volatile int poolAcquired, jobRegistered, mapAcquired, collectFinished, destroyFinished,
            activeWorkerTasks, pendingChunks, finishQueued, cancelCleanup;
    private volatile boolean cancelling;
    private ForkJoinPool pool;
    private ConcurrentHashMap<Long, Chunk> mirror;

    public EntityFalloutRainParallelized(World world) {
        super(world);
        this.worldServer = world.isRemote ? null : (WorldServer) world;
        this.setSize(4, 20);
        this.ignoreFrustumCheck = false;
        this.isImmuneToFire = true;
        this.waterLevel = getInt(CompatibilityConfig.fillCraterWithWater.get(world.provider.getDimension()));
        if (this.waterLevel == 0) {
            this.waterLevel = world.getSeaLevel();
        } else if (this.waterLevel < 0 && this.waterLevel > -world.getSeaLevel()) {
            this.waterLevel = world.getSeaLevel() - this.waterLevel;
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(SCALE, 0);
    }

    public void setScale(int rainScale, int radialRadius, int fallingRadius) {
        this.dataManager.set(SCALE, rainScale);
        this.s0 = 0.8D * rainScale;
        this.s1 = 0.65D * rainScale;
        this.s2 = 0.5D * rainScale;
        this.s3 = 0.4D * rainScale;
        this.s4 = 0.3D * rainScale;
        this.s5 = 0.2D * rainScale;
        this.s6 = 0.1D * rainScale;
        this.s7 = 0.05D * rainScale;

        this.radialRadius = radialRadius;
        this.rS0 = 0.84 * radialRadius;
        this.rS1 = 0.74 * radialRadius;
        this.rS2 = 0.64 * radialRadius;
        this.rS3 = 0.54 * radialRadius;
        this.rS4 = 0.44 * radialRadius;
        this.rS5 = 0.34 * radialRadius;
        this.rS6 = 0.24 * radialRadius;
        this.rS7 = 0.05 * radialRadius;

        this.fallingRadius = fallingRadius > 15 ? fallingRadius : 0;
        this.doDrop = this.fallingRadius > 20;
    }

    public int getScale() {
        int scale = this.dataManager.get(SCALE);
        return scale == 0 ? 1 : scale;
    }

    @Override
    public void onUpdate() {
        if (world.isRemote) return;
        if (!CompatibilityConfig.isWarDim(world)) {
            setDead();
            return;
        }
        if (failure != null) {
            setDead();
            return;
        }
        if (collectFinished == 0 && mirror == null) {
            startWorkers();
        }
        if (radialComplete && !columnSubmitted && pool != null && !pool.isShutdown()) {
            columnSubmitted = true;
                        pool.submit(new ColumnTask(allChunksForColumn, 0, allChunksForColumn.size(), columnGrain));
        }
        if (!radialComplete && chunkLoadQueue.isEmpty() && priorityLoadQueue.isEmpty() && activeWorkerTasks == 0) {
            if (++radialIdleTicks > 200) radialComplete = true;
        } else {
            radialIdleTicks = 0;
        }
        if (collectFinished == 0 && (ticksExisted & 3) == 0) {
            submitRetry();
        }
                long deadline = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(BombConfig.mk5);
        while (System.nanoTime() < deadline) {
            Long cp = priorityLoadQueue.poll();
            if (cp == null) cp = chunkLoadQueue.poll();
            if (cp != null) {
                loadChunk(cp.longValue());
            }
            Long apply = applyQueue.poll();
            if (apply != null) {
                applyChunk(apply.longValue());
            }
            if (cp == null && apply == null) break;
        }
        drainSendQueue();
        maybeFinish();
    }

    @Override
    public void setDead() {
        cancel();
        super.setDead();
    }

    public void cancel() {
        cancelling = true;
        U.putIntRelease(this, OFF_COLLECT_FINISHED, 1);
        U.putIntRelease(this, OFF_DESTROY_FINISHED, 1);
        if (activeWorkerTasks == 0) {
            finishCancellation();
        } else {
            worldServer.addScheduledTask(this::pollCancelCleanup);
        }
    }

    @Override
    public void cancelJob() {
        cancel();
    }

    synchronized void fail(Throwable cause) {
        if (failure != null) return;
        failure = cause;
        MainRegistry.logger.error("Parallel fallout work failed", cause);
    }

    private void startWorkers() {
        if (chunksToProcess.isEmpty() && outerChunksToProcess.isEmpty()) gatherChunks();
        if (poolAcquired == 0) {
            if (U.compareAndSetInt(this, OFF_POOL_ACQUIRED, 0, 1)) pool = BombForkJoinPool.acquire();
        }
        registerJobIfNeeded();
        mirror = ChunkUtil.acquireMirrorMap(worldServer);
        U.putIntRelease(this, OFF_MAP_ACQUIRED, 1);
        List<Long> allChunks = new ArrayList<>(chunksToProcess.size() + outerChunksToProcess.size());
        allChunks.addAll(chunksToProcess);
        allChunks.addAll(outerChunksToProcess);
        prepareResumeState(allChunks);

        Set<Long> enqueued = new HashSet<>(allChunks.size() * 3);
        for (long cp : allChunks) {
            int cx = Library.getChunkPosX(cp);
            int cz = Library.getChunkPosZ(cp);
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    long ncp = ChunkPos.asLong(cx + dx, cz + dz);
                    if (!enqueued.add(ncp)) continue;
                    Chunk loadedChunk = worldServer.getChunkProvider().loadedChunks.get(ncp);
                    if (loadedChunk != null && loadedChunk.isTerrainPopulated()) {
                        populatedChunks.add(ncp);
                        mirror.put(ncp, loadedChunk);
                        continue;
                    }
                    chunkLoadQueue.offer(ncp);
                }
            }
        }

        if (pool == null || pool.isShutdown()) {
            U.putIntRelease(this, OFF_COLLECT_FINISHED, 1);
            maybeFinish();
            return;
        }
                int grain = Math.max(1, allChunks.size() / Math.max(1, pool.getParallelism() * 4));
        if (grain < 1) grain = 1;
        allChunksForColumn = allChunks;
        columnGrain = grain;
        if (radialRadius > 0 && !radialComplete) {
            int rayCount = (int) (Math.PI * radialRadius * radialRadius);
                        int rayGrain = Math.max(512, rayCount / Math.max(1, pool.getParallelism() * 4));
            radialPending.set(rayCount);
            pool.submit(new RadialTask(0, rayCount, rayGrain, rayCount));
        } else {
            radialComplete = true;
        }
    }

    private void gatherChunks() {
        double outerRange = (doFallout ? getScale() : fallingRadius) + 12.0;
        double outerRangeSq = outerRange * outerRange;
        double innerLimit = getScale();
        double innerLimitSq = innerLimit * innerLimit;
        int cx0 = ((int) posX) >> 4, cz0 = ((int) posZ) >> 4;
        int cr = (int) Math.ceil(outerRange / 16.0) + 1;
        for (int cx = cx0 - cr; cx <= cx0 + cr; cx++) {
            double dcx = (cx << 4) + 8 - posX;
            if (dcx * dcx > outerRangeSq) continue;
            int maxDcz = (int) Math.floor(Math.sqrt(outerRangeSq - dcx * dcx));
            int czOff = (maxDcz + 8) >> 4;
            for (int cz = cz0 - czOff; cz <= cz0 + czOff; cz++) {
                double dcz = (cz << 4) + 8 - posZ;
                double d2 = dcx * dcx + dcz * dcz;
                if (d2 > outerRangeSq) continue;
                long cp = ChunkPos.asLong(cx, cz);
                if (d2 > innerLimitSq) outerChunksToProcess.add(cp);
                else chunksToProcess.add(cp);
            }
        }
            }

    /** Seed resume state: scanned chunks from the persisted mask, populated chunks from NBT plus loaded chunks. */

    private void prepareResumeState(List<Long> allChunks) {
        for (Chunk c : worldServer.getChunkProvider().loadedChunks.values()) {
            if (c.isTerrainPopulated()) populatedChunks.add(ChunkPos.asLong(c.x, c.z));
        }
        int preScanned = 0;
        for (long cp : sectionMaskByChunk.keySet()) {
            if (scannedChunks.add(cp)) preScanned++;
        }
        pendingChunks = allChunks.size() - preScanned;
        conversionChunks.clear();
        conversionChunks.addAll(allChunks);
    }

    private void registerJobIfNeeded() {
        if (U.compareAndSetInt(this, OFF_JOB_REGISTERED, 0, 1)) {
            int dim = world == null ? Integer.MIN_VALUE : world.provider.getDimension();
            jobDimension = dim;
            BombForkJoinPool.register(dim, this);
        }
    }

    private void unregisterJobIfNeeded() {
        if (U.getAndSetInt(this, OFF_JOB_REGISTERED, 0) != 0) {
            int dim = jobDimension;
            jobDimension = Integer.MIN_VALUE;
            if (dim != Integer.MIN_VALUE) BombForkJoinPool.unregister(dim, this);
        }
    }

    private void releasePoolIfHeld() {
        unregisterJobIfNeeded();
        if (U.getAndSetInt(this, OFF_POOL_ACQUIRED, 0) != 0) {
            BombForkJoinPool.release();
            pool = null;
        }
    }

    private void triggerWeather() {
        if (RadiationConfig.rain > 0 && doFlood) {
            int scale = getScale();
            if ((doFallout && scale > 160) || scale > 200) {
                world.getWorldInfo().setThundering(true);
                world.getWorldInfo().setThunderTime(RadiationConfig.rain);
                AuxSavedData.setThunder(world, RadiationConfig.rain);
            } else if ((doFallout && scale > 80) || scale > 100) {
                world.getWorldInfo().setRaining(true);
                world.getWorldInfo().setRainTime(RadiationConfig.rain);
            }
        }
    }

    @ServerThread
    private void loadChunk(long chunkPos) {
        reloadQueued.remove(chunkPos);
        int cx = Library.getChunkPosX(chunkPos);
        int cz = Library.getChunkPosZ(chunkPos);
        Chunk chunk = world.getChunk(cx, cz);
        if (chunk != null) {
            if (!chunk.isTerrainPopulated()) {
                world.getChunk(cx + 1, cz);
                world.getChunk(cx, cz + 1);
                world.getChunk(cx + 1, cz + 1);
                ChunkProviderServer provider = (ChunkProviderServer) world.getChunkProvider();
                chunk.populate(provider, provider.chunkGenerator);
                chunk.generateSkylightMap();
            }
            populatedChunks.add(chunkPos);
            mirror.put(chunkPos, chunk);
        }
        ForkJoinPool p = pool;
        if (p != null && !p.isShutdown()) {
            U.getAndAddInt(this, OFF_ACTIVE_WORKER_TASKS, 1);
            p.submit(() -> {
                try {
                    // Radial first, then column: the column scan must see the radial sellafield and downgrade it.
                    resumeRadialRays(chunkPos);
                    retryChunk(chunkPos);
                } catch (Exception e) {
                    fail(e);
                } finally {
                    U.getAndAddInt(this, OFF_ACTIVE_WORKER_TASKS, -1);
                }
            });
        }
    }

    private void resumeRadialRays(long chunkPos) {
        ConcurrentLinkedQueue<double[]> waiters = radialWaitingRoom.remove(chunkPos);
        if (waiters == null || waiters.isEmpty()) return;
        RadialWorker worker = new RadialWorker();
        double[] dir;
        while ((dir = waiters.poll()) != null) {
            if (Thread.currentThread().isInterrupted() || destroyFinished != 0) break;
            worker.processRay(dir[0], dir[1], dir[2]);
            if (!worker.deferred) rayFinished();
        }
    }

    private void rayFinished() {
        if (radialPending.decrementAndGet() <= 0) {
            if (!radialComplete)             radialComplete = true;
        }
    }

    /**
    * True once all 8 neighbours are terrain-populated (their decoration has run).
    */

    private boolean isNeighborhoodPopulated(long cp) {
        int cx = Library.getChunkPosX(cp);
        int cz = Library.getChunkPosZ(cp);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (dx == 0 && dz == 0) continue;
                if (!populatedChunks.contains(ChunkPos.asLong(cx + dx, cz + dz))) return false;
            }
        }
        return true;
    }

    private void retryChunk(long cp) {
        if (!radialComplete) return;
        if (!conversionChunks.contains(cp)) {
            return; // margin chunk, loaded only to satisfy the isNeighborhoodPopulated() neighbour gate, not converted
        }
        int cx = Library.getChunkPosX(cp);
        int cz = Library.getChunkPosZ(cp);
        Chunk chunk = ChunkUtil.getLoadedChunk(mirror, cp);
        if (chunk == null || !chunk.isTerrainPopulated()) {
            requeueUnloaded(cp);
            return;
        }
        if (!isNeighborhoodPopulated(cp)) {
            return;
        }
        ColumnWorker worker = new ColumnWorker();
        worker.processChunk(cp, cx, cz, chunk.getBlockStorageArray());
    }

    private void requeueUnloaded(long cp) {
        if (!conversionChunks.contains(cp)) return;
        if (reloadQueued.add(cp)) chunkLoadQueue.offer(cp);
    }

    private void submitRetry() {
        if (!radialComplete) return;
        if (destroyFinished != 0 || failure != null) return;
        ForkJoinPool p = pool;
        if (p == null || p.isShutdown()) return;
        List<Long> pending = new ArrayList<>();
        for (long cp : conversionChunks) {
            if (!scannedChunks.contains(cp)) pending.add(cp);
        }
        if (pending.isEmpty()) return;
        long[] arr = new long[pending.size()];
        for (int i = 0; i < arr.length; i++) arr[i] = pending.get(i).longValue();
        int grain = Math.max(1, arr.length / Math.max(1, p.getParallelism() * 4));
        p.submit(new RetryTask(arr, 0, arr.length, grain));
    }

    final class ColumnWorker {
        private final Random rnd = new Random(ThreadLocalRandom.current().nextLong());

        void processChunk(long cp, int cx, int cz, ExtendedBlockStorage[] ebs) {
            if (scannedChunks.add(cp)) {
                ConcurrentMap<Long, IBlockState> out = replacements.computeIfAbsent(cp, k -> new ConcurrentHashMap<>());
                boolean isOuter = outerChunksToProcess.contains(cp);
                MutableBlockPos pos = new MutableBlockPos();
                int xBase = cx << 4;
                int zBase = cz << 4;
                for (int x = xBase; x < xBase + 16; x++) {
                    for (int z = zBase; z < zBase + 16; z++) {
                        double dist = Math.hypot(x - posX, z - posZ);
                        if (isOuter && dist > getScale()) continue;
                        if (dist > s0) {
                            if (rnd.nextFloat() > 0.05F + (5F * (s0 / dist) - 4F)) continue;
                        }
                        pos.setPos(x, 0, z);
                        FallColumn fc = doColumn(pos, dist, ebs, out, x, z);
                        if (fc != null) {
                            fallColumns.computeIfAbsent(cp, k -> new ConcurrentLinkedQueue<>()).offer(fc);
                        }
                    }
                }
                U.getAndAddInt(EntityFalloutRainParallelized.this, OFF_PENDING_CHUNKS, -1);
                // Non-nuclear bombs (doFallout=false) produce no block replacements but can still have
                // gap columns that must collapse (letFall). Offer to the apply queue in that case too.
                boolean hasFalls = fallColumns.containsKey(cp);
                if (out.isEmpty() && !hasFalls) {
                    replacements.remove(cp);
                } else {
                    applyQueue.offer(cp);
                }
            } else {
                // Re-scan after an unload-reoffer: the replacements were put back by applyChunk, but the
                // column scan already ran. Re-queue them so they get applied without re-scanning.
                ConcurrentMap<Long, IBlockState> existing = replacements.get(cp);
                if (existing != null && !existing.isEmpty()) {
                    applyQueue.offer(cp);
                }
            }
        }

        FallColumn doColumn(MutableBlockPos pos, double dist, ExtendedBlockStorage[] ebs, ConcurrentMap<Long, IBlockState> out, int x, int z) {
            int stoneDepth = 0;
            int maxStoneDepth = doFallout ? getMaxStoneDepth(dist) : 6;
            boolean reachedStone = false;
            boolean lastReachedStone = false;
            int contactHeight = 420;
            int lastGapHeight = 420;
            boolean gapFound = false;
            boolean[] stop = new boolean[]{false};

            for (int y = 255; y >= 0; y--) {
                IBlockState b = getEffectiveState(x, y, z, ebs);
                Block bblock = b.getBlock();
                Material bmaterial = b.getMaterial();

                lastReachedStone = reachedStone;

                if (contactHeight == 420 && (doFallout ? bblock != Blocks.AIR : bblock.isCollidable()))
                    contactHeight = Math.min(y + 1, 255);

                if (reachedStone && bmaterial != Material.AIR) stoneDepth++;
                else reachedStone = bmaterial == Material.ROCK;
                if (reachedStone && stoneDepth > maxStoneDepth) break;

                if (bmaterial == Material.AIR || bmaterial.isLiquid()) {
                    if (y < contactHeight) {
                        gapFound = true;
                        lastGapHeight = y;
                    }
                    continue;
                }

                if (doFallout) {
                    stop[0] = false;
                    convertColumnBlock(x, y, z, dist, b, bblock, bmaterial, reachedStone, lastReachedStone, stoneDepth, maxStoneDepth, out, ebs, contactHeight, stop);
                    if (stop[0]) break;
                }
            }

            if (dist < fallingRadius) {
                if (doFlood) floodColumn(x, z, out, ebs);
                else drainColumn(x, z, out, ebs);
            }

            if (doDrop && gapFound && dist < fallingRadius) {
                return new FallColumn(x, z, lastGapHeight, contactHeight);
            }
            return null;
        }
    }

    private int getMaxStoneDepth(double dist) {
        if (dist > s1) return 0;
        else if (dist > s2) return 1;
        else if (dist > s3) return 2;
        else if (dist > s4) return 3;
        else if (dist > s5) return 4;
        else if (dist > s6) return 5;
        else if (dist <= s6) return 6;
        return 7;
    }

    /** Maps a distance to a 0..6 tier index. radial=true uses the rS thresholds; sellafield=true uses the 0.1 jitter (meta uses 0.2); stoneDepth=0 with maxStoneDepth=-1 disables the depth term. */

    private int tierIndex(double dist, Random rnd, boolean radial, boolean sellafield, int stoneDepth, int maxStoneDepth) {
        double s1 = radial ? rS1 : this.s1;
        double s2 = radial ? rS2 : this.s2;
        double s3 = radial ? rS3 : this.s3;
        double s4 = radial ? rS4 : this.s4;
        double s5 = radial ? rS5 : this.s5;
        double s6 = radial ? rS6 : this.s6;
        double spread = sellafield ? 0.1D : 0.2D;
        double ranDist = dist * (1D + rnd.nextDouble() * spread);
        if (ranDist > s1 || stoneDepth == maxStoneDepth) return 0;
        if (ranDist > s2 || stoneDepth == maxStoneDepth - 1) return 1;
        if (ranDist > s3 || stoneDepth == maxStoneDepth - 2) return 2;
        if (ranDist > s4 || stoneDepth == maxStoneDepth - 3) return 3;
        if (ranDist > s5 || stoneDepth == maxStoneDepth - 4) return 4;
        if (ranDist > s6 || stoneDepth == maxStoneDepth - 5) return 5;
        return 6;
    }

    private void markNotify(int x, int y, int z) {
        notifyPositions.computeIfAbsent(ChunkPos.asLong(x >> 4, z >> 4), k -> ConcurrentHashMap.newKeySet())
                .add(Library.blockPosToLong(x, y, z));
    }

    // Column conversion rules: records replacements instead of calling setBlockState.

    private void convertColumnBlock(int x, int y, int z, double dist, IBlockState b, Block bblock, Material bmaterial,
                                    boolean reachedStone, boolean lastReachedStone, int stoneDepth, int maxStoneDepth,
                                    ConcurrentMap<Long, IBlockState> out, ExtendedBlockStorage[] ebs, int contactHeight, boolean[] stop) {
        Random rnd = ThreadLocalRandom.current();
        if (bblock == Blocks.BEDROCK || bblock == ModBlocks.ore_bedrock_oil || bblock == ModBlocks.ore_bedrock_block) {
            if (getEffectiveState(x, y + 1, z, ebs).getBlock() == Blocks.AIR) {
                recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y + 1, z), ebs);
                out.put(Library.blockPosToLong(x, y + 1, z), ModBlocks.toxic_block.getDefaultState());
                markNotify(x, y + 1, z);
            }
            stop[0] = true;
            return;
        }

        if (y == contactHeight - 1 && bblock != ModBlocks.fallout && Math.abs(rnd.nextGaussian() * (dist * dist) / (s0 * s0)) < 0.05
                && rnd.nextDouble() < 0.05 && canPlaceFallout(b)) {
            placeBlockFromDist(dist, ModBlocks.fallout, x, y + 1, z, out, rnd, ebs);
        }

        if (bblock == ModBlocks.waste_leaves) {
            if (!(dist > s1 || (dist > fallingRadius && (rnd.nextFloat() < (-5F * (fallingRadius / dist) + 5F))))) {
                recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                out.put(Library.blockPosToLong(x, y, z), Blocks.AIR.getDefaultState());
                markNotify(x, y, z);
            }
            return;
        }

        if (bblock instanceof BlockLeaves && !(bblock instanceof WasteLeaves)) {
            if (dist > s1 || (dist > fallingRadius && (rnd.nextFloat() < (-5F * (fallingRadius / dist) + 5F)))) {
                BlockPlanks.EnumType type = null;
                try {
                    type = ((BlockLeaves) bblock).getWoodType(bblock.getMetaFromState(b));
                } catch (UnsupportedOperationException ignored) {
                }
                if (type == null) type = BlockPlanks.EnumType.OAK;
                recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                out.put(Library.blockPosToLong(x, y, z), ModBlocks.waste_leaves.getDefaultState().withProperty(WasteLeaves.VARIANT, type));
                markNotify(x, y, z);
            } else {
                recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                out.put(Library.blockPosToLong(x, y, z), Blocks.AIR.getDefaultState());
                markNotify(x, y, z);
            }
            return;
        }

        if (bblock == Blocks.BROWN_MUSHROOM || bblock == Blocks.RED_MUSHROOM) {
            if (dist < s0) {
                recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                out.put(Library.blockPosToLong(x, y, z), ModBlocks.mush.getDefaultState());
            }
            return;
        }

        if (META_TIERED.contains(bblock)) {
            int m = tierIndex(dist, rnd, false, false, 0, -1);
            boolean podzol = bblock == ModBlocks.waste_dirt && b.getValue(WasteDirt.VARIANT) == WasteDirt.Variant.PODZOL;
            int current = podzol ? b.getValue(WasteDirt.META) : b.getBlock().getMetaFromState(b);
            if (m > current) {
                recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                out.put(Library.blockPosToLong(x, y, z), podzol ? WasteDirt.podzolState(m) : bblock.getStateFromMeta(m));
            }
            return;
        }

        if (bblock instanceof BlockOre && reachedStone && !lastReachedStone && dist < s1) {
            recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
            out.put(Library.blockPosToLong(x, y, z), ModBlocks.toxic_block.getDefaultState());
            markNotify(x, y, z);
            return;
        }

        if (bblock instanceof BlockStone || bblock == Blocks.COBBLESTONE) {
            recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
            out.put(Library.blockPosToLong(x, y, z), SELLAFIELD[tierIndex(dist, rnd, false, true, stoneDepth, maxStoneDepth)].getStateFromMeta(rnd.nextInt(4)));
            markNotify(x, y, z);
            return;
        }

        if (bblock instanceof BlockGrass) {
            placeBlockFromDist(dist, ModBlocks.waste_earth, x, y, z, out, rnd, ebs);
            return;
        }
        if (bblock instanceof BlockGravel) {
            placeBlockFromDist(dist, ModBlocks.waste_gravel, x, y, z, out, rnd, ebs);
            return;
        }
        if (bblock instanceof BlockDirt) {
            BlockDirt.DirtType meta = b.getValue(BlockDirt.VARIANT);
            if (meta == BlockDirt.DirtType.DIRT) placeBlockFromDist(dist, ModBlocks.waste_dirt, x, y, z, out, rnd, ebs);
            else if (meta == BlockDirt.DirtType.COARSE_DIRT)
                placeBlockFromDist(dist, ModBlocks.waste_gravel, x, y, z, out, rnd, ebs);
            else if (meta == BlockDirt.DirtType.PODZOL)
                placeBlockFromDist(dist, WasteDirt.podzolState(tierIndex(dist, rnd, false, false, 0, -1)), x, y, z, out, rnd, ebs);
            return;
        }
        if (bblock == Blocks.FARMLAND) {
            placeBlockFromDist(dist, ModBlocks.waste_dirt, x, y, z, out, rnd, ebs);
            return;
        }
        if (bblock instanceof BlockSnow) {
            placeBlockFromDist(dist, ModBlocks.waste_snow, x, y, z, out, rnd, ebs);
            return;
        }
        if (bblock instanceof BlockSnowBlock) {
            placeBlockFromDist(dist, ModBlocks.waste_snow_block, x, y, z, out, rnd, ebs);
            return;
        }
        if (bblock instanceof BlockIce) {
            recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
            out.put(Library.blockPosToLong(x, y, z), ModBlocks.waste_ice.getDefaultState());
            return;
        }
        if (bblock instanceof BlockBush) {
            IBlockState d = getState(ebs, x, y - 1, z);
            Block dblock = d.getBlock();
            boolean canStay = dblock.canSustainPlant(d, world, new BlockPos(x, y - 1, z), EnumFacing.UP, (IPlantable) bblock);
            if (!canStay || bblock instanceof BlockLilyPad) {
                recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                out.put(Library.blockPosToLong(x, y, z), Blocks.AIR.getDefaultState());
                markNotify(x, y, z);
                return;
            }
            if (dblock == Blocks.FARMLAND) {
                placeBlockFromDist(dist, ModBlocks.waste_dirt, x, y - 1, z, out, rnd, ebs);
                placeBlockFromDist(dist, ModBlocks.waste_grass_tall, x, y, z, out, rnd, ebs);
                markNotify(x, y, z);
            } else if (dblock instanceof BlockGrass) {
                placeBlockFromDist(dist, ModBlocks.waste_earth, x, y - 1, z, out, rnd, ebs);
                placeBlockFromDist(dist, ModBlocks.waste_grass_tall, x, y, z, out, rnd, ebs);
                markNotify(x, y, z);
            } else if (dblock instanceof BlockDirt) {
                BlockDirt.DirtType meta = d.getValue(BlockDirt.VARIANT);
                if (meta == BlockDirt.DirtType.PODZOL) placeBlockFromDist(dist, WasteDirt.podzolState(tierIndex(dist, rnd, false, false, 0, -1)), x, y - 1, z, out, rnd, ebs);
                else placeBlockFromDist(dist, ModBlocks.waste_dirt, x, y - 1, z, out, rnd, ebs);
                placeBlockFromDist(dist, ModBlocks.waste_grass_tall, x, y, z, out, rnd, ebs);
                markNotify(x, y, z);
            } else if (dblock == Blocks.MYCELIUM) {
                placeBlockFromDist(dist, ModBlocks.waste_mycelium, x, y - 1, z, out, rnd, ebs);
                recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                out.put(Library.blockPosToLong(x, y, z), ModBlocks.mush.getDefaultState());
                markNotify(x, y, z);
            }
            return;
        }
        if (bblock == Blocks.MYCELIUM) {
            placeBlockFromDist(dist, ModBlocks.waste_mycelium, x, y, z, out, rnd, ebs);
            return;
        }
        if (bblock == Blocks.SANDSTONE) {
            placeBlockFromDist(dist, ModBlocks.waste_sandstone, x, y, z, out, rnd, ebs);
            return;
        }
        if (bblock == Blocks.RED_SANDSTONE) {
            placeBlockFromDist(dist, ModBlocks.waste_sandstone_red, x, y, z, out, rnd, ebs);
            return;
        }
        if (bblock == Blocks.HARDENED_CLAY || bblock == Blocks.STAINED_HARDENED_CLAY) {
            placeBlockFromDist(dist, ModBlocks.waste_terracotta, x, y, z, out, rnd, ebs);
            return;
        }
        if (bblock instanceof BlockSand) {
            BlockSand.EnumType meta = b.getValue(BlockSand.VARIANT);
            if (rnd.nextInt(60) == 0) {
                placeBlockFromDist(dist, meta == BlockSand.EnumType.SAND ? ModBlocks.waste_trinitite : ModBlocks.waste_trinitite_red, x, y, z, out, rnd, ebs);
            } else {
                placeBlockFromDist(dist, meta == BlockSand.EnumType.SAND ? ModBlocks.waste_sand : ModBlocks.waste_sand_red, x, y, z, out, rnd, ebs);
            }
            return;
        }
        if (bblock == Blocks.CLAY) {
            recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
            out.put(Library.blockPosToLong(x, y, z), Blocks.HARDENED_CLAY.getDefaultState());
            return;
        }
        if (bblock == Blocks.MOSSY_COBBLESTONE) {
            recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
            out.put(Library.blockPosToLong(x, y, z), Blocks.COAL_ORE.getDefaultState());
            return;
        }
        if (bblock == Blocks.COAL_ORE || matchesOre(bblock, "oreCoal")) {
            if (dist < s5) {
                int ra = rnd.nextInt(150);
                if (ra < 7) {
                    recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                    out.put(Library.blockPosToLong(x, y, z), Blocks.DIAMOND_ORE.getDefaultState());
                } else if (ra < 10) {
                    recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                    out.put(Library.blockPosToLong(x, y, z), Blocks.EMERALD_ORE.getDefaultState());
                }
            }
            return;
        }
        if (bblock == ModBlocks.ore_lignite || matchesOre(bblock, "oreLignite")) {
            if (dist < s5) {
                if (rnd.nextInt(150) < 7) {
                    recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                    out.put(Library.blockPosToLong(x, y, z), Blocks.DIAMOND_ORE.getDefaultState());
                }
            }
            return;
        }
        if (bblock == ModBlocks.ore_beryllium || matchesOre(bblock, "oreBeryllium")) {
            if (dist < s5) {
                if (rnd.nextInt(150) < 10) {
                    recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                    out.put(Library.blockPosToLong(x, y, z), Blocks.EMERALD_ORE.getDefaultState());
                }
            }
            return;
        }
        if (bblock == Blocks.BROWN_MUSHROOM_BLOCK || bblock == Blocks.RED_MUSHROOM_BLOCK) {
            if (dist < s0) {
                BlockHugeMushroom.EnumType meta = b.getValue(BlockHugeMushroom.VARIANT);
                recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                out.put(Library.blockPosToLong(x, y, z), BlockMushHuge.getState(bblock == Blocks.RED_MUSHROOM_BLOCK, meta == BlockHugeMushroom.EnumType.STEM));
            }
            return;
        }
        if (bblock instanceof BlockLog) {
            if (dist < s1) {
                recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                out.put(Library.blockPosToLong(x, y, z), ((WasteLog) ModBlocks.waste_log).getSameRotationState(b));
                markNotify(x, y, z);
            }
            return;
        }
        if (bblock == Blocks.SPONGE) {
            if (b.getValue(BlockSponge.WET)) {
                recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                out.put(Library.blockPosToLong(x, y, z), Blocks.SPONGE.getDefaultState());
            }
            return;
        }
        if (bmaterial == Material.WOOD && bblock != ModBlocks.waste_log && bblock != ModBlocks.waste_planks) {
            if (dist < s1) {
                recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                out.put(Library.blockPosToLong(x, y, z), ModBlocks.waste_planks.getDefaultState());
            }
            return;
        }
        if (bblock == ModBlocks.sellafield_slaked || bblock == ModBlocks.sellafield_0 || bblock == ModBlocks.sellafield_1 || bblock == ModBlocks.sellafield_2 || bblock == ModBlocks.sellafield_3 || bblock == ModBlocks.sellafield_4 || bblock == ModBlocks.sellafield_core) {
            int nt = tierIndex(dist, rnd, false, true, stoneDepth, maxStoneDepth);
            if (nt > Arrays.asList(SELLAFIELD).indexOf(bblock)) {
                recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                out.put(Library.blockPosToLong(x, y, z), SELLAFIELD[nt].getStateFromMeta(rnd.nextInt(4)));
                markNotify(x, y, z);
            }
            return;
        }
        if (bblock == Blocks.CACTUS) {
            recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
            out.put(Library.blockPosToLong(x, y, z), Blocks.AIR.getDefaultState());
            markNotify(x, y, z);
            return;
        }
        if (bblock == Blocks.REEDS) {
            recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
            out.put(Library.blockPosToLong(x, y, z), Blocks.AIR.getDefaultState());
            markNotify(x, y, z);
            return;
        }
        if (bblock == Blocks.VINE) {
            recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
            out.put(Library.blockPosToLong(x, y, z), Blocks.AIR.getDefaultState());
            markNotify(x, y, z);
            return;
        }
        if (bblock == ModBlocks.ore_uranium || matchesOre(bblock, "oreUranium")) {
            if (dist <= s5) {
                if (rnd.nextInt(VersatileConfig.getSchrabOreChance()) == 0 || dist < s7) {
                    recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                    out.put(Library.blockPosToLong(x, y, z), ModBlocks.ore_schrabidium.getDefaultState());
                } else {
                    recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                    out.put(Library.blockPosToLong(x, y, z), ModBlocks.ore_uranium_scorched.getDefaultState());
                }
            }
            stop[0] = true;
            return;
        }
        if (bblock == ModBlocks.ore_nether_uranium || matchesOre(bblock, "oreNetherUranium")) {
            if (dist <= s5) {
                if (rnd.nextInt(VersatileConfig.getSchrabOreChance()) == 0) {
                    recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                    out.put(Library.blockPosToLong(x, y, z), ModBlocks.ore_nether_schrabidium.getDefaultState());
                } else {
                    recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                    out.put(Library.blockPosToLong(x, y, z), ModBlocks.ore_nether_uranium_scorched.getDefaultState());
                }
            }
            stop[0] = true;
            return;
        }
        if (bblock == ModBlocks.ore_gneiss_uranium || matchesOre(bblock, "oreNetherUranium")) {
            if (dist <= s4) {
                if (rnd.nextInt(VersatileConfig.getSchrabOreChance()) == 0) {
                    recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                    out.put(Library.blockPosToLong(x, y, z), ModBlocks.ore_gneiss_schrabidium.getDefaultState());
                } else {
                    recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                    out.put(Library.blockPosToLong(x, y, z), ModBlocks.ore_gneiss_uranium_scorched.getDefaultState());
                }
            }
            stop[0] = true;
            return;
        }
        if (bblock == ModBlocks.brick_concrete) {
            if (rnd.nextInt(80) == 0) {
                recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                out.put(Library.blockPosToLong(x, y, z), ModBlocks.brick_concrete_broken.getDefaultState());
            }
            stop[0] = true;
            return;
        }
        if (bblock.getExplosionResistance(null) > 300) {
            stop[0] = true;
            return;
        }
    }

    private void placeBlockFromDist(double dist, Block b, int x, int y, int z, ConcurrentMap<Long, IBlockState> out, Random rnd, ExtendedBlockStorage[] ebs) {
        recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
        out.put(Library.blockPosToLong(x, y, z), b.getStateFromMeta(tierIndex(dist, rnd, false, false, 0, -1)));
    }

    private void placeBlockFromDist(double dist, IBlockState state, int x, int y, int z, ConcurrentMap<Long, IBlockState> out, Random rnd, ExtendedBlockStorage[] ebs) {
        recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
        out.put(Library.blockPosToLong(x, y, z), state);
    }

    private void floodColumn(int x, int z, ConcurrentMap<Long, IBlockState> out, ExtendedBlockStorage[] ebs) {
        if (CompatibilityConfig.doFillCraterWithWater && waterLevel > 1) {
            for (int y = waterLevel - 1; y > 1; y--) {
                IBlockState s = getState(ebs, x, y, z);
                Block b = s.getBlock();
                if (s.getMaterial() == Material.AIR || b == Blocks.FLOWING_WATER) {
                    recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                    out.put(Library.blockPosToLong(x, y, z), Blocks.WATER.getDefaultState());
                } else if (b.getExplosionResistance(null) > 600_000) {
                    return;
                }
            }
        }
    }

    private void drainColumn(int x, int z, ConcurrentMap<Long, IBlockState> out, ExtendedBlockStorage[] ebs) {
        for (int y = 255; y > 1; y--) {
            Block b = getState(ebs, x, y, z).getBlock();
            if (b == Blocks.WATER || b == Blocks.FLOWING_WATER) {
                recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
                out.put(Library.blockPosToLong(x, y, z), Blocks.AIR.getDefaultState());
            }
        }
    }

    final class RadialWorker {
        private final Random rnd = new Random(ThreadLocalRandom.current().nextLong());

        boolean deferred;

        void processRay(double dx, double dy, double dz) {
            deferred = false;
            for (int l = 0; l < radialRadius; l++) {
                int x = (int) Math.floor(posX + dx * l);
                int y = (int) Math.floor(posY + dy * l);
                int z = (int) Math.floor(posZ + dz * l);
                if (y < 0 || y > 255) return;
                long cp = ChunkPos.asLong(x >> 4, z >> 4);
                Chunk radialChunk = ChunkUtil.getLoadedChunk(mirror, cp);
                ExtendedBlockStorage[] ebs = (radialChunk != null && radialChunk.isTerrainPopulated())
                        ? radialChunk.getBlockStorageArray() : null;
                if (ebs == null) {
                    ConcurrentLinkedQueue<double[]> q = radialWaitingRoom.get(cp);
                    if (q == null) {
                        ConcurrentLinkedQueue<double[]> created = new ConcurrentLinkedQueue<>();
                        ConcurrentLinkedQueue<double[]> prev = radialWaitingRoom.putIfAbsent(cp, created);
                        q = (prev != null) ? prev : created;
                        if (prev == null) {
                            priorityLoadQueue.offer(cp);
                        }
                    }
                    q.offer(new double[]{dx, dy, dz});
                    deferred = true;
                    return;
                }
                IBlockState b = getState(ebs, x, y, z);
                Block bblock = b.getBlock();
                if (bblock == Blocks.AIR) continue;

                ConcurrentMap<Long, IBlockState> out = replacements.computeIfAbsent(cp, k -> new ConcurrentHashMap<>());
                if (bblock instanceof BlockStone || bblock == Blocks.COBBLESTONE) {
                    recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                    out.put(Library.blockPosToLong(x, y, z), SELLAFIELD[tierIndex(l, rnd, true, true, 0, -1)].getStateFromMeta(rnd.nextInt(4)));
                    markNotify(x, y, z);
                    return;
                }
                if (bblock == ModBlocks.sellafield_slaked || bblock == ModBlocks.sellafield_0 || bblock == ModBlocks.sellafield_1 || bblock == ModBlocks.sellafield_2 || bblock == ModBlocks.sellafield_3 || bblock == ModBlocks.sellafield_4 || bblock == ModBlocks.sellafield_core) {
                    int nt = tierIndex(l, rnd, true, true, 0, -1);
                    if (nt > Arrays.asList(SELLAFIELD).indexOf(bblock)) {
                        recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                        out.put(Library.blockPosToLong(x, y, z), SELLAFIELD[nt].getStateFromMeta(rnd.nextInt(4)));
                        markNotify(x, y, z);
                    }
                    return;
                }
                if (META_TIERED.contains(bblock)) {
                    int m = tierIndex(l, rnd, true, false, 0, -1);
                    boolean podzol = bblock == ModBlocks.waste_dirt && b.getValue(WasteDirt.VARIANT) == WasteDirt.Variant.PODZOL;
                    int current = podzol ? b.getValue(WasteDirt.META) : b.getBlock().getMetaFromState(b);
                    if (m > current) {
                        recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                        out.put(Library.blockPosToLong(x, y, z), podzol ? WasteDirt.podzolState(m) : bblock.getStateFromMeta(m));
                        markNotify(x, y, z);
                    }
                    return;
                }
                if (bblock == Blocks.BEDROCK || bblock == ModBlocks.ore_bedrock_oil || bblock == ModBlocks.ore_bedrock_block) {
                    if (getState(ebs, x, y + 1, z).getBlock() == Blocks.AIR) {
                        recordSourceAt(cp, Library.blockPosToLong(x, y + 1, z), ebs);
                        out.put(Library.blockPosToLong(x, y + 1, z), ModBlocks.toxic_block.getDefaultState());
                        markNotify(x, y + 1, z);
                    }
                    return;
                }
                if (bblock instanceof BlockLeaves && !(bblock instanceof WasteLeaves)) {
                    if (l > rS1) {
                        BlockPlanks.EnumType type = null;
                        try {
                            type = ((BlockLeaves) bblock).getWoodType(bblock.getMetaFromState(b));
                        } catch (UnsupportedOperationException ignored) {
                        }
                        if (type == null) type = BlockPlanks.EnumType.OAK;
                        recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                        out.put(Library.blockPosToLong(x, y, z), ModBlocks.waste_leaves.getDefaultState().withProperty(WasteLeaves.VARIANT, type));
                    } else {
                        recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                        out.put(Library.blockPosToLong(x, y, z), Blocks.AIR.getDefaultState());
                    }
                    markNotify(x, y, z);
                    continue;
                }
                if (bblock instanceof BlockBush) {
                    IBlockState d = getState(ebs, x, y - 1, z);
                    Block dblock = d.getBlock();
                    boolean canStay = dblock.canSustainPlant(d, world, new BlockPos(x, y - 1, z), EnumFacing.UP, (IPlantable) bblock);
                    if (!canStay || bblock instanceof BlockLilyPad) {
                        recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                        out.put(Library.blockPosToLong(x, y, z), Blocks.AIR.getDefaultState());
                        markNotify(x, y, z);
                        continue;
                    }
                    if (dblock == Blocks.FARMLAND) {
                        placeBlockFromDistRadial(l, ModBlocks.waste_dirt, x, y - 1, z, out, rnd, ebs);
                        placeBlockFromDistRadial(l, ModBlocks.waste_grass_tall, x, y, z, out, rnd, ebs);
                    } else if (dblock instanceof BlockGrass) {
                        placeBlockFromDistRadial(l, ModBlocks.waste_earth, x, y - 1, z, out, rnd, ebs);
                        placeBlockFromDistRadial(l, ModBlocks.waste_grass_tall, x, y, z, out, rnd, ebs);
                    } else if (dblock instanceof BlockDirt) {
                        BlockDirt.DirtType meta = d.getValue(BlockDirt.VARIANT);
                        if (meta == BlockDirt.DirtType.PODZOL) placeBlockFromDistRadial(l, WasteDirt.podzolState(tierIndex(l, rnd, true, false, 0, -1)), x, y - 1, z, out, rnd, ebs); else placeBlockFromDistRadial(l, ModBlocks.waste_dirt, x, y - 1, z, out, rnd, ebs);
                        placeBlockFromDistRadial(l, ModBlocks.waste_grass_tall, x, y, z, out, rnd, ebs);
                    } else if (dblock == Blocks.MYCELIUM) {
                        placeBlockFromDistRadial(l, ModBlocks.waste_mycelium, x, y - 1, z, out, rnd, ebs);
                        recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                        out.put(Library.blockPosToLong(x, y, z), ModBlocks.mush.getDefaultState());
                        markNotify(x, y, z);
                    }
                    continue;
                }
                if (bblock instanceof BlockCactus) {
                    recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                    out.put(Library.blockPosToLong(x, y, z), Blocks.AIR.getDefaultState());
                    markNotify(x, y, z);
                    continue;
                }
                if (bblock instanceof BlockReed) {
                    recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                    out.put(Library.blockPosToLong(x, y, z), Blocks.AIR.getDefaultState());
                    markNotify(x, y, z);
                    continue;
                }
                if (bblock instanceof BlockGrass) {
                    placeBlockFromDistRadial(l, ModBlocks.waste_earth, x, y, z, out, rnd, ebs);
                    return;
                }
                if (bblock instanceof BlockDirt) {
                    BlockDirt.DirtType meta = b.getValue(BlockDirt.VARIANT);
                    if (meta == BlockDirt.DirtType.DIRT)
                        placeBlockFromDistRadial(l, ModBlocks.waste_dirt, x, y, z, out, rnd, ebs);
                    else if (meta == BlockDirt.DirtType.COARSE_DIRT)
                        placeBlockFromDistRadial(l, ModBlocks.waste_gravel, x, y, z, out, rnd, ebs);
                    else if (meta == BlockDirt.DirtType.PODZOL)
                        placeBlockFromDistRadial(l, WasteDirt.podzolState(tierIndex(l, rnd, true, false, 0, -1)), x, y, z, out, rnd, ebs);
                    return;
                }
                if (bblock == Blocks.FARMLAND) {
                    placeBlockFromDistRadial(l, ModBlocks.waste_dirt, x, y, z, out, rnd, ebs);
                    continue;
                }
                if (bblock instanceof BlockSnow) {
                    placeBlockFromDistRadial(l, ModBlocks.waste_snow, x, y, z, out, rnd, ebs);
                    continue;
                }
                if (bblock instanceof BlockSnowBlock) {
                    placeBlockFromDistRadial(l, ModBlocks.waste_snow_block, x, y, z, out, rnd, ebs);
                    continue;
                }
                if (bblock instanceof BlockIce) {
                    recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                    out.put(Library.blockPosToLong(x, y, z), ModBlocks.waste_ice.getDefaultState());
                    markNotify(x, y, z);
                    continue;
                }
                if (bblock == Blocks.MYCELIUM) {
                    placeBlockFromDistRadial(l, ModBlocks.waste_mycelium, x, y, z, out, rnd, ebs);
                    return;
                }
                if (bblock instanceof BlockGravel) {
                    placeBlockFromDistRadial(l, ModBlocks.waste_gravel, x, y, z, out, rnd, ebs);
                    return;
                }
                if (bblock == Blocks.SANDSTONE) {
                    placeBlockFromDistRadial(l, ModBlocks.waste_sandstone, x, y, z, out, rnd, ebs);
                    return;
                }
                if (bblock == Blocks.RED_SANDSTONE) {
                    placeBlockFromDistRadial(l, ModBlocks.waste_sandstone_red, x, y, z, out, rnd, ebs);
                    return;
                }
                if (bblock == Blocks.HARDENED_CLAY || bblock == Blocks.STAINED_HARDENED_CLAY) {
                    placeBlockFromDistRadial(l, ModBlocks.waste_terracotta, x, y, z, out, rnd, ebs);
                    return;
                }
                if (bblock instanceof BlockSand) {
                    BlockSand.EnumType meta = b.getValue(BlockSand.VARIANT);
                    if (rnd.nextInt(60) == 0) {
                        placeBlockFromDistRadial(l, meta == BlockSand.EnumType.SAND ? ModBlocks.waste_trinitite : ModBlocks.waste_trinitite_red, x, y, z, out, rnd, ebs);
                    } else {
                        placeBlockFromDistRadial(l, meta == BlockSand.EnumType.SAND ? ModBlocks.waste_sand : ModBlocks.waste_sand_red, x, y, z, out, rnd, ebs);
                    }
                    return;
                }
                if (bblock == Blocks.CLAY) {
                    recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                    out.put(Library.blockPosToLong(x, y, z), Blocks.HARDENED_CLAY.getDefaultState());
                    markNotify(x, y, z);
                    return;
                }
                if (bblock == Blocks.MOSSY_COBBLESTONE) {
                    recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                    out.put(Library.blockPosToLong(x, y, z), Blocks.COAL_ORE.getDefaultState());
                    markNotify(x, y, z);
                    return;
                }
                if (bblock == Blocks.COAL_ORE || matchesOre(bblock, "oreCoal")) {
                    if (l < rS6) {
                        int ra = rnd.nextInt(150);
                        if (ra < 7) {
                            recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                            out.put(Library.blockPosToLong(x, y, z), Blocks.DIAMOND_ORE.getDefaultState());
                            markNotify(x, y, z);
                        } else if (ra < 10) {
                            recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                            out.put(Library.blockPosToLong(x, y, z), Blocks.EMERALD_ORE.getDefaultState());
                            markNotify(x, y, z);
                        }
                    }
                    return;
                }
                if (bblock == ModBlocks.ore_lignite || matchesOre(bblock, "oreLignite")) {
                    if (l < rS6) {
                        if (rnd.nextInt(150) < 7) {
                            recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                            out.put(Library.blockPosToLong(x, y, z), Blocks.DIAMOND_ORE.getDefaultState());
                            markNotify(x, y, z);
                        }
                    }
                    return;
                }
                if (bblock == ModBlocks.ore_beryllium || matchesOre(bblock, "oreBeryllium")) {
                    if (l < rS6) {
                        if (rnd.nextInt(150) < 10) {
                            recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                            out.put(Library.blockPosToLong(x, y, z), Blocks.EMERALD_ORE.getDefaultState());
                            markNotify(x, y, z);
                        }
                    }
                    return;
                }
                if (bblock == Blocks.BROWN_MUSHROOM_BLOCK || bblock == Blocks.RED_MUSHROOM_BLOCK) {
                    if (l < rS0) {
                        BlockHugeMushroom.EnumType meta = b.getValue(BlockHugeMushroom.VARIANT);
                        recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                        out.put(Library.blockPosToLong(x, y, z), BlockMushHuge.getState(bblock == Blocks.RED_MUSHROOM_BLOCK, meta == BlockHugeMushroom.EnumType.STEM));
                        markNotify(x, y, z);
                    }
                    return;
                }
                if (bblock instanceof BlockLog) {
                    if (l < rS0) {
                        recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                        out.put(Library.blockPosToLong(x, y, z), ((WasteLog) ModBlocks.waste_log).getSameRotationState(b));
                        markNotify(x, y, z);
                    }
                    return;
                }
                if (bblock instanceof BlockSponge) {
                    if (b.getValue(BlockSponge.WET)) {
                        recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                        out.put(Library.blockPosToLong(x, y, z), Blocks.SPONGE.getDefaultState());
                    }
                    return;
                }
                if (bmaterialOf(b) == Material.WOOD && bblock != ModBlocks.waste_log && bblock != ModBlocks.waste_planks) {
                    if (l < rS0) {
                        recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                        out.put(Library.blockPosToLong(x, y, z), ModBlocks.waste_planks.getDefaultState());
                        markNotify(x, y, z);
                    }
                    return;
                }
                if (bblock == Blocks.VINE) {
                    recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                    out.put(Library.blockPosToLong(x, y, z), Blocks.AIR.getDefaultState());
                    markNotify(x, y, z);
                    continue;
                }
                if (bblock == ModBlocks.ore_uranium || matchesOre(bblock, "oreUranium")) {
                    if (l <= rS6) {
                        if (rnd.nextInt((int) (1 + VersatileConfig.getSchrabOreChance())) == 0 || l < rS7) {
                            recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                            out.put(Library.blockPosToLong(x, y, z), ModBlocks.ore_schrabidium.getDefaultState());
                        } else {
                            recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                            out.put(Library.blockPosToLong(x, y, z), ModBlocks.ore_uranium_scorched.getDefaultState());
                        }
                        markNotify(x, y, z);
                    }
                    return;
                }
                if (bblock == ModBlocks.ore_nether_uranium || matchesOre(bblock, "oreNetherUranium")) {
                    if (l <= rS5) {
                        if (rnd.nextInt((int) (1 + VersatileConfig.getSchrabOreChance())) == 0) {
                            recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                            out.put(Library.blockPosToLong(x, y, z), ModBlocks.ore_nether_schrabidium.getDefaultState());
                        } else {
                            recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                            out.put(Library.blockPosToLong(x, y, z), ModBlocks.ore_nether_uranium_scorched.getDefaultState());
                        }
                        markNotify(x, y, z);
                    }
                    return;
                }
                if (bblock == ModBlocks.ore_gneiss_uranium || matchesOre(bblock, "oreNetherUranium")) {
                    if (l <= rS4) {
                        if (rnd.nextInt((int) (1 + VersatileConfig.getSchrabOreChance() / 2)) == 0) {
                            recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                            out.put(Library.blockPosToLong(x, y, z), ModBlocks.ore_gneiss_schrabidium.getDefaultState());
                        } else {
                            recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                            out.put(Library.blockPosToLong(x, y, z), ModBlocks.ore_gneiss_uranium_scorched.getDefaultState());
                        }
                        markNotify(x, y, z);
                    }
                    return;
                }
                if (bblock == ModBlocks.brick_concrete) {
                    if (rnd.nextInt(60) == 0) {
                        recordSourceAt(cp, Library.blockPosToLong(x, y, z), ebs);
                        out.put(Library.blockPosToLong(x, y, z), ModBlocks.brick_concrete_broken.getDefaultState());
                        markNotify(x, y, z);
                    }
                    return;
                }
                if (b.getMaterial() == Material.ROCK || b.getMaterial() == Material.IRON) {
                    return;
                }
            }
        }

        private void placeBlockFromDistRadial(double dist, Block b, int x, int y, int z, ConcurrentMap<Long, IBlockState> out, Random rnd, ExtendedBlockStorage[] ebs) {
            recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
            out.put(Library.blockPosToLong(x, y, z), b.getStateFromMeta(tierIndex(dist, rnd, true, false, 0, -1)));
            // Radial placements notify neighbours (flag 3); column placements do not (flag 2).
            markNotify(x, y, z);
        }

    private void placeBlockFromDistRadial(double dist, IBlockState state, int x, int y, int z, ConcurrentMap<Long, IBlockState> out, Random rnd, ExtendedBlockStorage[] ebs) {
            recordSourceAt(ChunkPos.asLong(x >> 4, z >> 4), Library.blockPosToLong(x, y, z), ebs);
            out.put(Library.blockPosToLong(x, y, z), state);
            markNotify(x, y, z);
        }
    }

    private Material bmaterialOf(IBlockState s) {
        return s.getMaterial();
    }

    private static IBlockState getState(ExtendedBlockStorage[] ebs, int x, int y, int z) {
        if (ebs == null) return Blocks.AIR.getDefaultState();
        int subY = y >> 4;
        if (subY < 0 || subY >= 16) return Blocks.AIR.getDefaultState();
        ExtendedBlockStorage s = ebs[subY];
        if (s == Chunk.NULL_BLOCK_STORAGE || s.isEmpty()) return Blocks.AIR.getDefaultState();
        return s.get(x & 15, y & 15, z & 15);
    }

    // The column phase runs after the radial phase and must see its recorded replacements, not the untouched block.

    private IBlockState getEffectiveState(int x, int y, int z, ExtendedBlockStorage[] ebs) {
        ConcurrentMap<Long, IBlockState> rep = replacements.get(ChunkPos.asLong(x >> 4, z >> 4));
        if (rep != null) {
            IBlockState s = rep.get(Library.blockPosToLong(x, y, z));
            if (s != null) return s;
        }
        return getState(ebs, x, y, z);
    }

    private static boolean matchesOre(Block block, String oreDictName) {
        return OreDictionary.containsMatch(false, OreDictionary.getOres(oreDictName), new ItemStack(block, 1, OreDictionary.WILDCARD_VALUE));
    }

    /** Mirror version of canPlaceBlockAt: reads the loaded chunks only, never the world. */

    private static boolean canPlaceFallout(IBlockState below) {
        Block b = below.getBlock();
        if (b == Blocks.ICE || b == Blocks.PACKED_ICE) return false;
        if (below.getMaterial() == Material.LEAVES) return true;
        if (b == ModBlocks.fallout && (below.getValue(BlockPowder.META) & 7) == 7) return true;
        return below.isOpaqueCube() && below.getMaterial().blocksMovement();
    }

    private void recordSourceAt(long cp, long packed, ExtendedBlockStorage[] ebs) {
        int x = Library.getBlockPosX(packed);
        int y = Library.getBlockPosY(packed);
        int z = Library.getBlockPosZ(packed);
        int subY = y >> 4;
        ExtendedBlockStorage section = ebs[subY];
        IBlockState source = (section == Chunk.NULL_BLOCK_STORAGE || section.isEmpty())
                ? Blocks.AIR.getDefaultState() : section.get(x & 15, y & 15, z & 15);
        sampledSources.computeIfAbsent(cp, k -> new ConcurrentHashMap<>()).put(packed, source);
    }

    static final class FallColumn {
        final int x, z, lastGapHeight, contactHeight;

        FallColumn(int x, int z, int lastGapHeight, int contactHeight) {
            this.x = x;
            this.z = z;
            this.lastGapHeight = lastGapHeight;
            this.contactHeight = contactHeight;
        }
    }

    @ServerThread
    private void applyChunk(long cpLong) {
        ConcurrentMap<Long, IBlockState> changes = replacements.remove(cpLong);
        ConcurrentMap<Long, IBlockState> sources = sampledSources.remove(cpLong);
        ConcurrentLinkedQueue<FallColumn> falls = fallColumns.remove(cpLong);
        boolean hasChanges = changes != null && !changes.isEmpty();
        boolean hasFalls = falls != null && !falls.isEmpty();
        if (!hasChanges && !hasFalls) {
            return;
        }
        int cx = Library.getChunkPosX(cpLong);
        int cz = Library.getChunkPosZ(cpLong);
        Chunk chunk = worldServer.getChunkProvider().loadedChunks.get(cpLong);
        if (chunk == null) {
            if (hasChanges) replacements.put(cpLong, changes);
            chunkLoadQueue.offer(cpLong);
            return;
        }
        ExtendedBlockStorage[] storages = chunk.getBlockStorageArray();
        Int2ObjectOpenHashMap<IBlockState>[] buckets = new Int2ObjectOpenHashMap[16];
        LongArrayList fluidWake = new LongArrayList();
        int selfMask = 0;
        Long2ObjectOpenHashMap<IBlockState> oldStates = new Long2ObjectOpenHashMap<>();
        if (hasChanges) {
            for (Map.Entry<Long, IBlockState> e : changes.entrySet()) {
                long packed = e.getKey().longValue();
                int x = Library.getBlockPosX(packed);
                int y = Library.getBlockPosY(packed);
                int z = Library.getBlockPosZ(packed);
                int subY = y >> 4;
                if (subY < 0 || subY >= 16) continue;
                if (sources != null) {
                    IBlockState source = sources.get(packed);
                    if (source != null) {
                        ExtendedBlockStorage section = storages[subY];
                        IBlockState current = (section == Chunk.NULL_BLOCK_STORAGE || section.isEmpty())
                                ? Blocks.AIR.getDefaultState() : section.get(x & 15, y & 15, z & 15);
                        IBlockState intended = e.getValue();
                        if (current.getBlock() == intended.getBlock() && META_TIERED.contains(intended.getBlock())) {
                            if (intended.getBlock().getMetaFromState(intended)
                                    <= current.getBlock().getMetaFromState(current)) {
                                continue;
                            }
                        } else if (current.getBlock() != source.getBlock()) {
                            int liveTier = sellafieldTier(current.getBlock());
                            if (liveTier < 0 || sellafieldTier(intended.getBlock()) <= liveTier) continue;
                        }
                    }
                }
                if (e.getValue().getBlock() == ModBlocks.fallout
                        && !ModBlocks.fallout.canPlaceBlockAt(world, new BlockPos(x, y, z))) {
                    continue;
                }
                if (buckets[subY] == null) buckets[subY] = new Int2ObjectOpenHashMap<>();
                buckets[subY].put(Library.packLocal(x & 15, y & 15, z & 15), e.getValue());
                if (e.getValue().getBlock() instanceof BlockFluidBase) fluidWake.add(packed);
            }
        }
        for (int subY = 0; subY < 16; subY++) {
            Int2ObjectOpenHashMap<IBlockState> bucket = buckets[subY];
            if (bucket == null || bucket.isEmpty()) continue;
            ExtendedBlockStorage src = storages[subY];
            Optional<ExtendedBlockStorage> changed = ChunkUtil.copyAndModify(cx, cz, subY, world.provider.hasSkyLight(), src, bucket, oldStates);
            if (changed == null) continue;
            ExtendedBlockStorage carved = changed.orElse(Chunk.NULL_BLOCK_STORAGE);
            storages[subY] = carved;
            selfMask |= 1 << subY;
        }
        for (int s = 0; s < 16; s++) {
            if ((selfMask & (1 << s)) != 0) ChunkUtil.rebuildHeightMap(chunk, s);
        }
        MutableBlockPos p = new MutableBlockPos();
        for (Long2ObjectMap.Entry<IBlockState> e : oldStates.long2ObjectEntrySet()) {
            IBlockState oldState = e.getValue();
            if (oldState.getBlock().hasTileEntity(oldState)) {
                Library.fromLong(p, e.getLongKey());
                world.removeTileEntity(p);
            }
        }
        // Reproduce the flag-3 neighbour notification of the replaced block, only where the replacement used flag 3.
        Set<Long> notify = notifyPositions.remove(cpLong);
        if (notify != null && !notify.isEmpty()) {
            for (long packed : notify) {
                IBlockState oldState = oldStates.get(packed);
                if (oldState == null) continue;
                Library.fromLong(p, packed);
                world.notifyNeighborsOfStateChange(p, oldState.getBlock(), true);
            }
        }
        for (int i = 0; i < fluidWake.size(); i++) {
            long packed = fluidWake.getLong(i);
            if (wokenByNotify(notify, packed)) continue;
            Library.fromLong(p, packed);
            int sy = p.getY() >> 4;
            IBlockState nv = (sy >= 0 && sy < 16 && buckets[sy] != null)
                    ? buckets[sy].get(Library.packLocal(p.getX() & 15, p.getY() & 15, p.getZ() & 15)) : null;
            if (nv == null || !(nv.getBlock() instanceof BlockFluidBase)) continue;
            world.scheduleUpdate(p, nv.getBlock(), nv.getBlock().tickRate(world));
        }
        int lightMask = selfMask;
        // Apply structural collapse (letFall) for columns that were found to have gaps.
        if (hasFalls) {
            FallColumn fc;
            while ((fc = falls.poll()) != null) {
                lightMask |= letFall(fc);
            }
        }
        if (lightMask != 0) {
            ChunkUtil.markBlocksChanged(chunk);
            ChunkUtil.requestLight(chunk, lightMask);
            pendingSend.merge(ChunkPos.asLong(chunk.x, chunk.z), lightMask, (x, y) -> x | y);
        }
    }

    private int letFall(FallColumn fc) {
        int fallChance = RadiationConfig.blocksFallCh;
        if (fallChance < 1) return 0;
        if (fallChance < 100) {
            if (world.rand.nextInt(100) < fallChance) return 0;
        }

        int mask = 0;
        int bottomHeight = fc.lastGapHeight;
        MutableBlockPos pos = new MutableBlockPos();
        for (int y = fc.lastGapHeight; y <= fc.contactHeight; y++) {
            pos.setPos(fc.x, y, fc.z);
            IBlockState state = world.getBlockState(pos);
            Block b = state.getBlock();

            if (b.isReplaceable(world, pos)) continue;

            float hardness = b.getExplosionResistance(null);

            if (hardness > 15) {
                bottomHeight = y + 1;
                continue;
            }

            if (hardness >= 0 && y != bottomHeight) {
                BlockPos target = new BlockPos(fc.x, bottomHeight, fc.z);
                IBlockState targetState = world.getBlockState(target);

                if (targetState.getBlock().isReplaceable(world, target)) {
                    TileEntity te = world.getTileEntity(pos);
                    NBTTagCompound teNBT = null;
                    if (te != null) {
                        teNBT = new NBTTagCompound();
                        te.writeToNBT(teNBT);
                        teNBT.setInteger("x", target.getX());
                        teNBT.setInteger("y", target.getY());
                        teNBT.setInteger("z", target.getZ());
                        world.removeTileEntity(pos);
                    }

                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    world.setBlockState(target, state);
                    if (b.getLightValue(state) > 0) {
                    }
                    mask |= 1 << (y >> 4);
                    mask |= 1 << (bottomHeight >> 4);

                    if (teNBT != null) {
                        TileEntity newTE = world.getTileEntity(target);
                        if (newTE != null) {
                            newTE.readFromNBT(teNBT);
                            newTE.validate();
                            world.markBlockRangeForRenderUpdate(target, target);
                            world.notifyBlockUpdate(target, state, state, 3);
                        }
                    }

                    if (!(b.getCollisionBoundingBox(state, world, pos) == Block.NULL_AABB)) {
                        float distance = y - bottomHeight;
                        int i = MathHelper.ceil(distance - 1.0F);
                        if (i > 0) {
                            float fallHurtAmount = 2.0F * (hardness / 15F);
                            float damage = Math.min(MathHelper.floor((float) i * fallHurtAmount), 40.0F);
                            AxisAlignedBB blockBox = state.getCollisionBoundingBox(world, target);
                            if (blockBox != Block.NULL_AABB) {
                                List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, blockBox.offset(target));
                                for (Entity entity : entities) {
                                    entity.attackEntityFrom(DamageSource.FALLING_BLOCK, damage);
                                }
                            }
                        }
                    }
                }
            }
            bottomHeight++;
        }
        return mask;
    }

    private static boolean wokenByNotify(Set<Long> notify, long packed) {
        if (notify == null || notify.isEmpty()) return false;
        int x = Library.getBlockPosX(packed);
        int y = Library.getBlockPosY(packed);
        int z = Library.getBlockPosZ(packed);
        return notify.contains(Library.blockPosToLong(x + 1, y, z))
                || notify.contains(Library.blockPosToLong(x - 1, y, z))
                || notify.contains(Library.blockPosToLong(x, y + 1, z))
                || notify.contains(Library.blockPosToLong(x, y - 1, z))
                || notify.contains(Library.blockPosToLong(x, y, z + 1))
                || notify.contains(Library.blockPosToLong(x, y, z - 1));
    }

    @ServerThread
    private void secondPass() {
                Long2ObjectMap<Chunk> loaded = worldServer.getChunkProvider().loadedChunks;
        for (Map.Entry<Long, Integer> e : pendingSend.entrySet()) {
            long cp = e.getKey().longValue();
            Chunk chunk = loaded.get(cp);
            if (chunk == null) continue;
            int lightMask = ChunkUtil.takeLightMask(worldServer, cp);
            int mask = e.getValue().intValue() | lightMask;
            if (mask == 0) continue;
            chunk.markDirty();
            ChunkUtil.sendChunk(worldServer, chunk, mask);
        }
        pendingSend.clear();
        ChunkUtil.drainRemaining(worldServer);
        sectionMaskByChunk.clear();
    }

    private void drainSendQueue() {
        if (pendingSend.isEmpty()) return;
                Long2ObjectMap<Chunk> loaded = worldServer.getChunkProvider().loadedChunks;
        Iterator<Map.Entry<Long, Integer>> it = pendingSend.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Long, Integer> e = it.next();
            long cp = e.getKey().longValue();
            if (ChunkUtil.hasPendingLight(worldServer, cp)) continue;
            int lightMask = ChunkUtil.takeLightMask(worldServer, cp);
            int mask = e.getValue().intValue() | lightMask;
            Chunk chunk = loaded.get(cp);
            if (chunk != null && mask != 0) {
                chunk.markDirty();
                ChunkUtil.sendChunk(worldServer, chunk, mask);
            }
            it.remove();
        }
    }

    private void maybeFinish() {
        if (collectFinished == 0) {
            if (activeWorkerTasks == 0 && pendingChunks == 0) {
                U.putIntRelease(this, OFF_COLLECT_FINISHED, 1);
            } else {
                return;
            }
        }
        if (destroyFinished != 0) return;
        if (activeWorkerTasks != 0 || pendingChunks != 0) return;
        if (!applyQueue.isEmpty() || !chunkLoadQueue.isEmpty() || !priorityLoadQueue.isEmpty()) return;
        if (ChunkUtil.anyPendingLight(worldServer)) return;
        if (!U.compareAndSetInt(this, OFF_FINISH_QUEUED, 0, 1)) return;
        worldServer.addScheduledTask(() -> {
                        secondPass();
            U.putIntRelease(this, OFF_DESTROY_FINISHED, 1);
            releasePoolIfHeld();
            if (U.getAndSetInt(this, OFF_MAP_ACQUIRED, 0) != 0) {
                ChunkUtil.releaseMirrorMap(worldServer);
            }
            triggerWeather();
            setDead();
        });
    }

    @ServerThread
    private void finishCancellation() {
        if (activeWorkerTasks != 0 || !U.compareAndSetInt(this, OFF_CANCEL_CLEANUP, 0, 1)) return;
        replacements.clear();
        notifyPositions.clear();
        chunkLoadQueue.clear();
        priorityLoadQueue.clear();
        applyQueue.clear();
        sectionMaskByChunk.clear();
        releasePoolIfHeld();
        if (U.getAndSetInt(this, OFF_MAP_ACQUIRED, 0) != 0) {
            ChunkUtil.releaseMirrorMap(worldServer);
        }
    }

    @ServerThread
    private void pollCancelCleanup() {
        if (activeWorkerTasks == 0) {
            finishCancellation();
        } else {
            worldServer.addScheduledTask(this::pollCancelCleanup);
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt) {
        setScale(nbt.getInteger("scale"), nbt.getInteger("radialRadius"), nbt.getInteger("fallingRadius"));
        doFallout = nbt.getBoolean("doFallout");
        doFlood = nbt.getBoolean("doFlood");
        radialComplete = nbt.getBoolean("radialDone");
        if (nbt.getBoolean("collectDone")) U.putIntRelease(this, OFF_COLLECT_FINISHED, 1);
        if (nbt.getBoolean("destroyDone")) U.putIntRelease(this, OFF_DESTROY_FINISHED, 1);
        if (nbt.hasKey("sectionMask", Constants.NBT.TAG_LIST)) {
            NBTTagList list = nbt.getTagList("sectionMask", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < list.tagCount(); i++) {
                NBTTagCompound t = list.getCompoundTagAt(i);
                long ck = ChunkPos.asLong(t.getInteger("cX"), t.getInteger("cZ"));
                sectionMaskByChunk.put(ck, t.getInteger("mask"));
            }
        }
        if (nbt.hasKey("populatedChunks")) {
            for (long cp : ((NBTTagLongArray) nbt.getTag("populatedChunks")).data) {
                populatedChunks.add(cp);
            }
        }
        if (collectFinished != 0 && destroyFinished == 0) {
            // Resume the final packet pass (the conversion already committed its blocks).
            worldServer.addScheduledTask(() -> {
                secondPass();
                U.putIntRelease(this, OFF_DESTROY_FINISHED, 1);
                setDead();
            });
        } else if (destroyFinished != 0) {
            worldServer.addScheduledTask(this::setDead);
        }
        // else: collectFinished == 0 → onUpdate() re-gathers chunks and calls startWorkers().
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setInteger("scale", getScale());
        nbt.setInteger("radialRadius", radialRadius);
        nbt.setInteger("fallingRadius", fallingRadius);
        nbt.setBoolean("doFallout", doFallout);
        nbt.setBoolean("doFlood", doFlood);
        nbt.setBoolean("collectDone", collectFinished != 0);
        nbt.setBoolean("destroyDone", destroyFinished != 0);
        nbt.setBoolean("radialDone", radialComplete);
        // Always persist the section mask so a resume can skip already-converted chunks.
        if (!sectionMaskByChunk.isEmpty()) {
            NBTTagList list = new NBTTagList();
            ObjectIterator<Long2IntMap.Entry> iterator = sectionMaskByChunk.long2IntEntrySet().fastIterator();
            while (iterator.hasNext()) {
                Long2IntMap.Entry e = iterator.next();
                NBTTagCompound t = new NBTTagCompound();
                t.setInteger("cX", Library.getChunkPosX(e.getLongKey()));
                t.setInteger("cZ", Library.getChunkPosZ(e.getLongKey()));
                t.setInteger("mask", e.getIntValue());
                list.appendTag(t);
            }
            nbt.setTag("sectionMask", list);
        }
        // Persist the confirmed-populated set so a resume can gate isNeighborhoodPopulated without re-loading every chunk.
        if (!populatedChunks.isEmpty()) {
            long[] arr = new long[populatedChunks.size()];
            int i = 0;
            for (long cp : populatedChunks) arr[i++] = cp;
            nbt.setTag("populatedChunks", new NBTTagLongArray(arr));
        }
    }

    private static int sellafieldTier(Block b) {
        for (int i = 0; i < SELLAFIELD.length; i++) {
            if (SELLAFIELD[i] == b) return i;
        }
        return -1;
    }

    public static int getInt(Object e) {
        if (e == null) return 0;
        return (int) e;
    }

    /** Marks a replaced position that needs its neighbours notified. */
    /**
    * Squared distance from the epicenter to the given chunk's center, for radius-ordered applying.
    */

    private double chunkDistSq(long cp) {
        int cx = Library.getChunkPosX(cp);
        int cz = Library.getChunkPosZ(cp);
        double dx = (cx << 4) + 8.0 - posX;
        double dz = (cz << 4) + 8.0 - posZ;
        return dx * dx + dz * dz;
    }

    final class ColumnTask extends RecursiveAction {
        final List<Long> chunks;
        final int start, end, threshold;

        ColumnTask(List<Long> chunks, int start, int end, int threshold) {
            this.chunks = chunks;
            this.start = start;
            this.end = end;
            this.threshold = Math.max(1, threshold);
        }

        @Override
        protected void compute() {
            int len = end - start;
            if (len <= threshold) {
                U.getAndAddInt(EntityFalloutRainParallelized.this, OFF_ACTIVE_WORKER_TASKS, 1);
                try {
                    ColumnWorker worker = new ColumnWorker();
                    for (int i = start; i < end; i++) {
                        if (Thread.currentThread().isInterrupted() || destroyFinished != 0) break;
                        long cp = chunks.get(i).longValue();
                        int cx = Library.getChunkPosX(cp);
                        int cz = Library.getChunkPosZ(cp);
                        Chunk chunk = ChunkUtil.getLoadedChunk(mirror, cp);
                        if (chunk == null || !chunk.isTerrainPopulated()) {
                            requeueUnloaded(cp);
                            continue;
                        }
                        if (!isNeighborhoodPopulated(cp)) {
                            // Not ready yet; submitRetry re-scans it via the mirror, no disk re-read.
                            continue;
                        }
                        worker.processChunk(cp, cx, cz, chunk.getBlockStorageArray());
                    }
                } catch (Exception e) {
                    fail(e);
                } finally {
                    U.getAndAddInt(EntityFalloutRainParallelized.this, OFF_ACTIVE_WORKER_TASKS, -1);
                }
            } else {
                int mid = start + (len >>> 1);
                invokeAll(new ColumnTask(chunks, start, mid, threshold), new ColumnTask(chunks, mid, end, threshold));
            }
        }
    }

    final class RetryTask extends RecursiveAction {
        final long[] chunks;
        final int start, end, threshold;

        RetryTask(long[] chunks, int start, int end, int threshold) {
            this.chunks = chunks;
            this.start = start;
            this.end = end;
            this.threshold = Math.max(1, threshold);
        }

        @Override
        protected void compute() {
            int len = end - start;
            if (len <= threshold) {
                U.getAndAddInt(EntityFalloutRainParallelized.this, OFF_ACTIVE_WORKER_TASKS, 1);
                try {
                    ColumnWorker worker = new ColumnWorker();
                    for (int i = start; i < end; i++) {
                        if (Thread.currentThread().isInterrupted() || destroyFinished != 0) break;
                        long cp = chunks[i];
                        if (scannedChunks.contains(cp)) continue;
                        int cx = Library.getChunkPosX(cp);
                        int cz = Library.getChunkPosZ(cp);
                        Chunk chunk = ChunkUtil.getLoadedChunk(mirror, cp);
                        if (chunk == null || !chunk.isTerrainPopulated()) {
                            requeueUnloaded(cp);
                            continue;
                        }
                        if (!isNeighborhoodPopulated(cp)) continue;
                        worker.processChunk(cp, cx, cz, chunk.getBlockStorageArray());
                    }
                } catch (Exception e) {
                    fail(e);
                } finally {
                    U.getAndAddInt(EntityFalloutRainParallelized.this, OFF_ACTIVE_WORKER_TASKS, -1);
                }
            } else {
                int mid = start + (len >>> 1);
                invokeAll(new RetryTask(chunks, start, mid, threshold), new RetryTask(chunks, mid, end, threshold));
            }
        }
    }

    final class RadialTask extends RecursiveAction {
        final int start, end, threshold, total;

        RadialTask(int start, int end, int threshold, int total) {
            this.start = start;
            this.end = end;
            this.threshold = Math.max(1, threshold);
            this.total = total;
        }

        @Override
        protected void compute() {
            int len = end - start;
            if (len <= threshold) {
                U.getAndAddInt(EntityFalloutRainParallelized.this, OFF_ACTIVE_WORKER_TASKS, 1);
                try {
                    RadialWorker worker = new RadialWorker();
                    for (int i = start; i < end; i++) {
                        if (Thread.currentThread().isInterrupted() || destroyFinished != 0) break;
                        double fy = (2D * i / (total - 1D)) - 1D;
                        double fr = Math.sqrt(1D - fy * fy);
                        double theta = Math.PI * (3.0 - Math.sqrt(5.0)) * i;
                        worker.processRay(Math.cos(theta) * fr, fy, Math.sin(theta) * fr);
                        if (!worker.deferred) rayFinished();
                    }
                } catch (Exception e) {
                    fail(e);
                } finally {
                    U.getAndAddInt(EntityFalloutRainParallelized.this, OFF_ACTIVE_WORKER_TASKS, -1);
                }
            } else {
                int mid = start + (len >>> 1);
                invokeAll(new RadialTask(start, mid, threshold, total), new RadialTask(mid, end, threshold, total));
            }
        }
    }
}


