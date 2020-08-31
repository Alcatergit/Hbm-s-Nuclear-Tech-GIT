package com.hbm.tileentity;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityProxyInventory extends TileEntityProxyBase {

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			ICapabilityProvider te = this.getTE();
			if(te != null)
				return te.getCapability(capability, facing);
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			ICapabilityProvider te = this.getTE();
			if(te != null)
				return te.hasCapability(capability, facing);
		}
		return super.hasCapability(capability, facing);
	}
}
