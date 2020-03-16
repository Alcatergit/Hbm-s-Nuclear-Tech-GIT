package com.hbm.tileentity.machine;

import com.hbm.interfaces.IConsumer;
import com.hbm.inventory.MachineRecipes;
import com.hbm.items.special.ItemBlades;
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

public class TileEntityMachineShredder extends TileEntity implements ITickable, IConsumer {

	public ItemStackHandler inventory;

	public long power;
	public int progress;
	public int soundCycle = 0;
	public static final long maxPower = 10000;
	public static final int processingSpeed = 60;
	
	//private static final int[] slots_top = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
	//private static final int[] slots_bottom = new int[] {9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29};
	//private static final int[] slots_side = new int[] {27, 28, 29};
	
	private String customName;
	
	public TileEntityMachineShredder() {
		inventory = new ItemStackHandler(30){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
			
		};
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.machineShredder";
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
		this.power = compound.getLong("powerTime");
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("powerTime", power);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	public int getDiFurnaceProgressScaled(int i) {
		return (progress * i) / processingSpeed;
	}
	
	public boolean hasPower() {
		return power > 0;
	}
	
	public boolean isProcessing() {
		return this.progress > 0;
	}
	
	@Override
	public void update() {
		boolean flag = this.hasPower();
		boolean flag1 = false;
		
		if(!world.isRemote)
		{			
			if(hasPower() && canProcess())
			{
				progress++;
				
				power -= 5;
				
				if(this.progress == TileEntityMachineShredder.processingSpeed)
				{
					inventory.getStackInSlot(27).setItemDamage(inventory.getStackInSlot(27).getItemDamage() + 1);
					inventory.getStackInSlot(28).setItemDamage(inventory.getStackInSlot(28).getItemDamage() + 1);
					
					this.progress = 0;
					this.processItem();
					flag1 = true;
				}
				if(soundCycle == 0)
		        	this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_MINECART_RIDING, SoundCategory.BLOCKS, 1.0F, 0.75F);
				soundCycle++;
				
				if(soundCycle >= 50)
					soundCycle = 0;
			}else{
				progress = 0;
			}
			
			boolean trigger = true;
			
			if(hasPower() && canProcess() && this.progress == 0)
			{
				trigger = false;
			}
			
			if(trigger)
            {
                flag1 = true;
            }
			
			power = Library.chargeTEFromItems(inventory, 29, power, maxPower);
			
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos.getX(), pos.getY(), pos.getZ(), power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
		}
		
		if(flag1)
		{
			this.markDirty();
		}
	}
	
	public void processItem() {
		for(int i = 0; i < 9; i++)
		{
			if(!inventory.getStackInSlot(i).isEmpty() && hasSpace(inventory.getStackInSlot(i)))
			{
				ItemStack inp = inventory.getStackInSlot(i).copy();
				ItemStack outp = MachineRecipes.getShredderResult(inp);
				boolean flag = false;
				
				for (int j = 9; j < 27; j++)
				{
					if (!inventory.getStackInSlot(j).isEmpty() && inventory.getStackInSlot(j).getItem().equals(outp.getItem()) && inventory.getStackInSlot(j).getCount() + outp.getCount() <= outp.getMaxStackSize()) {
						inventory.getStackInSlot(j).grow(outp.getCount());
						inventory.getStackInSlot(i).shrink(1);
						flag = true;
						break;
					}
				}
				
				if(!flag)
					for (int j = 9; j < 27; j++)
					{
						if (inventory.getStackInSlot(j).isEmpty()) {
							inventory.setStackInSlot(j, outp.copy());
							inventory.getStackInSlot(i).shrink(1);
							break;
						}
					}
				
				if(inventory.getStackInSlot(i).isEmpty())
					inventory.setStackInSlot(i, ItemStack.EMPTY);
			}
		}
	}
	
	public boolean canProcess() {
		if(
				inventory.getStackInSlot(27).getItem() instanceof ItemBlades && inventory.getStackInSlot(28).getItem() instanceof ItemBlades && 
				inventory.getStackInSlot(27).getItemDamage() < inventory.getStackInSlot(27).getMaxDamage() && inventory.getStackInSlot(28).getItemDamage() < inventory.getStackInSlot(28).getMaxDamage())
		for(int i = 0; i < 9; i++)
		{
			if(!inventory.getStackInSlot(i).isEmpty() && inventory.getStackInSlot(i).getCount() > 0 && hasSpace(inventory.getStackInSlot(i)))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasSpace(ItemStack stack) {
		
		ItemStack result = MachineRecipes.getShredderResult(stack);
		
		if (result != null)
			for (int i = 9; i < 27; i++) {
				if (inventory.getStackInSlot(i).isEmpty()) {
					return true;
				}

				if (inventory.getStackInSlot(i).getItem().equals(result.getItem())
						&& inventory.getStackInSlot(i).getCount() + result.getCount() <= result.getMaxStackSize()) {
					return true;
				}
			}
		
		return false;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
		
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return TileEntityMachineShredder.maxPower;
	}
	
	public int getGearLeft() {
		
		if(!inventory.getStackInSlot(27).isEmpty() && inventory.getStackInSlot(27).getItem() instanceof ItemBlades)
		{
			if(inventory.getStackInSlot(27).getItemDamage() < inventory.getStackInSlot(27).getMaxDamage()/2)
			{
				return 1;
			} else if(inventory.getStackInSlot(27).getItemDamage() != inventory.getStackInSlot(27).getMaxDamage()) {
				return 2;
			} else {
				return 3;
			}
		}
		
		return 0;
	}
	
	public int getGearRight() {
		
		if(!inventory.getStackInSlot(28).isEmpty() && inventory.getStackInSlot(28).getItem() instanceof ItemBlades)
		{
			if(inventory.getStackInSlot(28).getItemDamage() < inventory.getStackInSlot(28).getMaxDamage()/2)
			{
				return 1;
			} else if(inventory.getStackInSlot(28).getItemDamage() != inventory.getStackInSlot(28).getMaxDamage()) {
				return 2;
			} else {
				return 3;
			}
		}
		
		return 0;
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
