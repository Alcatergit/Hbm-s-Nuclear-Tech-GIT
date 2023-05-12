package com.hbm.tileentity;

import api.hbm.energy.IEnergyUser;

import net.minecraft.tileentity.TileEntity;

//can be used as a soruce too since the core TE handles that anyway
public class TileEntityProxyEnergy extends TileEntityProxyBase implements IEnergyUser {

	@Override
	public void setPower(long i) {

		TileEntity te = getTE();

		if(te instanceof IEnergyUser) {
			((IEnergyUser) te).setPower(i);
		}
	}

	@Override
	public long getPower() {

		TileEntity te = getTE();

		if(te instanceof IEnergyUser) {
			return ((IEnergyUser) te).getPower();
		}

		return 0;
	}

	@Override
	public long getMaxPower() {

		TileEntity te = getTE();

		if(te instanceof IEnergyUser) {
			return ((IEnergyUser) te).getMaxPower();
		}

		return 0;
	}
}