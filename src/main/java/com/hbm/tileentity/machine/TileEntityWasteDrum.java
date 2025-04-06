package com.hbm.tileentity.machine;

import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.WasteDrumRecipes;
import com.hbm.items.machine.ItemRBMKRod;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class TileEntityWasteDrum extends TileEntityMachineBase implements ITickable {

	private static final int[] slots_arr = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
	
	private int water = 0;
	
	public TileEntityWasteDrum() {
		super(12, 1);
	}
	
	@Override
	public String getName() {
		return "container.wasteDrum";
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
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return WasteDrumRecipes.recipes.containsKey(new RecipesCommon.ComparableStack(stack)) || stack.getItem() instanceof ItemRBMKRod;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(EnumFacing e) {
		return slots_arr;
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack itemStack, int amount) {
		return this.isItemValidForSlot(slot, itemStack);
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int amount) {
		Item item = itemStack.getItem();

		if(item instanceof ItemRBMKRod) {
			return ItemRBMKRod.getCoreHeat(itemStack) < 50 && ItemRBMKRod.getHullHeat(itemStack) < 50;
		}
		
		return WasteDrumRecipes.isCold(itemStack);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound){
		water = compound.getInteger("water");
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		compound.setInteger("water", water);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void onLoad(){
		updateWater();
	}
	
	public void updateWater(){
		water = 0;

		Block b = world.getBlockState(pos.east()).getBlock();
		if(b == Blocks.WATER || b == Blocks.FLOWING_WATER)
			water++;
		b = world.getBlockState(pos.west()).getBlock();
		if(b == Blocks.WATER || b == Blocks.FLOWING_WATER)
			water++;
		b = world.getBlockState(pos.up()).getBlock();
		if(b == Blocks.WATER || b == Blocks.FLOWING_WATER)
			water++;
		b = world.getBlockState(pos.down()).getBlock();
		if(b == Blocks.WATER || b == Blocks.FLOWING_WATER)
			water++;
		b = world.getBlockState(pos.south()).getBlock();
		if(b == Blocks.WATER || b == Blocks.FLOWING_WATER)
			water++;
		b = world.getBlockState(pos.north()).getBlock();
		if(b == Blocks.WATER || b == Blocks.FLOWING_WATER)
			water++;
	}
	
	@Override
	public void update() {
		if(!world.isRemote) {
			if(water > 0) {
				
				int r = 60 * 60 * 20 / water;
				
				for(int i = 0; i < 12; i++) {
					
					if(inventory.getStackInSlot(i).getItem() instanceof ItemRBMKRod) {
						
						ItemRBMKRod rod = (ItemRBMKRod) inventory.getStackInSlot(i).getItem();
						rod.updateHeat(world, inventory.getStackInSlot(i), 0.025D);
						rod.provideHeat(world, inventory.getStackInSlot(i), 20D, 0.025D);
						
					} else if(world.rand.nextInt(r) == 0) {
						
						if(!inventory.getStackInSlot(i).isEmpty()) {

							RecipesCommon.ComparableStack comp = new RecipesCommon.ComparableStack(inventory.getStackInSlot(i));
							if(WasteDrumRecipes.recipes.containsKey(comp)) {
								inventory.setStackInSlot(i, WasteDrumRecipes.recipes.get(comp).copy());
								world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 1.0F);
							}
						}
					}
				}
			}
		}
	}
}
