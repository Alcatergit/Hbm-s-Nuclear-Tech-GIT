package com.hbm.tileentity.machine;

import com.hbm.blocks.machine.MachineDiFurnaceRTG;
import com.hbm.inventory.DiFurnaceRecipes;
import com.hbm.items.machine.ItemRTGPellet;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.RTGUtil;
import com.leafia.contents.control.fuel.nuclearfuel.LeafiaRodItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class TileEntityDiFurnaceRTG extends TileEntityMachineBase implements ITickable, ICapabilityProvider {

	public int rtgPower;
	public static final int maxRTGPower = 6000;
	public short progress;
	private static final short progressRequired = 2400;
	
	private static final int[] slots_top = new int[] {0, 1};
	private static final int[] slots_bottom = new int[] {2};
	private static final int[] slots_side = new int[] {3, 4, 5, 6, 7, 8};

	private boolean lastTrigger = false;
	
	public TileEntityDiFurnaceRTG() {
		super(10);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.progress = compound.getShort("progress");
		this.rtgPower = compound.getInteger("rtgPower");
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT((NBTTagCompound) compound.getTag("inventory"));
		migrateSlotCount(10);
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setShort("progress", progress);
		compound.setInteger("rtgPower", rtgPower);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void update() {
		if(!world.isRemote)	{
			migrateSlotCount(10);
			rtgPower = Math.min(RTGUtil.updateRTGs(inventory, new int[] {3, 4, 5, 6, 7, 8}), maxRTGPower);
			if (inventory.getStackInSlot(9).getItem() instanceof LeafiaRodItem) {
				ItemStack stack = inventory.getStackInSlot(9);
				LeafiaRodItem rod = (LeafiaRodItem)stack.getItem();
				NBTTagCompound nbt = stack.getTagCompound();
				double fuelHeat = 20;
				if (nbt != null) {
					fuelHeat = nbt.getDouble("heat");
					//if (fuelHeat >= 2000) {
					if (nbt.getInteger("spillage") > 20*5) {
						ItemStack prevStack = null;
						for (int i = 0; i < inventory.getSlots(); i++) {
							prevStack = LeafiaRodItem.comparePriority(inventory.getStackInSlot(i), prevStack);
							inventory.setStackInSlot(i, ItemStack.EMPTY);
						}
						world.setBlockToAir(pos);
						LeafiaRodItem detonate = (LeafiaRodItem)prevStack.getItem();
						detonate.resetDetonate();
						detonate.detonateRadius = 2;
						detonate.detonate(world, pos);
						return;
					}
					//}
				}
				if (canProcess()) {
					rtgPower += (int)Math.floor(Math.pow(fuelHeat/250,0.54)*15);
					rod.HeatFunction(stack,true,rod.getFlux(stack)*2,0,0,0);
					rod.decay(stack,inventory,0);
				} else
					rod.HeatFunction(stack,true,0,0,0,0);
			}
			if (hasPower() && canProcess()) {
				progress += rtgPower;
				if(progress >= progressRequired) {
					processItem();
					progress = 0;
				}
			} else {
				progress = 0;
			}

		
			boolean trigger = isProcessing() || (canProcess() && hasPower());
			if(trigger != lastTrigger)
				MachineDiFurnaceRTG.updateBlockState(trigger, this.world, pos);
			lastTrigger = trigger;

			NBTTagCompound data = new NBTTagCompound();
			data.setShort("progress", progress);
			data.setInteger("rtgPower", rtgPower);
			networkPack(data, 10);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		progress = nbt.getShort("progress");
		rtgPower = nbt.getShort("rtgPower");
	}
	
	@Override
	public String getName(){
		return "container.diFurnaceRTG";
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(EnumFacing e) {
		int i = e.ordinal();
		return i == 0 ? slots_bottom : (i == 1 ? slots_top : slots_side);
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		if(i == 2) {
			return false;
		} else if ((i >= 3) && (i <= 8)) {
			return stack.getItem() instanceof ItemRTGPellet;
		} else if (i == 9)
			return stack.getItem() instanceof LeafiaRodItem;
		return true;
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack itemStack, int amount) {
		if(slot == 0 && isItemValidForSlot(slot, itemStack)) return inventory.getStackInSlot(1).getItem() != itemStack.getItem();
		if(slot == 1 && isItemValidForSlot(slot, itemStack)) return inventory.getStackInSlot(0).getItem() != itemStack.getItem();
		return isItemValidForSlot(slot, itemStack);
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int amount) {
		return true;
	}
	
	public boolean isUsableByPlayer(EntityPlayer player){
		if(world.getTileEntity(pos) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <=64;
		}
	}
	
	public int getDiFurnaceProgressScaled(int i) {
		return (progress * i) / progressRequired;
	}
	
	public int getPowerRemainingScaled(int i) {
		return (rtgPower * i) / maxRTGPower;
	}

	public int getPower() {
		return rtgPower;
	}
	
	public boolean canProcess() {
		if(inventory.getStackInSlot(0) == null || inventory.getStackInSlot(1) == null)
		{
			return false;
		}
		ItemStack itemStack = DiFurnaceRecipes.getFurnaceProcessingResult(inventory.getStackInSlot(0), inventory.getStackInSlot(1));
		if(itemStack == null)
		{	
			return false;
		}
		
		if(inventory.getStackInSlot(2) == ItemStack.EMPTY)
		{
			return true;
		}
		if(inventory.getStackInSlot(2).getItem() != ItemStack.EMPTY.getItem() && !inventory.getStackInSlot(2).isItemEqual(itemStack)) {
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
			ItemStack itemStack = DiFurnaceRecipes.getFurnaceProcessingResult(inventory.getStackInSlot(0), inventory.getStackInSlot(1));
			
			if(inventory.getStackInSlot(2).isEmpty())
			{
				inventory.setStackInSlot(2, itemStack.copy());
			}else if(inventory.getStackInSlot(2).isItemEqual(itemStack)) {
				inventory.getStackInSlot(2).grow(itemStack.getCount());
			}
			
			for(int i = 0; i < 2; i++)
			{
				if(inventory.getStackInSlot(i).getCount() <= 0)
				{
					inventory.setStackInSlot(i, new ItemStack(inventory.getStackInSlot(i).getItem().setFull3D()));
				}else{
					inventory.getStackInSlot(i).shrink(1);
				}
				if(inventory.getStackInSlot(i).getCount() <= 0)
				{
					inventory.setStackInSlot(i, ItemStack.EMPTY);
				}
			}
			this.markDirty();
		}
	}
	
	public boolean hasPower() {
		return rtgPower > 0;
	}
	
	public boolean isProcessing() {
		return this.progress > 0;
	}
}