package com.hbm.forgefluid;

import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class HbmFluidHandlerItemStackInf implements IFluidHandlerItem, ICapabilityProvider {

	public static final String FLUID_NBT_KEY = "HbmFluidKey";

	private ItemStack container;
	private int maxDrainAmount;
	
	public HbmFluidHandlerItemStackInf(ItemStack stack, int maxDrain){
		container = stack;
		this.maxDrainAmount = maxDrain;
	}
	
	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{new FluidTankProperties((container.getItem() == ModItems.inf_water || container.getItem() == ModItems.inf_water_mk2 || container.getItem() == ModItems.inf_water_mk3 || container.getItem() == ModItems.inf_water_mk4) ? new FluidStack(FluidRegistry.WATER, maxDrainAmount) : null, maxDrainAmount)};
	}
	
	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(resource != null)
			return resource.amount;
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(container.getItem() == ModItems.inf_water || container.getItem() == ModItems.inf_water_mk2 || container.getItem() == ModItems.inf_water_mk3 || container.getItem() == ModItems.inf_water_mk4)
			return new FluidStack(FluidRegistry.WATER, maxDrainAmount);
		if(resource == null)
			return null;
		return new FluidStack(resource.getFluid(), maxDrainAmount);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if(container.getItem() == ModItems.inf_water || container.getItem() == ModItems.inf_water_mk2 || container.getItem() == ModItems.inf_water_mk3 || container.getItem() == ModItems.inf_water_mk4)
			return new FluidStack(FluidRegistry.WATER, maxDrainAmount);
		return null;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY ? (T)this : null;
	}

	@Override
	public ItemStack getContainer() {
		return container;
	}
	

}
