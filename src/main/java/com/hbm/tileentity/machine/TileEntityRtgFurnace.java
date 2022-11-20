package com.hbm.tileentity.machine;

import com.hbm.blocks.machine.MachineRtgFurnace;
import com.hbm.items.ModItems;
import com.hbm.util.RTGUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityRtgFurnace extends TileEntity implements ITickable {

	public ItemStackHandler inventory;

	public int dualCookTime;
	public int heat;
	public static final int processingSpeed = 3000;

	// private static final int[] slots_top = new int[] {0};
	// private static final int[] slots_bottom = new int[] {4};
	// private static final int[] slots_side = new int[] {1, 2, 3};

	private String customName;

	public TileEntityRtgFurnace() {
		inventory = new ItemStackHandler(5) {
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
		};
	}

	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.rtgFurnace";
	}

	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}

	public void setCustomName(String name) {
		this.customName = name;
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
		if(world.getTileEntity(pos) != this) {
			return false;
		} else {
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64;
		}
	}

	public boolean isLoaded() {
		return RTGUtil.hasHeat(inventory);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		dualCookTime = compound.getShort("CookTime");
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setShort("cookTime", (short) dualCookTime);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	public int getDiFurnaceProgressScaled(int i) {
		return (dualCookTime * i) / processingSpeed;
	}
	
	public boolean canProcess() {
		if(inventory.getStackInSlot(0).isEmpty())
		{
			return false;
		}
        ItemStack itemStack = FurnaceRecipes.instance().getSmeltingResult(inventory.getStackInSlot(0));
		if(itemStack == null || itemStack.isEmpty())
		{
			return false;
		}
		
		if(inventory.getStackInSlot(4).isEmpty())
		{
			return true;
		}
		
		if(!inventory.getStackInSlot(4).isItemEqual(itemStack)) {
			return false;
		}
		
		if(inventory.getStackInSlot(4).getCount() < inventory.getSlotLimit(4) && inventory.getStackInSlot(4).getCount() < inventory.getStackInSlot(4).getMaxStackSize()) {
			return true;
		}else{
			return inventory.getStackInSlot(4).getCount() < itemStack.getMaxStackSize();
		}
	}
	
	private void processItem() {
		if(canProcess()) {
	        ItemStack itemStack = FurnaceRecipes.instance().getSmeltingResult(inventory.getStackInSlot(0));
			
			if(inventory.getStackInSlot(4).isEmpty())
			{
				inventory.setStackInSlot(4, itemStack.copy());
			}else if(inventory.getStackInSlot(4).isItemEqual(itemStack)) {
				inventory.getStackInSlot(4).grow(itemStack.getCount());
			}
			
			for(int i = 0; i < 1; i++)
			{
				if(inventory.getStackInSlot(i).isEmpty())
				{
					inventory.setStackInSlot(i, new ItemStack(inventory.getStackInSlot(i).getItem()));
				}else{
					inventory.getStackInSlot(i).shrink(1);
				}
				if(inventory.getStackInSlot(i).isEmpty())
				{
					inventory.setStackInSlot(i, ItemStack.EMPTY);
				}
			}
		}
	}
	
	public boolean hasPower() {
		return isLoaded();
	}
	
	public boolean isProcessing() {
		return this.dualCookTime > 0;
	}

	@Override
	public void update() {
		boolean flag1 = false;
		
		if(!world.isRemote)
		{	
			heat = RTGUtil.updateRTGs(inventory);
			if(hasPower() && canProcess())
			{
				dualCookTime+=heat;
				
				if(this.dualCookTime >= TileEntityRtgFurnace.processingSpeed)
				{
					this.dualCookTime = 0;
					this.processItem();
					flag1 = true;
				}
			}else{
				dualCookTime = 0;
			}
			boolean trigger = true;
			
			if(hasPower() && canProcess() && this.dualCookTime == 0)
			{
				trigger = false;
			}
			
			if(trigger)
            {
                flag1 = true;
                MachineRtgFurnace.updateBlockState(this.dualCookTime > 0, this.world, pos);
            }
		}
		
		if(flag1)
		{
			this.markDirty();
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
