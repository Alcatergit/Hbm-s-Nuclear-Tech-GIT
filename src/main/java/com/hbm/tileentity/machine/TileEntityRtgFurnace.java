package com.hbm.tileentity.machine;

import com.hbm.blocks.machine.MachineRtgFurnace;
import com.hbm.items.machine.ItemRTGPellet;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.items.ModItems;
import com.hbm.util.RTGUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityRtgFurnace extends TileEntityMachineBase implements ITickable {

	public int dualCookTime;
	public int heat;
	public static final int processingSpeed = 3000;

	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] {4};
	private static final int[] slots_side = new int[] {1, 2, 3};

	private String customName;

	public TileEntityRtgFurnace() {
		super(5);
	}

	@Override
	public String getName() {
		return this.hasCustomInventoryName() ? this.customName : "container.rtgFurnace";
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
		return RTGUtil.hasHeat(this.inventory);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		dualCookTime = compound.getShort("CookTime");
		if(compound.hasKey("inventory"))
			this.inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setShort("cookTime", (short) dualCookTime);
		compound.setTag("inventory", this.inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	public int getDiFurnaceProgressScaled(int i) {
		return (dualCookTime * i) / processingSpeed;
	}
	
	public boolean canProcess() {
		if(this.inventory.getStackInSlot(0).isEmpty())
		{
			return false;
		}
        ItemStack itemStack = FurnaceRecipes.instance().getSmeltingResult(this.inventory.getStackInSlot(0));
		if(itemStack == null || itemStack.isEmpty())
		{
			return false;
		}
		
		if(this.inventory.getStackInSlot(4).isEmpty())
		{
			return true;
		}
		
		if(!this.inventory.getStackInSlot(4).isItemEqual(itemStack)) {
			return false;
		}
		
		if(this.inventory.getStackInSlot(4).getCount() < this.inventory.getSlotLimit(4) && this.inventory.getStackInSlot(4).getCount() < this.inventory.getStackInSlot(4).getMaxStackSize()) {
			return true;
		}else{
			return this.inventory.getStackInSlot(4).getCount() < itemStack.getMaxStackSize();
		}
	}
	
	private void processItem() {
		if(canProcess()) {
	        ItemStack itemStack = FurnaceRecipes.instance().getSmeltingResult(this.inventory.getStackInSlot(0));
			
			if(this.inventory.getStackInSlot(4).isEmpty())
			{
				this.inventory.setStackInSlot(4, itemStack.copy());
			}else if(this.inventory.getStackInSlot(4).isItemEqual(itemStack)) {
				this.inventory.getStackInSlot(4).grow(itemStack.getCount());
			}
			
			for(int i = 0; i < 1; i++)
			{
				if(this.inventory.getStackInSlot(i).isEmpty())
				{
					this.inventory.setStackInSlot(i, new ItemStack(this.inventory.getStackInSlot(i).getItem()));
				}else{
					this.inventory.getStackInSlot(i).shrink(1);
				}
				if(this.inventory.getStackInSlot(i).isEmpty())
				{
					this.inventory.setStackInSlot(i, ItemStack.EMPTY);
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
			heat = RTGUtil.updateRTGs(this.inventory, new int[] {1, 2, 3});
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
	public int[] getAccessibleSlotsFromSide(EnumFacing e) {
		int i = e.ordinal();
		return i == 0 ? slots_bottom : (i == 1 ? slots_top : slots_side);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int amount) {
		if(slot < 4){
			if(!(itemStack.getItem() instanceof ItemRTGPellet)){
				return true;
			}
		}
		if(slot == 4){
			return true;
		}
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		if(0 < i && i < 4){
			if(stack.getItem() instanceof ItemRTGPellet)
				return true;
		}
		if(i == 0){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack itemStack, int amount) {
		return this.isItemValidForSlot(slot, itemStack);
	}

}
