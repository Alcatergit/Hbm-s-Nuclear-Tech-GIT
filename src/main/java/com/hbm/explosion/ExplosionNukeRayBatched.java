package com.hbm.explosion;

import java.util.*;
import java.util.Map.Entry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import com.hbm.config.BombConfig;
import com.hbm.config.CompatibilityConfig;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.interfaces.IExplosionRay;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.WorldServer;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.util.math.BlockPos.MutableBlockPos;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagLongArray;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ExplosionNukeRayBatched implements IExplosionRay {

	public HashMap<ChunkPos, BitSet> perChunk = new HashMap<ChunkPos, BitSet>();
	public List<ChunkPos> orderedChunks = new ArrayList<>();
	private final CoordComparator comparator = new CoordComparator();
	public boolean isContained = true;
	double posX;
	double posY;
	double posZ;
	World world;

	int strength;
	int radius;

	int gspNumMax;
	int gspNum;
	double gspX;
	double gspY;

	private static final int maxY = 255;
	private static final int minY = 0;
	public boolean ignoreWater = false;

	public boolean isAusf3Complete = false;
	public int rayCheckInterval = 100;
	public int waterLevel;

	public ExplosionNukeRayBatched(World world, double x, double y, double z, int strength, int radius, boolean ignoreWater) {
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.strength = strength;
		this.radius = radius;

		// Total number of points
		this.gspNumMax = (int)(2.5 * Math.PI * Math.pow(this.strength, 2));
		this.gspNum = 1;

		// The beginning of the generalized spiral points
		this.gspX = Math.PI;
		this.gspY = 0.0;
		this.rayCheckInterval = 10000/radius;
		this.ignoreWater = ignoreWater;
		this.waterLevel = EntityFalloutRain.getInt(CompatibilityConfig.fillCraterWithWater.get(world.provider.getDimension()));
		if(this.waterLevel == 0){
			this.waterLevel = world.getSeaLevel();
		} else if(this.waterLevel < 0 && this.waterLevel > -world.getSeaLevel()){
			this.waterLevel = world.getSeaLevel() - this.waterLevel;
		}
	}

	private void generateGspUp(){
		if (this.gspNum < this.gspNumMax) {
			int k = this.gspNum + 1;
			double hk = -1.0 + 2.0 * (k - 1.0) / (this.gspNumMax - 1.0);
			this.gspX = Math.acos(hk);

			double prev_lon = this.gspY;
			double lon = prev_lon + 3.6 / Math.sqrt(this.gspNumMax) / Math.sqrt(1.0 - hk * hk);
			this.gspY = lon % (Math.PI * 2);
		} else {
			this.gspX = 0.0;
			this.gspY = 0.0;
		}
		this.gspNum++;
	}

	// Get Cartesian coordinates for spherical coordinates
	// 90 X-Axis rotation for more efficient chunk scanning
	private Vec3 getSpherical2cartesian(){
		double dx = Math.sin(this.gspX) * Math.cos(this.gspY);
		double dy = Math.sin(this.gspX) * Math.sin(this.gspY);
		double dz = Math.cos(this.gspX);
		return Vec3.createVectorHelper(dx, dy, dz);
	}

	public void addPos(int x, int y, int z){
		chunk = new ChunkPos(x >> 4, z >> 4);
        BitSet hitPositions = perChunk.computeIfAbsent(chunk, k -> new BitSet(65536));

        //we re-use the same pos instead of using individualized per-chunk ones to save on RAM
        hitPositions.set(((255-y) << 8) + ((x - chunk.getXStart()) << 4) + (z - chunk.getZStart()));
	}

	public boolean waterCheck(Block b, int y){
		if(b == Blocks.AIR) return false;
		if(this.ignoreWater && y < this.waterLevel) return b != Blocks.WATER && b != Blocks.FLOWING_WATER;
		return true;
	}

	int age = 0;
	public void collectTip(int time) {
		if(!CompatibilityConfig.isWarDim(world)){
			isAusf3Complete = true;
			return;
		}
		MutableBlockPos pos = new BlockPos.MutableBlockPos();
		long raysProcessed = 0;
		long start = System.currentTimeMillis();

		IBlockState blockState;
		Block b;
		int iX, iY, iZ, radius;
		float rayStrength;
		Vec3 vec;
		age++;
		if(age == 1200){
//			System.out.println("NTM C "+raysProcessed+" "+Math.round(10000D * 100D*gspNum/(double)gspNumMax)/10000D+"% "+gspNum+"/"+gspNumMax);
			age = 0;
		}
		while(this.gspNumMax >= this.gspNum){
			// Get Cartesian coordinates for spherical coordinates
			vec = this.getSpherical2cartesian();

			radius = (int) (double) this.radius;
			rayStrength = strength * 0.3F;

			//Finding the end of the ray
			for(int r = 0; r < radius+1; r ++) {

				iY = (int) Math.floor(posY + (vec.yCoord * r));
				
				if(iY < minY || iY > maxY){
					isContained = false;
					break;
				}

				iX = (int) Math.floor(posX + (vec.xCoord * r));
				iZ = (int) Math.floor(posZ + (vec.zCoord * r));


				pos.setPos(iX, iY, iZ);
				blockState = world.getBlockState(pos);
				b = blockState.getBlock();
				if(b.getExplosionResistance(null) >= 2_000_000)
					break;

				rayStrength -= (float) (Math.pow(getNukeResistance(blockState, b)+1, 3 * ((double) r) / ((double) radius))-1);

				//save block positions in to-destroy-boolean[] until rayStrength is 0 
				if(rayStrength > 0){
					if(waterCheck(b, iY)) {
						//all-air chunks don't need to be buffered at all
						addPos(iX, iY, iZ);
					}
					if(r >= radius) {
						isContained = false;
					}
				} else {
					break;
				}
			}
			
			// Raise one generalized spiral points
			this.generateGspUp();
			raysProcessed++;
			if(raysProcessed % rayCheckInterval == 0 && System.currentTimeMillis()+1 > start + time) {
				return;
			}
		} 
		orderedChunks.addAll(perChunk.keySet());
		orderedChunks.sort(comparator);
		
		isAusf3Complete = true;
	}
	
	public static float getNukeResistance(IBlockState blockState, Block b) {
		if(blockState.getMaterial().isLiquid()){
			return 0.1F;
		} else {
			if(b == Blocks.SANDSTONE) return 4F;
			if(b == Blocks.OBSIDIAN) return 18F;
			return b.getExplosionResistance(null);
		}
	}
	
	/** little comparator for roughly sorting chunks by distance to the center */
	public class CoordComparator implements Comparator<ChunkPos> {

		@Override
		public int compare(ChunkPos o1, ChunkPos o2) {

			int chunkX = (int)ExplosionNukeRayBatched.this.posX >> 4;
			int chunkZ = (int)ExplosionNukeRayBatched.this.posZ >> 4;

			int diff1 = Math.abs((chunkX - (o1.getXStart() >> 4))) + Math.abs((chunkZ - (o1.getZStart() >> 4)));
			int diff2 = Math.abs((chunkX - (o2.getXStart() >> 4))) + Math.abs((chunkZ - (o2.getZStart() >> 4)));
			
			return Integer.compare(diff1, diff2);
		}
	}

	public void processChunk(int time){
		long start = System.currentTimeMillis();
		while(System.currentTimeMillis() < start + time){
			processChunkBlocks(start, time);
		}
	}

	BitSet hitArray;
	ChunkPos chunk;
	boolean needsNewHitArray = true;
	int index = 0;

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

	public void processChunkBlocks(long start, int time){
		if(!CompatibilityConfig.isWarDim(world)){
			this.perChunk.clear();
		}
		if(this.perChunk.isEmpty()) return;
		if(needsNewHitArray){
			chunk = orderedChunks.get(0);
			hitArray = perChunk.get(chunk);
			index = hitArray.nextSetBit(0);
			needsNewHitArray = false;
		}
		
		int chunkX = chunk.getXStart();
		int chunkZ = chunk.getZStart();
		
		MutableBlockPos pos = new BlockPos.MutableBlockPos();
		int blocksRemoved = 0;
		while(index > -1) {
			pos.setPos(((index >> 4) % 16) + chunkX, 255 - (index >> 8), (index % 16) + chunkZ);
			IBlockState oldState = world.getBlockState(pos);
			Block blockBelow = oldState.getBlock();

			world.removeTileEntity(pos);
			snapshotForLight(pos, oldState, Blocks.AIR.getDefaultState());
			world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);

			for (EnumFacing facing : EnumFacing.values()) {
				BlockPos neighborPos = pos.offset(facing);
				if (world.isBlockLoaded(neighborPos)) {
					IBlockState stateNeighbor = world.getBlockState(neighborPos);
					Block blockNeighbor = stateNeighbor.getBlock();
					if (blockNeighbor != Blocks.WATER && blockNeighbor != Blocks.FLOWING_WATER && !hasWaterNeighbor(neighborPos)) {
						world.neighborChanged(neighborPos, blockBelow, pos);
					}
				}
			}

			index = hitArray.nextSetBit(index + 1);
			blocksRemoved++;
			if (blocksRemoved % 256 == 0 && System.currentTimeMillis() + 1 > start + time) {
				break;
			}
		}

		if(index < 0){
			flushSectionMasks();
			perChunk.remove(chunk);
			orderedChunks.remove(0);
			needsNewHitArray = true;
		}
	}
	
	private boolean hasWaterNeighbor(BlockPos pos) {
		for (EnumFacing facing : EnumFacing.values()) {
			BlockPos np = pos.offset(facing);
			if (world.isBlockLoaded(np)) {
				Block b = world.getBlockState(np).getBlock();
				if (b == Blocks.WATER || b == Blocks.FLOWING_WATER) {
					return true;
				}
			}
		}
		return false;
	}

	public void readEntityFromNBT(NBTTagCompound nbt) {
		radius = nbt.getInteger("radius");
		strength = nbt.getInteger("strength");
		posX = nbt.getDouble("posX");
		posY = nbt.getDouble("posY");
		posZ = nbt.getDouble("posZ");
		gspNumMax = (int)(2.5 * Math.PI * Math.pow(strength, 2));
		rayCheckInterval = 10000/radius;
		if(nbt.hasKey("igW")) ignoreWater = nbt.getBoolean("igW");
		this.waterLevel = EntityFalloutRain.getInt(CompatibilityConfig.fillCraterWithWater.get(world.provider.getDimension()));
		if(this.waterLevel == 0){
			this.waterLevel = world.getSeaLevel();
		} else if(this.waterLevel < 0 && this.waterLevel > -world.getSeaLevel()){
			this.waterLevel = world.getSeaLevel() - this.waterLevel;
		}

		if(nbt.hasKey("gspNum")){
			gspNum = nbt.getInteger("gspNum");
			isAusf3Complete = nbt.getBoolean("f3");
			isContained = nbt.getBoolean("isContained");

			int i = 0;
			while(nbt.hasKey("chunks"+i)){
				NBTTagCompound c = (NBTTagCompound)nbt.getTag("chunks"+i);

				perChunk.put(new ChunkPos(c.getInteger("cX"), c.getInteger("cZ")), BitSet.valueOf(getLongArray((NBTTagLongArray)c.getTag("cB"))));
				i++;
			}
			if(isAusf3Complete){
				orderedChunks.addAll(perChunk.keySet());
				orderedChunks.sort(comparator);
			}
		}
	}

	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("radius", radius);
		nbt.setInteger("strength", strength);
		nbt.setDouble("posX", posX);
		nbt.setDouble("posY", posY);
		nbt.setDouble("posZ", posZ);
		nbt.setBoolean("igW", ignoreWater);
		
		if(BombConfig.enableNukeNBTSaving){
			nbt.setInteger("gspNum", gspNum);
			nbt.setBoolean("f3", isAusf3Complete);
			nbt.setBoolean("isContained", isContained);
		
			int i = 0;
			for(Entry<ChunkPos, BitSet> e : perChunk.entrySet()){
				NBTTagCompound c = new NBTTagCompound();
				c.setInteger("cX", e.getKey().x);
				c.setInteger("cZ", e.getKey().z);
				c.setTag("cB", new NBTTagLongArray(e.getValue().toLongArray()));
				nbt.setTag("chunks"+i, c.copy());
				i++;
			}
		}
	}

	// Who tf forgot to add a way to retrieve the long array from NBTTagLongArray??
	public static long[] getLongArray(NBTTagLongArray nbt) {
		return ObfuscationReflectionHelper.getPrivateValue(NBTTagLongArray.class, nbt, 0);
	}

	@Override
	public void update(int processTimeMs) {
		if(!CompatibilityConfig.isWarDim(world)) {
			isAusf3Complete = true;
			return;
		}
		if(isAusf3Complete)
			processChunk(processTimeMs);
		else
			collectTip(processTimeMs);
	}

	@Override
	public void cancel() {
		isAusf3Complete = true;
		orderedChunks.clear();
		perChunk.clear();
	}

	@Override
	public boolean isComplete() {
		return isAusf3Complete && perChunk.isEmpty();
	}

	@Override
	public boolean isContained() {
		return isContained;
	}

	@Override
	public void setDetonator(UUID detonator) {
		// the batched executor does not track the detonator
	}
}