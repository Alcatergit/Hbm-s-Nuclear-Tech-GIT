package com.hbm.tileentity.bomb;

import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityNukeMan extends TileEntity {

	public ItemStackHandler inventory = new ItemStackHandler(6) {
		protected void onContentsChanged(int slot) {
			super.onContentsChanged(slot);
			markDirty();
		};
	};
	private String customName;
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagCompound tag = inventory.serializeNBT();
		compound.setTag("inventory", tag);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT((NBTTagCompound) compound.getTag("inventory"));
		super.readFromNBT(compound);
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.nukeMan";
	}

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
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <=64;
		}
	}
	
	public boolean isReady() {
		if(this.exp1() && this.exp2() && this.exp3() && this.exp4())
		{
			if(this.inventory.getStackInSlot(0) != ItemStack.EMPTY && this.inventory.getStackInSlot(5) != ItemStack.EMPTY && this.inventory.getStackInSlot(0).getItem() == ModItems.man_igniter && this.inventory.getStackInSlot(5).getItem() == ModItems.man_core)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean exp1() {
		if(this.inventory.getStackInSlot(1) != ItemStack.EMPTY && this.inventory.getStackInSlot(1).getItem() == ModItems.gadget_explosive8)
		{
			return true;
		}
		
		return false;
	}
	
	public boolean exp2() {
		if(this.inventory.getStackInSlot(2) != ItemStack.EMPTY && this.inventory.getStackInSlot(2).getItem() == ModItems.gadget_explosive8)
		{
			return true;
		}
		
		return false;
	}
	
	public boolean exp3() {
		if(this.inventory.getStackInSlot(3) != ItemStack.EMPTY && this.inventory.getStackInSlot(3).getItem() == ModItems.gadget_explosive8)
		{
			return true;
		}
		
		return false;
	}
	
	public boolean exp4() {
		if(this.inventory.getStackInSlot(4) != ItemStack.EMPTY && this.inventory.getStackInSlot(4).getItem() == ModItems.gadget_explosive8)
		{
			return true;
		}
		
		return false;
	}
	
	public void clearSlots() {
		for(int i = 0; i < inventory.getSlots(); i++)
		{
			inventory.setStackInSlot(i, ItemStack.EMPTY);
		}
	}
	
	@Override
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? true : super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) : super.getCapability(capability, facing);
	}
}
