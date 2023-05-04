package com.hbm.inventory;

import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

import static com.hbm.inventory.OreDictManager.*;
import com.google.common.collect.Lists;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;
import com.hbm.items.machine.ItemChemistryTemplate;
import com.hbm.items.special.ItemCell;
import com.hbm.items.tool.ItemFluidCanister;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

//TODO: clean this shit up
@Spaghetti("everything")
public class ChemplantRecipes {

	public static HashMap<EnumChemistryTemplate, AStack[]> recipeItemInputs = new HashMap<>();
	public static HashMap<EnumChemistryTemplate, FluidStack[]> recipeFluidInputs = new HashMap<>();
	public static HashMap<EnumChemistryTemplate, AStack[]> recipeItemOutputs = new HashMap<>();
	public static HashMap<EnumChemistryTemplate, FluidStack[]> recipeFluidOutputs = new HashMap<>();
	public static HashMap<EnumChemistryTemplate, Integer> recipeDurations = new HashMap<>();

	public static void registerRecipes() {
		makeRecipe(EnumChemistryTemplate.FP_HEAVYOIL, null, new FluidStack[]{ new FluidStack(ModForgeFluids.heavyoil, 1000) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.bitumen, 300), new FluidStack(ModForgeFluids.smear, 700) }, 50);
		
		makeRecipe(EnumChemistryTemplate.FP_SMEAR, null, new FluidStack[]{ new FluidStack(ModForgeFluids.smear, 1000) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.heatingoil, 600), new FluidStack(ModForgeFluids.lubricant, 400) }, 50);
		
		makeRecipe(EnumChemistryTemplate.FP_NAPHTHA, null, new FluidStack[]{ new FluidStack(ModForgeFluids.naphtha, 1000) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.heatingoil, 400), new FluidStack(ModForgeFluids.diesel, 600) }, 50);
		
		makeRecipe(EnumChemistryTemplate.FP_LIGHTOIL, null, new FluidStack[]{ new FluidStack(ModForgeFluids.lightoil, 1000) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.diesel, 400), new FluidStack(ModForgeFluids.kerosene, 600) }, 50);
		
		makeRecipe(EnumChemistryTemplate.FR_REOIL, null, new FluidStack[]{ new FluidStack(ModForgeFluids.smear, 1000) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.reclaimed, 800) }, 30);
		
		makeRecipe(EnumChemistryTemplate.FR_PETROIL, null, new FluidStack[]{ new FluidStack(ModForgeFluids.reclaimed, 800), new FluidStack(ModForgeFluids.lubricant, 200) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.petroil, 1000) }, 30);
		
		makeRecipe(EnumChemistryTemplate.FC_BITUMEN, null, new FluidStack[]{ new FluidStack(ModForgeFluids.bitumen, 1200), new FluidStack(ModForgeFluids.steam, 2400) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.oil, 1000), new FluidStack(ModForgeFluids.petroleum, 200) }, 100);
		
		makeRecipe(EnumChemistryTemplate.FC_I_NAPHTHA, null, new FluidStack[]{ new FluidStack(ModForgeFluids.smear, 1400), new FluidStack(FluidRegistry.WATER, 800) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.naphtha, 800) }, 150);
		
		makeRecipe(EnumChemistryTemplate.FC_GAS_PETROLEUM, null, new FluidStack[]{ new FluidStack(ModForgeFluids.gas, 1800), new FluidStack(FluidRegistry.WATER, 1200) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.petroleum, 800) }, 100);
		
		makeRecipe(EnumChemistryTemplate.FC_DIESEL_KEROSENE, null, new FluidStack[]{ new FluidStack(ModForgeFluids.diesel, 1200), new FluidStack(ModForgeFluids.steam, 2000) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.kerosene, 400) }, 150);
		
		makeRecipe(EnumChemistryTemplate.FC_KEROSENE_PETROLEUM, null, new FluidStack[]{ new FluidStack(ModForgeFluids.kerosene, 1400), new FluidStack(ModForgeFluids.steam, 2000) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.petroleum, 800) }, 150);
		
		makeRecipe(EnumChemistryTemplate.CC_OIL, new AStack[] { new OreDictStack(COAL.dust(), 8), new ComparableStack(ModItems.oil_tar, 4) }, new FluidStack[]{ new FluidStack(ModForgeFluids.oil, 600), new FluidStack(ModForgeFluids.steam, 1400) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.oil, 2000) }, 150);
		
		makeRecipe(EnumChemistryTemplate.CC_I, new AStack[] { new OreDictStack(COAL.dust(), 6), new ComparableStack(ModItems.oil_tar, 4) }, new FluidStack[]{ new FluidStack(ModForgeFluids.smear, 800), new FluidStack(FluidRegistry.WATER, 1800) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.smear, 1600) }, 200);
		
		makeRecipe(EnumChemistryTemplate.CC_HEATING, new AStack[] { new OreDictStack(COAL.dust(), 6), new ComparableStack(ModItems.oil_tar, 4) }, new FluidStack[]{ new FluidStack(ModForgeFluids.heatingoil, 800), new FluidStack(ModForgeFluids.steam, 2000) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.heatingoil, 1800) }, 250);
		
		makeRecipe(EnumChemistryTemplate.CC_HEAVY, new AStack[] { new OreDictStack(COAL.dust(), 8), new ComparableStack(ModItems.oil_tar, 4) }, new FluidStack[]{ new FluidStack(ModForgeFluids.heavyoil, 600), new FluidStack(FluidRegistry.WATER, 1400) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.heavyoil, 1800) }, 200);
		
		makeRecipe(EnumChemistryTemplate.CC_NAPHTHA, new AStack[] { new OreDictStack(COAL.dust(), 8), new ComparableStack(ModItems.oil_tar, 4) }, new FluidStack[]{ new FluidStack(ModForgeFluids.naphtha, 1200), new FluidStack(ModForgeFluids.steam, 2400) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.naphtha, 2000) }, 300);
		
		makeRecipe(EnumChemistryTemplate.SF_OIL, null, new FluidStack[]{ new FluidStack(ModForgeFluids.oil, 350) }, new AStack[] { new ComparableStack(ModItems.solid_fuel, 2) }, null, 20);
		
		makeRecipe(EnumChemistryTemplate.SF_HEAVYOIL, null, new FluidStack[]{ new FluidStack(ModForgeFluids.heavyoil, 250) }, new AStack[] { new ComparableStack(ModItems.oil_tar, 2) }, null, 20);
		
		makeRecipe(EnumChemistryTemplate.SF_SMEAR, null, new FluidStack[]{ new FluidStack(ModForgeFluids.smear, 200) }, new AStack[] { new ComparableStack(ModItems.oil_tar, 2) }, null, 20);
		
		makeRecipe(EnumChemistryTemplate.SF_HEATINGOIL, null, new FluidStack[]{ new FluidStack(ModForgeFluids.heatingoil, 100) }, new AStack[] { new ComparableStack(ModItems.solid_fuel, 2) }, null, 20);
		
		makeRecipe(EnumChemistryTemplate.SF_RECLAIMED, null, new FluidStack[]{ new FluidStack(ModForgeFluids.reclaimed, 200) }, new AStack[] { new ComparableStack(ModItems.solid_fuel, 2) }, null, 20);
		
		makeRecipe(EnumChemistryTemplate.SF_PETROIL, null, new FluidStack[]{ new FluidStack(ModForgeFluids.petroil, 250) }, new AStack[] { new ComparableStack(ModItems.solid_fuel, 2) }, null, 20);
		
		makeRecipe(EnumChemistryTemplate.SF_LUBRICANT, null, new FluidStack[]{ new FluidStack(ModForgeFluids.lubricant, 250) }, new AStack[] { new ComparableStack(ModItems.solid_fuel, 2) }, null, 20);
		
		makeRecipe(EnumChemistryTemplate.SF_NAPHTHA, null, new FluidStack[]{ new FluidStack(ModForgeFluids.naphtha, 300) }, new AStack[] { new ComparableStack(ModItems.solid_fuel, 2) }, null, 20);
		
		makeRecipe(EnumChemistryTemplate.SF_DIESEL, null, new FluidStack[]{ new FluidStack(ModForgeFluids.diesel, 400) }, new AStack[] { new ComparableStack(ModItems.solid_fuel, 2) }, null, 20);
		
		makeRecipe(EnumChemistryTemplate.SF_LIGHTOIL, null, new FluidStack[]{ new FluidStack(ModForgeFluids.lightoil, 450) }, new AStack[] { new ComparableStack(ModItems.solid_fuel, 2) }, null, 20);
		
		makeRecipe(EnumChemistryTemplate.SF_KEROSENE, null, new FluidStack[]{ new FluidStack(ModForgeFluids.kerosene, 550) }, new AStack[] { new ComparableStack(ModItems.solid_fuel, 2) }, null, 20);
		
		makeRecipe(EnumChemistryTemplate.SF_GAS, null, new FluidStack[]{ new FluidStack(ModForgeFluids.gas, 750) }, new AStack[] { new ComparableStack(ModItems.solid_fuel, 2) }, null, 20);
		
		makeRecipe(EnumChemistryTemplate.SF_PETROLEUM, null, new FluidStack[]{ new FluidStack(ModForgeFluids.petroleum, 600) }, new AStack[] { new ComparableStack(ModItems.solid_fuel, 2) }, null, 20);
		
		makeRecipe(EnumChemistryTemplate.SF_BIOGAS, null, new FluidStack[]{ new FluidStack(ModForgeFluids.biogas, 400) }, new AStack[] { new ComparableStack(ModItems.solid_fuel, 2) }, null, 20);
		
		makeRecipe(EnumChemistryTemplate.SF_BIOFUEL, null, new FluidStack[]{ new FluidStack(ModForgeFluids.biofuel, 300) }, new AStack[] { new ComparableStack(ModItems.solid_fuel, 2) }, null, 20);
		
		makeRecipe(EnumChemistryTemplate.BP_BIOGAS, new AStack[] { new ComparableStack(ModItems.biomass, 16) }, null, null, new FluidStack[]{ new FluidStack(ModForgeFluids.biogas, 4000) }, 200);
		
		makeRecipe(EnumChemistryTemplate.BP_BIOFUEL, null, new FluidStack[]{ new FluidStack(ModForgeFluids.biogas, 2000) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.biofuel, 1000) }, 100);
		
		makeRecipe(EnumChemistryTemplate.OIL_SAND, new AStack[] { new ComparableStack(ModBlocks.ore_oil_sand, 16), new OreDictStack(KEY_OIL_TAR, 1) }, null, new AStack[] { new ComparableStack(Blocks.SAND, 16) }, new FluidStack[]{ new FluidStack(ModForgeFluids.bitumen, 1000) }, 200);
		
		makeRecipe(EnumChemistryTemplate.ASPHALT, new AStack[] { new OreDictStack(KEY_GRAVEL, 2), new OreDictStack(KEY_SAND, 6) }, new FluidStack[]{ new FluidStack(ModForgeFluids.bitumen, 8000) }, new AStack[] { new ComparableStack(ModBlocks.asphalt, 16) }, null, 100);
		
		makeRecipe(EnumChemistryTemplate.GNEISS_GAS, new AStack[] { new ComparableStack(ModBlocks.ore_gneiss_gas, 2) }, new FluidStack[]{ new FluidStack(ModForgeFluids.superhotsteam, 1000) }, new AStack[]{ new ComparableStack(ModBlocks.stone_gneiss, 2) }, new FluidStack[]{ new FluidStack(ModForgeFluids.gas, 1000), new FluidStack(ModForgeFluids.spentsteam, 1000) }, 100);
		
		makeRecipe(EnumChemistryTemplate.COOLANT, new AStack[] { new OreDictStack(KNO.dust(), 1) }, new FluidStack[]{ new FluidStack(FluidRegistry.WATER, 1800) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.coolant, 2000) }, 50);
		
		makeRecipe(EnumChemistryTemplate.CRYOGEL, new AStack[] { new ComparableStack(ModItems.powder_ice, 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.coolant, 1800) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.cryogel, 2000) }, 50);
		
		if(GeneralConfig.enableBabyMode) {
			makeRecipe(EnumChemistryTemplate.DESH, new AStack[] { new ComparableStack(ModItems.powder_desh_mix, 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.lightoil, 200) }, new AStack[] { new ComparableStack(ModItems.ingot_desh, 1) }, null, 300);
		} else {
			makeRecipe(EnumChemistryTemplate.DESH, new AStack[] { new ComparableStack(ModItems.powder_desh_mix, 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.mercury, 200), new FluidStack(ModForgeFluids.lightoil, 200) }, new AStack[] { new ComparableStack(ModItems.ingot_desh, 1) }, null, 300);
		}
		
		makeRecipe(EnumChemistryTemplate.NITAN, new AStack[] { new ComparableStack(ModItems.powder_nitan_mix, 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.kerosene, 600), new FluidStack(ModForgeFluids.mercury, 200) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.nitan, 1000) }, 50);
		
		makeRecipe(EnumChemistryTemplate.PEROXIDE, null, new FluidStack[]{ new FluidStack(FluidRegistry.WATER, 1000) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.acid, 800) }, 50);
		
		makeRecipe(EnumChemistryTemplate.SULFURIC_ACID, new AStack[] { new OreDictStack(S.dust()) }, new FluidStack[]{ new FluidStack(ModForgeFluids.acid, 800) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.sulfuric_acid, 500) }, 50);
		
		makeRecipe(EnumChemistryTemplate.CIRCUIT_4, new AStack[] { new ComparableStack(ModItems.circuit_red_copper, 1), new ComparableStack(ModItems.wire_gold, 4), new OreDictStack(LAPIS.dust(), 1), new OreDictStack(ANY_PLASTIC.ingot(), 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.acid, 400) }, new AStack[] { new ComparableStack(ModItems.circuit_gold, 1) }, null, 200);
		
		makeRecipe(EnumChemistryTemplate.CIRCUIT_5, new AStack[] { new ComparableStack(ModItems.circuit_gold, 1), new ComparableStack(ModItems.wire_schrabidium, 4), new OreDictStack(DIAMOND.dust(), 1), new OreDictStack(DESH.ingot(), 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.acid, 800), new FluidStack(ModForgeFluids.petroleum, 400) }, new AStack[] { new ComparableStack(ModItems.circuit_schrabidium, 1) }, null, 250);
		
		makeRecipe(EnumChemistryTemplate.POLYMER, new AStack[] { new OreDictStack(COAL.gem(), 2), new OreDictStack(F.dust(), 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.petroleum, 600) }, new AStack[] { new ComparableStack(ModItems.ingot_polymer, 1) }, null, 100);
		
		makeRecipe(EnumChemistryTemplate.BAKELITE, null, new FluidStack[]{ new FluidStack(ModForgeFluids.aromatics, 500), new FluidStack(ModForgeFluids.petroleum, 500) }, new AStack[] { new ComparableStack(ModItems.ingot_bakelite, 1) }, null, 100);
		
		makeRecipe(EnumChemistryTemplate.RUBBER, new AStack[] { new OreDictStack(S.dust(), 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.unsaturateds, 500) }, new AStack[] { new ComparableStack(ModItems.ingot_rubber, 1) }, null, 100);
		
		makeRecipe(EnumChemistryTemplate.DYNAMITE, new AStack[] { new ComparableStack(Items.SUGAR), new OreDictStack(KNO.dust()), new OreDictStack("sand") }, new FluidStack[]{ new FluidStack(ModForgeFluids.sulfuric_acid, 1000) }, new AStack[] { new ComparableStack(ModItems.ball_dynamite, 2) }, null, 50);
		
		makeRecipe(EnumChemistryTemplate.TNT, new AStack[] { new OreDictStack(KNO.dust(), 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.aromatics, 500) }, new AStack[] { new ComparableStack(ModItems.ball_tnt, 4) }, null, 150);

		makeRecipe(EnumChemistryTemplate.C4, new AStack[] { new OreDictStack(KNO.dust(), 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.unsaturateds, 500) }, new AStack[] { new ComparableStack(ModItems.ingot_c4, 4) }, null, 150);

		makeRecipe(EnumChemistryTemplate.DEUTERIUM, new AStack[] { new OreDictStack(S.dust(), 2) }, new FluidStack[]{ new FluidStack(FluidRegistry.WATER, 4000) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.deuterium, 500) }, 200);
		
		makeRecipe(EnumChemistryTemplate.STEAM, null, new FluidStack[]{ new FluidStack(FluidRegistry.WATER, 1000) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.steam, 1000) }, 20);
		
		makeRecipe(EnumChemistryTemplate.ALGE, new AStack[] { new ComparableStack(ModItems.biomass, 4), new OreDictStack(KNO.dust(), 1), new OreDictStack(COAL.dustTiny(), 2) }, new FluidStack[]{ new FluidStack(FluidRegistry.WATER, 8000) }, new AStack[] { new ComparableStack(ModItems.biomass, 36) }, new FluidStack[]{ new FluidStack(FluidRegistry.WATER, 1000) }, 20*60);
		
		makeRecipe(EnumChemistryTemplate.YELLOWCAKE, new AStack[] { new OreDictStack(U.dust(), 1), new OreDictStack(S.dust(), 2) }, new FluidStack[]{ new FluidStack(ModForgeFluids.acid, 500) }, new AStack[] { new ComparableStack(ModItems.powder_yellowcake, 1) }, null, 250);
		
		makeRecipe(EnumChemistryTemplate.UF6, new AStack[] { new ComparableStack(ModItems.powder_yellowcake, 1), new OreDictStack(F.dust(), 3) }, new FluidStack[]{ new FluidStack(FluidRegistry.WATER, 1000) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.uf6, 1000) }, 100);
		
		makeRecipe(EnumChemistryTemplate.PUF6, new AStack[] { new OreDictStack(PU.dust(), 1), new OreDictStack(F.dust(), 3) }, new FluidStack[]{ new FluidStack(FluidRegistry.WATER, 1000) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.puf6, 1000) }, 150);
		
		makeRecipe(EnumChemistryTemplate.SAS3, new AStack[] { new OreDictStack(SA326.dust(), 1), new OreDictStack(S.dust(), 2) }, new FluidStack[]{ new FluidStack(ModForgeFluids.acid, 2000) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.sas3, 1000) }, 200);
		
		makeRecipe(EnumChemistryTemplate.DYN_SCHRAB, new AStack[] {new ComparableStack(ModItems.particle_higgs, 1), new OreDictStack(U.ingot(), 8), new ComparableStack(ModItems.catalyst_clay, 8) }, new FluidStack[]{ new FluidStack(ModForgeFluids.coolant, 1000) }, new AStack[] { new ComparableStack(ModItems.particle_empty, 1), new ComparableStack(ModItems.ingot_schrabidium, 8) }, new FluidStack[]{ new FluidStack(ModForgeFluids.watz, 50) }, 20*30);
		
		makeRecipe(EnumChemistryTemplate.DYN_STR, new AStack[] {new ComparableStack(ModItems.particle_strange, 1), new ComparableStack(ModItems.nugget_radspice, 8), new ComparableStack(ModItems.catalyst_clay, 8) }, new FluidStack[]{ new FluidStack(ModForgeFluids.cryogel, 1000) }, new AStack[] { new ComparableStack(ModItems.particle_empty, 1), new ComparableStack(ModItems.egg_balefire, 8) }, new FluidStack[]{ new FluidStack(ModForgeFluids.watz, 200) }, 20*60);
		
		makeRecipe(EnumChemistryTemplate.DYN_EUPH, new AStack[] {new ComparableStack(ModItems.particle_dark, 1), new OreDictStack(SA327.ingot(), 8), new ComparableStack(ModItems.catalyst_clay, 16) }, new FluidStack[]{ new FluidStack(ModForgeFluids.cryogel, 2000) }, new AStack[] { new ComparableStack(ModItems.particle_empty, 1), new ComparableStack(ModItems.ingot_euphemium, 8) }, new FluidStack[]{ new FluidStack(ModForgeFluids.watz, 100) }, 20*60*2);
		
		makeRecipe(EnumChemistryTemplate.DYN_DNT, new AStack[] {new ComparableStack(ModItems.particle_sparkticle, 1), new OreDictStack(SBD.ingot(), 8), new ComparableStack(ModItems.catalyst_clay, 32) }, new FluidStack[]{ new FluidStack(ModForgeFluids.cryogel, 4000) }, new AStack[] { new ComparableStack(ModItems.particle_empty, 1), new ComparableStack(ModItems.ingot_dineutronium, 8) }, new FluidStack[]{ new FluidStack(ModForgeFluids.watz, 400) }, 20*60*5);
		
		makeRecipe(EnumChemistryTemplate.DYN_EL, new AStack[] {new ComparableStack(ModItems.particle_digamma, 1), new OreDictStack(DNT.ingot(), 16), new ComparableStack(ModItems.catalyst_clay, 64) }, new FluidStack[]{ new FluidStack(ModForgeFluids.cryogel, 8000) }, new AStack[] { new ComparableStack(ModItems.particle_empty, 1), new ComparableStack(ModItems.ingot_electronium, 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.watz, 800) }, 20*60*10);
		
		makeRecipe(EnumChemistryTemplate.CORDITE, new AStack[] {new OreDictStack(KNO.dust(), 2), new OreDictStack(KEY_PLANKS, 1), new ComparableStack(Items.SUGAR, 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.heatingoil, 200) }, new AStack[] { new ComparableStack(ModItems.cordite, 4) }, null, 40);
		
		makeRecipe(EnumChemistryTemplate.KEVLAR, new AStack[] {new OreDictStack(KNO.dust(), 2), new OreDictStack(KEY_BRICK, 1), new OreDictStack(COAL.gem(), 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.petroleum, 100) }, new AStack[] { new ComparableStack(ModItems.plate_kevlar, 4) }, null, 40);
		
		makeRecipe(EnumChemistryTemplate.CONCRETE, new AStack[] { new OreDictStack(KEY_GRAVEL, 8), new OreDictStack(KEY_SAND, 8) }, new FluidStack[]{ new FluidStack(FluidRegistry.WATER, 2000) }, new AStack[] { new ComparableStack(ModBlocks.concrete_smooth, 16) }, null, 100);
		
		makeRecipe(EnumChemistryTemplate.CONCRETE_ASBESTOS, new AStack[] { new OreDictStack(KEY_GRAVEL, 2), new OreDictStack(KEY_SAND, 2), new OreDictStack(ASBESTOS.ingot(), 4) }, new FluidStack[]{ new FluidStack(FluidRegistry.WATER, 2000) }, new AStack[] { new ComparableStack(ModBlocks.concrete_asbestos, 16) }, null, 100);
		
		makeRecipe(EnumChemistryTemplate.DUCRETE, new AStack[] { new OreDictStack(KEY_SAND, 8), new OreDictStack(U238.billet(), 2), new ComparableStack(Items.CLAY_BALL, 4) }, new FluidStack[]{ new FluidStack(FluidRegistry.WATER, 2000) }, new AStack[] { new ComparableStack(ModBlocks.ducrete_smooth, 8) }, null, 200);
		
		makeRecipe(EnumChemistryTemplate.SOLID_FUEL, new AStack[] {new ComparableStack(ModItems.solid_fuel, 2), new OreDictStack(KNO.dust(), 1), new OreDictStack(REDSTONE.dust(), 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.petroleum, 200) }, new AStack[] { new ComparableStack(ModItems.rocket_fuel, 4) }, null, 200);
		
		makeRecipe(EnumChemistryTemplate.ELECTROLYSIS, null, new FluidStack[]{ new FluidStack(FluidRegistry.WATER, 8000) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.hydrogen, 800), new FluidStack(ModForgeFluids.oxygen, 800) }, 150);
		
		makeRecipe(EnumChemistryTemplate.XENON, null, null, null, new FluidStack[]{ new FluidStack(ModForgeFluids.xenon, 50) }, 300);
		
		makeRecipe(EnumChemistryTemplate.XENON_OXY, null, new FluidStack[]{ new FluidStack(ModForgeFluids.oxygen, 250) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.xenon, 50) }, 20);
		
		makeRecipe(EnumChemistryTemplate.SATURN, new AStack[] {new OreDictStack(DURA.dust(), 1), new OreDictStack(P_RED.dust(), 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.acid, 100), new FluidStack(ModForgeFluids.mercury, 50) }, new AStack[] { new ComparableStack(ModItems.ingot_saturnite, 2) }, null, 60);
		
		makeRecipe(EnumChemistryTemplate.BALEFIRE, new AStack[] {new ComparableStack(ModItems.egg_balefire_shard, 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.kerosene, 6000) }, new AStack[] { new ComparableStack(ModItems.powder_balefire, 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.balefire, 8000) }, 100);
		
		makeRecipe(EnumChemistryTemplate.SCHRABIDIC, new AStack[] {new ComparableStack(ModItems.pellet_charged, 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.sas3, 8000), new FluidStack(ModForgeFluids.acid, 6000) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.schrabidic, 16000) }, 100);
		
		makeRecipe(EnumChemistryTemplate.SCHRABIDATE, new AStack[] {new OreDictStack(IRON.dust(), 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.schrabidic, 250) }, new AStack[] { new ComparableStack(ModItems.powder_schrabidate, 1) }, null, 600);
		
		makeRecipe(EnumChemistryTemplate.COLTAN_CLEANING, new AStack[] {new OreDictStack(COLTAN.dust(), 2), new OreDictStack(COAL.dust(), 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.acid, 250), new FluidStack(ModForgeFluids.hydrogen, 500) }, new AStack[] { new ComparableStack(ModItems.powder_coltan, 1), new ComparableStack(ModItems.powder_niobium, 1), new ComparableStack(ModItems.dust, 1) }, new FluidStack[]{ new FluidStack(FluidRegistry.WATER, 500) }, 60);
		
		makeRecipe(EnumChemistryTemplate.COLTAN_PAIN, new AStack[] {new ComparableStack(ModItems.powder_coltan, 1), new OreDictStack(F.dust(), 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.gas, 1000), new FluidStack(ModForgeFluids.oxygen, 500) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.pain, 1000) }, 120);
		
		makeRecipe(EnumChemistryTemplate.COLTAN_CRYSTAL, null, new FluidStack[]{ new FluidStack(ModForgeFluids.pain, 1000), new FluidStack(ModForgeFluids.acid, 500) }, new AStack[] { new ComparableStack(ModItems.gem_tantalium, 1), new ComparableStack(ModItems.dust, 3) }, new FluidStack[]{ new FluidStack(FluidRegistry.WATER, 250) }, 80);
		
		makeRecipe(EnumChemistryTemplate.ARSENIC, new AStack[] { new ComparableStack(ModItems.scrap_oil, 64), new ComparableStack(ModItems.scrap_oil, 64), new ComparableStack(ModItems.scrap_oil, 64), new ComparableStack(ModItems.scrap_oil, 64) }, new FluidStack[]{ new FluidStack(ModForgeFluids.sulfuric_acid, 1000) }, new AStack[] { new ComparableStack(ModItems.nugget_arsenic, 1), new ComparableStack(ModItems.sulfur, 2) }, new FluidStack[]{ new FluidStack(ModForgeFluids.heavyoil, 1500) }, 1200);

		makeRecipe(EnumChemistryTemplate.VIT_LIQUID, new AStack[] {new ComparableStack(ModBlocks.sand_lead, 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.wastefluid, 1000) }, new AStack[] {new ComparableStack(ModItems.nuclear_waste_vitrified, 1) }, null, 100);
		
		makeRecipe(EnumChemistryTemplate.VIT_GAS, new AStack[] {new ComparableStack(ModBlocks.sand_lead, 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.wastegas, 1000) }, new AStack[] {new ComparableStack(ModItems.nuclear_waste_vitrified, 1) }, null, 100);
		
		makeRecipe(EnumChemistryTemplate.TEL, new AStack[] {new OreDictStack(KEY_OIL_TAR, 1), new OreDictStack(PB.dust(), 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.petroleum, 100), new FluidStack(ModForgeFluids.steam, 1000) }, new AStack[] { new ComparableStack(ModItems.antiknock, 1) }, null, 40);
		
		makeRecipe(EnumChemistryTemplate.GASOLINE, new AStack[] {new ComparableStack(ModItems.antiknock, 1) }, new FluidStack[]{ new FluidStack(ModForgeFluids.petroil, 10000) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.gasoline, 12000) }, 40);
		
		makeRecipe(EnumChemistryTemplate.FRACKSOL, new AStack[] { new OreDictStack(S.dust()) }, new FluidStack[]{ new FluidStack(ModForgeFluids.petroleum, 100), new FluidStack(FluidRegistry.WATER, 1000) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.fracksol, 1000) }, 20);
		
		makeRecipe(EnumChemistryTemplate.OSMIRIDIUM_DEATH, new AStack[] { new ComparableStack(ModItems.powder_paleogenite), new OreDictStack(F.dust(), 8), new ComparableStack(ModItems.nugget_bismuth, 4) }, new FluidStack[]{ new FluidStack(ModForgeFluids.sulfuric_acid, 1000), new FluidStack(ModForgeFluids.sas3, 1000) }, null, new FluidStack[]{ new FluidStack(ModForgeFluids.liquid_osmiridium, 1000) }, 240);
		
		// makeRecipe(EnumChemistryTemplate.FP_SMEAR, new AStack[] { new ComparableStack() }, new FluidStack[]{ new FluidStack() }, null, null, 100);
		
	}
	
	public enum EnumChemistryTemplate {
		
		//FP - Fuel Processing
		//FR - Fuel Reprocessing
		//FC - Fuel Cracking
		//CC - Coal Cracking
		//SF - Solid Fuel Production
		//BP - Biofuel Production
		
		FP_HEAVYOIL,
		FP_SMEAR,
		FP_NAPHTHA,
		FP_LIGHTOIL,
		FR_REOIL,
		FR_PETROIL,
		FC_BITUMEN,
		FC_I_NAPHTHA,
		FC_GAS_PETROLEUM,
		FC_DIESEL_KEROSENE,
		FC_KEROSENE_PETROLEUM,
		CC_OIL,
		CC_I,
		CC_HEATING,
		CC_HEAVY,
		CC_NAPHTHA,
		SF_OIL,
		SF_HEAVYOIL,
		SF_SMEAR,
		SF_HEATINGOIL,
		SF_RECLAIMED,
		SF_PETROIL,
		SF_LUBRICANT,
		SF_NAPHTHA,
		SF_DIESEL,
		SF_LIGHTOIL,
		SF_KEROSENE,
		SF_GAS,
		SF_PETROLEUM,
		SF_BIOGAS,
		SF_BIOFUEL,
		BP_BIOGAS,
		BP_BIOFUEL,
		GNEISS_GAS,
		OIL_SAND,
		ASPHALT,
		COOLANT,
		CRYOGEL,
		DESH,
		NITAN,
		PEROXIDE,
		SULFURIC_ACID,
		CIRCUIT_4,
		CIRCUIT_5,
		POLYMER,
		BAKELITE,
		RUBBER,
		DYNAMITE,
		TNT,
		C4,
		DEUTERIUM,
		STEAM,
		ALGE,
		YELLOWCAKE,
		UF6,
		PUF6,
		SAS3,
		DYN_SCHRAB,
		DYN_STR,
		DYN_EUPH,
		DYN_DNT,
		DYN_EL,
		CORDITE,
		KEVLAR,
		CONCRETE,
		CONCRETE_ASBESTOS,
		DUCRETE,
		SOLID_FUEL,
		ELECTROLYSIS,
		XENON,
		XENON_OXY,
		SATURN,
		BALEFIRE,
		SCHRABIDIC,
		SCHRABIDATE,
		COLTAN_CLEANING,
		COLTAN_PAIN,
		COLTAN_CRYSTAL,
		ARSENIC,
		VIT_LIQUID,
		VIT_GAS,
		TEL,
		GASOLINE,
		FRACKSOL,
		OSMIRIDIUM_DEATH;
		
		public static EnumChemistryTemplate getEnum(int i) {
			if(i < EnumChemistryTemplate.values().length)
				return EnumChemistryTemplate.values()[i];
			else
				return FP_HEAVYOIL;
		}
		
		public String getName() {
			return this.toString();
		}
	}

	public static void makeRecipe(EnumChemistryTemplate name, AStack[] itemInputs, FluidStack[] fluidInputs, AStack[] outputItems, FluidStack[] outputFluids, int duration) {
		if(itemInputs != null)
			recipeItemInputs.put(name, itemInputs);
		if(fluidInputs != null)
			recipeFluidInputs.put(name, fluidInputs);
		if(outputItems != null)
			recipeItemOutputs.put(name, outputItems);
		if(outputFluids != null)
			recipeFluidOutputs.put(name, outputFluids);
		if(duration > 1)
			recipeDurations.put(name, duration);
	}

	public static List<AStack> getChemInputFromTempate(ItemStack stack) {
		if (stack == null || !(stack.getItem() instanceof ItemChemistryTemplate))
			return null;
		EnumChemistryTemplate recipeName = EnumChemistryTemplate.getEnum(stack.getItemDamage());
		AStack[] inputs = recipeItemInputs.get(recipeName);
		if(inputs != null)
			return Arrays.asList(inputs);
		return null;
	}


	public static FluidStack[] getFluidInputFromTempate(ItemStack stack) {
		if (stack == null || !(stack.getItem() instanceof ItemChemistryTemplate))
			return null;
		EnumChemistryTemplate recipeName = EnumChemistryTemplate.getEnum(stack.getItemDamage());
		return recipeFluidInputs.get(recipeName);
	}


	public static ItemStack[] getChemOutputFromTempate(ItemStack stack) {
		if (stack == null || !(stack.getItem() instanceof ItemChemistryTemplate))
			return null;
		EnumChemistryTemplate recipeName = EnumChemistryTemplate.getEnum(stack.getItemDamage());
		AStack[] outputs = recipeItemOutputs.get(recipeName);
		if(outputs != null){
			ItemStack[] stackOutputs = new ItemStack[outputs.length];
			for(int i=0; i<stackOutputs.length; i++){
				stackOutputs[i] = outputs[i].getStack();
			}
			return stackOutputs;
		}
		return null;
	}


	public static FluidStack[] getFluidOutputFromTempate(ItemStack stack) {
		if (stack == null || !(stack.getItem() instanceof ItemChemistryTemplate))
			return null;
		EnumChemistryTemplate recipeName = EnumChemistryTemplate.getEnum(stack.getItemDamage());
		return recipeFluidOutputs.get(recipeName);
	}


	public static int getProcessTime(ItemStack stack) {	
    	if(!(stack.getItem() instanceof ItemChemistryTemplate))
    		return 100;
        EnumChemistryTemplate recipeName = EnumChemistryTemplate.getEnum(stack.getItemDamage());
        Integer time = recipeDurations.get(recipeName);
        if(time != null)
        	return time;
        return 100;
    }
}
