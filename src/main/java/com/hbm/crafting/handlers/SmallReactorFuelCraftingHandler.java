package com.hbm.crafting.handlers;

import com.hbm.items.machine.ItemFuelRod;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class SmallReactorFuelCraftingHandler extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	/**
	 * The only rules for matching is that the item is fuel (meta and NBT don't matter) and that it's the only stack in the grid
	 */
	@Override
	public boolean matches(InventoryCrafting inventory, World world) {
		if(!hasExactlyOneStack(inventory))
			return false;
		
		ItemStack stack = getFirstStack(inventory);
		
		if(stack.getItem() instanceof ItemFuelRod){
			return ItemFuelRod.getLifeTime(stack) == 0;
		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		if(!hasExactlyOneStack(inventory))
			return ItemStack.EMPTY;
		
		ItemStack stack = getFirstStack(inventory);
		
		if(stack.getItem() instanceof ItemFuelRod) {
			if(ItemFuelRod.getLifeTime(stack) == 0){
				return ((ItemFuelRod)stack.getItem()).getUncrafting();
			}
		}

		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}
	
	private boolean hasExactlyOneStack(InventoryCrafting inventory) {
		
		boolean hasOne = false;

		for(int i = 0; i < inventory.getSizeInventory(); ++i) {
			ItemStack stack = inventory.getStackInSlot(i);
				
			if(!stack.isEmpty()) {

				if(!hasOne)
					hasOne = true;
				else
					return false;
			}
		}
		
		return hasOne;
	}
	
	private ItemStack getFirstStack(InventoryCrafting inventory) {

		for(int i = 0; i < inventory.getSizeInventory(); ++i) {
			ItemStack stack = inventory.getStackInSlot(i);
				
			if(!stack.isEmpty()) {
				return stack;
			}
		}
		
		return ItemStack.EMPTY;
	}

	@Override
	public boolean isDynamic(){
		return true;
	}
	
	@Override
	public boolean canFit(int width, int height){
		return width >= 1 && height >= 1;
	}

}