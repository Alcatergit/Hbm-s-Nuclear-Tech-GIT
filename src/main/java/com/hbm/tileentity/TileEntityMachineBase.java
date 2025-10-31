package com.hbm.tileentity;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.Spaghetti;
import com.hbm.lib.ItemStackHandlerWrapper;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;
import com.llib.exceptions.messages.TextWarningLeafia;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Spaghetti("Not spaghetti in itself, but for the love of god please use this base class for all machines")
public abstract class TileEntityMachineBase extends TileEntityLoadedBase implements INBTPacketReceiver {

	public ItemStackHandler inventory;

	private String customName;

	public TileEntityMachineBase(int scount) {
		this(scount, 64);
	}

	public TileEntityMachineBase(int scount, int slotlimit) {
		inventory = getNewInventory(scount, slotlimit);
	}
	protected void migrateSlotCount(int scount) {
		if (inventory.getSlots() != scount) {
			List<ItemStack> stacks = new ArrayList<>();
			int oglength = inventory.getSlots();
			for (int i = 0; i < Math.min(oglength,scount); i++)
				stacks.add(i,inventory.getStackInSlot(i));
			inventory.setSize(scount);
			for (int i = 0; i < scount; i++) {
				if (i < oglength)
					inventory.setStackInSlot(i,stacks.get(i));
				else
					inventory.setStackInSlot(i,ItemStack.EMPTY);
			}
		}
	}
	public void slotContentsChanged(int slot,ItemStack newStack) { }
	public ItemStackHandler getNewInventory(int scount, int slotlimit){
		return new ItemStackHandler(scount){
			@Override
			protected void onContentsChanged(int slot) {
				super.onContentsChanged(slot);
				slotContentsChanged(slot,this.getStackInSlot(slot));
				markDirty();
			}
			
			@Override
			public int getSlotLimit(int slot) {
				return slotlimit;
			}

			@Override
			public boolean isItemValid(int i, ItemStack itemStack) {
				return isItemValidForSlot(i,itemStack);
			}
			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
				if(canInsertItem(slot, stack, stack.getCount()))
					return super.insertItem(slot, stack, simulate);
				return stack;
			}
			@Override
			public ItemStack extractItem(int slot, int amount, boolean simulate) {
				validateSlotIndex(slot);
				ItemStack stack = stacks.get(slot);
				if (stack.isEmpty()) return ItemStack.EMPTY;
				return canExtractItem(slot,stack,amount) ? super.extractItem(slot,amount,simulate) : ItemStack.EMPTY;
			}
		};
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
	
	public int[] getAccessibleSlotsFromSide(EnumFacing e) {
		return new int[] {};
	}
	
	public int getGaugeScaled(int i, FluidTank tank) {
		return tank.getFluidAmount() * i / tank.getCapacity();
	}

	@Deprecated
	@Spaghetti("For f*ck's sake we are tired of NBTPacket, fucking don't use it for new work or i'll cut your face down")
	public void networkPack(NBTTagCompound nbt, int range) {

		if(!world.isRemote)
			PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(nbt, pos), new TargetPoint(this.world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), range));
	}

	@Deprecated
	@Spaghetti("For f*ck's sake we are tired of NBTPacket, fucking don't use it for new work or i'll cut your face down")
	public void networkUnpack(NBTTagCompound nbt) { }
	
	public void handleButtonPacket(int value, int meta) { }
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("inventory")) {
			NBTTagCompound nbt = compound.getCompoundTag("inventory");
			if (nbt.getInteger("Size") == inventory.getSlots())
				inventory.deserializeNBT(nbt);
			else {
				System.out.println("WARNING: Invalid machine storage size! Resetting storage for block at "+pos.getX()+","+pos.getY()+","+pos.getZ());
				for (EntityPlayer plr : world.playerEntities)
					plr.sendMessage(new TextWarningLeafia("Invalid machine storage size! Resetting storage.. ("+pos.getX()+","+pos.getY()+","+pos.getZ()+")"));
			}
		}
		super.readFromNBT(compound);
	}
	
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return true;
	}
	public boolean isItemValidForSlotHopper(int i, ItemStack stack) {
		return true;
	}
	public boolean canInsertItem(int slot, ItemStack itemStack, int amount) {
		return this.isItemValidForSlot(slot, itemStack);
	}
	public boolean canExtractItem(int slot, ItemStack itemStack, int amount) {
		return true;
	}
	public boolean canInsertItemHopper(int slot, ItemStack itemStack, int amount) {
		return this.isItemValidForSlotHopper(slot, itemStack);
	}
	public boolean canExtractItemHopper(int slot, ItemStack itemStack, int amount) {
		return true;
	}
	
	public int countMufflers() {

		int count = 0;

		for(EnumFacing dir : EnumFacing.VALUES)
			if(world.getBlockState(pos.offset(dir)).getBlock() == ModBlocks.muffler)
				count++;

		return count;
	}

	public float getVolume(int toSilence) {

		float volume = 1 - (countMufflers() / (float)toSilence);

		return Math.max(volume, 0);
	}

	public EnumFacing getFacingDummyable() { // for BlockDummyables
		int meta = world.getBlockState(pos).getValue(BlockDummyable.META);
		return switch(meta) { // matches opposite IDs
			case 13 -> EnumFacing.NORTH;
			case 14 -> EnumFacing.EAST;
			case 12 -> EnumFacing.SOUTH;
			case 15 -> EnumFacing.WEST;
			default -> null;
		};
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && inventory != null){
			if(facing == null)
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(new ItemStackHandlerWrapper(inventory, getAccessibleSlotsFromSide(facing)){
				@Override
				public ItemStack extractItem(int slot, int amount, boolean simulate) {
					if(canExtractItemHopper(slot, inventory.getStackInSlot(slot), amount) && canExtractItem(slot, inventory.getStackInSlot(slot), amount))
						return super.extractItem(slot, amount, simulate);
					return ItemStack.EMPTY;
				}
				
				@Override
				public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
					if(canInsertItemHopper(slot, stack, stack.getCount()) && canInsertItem(slot, stack, stack.getCount()))
						return super.insertItem(slot, stack, simulate);
					return stack;
				}
			});
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && inventory != null) || super.hasCapability(capability, facing);
	}
}
