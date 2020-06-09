package com.hbm.tileentity.machine;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileEntityDummyFluidPort extends TileEntityDummy implements IFluidHandler {

	@Override
	public IFluidTankProperties[] getTankProperties() {
		if(target == null)
			return new IFluidTankProperties[]{};
		TileEntity te = world.getTileEntity(target);
		if(te instanceof IFluidHandler){
			return ((IFluidHandler)te).getTankProperties();
		}
		return new IFluidTankProperties[]{};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(target == null)
			return 0;
		TileEntity te = world.getTileEntity(target);
		if(te instanceof IFluidHandler){
			return ((IFluidHandler)te).fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(target == null)
			return null;
		TileEntity te = world.getTileEntity(target);
		if(te instanceof IFluidHandler){
			return ((IFluidHandler)te).drain(resource, doDrain);
		}
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if(target == null)
			return null;
		TileEntity te = world.getTileEntity(target);
		if(te instanceof IFluidHandler){
			return ((IFluidHandler)te).drain(maxDrain, doDrain);
		}
		return null;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return true;
		if(target != null && world.getTileEntity(target) != null){
			return world.getTileEntity(target).hasCapability(capability, facing);
		}
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if((target == null || world.getTileEntity(target) == null) && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		if(target != null && world.getTileEntity(target) != null){
			return world.getTileEntity(target).getCapability(capability, facing);
		}
		return super.getCapability(capability, facing);
	}
}
