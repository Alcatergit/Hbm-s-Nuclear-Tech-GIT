package com.hbm.capability;

import java.util.concurrent.Callable;

import com.hbm.handler.HbmKeybinds.EnumKeybind;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class HbmCapability {

	public interface IHBMData {
		public boolean getKeyPressed(EnumKeybind key);
		public void setKeyPressed(EnumKeybind key, boolean pressed);
	}
	
	public static class HBMData implements IHBMData {

		public static final Callable<IHBMData> FACTORY = () -> {return new HBMData();};
		
		private boolean[] keysPressed = new boolean[EnumKeybind.values().length];
		
		@Override
		public boolean getKeyPressed(EnumKeybind key) {
			return keysPressed[key.ordinal()];
		}

		@Override
		public void setKeyPressed(EnumKeybind key, boolean pressed) {
			keysPressed[key.ordinal()] = pressed;
		}
		
	}
	
	public static class HBMDataStorage implements IStorage<IHBMData>{

		@Override
		public NBTBase writeNBT(Capability<IHBMData> capability, IHBMData instance, EnumFacing side) {
			NBTTagCompound tag = new NBTTagCompound();
			for(EnumKeybind key : EnumKeybind.values()){
				tag.setBoolean(key.name(), instance.getKeyPressed(key));
			}
			return tag;
		}

		@Override
		public void readNBT(Capability<IHBMData> capability, IHBMData instance, EnumFacing side, NBTBase nbt) {
			if(nbt instanceof NBTTagCompound){
				for(EnumKeybind key : EnumKeybind.values()){
					instance.setKeyPressed(key, ((NBTTagCompound)nbt).getBoolean(key.name()));
				}
			}
		}
		
	}
	
	public static class HBMDataProvider implements ICapabilitySerializable<NBTBase> {

		public static final IHBMData DUMMY = new IHBMData(){

			@Override
			public boolean getKeyPressed(EnumKeybind key) {
				return false;
			}

			@Override
			public void setKeyPressed(EnumKeybind key, boolean pressed) {
			}
		};
		
		@CapabilityInject(IHBMData.class)
		public static final Capability<IHBMData> HBM_CAP = null;
		
		private IHBMData instance = HBM_CAP.getDefaultInstance();
		
		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			return capability == HBM_CAP;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			return capability == HBM_CAP ? HBM_CAP.<T>cast(this.instance) : null;
		}

		@Override
		public NBTBase serializeNBT() {
			return HBM_CAP.getStorage().writeNBT(HBM_CAP, instance, null);
		}

		@Override
		public void deserializeNBT(NBTBase nbt) {
			HBM_CAP.getStorage().readNBT(HBM_CAP, instance, null, nbt);
		}
		
	}
	
	public static IHBMData getData(Entity e){
		if(e.hasCapability(HBMDataProvider.HBM_CAP, null))
			return e.getCapability(HBMDataProvider.HBM_CAP, null);
		return HBMDataProvider.DUMMY;
	}
}
