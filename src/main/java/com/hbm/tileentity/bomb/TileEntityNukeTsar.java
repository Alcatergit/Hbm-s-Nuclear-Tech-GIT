package com.hbm.tileentity.bomb;

import com.hbm.items.ModItems;

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

public class TileEntityNukeTsar extends TileEntity {

	public ItemStackHandler inventory;
	private String customName;
	
	public TileEntityNukeTsar() {
		inventory = new ItemStackHandler(9){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
		};
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.nukeTsar";
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

	public boolean isCoreFilled(){
		return inventory.getStackInSlot(0).getItem() == ModItems.man_core;
	}

	public boolean isTopLeftLenseFilled(){
		return inventory.getStackInSlot(1).getItem() == ModItems.man_explosive8;
	}
	public boolean isTopRightLenseFilled(){
		return inventory.getStackInSlot(2).getItem() == ModItems.man_explosive8;
	}
	public boolean isBottomLeftLenseFilled(){
		return inventory.getStackInSlot(3).getItem() == ModItems.man_explosive8;
	}
	public boolean isBottomRightLenseFilled(){
		return inventory.getStackInSlot(4).getItem() == ModItems.man_explosive8;
	}

	public boolean isStage1UFilled(){
		return inventory.getStackInSlot(5).getItem() == ModItems.mike_core;
	}
	public boolean isStage1DFilled(){
		return inventory.getStackInSlot(6).getItem() == ModItems.mike_deut;
	}

	public boolean isStage2UFilled(){
		return inventory.getStackInSlot(7).getItem() == ModItems.mike_core;
	}
	public boolean isStage2DFilled(){
		return inventory.getStackInSlot(8).getItem() == ModItems.mike_deut;
	}

	public boolean isReady() {
		return (isCoreFilled() && isTopLeftLenseFilled() && isTopRightLenseFilled() && isBottomLeftLenseFilled() && isBottomRightLenseFilled());
	}
	
	public boolean isStage1Filled() {
		return (isReady() && isStage1UFilled() && isStage1DFilled());
	}

	public boolean isStage2Filled() {
		return (isReady() && isStage2UFilled() && isStage2DFilled());
	}

	public boolean isStage3Filled() {
		return (isStage1Filled() && isStage2UFilled() && isStage2DFilled());
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
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) : super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
}
