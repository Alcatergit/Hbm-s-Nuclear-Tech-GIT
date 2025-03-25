package com.hbm.tileentity.machine;

import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.lib.ItemStackHandlerWrapper;
import com.hbm.lib.ForgeDirection;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMold;
import com.hbm.items.machine.ItemMold.Mold;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Thank god we have a base class now. Now with documentation and as little redundant crap in the child classes as possible.
 * @author hbm
 *
 */
public abstract class TileEntityFoundryCastingBase extends TileEntityFoundryBase {
	
	public ItemStackHandler inventory;
	public int cooloff = 100;


	public TileEntityFoundryCastingBase(int slotCount) {
		inventory = getNewInventory(slotCount);
	}

	public ItemStackHandler getNewInventory(int scount){
		return new ItemStackHandler(scount){
			@Override
			protected void onContentsChanged(int slot) {
				super.onContentsChanged(slot);
				markDirty();
			}
			
			@Override
			public int getSlotLimit(int slot) {
				return 64;
			}
		};
	}
	
	@Override
	public void update() {
		super.update();
		
		if(!world.isRemote) {
			
			if(this.amount > this.getCapacity()) {
				this.amount = this.getCapacity();
			}
			
			if(this.amount == 0) {
				this.type = null;
			}
			
			Mold mold = this.getInstalledMold();
			
			if(mold != null && this.amount == this.getCapacity() && inventory.getStackInSlot(1).isEmpty()) {
				cooloff--;
				
				if(cooloff <= 0) {
					this.amount = 0;
					
					ItemStack out = mold.getOutput(type);
					
					if(out != null && !out.isEmpty()) {
						inventory.setStackInSlot(1, out.copy());
					}
					
					cooloff = 200;
					this.markDirty();
				}
				
			} else {
				cooloff = 200;
			}
		}
	}

	@Override
	protected boolean shouldClientReRender() {
		return false;
	}
	
	/** Checks slot 0 to see what mold type is installed. Returns null if no mold is found or an incorrect size was used. */
	public Mold getInstalledMold() {
		if(inventory.getStackInSlot(0).isEmpty()) return null;
		
		if(inventory.getStackInSlot(0).getItem() == ModItems.mold) {
			Mold mold = ((ItemMold) inventory.getStackInSlot(0).getItem()).getMold(inventory.getStackInSlot(0));
			
			if(mold.size == this.getMoldSize()){
				return mold;
			}
		}
		
		return null;
	}

	/** Returns the amount of quanta this casting block can hold, depending on the installed mold or 0 if no mold is found. */
	@Override
	public int getCapacity() {
		Mold mold = this.getInstalledMold();
		return mold == null ? 0 : mold.getCost();
	}
	
	/**
	 * Standard check for testing if this material stack can be added to the casting block. Checks:<br>
	 * - type matching<br>
	 * - amount being at max<br>
	 * - whether a mold is installed<br>
	 * - whether the mold can accept this type
	 */
	public boolean standardCheck(World world, BlockPos p, ForgeDirection side, MaterialStack stack) {
		if(!super.standardCheck(world, p, side, stack)) return false; //reject if base conditions are not met
		if(!inventory.getStackInSlot(1).isEmpty()) return false; //reject if a freshly casted item is still present
		Mold mold = this.getInstalledMold();
		if(mold == null) return false;
		
		return mold.getOutput(stack.material) != null; //no OD match -> no pouring
	}
	
	/** Returns an integer determining the mold size, 0 for small molds and 1 for the basin */
	public abstract int getMoldSize();


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

	public int[] getAccessibleSlotsFromSide(EnumFacing face) {
		return new int[] { 1 };
	}

	public boolean canInsertItem(int slot, ItemStack stack, int amount) {
		return false;
	}

	public boolean canExtractItem(int slot, ItemStack stack, int amount) {
		return slot == 1;
	}

	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return true;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && inventory != null){
			if(facing == null)
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(new ItemStackHandlerWrapper(inventory, getAccessibleSlotsFromSide(facing)){
				@Override
				public ItemStack extractItem(int slot, int amount, boolean simulate) {
					if(canExtractItem(slot, inventory.getStackInSlot(slot), amount))
						return super.extractItem(slot, amount, simulate);
					return ItemStack.EMPTY;
				}
				
				@Override
				public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
					if(canInsertItem(slot, stack, stack.getCount()))
						return super.insertItem(slot, stack, simulate);
					return stack;
				}
			});
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && inventory != null) || super.hasCapability(capability, facing);
	}
}
