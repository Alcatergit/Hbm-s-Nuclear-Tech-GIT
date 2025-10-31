package com.leafia.dev.proxy;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

public interface IFluidHandlerIn extends IFluidHandler {
	@Nullable
	FluidStack drainForce(FluidStack resource,boolean doDrain);
	@Nullable
	FluidStack drainForce(int maxDrain,boolean doDrain);
	@Override
	default @Nullable FluidStack drain(int maxDrain,boolean doDrain) {
		return null;
	}
	@Override
	default @Nullable FluidStack drain(FluidStack resource,boolean doDrain) {
		return null;
	}
}
