package com.hbm.handler.jei;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.jei.JeiRecipes.AssemblerRecipeWrapper;
import com.hbm.inventory.AssemblerRecipes;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;

import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IFocus.Mode;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeRegistryPlugin;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HbmJeiRegistryPlugin implements IRecipeRegistryPlugin {

	// Drillgon200: This is needed because assembler recipes can change during
		// run time.
	@Override
	public <V> List<String> getRecipeCategoryUids(IFocus<V> focus) {
		return Lists.newArrayList(JEIConfig.ASSEMBLY);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IRecipeWrapper, V> List<T> getRecipeWrappers(IRecipeCategory<T> recipeCategory, IFocus<V> focus) {
		if(focus.getValue() instanceof ItemStack) {
			ItemStack stack = ((ItemStack) focus.getValue()).copy();
			stack.setCount(1);
			if(JEIConfig.ASSEMBLY.equals(recipeCategory.getUid())) {
				if(focus.getMode() == Mode.INPUT) {
					if(stack.getItem() == Item.getItemFromBlock(ModBlocks.machine_assembler)){
						return getRecipeWrappers(recipeCategory);
					}
					List<T> list = (List<T>) AssemblerRecipes.recipes.entrySet().stream().filter(recipe -> {
						for(AStack input : recipe.getValue()) {
							if(input.copy().singulize().isApplicable(stack))
								return true;
						}
						return false;
					}).map(recipe -> new AssemblerRecipeWrapper(recipe.getKey().toStack(), recipe.getValue(), AssemblerRecipes.time.get(recipe.getKey()))).collect(Collectors.toList());
					return list;
				} else if(focus.getMode() == Mode.OUTPUT) {
					return (List<T>) AssemblerRecipes.recipes.entrySet().stream().filter(recipe -> (new ComparableStack(recipe.getKey().toStack()).matchesRecipe(stack, true))).map(recipe -> new AssemblerRecipeWrapper(recipe.getKey().toStack(), recipe.getValue(), AssemblerRecipes.time.get(recipe.getKey()))).collect(Collectors.toList());
				}
			}
		}
		return Collections.emptyList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IRecipeWrapper> List<T> getRecipeWrappers(IRecipeCategory<T> recipeCategory) {
		if(recipeCategory.getUid().equals(JEIConfig.ASSEMBLY)) {
			
			return (List<T>) AssemblerRecipes.recipes.entrySet().stream().map(recipe -> new AssemblerRecipeWrapper(recipe.getKey().toStack(), recipe.getValue(), AssemblerRecipes.time.get(recipe.getKey()))).collect(Collectors.toList());
		} else {
			return Collections.emptyList();
		}
	}

}
