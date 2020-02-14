package com.hbm.main;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
		//Machines
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_press, 1), new Object[] { "IRI", "IPI", "IBI", 'I', "ingotIron", 'R', Blocks.FURNACE, 'B', "blockIron", 'P', Blocks.PISTON });
		
		//Generic Items
		addShapelessRecipe(new ItemStack(ModItems.nugget_u235, 9), new Object[] {"#", '#', ModItems.ingot_u235});
		addShapedRecipe(new ItemStack(ModItems.ingot_u235, 1), new Object[] {"###", "###", "###", '#', ModItems.nugget_u235});
		addShapelessRecipe(new ItemStack(ModItems.nugget_pu238, 9), new Object[] {"#", '#', ModItems.ingot_pu238});
		
		addShapelessRecipe(new ItemStack(ModItems.sulfur, 9), new Object[]{"#", '#', ModBlocks.block_sulfur});
		addShapedRecipe(new ItemStack(ModBlocks.block_sulfur, 1), new Object[] {"###", "###", "###", '#', ModItems.sulfur});
		addShapelessRecipe(new ItemStack(ModItems.niter, 9), new Object[]{"#", '#', ModBlocks.block_niter});
		addShapedRecipe(new ItemStack(ModBlocks.block_niter, 1), new Object[] {"###", "###", "###", '#', ModItems.niter});

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
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_thorium), new ItemStack(ModItems.ingot_th232), 3.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_uranium), new ItemStack(ModItems.ingot_uranium), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_nether_uranium), new ItemStack(ModItems.ingot_uranium), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_nether_plutonium), new ItemStack(ModItems.ingot_plutonium), 24.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_titanium), new ItemStack(ModItems.ingot_titanium), 3.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_copper), new ItemStack(ModItems.ingot_copper), 2.5F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_tungsten), new ItemStack(ModItems.ingot_tungsten), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_nether_tungsten), new ItemStack(ModItems.ingot_tungsten), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_aluminium), new ItemStack(ModItems.ingot_aluminium), 2.5F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_lead), new ItemStack(ModItems.ingot_lead), 3.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_beryllium), new ItemStack(ModItems.ingot_beryllium), 2.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_schrabidium), new ItemStack(ModItems.ingot_schrabidium), 128.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_nether_schrabidium), new ItemStack(ModItems.ingot_schrabidium), 256.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_uranium), new ItemStack(ModItems.ingot_uranium, 2), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_thorium), new ItemStack(ModItems.ingot_th232, 2), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_titanium), new ItemStack(ModItems.ingot_titanium, 3), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_copper), new ItemStack(ModItems.ingot_copper, 3), 5.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_tungsten), new ItemStack(ModItems.ingot_tungsten, 3), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_aluminium), new ItemStack(ModItems.ingot_aluminium, 3), 5.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_lead), new ItemStack(ModItems.ingot_lead, 3), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_lithium), new ItemStack(ModItems.lithium), 20.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_starmetal), new ItemStack(ModItems.ingot_starmetal), 50.0F);
	
		GameRegistry.addSmelting(ModItems.powder_lead, new ItemStack(ModItems.ingot_lead), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_neptunium, new ItemStack(ModItems.ingot_neptunium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_schrabidium, new ItemStack(ModItems.ingot_schrabidium), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_aluminium, new ItemStack(ModItems.ingot_aluminium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_beryllium, new ItemStack(ModItems.ingot_beryllium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_copper, new ItemStack(ModItems.ingot_copper), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_gold, new ItemStack(Items.GOLD_INGOT), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_iron, new ItemStack(Items.IRON_INGOT), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_titanium, new ItemStack(ModItems.ingot_titanium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_tungsten, new ItemStack(ModItems.ingot_tungsten), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_uranium, new ItemStack(ModItems.ingot_uranium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_thorium, new ItemStack(ModItems.ingot_th232), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_plutonium, new ItemStack(ModItems.ingot_plutonium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_advanced_alloy, new ItemStack(ModItems.ingot_advanced_alloy), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_combine_steel, new ItemStack(ModItems.ingot_combine_steel), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_magnetized_tungsten, new ItemStack(ModItems.ingot_magnetized_tungsten), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_red_copper, new ItemStack(ModItems.ingot_red_copper), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_steel, new ItemStack(ModItems.ingot_steel), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_lithium, new ItemStack(ModItems.lithium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_dura_steel, new ItemStack(ModItems.ingot_dura_steel), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_polymer, new ItemStack(ModItems.ingot_polymer), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_lanthanium, new ItemStack(ModItems.ingot_lanthanium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_actinium, new ItemStack(ModItems.ingot_actinium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_desh, new ItemStack(ModItems.ingot_desh), 1.0F);
		
		GameRegistry.addSmelting(ModItems.powder_coal, new ItemStack(ModItems.coke), 1.0F);
		GameRegistry.addSmelting(ModItems.briquette_lignite, new ItemStack(ModItems.coke), 1.0F);
		
		GameRegistry.addSmelting(ModItems.combine_scrap, new ItemStack(ModItems.ingot_combine_steel), 1.0F);
		//GameRegistry.addSmelting(ModItems.tank_waste, new ItemStack(ModItems.tank_waste), 0.0F);
		//GameRegistry.addSmelting(ModItems.canister_smear, new ItemStack(ModItems.canister_reoil), 1.0F);
		
		GameRegistry.addSmelting(Items.BONE, new ItemStack(Items.SLIME_BALL, 3), 0.0F);
		GameRegistry.addSmelting(new ItemStack(Items.DYE, 1, 15), new ItemStack(Items.SLIME_BALL, 1), 0.0F);
		GameRegistry.addSmelting(new ItemStack(Blocks.GRAVEL, 1), new ItemStack(Blocks.COBBLESTONE, 1), 0.0F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.gravel_obsidian), new ItemStack(Blocks.OBSIDIAN), 0.0F);
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
