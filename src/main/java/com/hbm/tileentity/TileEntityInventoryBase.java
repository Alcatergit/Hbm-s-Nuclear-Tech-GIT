package com.hbm.tileentity;

import com.hbm.lib.ItemStackHandlerWrapper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public abstract class TileEntityInventoryBase extends TileEntity {

	public ItemStackHandler inventory;
	
	private String customName;
	
	public TileEntityInventoryBase(int scount) {
		inventory = new ItemStackHandler(scount);
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : getName();
	}
	
	public abstract String getName();

	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String name) {
		this.customName = name;
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(world.getTileEntity(pos) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <=128;
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	
	public int[] getAccessibleSlotsFromSide(EnumFacing p_94128_1_) {
		return new int[] { 0 };
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && inventory != null){
			if(facing == null)
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(new ItemStackHandlerWrapper(inventory, getAccessibleSlotsFromSide(facing)));
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && inventory != null) || super.hasCapability(capability, facing);
	}
}
