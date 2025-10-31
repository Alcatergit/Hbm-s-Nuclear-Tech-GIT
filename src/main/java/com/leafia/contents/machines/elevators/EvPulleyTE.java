package com.leafia.contents.machines.elevators;

import api.hbm.energy.IEnergyUser;
import net.minecraft.tileentity.TileEntity;

public class EvPulleyTE extends TileEntity implements IEnergyUser {
	@Override
	public void setPower(long power) {

	}

	@Override
	public long getPower() {
		return 0;
	}

	@Override
	public long getMaxPower() {
		return 0;
	}

	@Override
	public boolean isLoaded() {
		return false;
	}
}
