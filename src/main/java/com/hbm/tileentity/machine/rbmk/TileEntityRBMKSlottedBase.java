package com.hbm.tileentity.machine.rbmk;

import com.hbm.lib.ItemStackHandlerWrapper;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public abstract class TileEntityRBMKSlottedBase extends TileEntityRBMKActiveBase {

	public ItemStackHandler inventory;

	public TileEntityRBMKSlottedBase(int scount) {
		inventory = new ItemStackHandler(scount){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
			
			@Override
			public boolean isItemValid(int slot, ItemStack itemStack) {
				return isItemValidForSlot(slot, itemStack);
			}

			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
				if(canInsertItem(slot, stack, stack.getCount()))
					return super.insertItem(slot, stack, simulate);
				return stack;
			}

			@Override
			public ItemStack extractItem(int slot, int amount, boolean simulate) {
				if(canExtractItem(slot, inventory.getStackInSlot(slot), amount))
					return super.extractItem(slot, amount, simulate);
				return ItemStack.EMPTY;
			}
		};
	}

	public int getGaugeScaled(int i, FluidTank tank) {
		return tank.getFluidAmount() * i / tank.getCapacity();
	}

	public void networkPack(NBTTagCompound nbt, int range) {
		if(!world.isRemote)
			PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(nbt, pos), new TargetPoint(this.world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), range));
	}

	public void networkUnpack(NBTTagCompound nbt) {
		super.networkUnpack(nbt);
	}

	public void handleButtonPacket(int value, int meta) {
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if(!diag) {
			inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		if(!diag) {
			nbt.setTag("inventory", inventory.serializeNBT());
		}
		return nbt;
	}
	
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return true;
	}
	
	public boolean canInsertItem(int slot, ItemStack itemStack, int amount) {
		return this.isItemValidForSlot(slot, itemStack);
	}

	public boolean canExtractItem(int slot, ItemStack itemStack, int amount) {
		return true;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) : 
			super.getCapability(capability, facing);
	}
}