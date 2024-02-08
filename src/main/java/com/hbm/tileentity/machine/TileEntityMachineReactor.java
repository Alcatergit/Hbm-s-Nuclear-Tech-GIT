package com.hbm.tileentity.machine;

import com.hbm.inventory.BreederRecipes;
import com.hbm.inventory.BreederRecipes.BreederRecipe;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMachineReactor extends TileEntityMachineBase implements ITickable {

	public int progress;
	public int charge;
	public int heat;
	public int recipeHeat;
	public static final int maxPower = 1000;
	public static final int processingSpeed = 1000;

	private static final int[] slots_top = new int[] { 1 };
	private static final int[] slots_bottom = new int[] { 2, 0 };
	private static final int[] slots_side = new int[] { 0 };

	public TileEntityMachineReactor() {
		super(3);
	}

	@Override
	public String getName() {
		return "container.reactor";
	}

	public boolean hasItemPower(ItemStack stack) {
		return BreederRecipes.getFuelValue(stack) != null;
	}

	private static int getItemPower(ItemStack stack) {
		int[] power = BreederRecipes.getFuelValue(stack);
		
		if(power == null)
			return 0;
		
		return power[1];
	}
	
	private static int getItemHeat(ItemStack stack) {
		
		int[] power = BreederRecipes.getFuelValue(stack);
		
		if(power == null)
			return 0;
		
		return power[0];
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		charge = nbt.getShort("powerTime");
		heat = nbt.getShort("heat");
		progress = nbt.getShort("CookTime");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {

		nbt.setShort("powerTime", (short) charge);
		nbt.setShort("heat", (short) heat);
		nbt.setShort("cookTime", (short) progress);

		return super.writeToNBT(nbt);
	}
	
	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = new AxisAlignedBB(
					pos.getX(),
					pos.getY(),
					pos.getZ(),
					pos.getX() + 1,
					pos.getY() + 3,
					pos.getZ() + 1
					);
		}

		return bb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(EnumFacing e) {
		int side = e.ordinal();
		return side == 0 ? slots_bottom : (side == 1 ? slots_top : slots_side);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int amount) {
		if(slot == 0) {
			if(!hasItemPower(inventory.getStackInSlot(0))) {
				return true;
			}

			return false;
		}

		return true;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return i == 2 ? false : (i == 0 ? hasItemPower(stack) : true);
	}

	public int getProgressScaled(int i) {
		return (progress * i) / processingSpeed;
	}

	public int getHeatScaled(int i) {
		return (heat * i) / 4;
	}

	public boolean hasPower() {
		return charge > 0;
	}

	public boolean isProcessing() {
		return this.progress > 0;
	}

	public boolean canProcess() {
		if(inventory.getStackInSlot(1).isEmpty()) {
			return false;
		}
		
		BreederRecipe recipe = BreederRecipes.getOutput(inventory.getStackInSlot(1));
		
		if(recipe == null)
			return false;
		recipeHeat = recipe.heat;
		if(this.heat < recipe.heat)
			return false;

		if(inventory.getStackInSlot(2).isEmpty())
			return true;

		if(!inventory.getStackInSlot(2).isItemEqual(recipe.output))
			return false;

		if(inventory.getStackInSlot(2).getCount() < inventory.getSlotLimit(2) && inventory.getStackInSlot(2).getCount() < inventory.getStackInSlot(2).getMaxStackSize())
			return true;
		else
			return inventory.getStackInSlot(2).getCount() < recipe.output.getMaxStackSize();
	}

	private void processItem() {
		if(canProcess()) {
			
			BreederRecipe rec = BreederRecipes.getOutput(inventory.getStackInSlot(1));
			
			if(rec == null)
				return;
			
			ItemStack itemStack = rec.output;

			if(inventory.getStackInSlot(2).isEmpty()) {
				inventory.setStackInSlot(2, itemStack.copy());
			} else if(inventory.getStackInSlot(2).isItemEqual(itemStack)) {
				inventory.getStackInSlot(2).grow(itemStack.getCount());
			}

			for(int i = 1; i < 2; i++) {
				if(inventory.getStackInSlot(i).isEmpty()) {
					inventory.setStackInSlot(i, new ItemStack(inventory.getStackInSlot(i).getItem()));
				} else {
					inventory.getStackInSlot(i).shrink(1);
				}
				if(inventory.getStackInSlot(i).isEmpty()) {
					inventory.setStackInSlot(i, ItemStack.EMPTY);
				}
			}
		}
	}

	public void updateReactorPower(){
		int incomingflux = getReactorPower(pos.north()) + getReactorPower(pos.south()) + getReactorPower(pos.west()) + getReactorPower(pos.east());
		if(incomingflux > 0 && charge < 2){
			charge = 1;
			heat = (incomingflux * 5) / TileEntityMachineReactorSmall.maxCoreHeat;
		}
	}

	public int getReactorPower(BlockPos rPos){
		TileEntity r = world.getTileEntity(rPos);
		if(r == null || !(r instanceof TileEntityMachineReactorSmall)) return 0;
		return ((TileEntityMachineReactorSmall)r).coreHeat;
	}

	@Override
	public void update() {
		if(!world.isRemote) {
			
			boolean markDirty = false;

			updateReactorPower();
			
			if(charge == 0) {
				heat = 0;
			}
			
			if(hasItemPower(inventory.getStackInSlot(0)) && charge == 0) {
				
				charge += getItemPower(inventory.getStackInSlot(0));
				heat = getItemHeat(inventory.getStackInSlot(0));
				
				if(!inventory.getStackInSlot(0).isEmpty()) {
					ItemStack container = inventory.getStackInSlot(0).getItem().getContainerItem(inventory.getStackInSlot(0));
					inventory.getStackInSlot(0).shrink(1);
					
					if(inventory.getStackInSlot(0).isEmpty()) {
						inventory.setStackInSlot(0, container);
					}
					
					markDirty = true;
				}
			}

			if(hasPower() && canProcess()) {
				
				progress += heat / recipeHeat;

				if(this.progress >= TileEntityMachineReactor.processingSpeed) {
					this.progress = 0;
					this.charge--;
					this.processItem();
					markDirty = true;
				}
			} else {
				progress = 0;
			}

			boolean trigger = true;

			if(hasPower() && canProcess() && this.progress == 0)
				trigger = false;

			if(trigger) {
				markDirty = true;
			}

			if(markDirty)
				this.markDirty();
			
			NBTTagCompound data = new NBTTagCompound();
			data.setShort("charge", (short)charge);
			data.setShort("progress", (short)progress);
			data.setByte("heat", (byte)heat);
			this.networkPack(data, 20);
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		charge = data.getShort("charge");
		progress = data.getShort("progress");
		heat = data.getByte("heat");
	}

}
