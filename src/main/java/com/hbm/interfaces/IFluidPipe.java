package com.hbm.interfaces;

import com.hbm.forgefluid.FFPipeNetwork;

import net.minecraftforge.fluids.Fluid;

public interface IFluidPipe {

	
	public FFPipeNetwork getNetwork();
	public FFPipeNetwork getNetworkTrue();
	public void setNetwork(FFPipeNetwork net);
	public Fluid getType();
	public void setType(Fluid fluid);
	public boolean getIsValidForForming();
	public void breakBlock();
	void setTypeTrue(Fluid fluid);
}
