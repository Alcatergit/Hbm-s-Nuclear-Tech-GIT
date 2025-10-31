package com.leafia.contents.machines.manfacturing.wish;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyUser;
import com.hbm.blocks.machine.MachineElectricFurnace;
import com.hbm.inventory.WishRecipes;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.TileEntityMachineBase;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.container_utility.LeafiaPacketReceiver;
import com.llib.group.LeafiaSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.util.List;

public class WishTE extends TileEntityMachineBase implements ITickable, LeafiaPacketReceiver {

	public int dualCookTime;
	public static final int processingSpeed = 200;

	private static final int[] slots_top = new int[] {1};
	private static final int[] slots_bottom = new int[] {2, 0};
	private static final int[] slots_side = new int[] {0};

	public WishTE() {
		super(3);
	}
	
	@Override
	public String getName() {
		return "tile.wish_crucible.name";
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
		this.dualCookTime = compound.getInteger("cookTime");
		this.burn = compound.getInteger("burn");
		this.burnMax = compound.getInteger("burnMax");
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("cookTime", dualCookTime);
		compound.setInteger("burn", burn);
		compound.setInteger("burnMax", burnMax);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(EnumFacing e) {
		int i = e.ordinal();
		return i == 0 ? slots_bottom : (i == 1 ? slots_top : slots_side);
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		if(i == 0)
			return (TileEntityFurnace.getItemBurnTime(stack) > 0);
		
		if(i == 1)
			return true;
		
		return true;
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack itemStack, int amount) {
		return isItemValidForSlot(slot, itemStack);
	}
	
	@Override
	public boolean canExtractItemHopper(int slot, ItemStack itemStack, int amount) {
		if(slot == 0)
			if (itemStack.getItem() instanceof IBatteryItem && ((IBatteryItem)itemStack.getItem()).getCharge(itemStack) == 0)
				return true;
		if(slot == 2)
			return true;
		
		return false;
	}
	
	public int getDiFurnaceProgressScaled(int i) {
		return (dualCookTime * i) / processingSpeed;
	}

	int burn = 0;
	int burnMax = 1;
	public int getBurnScaled(int i) {
		return (burn*i)/Math.max(1,burnMax);
	}
	
	public boolean isProcessing() {
		return this.dualCookTime > 0;
	}
	
	public boolean canProcess() {
		if(inventory.getStackInSlot(1).isEmpty())
		{
			return false;
		}
        ItemStack itemStack = WishRecipes.getFurnaceProcessingResult(inventory.getStackInSlot(1));
        
		if(itemStack == null || itemStack.isEmpty())
		{
			return false;
		}
		
		if(inventory.getStackInSlot(2).isEmpty())
		{
			return true;
		}
		
		if(!inventory.getStackInSlot(2).isItemEqual(itemStack)) {
			return false;
		}
		
		if(inventory.getStackInSlot(2).getCount() < inventory.getSlotLimit(2) && inventory.getStackInSlot(2).getCount() < inventory.getStackInSlot(2).getMaxStackSize()) {
			return true;
		}else{
			return inventory.getStackInSlot(2).getCount() < itemStack.getMaxStackSize();
		}
	}
	
	private void processItem() {
		if(canProcess()) {
	        ItemStack itemStack = WishRecipes.getFurnaceProcessingResult(inventory.getStackInSlot(1));
			
			if(inventory.getStackInSlot(2).isEmpty())
			{
				inventory.setStackInSlot(2, itemStack.copy());
			}else if(inventory.getStackInSlot(2).isItemEqual(itemStack)) {
				inventory.getStackInSlot(2).grow(itemStack.getCount());
			}
			
			for(int i = 1; i < 2; i++)
			{
				if(inventory.getStackInSlot(2).isEmpty())
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
	
	@Override
	public void update() {
		if(!world.isRemote)
		{
			if(canProcess())
			{
				int burnTime = TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(0));
				if (burnTime > 0 && burn <= 0) {
					inventory.getStackInSlot(0).shrink(1);
					burnMax = burnTime;
					burn = burnTime;
				}
				if (burn > 0) {
					dualCookTime++;

					if (this.dualCookTime == WishTE.processingSpeed) {
						this.dualCookTime = 0;
						this.processItem();
					}
				} else
					dualCookTime = 0;
			}else{
				dualCookTime = 0;
			}
			if (burn > 0)
				burn--;
			startPacket().__sendToListeners();
		}
	}

	public LeafiaPacket startPacket() {
		return LeafiaPacket._start(this)
				.__write(0,burn).__write(1,burnMax)
				.__write(2,dualCookTime);//.__write(3,processingSpeed);
	}

	@Override
	public String getPacketIdentifier() {
		return "wish";
	}

	@Override
	public void onReceivePacketLocal(byte key,Object value) {
		switch(key) {
			case 0: burn = (int)value; break;
			case 1: burnMax = (int)value; break;
			case 2: dualCookTime = (int)value; break;
			//case 3: processingSpeed = (int)value; break;
		}
	}

	@Override
	public void onReceivePacketServer(byte key,Object value,EntityPlayer plr) { }

	public LeafiaSet<EntityPlayer> listeners = new LeafiaSet<>();
	@Override
	public List<EntityPlayer> getListeners() {
		return listeners;
	}

	@Override
	public void onPlayerValidate(EntityPlayer plr) { }
}
