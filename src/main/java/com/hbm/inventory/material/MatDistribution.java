package com.hbm.inventory.material;

import static com.hbm.inventory.OreDictManager.*;
import static com.hbm.inventory.material.Mats.*;
import static com.hbm.inventory.material.MaterialShapes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.items.ModItems;

import com.hbm.items.ModItems.BedrockOreV2;
import com.hbm.items.ModItems.Foundry;
import com.hbm.items.ModItems.Materials.Ingots;
import com.hbm.items.ModItems.Materials.Powders;
import com.hbm.items.ModItems.RetroRods;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MatDistribution {

	public static void registerDefaults() {
		//vanilla crap
		registerOre("stone", MAT_STONE, BLOCK.q(1));
		registerOre("gravel", MAT_STONE, BLOCK.q(1));
		registerOre("cobblestone", MAT_STONE, BLOCK.q(1));
		registerEntry(Blocks.STONE_BUTTON, MAT_STONE, BLOCK.q(1));
		registerEntry(Blocks.STONE_PRESSURE_PLATE, MAT_STONE, BLOCK.q(2));
		registerEntry(Blocks.LEVER, MAT_STONE, BLOCK.q(1), MAT_CARBON, QUANTUM.q(3));
		registerEntry(Blocks.REDSTONE_TORCH, MAT_REDSTONE, INGOT.q(1), MAT_CARBON, QUANTUM.q(3));
		registerEntry(Blocks.TRIPWIRE_HOOK, MAT_IRON, QUANTUM.q(36), MAT_CARBON, QUANTUM.q(5));

		registerOre("logWood", MAT_CARBON, NUGGET.q(1));
		registerOre("plankWood", MAT_CARBON, QUANTUM.q(7));
		registerOre("slabWood", MAT_CARBON, QUANTUM.q(3));
		registerOre("stairWood", MAT_CARBON, QUANTUM.q(5));
		registerOre("stickWood", MAT_CARBON, QUANTUM.q(3));
		registerOre("doorWood", MAT_CARBON, QUANTUM.q(21));
		registerOre("fenceWood", MAT_CARBON, QUANTUM.q(8));
		registerOre("fenceGateWood", MAT_CARBON, QUANTUM.q(26));
		registerOre("chestWood", MAT_CARBON, QUANTUM.q(56));

		registerOre("workbench", MAT_CARBON, QUANTUM.q(28));
		registerOre("treeSapling", MAT_CARBON, QUANTUM.q(5));
		registerOre("treeLeaves", MAT_CARBON, QUANTUM.q(4));
		registerOre("vine", MAT_CARBON, QUANTUM.q(4));
		registerEntry(Blocks.NOTEBLOCK, MAT_REDSTONE, INGOT.q(1), MAT_CARBON, QUANTUM.q(56));
		registerEntry(Blocks.JUKEBOX, MAT_CARBON, QUANTUM.q(65));
		registerEntry(Blocks.LADDER, MAT_CARBON, QUANTUM.q(7));
		registerEntry(Blocks.TRAPDOOR, MAT_CARBON, QUANTUM.q(21));
		registerEntry(Blocks.WOODEN_BUTTON, MAT_CARBON, QUANTUM.q(7));
		registerEntry(Blocks.WOODEN_PRESSURE_PLATE, MAT_CARBON, QUANTUM.q(14));
		registerEntry(Blocks.DROPPER, MAT_STONE, BLOCK.q(7), MAT_REDSTONE, INGOT.q(1));
		registerEntry(Blocks.OBSERVER, MAT_STONE, BLOCK.q(7), MAT_REDSTONE, INGOT.q(2));

		registerOre(KEY_SAND,			Mats.MAT_SILICON, MaterialShapes.NUGGET.q(1));
		registerEntry(new ComparableStack(Items.FLINT),		Mats.MAT_SILICON, MaterialShapes.INGOT.q(1, 2));
		registerOre(QUARTZ.gem(),		Mats.MAT_SILICON, MaterialShapes.NUGGET.q(3));
		registerOre(QUARTZ.dust(),		Mats.MAT_SILICON, MaterialShapes.NUGGET.q(3));
		registerOre(QUARTZ.block(),		Mats.MAT_SILICON, MaterialShapes.NUGGET.q(12));
		registerOre(FIBER.ingot(),		Mats.MAT_SILICON, MaterialShapes.INGOT.q(1, 2));
		registerOre(FIBER.block(),		Mats.MAT_SILICON, MaterialShapes.INGOT.q(9, 2));
		registerOre(ASBESTOS.ingot(),	Mats.MAT_SILICON, MaterialShapes.INGOT.q(1, 2));
		registerOre(ASBESTOS.dust(),	Mats.MAT_SILICON, MaterialShapes.INGOT.q(1, 2));
		registerOre(ASBESTOS.block(),	Mats.MAT_SILICON, MaterialShapes.INGOT.q(9, 2));


		registerEntry(Blocks.MOSSY_COBBLESTONE, MAT_STONE, BLOCK.q(1), MAT_CARBON, QUANTUM.q(4));
		registerOre("oreDiamond", MAT_STONE, BLOCK.q(1), MAT_CARBON, INGOT.q(1));

		registerEntry(Items.IRON_DOOR, MAT_IRON, INGOT.q(2));
		registerEntry(Blocks.IRON_TRAPDOOR, MAT_IRON, INGOT.q(4));
		registerEntry(Blocks.OBSIDIAN, MAT_OBSIDIAN, BLOCK.q(1));
		registerEntry(Blocks.ENDER_CHEST, MAT_OBSIDIAN, BLOCK.q(8));
		registerEntry(Blocks.ENCHANTING_TABLE, MAT_OBSIDIAN, BLOCK.q(4), MAT_CARBON, INGOT.q(2));
		registerEntry(Blocks.ANVIL, MAT_IRON, INGOT.q(31));
		registerEntry(Blocks.IRON_BARS, MAT_IRON, INGOT.q(6, 16));
		registerEntry(Blocks.HOPPER, MAT_IRON, INGOT.q(5));
		registerEntry(Items.CAULDRON, MAT_IRON, INGOT.q(7));
		registerEntry(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, MAT_GOLD, INGOT.q(2));
		registerEntry(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, MAT_IRON, INGOT.q(2));

		registerEntry(Blocks.RAIL, MAT_IRON, INGOT.q(6, 16), MAT_CARBON, QUANTUM.q(3));
		registerEntry(Blocks.GOLDEN_RAIL, MAT_GOLD, INGOT.q(1), MAT_REDSTONE, DUST.q(1, 6), MAT_CARBON, QUANTUM.q(3));
		registerEntry(Blocks.DETECTOR_RAIL, MAT_STONE, BLOCK.q(2), MAT_IRON, INGOT.q(1), MAT_REDSTONE, DUST.q(1, 6));
		registerEntry(Blocks.ACTIVATOR_RAIL, MAT_IRON, INGOT.q(1), MAT_REDSTONE, DUST.q(1, 6), MAT_CARBON, QUANTUM.q(9));
		registerEntry(Blocks.FURNACE, MAT_STONE, BLOCK.q(8));

		registerEntry(Blocks.PISTON, MAT_STONE, INGOT.q(4), MAT_IRON, INGOT.q(1), MAT_REDSTONE, DUST.q(1), MAT_CARBON, QUANTUM.q(21));
		registerEntry(Blocks.STICKY_PISTON, MAT_STONE, INGOT.q(4), MAT_IRON, INGOT.q(1), MAT_REDSTONE, DUST.q(1), MAT_CARBON, QUANTUM.q(21));

		registerOre("bone", MAT_CALCIUM, QUANTUM.q(3), MAT_CARBON, QUANTUM.q(3));
		registerEntry(new ItemStack(Items.DYE, 1, 15), MAT_CALCIUM, QUANTUM.q(1));
		registerEntry(Blocks.BONE_BLOCK, MAT_CALCIUM, QUANTUM.q(27), MAT_CARBON, QUANTUM.q(27));
		registerOre("dye", MAT_CARBON, QUANTUM.q(1));
		registerEntry(Items.STRING, MAT_CARBON, QUANTUM.q(3));
		registerEntry(Items.CAULDRON, MAT_IRON, QUANTUM.q(7));

		registerEntry(Blocks.FURNACE, MAT_STONE, BLOCK.q(8));

		registerEntry(Items.MINECART, MAT_IRON, INGOT.q(5));
		registerEntry(Items.HOPPER_MINECART, MAT_IRON, INGOT.q(10));
		registerEntry(Items.FURNACE_MINECART, MAT_IRON, INGOT.q(5), MAT_STONE, BLOCK.q(8));
		registerEntry(Items.BUCKET, MAT_IRON, INGOT.q(3));
		registerEntry(Items.COMPASS, MAT_IRON, INGOT.q(4), MAT_REDSTONE, DUST.q(1));
		registerEntry(Items.CLOCK, MAT_GOLD, INGOT.q(4), MAT_REDSTONE, DUST.q(1));

		//castables
		registerEntry(ModItems.blade_titanium,				MAT_TITANIUM,		INGOT.q(2));
		registerEntry(ModItems.blade_tungsten,				MAT_TUNGSTEN,		INGOT.q(2));

		registerEntry(ModItems.blades_aluminum,				MAT_ALUMINIUM,		INGOT.q(4));
		registerEntry(ModItems.blades_gold,					MAT_GOLD,			INGOT.q(4));
		registerEntry(ModItems.blades_iron,					MAT_IRON,			INGOT.q(4));
		registerEntry(ModItems.blades_steel,				MAT_STEEL,			INGOT.q(4));
		registerEntry(ModItems.blades_titanium,				MAT_TITANIUM, 		INGOT.q(4));
		registerEntry(ModItems.blades_advanced_alloy,		MAT_ALLOY,			INGOT.q(4));
		registerEntry(ModItems.blades_combine_steel,		MAT_CMB,			INGOT.q(4));
		registerEntry(ModItems.blades_schrabidium,			MAT_SCHRABIDIUM,	INGOT.q(4));

		registerEntry(ModItems.stamp_stone_flat,			MAT_STONE,			INGOT.q(3), MAT_REDSTONE, DUST.q(1));
		registerEntry(ModItems.stamp_iron_flat,				MAT_IRON,			INGOT.q(3), MAT_REDSTONE, DUST.q(1));
		registerEntry(ModItems.stamp_steel_flat,			MAT_STEEL,			INGOT.q(3), MAT_REDSTONE, DUST.q(1));
		registerEntry(ModItems.stamp_titanium_flat,			MAT_TITANIUM,		INGOT.q(3), MAT_REDSTONE, DUST.q(1));
		registerEntry(ModItems.stamp_obsidian_flat,			MAT_OBSIDIAN,		INGOT.q(3), MAT_REDSTONE, DUST.q(1));
		registerEntry(ModItems.stamp_schrabidium_flat,		MAT_SCHRABIDIUM,	INGOT.q(3), MAT_REDSTONE, DUST.q(1));

		registerEntry(ModItems.stamp_stone_plate,			MAT_STONE,			INGOT.q(3), MAT_REDSTONE, DUST.q(1));
		registerEntry(ModItems.stamp_iron_plate,			MAT_IRON,			INGOT.q(3), MAT_REDSTONE, DUST.q(1));
		registerEntry(ModItems.stamp_steel_plate,			MAT_STEEL,			INGOT.q(3), MAT_REDSTONE, DUST.q(1));
		registerEntry(ModItems.stamp_titanium_plate,		MAT_TITANIUM,		INGOT.q(3), MAT_REDSTONE, DUST.q(1));
		registerEntry(ModItems.stamp_obsidian_plate,		MAT_OBSIDIAN,		INGOT.q(3), MAT_REDSTONE, DUST.q(1));
		registerEntry(ModItems.stamp_schrabidium_plate,		MAT_SCHRABIDIUM,	INGOT.q(3), MAT_REDSTONE, DUST.q(1));

		registerEntry(ModItems.stamp_stone_wire,			MAT_STONE,			INGOT.q(3), MAT_REDSTONE, DUST.q(1));
		registerEntry(ModItems.stamp_iron_wire,				MAT_IRON,			INGOT.q(3), MAT_REDSTONE, DUST.q(1));
		registerEntry(ModItems.stamp_steel_wire,			MAT_STEEL,			INGOT.q(3), MAT_REDSTONE, DUST.q(1));
		registerEntry(ModItems.stamp_titanium_wire,			MAT_TITANIUM,		INGOT.q(3), MAT_REDSTONE, DUST.q(1));
		registerEntry(ModItems.stamp_obsidian_wire,			MAT_OBSIDIAN,		INGOT.q(3), MAT_REDSTONE, DUST.q(1));
		registerEntry(ModItems.stamp_schrabidium_wire,		MAT_SCHRABIDIUM,	INGOT.q(3), MAT_REDSTONE, DUST.q(1));

		registerEntry(ModItems.stamp_stone_circuit,			MAT_STONE,			INGOT.q(3), MAT_REDSTONE, DUST.q(1));
		registerEntry(ModItems.stamp_iron_circuit,			MAT_IRON,			INGOT.q(3), MAT_REDSTONE, DUST.q(1));
		registerEntry(ModItems.stamp_steel_circuit,			MAT_STEEL,			INGOT.q(3), MAT_REDSTONE, DUST.q(1));
		registerEntry(ModItems.stamp_titanium_circuit,		MAT_TITANIUM,		INGOT.q(3), MAT_REDSTONE, DUST.q(1));
		registerEntry(ModItems.stamp_obsidian_circuit,		MAT_OBSIDIAN,		INGOT.q(3), MAT_REDSTONE, DUST.q(1));
		registerEntry(ModItems.stamp_schrabidium_circuit,	MAT_SCHRABIDIUM,	INGOT.q(3), MAT_REDSTONE, DUST.q(1));

		registerEntry(ModItems.pipes_steel,					MAT_STEEL,			BLOCK.q(3));

		registerEntry(RetroRods.rod_empty,					MAT_STEEL,			INGOT.q(6, 16), MAT_LEAD,	INGOT.q(2, 16));
		registerEntry(RetroRods.rod_dual_empty,				MAT_STEEL,			INGOT.q(6, 8), MAT_LEAD,	INGOT.q(2, 8));
		registerEntry(RetroRods.rod_quad_empty,				MAT_STEEL,			INGOT.q(6, 4), MAT_LEAD,	INGOT.q(2, 4));

		registerEntry(ModItems.fluid_barrel_full,			MAT_STEEL,			INGOT.q(3), MAT_ALUMINIUM,	INGOT.q(1));
		registerEntry(ModItems.fluid_tank_full,				MAT_ALUMINIUM,		INGOT.q(6, 8), MAT_IRON,	INGOT.q(2, 8));
		//registerEntry(ModItems.fluid_tank_lead_full,		MAT_LEAD, 			INGOT.q(3), MAT_U238, 		BILLET.q(1), MAT_ALUMINIUM,	INGOT.q(6, 16), MAT_IRON,	INGOT.q(2, 16));
		registerEntry(ModItems.cell,						MAT_STEEL,			INGOT.q(1));
		registerEntry(ModItems.gas_canister,				MAT_STEEL,			INGOT.q(2), MAT_COPPER,		INGOT.q(1, 2));
		registerEntry(ModItems.canister_generic,			MAT_ALUMINIUM,		INGOT.q(2), MAT_STEEL,		INGOT.q(1, 2));

		registerEntry(ModItems.mirror_tool,					MAT_IRON,			INGOT.q(2), MAT_ALUMINIUM,	INGOT.q(2));
		registerEntry(ModItems.rbmk_tool,					MAT_IRON,			INGOT.q(2), MAT_LEAD,		INGOT.q(2));

		registerEntry(ModItems.syringe_empty,				MAT_IRON,			INGOT.q(1, 6), MAT_STEEL,	INGOT.q(1, 6));
		registerEntry(ModItems.syringe_metal_empty,			MAT_IRON,			INGOT.q(1, 6), MAT_STEEL,	INGOT.q(6, 96), MAT_LEAD,	INGOT.q(2, 96));

		registerEntry(ModItems.particle_empty, 				MAT_STEEL, INGOT.q(2), MAT_TUNGSTEN, INGOT.q(1));

		registerEntry(Foundry.mold_base,					MAT_IRON,			INGOT.q(1));
		for(int i = 0; i < 22; i++) registerEntry(new ItemStack(Foundry.mold,1, i),						MAT_IRON,			INGOT.q(1));

		registerOre(OreDictManager.IRON.ore(), MAT_IRON, INGOT.q(2), MAT_TITANIUM, NUGGET.q(3), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.TI.ore(), MAT_TITANIUM, INGOT.q(2), MAT_IRON, NUGGET.q(3), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.W.ore(), MAT_TUNGSTEN, INGOT.q(2), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.AL.ore(), MAT_ALUMINIUM, INGOT.q(2), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.COAL.ore(), MAT_CARBON, GEM.q(3), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.GOLD.ore(), MAT_GOLD, INGOT.q(2), MAT_LEAD, NUGGET.q(3), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.EMERALD.ore(), MAT_BERYLLIUM, GEM.q(1), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.U.ore(), MAT_URANIUM, INGOT.q(2), MAT_LEAD, NUGGET.q(3), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.TH232.ore(), MAT_THORIUM, INGOT.q(2), MAT_URANIUM, NUGGET.q(3), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.CU.ore(), MAT_COPPER, INGOT.q(2), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.PB.ore(), MAT_LEAD, INGOT.q(2), MAT_GOLD, NUGGET.q(1), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.BE.ore(), MAT_BERYLLIUM, INGOT.q(2), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.CO.ore(), MAT_COBALT, INGOT.q(1), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.REDSTONE.ore(), MAT_REDSTONE, INGOT.q(4), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.SA326.ore(), MAT_SCHRABIDIUM, INGOT.q(2), MAT_SOLINIUM, NUGGET.q(3), MAT_STONE, QUART.q(1));

		registerOre(OreDictManager.HEMATITE.ore(), MAT_HEMATITE, INGOT.q(1));
		registerOre(OreDictManager.MALACHITE.ore(), MAT_MALACHITE, INGOT.q(1));

		registerEntry(SI.billet(), MAT_SILICON, NUGGET.q(6));

		// registerEntry(DictFrame.fromOne(ModBlocks.stone_resource, EnumStoneType.LIMESTONE), MAT_FLUX, DUST.q(10));
		registerEntry(Powders.powder_flux, MAT_FLUX, DUST.q(1));
		registerEntry(new ItemStack(Items.COAL, 1, 1), MAT_CARBON, GEM.q(1, 3));

		registerEntry(ModItems.coil_copper, MAT_IRON, INGOT.q(1), MAT_MINGRADE, INGOT.q(1));
		registerEntry(ModItems.coil_copper_torus, MAT_IRON, INGOT.q(2), MAT_MINGRADE, INGOT.q(2));

		registerEntry(ModItems.coil_gold, MAT_IRON, INGOT.q(1), MAT_GOLD, INGOT.q(1));
		registerEntry(ModItems.coil_gold_torus, MAT_IRON, INGOT.q(2), MAT_GOLD, INGOT.q(2));

		registerEntry(ModItems.coil_advanced_alloy, MAT_IRON, INGOT.q(1), MAT_ALLOY, INGOT.q(1));
		registerEntry(ModItems.coil_advanced_torus, MAT_IRON, INGOT.q(2), MAT_ALLOY, INGOT.q(2));

		registerEntry(ModItems.coil_tungsten, MAT_IRON, INGOT.q(1), MAT_TUNGSTEN, INGOT.q(1));
		registerEntry(ModItems.coil_magnetized_tungsten, MAT_IRON, INGOT.q(1), MAT_MAGTUNG, INGOT.q(1));

		registerEntry(ModBlocks.rail_highspeed, MAT_STEEL, INGOT.q(6, 16), MAT_IRON, INGOT.q(1, 16));
		registerEntry(ModBlocks.rail_booster, MAT_STEEL, INGOT.q(4, 6), MAT_IRON, INGOT.q(3, 6), MAT_MINGRADE, INGOT.q(3, 6));

		registerEntry(ModBlocks.block_schrabidium_cluster, MAT_STAR, INGOT.q(4), MAT_SCHRABIDIUM, INGOT.q(4), MAT_SCHRABIDATE, INGOT.q(1));

		registerEntry(ModBlocks.machine_press, MAT_IRON, INGOT.q(16), MAT_STONE, INGOT.q(12), MAT_REDSTONE, DUST.q(1));

		registerEntry(ModBlocks.crate_iron, MAT_IRON, INGOT.q(8));
		registerEntry(ModBlocks.crate_steel, MAT_STEEL, INGOT.q(8));
		registerEntry(ModBlocks.crate_tungsten, MAT_TUNGSTEN, BLOCK.q(4), MAT_COPPER, INGOT.q(24), MAT_STEEL, INGOT.q(8));

		registerEntry(ModItems.pellet_buckshot, MAT_LEAD, NUGGET.q(6));
		registerEntry(ModItems.pellet_flechette, MAT_LEAD, NUGGET.q(5));
		registerEntry(ModItems.pellet_canister, MAT_IRON, INGOT.q(3, 2));
		registerEntry(ModItems.pellet_claws, MAT_STEEL, INGOT.q(5));

		registerEntry(ModItems.ring_starmetal, MAT_STAR, INGOT.q(4));

		registerEntry(ModItems.turbine_titanium, MAT_TITANIUM, INGOT.q(16), MAT_STEEL, INGOT.q(1));
		registerEntry(ModItems.turbine_tungsten, MAT_TUNGSTEN, INGOT.q(16), MAT_DURA, INGOT.q(1));

		registerEntry(ModItems.drillbit_steel, MAT_STEEL, INGOT.q(12), MAT_TUNGSTEN, INGOT.q(4));
		registerEntry(ModItems.drillbit_steel_diamond, MAT_CARBON, INGOT.q(16), MAT_STEEL, INGOT.q(12), MAT_TUNGSTEN, INGOT.q(4));

		registerEntry(ModItems.drillbit_hss, MAT_DURA, INGOT.q(12), MAT_TITANIUM, INGOT.q(8));
		registerEntry(ModItems.drillbit_hss_diamond, MAT_CARBON, INGOT.q(24), MAT_DURA, INGOT.q(12), MAT_TITANIUM, INGOT.q(8));

		registerEntry(ModItems.drillbit_tcalloy, MAT_TCALLOY, INGOT.q(20), MAT_DESH, INGOT.q(12));
		registerEntry(ModItems.drillbit_tcalloy_diamond, MAT_CARBON, INGOT.q(48), MAT_TCALLOY, INGOT.q(20), MAT_DESH, INGOT.q(12));

		registerEntry(ModItems.drillbit_ferro, MAT_FERRO, INGOT.q(24), MAT_CDALLOY, INGOT.q(12), MAT_BISMUTH, INGOT.q(4));
		registerEntry(ModItems.drillbit_ferro_diamond, MAT_CARBON, INGOT.q(64), MAT_FERRO, INGOT.q(24), MAT_CDALLOY, INGOT.q(12), MAT_BISMUTH, INGOT.q(4));

		registerEntry(ModItems.drillbit_desh, MAT_DESH, INGOT.q(16), MAT_NIOBIUM, INGOT.q(4));
		registerEntry(ModItems.drillbit_desh_diamond, MAT_CARBON, INGOT.q(32), MAT_DESH, INGOT.q(16), MAT_NIOBIUM, INGOT.q(4));

		registerEntry(ModItems.drillbit_dnt, MAT_STEEL, INGOT.q(8192), MAT_CARBON, INGOT.q(4096), MAT_DNT, INGOT.q(32), MAT_GHIORSIUM, INGOT.q(24));
		registerEntry(ModItems.drillbit_dnt_diamond, MAT_STEEL, INGOT.q(8192), MAT_CARBON, INGOT.q(4096), MAT_DNT, INGOT.q(32), MAT_GHIORSIUM, INGOT.q(24));

		registerEntry(Ingots.ingot_chainsteel, MAT_STEEL, INGOT.q(1024), MAT_CARBON, INGOT.q(512));

		registerEntry(ModItems.pin, MAT_COPPER, WIRE.q(3));

		registerEntry(ModItems.motor, MAT_IRON, INGOT.q(5, 2), MAT_MINGRADE, QUANTUM.q(225, 2));

		registerEntry(ModItems.man_core, MAT_PU239, NUGGET.q(8), MAT_BERYLLIUM, NUGGET.q(2));
		registerEntry(ModItems.gadget_core, MAT_PU239, NUGGET.q(7), MAT_U238, NUGGET.q(3));
		registerEntry(ModItems.boy_target, MAT_U238, NUGGET.q(7));
		registerEntry(ModItems.boy_bullet, MAT_U238, NUGGET.q(3));
		registerEntry(ModItems.mike_core, MAT_LEAD, INGOT.q(6), MAT_U238, NUGGET.q(24));

		registerEntry(ModBlocks.steel_beam, MAT_STEEL, INGOT.q(3, 8));
		registerEntry(ModBlocks.steel_grate, MAT_LEAD, INGOT.q(3, 8));
		registerEntry(ModBlocks.solar_mirror, MAT_STEEL, INGOT.q(1), MAT_ALUMINIUM, INGOT.q(1));
		// registerEntry(DictFrame.fromOne(ModItems.powder_ash, EnumAshType.WOOD), MAT_CARBON, NUGGET.q(1));
		// registerEntry(DictFrame.fromOne(ModItems.powder_ash, EnumAshType.COAL), MAT_CARBON, NUGGET.q(2));
		// registerEntry(DictFrame.fromOne(ModItems.powder_ash, EnumAshType.MISC), MAT_CARBON, NUGGET.q(1));

		for (Entry<Integer,NTMMaterial> entry : matById.entrySet()) {
			if (BedrockOreV2.fragment.isMaterialValid(entry.getValue()))
				registerEntry(new ComparableStack(BedrockOreV2.fragment,1,entry.getKey()),entry.getValue(),DUSTTINY.q(1));
		}
	}
	
	public static void registerEntry(Object key, Object... matDef) {
		ComparableStack comp = null;

		if(key instanceof Item) comp = new ComparableStack((Item) key);
		if(key instanceof Block) comp = new ComparableStack((Block) key);
		if(key instanceof ItemStack) comp = new ComparableStack((ItemStack) key);
		if (key instanceof ComparableStack) comp = (ComparableStack)key;
		
		if(comp == null) return;
		if(matDef.length % 2 == 1) return;
		
		List<MaterialStack> stacks = new ArrayList<MaterialStack>();
		
		for(int i = 0; i < matDef.length; i += 2) {
			stacks.add(new MaterialStack((NTMMaterial) matDef[i], (int) matDef[i + 1]));
		}
		
		if(stacks.isEmpty()) return;
		
		materialEntries.put(comp, stacks);
	}
	
	public static void registerOre(String key, Object... matDef) {
		if(matDef.length % 2 == 1) return;
		
		List<MaterialStack> stacks = new ArrayList<MaterialStack>();
		
		for(int i = 0; i < matDef.length; i += 2) {
			stacks.add(new MaterialStack((NTMMaterial) matDef[i], (int) matDef[i + 1]));
		}
		
		if(stacks.isEmpty()) return;
		
		materialOreEntries.put(key, stacks);
	}

//	public static List<MaterialStack> getMaterialList(ItemStack stack, List<MaterialStack> materials){
//		if(materials == null) materials = new ArrayList<MaterialStack>();
//		if(stack == null || stack.isEmpty()) return materials;
//
//	}
//
//	public static void registerAllSmeltingFromCrafting() {
//		for (IRecipe recipe : ForgeRegistries.RECIPES.getValuesCollection()) {
//			ItemStack output = recipe.getRecipeOutput();
//			if(materialEntries.containsKey(new ComparableStack(output)))
//				continue;
//			List<Ingredient> inputs = recipe.getIngredients();
//			List<MaterialStack> stacks = new ArrayList<MaterialStack>();
//			for (Ingredient input : inputs){
//				ItemStack[] inputVariants = input.getMatchingStacks();
//			}
//		}
//	}
}
