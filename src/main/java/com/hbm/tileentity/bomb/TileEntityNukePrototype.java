package com.hbm.tileentity.bomb;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemCell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityNukePrototype extends TileEntity {

	public ItemStackHandler inventory;
	private String customName;
	
	public TileEntityNukePrototype() {
		inventory = new ItemStackHandler(14){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
		};
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.nukePrototype";
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
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	public boolean isReady() {
		
			if(ItemCell.isFullCell(inventory.getStackInSlot(0), ModForgeFluids.sas3) && 
			ItemCell.isFullCell(inventory.getStackInSlot(1), ModForgeFluids.sas3) && 
			inventory.getStackInSlot(2).getItem() == ModItems.rod_quad_uranium && 
			inventory.getStackInSlot(3).getItem() == ModItems.rod_quad_uranium && 
			inventory.getStackInSlot(4).getItem() == ModItems.rod_quad_lead && 
			inventory.getStackInSlot(5).getItem() == ModItems.rod_quad_lead && 
			inventory.getStackInSlot(6).getItem() == ModItems.rod_quad_neptunium && 
			inventory.getStackInSlot(7).getItem() == ModItems.rod_quad_neptunium && 
			inventory.getStackInSlot(8).getItem() == ModItems.rod_quad_lead && 
			inventory.getStackInSlot(9).getItem() == ModItems.rod_quad_lead && 
			inventory.getStackInSlot(10).getItem() == ModItems.rod_quad_uranium && 
			inventory.getStackInSlot(11).getItem() == ModItems.rod_quad_uranium && 
			ItemCell.isFullCell(inventory.getStackInSlot(12), ModForgeFluids.sas3) && 
			ItemCell.isFullCell(inventory.getStackInSlot(13), ModForgeFluids.sas3))
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
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) :super.getCapability(capability, facing);
	}
}
