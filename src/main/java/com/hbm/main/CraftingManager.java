package com.hbm.main;

import java.util.Map.Entry;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.crafting.handlers.MKUCraftingHandler;
import com.hbm.crafting.handlers.RBMKFuelCraftingHandler;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.OreDictManager;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;
import com.hbm.items.machine.ItemFFFluidDuct;
import com.hbm.items.machine.ItemFluidTank;
import com.hbm.items.machine.ItemForgeFluidIdentifier;
import com.hbm.items.machine.ItemFuelRod;
import com.hbm.items.special.ItemCell;
import com.hbm.items.special.ItemHot;
import com.hbm.items.special.ItemWasteLong;
import com.hbm.items.special.ItemWasteShort;
import com.hbm.items.tool.ItemBombCaller;
import com.hbm.items.tool.ItemBombCaller.EnumCallerType;
import com.hbm.items.tool.ItemFluidCanister;
import com.hbm.items.weapon.GunB92Cell;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.util.EnchantmentUtil;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IngredientNBT;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class CraftingManager {

	public static RegistryEvent.Register<IRecipe> hack;

	public static void init(){
		if(!GeneralConfig.recipes) {
			return;
		}
		addCrafting();
		addSmelting();

		hack.getRegistry().register(new RBMKFuelCraftingHandler().setRegistryName(new ResourceLocation(RefStrings.MODID, "rbmk_fuel_crafting_handler")));
		hack.getRegistry().register(new MKUCraftingHandler().setRegistryName(new ResourceLocation(RefStrings.MODID, "mku_crafting_handler")));
	}

	public static void addCrafting(){

		addShapedRecipe(new ItemStack(ModItems.redstone_sword, 1), new Object[] { "R", "R", "S", 'R', Blocks.REDSTONE_BLOCK, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.big_sword, 1), new Object[] { "QIQ", "QIQ", "GSG", 'G', Items.GOLD_INGOT, 'S', Items.STICK, 'I', Items.IRON_INGOT, 'Q', Items.QUARTZ });

		addShapedRecipe(new ItemStack(ModItems.egg_balefire_shard, 1), new Object[] { "##", "##", '#', ModItems.powder_balefire });
		addShapedOreRecipe(new ItemStack(ModItems.board_copper, 1), new Object[] { "TTT", "TTT", 'T', "plateCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.hazmat_cloth_red, 1), new Object[] { "C", "R", "C", 'C', ModItems.hazmat_cloth, 'R', "dustRedstone" });
		addShapedOreRecipe(new ItemStack(ModItems.hazmat_cloth_grey, 1), new Object[] { " P ", "ICI", " L ", 'C', ModItems.hazmat_cloth_red, 'P', "plateIron", 'L', "plateLead", 'I', ModItems.plate_polymer });
		addShapedOreRecipe(new ItemStack(ModItems.asbestos_cloth, 8), new Object[] { "SCS", "CPC", "SCS", 'S', Items.STRING, 'P', ModItems.powder_bromine, 'C', Blocks.WOOL });
		addShapedRecipe(new ItemStack(ModItems.bolt_dura_steel, 4), new Object[] { "D", "D", 'D', ModItems.ingot_dura_steel });
		addShapedOreRecipe(new ItemStack(ModItems.pipes_steel, 1), new Object[] { "B", "B", "B", 'B', "blockSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.bolt_tungsten, 4), new Object[] { "D", "D", 'D', "ingotTungsten" });
		addShapedOreRecipe(new ItemStack(ModItems.bolt_compound, 1), new Object[] { "PDP", "PTP", "PDP", 'D', ModItems.bolt_dura_steel, 'T', ModItems.bolt_tungsten, 'P', "plateTitanium" });
		addShapedOreRecipe(new ItemStack(ModItems.pellet_coal, 1), new Object[] { "PFP", "FOF", "PFP", 'P', "dustCoal", 'F', Items.FLINT, 'O', ModBlocks.gravel_obsidian });
		addShapedRecipe(new ItemStack(ModItems.plate_polymer, 8), new Object[] { "DD", 'D', ModItems.ingot_polymer });
		addShapedRecipe(new ItemStack(ModItems.plate_polymer, 4), new Object[] { "SWS", 'S', Items.STRING, 'W', Blocks.WOOL });
		addShapedRecipe(new ItemStack(ModItems.plate_polymer, 4), new Object[] { "BB", 'B', Items.BRICK });

		addShapedOreRecipe(new ItemStack(ModBlocks.marker_structure, 1), new Object[] { "L", "G", "R", 'L', "dustLapis", 'G', Items.GLOWSTONE_DUST, 'R', Blocks.REDSTONE_TORCH });

		addShapedOreRecipe(new ItemStack(ModItems.circuit_raw, 1), new Object[] { "A", "R", "S", 'S', "plateSteel", 'R', "dustRedstone", 'A', ModItems.wire_aluminium });
		addShapedOreRecipe(new ItemStack(ModItems.circuit_targeting_tier1, 1), new Object[] { "CPC", 'C', ModItems.circuit_aluminium, 'P', "dustRedstone" });
		addShapedOreRecipe(new ItemStack(ModItems.circuit_targeting_tier2, 1), new Object[] { "CPC", 'C', ModItems.circuit_copper, 'P', "dustNetherQuartz" });
		addShapedOreRecipe(new ItemStack(ModItems.circuit_targeting_tier3, 1), new Object[] { "CPC", 'C', ModItems.circuit_red_copper, 'P', "dustGold" });
		addShapedOreRecipe(new ItemStack(ModItems.circuit_targeting_tier4, 1), new Object[] { "CPC", 'C', ModItems.circuit_gold, 'P', "dustLapis" });
		addShapedOreRecipe(new ItemStack(ModItems.circuit_targeting_tier5, 1), new Object[] { "CPC", 'C', ModItems.circuit_schrabidium, 'P', "dustDiamond" });
		addShapedRecipe(new ItemStack(ModItems.circuit_targeting_tier6, 1), new Object[] { "P", "D", "C", 'C', ModItems.circuit_targeting_tier5, 'D', ModItems.battery_potatos, 'P', ModItems.powder_spark_mix });
		addShapelessRecipe(new ItemStack(ModItems.circuit_aluminium, 2), new Object[] { ModItems.circuit_targeting_tier1 });
		addShapelessRecipe(new ItemStack(ModItems.circuit_copper, 2), new Object[] { ModItems.circuit_targeting_tier2 });
		addShapelessRecipe(new ItemStack(ModItems.circuit_red_copper, 2), new Object[] { ModItems.circuit_targeting_tier3 });
		addShapelessRecipe(new ItemStack(ModItems.circuit_gold, 2), new Object[] { ModItems.circuit_targeting_tier4 });
		addShapelessRecipe(new ItemStack(ModItems.circuit_schrabidium, 2), new Object[] { ModItems.circuit_targeting_tier5 });

		addShapelessOreRecipe(new ItemStack(Items.GUNPOWDER, 3), new Object[] { "dustSulfur", "dustSaltpeter", Items.COAL });
		addShapelessOreRecipe(new ItemStack(Items.GUNPOWDER, 3), new Object[] { "dustSulfur", "dustSaltpeter", new ItemStack(Items.COAL, 1, 1) });

		addShapedOreRecipe(new ItemStack(ModItems.cell, 6), new Object[] { "SSS", "G G", "SSS", 'S', "plateSteel", 'G', "paneGlass" });
		addShapedRecipe(ItemCell.getFullCell(ModForgeFluids.deuterium, 8), new Object[] { "DDD", "DTD", "DDD", 'D', ModItems.cell, 'T', ModItems.mike_deut });

		addShapedOreRecipe(new ItemStack(ModItems.canister_generic, 2), new Object[] { "S ", "AA", "AA", 'S', "plateSteel", 'A', "plateAluminum" });
		addShapedRecipe(new ItemStack(ModBlocks.yellow_barrel, 1), new Object[] { "DDD", "DTD", "DDD", 'D', ModItems.nuclear_waste, 'T', ModItems.tank_steel });
		addShapedRecipe(new ItemStack(ModItems.nuclear_waste, 8), new Object[] { "B", 'B', ModBlocks.yellow_barrel });
		addShapedOreRecipe(new ItemStack(ModItems.gas_canister, 2), new Object[] { "S ", "AA", "AA", 'A', "plateSteel", 'S', "plateCopper" });
		addShapedOreRecipe(new ItemStack(ModBlocks.vitrified_barrel, 1), new Object[] { "LSL", "PWP", "LSL", 'L', "plateLead", 'S', Blocks.SAND, 'P', "ingotPolymer", 'W', ModBlocks.block_waste });
		addShapelessOreRecipe(new ItemStack(ModBlocks.block_waste_painted, 1), new Object[] { "dyeYellow", ModBlocks.block_waste });

		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_aluminium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_aluminium });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_copper), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_copper });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_fluorite), 1), new Object[] { "###", "###", "###", '#', ModItems.fluorite });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_niter), 1), new Object[] { "###", "###", "###", '#', ModItems.niter });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_red_copper), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_red_copper });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_steel), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_steel });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_sulfur), 1), new Object[] { "###", "###", "###", '#', ModItems.sulfur });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_titanium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_titanium });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_tungsten), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_tungsten });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_uranium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_uranium });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_thorium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_th232 });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_lead), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_lead });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_trinitite), 1), new Object[] { "###", "###", "###", '#', ModItems.trinitite });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_waste), 1), new Object[] { "###", "###", "###", '#', ModItems.nuclear_waste });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_scrap), 1), new Object[] { "##", "##", '#', ModItems.scrap });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_scrap), 1), new Object[] { "###", "###", "###", '#', ModItems.dust });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_beryllium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_beryllium });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_schrabidium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_schrabidium });
		addShapedRecipe(new ItemStack(ModBlocks.block_schrabidium_cluster, 1), new Object[] { "#S#", "S#S", "#S#", '#', ModItems.ingot_schrabidium, 'S', ModItems.ingot_starmetal });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_euphemium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_euphemium });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_advanced_alloy), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_advanced_alloy });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_magnetized_tungsten), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_magnetized_tungsten });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_combine_steel), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_combine_steel });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_australium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_australium });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_weidanium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_weidanium });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_reiium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_reiium });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_unobtainium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_unobtainium });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_daffergon), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_daffergon });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_verticium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_verticium });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_desh), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_desh });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_dura_steel), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_dura_steel });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_meteor_cobble), 1), new Object[] { "##", "##", '#', ModItems.fragment_meteorite });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_meteor_broken), 1), new Object[] { "###", "###", "###", '#', ModItems.fragment_meteorite });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_yellowcake), 1), new Object[] { "###", "###", "###", '#', ModItems.powder_yellowcake });
		addShapedRecipe(new ItemStack(ModBlocks.block_starmetal, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_starmetal });

		addShapedRecipe(new ItemStack(ModBlocks.hazmat, 8), new Object[] { "###", "# #", "###", '#', ModItems.hazmat_cloth });
		addShapedRecipe(new ItemStack(ModItems.hazmat_cloth, 1), new Object[] { "#", '#', ModBlocks.hazmat });

		addShapelessOreRecipe(new ItemStack(ModItems.ingot_uranium_fuel, 1), new Object[] { "nuggetUranium235", "nuggetUranium235", "nuggetUranium235", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238" });
		addShapelessOreRecipe(new ItemStack(ModItems.ingot_uranium_fuel, 1), new Object[] { "nuggetUranium233", "nuggetUranium233", "nuggetUranium233", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238" });
		addShapelessOreRecipe(new ItemStack(ModItems.ingot_thorium_fuel, 1), new Object[] { "nuggetUranium233", "nuggetUranium233", "nuggetUranium233", "nuggetThorium232", "nuggetThorium232", "nuggetThorium232", "nuggetThorium232", "nuggetThorium232", "nuggetThorium232" });
		addShapelessOreRecipe(new ItemStack(ModItems.ingot_plutonium_fuel, 1), new Object[] { "nuggetPlutonium238", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium240", "nuggetPlutonium240", "nuggetPlutonium240" });
		addShapelessOreRecipe(new ItemStack(ModItems.ingot_mox_fuel, 1), new Object[] { "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239" });
		addShapelessOreRecipe(new ItemStack(ModItems.ingot_uranium_fuel, 1), new Object[] { "tinyU235", "tinyU235", "tinyU235", "tinyU238", "tinyU238", "tinyU238", "tinyU238", "tinyU238", "tinyU238" });
		addShapelessOreRecipe(new ItemStack(ModItems.ingot_plutonium_fuel, 1), new Object[] { "tinyPu238", "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu240", "tinyPu240", "tinyPu240" });
		addShapelessOreRecipe(new ItemStack(ModItems.ingot_mox_fuel, 1), new Object[] { "tinyU235", "tinyU235", "tinyU235", "tinyU238", "tinyU238", "tinyPu238", "tinyPu239", "tinyPu239", "tinyPu239" });
		addShapelessOreRecipe(new ItemStack(ModItems.ingot_schrabidium_fuel, 1), new Object[] { "nuggetSchrabidium", "nuggetSchrabidium", "nuggetSchrabidium", "nuggetNeptunium", "nuggetNeptunium", "nuggetNeptunium", ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium });
		addShapelessOreRecipe(new ItemStack(ModItems.ingot_hes, 1), new Object[] { "nuggetSchrabidium", "nuggetSchrabidium", "nuggetSchrabidium", "nuggetSchrabidium", "nuggetSchrabidium", "nuggetNeptunium", "nuggetNeptunium", ModItems.nugget_beryllium, ModItems.nugget_beryllium });
		addShapelessOreRecipe(new ItemStack(ModItems.ingot_les, 1), new Object[] { "nuggetSchrabidium", "nuggetNeptunium", "nuggetNeptunium", "nuggetNeptunium", "nuggetNeptunium", ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium });

		addShapedRecipe(new ItemStack(ModItems.ingot_copper, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_copper) });
		addShapedRecipe(new ItemStack(ModItems.fluorite, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_fluorite) });
		addShapedRecipe(new ItemStack(ModItems.niter, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_niter) });
		addShapedRecipe(new ItemStack(ModItems.ingot_red_copper, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_red_copper) });
		addShapedRecipe(new ItemStack(ModItems.ingot_steel, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_steel) });
		addShapedRecipe(new ItemStack(ModItems.sulfur, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_sulfur) });
		addShapedRecipe(new ItemStack(ModItems.ingot_titanium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_titanium) });
		addShapedRecipe(new ItemStack(ModItems.ingot_tungsten, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_tungsten) });
		addShapedRecipe(new ItemStack(ModItems.ingot_uranium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_uranium) });
		addShapedRecipe(new ItemStack(ModItems.ingot_th232, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_thorium) });
		addShapedRecipe(new ItemStack(ModItems.ingot_lead, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_lead) });
		addShapedRecipe(new ItemStack(ModItems.trinitite, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_trinitite) });
		addShapedRecipe(new ItemStack(ModItems.nuclear_waste, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_waste) });
		addShapedRecipe(new ItemStack(ModItems.nuclear_waste, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_waste_painted) });
		addShapedRecipe(new ItemStack(ModItems.ingot_beryllium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_beryllium) });
		addShapedRecipe(new ItemStack(ModItems.ingot_schrabidium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_schrabidium) });
		addShapedRecipe(new ItemStack(ModItems.ingot_euphemium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_euphemium) });
		addShapedRecipe(new ItemStack(ModItems.ingot_advanced_alloy, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_advanced_alloy) });
		addShapedRecipe(new ItemStack(ModItems.ingot_magnetized_tungsten, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_magnetized_tungsten) });
		addShapedRecipe(new ItemStack(ModItems.ingot_combine_steel, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_combine_steel) });
		addShapedRecipe(new ItemStack(ModItems.ingot_australium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_australium) });
		addShapedRecipe(new ItemStack(ModItems.ingot_weidanium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_weidanium) });
		addShapedRecipe(new ItemStack(ModItems.ingot_reiium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_reiium) });
		addShapedRecipe(new ItemStack(ModItems.ingot_unobtainium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_unobtainium) });
		addShapedRecipe(new ItemStack(ModItems.ingot_daffergon, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_daffergon) });
		addShapedRecipe(new ItemStack(ModItems.ingot_verticium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_verticium) });
		addShapedRecipe(new ItemStack(ModItems.ingot_desh, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_desh) });
		addShapedRecipe(new ItemStack(ModItems.ingot_dura_steel, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_dura_steel) });
		addShapedRecipe(new ItemStack(ModItems.powder_yellowcake, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_yellowcake) });
		addShapedRecipe(new ItemStack(ModItems.ingot_starmetal, 9), new Object[] { "#", '#', ModBlocks.block_starmetal });

		addShapedRecipe(new ItemStack(ModItems.ingot_plutonium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_plutonium });
		addShapedRecipe(new ItemStack(ModItems.nugget_plutonium, 9), new Object[] { "#", '#', ModItems.ingot_plutonium });
		addShapedRecipe(new ItemStack(ModItems.ingot_pu238, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_pu238 });
		addShapedRecipe(new ItemStack(ModItems.nugget_pu238, 9), new Object[] { "#", '#', ModItems.ingot_pu238 });
		addShapedRecipe(new ItemStack(ModItems.ingot_pu239, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_pu239 });
		addShapedRecipe(new ItemStack(ModItems.nugget_pu239, 9), new Object[] { "#", '#', ModItems.ingot_pu239 });
		addShapedRecipe(new ItemStack(ModItems.ingot_pu240, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_pu240 });
		addShapedRecipe(new ItemStack(ModItems.nugget_pu240, 9), new Object[] { "#", '#', ModItems.ingot_pu240 });
		addShapedRecipe(new ItemStack(ModItems.ingot_th232, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_th232 });
		addShapedRecipe(new ItemStack(ModItems.nugget_th232, 9), new Object[] { "#", '#', ModItems.ingot_th232 });
		addShapedRecipe(new ItemStack(ModItems.ingot_uranium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_uranium });
		addShapedRecipe(new ItemStack(ModItems.nugget_uranium, 9), new Object[] { "#", '#', ModItems.ingot_uranium });
		addShapedRecipe(new ItemStack(ModItems.ingot_u233, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_u233 });
		addShapedRecipe(new ItemStack(ModItems.nugget_u233, 9), new Object[] { "#", '#', ModItems.ingot_u233 });
		addShapedRecipe(new ItemStack(ModItems.ingot_u235, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_u235 });
		addShapedRecipe(new ItemStack(ModItems.nugget_u235, 9), new Object[] { "#", '#', ModItems.ingot_u235 });
		addShapedRecipe(new ItemStack(ModItems.ingot_u238, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_u238 });
		addShapedRecipe(new ItemStack(ModItems.nugget_u238, 9), new Object[] { "#", '#', ModItems.ingot_u238 });
		addShapedRecipe(new ItemStack(ModItems.ingot_neptunium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_neptunium });
		addShapedRecipe(new ItemStack(ModItems.nugget_neptunium, 9), new Object[] { "#", '#', ModItems.ingot_neptunium });
		addShapedRecipe(new ItemStack(ModItems.ingot_polonium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_polonium });
		addShapedRecipe(new ItemStack(ModItems.nugget_polonium, 9), new Object[] { "#", '#', ModItems.ingot_polonium });
		addShapedRecipe(new ItemStack(ModItems.ingot_lead, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_lead });
		addShapedRecipe(new ItemStack(ModItems.nugget_lead, 9), new Object[] { "#", '#', ModItems.ingot_lead });
		addShapedRecipe(new ItemStack(ModItems.ingot_beryllium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_beryllium });
		addShapedRecipe(new ItemStack(ModItems.nugget_beryllium, 9), new Object[] { "#", '#', ModItems.ingot_beryllium });
		addShapedRecipe(new ItemStack(ModItems.ingot_schrabidium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_schrabidium });
		addShapedRecipe(new ItemStack(ModItems.nugget_schrabidium, 9), new Object[] { "#", '#', ModItems.ingot_schrabidium });
		addShapedRecipe(new ItemStack(ModItems.ingot_uranium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_uranium_fuel });
		addShapedRecipe(new ItemStack(ModItems.nugget_uranium_fuel, 9), new Object[] { "#", '#', ModItems.ingot_uranium_fuel });
		addShapedRecipe(new ItemStack(ModItems.ingot_thorium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_thorium_fuel });
		addShapedRecipe(new ItemStack(ModItems.nugget_thorium_fuel, 9), new Object[] { "#", '#', ModItems.ingot_thorium_fuel });
		addShapedRecipe(new ItemStack(ModItems.ingot_plutonium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_plutonium_fuel });
		addShapedRecipe(new ItemStack(ModItems.nugget_plutonium_fuel, 9), new Object[] { "#", '#', ModItems.ingot_plutonium_fuel });
		addShapedRecipe(new ItemStack(ModItems.ingot_mox_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_mox_fuel });
		addShapedRecipe(new ItemStack(ModItems.nugget_mox_fuel, 9), new Object[] { "#", '#', ModItems.ingot_mox_fuel });
		addShapedRecipe(new ItemStack(ModItems.ingot_schrabidium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_schrabidium_fuel });
		addShapedRecipe(new ItemStack(ModItems.nugget_schrabidium_fuel, 9), new Object[] { "#", '#', ModItems.ingot_schrabidium_fuel });
		addShapedRecipe(new ItemStack(ModItems.ingot_hes, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_hes });
		addShapedRecipe(new ItemStack(ModItems.nugget_hes, 9), new Object[] { "#", '#', ModItems.ingot_hes });
		addShapedRecipe(new ItemStack(ModItems.ingot_les, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_les });
		addShapedRecipe(new ItemStack(ModItems.nugget_les, 9), new Object[] { "#", '#', ModItems.ingot_les });
		addShapedRecipe(new ItemStack(ModItems.ingot_australium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_australium });
		addShapedRecipe(new ItemStack(ModItems.nugget_australium, 9), new Object[] { "#", '#', ModItems.ingot_australium });
		addShapedRecipe(new ItemStack(ModItems.ingot_weidanium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_weidanium });
		addShapedRecipe(new ItemStack(ModItems.nugget_weidanium, 9), new Object[] { "#", '#', ModItems.ingot_weidanium });
		addShapedRecipe(new ItemStack(ModItems.ingot_reiium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_reiium });
		addShapedRecipe(new ItemStack(ModItems.nugget_reiium, 9), new Object[] { "#", '#', ModItems.ingot_reiium });
		addShapedRecipe(new ItemStack(ModItems.ingot_unobtainium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_unobtainium });
		addShapedRecipe(new ItemStack(ModItems.nugget_unobtainium, 9), new Object[] { "#", '#', ModItems.ingot_unobtainium });
		addShapedRecipe(new ItemStack(ModItems.ingot_daffergon, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_daffergon });
		addShapedRecipe(new ItemStack(ModItems.nugget_daffergon, 9), new Object[] { "#", '#', ModItems.ingot_daffergon });
		addShapedRecipe(new ItemStack(ModItems.ingot_verticium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_verticium });
		addShapedRecipe(new ItemStack(ModItems.nugget_verticium, 9), new Object[] { "#", '#', ModItems.ingot_verticium });

		addShapedRecipe(new ItemStack(ModItems.powder_steel, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_steel_tiny });
		addShapedRecipe(new ItemStack(ModItems.powder_steel_tiny, 9), new Object[] { "#", '#', ModItems.powder_steel });
		addShapedRecipe(new ItemStack(ModItems.powder_lithium, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_lithium_tiny });
		addShapedRecipe(new ItemStack(ModItems.powder_lithium_tiny, 9), new Object[] { "#", '#', ModItems.powder_lithium });
		addShapedRecipe(new ItemStack(ModItems.powder_cobalt, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_cobalt_tiny });
		addShapedRecipe(new ItemStack(ModItems.powder_cobalt_tiny, 9), new Object[] { "#", '#', ModItems.powder_cobalt });
		addShapedRecipe(new ItemStack(ModItems.powder_neodymium, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_neodymium_tiny });
		addShapedRecipe(new ItemStack(ModItems.powder_neodymium_tiny, 9), new Object[] { "#", '#', ModItems.powder_neodymium });
		addShapedRecipe(new ItemStack(ModItems.powder_niobium, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_niobium_tiny });
		addShapedRecipe(new ItemStack(ModItems.powder_niobium_tiny, 9), new Object[] { "#", '#', ModItems.powder_niobium });
		addShapedRecipe(new ItemStack(ModItems.powder_cerium, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_cerium_tiny });
		addShapedRecipe(new ItemStack(ModItems.powder_cerium_tiny, 9), new Object[] { "#", '#', ModItems.powder_cerium });
		addShapedRecipe(new ItemStack(ModItems.powder_lanthanium, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_lanthanium_tiny });
		addShapedRecipe(new ItemStack(ModItems.powder_lanthanium_tiny, 9), new Object[] { "#", '#', ModItems.powder_lanthanium });
		addShapedRecipe(new ItemStack(ModItems.powder_actinium, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_actinium_tiny });
		addShapedRecipe(new ItemStack(ModItems.powder_actinium_tiny, 9), new Object[] { "#", '#', ModItems.powder_actinium });
		addShapedRecipe(new ItemStack(ModItems.powder_meteorite, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_meteorite_tiny });
		addShapedRecipe(new ItemStack(ModItems.powder_meteorite_tiny, 9), new Object[] { "#", '#', ModItems.powder_meteorite });
		addShapedRecipe(new ItemStack(ModItems.ingot_solinium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_solinium });
		addShapedRecipe(new ItemStack(ModItems.nugget_solinium, 9), new Object[] { "#", '#', ModItems.ingot_solinium });
		addShapedRecipe(new ItemStack(ModItems.nuclear_waste, 1), new Object[] { "###", "###", "###", '#', ModItems.nuclear_waste_tiny });
		addShapedRecipe(new ItemStack(ModItems.nuclear_waste_tiny, 9), new Object[] { "#", '#', ModItems.nuclear_waste });

		addShapelessRecipe(new ItemStack(ModItems.nuclear_waste, 1), new Object[] { ModItems.rod_waste });
		addShapelessRecipe(new ItemStack(ModItems.nuclear_waste, 2), new Object[] { ModItems.rod_dual_waste });
		addShapelessRecipe(new ItemStack(ModItems.nuclear_waste, 4), new Object[] { ModItems.rod_quad_waste });

		addShapedOreRecipe(new ItemStack(ModItems.rod_empty, 16), new Object[] { "SSS", "L L", "SSS", 'S', "plateSteel", 'L', "plateLead" });
		addShapelessRecipe(new ItemStack(ModItems.rod_th232, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_th232, ModItems.nugget_th232, ModItems.nugget_th232, ModItems.nugget_th232, ModItems.nugget_th232, ModItems.nugget_th232 });
		addShapelessRecipe(new ItemStack(ModItems.rod_uranium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium });
		addShapelessRecipe(new ItemStack(ModItems.rod_u233, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_u233, ModItems.nugget_u233, ModItems.nugget_u233, ModItems.nugget_u233, ModItems.nugget_u233, ModItems.nugget_u233 });
		addShapelessRecipe(new ItemStack(ModItems.rod_u235, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235 });
		addShapelessRecipe(new ItemStack(ModItems.rod_u238, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238 });
		addShapelessRecipe(new ItemStack(ModItems.rod_plutonium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium });
		addShapelessRecipe(new ItemStack(ModItems.rod_pu238, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238 });
		addShapelessRecipe(new ItemStack(ModItems.rod_pu239, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239 });
		addShapelessRecipe(new ItemStack(ModItems.rod_pu240, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240 });
		addShapelessRecipe(new ItemStack(ModItems.rod_neptunium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium });
		addShapelessRecipe(new ItemStack(ModItems.rod_polonium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_polonium, ModItems.nugget_polonium, ModItems.nugget_polonium, ModItems.nugget_polonium, ModItems.nugget_polonium, ModItems.nugget_polonium });
		addShapelessRecipe(new ItemStack(ModItems.rod_lead, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead });
		addShapelessRecipe(new ItemStack(ModItems.rod_schrabidium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium });
		addShapelessRecipe(new ItemStack(ModItems.rod_solinium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_solinium, ModItems.nugget_solinium, ModItems.nugget_solinium, ModItems.nugget_solinium, ModItems.nugget_solinium, ModItems.nugget_solinium });
		addShapelessRecipe(new ItemStack(ModItems.rod_uranium_fuel, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.rod_thorium_fuel, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.rod_plutonium_fuel, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.rod_mox_fuel, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel });
		addShapelessRecipe(new ItemStack(ModItems.rod_schrabidium_fuel, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.rod_euphemium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_euphemium, ModItems.nugget_euphemium, ModItems.nugget_euphemium, ModItems.nugget_euphemium, ModItems.nugget_euphemium, ModItems.nugget_euphemium });
		addShapelessRecipe(new ItemStack(ModItems.rod_australium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_australium, ModItems.nugget_australium, ModItems.nugget_australium, ModItems.nugget_australium, ModItems.nugget_australium, ModItems.nugget_australium });
		addShapelessRecipe(new ItemStack(ModItems.rod_weidanium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_weidanium, ModItems.nugget_weidanium, ModItems.nugget_weidanium, ModItems.nugget_weidanium, ModItems.nugget_weidanium, ModItems.nugget_weidanium });
		addShapelessRecipe(new ItemStack(ModItems.rod_reiium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_reiium, ModItems.nugget_reiium, ModItems.nugget_reiium, ModItems.nugget_reiium, ModItems.nugget_reiium, ModItems.nugget_reiium });
		addShapelessRecipe(new ItemStack(ModItems.rod_unobtainium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_unobtainium, ModItems.nugget_unobtainium, ModItems.nugget_unobtainium, ModItems.nugget_unobtainium, ModItems.nugget_unobtainium, ModItems.nugget_unobtainium });
		addShapelessRecipe(new ItemStack(ModItems.rod_daffergon, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_daffergon, ModItems.nugget_daffergon, ModItems.nugget_daffergon, ModItems.nugget_daffergon, ModItems.nugget_daffergon, ModItems.nugget_daffergon });
		addShapelessRecipe(new ItemStack(ModItems.rod_verticium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_verticium, ModItems.nugget_verticium, ModItems.nugget_verticium, ModItems.nugget_verticium, ModItems.nugget_verticium, ModItems.nugget_verticium });
		addShapelessRecipe(new ItemStack(ModItems.rod_balefire, 1), new Object[] { ModItems.rod_empty, ModItems.egg_balefire_shard });

		addRodBilletUnload(ModItems.billet_uranium, ModItems.rod_uranium);
		addRodBilletUnload(ModItems.billet_u233, ModItems.rod_u233);
		addRodBilletUnload(ModItems.billet_u235, ModItems.rod_u235);
		addRodBilletUnload(ModItems.billet_u238, ModItems.rod_u238);
		addRodBilletUnload(ModItems.billet_th232, ModItems.rod_th232);
		addRodBilletUnload(ModItems.billet_plutonium, ModItems.rod_plutonium);
		addRodBilletUnload(ModItems.billet_pu238, ModItems.rod_pu238);
		addRodBilletUnload(ModItems.billet_pu239, ModItems.rod_pu239);
		addRodBilletUnload(ModItems.billet_pu240, ModItems.rod_pu240);
		addRodBilletUnload(ModItems.billet_neptunium, ModItems.rod_neptunium);
		addRodBilletUnload(ModItems.billet_polonium, ModItems.rod_polonium);
		addRodBilletUnload(ModItems.billet_schrabidium, ModItems.rod_schrabidium);
		addRodBilletUnload(ModItems.billet_solinium, ModItems.rod_solinium);
		addRodBillet(ModItems.billet_uranium_fuel, ModItems.rod_uranium_fuel);
		addRodBillet(ModItems.billet_thorium_fuel, ModItems.rod_thorium_fuel);
		addRodBillet(ModItems.billet_plutonium_fuel, ModItems.rod_plutonium_fuel);
		addRodBillet(ModItems.billet_mox_fuel, ModItems.rod_mox_fuel);
		addRodBillet(ModItems.billet_schrabidium_fuel, ModItems.rod_schrabidium_fuel);

		addShapelessRecipe(new ItemStack(ModItems.rod_empty, 2), new Object[] { ModItems.rod_dual_empty });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_empty, 1), new Object[] { ModItems.rod_empty, ModItems.rod_empty });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_th232, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_th232, ModItems.nugget_th232, ModItems.nugget_th232, ModItems.nugget_th232 });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_uranium, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_u233, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_u233, ModItems.nugget_u233, ModItems.nugget_u233, ModItems.nugget_u233 });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_u235, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235 });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_u238, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238 });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_plutonium, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_pu238, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238 });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_pu239, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239 });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_pu240, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240 });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_neptunium, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_polonium, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_polonium, ModItems.nugget_polonium, ModItems.nugget_polonium, ModItems.nugget_polonium });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_lead, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_schrabidium, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_solinium, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_solinium, ModItems.nugget_solinium, ModItems.nugget_solinium, ModItems.nugget_solinium });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_uranium_fuel, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_thorium_fuel, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_thorium_fuel, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_plutonium_fuel, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_mox_fuel, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_schrabidium_fuel, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_balefire, 1), new Object[] { ModItems.rod_dual_empty, ModItems.egg_balefire_shard, ModItems.egg_balefire_shard });

		addDualRodBilletUnload(ModItems.billet_uranium, ModItems.rod_dual_uranium);
		addDualRodBilletUnload(ModItems.billet_u233, ModItems.rod_dual_u233);
		addDualRodBilletUnload(ModItems.billet_u235, ModItems.rod_dual_u235);
		addDualRodBilletUnload(ModItems.billet_u238, ModItems.rod_dual_u238);
		addDualRodBilletUnload(ModItems.billet_th232, ModItems.rod_dual_th232);
		addDualRodBilletUnload(ModItems.billet_plutonium, ModItems.rod_dual_plutonium);
		addDualRodBilletUnload(ModItems.billet_pu238, ModItems.rod_dual_pu238);
		addDualRodBilletUnload(ModItems.billet_pu239, ModItems.rod_dual_pu239);
		addDualRodBilletUnload(ModItems.billet_pu240, ModItems.rod_dual_pu240);
		addDualRodBilletUnload(ModItems.billet_neptunium, ModItems.rod_dual_neptunium);
		addDualRodBilletUnload(ModItems.billet_polonium, ModItems.rod_dual_polonium);
		addDualRodBilletUnload(ModItems.billet_schrabidium, ModItems.rod_dual_schrabidium);
		addDualRodBilletUnload(ModItems.billet_solinium, ModItems.rod_dual_solinium);
		addDualRodBillet(ModItems.billet_uranium_fuel, ModItems.rod_dual_uranium_fuel);
		addDualRodBillet(ModItems.billet_thorium_fuel, ModItems.rod_dual_thorium_fuel);
		addDualRodBillet(ModItems.billet_plutonium_fuel, ModItems.rod_dual_plutonium_fuel);
		addDualRodBillet(ModItems.billet_mox_fuel, ModItems.rod_dual_mox_fuel);
		addDualRodBillet(ModItems.billet_schrabidium_fuel, ModItems.rod_dual_schrabidium_fuel);

		addShapelessRecipe(new ItemStack(ModItems.rod_lithium, 1), new Object[] { ModItems.rod_empty, ModItems.lithium });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_lithium, 1), new Object[] { ModItems.rod_dual_empty, ModItems.lithium, ModItems.lithium });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_lithium, 1), new Object[] { ModItems.rod_quad_empty, ModItems.lithium, ModItems.lithium, ModItems.lithium, ModItems.lithium });
		addShapelessRecipe(ItemCell.getFullCell(ModForgeFluids.tritium, 1), new Object[] { ModItems.rod_tritium, ModItems.cell });
		addShapelessRecipe(ItemCell.getFullCell(ModForgeFluids.tritium, 2), new Object[] { ModItems.rod_dual_tritium, ModItems.cell, ModItems.cell });
		addShapelessRecipe(ItemCell.getFullCell(ModForgeFluids.tritium, 4), new Object[] { ModItems.rod_quad_tritium, ModItems.cell, ModItems.cell, ModItems.cell, ModItems.cell });

		addShapelessRecipe(new ItemStack(ModItems.rod_empty, 4), new Object[] { ModItems.rod_quad_empty });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_empty, 1), new Object[] { ModItems.rod_empty, ModItems.rod_empty, ModItems.rod_empty, ModItems.rod_empty });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_empty, 1), new Object[] { ModItems.rod_dual_empty, ModItems.rod_dual_empty });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_th232, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_th232, ModItems.ingot_th232, ModItems.nugget_th232, ModItems.nugget_th232, ModItems.nugget_th232, ModItems.nugget_th232, ModItems.nugget_th232, ModItems.nugget_th232 });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_uranium, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_uranium, ModItems.ingot_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_u233, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_u233, ModItems.ingot_u233, ModItems.nugget_u233, ModItems.nugget_u233, ModItems.nugget_u233, ModItems.nugget_u233, ModItems.nugget_u233, ModItems.nugget_u233 });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_u235, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_u235, ModItems.ingot_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235 });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_u238, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_u238, ModItems.ingot_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238 });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_plutonium, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_plutonium, ModItems.ingot_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_pu238, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_pu238, ModItems.ingot_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238 });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_pu239, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_pu239, ModItems.ingot_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239 });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_pu240, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_pu240, ModItems.ingot_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240 });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_neptunium, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_neptunium, ModItems.ingot_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_polonium, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_polonium, ModItems.ingot_polonium, ModItems.nugget_polonium, ModItems.nugget_polonium, ModItems.nugget_polonium, ModItems.nugget_polonium, ModItems.nugget_polonium, ModItems.nugget_polonium });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_lead, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_lead, ModItems.ingot_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_schrabidium, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_schrabidium, ModItems.ingot_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_solinium, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_solinium, ModItems.ingot_solinium, ModItems.nugget_solinium, ModItems.nugget_solinium, ModItems.nugget_solinium, ModItems.nugget_solinium, ModItems.nugget_solinium, ModItems.nugget_solinium });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_uranium_fuel, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_uranium_fuel, ModItems.ingot_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_thorium_fuel, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_thorium_fuel, ModItems.ingot_thorium_fuel, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_plutonium_fuel, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_plutonium_fuel, ModItems.ingot_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_mox_fuel, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_mox_fuel, ModItems.ingot_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_schrabidium_fuel, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_schrabidium_fuel, ModItems.ingot_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_balefire, 1), new Object[] { ModItems.rod_quad_empty, ModItems.egg_balefire_shard, ModItems.egg_balefire_shard, ModItems.egg_balefire_shard, ModItems.egg_balefire_shard });

		addQuadRodBilletUnload(ModItems.billet_uranium, ModItems.rod_quad_uranium);
		addQuadRodBilletUnload(ModItems.billet_u233, ModItems.rod_quad_u233);
		addQuadRodBilletUnload(ModItems.billet_u235, ModItems.rod_quad_u235);
		addQuadRodBilletUnload(ModItems.billet_u238, ModItems.rod_quad_u238);
		addQuadRodBilletUnload(ModItems.billet_th232, ModItems.rod_quad_th232);
		addQuadRodBilletUnload(ModItems.billet_plutonium, ModItems.rod_quad_plutonium);
		addQuadRodBilletUnload(ModItems.billet_pu238, ModItems.rod_quad_pu238);
		addQuadRodBilletUnload(ModItems.billet_pu239, ModItems.rod_quad_pu239);
		addQuadRodBilletUnload(ModItems.billet_pu240, ModItems.rod_quad_pu240);
		addQuadRodBilletUnload(ModItems.billet_neptunium, ModItems.rod_quad_neptunium);
		addQuadRodBilletUnload(ModItems.billet_polonium, ModItems.rod_quad_polonium);
		addQuadRodBilletUnload(ModItems.billet_schrabidium, ModItems.rod_quad_schrabidium);
		addQuadRodBilletUnload(ModItems.billet_solinium, ModItems.rod_quad_solinium);
		addQuadRodBillet(ModItems.billet_uranium_fuel, ModItems.rod_quad_uranium_fuel);
		addQuadRodBillet(ModItems.billet_thorium_fuel, ModItems.rod_quad_thorium_fuel);
		addQuadRodBillet(ModItems.billet_plutonium_fuel, ModItems.rod_quad_plutonium_fuel);
		addQuadRodBillet(ModItems.billet_mox_fuel, ModItems.rod_quad_mox_fuel);
		addQuadRodBillet(ModItems.billet_schrabidium_fuel, ModItems.rod_quad_schrabidium_fuel);

		addShapelessRecipe(new ItemStack(ModItems.rod_water, 1), new Object[] { ModItems.rod_empty, Items.WATER_BUCKET });
		addShapelessRecipe(new ItemStack(ModItems.rod_dual_water, 1), new Object[] { ModItems.rod_dual_empty, Items.WATER_BUCKET, Items.WATER_BUCKET });
		addShapelessRecipe(new ItemStack(ModItems.rod_quad_water, 1), new Object[] { ModItems.rod_quad_empty, Items.WATER_BUCKET, Items.WATER_BUCKET, Items.WATER_BUCKET, Items.WATER_BUCKET });

		addShapelessRecipe(new ItemStack(ModItems.nugget_th232, 6), new Object[] { ModItems.rod_th232 });
		addShapelessRecipe(new ItemStack(ModItems.nugget_uranium, 6), new Object[] { ModItems.rod_uranium });
		addShapelessRecipe(new ItemStack(ModItems.nugget_u233, 6), new Object[] { ModItems.rod_u233 });
		addShapelessRecipe(new ItemStack(ModItems.nugget_u235, 6), new Object[] { ModItems.rod_u235 });
		addShapelessRecipe(new ItemStack(ModItems.nugget_u238, 6), new Object[] { ModItems.rod_u238 });
		addShapelessRecipe(new ItemStack(ModItems.nugget_plutonium, 6), new Object[] { ModItems.rod_plutonium });
		addShapelessRecipe(new ItemStack(ModItems.nugget_pu238, 6), new Object[] { ModItems.rod_pu238 });
		addShapelessRecipe(new ItemStack(ModItems.nugget_pu239, 6), new Object[] { ModItems.rod_pu239 });
		addShapelessRecipe(new ItemStack(ModItems.nugget_pu240, 6), new Object[] { ModItems.rod_pu240 });
		addShapelessRecipe(new ItemStack(ModItems.nugget_neptunium, 6), new Object[] { ModItems.rod_neptunium });
		addShapelessRecipe(new ItemStack(ModItems.nugget_polonium, 6), new Object[] { ModItems.rod_polonium });
		addShapelessRecipe(new ItemStack(ModItems.nugget_lead, 6), new Object[] { ModItems.rod_lead });
		addShapelessRecipe(new ItemStack(ModItems.nugget_schrabidium, 6), new Object[] { ModItems.rod_schrabidium });
		addShapelessRecipe(new ItemStack(ModItems.nugget_solinium, 6), new Object[] { ModItems.rod_solinium });
		/*addShapelessRecipe(new ItemStack(ModItems.nugget_uranium_fuel, 6), new Object[] { ModItems.rod_uranium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.nugget_plutonium_fuel, 6), new Object[] { ModItems.rod_plutonium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.nugget_mox_fuel, 6), new Object[] { ModItems.rod_mox_fuel });
		addShapelessRecipe(new ItemStack(ModItems.nugget_schrabidium_fuel, 6), new Object[] { ModItems.rod_schrabidium_fuel });*/
		addShapelessRecipe(new ItemStack(ModItems.nugget_australium, 6), new Object[] { ModItems.rod_australium });
		addShapelessRecipe(new ItemStack(ModItems.nugget_weidanium, 6), new Object[] { ModItems.rod_weidanium });
		addShapelessRecipe(new ItemStack(ModItems.nugget_reiium, 6), new Object[] { ModItems.rod_reiium });
		addShapelessRecipe(new ItemStack(ModItems.nugget_unobtainium, 6), new Object[] { ModItems.rod_unobtainium });
		addShapelessRecipe(new ItemStack(ModItems.nugget_daffergon, 6), new Object[] { ModItems.rod_daffergon });
		addShapelessRecipe(new ItemStack(ModItems.nugget_verticium, 6), new Object[] { ModItems.rod_verticium });
		addShapelessRecipe(new ItemStack(ModItems.nugget_euphemium, 6), new Object[] { ModItems.rod_euphemium });
		addShapelessRecipe(new ItemStack(ModItems.egg_balefire_shard, 1), new Object[] { ModItems.rod_balefire });

		addShapelessRecipe(new ItemStack(ModItems.nugget_th232, 12), new Object[] { ModItems.rod_dual_th232 });
		addShapelessRecipe(new ItemStack(ModItems.nugget_uranium, 12), new Object[] { ModItems.rod_dual_uranium });
		addShapelessRecipe(new ItemStack(ModItems.nugget_u233, 12), new Object[] { ModItems.rod_dual_u233 });
		addShapelessRecipe(new ItemStack(ModItems.nugget_u235, 12), new Object[] { ModItems.rod_dual_u235 });
		addShapelessRecipe(new ItemStack(ModItems.nugget_u238, 12), new Object[] { ModItems.rod_dual_u238 });
		addShapelessRecipe(new ItemStack(ModItems.nugget_plutonium, 12), new Object[] { ModItems.rod_dual_plutonium });
		addShapelessRecipe(new ItemStack(ModItems.nugget_pu238, 12), new Object[] { ModItems.rod_dual_pu238 });
		addShapelessRecipe(new ItemStack(ModItems.nugget_pu239, 12), new Object[] { ModItems.rod_dual_pu239 });
		addShapelessRecipe(new ItemStack(ModItems.nugget_pu240, 12), new Object[] { ModItems.rod_dual_pu240 });
		addShapelessRecipe(new ItemStack(ModItems.nugget_neptunium, 12), new Object[] { ModItems.rod_dual_neptunium });
		addShapelessRecipe(new ItemStack(ModItems.nugget_polonium, 12), new Object[] { ModItems.rod_dual_polonium });
		addShapelessRecipe(new ItemStack(ModItems.nugget_lead, 12), new Object[] { ModItems.rod_dual_lead });
		addShapelessRecipe(new ItemStack(ModItems.nugget_schrabidium, 12), new Object[] { ModItems.rod_dual_schrabidium });
		addShapelessRecipe(new ItemStack(ModItems.nugget_solinium, 12), new Object[] { ModItems.rod_dual_solinium });
		/*addShapelessRecipe(new ItemStack(ModItems.nugget_uranium_fuel, 12), new Object[] { ModItems.rod_dual_uranium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.nugget_plutonium_fuel, 12), new Object[] { ModItems.rod_dual_plutonium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.nugget_mox_fuel, 12), new Object[] { ModItems.rod_dual_mox_fuel });
		addShapelessRecipe(new ItemStack(ModItems.nugget_schrabidium_fuel, 12), new Object[] { ModItems.rod_dual_schrabidium_fuel });*/
		addShapelessRecipe(new ItemStack(ModItems.egg_balefire_shard, 2), new Object[] { ModItems.rod_dual_balefire });

		addShapelessRecipe(new ItemStack(ModItems.nugget_th232, 24), new Object[] { ModItems.rod_quad_th232 });
		addShapelessRecipe(new ItemStack(ModItems.nugget_uranium, 24), new Object[] { ModItems.rod_quad_uranium });
		addShapelessRecipe(new ItemStack(ModItems.nugget_u233, 24), new Object[] { ModItems.rod_quad_u233 });
		addShapelessRecipe(new ItemStack(ModItems.nugget_u235, 24), new Object[] { ModItems.rod_quad_u235 });
		addShapelessRecipe(new ItemStack(ModItems.nugget_u238, 24), new Object[] { ModItems.rod_quad_u238 });
		addShapelessRecipe(new ItemStack(ModItems.nugget_plutonium, 24), new Object[] { ModItems.rod_quad_plutonium });
		addShapelessRecipe(new ItemStack(ModItems.nugget_pu238, 24), new Object[] { ModItems.rod_quad_pu238 });
		addShapelessRecipe(new ItemStack(ModItems.nugget_pu239, 24), new Object[] { ModItems.rod_quad_pu239 });
		addShapelessRecipe(new ItemStack(ModItems.nugget_pu240, 24), new Object[] { ModItems.rod_quad_pu240 });
		addShapelessRecipe(new ItemStack(ModItems.nugget_neptunium, 24), new Object[] { ModItems.rod_quad_neptunium });
		addShapelessRecipe(new ItemStack(ModItems.nugget_polonium, 24), new Object[] { ModItems.rod_quad_polonium });
		addShapelessRecipe(new ItemStack(ModItems.nugget_lead, 24), new Object[] { ModItems.rod_quad_lead });
		addShapelessRecipe(new ItemStack(ModItems.nugget_schrabidium, 24), new Object[] { ModItems.rod_quad_schrabidium });
		addShapelessRecipe(new ItemStack(ModItems.nugget_solinium, 24), new Object[] { ModItems.rod_quad_solinium });
		/*addShapelessRecipe(new ItemStack(ModItems.nugget_uranium_fuel, 24), new Object[] { ModItems.rod_quad_uranium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.nugget_plutonium_fuel, 24), new Object[] { ModItems.rod_quad_plutonium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.nugget_mox_fuel, 24), new Object[] { ModItems.rod_quad_mox_fuel });
		addShapelessRecipe(new ItemStack(ModItems.nugget_schrabidium_fuel, 24), new Object[] { ModItems.rod_quad_schrabidium_fuel });*/
		addShapelessRecipe(new ItemStack(ModItems.egg_balefire_shard, 4), new Object[] { ModItems.rod_quad_balefire });

		addShapelessRecipe(new ItemStack(ModItems.waste_uranium_hot, 1), new Object[] { ModItems.rod_uranium_fuel_depleted });
		addShapelessRecipe(new ItemStack(ModItems.waste_uranium_hot, 2), new Object[] { ModItems.rod_dual_uranium_fuel_depleted });
		addShapelessRecipe(new ItemStack(ModItems.waste_uranium_hot, 4), new Object[] { ModItems.rod_quad_uranium_fuel_depleted });
		addShapelessRecipe(new ItemStack(ModItems.waste_thorium_hot, 1), new Object[] { ModItems.rod_thorium_fuel_depleted });
		addShapelessRecipe(new ItemStack(ModItems.waste_thorium_hot, 2), new Object[] { ModItems.rod_dual_thorium_fuel_depleted });
		addShapelessRecipe(new ItemStack(ModItems.waste_thorium_hot, 4), new Object[] { ModItems.rod_quad_thorium_fuel_depleted });
		addShapelessRecipe(new ItemStack(ModItems.waste_plutonium_hot, 1), new Object[] { ModItems.rod_plutonium_fuel_depleted });
		addShapelessRecipe(new ItemStack(ModItems.waste_plutonium_hot, 2), new Object[] { ModItems.rod_dual_plutonium_fuel_depleted });
		addShapelessRecipe(new ItemStack(ModItems.waste_plutonium_hot, 4), new Object[] { ModItems.rod_quad_plutonium_fuel_depleted });
		addShapelessRecipe(new ItemStack(ModItems.waste_mox_hot, 1), new Object[] { ModItems.rod_mox_fuel_depleted });
		addShapelessRecipe(new ItemStack(ModItems.waste_mox_hot, 2), new Object[] { ModItems.rod_dual_mox_fuel_depleted });
		addShapelessRecipe(new ItemStack(ModItems.waste_mox_hot, 4), new Object[] { ModItems.rod_quad_mox_fuel_depleted });
		addShapelessRecipe(new ItemStack(ModItems.waste_schrabidium_hot, 1), new Object[] { ModItems.rod_schrabidium_fuel_depleted });
		addShapelessRecipe(new ItemStack(ModItems.waste_schrabidium_hot, 2), new Object[] { ModItems.rod_dual_schrabidium_fuel_depleted });
		addShapelessRecipe(new ItemStack(ModItems.waste_schrabidium_hot, 4), new Object[] { ModItems.rod_quad_schrabidium_fuel_depleted });

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

		addShapelessOreRecipe(new ItemStack(ModBlocks.deco_aluminium), new Object[] { "ingotAluminum", ModBlocks.steel_scaffold });
		addShapelessOreRecipe(new ItemStack(ModBlocks.deco_beryllium), new Object[] { "ingotBeryllium", ModBlocks.steel_scaffold });
		addShapelessOreRecipe(new ItemStack(ModBlocks.deco_lead), new Object[] { "ingotLead", ModBlocks.steel_scaffold });
		addShapelessOreRecipe(new ItemStack(ModBlocks.deco_red_copper), new Object[] { "ingotMingrade", ModBlocks.steel_scaffold });
		addShapelessOreRecipe(new ItemStack(ModBlocks.deco_steel), new Object[] { "ingotSteel", ModBlocks.steel_scaffold });
		addShapelessOreRecipe(new ItemStack(ModBlocks.deco_titanium), new Object[] { "ingotTitanium", ModBlocks.steel_scaffold });
		addShapelessOreRecipe(new ItemStack(ModBlocks.deco_tungsten), new Object[] { "ingotTungsten", ModBlocks.steel_scaffold });

		addShapedRecipe(new ItemStack(ModItems.nugget_euphemium, 1), new Object[] { "#", '#', ModItems.rod_quad_euphemium });
		//Drillgon200: Gone, reduced.
		//addShapedRecipe(new ItemStack(ModItems.ingot_euphemium, 1), new Object[] { "###", "###", "###", '#', ModItems.rod_quad_euphemium });
		addShapedRecipe(new ItemStack(ModItems.ingot_euphemium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_euphemium });
		addShapedRecipe(new ItemStack(ModItems.nugget_euphemium, 9), new Object[] { "#", '#', ModItems.ingot_euphemium });

		addShapedOreRecipe(new ItemStack(ModItems.coil_copper, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_red_copper, 'I', "ingotIron" });
		addShapedOreRecipe(new ItemStack(ModItems.coil_advanced_alloy, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_advanced_alloy, 'I', "ingotIron" });
		addShapedOreRecipe(new ItemStack(ModItems.coil_gold, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_gold, 'I', "ingotIron" });
		addShapedOreRecipe(new ItemStack(ModItems.coil_copper_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', "plateIron", 'C', ModItems.coil_copper });
		addShapedOreRecipe(new ItemStack(ModItems.coil_advanced_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', "plateIron", 'C', ModItems.coil_advanced_alloy });
		addShapedOreRecipe(new ItemStack(ModItems.coil_gold_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', "plateIron", 'C', ModItems.coil_gold });
		addShapedOreRecipe(new ItemStack(ModItems.coil_tungsten, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_tungsten, 'I', "ingotIron" });
		addShapedOreRecipe(new ItemStack(ModItems.coil_magnetized_tungsten, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_magnetized_tungsten, 'I', "ingotIron" });
		addShapedOreRecipe(new ItemStack(ModItems.tank_steel, 2), new Object[] { "STS", "S S", "STS", 'S', "plateSteel", 'T', "plateTitanium" });
		addShapedOreRecipe(new ItemStack(ModItems.motor, 2), new Object[] { " R ", "ICI", "ITI", 'R', ModItems.wire_red_copper, 'T', ModItems.coil_copper_torus, 'I', "plateIron", 'C', ModItems.coil_copper });
		addShapedOreRecipe(new ItemStack(ModItems.motor, 2), new Object[] { " R ", "ICI", " T ", 'R', ModItems.wire_red_copper, 'T', ModItems.coil_copper_torus, 'I', "plateSteel", 'C', ModItems.coil_copper });
		addShapedOreRecipe(new ItemStack(ModItems.motor_desh, 1), new Object[] { "PCP", "DMD", "PCP", 'P', ModItems.ingot_polymer, 'C', ModItems.coil_gold_torus, 'D', "ingotDesh", 'M', ModItems.motor });
		addShapedRecipe(new ItemStack(ModItems.thermo_unit_endo, 1), new Object[] { "EEE", "ETE", "EEE", 'E', Item.getItemFromBlock(Blocks.ICE), 'T', ModItems.thermo_unit_empty });
		addShapedRecipe(new ItemStack(ModItems.thermo_unit_exo, 1), new Object[] { "LLL", "LTL", "LLL", 'L', Items.LAVA_BUCKET, 'T', ModItems.thermo_unit_empty });

		addShapedOreRecipe(new ItemStack(ModItems.cap_aluminium, 1), new Object[] { "PIP", 'P', "plateAluminum", 'I', "ingotAluminum" });
		addShapedOreRecipe(new ItemStack(ModItems.hull_small_steel, 1), new Object[] { "PPP", "   ", "PPP", 'P', "plateSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.hull_small_aluminium, 1), new Object[] { "PPP", "   ", "PPP", 'P', "plateAluminum" });
		addShapedOreRecipe(new ItemStack(ModItems.hull_big_steel, 1), new Object[] { "III", "   ", "III", 'I', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.hull_big_aluminium, 1), new Object[] { "III", "   ", "III", 'I', "ingotAluminum" });
		addShapedOreRecipe(new ItemStack(ModItems.hull_big_titanium, 1), new Object[] { "III", "   ", "III", 'I', "ingotTitanium" });
		addShapedOreRecipe(new ItemStack(ModItems.fins_flat, 1), new Object[] { "IP", "PP", "IP", 'P', "plateSteel", 'I', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.fins_small_steel, 1), new Object[] { " PP", "PII", " PP", 'P', "plateSteel", 'I', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.fins_big_steel, 1), new Object[] { " PI", "III", " PI", 'P', "plateSteel", 'I', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.fins_tri_steel, 1), new Object[] { " PI", "IIB", " PI", 'P', "plateSteel", 'I', "ingotSteel", 'B', "blockSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.fins_quad_titanium, 1), new Object[] { " PP", "III", " PP", 'P', "plateTitanium", 'I', "ingotTitanium" });
		addShapedOreRecipe(new ItemStack(ModItems.sphere_steel, 1), new Object[] { "PIP", "I I", "PIP", 'P', "plateSteel", 'I', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.pedestal_steel, 1), new Object[] { "P P", "P P", "III", 'P', "plateSteel", 'I', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.lemon, 1), new Object[] { " D ", "DSD", " D ", 'D', new ItemStack(Items.DYE, 1, 11), 'S', "stone" });
		addShapedOreRecipe(new ItemStack(ModItems.blade_titanium, 2), new Object[] { "TP", "TP", "TT", 'P', "plateTitanium", 'T', "ingotTitanium" });
		addShapedOreRecipe(new ItemStack(ModItems.turbine_titanium, 1), new Object[] { "BBB", "BSB", "BBB", 'B', ModItems.blade_titanium, 'S', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.rotor_steel, 3), new Object[] { "CCC", "SSS", "CCC", 'C', ModItems.coil_gold, 'S', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.generator_steel, 1), new Object[] { "RRR", "CCC", "SSS", 'C', ModItems.coil_gold_torus, 'S', "ingotSteel", 'R', ModItems.rotor_steel });
		addShapedOreRecipe(new ItemStack(ModItems.shimmer_head, 1), new Object[] { "SSS", "DTD", "SSS", 'S', "ingotSteel", 'D', "blockDesh", 'T', "blockTungsten" });
		addShapedOreRecipe(new ItemStack(ModItems.shimmer_axe_head, 1), new Object[] { "PII", "PBB", "PII", 'P', "plateSteel", 'B', "blockDesh", 'I', "ingotTungsten" });
		addShapedOreRecipe(new ItemStack(ModItems.shimmer_handle, 1), new Object[] { "GP", "GP", "GP", 'G', "plateGold", 'P', "ingotPolymer" });
		addShapedRecipe(new ItemStack(ModItems.shimmer_sledge, 1), new Object[] { "H", "G", "G", 'G', ModItems.shimmer_handle, 'H', ModItems.shimmer_head });
		addShapedRecipe(new ItemStack(ModItems.shimmer_axe, 1), new Object[] { "H", "G", "G", 'G', ModItems.shimmer_handle, 'H', ModItems.shimmer_axe_head });
		addShapedOreRecipe(new ItemStack(ModItems.definitelyfood, 1), new Object[] { "DDD", "SDS", "DDD", 'D', Blocks.DIRT, 'S', "plateSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.blade_tungsten, 2), new Object[] { "IP", "TP", "TI", 'P', "plateTitanium", 'T', "ingotTitanium", 'I', "ingotTungsten" });
		addShapedRecipe(new ItemStack(ModItems.turbine_tungsten, 1), new Object[] { "BBB", "BSB", "BBB", 'B', ModItems.blade_tungsten, 'S', ModItems.ingot_dura_steel });
		addShapedRecipe(new ItemStack(ModItems.ring_starmetal, 1), new Object[] { " S ", "S S", " S ", 'S', ModItems.ingot_starmetal });

		addShapedOreRecipe(new ItemStack(ModItems.wrench, 1), new Object[] { " S ", " IS", "I  ", 'S', "ingotSteel", 'I', "ingotIron" });
		addShapedRecipe(new ItemStack(ModItems.wrench_flipped, 1), new Object[] { "S", "D", "W", 'S', Items.IRON_SWORD, 'D', ModItems.ducttape, 'W', ModItems.wrench });
		addShapedOreRecipe(new ItemStack(ModItems.memespoon, 1), new Object[] { "CGC", "PSP", "IAI", 'C', ModItems.powder_cloud, 'G', ModBlocks.block_thorium, 'P', ModItems.photo_panel, 'S', ModItems.steel_shovel, 'I', ModItems.plate_polymer, 'A', "ingotAustralium" });
		addShapelessRecipe(new ItemStack(ModItems.cbt_device, 1), new Object[] { ModItems.bolt_tungsten, ModItems.wrench });

		addShapelessRecipe(new ItemStack(ModItems.toothpicks, 3), new Object[] { Items.STICK, Items.STICK, Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.ducttape, 6), new Object[] { "FSF", "SPS", "FSF", 'F', Items.STRING, 'S', Items.SLIME_BALL, 'P', Items.PAPER });

		addShapelessRecipe(new ItemStack(ModItems.missile_taint, 1), new Object[] { ModItems.missile_assembly, new IngredientContainsTag(FluidUtil.getFilledBucket(new FluidStack(ModForgeFluids.mud_fluid, 1000))), ModItems.powder_spark_mix, ModItems.powder_magic });
		addShapelessRecipe(new ItemStack(ModItems.missile_micro, 1), new Object[] { ModItems.missile_assembly, ModItems.ducttape, ModItems.gun_fatman_ammo });
		addShapelessRecipe(new ItemStack(ModItems.missile_bhole, 1), new Object[] { ModItems.missile_assembly, ModItems.ducttape, ModItems.grenade_black_hole });
		addShapelessRecipe(new ItemStack(ModItems.missile_schrabidium, 1), new Object[] { ModItems.missile_assembly, ModItems.ducttape, ModItems.grenade_aschrab });
		addShapelessRecipe(new ItemStack(ModItems.missile_schrabidium, 1), new Object[] { ModItems.missile_assembly, ModItems.ducttape, new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.sas3)), ModItems.circuit_targeting_tier4 });
		addShapelessRecipe(new ItemStack(ModItems.missile_emp, 1), new Object[] { ModItems.missile_assembly, ModItems.ducttape, ModBlocks.emp_bomb, ModItems.circuit_targeting_tier3 });
		addShapelessRecipe(new ItemStack(ModItems.missile_anti_ballistic, 1), new Object[] { ModItems.missile_generic, ModItems.circuit_targeting_tier3 });

		addShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_difurnace_off), 1), new Object[] { "T T", "PHP", "TFT", 'T', "ingotTungsten", 'P', ModItems.board_copper, 'H', Blocks.HOPPER, 'F', Blocks.FURNACE });
		addShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_uf6_tank), 1), new Object[] { "WTW", "WTW", "SRS", 'S', "plateIron", 'W', ModItems.coil_tungsten, 'T', ModItems.tank_steel, 'W', ModItems.coil_tungsten, 'R', "ingotMingrade" });
		addShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_puf6_tank), 1), new Object[] { "WTW", "WTW", "SRS", 'S', "plateSteel", 'W', ModItems.coil_tungsten, 'T', ModItems.tank_steel, 'W', ModItems.coil_tungsten, 'R', "ingotMingrade" });
		addShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_nuke_furnace_off), 1), new Object[] { "SSS", "LFL", "CCC", 'S', "plateSteel", 'C', ModItems.board_copper, 'L', "plateLead", 'F', Item.getItemFromBlock(Blocks.FURNACE) });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_electric_furnace_off), 1), new Object[] { "BBB", "WFW", "RRR", 'B', ModItems.ingot_beryllium, 'R', ModItems.coil_tungsten, 'W', ModItems.board_copper, 'F', Item.getItemFromBlock(Blocks.FURNACE) });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_arc_furnace_off, 1), new Object[] { "ITI", "PFP", "ITI", 'I', "ingotTungsten", 'T', ModBlocks.machine_transformer, 'P', ModItems.board_copper, 'F', Blocks.FURNACE });
		addShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.red_wire_coated), 16), new Object[] { "WRW", "RIR", "WRW", 'W', ModItems.plate_polymer, 'I', "ingotMingrade", 'R', ModItems.wire_red_copper });
		addShapedRecipe(new ItemStack(ModBlocks.cable_switch, 1), new Object[] { "S", "W", 'S', Blocks.LEVER, 'W', ModBlocks.red_wire_coated });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.red_cable), 16), new Object[] { " W ", "RRR", " W ", 'W', ModItems.plate_polymer, 'R', ModItems.wire_red_copper });
		addShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.red_pylon), 4), new Object[] { "CWC", "PWP", " T ", 'C', ModItems.coil_copper_torus, 'W', "plankWood", 'P', ModItems.plate_polymer, 'T', ModBlocks.red_wire_coated });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_battery_potato, 1), new Object[] { "PCP", "WRW", "PCP", 'P', ItemBattery.getEmptyBattery(ModItems.battery_potato), 'C', "ingotCopper", 'R', Blocks.REDSTONE_BLOCK, 'W', "plankWood" });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_coal_off, 1), new Object[] { "STS", "SCS", "SFS", 'S', "ingotSteel", 'T', ModItems.tank_steel, 'C', "ingotMingrade", 'F', Blocks.FURNACE });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_boiler_off, 1), new Object[] { "SPS", "TFT", "SPS", 'S', "ingotSteel", 'P', ModItems.board_copper, 'T', ModItems.tank_steel, 'F', Blocks.FURNACE });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_boiler_electric_off, 1), new Object[] { "SPS", "TFT", "SPS", 'S', "ingotDesh", 'P', ModItems.board_copper, 'T', ModItems.tank_steel, 'F', ModBlocks.machine_electric_furnace_off });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_turbine, 1), new Object[] { "PTP", "BMB", "PTP", 'P', "plateTitanium", 'T', ModItems.turbine_titanium, 'B', ModItems.tank_steel, 'M', ModItems.motor });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_converter_he_rf, 1), new Object[] { "SSS", "CRB", "SSS", 'S', "ingotSteel", 'C', ModItems.coil_copper, 'R', ModItems.coil_copper_torus, 'B', "blockRedstone" });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_converter_rf_he, 1), new Object[] { "SSS", "BRC", "SSS", 'S', ModItems.ingot_beryllium, 'C', ModItems.coil_copper, 'R', ModItems.coil_copper_torus, 'B', "blockRedstone" });
		addShapedOreRecipe(new ItemStack(ModBlocks.crate_iron, 1), new Object[] { "PPP", "I I", "III", 'P', "plateIron", 'I', "ingotIron" });
		addShapedOreRecipe(new ItemStack(ModBlocks.crate_steel, 1), new Object[] { "PPP", "I I", "III", 'P', "plateSteel", 'I', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModBlocks.crate_tungsten, 1), new Object[] { "BPB", "PCP", "BPB", 'B', "blockTungsten", 'P', ModItems.board_copper, 'C', ModBlocks.crate_steel });
		addShapedOreRecipe(new ItemStack(ModBlocks.safe, 1), new Object[] { "LAL", "ACA", "LAL", 'L', "plateLead", 'A', "plateAdvanced", 'C', ModBlocks.crate_steel });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_waste_drum, 1), new Object[] { "LRL", "BRB", "LRL", 'L', "ingotLead", 'B', Blocks.IRON_BARS, 'R', ModItems.rod_quad_empty });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_press, 1), new Object[] { "IRI", "IPI", "IBI", 'I', "ingotIron", 'R', Blocks.FURNACE, 'B', "blockIron", 'P', Blocks.PISTON });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_siren, 1), new Object[] { "SIS", "ICI", "SRS", 'S', "plateSteel", 'I', ModItems.plate_polymer, 'C', ModItems.circuit_copper, 'R', "dustRedstone" });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_microwave, 1), new Object[] { "III", "SGM", "IDI", 'I', ModItems.plate_polymer, 'S', "plateSteel", 'G', "paneGlass", 'M', ModItems.magnetron, 'D', ModItems.motor });

		addShapedRecipe(new ItemStack(ModBlocks.muffler, 1), new Object[] { "III", "IWI", "III", 'I', ModItems.plate_polymer, 'W', Blocks.WOOL });

		addShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_titanium_hull), 1), new Object[] { "PIP", "I I", "PIP", 'P', "plateTitanium", 'I', "ingotTitanium" });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_titanium_furnace), 1), new Object[] { "HMH", "MFM", "HMH", 'H', Item.getItemFromBlock(ModBlocks.factory_titanium_hull), 'M', ModItems.motor, 'F', Item.getItemFromBlock(Blocks.FURNACE) });
		addShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_titanium_conductor), 1), new Object[] { "SWS", "FFF", "SWS", 'S', "ingotTitanium", 'W', Item.getItemFromBlock(ModBlocks.red_wire_coated), 'F', ModItems.fuse });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_titanium_core), 1), new Object[] { "HPH", "PCP", "HPH", 'H', Item.getItemFromBlock(ModBlocks.factory_titanium_hull), 'C', ModItems.circuit_aluminium, 'P', Item.getItemFromBlock(Blocks.PISTON) });
		addShapedRecipe(ItemBattery.getEmptyBattery(ModItems.factory_core_titanium), new Object[] { "BRB", "RHR", "BRB", 'B', ItemBattery.getEmptyBattery(ModItems.battery_generic), 'R', Item.getItemFromBlock(Blocks.REDSTONE_BLOCK), 'H', Item.getItemFromBlock(ModBlocks.factory_titanium_hull) });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.factory_core_advanced), new Object[] { "BLB", "SHS", "BLB", 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced), 'S', "blockSulfur", 'L', "blockLead", 'H', Item.getItemFromBlock(ModBlocks.factory_advanced_hull) });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.factory_core_advanced), new Object[] { "BSB", "LHL", "BSB", 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced), 'S', "blockSulfur", 'L', "blockLead", 'H', Item.getItemFromBlock(ModBlocks.factory_advanced_hull) });

		addShapedOreRecipe(new ItemStack(ModItems.arc_electrode, 1), new Object[] { "C", "T", "C", 'C', "dustCoal", 'T', ModItems.bolt_tungsten });
		addShapedOreRecipe(new ItemStack(ModItems.arc_electrode_desh, 1), new Object[] { "C", "T", "C", 'C', "dustDesh", 'T', ModItems.arc_electrode });
		addShapedOreRecipe(new ItemStack(ModItems.detonator, 1), new Object[] { " W", "SC", "CE", 'S', "plateSteel", 'W', ModItems.wire_red_copper, 'C', ModItems.circuit_red_copper, 'E', "ingotSteel" });
		addShapelessRecipe(new ItemStack(ModItems.detonator_multi, 1), new Object[] { ModItems.detonator, ModItems.circuit_targeting_tier3 });
		addShapedOreRecipe(new ItemStack(ModItems.detonator_laser, 1), new Object[] { "RRD", "PIC", "  P", 'P', "plateSteel", 'R', Items.REDSTONE, 'C', ModItems.circuit_targeting_tier3, 'D', "gemDiamond", 'I', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.detonator_laser, 1), new Object[] { "RRD", "PIC", "  P", 'P', "plateSteel", 'R', Items.REDSTONE, 'C', ModItems.circuit_targeting_tier3, 'D', "gemEmerald", 'I', "ingotSteel" });
		addShapelessRecipe(new ItemStack(ModItems.detonator_deadman, 1), new Object[] { ModItems.detonator, ModItems.defuser, ModItems.ducttape });
		addShapedRecipe(new ItemStack(ModItems.detonator_de, 1), new Object[] { "T", "D", "T", 'T', Blocks.TNT, 'D', ModItems.detonator_deadman });

		addShapedRecipe(ItemBombCaller.getStack(EnumCallerType.CARPET), new Object[] { "TTT", "TRT", "TTT", 'T', Blocks.TNT, 'R', ModItems.detonator_laser });
		addShapedRecipe(ItemBombCaller.getStack(EnumCallerType.NAPALM), new Object[] { "TTT", "TRT", "TTT", 'T', ModItems.grenade_gascan, 'R', ModItems.detonator_laser });
		addShapedRecipe(ItemBombCaller.getStack(EnumCallerType.POISON), new Object[] { "TTT", "TRT", "TTT", 'T', ModItems.pellet_gas, 'R', ModItems.detonator_laser });
		addShapedRecipe(ItemBombCaller.getStack(EnumCallerType.ORANGE), new Object[] { "TRT", 'T', ModItems.grenade_cloud, 'R', ModItems.detonator_laser });
		addShapedRecipe(ItemBombCaller.getStack(EnumCallerType.ATOMIC), new Object[] { "TRT", 'T', ModItems.gun_fatman_ammo, 'R', ModItems.detonator_laser });

		addShapedRecipe(new ItemStack(ModItems.singularity, 1), new Object[] { "ESE", "SBS", "ESE", 'E', ModItems.nugget_euphemium, 'S', new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.aschrab)), 'B', Item.getItemFromBlock(ModBlocks.block_schrabidium) });
		addShapedRecipe(new ItemStack(ModItems.singularity_counter_resonant, 1), new Object[] { "CTC", "TST", "CTC", 'C', ModItems.plate_combine_steel, 'T', ModItems.ingot_magnetized_tungsten, 'S', ModItems.singularity });
		addShapedRecipe(new ItemStack(ModItems.singularity_super_heated, 1), new Object[] { "CTC", "TST", "CTC", 'C', ModItems.plate_advanced_alloy, 'T', ModItems.powder_power, 'S', ModItems.singularity });
		addShapedRecipe(new ItemStack(ModItems.black_hole, 1), new Object[] { "SSS", "SCS", "SSS", 'C', ModItems.singularity, 'S', ModItems.crystal_xen });
		addShapedRecipe(new ItemStack(ModItems.crystal_xen, 1), new Object[] { "EEE", "EIE", "EEE", 'E', ModItems.powder_power, 'I', ModItems.ingot_euphemium });

		addShapedOreRecipe(new ItemStack(ModItems.screwdriver, 1), new Object[] { "  I", " I ", "S  ", 'S', "ingotSteel", 'I', "ingotIron" });
		addShapelessOreRecipe(new ItemStack(ModItems.overfuse, 1), new Object[] { ModItems.screwdriver, "dustNeptunium", ModItems.powder_iodine, ModItems.powder_thorium, ModItems.powder_astatine, ModItems.powder_neodymium, ModItems.board_copper, ModItems.black_hole, ModItems.powder_caesium });
		addShapelessOreRecipe(new ItemStack(ModItems.overfuse, 1), new Object[] { ModItems.screwdriver, ModItems.powder_strontium, ModItems.powder_bromine, ModItems.powder_cobalt, ModItems.powder_tennessine, ModItems.powder_niobium, ModItems.board_copper, ModItems.black_hole, ModItems.powder_cerium });
		addShapedRecipe(new ItemStack(ModItems.crystal_energy, 1), new Object[] { "EEE", "EGE", "EEE", 'E', ModItems.powder_power, 'G', Items.GLOWSTONE_DUST });
		addShapedRecipe(new ItemStack(ModItems.pellet_coolant, 1), new Object[] { "CRC", "RBR", "CRC", 'C', ModItems.powder_ice, 'R', ModItems.rod_quad_coolant, 'B', ModBlocks.block_niter });

		addShapedOreRecipe(new ItemStack(ModItems.blades_gold, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateGold", 'I', "ingotGold" });
		addShapedOreRecipe(new ItemStack(ModItems.blades_iron, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateIron", 'I', "ingotIron" });
		addShapedOreRecipe(new ItemStack(ModItems.blades_steel, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateSteel", 'I', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.blades_titanium, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateTitanium", 'I', "ingotTitanium" });
		addShapedOreRecipe(new ItemStack(ModItems.blades_advanced_alloy, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateAdvanced", 'I', "ingotAdvanced" });
		addShapedOreRecipe(new ItemStack(ModItems.blades_combine_steel, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateCMBSteel", 'I', "ingotCMBSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.blades_schrabidium, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateSchrabidium", 'I', "ingotSchrabidium" });
		addShapedOreRecipe(new ItemStack(ModItems.blades_desh, 1), new Object[] { "NPN", "PBP", "NPN", 'N', "nuggetSchrabidium", 'P', ModItems.plate_desh, 'B', ModItems.blades_combine_steel });

		addShapedOreRecipe(new ItemStack(ModItems.blades_aluminum, 1), new Object[] { "PIP", 'P', "plateAluminum", 'I', new ItemStack(ModItems.blades_aluminum, 1, OreDictionary.WILDCARD_VALUE) });
		addShapedOreRecipe(new ItemStack(ModItems.blades_gold, 1), new Object[] { "PIP", 'P', "plateGold", 'I', new ItemStack(ModItems.blades_gold, 1, OreDictionary.WILDCARD_VALUE) });
		addShapedOreRecipe(new ItemStack(ModItems.blades_iron, 1), new Object[] { "PIP", 'P', "plateIron", 'I', new ItemStack(ModItems.blades_iron, 1, OreDictionary.WILDCARD_VALUE) });
		addShapedOreRecipe(new ItemStack(ModItems.blades_steel, 1), new Object[] { "PIP", 'P', "plateSteel", 'I', new ItemStack(ModItems.blades_steel, 1, OreDictionary.WILDCARD_VALUE) });
		addShapedOreRecipe(new ItemStack(ModItems.blades_titanium, 1), new Object[] { "PIP", 'P', "plateTitanium", 'I', new ItemStack(ModItems.blades_titanium, 1, OreDictionary.WILDCARD_VALUE) });
		addShapedOreRecipe(new ItemStack(ModItems.blades_advanced_alloy, 1), new Object[] { "PIP", 'P', "plateAdvanced", 'I', new ItemStack(ModItems.blades_advanced_alloy, 1, OreDictionary.WILDCARD_VALUE) });
		addShapedOreRecipe(new ItemStack(ModItems.blades_combine_steel, 1), new Object[] { "PIP", 'P', "plateCMBSteel", 'I', new ItemStack(ModItems.blades_combine_steel, 1, OreDictionary.WILDCARD_VALUE) });
		addShapedOreRecipe(new ItemStack(ModItems.blades_schrabidium, 1), new Object[] { "PIP", 'P', "plateSchrabidium", 'I', new ItemStack(ModItems.blades_schrabidium, 1, OreDictionary.WILDCARD_VALUE) });

		addShapedOreRecipe(new ItemStack(ModItems.stamp_stone_flat, 1), new Object[] { " R ", "III", "SSS", 'R', "dustRedstone", 'I', "ingotBrick", 'S', "stone" });
		addShapedOreRecipe(new ItemStack(ModItems.stamp_iron_flat, 1), new Object[] { " R ", "III", "SSS", 'R', "dustRedstone", 'I', "ingotBrick", 'S', "ingotIron" });
		addShapedOreRecipe(new ItemStack(ModItems.stamp_steel_flat, 1), new Object[] { " R ", "III", "SSS", 'R', "dustRedstone", 'I', "ingotBrick", 'S', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.stamp_titanium_flat, 1), new Object[] { " R ", "III", "SSS", 'R', "dustRedstone", 'I', "ingotBrick", 'S', "ingotTitanium" });
		addShapedOreRecipe(new ItemStack(ModItems.stamp_obsidian_flat, 1), new Object[] { " R ", "III", "SSS", 'R', "dustRedstone", 'I', "ingotBrick", 'S', Blocks.OBSIDIAN });
		addShapedOreRecipe(new ItemStack(ModItems.stamp_schrabidium_flat, 1), new Object[] { " R ", "III", "SSS", 'R', "dustRedstone", 'I', "ingotBrick", 'S', "ingotSchrabidium" });

		addShapedOreRecipe(new ItemStack(ModItems.mechanism_revolver_1, 1), new Object[] { " II", "ICA", "IKW", 'I', "plateIron", 'C', "ingotCopper", 'A', "ingotAluminum", 'K', ModItems.wire_copper, 'W', ModItems.wire_aluminium });
		addShapedOreRecipe(new ItemStack(ModItems.mechanism_revolver_2, 1), new Object[] { " II", "ICA", "IKW", 'I', "plateAdvanced", 'C', "ingotDuraSteel", 'A', "ingotTungsten", 'K', ModItems.bolt_dura_steel, 'W', ModItems.bolt_tungsten });
		addShapedOreRecipe(new ItemStack(ModItems.mechanism_rifle_1, 1), new Object[] { "ICI", "CMA", "IAM", 'I', "plateIron", 'C', "ingotCopper", 'A', "ingotAluminum", 'M', ModItems.mechanism_revolver_1 });
		addShapedOreRecipe(new ItemStack(ModItems.mechanism_rifle_2, 1), new Object[] { "ICI", "CMA", "IAM", 'I', "plateAdvanced", 'C', "ingotDuraSteel", 'A', "ingotTungsten", 'M', ModItems.mechanism_revolver_2 });
		addShapedOreRecipe(new ItemStack(ModItems.mechanism_launcher_1, 1), new Object[] { "TTT", "SSS", "BBI", 'T', "plateTitanium", 'S', "ingotSteel", 'B', ModItems.bolt_tungsten, 'I', "ingotMingrade" });
		addShapedOreRecipe(new ItemStack(ModItems.mechanism_launcher_2, 1), new Object[] { "TTT", "SSS", "BBI", 'T', "plateAdvanced", 'S', "ingotPolymer", 'B', ModItems.bolt_dura_steel, 'I', "ingotDesh" });

		addShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.fwatz_cooler), 1), new Object[] { "IPI", "IPI", "IPI", 'I', "ingotTitanium", 'P', "plateTitanium" });
		addShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.fwatz_tank), 1), new Object[] { "CGC", "GGG", "CGC", 'C', ModItems.plate_combine_steel, 'G', "paneGlass" });
		addShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.fwatz_scaffold), 1), new Object[] { "IPI", "P P", "IPI", 'I', "ingotTungsten", 'P', OreDictManager.getReflector() });

		addShapedRecipe(new ItemStack(ModBlocks.reinforced_stone, 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.COBBLESTONE, 'B', Blocks.STONE });
		addShapedRecipe(new ItemStack(ModBlocks.brick_light, 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.OAK_FENCE, 'B', Blocks.BRICK_BLOCK });
		addShapedRecipe(new ItemStack(ModBlocks.brick_asbestos, 2), new Object[] { " A ", "ABA", " A ", 'B', ModBlocks.brick_light, 'A', ModItems.ingot_asbestos });
		addShapedRecipe(new ItemStack(ModBlocks.concrete, 4), new Object[] { "CC", "CC", 'C', ModBlocks.concrete_smooth });
		addShapedRecipe(new ItemStack(ModBlocks.concrete_pillar, 8), new Object[] { "CBC", "CBC", "CBC", 'C', ModBlocks.concrete_smooth, 'B', Blocks.IRON_BARS });
		addShapedRecipe(new ItemStack(ModBlocks.brick_concrete, 4), new Object[] { " C ", "CBC", " C ", 'C', ModBlocks.concrete_smooth, 'B', Items.CLAY_BALL });
		addShapedRecipe(new ItemStack(ModBlocks.brick_concrete, 4), new Object[] { " C ", "CBC", " C ", 'C', ModBlocks.concrete, 'B', Items.CLAY_BALL });
		addShapedRecipe(new ItemStack(ModBlocks.brick_concrete_mossy, 8), new Object[] { "CCC", "CVC", "CCC", 'C', ModBlocks.brick_concrete, 'V', Blocks.VINE });
		addShapedRecipe(new ItemStack(ModBlocks.brick_concrete_cracked, 6), new Object[] { " C ", "C C", " C ", 'C', ModBlocks.brick_concrete });
		addShapedRecipe(new ItemStack(ModBlocks.brick_concrete_broken, 6), new Object[] { " C ", "C C", " C ", 'C', ModBlocks.brick_concrete_cracked });
		addShapedRecipe(new ItemStack(ModBlocks.brick_obsidian, 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.IRON_BARS, 'B', Blocks.OBSIDIAN });
		addShapedRecipe(new ItemStack(ModBlocks.meteor_polished, 4), new Object[] { "CC", "CC", 'C', ModBlocks.block_meteor_broken });
		addShapedRecipe(new ItemStack(ModBlocks.meteor_pillar, 2), new Object[] { "C", "C", 'C', ModBlocks.meteor_polished });
		addShapedRecipe(new ItemStack(ModBlocks.meteor_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.meteor_polished });
		addShapedRecipe(new ItemStack(ModBlocks.meteor_brick_mossy, 8), new Object[] { "CCC", "CVC", "CCC", 'C', ModBlocks.meteor_brick, 'V', Blocks.VINE });
		addShapedRecipe(new ItemStack(ModBlocks.meteor_brick_cracked, 6), new Object[] { " C ", "C C", " C ", 'C', ModBlocks.meteor_brick });
		addShapedRecipe(new ItemStack(ModBlocks.meteor_battery, 1), new Object[] { "MSM", "MWM", "MSM", 'M', ModBlocks.meteor_polished, 'S', ModBlocks.block_starmetal, 'W', ModItems.wire_schrabidium });
		addShapedRecipe(new ItemStack(ModBlocks.tile_lab, 4), new Object[] { "CBC", "CBC", "CBC", 'C', Items.BRICK, 'B', ModItems.ingot_asbestos });
		addShapedRecipe(new ItemStack(ModBlocks.tile_lab_cracked, 6), new Object[] { " C ", "C C", " C ", 'C', ModBlocks.tile_lab });
		addShapedRecipe(new ItemStack(ModBlocks.tile_lab_broken, 6), new Object[] { " C ", "C C", " C ", 'C', ModBlocks.tile_lab_cracked });

		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_brick), 8), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.IRON_BARS, 'B', ModBlocks.brick_concrete });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.brick_compound), 8), new Object[] { "FBF", "BFB", "FBF", 'F', ModItems.bolt_tungsten, 'B', ModBlocks.reinforced_brick });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_glass), 8), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.IRON_BARS, 'B', Blocks.GLASS });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_light), 1), new Object[] { "FFF", "FBF", "FFF", 'F', Blocks.IRON_BARS, 'B', Blocks.GLOWSTONE });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_lamp_off), 1), new Object[] { "FFF", "FBF", "FFF", 'F', Blocks.IRON_BARS, 'B', Blocks.REDSTONE_LAMP });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_sand), 8), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.IRON_BARS, 'B', Blocks.SANDSTONE });

		addShapedOreRecipe(new ItemStack(ModBlocks.barbed_wire, 16), new Object[] { "AIA", "I I", "AIA", 'A', ModItems.wire_aluminium, 'I', "ingotIron" });
		addShapedRecipe(new ItemStack(ModBlocks.barbed_wire_fire, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', ModItems.powder_fire });
		addShapedRecipe(new ItemStack(ModBlocks.barbed_wire_poison, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', ModItems.powder_poison });
		addShapedRecipe(new ItemStack(ModBlocks.barbed_wire_acid, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', new IngredientContainsTag(ItemFluidTank.getFullTank(ModForgeFluids.acid)) });
		addShapedRecipe(new ItemStack(ModBlocks.barbed_wire_wither, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', new ItemStack(Items.SKULL, 1, 1) });
		addShapedRecipe(new ItemStack(ModBlocks.barbed_wire_ultradeath, 4), new Object[] { "BCB", "CIC", "BCB", 'B', ModBlocks.barbed_wire, 'C', ModItems.powder_cloud, 'I', ModItems.nuclear_waste });

		addShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.tape_recorder), 4), new Object[] { "TST", "SSS", 'T', "ingotTungsten", 'S', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_poles), 16), new Object[] { "S S", "SSS", "S S", 'S', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.pole_top), 1), new Object[] { "T T", "TRT", "BBB", 'T', "ingotTungsten", 'B', ModItems.ingot_beryllium, 'R', "ingotMingrade" });
		addShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.pole_satellite_receiver), 1), new Object[] { "SS ", "SCR", "SS ", 'S', "ingotSteel", 'C', ModItems.circuit_red_copper, 'R', ModItems.wire_red_copper });
		addShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_beam), 8), new Object[] { "S", "S", "S", 'S', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_wall), 4), new Object[] { "SSS", "SSS", 'S', "ingotSteel" });
		addShapelessRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_corner)), new Object[] { Item.getItemFromBlock(ModBlocks.steel_wall), Item.getItemFromBlock(ModBlocks.steel_wall) });
		addShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_roof), 2), new Object[] { "SSS", 'S', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_scaffold), 8), new Object[] { "SSS", " S ", "SSS", 'S', "ingotSteel" });

		addShapedOreRecipe(new ItemStack(ModItems.gun_rpg, 1), new Object[] { "SSW", " MW", 'S', ModItems.hull_small_steel, 'W', "plateIron", 'M', ModItems.mechanism_launcher_1 });
		addShapedOreRecipe(new ItemStack(ModItems.gun_panzerschreck, 1), new Object[] { "SSS", " MW", 'S', ModItems.hull_small_steel, 'W', "plateCopper", 'M', ModItems.mechanism_launcher_1 });
		addShapedOreRecipe(new ItemStack(ModItems.gun_karl, 1), new Object[] { "SSW", " MW", 'S', ModItems.hull_small_steel, 'W', "plateAdvanced", 'M', ModItems.mechanism_launcher_2 });
		addShapedOreRecipe(new ItemStack(ModItems.gun_quadro, 1), new Object[] { "SSS", "SSS", "CM ", 'S', ModItems.hull_small_steel, 'C', ModItems.circuit_targeting_tier3, 'M', ModItems.mechanism_launcher_2 });
		addShapedOreRecipe(new ItemStack(ModItems.gun_hk69, 1), new Object[] { "SSI", " MB", 'S', ModItems.hull_small_steel, 'I', "ingotIron", 'M', ModItems.mechanism_launcher_1, 'B', ModItems.bolt_tungsten });
		addShapedOreRecipe(new ItemStack(ModItems.gun_stinger, 1), new Object[] { "SSW", "CMW", 'S', "plateSteel", 'W', "plateTitanium", 'C', ModItems.circuit_red_copper, 'M', ModItems.mechanism_launcher_2 });
		addShapedOreRecipe(new ItemStack(ModItems.gun_stinger_ammo, 4), new Object[] { "SS ", "STI", " IR", 'S', "plateSteel", 'T', Item.getItemFromBlock(Blocks.TNT), 'I', "plateAluminum", 'R', "dustRedstone" });
		addShapedOreRecipe(new ItemStack(ModItems.gun_revolver, 1), new Object[] { "SSM", " RW", 'S', "plateSteel", 'W', "plankWood", 'R', ModItems.wire_aluminium, 'M', ModItems.mechanism_revolver_1 });
		addShapedOreRecipe(new ItemStack(ModItems.gun_revolver_saturnite, 1), new Object[] { "SSM", " RW", 'S', "plateSaturnite", 'W', "plankWood", 'R', ModItems.wire_tungsten, 'M', ModItems.mechanism_revolver_2 });
		addShapedOreRecipe(new ItemStack(ModItems.gun_revolver_iron, 1), new Object[] { "SSM", " RW", 'S', "plateIron", 'W', "plankWood", 'R', ModItems.wire_aluminium, 'M', ModItems.mechanism_revolver_1 });
		addShapedOreRecipe(new ItemStack(ModItems.gun_revolver_gold, 1), new Object[] { "SSM", " RW", 'S', "plateGold", 'W', "ingotGold", 'R', ModItems.wire_gold, 'M', ModItems.mechanism_revolver_1 });
		addShapedOreRecipe(new ItemStack(ModItems.gun_revolver_lead, 1), new Object[] { "SSM", " RW", 'S', "plateLead", 'W', "ingotTungsten", 'R', ModItems.wire_tungsten, 'M', ModItems.mechanism_revolver_2 });
		addShapedOreRecipe(new ItemStack(ModItems.gun_revolver_schrabidium, 1), new Object[] { "SSM", " RW", 'S', ModBlocks.block_schrabidium, 'W', "ingotTungsten", 'R', ModItems.wire_schrabidium, 'M', ModItems.mechanism_special });
		addShapedOreRecipe(new ItemStack(ModItems.gun_deagle, 1), new Object[] { "PPM", " BI", 'P', "plateSteel", 'B', ModItems.bolt_tungsten, 'I', "ingotPolymer", 'M', ModItems.mechanism_rifle_1 });
		addShapedOreRecipe(new ItemStack(ModItems.gun_revolver_cursed, 1), new Object[] { "TTM", "SRI", 'S', "plateSteel", 'I', "ingotSteel", 'R', ModItems.wire_red_copper, 'T', "plateTitanium", 'M', ModItems.mechanism_revolver_2 });
		addShapedOreRecipe(new ItemStack(ModItems.gun_revolver_nightmare, 1), new Object[] { "SEM", " RW", 'S', "plateSteel", 'W', "plankWood", 'R', ModItems.wire_aluminium, 'E', ModItems.powder_power, 'M', ModItems.mechanism_revolver_2 });
		addShapedOreRecipe(new ItemStack(ModItems.gun_revolver_nightmare2, 1), new Object[] { "SSM", "RRW", 'S', OreDictManager.getReflector(), 'W', "ingotTungsten", 'R', ModItems.wire_gold, 'M', ModItems.mechanism_special });
		addShapedOreRecipe(new ItemStack(ModItems.gun_fatman, 1), new Object[] { "SSI", "IIM", "WPH", 'S', "plateSteel", 'I', "ingotSteel", 'W', ModItems.wire_aluminium, 'H', ModItems.hull_small_steel, 'P', Item.getItemFromBlock(Blocks.PISTON), 'M', ModItems.mechanism_launcher_2 });

		addShapedOreRecipe(new ItemStack(ModItems.gun_mirv, 1), new Object[] { "LLL", "WFW", "SSS", 'S', "plateSteel", 'L', "plateLead", 'W', ModItems.wire_gold, 'F', ModItems.gun_fatman });
		addShapedOreRecipe(new ItemStack(ModItems.gun_proto, 1), new Object[] { "LLL", "WFW", "SSS", 'S', ModItems.plate_polymer, 'L', "plateDesh", 'W', ModItems.wire_tungsten, 'F', ModItems.gun_fatman });
		//addShapedOreRecipe(new ItemStack(ModItems.gun_bf, 1), new Object[] { "LLL", "WFW", "SSS", 'S', ModItems.plate_paa, 'L', OreDictManager.getReflector(), 'W', ModItems.wire_advanced_alloy, 'F', ModItems.gun_mirv });
		addShapedRecipe(new ItemStack(ModItems.gun_bf_ammo, 1), new Object[] { " S ", "EBE", " S ", 'S', ModItems.hull_small_steel, 'E', ModItems.powder_power, 'B', ModItems.egg_balefire_shard });
		addShapedOreRecipe(new ItemStack(ModItems.gun_mp40, 1), new Object[] { "IIM", " SW", " S ", 'S', "plateSteel", 'I', "ingotSteel", 'W', "plankWood", 'M', ModItems.mechanism_rifle_2 });
		addShapedOreRecipe(new ItemStack(ModItems.gun_thompson, 1), new Object[] { "IIM", " SW", " S ", 'S', "plateIron", 'I', "plateSteel", 'W', "plankWood", 'M', ModItems.mechanism_rifle_2 });
		addShapedOreRecipe(new ItemStack(ModItems.gun_flechette, 1), new Object[] { "PPM", "TIS", "G  ", 'P', "plateSteel", 'M', ModItems.mechanism_rifle_2, 'T', ModItems.hull_small_steel, 'I', "ingotSteel", 'S', ModItems.ingot_polymer, 'G', ModItems.mechanism_launcher_1 });
		addShapedOreRecipe(new ItemStack(ModItems.gun_uboinik, 1), new Object[] { "IIM", "SPW", 'P', "plateSteel", 'I', "ingotSteel", 'W', "plankWood", 'S', Items.STICK, 'M', ModItems.mechanism_revolver_2 });
		addShapedOreRecipe(new ItemStack(ModItems.gun_xvl1456, 1), new Object[] { "PBB", "ACC", "PRY", 'P', "plateSteel", 'R', ModItems.redcoil_capacitor, 'A', ModItems.coil_advanced_alloy, 'B', ModItems.battery_generic, 'C', ModItems.coil_advanced_torus, 'Y', ModItems.mechanism_special });
		addShapedOreRecipe(new ItemStack(ModItems.gun_xvl1456_ammo, 64), new Object[] { "SSS", "SRS", "SSS", 'S', "plateSteel", 'R', ModItems.rod_quad_uranium_fuel_depleted });
		addShapedOreRecipe(new ItemStack(ModItems.gun_xvl1456_ammo, 32), new Object[] { " S ", "SRS", " S ", 'S', "plateSteel", 'R', ModItems.rod_dual_uranium_fuel_depleted });
		addShapedOreRecipe(new ItemStack(ModItems.gun_xvl1456_ammo, 16), new Object[] { " S ", " R ", " S ", 'S', "plateSteel", 'R', ModItems.rod_uranium_fuel_depleted });
		addShapedOreRecipe(new ItemStack(ModItems.gun_xvl1456_ammo, 16), new Object[] { "SRS", 'S', "plateSteel", 'R', ModItems.rod_uranium_fuel_depleted });
		addShapedOreRecipe(new ItemStack(ModItems.gun_xvl1456_ammo, 16), new Object[] { " S ", " R ", " S ", 'S', "plateSteel", 'R', "U238" });
		addShapedOreRecipe(new ItemStack(ModItems.gun_xvl1456_ammo, 16), new Object[] { "SRS", 'S', "plateSteel", 'R', "U238" });
		addShapedOreRecipe(new ItemStack(ModItems.gun_osipr, 1), new Object[] { "CCT", "WWI", "MCC", 'C', ModItems.plate_combine_steel, 'T', "ingotTungsten", 'W', ModItems.wire_magnetized_tungsten, 'I', ModItems.mechanism_rifle_2, 'M', ModItems.coil_magnetized_tungsten });
		addShapedOreRecipe(new ItemStack(ModItems.gun_immolator, 1), new Object[] { "WCC", "PMT", "WAA", 'W', ModItems.wire_gold, 'C', "plateCopper", 'P', "plateAdvanced", 'M', ModItems.mechanism_launcher_1, 'T', ModItems.tank_steel, 'A', "plateSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.gun_immolator_ammo, 16), new Object[] { "SPS", "PCP", "SPS", 'S', "plateSteel", 'C', "dustCoal", 'P', ModItems.powder_fire });
		addShapedOreRecipe(new ItemStack(ModItems.gun_immolator_ammo, 16), new Object[] { " F ", "SFS", " F ", 'S', "plateSteel", 'F', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.diesel)) });
		addShapedOreRecipe(new ItemStack(ModItems.gun_immolator_ammo, 24), new Object[] { " F ", "SFS", " F ", 'S', "plateSteel", 'F', ModItems.canister_napalm });
		addShapedOreRecipe(new ItemStack(ModItems.gun_cryolator, 1), new Object[] { "SSS", "IWL", "LMI", 'S', "plateSteel", 'I', "plateIron", 'L', Items.LEATHER, 'M', ModItems.mechanism_launcher_1, 'W', ModItems.wire_aluminium });
		addShapedOreRecipe(new ItemStack(ModItems.gun_cryolator_ammo, 16), new Object[] { "SPS", "PCP", "SPS", 'S', "plateSteel", 'C', "dustSaltpeter", 'P', Items.SNOWBALL });
		addShapedOreRecipe(new ItemStack(ModItems.gun_cryolator_ammo, 16), new Object[] { " F ", "SFS", " F ", 'S', "plateSteel", 'F', ModItems.powder_ice });
		addShapedOreRecipe(new ItemStack(ModItems.gun_mp, 1), new Object[] { "EEE", "SSM", "III", 'E', ModItems.ingot_euphemium, 'S', "plateSteel", 'I', "ingotSteel", 'M', ModItems.mechanism_rifle_2 });
		addShapedOreRecipe(new ItemStack(ModItems.gun_emp, 1), new Object[] { "CPG", "CMF", "CPI", 'C', ModItems.coil_copper, 'P', "plateLead", 'G', ModItems.circuit_gold, 'M', ModItems.magnetron, 'I', "ingotTungsten", 'F', ModItems.mechanism_special });
		addShapedOreRecipe(new ItemStack(ModItems.gun_emp_ammo, 8), new Object[] { "IGI", "IPI", "IPI", 'G', "plateGold", 'I', "plateIron", 'P', ModItems.powder_power });
		addShapedOreRecipe(new ItemStack(ModItems.gun_jack, 1), new Object[] { "WW ", "TSD", " TT", 'W', "ingotWeidanium", 'T', ModItems.toothpicks, 'S', ModItems.gun_uboinik, 'D', ModItems.ducttape });
		addShapelessRecipe(new ItemStack(ModItems.gun_jack_ammo, 1), new Object[] { ModItems.ammo_12gauge, ModItems.ammo_12gauge, ModItems.ammo_12gauge, ModItems.ammo_12gauge });
		addShapedOreRecipe(new ItemStack(ModItems.gun_euthanasia, 1), new Object[] { "TDT", "AAS", " T ", 'A', "ingotAustralium", 'T', ModItems.toothpicks, 'S', ModItems.gun_mp40, 'D', ModItems.ducttape });
		addShapedRecipe(new ItemStack(ModItems.gun_euthanasia_ammo, 12), new Object[] { "P", "S", "N", 'P', ModItems.powder_poison, 'N', ModItems.niter, 'S', ModItems.syringe_metal_empty });
		addShapedOreRecipe(new ItemStack(ModItems.gun_spark, 1), new Object[] { "TTD", "AAS", "  T", 'A', "ingotDaffergon", 'T', ModItems.toothpicks, 'S', ModItems.gun_rpg, 'D', ModItems.ducttape });
		addShapedOreRecipe(new ItemStack(ModItems.gun_spark_ammo, 4), new Object[] { "PCP", "DDD", "PCP", 'P', "plateLead", 'C', ModItems.coil_gold, 'D', ModItems.powder_power });
		addShapedOreRecipe(new ItemStack(ModItems.gun_skystinger, 1), new Object[] { "TTT", "AAS", " D ", 'A', "ingotUnobtainium", 'T', ModItems.toothpicks, 'S', ModItems.gun_stinger, 'D', ModItems.ducttape });
		addShapedOreRecipe(new ItemStack(ModItems.gun_hp, 1), new Object[] { "TDT", "ASA", " T ", 'A', "ingotReiium", 'T', ModItems.toothpicks, 'S', ModItems.gun_xvl1456, 'D', ModItems.ducttape });
		addShapedOreRecipe(new ItemStack(ModItems.gun_hp_ammo, 8), new Object[] { " R ", "BSK", " Y ", 'S', "plateSteel", 'K', new ItemStack(Items.DYE, 1, 0), 'R', new ItemStack(Items.DYE, 1, 1), 'B', new ItemStack(Items.DYE, 1, 4), 'Y', new ItemStack(Items.DYE, 1, 11) });
		addShapedOreRecipe(new ItemStack(ModItems.gun_defabricator_ammo, 16), new Object[] { "PCP", "DDD", "PCP", 'P', "plateSteel", 'C', ModItems.coil_copper, 'D', "dustLithium" });
		addShapedOreRecipe(new ItemStack(ModItems.gun_lever_action, 1), new Object[] { "PPI", "SWD", 'P', "plateIron", 'I', ModItems.mechanism_rifle_1, 'S', Items.STICK, 'D', "plankWood", 'W', ModItems.wire_aluminium });
		addShapedOreRecipe(new ItemStack(ModItems.gun_lever_action_dark, 1), new Object[] { "PPI", "SWD", 'P', "plateSteel", 'I', ModItems.mechanism_rifle_1, 'S', Items.STICK, 'D', "plankWood", 'W', ModItems.wire_aluminium });
		addShapedOreRecipe(new ItemStack(ModItems.gun_bolt_action, 1), new Object[] { "PPI", "SWD", 'P', "plateSteel", 'I', ModItems.mechanism_rifle_1, 'S', Items.STICK, 'D', "plankWood", 'W', ModItems.wire_copper });
		addShapedOreRecipe(new ItemStack(ModItems.gun_bolt_action_green, 1), new Object[] { "PPI", "SWD", 'P', "plateIron", 'I', ModItems.mechanism_rifle_1, 'S', Items.STICK, 'D', "plankWood", 'W', ModItems.wire_copper });
		addShapedOreRecipe(new ItemStack(ModItems.gun_bolt_action_saturnite, 1), new Object[] { "PPI", "SWD", 'P', "plateSaturnite", 'I', ModItems.mechanism_rifle_1, 'S', Items.STICK, 'D', "plankWood", 'W', ModItems.wire_tungsten });
		addShapedRecipe(new ItemStack(ModItems.gun_b92, 1), new Object[] { "DDD", "SSC", "  R", 'D', ModItems.plate_dineutronium, 'S', ModItems.ingot_starmetal, 'C', ModItems.circuit_targeting_tier6, 'R', ModItems.gun_revolver_schrabidium });
		addShapedOreRecipe(new ItemStack(ModItems.gun_b92_ammo, 1), new Object[] { "PSP", "ESE", "PSP", 'P', "plateSteel", 'S', ModItems.ingot_starmetal, 'E', ModItems.powder_spark_mix });
		addShapelessRecipe(new ItemStack(ModItems.weaponized_starblaster_cell, 1), new Object[] { new IngredientContainsTag(ItemFluidTank.getFullTank(ModForgeFluids.acid)), new IngredientContainsTag(GunB92Cell.getFullCell()), ModItems.wire_copper });
		addShapedOreRecipe(new ItemStack(ModItems.gun_uzi, 1), new Object[] { "SMS", " PB", " P ", 'S', "ingotSteel", 'M', ModItems.mechanism_rifle_2, 'P', "plateSteel", 'B', ModItems.bolt_dura_steel });
		addShapedOreRecipe(new ItemStack(ModItems.gun_uzi_silencer, 1), new Object[] { "P  ", " P ", "  U", 'P', "ingotPolymer", 'U', ModItems.gun_uzi });
		addShapedOreRecipe(new ItemStack(ModItems.gun_uzi_saturnite, 1), new Object[] { "SMS", " PB", " P ", 'S', "ingotSaturnite", 'M', ModItems.mechanism_rifle_2, 'P', "plateSaturnite", 'B', ModItems.bolt_tungsten });
		addShapedOreRecipe(new ItemStack(ModItems.gun_uzi_saturnite_silencer, 1), new Object[] { "P  ", " P ", "  U", 'P', "ingotPolymer", 'U', ModItems.gun_uzi_saturnite });
		addShapedOreRecipe(new ItemStack(ModItems.gun_bolter, 1), new Object[] { "SSM", "PIP", " I ", 'S', ModItems.plate_saturnite, 'I', ModItems.ingot_saturnite, 'M', ModItems.mechanism_special, 'P', "ingotPolymer" });

		addShapelessRecipe(new ItemStack(ModItems.ammo_44, 1), new Object[] { ModItems.gun_revolver_nopip_ammo });
		addShapelessRecipe(new ItemStack(ModItems.ammo_44_pip, 1), new Object[] { ModItems.gun_revolver_pip_ammo });
		addShapelessRecipe(new ItemStack(ModItems.ammo_50bmg, 1), new Object[] { ModItems.gun_calamity_ammo });
		addShapelessRecipe(new ItemStack(ModItems.ammo_5mm, 1), new Object[] { ModItems.gun_lacunae_ammo });
		addShapelessRecipe(new ItemStack(ModItems.ammo_rocket, 1), new Object[] { ModItems.gun_rpg_ammo });
		addShapelessRecipe(new ItemStack(ModItems.ammo_9mm, 1), new Object[] { ModItems.gun_mp40_ammo });
		addShapelessRecipe(new ItemStack(ModItems.ammo_22lr, 1), new Object[] { ModItems.gun_uzi_ammo });
		addShapelessRecipe(new ItemStack(ModItems.ammo_12gauge, 1), new Object[] { ModItems.gun_uboinik_ammo });
		addShapelessRecipe(new ItemStack(ModItems.ammo_20gauge, 1), new Object[] { ModItems.gun_lever_action_ammo });
		addShapelessRecipe(new ItemStack(ModItems.ammo_20gauge_slug, 1), new Object[] { ModItems.gun_bolt_action_ammo });

		reg2();

	}

	public static void reg2(){

		addShapedOreRecipe(new ItemStack(ModItems.pellet_flechette, 1), new Object[] { " L ", " L ", "LLL", 'L', "nuggetLead" });
		addShapedOreRecipe(new ItemStack(ModItems.assembly_iron, 24), new Object[] { " I", "GC", " P", 'I', "ingotIron", 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357 });
		addShapedOreRecipe(new ItemStack(ModItems.assembly_iron, 24), new Object[] { " I", "GC", " P", 'I', "ingotIron", 'G', ModItems.ballistite, 'C', ModItems.casing_357, 'P', ModItems.primer_357 });
		addShapedOreRecipe(new ItemStack(ModItems.assembly_steel, 24), new Object[] { " I", "GC", " P", 'I', "ingotLead", 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357 });
		addShapedOreRecipe(new ItemStack(ModItems.assembly_steel, 24), new Object[] { " I", "GC", " P", 'I', "ingotLead", 'G', ModItems.ballistite, 'C', ModItems.casing_357, 'P', ModItems.primer_357 });
		addShapedOreRecipe(new ItemStack(ModItems.assembly_lead, 24), new Object[] { " I", "GC", " P", 'I', ModItems.ingot_u235, 'G', ModItems.cordite, 'C', "paneGlassColorless", 'P', ModItems.primer_357 });
		addShapedOreRecipe(new ItemStack(ModItems.assembly_lead, 24), new Object[] { " I", "GC", " P", 'I', ModItems.ingot_pu239, 'G', ModItems.cordite, 'C', "paneGlassColorless", 'P', ModItems.primer_357 });
		addShapedOreRecipe(new ItemStack(ModItems.assembly_lead, 24), new Object[] { " I", "GC", " P", 'I', ModItems.trinitite, 'G', ModItems.cordite, 'C', "paneGlassColorless", 'P', ModItems.primer_357 });
		addShapedOreRecipe(new ItemStack(ModItems.assembly_lead, 24), new Object[] { " I", "GC", " P", 'I', ModItems.nuclear_waste_tiny, 'G', ModItems.cordite, 'C', "paneGlassColorless", 'P', ModItems.primer_357 });
		addShapedOreRecipe(new ItemStack(ModItems.assembly_gold, 24), new Object[] { " I", "GC", " P", 'I', "ingotGold", 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357 });
		addShapedOreRecipe(new ItemStack(ModItems.assembly_schrabidium, 6), new Object[] { " I ", "GCN", " P ", 'I', "ingotSchrabidium", 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357, 'N', Items.NETHER_STAR });
		addShapedOreRecipe(new ItemStack(ModItems.assembly_nightmare, 24), new Object[] { " I", "GC", " P", 'I', "ingotTungsten", 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357 });
		addShapedOreRecipe(new ItemStack(ModItems.assembly_desh, 24), new Object[] { " I", "GC", " P", 'I', "ingotDesh", 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357 });
		addShapedOreRecipe(new ItemStack(ModItems.assembly_smg, 32), new Object[] { " I", "GC", " P", 'I', "ingotLead", 'G', ModItems.cordite, 'C', ModItems.casing_9, 'P', ModItems.primer_9 });
		addShapedOreRecipe(new ItemStack(ModItems.assembly_smg, 32), new Object[] { " I", "GC", " P", 'I', "ingotLead", 'G', ModItems.ballistite, 'C', ModItems.casing_9, 'P', ModItems.primer_9 });
		addShapedOreRecipe(new ItemStack(ModItems.assembly_556, 32), new Object[] { " I", "GC", " P", 'I', "ingotSteel", 'G', ModItems.cordite, 'C', ModItems.casing_9, 'P', ModItems.primer_9 });
		addShapedRecipe(new ItemStack(ModItems.ammo_556_k, 32), new Object[] { "G", "C", "P", 'G', Items.GUNPOWDER, 'C', ModItems.casing_9, 'P', ModItems.primer_9 });
		addShapedRecipe(new ItemStack(ModItems.ammo_556_k, 32), new Object[] { "G", "C", "P", 'G', ModItems.ballistite, 'C', ModItems.casing_9, 'P', ModItems.primer_9 });
		addShapedOreRecipe(new ItemStack(ModItems.assembly_uzi, 32), new Object[] { " I", "GC", " P", 'I', "ingotIron", 'G', ModItems.cordite, 'C', ModItems.casing_9, 'P', ModItems.primer_9 });
		addShapedOreRecipe(new ItemStack(ModItems.assembly_uzi, 32), new Object[] { " I", "GC", " P", 'I', "ingotIron", 'G', ModItems.ballistite, 'C', ModItems.casing_9, 'P', ModItems.primer_9 });
		addShapedOreRecipe(new ItemStack(ModItems.assembly_lacunae, 32), new Object[] { " I", "GC", " P", 'I', "ingotCopper", 'G', ModItems.cordite, 'C', ModItems.casing_9, 'P', ModItems.primer_9 });
		addShapedOreRecipe(new ItemStack(ModItems.assembly_nopip, 24), new Object[] { " I", "GC", " P", 'I', "ingotLead", 'G', ModItems.cordite, 'C', ModItems.casing_44, 'P', ModItems.primer_44 });
		addShapedOreRecipe(new ItemStack(ModItems.assembly_nopip, 24), new Object[] { " I", "GC", " P", 'I', "ingotLead", 'G', ModItems.ballistite, 'C', ModItems.casing_44, 'P', ModItems.primer_44 });
		addShapedRecipe(new ItemStack(ModItems.ammo_12gauge, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_buckshot, 'G', ModItems.cordite, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', ModItems.plate_polymer });
		addShapedRecipe(new ItemStack(ModItems.ammo_12gauge, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_buckshot, 'G', ModItems.ballistite, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', ModItems.plate_polymer });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_20gauge, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_buckshot, 'G', ModItems.cordite, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', "plateCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_20gauge, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_buckshot, 'G', ModItems.ballistite, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', "plateCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_20gauge_slug, 12), new Object[] { " I ", "GCL", " P ", 'I', "ingotLead", 'G', ModItems.cordite, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', "plateCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_20gauge_slug, 12), new Object[] { " I ", "GCL", " P ", 'I', "ingotLead", 'G', ModItems.ballistite, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', "plateCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_20gauge_explosive, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_cluster, 'G', ModItems.cordite, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', "plateCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_20gauge_explosive, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_cluster, 'G', ModItems.ballistite, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', "plateCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_20gauge_flechette, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_flechette, 'G', ModItems.cordite, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', "plateCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_20gauge_flechette, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_flechette, 'G', ModItems.ballistite, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', "plateCopper" });
		addShapedRecipe(new ItemStack(ModItems.gun_revolver_nightmare2_ammo, 12), new Object[] { "I", "C", "P", 'I', ModItems.powder_power, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot });
		addShapedOreRecipe(new ItemStack(ModItems.assembly_calamity, 12), new Object[] { " I ", "GCG", " P ", 'I', "ingotLead", 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50 });
		addShapedOreRecipe(new ItemStack(ModItems.assembly_actionexpress, 12), new Object[] { " I", "GC", " P", 'I', "ingotLead", 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50 });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_dart, 16), new Object[] { "IPI", "ICI", "IPI", 'I', ModItems.plate_polymer, 'P', "plateIron", 'C', new IngredientContainsTag(ItemFluidTank.getFullBarrel(ModForgeFluids.watz)) });

		addShapedRecipe(new ItemStack(ModItems.ammo_12gauge_incendiary, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_12gauge, 'A', ModItems.powder_fire });
		addShapedRecipe(new ItemStack(ModItems.ammo_12gauge_shrapnel, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_12gauge, 'A', ModBlocks.gravel_obsidian });
		addShapedRecipe(new ItemStack(ModItems.ammo_12gauge_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_12gauge, 'A', ModItems.ingot_u238 });
		addShapedRecipe(new ItemStack(ModItems.ammo_12gauge_sleek, 64), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_12gauge, 'A', ModItems.coin_maskman });
		addShapedRecipe(new ItemStack(ModItems.ammo_20gauge_incendiary, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_20gauge, 'A', ModItems.powder_fire });
		addShapedRecipe(new ItemStack(ModItems.ammo_20gauge_shrapnel, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_20gauge, 'A', ModBlocks.gravel_obsidian });
		addShapedRecipe(new ItemStack(ModItems.ammo_20gauge_caustic, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_20gauge, 'A', ModItems.powder_poison });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_20gauge_shock, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_20gauge, 'A', "dustDiamond" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_20gauge_wither, 4), new Object[] { "BCB", "CAC", "BCB", 'B', ModItems.ammo_20gauge, 'A', Blocks.SOUL_SAND, 'C', "dustCoal" });
		addShapedRecipe(new ItemStack(ModItems.ammo_20gauge_sleek, 64), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_20gauge, 'A', ModItems.coin_maskman });
		addShapedRecipe(new ItemStack(ModItems.ammo_4gauge_sleek, 64), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_4gauge, 'A', ModItems.coin_maskman });
		addShapedRecipe(new ItemStack(ModItems.ammo_4gauge_flechette_phosphorus, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_4gauge_flechette, 'A', ModItems.ingot_phosphorus });
		addShapedRecipe(new ItemStack(ModItems.ammo_4gauge_balefire, 4), new Object[] { " B ", "BAB", " B ", 'B', ModItems.ammo_4gauge_explosive, 'A', ModItems.egg_balefire_shard });
		addShapedRecipe(new ItemStack(ModItems.ammo_4gauge_kampf, 2), new Object[] { "G", "R", 'G', ModItems.ammo_rocket, 'R', ModItems.ammo_4gauge_explosive });
		addShapedRecipe(new ItemStack(ModItems.ammo_44_ap, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_44, 'A', ModItems.ingot_dura_steel });
		addShapedRecipe(new ItemStack(ModItems.ammo_44_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_44, 'A', ModItems.ingot_u238 });
		addShapedRecipe(new ItemStack(ModItems.ammo_44_star, 4), new Object[] { " B ", "BAB", " B ", 'B', ModItems.ammo_44_du, 'A', ModItems.ingot_starmetal });
		addShapedRecipe(new ItemStack(ModItems.ammo_5mm_explosive, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_5mm, 'A', ModItems.ingot_semtex });
		addShapedRecipe(new ItemStack(ModItems.ammo_5mm_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_5mm, 'A', ModItems.ingot_u238 });
		addShapedRecipe(new ItemStack(ModItems.ammo_5mm_star, 4), new Object[] { " B ", "BAB", " B ", 'B', ModItems.ammo_5mm_du, 'A', ModItems.ingot_starmetal });
		addShapedRecipe(new ItemStack(ModItems.ammo_9mm_ap, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_9mm, 'A', ModItems.ingot_dura_steel });
		addShapedRecipe(new ItemStack(ModItems.ammo_9mm_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_9mm, 'A', ModItems.ingot_u238 });
		addShapedRecipe(new ItemStack(ModItems.ammo_22lr_ap, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_22lr, 'A', ModItems.ingot_dura_steel });
		addShapedRecipe(new ItemStack(ModItems.ammo_50bmg_incendiary, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_50bmg, 'A', ModItems.powder_fire });
		addShapedRecipe(new ItemStack(ModItems.ammo_50bmg_explosive, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_50bmg, 'A', ModItems.ingot_semtex });
		addShapedRecipe(new ItemStack(ModItems.ammo_50bmg_ap, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_50bmg, 'A', ModItems.ingot_dura_steel });
		addShapedRecipe(new ItemStack(ModItems.ammo_50bmg_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_50bmg, 'A', ModItems.ingot_u238 });
		addShapedRecipe(new ItemStack(ModItems.ammo_50bmg_star, 4), new Object[] { " B ", "BAB", " B ", 'B', ModItems.ammo_50bmg_du, 'A', ModItems.ingot_starmetal });
		addShapedRecipe(new ItemStack(ModItems.ammo_50bmg_sleek, 64), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_50bmg, 'A', ModItems.coin_maskman });
		addShapedRecipe(new ItemStack(ModItems.ammo_50ae_ap, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_50ae, 'A', ModItems.ingot_dura_steel });
		addShapedRecipe(new ItemStack(ModItems.ammo_50ae_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_50ae, 'A', ModItems.ingot_u238 });
		addShapedRecipe(new ItemStack(ModItems.ammo_50ae_star, 4), new Object[] { " B ", "BAB", " B ", 'B', ModItems.ammo_50ae_du, 'A', ModItems.ingot_starmetal });
		addShapedRecipe(new ItemStack(ModItems.ammo_556_phosphorus, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_556, 'A', ModItems.ingot_phosphorus });
		addShapedRecipe(new ItemStack(ModItems.ammo_556_ap, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_556, 'A', ModItems.ingot_dura_steel });
		addShapedRecipe(new ItemStack(ModItems.ammo_556_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_556, 'A', ModItems.ingot_u238 });
		addShapedRecipe(new ItemStack(ModItems.ammo_556_star, 4), new Object[] { " B ", "BAB", " B ", 'B', ModItems.ammo_556_du, 'A', ModItems.ingot_starmetal });
		addShapedRecipe(new ItemStack(ModItems.ammo_556_sleek, 64), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_556, 'A', ModItems.coin_maskman });
		addShapedRecipe(new ItemStack(ModItems.ammo_556_tracer, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_556, 'A', Items.REDSTONE });
		addShapedRecipe(new ItemStack(ModItems.ammo_556_flechette, 4), new Object[] { " B ", "BAB", " B ", 'B', ModItems.ammo_556, 'A', ModItems.pellet_flechette });
		addShapedRecipe(new ItemStack(ModItems.ammo_556_flechette_incendiary, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_556_flechette, 'A', ModItems.powder_fire });
		addShapedRecipe(new ItemStack(ModItems.ammo_556_flechette_phosphorus, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_556_flechette, 'A', ModItems.ingot_phosphorus });
		addShapedRecipe(new ItemStack(ModItems.ammo_556_flechette_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_556_flechette, 'A', ModItems.ingot_u238 });
		addShapedRecipe(new ItemStack(ModItems.ammo_556_flechette_sleek, 64), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_556_flechette, 'A', ModItems.coin_maskman });

		addShapedRecipe(new ItemStack(ModItems.folly_bullet, 1), new Object[] { " S ", "STS", "SMS", 'S', ModItems.ingot_starmetal, 'T', ModItems.powder_magic, 'M', ModBlocks.block_meteor });
		addShapedOreRecipe(new ItemStack(ModItems.folly_bullet_nuclear, 1), new Object[] { " N ", "UTU", "UTU", 'N', ModItems.gun_fatman_ammo, 'U', "ingotIron", 'T', "blockTungsten" });
		addShapedOreRecipe(new ItemStack(ModItems.folly_bullet_du, 1), new Object[] { " U ", "UDU", "UTU", 'U', "blockUranium238", 'D', "blockDesh", 'T', "blockTungsten" });
		addShapedOreRecipe(new ItemStack(ModItems.folly_shell, 1), new Object[] { "IPI", "IPI", "IMI", 'I', "ingotIron", 'P', "plateIron", 'M', ModItems.primer_50 });
		addShapedRecipe(new ItemStack(ModItems.ammo_folly, 1), new Object[] { " B ", "MEM", " S ", 'B', ModItems.folly_bullet, 'M', ModItems.powder_magic, 'E', ModItems.powder_power, 'S', ModItems.folly_shell });
		addShapedRecipe(new ItemStack(ModItems.ammo_folly_nuclear, 1), new Object[] { " B ", "EEE", " S ", 'B', ModItems.folly_bullet_nuclear, 'E', ModBlocks.det_charge, 'S', ModItems.folly_shell });
		addShapedRecipe(new ItemStack(ModItems.ammo_folly_du, 1), new Object[] { " B ", "EEE", " S ", 'B', ModItems.folly_bullet_du, 'E', ModBlocks.det_charge, 'S', ModItems.folly_shell });

		addShapedRecipe(new ItemStack(ModItems.ammo_rocket, 1), new Object[] { " T ", "GCG", " P ", 'T', Blocks.TNT, 'G', ModItems.rocket_fuel, 'C', ModItems.casing_50, 'P', ModItems.primer_50 });
		addShapedRecipe(new ItemStack(ModItems.ammo_rocket, 2), new Object[] { " T ", "GCG", " P ", 'T', ModItems.ingot_semtex, 'G', ModItems.rocket_fuel, 'C', ModItems.casing_50, 'P', ModItems.primer_50 });
		addShapedRecipe(new ItemStack(ModItems.ammo_rocket_he, 1), new Object[] { "G", "R", 'G', ModItems.ingot_semtex, 'R', ModItems.ammo_rocket });
		addShapedRecipe(new ItemStack(ModItems.ammo_rocket_incendiary, 1), new Object[] { "G", "R", 'G', ModItems.powder_fire, 'R', ModItems.ammo_rocket });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_rocket_emp, 1), new Object[] { "G", "R", 'G', "dustDiamond", 'R', ModItems.ammo_rocket });
		addShapedRecipe(new ItemStack(ModItems.ammo_rocket_shrapnel, 1), new Object[] { "G", "R", 'G', ModItems.pellet_buckshot, 'R', ModItems.ammo_rocket });
		addShapedRecipe(new ItemStack(ModItems.ammo_rocket_glare, 1), new Object[] { "GGG", "GRG", "GGG", 'G', Items.REDSTONE, 'R', ModItems.ammo_rocket });
		addShapedRecipe(new ItemStack(ModItems.ammo_rocket_toxic, 1), new Object[] { "G", "R", 'G', ModItems.pellet_gas, 'R', ModItems.ammo_rocket });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_rocket_nuclear, 1), new Object[] { " P ", "NRN", " P ", 'P', ModItems.nugget_pu239, 'N', OreDictManager.getReflector(), 'R', ModItems.ammo_rocket });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_rocket_rpc, 2), new Object[] { "BP ", "CBH", " DR", 'B', ModItems.blades_steel, 'P', "plateSteel", 'C', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.diesel)), 'H', ModItems.hull_small_steel, 'D', ModItems.piston_selenium, 'R', ModItems.ammo_rocket });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_rocket_rpc, 2), new Object[] { "BP ", "CBH", " DR", 'B', ModItems.blades_steel, 'P', "plateSteel", 'C', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.petroil)), 'H', ModItems.hull_small_steel, 'D', ModItems.piston_selenium, 'R', ModItems.ammo_rocket });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_rocket_rpc, 2), new Object[] { "BP ", "CBH", " DR", 'B', ModItems.blades_steel, 'P', "plateSteel", 'C', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.biofuel)), 'H', ModItems.hull_small_steel, 'D', ModItems.piston_selenium, 'R', ModItems.ammo_rocket });

		addShapedOreRecipe(new ItemStack(ModItems.ammo_grenade, 2), new Object[] { " T ", "GCI", " P ", 'T', Items.GUNPOWDER, 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'I', "plateIron" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_grenade, 2), new Object[] { " T ", "GCI", " P ", 'T', Items.GUNPOWDER, 'G', ModItems.ballistite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'I', "plateIron" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_grenade_tracer, 2), new Object[] { " T ", "GCI", " P ", 'T', "dustLapis", 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'I', "plateIron" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_grenade_tracer, 2), new Object[] { " T ", "GCI", " P ", 'T', "dustLapis", 'G', ModItems.ballistite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'I', "plateIron" });
		addShapedRecipe(new ItemStack(ModItems.ammo_grenade_he, 2), new Object[] { "GIG", 'G', ModItems.ammo_grenade, 'I', ModItems.ingot_semtex });
		addShapedRecipe(new ItemStack(ModItems.ammo_grenade_incendiary, 2), new Object[] { "GIG", 'G', ModItems.ammo_grenade, 'I', ModItems.powder_fire });
		addShapedRecipe(new ItemStack(ModItems.ammo_grenade_toxic, 2), new Object[] { "GIG", 'G', ModItems.ammo_grenade, 'I', ModItems.powder_poison });
		addShapedRecipe(new ItemStack(ModItems.ammo_grenade_concussion, 2), new Object[] { "GIG", 'G', ModItems.ammo_grenade, 'I', Items.GLOWSTONE_DUST });
		addShapedRecipe(new ItemStack(ModItems.ammo_grenade_nuclear, 2), new Object[] { " P ", "GIG", " P ", 'G', ModItems.ammo_grenade, 'I', ModItems.neutron_reflector, 'P', ModItems.nugget_pu239 });
		addShapedRecipe(new ItemStack(ModItems.ammo_grenade_finned, 1), new Object[] { "G", "R", 'G', Items.FEATHER, 'R', ModItems.ammo_grenade });
		addShapedRecipe(new ItemStack(ModItems.ammo_grenade_kampf, 2), new Object[] { "G", "R", 'G', ModItems.ammo_rocket, 'R', ModItems.ammo_grenade });

		addShapedOreRecipe(new ItemStack(ModItems.stamp_357, 1), new Object[] { "RSR", "III", " C ", 'R', "dustRedstone", 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_357 });
		addShapedOreRecipe(new ItemStack(ModItems.stamp_44, 1), new Object[] { "RSR", "III", " C ", 'R', "dustRedstone", 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_44 });
		addShapedOreRecipe(new ItemStack(ModItems.stamp_9, 1), new Object[] { "RSR", "III", " C ", 'R', "dustRedstone", 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_9 });
		addShapedOreRecipe(new ItemStack(ModItems.stamp_50, 1), new Object[] { "RSR", "III", " C ", 'R', "dustRedstone", 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_50 });

		addShapedOreRecipe(new ItemStack(ModItems.casing_357, 1), new Object[] { " P ", "   ", "P P", 'P', "plateCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.casing_44, 1), new Object[] { "P", " ", "P", 'P', "plateCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.casing_9, 1), new Object[] { "P", "P", 'P', "plateCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.casing_50, 1), new Object[] { " P ", " P ", "PPP", 'P', "plateCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.casing_buckshot, 1), new Object[] { "P P", "PPP", 'P', "plateCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.casing_357, 1), new Object[] { " P ", "   ", "P P", 'P', "plateIron" });
		addShapedOreRecipe(new ItemStack(ModItems.casing_44, 1), new Object[] { "P", " ", "P", 'P', "plateIron" });
		addShapedOreRecipe(new ItemStack(ModItems.casing_9, 1), new Object[] { "P", "P", 'P', "plateIron" });
		addShapedOreRecipe(new ItemStack(ModItems.casing_50, 1), new Object[] { " P ", " P ", "PPP", 'P', "plateIron" });
		addShapedOreRecipe(new ItemStack(ModItems.casing_buckshot, 1), new Object[] { "P P", "PPP", 'P', "plateIron" });

		addShapedOreRecipe(new ItemStack(ModItems.primer_357, 1), new Object[] { "R", "P", 'P', "plateIron", 'R', "dustRedstone" });
		addShapedOreRecipe(new ItemStack(ModItems.primer_44, 1), new Object[] { "P", "R", 'P', "plateIron", 'R', "dustRedstone" });
		addShapedOreRecipe(new ItemStack(ModItems.primer_9, 1), new Object[] { "R", "P", 'P', "plateAluminum", 'R', "dustRedstone" });
		addShapedOreRecipe(new ItemStack(ModItems.primer_50, 1), new Object[] { "P", "R", 'P', "plateAluminum", 'R', "dustRedstone" });
		addShapedOreRecipe(new ItemStack(ModItems.primer_buckshot, 1), new Object[] { "R", "P", 'P', "plateCopper", 'R', "dustRedstone" });

		addShapedOreRecipe(new ItemStack(ModItems.turret_light_ammo, 1), new Object[] { " L ", "IGI", "ICI", 'L', "plateLead", 'I', "plateIron", 'C', "plateCopper", 'G', Items.GUNPOWDER });
		addShapedOreRecipe(new ItemStack(ModItems.turret_heavy_ammo, 1), new Object[] { "LGC", "LGC", "LGC", 'L', "plateLead", 'C', "plateCopper", 'G', Items.GUNPOWDER });
		addShapedOreRecipe(new ItemStack(ModItems.turret_rocket_ammo, 1), new Object[] { "TS ", "SGS", " SR", 'T', Blocks.TNT, 'S', "plateSteel", 'G', Items.GUNPOWDER, 'R', "dustRedstone" });
		addShapedOreRecipe(new ItemStack(ModItems.turret_flamer_ammo, 1), new Object[] { "FSF", "FPF", "FPF", 'F', ModItems.gun_immolator_ammo, 'S', ModItems.pipes_steel, 'P', "plateCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.turret_tau_ammo, 1), new Object[] { "AAA", "AUA", "AAA", 'A', ModItems.gun_xvl1456_ammo, 'U', "blockUranium238" });
		addShapedOreRecipe(new ItemStack(ModItems.turret_spitfire_ammo, 1), new Object[] { "CP ", "PTP", " PR", 'P', "plateSteel", 'C', ModItems.circuit_copper, 'T', Blocks.TNT, 'R', "dustRedstone" });
		addShapedOreRecipe(new ItemStack(ModItems.turret_cwis_ammo, 1), new Object[] { "LLL", "GGG", "IGI", 'L', "plateLead", 'I', "plateIron", 'G', Items.GUNPOWDER });
		addShapedOreRecipe(new ItemStack(ModItems.turret_cheapo_ammo, 1), new Object[] { "ILI", "IGI", "ICI", 'L', "plateLead", 'I', "plateSteel", 'C', "plateCopper", 'G', Items.GUNPOWDER });

		addShapedOreRecipe(new ItemStack(ModItems.grenade_generic, 4), new Object[] { "RS ", "ITI", " I ", 'I', "plateIron", 'R', ModItems.wire_red_copper, 'S', "plateSteel", 'T', Item.getItemFromBlock(Blocks.TNT) });
		addShapedRecipe(new ItemStack(ModItems.grenade_strong, 2), new Object[] { " G ", "SGS", " S ", 'G', ModItems.grenade_generic, 'S', Items.GUNPOWDER });
		addShapedRecipe(new ItemStack(ModItems.grenade_frag, 2), new Object[] { " G ", "WGW", " K ", 'G', ModItems.grenade_generic, 'W', Item.getItemFromBlock(Blocks.PLANKS), 'K', Item.getItemFromBlock(Blocks.GRAVEL) });
		addShapedRecipe(new ItemStack(ModItems.grenade_poison, 2), new Object[] { " G ", "PGP", " P ", 'G', ModItems.grenade_generic, 'P', ModItems.powder_poison });
		addShapedRecipe(new ItemStack(ModItems.grenade_gas, 2), new Object[] { " G ", "CGC", " C ", 'G', ModItems.grenade_generic, 'C', ModItems.pellet_gas });
		addShapedOreRecipe(new ItemStack(ModItems.grenade_aschrab, 1), new Object[] { "RS ", "ITI", " S ", 'I', "paneGlassColorless", 'R', ModItems.wire_red_copper, 'S', "plateSteel", 'T', new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.aschrab)) });
		addShapedRecipe(new ItemStack(ModItems.grenade_mk2, 2), new Object[] { " G ", "SGS", " S ", 'G', ModItems.grenade_strong, 'S', Items.GUNPOWDER });
		addShapelessRecipe(new ItemStack(ModItems.grenade_gascan, 1), new Object[] { new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.diesel)), Items.FLINT });
		addShapelessRecipe(new ItemStack(ModItems.grenade_gascan, 1), new Object[] { new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.biofuel)), Items.FLINT });
		addShapelessRecipe(new ItemStack(ModItems.grenade_gascan, 1), new Object[] { new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.petroil)), Items.FLINT });
		addShapelessRecipe(new ItemStack(ModItems.grenade_gascan, 1), new Object[] { new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.kerosene)), Items.FLINT });
		addShapelessOreRecipe(new ItemStack(ModItems.grenade_lemon, 1), new Object[] { ModItems.lemon, ModItems.grenade_strong });
		addShapelessRecipe(new ItemStack(ModItems.gun_moist_nugget, 12), new Object[] { Items.BREAD, Items.WHEAT, Items.COOKED_CHICKEN, Items.EGG });
		addShapedRecipe(new ItemStack(ModItems.grenade_smart, 4), new Object[] { " A ", "ACA", " A ", 'A', ModItems.grenade_strong, 'C', ModItems.circuit_aluminium });
		addShapedRecipe(new ItemStack(ModItems.grenade_mirv, 1), new Object[] { "GGG", "GCG", "GGG", 'G', ModItems.grenade_smart, 'C', ModItems.grenade_generic });
		addShapedOreRecipe(new ItemStack(ModItems.grenade_breach, 1), new Object[] { "G", "G", "P", 'G', ModItems.grenade_smart, 'P', "plateSaturnite" });
		addShapedRecipe(new ItemStack(ModItems.grenade_burst, 1), new Object[] { "GGG", "GCG", "GGG", 'G', ModItems.grenade_breach, 'C', ModItems.grenade_generic });

		addShapedOreRecipe(new ItemStack(ModItems.grenade_if_generic, 1), new Object[] { " C ", "PTP", " P ", 'C', ModItems.coil_tungsten, 'P', "plateSteel", 'T', Blocks.TNT });
		addShapedRecipe(new ItemStack(ModItems.grenade_if_he, 1), new Object[] { "A", "G", "A", 'G', ModItems.grenade_if_generic, 'A', Items.GUNPOWDER });
		addShapedRecipe(new ItemStack(ModItems.grenade_if_bouncy, 1), new Object[] { "G", "A", 'G', ModItems.grenade_if_generic, 'A', ModItems.plate_polymer });
		addShapedRecipe(new ItemStack(ModItems.grenade_if_sticky, 1), new Object[] { "G", "A", 'G', ModItems.grenade_if_generic, 'A', Items.SLIME_BALL });
		addShapedRecipe(new ItemStack(ModItems.grenade_if_impact, 1), new Object[] { "G", "A", 'G', ModItems.grenade_if_generic, 'A', Items.REDSTONE });
		addShapedRecipe(new ItemStack(ModItems.grenade_if_concussion, 1), new Object[] { "G", "A", 'G', ModItems.grenade_if_generic, 'A', Items.GLOWSTONE_DUST });
		addShapedRecipe(new ItemStack(ModItems.grenade_if_toxic, 1), new Object[] { "G", "A", 'G', ModItems.grenade_if_generic, 'A', ModItems.powder_poison });
		addShapedRecipe(new ItemStack(ModItems.grenade_if_incendiary, 1), new Object[] { "G", "A", 'G', ModItems.grenade_if_generic, 'A', ModItems.powder_fire });
		addShapedRecipe(new ItemStack(ModItems.grenade_if_brimstone, 1), new Object[] { "R", "G", "A", 'G', ModItems.grenade_if_generic, 'R', Items.REDSTONE, 'A', ModItems.sulfur });
		addShapedRecipe(new ItemStack(ModItems.grenade_if_mystery, 1), new Object[] { "A", "G", "A", 'G', ModItems.grenade_if_generic, 'A', ModItems.powder_magic });
		addShapedRecipe(new ItemStack(ModItems.grenade_if_spark, 1), new Object[] { " A ", "AGA", " A ", 'G', ModItems.grenade_if_generic, 'A', ModItems.powder_spark_mix });
		addShapedRecipe(new ItemStack(ModItems.grenade_if_hopwire, 1), new Object[] { " A ", "AGA", " A ", 'G', ModItems.grenade_if_generic, 'A', ModItems.powder_power });
		addShapedRecipe(new ItemStack(ModItems.grenade_if_null, 1), new Object[] { "BAB", "AGA", "BAB", 'G', ModItems.grenade_if_generic, 'A', Blocks.OBSIDIAN, 'B', ModItems.ingot_saturnite });

		addShapedRecipe(new ItemStack(ModItems.bomb_waffle, 1), new Object[] { "WEW", "MPM", "WEW", 'W', Items.WHEAT, 'E', Items.EGG, 'M', Items.MILK_BUCKET, 'P', ModItems.man_core });
		addShapedRecipe(new ItemStack(ModItems.schnitzel_vegan, 3), new Object[] { "RWR", "WPW", "RWR", 'W', ModItems.nuclear_waste, 'R', Items.REEDS, 'P', Items.PUMPKIN_SEEDS });
		addShapedRecipe(new ItemStack(ModItems.cotton_candy, 2), new Object[] { " S ", "SPS", " H ", 'P', ModItems.nugget_pu239, 'S', Items.SUGAR, 'H', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.apple_schrabidium, 1, 0), new Object[] { "SSS", "SAS", "SSS", 'S', ModItems.nugget_schrabidium, 'A', Items.APPLE });
		addShapedRecipe(new ItemStack(ModItems.apple_schrabidium1, 1, 0), new Object[] { "SSS", "SAS", "SSS", 'S', ModItems.ingot_schrabidium, 'A', Items.APPLE });
		addShapedRecipe(new ItemStack(ModItems.apple_schrabidium2, 1, 0), new Object[] { "SSS", "SAS", "SSS", 'S', Item.getItemFromBlock(ModBlocks.block_schrabidium), 'A', Items.APPLE });
		addShapedOreRecipe(new ItemStack(ModItems.apple_lead, 1, 0), new Object[] { "SSS", "SAS", "SSS", 'S', "nuggetLead", 'A', Items.APPLE });
		addShapedOreRecipe(new ItemStack(ModItems.apple_lead1, 1, 0), new Object[] { "SSS", "SAS", "SSS", 'S', "ingotLead", 'A', Items.APPLE });
		addShapedOreRecipe(new ItemStack(ModItems.apple_lead2, 1, 0), new Object[] { "SSS", "SAS", "SSS", 'S', "blockLead", 'A', Items.APPLE });
		addShapelessRecipe(new ItemStack(ModItems.tem_flakes, 1, 0), new Object[] { Items.GOLD_NUGGET, Items.PAPER });
		addShapelessRecipe(new ItemStack(ModItems.tem_flakes1, 1, 0), new Object[] { Items.GOLD_NUGGET, Items.GOLD_NUGGET, Items.GOLD_NUGGET, Items.PAPER });
		addShapelessRecipe(new ItemStack(ModItems.tem_flakes2, 1, 0), new Object[] { Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_NUGGET, Items.GOLD_NUGGET, Items.PAPER });
		addShapelessRecipe(new ItemStack(ModItems.glowing_stew, 1), new Object[] { Items.BOWL, Item.getItemFromBlock(ModBlocks.mush), Item.getItemFromBlock(ModBlocks.mush) });
		addShapelessRecipe(new ItemStack(ModItems.balefire_scrambled, 1), new Object[] { Items.BOWL, ModItems.egg_balefire });
		addShapelessRecipe(new ItemStack(ModItems.balefire_and_ham, 1), new Object[] { ModItems.balefire_scrambled, Items.COOKED_BEEF });
		addShapelessRecipe(new ItemStack(ModItems.med_ipecac, 1), new Object[] { Items.GLASS_BOTTLE, Items.NETHER_WART });
		addShapelessRecipe(new ItemStack(ModItems.med_ptsd, 1), new Object[] { ModItems.med_ipecac });
		addShapelessOreRecipe(new ItemStack(ModItems.pancake, 1), new Object[] { "dustRedstone", "dustDiamond", Items.WHEAT, ModItems.bolt_tungsten, ModItems.wire_copper, "plateSteel" });
		addShapelessOreRecipe(new ItemStack(ModItems.pancake, 1), new Object[] { "dustRedstone", "dustEmerald", Items.WHEAT, ModItems.bolt_tungsten, ModItems.wire_copper, "plateSteel" });

		addShapedOreRecipe(new ItemStack(ModItems.can_empty, 1), new Object[] { "P", "P", 'P', "plateAluminum" });
		addShapelessRecipe(new ItemStack(ModItems.can_smart, 1), new Object[] { ModItems.can_empty, Items.POTIONITEM, Items.SUGAR, ModItems.niter });
		addShapelessRecipe(new ItemStack(ModItems.can_creature, 1), new Object[] { ModItems.can_empty, Items.POTIONITEM, Items.SUGAR, new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.diesel)) });
		addShapelessRecipe(new ItemStack(ModItems.can_redbomb, 1), new Object[] { ModItems.can_empty, Items.POTIONITEM, Items.SUGAR, ModItems.pellet_cluster });
		addShapelessRecipe(new ItemStack(ModItems.can_mrsugar, 1), new Object[] { ModItems.can_empty, Items.POTIONITEM, Items.SUGAR, ModItems.fluorite });
		addShapelessRecipe(new ItemStack(ModItems.can_overcharge, 1), new Object[] { ModItems.can_empty, Items.POTIONITEM, Items.SUGAR, ModItems.sulfur });
		addShapelessRecipe(new ItemStack(ModItems.can_luna, 1), new Object[] { ModItems.can_empty, Items.POTIONITEM, Items.SUGAR, ModItems.powder_meteorite_tiny });

		addShapedOreRecipe(new ItemStack(ModItems.canteen_13, 1), new Object[] { "O", "P", 'O', Items.POTIONITEM, 'P', "plateSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.canteen_vodka, 1), new Object[] { "O", "P", 'O', Items.POTATO, 'P', "plateSteel" });

		addShapedOreRecipe(new ItemStack(ModItems.bottle_empty, 6), new Object[] { " G ", "G G", "GGG", 'G', "paneGlass" });
		addShapelessOreRecipe(new ItemStack(ModItems.bottle_nuka, 1), new Object[] { ModItems.bottle_empty, Items.POTIONITEM, Items.SUGAR, "dustCoal" });
		addShapelessRecipe(new ItemStack(ModItems.bottle_cherry, 1), new Object[] { ModItems.bottle_empty, Items.POTIONITEM, Items.SUGAR, Items.REDSTONE });
		addShapelessRecipe(new ItemStack(ModItems.bottle_quantum, 1), new Object[] { ModItems.bottle_empty, Items.POTIONITEM, Items.SUGAR, ModItems.trinitite });
		addShapedOreRecipe(new ItemStack(ModItems.bottle2_empty, 6), new Object[] { " G ", "G G", "G G", 'G', "paneGlass" });
		addShapelessOreRecipe(new ItemStack(ModItems.bottle2_korl, 1), new Object[] { ModItems.bottle2_empty, Items.POTIONITEM, Items.SUGAR, "dustCopper" });
		addShapelessOreRecipe(new ItemStack(ModItems.bottle2_fritz, 1), new Object[] { ModItems.bottle2_empty, Items.POTIONITEM, Items.SUGAR, "dustTungsten" });
		addShapelessOreRecipe(new ItemStack(ModItems.bottle2_korl_special, 1), new Object[] { ModItems.bottle2_empty, Items.POTIONITEM, Items.SUGAR, "dustCopper", ModItems.powder_strontium });
		addShapelessOreRecipe(new ItemStack(ModItems.bottle2_fritz_special, 1), new Object[] { ModItems.bottle2_empty, Items.POTIONITEM, Items.SUGAR, "dustTungsten", ModItems.powder_thorium });
		addShapelessOreRecipe(new ItemStack(ModItems.bottle2_sunset, 1), new Object[] { ModItems.bottle2_empty, Items.POTIONITEM, Items.SUGAR, "dustGold" });

		addShapedOreRecipe(new ItemStack(ModItems.syringe_empty, 6), new Object[] { "P", "C", "B", 'B', Item.getItemFromBlock(Blocks.IRON_BARS), 'C', new IngredientNBT2(new ItemStack(ModItems.cell)), 'P', "plateIron" });
		addShapedRecipe(new ItemStack(ModItems.syringe_antidote, 6), new Object[] { "SSS", "PMP", "SSS", 'S', ModItems.syringe_empty, 'P', Items.PUMPKIN_SEEDS, 'M', Items.MILK_BUCKET });
		addShapedRecipe(new ItemStack(ModItems.syringe_antidote, 6), new Object[] { "SPS", "SMS", "SPS", 'S', ModItems.syringe_empty, 'P', Items.PUMPKIN_SEEDS, 'M', Items.MILK_BUCKET });
		addShapedRecipe(new ItemStack(ModItems.syringe_antidote, 6), new Object[] { "SSS", "PMP", "SSS", 'S', ModItems.syringe_empty, 'P', Items.PUMPKIN_SEEDS, 'M', Items.REEDS });
		addShapedRecipe(new ItemStack(ModItems.syringe_antidote, 6), new Object[] { "SPS", "SMS", "SPS", 'S', ModItems.syringe_empty, 'P', Items.PUMPKIN_SEEDS, 'M', Items.REEDS });
		addShapedOreRecipe(new ItemStack(ModItems.syringe_poison, 1), new Object[] { "SLS", "LCL", "SLS", 'C', ModItems.syringe_empty, 'S', Items.SPIDER_EYE, 'L', "dustLead" });
		addShapedRecipe(new ItemStack(ModItems.syringe_poison, 1), new Object[] { "SLS", "LCL", "SLS", 'C', ModItems.syringe_empty, 'S', Items.SPIDER_EYE, 'L', ModItems.powder_poison });
		addShapedOreRecipe(new ItemStack(ModItems.syringe_awesome, 1), new Object[] { "SPS", "NCN", "SPS", 'C', ModItems.syringe_empty, 'S', "sulfur", 'P', ModItems.nugget_pu239, 'N', ModItems.nugget_pu238 });
		addShapedOreRecipe(new ItemStack(ModItems.syringe_awesome, 1), new Object[] { "SNS", "PCP", "SNS", 'C', ModItems.syringe_empty, 'S', "sulfur", 'P', ModItems.nugget_pu239, 'N', ModItems.nugget_pu238 });
		addShapedOreRecipe(new ItemStack(ModItems.syringe_awesome, 1), new Object[] { "SPS", "NCN", "SPS", 'C', ModItems.syringe_empty, 'S', "dustSulfur", 'P', ModItems.nugget_pu239, 'N', ModItems.nugget_pu238 });
		addShapedOreRecipe(new ItemStack(ModItems.syringe_awesome, 1), new Object[] { "SNS", "PCP", "SNS", 'C', ModItems.syringe_empty, 'S', "dustSulfur", 'P', ModItems.nugget_pu239, 'N', ModItems.nugget_pu238 });
		addShapedOreRecipe(new ItemStack(ModItems.syringe_metal_empty, 6), new Object[] { "P", "C", "B", 'B', Item.getItemFromBlock(Blocks.IRON_BARS), 'C', ModItems.rod_empty, 'P', "plateIron" });
		addShapedRecipe(new ItemStack(ModItems.syringe_metal_stimpak, 1), new Object[] { " N ", "NSN", " N ", 'N', Items.NETHER_WART, 'S', ModItems.syringe_metal_empty });
		addShapedRecipe(new ItemStack(ModItems.syringe_metal_medx, 1), new Object[] { " N ", "NSN", " N ", 'N', Items.QUARTZ, 'S', ModItems.syringe_metal_empty });
		addShapedRecipe(new ItemStack(ModItems.syringe_metal_psycho, 1), new Object[] { " N ", "NSN", " N ", 'N', Items.GLOWSTONE_DUST, 'S', ModItems.syringe_metal_empty });
		addShapedRecipe(new ItemStack(ModItems.pill_iodine, 8), new Object[] { "IF", 'I', ModItems.powder_iodine, 'F', ModItems.fluorite });
		addShapedRecipe(new ItemStack(ModItems.plan_c, 1), new Object[] { "PFP", 'P', ModItems.powder_poison, 'F', ModItems.fluorite });
		addShapelessOreRecipe(new ItemStack(ModItems.radx, 1), new Object[] { "dustCoal", "dustFluorite", "dustFluorite" });
		addShapedRecipe(new ItemStack(ModItems.med_bag, 1), new Object[] { "LLL", "SIS", "LLL", 'L', Items.LEATHER, 'S', ModItems.syringe_metal_stimpak, 'I', ModItems.syringe_antidote });
		addShapedRecipe(new ItemStack(ModItems.med_bag, 1), new Object[] { "LLL", "SIS", "LLL", 'L', Items.LEATHER, 'S', ModItems.syringe_metal_stimpak, 'I', ModItems.pill_iodine });
		addShapedRecipe(new ItemStack(ModItems.med_bag, 1), new Object[] { "LL", "SI", "LL", 'L', Items.LEATHER, 'S', ModItems.syringe_metal_super, 'I', ModItems.radaway });
		addShapedRecipe(new ItemStack(ModItems.med_bag, 1), new Object[] { "LLL", "SIS", "LLL", 'L', ModItems.plate_polymer, 'S', ModItems.syringe_metal_stimpak, 'I', ModItems.syringe_antidote });
		addShapedRecipe(new ItemStack(ModItems.med_bag, 1), new Object[] { "LLL", "SIS", "LLL", 'L', ModItems.plate_polymer, 'S', ModItems.syringe_metal_stimpak, 'I', ModItems.pill_iodine });
		addShapedRecipe(new ItemStack(ModItems.med_bag, 1), new Object[] { "LL", "SI", "LL", 'L', ModItems.plate_polymer, 'S', ModItems.syringe_metal_super, 'I', ModItems.radaway });
		addShapedOreRecipe(new ItemStack(ModItems.syringe_metal_super, 1), new Object[] { " N ", "PSP", "L L", 'N', ModItems.bottle_nuka, 'P', "plateSteel", 'S', ModItems.syringe_metal_stimpak, 'L', Items.LEATHER });
		addShapedOreRecipe(new ItemStack(ModItems.syringe_metal_super, 1), new Object[] { " N ", "PSP", "L L", 'N', ModItems.bottle_nuka, 'P', "plateSteel", 'S', ModItems.syringe_metal_stimpak, 'L', ModItems.plate_polymer });
		addShapedOreRecipe(new ItemStack(ModItems.syringe_metal_super, 1), new Object[] { " N ", "PSP", "L L", 'N', ModItems.bottle_cherry, 'P', "plateSteel", 'S', ModItems.syringe_metal_stimpak, 'L', Items.LEATHER });
		addShapedOreRecipe(new ItemStack(ModItems.syringe_metal_super, 1), new Object[] { " N ", "PSP", "L L", 'N', ModItems.bottle_cherry, 'P', "plateSteel", 'S', ModItems.syringe_metal_stimpak, 'L', ModItems.plate_polymer });
		addShapelessRecipe(new ItemStack(ModItems.radaway, 1), new Object[] { ModItems.powder_coal, Items.PUMPKIN_SEEDS, Items.GLASS_BOTTLE});
		addShapelessRecipe(new ItemStack(ModItems.radaway_strong, 1), new Object[] { ModBlocks.mush, ModItems.radaway });
		addShapelessRecipe(new ItemStack(ModItems.radaway_flush, 1), new Object[] { ModItems.powder_iodine, ModItems.radaway_strong });

		addShapedOreRecipe(new ItemStack(ModItems.stealth_boy, 1), new Object[] { " B", "LI", "LC", 'B', Item.getItemFromBlock(Blocks.STONE_BUTTON), 'L', Items.LEATHER, 'I', "ingotSteel", 'C', ModItems.circuit_red_copper });

		addShapedOreRecipe(new ItemStack(ModBlocks.sat_dock, 1), new Object[] { "SSS", "PCP", 'S', "ingotSteel", 'P', "ingotPolymer", 'C', ModBlocks.crate_iron });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.book_guide), 1), new Object[] { "IBI", "LBL", "IBI", 'B', Items.BOOK, 'I', new ItemStack(Items.DYE, 1, 0), 'L', new ItemStack(Items.DYE, 1, 4) });

		addShapedOreRecipe(new ItemStack(ModBlocks.rail_highspeed, 16), new Object[] { "S S", "SIS", "S S", 'S', "ingotSteel", 'I', "plateIron" });
		addShapedOreRecipe(new ItemStack(ModBlocks.rail_booster, 6), new Object[] { "S S", "CIC", "SRS", 'S', "ingotSteel", 'I', "plateIron", 'R', "ingotMingrade", 'C', ModItems.coil_copper });

		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.bomb_multi), 1), new Object[] { "AAD", "CHF", "AAD", 'A', ModItems.wire_aluminium, 'C', ModItems.circuit_aluminium, 'H', ModItems.hull_small_aluminium, 'F', ModItems.fins_quad_titanium, 'D', new ItemStack(Items.DYE, 1, 15) });
		addShapelessOreRecipe(new ItemStack(ModItems.powder_ice, 4), new Object[] { Items.SNOWBALL, "dustNiter", "dustRedstone" });
		addShapelessOreRecipe(new ItemStack(ModItems.powder_poison, 4), new Object[] { Items.SPIDER_EYE, "dustRedstone", "gemQuartz" });
		addShapelessOreRecipe(new ItemStack(ModItems.pellet_gas, 2), new Object[] { Items.WATER_BUCKET, "dustGlowstone", "plateSteel" });

		addShapedRecipe(new ItemStack(ModItems.flame_pony, 1), new Object[] { " O ", "DPD", " O ", 'D', new ItemStack(Items.DYE, 1, 11), 'O', new ItemStack(Items.DYE, 1, 9), 'P', Items.PAPER });
		addShapedRecipe(new ItemStack(ModItems.flame_conspiracy, 1), new Object[] { " S ", "STS", " S ", 'S', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.diesel)), 'T', ModItems.ingot_steel });
		addShapedRecipe(new ItemStack(ModItems.flame_politics, 1), new Object[] { " I ", "IPI", " I ", 'P', Items.PAPER, 'I', new ItemStack(Items.DYE, 1, 0) });
		addShapedRecipe(new ItemStack(ModItems.flame_opinion, 1), new Object[] { " R ", "RPR", " R ", 'P', Items.PAPER, 'R', new ItemStack(Items.DYE, 1, 1) });

		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.flame_war), 1), new Object[] { "WHW", "CTP", "WOW", 'W', Item.getItemFromBlock(Blocks.PLANKS), 'T', Item.getItemFromBlock(Blocks.TNT), 'H', ModItems.flame_pony, 'C', ModItems.flame_conspiracy, 'P', ModItems.flame_politics, 'O', ModItems.flame_opinion });
		addShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.emp_bomb), 1), new Object[] { "LML", "LCL", "LML", 'L', "plateLead", 'M', ModItems.magnetron, 'C', ModItems.circuit_gold });

		addShapedOreRecipe(new ItemStack(ModItems.gadget_explosive8, 1), new Object[] { "EEE", "EPE", "EEE", 'E', ModItems.gadget_explosive, 'P', "plateAluminum" });

		addShapedOreRecipe(new ItemStack(ModItems.man_explosive8, 1), new Object[] { "EEE", "ESE", "EEE", 'E', ModItems.man_explosive, 'S', "plateSteel" });

		addShapedOreRecipe(new ItemStack(ModItems.n2_charge, 1), new Object[] { " D ", "ERE", " D ", 'D', ModItems.ducttape, 'E', ModBlocks.det_charge, 'R', "blockRedstone" });

		addShapedOreRecipe(new ItemStack(ModItems.custom_tnt, 1), new Object[] { " C ", "TIT", "TIT", 'C', "plateCopper", 'I', "plateIron", 'T', Blocks.TNT });
		addShapedOreRecipe(new ItemStack(ModItems.custom_nuke, 1), new Object[] { " C ", "LUL", "LUL", 'C', "plateCopper", 'L', "plateLead", 'U', "ingotUranium235" });
		addShapedOreRecipe(new ItemStack(ModItems.custom_hydro, 1), new Object[] { " C ", "LTL", "LIL", 'C', "plateCopper", 'L', "plateLead", 'I', "plateIron", 'T', new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.tritium)) });
		addShapedOreRecipe(new ItemStack(ModItems.custom_amat, 1), new Object[] { " C ", "MMM", "AAA", 'C', "plateCopper", 'A', "plateAluminum", 'M', new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.amat)) });
		addShapedOreRecipe(new ItemStack(ModItems.custom_dirty, 1), new Object[] { " C ", "WLW", "WLW", 'C', "plateCopper", 'L', "plateLead", 'W', ModItems.nuclear_waste });
		addShapedOreRecipe(new ItemStack(ModItems.custom_schrab, 1), new Object[] { " C ", "LUL", "LUL", 'C', "plateCopper", 'L', "plateLead", 'U', "ingotSchrabidium" });

		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_generic), new Object[] { " A ", "PRP", "PRP", 'A', ModItems.wire_aluminium, 'P', "plateAluminum", 'R', "dustRedstone" });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PSP", "PLP", 'A', ModItems.wire_red_copper, 'P', "plateCopper", 'S', "sulfur", 'L', "dustLead" });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PLP", "PSP", 'A', ModItems.wire_red_copper, 'P', "plateCopper", 'S', "sulfur", 'L', "dustLead" });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PSP", "PLP", 'A', ModItems.wire_red_copper, 'P', "plateCopper", 'S', "dustSulfur", 'L', "dustLead" });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PLP", "PSP", 'A', ModItems.wire_red_copper, 'P', "plateCopper", 'S', "dustSulfur", 'L', "dustLead" });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_lithium), new Object[] { "A A", "PSP", "PLP", 'A', ModItems.wire_gold, 'P', "plateTitanium", 'S', "dustLithium", 'L', ModItems.powder_cobalt });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_lithium), new Object[] { "A A", "PLP", "PSP", 'A', ModItems.wire_gold, 'P', "plateTitanium", 'S', "dustLithium", 'L', ModItems.powder_cobalt });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium), new Object[] { " A ", "PNP", "PSP", 'A', ModItems.wire_schrabidium, 'P', "plateSchrabidium", 'S', "dustSchrabidium", 'N', ModItems.powder_neptunium });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium), new Object[] { " A ", "PSP", "PNP", 'A', ModItems.wire_schrabidium, 'P', "plateSchrabidium", 'S', "dustSchrabidium", 'N', ModItems.powder_neptunium });
		addShapedRecipe(ItemBattery.getEmptyBattery(ModItems.battery_spark), new Object[] { " A ", "PSP", "PSP", 'A', ModItems.wire_magnetized_tungsten, 'P', ModItems.plate_dineutronium, 'S', ModItems.powder_spark_mix });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_trixite), new Object[] { " A ", "PSP", "PTP", 'A', ModItems.wire_aluminium, 'P', "plateAluminum", 'S', ModItems.powder_power, 'T', ModItems.crystal_trixite });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_trixite), new Object[] { " A ", "PTP", "PSP", 'A', ModItems.wire_aluminium, 'P', "plateAluminum", 'S', ModItems.powder_power, 'T', ModItems.crystal_trixite });
		addShapedOreRecipe(ItemBattery.getFullBattery(ModItems.energy_core), new Object[] { "PCW", "TRD", "PCW", 'P', ModItems.plate_advanced_alloy, 'C', ModItems.coil_advanced_alloy, 'W', ModItems.wire_advanced_alloy, 'R', new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.tritium)), 'D', new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.deuterium)), 'T', "ingotTungsten" });
		addShapedOreRecipe(ItemBattery.getFullBattery(ModItems.energy_core), new Object[] { "PCW", "TDR", "PCW", 'P', ModItems.plate_advanced_alloy, 'C', ModItems.coil_advanced_alloy, 'W', ModItems.wire_advanced_alloy, 'R', new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.tritium)), 'D', new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.deuterium)), 'T', "ingotTungsten" });

		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_red_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_aluminium, 'P', "plateAluminum", 'B', ItemBattery.getEmptyBattery(ModItems.battery_generic) });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_red_copper, 'P', "plateCopper", 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced) });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_gold, 'P', "plateTitanium", 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium) });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_schrabidium, 'P', "plateSchrabidium", 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium) });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_red_cell_6), new Object[] { "BBB", "WPW", "BBB", 'W', ModItems.wire_aluminium, 'P', "plateAluminum", 'B', ItemBattery.getEmptyBattery(ModItems.battery_red_cell) });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_4), new Object[] { "BWB", "WPW", "BWB", 'W', ModItems.wire_red_copper, 'P', "plateCopper", 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell) });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_3), new Object[] { "WPW", "BBB", "WPW", 'W', ModItems.wire_gold, 'P', "plateTitanium", 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell) });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_2), new Object[] { "WPW", "BWB", "WPW", 'W', ModItems.wire_schrabidium, 'P', "plateSchrabidium", 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell) });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_red_cell_24), new Object[] { "BWB", "WPW", "BWB", 'W', ModItems.wire_aluminium, 'P', "plateAluminum", 'B', ItemBattery.getEmptyBattery(ModItems.battery_red_cell_6) });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_12), new Object[] { "WPW", "BBB", "WPW", 'W', ModItems.wire_red_copper, 'P', "plateCopper", 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_4) });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_6), new Object[] { "WPW", "BWB", "WPW", 'W', ModItems.wire_gold, 'P', "plateTitanium", 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_3) });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_4), new Object[] { "WPW", "BWB", "WPW", 'W', ModItems.wire_schrabidium, 'P', "plateSchrabidium", 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_2) });
		addShapedRecipe(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_6), new Object[] { "BBW", "BBP", "BBW", 'W', ModItems.wire_magnetized_tungsten, 'P', ModItems.plate_dineutronium, 'B', ItemBattery.getEmptyBattery(ModItems.battery_spark) });
		addShapedRecipe(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_25), new Object[] { " WW", "PCC", "BCC", 'W', ModItems.wire_magnetized_tungsten, 'P', ModItems.plate_dineutronium, 'B', ItemBattery.getEmptyBattery(ModItems.battery_spark), 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_6) });
		addShapedRecipe(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_100), new Object[] { "W W", "BPB", "BPB", 'W', ModItems.wire_magnetized_tungsten, 'P', ModItems.plate_dineutronium, 'B', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_25) });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_1000), new Object[] { "CCC", "CSC", "CCC", 'S', ModItems.singularity_spark, 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_100) });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_2500), new Object[] { "CVC", "PSP", "CVC", 'S', ModItems.singularity_spark, 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_100), 'V', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_1000), 'P', ModItems.plate_dineutronium });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_10000), new Object[] { "PVP", "VSV", "PVP", 'S', ModItems.singularity_spark, 'V', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_2500), 'P', ModItems.plate_dineutronium });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_power), new Object[] { "CCC", "CSC", "CCC", 'S', ModItems.singularity_spark, 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_10000) });

		addShapedOreRecipe(ItemBattery.getFullBattery(ModItems.battery_su), new Object[] { "P", "R", "C", 'P', Items.PAPER, 'R', "dustRedstone", 'C', "dustCoal" });
		addShapedOreRecipe(ItemBattery.getFullBattery(ModItems.battery_su), new Object[] { "P", "C", "R", 'P', Items.PAPER, 'R', "dustRedstone", 'C', "dustCoal" });
		addShapedOreRecipe(ItemBattery.getFullBattery(ModItems.battery_su_l), new Object[] { " W ", "CPC", "RPR", 'W', ModItems.wire_aluminium, 'P', Items.PAPER, 'R', "dustRedstone", 'C', "dustCoal" });
		addShapedOreRecipe(ItemBattery.getFullBattery(ModItems.battery_su_l), new Object[] { " W ", "RPR", "CPC", 'W', ModItems.wire_aluminium, 'P', Items.PAPER, 'R', "dustRedstone", 'C', "dustCoal" });
		addShapedOreRecipe(ItemBattery.getFullBattery(ModItems.battery_su_l), new Object[] { " W ", "CPC", "RPR", 'W', ModItems.wire_copper, 'P', Items.PAPER, 'R', "dustRedstone", 'C', "dustCoal" });
		addShapedOreRecipe(ItemBattery.getFullBattery(ModItems.battery_su_l), new Object[] { " W ", "RPR", "CPC", 'W', ModItems.wire_copper, 'P', Items.PAPER, 'R', "dustRedstone", 'C', "dustCoal" });
		addShapelessRecipe(ItemBattery.getFullBattery(ModItems.battery_potato), new Object[] { Items.POTATO, ModItems.wire_aluminium, ModItems.wire_copper });
		addShapelessOreRecipe(ItemBattery.getFullBattery(ModItems.battery_potatos), new Object[] { ItemBattery.getFullBattery(ModItems.battery_potato), ModItems.turret_chip, "dustRedstone" });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_steam), new Object[] { "PMP", "ISI", "PCP", 'P', "plateCopper", 'M', ModItems.motor, 'C', ModItems.coil_tungsten, 'S', new IngredientContainsTag(ItemFluidTank.getFullTank(FluidRegistry.WATER)), 'I', ModItems.plate_polymer });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_steam_large), new Object[] { "MPM", "ISI", "CPC", 'P', ModItems.board_copper, 'M', ModItems.motor, 'C', ModItems.coil_tungsten, 'S', new IngredientContainsTag(ItemFluidTank.getFullBarrel(FluidRegistry.WATER)), 'I', "ingotPolymer" });

		if(GeneralConfig.enableBabyMode) {
			addShapedRecipe(new ItemStack(ModItems.starmetal_helmet, 1), new Object[] { "EEE", "E E", 'E', ModItems.ingot_starmetal });
			addShapedRecipe(new ItemStack(ModItems.starmetal_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.ingot_starmetal });
			addShapedRecipe(new ItemStack(ModItems.starmetal_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.ingot_starmetal });
			addShapedRecipe(new ItemStack(ModItems.starmetal_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.ingot_starmetal });
			addShapedRecipe(new ItemStack(ModItems.schrabidium_helmet, 1), new Object[] { "EEE", "E E", 'E', ModItems.ingot_schrabidium });
			addShapedRecipe(new ItemStack(ModItems.schrabidium_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.ingot_schrabidium });
			addShapedRecipe(new ItemStack(ModItems.schrabidium_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.ingot_schrabidium });
			addShapedRecipe(new ItemStack(ModItems.schrabidium_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.ingot_schrabidium });
			addShapedRecipe(new ItemStack(ModItems.schrabidium_sword, 1), new Object[] { "I", "I", "S", 'I', ModItems.ingot_schrabidium, 'S', Items.STICK });
			addShapedRecipe(new ItemStack(ModItems.schrabidium_pickaxe, 1), new Object[] { "III", " S ", " S ", 'I', ModItems.ingot_schrabidium, 'S', Items.STICK });
			addShapedRecipe(new ItemStack(ModItems.schrabidium_axe, 1), new Object[] { "II", "IS", " S", 'I', ModItems.ingot_schrabidium, 'S', Items.STICK });
			addShapedRecipe(new ItemStack(ModItems.schrabidium_shovel, 1), new Object[] { "I", "S", "S", 'I', ModItems.ingot_schrabidium, 'S', Items.STICK });
			addShapedRecipe(new ItemStack(ModItems.schrabidium_hoe, 1), new Object[] { "II", " S", " S", 'I', ModItems.ingot_schrabidium, 'S', Items.STICK });
		} else {
			addShapedRecipe(new ItemStack(ModItems.starmetal_helmet, 1), new Object[] { "EEE", "ECE", 'E', ModItems.ingot_starmetal, 'C', ModItems.cobalt_helmet });
			addShapedRecipe(new ItemStack(ModItems.starmetal_plate, 1), new Object[] { "ECE", "EEE", "EEE", 'E', ModItems.ingot_starmetal, 'C', ModItems.cobalt_plate });
			addShapedRecipe(new ItemStack(ModItems.starmetal_legs, 1), new Object[] { "EEE", "ECE", "E E", 'E', ModItems.ingot_starmetal, 'C', ModItems.cobalt_legs });
			addShapedRecipe(new ItemStack(ModItems.starmetal_boots, 1), new Object[] { "E E", "ECE", 'E', ModItems.ingot_starmetal, 'C', ModItems.cobalt_boots });
			addShapedRecipe(new ItemStack(ModItems.schrabidium_helmet, 1), new Object[] { "EEE", "ESE", " P ", 'E', ModItems.ingot_schrabidium, 'S', ModItems.starmetal_helmet, 'P', ModItems.pellet_charged });
			addShapedRecipe(new ItemStack(ModItems.schrabidium_plate, 1), new Object[] { "ESE", "EPE", "EEE", 'E', ModItems.ingot_schrabidium, 'S', ModItems.starmetal_plate, 'P', ModItems.pellet_charged });
			addShapedRecipe(new ItemStack(ModItems.schrabidium_legs, 1), new Object[] { "EEE", "ESE", "EPE", 'E', ModItems.ingot_schrabidium, 'S', ModItems.starmetal_legs, 'P', ModItems.pellet_charged });
			addShapedRecipe(new ItemStack(ModItems.schrabidium_boots, 1), new Object[] { "EPE", "ESE", 'E', ModItems.ingot_schrabidium, 'S', ModItems.starmetal_boots, 'P', ModItems.pellet_charged });
			addShapedRecipe(new ItemStack(ModItems.schrabidium_sword, 1), new Object[] { "I", "W", "S", 'I', ModBlocks.block_schrabidium, 'W', ModItems.desh_sword, 'S', ModItems.ingot_polymer });
			addShapedRecipe(new ItemStack(ModItems.schrabidium_pickaxe, 1), new Object[] { "SWS", " P ", " P ", 'S', ModItems.blades_schrabidium, 'W', ModItems.desh_pickaxe, 'P', ModItems.ingot_polymer });
			addShapedRecipe(new ItemStack(ModItems.schrabidium_axe, 1), new Object[] { "SW", "SP", " P", 'S', ModItems.blades_schrabidium, 'W', ModItems.desh_axe, 'P', ModItems.ingot_polymer });
			addShapedRecipe(new ItemStack(ModItems.schrabidium_shovel, 1), new Object[] { "S", "W", "P", 'S', ModItems.blades_schrabidium, 'W', ModItems.desh_shovel, 'P', ModItems.ingot_polymer });
			addShapedRecipe(new ItemStack(ModItems.schrabidium_hoe, 1), new Object[] { "IW", " S", " S", 'I', ModItems.ingot_schrabidium, 'W', ModItems.desh_hoe, 'S', ModItems.ingot_polymer });
		}

		addShapedOreRecipe(new ItemStack(ModItems.steel_helmet, 1), new Object[] { "EEE", "E E", 'E', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.steel_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.steel_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.steel_boots, 1), new Object[] { "E E", "E E", 'E', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.steel_sword, 1), new Object[] { "I", "I", "S", 'I', "ingotSteel", 'S', Items.STICK });
		addShapedOreRecipe(new ItemStack(ModItems.steel_pickaxe, 1), new Object[] { "III", " S ", " S ", 'I', "ingotSteel", 'S', Items.STICK });
		addShapedOreRecipe(new ItemStack(ModItems.steel_axe, 1), new Object[] { "II", "IS", " S", 'I', "ingotSteel", 'S', Items.STICK });
		addShapedOreRecipe(new ItemStack(ModItems.steel_shovel, 1), new Object[] { "I", "S", "S", 'I', "ingotSteel", 'S', Items.STICK });
		addShapedOreRecipe(new ItemStack(ModItems.steel_hoe, 1), new Object[] { "II", " S", " S", 'I', "ingotSteel", 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.titanium_helmet, 1), new Object[] { "EEE", "E E", 'E', ModItems.ingot_titanium });
		addShapedRecipe(new ItemStack(ModItems.titanium_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.ingot_titanium });
		addShapedRecipe(new ItemStack(ModItems.titanium_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.ingot_titanium });
		addShapedRecipe(new ItemStack(ModItems.titanium_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.ingot_titanium });
		addShapedRecipe(new ItemStack(ModItems.titanium_sword, 1), new Object[] { "I", "I", "S", 'I', ModItems.ingot_titanium, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.titanium_pickaxe, 1), new Object[] { "III", " S ", " S ", 'I', ModItems.ingot_titanium, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.titanium_axe, 1), new Object[] { "II", "IS", " S", 'I', ModItems.ingot_titanium, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.titanium_shovel, 1), new Object[] { "I", "S", "S", 'I', ModItems.ingot_titanium, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.titanium_hoe, 1), new Object[] { "II", " S", " S", 'I', ModItems.ingot_titanium, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.cobalt_sword, 1), new Object[] { "I", "I", "S", 'I', ModItems.ingot_cobalt, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.cobalt_pickaxe, 1), new Object[] { "III", " S ", " S ", 'I', ModItems.ingot_cobalt, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.cobalt_axe, 1), new Object[] { "II", "IS", " S", 'I', ModItems.ingot_cobalt, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.cobalt_shovel, 1), new Object[] { "I", "S", "S", 'I', ModItems.ingot_cobalt, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.cobalt_hoe, 1), new Object[] { "II", " S", " S", 'I', ModItems.ingot_cobalt, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.alloy_helmet, 1), new Object[] { "EEE", "E E", 'E', ModItems.ingot_advanced_alloy });
		addShapedRecipe(new ItemStack(ModItems.alloy_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.ingot_advanced_alloy });
		addShapedRecipe(new ItemStack(ModItems.alloy_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.ingot_advanced_alloy });
		addShapedRecipe(new ItemStack(ModItems.alloy_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.ingot_advanced_alloy });
		addShapedRecipe(new ItemStack(ModItems.alloy_sword, 1), new Object[] { "I", "I", "S", 'I', ModItems.ingot_advanced_alloy, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.alloy_pickaxe, 1), new Object[] { "III", " S ", " S ", 'I', ModItems.ingot_advanced_alloy, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.alloy_axe, 1), new Object[] { "II", "IS", " S", 'I', ModItems.ingot_advanced_alloy, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.alloy_shovel, 1), new Object[] { "I", "S", "S", 'I', ModItems.ingot_advanced_alloy, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.alloy_hoe, 1), new Object[] { "II", " S", " S", 'I', ModItems.ingot_advanced_alloy, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.cmb_helmet, 1), new Object[] { "EEE", "E E", 'E', ModItems.ingot_combine_steel });
		addShapedRecipe(new ItemStack(ModItems.cmb_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.ingot_combine_steel });
		addShapedRecipe(new ItemStack(ModItems.cmb_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.ingot_combine_steel });
		addShapedRecipe(new ItemStack(ModItems.cmb_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.ingot_combine_steel });
		addShapedRecipe(new ItemStack(ModItems.cmb_sword, 1), new Object[] { "I", "I", "S", 'I', ModItems.ingot_combine_steel, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.cmb_pickaxe, 1), new Object[] { "III", " S ", " S ", 'I', ModItems.ingot_combine_steel, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.cmb_axe, 1), new Object[] { "II", "IS", " S", 'I', ModItems.ingot_combine_steel, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.cmb_shovel, 1), new Object[] { "I", "S", "S", 'I', ModItems.ingot_combine_steel, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.cmb_hoe, 1), new Object[] { "II", " S", " S", 'I', ModItems.ingot_combine_steel, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.elec_sword, 1), new Object[] { "RPR", "RPR", " B ", 'P', ModItems.ingot_polymer, 'R', ModItems.bolt_dura_steel, 'B', ModItems.battery_lithium });
		addShapedRecipe(new ItemStack(ModItems.elec_pickaxe, 1), new Object[] { "RDM", " PB", " P ", 'P', ModItems.ingot_polymer, 'D', ModItems.ingot_dura_steel, 'R', ModItems.bolt_dura_steel, 'M', ModItems.motor, 'B', ModItems.battery_lithium });
		addShapedRecipe(new ItemStack(ModItems.elec_axe, 1), new Object[] { " DP", "RRM", " PB", 'P', ModItems.ingot_polymer, 'D', ModItems.ingot_dura_steel, 'R', ModItems.bolt_dura_steel, 'M', ModItems.motor, 'B', ModItems.battery_lithium });
		addShapedRecipe(new ItemStack(ModItems.elec_shovel, 1), new Object[] { "  P", "RRM", "  B", 'P', ModItems.ingot_polymer, 'R', ModItems.bolt_dura_steel, 'M', ModItems.motor, 'B', ModItems.battery_lithium });
		addShapelessRecipe(new ItemStack(ModItems.centri_stick, 1), new Object[] { ModItems.centrifuge_element, ModItems.energy_core, Items.STICK });
		addShapedOreRecipe(new ItemStack(ModItems.smashing_hammer, 1), new Object[] { "STS", "SPS", " P ", 'S', "blockSteel", 'T', "blockTungsten", 'P', "ingotPolymer" });
		addShapedRecipe(new ItemStack(ModItems.desh_sword, 1), new Object[] { "I", "I", "S", 'I', ModItems.ingot_desh, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.desh_pickaxe, 1), new Object[] { "III", " S ", " S ", 'I', ModItems.ingot_desh, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.desh_axe, 1), new Object[] { "II", "IS", " S", 'I', ModItems.ingot_desh, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.desh_shovel, 1), new Object[] { "I", "S", "S", 'I', ModItems.ingot_desh, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.desh_hoe, 1), new Object[] { "II", " S", " S", 'I', ModItems.ingot_desh, 'S', Items.STICK });
		addShapedOreRecipe(new ItemStack(ModItems.security_helmet, 1), new Object[] { "SSS", "IGI", 'S', "plateSteel", 'I', ModItems.plate_polymer, 'G', "paneGlass" });
		addShapedRecipe(new ItemStack(ModItems.security_plate, 1), new Object[] { "KWK", "IKI", "WKW", 'K', ModItems.plate_kevlar, 'I', ModItems.ingot_polymer, 'W', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE) });
		addShapedRecipe(new ItemStack(ModItems.security_legs, 1), new Object[] { "IWI", "K K", "W W", 'K', ModItems.plate_kevlar, 'I', ModItems.ingot_polymer, 'W', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE) });
		addShapedOreRecipe(new ItemStack(ModItems.security_boots, 1), new Object[] { "P P", "I I", 'P', "plateSteel", 'I', ModItems.plate_polymer });

		addShapedOreRecipe(new ItemStack(ModItems.jetpack_boost, 1), new Object[] { "PTP", "SLS", "W W", 'P', "plateSteel", 'T', ModItems.tank_steel, 'S', ModItems.pipes_steel, 'L', Items.LEATHER, 'W', ModItems.thruster_small });
		addShapedOreRecipe(new ItemStack(ModItems.jetpack_fly, 1), new Object[] { "PTP", "SLS", "W W", 'P', "plateSteel", 'T', ModItems.cap_aluminium, 'S', ModItems.pipes_steel, 'L', ModItems.jetpack_boost, 'W', ModItems.thruster_small });
		addShapedOreRecipe(new ItemStack(ModItems.jetpack_break, 1), new Object[] { "PTP", "SLS", "P P", 'P', "plateSteel", 'T', ModItems.cap_aluminium, 'S', ModItems.coil_tungsten, 'L', ModItems.jetpack_boost });
		addShapedOreRecipe(new ItemStack(ModItems.jetpack_vector, 1), new Object[] { "PTP", "SLS", "W W", 'P', "plateTitanium", 'T', ModItems.circuit_copper, 'S', ModItems.motor, 'L', ModItems.jetpack_fly, 'W', ModItems.thruster_small });

		addShapedRecipe(new ItemStack(ModItems.chainsaw, 1), new Object[] { "  H", "BBP", "  C", 'H', ModItems.hull_small_steel, 'B', ModItems.blades_steel, 'P', ModItems.piston_selenium, 'C', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.diesel)) });
		addShapedRecipe(new ItemStack(ModItems.chainsaw, 1), new Object[] { "  H", "BBP", "  C", 'H', ModItems.hull_small_steel, 'B', ModItems.blades_steel, 'P', ModItems.piston_selenium, 'C', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.petroil)) });
		addShapedRecipe(new ItemStack(ModItems.chainsaw, 1), new Object[] { "  H", "BBP", "  C", 'H', ModItems.hull_small_steel, 'B', ModItems.blades_steel, 'P', ModItems.piston_selenium, 'C', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.biofuel)) });

		addShapedOreRecipe(new ItemStack(ModItems.wiring_red_copper, 1), new Object[] { "PPP", "PIP", "PPP", 'P', "plateSteel", 'I', "ingotSteel" });

		addShapedOreRecipe(new ItemStack(ModItems.hazmat_helmet, 1), new Object[] { "EEE", "EIE", "FPF", 'E', ModItems.hazmat_cloth, 'I', "paneGlass", 'P', "plateSteel", 'F', ModItems.filter_coal });
		addShapedRecipe(new ItemStack(ModItems.hazmat_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.hazmat_cloth });
		addShapedRecipe(new ItemStack(ModItems.hazmat_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.hazmat_cloth });
		addShapedRecipe(new ItemStack(ModItems.hazmat_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.hazmat_cloth });
		addShapedOreRecipe(new ItemStack(ModItems.hazmat_helmet_red, 1), new Object[] { "EEE", "IEI", "EFE", 'E', ModItems.hazmat_cloth_red, 'I', "paneGlass", 'F', ModItems.gas_mask_filter });
		addShapedRecipe(new ItemStack(ModItems.hazmat_plate_red, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.hazmat_cloth_red });
		addShapedRecipe(new ItemStack(ModItems.hazmat_legs_red, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.hazmat_cloth_red });
		addShapedRecipe(new ItemStack(ModItems.hazmat_boots_red, 1), new Object[] { "E E", "E E", 'E', ModItems.hazmat_cloth_red });
		addShapedOreRecipe(new ItemStack(ModItems.hazmat_helmet_grey, 1), new Object[] { "EEE", "IEI", "EFE", 'E', ModItems.hazmat_cloth_grey, 'I', "paneGlass", 'F', ModItems.gas_mask_filter });
		addShapedRecipe(new ItemStack(ModItems.hazmat_plate_grey, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.hazmat_cloth_grey });
		addShapedRecipe(new ItemStack(ModItems.hazmat_legs_grey, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.hazmat_cloth_grey });
		addShapedRecipe(new ItemStack(ModItems.hazmat_boots_grey, 1), new Object[] { "E E", "E E", 'E', ModItems.hazmat_cloth_grey });
		addShapedOreRecipe(new ItemStack(ModItems.asbestos_helmet, 1), new Object[] { "EEE", "EIE", 'E', ModItems.asbestos_cloth, 'I', "plateGold" });
		addShapedRecipe(new ItemStack(ModItems.asbestos_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.asbestos_cloth });
		addShapedRecipe(new ItemStack(ModItems.asbestos_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.asbestos_cloth });
		addShapedRecipe(new ItemStack(ModItems.asbestos_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.asbestos_cloth });
		addShapedOreRecipe(new ItemStack(ModItems.hazmat_paa_helmet, 1), new Object[] { "EEE", "IEI", "FPF", 'E', ModItems.plate_paa, 'I', "paneGlass", 'P', "plateSteel", 'F', ModItems.filter_coal });
		addShapedRecipe(new ItemStack(ModItems.hazmat_paa_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.plate_paa });
		addShapedRecipe(new ItemStack(ModItems.hazmat_paa_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.plate_paa });
		addShapedRecipe(new ItemStack(ModItems.hazmat_paa_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.plate_paa });
		addShapedOreRecipe(new ItemStack(ModItems.paa_plate, 1), new Object[] { "E E", "NEN", "ENE", 'E', ModItems.plate_paa, 'N', OreDictManager.getReflector() });
		addShapedOreRecipe(new ItemStack(ModItems.paa_legs, 1), new Object[] { "EEE", "N N", "E E", 'E', ModItems.plate_paa, 'N', OreDictManager.getReflector() });
		addShapedOreRecipe(new ItemStack(ModItems.paa_boots, 1), new Object[] { "E E", "N N", 'E', ModItems.plate_paa, 'N', OreDictManager.getReflector() });

		addShapedOreRecipe(new ItemStack(ModItems.goggles, 1), new Object[] { "P P", "GPG", 'G', "paneGlass", 'P', "plateSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.gas_mask, 1), new Object[] { "PPP", "GPG", " F ", 'G', "paneGlass", 'P', "plateSteel", 'F', ModItems.gas_mask_filter });
		addShapedOreRecipe(new ItemStack(ModItems.gas_mask_m65, 1), new Object[] { "PPP", "GPG", " F ", 'G', "paneGlass", 'P', ModItems.plate_polymer, 'F', ModItems.gas_mask_filter });
		addShapedOreRecipe(new ItemStack(ModItems.gas_mask_mono, 1), new Object[] { " P ", "PPP", " F ", 'P', ModItems.plate_polymer, 'F', ModItems.gas_mask_filter_mono });
		addShapedOreRecipe(new ItemStack(ModItems.gas_mask_filter, 1), new Object[] { "F", "I", "F", 'F', ModItems.filter_coal, 'I', "plateIron" });
		addShapedOreRecipe(new ItemStack(ModItems.jetpack_tank, 1), new Object[] { " S ", "BKB", " S ", 'S', "plateSteel", 'B', ModItems.bolt_tungsten, 'K', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.kerosene)) });
		addShapedOreRecipe(new ItemStack(ModItems.gun_kit_1, 4), new Object[] { "I ", "LB", "P ", 'I', ModItems.plate_polymer, 'L', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.lubricant)), 'B', ModItems.bolt_tungsten, 'P', "plateIron" });
		addShapedOreRecipe(new ItemStack(ModItems.gun_kit_2, 1), new Object[] { "III", "GLG", "PPP", 'I', ModItems.plate_polymer, 'L', ModItems.ducttape, 'G', ModItems.gun_kit_1, 'P', "plateIron" });

		addShapedOreRecipe(new ItemStack(ModItems.igniter, 1), new Object[] { " W", "SC", "CE", 'S', "plateSteel", 'W', ModItems.wire_schrabidium, 'C', ModItems.circuit_schrabidium, 'E', ModItems.ingot_euphemium });
		addShapedRecipe(new ItemStack(ModItems.euphemium_helmet, 1), new Object[] { "EEE", "E E", 'E', ModItems.plate_euphemium });
		addShapedRecipe(new ItemStack(ModItems.euphemium_plate, 1), new Object[] { "EWE", "EEE", "EEE", 'E', ModItems.plate_euphemium, 'W', ModItems.watch });
		addShapedRecipe(new ItemStack(ModItems.euphemium_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.plate_euphemium });
		addShapedRecipe(new ItemStack(ModItems.euphemium_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.plate_euphemium });
		addShapedRecipe(new ItemStack(ModItems.watch, 1), new Object[] { "LEL", "EWE", "LEL", 'E', ModItems.ingot_euphemium, 'L', new ItemStack(Items.DYE, 1, 4), 'W', Items.CLOCK });
		addShapedRecipe(new ItemStack(ModItems.apple_euphemium, 1), new Object[] { "EEE", "EAE", "EEE", 'E', ModItems.nugget_euphemium, 'A', Items.APPLE });
		addShapedRecipe(new ItemStack(ModItems.plate_euphemium, 1), new Object[] { "AEA", "ENE", "AEA", 'E', ModItems.ingot_euphemium, 'N', Items.NETHER_STAR, 'A', ModItems.powder_astatine });

		addShapedOreRecipe(new ItemStack(ModItems.mask_of_infamy, 1), new Object[] { "III", "III", " I ", 'I', "plateIron" });
		addShapedOreRecipe(new ItemStack(ModItems.designator, 1), new Object[] { "  A", "#B#", "#B#", '#', "plateIron", 'A', "plateSteel", 'B', ModItems.circuit_red_copper });
		addShapedOreRecipe(new ItemStack(ModItems.designator_range, 1), new Object[] { "RRD", "PIC", "  P", 'P', "plateSteel", 'R', Items.REDSTONE, 'C', ModItems.circuit_gold, 'D', ModItems.designator, 'I', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.designator_manual, 1), new Object[] { "  A", "#C#", "#B#", '#', "ingotPolymer", 'A', "plateLead", 'B', ModItems.circuit_gold, 'C', ModItems.designator });
		addShapedOreRecipe(new ItemStack(ModItems.linker, 1), new Object[] { "I I", "ICI", "GGG", 'I', "plateIron", 'G', "plateGold", 'C', ModItems.circuit_gold });
		addShapedOreRecipe(new ItemStack(ModItems.oil_detector, 1), new Object[] { "W I", "WCI", "PPP", 'W', ModItems.wire_gold, 'I', "ingotCopper", 'C', ModItems.circuit_red_copper, 'P', "plateSteel" });
		addShapedRecipe(new ItemStack(ModItems.turret_chip, 1), new Object[] { "WWW", "CPC", "WWW", 'W', ModItems.wire_gold, 'P', ModItems.ingot_polymer, 'C', ModItems.circuit_gold, });
		addShapedOreRecipe(new ItemStack(ModItems.turret_biometry, 1), new Object[] { "CC ", "GGS", "SSS", 'C', ModItems.circuit_copper, 'S', "plateSteel", 'G', "plateGold" });
		addShapedOreRecipe(new ItemStack(ModItems.survey_scanner, 1), new Object[] { "SWS", " G ", "PCP", 'W', ModItems.wire_gold, 'P', ModItems.ingot_polymer, 'C', ModItems.circuit_gold, 'S', "plateSteel", 'G', "ingotGold" });
		addShapedOreRecipe(new ItemStack(ModItems.geiger_counter, 1), new Object[] { "GPP", "WCS", "WBB", 'W', ModItems.wire_gold, 'P', ModItems.ingot_polymer, 'C', ModItems.circuit_copper, 'G', "ingotGold", 'S', "plateSteel", 'B', ModItems.ingot_beryllium });

		addShapedOreRecipe(new ItemStack(ModItems.key, 1), new Object[] { "  B", " B ", "P  ", 'P', "plateSteel", 'B', ModItems.bolt_tungsten });
		addShapedOreRecipe(new ItemStack(ModItems.key_kit, 1), new Object[] { "PKP", "DTD", "PKP", 'P', "plateGold", 'K', ModItems.key, 'D', "dustDiamond", 'T', ModItems.screwdriver });
		addShapedOreRecipe(new ItemStack(ModItems.key_red, 1), new Object[] { "DSC", "SMS", "KSD", 'C', ModItems.circuit_targeting_tier4, 'M', Items.NETHER_STAR, 'K', ModItems.key, 'D', "dustDesh", 'S', "plateSaturnite" });
		addShapedRecipe(new ItemStack(ModItems.pin, 1), new Object[] { "W ", " W", " W", 'W', ModItems.wire_copper });
		addShapedOreRecipe(new ItemStack(ModItems.padlock_rusty, 1), new Object[] { "I", "B", "I", 'I', "ingotIron", 'B', ModItems.bolt_tungsten });
		addShapedOreRecipe(new ItemStack(ModItems.padlock, 1), new Object[] { " P ", "PBP", "PPP", 'P', "plateSteel", 'B', ModItems.bolt_tungsten });
		addShapedOreRecipe(new ItemStack(ModItems.padlock_reinforced, 1), new Object[] { " P ", "PBP", "PDP", 'P', "plateAdvanced", 'D', ModItems.plate_desh, 'B', ModItems.bolt_dura_steel });
		addShapedOreRecipe(new ItemStack(ModItems.padlock_unbreakable, 1), new Object[] { " P ", "PBP", "PDP", 'P', "plateSaturnite", 'D', "gemDiamond", 'B', ModItems.bolt_dura_steel });

		addShapedRecipe(new ItemStack(ModItems.euphemium_stopper, 1), new Object[] { "I", "S", "S", 'I', ModItems.ingot_euphemium, 'S', Items.STICK });
		addShapedOreRecipe(new ItemStack(ModItems.matchstick, 16), new Object[] { "I", "S", 'I', "sulfur", 'S', Items.STICK });
		addShapedOreRecipe(new ItemStack(ModItems.matchstick, 16), new Object[] { "I", "S", 'I', "dustSulfur", 'S', Items.STICK });
		addShapedRecipe(new ItemStack(ModItems.matchstick, 24), new Object[] { "I", "S", 'I', ModItems.powder_fire, 'S', Items.STICK });
		addShapedOreRecipe(new ItemStack(ModItems.crowbar, 1), new Object[] { "II", " I", " I", 'I', "ingotSteel" });

		addShapelessOreRecipe(new ItemStack(ModItems.powder_power, 5), new Object[] { "dustRedstone", "dustGlowstone", "dustDiamond", "dustNeptunium", "dustMagnetizedTungsten" });
		addShapelessOreRecipe(new ItemStack(ModItems.ballistite, 3), new Object[] { Items.GUNPOWDER, "dustSaltpeter", Items.SUGAR });

		addShapelessOreRecipe(new ItemStack(ModItems.powder_nitan_mix, 6), new Object[] { "dustNeptunium", "dustIodine", "dustThorium", "dustAstatine", "dustNeodymium", "dustCaesium" });
		addShapelessOreRecipe(new ItemStack(ModItems.powder_nitan_mix, 6), new Object[] { "dustStrontium", "dustCobalt", "dustBromine", "dustTennessine", "dustNiobium", "dustCerium" });
		addShapelessOreRecipe(new ItemStack(ModItems.powder_spark_mix, 5), new Object[] { "dustDesh", "dustEuphemium", ModItems.powder_meteorite, ModItems.powder_power, ModItems.powder_nitan_mix });
		addShapelessOreRecipe(new ItemStack(ModItems.powder_meteorite, 5), new Object[] { "dustIron", "dustCopper", "dustLithium", "dustTungsten", "dustUranium" });
		addShapelessOreRecipe(new ItemStack(ModItems.powder_thermite, 4), new Object[] { "dustIron", "dustIron", "dustIron", "dustAluminum" });
		addShapelessOreRecipe(new ItemStack(ModItems.powder_semtex_mix, 3), new Object[] { ModItems.solid_fuel, ModItems.cordite, "dustSaltpeter" });

		addShapedRecipe(ItemFluidCanister.getFullCanister(ModForgeFluids.petroil, 9), new Object[] { "RRR", "RLR", "RRR", 'R', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.reclaimed)), 'L', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.lubricant)) });

		addShapedOreRecipe(new ItemStack(ModItems.record_lc, 1), new Object[] { " S ", "SDS", " S ", 'S', "ingotPolymer", 'D', "dustLapis" });
		addShapedOreRecipe(new ItemStack(ModItems.record_ss, 1), new Object[] { " S ", "SDS", " S ", 'S', "ingotPolymer", 'D', "dustAdvanced" });
		addShapedOreRecipe(new ItemStack(ModItems.record_vc, 1), new Object[] { " S ", "SDS", " S ", 'S', "ingotPolymer", 'D', "dustCMBSteel" });

		addShapelessOreRecipe(new ItemStack(ModItems.powder_advanced_alloy, 4), new Object[] { "dustRedstone", "dustIron", "dustCoal", "dustCopper" });
		addShapelessOreRecipe(new ItemStack(ModItems.powder_advanced_alloy, 3), new Object[] { "dustIron", "dustCoal", "dustMingrade" });
		addShapelessOreRecipe(new ItemStack(ModItems.powder_advanced_alloy, 3), new Object[] { "dustRedstone", "dustSteel", "dustCopper" });
		addShapelessOreRecipe(new ItemStack(ModItems.powder_advanced_alloy, 2), new Object[] { "dustMingrade", "dustSteel" });
		addShapelessOreRecipe(new ItemStack(ModItems.powder_magnetized_tungsten, 1), new Object[] { "dustTungsten", "nuggetSchrabidium" });
		addShapelessOreRecipe(new ItemStack(ModItems.powder_red_copper, 2), new Object[] { "dustRedstone", "dustCopper" });
		addShapelessOreRecipe(new ItemStack(ModItems.powder_steel, 2), new Object[] { "dustIron", "dustCoal" });
		addShapelessOreRecipe(new ItemStack(ModItems.powder_dura_steel, 2), new Object[] { "dustSteel", "dustTungsten" });
		addShapelessOreRecipe(new ItemStack(ModItems.powder_dura_steel, 2), new Object[] { "dustSteel", ModItems.powder_cobalt });
		addShapelessOreRecipe(new ItemStack(ModItems.powder_dura_steel, 4), new Object[] { "dustIron", "dustCoal", "dustTungsten", "dustTungsten" });
		addShapelessOreRecipe(new ItemStack(ModItems.powder_dura_steel, 4), new Object[] { "dustIron", "dustCoal", ModItems.powder_cobalt, ModItems.powder_cobalt });

		addShapedOreRecipe(new ItemStack(ModItems.saw, 1), new Object[] { "IIL", "PP ", 'P', "plateSteel", 'I', "ingotSteel", 'L', Items.LEATHER });
		addShapedOreRecipe(new ItemStack(ModItems.bat, 1), new Object[] { "P", "P", "S", 'S', "plateSteel", 'P', "plankWood" });
		addShapelessOreRecipe(new ItemStack(ModItems.bat_nail, 1), new Object[] { ModItems.bat, "plateSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.golf_club, 1), new Object[] { "IP", " P", " P", 'P', "plateSteel", 'I', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.pipe_rusty, 1), new Object[] { "II", " I", " I", 'I', "ingotIron" });
		addShapedOreRecipe(new ItemStack(ModItems.pipe_lead, 1), new Object[] { "II", " I", " I", 'I', "ingotLead" });

		addShapedOreRecipe(new ItemStack(ModItems.bottle_opener, 1), new Object[] { "S", "P", 'S', "plateSteel", 'P', "plankWood" });

		addShapedOreRecipe(new ItemStack(ModItems.polaroid, 1), new Object[] { " C ", "RPY", " B ", 'B', "dustLapis", 'C', "dustCoal", 'R', "dustAdvanced", 'Y', "dustGold", 'P', Items.PAPER });

		addShapedOreRecipe(new ItemStack(ModItems.ullapool_caber, 1), new Object[] { "ITI", " S ", " S ", 'I', "plateIron", 'T', Blocks.TNT, 'S', Items.STICK });
		addShapelessOreRecipe(new ItemStack(ModItems.chocolate_milk, 1), new Object[] { "paneGlass", new ItemStack(Items.DYE, 1, 3), Items.MILK_BUCKET, ModBlocks.block_niter, ModItems.sulfur, ModItems.sulfur, ModItems.sulfur, ModItems.powder_fire });

		addShapelessRecipe(new ItemStack(ModItems.crystal_horn, 1), new Object[] { ModItems.powder_neptunium, ModItems.powder_iodine, ModItems.powder_thorium, ModItems.powder_astatine, ModItems.powder_neodymium, ModItems.powder_caesium, ModBlocks.block_meteor, ModBlocks.gravel_obsidian, Items.WATER_BUCKET });
		addShapelessRecipe(new ItemStack(ModItems.crystal_charred, 1), new Object[] { ModItems.powder_strontium, ModItems.powder_cobalt, ModItems.powder_bromine, ModItems.powder_niobium, ModItems.powder_tennessine, ModItems.powder_cerium, ModBlocks.block_meteor, ModBlocks.block_aluminium, Items.WATER_BUCKET });
		addShapedOreRecipe(new ItemStack(ModBlocks.crystal_virus, 1), new Object[] { "STS", "THT", "STS", 'S', ModItems.particle_strange, 'T', "dustTungsten", 'H', ModItems.crystal_horn });
		addShapedOreRecipe(new ItemStack(ModBlocks.crystal_pulsar, 32), new Object[] { "STS", "THT", "STS", 'S', new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.uf6)), 'T', "dustAluminum", 'H', ModItems.crystal_charred });

		addShapedOreRecipe(new ItemStack(ModBlocks.fluid_duct_mk2, 8), new Object[] { "SAS", "   ", "SAS", 'S', "plateSteel", 'A', "plateAluminum" });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_assembler, 1), new Object[] { "WWW", "MCM", "ISI", 'W', "paneGlass", 'M', ModItems.motor, 'C', ModItems.circuit_aluminium, 'I', "blockCopper", 'S', "blockSteel" });
		addShapedRecipe(new ItemStack(ModItems.template_folder, 1), new Object[] { "LPL", "BPB", "LPL", 'P', Items.PAPER, 'L', new ItemStack(Items.DYE, 1, 4), 'B', new ItemStack(Items.DYE, 1, 15) });
		addShapedOreRecipe(new ItemStack(ModItems.turret_control, 1), new Object[] { "R12", "PPI", "  I", 'R', Items.REDSTONE, '1', ModItems.circuit_aluminium, '2', ModItems.circuit_red_copper, 'P', "plateSteel", 'I', "ingotSteel" });
		addShapedRecipe(new ItemStack(ModItems.pellet_antimatter, 1), new Object[] { "###", "###", "###", '#', new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.amat)) });
		addShapedOreRecipe(new ItemStack(ModItems.fluid_tank_full, 8), new Object[] { "121", "1 1", "121", '1', "plateAluminum", '2', "plateIron" });
		addShapedOreRecipe(new ItemStack(ModItems.fluid_barrel_full, 2), new Object[] { "121", "1 1", "121", '1', "plateSteel", '2', "plateAluminum" });
		addShapedOreRecipe(new ItemStack(ModItems.inf_water, 1), new Object[] { "222", "131", "222", '1', Items.WATER_BUCKET, '2', "plateAluminum", '3', Items.DIAMOND });
		addShapelessRecipe(new ItemStack(ModItems.powder_desh_mix, 1), new Object[] { ModItems.powder_actinium_tiny, ModItems.powder_actinium_tiny, ModItems.powder_lanthanium_tiny, ModItems.powder_lanthanium_tiny, ModItems.powder_cerium_tiny, ModItems.powder_cobalt_tiny, ModItems.powder_lithium_tiny, ModItems.powder_neodymium_tiny, ModItems.powder_niobium_tiny });
		addShapelessRecipe(new ItemStack(ModItems.powder_desh_mix, 9), new Object[] { ModItems.powder_actinium, ModItems.powder_actinium, ModItems.powder_lanthanium, ModItems.powder_lanthanium, ModItems.powder_cerium, ModItems.powder_cobalt, ModItems.powder_lithium, ModItems.powder_neodymium, ModItems.powder_niobium });
		addShapelessOreRecipe(new ItemStack(ModItems.powder_desh_ready, 1), new Object[] { ModItems.powder_desh_mix, ModItems.nugget_mercury, ModItems.nugget_mercury, "dustCoal" });

		addShapedOreRecipe(new ItemStack(ModItems.dynosphere_base), new Object[] { "RPR", "PBP", "RPR", 'R', "dustMingrade", 'P', "plateSteel", 'B', "blockRedstone" });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.dynosphere_desh), new Object[] { "RPR", "PBP", "RPR", 'R', ModItems.powder_desh_mix, 'P', "ingotDesh", 'B', ModItems.dynosphere_base });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.dynosphere_schrabidium), new Object[] { "RPR", "PBP", "RPR", 'R', ModItems.powder_power, 'P', "ingotSchrabidium", 'B', ModItems.dynosphere_desh_charged });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.dynosphere_euphemium), new Object[] { "RPR", "PBP", "RPR", 'R', ModItems.powder_nitan_mix, 'P', "ingotEuphemium", 'B', ModItems.dynosphere_schrabidium_charged });
		addShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.dynosphere_dineutronium), new Object[] { "RPR", "PBP", "RPR", 'R', ModItems.powder_spark_mix, 'P', "ingotDineutronium", 'B', ModItems.dynosphere_euphemium_charged });

		//not so Temporary Crappy Recipes
		addShapedOreRecipe(new ItemStack(ModItems.gun_revolver_pip, 1), new Object[] { " G ", "SSP", " TI", 'G', "paneGlass", 'S', "plateSteel", 'P', ModItems.mechanism_revolver_2, 'T', ModItems.wire_tungsten, 'I', "ingotPolymer" });
		addShapedOreRecipe(new ItemStack(ModItems.gun_revolver_nopip, 1), new Object[] { "SSP", " TI", 'S', "plateSteel", 'P', ModItems.mechanism_revolver_2, 'T', ModItems.wire_tungsten, 'I', "ingotPolymer" });
		addShapedOreRecipe(new ItemStack(ModItems.gun_revolver_silver, 1), new Object[] { "SSP", " TI", 'S', "plateAluminum", 'P', ModItems.mechanism_revolver_2, 'T', ModItems.wire_tungsten, 'I', "plankWood" });
		addShapedOreRecipe(new ItemStack(ModItems.gun_revolver_blackjack, 1), new Object[] { "SSP", " TI", 'S', "plateSteel", 'P', ModItems.mechanism_revolver_2, 'T', ModItems.wire_tungsten, 'I', "plankWood" });
		addShapedRecipe(new ItemStack(ModItems.gun_revolver_red, 1), new Object[] { "R ", " B", 'R', ModItems.key_red, 'B', ModItems.gun_revolver_blackjack });
		addShapedOreRecipe(new ItemStack(ModItems.plate_dineutronium, 4), new Object[] { "PIP", "IDI", "PIP", 'P', ModItems.powder_spark_mix, 'I', "ingotDineutronium", 'D', "ingotDesh" });
		addShapedOreRecipe(new ItemStack(ModItems.plate_desh, 4), new Object[] { "PIP", "IDI", "PIP", 'P', ModItems.powder_polymer, 'I', "ingotDesh", 'D', "ingotDuraSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.piston_selenium, 1), new Object[] { "SSS", "STS", " D ", 'S', "plateSteel", 'T', "ingotTungsten", 'D', ModItems.bolt_dura_steel });
		addShapelessOreRecipe(new ItemStack(ModItems.catalyst_clay), new Object[] { "dustIron", Items.CLAY_BALL });
		addShapedRecipe(new ItemStack(ModItems.singularity_spark, 1), new Object[] { "XAX", "BCB", "XAX", 'X', ModItems.plate_dineutronium, 'A', ModItems.singularity_counter_resonant, 'B', ModItems.singularity_super_heated, 'C', ModItems.black_hole });
		addShapedRecipe(new ItemStack(ModItems.singularity_spark, 1), new Object[] { "XBX", "ACA", "XBX", 'X', ModItems.plate_dineutronium, 'A', ModItems.singularity_counter_resonant, 'B', ModItems.singularity_super_heated, 'C', ModItems.black_hole });
		addShapedRecipe(new ItemStack(ModItems.ams_core_sing, 1), new Object[] { "EAE", "ASA", "EAE", 'E', ModItems.plate_euphemium, 'A', new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.aschrab)), 'S', ModItems.singularity });
		addShapedRecipe(new ItemStack(ModItems.ams_core_wormhole, 1), new Object[] { "DPD", "PSP", "DPD", 'D', ModItems.plate_dineutronium, 'P', ModItems.powder_spark_mix, 'S', ModItems.singularity });
		addShapedRecipe(new ItemStack(ModItems.ams_core_eyeofharmony, 1), new Object[] { "ALA", "LSL", "ALA", 'A', ModItems.plate_dalekanium, 'L', new IngredientContainsTag(ItemFluidTank.getFullBarrel(FluidRegistry.LAVA)), 'S', ModItems.black_hole });
		addShapedOreRecipe(new ItemStack(ModItems.ams_core_thingy), new Object[] { "NSN", "NGN", "G G", 'N', "nuggetGold", 'G', "ingotGold", 'S', ModItems.battery_spark_cell_10000 });
		addShapedOreRecipe(new ItemStack(ModItems.photo_panel), new Object[] { " G ", "IPI", " C ", 'G', "paneGlass", 'I', ModItems.plate_polymer, 'P', "dustNetherQuartz", 'C', ModItems.circuit_aluminium });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_satlinker), new Object[] { "PSP", "SCS", "PSP", 'P', "plateSteel", 'S', ModItems.ingot_starmetal, 'C', ModItems.sat_chip });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_telelinker), new Object[] { "PSP", "SCS", "PSP", 'P', "plateSteel", 'S', "ingotAdvanced", 'C', ModItems.turret_biometry });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_keyforge), new Object[] { "PCP", "WSW", "WSW", 'P', "plateSteel", 'S', "ingotTungsten", 'C', ModItems.padlock, 'W', "plankWood" });
		addShapedRecipe(new ItemStack(ModItems.sat_chip), new Object[] { "WWW", "CIC", "WWW", 'W', ModItems.wire_red_copper, 'C', ModItems.circuit_red_copper, 'I', ModItems.ingot_polymer });
		addShapedRecipe(new ItemStack(ModItems.sat_mapper), new Object[] { "H", "B", 'H', ModItems.sat_head_mapper, 'B', ModItems.sat_base });
		addShapedRecipe(new ItemStack(ModItems.sat_scanner), new Object[] { "H", "B", 'H', ModItems.sat_head_scanner, 'B', ModItems.sat_base });
		addShapedRecipe(new ItemStack(ModItems.sat_radar), new Object[] { "H", "B", 'H', ModItems.sat_head_radar, 'B', ModItems.sat_base });
		addShapedRecipe(new ItemStack(ModItems.sat_laser), new Object[] { "H", "B", 'H', ModItems.sat_head_laser, 'B', ModItems.sat_base });
		addShapedRecipe(new ItemStack(ModItems.sat_resonator), new Object[] { "H", "B", 'H', ModItems.sat_head_resonator, 'B', ModItems.sat_base });
		addShapelessRecipe(new ItemStack(ModItems.sat_mapper), new Object[] { ModBlocks.sat_mapper });
		addShapelessRecipe(new ItemStack(ModItems.sat_scanner), new Object[] { ModBlocks.sat_scanner });
		addShapelessRecipe(new ItemStack(ModItems.sat_radar), new Object[] { ModBlocks.sat_radar });
		addShapelessRecipe(new ItemStack(ModItems.sat_laser), new Object[] { ModBlocks.sat_laser });
		addShapelessRecipe(new ItemStack(ModItems.sat_resonator), new Object[] { ModBlocks.sat_resonator });
		addShapelessRecipe(new ItemStack(ModItems.sat_foeq), new Object[] { ModBlocks.sat_foeq });
		addShapelessRecipe(new ItemStack(ModItems.geiger_counter), new Object[] { ModBlocks.geiger });
		addShapelessRecipe(new ItemStack(ModBlocks.sat_mapper), new Object[] { ModItems.sat_mapper });
		addShapelessRecipe(new ItemStack(ModBlocks.sat_scanner), new Object[] { ModItems.sat_scanner });
		addShapelessRecipe(new ItemStack(ModBlocks.sat_radar), new Object[] { ModItems.sat_radar });
		addShapelessRecipe(new ItemStack(ModBlocks.sat_laser), new Object[] { ModItems.sat_laser });
		addShapelessRecipe(new ItemStack(ModBlocks.sat_resonator), new Object[] { ModItems.sat_resonator });
		addShapelessRecipe(new ItemStack(ModBlocks.sat_foeq), new Object[] { ModItems.sat_foeq });
		addShapelessRecipe(new ItemStack(ModBlocks.geiger), new Object[] { ModItems.geiger_counter });
		addShapedOreRecipe(new ItemStack(ModItems.sat_interface), new Object[] { "ISI", "PCP", "PAP", 'I', "ingotSteel", 'S', ModItems.ingot_starmetal, 'P', ModItems.plate_polymer, 'C', ModItems.sat_chip, 'A', ModItems.circuit_gold });
		addShapedOreRecipe(new ItemStack(ModItems.sat_coord), new Object[] { "SII", "SCA", "SPP", 'I', "ingotSteel", 'S', ModItems.ingot_starmetal, 'P', ModItems.plate_polymer, 'C', ModItems.sat_chip, 'A', ModItems.circuit_red_copper });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_spp_bottom), new Object[] { "MDM", "LCL", "LWL", 'M', "ingotMagnetizedTungsten", 'D', ModItems.plate_desh, 'L', "plateLead", 'C', ModItems.circuit_gold, 'W', ModItems.coil_magnetized_tungsten });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_spp_top), new Object[] { "LWL", "LCL", "MDM", 'M', "ingotMagnetizedTungsten", 'D', ModItems.plate_desh, 'L', "plateLead", 'C', ModItems.circuit_gold, 'W', ModItems.coil_magnetized_tungsten });
		addShapelessRecipe(new ItemStack(ModBlocks.machine_spp_bottom), new Object[] { ModBlocks.machine_spp_top });
		addShapelessRecipe(new ItemStack(ModBlocks.machine_spp_top), new Object[] { ModBlocks.machine_spp_bottom });
		addShapedRecipe(new ItemStack(ModItems.gun_b93), new Object[] { "PCE", "SEB", "PCE", 'P', ModItems.plate_dineutronium, 'C', ModItems.weaponized_starblaster_cell, 'E', ModItems.component_emitter, 'B', ModItems.gun_b92, 'S', ModItems.singularity_spark });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_transformer), new Object[] { "SDS", "MCM", "MCM", 'S', "ingotIron", 'D', "ingotMingrade", 'M', ModItems.coil_advanced_alloy, 'C', ModItems.circuit_copper });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_transformer_20), new Object[] { "SDS", "MCM", "MCM", 'S', "ingotIron", 'D', "ingotMingrade", 'M', ModItems.coil_copper, 'C', ModItems.circuit_copper });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_transformer_dnt), new Object[] { "SDS", "MCM", "MCM", 'S', ModItems.ingot_starmetal, 'D', "ingotDesh", 'M', ModBlocks.fwatz_conductor, 'C', ModItems.circuit_targeting_tier6 });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_transformer_dnt_20), new Object[] { "SDS", "MCM", "MCM", 'S', ModItems.ingot_starmetal, 'D', "ingotDesh", 'M', ModBlocks.fusion_conductor, 'C', ModItems.circuit_targeting_tier6 });
		addShapelessRecipe(new ItemStack(ModItems.bottle_sparkle), new Object[] { ModItems.bottle_nuka, Items.CARROT, Items.GOLD_NUGGET });
		addShapelessRecipe(new ItemStack(ModItems.bottle_rad), new Object[] { ModItems.bottle_quantum, Items.CARROT, Items.GOLD_NUGGET });
		addShapedOreRecipe(new ItemStack(ModItems.grenade_nuke), new Object[] { "CGC", "CGC", "PAP", 'C', ModBlocks.det_charge, 'G', ModItems.grenade_mk2, 'P', "plateAdvanced", 'A', Blocks.ANVIL });
		addShapedOreRecipe(new ItemStack(ModBlocks.radiobox), new Object[] { "PLP", "PSP", "PLP", 'P', "plateSteel", 'S', ModItems.ring_starmetal, 'L', OreDictManager.getReflector() });
		addShapedOreRecipe(new ItemStack(ModBlocks.radiorec), new Object[] { "  W", "PCP", "PIP", 'W', ModItems.wire_copper, 'P', "plateSteel", 'C', ModItems.circuit_red_copper, 'I', "ingotPolymer" });
		addShapedOreRecipe(new ItemStack(ModItems.jackt), new Object[] { "S S", "LIL", "LIL", 'S', "plateSteel", 'L', Items.LEATHER, 'I', ModItems.plate_polymer });
		addShapedOreRecipe(new ItemStack(ModItems.jackt2), new Object[] { "S S", "LIL", "III", 'S', "plateSteel", 'L', Items.LEATHER, 'I', ModItems.plate_polymer });
		addShapedOreRecipe(new ItemStack(ModItems.grenade_cloud), new Object[] { "SPS", "CAC", "SPS", 'S', "dustSulfur", 'P', ModItems.powder_poison, 'C', "dustCopper", 'A', new IngredientContainsTag(ItemFluidTank.getFullTank(ModForgeFluids.acid)) });
		addShapedRecipe(new ItemStack(ModItems.grenade_pink_cloud), new Object[] { " S ", "ECE", " E ", 'S', ModItems.powder_spark_mix, 'E', ModItems.powder_magic, 'C', ModItems.grenade_cloud });
		addShapedOreRecipe(new ItemStack(ModBlocks.vent_chlorine), new Object[] { "IGI", "ICI", "IDI", 'I', "plateIron", 'G', Blocks.IRON_BARS, 'C', ModItems.pellet_gas, 'D', Blocks.DISPENSER });
		addShapedRecipe(new ItemStack(ModBlocks.vent_chlorine_seal), new Object[] { "ISI", "SCS", "ISI", 'I', ModItems.ingot_saturnite, 'S', ModItems.ingot_starmetal, 'C', ModItems.chlorine_pinwheel });
		addShapedOreRecipe(new ItemStack(ModBlocks.vent_cloud), new Object[] { "IGI", "ICI", "IDI", 'I', "plateIron", 'G', Blocks.IRON_BARS, 'C', ModItems.grenade_cloud, 'D', Blocks.DISPENSER });
		addShapedOreRecipe(new ItemStack(ModBlocks.vent_pink_cloud), new Object[] { "IGI", "ICI", "IDI", 'I', "plateIron", 'G', Blocks.IRON_BARS, 'C', ModItems.grenade_pink_cloud, 'D', Blocks.DISPENSER });
		addShapedOreRecipe(new ItemStack(ModBlocks.spikes, 4), new Object[] { "FFF", "BBB", "TTT", 'F', Items.FLINT, 'B', ModItems.bolt_tungsten, 'T', "ingotTungsten" });
		addShapedOreRecipe(new ItemStack(ModBlocks.mine_ap, 4), new Object[] { "C", "P", "T", 'C', ModItems.circuit_targeting_tier2, 'P', "plateIron", 'T', ModItems.ingot_semtex });
		addShapedOreRecipe(new ItemStack(ModBlocks.mine_he, 1), new Object[] { " C ", "PTP", 'C', ModItems.circuit_targeting_tier2, 'P', "plateSteel", 'T', ModItems.ingot_semtex });
		addShapedOreRecipe(new ItemStack(ModBlocks.mine_shrap, 2), new Object[] { "LLL", " C ", "PTP", 'C', ModItems.circuit_targeting_tier2, 'P', "plateSteel", 'T', ModBlocks.det_cord, 'L', ModItems.pellet_buckshot });
		addShapedRecipe(new ItemStack(ModBlocks.mine_fat, 1), new Object[] { "CDN", 'C', ModItems.circuit_targeting_tier2, 'D', ModItems.ducttape, 'N', ModItems.gun_fatman_ammo });
		addShapedOreRecipe(new ItemStack(ModItems.defuser, 1), new Object[] { " PS", "P P", " P ", 'P', "ingotPolymer", 'S', "plateSteel" });
		addShapelessOreRecipe(new ItemStack(ModItems.syringe_taint), new Object[] { ModItems.bottle2_empty, ModItems.syringe_metal_empty, ModItems.ducttape, ModItems.powder_magic, "nuggetSchrabidium", Items.POTIONITEM });
		addShapelessRecipe(new ItemStack(ModItems.loops), new Object[] { ModItems.flame_pony, Items.WHEAT, Items.SUGAR });
		addShapelessRecipe(new ItemStack(ModItems.loop_stew), new Object[] { ModItems.loops, ModItems.can_smart, Items.BOWL });
		addShapedOreRecipe(new ItemStack(ModItems.gun_calamity, 1), new Object[] { " PI", "BBM", " PI", 'P', "plateIron", 'B', ModItems.pipes_steel, 'M', ModItems.mechanism_rifle_1, 'I', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.gun_calamity_dual, 1), new Object[] { "BBM", " PI", "BBM", 'P', "plateIron", 'B', ModItems.pipes_steel, 'M', ModItems.mechanism_rifle_1, 'I', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.custom_fall, 1), new Object[] { "IIP", "CHW", "IIP", 'I', ModItems.plate_polymer, 'P', "plateSaturnite", 'C', ModItems.circuit_red_copper, 'H', ModItems.hull_small_steel, 'W', ModItems.coil_copper });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_controller, 1), new Object[] { "PGP", "ICI", "PRP", 'P', ModItems.plate_desh, 'G', "paneGlass", 'I', "ingotPolymer", 'R', "blockRedstone", 'C', ModItems.circuit_targeting_tier4 });
		addShapedOreRecipe(new ItemStack(ModItems.gun_minigun, 1), new Object[] { "PIB", "PCM", "PIB", 'P', ModItems.pipes_steel, 'B', "blockSteel", 'I', "ingotPolymer", 'C', ModItems.mechanism_rifle_2, 'M', ModItems.motor });
		addShapedOreRecipe(new ItemStack(ModItems.gun_avenger, 1), new Object[] { "PIB", "PCM", "PIB", 'P', ModItems.pipes_steel, 'B', "blockBeryllium", 'I', "ingotDesh", 'C', ModItems.mechanism_rifle_2, 'M', ModItems.motor });
		addShapedRecipe(new ItemStack(ModItems.gun_lacunae, 1), new Object[] { "TIT", "ILI", "PRP", 'T', ModItems.syringe_taint, 'I', ModItems.ingot_starmetal, 'L', ModItems.gun_minigun, 'P', ModItems.pellet_rtg, 'R', ModBlocks.machine_rtg_grey });
		addShapedOreRecipe(new ItemStack(ModItems.containment_box, 1), new Object[] { "LLL", "LCL", "LLL", 'L', "plateLead", 'C', Blocks.CHEST });

		addShapedOreRecipe(new ItemStack(ModBlocks.absorber, 1), new Object[] { "ICI", "CPC", "ICI", 'I', "ingotCopper", 'C', "dustCoal", 'P', "dustLead" });
		addShapedOreRecipe(new ItemStack(ModBlocks.absorber_red, 1), new Object[] { "ICI", "CPC", "ICI", 'I', "ingotTitanium", 'C', "dustCoal", 'P', ModBlocks.absorber });
		addShapedOreRecipe(new ItemStack(ModBlocks.absorber_green, 1), new Object[] { "ICI", "CPC", "ICI", 'I', "ingotPolymer", 'C', ModItems.powder_desh_mix, 'P', ModBlocks.absorber_red });
		addShapedRecipe(new ItemStack(ModBlocks.absorber_pink, 1), new Object[] { "ICI", "CPC", "ICI", 'I', ModItems.ingot_saturnite, 'C', ModItems.powder_nitan_mix, 'P', ModBlocks.absorber_green });
		addShapedOreRecipe(new ItemStack(ModBlocks.decon, 1), new Object[] { "BGB", "SAS", "BSB", 'B', "ingotBeryllium", 'G', Blocks.IRON_BARS, 'S', "ingotSteel", 'A', ModBlocks.absorber });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_amgen, 1), new Object[] { "ITI", "TAT", "ITI", 'I', "ingotAdvanced", 'T', ModItems.thermo_element, 'A', ModBlocks.absorber });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_geo, 1), new Object[] { "ITI", "PCP", "ITI", 'I', "ingotDuraSteel", 'T', ModItems.thermo_element, 'P', ModItems.board_copper, 'C', ModBlocks.red_wire_coated });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_minirtg, 1), new Object[] { "LLL", "PPP", "TRT", 'L', "plateLead", 'P', "Pu238", 'T', ModItems.thermo_element, 'R', ModItems.rtg_unit });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_powerrtg, 1), new Object[] { "SRS", "PTP", "SRS", 'S', ModItems.ingot_starmetal, 'R', ModItems.rtg_unit, 'P', "ingotPolonium", 'T', ModItems.powder_tennessine });

		addShapedRecipe(new ItemStack(ModBlocks.pink_planks, 4), new Object[] { "W", 'W', ModBlocks.pink_log });
		addShapedRecipe(new ItemStack(ModBlocks.pink_slab, 6), new Object[] { "WWW", 'W', ModBlocks.pink_planks });
		addShapedRecipe(new ItemStack(ModBlocks.pink_stairs, 6), new Object[] { "W  ", "WW ", "WWW", 'W', ModBlocks.pink_planks });

		addShapedOreRecipe(new ItemStack(ModItems.door_metal, 1), new Object[] { "II", "SS", "II", 'I', "plateIron", 'S', "plateSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.door_office, 1), new Object[] { "II", "SS", "II", 'I', "plankWood", 'S', "plateIron" });
		addShapedOreRecipe(new ItemStack(ModItems.door_bunker, 1), new Object[] { "II", "SS", "II", 'I', "plateSteel", 'S', "plateLead" });

		addShapelessRecipe(new ItemStack(Items.PAPER, 1), new Object[] { new ItemStack(ModItems.assembly_template, 1, OreDictionary.WILDCARD_VALUE) });
		addShapelessRecipe(new ItemStack(Items.PAPER, 1), new Object[] { new ItemStack(ModItems.chemistry_template, 1, OreDictionary.WILDCARD_VALUE) });

		for(Entry<String, Fluid> entry : FluidRegistry.getRegisteredFluids().entrySet()) {
			Fluid fluid = entry.getValue();
			addShapelessRecipe(ItemFFFluidDuct.getStackFromFluid(fluid), new Object[] { new ItemStack(ModBlocks.fluid_duct_mk2, 1), new IngredientContainsTag(ItemForgeFluidIdentifier.getStackFromFluid(entry.getValue())) });

			addShapelessRecipe(ItemFFFluidDuct.getStackFromFluid(fluid, 8), new Object[] { new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new IngredientContainsTag(ItemForgeFluidIdentifier.getStackFromFluid(entry.getValue())) });

			addShapelessRecipe(ItemFFFluidDuct.getStackFromFluid(fluid), new Object[] { new ItemStack(ModItems.ff_fluid_duct, 1, OreDictionary.WILDCARD_VALUE), new IngredientContainsTag(ItemForgeFluidIdentifier.getStackFromFluid(entry.getValue())) });

			addShapelessRecipe(ItemFFFluidDuct.getStackFromFluid(fluid, 8), new Object[] { new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new IngredientContainsTag(ItemForgeFluidIdentifier.getStackFromFluid(entry.getValue())) });
		}

		addShapelessRecipe(new ItemStack(ModBlocks.fluid_duct_mk2, 1), new Object[] { new ItemStack(ModItems.ff_fluid_duct, 1, OreDictionary.WILDCARD_VALUE) });

		addShapelessRecipe(new ItemStack(ModItems.redstone_depleted, 1), new Object[] { new ItemStack(ModItems.battery_su, 1, OreDictionary.WILDCARD_VALUE) });
		addShapelessRecipe(new ItemStack(ModItems.redstone_depleted, 2), new Object[] { new ItemStack(ModItems.battery_su_l, 1, OreDictionary.WILDCARD_VALUE) });
		addShapelessRecipe(new ItemStack(Items.REDSTONE, 1), new Object[] { ModItems.redstone_depleted, ModItems.redstone_depleted });

		addShapelessRecipe(new ItemStack(ModItems.bobmazon_materials), new Object[] { Items.BOOK, Items.GOLD_NUGGET, Items.STRING });
		addShapelessRecipe(new ItemStack(ModItems.bobmazon_machines), new Object[] { Items.BOOK, Items.GOLD_NUGGET, new ItemStack(Items.DYE, 1, 1) });
		addShapelessRecipe(new ItemStack(ModItems.bobmazon_weapons), new Object[] { Items.BOOK, Items.GOLD_NUGGET, new ItemStack(Items.DYE, 1, 8) });
		addShapelessRecipe(new ItemStack(ModItems.bobmazon_tools), new Object[] { Items.BOOK, Items.GOLD_NUGGET, new ItemStack(Items.DYE, 1, 2) });

		addShapedRecipe(new ItemStack(Blocks.TORCH, 3), new Object[] { "L", "S", 'L', ModItems.lignite, 'S', Items.STICK });
		addShapedRecipe(new ItemStack(Blocks.TORCH, 6), new Object[] { "L", "S", 'L', ModItems.briquette_lignite, 'S', Items.STICK });
		addShapedOreRecipe(new ItemStack(Blocks.TORCH, 8), new Object[] { "L", "S", 'L', "fuelCoke", 'S', Items.STICK });

		addShapedOreRecipe(new ItemStack(ModBlocks.machine_missile_assembly, 1), new Object[] { "PWP", "SSS", "CCC", 'P', ModItems.pedestal_steel, 'W', ModItems.wrench, 'S', "plateSteel", 'C', ModBlocks.steel_scaffold });
		addShapedOreRecipe(new ItemStack(ModBlocks.struct_launcher, 1), new Object[] { "PPP", "SDS", "CCC", 'P', "plateSteel", 'S', ModBlocks.steel_scaffold, 'D', ModItems.pipes_steel, 'C', ModBlocks.concrete_smooth });
		addShapedOreRecipe(new ItemStack(ModBlocks.struct_launcher, 1), new Object[] { "PPP", "SDS", "CCC", 'P', "plateSteel", 'S', ModBlocks.steel_scaffold, 'D', ModItems.pipes_steel, 'C', ModBlocks.concrete });
		addShapedRecipe(new ItemStack(ModBlocks.struct_scaffold, 1), new Object[] { "SSS", "DCD", "SSS", 'S', ModBlocks.steel_scaffold, 'D', ModBlocks.fluid_duct_mk2, 'C', ModBlocks.red_cable });

		addShapedOreRecipe(new ItemStack(ModItems.mp_stability_10_flat, 1), new Object[] { "PSP", "P P", 'P', "plateSteel", 'S', ModBlocks.steel_scaffold });
		addShapedOreRecipe(new ItemStack(ModItems.mp_stability_10_cruise, 1), new Object[] { "ASA", " S ", "PSP", 'A', "plateTitanium", 'P', "plateSteel", 'S', ModBlocks.steel_scaffold });
		addShapedOreRecipe(new ItemStack(ModItems.mp_stability_10_space, 1), new Object[] { "ASA", "PSP", 'A', "plateAluminum", 'P', "ingotSteel", 'S', ModBlocks.steel_scaffold });
		addShapedOreRecipe(new ItemStack(ModItems.mp_stability_15_flat, 1), new Object[] { "ASA", "PSP", 'A', "plateAluminum", 'P', "plateSteel", 'S', ModBlocks.steel_scaffold });
		addShapedOreRecipe(new ItemStack(ModItems.mp_stability_15_thin, 1), new Object[] { "A A", "PSP", "PSP", 'A', "plateAluminum", 'P', "plateSteel", 'S', ModBlocks.steel_scaffold });

		addShapedRecipe(new ItemStack(ModItems.mp_thruster_15_balefire_large_rad, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ModItems.board_copper, 'T', ModItems.mp_thruster_15_balefire_large });
		addShapedRecipe(new ItemStack(ModItems.mp_fuselage_10_kerosene_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ModItems.plate_polymer, 'T', ModItems.mp_fuselage_10_kerosene });
		addShapedRecipe(new ItemStack(ModItems.mp_fuselage_10_long_kerosene_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ModItems.plate_polymer, 'T', ModItems.mp_fuselage_10_long_kerosene });
		addShapedRecipe(new ItemStack(ModItems.mp_fuselage_15_kerosene_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ModItems.plate_polymer, 'T', ModItems.mp_fuselage_15_kerosene });
		addShapedRecipe(new ItemStack(ModItems.mp_fuselage_10_solid_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ModItems.plate_polymer, 'T', ModItems.mp_fuselage_10_solid });
		addShapedRecipe(new ItemStack(ModItems.mp_fuselage_10_long_solid_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ModItems.plate_polymer, 'T', ModItems.mp_fuselage_10_long_solid });
		addShapedRecipe(new ItemStack(ModItems.mp_fuselage_15_solid_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ModItems.plate_polymer, 'T', ModItems.mp_fuselage_15_solid });
		addShapedRecipe(new ItemStack(ModItems.mp_fuselage_15_solid_desh, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ModItems.ingot_desh, 'T', ModItems.mp_fuselage_15_solid });
		addShapedOreRecipe(new ItemStack(ModItems.mp_fuselage_10_kerosene_metal, 1), new Object[] { "ICI", "CTC", "ICI", 'C', "plateSteel", 'I', "plateIron", 'T', ModItems.mp_fuselage_10_kerosene });
		addShapedOreRecipe(new ItemStack(ModItems.mp_fuselage_10_long_kerosene_metal, 1), new Object[] { "ICI", "CTC", "ICI", 'C', "plateSteel", 'I', "plateIron", 'T', ModItems.mp_fuselage_10_long_kerosene });
		addShapedOreRecipe(new ItemStack(ModItems.mp_fuselage_15_kerosene_metal, 1), new Object[] { "ICI", "CTC", "ICI", 'C', "plateSteel", 'I', "plateIron", 'T', ModItems.mp_fuselage_15_kerosene });

		addShapedRecipe(new ItemStack(ModItems.mp_warhead_15_boxcar, 1), new Object[] { "SNS", "CBC", "SFS", 'S', ModItems.ingot_starmetal, 'N', ModBlocks.det_nuke, 'C', ModItems.circuit_targeting_tier4, 'B', ModBlocks.boxcar, 'F', ModItems.tritium_deuterium_cake });

		addShapedRecipe(new ItemStack(ModItems.mp_chip_1, 1), new Object[] { "P", "C", "S", 'P', ModItems.plate_polymer, 'C', ModItems.circuit_targeting_tier1, 'S', ModBlocks.steel_scaffold });
		addShapedRecipe(new ItemStack(ModItems.mp_chip_2, 1), new Object[] { "P", "C", "S", 'P', ModItems.plate_polymer, 'C', ModItems.circuit_targeting_tier2, 'S', ModBlocks.steel_scaffold });
		addShapedRecipe(new ItemStack(ModItems.mp_chip_3, 1), new Object[] { "P", "C", "S", 'P', ModItems.plate_polymer, 'C', ModItems.circuit_targeting_tier3, 'S', ModBlocks.steel_scaffold });
		addShapedRecipe(new ItemStack(ModItems.mp_chip_4, 1), new Object[] { "P", "C", "S", 'P', ModItems.plate_polymer, 'C', ModItems.circuit_targeting_tier4, 'S', ModBlocks.steel_scaffold });
		addShapedRecipe(new ItemStack(ModItems.mp_chip_5, 1), new Object[] { "P", "C", "S", 'P', ModItems.plate_polymer, 'C', ModItems.circuit_targeting_tier5, 'S', ModBlocks.steel_scaffold });

		addShapedOreRecipe(new ItemStack(ModItems.seg_10, 1), new Object[] { "P", "S", "B", 'P', "plateAluminum", 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.steel_beam });
		addShapedOreRecipe(new ItemStack(ModItems.seg_15, 1), new Object[] { "PP", "SS", "BB", 'P', "plateTitanium", 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.steel_beam });
		addShapedOreRecipe(new ItemStack(ModItems.seg_20, 1), new Object[] { "PGP", "SSS", "BBB", 'P', "plateSteel", 'G', "plateGold", 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.steel_beam });

		addShapedRecipe(new ItemStack(ModBlocks.struct_launcher_core, 1), new Object[] { "SCS", "SIS", "BEB", 'S', ModBlocks.steel_scaffold, 'I', Blocks.IRON_BARS, 'C', ModItems.circuit_targeting_tier3, 'B', ModBlocks.struct_launcher, 'E', ModBlocks.machine_battery });
		addShapedRecipe(new ItemStack(ModBlocks.struct_launcher_core_large, 1), new Object[] { "SIS", "ICI", "BEB", 'S', ModItems.circuit_red_copper, 'I', Blocks.IRON_BARS, 'C', ModItems.circuit_targeting_tier4, 'B', ModBlocks.struct_launcher, 'E', ModBlocks.machine_battery });
		addShapedRecipe(new ItemStack(ModBlocks.struct_soyuz_core, 1), new Object[] { "CUC", "TST", "TBT", 'C', ModItems.circuit_targeting_tier4, 'U', ModItems.upgrade_power_3, 'T', ModBlocks.barrel_steel, 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.machine_lithium_battery });

		//addShapedOreRecipe(new ItemStack(ModBlocks.obj_tester, 1), new Object[] { "P", "I", "S", 'P', ModItems.polaroid, 'I', ModItems.flame_pony, 'S', "plateSteel" });

		addShapedRecipe(new ItemStack(ModBlocks.fence_metal, 6), new Object[] { "BIB", "BIB", 'B', Blocks.IRON_BARS, 'I', Items.IRON_INGOT });

		addShapelessRecipe(new ItemStack(ModBlocks.waste_trinitite), new Object[] { new ItemStack(Blocks.SAND, 1, 0), ModItems.trinitite });
		addShapelessRecipe(new ItemStack(ModBlocks.waste_trinitite_red), new Object[] { new ItemStack(Blocks.SAND, 1, 1), ModItems.trinitite });
		addShapelessOreRecipe(new ItemStack(ModBlocks.sand_uranium), new Object[] { "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", "dustUranium" });
		addShapelessOreRecipe(new ItemStack(ModBlocks.sand_polonium), new Object[] { "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", "dustPolonium" });
		addShapelessOreRecipe(new ItemStack(ModBlocks.sand_boron, 8), new Object[] { "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", "dustBoron" });
		addShapelessOreRecipe(new ItemStack(ModBlocks.sand_lead, 8), new Object[] { "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", "dustLead" });
		addShapelessOreRecipe(new ItemStack(ModBlocks.sand_quartz, 1), new Object[] { "sand", "sand", "dustNetherQuartz", "dustNetherQuartz" });

		addShapedRecipe(new ItemStack(ModItems.rune_blank, 1), new Object[] { "PSP", "SDS", "PSP", 'P', ModItems.powder_magic, 'S', ModItems.ingot_starmetal, 'D', ModItems.dynosphere_dineutronium_charged });
		addShapelessRecipe(new ItemStack(ModItems.rune_isa, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity_counter_resonant });
		addShapelessRecipe(new ItemStack(ModItems.rune_dagaz, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity });
		addShapelessRecipe(new ItemStack(ModItems.rune_hagalaz, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity_super_heated });
		addShapelessRecipe(new ItemStack(ModItems.rune_jera, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity_spark });
		addShapelessRecipe(new ItemStack(ModItems.rune_thurisaz, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.black_hole });
		addShapedRecipe(new ItemStack(ModItems.ams_lens, 1), new Object[] { "PDP", "GDG", "PDP", 'P', ModItems.plate_dineutronium, 'G', ModBlocks.reinforced_glass, 'D', Blocks.DIAMOND_BLOCK });
		addShapedRecipe(new ItemStack(ModItems.ams_catalyst_blank, 1), new Object[] { "TET", "ETE", "TET", 'T', ModItems.powder_tennessine, 'E', ModItems.ingot_euphemium });
		addShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_lithium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_jera, ModItems.rune_jera, "dustLithium", "dustLithium", "dustLithium", "dustLithium" });
		addShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_beryllium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_dagaz, ModItems.rune_jera, ModItems.rune_jera, "dustBeryllium", "dustBeryllium", "dustBeryllium", "dustBeryllium" });
		addShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_copper, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_jera, ModItems.rune_jera, "dustCopper", "dustCopper", "dustCopper", "dustCopper" });
		addShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_cobalt, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_jera, "dustCobalt", "dustCobalt", "dustCobalt", "dustCobalt" });
		addShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_tungsten, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_jera, "dustTungsten", "dustTungsten", "dustTungsten", "dustTungsten" });
		addShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_aluminium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_jera, ModItems.rune_thurisaz, "dustAluminum", "dustAluminum", "dustAluminum", "dustAluminum" });
		addShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_iron, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_dagaz, ModItems.rune_jera, ModItems.rune_thurisaz, "dustIron", "dustIron", "dustIron", "dustIron" });
		addShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_strontium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_jera, ModItems.rune_thurisaz, "dustStrontium", "dustStrontium", "dustStrontium", "dustStrontium" });
		addShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_niobium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_thurisaz, "dustNiobium", "dustNiobium", "dustNiobium", "dustNiobium" });
		addShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_cerium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_thurisaz, "dustCerium", "dustCerium", "dustCerium", "dustCerium" });
		addShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_caesium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_thurisaz, ModItems.rune_thurisaz, "dustCaesium", "dustCaesium", "dustCaesium", "dustCaesium" });
		addShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_thorium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_dagaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, "dustThorium", "dustThorium", "dustThorium", "dustThorium" });
		addShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_euphemium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, "dustEuphemium", "dustEuphemium", "dustEuphemium", "dustEuphemium" });
		addShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_schrabidium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_hagalaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, "dustSchrabidium", "dustSchrabidium", "dustSchrabidium", "dustSchrabidium" });
		addShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_dineutronium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, "dustDineutronium", "dustDineutronium", "dustDineutronium", "dustDineutronium" });
		addShapedOreRecipe(new ItemStack(ModBlocks.dfc_core, 1), new Object[] { "DLD", "LML", "DLD", 'D', ModItems.plate_dineutronium, 'L', "blockLead", 'M', ModBlocks.block_meteor });
		addShapedRecipe(new ItemStack(ModBlocks.dfc_emitter, 1), new Object[] { "SDS", "TXL", "SDS", 'S', ModItems.ingot_starmetal, 'D', ModItems.plate_desh, 'T', ModBlocks.machine_transformer_dnt, 'X', ModItems.crystal_xen, 'L', ModItems.sat_head_laser });
		addShapedRecipe(new ItemStack(ModBlocks.dfc_receiver, 1), new Object[] { "SDS", "TXL", "SDS", 'S', ModItems.ingot_starmetal, 'D', ModItems.plate_desh, 'T', ModBlocks.machine_transformer_dnt, 'X', ModBlocks.sellafield_core, 'L', ModItems.hull_small_steel });
		addShapedOreRecipe(new ItemStack(ModBlocks.dfc_injector, 1), new Object[] { "SDS", "TXL", "SDS", 'S', ModItems.ingot_starmetal, 'D', ModItems.plate_combine_steel, 'T', ModBlocks.machine_fluidtank, 'X', ModItems.motor, 'L', ModItems.pipes_steel });
		addShapedRecipe(new ItemStack(ModBlocks.dfc_stabilizer, 1), new Object[] { "SDS", "TXL", "SDS", 'S', ModItems.ingot_starmetal, 'D', ModItems.plate_desh, 'T', ModItems.singularity_spark, 'X', ModItems.magnet_circular, 'L', ModItems.crystal_xen });
		addShapedOreRecipe(new ItemStack(ModBlocks.barrel_plastic, 1), new Object[] { "IPI", "I I", "IPI", 'I', ModItems.plate_polymer, 'P', "plateAluminum" });
		addShapedOreRecipe(new ItemStack(ModBlocks.barrel_iron, 1), new Object[] { "IPI", "I I", "IPI", 'I', "plateIron", 'P', "ingotIron" });
		addShapedOreRecipe(new ItemStack(ModBlocks.barrel_steel, 1), new Object[] { "IPI", "I I", "IPI", 'I', "plateSteel", 'P', "ingotSteel" });
		addShapedRecipe(new ItemStack(ModBlocks.barrel_antimatter, 1), new Object[] { "IPI", "IPI", "IPI", 'I', ModItems.plate_saturnite, 'P', ModItems.coil_advanced_torus });
		addShapedOreRecipe(new ItemStack(ModBlocks.tesla, 1), new Object[] { "CCC", "PIP", "WTW", 'C', ModItems.coil_copper, 'I', "ingotIron", 'P', "ingotPolymer", 'T', ModBlocks.machine_transformer, 'W', "plankWood" });

		addShapedRecipe(new ItemStack(ModItems.bottle_mercury, 1), new Object[] { "###", "#B#", "###", '#', ModItems.nugget_mercury, 'B', Items.GLASS_BOTTLE });
		addShapedRecipe(new ItemStack(ModItems.nugget_mercury, 8), new Object[] { "#", '#', ModItems.bottle_mercury });
		addShapedRecipe(new ItemStack(ModItems.egg_balefire, 1), new Object[] { "###", "###", "###", '#', ModItems.egg_balefire_shard });
		addShapedRecipe(new ItemStack(ModItems.egg_balefire_shard, 9), new Object[] { "#", '#', ModItems.egg_balefire });
		addShapedRecipe(new ItemStack(ModItems.ammo_44_phosphorus, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_44, 'A', ModItems.ingot_phosphorus });
		addShapedRecipe(new ItemStack(ModItems.ammo_50bmg_phosphorus, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_50bmg, 'A', ModItems.ingot_phosphorus });
		addShapedRecipe(new ItemStack(ModItems.ammo_rocket_phosphorus, 1), new Object[] { "G", "R", 'G', ModItems.ingot_phosphorus, 'R', ModItems.ammo_rocket });
		addShapedRecipe(new ItemStack(ModItems.ammo_grenade_phosphorus, 2), new Object[] { "GIG", 'G', ModItems.ammo_grenade, 'I', ModItems.ingot_phosphorus });

		addShapedRecipe(new ItemStack(ModBlocks.block_u233, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_u233 });
		addShapedRecipe(new ItemStack(ModBlocks.block_u235, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_u235 });
		addShapedRecipe(new ItemStack(ModBlocks.block_u238, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_u238 });
		addShapedRecipe(new ItemStack(ModBlocks.block_uranium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_uranium_fuel });
		addShapedRecipe(new ItemStack(ModBlocks.block_neptunium, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_neptunium });
		addShapedRecipe(new ItemStack(ModBlocks.block_polonium, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_polonium });
		addShapedRecipe(new ItemStack(ModBlocks.block_plutonium, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_plutonium });
		addShapedRecipe(new ItemStack(ModBlocks.block_pu238, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_pu238 });
		addShapedRecipe(new ItemStack(ModBlocks.block_pu239, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_pu239 });
		addShapedRecipe(new ItemStack(ModBlocks.block_pu240, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_pu240 });
		addShapedRecipe(new ItemStack(ModBlocks.block_plutonium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_plutonium_fuel });
		addShapedRecipe(new ItemStack(ModBlocks.block_thorium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_thorium_fuel });
		addShapedRecipe(new ItemStack(ModBlocks.block_solinium, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_solinium });
		addShapedRecipe(new ItemStack(ModBlocks.block_schrabidium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_schrabidium_fuel });
		addShapedRecipe(new ItemStack(ModItems.ingot_u233, 9), new Object[] { "#", '#', ModBlocks.block_u233 });
		addShapedRecipe(new ItemStack(ModItems.ingot_u235, 9), new Object[] { "#", '#', ModBlocks.block_u235 });
		addShapedRecipe(new ItemStack(ModItems.ingot_u238, 9), new Object[] { "#", '#', ModBlocks.block_u238 });
		addShapedRecipe(new ItemStack(ModItems.ingot_uranium_fuel, 9), new Object[] { "#", '#', ModBlocks.block_uranium_fuel });
		addShapedRecipe(new ItemStack(ModItems.ingot_neptunium, 9), new Object[] { "#", '#', ModBlocks.block_neptunium });
		addShapedRecipe(new ItemStack(ModItems.ingot_polonium, 9), new Object[] { "#", '#', ModBlocks.block_polonium });
		addShapedRecipe(new ItemStack(ModItems.ingot_plutonium, 9), new Object[] { "#", '#', ModBlocks.block_plutonium });
		addShapedRecipe(new ItemStack(ModItems.ingot_pu238, 9), new Object[] { "#", '#', ModBlocks.block_pu238 });
		addShapedRecipe(new ItemStack(ModItems.ingot_pu239, 9), new Object[] { "#", '#', ModBlocks.block_pu239 });
		addShapedRecipe(new ItemStack(ModItems.ingot_pu240, 9), new Object[] { "#", '#', ModBlocks.block_pu240 });
		addShapedRecipe(new ItemStack(ModItems.ingot_plutonium_fuel, 9), new Object[] { "#", '#', ModBlocks.block_plutonium_fuel });
		addShapedRecipe(new ItemStack(ModItems.ingot_thorium_fuel, 9), new Object[] { "#", '#', ModBlocks.block_thorium_fuel });
		addShapedRecipe(new ItemStack(ModItems.ingot_solinium, 9), new Object[] { "#", '#', ModBlocks.block_solinium });
		addShapedRecipe(new ItemStack(ModItems.ingot_schrabidium_fuel, 9), new Object[] { "#", '#', ModBlocks.block_schrabidium_fuel });
		addShapedRecipe(new ItemStack(ModItems.ammo_4gauge, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_buckshot, 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer });
		addShapedRecipe(new ItemStack(ModItems.ammo_4gauge, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_buckshot, 'G', ModItems.ballistite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_4gauge_slug, 12), new Object[] { " I ", "GCL", " P ", 'I', "ingotLead", 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_4gauge_slug, 12), new Object[] { " I ", "GCL", " P ", 'I', "ingotLead", 'G', ModItems.ballistite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer });
		addShapedRecipe(new ItemStack(ModItems.ammo_4gauge_flechette, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_flechette, 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer });
		addShapedRecipe(new ItemStack(ModItems.ammo_4gauge_flechette, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_flechette, 'G', ModItems.ballistite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer });
		addShapedRecipe(new ItemStack(ModItems.ammo_4gauge_explosive, 4), new Object[] { " I ", "GCL", " P ", 'I', Blocks.TNT, 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer });
		addShapedRecipe(new ItemStack(ModItems.ammo_4gauge_explosive, 4), new Object[] { " I ", "GCL", " P ", 'I', Blocks.TNT, 'G', ModItems.ballistite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer });
		addShapedRecipe(new ItemStack(ModItems.ammo_4gauge_explosive, 6), new Object[] { " I ", "GCL", " P ", 'I', ModItems.ingot_semtex, 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer });
		addShapedRecipe(new ItemStack(ModItems.ammo_4gauge_explosive, 6), new Object[] { " I ", "GCL", " P ", 'I', ModItems.ingot_semtex, 'G', ModItems.ballistite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer });
		addShapedRecipe(new ItemStack(ModItems.ammo_4gauge_semtex, 4), new Object[] { " I ", "GCL", " P ", 'I', ModBlocks.det_miner, 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer });
		addShapedRecipe(new ItemStack(ModItems.ammo_4gauge_semtex, 4), new Object[] { " I ", "GCL", " P ", 'I', ModBlocks.det_miner, 'G', ModItems.ballistite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer });

		addShapedRecipe(new ItemStack(ModBlocks.block_mox_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_mox_fuel });
		addShapedRecipe(new ItemStack(ModBlocks.block_lithium, 1), new Object[] { "###", "###", "###", '#', ModItems.lithium });
		addShapedRecipe(new ItemStack(ModBlocks.block_white_phosphorus, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_phosphorus });
		addShapedRecipe(new ItemStack(ModBlocks.block_red_phosphorus, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_fire });
		addShapedRecipe(new ItemStack(ModBlocks.block_insulator, 1), new Object[] { "###", "###", "###", '#', ModItems.plate_polymer });
		addShapedRecipe(new ItemStack(ModBlocks.block_asbestos, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_asbestos });
		addShapedRecipe(new ItemStack(ModBlocks.block_fiberglass, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_fiberglass });
		addShapedRecipe(new ItemStack(ModBlocks.block_cobalt, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_cobalt });

		addShapedRecipe(new ItemStack(ModItems.ingot_aluminium, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_aluminium });
		addShapedRecipe(new ItemStack(ModItems.ingot_copper, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_copper });
		addShapedRecipe(new ItemStack(ModItems.ingot_tungsten, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_tungsten });
		addShapedRecipe(new ItemStack(ModItems.ingot_red_copper, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_red_copper });
		addShapedRecipe(new ItemStack(ModItems.ingot_advanced_alloy, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_advanced_alloy });
		addShapedRecipe(new ItemStack(Items.GOLD_INGOT, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_gold });
		addShapedRecipe(new ItemStack(ModItems.ingot_schrabidium, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_schrabidium });
		addShapedRecipe(new ItemStack(ModItems.ingot_magnetized_tungsten, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_magnetized_tungsten });

		addShapedRecipe(new ItemStack(ModItems.ingot_mox_fuel, 9), new Object[] { "#", '#', ModBlocks.block_mox_fuel });
		addShapedRecipe(new ItemStack(ModItems.lithium, 9), new Object[] { "#", '#', ModBlocks.block_lithium });
		addShapedRecipe(new ItemStack(ModItems.ingot_phosphorus, 9), new Object[] { "#", '#', ModBlocks.block_white_phosphorus });
		addShapedRecipe(new ItemStack(ModItems.powder_fire, 9), new Object[] { "#", '#', ModBlocks.block_red_phosphorus });
		addShapedRecipe(new ItemStack(ModItems.plate_polymer, 9), new Object[] { "#", '#', ModBlocks.block_insulator });
		addShapedRecipe(new ItemStack(ModItems.plate_polymer, 16), new Object[] { "DD", 'D', ModItems.ingot_fiberglass });
		addShapedRecipe(new ItemStack(ModItems.plate_polymer, 16), new Object[] { "DD", 'D', ModItems.ingot_asbestos });
		addShapedRecipe(new ItemStack(ModItems.ingot_asbestos, 9), new Object[] { "#", '#', ModBlocks.block_asbestos });
		addShapedRecipe(new ItemStack(ModItems.ingot_fiberglass, 9), new Object[] { "#", '#', ModBlocks.block_fiberglass });
		addShapedRecipe(new ItemStack(ModItems.ingot_cobalt, 9), new Object[] { "#", '#', ModBlocks.block_cobalt });
		//addShapelessRecipe(new ItemStack(ModItems.nugget_thorium_fuel, 6), new Object[] { ModItems.rod_thorium_fuel });
		//addShapelessRecipe(new ItemStack(ModItems.nugget_thorium_fuel, 12), new Object[] { ModItems.rod_dual_thorium_fuel });
		//addShapelessRecipe(new ItemStack(ModItems.nugget_thorium_fuel, 24), new Object[] { ModItems.rod_quad_thorium_fuel });
		addShapedRecipe(new ItemStack(ModItems.mechanism_special, 1), new Object[] { "PCI", "ISS", "PCI", 'P', ModItems.plate_desh, 'C', ModItems.coil_advanced_alloy, 'I', ModItems.ingot_starmetal, 'S', ModItems.circuit_targeting_tier3 });
		addShapedRecipe(new ItemStack(ModItems.gun_proto, 1), new Object[] { "LLL", "WFW", "SSS", 'S', ModItems.plate_polymer, 'L', ModItems.plate_desh, 'W', ModItems.wire_tungsten, 'F', ModItems.gun_fatman });
		addShapedOreRecipe(new ItemStack(ModItems.gun_ks23, 1), new Object[] { "PPM", "SWL", 'P', "plateSteel", 'M', ModItems.mechanism_rifle_1, 'S', Items.STICK, 'W', ModItems.wire_tungsten, 'L', "logWood" });
		addShapelessRecipe(new ItemStack(ModItems.gun_sauer, 1), new Object[] { ModItems.ducttape, ModItems.gun_ks23, Blocks.LEVER, ModItems.gun_ks23 });
		addShapedOreRecipe(new ItemStack(ModItems.gun_flamer, 1), new Object[] { "WPP", "SCT", "WMI", 'W', ModItems.wire_gold, 'P', ModItems.pipes_steel, 'S', ModItems.hull_small_steel, 'C', ModItems.coil_tungsten, 'T', ModItems.tank_steel, 'M', ModItems.mechanism_launcher_1, 'I', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_fuel, 1), new Object[] { " P ", "BDB", " P ", 'P', "plateSteel", 'B', ModItems.bolt_tungsten, 'D', new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.diesel)) });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_fuel_napalm, 1), new Object[] { " P ", "BDB", " P ", 'P', "plateSteel", 'B', ModItems.bolt_tungsten, 'D', ModItems.canister_napalm });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_fuel_phosphorus, 1), new Object[] { "CPC", "CDC", "CPC", 'C', "dustCoal", 'P', ModItems.ingot_phosphorus, 'D', ModItems.ammo_fuel });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_fuel_gas, 1), new Object[] { "PDP", "BDB", "PDP", 'P', "plateSteel", 'B', ModItems.bolt_tungsten, 'D', ModItems.pellet_gas });
		addShapedRecipe(new ItemStack(ModItems.ammo_fuel_vaporizer, 1), new Object[] { "PSP", "SNS", "PSP", 'P', ModItems.ingot_phosphorus, 'S', ModItems.crystal_sulfur, 'N', ModItems.ammo_fuel_napalm });
		addShapedOreRecipe(new ItemStack(ModBlocks.det_cord, 8), new Object[] { "TNT", "NGN", "TNT", 'T', "plateSteel", 'N', "dustSaltpeter", 'G', "gunpowder" });
		addShapedOreRecipe(new ItemStack(ModBlocks.det_charge, 1), new Object[] { "PDP", "DTD", "PDP", 'P', "plateSteel", 'D', ModBlocks.det_cord, 'T', ModItems.ingot_semtex });
		addShapedRecipe(new ItemStack(ModBlocks.det_nuke, 1), new Object[] { "PDP", "DCD", "PDP", 'P', ModItems.plate_desh, 'D', ModBlocks.det_charge, 'C', ModItems.man_core });
		addShapedOreRecipe(new ItemStack(ModBlocks.det_miner, 3), new Object[] { "FFF", "ITI", "ITI", 'F', Items.FLINT, 'I', "plateIron", 'T', Blocks.TNT });
		addShapedOreRecipe(new ItemStack(ModBlocks.det_miner, 12), new Object[] { "FFF", "ITI", "ITI", 'F', Items.FLINT, 'I', "plateSteel", 'T', ModItems.ingot_semtex });
		addShapedRecipe(new ItemStack(ModItems.cobalt_helmet, 1), new Object[] { "EEE", "E E", 'E', ModItems.ingot_cobalt });
		addShapedRecipe(new ItemStack(ModItems.cobalt_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.ingot_cobalt });
		addShapedRecipe(new ItemStack(ModItems.cobalt_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.ingot_cobalt });
		addShapedRecipe(new ItemStack(ModItems.cobalt_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.ingot_cobalt });

		addShapedRecipe(new ItemStack(ModItems.t45_helmet, 1), new Object[] { "PPC", "PBP", "IXI", 'P', ModItems.plate_armor_titanium, 'C', ModItems.circuit_targeting_tier3, 'I', ModItems.plate_polymer, 'X', ModItems.gas_mask_m65, 'B', ModItems.titanium_helmet });
		addShapedRecipe(new ItemStack(ModItems.t45_plate, 1), new Object[] { "MPM", "TBT", "PPP", 'M', ModItems.motor, 'P', ModItems.plate_armor_titanium, 'T', ModItems.gas_canister, 'B', ModItems.titanium_plate });
		addShapedRecipe(new ItemStack(ModItems.t45_legs, 1), new Object[] { "MPM", "PBP", "P P", 'M', ModItems.motor, 'P', ModItems.plate_armor_titanium, 'B', ModItems.titanium_legs });
		addShapedRecipe(new ItemStack(ModItems.t45_boots, 1), new Object[] { "P P", "PBP", 'P', ModItems.plate_armor_titanium, 'B', ModItems.titanium_boots });
		addShapedRecipe(new ItemStack(ModItems.bj_helmet, 1), new Object[] { "SBS", " C ", " I ", 'S', Items.STRING, 'B', new ItemStack(Blocks.WOOL, 1, 15), 'C', ModItems.circuit_targeting_tier4, 'I', ModItems.ingot_starmetal });
		addShapedRecipe(new ItemStack(ModItems.bj_plate, 1), new Object[] { "N N", "MSM", "NCN", 'N', ModItems.plate_armor_lunar, 'M', ModItems.motor_desh, 'S', ModItems.starmetal_plate, 'C', ModItems.circuit_targeting_tier5 });
		addShapedRecipe(new ItemStack(ModItems.bj_plate_jetpack, 1), new Object[] { "NFN", "TPT", "ICI", 'N', ModItems.plate_armor_lunar, 'F', ModItems.fins_quad_titanium, 'T', new IngredientContainsTag(ItemFluidTank.getFullTank(ModForgeFluids.xenon)), 'P', ModItems.bj_plate, 'I', ModItems.mp_thruster_10_xenon, 'C', ModItems.crystal_phosphorus });
		addShapedRecipe(new ItemStack(ModItems.bj_legs, 1), new Object[] { "NBN", "MSM", "N N", 'N', ModItems.plate_armor_lunar, 'M', ModItems.motor_desh, 'S', ModItems.starmetal_legs, 'B', ModBlocks.block_starmetal });
		addShapedRecipe(new ItemStack(ModItems.bj_boots, 1), new Object[] { "N N", "BSB", 'N', ModItems.plate_armor_lunar, 'S', ModItems.starmetal_boots, 'B', ModBlocks.block_starmetal });

		reg3();
	}

	public static void reg3(){
		addShapedOreRecipe(new ItemStack(ModBlocks.ladder_sturdy, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', "plankWood" });
		addShapedOreRecipe(new ItemStack(ModBlocks.ladder_iron, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', "ingotIron" });
		addShapedOreRecipe(new ItemStack(ModBlocks.ladder_gold, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', "ingotGold" });
		addShapedOreRecipe(new ItemStack(ModBlocks.ladder_aluminium, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', "ingotAluminum" });
		addShapedOreRecipe(new ItemStack(ModBlocks.ladder_copper, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', "ingotCopper" });
		addShapedOreRecipe(new ItemStack(ModBlocks.ladder_titanium, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', "ingotTitanium" });
		addShapedOreRecipe(new ItemStack(ModBlocks.ladder_lead, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', "ingotLead" });
		addShapedOreRecipe(new ItemStack(ModBlocks.ladder_cobalt, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', "ingotCobalt" });
		addShapedOreRecipe(new ItemStack(ModBlocks.ladder_steel, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModBlocks.ladder_tungsten, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', "ingotTungsten" });

		addShapedRecipe(new ItemStack(ModBlocks.deco_pipe, 6), new Object[] { "PPP", 'P', ModItems.hull_small_steel });
		addShapelessRecipe(new ItemStack(ModBlocks.deco_pipe, 1), new Object[] { ModBlocks.deco_pipe_rim });
		addShapelessRecipe(new ItemStack(ModBlocks.deco_pipe, 1), new Object[] { ModBlocks.deco_pipe_framed });
		addShapelessRecipe(new ItemStack(ModBlocks.deco_pipe, 1), new Object[] { ModBlocks.deco_pipe_quad });

		addShapedOreRecipe(new ItemStack(ModBlocks.deco_pipe_rim, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', "plateSteel" });
		addShapedRecipe(new ItemStack(ModBlocks.deco_pipe_quad, 4), new Object[] { "PP", "PP", 'P', ModBlocks.deco_pipe });
		addShapedRecipe(new ItemStack(ModBlocks.deco_pipe_framed, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', Blocks.IRON_BARS });
		addShapedRecipe(new ItemStack(ModBlocks.deco_pipe_framed, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', Blocks.IRON_BARS });

		addShapedOreRecipe(new ItemStack(ModBlocks.deco_pipe_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', "dustIron" });
		addShapedOreRecipe(new ItemStack(ModBlocks.deco_pipe_rim_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', "dustIron" });
		addShapedOreRecipe(new ItemStack(ModBlocks.deco_pipe_quad_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad, 'C', "dustIron" });
		addShapedOreRecipe(new ItemStack(ModBlocks.deco_pipe_framed_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed, 'C', "dustIron" });
		addShapedOreRecipe(new ItemStack(ModBlocks.deco_pipe_green, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', "dyeGreen" });
		addShapedOreRecipe(new ItemStack(ModBlocks.deco_pipe_rim_green, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', "dyeGreen" });
		addShapedOreRecipe(new ItemStack(ModBlocks.deco_pipe_quad_green, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad, 'C', "dyeGreen" });
		addShapedOreRecipe(new ItemStack(ModBlocks.deco_pipe_framed_green, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed, 'C', "dyeGreen" });
		addShapedOreRecipe(new ItemStack(ModBlocks.deco_pipe_green_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_green, 'C', "dustIron" });
		addShapedOreRecipe(new ItemStack(ModBlocks.deco_pipe_rim_green_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim_green, 'C', "dustIron" });
		addShapedOreRecipe(new ItemStack(ModBlocks.deco_pipe_quad_green_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad_green, 'C', "dustIron" });
		addShapedOreRecipe(new ItemStack(ModBlocks.deco_pipe_framed_green_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed_green, 'C', "dustIron" });
		addShapedOreRecipe(new ItemStack(ModBlocks.deco_pipe_red, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', "dyeRed" });
		addShapedOreRecipe(new ItemStack(ModBlocks.deco_pipe_rim_red, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', "dyeRed" });
		addShapedOreRecipe(new ItemStack(ModBlocks.deco_pipe_quad_red, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad, 'C', "dyeRed" });
		addShapedOreRecipe(new ItemStack(ModBlocks.deco_pipe_framed_red, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed, 'C', "dyeRed" });
		addShapedOreRecipe(new ItemStack(ModBlocks.deco_pipe_marked, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_green, 'C', "dyeGreen" });
		addShapedOreRecipe(new ItemStack(ModBlocks.deco_pipe_rim_marked, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim_green, 'C', "dyeGreen" });
		addShapedOreRecipe(new ItemStack(ModBlocks.deco_pipe_quad_marked, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad_green, 'C', "dyeGreen" });
		addShapedOreRecipe(new ItemStack(ModBlocks.deco_pipe_framed_marked, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed_green, 'C', "dyeGreen" });

		addShapedRecipe(new ItemStack(ModItems.ajr_helmet, 1), new Object[] { "PPC", "PBP", "IXI", 'P', ModItems.plate_armor_ajr, 'C', ModItems.circuit_targeting_tier4, 'I', ModItems.ingot_polymer, 'X', ModItems.gas_mask_m65, 'B', ModItems.alloy_helmet });
		addShapedRecipe(new ItemStack(ModItems.ajr_plate, 1), new Object[] { "MPM", "TBT", "PPP", 'M', ModItems.motor_desh, 'P', ModItems.plate_armor_ajr, 'T', ModItems.gas_canister, 'B', ModItems.alloy_plate });
		addShapedRecipe(new ItemStack(ModItems.ajr_legs, 1), new Object[] { "MPM", "PBP", "P P", 'M', ModItems.motor_desh, 'P', ModItems.plate_armor_ajr, 'B', ModItems.alloy_legs });
		addShapedRecipe(new ItemStack(ModItems.ajr_boots, 1), new Object[] { "P P", "PBP", 'P', ModItems.plate_armor_ajr, 'B', ModItems.alloy_boots });
		addShapelessOreRecipe(new ItemStack(ModItems.ajro_helmet, 1), new Object[] { ModItems.ajr_helmet, "dyeRed", "dyeBlack" });
		addShapelessOreRecipe(new ItemStack(ModItems.ajro_plate, 1), new Object[] { ModItems.ajr_plate, "dyeRed", "dyeBlack" });
		addShapelessOreRecipe(new ItemStack(ModItems.ajro_legs, 1), new Object[] { ModItems.ajr_legs, "dyeRed", "dyeBlack" });
		addShapelessOreRecipe(new ItemStack(ModItems.ajro_boots, 1), new Object[] { ModItems.ajr_boots, "dyeRed", "dyeBlack" });
		addShapedRecipe(new ItemStack(ModItems.fau_helmet, 1), new Object[] { "PWP", "PBP", "FSF", 'P', ModItems.plate_armor_fau, 'W', new ItemStack(Blocks.WOOL, 1, 14), 'B', ModItems.starmetal_helmet, 'F', ModItems.gas_mask_filter, 'S', ModItems.pipes_steel });
		addShapedRecipe(new ItemStack(ModItems.fau_plate, 1), new Object[] { "MCM", "PBP", "PSP", 'M', ModItems.motor_desh, 'C', ModItems.demon_core_closed, 'P', ModItems.plate_armor_fau, 'B', ModItems.starmetal_plate, 'S', ModBlocks.ancient_scrap });
		addShapedRecipe(new ItemStack(ModItems.fau_legs, 1), new Object[] { "MPM", "PBP", "PDP", 'M', ModItems.motor_desh, 'P', ModItems.plate_armor_fau, 'B', ModItems.starmetal_legs, 'D', ModItems.billet_polonium });
		addShapedRecipe(new ItemStack(ModItems.fau_boots, 1), new Object[] { "PDP", "PBP", 'P', ModItems.plate_armor_fau, 'D', ModItems.billet_polonium, 'B', ModItems.starmetal_boots });
		addShapedRecipe(new ItemStack(ModItems.dns_helmet, 1), new Object[] { "PCP", "PBP", "PSP", 'P', ModItems.plate_armor_dnt, 'S', ModItems.ingot_chainsteel, 'B', ModItems.bj_helmet, 'C', ModItems.circuit_targeting_tier6 });
		addShapedRecipe(new ItemStack(ModItems.dns_plate, 1), new Object[] { "PCP", "PBP", "PSP", 'P', ModItems.plate_armor_dnt, 'S', ModItems.ingot_chainsteel, 'B', ModItems.bj_plate_jetpack, 'C', ModItems.singularity_spark });
		addShapedRecipe(new ItemStack(ModItems.dns_legs, 1), new Object[] { "PCP", "PBP", "PSP", 'P', ModItems.plate_armor_dnt, 'S', ModItems.ingot_chainsteel, 'B', ModItems.bj_legs, 'C', ModItems.coin_worm });
		addShapedRecipe(new ItemStack(ModItems.dns_boots, 1), new Object[] { "PCP", "PBP", "PSP", 'P', ModItems.plate_armor_dnt, 'S', ModItems.ingot_chainsteel, 'B', ModItems.bj_boots, 'C', ModItems.demon_core_closed });

		addShapedOreRecipe(new ItemStack(ModItems.ammo_shell, 4), new Object[] { " T ", "GHG", "CCC", 'T', Blocks.TNT, 'G', Items.GUNPOWDER, 'H', ModItems.hull_small_steel, 'C', "ingotCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_shell, 4), new Object[] { " T ", "GHG", "CCC", 'T', Blocks.TNT, 'G', ModItems.ballistite, 'H', ModItems.hull_small_steel, 'C', "ingotCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_shell, 6), new Object[] { " T ", "GHG", "CCC", 'T', Blocks.TNT, 'G', ModItems.cordite, 'H', ModItems.hull_small_steel, 'C', "ingotCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_shell_explosive, 4), new Object[] { " T ", "GHG", "CCC", 'T', ModItems.ingot_semtex, 'G', Items.GUNPOWDER, 'H', ModItems.hull_small_steel, 'C', "ingotCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_shell_explosive, 4), new Object[] { " T ", "GHG", "CCC", 'T', ModItems.ingot_semtex, 'G', ModItems.ballistite, 'H', ModItems.hull_small_steel, 'C', "ingotCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_shell_explosive, 6), new Object[] { " T ", "GHG", "CCC", 'T', ModItems.ingot_semtex, 'G', ModItems.cordite, 'H', ModItems.hull_small_steel, 'C', "ingotCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_shell_apfsds_t, 4), new Object[] { " I ", "GIG", "CCC", 'I', "ingotTungsten", 'G', Items.GUNPOWDER, 'C', "ingotCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_shell_apfsds_t, 4), new Object[] { " I ", "GIG", "CCC", 'I', "ingotTungsten", 'G', ModItems.ballistite, 'C', "ingotCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_shell_apfsds_t, 6), new Object[] { " I ", "GIG", "CCC", 'I', "ingotTungsten", 'G', ModItems.cordite, 'C', "ingotCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_shell_apfsds_du, 4), new Object[] { " I ", "GIG", "CCC", 'I', "ingotUranium238", 'G', Items.GUNPOWDER, 'C', "ingotCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_shell_apfsds_du, 4), new Object[] { " I ", "GIG", "CCC", 'I', "ingotUranium238", 'G', ModItems.ballistite, 'C', "ingotCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_shell_apfsds_du, 6), new Object[] { " I ", "GIG", "CCC", 'I', "ingotUranium238", 'G', ModItems.cordite, 'C', "ingotCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_shell_w9, 1), new Object[] { " P ", "NSN", " P ", 'P', "nuggetPlutonium239", 'N', OreDictManager.getReflector(), 'S', ModItems.ammo_shell_explosive });

		addShapelessRecipe(new ItemStack(ModItems.ingot_smore), new Object[] { Items.WHEAT, new ItemStack(ModItems.marshmallow, 1, 1), new ItemStack(Items.DYE, 1, 3) });
		addShapelessRecipe(new ItemStack(ModItems.marshmallow), new Object[] { Items.STICK, Items.SUGAR, Items.WHEAT_SEEDS });

		addShapedRecipe(new ItemStack(ModItems.coltass, 1), new Object[] { "ACA", "CXC", "ACA", 'A', ModItems.ingot_advanced_alloy, 'C', ModItems.cinnebar, 'X', Items.COMPASS });
		addShapedRecipe(new ItemStack(ModItems.bismuth_tool, 1), new Object[] { "TBT", "SRS", "SCS", 'T', ModItems.nugget_tantalium, 'B', ModItems.nugget_bismuth, 'S', ModItems.ingot_tcalloy, 'R', ModItems.reacher, 'C', ModItems.circuit_aluminium });
		addShapedOreRecipe(new ItemStack(ModItems.reacher, 1), new Object[] { "BIB", "P P", "B B", 'B', ModItems.bolt_tungsten, 'I', "ingotTungsten", 'P', ModItems.plate_polymer });
		addShapelessOreRecipe(new ItemStack(ModItems.powder_tcalloy, 1), new Object[] { "dustSteel", "nuggetTechnetium99" });

		addShapedRecipe(new ItemStack(ModBlocks.depth_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.stone_depth });
		addShapedRecipe(new ItemStack(ModBlocks.depth_tiles, 4), new Object[] { "CC", "CC", 'C', ModBlocks.depth_brick });
		addShapedRecipe(new ItemStack(ModBlocks.depth_nether_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.stone_depth_nether });
		addShapedRecipe(new ItemStack(ModBlocks.depth_nether_tiles, 4), new Object[] { "CC", "CC", 'C', ModBlocks.depth_nether_brick });
		addShapedRecipe(new ItemStack(ModBlocks.basalt_polished, 4), new Object[] { "CC", "CC", 'C', ModBlocks.basalt_smooth });
		addShapedRecipe(new ItemStack(ModBlocks.basalt_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.basalt_polished });
		addShapedRecipe(new ItemStack(ModBlocks.basalt_tiles, 4), new Object[] { "CC", "CC", 'C', ModBlocks.basalt_brick });
		addShapedOreRecipe(new ItemStack(ModBlocks.barrel_tcalloy, 1), new Object[] { "IPI", "I I", "IPI", 'I', "ingotTcAlloy", 'P', "plateTitanium" });

		addMineralSet(ModItems.nugget_bismuth, ModItems.ingot_bismuth, ModBlocks.block_bismuth);
		addMineralSet(ModItems.nugget_tantalium, ModItems.ingot_tantalium, ModBlocks.block_tantalium);
		addMineralSet(ModItems.nugget_zirconium, ModItems.ingot_zirconium, ModBlocks.block_zirconium);
		addMineralSet(ModItems.nugget_dineutronium, ModItems.ingot_dineutronium, ModBlocks.block_dineutronium);

		add1To9Pair(ModItems.powder_xe135, ModItems.powder_xe135_tiny);
		add1To9Pair(ModItems.powder_cs137, ModItems.powder_cs137_tiny);
		add1To9Pair(ModItems.powder_i131, ModItems.powder_i131_tiny);

		add1To9Pair(ModItems.ingot_technetium, ModItems.nugget_technetium);
		add1To9Pair(ModItems.ingot_co60, ModItems.nugget_co60);
		add1To9Pair(ModItems.ingot_au198, ModItems.nugget_au198);

		add1To9Pair(ModItems.ingot_pu241, ModItems.nugget_pu241);
		add1To9Pair(ModItems.ingot_am241, ModItems.nugget_am241);
		add1To9Pair(ModItems.ingot_am242, ModItems.nugget_am242);
		add1To9Pair(ModItems.ingot_am_mix, ModItems.nugget_am_mix);
		add1To9Pair(ModItems.ingot_americium_fuel, ModItems.nugget_americium_fuel);

		add1To9Pair(ModItems.powder_coal, ModItems.powder_coal_tiny);

		add1To9Pair(ModBlocks.sand_gold, ModItems.powder_gold);
		add1To9Pair(ModBlocks.sand_gold198, ModItems.powder_au198);
		add1To9Pair(ModBlocks.block_coltan, ModItems.fragment_coltan);
		add1To9Pair(ModBlocks.block_smore, ModItems.ingot_smore);
		add1To9Pair(ModItems.nuclear_waste_vitrified, ModItems.nuclear_waste_vitrified_tiny);
		add1To9Pair(ModBlocks.block_waste_vitrified, ModItems.nuclear_waste_vitrified);
		add1To9Pair(ModBlocks.block_niobium, ModItems.ingot_niobium);
		add1To9Pair(ModBlocks.block_boron, ModItems.ingot_boron);
		add1To9Pair(ModItems.powder_boron, ModItems.powder_boron_tiny);
		add1To9Pair(ModBlocks.block_graphite, ModItems.ingot_graphite);
		addShapedOreRecipe(new ItemStack(ModItems.circuit_tantalium_raw, 1), new Object[] { "RWR", "PTP", "RWR", 'R', "dustRedstone", 'W', ModItems.wire_gold, 'P', "plateCopper", 'T', "nuggetTantalum" });
		addShapedOreRecipe(new ItemStack(ModItems.circuit_bismuth_raw, 1), new Object[] { "RPR", "ABA", "RPR", 'R', "dustRedstone", 'P', "ingotPolymer", 'A', (GeneralConfig.enable528 ? ModItems.circuit_tantalium : "ingotAsbestos"), 'B', ModItems.ingot_bismuth });

		addShapedRecipe(new ItemStack(ModItems.inf_water_mk2, 1), new Object[] { "BPB", "PTP", "BPB", 'B', ModItems.inf_water, 'P', ModBlocks.fluid_duct_mk2, 'T', ModItems.tank_steel });

		addShapedOreRecipe(new ItemStack(ModBlocks.machine_condenser), new Object[] { "SIS", "ICI", "SIS", 'S', "ingotSteel", 'I', "plateIron", 'C', ModItems.board_copper });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_storage_drum), new Object[] { "LLL", "L#L", "LLL", 'L', "plateLead", '#', ModItems.tank_steel });

		addShapedOreRecipe(new ItemStack(ModItems.battery_sc_uranium), new Object[] { "NBN", "PCP", "NBN", 'N', "nuggetGold", 'B', ModItems.billet_u238, 'P', "plateLead", 'C', ModItems.thermo_element });
		addShapedOreRecipe(new ItemStack(ModItems.battery_sc_technetium), new Object[] { "NBN", "PCP", "NBN", 'N', "nuggetGold", 'B', ModItems.billet_technetium, 'P', "plateLead", 'C', ModItems.battery_sc_uranium });
		addShapedOreRecipe(new ItemStack(ModItems.battery_sc_plutonium), new Object[] { "NBN", "PCP", "NBN", 'N', "nuggetTechnetium99", 'B', ModItems.billet_pu238, 'P', "plateLead", 'C', ModItems.battery_sc_technetium });
		addShapedOreRecipe(new ItemStack(ModItems.battery_sc_polonium), new Object[] { "NBN", "PCP", "NBN", 'N', "nuggetTechnetium99", 'B', ModItems.billet_polonium, 'P', ModItems.ingot_polymer, 'C', ModItems.battery_sc_plutonium });
		addShapedRecipe(new ItemStack(ModItems.battery_sc_gold), new Object[] { "NBN", "PCP", "NBN", 'N', ModItems.nugget_tantalium, 'B', ModItems.billet_au198, 'P', ModItems.ingot_polymer, 'C', ModItems.battery_sc_polonium });
		addShapedRecipe(new ItemStack(ModItems.battery_sc_americium), new Object[] { "NBN", "PCP", "NBN", 'N', ModItems.nugget_tantalium, 'B', ModItems.billet_am241, 'P', ModItems.ingot_polymer, 'C', ModItems.battery_sc_gold });

		addShapedOreRecipe(new ItemStack(ModBlocks.hadron_coil_chlorophyte, 1), new Object[] { "TCT", "CSC", "TCT", 'T', "dustTungsten", 'C', ModItems.powder_chlorophyte, 'S', ModBlocks.hadron_coil_starmetal });
		addShapedRecipe(new ItemStack(ModBlocks.hadron_coil_magtung, 1), new Object[] { "WWW", "WCW", "WWW", 'W', ModItems.wire_magnetized_tungsten, 'C', ModBlocks.fwatz_conductor });
		addShapedRecipe(new ItemStack(ModBlocks.hadron_coil_gold, 1), new Object[] { "GGG", "GCG", "GGG", 'G', ModItems.coil_gold, 'C', ModBlocks.hadron_coil_alloy });
		addShapedRecipe(new ItemStack(ModBlocks.hadron_coil_neodymium, 1), new Object[] { " G ", "GCG", " G ", 'G', ModItems.powder_neodymium, 'C', ModBlocks.hadron_coil_gold });
		addShapedRecipe(new ItemStack(ModBlocks.hadron_coil_schrabidate, 1), new Object[] { "WSW", "SCS", "WSW", 'W', ModItems.wire_schrabidium, 'S', ModItems.powder_schrabidate, 'C', ModBlocks.hadron_coil_schrabidium });

		addShapedOreRecipe(new ItemStack(ModItems.flywheel_beryllium, 1), new Object[] { "BBB", "BTB", "BBB", 'B', "blockBeryllium", 'T', ModItems.bolt_compound });

		addShapelessOreRecipe(new ItemStack(ModItems.siox, 8), new Object[] { "dustCoal", "dustAsbestos", ModItems.nugget_bismuth });
		addShapelessRecipe(new ItemStack(ModItems.xanax, 1), new Object[] { ModItems.powder_coal, ModItems.niter, ModItems.powder_bromine });
		addShapelessRecipe(new ItemStack(ModItems.fmn, 1), new Object[] { ModItems.powder_coal, ModItems.powder_polonium, ModItems.powder_strontium });
		addShapelessRecipe(new ItemStack(ModItems.five_htp, 1), new Object[] { ModItems.powder_coal, ModItems.powder_euphemium, ModItems.canteen_fab });

		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_grate), 4), new Object[] { "SS", "SS", 'S', ModBlocks.steel_beam });

		addShapedOreRecipe(new ItemStack(ModItems.pile_rod_uranium, 1), new Object[] { " U ", "PUP", " U ", 'P', "plateIron", 'U', ModItems.billet_uranium });
		addShapedOreRecipe(new ItemStack(ModItems.pile_rod_source, 1), new Object[] { " U ", "PUP", " U ", 'P', "plateIron", 'U', ModItems.billet_ra226be });
		addShapedOreRecipe(new ItemStack(ModItems.pile_rod_boron, 1), new Object[] { "B", "W", "B", 'B', "ingotBoron", 'W', "plankWood" });

		addShapedOreRecipe(new ItemStack(ModItems.rbmk_fuel_empty, 1), new Object[] { "ZRZ", "Z Z", "ZRZ", 'Z', "ingotZirconium", 'R', ModItems.rod_quad_empty });
		addRBMKRod(ModItems.billet_uranium, ModItems.rbmk_fuel_ueu);
		addRBMKRod(ModItems.billet_uranium_fuel, ModItems.rbmk_fuel_meu);
		addRBMKRod(ModItems.billet_u233, ModItems.rbmk_fuel_heu233);
		addRBMKRod(ModItems.billet_u235, ModItems.rbmk_fuel_heu235);
		addRBMKRod(ModItems.billet_thorium_fuel, ModItems.rbmk_fuel_thmeu);
		addRBMKRod(ModItems.billet_mox_fuel, ModItems.rbmk_fuel_mox);
		addRBMKRod(ModItems.billet_plutonium_fuel, ModItems.rbmk_fuel_lep);
		addRBMKRod(ModItems.billet_pu_mix, ModItems.rbmk_fuel_mep);
		addRBMKRod(ModItems.billet_pu239, ModItems.rbmk_fuel_hep239);
		addRBMKRod(ModItems.billet_pu241, ModItems.rbmk_fuel_hep241);
		addRBMKRod(ModItems.billet_americium_fuel, ModItems.rbmk_fuel_lea);
		addRBMKRod(ModItems.billet_am_mix, ModItems.rbmk_fuel_mea);
		addRBMKRod(ModItems.billet_am241, ModItems.rbmk_fuel_hea241);
		addRBMKRod(ModItems.billet_am242, ModItems.rbmk_fuel_hea242);
		addRBMKRod(ModItems.billet_neptunium_fuel, ModItems.rbmk_fuel_men);
		addRBMKRod(ModItems.billet_neptunium, ModItems.rbmk_fuel_hen);
		addRBMKRod(ModItems.billet_po210be, ModItems.rbmk_fuel_po210be);
		addRBMKRod(ModItems.billet_ra226be, ModItems.rbmk_fuel_ra226be);
		addRBMKRod(ModItems.billet_pu238be, ModItems.rbmk_fuel_pu238be);
		addRBMKRod(ModItems.billet_australium_lesser, ModItems.rbmk_fuel_leaus);
		addRBMKRod(ModItems.billet_australium_greater, ModItems.rbmk_fuel_heaus);
		addRBMKRod(ModItems.egg_balefire_shard, ModItems.rbmk_fuel_balefire);
		addRBMKRod(ModItems.billet_les, ModItems.rbmk_fuel_les);
		addRBMKRod(ModItems.billet_schrabidium_fuel, ModItems.rbmk_fuel_mes);
		addRBMKRod(ModItems.billet_hes, ModItems.rbmk_fuel_hes);
		addRBMKRod(ModItems.billet_balefire_gold, ModItems.rbmk_fuel_balefire_gold);
		addShapelessRecipe(new ItemStack(ModItems.rbmk_fuel_drx, 1), new Object[] { ModItems.rbmk_fuel_balefire, ModItems.particle_digamma });

		addShapedOreRecipe(new ItemStack(ModItems.rbmk_lid, 4), new Object[] { "PPP", "CCC", "PPP", 'P', "plateSteel", 'C', ModBlocks.concrete_asbestos });
		addShapedOreRecipe(new ItemStack(ModItems.rbmk_lid_glass, 4), new Object[] { "LLL", "BBB", "P P", 'P', "plateSteel", 'L', ModBlocks.glass_lead, 'B', ModBlocks.glass_boron });
		addShapedOreRecipe(new ItemStack(ModItems.rbmk_lid_glass, 4), new Object[] { "BBB", "LLL", "P P", 'P', "plateSteel", 'L', ModBlocks.glass_lead, 'B', ModBlocks.glass_boron });

		addShapedRecipe(new ItemStack(ModBlocks.rbmk_moderator, 1), new Object[] { " G ", "GRG", " G ", 'G', ModBlocks.block_graphite, 'R', ModBlocks.rbmk_blank });
		addShapedRecipe(new ItemStack(ModBlocks.rbmk_absorber, 1), new Object[] { "GGG", "GRG", "GGG", 'G', ModItems.ingot_boron, 'R', ModBlocks.rbmk_blank });
		addShapedRecipe(new ItemStack(ModBlocks.rbmk_reflector, 1), new Object[] { "GGG", "GRG", "GGG", 'G', ModItems.neutron_reflector, 'R', ModBlocks.rbmk_blank });
		addShapedRecipe(new ItemStack(ModBlocks.rbmk_control, 1), new Object[] { " B ", "GRG", " B ", 'G', ModItems.ingot_graphite, 'B', ModItems.motor, 'R', ModBlocks.rbmk_absorber });
		addShapedRecipe(new ItemStack(ModBlocks.rbmk_control_mod, 1), new Object[] { "BGB", "GRG", "BGB", 'G', ModBlocks.block_graphite, 'R', ModBlocks.rbmk_control, 'B', ModItems.nugget_bismuth });
		addShapedRecipe(new ItemStack(ModBlocks.rbmk_control_auto, 1), new Object[] { "C", "R", "C", 'C', ModItems.circuit_targeting_tier1, 'R', ModBlocks.rbmk_control });
		addShapedOreRecipe(new ItemStack(ModBlocks.rbmk_rod_reasim, 1), new Object[] { "ZCZ", "ZRZ", "ZCZ", 'C', ModItems.hull_small_steel, 'R', ModBlocks.rbmk_blank, 'Z', "ingotZirconium" });
		addShapedRecipe(new ItemStack(ModBlocks.rbmk_rod_reasim_mod, 1), new Object[] { "BGB", "GRG", "BGB", 'G', ModBlocks.block_graphite, 'R', ModBlocks.rbmk_rod_reasim, 'B', ModItems.ingot_tcalloy });
		addShapedRecipe(new ItemStack(ModBlocks.rbmk_outgasser, 1), new Object[] { "GHG", "GRG", "GTG", 'G', ModBlocks.steel_grate, 'H', Blocks.HOPPER, 'T', ModItems.tank_steel, 'R', ModBlocks.rbmk_blank });
		addShapedOreRecipe(new ItemStack(ModBlocks.rbmk_loader, 1), new Object[] { "SCS", "CBC", "SCS", 'S', "plateSteel", 'C', "ingotCopper", 'B', ModItems.tank_steel });
		addShapedOreRecipe(new ItemStack(ModBlocks.rbmk_steam_inlet, 1), new Object[] { "SCS", "CBC", "SCS", 'S', "ingotSteel", 'C', "plateIron", 'B', ModItems.tank_steel });
		addShapedOreRecipe(new ItemStack(ModBlocks.rbmk_steam_outlet, 1), new Object[] { "SCS", "CBC", "SCS", 'S', "ingotSteel", 'C', "plateCopper", 'B', ModItems.tank_steel });

		addShapedOreRecipe(new ItemStack(ModBlocks.anvil_iron, 1), new Object[] { "III", " B ", "III", 'I', "ingotIron", 'B', "blockIron" });
		addShapedOreRecipe(new ItemStack(ModBlocks.anvil_lead, 1), new Object[] { "III", " B ", "III", 'I', "ingotLead", 'B', "blockLead" });
		addShapedRecipe(new ItemStack(ModBlocks.anvil_murky, 1), new Object[] { "UUU", "UAU", "UUU", 'U', ModItems.undefined, 'A', ModBlocks.anvil_steel });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_fraction_tower), new Object[] { "SHS", "SGS", "SHS", 'S', "plateSteel", 'H', ModItems.hull_big_steel, 'G', ModBlocks.steel_grate });
		addShapedRecipe(new ItemStack(ModBlocks.fraction_spacer), new Object[] { "BHB", 'H', ModItems.hull_big_steel, 'B', Blocks.IRON_BARS });

		//Cladding
		addShapelessOreRecipe(new ItemStack(ModItems.cladding_paint, 1), new Object[] { "nuggetLead", "nuggetLead", "nuggetLead", "nuggetLead", Items.CLAY_BALL, Items.GLASS_BOTTLE });
		addShapedOreRecipe(new ItemStack(ModItems.cladding_rubber, 1), new Object[] { "RCR", "CDC", "RCR", 'R', ModItems.plate_polymer, 'C', "dustCoal", 'D', ModItems.ducttape });
		addShapedOreRecipe(new ItemStack(ModItems.cladding_lead, 1), new Object[] { "DPD", "PRP", "DPD", 'R', ModItems.cladding_rubber, 'P', "plateLead", 'D', ModItems.ducttape });
		addShapedRecipe(new ItemStack(ModItems.cladding_desh, 1), new Object[] { "DPD", "PRP", "DPD", 'R', ModItems.cladding_lead, 'P', ModItems.plate_desh, 'D', ModItems.ducttape });
		addShapedOreRecipe(new ItemStack(ModItems.cladding_obsidian, 1), new Object[] { "OOO", "PDP", "OOO", 'O', Blocks.OBSIDIAN, 'P', "plateSteel", 'D', ModItems.ducttape });
		addShapedOreRecipe(new ItemStack(ModItems.cladding_iron, 1), new Object[] { "OOO", "PDP", "OOO", 'O', "plateIron", 'P', ModItems.plate_polymer, 'D', ModItems.ducttape });

		//Inserts
		addShapedOreRecipe(new ItemStack(ModItems.insert_steel, 1), new Object[] { "DPD", "PSP", "DPD", 'D', ModItems.ducttape, 'P', "plateIron", 'S', "blockSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.insert_du, 1), new Object[] { "DPD", "PSP", "DPD", 'D', ModItems.ducttape, 'P', "plateIron", 'S', "blockUranium238" });
		addShapedOreRecipe(new ItemStack(ModItems.insert_polonium, 1), new Object[] { "DPD", "PSP", "DPD", 'D', ModItems.ducttape, 'P', "plateIron", 'S', "blockPolonium" });
		addShapedOreRecipe(new ItemStack(ModItems.insert_era, 1), new Object[] { "DPD", "PSP", "DPD", 'D', ModItems.ducttape, 'P', "plateIron", 'S', ModItems.ingot_semtex });
		addShapedRecipe(new ItemStack(ModItems.insert_kevlar, 1), new Object[] { "KIK", "IDI", "KIK", 'K', ModItems.plate_kevlar, 'I', ModItems.plate_polymer, 'D', ModItems.ducttape });
		addShapedRecipe(new ItemStack(ModItems.insert_sapi, 1), new Object[] { "PKP", "DPD", "PKP", 'P', ModItems.ingot_polymer, 'K', ModItems.insert_kevlar, 'D', ModItems.ducttape });
		addShapedRecipe(new ItemStack(ModItems.insert_esapi, 1), new Object[] { "PKP", "DSD", "PKP", 'P', ModItems.ingot_polymer, 'K', ModItems.insert_sapi, 'D', ModItems.ducttape, 'S', ModItems.plate_saturnite });
		addShapedRecipe(new ItemStack(ModItems.insert_xsapi, 1), new Object[] { "PKP", "DSD", "PKP", 'P', ModItems.ingot_asbestos, 'K', ModItems.insert_esapi, 'D', ModItems.ducttape, 'S', ModItems.ingot_meteorite_forged });
		addShapedRecipe(new ItemStack(ModItems.insert_yharonite, 1), new Object[] { "YIY", "IYI", "YIY", 'Y', ModItems.billet_yharonite, 'I', ModItems.insert_du });

		//Servos
		addShapedOreRecipe(new ItemStack(ModItems.servo_set, 1), new Object[] { "MBM", "PBP", "MBM", 'M', ModItems.motor, 'B', ModItems.bolt_tungsten, 'P', "plateIron" });
		addShapedOreRecipe(new ItemStack(ModItems.servo_set_desh, 1), new Object[] { "MBM", "PSP", "MBM", 'M', ModItems.motor_desh, 'B', ModItems.bolt_dura_steel, 'P', "plateAdvanced", 'S', ModItems.servo_set });

		//Helmet Mods
		addShapedOreRecipe(new ItemStack(ModItems.attachment_mask, 1), new Object[] { "DID", "IGI", " F ", 'D', ModItems.ducttape, 'I', ModItems.plate_polymer, 'G', "paneGlass", 'F', "plateIron" });
		addShapedOreRecipe(new ItemStack(ModItems.attachment_mask_mono, 1), new Object[] { " D ", "DID", " F ", 'D', ModItems.ducttape, 'I', ModItems.plate_polymer, 'F', "plateIron" });

		//Boot Mods
		addShapedOreRecipe(new ItemStack(ModItems.pads_rubber, 1), new Object[] { "P P", "IDI", "P P", 'P', ModItems.plate_polymer, 'I', "plateIron", 'D', ModItems.ducttape });
		addShapedOreRecipe(new ItemStack(ModItems.pads_slime, 1), new Object[] { "SPS", "DSD", "SPS", 'S', "slimeball", 'P', ModItems.pads_rubber, 'D', ModItems.ducttape });
		addShapedOreRecipe(new ItemStack(ModItems.pads_static, 1), new Object[] { "CDC", "ISI", "CDC", 'C', "plateCopper", 'D', ModItems.ducttape, 'I', ModItems.plate_polymer, 'S', ModItems.pads_slime });

		//Special Mods
		addShapedOreRecipe(new ItemStack(ModItems.horseshoe_magnet, 1), new Object[] { "L L", "I I", "ILI", 'L', ModItems.lodestone, 'I', "ingotIron" });
		addShapedOreRecipe(new ItemStack(ModItems.industrial_magnet, 1), new Object[] { "SMS", " B ", "SMS", 'S', "ingotSteel", 'M', ModItems.horseshoe_magnet, 'B', ModBlocks.fusion_conductor });
		addShapedOreRecipe(new ItemStack(ModItems.heart_container, 1), new Object[] { "HAH", "ACA", "HAH", 'H', ModItems.heart_piece, 'A', "ingotAluminum", 'C', ModItems.coin_creeper });
		addShapedOreRecipe(new ItemStack(ModItems.heart_booster, 1), new Object[] { "GHG", "MCM", "GHG", 'G', "ingotGold", 'H', ModItems.heart_container, 'M', ModItems.morning_glory, 'C', ModItems.coin_maskman });
		addShapedRecipe(new ItemStack(ModItems.heart_fab, 1), new Object[] { "GHG", "MCM", "GHG", 'G', ModItems.billet_polonium, 'H', ModItems.heart_booster, 'M', ModItems.canteen_fab, 'C', ModItems.coin_worm });
		addShapedOreRecipe(new ItemStack(ModItems.ink, 1), new Object[] { "FPF", "PIP", "FPF", 'F', new ItemStack(Blocks.RED_FLOWER, 1, OreDictionary.WILDCARD_VALUE), 'P', ModItems.armor_polish, 'I', "dyeBlack" });
		addShapedRecipe(new ItemStack(ModItems.bathwater_mk2, 1), new Object[] { "MWM", "WBW", "MWM", 'M', ModItems.bottle_mercury, 'W', ModItems.nuclear_waste, 'B', ModItems.bathwater });
		addShapedRecipe(new ItemStack(ModItems.back_tesla, 1), new Object[] { "DGD", "GTG", "DGD", 'D', ModItems.ducttape, 'G', ModItems.wire_gold, 'T', ModBlocks.tesla });
		addShapedRecipe(new ItemStack(ModItems.medal_liquidator, 1), new Object[] { "GBG", "BFB", "GBG", 'G', ModItems.nugget_au198, 'B', ModItems.ingot_boron, 'F', ModItems.debris_fuel });
		addShapelessRecipe(new ItemStack(ModItems.injector_5htp, 1), new Object[] { ModItems.five_htp, ModItems.circuit_targeting_tier1, ModItems.plate_saturnite });
		addShapelessRecipe(new ItemStack(ModItems.injector_knife, 1), new Object[] { ModItems.injector_5htp, Items.IRON_SWORD });
		addShapedRecipe(new ItemStack(ModItems.shackles, 1), new Object[] { "CIC", "C C", "I I", 'I', ModItems.ingot_chainsteel, 'C', ModBlocks.chain });
		addShapedRecipe(new ItemStack(ModItems.black_diamond, 1), new Object[] { "NIN", "IGI", "NIN", 'N', ModItems.nugget_au198, 'I', ModItems.ink, 'G', ModItems.gem_volcanic });
		addShapelessOreRecipe(new ItemStack(ModItems.cladding_paint, 1), new Object[] { "dustLead", Items.CLAY_BALL, Items.GLASS_BOTTLE });
		addShapedOreRecipe(new ItemStack(ModItems.cladding_rubber, 1), new Object[] { "RCR", "CDC", "RCR", 'R', ModItems.plate_polymer, 'C', "dustCoal", 'D', ModItems.ducttape });
		addShapedOreRecipe(new ItemStack(ModItems.cladding_lead, 1), new Object[] { "DPD", "PRP", "DPD", 'R', ModItems.cladding_rubber, 'P', "plateLead", 'D', ModItems.ducttape });
		addShapedRecipe(new ItemStack(ModItems.cladding_desh, 1), new Object[] { "DPD", "PRP", "DPD", 'R', ModItems.cladding_lead, 'P', ModItems.plate_desh, 'D', ModItems.ducttape });
		addShapedRecipe(new ItemStack(ModBlocks.struct_plasma_core, 1), new Object[] { "CBC", "BHB", "CBC", 'C', ModItems.circuit_gold, 'B', ModBlocks.machine_lithium_battery, 'H', ModBlocks.fusion_heater });

		addShapedOreRecipe(new ItemStack(ModItems.drax, 1), new Object[] { "BDS", "CDC", "FMF", 'B', ModItems.starmetal_pickaxe, 'S', ModItems.starmetal_shovel, 'C', "ingotCobalt", 'F', ModItems.fusion_core, 'D', "ingotDesh", 'M', ModItems.motor_desh });
		addShapedRecipe(new ItemStack(ModItems.drax_mk2, 1), new Object[] { "SCS", "IDI", "FEF", 'S', ModItems.ingot_starmetal, 'C', ModItems.crystal_trixite, 'I', ModItems.ingot_saturnite, 'D', ModItems.drax, 'F', ModItems.fusion_core, 'E', ModItems.circuit_targeting_tier5 });
		addShapedRecipe(new ItemStack(ModItems.drax_mk3, 1), new Object[] { "ECE", "CDC", "SBS", 'E', ModBlocks.block_euphemium_cluster, 'C', ModItems.crystal_schrabidium, 'D', ModItems.drax_mk2, 'S', ModItems.circuit_targeting_tier6, 'B', ItemBattery.getFullBattery(ModItems.battery_spark) });

		addShapedRecipe(new ItemStack(ModItems.cobalt_decorated_sword, 1), new Object[] { " I ", " I ", "SBS", 'I', ModItems.ingot_cobalt, 'S', ModItems.ingot_meteorite_forged, 'B', ModItems.cobalt_sword });
		addShapedRecipe(new ItemStack(ModItems.cobalt_decorated_pickaxe, 1), new Object[] { "III", " B ", " S ", 'I', ModItems.ingot_cobalt, 'S', ModItems.ingot_meteorite_forged, 'B', ModItems.cobalt_pickaxe });
		addShapedRecipe(new ItemStack(ModItems.cobalt_decorated_axe, 1), new Object[] { "II", "IB", " S", 'I', ModItems.ingot_cobalt, 'S', ModItems.ingot_meteorite_forged, 'B', ModItems.cobalt_axe });
		addShapedRecipe(new ItemStack(ModItems.cobalt_decorated_shovel, 1), new Object[] { "I", "B", "S", 'I', ModItems.ingot_cobalt, 'S', ModItems.ingot_meteorite_forged, 'B', ModItems.cobalt_shovel });
		addShapedRecipe(new ItemStack(ModItems.cobalt_decorated_hoe, 1), new Object[] { "II", " B", " S", 'I', ModItems.ingot_cobalt, 'S', ModItems.ingot_meteorite_forged, 'B', ModItems.cobalt_hoe });
		addShapedRecipe(new ItemStack(ModItems.starmetal_sword, 1), new Object[] { " I ", " I ", "SBS", 'I', ModItems.ingot_starmetal, 'S', ModItems.ingot_cobalt, 'B', ModItems.cobalt_decorated_sword });
		addShapedRecipe(new ItemStack(ModItems.starmetal_pickaxe, 1), new Object[] { "III", " B ", " S ", 'I', ModItems.ingot_starmetal, 'S', ModItems.ingot_cobalt, 'B', ModItems.cobalt_decorated_pickaxe });
		addShapedRecipe(new ItemStack(ModItems.starmetal_axe, 1), new Object[] { "II", "IB", " S", 'I', ModItems.ingot_starmetal, 'S', ModItems.ingot_cobalt, 'B', ModItems.cobalt_decorated_axe });
		addShapedRecipe(new ItemStack(ModItems.starmetal_shovel, 1), new Object[] { "I", "B", "S", 'I', ModItems.ingot_starmetal, 'S', ModItems.ingot_cobalt, 'B', ModItems.cobalt_decorated_shovel });
		addShapedRecipe(new ItemStack(ModItems.starmetal_hoe, 1), new Object[] { "II", " B", " S", 'I', ModItems.ingot_starmetal, 'S', ModItems.ingot_cobalt, 'B', ModItems.cobalt_decorated_hoe });

		addShapedRecipe(new ItemStack(ModItems.chlorophyte_pickaxe, 1), new Object[] { " SD", "APS", "FA ", 'S', ModItems.blades_steel, 'D', ModItems.powder_chlorophyte, 'A', ModItems.ingot_fiberglass, 'P', ModItems.bismuth_pickaxe, 'F', ModItems.bolt_dura_steel });
		addShapedRecipe(new ItemStack(ModItems.chlorophyte_pickaxe, 1), new Object[] { " SD", "APS", "FA ", 'S', ModItems.blades_steel, 'D', ModItems.powder_chlorophyte, 'A', ModItems.ingot_fiberglass, 'P', ModItems.volcanic_pickaxe, 'F', ModItems.bolt_dura_steel });
		addShapedRecipe(new ItemStack(ModItems.bismuth_pickaxe, 1), new Object[] { " BM", "BPB", "TB ", 'B', ModItems.ingot_bismuth, 'M', ModItems.ingot_meteorite, 'P', ModItems.starmetal_pickaxe, 'T', ModItems.bolt_tungsten });
		addShapedRecipe(new ItemStack(ModItems.volcanic_pickaxe, 1), new Object[] { " BM", "BPB", "TB ", 'B', ModItems.gem_volcanic, 'M', ModItems.ingot_meteorite, 'P', ModItems.starmetal_pickaxe, 'T', ModItems.bolt_tungsten });
		addShapedRecipe(new ItemStack(ModItems.mese_pickaxe, 1), new Object[] { " SD", "APS", "FA ", 'S', ModItems.blades_desh, 'D', ModItems.powder_dineutronium, 'A', ModItems.plate_paa, 'P', ModItems.chlorophyte_pickaxe, 'F', ModItems.shimmer_handle });

		addShapedOreRecipe(new ItemStack(ModItems.upgrade_nullifier, 1), new Object[] { "SPS", "PUP", "SPS", 'S', "plateSteel", 'P', ModItems.powder_fire, 'U', ModItems.upgrade_template });
		addShapedOreRecipe(new ItemStack(ModItems.upgrade_smelter, 1), new Object[] { "PHP", "CUC", "DTD", 'P', "plateCopper", 'H', Blocks.HOPPER, 'C', ModItems.coil_tungsten, 'U', ModItems.upgrade_template, 'D', ModItems.coil_copper, 'T', ModBlocks.machine_transformer });
		addShapedOreRecipe(new ItemStack(ModItems.upgrade_shredder, 1), new Object[] { "PHP", "CUC", "DTD", 'P', ModItems.motor, 'H', Blocks.HOPPER, 'C', ModItems.blades_advanced_alloy, 'U', ModItems.upgrade_smelter, 'D', "plateTitanium", 'T', ModBlocks.machine_transformer });
		addShapedOreRecipe(new ItemStack(ModItems.upgrade_centrifuge, 1), new Object[] { "PHP", "PUP", "DTD", 'P', ModItems.centrifuge_element, 'H', Blocks.HOPPER, 'U', ModItems.upgrade_shredder, 'D', "ingotPolymer", 'T', ModBlocks.machine_transformer });
		addShapedOreRecipe(new ItemStack(ModItems.upgrade_crystallizer, 1), new Object[] { "PHP", "CUC", "DTD", 'P', new IngredientContainsTag(ItemFluidTank.getFullBarrel(ModForgeFluids.acid)), 'H', ModItems.circuit_targeting_tier4, 'C', ModBlocks.barrel_steel, 'U', ModItems.upgrade_centrifuge, 'D', ModItems.motor, 'T', ModBlocks.machine_transformer });

		addShapedOreRecipe(new ItemStack(ModItems.charge_railgun), new Object[] { "PDP", "DDD", "PDP", 'P', "plateSteel", 'D', new IngredientContainsTag(ItemFluidTank.getFullTank(ModForgeFluids.deuterium)) });

		addShapedRecipe(new ItemStack(ModItems.ammo_4gauge_canister, 4), new Object[] { " B ", "BAB", " B ", 'B', ModItems.ammo_4gauge_kampf, 'A', ModItems.pellet_canister });
		addShapedRecipe(new ItemStack(ModItems.ammo_44_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_44, 'A', ModItems.pellet_chlorophyte });
		addShapedRecipe(new ItemStack(ModItems.ammo_5mm_chlorophyte, 4), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_5mm, 'A', ModItems.pellet_chlorophyte });
		addShapedRecipe(new ItemStack(ModItems.ammo_9mm_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_9mm, 'A', ModItems.pellet_chlorophyte });
		addShapedRecipe(new ItemStack(ModItems.ammo_22lr_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_22lr, 'A', ModItems.pellet_chlorophyte });
		addShapedRecipe(new ItemStack(ModItems.ammo_50bmg_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_50bmg, 'A', ModItems.pellet_chlorophyte });
		addShapedRecipe(new ItemStack(ModItems.ammo_50ae_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_50ae, 'A', ModItems.pellet_chlorophyte });
		addShapedRecipe(new ItemStack(ModItems.ammo_556_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_556, 'A', ModItems.pellet_chlorophyte });
		addShapedRecipe(new ItemStack(ModItems.ammo_556_flechette_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_556_flechette, 'A', ModItems.pellet_chlorophyte });
		addShapedRecipe(new ItemStack(ModItems.ammo_rocket_canister, 1), new Object[] { "G", "R", 'G', ModItems.pellet_canister, 'R', ModItems.ammo_rocket });

		addShapelessRecipe(new ItemStack(ModItems.circuit_red_copper, 48), new Object[] { ModBlocks.fusion_core });
		addShapelessRecipe(new ItemStack(ModBlocks.fusion_heater), new Object[] { ModBlocks.fusion_hatch });
		addShapelessRecipe(new ItemStack(ModItems.energy_core), new Object[] { ModItems.fusion_core, ModItems.fuse });

		addShapedOreRecipe(new ItemStack(ModItems.plate_armor_titanium, 1), new Object[] { "NPN", "PIP", "NPN", 'N', ModItems.bolt_tungsten, 'P', "plateTitanium", 'I', "ingotSteel" });
		addShapedOreRecipe(new ItemStack(ModItems.plate_armor_lunar, 1), new Object[] { "NPN", "PIP", "NPN", 'N', ModItems.wire_magnetized_tungsten, 'P', OreDictManager.getReflector(), 'I', ModItems.ingot_starmetal });

		addShapedOreRecipe(new ItemStack(ModItems.wood_gavel, 1), new Object[] { "SWS", " R ", " R ", 'S', "slabWood", 'W', "logWood", 'R', "stickWood" });
		addShapedOreRecipe(new ItemStack(ModItems.lead_gavel, 1), new Object[] { "PIP", "IGI", "PIP", 'P', ModItems.pellet_buckshot, 'I', "ingotLead", 'G', ModItems.wood_gavel });

		addShapedRecipe(new ItemStack(ModItems.mech_key, 1), new Object[] { "MCM", "MKM", "MMM", 'M', ModItems.ingot_meteorite_forged, 'C', ModItems.coin_maskman, 'K', ModItems.key });
		addShapedRecipe(new ItemStack(ModItems.spawn_ufo, 1), new Object[] { "MMM", "DCD", "MMM", 'M', ModItems.ingot_meteorite, 'D', ModItems.ingot_dineutronium, 'C', ModItems.coin_worm });

		addShapedOreRecipe(new ItemStack(ModItems.particle_empty, 2), new Object[] { "STS", "G G", "STS", 'S', "plateSteel", 'T', "ingotTungsten", 'G', "paneGlass" });
		addShapelessOreRecipe(new ItemStack(ModItems.particle_copper, 1), new Object[] { ModItems.particle_empty, "dustCopper", ModItems.pellet_charged });
		addShapelessOreRecipe(new ItemStack(ModItems.particle_lead, 1), new Object[] { ModItems.particle_empty, "dustLead", ModItems.pellet_charged });
		addShapelessRecipe(ItemCell.getFullCell(ModForgeFluids.amat), new Object[] { ModItems.particle_aproton, ModItems.particle_aelectron, ModItems.cell });
		addShapelessRecipe(new ItemStack(ModItems.particle_amat, 1), new Object[] { ModItems.particle_aproton, ModItems.particle_aelectron, ModItems.particle_empty });

		ItemStack infinity = new ItemStack(Items.ENCHANTED_BOOK);
		EnchantmentUtil.addEnchantment(infinity, Enchantments.INFINITY, 1);
		addShapedRecipe(infinity, new Object[] { "SBS", "BDB", "SBS", 'S', ModItems.ammo_50bmg_star, 'B', ModItems.ammo_5mm_star, 'D', ModItems.powder_magic });
		ItemStack unbreaking = new ItemStack(Items.ENCHANTED_BOOK);
		EnchantmentUtil.addEnchantment(unbreaking, Enchantments.UNBREAKING, 3);
		addShapedRecipe(unbreaking, new Object[] { "SBS", "BDB", "SBS", 'S', ModItems.ingot_saturnite, 'B', ModItems.plate_armor_lunar, 'D', ModItems.powder_magic });
		ItemStack thorns = new ItemStack(Items.ENCHANTED_BOOK);
		EnchantmentUtil.addEnchantment(thorns, Enchantments.THORNS, 3);
		addShapedRecipe(thorns, new Object[] { "SBS", "BDB", "SBS", 'S', ModBlocks.barbed_wire, 'B', ModBlocks.spikes, 'D', ModItems.powder_magic });

		addShapedRecipe(new ItemStack(ModBlocks.hadron_coil_alloy, 1), new Object[] { "WWW", "WCW", "WWW", 'W', ModItems.wire_advanced_alloy, 'C', ModBlocks.fusion_conductor });
		addShapedRecipe(new ItemStack(ModBlocks.hadron_coil_schrabidium, 1), new Object[] { "SCS", "CSC", "SCS", 'S', ModItems.powder_schrabidium, 'C', ModBlocks.hadron_coil_alloy });
		addShapedRecipe(new ItemStack(ModBlocks.hadron_coil_starmetal, 1), new Object[] { "SCS", "CSC", "SCS", 'S', ModItems.ring_starmetal, 'C', ModBlocks.hadron_coil_schrabidium });
		addShapedOreRecipe(new ItemStack(ModBlocks.hadron_diode, 1), new Object[] { "CIC", "ISI", "CIC", 'C', ModBlocks.hadron_coil_alloy, 'I', "ingotSteel", 'S', ModItems.circuit_gold });
		addShapedOreRecipe(new ItemStack(ModBlocks.hadron_plating, 1), new Object[] { "IPI", "P P", "IPI", 'I', "ingotSteel", 'P', "plateSteel" });
		addShapelessOreRecipe(new ItemStack(ModBlocks.hadron_plating_blue, 1), new Object[] { ModBlocks.hadron_plating, "dyeBlue" });
		addShapelessOreRecipe(new ItemStack(ModBlocks.hadron_plating_black, 1), new Object[] { ModBlocks.hadron_plating, "dyeBlack" });
		addShapelessOreRecipe(new ItemStack(ModBlocks.hadron_plating_yellow, 1), new Object[] { ModBlocks.hadron_plating, "dyeYellow" });
		addShapelessOreRecipe(new ItemStack(ModBlocks.hadron_plating_striped, 1), new Object[] { ModBlocks.hadron_plating, "dyeBlack", "dyeYellow" });
		addShapelessOreRecipe(new ItemStack(ModBlocks.hadron_plating_glass, 1), new Object[] { ModBlocks.hadron_plating, "blockGlass" });
		addShapelessOreRecipe(new ItemStack(ModBlocks.hadron_plating_voltz, 1), new Object[] { ModBlocks.hadron_plating, "dyeRed" });
		addShapedRecipe(new ItemStack(ModBlocks.hadron_power, 1), new Object[] { "STS", "CPC", "STS", 'S', ModItems.ingot_saturnite, 'T', ModBlocks.machine_transformer, 'C', ModItems.circuit_targeting_tier3, 'P', ModBlocks.hadron_plating_blue });
		addShapedOreRecipe(new ItemStack(ModBlocks.hadron_analysis, 1), new Object[] { "IPI", "PCP", "IPI", 'I', "ingotTitanium", 'P', OreDictManager.getReflector(), 'C', ModItems.circuit_gold });
		addShapelessOreRecipe(new ItemStack(ModBlocks.hadron_analysis_glass, 1), new Object[] { ModBlocks.hadron_analysis, "blockGlass" });
		addShapedOreRecipe(new ItemStack(ModBlocks.hadron_access, 1), new Object[] { "IGI", "CRC", "IPI", 'I', ModItems.plate_polymer, 'G', "paneGlass", 'C', ModItems.circuit_aluminium, 'R', "blockRedstone", 'P', ModBlocks.hadron_plating_blue });

		addShapedOreRecipe(new ItemStack(ModItems.coil_copper_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', "plateSteel", 'C', ModItems.coil_copper });
		addShapedOreRecipe(new ItemStack(ModItems.coil_advanced_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', "plateSteel", 'C', ModItems.coil_advanced_alloy });
		addShapedOreRecipe(new ItemStack(ModItems.coil_gold_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', "plateSteel", 'C', ModItems.coil_gold });
		addShapedOreRecipe(new ItemStack(ModBlocks.machine_solar_boiler), new Object[] { "SHS", "DHD", "SHS", 'S', "ingotSteel", 'H', ModItems.hull_big_steel, 'D', "dyeBlack" });
		addShapedOreRecipe(new ItemStack(ModBlocks.solar_mirror, 3), new Object[] { "AAA", " B ", "SSS", 'A', "plateAluminum", 'B', ModBlocks.steel_beam, 'S', "ingotSteel" });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_beam), 8), new Object[] { "S", "S", "S", 'S', ModBlocks.steel_scaffold });
		addShapedOreRecipe(new ItemStack(ModItems.mirror_tool), new Object[] { " A ", " IA", "I  ", 'A', "ingotAluminum", 'I', "ingotIron" });
		addShapedOreRecipe(new ItemStack(ModItems.rbmk_tool), new Object[] { " A ", " IA", "I  ", 'A', "ingotLead", 'I', "ingotIron" });
		addShapedOreRecipe(new ItemStack(ModItems.hand_drill), new Object[] { " D", "S ", " S", 'D', "ingotDuraSteel", 'S', Items.STICK });

		addShapedRecipe(new ItemStack(ModItems.hev_helmet, 1), new Object[] { "PCP", "PBP", "IFI", 'P', ModItems.plate_armor_hev, 'C', ModItems.circuit_targeting_tier4, 'B', ModItems.titanium_helmet, 'I', ModItems.plate_polymer, 'F', ModItems.gas_mask_filter });
		addShapedRecipe(new ItemStack(ModItems.hev_plate, 1), new Object[] { "MPM", "IBI", "PPP", 'P', ModItems.plate_armor_hev, 'B', ModItems.titanium_plate, 'I', ModItems.ingot_polymer, 'M', ModItems.motor_desh });
		addShapedRecipe(new ItemStack(ModItems.hev_legs, 1), new Object[] { "MPM", "IBI", "P P", 'P', ModItems.plate_armor_hev, 'B', ModItems.titanium_legs, 'I', ModItems.ingot_polymer, 'M', ModItems.motor_desh });
		addShapedRecipe(new ItemStack(ModItems.hev_boots, 1), new Object[] { "P P", "PBP", 'P', ModItems.plate_armor_hev, 'B', ModItems.titanium_boots });
		addShapedRecipe(new ItemStack(ModItems.plate_armor_hev, 1), new Object[] { "NPN", "PIP", "NPN", 'N', ModItems.wire_tungsten, 'P', ModItems.plate_advanced_alloy, 'I', ModItems.plate_armor_titanium });

		addShapedRecipe(new ItemStack(ModBlocks.machine_detector, 1), new Object[] { "IRI", "CTC", "IRI", 'I', ModItems.plate_polymer, 'R', Items.REDSTONE, 'C', ModItems.wire_red_copper, 'T', ModItems.coil_tungsten });
		addShapedOreRecipe(new ItemStack(ModItems.ingot_schrabidium, 8), new Object[] { "UUU", "UPU", "UUU", 'U', "ingotUranium", 'P', new ItemStack(ModItems.particle_higgs).setStackDisplayName("Higgs Boson (Temporary Recipe)") });
		addShapedOreRecipe(new ItemStack(ModItems.ingot_euphemium, 8), new Object[] { "UUU", "UPU", "UUU", 'U', "ingotPlutonium", 'P', new ItemStack(ModItems.particle_dark).setStackDisplayName("Dark Matter (Temporary Recipe)") });
		addShapedRecipe(new ItemStack(ModItems.ingot_dineutronium, 8), new Object[] { "UUU", "UPU", "UUU", 'U', ModItems.ingot_schrabidate, 'P', new ItemStack(ModItems.particle_sparkticle).setStackDisplayName("Sparkticle (Temporary Recipe)") });
		addShapedOreRecipe(new ItemStack(ModItems.meteorite_sword, 1), new Object[] { "  B", "GB ", "SG ", 'B', ModItems.blade_meteorite, 'G', "plateGold", 'S', Items.STICK });
		addShapelessOreRecipe(new ItemStack(ModItems.powder_semtex_mix, 1), new Object[] { ModItems.solid_fuel, ModItems.ballistite, "dustSaltpeter" });
		add9To1(ModItems.ingot_aluminium, ModBlocks.block_aluminium);
		add1To9(ModBlocks.block_aluminium, ModItems.ingot_aluminium);

		add9To1(ModItems.ingot_schraranium, ModBlocks.block_schraranium);
		add1To9(ModBlocks.block_schraranium, ModItems.ingot_schraranium);

		add1To9Pair(ModBlocks.block_lanthanium, ModItems.ingot_lanthanium);
		add1To9Pair(ModBlocks.block_actinium, ModItems.ingot_actinium);
		add9To1(ModItems.ingot_schrabidate, ModBlocks.block_schrabidate);
		add1To9(ModBlocks.block_schrabidate, ModItems.ingot_schrabidate);

		add9To1(ModItems.ingot_dineutronium, ModBlocks.block_dineutronium);
		add1To9(ModBlocks.block_dineutronium, ModItems.ingot_dineutronium);
		addShapedRecipe(new ItemStack(ModItems.canteen_fab, 1), new Object[] { "VMV", "MVM", "VMV", 'V', ModItems.canteen_vodka, 'M', ModItems.powder_magic });
		addShapedOreRecipe(new ItemStack(ModBlocks.fireworks, 1), new Object[] { "PPP", "PPP", "WIW", 'P', Items.PAPER, 'W', "plankWood", 'I', "ingotIron" });

		addShapedOreRecipe(new ItemStack(ModItems.pellet_claws, 1), new Object[] { " X ", "X X", " XX", 'X', "plateSteel" });
		addShapedRecipe(new ItemStack(ModItems.ammo_4gauge_claw, 4), new Object[] { " B ", "BAB", " B ", 'B', ModItems.ammo_4gauge, 'A', ModItems.pellet_claws });
		addShapedRecipe(new ItemStack(ModItems.ammo_4gauge_vampire, 4), new Object[] { "ABA", "BAB", "ABA", 'B', ModItems.ammo_4gauge, 'A', ModItems.toothpicks });
		addShapedRecipe(new ItemStack(ModItems.ammo_4gauge_void, 4), new Object[] { " B ", "BAB", " B ", 'B', ModItems.ammo_4gauge, 'A', ModItems.pellet_charged });

		addShapedOreRecipe(new ItemStack(ModItems.hev_battery, 4), new Object[] { " W ", "IEI", "ICI", 'W', ModItems.wire_gold, 'I', ModItems.plate_polymer, 'E', ModItems.powder_power, 'C', "dustCobalt" });
		addShapedOreRecipe(new ItemStack(ModItems.hev_battery, 4), new Object[] { " W ", "ICI", "IEI", 'W', ModItems.wire_gold, 'I', ModItems.plate_polymer, 'E', ModItems.powder_power, 'C', "dustCobalt" });
		addShapedRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.chain), 8), new Object[] { "S", "S", "S", 'S', ModBlocks.steel_beam });

		addShapedOreRecipe(new ItemStack(ModBlocks.spinny_light), new Object[] { " G ", "GFG", "SRS", 'G', "blockGlass", 'F', ModItems.fuse, 'S', new ItemStack(Blocks.STONE_SLAB, 1, 0), 'R', "dustRedstone" });

		addShapedRecipe(new ItemStack(ModItems.jetpack_glider), new Object[] { "CSC", "DJD", "T T", 'J', ModItems.jetpack_boost, 'C', ModItems.circuit_targeting_tier5, 'D', ModItems.plate_desh, 'T', ModItems.thruster_nuclear, 'S', ModItems.motor });

		addShapedOreRecipe(new ItemStack(ModItems.sliding_blast_door_skin0), new Object[] { "SPS", "DPD", "SPS", 'P', Items.PAPER, 'D', "dye", 'S', "plateSteel" });
		addShapelessRecipe(new ItemStack(ModItems.sliding_blast_door_skin1), new Object[] { ModItems.sliding_blast_door_skin0 });
		addShapelessRecipe(new ItemStack(ModItems.sliding_blast_door_skin2), new Object[] { ModItems.sliding_blast_door_skin1 });
		addShapelessRecipe(new ItemStack(ModItems.sliding_blast_door_skin0), new Object[] { ModItems.sliding_blast_door_skin2 });

		//Peas
		addShapedRecipe(new ItemStack(ModItems.peas), new Object[] { " S ", "SNS", " S ", 'S', Items.WHEAT_SEEDS, 'N', Items.GOLD_NUGGET });

		//Liquidator Suit
		addShapedRecipe(new ItemStack(ModItems.liquidator_helmet, 1), new Object[] { "III", "CBC", "IFI", 'I', ModItems.plate_polymer, 'C', ModItems.cladding_lead, 'B', ModItems.hazmat_helmet_grey, 'F', ModItems.gas_mask_filter_mono });
		addShapedRecipe(new ItemStack(ModItems.liquidator_plate, 1), new Object[] { "ICI", "TBT", "ICI", 'I', ModItems.plate_polymer, 'C', ModItems.cladding_lead, 'B', ModItems.hazmat_plate_grey, 'T', ModItems.gas_canister });
		addShapedRecipe(new ItemStack(ModItems.liquidator_legs, 1), new Object[] { "III", "CBC", "I I", 'I', ModItems.plate_polymer, 'C', ModItems.cladding_lead, 'B', ModItems.hazmat_legs_grey });
		addShapedRecipe(new ItemStack(ModItems.liquidator_boots, 1), new Object[] { "ICI", "IBI", 'I', ModItems.plate_polymer, 'C', ModItems.cladding_lead, 'B', ModItems.hazmat_boots_grey });

		addShapedRecipe(new ItemStack(ModItems.gas_mask_filter_mono, 1), new Object[] { "ZZZ", "ZCZ", "ZZZ", 'Z', ModItems.nugget_zirconium, 'C', ModItems.catalyst_clay });

		addShapedOreRecipe(new ItemStack(ModItems.jshotgun, 1), new Object[] { "LPP", "SSW", "PPD", 'S', ModItems.gun_uboinik, 'P', "plateSteel", 'D', new ItemStack(Items.DYE, 1, EnumDyeColor.GREEN.getDyeDamage()), 'L', ModBlocks.spinny_light, 'W', ModItems.mechanism_rifle_2 });

		addShapedOreRecipe(new ItemStack(ModBlocks.machine_armor_table, 1), new Object[] { "PPP", "TCT", "TST", 'P', "plateSteel", 'T', "ingotTungsten", 'C', Blocks.CRAFTING_TABLE, 'S', "blockSteel" });

		addShapelessOreRecipe(new ItemStack(ModItems.ingot_steel_dusted, 1), new Object[] { "ingotSteel", "dustCoal" });

		addShapedOreRecipe(new ItemStack(ModItems.demon_core_open, 1), new Object[] { "PRP", " CS", "PRP", 'P', "plateTitanium", 'R', OreDictManager.getReflector(), 'C', ModItems.man_core, 'S', ModItems.screwdriver });
		addShapedOreRecipe(new ItemStack(ModBlocks.lamp_demon, 1), new Object[] { " D ", "S S", 'D', ModItems.demon_core_closed, 'S', "ingotSteel" });

		addShapedOreRecipe(new ItemStack(ModItems.crucible, 1, 3), new Object[] { "MEM", "YDY", "YCY", 'M', ModItems.ingot_meteorite_forged, 'E', ModItems.ingot_euphemium, 'Y', ModItems.billet_yharonite, 'D', ModItems.demon_core_closed, 'C', ModItems.ingot_chainsteel });

		for(int i = 0; i < ItemWasteLong.WasteClass.values().length; i++) {
			add1To9PairSameMeta(ModItems.nuclear_waste_long, ModItems.nuclear_waste_long_tiny, i);
			add1To9PairSameMeta(ModItems.nuclear_waste_long_depleted, ModItems.nuclear_waste_long_depleted_tiny, i);
		}

		for(int i = 0; i < ItemWasteShort.WasteClass.values().length; i++) {
			add1To9PairSameMeta(ModItems.nuclear_waste_short, ModItems.nuclear_waste_short_tiny, i);
			add1To9PairSameMeta(ModItems.nuclear_waste_short_depleted, ModItems.nuclear_waste_short_depleted_tiny, i);
		}

		add1To9Pair(ModBlocks.block_fallout, ModItems.fallout);
		addShapedRecipe(new ItemStack(ModBlocks.fallout, 2), new Object[] { "##", '#', ModItems.fallout });

		addShapedRecipe(new ItemStack(ModItems.ashglasses, 1), new Object[] { "I I", "GPG", 'I', ModItems.plate_polymer, 'G', ModBlocks.glass_ash, 'P', ModItems.ingot_polymer });

		addMineralSet(ModItems.nugget_pu_mix, ModItems.ingot_pu_mix, ModBlocks.block_pu_mix);
		add1To9Pair(ModItems.ingot_neptunium_fuel, ModItems.nugget_neptunium_fuel);

		addBillet(ModItems.billet_uranium, ModItems.nugget_uranium, "nuggetUranium");
		addBillet(ModItems.billet_u233, ModItems.nugget_u233, "nuggetUranium233", "tinyU233");
		addBillet(ModItems.billet_u235, ModItems.nugget_u235, "nuggetUranium235", "tinyU235");
		addBillet(ModItems.billet_u238, ModItems.nugget_u238, "nuggetUranium238", "tinyU238");
		addBillet(ModItems.billet_th232, ModItems.nugget_th232, "nuggetThorium232", "tinyTh232");
		addBillet(ModItems.billet_plutonium, ModItems.nugget_plutonium, "nuggetPlutonium");
		addBillet(ModItems.billet_pu238, ModItems.nugget_pu238, "nuggetPlutonium238", "tinyPu238");
		addBillet(ModItems.billet_pu239, ModItems.nugget_pu239, "nuggetPlutonium239", "tinyPu239");
		addBillet(ModItems.billet_pu240, ModItems.nugget_pu240, "nuggetPlutonium240", "tinyPu240");
		addBillet(ModItems.billet_pu241, ModItems.nugget_pu241, "nuggetPlutonium241", "tinyPu241");
		addBillet(ModItems.billet_pu_mix, ModItems.nugget_pu_mix);
		addBillet(ModItems.billet_am241, ModItems.nugget_am241, "nuggetAmericium241", "tinyAm241");
		addBillet(ModItems.billet_am242, ModItems.nugget_am242, "nuggetAmericium242", "tinyAm242");
		addBillet(ModItems.billet_am_mix, ModItems.nugget_am_mix);
		addBillet(ModItems.billet_neptunium, ModItems.nugget_neptunium, "nuggetNeptunium237", "tinyNp237");
		addBillet(ModItems.billet_polonium, ModItems.nugget_polonium, "nuggetPolonium");
		addBillet(ModItems.billet_technetium, ModItems.nugget_technetium, "nuggetTechnetium");
		addBillet(ModItems.billet_au198, ModItems.nugget_au198, "nuggetGold198");
		addBillet(ModItems.billet_schrabidium, ModItems.nugget_schrabidium, "nuggetSchrabidium");
		addBillet(ModItems.billet_solinium, ModItems.nugget_solinium, "nuggetSolinium");
		addBillet(ModItems.billet_uranium_fuel, ModItems.nugget_uranium_fuel);
		addBillet(ModItems.billet_thorium_fuel, ModItems.nugget_thorium_fuel);
		addBillet(ModItems.billet_plutonium_fuel, ModItems.nugget_plutonium_fuel);
		addBillet(ModItems.billet_neptunium_fuel, ModItems.nugget_neptunium_fuel);
		addBillet(ModItems.billet_mox_fuel, ModItems.nugget_mox_fuel);
		addBillet(ModItems.billet_les, ModItems.nugget_les);
		addBillet(ModItems.billet_schrabidium_fuel, ModItems.nugget_schrabidium_fuel);
		addBillet(ModItems.billet_hes, ModItems.nugget_hes);
		addBillet(ModItems.billet_australium, ModItems.nugget_australium, "nuggetAustralium");
		addBillet(ModItems.billet_australium_greater, ModItems.nugget_australium_greater);
		addBillet(ModItems.billet_australium_lesser, ModItems.nugget_australium_lesser);

		addShapelessRecipe(new ItemStack(ModItems.billet_thorium_fuel, 3), new Object[] { ModItems.billet_th232, ModItems.billet_th232, ModItems.billet_u233 });
		addShapelessOreRecipe(new ItemStack(ModItems.billet_thorium_fuel, 1), new Object[] { "nuggetThorium232", "nuggetThorium232", "nuggetThorium232", "nuggetThorium232", "nuggetUranium233", "nuggetUranium233" });
		addShapelessOreRecipe(new ItemStack(ModItems.billet_thorium_fuel, 1), new Object[] { "tinyTh232", "tinyTh232", "tinyTh232", "tinyTh232", "tinyU233", "tinyU233" });
		addShapelessRecipe(new ItemStack(ModItems.billet_uranium_fuel, 3), new Object[] { ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_u235 });
		addShapelessOreRecipe(new ItemStack(ModItems.billet_uranium_fuel, 1), new Object[] { "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium235", "nuggetUranium235" });
		addShapelessOreRecipe(new ItemStack(ModItems.billet_uranium_fuel, 1), new Object[] { "tinyU238", "tinyU238", "tinyU238", "tinyU238", "tinyU235", "tinyU235" });
		addShapelessRecipe(new ItemStack(ModItems.billet_plutonium_fuel, 3), new Object[] { ModItems.billet_u238, ModItems.billet_pu_mix, ModItems.billet_pu_mix });
		addShapelessOreRecipe(new ItemStack(ModItems.billet_plutonium_fuel, 1), new Object[] { ModItems.nugget_pu_mix, ModItems.nugget_pu_mix, ModItems.nugget_pu_mix, ModItems.nugget_pu_mix, "nuggetUranium238", "nuggetUranium238" });
		addShapelessOreRecipe(new ItemStack(ModItems.billet_plutonium_fuel, 1), new Object[] { ModItems.nugget_pu_mix, ModItems.nugget_pu_mix, ModItems.nugget_pu_mix, ModItems.nugget_pu_mix, "tinyU238", "tinyU238" });
		addShapelessRecipe(new ItemStack(ModItems.billet_pu_mix, 3), new Object[] { ModItems.billet_pu239, ModItems.billet_pu239, ModItems.billet_pu240 });
		addShapelessOreRecipe(new ItemStack(ModItems.billet_pu_mix, 1), new Object[] { "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium240", "nuggetPlutonium240" });
		addShapelessOreRecipe(new ItemStack(ModItems.billet_pu_mix, 1), new Object[] { "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu240", "tinyPu240" });
		addShapelessRecipe(new ItemStack(ModItems.billet_americium_fuel, 3), new Object[] { ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_am_mix });
		addShapelessOreRecipe(new ItemStack(ModItems.billet_americium_fuel, 1), new Object[] { ModItems.nugget_am_mix, ModItems.nugget_am_mix, "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238" });
		addShapelessOreRecipe(new ItemStack(ModItems.billet_americium_fuel, 1), new Object[] { ModItems.nugget_am_mix, ModItems.nugget_am_mix, "tinyU238", "tinyU238", "tinyU238", "tinyU238" });
		addShapelessRecipe(new ItemStack(ModItems.billet_am_mix, 3), new Object[] { ModItems.billet_am241, ModItems.billet_am242, ModItems.billet_am242 });
		addShapelessOreRecipe(new ItemStack(ModItems.billet_am_mix, 1), new Object[] { "nuggetAmericium241", "nuggetAmericium241", "nuggetAmericium242", "nuggetAmericium242", "nuggetAmericium242", "nuggetAmericium242" });
		addShapelessOreRecipe(new ItemStack(ModItems.billet_am_mix, 1), new Object[] { "tinyAm241", "tinyAm241", "tinyAm242", "tinyAm242", "tinyAm242", "tinyAm242" });
		addShapelessRecipe(new ItemStack(ModItems.billet_neptunium_fuel, 3), new Object[] { ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_neptunium });
		addShapelessOreRecipe(new ItemStack(ModItems.billet_neptunium_fuel, 1), new Object[] { "nuggetNeptunium237", "nuggetNeptunium237", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238" });
		addShapelessOreRecipe(new ItemStack(ModItems.billet_neptunium_fuel, 1), new Object[] { "tinyNp237", "tinyNp237", "tinyU238", "tinyU238", "tinyU238", "tinyU238" });
		addShapelessRecipe(new ItemStack(ModItems.billet_mox_fuel, 3), new Object[] { ModItems.billet_u238, ModItems.billet_u235, ModItems.billet_pu_mix });
		addShapelessOreRecipe(new ItemStack(ModItems.billet_mox_fuel, 1), new Object[] { ModItems.nugget_pu_mix, ModItems.nugget_pu_mix, "nuggetUranium238", "nuggetUranium238", "nuggetUranium235", "nuggetUranium235" });
		addShapelessOreRecipe(new ItemStack(ModItems.billet_mox_fuel, 1), new Object[] { ModItems.nugget_pu_mix, ModItems.nugget_pu_mix, "tinyU238", "tinyU238", "tinyU235", "tinyU235" });
		addShapelessRecipe(new ItemStack(ModItems.billet_schrabidium_fuel, 3), new Object[] { ModItems.billet_schrabidium, ModItems.billet_neptunium, ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium });
		addShapelessOreRecipe(new ItemStack(ModItems.billet_schrabidium_fuel, 1), new Object[] { ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, "nuggetNeptunium237", "nuggetNeptunium237", ModItems.nugget_beryllium, ModItems.nugget_beryllium });
		addShapelessOreRecipe(new ItemStack(ModItems.billet_schrabidium_fuel, 1), new Object[] { ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, "tinyNp237", "tinyNp237", ModItems.nugget_beryllium, ModItems.nugget_beryllium });
		addShapelessOreRecipe(new ItemStack(ModItems.billet_po210be, 1), new Object[] { "nuggetPolonium210", "nuggetPolonium210", "nuggetPolonium210", ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium });
		addShapelessOreRecipe(new ItemStack(ModItems.billet_pu238be, 1), new Object[] { "nuggetPlutonium238", "nuggetPlutonium238", "nuggetPlutonium238", ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium });
		addShapelessOreRecipe(new ItemStack(ModItems.billet_ra226be, 1), new Object[] { "nuggetRadium226", "nuggetRadium226", "nuggetRadium226", ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium });

		addShapelessRecipe(new ItemStack(ModItems.billet_uranium, 9), new Object[] { ModItems.billet_u235, ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_u238 });

		addShapelessRecipe(new ItemStack(ModItems.ingot_uranium, 2), new Object[] { ModItems.billet_uranium, ModItems.billet_uranium, ModItems.billet_uranium });
		addShapelessRecipe(new ItemStack(ModItems.ingot_u233, 2), new Object[] { ModItems.billet_u233, ModItems.billet_u233, ModItems.billet_u233 });
		addShapelessRecipe(new ItemStack(ModItems.ingot_u235, 2), new Object[] { ModItems.billet_u235, ModItems.billet_u235, ModItems.billet_u235 });
		addShapelessRecipe(new ItemStack(ModItems.ingot_u238, 2), new Object[] { ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_u238 });
		addShapelessRecipe(new ItemStack(ModItems.ingot_plutonium, 2), new Object[] { ModItems.billet_plutonium, ModItems.billet_plutonium, ModItems.billet_plutonium });
		addShapelessRecipe(new ItemStack(ModItems.ingot_pu238, 2), new Object[] { ModItems.billet_pu238, ModItems.billet_pu238, ModItems.billet_pu238 });
		addShapelessRecipe(new ItemStack(ModItems.ingot_pu239, 2), new Object[] { ModItems.billet_pu239, ModItems.billet_pu239, ModItems.billet_pu239 });
		addShapelessRecipe(new ItemStack(ModItems.ingot_pu240, 2), new Object[] { ModItems.billet_pu240, ModItems.billet_pu240, ModItems.billet_pu240 });
		addShapelessRecipe(new ItemStack(ModItems.ingot_pu241, 2), new Object[] { ModItems.billet_pu241, ModItems.billet_pu241, ModItems.billet_pu241 });
		addShapelessRecipe(new ItemStack(ModItems.ingot_pu_mix, 2), new Object[] { ModItems.billet_pu_mix, ModItems.billet_pu_mix, ModItems.billet_pu_mix });
		addShapelessRecipe(new ItemStack(ModItems.ingot_am241, 2), new Object[] { ModItems.billet_am241, ModItems.billet_am241, ModItems.billet_am241 });
		addShapelessRecipe(new ItemStack(ModItems.ingot_am242, 2), new Object[] { ModItems.billet_am242, ModItems.billet_am242, ModItems.billet_am242 });
		addShapelessRecipe(new ItemStack(ModItems.ingot_am_mix, 2), new Object[] { ModItems.billet_am_mix, ModItems.billet_am_mix, ModItems.billet_am_mix });
		addShapelessRecipe(new ItemStack(ModItems.ingot_uranium_fuel, 2), new Object[] { ModItems.billet_uranium_fuel, ModItems.billet_uranium_fuel, ModItems.billet_uranium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.ingot_plutonium_fuel, 2), new Object[] { ModItems.billet_plutonium_fuel, ModItems.billet_plutonium_fuel, ModItems.billet_plutonium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.ingot_americium_fuel, 2), new Object[] { ModItems.billet_americium_fuel, ModItems.billet_americium_fuel, ModItems.billet_americium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.ingot_mox_fuel, 2), new Object[] { ModItems.billet_mox_fuel, ModItems.billet_mox_fuel, ModItems.billet_mox_fuel });
		addShapelessRecipe(new ItemStack(ModItems.ingot_neptunium, 2), new Object[] { ModItems.billet_neptunium, ModItems.billet_neptunium, ModItems.billet_neptunium });
		addShapelessRecipe(new ItemStack(ModItems.ingot_neptunium_fuel, 2), new Object[] { ModItems.billet_neptunium_fuel, ModItems.billet_neptunium_fuel, ModItems.billet_neptunium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.ingot_polonium, 2), new Object[] { ModItems.billet_polonium, ModItems.billet_polonium, ModItems.billet_polonium });
		addShapelessRecipe(new ItemStack(ModItems.ingot_technetium, 2), new Object[] { ModItems.billet_technetium, ModItems.billet_technetium, ModItems.billet_technetium });
		addShapelessRecipe(new ItemStack(ModItems.ingot_schrabidium, 2), new Object[] { ModItems.billet_schrabidium, ModItems.billet_schrabidium, ModItems.billet_schrabidium });
		addShapelessRecipe(new ItemStack(ModItems.ingot_solinium, 2), new Object[] { ModItems.billet_solinium, ModItems.billet_solinium, ModItems.billet_solinium });
		addShapelessRecipe(new ItemStack(ModItems.ingot_les, 2), new Object[] { ModItems.billet_les, ModItems.billet_les, ModItems.billet_les });
		addShapelessRecipe(new ItemStack(ModItems.ingot_schrabidium_fuel, 2), new Object[] { ModItems.billet_schrabidium_fuel, ModItems.billet_schrabidium_fuel, ModItems.billet_schrabidium_fuel });
		addShapelessRecipe(new ItemStack(ModItems.ingot_hes, 2), new Object[] { ModItems.billet_hes, ModItems.billet_hes, ModItems.billet_hes });

		addShapelessRecipe(new ItemStack(ModItems.billet_balefire_gold, 1), new Object[] { ModItems.billet_au198, new IngredientContainsTag(ItemCell.getFullCell(ModForgeFluids.amat)), ModItems.pellet_charged });

		addShapedOreRecipe(new ItemStack(ModItems.ammo_dgk, 1), new Object[] { "LLL", "GGG", "CCC", 'L', "plateLead", 'G', ModItems.ballistite, 'C', "ingotCopper" });
		addShapedOreRecipe(new ItemStack(ModItems.ammo_dgk, 1), new Object[] { "LLL", "GGG", "CCC", 'L', "plateLead", 'G', ModItems.cordite, 'C', "ingotCopper" });

		addShapelessOreRecipe(new ItemStack(ModBlocks.ladder_red), new Object[] { "dyeRed", ModBlocks.ladder_steel });
		addShapelessRecipe(new ItemStack(ModBlocks.ladder_red_top), new Object[] { ModBlocks.ladder_red });
		addShapedRecipe(new ItemStack(ModBlocks.railing_normal), new Object[] { "   ", "SSS", "S S", 'S', ModBlocks.steel_beam });
		addShapedRecipe(new ItemStack(ModBlocks.railing_bend), new Object[] { "   ", "S  ", " S ", 'S', ModBlocks.railing_normal });
		addShapedRecipe(new ItemStack(ModBlocks.railing_end_self, 2), new Object[] { "   ", " SS", "   ", 'S', ModBlocks.railing_normal });
		addShapedRecipe(new ItemStack(ModBlocks.railing_end_floor, 2), new Object[] { "   ", " S ", " S ", 'S', ModBlocks.railing_normal });
		addShapelessRecipe(new ItemStack(ModBlocks.railing_end_flipped_self), new Object[] { ModBlocks.railing_end_self });
		addShapelessRecipe(new ItemStack(ModBlocks.railing_end_flipped_floor), new Object[] { ModBlocks.railing_end_floor });

		addShapedOreRecipe(new ItemStack(ModItems.assembly_nuke, 1), new Object[] { " WP", "SEP", " WP", 'W', ModItems.wire_aluminium, 'P', "plateSteel", 'S', ModItems.hull_small_steel, 'E', ModItems.ingot_semtex })	;
		
		//Mini Nuke
		addShapedRecipe(new ItemStack(ModItems.ammo_nuke, 1), new Object[] { "P", "S", "P", 'P', ModItems.nugget_pu239, 'S', ModItems.assembly_nuke });
		addShapedRecipe(new ItemStack(ModItems.ammo_nuke_low, 1), new Object[] { "P", "S", 'P', ModItems.nugget_pu239, 'S', ModItems.assembly_nuke });
		addShapedRecipe(new ItemStack(ModItems.ammo_nuke_high, 1), new Object[] { "PPP", "PSP", "PPP", 'P', ModItems.nugget_pu239, 'S', ModItems.assembly_nuke });
		addShapedRecipe(new ItemStack(ModItems.ammo_nuke_tots, 1), new Object[] { "PPP", "PIP", "PPP", 'P', ModItems.pellet_cluster, 'I', ModItems.ingot_pu239 });
		addShapedRecipe(new ItemStack(ModItems.ammo_nuke_safe, 1), new Object[] { "G", "N", 'G', Items.GLOWSTONE_DUST, 'N', ModItems.ammo_nuke_low });
		addShapedRecipe(new ItemStack(ModItems.ammo_nuke_pumpkin, 1), new Object[] { " T ", "TST", " T ", 'T', Blocks.TNT, 'S', ModItems.assembly_nuke });

		//MIRV recycling
		addShapelessRecipe(new ItemStack(ModItems.ammo_nuke, 6), new Object[] { ModItems.ammo_mirv });
		addShapelessRecipe(new ItemStack(ModItems.ammo_nuke_low, 6), new Object[] { ModItems.ammo_mirv_low });
		addShapelessRecipe(new ItemStack(ModItems.ammo_nuke_high, 6), new Object[] { ModItems.ammo_mirv_high });
		addShapelessRecipe(new ItemStack(ModItems.ammo_nuke_safe, 6), new Object[] { ModItems.ammo_mirv_safe });

		//MIRV
		addShapedRecipe(new ItemStack(ModItems.ammo_mirv, 1), new Object[] { "NNN", "CDS", "NNN", 'N', ModItems.ammo_nuke, 'C', ModItems.cap_aluminium, 'D', ModBlocks.det_cord, 'S', ModItems.hull_small_steel });
		addShapedRecipe(new ItemStack(ModItems.ammo_mirv_low, 1), new Object[] { "NNN", "CDS", "NNN", 'N', ModItems.ammo_nuke_low, 'C', ModItems.cap_aluminium, 'D', ModBlocks.det_cord, 'S', ModItems.hull_small_steel });
		addShapedRecipe(new ItemStack(ModItems.ammo_mirv_high, 1), new Object[] { "NNN", "CDS", "NNN", 'N', ModItems.ammo_nuke_high, 'C', ModItems.cap_aluminium, 'D', ModBlocks.det_cord, 'S', ModItems.hull_small_steel });
		addShapedRecipe(new ItemStack(ModItems.ammo_mirv_safe, 1), new Object[] { "NNN", "CDS", "NNN", 'N', ModItems.ammo_nuke_safe, 'C', ModItems.cap_aluminium, 'D', ModBlocks.det_cord, 'S', ModItems.hull_small_steel });
		//since the milk part of the recipe isn't realy present in the MIRV's effect, it might as well be replaced with something more sensible, i.e. duct tape
		addShapedRecipe(new ItemStack(ModItems.ammo_mirv_special, 1), new Object[] { "CBC", "MCM", "CBC", 'C', ModItems.canned_jizz, 'B', ModItems.gun_bf_ammo, 'M', ModItems.ammo_mirv });

		add1To9Pair(ModBlocks.block_semtex, ModItems.ingot_semtex);
		
		if(!GeneralConfig.enable528) {
			addShapedRecipe(new ItemStack(ModBlocks.struct_launcher_core, 1), new Object[] { "SCS", "SIS", "BEB", 'S', ModBlocks.steel_scaffold, 'I', Blocks.IRON_BARS, 'C', ModItems.circuit_targeting_tier3, 'B', ModBlocks.struct_launcher, 'E', ModBlocks.machine_battery });
			addShapedRecipe(new ItemStack(ModBlocks.struct_launcher_core_large, 1), new Object[] { "SIS", "ICI", "BEB", 'S', ModItems.circuit_red_copper, 'I', Blocks.IRON_BARS, 'C', ModItems.circuit_targeting_tier4, 'B', ModBlocks.struct_launcher, 'E', ModBlocks.machine_battery });
			addShapedRecipe(new ItemStack(ModBlocks.struct_soyuz_core, 1), new Object[] { "CUC", "TST", "TBT", 'C', ModItems.circuit_targeting_tier4, 'U', ModItems.upgrade_power_3, 'T', ModBlocks.barrel_steel, 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.machine_lithium_battery });
			addShapedOreRecipe(new ItemStack(ModItems.reactor_sensor, 1), new Object[] { "WPW", "CMC", "PPP", 'W', ModItems.wire_tungsten, 'P', "plateLead", 'C', ModItems.circuit_targeting_tier3, 'M', ModItems.magnetron });
			addShapedOreRecipe(new ItemStack(ModBlocks.reactor_ejector, 1), new Object[] { "CLC", "MHM", "CLC", 'C', ModBlocks.brick_concrete, 'L', "plateLead", 'M', ModItems.motor, 'H', ModBlocks.reactor_hatch });
			addShapedOreRecipe(new ItemStack(ModBlocks.reactor_inserter, 1), new Object[] { "CLC", "MHM", "CLC", 'C', ModBlocks.brick_concrete, 'L', "plateCopper", 'M', ModItems.motor, 'H', ModBlocks.reactor_hatch });
			addShapedRecipe(new ItemStack(ModBlocks.rbmk_console, 1), new Object[] { "PPP", "PCP", "PPP", 'C', ModItems.circuit_targeting_tier3, 'P', Items.POTATO });
			addShapedRecipe(new ItemStack(ModBlocks.hadron_core, 1), new Object[] { "CCC", "DSD", "CCC", 'C', ModBlocks.hadron_coil_alloy, 'D', ModBlocks.hadron_diode, 'S', ModItems.circuit_schrabidium });
			addShapedRecipe(new ItemStack(ModBlocks.rbmk_rod, 1), new Object[] { "C", "R", "C", 'C', ModItems.hull_small_steel, 'R', ModBlocks.rbmk_blank });
			addShapedRecipe(new ItemStack(ModBlocks.rbmk_rod_mod, 1), new Object[] { "BGB", "GRG", "BGB", 'G', ModBlocks.block_graphite, 'R', ModBlocks.rbmk_rod, 'B', ModItems.nugget_bismuth });
			addShapedRecipe(new ItemStack(ModBlocks.rbmk_boiler, 1), new Object[] { "CPC", "CRC", "CPC", 'C', ModItems.board_copper, 'P', ModItems.pipes_steel, 'R', ModBlocks.rbmk_blank });
		}

		if(GeneralConfig.enableBabyMode) {
			addShapelessRecipe(new ItemStack(ModItems.cordite, 3), new Object[] { ModItems.ballistite, Items.GUNPOWDER, new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE) });
			addShapelessRecipe(new ItemStack(ModItems.ingot_semtex, 3), new Object[] { Items.SLIME_BALL, Blocks.TNT, ModItems.niter });
			addShapelessRecipe(ItemFluidCanister.getFullCanister(ModForgeFluids.diesel), new Object[] { new IngredientContainsTag(ItemFluidCanister.getFullCanister(ModForgeFluids.oil)), Items.REDSTONE });

			addShapelessRecipe(new ItemStack(ModBlocks.ore_uranium, 1), new Object[] { ModBlocks.ore_uranium_scorched, Items.WATER_BUCKET });
			addShapedRecipe(new ItemStack(ModBlocks.ore_uranium, 8), new Object[] { "OOO", "OBO", "OOO", 'O', ModBlocks.ore_uranium_scorched, 'B', Items.WATER_BUCKET });
			addShapelessRecipe(new ItemStack(ModBlocks.ore_nether_uranium, 1), new Object[] { ModBlocks.ore_nether_uranium_scorched, Items.WATER_BUCKET });
			addShapedRecipe(new ItemStack(ModBlocks.ore_nether_uranium, 8), new Object[] { "OOO", "OBO", "OOO", 'O', ModBlocks.ore_nether_uranium_scorched, 'B', Items.WATER_BUCKET });
			addShapelessRecipe(new ItemStack(ModBlocks.ore_gneiss_uranium, 1), new Object[] { ModBlocks.ore_gneiss_uranium_scorched, Items.WATER_BUCKET });
			addShapedRecipe(new ItemStack(ModBlocks.ore_gneiss_uranium, 8), new Object[] { "OOO", "OBO", "OOO", 'O', ModBlocks.ore_gneiss_uranium_scorched, 'B', Items.WATER_BUCKET });

			addShapedOreRecipe(new ItemStack(ModItems.plate_iron, 4), new Object[] { "##", "##", '#', "ingotIron" });
			addShapedOreRecipe(new ItemStack(ModItems.plate_gold, 4), new Object[] { "##", "##", '#', "ingotGold" });
			addShapedOreRecipe(new ItemStack(ModItems.plate_aluminium, 4), new Object[] { "##", "##", '#', "ingotAluminum" });
			addShapedOreRecipe(new ItemStack(ModItems.plate_titanium, 4), new Object[] { "##", "##", '#', "ingotTitanium" });
			addShapedOreRecipe(new ItemStack(ModItems.plate_copper, 4), new Object[] { "##", "##", '#', "ingotCopper" });
			addShapedOreRecipe(new ItemStack(ModItems.plate_lead, 4), new Object[] { "##", "##", '#', "ingotLead" });
			addShapedOreRecipe(new ItemStack(ModItems.plate_steel, 4), new Object[] { "##", "##", '#', "ingotSteel" });
			addShapedOreRecipe(new ItemStack(ModItems.plate_schrabidium, 4), new Object[] { "##", "##", '#', "ingotSchrabidium" });
			addShapedRecipe(new ItemStack(ModItems.plate_advanced_alloy, 4), new Object[] { "##", "##", '#', ModItems.ingot_advanced_alloy });
			addShapedRecipe(new ItemStack(ModItems.plate_saturnite, 4), new Object[] { "##", "##", '#', ModItems.ingot_saturnite });
			addShapedRecipe(new ItemStack(ModItems.plate_combine_steel, 4), new Object[] { "##", "##", '#', ModItems.ingot_combine_steel });
			addShapedOreRecipe(new ItemStack(ModItems.neutron_reflector, 4), new Object[] { "##", "##", '#', "ingotTungsten" });

			addShapedOreRecipe(new ItemStack(ModItems.wire_aluminium, 16), new Object[] { "###", '#', "ingotAluminum" });
			addShapedOreRecipe(new ItemStack(ModItems.wire_copper, 16), new Object[] { "###", '#', "ingotCopper" });
			addShapedOreRecipe(new ItemStack(ModItems.wire_tungsten, 16), new Object[] { "###", '#', "ingotTungsten" });
			addShapedRecipe(new ItemStack(ModItems.wire_red_copper, 16), new Object[] { "###", '#', ModItems.ingot_red_copper });
			addShapedRecipe(new ItemStack(ModItems.wire_advanced_alloy, 16), new Object[] { "###", '#', ModItems.ingot_advanced_alloy });
			addShapedOreRecipe(new ItemStack(ModItems.wire_gold, 16), new Object[] { "###", '#', "ingotGold" });
			addShapedRecipe(new ItemStack(ModItems.wire_schrabidium, 16), new Object[] { "###", '#', ModItems.ingot_schrabidium });

			addShapedOreRecipe(new ItemStack(ModItems.book_of_), new Object[] { "BGB", "GAG", "BGB", 'B', ModItems.egg_balefire_shard, 'G', "ingotGold", 'A', Items.BOOK });
		}
	}

	public static void addSmelting(){
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_thorium), new ItemStack(ModItems.ingot_th232), 3.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_uranium), new ItemStack(ModItems.ingot_uranium), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_uranium_scorched), new ItemStack(ModItems.ingot_uranium), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_nether_uranium), new ItemStack(ModItems.ingot_uranium), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_nether_uranium_scorched), new ItemStack(ModItems.ingot_uranium), 12.0F);
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
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_cobalt), new ItemStack(ModItems.ingot_cobalt), 2.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_nether_cobalt), new ItemStack(ModItems.ingot_cobalt), 2.0F);

		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_iron), new ItemStack(Items.IRON_INGOT), 5.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_gold), new ItemStack(Items.GOLD_INGOT), 5.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_uranium), new ItemStack(ModItems.ingot_uranium), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_uranium_scorched), new ItemStack(ModItems.ingot_uranium), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_copper), new ItemStack(ModItems.ingot_copper), 5F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_lithium), new ItemStack(ModItems.lithium), 10F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_schrabidium), new ItemStack(ModItems.ingot_schrabidium), 256.0F);
		;

		GameRegistry.addSmelting(ModItems.casing_357, new ItemStack(ModItems.ingot_copper), 0.1F);
		GameRegistry.addSmelting(ModItems.casing_44, new ItemStack(ModItems.ingot_copper), 0.1F);
		GameRegistry.addSmelting(ModItems.casing_9, new ItemStack(ModItems.ingot_copper), 0.1F);
		GameRegistry.addSmelting(ModItems.casing_50, new ItemStack(ModItems.ingot_copper), 0.1F);
		GameRegistry.addSmelting(ModItems.casing_buckshot, new ItemStack(ModItems.ingot_copper), 0.1F);

		GameRegistry.addSmelting(ModItems.circuit_schrabidium, new ItemStack(ModItems.circuit_gold, 1), 1.0F);
		GameRegistry.addSmelting(ModItems.circuit_gold, new ItemStack(ModItems.circuit_red_copper, 1), 1.0F);
		GameRegistry.addSmelting(ModItems.circuit_red_copper, new ItemStack(ModItems.circuit_copper, 1), 1.0F);
		GameRegistry.addSmelting(ModItems.circuit_copper, new ItemStack(ModItems.circuit_aluminium, 1), 1.0F);

		GameRegistry.addSmelting(ModItems.powder_australium, new ItemStack(ModItems.ingot_australium), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_weidanium, new ItemStack(ModItems.ingot_weidanium), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_reiium, new ItemStack(ModItems.ingot_reiium), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_unobtainium, new ItemStack(ModItems.ingot_unobtainium), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_daffergon, new ItemStack(ModItems.ingot_daffergon), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_verticium, new ItemStack(ModItems.ingot_verticium), 5.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_australium), new ItemStack(ModItems.nugget_australium), 2.5F);

		GameRegistry.addSmelting(ModItems.powder_lead, new ItemStack(ModItems.ingot_lead), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_neptunium, new ItemStack(ModItems.ingot_neptunium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_polonium, new ItemStack(ModItems.ingot_polonium), 1.0F);
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
		GameRegistry.addSmelting(ModItems.powder_boron, new ItemStack(ModItems.ingot_boron), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_desh, new ItemStack(ModItems.ingot_desh), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_cobalt, new ItemStack(ModItems.ingot_cobalt), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_schrabidate, new ItemStack(ModItems.ingot_schrabidate), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_dineutronium, new ItemStack(ModItems.ingot_dineutronium), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_asbestos, new ItemStack(ModItems.ingot_asbestos), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_zirconium, new ItemStack(ModItems.ingot_zirconium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_tcalloy, new ItemStack(ModItems.ingot_tcalloy), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_au198, new ItemStack(ModItems.ingot_au198), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_tantalium, new ItemStack(ModItems.ingot_tantalium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_niobium, new ItemStack(ModItems.ingot_niobium), 1.0F);

		GameRegistry.addSmelting(ModItems.powder_coal, new ItemStack(ModItems.coke), 1.0F);
		GameRegistry.addSmelting(ModItems.briquette_lignite, new ItemStack(ModItems.coke), 1.0F);
		GameRegistry.addSmelting(ModItems.oil_tar, new ItemStack(ModItems.powder_coal), 1.0F);

		GameRegistry.addSmelting(ModItems.combine_scrap, new ItemStack(ModItems.ingot_combine_steel), 1.0F);

		GameRegistry.addSmelting(Items.BONE, new ItemStack(Items.SLIME_BALL, 3), 0.0F);
		GameRegistry.addSmelting(new ItemStack(Items.DYE, 1, 15), new ItemStack(Items.SLIME_BALL, 1), 0.0F);
		GameRegistry.addSmelting(new ItemStack(Blocks.GRAVEL, 1), new ItemStack(Blocks.COBBLESTONE, 1), 0.0F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.gravel_obsidian), new ItemStack(Blocks.OBSIDIAN), 0.0F);

		GameRegistry.addSmelting(ModItems.powder_euphemium, new ItemStack(ModItems.ingot_euphemium), 10.0F);
		GameRegistry.addSmelting(ModItems.powder_dineutronium, new ItemStack(ModItems.ingot_dineutronium), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_asbestos, new ItemStack(ModItems.ingot_asbestos), 1.0F);

		GameRegistry.addSmelting(ModItems.lodestone, new ItemStack(ModItems.crystal_iron, 1), 5.0F);
		GameRegistry.addSmelting(ModItems.crystal_iron, new ItemStack(Items.IRON_INGOT, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_gold, new ItemStack(Items.GOLD_INGOT, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_redstone, new ItemStack(Items.REDSTONE, 6), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_uranium, new ItemStack(ModItems.ingot_uranium, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_thorium, new ItemStack(ModItems.ingot_th232, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_plutonium, new ItemStack(ModItems.ingot_plutonium, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_titanium, new ItemStack(ModItems.ingot_titanium, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_sulfur, new ItemStack(ModItems.sulfur, 6), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_niter, new ItemStack(ModItems.niter, 6), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_copper, new ItemStack(ModItems.ingot_copper, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_tungsten, new ItemStack(ModItems.ingot_tungsten, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_aluminium, new ItemStack(ModItems.ingot_aluminium, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_fluorite, new ItemStack(ModItems.fluorite, 6), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_beryllium, new ItemStack(ModItems.ingot_beryllium, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_lead, new ItemStack(ModItems.ingot_lead, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_schrabidium, new ItemStack(ModItems.ingot_schrabidium, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_rare, new ItemStack(ModItems.powder_desh_mix, 1), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_phosphorus, new ItemStack(ModItems.powder_fire, 6), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_lithium, new ItemStack(ModItems.lithium, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_starmetal, new ItemStack(ModItems.ingot_starmetal, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_trixite, new ItemStack(ModItems.ingot_plutonium, 4), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_cinnebar, new ItemStack(ModItems.cinnebar, 4), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_cobalt, new ItemStack(ModItems.ingot_cobalt, 2), 2.0F);

		GameRegistry.addSmelting(new ItemStack(ModBlocks.gravel_diamond), new ItemStack(Items.DIAMOND), 3.0F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.sand_uranium), new ItemStack(ModBlocks.glass_uranium), 0.25F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.sand_polonium), new ItemStack(ModBlocks.glass_polonium), 0.75F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.waste_trinitite), new ItemStack(ModBlocks.glass_trinitite), 0.25F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.waste_trinitite_red), new ItemStack(ModBlocks.glass_trinitite), 0.25F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.sand_quartz), new ItemStack(ModBlocks.glass_quartz), 0.75F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.sand_boron), new ItemStack(ModBlocks.glass_boron), 0.25F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.sand_lead), new ItemStack(ModBlocks.glass_lead), 0.25F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.ash_digamma), new ItemStack(ModBlocks.glass_ash), 10F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.basalt), new ItemStack(ModBlocks.basalt_smooth), 0.1F);
		GameRegistry.addSmelting(ModItems.crystal_diamond, new ItemStack(Items.DIAMOND, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_schraranium, new ItemStack(ModItems.nugget_schrabidium, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.ingot_schraranium, new ItemStack(ModItems.nugget_schrabidium, 1), 2.0F);

		GameRegistry.addSmelting(ModItems.ingot_chainsteel, ItemHot.heatUp(new ItemStack(ModItems.ingot_chainsteel)), 1.0F);
		for(int i = 0; i < 10; i++)
			GameRegistry.addSmelting(new ItemStack(ModItems.ingot_steel_dusted, 1, i), ItemHot.heatUp(new ItemStack(ModItems.ingot_steel_dusted, 1, i)), 1.0F);
		GameRegistry.addSmelting(ModItems.ingot_meteorite, ItemHot.heatUp(new ItemStack(ModItems.ingot_meteorite)), 1.0F);
		GameRegistry.addSmelting(ModItems.ingot_meteorite_forged, ItemHot.heatUp(new ItemStack(ModItems.ingot_meteorite_forged)), 1.0F);
		GameRegistry.addSmelting(ModItems.blade_meteorite, ItemHot.heatUp(new ItemStack(ModItems.blade_meteorite)), 1.0F);
		GameRegistry.addSmelting(ModItems.meteorite_sword, ItemHot.heatUp(new ItemStack(ModItems.meteorite_sword_seared)), 1.0F);
	}

	public static void addBillet(Item billet, Item nugget, String... ore){
		for(String o : ore)
			addShapedOreRecipe(new ItemStack(billet), new Object[] { "###", "###", '#', o });

		addBillet(billet, nugget);
	}

	public static void addBillet(Item billet, Item nugget){
		addShapedRecipe(new ItemStack(billet), new Object[] { "###", "###", '#', nugget });
		addShapelessRecipe(new ItemStack(nugget, 6), new Object[] { billet });
	}

	//Fill rods with one billet
	public static void addRodBillet(Item billet, Item out){
		addShapelessRecipe(new ItemStack(out), new Object[] { ModItems.rod_empty, billet });
	}

	//Fill rods with two billets
	public static void addDualRodBillet(Item billet, Item out){
		addShapelessRecipe(new ItemStack(out), new Object[] { ModItems.rod_dual_empty, billet, billet });
	}

	//Fill rods with three billets
	public static void addQuadRodBillet(Item billet, Item out){
		addShapelessRecipe(new ItemStack(out), new Object[] { ModItems.rod_quad_empty, billet, billet, billet, billet });
	}

	//Fill rods with one billet + unload
	public static void addRodBilletUnload(Item billet, Item out){
		addShapelessRecipe(new ItemStack(out), new Object[] { ModItems.rod_empty, billet });
		addShapelessRecipe(new ItemStack(billet, 1), new Object[] { out });
	}

	//Fill rods with two billets + unload
	public static void addDualRodBilletUnload(Item billet, Item out){
		addShapelessRecipe(new ItemStack(out), new Object[] { ModItems.rod_dual_empty, billet, billet });
		addShapelessRecipe(new ItemStack(billet, 2), new Object[] { out });
	}

	//Fill rods with three billets + unload
	public static void addQuadRodBilletUnload(Item billet, Item out){
		addShapelessRecipe(new ItemStack(out), new Object[] { ModItems.rod_quad_empty, billet, billet, billet, billet });
		addShapelessRecipe(new ItemStack(billet, 4), new Object[] { out });
	}

	//Fill rods with 6 nuggets
	public static void addRBMKRod(Item billet, Item out){
		addShapelessRecipe(new ItemStack(out), new Object[] { ModItems.rbmk_fuel_empty, billet, billet, billet, billet, billet, billet, billet, billet });
	}

	//Bundled 1/9 recipes
	public static void add1To9Pair(Item one, Item nine){
		add1To9(new ItemStack(one), new ItemStack(nine, 9));
		add9To1(new ItemStack(nine), new ItemStack(one));
	}

	public static void add1To9Pair(Block one, Item nine){
		add1To9(new ItemStack(one), new ItemStack(nine, 9));
		add9To1(new ItemStack(nine), new ItemStack(one));
	}

	public static void add1To9PairSameMeta(Item one, Item nine, int meta){
		add1To9SameMeta(one, nine, meta);
		add9To1SameMeta(nine, one, meta);
	}

	public static void add1To9SameMeta(Item one, Item nine, int meta){
		add1To9(new ItemStack(one, 1, meta), new ItemStack(nine, 9, meta));
	}

	//Full set of nugget, ingot and block
	public static void addMineralSet(Item nugget, Item ingot, Block block){
		add1To9(new ItemStack(ingot), new ItemStack(nugget, 9));
		add9To1(new ItemStack(nugget), new ItemStack(ingot));
		add1To9(new ItemStack(block), new ItemStack(ingot, 9));
		add9To1(new ItemStack(ingot), new ItemStack(block));
	}

	public static void add9To1SameMeta(Item nine, Item one, int meta){
		add9To1(new ItemStack(nine, 1, meta), new ItemStack(one, 1, meta));
	}

	//Decompress one item into nine
	public static void add1To9(Block one, Item nine){
		add1To9(new ItemStack(one), new ItemStack(nine, 9));
	}

	public static void add1To9(Item one, Item nine){
		add1To9(new ItemStack(one), new ItemStack(nine, 9));
	}

	public static void add1To9(ItemStack one, ItemStack nine){
		addShapedRecipe(nine, new Object[] { "#", '#', one });
	}

	//Compress nine items into one
	public static void add9To1(Item nine, Block one){
		add9To1(new ItemStack(nine), new ItemStack(one));
	}

	public static void add9To1(Item nine, Item one){
		add9To1(new ItemStack(nine), new ItemStack(one));
	}

	public static void add9To1(ItemStack nine, ItemStack one){
		addShapedRecipe(one, new Object[] { "###", "###", "###", '#', nine });
	}

	//Fill rods with 6 nuggets
	public static void addRod(Item nugget, Item out){
		addShapelessRecipe(new ItemStack(out), new Object[] { ModItems.rod_empty, nugget, nugget, nugget, nugget, nugget, nugget });
	}

	//Fill rods with 12 nuggets
	public static void addDualRod(Item ingot, Item nugget, Item out){
		addShapelessRecipe(new ItemStack(out), new Object[] { ModItems.rod_dual_empty, ingot, nugget, nugget, nugget });
	}

	//Fill rods with 24 nuggets
	public static void addQuadRod(Item ingot, Item nugget, Item out){
		addShapelessRecipe(new ItemStack(out), new Object[] { ModItems.rod_quad_empty, ingot, ingot, nugget, nugget, nugget, nugget, nugget, nugget });
	}

	public static void addSword(Item ingot, Item sword){
		addShapedRecipe(new ItemStack(sword), new Object[] { "I", "I", "S", 'I', ingot, 'S', Items.STICK });
	}

	public static void addPickaxe(Item ingot, Item pick){
		addShapedRecipe(new ItemStack(pick), new Object[] { "III", " S ", " S ", 'I', ingot, 'S', Items.STICK });
	}

	public static void addAxe(Item ingot, Item axe){
		addShapedRecipe(new ItemStack(axe), new Object[] { "II", "IS", " S", 'I', ingot, 'S', Items.STICK });
	}

	public static void addShovel(Item ingot, Item shovel){
		addShapedRecipe(new ItemStack(shovel), new Object[] { "I", "S", "S", 'I', ingot, 'S', Items.STICK });
	}

	public static void addHoe(Item ingot, Item hoe){
		addShapedRecipe(new ItemStack(hoe), new Object[] { "II", " S", " S", 'I', ingot, 'S', Items.STICK });
	}

	public static void addShapedRecipe(ItemStack output, Object... args){
		if(!GeneralConfig.nonoredict)
			return;
		if(!GeneralConfig.shaped)
			return;
		CraftingHelper.ShapedPrimer primer = CraftingHelper.parseShaped(args);
		ResourceLocation loc = getRecipeName(output);
		ShapedRecipes recipe = new ShapedRecipes(output.getItem().getRegistryName().toString(), primer.width, primer.height, primer.input, output);
		recipe.setRegistryName(loc);
		hack.getRegistry().register(recipe);
	}

	public static void addShapelessRecipe(ItemStack output, Object... args){
		if(!GeneralConfig.nonoredict)
			return;
		if(!GeneralConfig.shapeless)
			return;
		ResourceLocation loc = getRecipeName(output);
		ShapelessRecipes recipe = new ShapelessRecipes(loc.getResourceDomain(), output, buildInput(args));
		recipe.setRegistryName(loc);
		hack.getRegistry().register(recipe);
	}

	public static void addShapedOreRecipe(ItemStack output, Object... args){
		if(!GeneralConfig.oredict)
			return;
		if(!GeneralConfig.shaped)
			return;
		ResourceLocation loc = getRecipeName(output);
		ShapedOreRecipe recipe = new ShapedOreRecipe(loc, output, args);
		recipe.setRegistryName(loc);
		hack.getRegistry().register(recipe);
	}

	public static void addShapelessOreRecipe(ItemStack output, Object... args){
		if(!GeneralConfig.oredict)
			return;
		if(!GeneralConfig.shapeless)
			return;
		ResourceLocation loc = getRecipeName(output);
		ShapelessOreRecipe recipe = new ShapelessOreRecipe(loc, output, args);
		recipe.setRegistryName(loc);
		hack.getRegistry().register(recipe);
	}

	public static ResourceLocation getRecipeName(ItemStack output){
		ResourceLocation loc = new ResourceLocation(RefStrings.MODID, output.getItem().getRegistryName().getResourcePath());
		int i = 0;
		ResourceLocation r_loc = loc;
		while(net.minecraft.item.crafting.CraftingManager.REGISTRY.containsKey(r_loc)) {
			i++;
			r_loc = new ResourceLocation(RefStrings.MODID, loc.getResourcePath() + "_" + i);
		}
		return r_loc;
	}

	public static NonNullList<Ingredient> buildInput(Object[] args){
		NonNullList<Ingredient> list = NonNullList.create();
		for(Object obj : args) {
			if(obj instanceof ItemFuelRod) {
				obj = new ItemStack((Item)obj);
			}
			if(obj instanceof Ingredient) {
				list.add((Ingredient)obj);
			} else {
				Ingredient i = CraftingHelper.getIngredient(obj);
				if(i == null) {
					i = Ingredient.EMPTY;
				}
				list.add(i);
			}
		}
		return list;
	}

	public static class IngredientContainsTag extends Ingredient {

		private final ItemStack stack;

		public IngredientContainsTag(ItemStack stack){
			super(stack);
			this.stack = stack;
		}

		@Override
		public boolean apply(ItemStack p_apply_1_){
			if(p_apply_1_ == null) {
				return false;
			} else {
				if(Library.areItemStacksCompatible(stack, p_apply_1_, false)) {
					return true;
				}
				return false;
			}
		}

		@Override
		public boolean isSimple(){
			return false;
		}
	}

	//B r u h why wasn't the constructor visible in the first place?
	public static class IngredientNBT2 extends IngredientNBT {

		public IngredientNBT2(ItemStack stack){
			super(stack);
		}

	}
}
