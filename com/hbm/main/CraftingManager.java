package com.hbm.main;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
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
		
		//Generic Items
		addShapedOreRecipe(new ItemStack(ModItems.pellet_coal, 1), new Object[] { "PFP", "FOF", "PFP", 'P', "dustCoal", 'F', Items.FLINT, 'O', ModBlocks.gravel_obsidian });
		addShapedRecipe(new ItemStack(Blocks.TORCH, 8), new Object[] { "L", "S", 'L', ModItems.coke, 'S', Items.STICK });
		
		addShapelessRecipe(new ItemStack(ModItems.biomass, 4), new Object[] { Items.MELON, Items.MELON, Items.MELON, Items.MELON, Items.MELON, Items.MELON, Items.MELON });
		addShapelessRecipe(new ItemStack(ModItems.biomass, 4), new Object[] { Items.APPLE, Items.APPLE, Items.APPLE, Items.APPLE, Items.APPLE, Items.APPLE, Items.APPLE, Items.APPLE, Items.APPLE });
		addShapelessRecipe(new ItemStack(ModItems.biomass, 4), new Object[] { Items.REEDS, Items.REEDS, Items.REEDS, Items.REEDS, Items.REEDS, Items.REEDS, Items.REEDS, Items.REEDS, Items.REEDS });
		addShapelessRecipe(new ItemStack(ModItems.biomass, 4), new Object[] { Items.ROTTEN_FLESH, Items.ROTTEN_FLESH, Items.ROTTEN_FLESH, Items.ROTTEN_FLESH, Items.ROTTEN_FLESH, Items.ROTTEN_FLESH, Items.ROTTEN_FLESH });
		addShapelessRecipe(new ItemStack(ModItems.biomass, 4), new Object[] { Items.CARROT, Items.CARROT, Items.CARROT, Items.CARROT, Items.CARROT, Items.CARROT, Items.CARROT, Items.CARROT, Items.CARROT });
		addShapelessRecipe(new ItemStack(ModItems.biomass, 4), new Object[] { Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO });
		addShapelessRecipe(new ItemStack(ModItems.biomass, 4), new Object[] { Items.POISONOUS_POTATO, Items.POISONOUS_POTATO, Items.POISONOUS_POTATO, Items.POISONOUS_POTATO, Items.POISONOUS_POTATO, Items.POISONOUS_POTATO, Items.POISONOUS_POTATO, Items.POISONOUS_POTATO, Items.POISONOUS_POTATO });
		addShapelessRecipe(new ItemStack(ModItems.biomass, 4), new Object[] { Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO });
		addShapelessOreRecipe(new ItemStack(ModItems.biomass, 4), new Object[] { "treeSapling", "treeSapling", "treeSapling", "treeSapling", "treeSapling", "treeSapling", "treeSapling" });
		addShapelessOreRecipe(new ItemStack(ModItems.biomass, 4), new Object[] { "treeLeaves", "treeLeaves", "treeLeaves", "treeLeaves", "treeLeaves" });
		addShapelessRecipe(new ItemStack(ModItems.biomass, 8), new Object[] { Blocks.PUMPKIN, Blocks.PUMPKIN, Blocks.PUMPKIN, Blocks.PUMPKIN, Blocks.PUMPKIN, Blocks.PUMPKIN });
		addShapelessOreRecipe(new ItemStack(ModItems.biomass, 6), new Object[] { "logWood", "logWood", "logWood" });
		addShapelessOreRecipe(new ItemStack(ModItems.biomass, 4), new Object[] { "plankWood", "plankWood", "plankWood", "plankWood", "plankWood", "plankWood", "plankWood", "plankWood", "plankWood" });
		addShapelessRecipe(new ItemStack(ModItems.biomass, 8), new Object[] { Blocks.HAY_BLOCK, Blocks.HAY_BLOCK });
		addShapelessRecipe(new ItemStack(ModItems.biomass, 1), new Object[] { Items.WHEAT_SEEDS, Items.WHEAT_SEEDS, Items.WHEAT_SEEDS, Items.WHEAT_SEEDS, Items.WHEAT_SEEDS, Items.WHEAT_SEEDS, Items.WHEAT_SEEDS, Items.WHEAT_SEEDS, Items.WHEAT_SEEDS });
		addShapelessRecipe(new ItemStack(ModItems.biomass, 2), new Object[] { Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS });
		addShapelessRecipe(new ItemStack(ModItems.biomass, 2), new Object[] { Items.MELON_SEEDS, Items.MELON_SEEDS, Items.MELON_SEEDS, Items.MELON_SEEDS, Items.MELON_SEEDS, Items.MELON_SEEDS, Items.MELON_SEEDS, Items.MELON_SEEDS, Items.MELON_SEEDS });
		
	}
	
	public static void addSmelting(){
		GameRegistry.addSmelting(ModItems.powder_coal, new ItemStack(ModItems.coke), 1.0F);
		GameRegistry.addSmelting(ModItems.briquette_lignite, new ItemStack(ModItems.coke), 1.0F);
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
