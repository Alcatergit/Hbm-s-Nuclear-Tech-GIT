package com.hbm.inventory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SAFERecipes {

	private static LinkedHashMap<ComparableStack, ItemStack> recipes = new LinkedHashMap<>();
	
	public static void registerRecipes() {
		addRecipe(new ComparableStack(ModItems.tiny_singularity), new ItemStack(ModItems.singularity));
		addRecipe(new ComparableStack(ModItems.tiny_singularity_counter_resonant), new ItemStack(ModItems.singularity_counter_resonant));
		addRecipe(new ComparableStack(ModItems.tiny_singularity_super_heated), new ItemStack(ModItems.singularity_super_heated));
		addRecipe(new ComparableStack(ModItems.tiny_black_hole), new ItemStack(ModItems.black_hole));
		addRecipe(new ComparableStack(ModItems.tiny_singularity_spark), new ItemStack(ModItems.singularity_spark));
	}

	public static void addRecipe(ComparableStack input, ItemStack output){
		recipes.put(input, output);
	}

	public static void removeRecipe(ComparableStack input){
		recipes.remove(input);
	}
	
	public static LinkedHashMap<ItemStack, ItemStack> getAllRecipes() {
		
		LinkedHashMap<ItemStack, ItemStack> map = new LinkedHashMap<>();
		for(Map.Entry<ComparableStack, ItemStack> recipe : recipes.entrySet()) {
			map.put(recipe.getKey().toStack(), recipe.getValue());
		}
		
		return map;
	}
	
	public static ItemStack getOutput(ItemStack stack) {
		if(stack == null)
			return null;
		return recipes.get(new ComparableStack(stack));
	}
}
