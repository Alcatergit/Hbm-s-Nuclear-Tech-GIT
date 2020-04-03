package com.hbm.saveddata;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

public class SatelliteSaveData extends WorldSavedData {

	private Map<Integer, SatelliteSaveStructure> satellites = new HashMap<Integer, SatelliteSaveStructure>();
	
	private static SatelliteSaveData openInstance;
	public World world;
	
	public SatelliteSaveData(String name) {
		super(name);
	}
	
	public SatelliteSaveData(World w){
		super("hbmsatellites");
		this.world = w;
		this.markDirty();
	}
	
	public static SatelliteSaveData getData(World w){
		if(openInstance != null && openInstance.world == w)
			return openInstance;
		SatelliteSaveData data = (SatelliteSaveData)w.getPerWorldStorage().getOrLoadData(SatelliteSaveData.class, "hbmsatellites");
		if(data == null){
			w.getPerWorldStorage().setData("hbmsatellites", new SatelliteSaveData(w));
			data = (SatelliteSaveData)w.getPerWorldStorage().getOrLoadData(SatelliteSaveData.class, "hbmsatellites");
		}
		data.world = w;
		openInstance = data;
		
		return openInstance;
	}
	
	public void addSatellite(int freq, SatelliteType type){
		addSatellite(new SatelliteSaveStructure(freq, type));
	}
	
	public void addSatellite(SatelliteSaveStructure s){
		if(s == null)
			return;
		satellites.put(s.satelliteID, s);
		markDirty();
	}
	
	public void removeSatellite(int freq){
		satellites.remove(freq);
		markDirty();
	}
	
	public boolean isFreqTaken(int freq) {
    	return getSatFromFreq(freq) != null;
    }
	
	public SatelliteSaveStructure getSatFromFreq(int freq) {
    	return satellites.get(freq);
    }

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		int satCount = nbt.getInteger("satCount");
		for(int i = 0; i < satCount; i++){
			SatelliteSaveStructure sat = new SatelliteSaveStructure(nbt, i);
			satellites.put(sat.satelliteID, sat);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("satCount", satellites.size());
		int i = 0;
		Iterator<SatelliteSaveStructure> itr = satellites.values().iterator();
		while(itr.hasNext()){
			itr.next().writeToNBT(compound, i);
			i++;
		}
		return compound;
	}

	public static class SatelliteSaveStructure {
		public int satelliteID;
		public SatelliteType type;
		public long lastOp;
		
		public SatelliteSaveStructure(int id, SatelliteType type) {
			satelliteID = id;
			this.type = type;
			lastOp = 0;
		}
		
		public SatelliteSaveStructure(int id, SatelliteType type, long op) {
			satelliteID = id;
			this.type = type;
			lastOp = op;
		}
		
		public SatelliteSaveStructure(NBTTagCompound tag, int i) {
			satelliteID = tag.getInteger("sat_" + i + "_id");
			type = SatelliteType.getEnum(tag.getInteger("sat_" + i + "_type"));
			lastOp = tag.getLong("sat_" + i + "_op");
		}
		
		public void writeToNBT(NBTTagCompound tag, int i){
			tag.setInteger("sat_" + i + "_id", satelliteID);
			tag.setInteger("sat_" + i + "_type", type.getID());
			tag.setLong("sat_" + i + "_op", lastOp);
		}
	}
	
	public enum SatelliteType {
		
		//Prints map remotely
		MAPPER,
		//Displays entities
		RADAR,
		//Prints map, ores only
		SCANNER,
		//Does nothing
		RELAY,
		//Death ray
		LASER,
		//Allows use of AMS
		RESONATOR,
		//Farms ores for free
		MINER;
		
		public static SatelliteType getEnum(int i) {
			if(i < SatelliteType.values().length)
				return SatelliteType.values()[i];
			else
				return SatelliteType.RELAY;
		}
		
		public int getID() {
			return Arrays.asList(SatelliteType.values()).indexOf(this);
		}
	}
}
