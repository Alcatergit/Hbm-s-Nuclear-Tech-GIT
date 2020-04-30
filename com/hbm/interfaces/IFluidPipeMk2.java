package com.hbm.interfaces;

import com.hbm.forgefluid.FFPipeNetworkMk2;

import net.minecraftforge.fluids.Fluid;

public interface IFluidPipeMk2 {

	public Fluid getType();
	public void setType(Fluid fluid);
	public FFPipeNetworkMk2 getNetwork();
	public void setNetwork(FFPipeNetworkMk2 net);
	public void joinOrMakeNetwork();
	public boolean isValidForBuilding();
}
