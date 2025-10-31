package com.leafia.dev.proxy;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public interface IFluidHandlerOut extends IFluidHandler {
	int fillForce(FluidStack resource,boolean doFill);
	@Override
	default int fill(FluidStack resource,boolean doFill) {
		return 0;
	}
}
