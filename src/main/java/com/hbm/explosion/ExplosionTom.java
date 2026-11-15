package com.hbm.explosion;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

import com.hbm.config.CompatibilityConfig;
import com.hbm.config.WorldConfig;
import com.hbm.blocks.ModBlocks;
import com.hbm.world.WorldUtil;
import com.hbm.world.biome.BiomeGenDustWastes;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.ChunkProviderServer;

public class ExplosionTom
{
	public int posX;
	public int posY;
	public int posZ;
	public int lastposX = 0;
	public int lastposZ = 0;
	public int radius;
	public int radius2;
	public World world;
	private int n = 1;
	private int nlimit;
	private int shell;
	private int leg;
	private int element;
	private final Map<Long, Integer> biomeChunkCounts = new HashMap<Long, Integer>();
	private final Map<Long, Integer> biomeChunkTotals = new HashMap<Long, Integer>();
	private final ArrayDeque<Long> deferredColumns = new ArrayDeque<Long>();

	public void saveToNbt(NBTTagCompound nbt, String name) {
		nbt.setInteger(name + "posX", posX);
		nbt.setInteger(name + "posY", posY);
		nbt.setInteger(name + "posZ", posZ);
		nbt.setInteger(name + "lastposX", lastposX);
		nbt.setInteger(name + "lastposZ", lastposZ);
		nbt.setInteger(name + "radius", radius);
		nbt.setInteger(name + "radius2", radius2);
		nbt.setInteger(name + "n", n);
		nbt.setInteger(name + "nlimit", nlimit);
		nbt.setInteger(name + "shell", shell);
		nbt.setInteger(name + "leg", leg);
		nbt.setInteger(name + "element", element);
	}

	public void readFromNbt(NBTTagCompound nbt, String name) {
		posX = nbt.getInteger(name + "posX");
		posY = nbt.getInteger(name + "posY");
		posZ = nbt.getInteger(name + "posZ");
		lastposX = nbt.getInteger(name + "lastposX");
		lastposZ = nbt.getInteger(name + "lastposZ");
		radius = nbt.getInteger(name + "radius");
		radius2 = nbt.getInteger(name + "radius2");
		n = nbt.getInteger(name + "n");
		nlimit = nbt.getInteger(name + "nlimit");
		shell = nbt.getInteger(name + "shell");
		leg = nbt.getInteger(name + "leg");
		element = nbt.getInteger(name + "element");
	}

	public ExplosionTom(int x, int y, int z, World world, int rad)
	{
		this.posX = x;
		this.posY = y;
		this.posZ = z;

		this.world = world;

		this.radius = rad;
		this.radius2 = this.radius * this.radius;

		this.nlimit = this.radius2 * 4;
	}

	public boolean update() {
		flushSectionMasks();
		if(!CompatibilityConfig.isWarDim(world)){
			return true;
		}
		breakColumn(this.lastposX, this.lastposZ);
		this.shell = (int) Math.floor((Math.sqrt(n) + 1) / 2);
		int shell2 = this.shell * 2;
		this.leg = (int) Math.floor((this.n - (shell2 - 1) * (shell2 - 1)) / shell2);
		this.element = (this.n - (shell2 - 1) * (shell2 - 1)) - shell2 * this.leg - this.shell + 1;
		this.lastposX = this.leg == 0 ? this.shell : this.leg == 1 ? -this.element : this.leg == 2 ? -this.shell : this.element;
		this.lastposZ = this.leg == 0 ? this.element : this.leg == 1 ? this.shell : this.leg == 2 ? -this.element : -this.shell;
		this.n++;
		int retryBudget = 32;
		while(!deferredColumns.isEmpty() && retryBudget-- > 0) {
			long packedColumn = deferredColumns.poll();
			breakColumn(unpackColumnX(packedColumn), unpackColumnZ(packedColumn));
		}
		return this.n > this.nlimit && deferredColumns.isEmpty();
	}

	private boolean isNeighborhoodPopulated(int chunkX, int chunkZ) {
		for(int dx = -1; dx <= 1; dx++) {
			for(int dz = -1; dz <= 1; dz++) {
				int neighborX = chunkX + dx;
				int neighborZ = chunkZ + dz;
				Chunk chunk = world.getChunk(neighborX, neighborZ);
				if(chunk == null) return false;
				if(!chunk.isTerrainPopulated()) {
					ensureChunkPopulated(chunk, neighborX, neighborZ);
					if(!chunk.isTerrainPopulated()) return false;
				}
			}
		}
		return true;
	}

	private void ensureChunkPopulated(Chunk chunk, int chunkX, int chunkZ) {
		world.getChunk(chunkX + 1, chunkZ);
		world.getChunk(chunkX, chunkZ + 1);
		world.getChunk(chunkX + 1, chunkZ + 1);
		ChunkProviderServer provider = (ChunkProviderServer) world.getChunkProvider();
		chunk.populate(provider, provider.chunkGenerator);
	}

	private static long packColumn(int x, int z) {
		return ((long) x << 32) | (z & 0xFFFFFFFFL);
	}

	private static int unpackColumnX(long packedColumn) {
		return (int)(packedColumn >> 32);
	}

	private static int unpackColumnZ(long packedColumn) {
		return (int)packedColumn;
	}

	private void breakColumn(int x, int z) {
		int dist = this.radius2 - (x * x + z * z);

		if(dist > 0) {
			int pX = posX + x;
			int pZ = posZ + z;
			if(!isNeighborhoodPopulated(pX >> 4, pZ >> 4)) {
				deferredColumns.add(packColumn(x, z));
				return;
			}
			double X = Math.pow((this.posX - pX), 2);
			double Z = Math.pow((this.posZ - pZ), 2);
			double distance = Math.sqrt(X + Z); // Distance calculations used for crater rim stuff

			int y = 256;
			int terrain = posY - 1;

			double cA = (terrain - Math.pow(Math.E, -Math.pow(Math.sqrt(x * x + z * z), 2) / 40000) * 13) + world.rand.nextInt(2); // Basic crater bowl shape
			double cB = cA + Math.pow(Math.E, -Math.pow(Math.sqrt(x * x + z * z) - 200, 2) / 400) * 13 ;// Crater peak ring
			int craterFloor = (int) (cB + Math.pow(Math.E, -Math.pow(Math.sqrt(x * x + z * z) - 500, 2) / 2000) * 37); // Crater rim
			MutableBlockPos pos = new BlockPos.MutableBlockPos();
			for(int i = 256; i > 0; i--) {
				if(i == craterFloor || !world.isAirBlock(pos.setPos(pX, i, pZ))) {
					y = i;
					break;
				}
			}
			int height = terrain - 14;
			int offset = 20;
			int threshold = (int) ((float) Math.sqrt(x * x + z * z) * (float) (height + offset) / (float) this.radius) + world.rand.nextInt(2) - offset;

			if(y < terrain + 1) y = terrain + 1;

			Material m;
			while(y > threshold) {

				if(y == 0)
					break;
				if(y <= craterFloor) {
					pos.setPos(pX, y, pZ);
					if(world.rand.nextInt(200) == 0) {
						setBlockAndNotify(pos, ModBlocks.ore_tektite_osmiridium.getDefaultState());
					} else {
						setBlockAndNotify(pos, ModBlocks.tektite.getDefaultState());
					}

				} else {
					if(y > terrain + 1) {
						if(distance < 500) {
							for(int i = -2; i < 3; i++) {
								for(int j = -2; j < 3; j++) {
									for(int k = -2; k < 3; k++) {
										pos.setPos(pX + i, y + j, pZ + k);
										m = world.getBlockState(pos).getMaterial();
										if(m == Material.WATER || m == Material.ICE || m == Material.SNOW || m.getCanBurn()) {
											setBlockAndNotify(pos, Blocks.AIR.getDefaultState());
											setBlockAndNotify(pos.setPos(pX, y, pZ), Blocks.AIR.getDefaultState());
										}
									}
								}
							}
							setBlockAndNotify(pos.setPos(pX, y, pZ), Blocks.AIR.getDefaultState());
						}
					} else {
						for(int i = -2; i < 3; i++) {
							for(int j = -2; j < 3; j++) {
								for(int k = -2; k < 3; k++) {
									m = world.getBlockState(pos.setPos(pX + i, y + j, pZ + k)).getMaterial();
									IBlockState nb = world.getBlockState(pos.setPos(pX + i, y, pZ + k));
									Material nm = nb.getMaterial();
									if(m == Material.WATER || m == Material.ICE || nb.getBlock() == Blocks.AIR || nm == Material.SNOW || nm.getCanBurn()) {
										setBlockAndNotify(pos.setPos(pX + i, y, pZ + k), Blocks.LAVA.getDefaultState());
										setBlockAndNotify(pos.setPos(pX, y, pZ), Blocks.LAVA.getDefaultState());
									}
								}
							}
						}
						setBlockAndNotify(pos.setPos(pX, y, pZ), Blocks.LAVA.getDefaultState());
					}
				}
				y--;
			}

			if (WorldConfig.enableDustWastesBiome) {
				WorldUtil.setBiome(world, pX, pZ, BiomeGenDustWastes.dustWastes);
				int cx = pX >> 4;
				int cz = pZ >> 4;
				long cp = (((long) cx) << 32) | (cz & 0xFFFFFFFFL);
				int total = biomeChunkTotals.getOrDefault(cp, -1);
				if (total < 0) {
					total = 0;
					for (int tx = 0; tx < 16; tx++) {
						for (int tz = 0; tz < 16; tz++) {
							int dx = (cx << 4) + tx - posX;
							int dz = (cz << 4) + tz - posZ;
							if (dx * dx + dz * dz <= radius2) total++;
						}
					}
					biomeChunkTotals.put(cp, total);
				}
				int count = biomeChunkCounts.getOrDefault(cp, 0) + 1;
				if (count >= total) {
					WorldUtil.syncBiomeChange(world, cx, cz);
					biomeChunkCounts.remove(cp);
					biomeChunkTotals.remove(cp);
				} else {
					biomeChunkCounts.put(cp, count);
				}
			}
		}
	}

	// Changed-section tracking for client light sync.
	private static final class LightSnapshot {
		final byte[] sky;
		final byte[] block;
		final boolean wasNull;

		LightSnapshot(boolean wasNull, byte[] sky, byte[] block) {
			this.wasNull = wasNull;
			this.sky = sky;
			this.block = block;
		}
	}

	private final Map<Long, Map<Integer, LightSnapshot>> lightSnapshots = new HashMap<>();

	private void snapshotForLight(BlockPos pos, IBlockState oldState, IBlockState newState) {
		if (oldState.getLightValue() == newState.getLightValue()
				&& oldState.getLightOpacity() == newState.getLightOpacity()) return;
		if (!(world instanceof WorldServer)) return;
		WorldServer ws = (WorldServer) world;
		int cx = pos.getX() >> 4;
		int cz = pos.getZ() >> 4;
		int sy = pos.getY() >> 4;
		snapshotChunk(ws, cx, cz, sy);
		snapshotChunk(ws, cx - 1, cz, sy);
		snapshotChunk(ws, cx + 1, cz, sy);
		snapshotChunk(ws, cx, cz - 1, sy);
		snapshotChunk(ws, cx, cz + 1, sy);
		snapshotChunk(ws, cx - 1, cz - 1, sy);
		snapshotChunk(ws, cx - 1, cz + 1, sy);
		snapshotChunk(ws, cx + 1, cz - 1, sy);
		snapshotChunk(ws, cx + 1, cz + 1, sy);
	}

	private void snapshotChunk(WorldServer ws, int cx, int cz, int sy) {
		Chunk chunk = ws.getChunkProvider().loadedChunks.get(ChunkPos.asLong(cx, cz));
		if (chunk == null) return;
		long cp = ChunkPos.asLong(cx, cz);
		Map<Integer, LightSnapshot> map = lightSnapshots.computeIfAbsent(cp, k -> new HashMap<>());
		for (int s = Math.max(0, sy - 1); s <= Math.min(15, sy + 1); s++) {
			if (map.containsKey(s)) continue;
			ExtendedBlockStorage ebs = chunk.getBlockStorageArray()[s];
			if (ebs == null || ebs == Chunk.NULL_BLOCK_STORAGE) {
				map.put(s, new LightSnapshot(true, null, null));
			} else {
				map.put(s, new LightSnapshot(false,
						ebs.getSkyLight() == null ? null : ebs.getSkyLight().getData().clone(),
						ebs.getBlockLight() == null ? null : ebs.getBlockLight().getData().clone()));
			}
		}
	}

	private static void mergeMask(Map<Long, Integer> map, long cp, int mask) {
		map.merge(cp, mask, (a, b) -> a | b);
	}

	private void flushSectionMasks() {
		if (lightSnapshots.isEmpty()) return;
		if (!(world instanceof WorldServer)) { lightSnapshots.clear(); return; }
		WorldServer ws = (WorldServer) world;
		Map<Long, Integer> diff = new HashMap<>();
		for (Map.Entry<Long, Map<Integer, LightSnapshot>> ce : lightSnapshots.entrySet()) {
			long cp = ce.getKey().longValue();
			Chunk chunk = ws.getChunkProvider().loadedChunks.get(cp);
			if (chunk == null) continue;
			for (Map.Entry<Integer, LightSnapshot> se : ce.getValue().entrySet()) {
				int s = se.getKey().intValue();
				LightSnapshot snap = se.getValue();
				ExtendedBlockStorage ebs = chunk.getBlockStorageArray()[s];
				boolean changed;
				if (snap.wasNull) {
					changed = ebs != Chunk.NULL_BLOCK_STORAGE;
				} else if (ebs == null || ebs == Chunk.NULL_BLOCK_STORAGE) {
					changed = true;
				} else {
					byte[] sky = ebs.getSkyLight() == null ? null : ebs.getSkyLight().getData();
					byte[] block = ebs.getBlockLight() == null ? null : ebs.getBlockLight().getData();
					changed = !Arrays.equals(snap.sky, sky) || !Arrays.equals(snap.block, block);
				}
				if (changed) mergeMask(diff, cp, 1 << s);
			}
		}
		lightSnapshots.clear();
		for (Map.Entry<Long, Integer> e : diff.entrySet()) sendOneChunk(e.getKey().longValue(), e.getValue().intValue());
	}

	private void sendOneChunk(long cp, int mask) {
		if (!(world instanceof WorldServer)) return;
		WorldServer ws = (WorldServer) world;
		Chunk chunk = ws.getChunkProvider().loadedChunks.get(cp);
		if (chunk == null) return;
		PlayerChunkMapEntry entry = ws.getPlayerChunkMap().getEntry(chunk.x, chunk.z);
		if (entry == null) return;
		ExtendedBlockStorage[] storages = chunk.getBlockStorageArray();
		ExtendedBlockStorage[] fabricated = null;
		for (int s = 0; s < 16; s++) {
			if ((mask & (1 << s)) == 0) continue;
			if (storages[s] != Chunk.NULL_BLOCK_STORAGE) continue;
			ExtendedBlockStorage ebs = new ExtendedBlockStorage(s << 4, ws.provider.hasSkyLight());
			if (ws.provider.hasSkyLight()) {
				int base = s << 4;
				int maxHeight = 0;
				for (int x = 0; x < 16; x++) {
					for (int z = 0; z < 16; z++) {
						maxHeight = Math.max(maxHeight, chunk.getHeightValue(x, z));
					}
				}
				if (base >= maxHeight) {
					java.util.Arrays.fill(ebs.getSkyLight().getData(), (byte) 0xFF);
				} else {
					for (int x = 0; x < 16; x++) {
						for (int z = 0; z < 16; z++) {
							int height = chunk.getHeightValue(x, z);
							for (int y = 0; y < 16; y++) {
								if (base + y >= height) ebs.setSkyLight(x, y, z, 15);
							}
						}
					}
				}
			}
			storages[s] = ebs;
			if (fabricated == null) fabricated = new ExtendedBlockStorage[16];
			fabricated[s] = ebs;
		}
		try {
			entry.sendPacket(new SPacketChunkData(chunk, mask));
		} finally {
			if (fabricated != null) {
				for (int s = 0; s < 16; s++) if (fabricated[s] != null) storages[s] = Chunk.NULL_BLOCK_STORAGE;
			}
		}
	}

	private void setBlockAndNotify(MutableBlockPos pos, IBlockState state) {
		IBlockState oldState = world.getBlockState(pos);
		Block oldBlock = oldState.getBlock();
		world.removeTileEntity(pos);
		snapshotForLight(pos, oldState, state);
		world.setBlockState(pos, state, 2);
		for(EnumFacing facing : EnumFacing.values()) {
			BlockPos np = pos.offset(facing);
			if(world.isBlockLoaded(np)) {
				Block b = world.getBlockState(np).getBlock();
				if(b != Blocks.WATER && b != Blocks.FLOWING_WATER && !hasWaterNeighbor(np)) {
					world.neighborChanged(np, oldBlock, pos);
				}
			}
		}
	}

	private boolean hasWaterNeighbor(BlockPos pos) {
		for(EnumFacing facing : EnumFacing.values()) {
			BlockPos np = pos.offset(facing);
			if(world.isBlockLoaded(np)) {
				Block b = world.getBlockState(np).getBlock();
				if(b == Blocks.WATER || b == Blocks.FLOWING_WATER) {
					return true;
				}
			}
		}
		return false;
	}
}