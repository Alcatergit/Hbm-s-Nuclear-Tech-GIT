package com.hbm.tileentity.machine;

import java.util.Random;

import com.hbm.interfaces.IConsumer;
import com.hbm.inventory.MachineRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMachineSchrabidiumTransmutator extends TileEntity implements ITickable, IConsumer {

	public ItemStackHandler inventory;

	public long power = 0;
	public int process = 0;
	public int soundCycle = 0;
	public static final long maxPower = 5000000;
	public static final int processSpeed = 60;
	Random rand = new Random();
	
	//private static final int[] slots_top = new int[] { 0 };
	//private static final int[] slots_bottom = new int[] { 1, 2 };
	//private static final int[] slots_side = new int[] { 3, 2 };

	private String customName;
	
	public TileEntityMachineSchrabidiumTransmutator() {
		inventory = new ItemStackHandler(4){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
			@Override
			public boolean isItemValid(int slot, ItemStack stack) {
				switch (slot) {
				case 0:
					if (MachineRecipes.mODE(stack, "ingotUranium"))
						return true;
					break;
				case 2:
					if (stack.getItem() == ModItems.redcoil_capacitor)
						return true;
					break;
				case 3:
					if (stack.getItem() instanceof ItemBattery || stack.getItem() == ModItems.battery_creative)
						return true;
					break;
				}
				return false;
			}
			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
				if(isItemValid(slot, stack))
					return super.insertItem(slot, stack, simulate);
				else
					return ItemStack.EMPTY;
			}
		};
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.machine_schrabidium_transmutator";
	}

	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}

	public void setCustomName(String name) {
		this.customName = name;
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (world.getTileEntity(pos) != this) {
			return false;
		} else {
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		power = compound.getLong("power");
		process = compound.getShort("process");
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("power", power);
		compound.setShort("process", (short) process);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void update() {
		if (!world.isRemote) {
			
			power = Library.chargeTEFromItems(inventory, 3, power, maxPower);

			if (canProcess()) {
				process();
			} else {
				process = 0;
			}

			detectAndSendChanges();
		}
	}
	
	private long detectPower;
	
	private void detectAndSendChanges(){
		boolean mark = false;
		if(detectPower != power){
			mark = true;
			detectPower = power;
		}
		PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos.getX(), pos.getY(), pos.getZ(), power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
		if(mark)
			markDirty();
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}

	public int getProgressScaled(int i) {
		return (process * i) / processSpeed;
	}

	public boolean canProcess() {
		if (power >= 4990000 && MachineRecipes.mODE(inventory.getStackInSlot(0), "ingotUranium")
				&& inventory.getStackInSlot(2).getItem() == ModItems.redcoil_capacitor
				&& inventory.getStackInSlot(2).getItemDamage() < inventory.getStackInSlot(2).getMaxDamage()
				&& (inventory.getStackInSlot(1).isEmpty() || (inventory.getStackInSlot(1).getItem() == ModItems.ingot_schrabidium
						&& inventory.getStackInSlot(1).getCount() < inventory.getStackInSlot(1).getMaxStackSize()))) {
			return true;
		}
		return false;
	}

	public boolean isProcessing() {
		return process > 0;
	}

	public void process() {
		process++;

		if (isProcessing()) {
			if (soundCycle == 0)
				this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_MINECART_RIDING, SoundCategory.BLOCKS, 1.0F, 1.0F);
			soundCycle++;

			if (soundCycle >= 38)
				soundCycle = 0;
		}

		if (process >= processSpeed) {

			power = 0;
			process = 0;

			inventory.getStackInSlot(0).shrink(1);
			if (inventory.getStackInSlot(0).isEmpty()) {
				inventory.setStackInSlot(0, ItemStack.EMPTY);
			}

			if (inventory.getStackInSlot(1).isEmpty()) {
				inventory.setStackInSlot(1, new ItemStack(ModItems.ingot_schrabidium));
			} else {
				inventory.getStackInSlot(1).grow(1);
			}
			if (!inventory.getStackInSlot(2).isEmpty()) {
				inventory.getStackInSlot(2).setItemDamage(inventory.getStackInSlot(2).getItemDamage() + 1);
			}

			this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.BLOCKS, 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
		}
	}
	
	@Override
	public void setPower(long i) {
		power = i;

	}

	@Override
	public long getPower() {
		return power;

	}

	@Override
	public long getMaxPower() {
		return maxPower;
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
