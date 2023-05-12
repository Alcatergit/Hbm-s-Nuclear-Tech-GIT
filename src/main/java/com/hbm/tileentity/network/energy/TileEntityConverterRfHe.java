package com.hbm.tileentity.network.energy;

import com.hbm.config.GeneralConfig;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyGenerator;
import cofh.redstoneflux.api.IEnergyReceiver;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TileEntityConverterRfHe extends TileEntityLoadedBase implements IEnergyGenerator, IEnergyReceiver {

	private long subBuffer;
	private boolean recursionBrake = false;

	//NTM HE
	@Override
	public void setPower(long power) {
		subBuffer = power;
	}

	@Override
	public long getPower() {
		return subBuffer;
	}

	@Override
	public long getMaxPower() {
		return subBuffer;
	}

	//RF
	@Override
	public int getEnergyStored(EnumFacing from) {
		return 0;
	}

	@Override

	public int getMaxEnergyStored(EnumFacing from) {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		if(recursionBrake)
			return 0;
		
		if(simulate)
			return 0;
		
		recursionBrake = true;
		
		long capacity = maxReceive / GeneralConfig.rfConversionRate;
		subBuffer = capacity;
		
		this.sendPower(world, pos);
		
		recursionBrake = false;
		
		return (int) ((capacity - subBuffer) * GeneralConfig.rfConversionRate);
	}
}
