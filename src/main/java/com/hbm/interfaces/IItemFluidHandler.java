package com.hbm.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IItemFluidHandler {

	public int fill(ItemStack stack, FluidStack fluid, boolean doFill);
	
	public FluidStack drain(ItemStack stack, FluidStack resource, boolean doDrain);
	
	public FluidStack drain(ItemStack stack, int maxDrain, boolean doDrain);
}
