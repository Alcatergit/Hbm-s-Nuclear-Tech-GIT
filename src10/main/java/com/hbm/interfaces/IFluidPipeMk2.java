package com.hbm.interfaces;

import com.hbm.forgefluid.FFPipeNetworkMk2;

import net.minecraftforge.fluids.Fluid;

public interface IFluidPipeMk2 {

	Fluid getType();
	void setType(Fluid fluid);
	FFPipeNetworkMk2 getNetwork();
	void setNetwork(FFPipeNetworkMk2 net);
	void joinOrMakeNetwork();
	boolean isValidForBuilding();
}
