package com.hbm.tileentity.machine;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileEntityDummyFluidPort extends TileEntityDummy {

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(this.world.getTileEntity(target) == null)
			return false;
		return world.getTileEntity(target).hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(this.world.getTileEntity(target) == null)
			return null;
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? this.world.getTileEntity(this.target).getCapability(capability, facing) : null;
	}
}
