	package com.hbm.saveddata;

    import net.minecraft.nbt.*;
    import net.minecraft.world.World;
    import net.minecraft.world.storage.WorldSavedData;

    import javax.annotation.Nullable;
    import java.util.HashMap;
    import java.util.Map;

public class AuxSavedData extends WorldSavedData {
	public Map<String,DataPair> data = new HashMap<>();

	public AuxSavedData(String p_i2141_1_) {
		super(p_i2141_1_);
	}

    public AuxSavedData()
    {
        super("hbmauxdata");
        this.markDirty();
    }
	@Nullable
	public NBTBase get(String key) {
		if (data.containsKey(key))
			return data.get(key).value;
		return null;
	};
	@Nullable
	public NBTPrimitive getP(String key) {
		if (data.containsKey(key))
			return (NBTPrimitive)(data.get(key).value);
		return null;
	};
	@Nullable
	public NBTTagList getL(String key) {
		if (data.containsKey(key))
			return (NBTTagList)(data.get(key).value);
		return null;
	}
	public void set(String key,NBTBase value) {
		data.put(key,new DataPair(key,value));
		this.markDirty();
	};
	public boolean exists(String key) {
		return data.containsKey(key);
	}
    
    static class DataPair {

    	String key = "";
    	NBTBase value;

    	public DataPair() { }

    	public DataPair(String s, NBTBase i) {
    		key = s;
    		value = i;
    	}
		/* thank u for everything (not)
		void readFromNBT(NBTTagCompound nbt, int i) {
    		this.key = nbt.getString("aux_key_" + i);
    		this.value = nbt.getInteger("aux_val_" + i);
    	}
    	void writeToNBT(NBTTagCompound nbt, int i) {
    		nbt.setString("aux_key_" + i, key);
    		nbt.setInteger("aux_val_" + i, value);
    	}*/
    }

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("dCount")) { // retrocompatibility
			int count = nbt.getInteger("dCount");

			for (int i = 0; i < count; i++) {
				DataPair struct = new DataPair(nbt.getString("aux_key_" + i),new NBTTagInt(nbt.getInteger("aux_val_" + i)));
				data.put(struct.key,struct);
				//struct.readFromNBT(nbt, i);

				//data.add(struct);
			}
		} else {
			for (String key : nbt.getKeySet()) {
				data.put(key,new DataPair(key,nbt.getTag(key)));
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		data.forEach((key,value)->{
			nbt.setTag(key,value.value);
		});
		/*
		nbt.setInteger("dCount", data.size());
		
		for(int i = 0; i < data.size(); i++) {
			data.get(i).writeToNBT(nbt, i);
		}*/
		return nbt;
	}
	
	public static AuxSavedData getData(World worldObj) {

		AuxSavedData data = (AuxSavedData)worldObj.getPerWorldStorage().getOrLoadData(AuxSavedData.class, "hbmauxdata");
	    if(data == null) {
	        worldObj.getPerWorldStorage().setData("hbmauxdata", new AuxSavedData());
	        
	        data = (AuxSavedData)worldObj.getPerWorldStorage().getOrLoadData(AuxSavedData.class, "hbmauxdata");
	    }
		if (data.data == null)
			data.data = new HashMap<>();
	    
	    return data;
	}
	// get this thing alive agian-
	public static void addToList(World world,String listKey,NBTBase value) {
		AuxSavedData data = getData(world);
		if (!data.exists(listKey))
			data.set(listKey,new NBTTagList());
		data.getL(listKey).appendTag(value);
		data.markDirty();
	}
	
	public static void setThunder(World world, int dura) {
		AuxSavedData data = getData(world);
		/*
		if(data.data == null) {
			data.data = new ArrayList<DataPair>();
			data.data.add(new DataPair("thunder", dura));
			
		} else {
			
			DataPair thunder = null;
			
			for(DataPair pair : data.data) {
				if(pair.key.equals("thunder")) {
					thunder = pair;
					break;
				}
			}
			
			if(thunder == null) {
				data.data.add(new DataPair("thunder", dura));
			} else {
				thunder.value = dura;
			}
		}*/
		if (data.exists("thunder"))
			if (data.getP("thunder").getInt() >= dura)
				return;
		data.set("thunder",new NBTTagInt(dura));
		
		data.markDirty();
	}
	public static void decreaseThunder(World world, int decreasement) {
		AuxSavedData data = getData(world);
		if (data.exists("thunder")) {
			data.set("thunder", new NBTTagInt(data.getP("thunder").getInt()-decreasement));
			data.markDirty();
		}
	}

	public static int getThunder(World world) {

		AuxSavedData data = getData(world);
		/*
		if(data == null)
			return 0;

		for(DataPair pair : data.data) {
			if(pair.key.equals("thunder")) {
				return pair.value;
			}
		}*/
		if (data.exists("thunder"))
			return data.getP("thunder").getInt(); //((NBTTagInt)data.data.get("thunder").value).getInt();

		return 0;
	}
}
