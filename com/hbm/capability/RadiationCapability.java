package com.hbm.capability;

import java.util.concurrent.Callable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class RadiationCapability {
	
	public interface IEntityRadioactive {
		public float getRads();
		public void setRads(float rads);
		public void increaseRads(float rads);
		public void decreaseRads(float rads);
	}
	
	public static class EntityRadioactive implements IEntityRadioactive {

		public static final Callable<IEntityRadioactive> FACTORY = () -> {return new EntityRadioactive();};
		
		private float rads = 0;
		
		@Override
		public float getRads() {
			return rads;
		}

		@Override
		public void setRads(float rads) {
			this.rads = rads;
			if(this.rads < 0){
				this.rads = 0;
			}
		}
		
		@Override
		public void increaseRads(float rads){
			this.rads = this.rads + rads;
			if(this.rads < 0){
				this.rads = 0;
			}
		}
		
		@Override
		public void decreaseRads(float rads){
			this.rads = this.rads - rads;
			if(this.rads < 0){
				this.rads = 0;
			}
		}
		
	}
	
	public static class EntityRadioactiveStorage implements IStorage<IEntityRadioactive>{

		@Override
		public NBTBase writeNBT(Capability<IEntityRadioactive> capability, IEntityRadioactive instance, EnumFacing side) {
			return new NBTTagFloat(instance.getRads());
		}

		@Override
		public void readNBT(Capability<IEntityRadioactive> capability, IEntityRadioactive instance, EnumFacing side, NBTBase nbt) {
			instance.setRads(((NBTPrimitive)nbt).getFloat());
		}
		
	}
	
	public static class EntityRadiationProvider implements ICapabilitySerializable<NBTBase> {

		public static final IEntityRadioactive DUMMY = new IEntityRadioactive(){
			@Override
			public float getRads() {
				return 0;
			}
			@Override
			public void setRads(float rads) {
			}
			@Override
			public void increaseRads(float rads) {
			}
			@Override
			public void decreaseRads(float rads) {
			}
		};
		
		@CapabilityInject(IEntityRadioactive.class)
		public static final Capability<IEntityRadioactive> ENT_RAD_CAP = null;
		
		private IEntityRadioactive instance = ENT_RAD_CAP.getDefaultInstance();
		
		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			return capability == ENT_RAD_CAP;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			return capability == ENT_RAD_CAP ? ENT_RAD_CAP.<T>cast(this.instance) : null;
		}

		@Override
		public NBTBase serializeNBT() {
			return ENT_RAD_CAP.getStorage().writeNBT(ENT_RAD_CAP, instance, null);
		}

		@Override
		public void deserializeNBT(NBTBase nbt) {
			ENT_RAD_CAP.getStorage().readNBT(ENT_RAD_CAP, instance, null, nbt);
		}
		
	}
}
