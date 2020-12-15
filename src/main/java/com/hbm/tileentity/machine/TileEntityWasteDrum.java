package com.hbm.tileentity.machine;

import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityWasteDrum extends TileEntity implements ITickable {

	public ItemStackHandler inventory;
	
	//private static final int[] slots_arr = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
	
	public boolean lock = false;
	
	private String customName;
	
	public TileEntityWasteDrum() {
		inventory = new ItemStackHandler(12){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
			
			@Override
			public int getSlotLimit(int slot) {
				return 1;
			}
			
			@Override
			public boolean isItemValid(int slot, ItemStack stack) {
				Item item = stack.getItem();
				
				return item == ModItems.waste_mox_hot || 
						item == ModItems.waste_plutonium_hot || 
						item == ModItems.waste_schrabidium_hot || 
						item == ModItems.waste_thorium_hot || 
						item == ModItems.waste_uranium_hot;
			}
			
			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
				if(this.isItemValid(slot, stack))
					return super.insertItem(slot, stack, simulate);
				else
					return ItemStack.EMPTY;
			}
		};
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.wasteDrum";
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
		if(!world.isRemote) {
			
			int water = 0;

			if(world.getBlockState(pos.east()).getBlock() == Blocks.WATER || world.getBlockState(pos.east()).getBlock() == Blocks.FLOWING_WATER)
				water++;
			if(world.getBlockState(pos.west()).getBlock() == Blocks.WATER || world.getBlockState(pos.west()).getBlock() == Blocks.FLOWING_WATER)
				water++;
			if(world.getBlockState(pos.up()).getBlock() == Blocks.WATER || world.getBlockState(pos.up()).getBlock() == Blocks.FLOWING_WATER)
				water++;
			if(world.getBlockState(pos.down()).getBlock() == Blocks.WATER || world.getBlockState(pos.down()).getBlock() == Blocks.FLOWING_WATER)
				water++;
			if(world.getBlockState(pos.south()).getBlock() == Blocks.WATER || world.getBlockState(pos.south()).getBlock() == Blocks.FLOWING_WATER)
				water++;
			if(world.getBlockState(pos.north()).getBlock() == Blocks.WATER || world.getBlockState(pos.north()).getBlock() == Blocks.FLOWING_WATER)
				water++;
			
			if(water > 0) {
				
				int r = 60 * 60 * 20 / water;
				
				for(int i = 0; i < 12; i++) {
					
					if(world.rand.nextInt(r) == 0) {
						
						if(!inventory.getStackInSlot(i).isEmpty()) {
							
							if(inventory.getStackInSlot(i).getItem() == ModItems.waste_uranium_hot)
								inventory.setStackInSlot(i, new ItemStack(ModItems.waste_uranium));
							else if(inventory.getStackInSlot(i).getItem() == ModItems.waste_plutonium_hot)
								inventory.setStackInSlot(i, new ItemStack(ModItems.waste_plutonium));
							else if(inventory.getStackInSlot(i).getItem() == ModItems.waste_thorium_hot)
								inventory.setStackInSlot(i, new ItemStack(ModItems.waste_thorium));
							else if(inventory.getStackInSlot(i).getItem() == ModItems.waste_mox_hot)
								inventory.setStackInSlot(i, new ItemStack(ModItems.waste_mox));
							else if(inventory.getStackInSlot(i).getItem() == ModItems.waste_schrabidium_hot)
								inventory.setStackInSlot(i, new ItemStack(ModItems.waste_schrabidium));
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
		} else {
			return super.getCapability(capability, facing);
		}
	}

}
