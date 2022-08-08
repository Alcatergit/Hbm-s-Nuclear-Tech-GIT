package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.config.GeneralConfig;

import cofh.redstoneflux.api.IEnergyReceiver;
import cofh.redstoneflux.impl.EnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityConverterRfHe extends TileEntityMachineBase implements ITickable, ISource, IEnergyReceiver, IEnergyStorage {

	//Also converts from forge energy
	public long power;
	public long maxPower = 500000000;
	public List<IConsumer> list = new ArrayList<IConsumer>();
	public boolean tact;
	public EnergyStorage storage = new EnergyStorage(2000000000, 2000000000, 2000000000);
	public int buf;
	
	public TileEntityConverterRfHe() {
		super(0);
	}
	
	@Override
	public void update() {
		if (!world.isRemote) {

			power = storage.getEnergyStored() / GeneralConfig.rfConversionRate;
			maxPower = Math.max(1000000, power);
			
			buf = storage.getEnergyStored();

			tact = false;
			ffgeuaInit();
			tact = true;
			ffgeuaInit();
			storage.setEnergyStored((int)power * GeneralConfig.rfConversionRate);
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
	public String getName() {
		return "";
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
		return tact;
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
		if(storage.getEnergyStored() < 4000000)
			return 2000000000;
		return storage.getMaxEnergyStored();
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		storage.setCapacity(2000000000);
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

}
