package com.hbm.explosion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.hbm.config.CompatibilityConfig;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class ExplosionNukeRayBatched {

	public HashMap<ChunkPos, List<FloatTriplet>> perChunk = new HashMap(); //for future: optimize blockmap further by using sub-chunks instead of chunks
	public List<ChunkPos> orderedChunks = new ArrayList();
	private CoordComparator comparator = new CoordComparator();
	public boolean isContained = true;
	int posX;
	int posY;
	int posZ;
	World world;

	int strength;
	int radius;

	int gspNumMax;
	int gspNum;
	double gspX;
	double gspY;

	private static final int maxY = 255;
	private static final int minY = 0;

	public boolean isAusf3Complete = false;

	public ExplosionNukeRayBatched(World world, int x, int y, int z, int strength, int radius) {
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
	private Vec3 getSpherical2cartesian(){
		double dx = Math.sin(this.gspX) * Math.cos(this.gspY);
		double dz = Math.sin(this.gspX) * Math.sin(this.gspY);
		double dy = Math.cos(this.gspX);
		return Vec3.createVectorHelper(dx, dy, dz);
	}

	public void collectTip(int count) {
		if(!CompatibilityConfig.isWarDim(world)){
			isAusf3Complete = true;
			return;
		}
		MutableBlockPos pos = new BlockPos.MutableBlockPos();
		int rayProcessed = 0;
		double fac = 1;
		while (this.gspNumMax >= this.gspNum){
			// Get Cartesian coordinates for spherical coordinates
			Vec3 vec = this.getSpherical2cartesian();

			int radius = (int)Math.ceil(this.radius);
			float rayStrength = strength * 0.3F;

			FloatTriplet lastPos = null;
			HashSet<ChunkPos> chunkCoords = new HashSet();

			//Finding the end of the ray
			for(int r = 0; r < radius+1; r ++) {

				float x0 = (float) (posX + (vec.xCoord * r));
				float y0 = (float) (posY + (vec.yCoord * r));
				float z0 = (float) (posZ + (vec.zCoord * r));

				int iX = (int) Math.floor(x0);
				int iY = (int) Math.floor(y0);
				int iZ = (int) Math.floor(z0);

				if(iY < minY || iY > maxY){
					if(isContained) {
						isContained = false;
					}
					break;
				}

				pos.setPos(iX, iY, iZ);
				IBlockState blockState = world.getBlockState(pos);
				if(blockState.getBlock().getExplosionResistance(null) >= 2_000_000)
					break;

				fac = 3 * ((double) r) / ((double) radius);
				rayStrength -= Math.pow(getNukeResistance(blockState)+1, fac)-1;

				//save block positions in to-destroy-list until rayStrength is 0 
				if(rayStrength > 0){
					lastPos = new FloatTriplet(x0, y0, z0);
					if(blockState.getBlock() != Blocks.AIR) {
						//all-air chunks don't need to be buffered at all
						ChunkPos chunkPos = new ChunkPos(iX >> 4, iZ >> 4);
						chunkCoords.add(chunkPos);
					}
					if(isContained && r == radius) {
						isContained = false;
					}
				} else {
					break;
				}
			}
			
			//saving the ray endpoints per chunk
			for(ChunkPos cPos : chunkCoords) {
				List<FloatTriplet> triplets = perChunk.get(cPos);
				
				if(triplets == null) {
					triplets = new ArrayList();
					perChunk.put(cPos, triplets); //we re-use the same pos instead of using individualized per-chunk ones to save on RAM
				}
				
				triplets.add(lastPos);
			}
			
			// Raise one generalized spiral points
			this.generateGspUp();

			rayProcessed++;
			if(rayProcessed >= count) {
				return;
			}
		} 
		
		orderedChunks.addAll(perChunk.keySet());
		orderedChunks.sort(comparator);
		
		isAusf3Complete = true;
	}
	
	public static float getNukeResistance(IBlockState blockState) {
		if(blockState.getMaterial().isLiquid()){
			return 0.1F;
		} else {
			if(blockState.getBlock() == Blocks.SANDSTONE) return 4F;
			if(blockState.getBlock() == Blocks.OBSIDIAN) return 18F;
			return blockState.getBlock().getExplosionResistance(null);
		}
	}
	
	/** little comparator for roughly sorting chunks by distance to the center */
	public class CoordComparator implements Comparator<ChunkPos> {

		@Override
		public int compare(ChunkPos o1, ChunkPos o2) {

			int chunkX = ExplosionNukeRayBatched.this.posX >> 4;
			int chunkZ = ExplosionNukeRayBatched.this.posZ >> 4;

			int diff1 = Math.abs((chunkX - (int) (o1.getXStart() >> 4))) + Math.abs((chunkZ - (int) (o1.getZStart() >> 4)));
			int diff2 = Math.abs((chunkX - (int) (o2.getXStart() >> 4))) + Math.abs((chunkZ - (int) (o2.getZStart() >> 4)));
			
			return diff1 > diff2 ? 1 : diff1 < diff2 ? -1 : 0;
		}
	}

	public void processChunk() {
		if(!CompatibilityConfig.isWarDim(world)){
			this.perChunk.clear();
		}
		if(this.perChunk.isEmpty()) return;
		
		ChunkPos coord = orderedChunks.get(0);
		List<FloatTriplet> list = perChunk.get(coord);
		HashSet<BlockPos> toRem = new HashSet();

		int chunkX = coord.getXStart() >> 4;
		int chunkZ = coord.getZStart() >> 4;
		
		int enter = (int) (Math.min(
				Math.abs(posX - (chunkX << 4)),
				Math.abs(posZ - (chunkZ << 4))
			)) - 16; //jump ahead to cut back on NOPs
		
		enter = Math.max(enter, 0);
		
		for(FloatTriplet triplet : list) {
			float x = triplet.xCoord;
			float y = triplet.yCoord;
			float z = triplet.zCoord;
			Vec3 vec = Vec3.createVectorHelper(x - this.posX, y - this.posY, z - this.posZ);
			double vLen = vec.lengthVector();
			double pX = vec.xCoord / vLen;
			double pY = vec.yCoord / vLen;
			double pZ = vec.zCoord / vLen;
			
			boolean inChunk = false;
			for(int i = enter; i < vLen; i++) {
				int x0 = (int) Math.floor(posX + pX * i);
				int y0 = (int) Math.floor(posY + pY * i);
				int z0 = (int) Math.floor(posZ + pZ * i);
				
				if(x0 >> 4 != chunkX || z0 >> 4 != chunkZ) {
					if(inChunk) {
						break;
					} else {
						continue;
					}
				}
				
				inChunk = true;
				if(world.getBlockState(new BlockPos(x0, y0, z0)).getBlock() != Blocks.AIR) {
					toRem.add(new BlockPos(x0, y0, z0));
				}
			}
		}
		for(BlockPos pos : toRem) {
			world.setBlockToAir(pos);
		}
		
		perChunk.remove(coord);
		orderedChunks.remove(0);
	}
	
	public class FloatTriplet {
		public float xCoord;
		public float yCoord;
		public float zCoord;
		
		public FloatTriplet(float x, float y, float z) {
			xCoord = x;
			yCoord = y;
			zCoord = z;
		}
	}
}
