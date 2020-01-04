package com.hbm.tileentity.machine;

import com.hbm.interfaces.IConsumer;

import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import cofh.redstoneflux.impl.EnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityConverterHeRf extends TileEntity implements ITickable, IConsumer, IEnergyProvider, IEnergyStorage {

	public long power;
	public final long maxPower = 1000000;
	public EnergyStorage storage = new EnergyStorage(4000000, 2500000, 2500000);
	
	//Thanks to the great people of Fusion Warfare for helping me with this part.
	
	@Override
	public void update() {
		if (!world.isRemote) {

			for(int i = 0; i < 9; i++)
			if(power >= 100000 && storage.getEnergyStored() + 400000 <= storage.getMaxEnergyStored())
			{
				power -= 100000;
				storage.setEnergyStored(storage.getEnergyStored() + 400000);
			}
			for(int i = 0; i < 9; i++)
			if(power >= 10000 && storage.getEnergyStored() + 40000 <= storage.getMaxEnergyStored())
			{
				power -= 10000;
				storage.setEnergyStored(storage.getEnergyStored() + 40000);
			}
			for(int i = 0; i < 9; i++)
			if(power >= 1000 && storage.getEnergyStored() + 4000 <= storage.getMaxEnergyStored())
			{
				power -= 1000;
				storage.setEnergyStored(storage.getEnergyStored() + 4000);
			}
			for(int i = 0; i < 9; i++)
			if(power >= 100 && storage.getEnergyStored() + 400 <= storage.getMaxEnergyStored())
			{
				power -= 100;
				storage.setEnergyStored(storage.getEnergyStored() + 400);
			}
			for(int i = 0; i < 9; i++)
			if(power >= 10 && storage.getEnergyStored() + 40 <= storage.getMaxEnergyStored())
			{
				power -= 10;
				storage.setEnergyStored(storage.getEnergyStored() + 4);
			}
			for(int i = 0; i < 10; i++)
			if(power >= 1 && storage.getEnergyStored() + 4 <= storage.getMaxEnergyStored())
			{
				power -= 1;
				storage.setEnergyStored(storage.getEnergyStored() + 40);
			}
			
			for (EnumFacing dir : EnumFacing.VALUES) {
				//Drillgon200: BlockPos is basically the location class, but without as much abstraction.
				TileEntity entity = world.getTileEntity(pos.offset(dir));
			
				if (entity != null && entity instanceof IEnergyReceiver) {
				
					IEnergyReceiver receiver = (IEnergyReceiver) entity;
					
					int maxExtract = storage.getMaxExtract();
					int maxAvailable = storage.extractEnergy(maxExtract, true);
					int energyTransferred = receiver.receiveEnergy(dir.getOpposite(), maxAvailable, false);

					storage.extractEnergy(energyTransferred, false);
				}
			}
			detectAndSendChanges();
		}
		
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("storage", storage.writeToNBT(new NBTTagCompound()));
		compound.setLong("power", power);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("storage")){
			storage.readFromNBT(compound.getCompoundTag("storage"));
			detectRfPower = storage.getEnergyStored() + 1;
		}
		power = compound.getLong("power");
		detectPower = power + 1;
		super.readFromNBT(compound);
	}

	@Override
	public int getEnergyStored(EnumFacing from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return storage.getMaxEnergyStored();
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY ? CapabilityEnergy.ENERGY.cast(this) :super.getCapability(capability, facing);
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored() {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored() {
		return storage.getMaxEnergyStored();
	}

	@Override
	public boolean canExtract() {
		return true;
	}

	@Override
	public boolean canReceive() {
		return false;
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public long getFluxScaled(long i) {
		return (storage.getEnergyStored() * i) / storage.getMaxEnergyStored();
	}
	
	long detectPower;
	int detectRfPower;
	
	private void detectAndSendChanges() {
		boolean mark = false;
		if(detectPower != power){
			mark = true;
			detectPower = power;
		}
		if(detectRfPower != storage.getEnergyStored()){
			mark = true;
			detectRfPower = storage.getEnergyStored();
		}
		if(mark)
			markDirty();
		
	}

}
