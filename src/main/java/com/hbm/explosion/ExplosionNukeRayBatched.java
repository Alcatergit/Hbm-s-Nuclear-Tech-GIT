package com.hbm.explosion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.List;

import com.hbm.config.BombConfig;
import com.hbm.config.CompatibilityConfig;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class ExplosionNukeRayBatched {

	public HashMap<ChunkPos, boolean[]> perChunk = new HashMap(); //for future: optimize blockmap further by using sub-chunks instead of chunks
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

	public void addPos(int x, int y, int z){
		chunk = new ChunkPos(x >> 4, z >> 4);
		boolean[] hitPositions = perChunk.get(chunk);
				
		if(hitPositions == null) {
			hitPositions = new boolean[65536];
			perChunk.put(chunk, hitPositions); //we re-use the same pos instead of using individualized per-chunk ones to save on RAM
		}
		hitPositions[((255-y) << 8) + ((x - chunk.getXStart()) << 4) + (z - chunk.getZStart())] = true;
	}

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

		while (this.gspNumMax >= this.gspNum){
			// Get Cartesian coordinates for spherical coordinates
			vec = this.getSpherical2cartesian();

			radius = (int)Math.ceil(this.radius);
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

				rayStrength -= Math.pow(getNukeResistance(blockState, b)+1, 3 * ((double) r) / ((double) radius))-1;

				//save block positions in to-destroy-boolean[] until rayStrength is 0 
				if(rayStrength > 0){
					if(b != Blocks.AIR) {
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
			if(raysProcessed % 50 == 0 && System.currentTimeMillis()+1 > start + time) {
				// System.out.println("NTM C "+raysProcessed+" "+Math.round(1000D * 100D*gspNum/(double)gspNumMax)/1000D+"% "+gspNum+"/"+gspNumMax+" "+(System.currentTimeMillis()-start)+"ms");
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

			int chunkX = ExplosionNukeRayBatched.this.posX >> 4;
			int chunkZ = ExplosionNukeRayBatched.this.posZ >> 4;

			int diff1 = Math.abs((chunkX - (int) (o1.getXStart() >> 4))) + Math.abs((chunkZ - (int) (o1.getZStart() >> 4)));
			int diff2 = Math.abs((chunkX - (int) (o2.getXStart() >> 4))) + Math.abs((chunkZ - (int) (o2.getZStart() >> 4)));
			
			return diff1 > diff2 ? 1 : diff1 < diff2 ? -1 : 0;
		}
	}

	public void processChunk(int time){
		long start = System.currentTimeMillis();
		while(System.currentTimeMillis() < start + time){
			processChunkBlocks(start, time);
		}
	}

	public boolean getHasHits(boolean[] hitArray, int start){
		for(int i = start; i < 65536; i++){
			if(hitArray[i]){
				index = i;
				return true;
			}
		}
		return false;
	}

	boolean[] hitArray;
	ChunkPos chunk;
	boolean needsNewHitArray = true;
	int index = 0;

	public void processChunkBlocks(long start, int time){
		if(!CompatibilityConfig.isWarDim(world)){
			this.perChunk.clear();
		}
		if(this.perChunk.isEmpty()) return;
		if(needsNewHitArray){
			index = 0;
			chunk = orderedChunks.get(0);
			hitArray = perChunk.get(chunk);
		}
		
		int chunkX = chunk.getXStart();
		int chunkZ = chunk.getZStart();
		
		MutableBlockPos pos = new BlockPos.MutableBlockPos();
		for(; index < 65536; index++) {
			if(hitArray[index]){
				pos.setPos(((index >> 4) % 16) + chunkX, 255 - (index >> 8), (index % 16) + chunkZ);
				world.setBlockToAir(pos);

				if(index % 256 == 0 && System.currentTimeMillis()+1 > start + time){
					break;
				}
			}
		}

		if(index >= 65536 || !getHasHits(hitArray, index)){
			perChunk.remove(chunk);
			orderedChunks.remove(0);
			needsNewHitArray = true;
		} else {
			needsNewHitArray = false;
		}
	}


	public static byte[] convertBoolArrayToByteArray(boolean[] boolArr) {
	    byte[] byteArr = new byte[boolArr.length>>3];
	    for(int i = 0; i < byteArr.length; i++){
	    	byte b = 0;
		    for(int k = 0; k < 8; k++) {
		        if(boolArr[k + (i<<3)])
		            b |= 1 << (7 - k);
		    }
	        byteArr[i] = b;
	    }
	    return byteArr;
	}

	public static boolean[] convertByteArrayToBoolArray(byte[] byteArr) {
	    boolean[] boolArr = new boolean[byteArr.length<<3];
	    for(int i = 0; i < byteArr.length; i++){
	        for(int b = 0; b < 8; b++){
	    		boolArr[b + (i<<3)] = (byteArr[i] & (byte)(128 / Math.pow(2, b))) != 0;
	        }
	    }
	    return boolArr;
	}
	

	public void readEntityFromNBT(NBTTagCompound nbt) {
		radius = nbt.getInteger("radius");
		strength = nbt.getInteger("strength");
		posX = nbt.getInteger("posX");
		posY = nbt.getInteger("posY");
		posZ = nbt.getInteger("posZ");
		gspNumMax = (int)(2.5 * Math.PI * Math.pow(strength, 2));
		
		if(nbt.hasKey("gspNum")){
			gspNum = nbt.getInteger("gspNum");
			isAusf3Complete = nbt.getBoolean("f3");
			isContained = nbt.getBoolean("isContained");

			int i = 0;
			while(nbt.hasKey("chunks"+i)){
				NBTTagCompound c = (NBTTagCompound)nbt.getTag("chunks"+i);
				boolean[] hits = convertByteArrayToBoolArray(c.getByteArray("cB"));
				if(perChunk.containsKey(new ChunkPos(c.getInteger("cX"), c.getInteger("cZ")))) System.out.println("NTM DUPLICATE WTF");
				perChunk.put(new ChunkPos(c.getInteger("cX"), c.getInteger("cZ")), hits);
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
		nbt.setInteger("posX", posX);
		nbt.setInteger("posY", posY);
		nbt.setInteger("posZ", posZ);
		
		if(BombConfig.enableNukeNBTSaving){
			nbt.setInteger("gspNum", gspNum);
			nbt.setBoolean("f3", isAusf3Complete);
			nbt.setBoolean("isContained", isContained);
		
			int i = 0;
			for(Entry<ChunkPos, boolean[]> e : perChunk.entrySet()){
				NBTTagCompound c = new NBTTagCompound();
				c.setInteger("cX", e.getKey().x);
				c.setInteger("cZ", e.getKey().z);
				c.setByteArray("cB", convertBoolArrayToByteArray(e.getValue()));
				nbt.setTag("chunks"+i, c.copy());
				i++;
			}
		}
	}
}
