package com.hbm.inventory;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.ModItems.Armory;
import com.hbm.items.ModItems.Materials.Crystals;
import com.hbm.items.ModItems.Materials.Ingots;
import com.hbm.items.ModItems.Materials.Nuggies;
import com.hbm.items.ModItems.Materials.Powders;
import com.hbm.items.special.ItemBedrockOre;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class ShredderRecipes {

	public static HashMap<ComparableStack, ItemStack> shredderRecipes = new HashMap<ComparableStack, ItemStack>();
	public static List<ShredderRecipe> jeiShredderRecipes = null;
	
	public static void registerShredder() {
		
		String[] names = OreDictionary.getOreNames();
		
		for(int i = 0; i < names.length; i++) {
			
			String name = names[i];
			
			//if the dict contains invalid names, skip
			if(name == null || name.isEmpty())
				continue;
			
			List<ItemStack> matches = OreDictionary.getOres(name);
			
			//if the name isn't assigned to an ore, also skip
			if(matches == null || matches.isEmpty())
				continue;

			if(name.length() > 5 && name.substring(0, 5).equals("ingot")) {
				ItemStack dust = getDustByName(name.substring(5));
				
				if(dust != null && dust.getItem() != ModItems.scrap) {

					for(ItemStack stack : matches) {
						shredderRecipes.put(new ComparableStack(stack), dust);
					}
				}
			} else if(name.length() > 6 && name.substring(0, 6).equals("nugget")) {
				ItemStack dust = getTinyDustByName(name.substring(6));
				
				if(dust != null && dust.getItem() != ModItems.scrap) {

					for(ItemStack stack : matches) {
						shredderRecipes.put(new ComparableStack(stack), dust);
					}
				}
			} else if(name.length() > 3 && name.substring(0, 3).equals("ore")) {
				ItemStack dust = getDustByName(name.substring(3));
				
				if(dust != null && dust.getItem() != ModItems.scrap) {
					
					dust.setCount(2);

					for(ItemStack stack : matches) {
						shredderRecipes.put(new ComparableStack(stack), dust);
					}
				}
			} else if(name.length() > 5 && name.substring(0, 5).equals("block")) {
				ItemStack dust = getDustByName(name.substring(5));
				
				if(dust != null && dust.getItem() != ModItems.scrap) {
					
					dust.setCount(9);

					for(ItemStack stack : matches) {
						shredderRecipes.put(new ComparableStack(stack), dust);
					}
				}
			} else if(name.length() > 3 && name.substring(0, 3).equals("gem")) {
				ItemStack dust = getDustByName(name.substring(3));
				
				if(dust != null && dust.getItem() != ModItems.scrap) {

					for(ItemStack stack : matches) {
						shredderRecipes.put(new ComparableStack(stack), dust);
					}
				}
			} else if(name.length() > 3 && name.substring(0, 4).equals("dust")) {

				for(ItemStack stack : matches) {
					if(stack != null && !stack.isEmpty() && Item.REGISTRY.getNameForObject(stack.getItem()) != null)
						shredderRecipes.put(new ComparableStack(stack), new ItemStack(ModItems.dust));
				}
			}
		}
	}
	
	public static void registerOverrides() {

		ShredderRecipes.setRecipe(ModItems.scrap, new ItemStack(ModItems.dust));
		ShredderRecipes.setRecipe(ModItems.dust, new ItemStack(ModItems.dust));
		ShredderRecipes.setRecipe(Blocks.GLOWSTONE, new ItemStack(Items.GLOWSTONE_DUST, 4));
		ShredderRecipes.setRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0), new ItemStack(Powders.powder_quartz, 4));
		ShredderRecipes.setRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 1), new ItemStack(Powders.powder_quartz, 4));
		ShredderRecipes.setRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 2), new ItemStack(Powders.powder_quartz, 4));
		ShredderRecipes.setRecipe(Blocks.QUARTZ_STAIRS, new ItemStack(Powders.powder_quartz, 3));
		ShredderRecipes.setRecipe(new ItemStack(Blocks.STONE_SLAB, 1, 7), new ItemStack(Powders.powder_quartz, 2));
		ShredderRecipes.setRecipe(Items.QUARTZ, new ItemStack(Powders.powder_quartz));
		ShredderRecipes.setRecipe(Blocks.QUARTZ_ORE, new ItemStack(Powders.powder_quartz, 2));
		ShredderRecipes.setRecipe(ModBlocks.ore_nether_fire, new ItemStack(Powders.powder_fire, 6));
		ShredderRecipes.setRecipe(Blocks.PACKED_ICE, new ItemStack(Powders.powder_ice, 1));
		ShredderRecipes.setRecipe(ModBlocks.brick_light, new ItemStack(Items.CLAY_BALL, 4));
		ShredderRecipes.setRecipe(ModBlocks.concrete, new ItemStack(Blocks.GRAVEL, 1));
		ShredderRecipes.setRecipe(ModBlocks.concrete_smooth, new ItemStack(Blocks.GRAVEL, 1));
		ShredderRecipes.setRecipe(ModBlocks.brick_concrete, new ItemStack(Blocks.GRAVEL, 1));
		ShredderRecipes.setRecipe(ModBlocks.brick_obsidian, new ItemStack(ModBlocks.gravel_obsidian, 1));
		ShredderRecipes.setRecipe(Blocks.OBSIDIAN, new ItemStack(ModBlocks.gravel_obsidian, 1));
		ShredderRecipes.setRecipe(Blocks.STONE, new ItemStack(Blocks.GRAVEL, 1));
		ShredderRecipes.setRecipe(Blocks.COBBLESTONE, new ItemStack(Blocks.GRAVEL, 1));
		ShredderRecipes.setRecipe(Blocks.STONEBRICK, new ItemStack(Blocks.GRAVEL, 1));
		ShredderRecipes.setRecipe(Blocks.GRAVEL, new ItemStack(Blocks.SAND, 1));
		ShredderRecipes.setRecipe(Blocks.SAND, new ItemStack(ModItems.dust, 2));
		ShredderRecipes.setRecipe(Blocks.BRICK_BLOCK, new ItemStack(Items.CLAY_BALL, 4));
		ShredderRecipes.setRecipe(Blocks.BRICK_STAIRS, new ItemStack(Items.CLAY_BALL, 3));
		ShredderRecipes.setRecipe(Items.FLOWER_POT, new ItemStack(Items.CLAY_BALL, 3));
		ShredderRecipes.setRecipe(Items.BRICK, new ItemStack(Items.CLAY_BALL, 1));
		ShredderRecipes.setRecipe(Blocks.SANDSTONE, new ItemStack(Blocks.SAND, 4));
		ShredderRecipes.setRecipe(Blocks.SANDSTONE_STAIRS, new ItemStack(Blocks.SAND, 6));
		ShredderRecipes.setRecipe(Blocks.CLAY, new ItemStack(Items.CLAY_BALL, 4));
		ShredderRecipes.setRecipe(Blocks.HARDENED_CLAY, new ItemStack(Items.CLAY_BALL, 4));
		ShredderRecipes.setRecipe(Blocks.TNT, new ItemStack(Items.GUNPOWDER, 5));
		ShredderRecipes.setRecipe(Items.BONE, new ItemStack(Items.DYE, 5, 15));
		ShredderRecipes.setRecipe(Blocks.CACTUS, new ItemStack(Items.DYE, 2, 2));
		ShredderRecipes.setRecipe(ModBlocks.stone_gneiss, new ItemStack(Powders.powder_lithium_tiny, 1));
		ShredderRecipes.setRecipe(Powders.powder_lapis, new ItemStack(Powders.powder_cobalt_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_neodymium, new ItemStack(Powders.powder_neodymium_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_cobalt, new ItemStack(Powders.powder_cobalt_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_niobium, new ItemStack(Powders.powder_niobium_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_cerium, new ItemStack(Powders.powder_cerium_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_lanthanium, new ItemStack(Powders.powder_lanthanium_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_actinium, new ItemStack(Powders.powder_actinium_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_boron, new ItemStack(Powders.powder_boron_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_meteorite, new ItemStack(Powders.powder_meteorite_tiny, 1));
		ShredderRecipes.setRecipe(ModBlocks.block_meteor, new ItemStack(Powders.powder_meteorite, 10));
		ShredderRecipes.setRecipe(Items.ENCHANTED_BOOK, new ItemStack(Powders.powder_magic, 1));
		ShredderRecipes.setRecipe(ModItems.arc_electrode_burnt, new ItemStack(Powders.powder_coal, 1));
		ShredderRecipes.setRecipe(ModItems.arc_electrode_desh, new ItemStack(Powders.powder_desh, 2));
		ShredderRecipes.setRecipe(ModBlocks.meteor_polished, new ItemStack(Powders.powder_meteorite, 1));
		ShredderRecipes.setRecipe(ModBlocks.meteor_brick, new ItemStack(Powders.powder_meteorite, 1));
		ShredderRecipes.setRecipe(ModBlocks.meteor_brick_mossy, new ItemStack(Powders.powder_meteorite, 1));
		ShredderRecipes.setRecipe(ModBlocks.meteor_brick_cracked, new ItemStack(Powders.powder_meteorite, 1));
		ShredderRecipes.setRecipe(ModBlocks.meteor_brick_chiseled, new ItemStack(Powders.powder_meteorite, 1));
		ShredderRecipes.setRecipe(ModBlocks.meteor_pillar, new ItemStack(Powders.powder_meteorite, 1));
		ShredderRecipes.setRecipe(ModBlocks.ore_tektite_osmiridium, new ItemStack(Powders.powder_tektite, 1));
		ShredderRecipes.setRecipe(ModBlocks.ore_rare, new ItemStack(Powders.powder_desh_mix, 1));
		ShredderRecipes.setRecipe(Blocks.DIAMOND_ORE, new ItemStack(ModBlocks.gravel_diamond, 2));
		ShredderRecipes.setRecipe(ModBlocks.boxcar, new ItemStack(Powders.powder_steel, 32));
		ShredderRecipes.setRecipe(Ingots.ingot_schrabidate, new ItemStack(Powders.powder_schrabidate, 1));
		ShredderRecipes.setRecipe(ModBlocks.block_schrabidate, new ItemStack(Powders.powder_schrabidate, 9));
		ShredderRecipes.setRecipe(ModItems.coal_infernal, new ItemStack(Powders.powder_coal, 3));
		ShredderRecipes.setRecipe(Items.REEDS, new ItemStack(Items.SUGAR, 2));
		ShredderRecipes.setRecipe(Items.FERMENTED_SPIDER_EYE, new ItemStack(Powders.powder_poison, 3));
		ShredderRecipes.setRecipe(Items.POISONOUS_POTATO, new ItemStack(Powders.powder_poison, 1));

		ShredderRecipes.setRecipe(ModBlocks.dirt_dead, new ItemStack(ModItems.scrap_oil, 1));
		ShredderRecipes.setRecipe(ModBlocks.dirt_oily, new ItemStack(ModItems.scrap_oil, 1));
		ShredderRecipes.setRecipe(ModBlocks.sand_dirty, new ItemStack(ModItems.scrap_oil, 1));
		ShredderRecipes.setRecipe(ModBlocks.sand_dirty_red, new ItemStack(ModItems.scrap_oil, 1));
		ShredderRecipes.setRecipe(ModBlocks.stone_cracked, new ItemStack(ModItems.scrap_oil, 1));
		ShredderRecipes.setRecipe(ModBlocks.stone_porous, new ItemStack(ModItems.scrap_oil, 1));

		ShredderRecipes.setRecipe(ModBlocks.deco_pipe, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_rusted, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_green, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_green_rusted, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_red, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_marked, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_rim, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_rim_rusted, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_rim_green, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_rim_green_rusted, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_rim_red, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_rim_marked, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_quad, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_quad_rusted, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_quad_green, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_quad_green_rusted, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_quad_red, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_quad_marked, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_framed, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_framed_rusted, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_framed_green, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_framed_green_rusted, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_framed_red, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_framed_marked, new ItemStack(Powders.powder_steel, 1));
		
		ShredderRecipes.setRecipe(Ingots.ingot_schraranium, new ItemStack(Nuggies.nugget_schrabidium, 2));
		ShredderRecipes.setRecipe(Crystals.crystal_coal, new ItemStack(Powders.powder_coal, 3));
		ShredderRecipes.setRecipe(Crystals.crystal_iron, new ItemStack(Powders.powder_iron, 3));
		ShredderRecipes.setRecipe(Crystals.crystal_gold, new ItemStack(Powders.powder_gold, 3));
		ShredderRecipes.setRecipe(Crystals.crystal_redstone, new ItemStack(Items.REDSTONE, 8));
		ShredderRecipes.setRecipe(Crystals.crystal_lapis, new ItemStack(Powders.powder_lapis, 12));
		ShredderRecipes.setRecipe(Crystals.crystal_diamond, new ItemStack(Powders.powder_diamond, 3));
		ShredderRecipes.setRecipe(Crystals.crystal_uranium, new ItemStack(Powders.powder_uranium, 3));
		ShredderRecipes.setRecipe(Crystals.crystal_plutonium, new ItemStack(Powders.powder_plutonium, 3));
		ShredderRecipes.setRecipe(Crystals.crystal_thorium, new ItemStack(Powders.powder_thorium, 3));
		ShredderRecipes.setRecipe(Crystals.crystal_titanium, new ItemStack(Powders.powder_titanium, 3));
		ShredderRecipes.setRecipe(Crystals.crystal_sulfur, new ItemStack(ModItems.sulfur, 8));
		ShredderRecipes.setRecipe(Crystals.crystal_niter, new ItemStack(ModItems.niter, 8));
		ShredderRecipes.setRecipe(Crystals.crystal_copper, new ItemStack(Powders.powder_copper, 3));
		ShredderRecipes.setRecipe(Crystals.crystal_tungsten, new ItemStack(Powders.powder_tungsten, 3));
		ShredderRecipes.setRecipe(Crystals.crystal_aluminium, new ItemStack(Powders.powder_aluminium, 3));
		ShredderRecipes.setRecipe(Crystals.crystal_fluorite, new ItemStack(Ingots.fluorite, 8));
		ShredderRecipes.setRecipe(Crystals.crystal_beryllium, new ItemStack(Powders.powder_beryllium, 3));
		ShredderRecipes.setRecipe(Crystals.crystal_schraranium, new ItemStack(Nuggies.nugget_schrabidium, 3));
		ShredderRecipes.setRecipe(Crystals.crystal_lead, new ItemStack(Powders.powder_lead, 3));
		ShredderRecipes.setRecipe(Crystals.crystal_schrabidium, new ItemStack(Powders.powder_schrabidium, 3));
		ShredderRecipes.setRecipe(Crystals.crystal_rare, new ItemStack(Powders.powder_desh_mix, 2));
		ShredderRecipes.setRecipe(Crystals.crystal_phosphorus, new ItemStack(Powders.powder_fire, 8));
		ShredderRecipes.setRecipe(Crystals.crystal_trixite, new ItemStack(Powders.powder_plutonium, 6));
		ShredderRecipes.setRecipe(Crystals.crystal_lithium, new ItemStack(Powders.powder_lithium, 3));
		ShredderRecipes.setRecipe(Crystals.crystal_starmetal, new ItemStack(Powders.powder_dura_steel, 6));
		ShredderRecipes.setRecipe(Crystals.crystal_cobalt, new ItemStack(Powders.powder_cobalt, 3));
		ShredderRecipes.setRecipe(Crystals.crystal_cinnebar, new ItemStack(ModItems.cinnebar, 3));
		ShredderRecipes.setRecipe(Crystals.crystal_asbestos, new ItemStack(Powders.powder_asbestos, 3));
		
		ShredderRecipes.setRecipe(ModBlocks.steel_poles, new ItemStack(Powders.powder_steel_tiny, 3));
		ShredderRecipes.setRecipe(ModBlocks.pole_top, new ItemStack(Powders.powder_tungsten, 4));
		ShredderRecipes.setRecipe(ModBlocks.tape_recorder, new ItemStack(Powders.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.pole_satellite_receiver, new ItemStack(Powders.powder_steel, 5));
		ShredderRecipes.setRecipe(ModBlocks.steel_roof, new ItemStack(Powders.powder_steel_tiny, 13));
		ShredderRecipes.setRecipe(ModBlocks.steel_wall, new ItemStack(Powders.powder_steel_tiny, 13));
		ShredderRecipes.setRecipe(ModBlocks.steel_corner, new ItemStack(Powders.powder_steel_tiny, 26));
		ShredderRecipes.setRecipe(ModBlocks.steel_beam, new ItemStack(Powders.powder_steel_tiny, 3));
		ShredderRecipes.setRecipe(ModBlocks.steel_scaffold, new ItemStack(Powders.powder_steel_tiny, 7));
		ShredderRecipes.setRecipe(ModItems.coil_copper, new ItemStack(Powders.powder_red_copper, 1));
		ShredderRecipes.setRecipe(ModItems.coil_copper_torus, new ItemStack(Powders.powder_red_copper, 2));
		ShredderRecipes.setRecipe(ModItems.coil_advanced_alloy, new ItemStack(Powders.powder_advanced_alloy, 1));
		ShredderRecipes.setRecipe(ModItems.coil_advanced_torus, new ItemStack(Powders.powder_advanced_alloy, 2));
		ShredderRecipes.setRecipe(ModItems.coil_gold, new ItemStack(Powders.powder_gold, 1));
		ShredderRecipes.setRecipe(ModItems.coil_gold_torus, new ItemStack(Powders.powder_gold, 2));
		ShredderRecipes.setRecipe(ModItems.coil_tungsten, new ItemStack(Powders.powder_tungsten, 1));
		ShredderRecipes.setRecipe(ModItems.coil_magnetized_tungsten, new ItemStack(Powders.powder_magnetized_tungsten, 1));
		ShredderRecipes.setRecipe(ModBlocks.crate_iron, new ItemStack(Powders.powder_iron, 8));
		ShredderRecipes.setRecipe(ModBlocks.crate_steel, new ItemStack(Powders.powder_steel, 8));
		ShredderRecipes.setRecipe(ModBlocks.crate_tungsten, new ItemStack(Powders.powder_tungsten, 36));
		ShredderRecipes.setRecipe(Blocks.ANVIL, new ItemStack(Powders.powder_iron, 31));
		ShredderRecipes.setRecipe(ModBlocks.chain, new ItemStack(Powders.powder_steel_tiny, 1));
		ShredderRecipes.setRecipe(ModBlocks.steel_grate, new ItemStack(Powders.powder_steel_tiny, 3));
		ShredderRecipes.setRecipe(ModItems.pipes_steel, new ItemStack(Powders.powder_steel, 27));
		
		ShredderRecipes.setRecipe(Ingots.ingot_schrabidate, new ItemStack(Powders.powder_schrabidate, 1));
		ShredderRecipes.setRecipe(ModBlocks.block_schrabidate, new ItemStack(Powders.powder_schrabidate, 9));
		ShredderRecipes.setRecipe(Ingots.ingot_ac227, new ItemStack(Powders.powder_ac227, 1));
		ShredderRecipes.setRecipe(Ingots.ingot_co60, new ItemStack(Powders.powder_co60, 1));
		ShredderRecipes.setRecipe(Ingots.ingot_ra226, new ItemStack(Powders.powder_ra226, 1));
		ShredderRecipes.setRecipe(Ingots.ingot_sr90, new ItemStack(Powders.powder_sr90, 1));
		ShredderRecipes.setRecipe(Ingots.ingot_ra226, new ItemStack(Powders.powder_ra226, 1));
		ShredderRecipes.setRecipe(Ingots.ingot_pb209, new ItemStack(Powders.powder_pb209, 1));
		ShredderRecipes.setRecipe(Ingots.ingot_astatine, new ItemStack(Powders.powder_astatine, 1));

		ShredderRecipes.setRecipe(Ingots.ingot_tennessine, new ItemStack(Powders.powder_tennessine, 1));
		ShredderRecipes.setRecipe(Ingots.ingot_bromine, new ItemStack(Powders.powder_bromine, 1));
		ShredderRecipes.setRecipe(Ingots.ingot_caesium, new ItemStack(Powders.powder_caesium, 1));
		ShredderRecipes.setRecipe(Ingots.ingot_cerium, new ItemStack(Powders.powder_cerium, 1));
		ShredderRecipes.setRecipe(Ingots.ingot_co60, new ItemStack(Powders.powder_co60, 1));
		ShredderRecipes.setRecipe(Ingots.ingot_strontium, new ItemStack(Powders.powder_strontium, 1));
		ShredderRecipes.setRecipe(Ingots.ingot_sr90, new ItemStack(Powders.powder_sr90, 1));
		ShredderRecipes.setRecipe(Ingots.ingot_iodine, new ItemStack(Powders.powder_iodine, 1));
		ShredderRecipes.setRecipe(Ingots.ingot_i131, new ItemStack(Powders.powder_i131, 1));
		ShredderRecipes.setRecipe(Ingots.ingot_radspice, new ItemStack(Powders.powder_radspice, 1));
		ShredderRecipes.setRecipe(Ingots.ingot_polymer, new ItemStack(Powders.powder_polymer, 1));
		ShredderRecipes.setRecipe(Ingots.ingot_bakelite, new ItemStack(Powders.powder_bakelite, 1));

		ShredderRecipes.setRecipe(ModBlocks.turret_light, new ItemStack(Powders.powder_steel, 16));
		ShredderRecipes.setRecipe(ModBlocks.turret_heavy, new ItemStack(Powders.powder_steel, 16));
		ShredderRecipes.setRecipe(ModBlocks.turret_flamer, new ItemStack(Powders.powder_steel, 16));
		ShredderRecipes.setRecipe(ModBlocks.turret_rocket, new ItemStack(Powders.powder_steel, 16));
		ShredderRecipes.setRecipe(ModBlocks.turret_cwis, new ItemStack(Powders.powder_steel, 16));
		ShredderRecipes.setRecipe(ModBlocks.turret_tau, new ItemStack(Powders.powder_steel, 16));
		ShredderRecipes.setRecipe(Armory.turret_light_ammo, new ItemStack(Items.GUNPOWDER, 4));
		ShredderRecipes.setRecipe(Armory.turret_heavy_ammo, new ItemStack(Items.GUNPOWDER, 4));
		ShredderRecipes.setRecipe(Armory.turret_flamer_ammo, new ItemStack(Items.GUNPOWDER, 4));
		ShredderRecipes.setRecipe(Armory.turret_rocket_ammo, new ItemStack(Items.GUNPOWDER, 4));
		ShredderRecipes.setRecipe(Armory.turret_cwis_ammo, new ItemStack(Items.GUNPOWDER, 4));
		ShredderRecipes.setRecipe(Armory.turret_tau_ammo, new ItemStack(Powders.powder_uranium, 4));
		ShredderRecipes.setRecipe(ModBlocks.ore_nether_coal, new ItemStack(ModItems.coal_infernal, 2));
		ShredderRecipes.setRecipe(ModBlocks.ore_cinnebar, new ItemStack(ModItems.cinnebar, 2));
		
		for(int i = 0; i < 16; i++) {
			ShredderRecipes.setRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, i), new ItemStack(Items.CLAY_BALL, 4));
			ShredderRecipes.setRecipe(new ItemStack(Blocks.WOOL, 1, i), new ItemStack(Items.STRING, 4));
		}

		for(Integer oreMeta : BedrockOreRegistry.oreIndexes.keySet()) {
			int type = ItemBedrockOre.getOutType(oreMeta);
			if(type == 0 || type == 1){
				ShredderRecipes.setRecipe(new ItemStack(ModItems.ore_bedrock, 1, oreMeta), new ItemStack(ModItems.ore_bedrock_enriched, 2, oreMeta));
				ShredderRecipes.setRecipe(new ItemStack(ModItems.ore_bedrock_cleaned, 1, oreMeta), new ItemStack(ModItems.ore_bedrock_enriched, 2, oreMeta));
				ShredderRecipes.setRecipe(new ItemStack(ModItems.ore_bedrock_deepcleaned, 1, oreMeta), new ItemStack(ModItems.ore_bedrock_enriched, 2, oreMeta));
				ShredderRecipes.setRecipe(new ItemStack(ModItems.ore_bedrock_nitrated, 1, oreMeta), new ItemStack(ModItems.ore_bedrock_enriched, 2, oreMeta));
				ShredderRecipes.setRecipe(new ItemStack(ModItems.ore_bedrock_seared, 1, oreMeta), new ItemStack(ModItems.ore_bedrock_enriched, 2, oreMeta));
				ShredderRecipes.setRecipe(new ItemStack(ModItems.ore_bedrock_perfect, 1, oreMeta), new ItemStack(ModItems.ore_bedrock_enriched, 2, oreMeta));
				ShredderRecipes.setRecipe(new ItemStack(ModItems.ore_bedrock_enriched, 1, oreMeta), ItemBedrockOre.getOut(oreMeta, 2));
			}
		}
		setRecipe("crystalEnder", "dustEnder");
		setRecipe("crystalFluix", "dustFluix");
		setRecipe("crystalCertusQuartz", "dustCertusQuartz");
		setRecipe("enderpearl", "dustEnderPearl");
	}
	
	public static ItemStack getDustByName(String name) {
		
		return getOredictByName("dust" + name);
	}

	public static ItemStack getTinyDustByName(String name) {
		
		return getOredictByName("dustTiny" + name);
	}

	public static ItemStack getOredictByName(String name) {
		
		List<ItemStack> matches = OreDictionary.getOres(name);
		if(matches != null && !matches.isEmpty())
			return matches.get(0).copy();
		
		return new ItemStack(ModItems.scrap);
	}
	
	public static void setRecipe(Item in, ItemStack out) {
		
		shredderRecipes.put(new ComparableStack(in), out);
	}
	
	public static void setRecipe(Block in, ItemStack out) {
		
		shredderRecipes.put(new ComparableStack(in), out);
	}
	
	public static void setRecipe(ItemStack in, ItemStack out) {
		
		shredderRecipes.put(new ComparableStack(in), out);
	}

	public static void setRecipe(String in, String out) {
		if(OreDictionary.doesOreNameExist(in) && OreDictionary.doesOreNameExist(out)) 
			setRecipe(getOredictByName(in), getOredictByName(out));
	}

	public static void removeRecipe(ItemStack in) {
		
		shredderRecipes.remove(new ComparableStack(in));
	}
	
	public static List<ShredderRecipe> getShredderRecipes() {
		
		if(jeiShredderRecipes == null){
			jeiShredderRecipes = new ArrayList<ShredderRecipe>();
			for(Entry<ComparableStack, ItemStack> e : shredderRecipes.entrySet()){
				jeiShredderRecipes.add(new ShredderRecipe(e.getKey().toStack(), e.getValue()));
			}
		}
		
		return jeiShredderRecipes;
	}
	
	public static ItemStack getShredderResult(ItemStack stack) {
		
		if(stack == null || stack.getItem() == null || stack.isEmpty())
			return new ItemStack(ModItems.scrap);
		
		ItemStack sta = shredderRecipes.get(new ComparableStack(stack).makeSingular());
		
		return sta == null ? new ItemStack(ModItems.scrap) : sta;
	}
	
	public static class ShredderRecipe implements IRecipeWrapper {
		
		private final ItemStack input;
		private final ItemStack output;
		
		public ShredderRecipe(ItemStack input, ItemStack output) {
			this.input = input;
			this.output = output; 
		}
		
		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInput(VanillaTypes.ITEM, input);
			ingredients.setOutput(VanillaTypes.ITEM, output);
		}
		
	}
}
