package com.hbm.tileentity.machine;

import com.hbm.blocks.machine.MachineDiFurnace;
import com.hbm.inventory.MachineRecipes;
import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityDiFurnace extends TileEntity implements ITickable, ICapabilityProvider {

	public int dualCookTime;
	public int dualPower;
	public static final int maxPower = 12800;
	public static final int processingSpeed = 400;
	//TODO side based access
	public ItemStackHandler inventory = new ItemStackHandler(4){
		protected void onContentsChanged(int slot) {
			super.onContentsChanged(slot);
			markDirty();
		};
		public boolean isItemValid(int slot, net.minecraft.item.ItemStack stack) {
			if(slot == 3){
				return false;
			}
			return true;
		};
		
	};
	
	private String customName;
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.dualPower = compound.getInteger("dualPower");
		this.dualCookTime = compound.getInteger("cookTime");
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT((NBTTagCompound) compound.getTag("inventory"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("dualPower", this.dualPower);
		compound.setInteger("cookTime", this.dualCookTime);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void update() {
		boolean flag = this.hasPower();
		boolean flag1 = false;
		
		if(flag && isProcessing())
		{
			this.dualPower = this.dualPower - 1;
			this.markDirty();
			if(this.dualPower < 0)
			{
				this.dualPower = 0;
			}
		}
		if (this.hasItemPower(inventory.getStackInSlot(2))
				&& this.dualPower <= (TileEntityDiFurnace.maxPower - TileEntityDiFurnace.getItemPower(inventory.getStackInSlot(2)))) {
			this.markDirty();
			this.dualPower += getItemPower(inventory.getStackInSlot(2));
			if (!inventory.getStackInSlot(2).isEmpty()) {
				flag1 = true;
				inventory.getStackInSlot(2).shrink(1);
				if (inventory.getStackInSlot(2).isEmpty()) {
					inventory.setStackInSlot(2, inventory.getStackInSlot(2).getItem().getContainerItem(inventory.getStackInSlot(2)));
				}
			}
		}
		if (flag && canProcess()) {
			dualCookTime++;
			this.markDirty();
			if (this.dualCookTime == TileEntityDiFurnace.processingSpeed) {
				this.dualCookTime = 0;
				this.processItem();
				flag1 = true;
			}
		} else {
			dualCookTime = 0;
		}

		if(!world.isRemote)
		{
			boolean trigger = true;
			
			if(flag && canProcess() && this.dualCookTime == 0)
			{
				trigger = false;
			}

			if (!inventory.getStackInSlot(2).isEmpty() && inventory.getStackInSlot(2).getItem() == ModItems.pellet_rtg) {
				if(this.dualPower != maxPower){
					this.markDirty();
					this.dualPower = maxPower;
				}
			}
			
			if(trigger)
            {
                flag1 = true;
                MachineDiFurnace.updateBlockState(this.dualCookTime > 0, this.world, pos);
            }
		}
		
		if(flag1)
		{
			this.markDirty();
		}
		
	}
	
	public boolean hasItemPower(ItemStack itemStack) {
		return getItemPower(itemStack) > 0;
	}
	
	private static int getItemPower(ItemStack itemStack) {
		if(itemStack == null)
		{
			return 0;
		}else{
			Item item = itemStack.getItem();
			
			if(item == Items.COAL) return 200;
			if(item == Item.getItemFromBlock(Blocks.COAL_BLOCK)) return 2000;
			if(item == Items.LAVA_BUCKET) return 12800;
			if(item == Items.BLAZE_ROD) return 1000;
			if(item == Items.BLAZE_POWDER) return 300;
			if(item == ModItems.lignite) return 150;
			if(item == ModItems.powder_lignite) return 150;
			if(item == ModItems.powder_coal) return 200;
			if(item == ModItems.briquette_lignite) return 200;
			if(item == ModItems.coke) return 400;
			
			return 0;
		}
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			return (T) inventory;
		}
		return super.getCapability(capability, facing);
	}

	public void setCustomInventoryName(String displayName) {
		this.customName = displayName;
	}
	
	public String getName(){
		return this.hasCustomName() ? this.customName : "container.diFurnace";
	}
	
	public boolean hasCustomName(){
		return this.customName != null && this.customName.length() > 0;
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
		return (dualCookTime * i) / processingSpeed;
	}
	
	public int getPowerRemainingScaled(int i) {
		return (dualPower * i) / maxPower;
	}
	
	public boolean canProcess() {
		if(inventory.getStackInSlot(0) == null || inventory.getStackInSlot(1) == null)
		{
			return false;
		}
		ItemStack itemStack = MachineRecipes.getFurnaceProcessingResult(inventory.getStackInSlot(0), inventory.getStackInSlot(1));
		if(itemStack == null)
		{	
			return false;
		}
		
		if(inventory.getStackInSlot(3) == ItemStack.EMPTY)
		{
			return true;
		}
		if(inventory.getStackInSlot(3).getItem() != ItemStack.EMPTY.getItem() && !inventory.getStackInSlot(3).isItemEqual(itemStack)) {
			System.out.println(inventory.getStackInSlot(3).getItem());
			return false;
		}
		
		if(inventory.getStackInSlot(3).getCount() < inventory.getSlotLimit(3) && inventory.getStackInSlot(3).getCount() < inventory.getStackInSlot(3).getMaxStackSize()) {
			return true;
		}else{
			return inventory.getStackInSlot(3).getCount() < itemStack.getMaxStackSize();
		}
	}
	
	private void processItem() {
		if(canProcess()) {
			ItemStack itemStack = MachineRecipes.getFurnaceProcessingResult(inventory.getStackInSlot(0), inventory.getStackInSlot(1));
			
			if(inventory.getStackInSlot(3).isEmpty())
			{
				inventory.setStackInSlot(3, itemStack.copy());
			}else if(inventory.getStackInSlot(3).isItemEqual(itemStack)) {
				inventory.getStackInSlot(3).grow(itemStack.getCount());
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
		}
	}
	
	public boolean hasPower() {
		return dualPower > 0;
	}
	
	public boolean isProcessing() {
		return this.dualCookTime > 0;
	}

}
