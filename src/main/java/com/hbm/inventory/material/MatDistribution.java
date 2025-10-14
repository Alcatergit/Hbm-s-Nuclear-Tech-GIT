package com.hbm.inventory.material;

import static com.hbm.inventory.OreDictManager.*;
import static com.hbm.inventory.material.Mats.*;
import static com.hbm.inventory.material.MaterialShapes.*;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

import com.hbm.items.machine.ItemCircuit;
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

		registerOre(KEY_SAND,			MAT_SILICON, NUGGET.q(1));
		registerEntry(Items.FLINT,		MAT_SILICON, INGOT.q(1, 2));
		registerOre(QUARTZ.gem(),		MAT_SILICON, NUGGET.q(3));
		registerOre(QUARTZ.dust(),		MAT_SILICON, NUGGET.q(3));
		registerOre(QUARTZ.block(),		MAT_SILICON, NUGGET.q(12));
		registerOre(FIBER.ingot(),		MAT_SILICON, INGOT.q(1, 2));
		registerOre(FIBER.block(),		MAT_SILICON, INGOT.q(9, 2));
		registerOre(ASBESTOS.ingot(),	MAT_SILICON, INGOT.q(1, 2));
		registerOre(ASBESTOS.dust(),	MAT_SILICON, INGOT.q(1, 2));
		registerOre(ASBESTOS.block(),	MAT_SILICON, INGOT.q(9, 2));


		registerEntry(Blocks.MOSSY_COBBLESTONE, MAT_STONE, BLOCK.q(1), MAT_CARBON, QUANTUM.q(4));
		registerOre("oreDiamond", MAT_STONE, BLOCK.q(1), MAT_CARBON, INGOT.q(1));

		registerEntry(Items.IRON_DOOR, MAT_IRON, INGOT.q(2));
		registerEntry(Blocks.IRON_TRAPDOOR, MAT_IRON, INGOT.q(4));
		registerEntry(Blocks.OBSIDIAN, MAT_OBSIDIAN, BLOCK.q(1));
		registerEntry(Blocks.ENDER_CHEST, MAT_OBSIDIAN, BLOCK.q(8));
		registerEntry(Blocks.ENCHANTING_TABLE, MAT_OBSIDIAN, BLOCK.q(4), MAT_CARBON, INGOT.q(2));
		registerEntry(Blocks.ANVIL, MAT_IRON, INGOT.q(31));
        registerEntry(new ItemStack(Blocks.ANVIL, 1, 1), MAT_IRON, INGOT.q(16));
        registerEntry(new ItemStack(Blocks.ANVIL, 1, 2), MAT_IRON, INGOT.q(4));
        registerEntry(Blocks.IRON_BARS, MAT_IRON, INGOT.q(6, 16));
		registerEntry(Blocks.HOPPER, MAT_IRON, INGOT.q(5));
		registerEntry(Items.CAULDRON, MAT_IRON, INGOT.q(7));
		registerEntry(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, MAT_GOLD, INGOT.q(2));
		registerEntry(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, MAT_IRON, INGOT.q(2));
        registerEntry(Blocks.COBBLESTONE_WALL, MAT_STONE, BLOCK.q(1));


        registerEntry(Blocks.RAIL, MAT_IRON, INGOT.q(6, 16), MAT_CARBON, QUANTUM.q(3));
		registerEntry(Blocks.GOLDEN_RAIL, MAT_GOLD, INGOT.q(1), MAT_REDSTONE, DUST.q(1, 6), MAT_CARBON, QUANTUM.q(3));
		registerEntry(Blocks.DETECTOR_RAIL, MAT_STONE, BLOCK.q(2), MAT_IRON, INGOT.q(1), MAT_REDSTONE, DUST.q(1, 6));
		registerEntry(Blocks.ACTIVATOR_RAIL, MAT_IRON, INGOT.q(1), MAT_REDSTONE, DUST.q(1, 6), MAT_CARBON, QUANTUM.q(9));
		registerEntry(Blocks.FURNACE, MAT_STONE, BLOCK.q(8));

		registerEntry(Blocks.PISTON, MAT_STONE, INGOT.q(4), MAT_IRON, INGOT.q(1), MAT_REDSTONE, DUST.q(1), MAT_CARBON, QUANTUM.q(21));
		registerEntry(Blocks.STICKY_PISTON, MAT_STONE, INGOT.q(4), MAT_IRON, INGOT.q(1), MAT_REDSTONE, DUST.q(1), MAT_CARBON, QUANTUM.q(21));

		registerOre("bone", MAT_CALCIUM, QUANTUM.q(3), MAT_CARBON, QUANTUM.q(3));
        registerEntry(Items.SKULL, MAT_CALCIUM, INGOT.q(3), MAT_CARBON, NUGGET.q(3));
        registerEntry(new ItemStack(Items.DYE, 1, 15), MAT_CALCIUM, QUANTUM.q(1));
		registerEntry(Blocks.BONE_BLOCK, MAT_CALCIUM, QUANTUM.q(27), MAT_CARBON, QUANTUM.q(27));
		registerOre("dye", MAT_CARBON, QUANTUM.q(1));
		registerEntry(Items.STRING, MAT_CARBON, QUANTUM.q(3));
		registerEntry(Items.CAULDRON, MAT_IRON, QUANTUM.q(7));

		registerEntry(Blocks.FURNACE, MAT_STONE, BLOCK.q(8));

		registerEntry(Items.MINECART, MAT_IRON, INGOT.q(5));
		registerEntry(Items.HOPPER_MINECART, MAT_IRON, INGOT.q(10));
		registerEntry(Items.FURNACE_MINECART, MAT_IRON, INGOT.q(5), MAT_STONE, BLOCK.q(8));
        registerEntry(Items.CHEST_MINECART, MAT_IRON, INGOT.q(5), MAT_CARBON, NUGGET.q(7));
        registerEntry(Items.BUCKET, MAT_IRON, INGOT.q(3));
		registerEntry(Items.COMPASS, MAT_IRON, INGOT.q(4), MAT_REDSTONE, DUST.q(1));
		registerEntry(Items.CLOCK, MAT_GOLD, INGOT.q(4), MAT_REDSTONE, DUST.q(1));

		//castables
		registerEntry(ModItems.blade_titanium,				MAT_TITANIUM,		INGOT.q(2));
		registerEntry(ModItems.blade_tungsten,				MAT_TUNGSTEN,		INGOT.q(2));

		registerEntry(ModItems.blades_aluminum,				MAT_ALUMINIUM,		INGOT.q(5));
		registerEntry(ModItems.blades_gold,					MAT_GOLD,			INGOT.q(5));
		registerEntry(ModItems.blades_iron,					MAT_IRON,			INGOT.q(5));
		registerEntry(ModItems.blades_steel,				MAT_STEEL,			INGOT.q(5));
		registerEntry(ModItems.blades_titanium,				MAT_TITANIUM, 		INGOT.q(5));
		registerEntry(ModItems.blades_advanced_alloy,		MAT_ALLOY,			INGOT.q(5));
		registerEntry(ModItems.blades_combine_steel,		MAT_CMB,			INGOT.q(5));
		registerEntry(ModItems.blades_schrabidium,			MAT_SCHRABIDIUM,	INGOT.q(5));

		registerEntry(ModItems.stamp_stone_flat,			MAT_STONE,			INGOT.q(3));
		registerEntry(ModItems.stamp_iron_flat,				MAT_IRON,			INGOT.q(3));
		registerEntry(ModItems.stamp_steel_flat,			MAT_STEEL,			INGOT.q(3));
		registerEntry(ModItems.stamp_titanium_flat,			MAT_TITANIUM,		INGOT.q(3));
		registerEntry(ModItems.stamp_obsidian_flat,			MAT_OBSIDIAN,		INGOT.q(3));
		registerEntry(ModItems.stamp_schrabidium_flat,		MAT_SCHRABIDIUM,	INGOT.q(3));

		registerEntry(ModItems.stamp_stone_plate,			MAT_STONE,			INGOT.q(3));
		registerEntry(ModItems.stamp_iron_plate,			MAT_IRON,			INGOT.q(3));
		registerEntry(ModItems.stamp_steel_plate,			MAT_STEEL,			INGOT.q(3));
		registerEntry(ModItems.stamp_titanium_plate,		MAT_TITANIUM,		INGOT.q(3));
		registerEntry(ModItems.stamp_obsidian_plate,		MAT_OBSIDIAN,		INGOT.q(3));
		registerEntry(ModItems.stamp_schrabidium_plate,		MAT_SCHRABIDIUM,	INGOT.q(3));

		registerEntry(ModItems.stamp_stone_wire,			MAT_STONE,			INGOT.q(3));
		registerEntry(ModItems.stamp_iron_wire,				MAT_IRON,			INGOT.q(3));
		registerEntry(ModItems.stamp_steel_wire,			MAT_STEEL,			INGOT.q(3));
		registerEntry(ModItems.stamp_titanium_wire,			MAT_TITANIUM,		INGOT.q(3));
		registerEntry(ModItems.stamp_obsidian_wire,			MAT_OBSIDIAN,		INGOT.q(3));
		registerEntry(ModItems.stamp_schrabidium_wire,		MAT_SCHRABIDIUM,	INGOT.q(3));

		registerEntry(ModItems.stamp_stone_circuit,			MAT_STONE,			INGOT.q(3));
		registerEntry(ModItems.stamp_iron_circuit,			MAT_IRON,			INGOT.q(3));
		registerEntry(ModItems.stamp_steel_circuit,			MAT_STEEL,			INGOT.q(3));
		registerEntry(ModItems.stamp_titanium_circuit,		MAT_TITANIUM,		INGOT.q(3));
		registerEntry(ModItems.stamp_obsidian_circuit,		MAT_OBSIDIAN,		INGOT.q(3));
		registerEntry(ModItems.stamp_schrabidium_circuit,	MAT_SCHRABIDIUM,	INGOT.q(3));

		registerEntry(ModItems.pipes_steel,					MAT_STEEL,			BLOCK.q(3));

		registerEntry(ModItems.rod_empty,					MAT_STEEL,			INGOT.q(6, 16), MAT_LEAD,	INGOT.q(2, 16));
		registerEntry(ModItems.rod_dual_empty,				MAT_STEEL,			INGOT.q(6, 8), MAT_LEAD,	INGOT.q(2, 8));
		registerEntry(ModItems.rod_quad_empty,				MAT_STEEL,			INGOT.q(6, 4), MAT_LEAD,	INGOT.q(2, 4));

		registerEntry(ModItems.fluid_barrel_full,			MAT_STEEL,			INGOT.q(3), MAT_ALUMINIUM,	INGOT.q(1));
		registerEntry(ModItems.fluid_tank_full,				MAT_ALUMINIUM,		INGOT.q(6, 8), MAT_IRON,	INGOT.q(2, 8));
		registerEntry(ModItems.fluid_tank_lead_full,		MAT_LEAD, 			INGOT.q(3), MAT_U238, 		BILLET.q(1), MAT_ALUMINIUM,	INGOT.q(6, 16), MAT_IRON,	INGOT.q(2, 16));
		registerEntry(ModItems.cell,						MAT_STEEL,			INGOT.q(1));
		registerEntry(ModItems.gas_canister,				MAT_STEEL,			INGOT.q(2), MAT_COPPER,		INGOT.q(1, 2));
		registerEntry(ModItems.canister_generic,			MAT_ALUMINIUM,		INGOT.q(2), MAT_STEEL,		INGOT.q(1, 2));

		registerEntry(ModItems.mirror_tool,					MAT_IRON,			INGOT.q(2), MAT_ALUMINIUM,	INGOT.q(2));
		registerEntry(ModItems.rbmk_tool,					MAT_IRON,			INGOT.q(2), MAT_LEAD,		INGOT.q(2));

		registerEntry(ModItems.syringe_empty,				MAT_IRON,			INGOT.q(1, 6), MAT_STEEL,	INGOT.q(1, 6));
		registerEntry(ModItems.syringe_metal_empty,			MAT_IRON,			INGOT.q(1, 6), MAT_STEEL,	INGOT.q(6, 96), MAT_LEAD,	INGOT.q(2, 96));

		registerEntry(ModItems.particle_empty, 				MAT_STEEL, INGOT.q(2), MAT_TUNGSTEN, INGOT.q(1));

		registerEntry(ModItems.mold_base,					MAT_IRON,			INGOT.q(1));
		for(int i = 0; i < 22; i++) registerEntry(new ItemStack(ModItems.mold,1, i),						MAT_IRON,			INGOT.q(1));

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

		registerEntry(OreDictManager.DictFrame.fromOne(ModItems.circuit, ItemCircuit.EnumCircuitType.SILICON), MAT_SILICON, NUGGET.q(6));

		// registerEntry(DictFrame.fromOne(ModBlocks.stone_resource, EnumStoneType.LIMESTONE), MAT_FLUX, DUST.q(10));
		registerEntry(ModItems.powder_flux, MAT_FLUX, DUST.q(1));
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

		registerEntry(ModItems.ingot_chainsteel, MAT_STEEL, INGOT.q(1024), MAT_CARBON, INGOT.q(512));

		registerEntry(ModItems.pin, MAT_COPPER, WIRE.q(3));

		registerEntry(ModItems.motor, MAT_IRON, INGOT.q(5, 2), MAT_MINGRADE, QUANTUM.q(225, 2));

		registerEntry(ModItems.man_core, MAT_PU239, NUGGET.q(8), MAT_BERYLLIUM, NUGGET.q(2));
		registerEntry(ModItems.gadget_core, MAT_PU239, NUGGET.q(7), MAT_U238, NUGGET.q(3));
		registerEntry(ModItems.boy_target, MAT_U238, NUGGET.q(7));
		registerEntry(ModItems.boy_bullet, MAT_U238, NUGGET.q(3));
		registerEntry(ModItems.mike_core, MAT_LEAD, INGOT.q(6), MAT_U238, NUGGET.q(24));

		registerEntry(ModBlocks.steel_beam, MAT_STEEL, INGOT.q(3, 8));
		registerEntry(ModBlocks.steel_grate, MAT_STEEL, INGOT.q(3, 8));
		registerEntry(ModBlocks.solar_mirror, MAT_STEEL, INGOT.q(1), MAT_ALUMINIUM, INGOT.q(1));

		registerEntry(ModBlocks.hadron_coil_alloy, MAT_ALLOY, INGOT.q(4));
		registerEntry(ModBlocks.hadron_coil_gold, MAT_ALLOY, INGOT.q(2), MAT_GOLD, INGOT.q(2));
		registerEntry(ModBlocks.hadron_coil_neodymium, MAT_NEODYMIUM, INGOT.q(2), MAT_GOLD, INGOT.q(2));
		registerEntry(ModBlocks.hadron_coil_magtung, MAT_MAGTUNG, INGOT.q(2), MAT_BSCCO, INGOT.q(2));
		registerEntry(ModBlocks.hadron_coil_schrabidium, MAT_MAGTUNG, INGOT.q(2), MAT_SCHRABIDIUM, INGOT.q(2));
		registerEntry(ModBlocks.hadron_coil_schrabidate, MAT_SCHRABIDATE, INGOT.q(2), MAT_SCHRABIDIUM, INGOT.q(2));
		registerEntry(ModBlocks.hadron_coil_starmetal, MAT_SCHRABIDATE, INGOT.q(2), MAT_STAR, INGOT.q(2));
		registerEntry(ModBlocks.hadron_coil_chlorophyte, MAT_TUNGSTEN, INGOT.q(2));

		registerEntry(ModBlocks.hadron_plating, MAT_STEEL, INGOT.q(2));
		registerEntry(ModBlocks.hadron_plating_blue, MAT_STEEL, INGOT.q(2));
		registerEntry(ModBlocks.hadron_plating_black, MAT_STEEL, INGOT.q(2));
		registerEntry(ModBlocks.hadron_plating_yellow, MAT_STEEL, INGOT.q(2));
		registerEntry(ModBlocks.hadron_plating_striped, MAT_STEEL, INGOT.q(2));
		registerEntry(ModBlocks.hadron_plating_voltz, MAT_STEEL, INGOT.q(2));

		registerEntry(ModItems.mechanism_revolver_1, MAT_IRON, INGOT.q(4), MAT_ALUMINIUM, INGOT.q(1)+WIRE.q(1), MAT_COPPER, INGOT.q(1)+WIRE.q(1));
		registerEntry(ModItems.mechanism_revolver_2, MAT_ALLOY, INGOT.q(4), MAT_DURA, INGOT.q(1)+BOLT.q(1), MAT_TUNGSTEN, INGOT.q(1)+BOLT.q(1));
		registerEntry(ModItems.mechanism_rifle_1, MAT_IRON, INGOT.q(11), MAT_ALUMINIUM, INGOT.q(4)+WIRE.q(2), MAT_COPPER, INGOT.q(4)+WIRE.q(2));
		registerEntry(ModItems.mechanism_rifle_2, MAT_ALLOY, INGOT.q(11), MAT_DURA, INGOT.q(4)+BOLT.q(2), MAT_TUNGSTEN, INGOT.q(4)+BOLT.q(2));
		registerEntry(ModItems.mechanism_launcher_1, MAT_STEEL, INGOT.q(3)+BOLT.q(2), MAT_TITANIUM, PLATE.q(3), MAT_MINGRADE, INGOT.q(1));
		registerEntry(ModItems.mechanism_launcher_2, MAT_ALLOY, PLATE.q(3), MAT_DURA, BOLT.q(2), MAT_DESH, INGOT.q(1));

        registerEntry(ModBlocks.barrel_corroded, MAT_IRON, INGOT.q(2));
        registerEntry(ModBlocks.barrel_iron, MAT_IRON, INGOT.q(8));
		registerEntry(ModBlocks.barrel_steel, MAT_STEEL, INGOT.q(8));
		registerEntry(ModBlocks.barrel_tcalloy, MAT_TCALLOY, INGOT.q(6), MAT_TITANIUM, INGOT.q(2));
		registerEntry(ModBlocks.barrel_antimatter, MAT_IRON, INGOT.q(6), MAT_ALLOY, INGOT.q(6), MAT_SATURN, INGOT.q(6));

        registerEntry(ModBlocks.machine_storage_drum, MAT_IRON, INGOT.q(6), MAT_ALLOY, INGOT.q(6), MAT_SATURN, INGOT.q(6));
        registerEntry(ModBlocks.machine_waste_drum, MAT_LEAD, QUANTUM.q(802), MAT_STEEL, QUANTUM.q(648), MAT_IRON, QUANTUM.q(108));

        registerEntry(ModBlocks.safe, MAT_STEEL, INGOT.q(8), MAT_ALLOY, INGOT.q(4), MAT_LEAD, INGOT.q(4));

		registerEntry(ModBlocks.steel_scaffold, MAT_STEEL, INGOT.q(7, 8));
		registerEntry(ModBlocks.steel_poles, MAT_STEEL, INGOT.q(7, 16));
		registerEntry(ModBlocks.steel_roof, MAT_STEEL, INGOT.q(3, 2));
		registerEntry(ModBlocks.steel_wall, MAT_STEEL, INGOT.q(6, 4));
		registerEntry(ModBlocks.steel_corner, MAT_STEEL, INGOT.q(3));
		registerEntry(ModBlocks.deco_pipe, MAT_STEEL, INGOT.q(3));
		registerEntry(ModBlocks.fence_metal, MAT_STEEL, INGOT.q(2, 6)+QUANTUM.q(18));

		registerEntry(ModItems.seg_10, MAT_STEEL, QUANTUM.q(180), MAT_ALUMINIUM, INGOT.q(1));
		registerEntry(ModItems.seg_15, MAT_STEEL, QUANTUM.q(360), MAT_TITANIUM, INGOT.q(2));
		registerEntry(ModItems.seg_20, MAT_STEEL, QUANTUM.q(828), MAT_GOLD, INGOT.q(1));

		registerEntry(ModItems.rotor_steel, MAT_IRON, INGOT.q(2), MAT_GOLD, INGOT.q(2), MAT_STEEL, INGOT.q(1));
		registerEntry(ModItems.generator_steel, MAT_IRON, INGOT.q(12), MAT_GOLD, INGOT.q(12), MAT_STEEL, INGOT.q(6));

		registerEntry(ModItems.tank_steel, MAT_STEEL, INGOT.q(6), MAT_TITANIUM, INGOT.q(2));
		registerEntry(ModBlocks.machine_condenser, MAT_IRON, INGOT.q(4), MAT_STEEL, INGOT.q(4), MAT_COPPER, INGOT.q(3));

		registerEntry(ModBlocks.machine_turbine, MAT_TITANIUM, INGOT.q(40), MAT_STEEL, INGOT.q(14), MAT_IRON, QUANTUM.q(180), MAT_MINGRADE, QUANTUM.q(112));

		registerEntry(ModItems.flywheel_beryllium, MAT_BERYLLIUM, BLOCK.q(4), MAT_IRON, INGOT.q(12), MAT_DURA, INGOT.q(3));
		registerEntry(ModItems.hull_big_steel, MAT_STEEL, INGOT.q(6));
		registerEntry(ModItems.hull_big_aluminium, MAT_ALUMINIUM, INGOT.q(6));
		registerEntry(ModItems.hull_big_titanium, MAT_TITANIUM, INGOT.q(6));
		registerEntry(ModItems.hull_small_steel, MAT_STEEL, INGOT.q(6));
		registerEntry(ModItems.hull_small_aluminium, MAT_ALUMINIUM, INGOT.q(6));
		registerEntry(ModItems.cap_aluminium, MAT_ALUMINIUM, INGOT.q(3));

		registerEntry(ModItems.fins_flat, MAT_STEEL, INGOT.q(6));
		registerEntry(ModItems.fins_small_steel, MAT_STEEL, INGOT.q(7));
		registerEntry(ModItems.fins_big_steel, MAT_STEEL, INGOT.q(7));
		registerEntry(ModItems.fins_tri_steel, MAT_STEEL, INGOT.q(15));
		registerEntry(ModItems.sphere_steel, MAT_STEEL, INGOT.q(8));
		registerEntry(ModItems.pedestal_steel, MAT_STEEL, INGOT.q(7));
		registerEntry(ModItems.fins_quad_titanium, MAT_STEEL, INGOT.q(7));

		registerEntry(ModBlocks.rbmk_loader, MAT_STEEL, INGOT.q(10), MAT_COPPER, INGOT.q(4), MAT_TITANIUM, INGOT.q(2));
		registerEntry(ModBlocks.rbmk_steam_inlet, MAT_STEEL, INGOT.q(10), MAT_IRON, INGOT.q(4), MAT_TITANIUM, INGOT.q(2));
		registerEntry(ModBlocks.rbmk_steam_outlet, MAT_STEEL, INGOT.q(10), MAT_COPPER, INGOT.q(4), MAT_TITANIUM, INGOT.q(2));

		registerEntry(ModItems.casing_9, MAT_COPPER, INGOT.q(1));
		registerEntry(ModItems.casing_44, MAT_COPPER, INGOT.q(1));
		registerEntry(ModItems.casing_50, MAT_COPPER, INGOT.q(1));
		registerEntry(ModItems.casing_357, MAT_COPPER, INGOT.q(1));
		registerEntry(ModItems.casing_buckshot, MAT_COPPER, INGOT.q(1));

		registerEntry(ModItems.primer_9, MAT_REDSTONE, INGOT.q(1), MAT_ALUMINIUM, INGOT.q(1));
		registerEntry(ModItems.primer_44, MAT_REDSTONE, INGOT.q(1), MAT_IRON, INGOT.q(1));
		registerEntry(ModItems.primer_50, MAT_REDSTONE, INGOT.q(1), MAT_ALUMINIUM, INGOT.q(1));
		registerEntry(ModItems.primer_357, MAT_REDSTONE, INGOT.q(1), MAT_IRON, INGOT.q(1));
		registerEntry(ModItems.primer_buckshot, MAT_REDSTONE, INGOT.q(1), MAT_COPPER, INGOT.q(1));


		registerEntry(ModItems.piston_pneumatic, MAT_IRON, INGOT.q(3, 4), MAT_COPPER, INGOT.q(2, 4));

		registerEntry(ModItems.piston_selenium, MAT_STEEL, INGOT.q(5), MAT_TUNGSTEN, INGOT.q(1), MAT_DURA, BOLT.q(1));
		registerEntry(ModItems.drill_titanium, MAT_TITANIUM, INGOT.q(6), MAT_DURA, INGOT.q(2)+BOLT.q(4), MAT_STEEL, INGOT.q(2));

		registerEntry(ModItems.boy_shielding, MAT_TUNGCAR, INGOT.q(12), MAT_STEEL, INGOT.q(4));

		registerEntry(ModBlocks.machine_solar_boiler, MAT_STEEL, INGOT.q(22));
		registerEntry(ModBlocks.fwatz_scaffold, MAT_TUNGCAR, INGOT.q(2), MAT_TUNGSTEN, INGOT.q(2));
		registerEntry(ModBlocks.fwatz_tank, MAT_CMB, INGOT.q(4));
		registerEntry(ModBlocks.fwatz_cooler, MAT_SCHRABIDATE, INGOT.q(4));
		registerEntry(ModBlocks.fwatz_conductor, MAT_IRON, INGOT.q(2), MAT_TITANIUM, INGOT.q(2), MAT_MAGTUNG, INGOT.q(2), MAT_GOLD, INGOT.q(1), MAT_NEODYMIUM, INGOT.q(1));

		registerEntry(ModBlocks.machine_solar_boiler, MAT_STEEL, INGOT.q(22));

		registerEntry(ModItems.pile_rod_uranium, MAT_URANIUM, BILLET.q(3), MAT_IRON, INGOT.q(2));
		registerEntry(ModItems.pile_rod_plutonium, MAT_PLUTONIUM, BILLET.q(3), MAT_IRON, INGOT.q(2));

		registerEntry(ModBlocks.radiobox, MAT_STEEL, INGOT.q(6), MAT_STAR, INGOT.q(4), MAT_TUNGCAR, INGOT.q(2));

		registerEntry(ModItems.can_key, MAT_ALUMINIUM, NUGGET.q(1));
        registerEntry(ModItems.ring_pull, MAT_ALUMINIUM, NUGGET.q(1));
        registerEntry(ModItems.can_empty, MAT_ALUMINIUM, INGOT.q(2));

		registerEntry(ModItems.key, MAT_STEEL, INGOT.q(1)+BOLT.q(2));
		registerEntry(ModItems.padlock_rusty, MAT_IRON, INGOT.q(2), MAT_STEEL, BOLT.q(1));
		registerEntry(ModItems.padlock, MAT_STEEL, INGOT.q(7)+BOLT.q(1));

		registerEntry(ModItems.plate_armor_titanium, MAT_TITANIUM, INGOT.q(4), MAT_STEEL, INGOT.q(1)+BOLT.q(4));
		registerEntry(ModItems.plate_armor_ajr, MAT_TITANIUM, INGOT.q(4), MAT_SATURN, INGOT.q(2), MAT_STEEL, INGOT.q(1)+BOLT.q(4));

		registerEntry(ModBlocks.deco_pipe_framed, MAT_STEEL, INGOT.q(3));
		registerEntry(ModBlocks.deco_pipe_quad, MAT_STEEL, INGOT.q(3));
		registerEntry(ModBlocks.deco_pipe_rim, MAT_STEEL, INGOT.q(3));

        registerEntry(ModBlocks.railing_normal, MAT_STEEL, QUANTUM.q(270));
        registerEntry(ModBlocks.railing_bend, MAT_STEEL, QUANTUM.q(540));
        registerEntry(ModBlocks.railing_end_flipped_floor, MAT_STEEL, QUANTUM.q(270));
        registerEntry(ModBlocks.railing_end_floor, MAT_STEEL, QUANTUM.q(270));
        registerEntry(ModBlocks.railing_end_self, MAT_STEEL, QUANTUM.q(270));
        registerEntry(ModBlocks.railing_end_flipped_self, MAT_STEEL, QUANTUM.q(270));

        registerEntry(ModBlocks.ladder_aluminium, MAT_ALUMINIUM, INGOT.q(1, 8));
        registerEntry(ModBlocks.ladder_cobalt, MAT_COBALT, INGOT.q(1, 8));
        registerEntry(ModBlocks.ladder_copper, MAT_COPPER, INGOT.q(1, 8));
        registerEntry(ModBlocks.ladder_gold, MAT_GOLD, INGOT.q(1, 8));
        registerEntry(ModBlocks.ladder_iron, MAT_IRON, INGOT.q(1, 8));
        registerEntry(ModBlocks.ladder_steel, MAT_STEEL, INGOT.q(1, 8));
        registerEntry(ModBlocks.ladder_lead, MAT_LEAD, INGOT.q(1, 8));
        registerEntry(ModBlocks.ladder_titanium, MAT_TITANIUM, INGOT.q(1, 8));
        registerEntry(ModBlocks.ladder_tungsten, MAT_TUNGSTEN, INGOT.q(1, 8));

        registerEntry(ModBlocks.chain, MAT_STEEL, QUANTUM.q(162, 8));

        registerEntry(ModBlocks.barbed_wire, MAT_IRON, INGOT.q(1, 4), MAT_STEEL, WIRE.q(1, 4));
        registerEntry(ModBlocks.spikes, MAT_TUNGSTEN, INGOT.q(3, 4), MAT_STEEL, BOLT.q(3, 4));
        registerEntry(ModItems.pellet_meteorite, MAT_LEAD, NUGGET.q(6), MAT_METEOR, INGOT.q(1, 2));

        registerEntry(ModItems.heavy_duty_element, MAT_STEEL, HEAVY_COMPONENT.q(1), MAT_CMB, CASTPLATE.q(2), MAT_ZIRCONIUM, CASTPLATE.q(1));

        registerEntry(ModBlocks.pole_top, MAT_TUNGSTEN, INGOT.q(4), MAT_BERYLLIUM, INGOT.q(3), MAT_MINGRADE, INGOT.q(1));
        registerEntry(ModBlocks.tape_recorder, MAT_STEEL, INGOT.q(1), MAT_TUNGSTEN, INGOT.q(1, 2));
        registerEntry(ModBlocks.pole_satellite_receiver, MAT_STEEL, INGOT.q(5), MAT_MINGRADE, WIRE.q(1));

        registerEntry(ModItems.door_bunker, MAT_STEEL, INGOT.q(4), MAT_LEAD, INGOT.q(2));
        registerEntry(ModItems.door_metal, MAT_IRON, INGOT.q(4), MAT_STEEL, INGOT.q(2));
        registerEntry(ModItems.door_office, MAT_IRON, INGOT.q(2), MAT_CARBON, QUANTUM.q(28));

        registerEntry(ModBlocks.machine_fluidtank, MAT_STEEL, INGOT.q(24));
        registerEntry(ModBlocks.machine_bat9000, MAT_STEEL, INGOT.q(30), MAT_TITANIUM, INGOT.q(12));

        registerEntry(Items.IRON_HORSE_ARMOR, MAT_IRON, BLOCK.q(3));
        registerEntry(Items.GOLDEN_HORSE_ARMOR, MAT_GOLD, BLOCK.q(3));
        registerEntry(Items.DIAMOND_HORSE_ARMOR, MAT_CARBON, BLOCK.q(3));

        registerEntry(Items.GOLDEN_APPLE, MAT_GOLD, INGOT.q(8));
        registerEntry(new ItemStack(Items.GOLDEN_APPLE, 1, 1), MAT_GOLD, BLOCK.q(8));

        registerEntry(ModItems.apple_lead, MAT_LEAD, NUGGET.q(8));
        registerEntry(ModItems.apple_lead1, MAT_LEAD, INGOT.q(8));
        registerEntry(ModItems.apple_lead2, MAT_LEAD, BLOCK.q(8));

        registerEntry(ModItems.apple_schrabidium, MAT_SCHRABIDIUM, NUGGET.q(8));
        registerEntry(ModItems.apple_schrabidium1, MAT_SCHRABIDIUM, INGOT.q(8));
        registerEntry(ModItems.apple_schrabidium2, MAT_SCHRABIDIUM, BLOCK.q(8));

        registerEntry(Blocks.BEACON, MAT_OBSIDIAN, BLOCK.q(3));
        registerEntry(Blocks.BREWING_STAND, MAT_STONE, BLOCK.q(3));

        registerEntry(ModBlocks.boxcar, MAT_STEEL, INGOT.q(32));
        registerEntry(ModBlocks.ladder_red, MAT_STEEL, INGOT.q(1, 8));
        registerEntry(ModBlocks.ladder_red_top, MAT_STEEL, INGOT.q(1, 8));

        registerEntry(ModBlocks.machine_fraction_tower, MAT_STEEL, INGOT.q(12)+QUANTUM.q(54));
        registerEntry(ModBlocks.fraction_spacer, MAT_STEEL, INGOT.q(6), MAT_IRON, QUANTUM.q(108));

        registerEntry(ModBlocks.fluid_duct_mk2, MAT_STEEL, INGOT.q(1, 2), MAT_ALUMINIUM, INGOT.q(1, 4));
        registerEntry(ModBlocks.fluid_duct_solid, MAT_STEEL, INGOT.q(1, 2), MAT_ALUMINIUM, INGOT.q(1, 2));
        registerEntry(ModItems.screwdriver, MAT_IRON, INGOT.q(2), MAT_STEEL, INGOT.q(1));
        registerEntry(ModItems.hand_drill, MAT_DURA, INGOT.q(1), MAT_CARBON, QUANTUM.q(6));
        registerEntry(ModItems.reacher, MAT_TUNGSTEN, INGOT.q(1), MAT_STEEL, BOLT.q(4));

        registerEntry(ModBlocks.machine_converter_he_rf, MAT_REDSTONE, BLOCK.q(1), MAT_STEEL, INGOT.q(6), MAT_IRON, INGOT.q(3), MAT_MINGRADE, INGOT.q(3));
        registerEntry(ModBlocks.machine_converter_rf_he, MAT_REDSTONE, BLOCK.q(1), MAT_BERYLLIUM, INGOT.q(6), MAT_IRON, INGOT.q(3), MAT_MINGRADE, INGOT.q(3));

        registerEntry(ModBlocks.press_preheater, MAT_IRON, INGOT.q(9));
        registerEntry(ModBlocks.machine_coal_off, MAT_STEEL, INGOT.q(12), MAT_STONE, INGOT.q(8), MAT_TITANIUM, INGOT.q(2), MAT_MINGRADE, INGOT.q(1));

        registerEntry(ModItems.reactor_core, MAT_LEAD, INGOT.q(12), MAT_BERYLLIUM, INGOT.q(8), MAT_TUNGCAR, INGOT.q(8), MAT_SILICON, INGOT.q(2));

        registerEntry(ModBlocks.anvil_iron, MAT_IRON, INGOT.q(15));
        registerEntry(ModBlocks.anvil_lead, MAT_LEAD, INGOT.q(15));

        registerEntry(ModItems.gadget_wireing, MAT_GOLD, WIRE.q(12), MAT_IRON, INGOT.q(1));
        registerEntry(ModItems.forge_fluid_identifier, MAT_IRON, INGOT.q(1));
        registerEntry(ModItems.siren_track, MAT_STEEL, INGOT.q(1));

        registerEntry(Blocks.QUARTZ_STAIRS, MAT_SILICON, NUGGET.q(9));
        registerEntry(new ItemStack(Blocks.STONE_SLAB, 1, 7), MAT_SILICON, NUGGET.q(6));
        registerEntry(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 1), MAT_SILICON, NUGGET.q(12));
        registerEntry(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 2), MAT_SILICON, NUGGET.q(12));



//        registerAutoMats();
	}
	
	public static void registerEntry(Object key, Object... matDef) {
		ComparableStack comp = null;

		if(key instanceof Item) comp = new ComparableStack((Item) key);
		if(key instanceof Block) comp = new ComparableStack((Block) key);
		if(key instanceof ItemStack) comp = new ComparableStack((ItemStack) key);
		
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

//    public static void registerAutoMats(){
//        outer : for(IRecipe recipe : ForgeRegistries.RECIPES.getValuesCollection()) {
//            if(recipe.isDynamic() || recipe.getRecipeOutput().isEmpty()) continue;
//
//            ItemStack out = recipe.getRecipeOutput();
//            List<Ingredient> ingList = recipe.getIngredients();
//            int noMatIngFound = 0;
//            List<List<MaterialStack>> matCollection = new ArrayList<>();
//            for(Ingredient ing : ingList){
//                if(noMatIngFound > 1) continue outer;
//                ItemStack[] candidates = ing.getMatchingStacks();
//                if(candidates.length == 0){
//                    noMatIngFound++;
//                    continue outer;
//                }
//                List<MaterialStack> mats = getMaterialsFromItem(candidates[0]);
//                if(mats.isEmpty()) {
//                    noMatIngFound++;
//                } else {
//                    matCollection.add(mats);
//                }
//            }
//            if(matCollection.isEmpty()) continue;
//            registerEntry(out.copy(), Mats.sum(matCollection, out.getCount()));
//            System.out.println("ADDED "+recipe.getRecipeOutput().getDisplayName());
//        }
//    }

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
