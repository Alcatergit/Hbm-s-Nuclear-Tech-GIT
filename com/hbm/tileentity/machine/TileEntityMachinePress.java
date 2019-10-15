package com.hbm.tileentity.machine;

import com.hbm.items.special.ItemBlades;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.GameData;

public class TileEntityMachinePress extends TileEntity implements ISidedInventory, ITickable {
	
	public int progress = 0;
	public int power = 0;
	public int burnTime = 0;
	public final static int maxProgress = 200;
	public final static int maxPower = 700;
	public int maxBurn = 160;
	public int item;
	public int meta;
	public boolean isRetracting = false;
	
	private NonNullList<ItemStack> slots = NonNullList.<ItemStack>withSize(4, ItemStack.EMPTY);
	
	private String customName;
	
	public int getPowerScaled(int i) {
		return (power * i) / maxPower;
	}
	
	public int getBurnScaled(int i) {
		return (burnTime * i) / maxBurn;
	}
	
	public int getProgressScaled(int i) {
		return (progress * i) / maxProgress;
	}
	
	public int getSizeInventory(){
		return slots.size();
	}
	
	public ItemStack getStackInSlot(int i){
		return slots.get(i);
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "container.press";
	}

	@Override
	public boolean hasCustomName() {
		return this.customName != null && this.customName.length() > 0;
	}

	@Override
    public boolean isEmpty()
    {
        for (ItemStack itemstack : this.slots)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
    }

	@Override
    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(this.slots, index, count);
    }

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.slots, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
        this.slots.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }
		
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		if(world.getTileEntity(pos) != this)
		{
			return false;
		}else{
			return player.getDistanceSqToCenter(pos) <= 64;
		}
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		
		if(stack.getItem() instanceof ItemBlades && i == 1)
			return true;
		
		if(TileEntityFurnace.getItemBurnTime(stack) > 0 && i == 0)
			return true;
		
		return i == 2;
	}

	/**
	 * 0 is progress, 1 is power, 2 is burn time, 3 is item, 4 is meta
	 */
	@Override
	public int getField(int id) {
		switch(id){
		case 0:
			return this.progress;
		case 1:
			return this.power;
		case 2:
			return this.burnTime;
		case 3:
			return this.item;
		case 4:
			return this.meta;
		default:
			return 0;
	}
	}

	/**
	 * 0 is progress, 1 is power, 2 is burn time, 3 is item, 4 is meta
	 */
	@Override
	public void setField(int id, int value) {	
		switch(id){
			case 0:
				this.progress = value;
				break;
			case 1:
				this.power = value;
				break;
			case 2:
				this.burnTime = value;
				break;
			case 3:
				this.item = value;
				break;
			case 4:
				this.meta = value;
				break;
			default:
				break;
		}
	}

	@Override
	public int getFieldCount() {
		return 5;
	}

	@Override
	public void clear() {
		this.slots.clear();
	}
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return null;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return index == 2;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return index == 3;
	}

	@Override
	public void update() {	
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
	
}
