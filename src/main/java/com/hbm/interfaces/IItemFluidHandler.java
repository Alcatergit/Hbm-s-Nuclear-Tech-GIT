package com.hbm.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IItemFluidHandler {

	int fill(ItemStack stack, FluidStack fluid, boolean doFill);
	
	FluidStack drain(ItemStack stack, FluidStack resource, boolean doDrain);
	
	FluidStack drain(ItemStack stack, int maxDrain, boolean doDrain);
}
