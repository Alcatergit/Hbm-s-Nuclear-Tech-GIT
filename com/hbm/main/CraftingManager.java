package com.hbm.main;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.GameData;

public class CraftingManager {
	
	public static void init(){
		addCrafting();
		addSmelting();
	}
	
	public static void addCrafting(){
		//Tools
		addShapedRecipe(new ItemStack(ModItems.schrabidium_pickaxe, 1), new Object[]{"SSS", " T ", " T ", 'S', ModItems.ingot_schrabidium, 'T', Items.STICK});
		addShapedRecipe(new ItemStack(ModItems.schrabidium_axe, 1), new Object[]{"SS ", "ST ", " T ", 'S', ModItems.ingot_schrabidium, 'T', Items.STICK});
		addShapedRecipe(new ItemStack(ModItems.schrabidium_hoe, 1), new Object[]{"SS ", " T ", " T ", 'S', ModItems.ingot_schrabidium, 'T', Items.STICK});
		addShapedRecipe(new ItemStack(ModItems.schrabidium_shovel, 1), new Object[]{" S ", " T ", " T ", 'S', ModItems.ingot_schrabidium, 'T', Items.STICK});
		addShapedRecipe(new ItemStack(ModItems.schrabidium_sword, 1), new Object[]{" S ", " S ", " T ", 'S', ModItems.ingot_schrabidium, 'T', Items.STICK});
		
		addShapedRecipe(new ItemStack(ModItems.alloy_pickaxe, 1), new Object[]{"SSS", " T ", " T ", 'S', ModItems.ingot_advanced_alloy, 'T', Items.STICK});
		addShapedRecipe(new ItemStack(ModItems.alloy_axe, 1), new Object[]{"SS ", "ST ", " T ", 'S', ModItems.ingot_advanced_alloy, 'T', Items.STICK});
		addShapedRecipe(new ItemStack(ModItems.alloy_hoe, 1), new Object[]{"SS ", " T ", " T ", 'S', ModItems.ingot_advanced_alloy, 'T', Items.STICK});
		addShapedRecipe(new ItemStack(ModItems.alloy_shovel, 1), new Object[]{" S ", " T ", " T ", 'S', ModItems.ingot_advanced_alloy, 'T', Items.STICK});
		addShapedRecipe(new ItemStack(ModItems.alloy_sword, 1), new Object[]{" S ", " S ", " T ", 'S', ModItems.ingot_advanced_alloy, 'T', Items.STICK});
		
		
		
	}
	
	public static void addSmelting(){
		
	}
	
	public static void addShapedRecipe(ItemStack output, Object... args){
		CraftingHelper.ShapedPrimer primer = CraftingHelper.parseShaped(args);
		ResourceLocation loc = getRecipeName(output);
		ShapedRecipes recipe = new ShapedRecipes(output.getItem().getRegistryName().toString(), primer.width, primer.height, primer.input, output);
		recipe.setRegistryName(loc);
		GameData.register_impl(recipe);
	}
	
	public static void addShapelessRecipe(ItemStack output, Object... args){
		ResourceLocation loc = getRecipeName(output);
		ShapelessRecipes recipe = new ShapelessRecipes(loc.getResourceDomain(), output, buildInput(args));
		recipe.setRegistryName(loc);
		GameData.register_impl(recipe);
	}
	
	public static void addShapedOreRecipe(ItemStack output, Object... args){
		ResourceLocation loc = getRecipeName(output);
		ShapedOreRecipe recipe = new ShapedOreRecipe(loc, output, args);
		recipe.setRegistryName(loc);
		GameData.register_impl(recipe);
	}
	
	public static void addShapelessOreRecipe(ItemStack output, Object... args){
		ResourceLocation loc = getRecipeName(output);
		ShapelessOreRecipe recipe = new ShapelessOreRecipe(loc, output, args);
		recipe.setRegistryName(loc);
		GameData.register_impl(recipe);
	}

	public static ResourceLocation getRecipeName(ItemStack output){
		ResourceLocation loc = new ResourceLocation(RefStrings.MODID, output.getItem().getRegistryName().getResourcePath());
		int i = 0;
		while(net.minecraft.item.crafting.CraftingManager.REGISTRY.containsKey(loc)){
			i++;
			loc = new ResourceLocation(RefStrings.MODID, loc.getResourcePath() + "_" + i);
		}
		return loc;
	}
	
	public static NonNullList<Ingredient> buildInput(Object[] args){
		NonNullList<Ingredient> list = NonNullList.create();
		for(Object obj : args){
			if(obj instanceof Ingredient){
				list.add((Ingredient)obj);
			} else {
				Ingredient i = CraftingHelper.getIngredient(obj);
				if(i == null){
					i = Ingredient.EMPTY;
				}
				list.add(i);
			}
		}
		return list;
	}
}
