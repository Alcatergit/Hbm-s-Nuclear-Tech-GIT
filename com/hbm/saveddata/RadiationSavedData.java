package com.hbm.saveddata;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class RadiationSavedData extends WorldSavedData {
	public Map<ChunkPos, RadiationSaveStructure> contamination = new HashMap<ChunkPos, RadiationSaveStructure>();
	
	private static RadiationSavedData openInstance;
	
    public World worldObj;

	public RadiationSavedData(String p_i2141_1_) {
		super(p_i2141_1_);
	}

    public RadiationSavedData(World p_i1678_1_)
    {
        super("radiation");
        this.worldObj = p_i1678_1_;
        this.markDirty();
    }
    
    public boolean doesEntryExist(int x, int y) {
    	
    	return getRadFromCoord(x, y) != null;
    }
    
    public void createEntry(int x, int y, float rad) {
    	
    	contamination.put(new ChunkPos(x, y), new RadiationSaveStructure(x, y, rad));
        this.markDirty();
    }
    
    public void deleteEntry(RadiationSaveStructure struct) {
    	
    	contamination.remove(struct);
        this.markDirty();
    }
    
    public void jettisonData() {
    	
    	contamination.clear();
        this.markDirty();
    }
    
    public void setRadForCoord(int x, int y, float radiation) {
    	ChunkPos pos = new ChunkPos(x, y);
    	RadiationSaveStructure entry = contamination.get(pos);
    	
    	if(entry == null) {

    		entry = new RadiationSaveStructure(x, y, radiation);
        	contamination.put(pos, entry);
    	}
    	
    	entry.radiation = radiation;
        this.markDirty();
    	
    }
    
    public RadiationSaveStructure getRadFromCoord(int x, int y) {
    	ChunkPos pos = new ChunkPos(x, y);
    	return contamination.get(pos);
    }
    
    public float getRadNumFromCoord(int x, int y) {
    	ChunkPos pos = new ChunkPos(x, y);
    	RadiationSaveStructure rad = contamination.get(pos);
    	if(rad != null)
    		return rad.radiation;
    	return 0F;
    }
    
    public void updateSystem() {
    	
    	Map<ChunkPos, RadiationSaveStructure> tempList = new HashMap<ChunkPos, RadiationSaveStructure>(contamination);
    	
    	contamination.clear();
    	
    	for(RadiationSaveStructure struct : tempList.values()) {
    		
    		if(struct.radiation != 0) {

				//struct.radiation *= 0.999F;
				struct.radiation *= 0.99F;
				struct.radiation -= 0.5F;
				
				if(struct.radiation <= 0) {
					struct.radiation = 0;
				}
				
				if(struct.radiation > MainRegistry.fogRad && worldObj != null && worldObj.rand.nextInt(MainRegistry.fogCh) == 0 && worldObj.getChunkFromChunkCoords(struct.chunkX, struct.chunkY).isLoaded()) {
					
					int x = struct.chunkX * 16 + worldObj.rand.nextInt(16);
					int z = struct.chunkY * 16 + worldObj.rand.nextInt(16);
					int y = worldObj.getHeight(x, z) + worldObj.rand.nextInt(5);
					
					//EntityFogFX fog = new EntityFogFX(worldObj);
					//fog.setPosition(x, y, z);
					//System.out.println(x + " " + y + " " + z);
					//worldObj.spawnEntity(fog);
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(x, y, z, 3), new TargetPoint(worldObj.provider.getDimension(), x, y, z, 100));
				}
    			
    			if(struct.radiation > 1) {
    				
    				float[] rads = new float[9];

    				rads[0] = getRadNumFromCoord(struct.chunkX + 1, struct.chunkY + 1);
    				rads[1] = getRadNumFromCoord(struct.chunkX, struct.chunkY + 1);
    				rads[2] = getRadNumFromCoord(struct.chunkX - 1, struct.chunkY + 1);
    				rads[3] = getRadNumFromCoord(struct.chunkX - 1, struct.chunkY);
    				rads[4] = getRadNumFromCoord(struct.chunkX - 1, struct.chunkY - 1);
    				rads[5] = getRadNumFromCoord(struct.chunkX, struct.chunkY - 1);
    				rads[6] = getRadNumFromCoord(struct.chunkX + 1, struct.chunkY - 1);
    				rads[7] = getRadNumFromCoord(struct.chunkX + 1, struct.chunkY);
    				rads[8] = getRadNumFromCoord(struct.chunkX, struct.chunkY);
    				
    				float main = 0.6F;
    				float side = 0.075F;
    				float corner = 0.025F;
    				
    				setRadForCoord(struct.chunkX + 1, struct.chunkY + 1, rads[0] + struct.radiation * corner);
    				setRadForCoord(struct.chunkX, struct.chunkY + 1, rads[1] + struct.radiation * side);
    				setRadForCoord(struct.chunkX - 1, struct.chunkY + 1, rads[2] + struct.radiation * corner);
    				setRadForCoord(struct.chunkX - 1, struct.chunkY, rads[3] + struct.radiation * side);
    				setRadForCoord(struct.chunkX - 1, struct.chunkY - 1, rads[4] + struct.radiation * corner);
    				setRadForCoord(struct.chunkX, struct.chunkY - 1, rads[5] + struct.radiation * side);
    				setRadForCoord(struct.chunkX + 1, struct.chunkY - 1, rads[6] + struct.radiation * corner);
    				setRadForCoord(struct.chunkX + 1, struct.chunkY, rads[7] + struct.radiation * side);
    				setRadForCoord(struct.chunkX, struct.chunkY, rads[8] + struct.radiation * main);
    				
    			} else {
    				
    				this.setRadForCoord(struct.chunkX, struct.chunkY, getRadNumFromCoord(struct.chunkX, struct.chunkY) + struct.radiation);
    			}
    		}
    	}
    	
        this.markDirty();
    }

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		int count = nbt.getInteger("radCount");
		
		for(int i = 0; i < count; i++) {
			RadiationSaveStructure struct = new RadiationSaveStructure();
			struct.readFromNBT(nbt, i);
			
			contamination.put(new ChunkPos(struct.chunkX, struct.chunkY), struct);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("radCount", contamination.size());
		int i = 0;
		Iterator<RadiationSaveStructure> itr = contamination.values().iterator();
		while(itr.hasNext()){
			itr.next().writeToNBT(nbt, i);
			i++;
		}
		return nbt;
	}
	
	public static RadiationSavedData getData(World worldObj) {
		
		if(openInstance != null && openInstance.worldObj == worldObj)
			return openInstance;

		RadiationSavedData data = (RadiationSavedData)worldObj.getPerWorldStorage().getOrLoadData(RadiationSavedData.class, "radiation");
	    if(data == null) {
	        worldObj.getPerWorldStorage().setData("radiation", new RadiationSavedData(worldObj));
	        
	        data = (RadiationSavedData)worldObj.getPerWorldStorage().getOrLoadData(RadiationSavedData.class, "radiation");
	    }
	    
	    data.worldObj = worldObj;
	    openInstance  = data;
	    
	    return openInstance;
	}
	
	public static void incrementRad(World worldObj, int x, int z, float rad, float maxRad) {
		
		RadiationSavedData data = getData(worldObj);
		
		Chunk chunk = worldObj.getChunkFromBlockCoords(new BlockPos(x, -1, z));
		
		float r = data.getRadNumFromCoord(chunk.x, chunk.z);
		
		if(r < maxRad) {
			
			data.setRadForCoord(chunk.x, chunk.z, r + rad);
		}
	}
	
	public static void decrementRad(World worldObj, int x, int z, float rad) {
		
		RadiationSavedData data = getData(worldObj);
		
		Chunk chunk = worldObj.getChunkFromBlockCoords(new BlockPos(x, -1, z));
		
		float r = data.getRadNumFromCoord(chunk.x, chunk.z);
		
		r -= rad;
		
		if(r > 0) {
			data.setRadForCoord(chunk.x, chunk.z, r);
		} else {
			data.setRadForCoord(chunk.x, chunk.z, 0);
		}
	}
}
