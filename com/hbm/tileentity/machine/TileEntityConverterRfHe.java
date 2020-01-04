package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.lib.Library;

import cofh.redstoneflux.api.IEnergyReceiver;
import cofh.redstoneflux.impl.EnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityConverterRfHe extends TileEntity implements ITickable, ISource, IEnergyReceiver, IEnergyStorage {

	//Also converts from forge energy
	public long power;
	public final long maxPower = 1000000;
	public List<IConsumer> list = new ArrayList<IConsumer>();
	public int age = 0;
	public EnergyStorage storage = new EnergyStorage(4000000, 2500000, 2500000);
	
	@Override
	public void update() {
		if (!world.isRemote) {

			for(int i = 0; i < 9; i++)
			if(storage.getEnergyStored() >= 400000 && power + 100000 <= maxPower)
			{
				storage.setEnergyStored(storage.getEnergyStored() - 400000);
				power += 100000;
			}
			for(int i = 0; i < 9; i++)
			if(storage.getEnergyStored() >= 40000 && power + 10000 <= maxPower)
			{
				storage.setEnergyStored(storage.getEnergyStored() - 40000);
				power += 10000;
			}
			for(int i = 0; i < 9; i++)
			if(storage.getEnergyStored() >= 4000 && power + 1000 <= maxPower)
			{
				storage.setEnergyStored(storage.getEnergyStored() - 4000);
				power += 1000;
			}
			for(int i = 0; i < 9; i++)
			if(storage.getEnergyStored() >= 400 && power + 100 <= maxPower)
			{
				storage.setEnergyStored(storage.getEnergyStored() - 400);
				power += 100;
			}
			for(int i = 0; i < 9; i++)
			if(storage.getEnergyStored() >= 40 && power + 10 <= maxPower)
			{
				storage.setEnergyStored(storage.getEnergyStored() - 40);
				power += 10;
			}
			for(int i = 0; i < 10; i++)
			if(storage.getEnergyStored() >= 4 && power + 1 <= maxPower)
			{
				storage.setEnergyStored(storage.getEnergyStored() - 4);
				power += 1;
			}
			detectAndSendChanges();
		}
			
		age++;
		if(age >= 20)
		{
			age = 0;
		}
		
		if(age == 9 || age == 19)
			ffgeuaInit();
		
		
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
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public int getFluxScaled(int i) {
		return (storage.getEnergyStored() * i) / storage.getMaxEnergyStored();
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(pos.up(), getTact());
		ffgeua(pos.down(), getTact());
		ffgeua(pos.west(), getTact());
		ffgeua(pos.east(), getTact());
		ffgeua(pos.north(), getTact());
		ffgeua(pos.south(), getTact());
		
	}

	@Override
	public void ffgeua(BlockPos pos, boolean newTact) {
		Library.ffgeua(new BlockPos.MutableBlockPos(pos), newTact, this, world);
		
	}

	@Override
	public boolean getTact() {
		if(age >= 0 && age < 10)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public long getSPower() {
		return power;
	}

	@Override
	public void setSPower(long i) {
		this.power = i;
	}

	@Override
	public List<IConsumer> getList() {
		return list;
	}

	@Override
	public void clearList() {
		this.list.clear();
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
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive, simulate);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY ? CapabilityEnergy.ENERGY.cast(this)
				: super.getCapability(capability, facing);
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return 0;
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
		return false;
	}

	@Override
	public boolean canReceive() {
		return true;
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
