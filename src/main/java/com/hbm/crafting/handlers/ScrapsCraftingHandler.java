package com.hbm.crafting.handlers;

import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemScraps;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class ScrapsCraftingHandler extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inventory, World world) {
        MaterialStack mat = null;
        for(int i = 0; i < inventory.getSizeInventory(); ++i) {
			ItemStack stack = inventory.getStackInSlot(i);

			if(stack.isEmpty()) continue;
            if(mat != null || stack.getItem() != ModItems.scraps) return false;

			mat = ItemScraps.getMats(stack);
        }
		return mat != null && mat.amount >= 2;
	}

	@Override
	public @NotNull ItemStack getCraftingResult(InventoryCrafting inventory) {
        MaterialStack mat = null;
        for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			
			if(stack.isEmpty()) continue;
			if(mat != null || stack.getItem() != ModItems.scraps) return ItemStack.EMPTY;

			mat = ItemScraps.getMats(stack);
            if(mat == null || mat.amount < 2) return ItemStack.EMPTY;
		}
        if(mat == null) return ItemStack.EMPTY;
        ItemStack scrap = ItemScraps.create(new MaterialStack(mat.material, mat.amount >> 1));
        scrap.setCount(2);
        return scrap;
	}

	@Override
	public @NotNull ItemStack getRecipeOutput() {
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
