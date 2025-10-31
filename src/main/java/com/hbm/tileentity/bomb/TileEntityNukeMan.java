package com.hbm.tileentity.bomb;

import com.hbm.items.ModItems;
import com.hbm.items.ModItems.Materials.Nuggies;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
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
		if (world.isRemote) {
			if (inventory.getStackInSlot(0).getItem() != ModItems.man_igniter) return false;
			for (int i = 1; i <= 4; i++) if (inventory.getStackInSlot(i).getItem() != ModItems.gadget_explosive8) return false;
			return inventory.getStackInSlot(5).getItem() == ModItems.man_core;
		}
		if (inventory.getStackInSlot(0).getItem() == ModItems.man_igniter) {
			world.playSound(null,pos,SoundEvents.BLOCK_TRIPWIRE_ATTACH,SoundCategory.BLOCKS,0.5f,1.5f);
			int amt = 9;
			for (int i = 1; i <= 4; i++) {
				if (inventory.getStackInSlot(i).getItem() == ModItems.gadget_explosive8) {
					amt--;
					inventory.setStackInSlot(i,ItemStack.EMPTY);
				}
			}
			if (amt < 9) {
				inventory.setStackInSlot(0,new ItemStack(ModItems.scrap));
				world.playSound(null,pos,SoundEvents.ENTITY_GENERIC_EXPLODE,SoundCategory.BLOCKS,0.3f + (7 - amt) * 0.2f,(float) Math.pow(amt / 7f,0.5f));
				if (inventory.getStackInSlot(5).getItem() == ModItems.man_core) {
					if (amt <= 5)
						return true;
					else
						inventory.setStackInSlot(5,new ItemStack(Nuggies.nugget_pu239,amt));
				}
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
