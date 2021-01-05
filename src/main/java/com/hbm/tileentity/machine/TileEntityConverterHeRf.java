package com.hbm.tileentity.machine;

import com.hbm.interfaces.IConsumer;
import com.hbm.tileentity.TileEntityMachineBase;

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

public class TileEntityConverterHeRf extends TileEntityMachineBase implements ITickable, IConsumer, IEnergyProvider, IEnergyStorage {

	public long power;
	public long maxPower = 500000000;
	public EnergyStorage storage = new EnergyStorage(2000000000, 2000000000, 2000000000);
	
	public int buf;
	
	//Thanks to the great people of Fusion Warfare for helping me with this part.
	
	public TileEntityConverterHeRf() {
		super(0);
	}
	
	@Override
	public String getName() {
		return "";
	}
	
	@Override
	public void update() {
		if (!world.isRemote) {

			storage.setCapacity((int)power * 4);
			storage.setEnergyStored((int)power * 4);
			
			buf = storage.getEnergyStored();
			
			for (EnumFacing dir : EnumFacing.VALUES) {
				//Drillgon200: BlockPos is basically the location class, but without as much abstraction.
				TileEntity entity = world.getTileEntity(pos.offset(dir));
				if(entity == null)
					continue;
			
				if (entity instanceof IEnergyReceiver) {
				
					IEnergyReceiver receiver = (IEnergyReceiver) entity;
					
					int maxExtract = storage.getMaxExtract();
					int maxAvailable = storage.extractEnergy(maxExtract, true);
					int energyTransferred = receiver.receiveEnergy(dir.getOpposite(), maxAvailable, false);

					storage.extractEnergy(energyTransferred, false);
				} else if(entity.hasCapability(CapabilityEnergy.ENERGY, dir.getOpposite())){
					IEnergyStorage receiver = entity.getCapability(CapabilityEnergy.ENERGY, dir.getOpposite());
					
					int maxExtract = storage.getMaxExtract();
					int maxAvailable = storage.extractEnergy(maxExtract, true);
					int energyTransferred = receiver.receiveEnergy(maxAvailable, false);
					
					storage.extractEnergy(energyTransferred, false);
				}
			}
			
			power = storage.getEnergyStored() / 4;
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("rf", storage.getEnergyStored());
			data.setInteger("maxrf", storage.getEnergyStored());
			data.setLong("he", power);
			data.setLong("maxhe", power);
			this.networkPack(data, 25);
		}
		
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		storage.setEnergyStored(nbt.getInteger("rf"));
		storage.setCapacity(nbt.getInteger("maxrf"));
		power = nbt.getLong("he");
		maxPower = nbt.getLong("maxhe");
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
		}
		power = compound.getLong("power");
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
		if(power < 1000000)
			return 500000000;//Long.MAX_VALUE / 100;
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

}
