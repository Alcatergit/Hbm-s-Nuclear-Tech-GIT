package com.hbm.tileentity.machine;

import com.hbm.items.tool.ItemKeyPin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMachineKeyForge extends TileEntity implements ITickable {

	public ItemStackHandler inventory;
	
	//private static final int[] slots_top = new int[] {0};
	//private static final int[] slots_bottom = new int[] {1};
	//private static final int[] slots_side = new int[] {2};
	
	private String customName;
	
	public TileEntityMachineKeyForge() {
		inventory = new ItemStackHandler(3){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
		};
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.keyForge";
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

	@Override
	public void update() {
		if(!world.isRemote)
		{
			if(inventory.getStackInSlot(0).getItem() instanceof ItemKeyPin && inventory.getStackInSlot(1).getItem() instanceof ItemKeyPin && 
					((ItemKeyPin)inventory.getStackInSlot(0).getItem()).canTransfer() && ((ItemKeyPin)inventory.getStackInSlot(1).getItem()).canTransfer()) {
				
				ItemKeyPin.setPins(inventory.getStackInSlot(1), ItemKeyPin.getPins(inventory.getStackInSlot(0)));
			}
			
			if(inventory.getStackInSlot(2).getItem() instanceof ItemKeyPin && ((ItemKeyPin)inventory.getStackInSlot(2).getItem()).canTransfer()) {
				ItemKeyPin.setPins(inventory.getStackInSlot(2), world.rand.nextInt(900) + 100);
			}

			//DEBUG, remove later
			//Drillgon200: Later is now.
			/*if(slots[2] != null && slots[2].getItem() == Items.wheat_seeds) {
				slots[2] = new ItemStack(ModItems.nuke_starter_kit);
			}
			if(slots[2] != null && slots[2].getItem() == Items.bone) {
				slots[2] = new ItemStack(ModItems.nuke_advanced_kit);
			}
			if(slots[2] != null && slots[2].getItem() == Items.feather) {
				slots[2] = new ItemStack(ModItems.nuke_commercially_kit);
			}
			if(slots[2] != null && slots[2].getItem() == Items.apple) {
				slots[2] = new ItemStack(ModItems.nuke_electric_kit);
			}
			if(slots[2] != null && slots[2].getItem() == Items.clay_ball) {
				slots[2] = new ItemStack(ModItems.t45_kit);
			}
			if(slots[2] != null && slots[2].getItem() == Items.stick) {
				slots[2] = new ItemStack(ModItems.missile_kit);
			}
			if(slots[2] != null && slots[2].getItem() == Items.string) {
				slots[2] = new ItemStack(ModItems.grenade_kit);
			}
			if(slots[2] != null && slots[2].getItem() == Items.reeds) {
				slots[2] = new ItemStack(ModItems.man_kit);
			}
			if(slots[2] != null && slots[2].getItem() == ModItems.battery_generic) {
				slots[2] = new ItemStack(ModItems.memory);
			}*/
		}
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
