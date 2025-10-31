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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityNukeGadget extends TileEntity {

	public ItemStackHandler inventory;
	private String customName;
	
	public TileEntityNukeGadget() {
		inventory = new ItemStackHandler(6){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
		};
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.nukeGadget";
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
	
	public boolean exp1() {
		if(inventory.getStackInSlot(1).getItem() == ModItems.gadget_explosive8)
		{
			return true;
		}
		
		return false;
	}
	
	public boolean exp2() {
		if(inventory.getStackInSlot(2).getItem() == ModItems.gadget_explosive8)
		{
			return true;
		}
		
		return false;
	}
	
	public boolean exp3() {
		if(inventory.getStackInSlot(3).getItem() == ModItems.gadget_explosive8)
		{
			return true;
		}
		
		return false;
	}
	
	public boolean exp4() {
		if(inventory.getStackInSlot(4).getItem() == ModItems.gadget_explosive8)
		{
			return true;
		}
		
		return false;
	}
	
	public boolean isReady() {
		if (world.isRemote) {
			if (inventory.getStackInSlot(0).getItem() != ModItems.gadget_wireing) return false;
			for (int i = 1; i <= 4; i++) if (inventory.getStackInSlot(i).getItem() != ModItems.gadget_explosive8) return false;
			return inventory.getStackInSlot(5).getItem() == ModItems.gadget_core;
		}
		if (inventory.getStackInSlot(0).getItem() == ModItems.gadget_wireing) {
			this.world.playSound(null,pos.getX(),pos.getY(),pos.getZ(),SoundEvents.ENTITY_FIREWORK_BLAST,SoundCategory.BLOCKS,0.1F,1.5f);
			int amt = 8;
			for (int i = 1; i <= 4; i++) {
				if (inventory.getStackInSlot(i).getItem() == ModItems.gadget_explosive8) {
					amt--;
					inventory.setStackInSlot(i,ItemStack.EMPTY);
				}
			}
			if (amt < 8) {
				inventory.setStackInSlot(0,new ItemStack(ModItems.scrap));
				world.playSound(null,pos,SoundEvents.ENTITY_GENERIC_EXPLODE,SoundCategory.BLOCKS,0.3f + (7 - amt) * 0.2f,(float) Math.pow(amt / 7f,0.5f));
				if (inventory.getStackInSlot(5).getItem() == ModItems.gadget_core) {
					if (amt <= 4)
						return true;
					else
						inventory.setStackInSlot(5,new ItemStack(Nuggies.nugget_pu239,amt));
				}
			}
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
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) : super.getCapability(capability, facing);
	}
}
