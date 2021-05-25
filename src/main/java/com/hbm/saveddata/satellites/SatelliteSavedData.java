package com.hbm.saveddata.satellites;

import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

public class SatelliteSavedData extends WorldSavedData {

	public HashMap<Integer, Satellite> sats = new HashMap<Integer, Satellite>();
	
	public SatelliteSavedData(String p_i2141_1_) {
		super(p_i2141_1_);
	}

    public SatelliteSavedData()
    {
        super("satellites");
        this.markDirty();
    }
    
    public boolean isFreqTaken(int freq) {
    	
    	return getSatFromFreq(freq) != null;
    }
    
    public Satellite getSatFromFreq(int freq) {
    	
    	return sats.get(freq);
    }

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		int satCount = nbt.getInteger("satCount");
		
		for(int i = 0; i < satCount; i++) {
			
			Satellite sat = Satellite.create(nbt.getInteger("sat_id_" + i));
			sat.readFromNBT((NBTTagCompound) nbt.getTag("sat_data_" + i));
			
			int freq = nbt.getInteger("sat_freq_" + i);
			
			sats.put(freq, sat);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("satCount", sats.size());
		
		int i = 0;

    	for(Entry<Integer, Satellite> struct : sats.entrySet()) {

    		NBTTagCompound data = new NBTTagCompound();
    		struct.getValue().writeToNBT(data);
    		
    		nbt.setInteger("sat_id_" + i, struct.getValue().getID());
    		nbt.setTag("sat_data_" + i, data);
    		nbt.setInteger("sat_freq_" + i, struct.getKey());
			i++;
    	}
    	return nbt;
	}
	
	public static SatelliteSavedData getData(World worldObj) {
		SatelliteSavedData data = (SatelliteSavedData)worldObj.getPerWorldStorage().getOrLoadData(SatelliteSavedData.class, "satellites");
	    if(data == null) {
	        worldObj.getPerWorldStorage().setData("satellites", new SatelliteSavedData());
	        data = (SatelliteSavedData)worldObj.getPerWorldStorage().getOrLoadData(SatelliteSavedData.class, "satellites");
	    }
	    return data;
	}
}
