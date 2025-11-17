package com.hbm.main;

import java.util.Map.Entry;

import static com.hbm.inventory.OreDictManager.*;
import static com.hbm.inventory.material.Mats.*;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.crafting.handlers.*;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.BedrockOreRegistry;
import com.hbm.inventory.RecipesCommon;
import com.hbm.items.ItemEnums;
import com.hbm.items.ModItems;
import com.hbm.items.machine.*;
import com.hbm.items.machine.ItemCircuit.EnumCircuitType;
import com.hbm.items.machine.ItemChemicalDye.EnumChemDye;
import com.hbm.items.machine.ItemWatzPellet.EnumWatzType;
import com.hbm.items.special.ItemCell;
import com.hbm.items.special.ItemHot;
import com.hbm.items.special.ItemWasteLong;
import com.hbm.items.special.ItemWasteShort;
import com.hbm.items.special.ItemBedrockOre;
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
import net.minecraft.item.crafting.*;
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
	public static boolean registeringFluids = false;

	public static void init(){
		if(!GeneralConfig.recipes) {
			return;
		}
		addCrafting();
		addSmelting();

		hack.getRegistry().register(new SmallReactorFuelCraftingHandler().setRegistryName(new ResourceLocation(RefStrings.MODID, "reactor_fuel_crafting_handler")));
		hack.getRegistry().register(new ScrapsCraftingHandler().setRegistryName(new ResourceLocation(RefStrings.MODID, "scrap_crafting_handler")));
		hack.getRegistry().register(new RBMKFuelCraftingHandler().setRegistryName(new ResourceLocation(RefStrings.MODID, "rbmk_fuel_crafting_handler")));
		hack.getRegistry().register(new MKUCraftingHandler().setRegistryName(new ResourceLocation(RefStrings.MODID, "mku_crafting_handler")));
        hack.getRegistry().register(new NTMShieldCraftingHandler().setRegistryName(new ResourceLocation(RefStrings.MODID, "ntmshield_crafting_handler")));
	}

	public static void addCircuitCrafting(){
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), "G", "W", "I", 'G', KEY_ANYPANE, 'W', W.wire(), 'I', ModItems.plate_polymer);
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), "G", "W", "I", 'G', KEY_ANYPANE, 'W', CARBON.wire(), 'I', ModItems.plate_polymer);
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR), "I", "N", "W", 'I', ModItems.plate_polymer, 'N', NB.nugget(), 'W', AL.wire());
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR), "I", "N", "W", 'I', ModItems.plate_polymer, 'N', NB.nugget(), 'W', CU.wire());
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR, 2), "IAI", "W W", 'I', ModItems.plate_polymer, 'A', AL.dust(), 'W', AL.wire());
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR, 2), "IAI", "W W", 'I', ModItems.plate_polymer, 'A', AL.dust(), 'W', CU.wire());
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR_TANTALIUM), "I", "N", "W", 'I', ModItems.plate_polymer, 'N', TA.nugget(), 'W', AL.wire());
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR_TANTALIUM), "I", "N", "W", 'I', ModItems.plate_polymer, 'N', TA.nugget(), 'W', CU.wire());
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.PCB), "I", "P", 'I', ModItems.plate_polymer, 'P', CU.plate());
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.PCB, 2), "I", "P", 'I', ModItems.plate_polymer, 'P', GOLD.plate());
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP), "I", "S", "W", 'I', ModItems.plate_polymer, 'S', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.SILICON), 'W', CU.wire());
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP, 2), "I", "S", "W", 'I', ModItems.plate_polymer, 'S', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.SILICON), 'W', GOLD.wire());
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP_BISMOID), "III", "SNS", "WWW", 'I', ModItems.plate_polymer, 'S', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.SILICON), 'N', ANY_BISMOID.nugget(), 'W', CU.wire());
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP_BISMOID), "III", "SNS", "WWW", 'I', ModItems.plate_polymer, 'S', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.SILICON), 'N', ANY_BISMOID.nugget(), 'W', GOLD.wire());
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP_QUANTUM), "HHH", "SIS", "WWW", 'H', ANY_HARDPLASTIC.ingot(), 'S', BSCCO.wireDense(), 'I', ModItems.pellet_charged, 'W', CU.wire());
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP_QUANTUM), "HHH", "SIS", "WWW", 'H', ANY_HARDPLASTIC.ingot(), 'S', BSCCO.wireDense(), 'I', ModItems.pellet_charged, 'W', GOLD.wire());
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CONTROLLER_CHASSIS), "PPP", "CBB", "PPP", 'P', ANY_PLASTIC.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CRT_TUBE), 'B', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.PCB));
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ATOMIC_CLOCK), "ICI", "CSC", "ICI", 'I', ModItems.plate_polymer, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP), 'S', CS.dust());
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CRT_TUBE, 4), " A ", "SGS", " T ", 'A', AL.dust(), 'S', STEEL.plate(), 'G', KEY_ANYPANE, 'T', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE));
	}

	public static void addWatzPellets(){
		addPellet(SA326,	EnumWatzType.SCHRABIDIUM);
		addPellet(HES,		EnumWatzType.HES);
		addPellet(MES,		EnumWatzType.MES);
		addPellet(LES,		EnumWatzType.LES);
		addPellet(NP237,	EnumWatzType.HEN);
		addPellet(UF,		EnumWatzType.MEU);
		addPellet(PUF,		EnumWatzType.MEP);
		addPellet(PB,		EnumWatzType.LEAD);
		addPellet(B,		EnumWatzType.BORON);
		addPellet(U238,		EnumWatzType.DU);
		addPellet(PU241,	EnumWatzType.PU241);
		addPellet(AMRG,		EnumWatzType.AMRG);
		addPellet(AMF,		EnumWatzType.AMF);
	}

	public static void addConveyorCrafting(){
		addRecipeAuto(new ItemStack(ModBlocks.conveyor, 16), "LLL", "I I", "LLL", 'L', Items.LEATHER, 'I', IRON.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.conveyor, 64), "LLL", "I I", "LLL", 'L', RUBBER.ingot(), 'I', IRON.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.conveyor_express, 8), "CCC", "CLC", "CCC", 'C', ModBlocks.conveyor, 'L', new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.LUBRICANT)));
		addRecipeAuto(new ItemStack(ModBlocks.conveyor_double, 3), "CPC", "CPC", "CPC", 'C', ModBlocks.conveyor, 'P', IRON.plate());
		addRecipeAuto(new ItemStack(ModBlocks.conveyor_triple, 3), "CPC", "CPC", "CPC", 'C', ModBlocks.conveyor_double, 'P', STEEL.plate());
		addRecipeAuto(new ItemStack(ModBlocks.conveyor_chute, 3), "IGI", "IGI", "ICI", 'I', IRON.ingot(), 'G', ModBlocks.steel_grate, 'C', ModBlocks.conveyor);
		addRecipeAuto(new ItemStack(ModBlocks.conveyor_lift, 3), "IGI", "IGI", "ICI", 'I', IRON.ingot(), 'G', ModBlocks.chain, 'C', ModBlocks.conveyor);

		Object[] craneCasing = new Object[] {
				Blocks.STONEBRICK, 1,
				IRON.ingot(), 2,
				STEEL.ingot(), 4
		};

		for(int i = 0; i < craneCasing.length / 2; i++) {
			Object casing = craneCasing[i * 2];
			int amount = (int) craneCasing[i * 2 + 1];
			addRecipeAuto(new ItemStack(ModBlocks.crane_inserter, amount), "CCC", "C C", "CBC", 'C', casing, 'B', ModBlocks.conveyor);
			addRecipeAuto(new ItemStack(ModBlocks.crane_ejector, amount), "CCC", "CPC", "CBC", 'C', casing, 'B', ModBlocks.conveyor, 'P', ModItems.piston_pneumatic);
			addRecipeAuto(new ItemStack(ModBlocks.crane_grabber, amount), "C C", "P P", "CBC", 'C', casing, 'B', ModBlocks.conveyor, 'P', ModItems.piston_pneumatic);
		}

		addRecipeAuto(new ItemStack(ModBlocks.crane_boxer, 1), "WWW", "WPW", "CCC", 'W', KEY_PLANKS, 'P', ModItems.piston_pneumatic, 'C', ModBlocks.conveyor);
		addRecipeAuto(new ItemStack(ModBlocks.crane_unboxer, 1), "WWW", "WPW", "CCC", 'W', KEY_STICK, 'P', Items.SHEARS, 'C', ModBlocks.conveyor);
		addRecipeAuto(new ItemStack(ModBlocks.crane_splitter), "III", "PCP", "III", 'P', ModItems.piston_pneumatic, 'I', STEEL.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE));
		addRecipeAuto(new ItemStack(ModBlocks.crane_router), "PIP", "ICI", "PIP", 'P', ModItems.piston_pneumatic, 'I', ModItems.plate_polymer, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC));
	}

	public static void addPureCrafting(){
		for(DictFrame mat: dictList){
			addAll(mat);
		}

		add1To9Pair(ModItems.ingot_u238m2, ModItems.nugget_u238m2);
		add1To9Pair(ModBlocks.block_trinitite, ModItems.trinitite);
		add9To1(ModItems.dust, ModBlocks.block_scrap);
		add1To9Pair(ModBlocks.block_scrap, ModItems.scrap);
		add1To9Pair(ModBlocks.block_yellowcake, ModItems.powder_yellowcake);
		add1To9Pair(ModItems.powder_meteorite, ModItems.powder_meteorite_tiny);
		add1To9Pair(ModBlocks.block_insulator, ModItems.plate_polymer);
		add1To9Pair(ModItems.powder_paleogenite, ModItems.powder_paleogenite_tiny);
		add1To9Pair(ModBlocks.block_fallout, ModItems.fallout);
		add1To9Pair(ModBlocks.block_semtex, ModItems.ingot_semtex);
		add1To9Pair(ModBlocks.block_smore, ModItems.ingot_smore);

        addBillet(ModItems.billet_bismuth, ModItems.nugget_bismuth);
        addBilletByIngot(ModItems.billet_bismuth, ModItems.ingot_bismuth);
        addBillet(ModItems.billet_australium_lesser, ModItems.nugget_australium_lesser);
        addBillet(ModItems.billet_australium_greater, ModItems.nugget_australium_greater);
		addBillet(ModItems.billet_nuclear_waste, ModItems.nuclear_waste_tiny);
		addBilletByIngot(ModItems.billet_nuclear_waste, ModItems.nuclear_waste);
		add1To9Pair(ModItems.nuclear_waste, ModItems.nuclear_waste_tiny );
		add1To9Pair(ModBlocks.block_waste, ModItems.nuclear_waste);
		add1To9(ModBlocks.block_waste_painted, ModItems.nuclear_waste);

		add1To9Pair(ModItems.nuclear_waste_vitrified, ModItems.nuclear_waste_vitrified_tiny);
		add1To9Pair(ModBlocks.block_waste_vitrified, ModItems.nuclear_waste_vitrified);

		add1To9Pair(ModBlocks.block_solid_fuel, ModItems.solid_fuel);
		add1To9Pair(ModBlocks.block_solid_fuel_presto, ModItems.solid_fuel_presto);
		add1To9Pair(ModBlocks.block_solid_fuel_presto_triplet, ModItems.solid_fuel_presto_triplet);
		add1To9Pair(ModBlocks.block_solid_fuel_bf, ModItems.solid_fuel_bf);
		add1To9Pair(ModBlocks.block_solid_fuel_presto_bf, ModItems.solid_fuel_presto_bf);
		add1To9Pair(ModBlocks.block_solid_fuel_presto_triplet_bf, ModItems.solid_fuel_presto_triplet_bf);

        add1To9Pair(ModBlocks.sand_gold, ModItems.powder_gold);
        add1To9Pair(ModBlocks.sand_gold198, ModItems.powder_au198);
    }

	public static void addRawFuelCrafting(){

		addShapelessAuto(new ItemStack(ModItems.ingot_neptunium_fuel, 27), U238.block(), U238.block(), NP237.block());
		addShapelessAuto(new ItemStack(ModItems.ingot_neptunium_fuel, 3), U238.ingot(), U238.ingot(), NP237.ingot());
		addShapelessAuto(new ItemStack(ModItems.billet_neptunium_fuel, 3), U238.billet(), U238.billet(), NP237.billet());
		addShapelessAuto(new ItemStack(ModItems.billet_neptunium_fuel, 1), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), NP237.nugget(), NP237.nugget());

		addShapelessAuto(new ItemStack(ModItems.billet_uranium, 2), ModItems.billet_uranium_fuel, U238.billet());
		addShapelessAuto(new ItemStack(ModItems.billet_uranium, 2), U238.billet(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U235.nugget());

        addShapelessAuto(new ItemStack(ModBlocks.block_mox_fuel, 3), UF.block(), UF.block(), PURG.block());
        addShapelessAuto(new ItemStack(ModItems.ingot_mox_fuel, 1), UF.nugget(), UF.nugget(), UF.nugget(), UF.nugget(), UF.nugget(), UF.nugget(), PU239.nugget(), PU239.nugget(), PU239.nugget());
        addShapelessAuto(new ItemStack(ModItems.ingot_mox_fuel, 1), UF.nugget(), UF.nugget(), UF.nugget(), UF.nugget(), UF.nugget(), UF.nugget(), PU239.nugget(), PU239.nugget(), PU239.nugget());
        addShapelessAuto(new ItemStack(ModItems.ingot_mox_fuel, 3), UF.ingot(), UF.ingot(), PURG.ingot());
        addShapelessAuto(new ItemStack(ModItems.billet_mox_fuel, 3), UF.billet(), UF.billet(), PURG.billet());
		addShapelessAuto(new ItemStack(ModItems.billet_mox_fuel, 1), UF.nugget(), UF.nugget(), UF.nugget(), UF.nugget(), PURG.nugget(), PURG.nugget());

		addShapelessAuto(new ItemStack(ModItems.ingot_les, 9), SA326.ingot(), NP237.ingot(), NP237.ingot(), NP237.ingot(), NP237.ingot(), BE.ingot(), BE.ingot(), BE.ingot());
        addShapelessAuto(new ItemStack(ModItems.ingot_les, 1), SA326.nugget(), NP237.nugget(), NP237.nugget(), NP237.nugget(), NP237.nugget(), BE.nugget(), BE.nugget(), BE.nugget());
        addShapelessAuto(new ItemStack(ModItems.billet_les, 9), SA326.billet(), NP237.billet(), NP237.billet(), NP237.billet(), NP237.billet(), BE.billet(), BE.billet(), BE.billet());

        addShapelessAuto(new ItemStack(ModBlocks.block_schrabidium_fuel, 3), SA326.block(), NP237.block(), BE.block());
        addShapelessAuto(new ItemStack(ModItems.ingot_schrabidium_fuel, 1), SA326.nugget(), SA326.nugget(), SA326.nugget(), NP237.nugget(), NP237.nugget(), NP237.nugget(), BE.nugget(), BE.nugget(), BE.nugget());
        addShapelessAuto(new ItemStack(ModItems.ingot_schrabidium_fuel, 3), SA326.ingot(), NP237.ingot(), BE.ingot());
		addShapelessAuto(new ItemStack(ModItems.billet_schrabidium_fuel, 3), SA326.billet(), NP237.billet(), BE.billet());
		addShapelessAuto(new ItemStack(ModItems.nugget_schrabidium_fuel, 3), SA326.nugget(), NP237.nugget(), BE.nugget());

        addShapelessAuto(new ItemStack(ModItems.ingot_hes, 1), SA326.nugget(), SA326.nugget(), SA326.nugget(), SA326.nugget(), SA326.nugget(), NP237.nugget(), NP237.nugget(), BE.nugget(), BE.nugget());
        addShapelessAuto(new ItemStack(ModItems.ingot_hes, 4), SA326.ingot(), SA326.ingot(), NP237.ingot(), BE.ingot());
        addShapelessAuto(new ItemStack(ModItems.billet_hes, 4), SA326.billet(), SA326.billet(), NP237.billet(), BE.billet());
        addShapelessAuto(new ItemStack(ModItems.nugget_hes, 4), SA326.nugget(), SA326.nugget(), NP237.nugget(), BE.nugget());

		addShapelessAuto(new ItemStack(ModItems.billet_po210be, 1), PO210.nugget(), PO210.nugget(), PO210.nugget(), BE.nugget(), BE.nugget(), BE.nugget());
		addShapelessAuto(new ItemStack(ModItems.billet_pu238be, 1), PU238.nugget(), PU238.nugget(), PU238.nugget(), BE.nugget(), BE.nugget(), BE.nugget());
		addShapelessAuto(new ItemStack(ModItems.billet_ra226be, 1), RA226.nugget(), RA226.nugget(), RA226.nugget(), BE.nugget(), BE.nugget(), BE.nugget());
        addShapelessAuto(new ItemStack(ModItems.billet_po210be, 2), PO210.billet(), BE.billet());
        addShapelessAuto(new ItemStack(ModItems.billet_pu238be, 2), PU238.billet(), BE.billet());
        addShapelessAuto(new ItemStack(ModItems.billet_ra226be, 2), RA226.billet(), BE.billet());

        addShapelessAuto(new ItemStack(ModBlocks.block_thorium_fuel, 6), U233.block(), TH232.block(), TH232.block(), TH232.block(), TH232.block(), TH232.block());
        addShapelessAuto(new ItemStack(ModItems.nugget_thorium_fuel, 6), U233.nugget(), TH232.nugget(), TH232.nugget(), TH232.nugget(), TH232.nugget(), TH232.nugget());
        addShapelessAuto(new ItemStack(ModItems.ingot_thorium_fuel, 6), U233.ingot(), TH232.ingot(), TH232.ingot(), TH232.ingot(), TH232.ingot(), TH232.ingot());
        addShapelessAuto(new ItemStack(ModItems.billet_thorium_fuel, 6), U233.billet(), TH232.billet(), TH232.billet(), TH232.billet(), TH232.billet(), TH232.billet());
        addShapelessAuto(new ItemStack(ModItems.billet_thorium_fuel, 1), U233.nugget(), TH232.nugget(), TH232.nugget(), TH232.nugget(), TH232.nugget(), TH232.nugget());


        addShapelessAuto(new ItemStack(ModBlocks.block_uranium_fuel, 6), U235.block(), U238.block(), U238.block(), U238.block(), U238.block(), U238.block());
        addShapelessAuto(new ItemStack(ModBlocks.block_uranium_fuel, 6), U233.block(), U238.block(), U238.block(), U238.block(), U238.block(), U238.block());
        addShapelessAuto(new ItemStack(ModItems.ingot_uranium_fuel, 6), U235.ingot(), U238.ingot(), U238.ingot(), U238.ingot(), U238.ingot(), U238.ingot());
        addShapelessAuto(new ItemStack(ModItems.ingot_uranium_fuel, 6), U233.ingot(), U238.ingot(), U238.ingot(), U238.ingot(), U238.ingot(), U238.ingot());
        addShapelessAuto(new ItemStack(ModItems.nugget_uranium_fuel, 6), U235.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget());
        addShapelessAuto(new ItemStack(ModItems.nugget_uranium_fuel, 6), U233.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget());
        addShapelessAuto(new ItemStack(ModItems.billet_uranium_fuel, 6), U235.billet(), U238.billet(), U238.billet(), U238.billet(), U238.billet(), U238.billet());
        addShapelessAuto(new ItemStack(ModItems.billet_uranium_fuel, 6), U233.billet(), U238.billet(), U238.billet(), U238.billet(), U238.billet(), U238.billet());
        addShapelessAuto(new ItemStack(ModItems.billet_uranium_fuel, 1), U235.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget());
        addShapelessAuto(new ItemStack(ModItems.billet_uranium_fuel, 1), U233.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget());


        addShapelessAuto(new ItemStack(ModBlocks.block_plutonium_fuel, 3), U238.block(), U238.block(), PURG.block());
        addShapelessAuto(new ItemStack(ModBlocks.block_plutonium_fuel, 9), PU238.block(), PU239.block(), PU239.block(), PU239.block(), PU239.block(), PU239.block(), PU240.block(), PU240.block(), PU240.block());
        addShapelessAuto(new ItemStack(ModBlocks.block_plutonium_fuel, 1), PU238.ingot(), PU239.ingot(), PU239.ingot(), PU239.ingot(), PU239.ingot(), PU239.ingot(), PU240.ingot(), PU240.ingot(), PU240.ingot());
        addShapelessAuto(new ItemStack(ModItems.ingot_plutonium_fuel, 1), PU238.nugget(), PU239.nugget(), PU239.nugget(), PU239.nugget(), PU239.nugget(), PU239.nugget(), PU240.nugget(), PU240.nugget(), PU240.nugget());
        addShapelessAuto(new ItemStack(ModItems.billet_plutonium_fuel, 9), PU238.billet(), PU239.billet(), PU239.billet(), PU239.billet(), PU239.billet(), PU239.billet(), PU240.billet(), PU240.billet(), PU240.billet());
        addShapelessAuto(new ItemStack(ModItems.ingot_plutonium_fuel, 3), U238.ingot(), U238.ingot(), PURG.ingot());
        addShapelessAuto(new ItemStack(ModItems.nugget_plutonium_fuel, 3), U238.nugget(), U238.nugget(), PURG.nugget());
        addShapelessAuto(new ItemStack(ModItems.billet_plutonium_fuel, 3), U238.billet(), U238.billet(), PURG.billet());
		addShapelessAuto(new ItemStack(ModItems.billet_plutonium_fuel, 1), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), PURG.nugget(), PURG.nugget());

		addShapelessAuto(new ItemStack(ModBlocks.block_pu_mix, 3), PU239.block(), PU239.block(), PU240.block());
		addShapelessAuto(new ItemStack(ModItems.ingot_pu_mix, 3), PU239.ingot(), PU239.ingot(), PU240.ingot());
		addShapelessAuto(new ItemStack(ModItems.billet_pu_mix, 3), PU239.billet(), PU239.billet(), PU240.billet());
		addShapelessAuto(new ItemStack(ModItems.billet_pu_mix, 1), PU239.nugget(), PU239.nugget(), PU239.nugget(), PU239.nugget(), PU240.nugget(), PU240.nugget());
        addShapelessAuto(new ItemStack(ModItems.nugget_pu_mix, 3), PU239.nugget(), PU239.nugget(), PU240.nugget());

        addShapelessAuto(new ItemStack(ModItems.ingot_americium_fuel, 3), U238.ingot(), U238.ingot(), AMRG.ingot());
		addShapelessAuto(new ItemStack(ModItems.billet_americium_fuel, 3), U238.billet(), U238.billet(), AMRG.billet());
		addShapelessAuto(new ItemStack(ModItems.billet_americium_fuel, 1), U238.nugget(), U238.nugget(), U238.nugget(), U238.nugget(), AMRG.nugget(), AMRG.nugget());
        addShapelessAuto(new ItemStack(ModItems.nugget_americium_fuel, 3), U238.nugget(), U238.nugget(), AMRG.nugget());

        addShapelessAuto(new ItemStack(ModItems.ingot_am_mix, 3), AM241.ingot(), AM242.ingot(), AM242.ingot());
		addShapelessAuto(new ItemStack(ModItems.billet_am_mix, 3), AM241.billet(), AM242.billet(), AM242.billet());
		addShapelessAuto(new ItemStack(ModItems.billet_am_mix, 1), AM241.nugget(), AM241.nugget(), AM242.nugget(), AM242.nugget(), AM242.nugget(), AM242.nugget());
        addShapelessAuto(new ItemStack(ModItems.nugget_am_mix, 3), AM241.nugget(), AM242.nugget(), AM242.nugget());

        addShapelessAuto(new ItemStack(ModItems.billet_balefire_gold, 1), AU198.billet(), new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.AMAT)), ModItems.pellet_charged);
		addShapelessAuto(new ItemStack(ModItems.billet_flashlead, 2), ModItems.billet_balefire_gold, PB209.billet(), new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.AMAT)));
		addShapelessAuto(new ItemStack(ModItems.billet_flashlead, 2), AU198.billet(), PB209.billet(), new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.AMAT)), new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.AMAT)), ModItems.pellet_charged);

		addShapelessAuto(new ItemStack(ModItems.billet_zfb_bismuth), ZR.nugget(), ZR.nugget(), ZR.nugget(), U.nugget(), PU241.nugget(), ANY_BISMOID.nugget());
		addShapelessAuto(new ItemStack(ModItems.billet_zfb_pu241), ZR.nugget(), ZR.nugget(), ZR.nugget(), U235.nugget(), PU240.nugget(), PU241.nugget());
		addShapelessAuto(new ItemStack(ModItems.billet_zfb_am_mix), ZR.nugget(), ZR.nugget(), ZR.nugget(), PU241.nugget(), PU241.nugget(), AMRG.nugget());

		addShapelessAuto(new ItemStack(ModItems.billet_zfb_bismuth, 6), ZR.billet(), ZR.billet(), ZR.billet(), U.billet(), PU241.billet(), ANY_BISMOID.billet());
		addShapelessAuto(new ItemStack(ModItems.billet_zfb_pu241, 6), ZR.billet(), ZR.billet(), ZR.billet(), U235.billet(), PU240.billet(), PU241.billet());
		addShapelessAuto(new ItemStack(ModItems.billet_zfb_am_mix, 6), ZR.billet(), ZR.billet(), ZR.billet(), PU241.billet(), PU241.billet(), AMRG.billet());

		addShapelessAuto(new ItemStack(ModItems.billet_unobtainium), ModItems.nugget_radspice, AMRG.nugget(), ModItems.nugget_unobtainium_lesser, ModItems.nugget_unobtainium_greater, ModItems.nugget_unobtainium_greater, ModItems.nugget_unobtainium_greater);
	}

	public static void addRodCrafting(){
		addRecipeAuto(new ItemStack(ModItems.rod_empty, 16), "SSS", "L L", "SSS", 'S', STEEL.plate(), 'L', PB.plate());

		addRod(ModItems.rod_th232, TH232.nugget());
		addRod(ModItems.rod_uranium, U.nugget());
		addRod(ModItems.rod_u233, U233.nugget());
		addRod(ModItems.rod_u235, U235.nugget());
		addRod(ModItems.rod_u238, U238.nugget());
		addRod(ModItems.rod_plutonium, PU.nugget());
		addRod(ModItems.rod_pu238, PU238.nugget());
		addRod(ModItems.rod_pu239, PU239.nugget());
		addRod(ModItems.rod_pu240, PU240.nugget());
		addRod(ModItems.rod_neptunium, NP237.nugget());
		addRod(ModItems.rod_polonium, PO210.nugget());
		addRod(ModItems.rod_lead, PB.nugget());
		addRod(ModItems.rod_schrabidium, SA326.nugget());
		addRod(ModItems.rod_solinium, SA327.nugget());
		addRod(ModItems.rod_uranium_fuel, UF.nugget());
		addRod(ModItems.rod_thorium_fuel, THF.nugget());
		addRod(ModItems.rod_plutonium_fuel, PUF.nugget());
		addRod(ModItems.rod_mox_fuel, MOXF.nugget());
		addRod(ModItems.rod_schrabidium_fuel, MES.nugget());
		addRod(ModItems.rod_euphemium, EUPH.nugget());
		addRod(ModItems.rod_australium, AUSTRALIUM.nugget());
		addRod(ModItems.rod_weidanium, WEIDANIUM.nugget());
		addRod(ModItems.rod_reiium, REIIUM.nugget());
		addRod(ModItems.rod_unobtainium, UNOBTAINIUM.nugget());
		addRod(ModItems.rod_daffergon, DAFFERGON.nugget());
		addRod(ModItems.rod_verticium, VERTICIUM.nugget());
		addShapelessAuto(new ItemStack(ModItems.rod_balefire, 1), ModItems.rod_empty, ModItems.egg_balefire_shard);

		addRod(ModItems.rod_ac227, AC227.nugget());
		addRod(ModItems.rod_cobalt, CO.nugget());
		addRod(ModItems.rod_co60, CO60.nugget());
		addRod(ModItems.rod_ra226, RA226.nugget());
		addRod(ModItems.rod_rgp, PURG.nugget());


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
		addRodBilletUnload(ModItems.billet_nuclear_waste, ModItems.rod_waste );
		addRodBilletUnload(ModItems.billet_ac227, ModItems.rod_ac227);
		addRodBilletUnload(ModItems.billet_ra226, ModItems.rod_ra226);
		addRodBilletUnload(ModItems.billet_pu_mix, ModItems.rod_rgp);
		addRodBilletUnload(ModItems.billet_co60, ModItems.rod_co60);

		addShapelessAuto(new ItemStack(ModItems.rod_empty, 2), ModItems.rod_dual_empty);
		addShapelessAuto(new ItemStack(ModItems.rod_dual_empty, 1), ModItems.rod_empty, ModItems.rod_empty);

		addDualRod(ModItems.rod_dual_th232, TH232.ingot(), TH232.nugget());
		addDualRod(ModItems.rod_dual_uranium, U.ingot(), U.nugget());
		addDualRod(ModItems.rod_dual_u233, U233.ingot(), U233.nugget());
		addDualRod(ModItems.rod_dual_u235, U235.ingot(), U235.nugget());
		addDualRod(ModItems.rod_dual_u238, U238.ingot(), U238.nugget());
		addDualRod(ModItems.rod_dual_plutonium, PU.ingot(), PU.nugget());
		addDualRod(ModItems.rod_dual_pu238, PU238.ingot(), PU238.nugget());
		addDualRod(ModItems.rod_dual_pu239, PU239.ingot(), PU239.nugget());
		addDualRod(ModItems.rod_dual_pu240, PU240.ingot(), PU240.nugget());
		addDualRod(ModItems.rod_dual_neptunium, NP237.ingot(), NP237.nugget());
		addDualRod(ModItems.rod_dual_polonium, PO210.ingot(), PO210.nugget());
		addDualRod(ModItems.rod_dual_lead, PB.ingot(), PB.nugget());
		addDualRod(ModItems.rod_dual_schrabidium, SA326.ingot(), SA326.nugget());
		addDualRod(ModItems.rod_dual_solinium, SA327.ingot(), SA327.nugget());
		addDualRod(ModItems.rod_dual_uranium_fuel, UF.ingot(), UF.nugget());
		addDualRod(ModItems.rod_dual_thorium_fuel, THF.ingot(), THF.nugget());
		addDualRod(ModItems.rod_dual_plutonium_fuel, PUF.ingot(), PUF.nugget());
		addDualRod(ModItems.rod_dual_mox_fuel, MOXF.ingot(), MOXF.nugget());
		addDualRod(ModItems.rod_dual_schrabidium_fuel, MES.ingot(), MES.nugget());
		addDualRod(ModItems.rod_dual_balefire, ModItems.egg_balefire_shard, ModItems.egg_balefire_shard);
		addShapelessAuto(new ItemStack(ModItems.rod_quad_euphemium, 1), ModItems.rod_quad_empty, EUPH.nugget());

		addDualRod(ModItems.rod_dual_ac227, AC227.ingot(), AC227.nugget());
		addDualRod(ModItems.rod_dual_cobalt, CO.ingot(), CO.nugget());
		addDualRod(ModItems.rod_dual_co60, CO60.ingot(), CO60.nugget());
		addDualRod(ModItems.rod_dual_ra226, RA226.ingot(), RA226.nugget());
		addDualRod(ModItems.rod_dual_rgp, PURG.ingot(), PURG.nugget());


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
		addDualRodBilletUnload(ModItems.billet_nuclear_waste, ModItems.rod_dual_waste);

		addDualRodBilletUnload(ModItems.billet_ac227, ModItems.rod_dual_ac227);
		addDualRodBilletUnload(ModItems.billet_ra226, ModItems.rod_dual_ra226);
		addDualRodBilletUnload(ModItems.billet_pu_mix, ModItems.rod_dual_rgp);
		addDualRodBilletUnload(ModItems.billet_co60, ModItems.rod_dual_co60);


		addShapelessAuto(new ItemStack(ModItems.rod_lithium, 1), ModItems.rod_empty, LI.ingot());
		addShapelessAuto(new ItemStack(ModItems.rod_dual_lithium, 1), ModItems.rod_dual_empty, LI.ingot(), LI.ingot());
		addShapelessAuto(new ItemStack(ModItems.rod_quad_lithium, 1), ModItems.rod_quad_empty, LI.ingot(), LI.ingot(), LI.ingot(), LI.ingot());
		addShapelessAuto(ItemCell.getFullCell(ModForgeFluids.TRITIUM, 1), ModItems.rod_tritium, new IngredientNBT2(new ItemStack(ModItems.cell)));
		addShapelessAuto(ItemCell.getFullCell(ModForgeFluids.TRITIUM, 2), ModItems.rod_dual_tritium, new IngredientNBT2(new ItemStack(ModItems.cell)), new IngredientNBT2(new ItemStack(ModItems.cell)));
		addShapelessAuto(ItemCell.getFullCell(ModForgeFluids.TRITIUM, 4), ModItems.rod_quad_tritium, new IngredientNBT2(new ItemStack(ModItems.cell)), new IngredientNBT2(new ItemStack(ModItems.cell)), new IngredientNBT2(new ItemStack(ModItems.cell)), new IngredientNBT2(new ItemStack(ModItems.cell)));

		addShapelessAuto(new ItemStack(ModItems.rod_empty, 4), ModItems.rod_quad_empty);
		addShapelessAuto(new ItemStack(ModItems.rod_quad_empty, 1), ModItems.rod_empty, ModItems.rod_empty, ModItems.rod_empty, ModItems.rod_empty);
		addShapelessAuto(new ItemStack(ModItems.rod_quad_empty, 1), ModItems.rod_dual_empty, ModItems.rod_dual_empty);

		addQuadRod(ModItems.rod_quad_th232, TH232.ingot(), TH232.nugget());
		addQuadRod(ModItems.rod_quad_uranium, U.ingot(), U.nugget());
		addQuadRod(ModItems.rod_quad_u233, U233.ingot(), U233.nugget());
		addQuadRod(ModItems.rod_quad_u235, U235.ingot(), U235.nugget());
		addQuadRod(ModItems.rod_quad_u238, U238.ingot(), U238.nugget());
		addQuadRod(ModItems.rod_quad_plutonium, PU.ingot(), PU.nugget());
		addQuadRod(ModItems.rod_quad_pu238, PU238.ingot(), PU238.nugget());
		addQuadRod(ModItems.rod_quad_pu239, PU239.ingot(), PU239.nugget());
		addQuadRod(ModItems.rod_quad_pu240, PU240.ingot(), PU240.nugget());
		addQuadRod(ModItems.rod_quad_neptunium, NP237.ingot(), NP237.nugget());
		addQuadRod(ModItems.rod_quad_polonium, PO210.ingot(), PO210.nugget());
		addQuadRod(ModItems.rod_quad_lead, PB.ingot(), PB.nugget());
		addQuadRod(ModItems.rod_quad_schrabidium, SA326.ingot(), SA326.nugget());
		addQuadRod(ModItems.rod_quad_solinium, SA327.ingot(), SA327.nugget());
		addQuadRod(ModItems.rod_quad_uranium_fuel, UF.ingot(), UF.nugget());
		addQuadRod(ModItems.rod_quad_thorium_fuel, THF.ingot(), THF.nugget());
		addQuadRod(ModItems.rod_quad_plutonium_fuel, PUF.ingot(), PUF.nugget());
		addQuadRod(ModItems.rod_quad_mox_fuel, MOXF.ingot(), MOXF.nugget());
		addQuadRod(ModItems.rod_quad_schrabidium_fuel, MES.ingot(), MES.nugget());
		addShapelessAuto(new ItemStack(ModItems.rod_quad_balefire, 1), ModItems.rod_quad_empty, ModItems.egg_balefire_shard, ModItems.egg_balefire_shard, ModItems.egg_balefire_shard, ModItems.egg_balefire_shard);

		addQuadRod(ModItems.rod_quad_ac227, AC227.ingot(), AC227.nugget());
		addQuadRod(ModItems.rod_quad_cobalt, CO.ingot(), CO.nugget());
		addQuadRod(ModItems.rod_quad_co60, CO60.ingot(), CO60.nugget());
		addQuadRod(ModItems.rod_quad_ra226, RA226.ingot(), RA226.nugget());
		addQuadRod(ModItems.rod_quad_rgp, PURG.ingot(), PURG.nugget());

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
		addQuadRodBilletUnload(ModItems.billet_nuclear_waste, ModItems.rod_quad_waste);

		addQuadRodBilletUnload(ModItems.billet_ac227, ModItems.rod_quad_ac227);
		addQuadRodBilletUnload(ModItems.billet_ra226, ModItems.rod_quad_ra226);
		addQuadRodBilletUnload(ModItems.billet_pu_mix, ModItems.rod_quad_rgp);
		addQuadRodBilletUnload(ModItems.billet_co60, ModItems.rod_quad_co60);

		addShapelessAuto(new ItemStack(ModItems.rod_water, 1), ModItems.rod_empty, Items.WATER_BUCKET);
		addShapelessAuto(new ItemStack(ModItems.rod_dual_water, 1), ModItems.rod_dual_empty, Items.WATER_BUCKET, Items.WATER_BUCKET);
		addShapelessAuto(new ItemStack(ModItems.rod_quad_water, 1), ModItems.rod_quad_empty, Items.WATER_BUCKET, Items.WATER_BUCKET, Items.WATER_BUCKET, Items.WATER_BUCKET);

		addShapelessAuto(new ItemStack(ModItems.nugget_lead, 6), ModItems.rod_lead);
		addShapelessAuto(new ItemStack(ModItems.lithium, 1), ModItems.rod_lithium);
		addShapelessAuto(new ItemStack(ModItems.nugget_cobalt, 6), ModItems.rod_cobalt);
		addShapelessAuto(new ItemStack(ModItems.nugget_australium, 6), ModItems.rod_australium);
		addShapelessAuto(new ItemStack(ModItems.nugget_weidanium, 6), ModItems.rod_weidanium);
		addShapelessAuto(new ItemStack(ModItems.nugget_reiium, 6), ModItems.rod_reiium);
		addShapelessAuto(new ItemStack(ModItems.nugget_unobtainium, 6), ModItems.rod_unobtainium);
		addShapelessAuto(new ItemStack(ModItems.nugget_daffergon, 6), ModItems.rod_daffergon);
		addShapelessAuto(new ItemStack(ModItems.nugget_verticium, 6), ModItems.rod_verticium);
		addShapelessAuto(new ItemStack(ModItems.nugget_euphemium, 6), ModItems.rod_euphemium);
		addShapelessAuto(new ItemStack(ModItems.egg_balefire_shard, 1), ModItems.rod_balefire);
		addShapelessAuto(new ItemStack(ModItems.egg_balefire_shard, 1), ModItems.rod_balefire_blazing);

		addShapelessAuto(new ItemStack(ModItems.nugget_lead, 12), ModItems.rod_dual_lead);
		addShapelessAuto(new ItemStack(ModItems.lithium, 2), ModItems.rod_dual_lithium);
		addShapelessAuto(new ItemStack(ModItems.nugget_cobalt, 12), ModItems.rod_dual_cobalt);
		addShapelessAuto(new ItemStack(ModItems.egg_balefire_shard, 2), ModItems.rod_dual_balefire);
		addShapelessAuto(new ItemStack(ModItems.egg_balefire_shard, 2), ModItems.rod_dual_balefire_blazing);

		addShapelessAuto(new ItemStack(ModItems.nugget_lead, 24), ModItems.rod_quad_lead);
		addShapelessAuto(new ItemStack(ModItems.lithium, 4), ModItems.rod_quad_lithium);
		addShapelessAuto(new ItemStack(ModItems.nugget_cobalt, 24), ModItems.rod_quad_cobalt);
		addShapelessAuto(new ItemStack(ModItems.egg_balefire_shard, 4), ModItems.rod_quad_balefire);
		addShapelessAuto(new ItemStack(ModItems.egg_balefire_shard, 4), ModItems.rod_quad_balefire_blazing);

		addShapelessAuto(new ItemStack(ModItems.waste_uranium_hot, 1), ModItems.rod_uranium_fuel_depleted);
		addShapelessAuto(new ItemStack(ModItems.waste_uranium_hot, 2), ModItems.rod_dual_uranium_fuel_depleted);
		addShapelessAuto(new ItemStack(ModItems.waste_uranium_hot, 4), ModItems.rod_quad_uranium_fuel_depleted);
		addShapelessAuto(new ItemStack(ModItems.waste_thorium_hot, 1), ModItems.rod_thorium_fuel_depleted);
		addShapelessAuto(new ItemStack(ModItems.waste_thorium_hot, 2), ModItems.rod_dual_thorium_fuel_depleted);
		addShapelessAuto(new ItemStack(ModItems.waste_thorium_hot, 4), ModItems.rod_quad_thorium_fuel_depleted);
		addShapelessAuto(new ItemStack(ModItems.waste_plutonium_hot, 1), ModItems.rod_plutonium_fuel_depleted);
		addShapelessAuto(new ItemStack(ModItems.waste_plutonium_hot, 2), ModItems.rod_dual_plutonium_fuel_depleted);
		addShapelessAuto(new ItemStack(ModItems.waste_plutonium_hot, 4), ModItems.rod_quad_plutonium_fuel_depleted);
		addShapelessAuto(new ItemStack(ModItems.waste_mox_hot, 1), ModItems.rod_mox_fuel_depleted);
		addShapelessAuto(new ItemStack(ModItems.waste_mox_hot, 2), ModItems.rod_dual_mox_fuel_depleted);
		addShapelessAuto(new ItemStack(ModItems.waste_mox_hot, 4), ModItems.rod_quad_mox_fuel_depleted);
		addShapelessAuto(new ItemStack(ModItems.waste_schrabidium_hot, 1), ModItems.rod_schrabidium_fuel_depleted);
		addShapelessAuto(new ItemStack(ModItems.waste_schrabidium_hot, 2), ModItems.rod_dual_schrabidium_fuel_depleted);
		addShapelessAuto(new ItemStack(ModItems.waste_schrabidium_hot, 4), ModItems.rod_quad_schrabidium_fuel_depleted);

		addShapelessAuto(new ItemStack(ModItems.nugget_euphemium, 1), ModItems.rod_quad_euphemium);
	}

	public static void addPowderCrafting() {
		addShapelessAuto(new ItemStack(ModItems.powder_tcalloy, 1), STEEL.dust(), TC99.nugget());
		addShapelessAuto(new ItemStack(ModItems.powder_ice, 4), Items.SNOWBALL, KNO.dust(), REDSTONE.dust());
		addShapelessAuto(new ItemStack(ModItems.powder_poison, 4), Items.SPIDER_EYE, REDSTONE.dust(), QUARTZ.gem(), PO210.nugget());
		addShapelessAuto(new ItemStack(ModItems.powder_power, 5), REDSTONE.dust(), "dustGlowstone", DIAMOND.dust(), NP237.dust(), MAGTUNG.dust());
		addShapelessAuto(new ItemStack(ModItems.ballistite, 3), Items.GUNPOWDER, KNO.dust(), Items.SUGAR);
		addShapelessAuto(new ItemStack(Items.GUNPOWDER, 3), S.dust(), KNO.dust(), Items.COAL);
		addShapelessAuto(new ItemStack(Items.GUNPOWDER, 3), S.dust(), KNO.dust(), new ItemStack(Items.COAL, 1, 1));

		addShapelessAuto(new ItemStack(ModItems.powder_nitan_mix, 6), NP237.dust(), I.dust(), TH232.dust(), AT.dust(), ND.dust(), CS.dust());
		addShapelessAuto(new ItemStack(ModItems.powder_nitan_mix, 6), SR.dust(), CO.dust(), BR.dust(), TS.dust(), NB.dust(), CE.dust());
		addShapelessAuto(new ItemStack(ModItems.powder_spark_mix, 5), DESH.dust(), EUPH.dust(), ModItems.powder_meteorite, ModItems.powder_power, ModItems.powder_nitan_mix);
		addShapelessAuto(new ItemStack(ModItems.powder_meteorite, 5), IRON.dust(), CU.dust(), LI.dust(), W.dust(), U.dust());
		addShapelessAuto(new ItemStack(ModItems.powder_thermite, 4), IRON.dust(), IRON.dust(), IRON.dust(), AL.dust());
		addShapelessAuto(new ItemStack(ModItems.powder_desh_mix, 1), B.dustTiny(), B.dustTiny(), AC.dustTiny(), LA.dustTiny(), CE.dustTiny(), CO.dustTiny(), LI.dustTiny(), ND.dustTiny(), NB.dustTiny());
		addShapelessAuto(new ItemStack(ModItems.powder_desh_mix, 9), B.dust(), B.dust(), AC.dust(), LA.dust(), CE.dust(), CO.dust(), LI.dust(), ND.dust(), NB.dust());
		addShapelessAuto(new ItemStack(ModItems.powder_desh_ready, 1), ModItems.powder_desh_mix, ModItems.nugget_mercury, ModItems.nugget_mercury, COAL.dust());
		addShapelessAuto(new ItemStack(ModItems.powder_semtex_mix, 1), ModItems.solid_fuel, ModItems.ballistite, KNO.dust());
		addShapelessAuto(new ItemStack(ModItems.powder_radspice, 1), CO60.dust(), SR90.dust(), I131.dust(), CS137.dust(), XE135.dust(), AU198.dust(), PB209.dust(), AT209.dust(), AC227.dust());
		addShapelessAuto(new ItemStack(ModItems.powder_radspice_tiny, 1), CO60.dustTiny(), SR90.dustTiny(), I131.dustTiny(), CS137.dustTiny(), XE135.dustTiny(), AU198.dustTiny(), PB209.dustTiny(), AT209.dustTiny(), AC227.dustTiny());

		addShapelessAuto(new ItemStack(ModItems.powder_flux, 1), new ItemStack(Items.COAL, 1, 1), KEY_SAND);
		addShapelessAuto(new ItemStack(ModItems.powder_flux, 2), COAL.dust(), KEY_SAND);
		addShapelessAuto(new ItemStack(ModItems.powder_flux, 4), F.dust(), KEY_SAND);
		addShapelessAuto(new ItemStack(ModItems.powder_flux, 8), PB.dust(), S.dust(), KEY_SAND);
        addShapelessAuto(new ItemStack(ModItems.powder_flux, 12), CA.dust(), KEY_SAND);
        addShapelessAuto(new ItemStack(ModItems.powder_flux, 16), BORAX.dust(), KEY_SAND);
	}

	public static void addGearCrafting() {
		if(GeneralConfig.enableBabyMode) {
			addRecipeAuto(new ItemStack(ModItems.starmetal_helmet, 1), "EEE", "E E", 'E', STAR.ingot());
			addRecipeAuto(new ItemStack(ModItems.starmetal_plate, 1), "E E", "EEE", "EEE", 'E', STAR.ingot());
			addRecipeAuto(new ItemStack(ModItems.starmetal_legs, 1), "EEE", "E E", "E E", 'E', STAR.ingot());
			addRecipeAuto(new ItemStack(ModItems.starmetal_boots, 1), "E E", "E E", 'E', STAR.ingot());
			addRecipeAuto(new ItemStack(ModItems.schrabidium_helmet, 1), "EEE", "E E", 'E', SA326.ingot());
			addRecipeAuto(new ItemStack(ModItems.schrabidium_plate, 1), "E E", "EEE", "EEE", 'E', SA326.ingot());
			addRecipeAuto(new ItemStack(ModItems.schrabidium_legs, 1), "EEE", "E E", "E E", 'E', SA326.ingot());
			addRecipeAuto(new ItemStack(ModItems.schrabidium_boots, 1), "E E", "E E", 'E', SA326.ingot());
			addRecipeAuto(new ItemStack(ModItems.schrabidium_sword, 1), "I", "I", "S", 'I', SA326.ingot(), 'S', Items.STICK);
            addRecipeAuto(new ItemStack(ModItems.schrabidium_shield, 1), "IAI", "III", " I ", 'I', SA326.ingot(), 'A', DESH.ingot());
            addRecipeAuto(new ItemStack(ModItems.schrabidium_pickaxe, 1), "III", " S ", " S ", 'I', SA326.ingot(), 'S', Items.STICK);
			addRecipeAuto(new ItemStack(ModItems.schrabidium_axe, 1), "II", "IS", " S", 'I', SA326.ingot(), 'S', Items.STICK);
			addRecipeAuto(new ItemStack(ModItems.schrabidium_shovel, 1), "I", "S", "S", 'I', SA326.ingot(), 'S', Items.STICK);
			addRecipeAuto(new ItemStack(ModItems.schrabidium_hoe, 1), "II", " S", " S", 'I', SA326.ingot(), 'S', Items.STICK);
		} else {
			addRecipeAuto(new ItemStack(ModItems.starmetal_helmet, 1), "EEE", "ECE", 'E', STAR.ingot(), 'C', ModItems.cobalt_helmet);
			addRecipeAuto(new ItemStack(ModItems.starmetal_plate, 1), "ECE", "EEE", "EEE", 'E', STAR.ingot(), 'C', ModItems.cobalt_plate);
			addRecipeAuto(new ItemStack(ModItems.starmetal_legs, 1), "EEE", "ECE", "E E", 'E', STAR.ingot(), 'C', ModItems.cobalt_legs);
			addRecipeAuto(new ItemStack(ModItems.starmetal_boots, 1), "E E", "ECE", 'E', STAR.ingot(), 'C', ModItems.cobalt_boots);
			addRecipeAuto(new ItemStack(ModItems.schrabidium_helmet, 1), "EEE", "ESE", " P ", 'E', SA326.ingot(), 'S', ModItems.starmetal_helmet, 'P', ModItems.pellet_charged);
			addRecipeAuto(new ItemStack(ModItems.schrabidium_plate, 1), "ESE", "EPE", "EEE", 'E', SA326.ingot(), 'S', ModItems.starmetal_plate, 'P', ModItems.pellet_charged);
			addRecipeAuto(new ItemStack(ModItems.schrabidium_legs, 1), "EEE", "ESE", "EPE", 'E', SA326.ingot(), 'S', ModItems.starmetal_legs, 'P', ModItems.pellet_charged);
			addRecipeAuto(new ItemStack(ModItems.schrabidium_boots, 1), "EPE", "ESE", 'E', SA326.ingot(), 'S', ModItems.starmetal_boots, 'P', ModItems.pellet_charged);
			addRecipeAuto(new ItemStack(ModItems.schrabidium_sword, 1), "I", "W", "S", 'I', SA326.block(), 'W', ModItems.starmetal_sword, 'S', POLYMER.ingot());
            addRecipeAuto(new ItemStack(ModItems.schrabidium_shield, 1), "ISI", "IWI", "PIP", 'I', SA326.block(), 'W', ModItems.starmetal_shield, 'S', ModItems.pellet_charged, 'P', ModItems.powder_fire);
            addRecipeAuto(new ItemStack(ModItems.schrabidium_pickaxe, 1), "SWS", " P ", " P ", 'S', ModItems.blades_schrabidium, 'W', ModItems.desh_pickaxe, 'P', POLYMER.ingot());
			addRecipeAuto(new ItemStack(ModItems.schrabidium_axe, 1), "SW", "SP", " P", 'S', ModItems.blades_schrabidium, 'W', ModItems.desh_axe, 'P', POLYMER.ingot());
			addRecipeAuto(new ItemStack(ModItems.schrabidium_shovel, 1), "S", "W", "P", 'S', ModItems.blades_schrabidium, 'W', ModItems.desh_shovel, 'P', POLYMER.ingot());
			addRecipeAuto(new ItemStack(ModItems.schrabidium_hoe, 1), "IW", " S", " S", 'I', SA326.ingot(), 'W', ModItems.desh_hoe, 'S', POLYMER.ingot());
		}

		addRecipeAuto(new ItemStack(ModItems.steel_helmet, 1), "EEE", "E E", 'E', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.steel_plate, 1), "E E", "EEE", "EEE", 'E', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.steel_legs, 1), "EEE", "E E", "E E", 'E', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.steel_boots, 1), "E E", "E E", 'E', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.steel_sword, 1), "I", "I", "S", 'I', STEEL.ingot(), 'S', Items.STICK);
        addRecipeAuto(new ItemStack(ModItems.steel_shield, 1), "IAI", "III", " I ", 'I', STEEL.ingot(), 'A', IRON.ingot());
        addRecipeAuto(new ItemStack(ModItems.steel_pickaxe, 1), "III", " S ", " S ", 'I', STEEL.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.steel_axe, 1), "II", "IS", " S", 'I', STEEL.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.steel_shovel, 1), "I", "S", "S", 'I', STEEL.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.steel_hoe, 1), "II", " S", " S", 'I', STEEL.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.titanium_helmet, 1), "EEE", "E E", 'E', TI.ingot());
		addRecipeAuto(new ItemStack(ModItems.titanium_plate, 1), "E E", "EEE", "EEE", 'E', TI.ingot());
		addRecipeAuto(new ItemStack(ModItems.titanium_legs, 1), "EEE", "E E", "E E", 'E', TI.ingot());
		addRecipeAuto(new ItemStack(ModItems.titanium_boots, 1), "E E", "E E", 'E', TI.ingot());
		addRecipeAuto(new ItemStack(ModItems.titanium_sword, 1), "I", "I", "S", 'I', TI.ingot(), 'S', Items.STICK);
        addRecipeAuto(new ItemStack(ModItems.titanium_shield, 1), "IAI", "III", " I ", 'I', TI.ingot(), 'A', IRON.ingot());
        addRecipeAuto(new ItemStack(ModItems.titanium_pickaxe, 1), "III", " S ", " S ", 'I', TI.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.titanium_axe, 1), "II", "IS", " S", 'I', TI.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.titanium_shovel, 1), "I", "S", "S", 'I', TI.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.titanium_hoe, 1), "II", " S", " S", 'I', TI.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.cobalt_sword, 1), "I", "I", "S", 'I', CO.ingot(), 'S', Items.STICK);
        addRecipeAuto(new ItemStack(ModItems.cobalt_shield, 1), "IAI", "III", " I ", 'I', CO.ingot(), 'A', IRON.ingot());
        addRecipeAuto(new ItemStack(ModItems.cobalt_pickaxe, 1), "III", " S ", " S ", 'I', CO.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.cobalt_axe, 1), "II", "IS", " S", 'I', CO.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.cobalt_shovel, 1), "I", "S", "S", 'I', CO.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.cobalt_hoe, 1), "II", " S", " S", 'I', CO.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.alloy_helmet, 1), "EEE", "E E", 'E', ALLOY.ingot());
		addRecipeAuto(new ItemStack(ModItems.alloy_plate, 1), "E E", "EEE", "EEE", 'E', ALLOY.ingot());
		addRecipeAuto(new ItemStack(ModItems.alloy_legs, 1), "EEE", "E E", "E E", 'E', ALLOY.ingot());
		addRecipeAuto(new ItemStack(ModItems.alloy_boots, 1), "E E", "E E", 'E', ALLOY.ingot());
		addRecipeAuto(new ItemStack(ModItems.alloy_sword, 1), "I", "I", "S", 'I', ALLOY.ingot(), 'S', Items.STICK);
        addRecipeAuto(new ItemStack(ModItems.alloy_shield, 1), "IAI", "III", " I ", 'I', ALLOY.ingot(), 'A', IRON.ingot());
        addRecipeAuto(new ItemStack(ModItems.alloy_pickaxe, 1), "III", " S ", " S ", 'I', ALLOY.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.alloy_axe, 1), "II", "IS", " S", 'I', ALLOY.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.alloy_shovel, 1), "I", "S", "S", 'I', ALLOY.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.alloy_hoe, 1), "II", " S", " S", 'I', ALLOY.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.cmb_helmet, 1), "EEE", "E E", 'E', CMB.ingot());
		addRecipeAuto(new ItemStack(ModItems.cmb_plate, 1), "E E", "EEE", "EEE", 'E', CMB.ingot());
		addRecipeAuto(new ItemStack(ModItems.cmb_legs, 1), "EEE", "E E", "E E", 'E', CMB.ingot());
		addRecipeAuto(new ItemStack(ModItems.cmb_boots, 1), "E E", "E E", 'E', CMB.ingot());
		addRecipeAuto(new ItemStack(ModItems.cmb_sword, 1), "I", "I", "S", 'I', CMB.ingot(), 'S', Items.STICK);
        addRecipeAuto(new ItemStack(ModItems.cmb_shield, 1), "IAI", "III", " I ", 'I', CMB.ingot(), 'A', IRON.ingot());
        addRecipeAuto(new ItemStack(ModItems.cmb_pickaxe, 1), "III", " S ", " S ", 'I', CMB.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.cmb_axe, 1), "II", "IS", " S", 'I', CMB.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.cmb_shovel, 1), "I", "S", "S", 'I', CMB.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.cmb_hoe, 1), "II", " S", " S", 'I', CMB.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.elec_sword, 1), "RPR", "RPR", " B ", 'P', POLYMER.ingot(), 'R', new ItemStack(ModItems.bolt, 1, MAT_DURA.id), 'B', ModItems.battery_lithium);
        addRecipeAuto(new ItemStack(ModItems.elec_shield, 1), "IAI", "IAI", "IAI", 'I', POLYMER.ingot(), 'A', "paneGlassColorless");
        addRecipeAuto(new ItemStack(ModItems.elec_pickaxe, 1), "RDM", " PB", " P ", 'P', POLYMER.ingot(), 'D', DURA.ingot(), 'R', new ItemStack(ModItems.bolt, 1, MAT_DURA.id), 'M', ModItems.motor, 'B', ModItems.battery_lithium);
		addRecipeAuto(new ItemStack(ModItems.elec_axe, 1), " DP", "RRM", " PB", 'P', POLYMER.ingot(), 'D', DURA.ingot(), 'R', new ItemStack(ModItems.bolt, 1, MAT_DURA.id), 'M', ModItems.motor, 'B', ModItems.battery_lithium);
		addRecipeAuto(new ItemStack(ModItems.elec_shovel, 1), "  P", "RRM", "  B", 'P', POLYMER.ingot(), 'R', new ItemStack(ModItems.bolt, 1, MAT_DURA.id), 'M', ModItems.motor, 'B', ModItems.battery_lithium);
		addShapelessAuto(new ItemStack(ModItems.centri_stick, 1), ModItems.centrifuge_element, ModItems.energy_core, Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.smashing_hammer, 1), "STS", "SPS", " P ", 'S', STEEL.block(), 'T', W.block(), 'P', POLYMER.ingot());
		addRecipeAuto(new ItemStack(ModItems.desh_sword, 1), "I", "I", "S", 'I', DESH.ingot(), 'S', Items.STICK);
        addRecipeAuto(new ItemStack(ModItems.desh_shield, 1), "IAI", "III", " I ", 'I', DESH.ingot(), 'A', POLYMER.ingot());
        addRecipeAuto(new ItemStack(ModItems.desh_pickaxe, 1), "III", " S ", " S ", 'I', DESH.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.desh_axe, 1), "II", "IS", " S", 'I', DESH.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.desh_shovel, 1), "I", "S", "S", 'I', DESH.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.desh_hoe, 1), "II", " S", " S", 'I', DESH.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.security_helmet, 1), "SSS", "IGI", 'S', STEEL.plate(), 'I', ModItems.plate_kevlar, 'G', KEY_ANYPANE);
		addRecipeAuto(new ItemStack(ModItems.security_plate, 1), "KWK", "IKI", "WKW", 'K', ModItems.plate_kevlar, 'I', POLYMER.ingot(), 'W', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE));
		addRecipeAuto(new ItemStack(ModItems.security_legs, 1), "IWI", "K K", "W W", 'K', ModItems.plate_kevlar, 'I', POLYMER.ingot(), 'W', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE));
		addRecipeAuto(new ItemStack(ModItems.security_boots, 1), "P P", "I I", 'P', STEEL.plate(), 'I', ModItems.plate_kevlar);

		addRecipeAuto(new ItemStack(ModItems.jetpack_boost, 1), "PTP", "SLS", "W W", 'P', STEEL.plate(), 'T', ModItems.tank_steel, 'S', ModItems.pipes_steel, 'L', Items.LEATHER, 'W', ModItems.thruster_small);
		addRecipeAuto(new ItemStack(ModItems.jetpack_fly, 1), "PTP", "SLS", "W W", 'P', STEEL.plate(), 'T', ModItems.cap_aluminium, 'S', ModItems.pipes_steel, 'L', ModItems.jetpack_boost, 'W', ModItems.thruster_small);
		addRecipeAuto(new ItemStack(ModItems.jetpack_break, 1), "PTP", "SLS", "P P", 'P', STEEL.plate(), 'T', ModItems.cap_aluminium, 'S', ModItems.coil_tungsten, 'L', ModItems.jetpack_boost);
		addRecipeAuto(new ItemStack(ModItems.jetpack_vector, 1), "PTP", "SLS", "W W", 'P', TI.plate(), 'T', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'S', ModItems.motor, 'L', ModItems.jetpack_fly, 'W', ModItems.thruster_small);

		addRecipeAuto(new ItemStack(ModItems.chainsaw, 1), "  H", "BBP", "  C", 'H', ModItems.hull_small_steel, 'B', ModItems.blades_steel, 'P', ModItems.piston_selenium, 'C', new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.DIESEL)));
		addRecipeAuto(new ItemStack(ModItems.chainsaw, 1), "  H", "BBP", "  C", 'H', ModItems.hull_small_steel, 'B', ModItems.blades_steel, 'P', ModItems.piston_selenium, 'C', new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.PETROIL)));
		addRecipeAuto(new ItemStack(ModItems.chainsaw, 1), "  H", "BBP", "  C", 'H', ModItems.hull_small_steel, 'B', ModItems.blades_steel, 'P', ModItems.piston_selenium, 'C', new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.BIOFUEL)));
		addRecipeAuto(new ItemStack(ModItems.hazmat_helmet, 1), "EEE", "EIE", "FPF", 'E', ModItems.hazmat_cloth, 'I', KEY_ANYPANE, 'P', STEEL.plate(), 'F', ModItems.filter_coal);
		addRecipeAuto(new ItemStack(ModItems.hazmat_plate, 1), "E E", "EEE", "EEE", 'E', ModItems.hazmat_cloth);
		addRecipeAuto(new ItemStack(ModItems.hazmat_legs, 1), "EEE", "E E", "E E", 'E', ModItems.hazmat_cloth);
		addRecipeAuto(new ItemStack(ModItems.hazmat_boots, 1), "E E", "E E", 'E', ModItems.hazmat_cloth);
		addShapelessAuto(new ItemStack(ModItems.hazmat_kit, 1), new ItemStack(ModItems.gas_mask_filter, 1, 0), new ItemStack(ModItems.hazmat_helmet, 1, 0), new ItemStack(ModItems.hazmat_plate, 1, 0), new ItemStack(ModItems.hazmat_legs, 1, 0), new ItemStack(ModItems.hazmat_boots, 1, 0));
		addRecipeAuto(new ItemStack(ModItems.hazmat_helmet_red, 1), "EEE", "IEI", "EFE", 'E', ModItems.hazmat_cloth_red, 'I', KEY_ANYPANE, 'F', ModItems.gas_mask_filter);
		addRecipeAuto(new ItemStack(ModItems.hazmat_plate_red, 1), "E E", "EEE", "EEE", 'E', ModItems.hazmat_cloth_red);
		addRecipeAuto(new ItemStack(ModItems.hazmat_legs_red, 1), "EEE", "E E", "E E", 'E', ModItems.hazmat_cloth_red);
		addRecipeAuto(new ItemStack(ModItems.hazmat_boots_red, 1), "E E", "E E", 'E', ModItems.hazmat_cloth_red);
		addShapelessAuto(new ItemStack(ModItems.hazmat_red_kit, 1), new ItemStack(ModItems.gas_mask_filter, 1, 0), new ItemStack(ModItems.hazmat_helmet_red, 1, 0), new ItemStack(ModItems.hazmat_plate_red, 1, 0), new ItemStack(ModItems.hazmat_legs_red, 1, 0), new ItemStack(ModItems.hazmat_boots_red, 1, 0));
		addRecipeAuto(new ItemStack(ModItems.hazmat_helmet_grey, 1), "EEE", "IEI", "EFE", 'E', ModItems.hazmat_cloth_grey, 'I', KEY_ANYPANE, 'F', ModItems.gas_mask_filter);
		addRecipeAuto(new ItemStack(ModItems.hazmat_plate_grey, 1), "E E", "EEE", "EEE", 'E', ModItems.hazmat_cloth_grey);
		addRecipeAuto(new ItemStack(ModItems.hazmat_legs_grey, 1), "EEE", "E E", "E E", 'E', ModItems.hazmat_cloth_grey);
		addRecipeAuto(new ItemStack(ModItems.hazmat_boots_grey, 1), "E E", "E E", 'E', ModItems.hazmat_cloth_grey);
		addShapelessAuto(new ItemStack(ModItems.hazmat_grey_kit, 1), new ItemStack(ModItems.gas_mask_filter_combo, 1, 0), new ItemStack(ModItems.hazmat_helmet_grey, 1, 0), new ItemStack(ModItems.hazmat_plate_grey, 1, 0), new ItemStack(ModItems.hazmat_legs_grey, 1, 0), new ItemStack(ModItems.hazmat_boots_grey, 1, 0));
		addRecipeAuto(new ItemStack(ModItems.asbestos_helmet, 1), "EEE", "EIE", 'E', ModItems.asbestos_cloth, 'I', GOLD.plate());
		addRecipeAuto(new ItemStack(ModItems.asbestos_plate, 1), "E E", "EEE", "EEE", 'E', ModItems.asbestos_cloth);
		addRecipeAuto(new ItemStack(ModItems.asbestos_legs, 1), "EEE", "E E", "E E", 'E', ModItems.asbestos_cloth);
		addRecipeAuto(new ItemStack(ModItems.asbestos_boots, 1), "E E", "E E", 'E', ModItems.asbestos_cloth);

		addRecipeAuto(new ItemStack(ModItems.hazmat_paa_helmet, 1), "EEE", "EIE", "FPF", 'E', ModItems.plate_paa, 'I', ModItems.liquidator_helmet, 'P', STEEL.plate(), 'F', ModItems.filter_coal);
		addRecipeAuto(new ItemStack(ModItems.hazmat_paa_plate, 1), "E E", "EIE", "EEE", 'E', ModItems.plate_paa, 'I', ModItems.liquidator_plate);
		addRecipeAuto(new ItemStack(ModItems.hazmat_paa_legs, 1), "EEE", "EIE", "E E", 'E', ModItems.plate_paa, 'I', ModItems.liquidator_legs);
		addRecipeAuto(new ItemStack(ModItems.hazmat_paa_boots, 1), "E E", "EIE", 'E', ModItems.plate_paa, 'I', ModItems.liquidator_boots);

		addRecipeAuto(new ItemStack(ModItems.paa_helmet, 1), "XGX", "NEN", 'E', ModItems.hazmat_paa_helmet, 'N', OreDictManager.getReflector(), 'G', ModItems.billet_gh336, 'X', ModItems.powder_verticium);
		addRecipeAuto(new ItemStack(ModItems.paa_plate, 1), "XGX", "NEN", 'E', ModItems.hazmat_paa_plate, 'N', OreDictManager.getReflector(), 'G', ModItems.billet_gh336, 'X', ModItems.powder_verticium);
		addRecipeAuto(new ItemStack(ModItems.paa_legs, 1), "XGX", "NEN", 'E', ModItems.hazmat_paa_legs, 'N', OreDictManager.getReflector(), 'G', ModItems.billet_gh336, 'X', ModItems.powder_verticium);
		addRecipeAuto(new ItemStack(ModItems.paa_boots, 1), "XGX", "NEN", 'E', ModItems.hazmat_paa_boots, 'N', OreDictManager.getReflector(), 'G', ModItems.billet_gh336, 'X', ModItems.powder_verticium);

		addRecipeAuto(new ItemStack(ModItems.goggles, 1), "P P", "GPG", 'G', KEY_ANYPANE, 'P', STEEL.plate());
		addRecipeAuto(new ItemStack(ModItems.gas_mask, 1), "PPP", "GPG", " F ", 'G', KEY_ANYPANE, 'P', STEEL.plate(), 'F', IRON.plate());
		addRecipeAuto(new ItemStack(ModItems.gas_mask_m65, 1), "PPP", "GPG", " F ", 'G', KEY_ANYPANE, 'P', ANY_RUBBER.ingot(), 'F', IRON.plate());
		addRecipeAuto(new ItemStack(ModItems.gas_mask_mono, 1), " P ", "PPP", " F ", 'P', ANY_RUBBER.ingot(), 'F', IRON.plate());
		addRecipeAuto(new ItemStack(ModItems.mask_damp, 1), "RRR", 'R', ModItems.rag_damp);
		addRecipeAuto(new ItemStack(ModItems.mask_piss, 1), "RRR", 'R', ModItems.rag_piss);
		addRecipeAuto(new ItemStack(ModItems.mask_rag, 1), "RRR", 'R', ModItems.rag);
		addRecipeAuto(new ItemStack(ModItems.jetpack_tank, 1), " S ", "BKB", " S ", 'S', STEEL.plate(), 'B', new ItemStack(ModItems.bolt, 1, MAT_STEEL.id), 'K', new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.KEROSENE)));
		addRecipeAuto(new ItemStack(ModItems.gun_kit_1, 4), "I ", "LB", "P ", 'I', ANY_RUBBER.ingot(), 'L', new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.LUBRICANT)), 'B', new ItemStack(ModItems.bolt, 1, MAT_STEEL.id), 'P', IRON.plate());
		addRecipeAuto(new ItemStack(ModItems.gun_kit_2, 1), "III", "GLG", "PPP", 'I', ANY_RUBBER.ingot(), 'L', ModItems.ducttape, 'G', ModItems.gun_kit_1, 'P', IRON.plate());

		addRecipeAuto(new ItemStack(ModItems.igniter, 1), " W", "SC", "CE", 'S', STEEL.plate(), 'W', new ItemStack(ModItems.wire, 1, MAT_SCHRABIDIUM.id), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'E', EUPH.ingot());
		addRecipeAuto(new ItemStack(ModItems.euphemium_helmet, 1), "EEE", "E E", 'E', ModItems.plate_euphemium);
		addRecipeAuto(new ItemStack(ModItems.euphemium_plate, 1), "EWE", "EEE", "EEE", 'E', ModItems.plate_euphemium, 'W', ModItems.watch);
		addRecipeAuto(new ItemStack(ModItems.euphemium_legs, 1), "EEE", "E E", "E E", 'E', ModItems.plate_euphemium);
		addRecipeAuto(new ItemStack(ModItems.euphemium_boots, 1), "E E", "E E", 'E', ModItems.plate_euphemium);
		addRecipeAuto(new ItemStack(ModItems.watch, 1), "LEL", "EWE", "LEL", 'E', EUPH.ingot(), 'L', new ItemStack(Items.DYE, 1, 4), 'W', Items.CLOCK);
		addRecipeAuto(new ItemStack(ModItems.apple_euphemium, 1), "EEE", "EAE", "EEE", 'E', EUPH.nugget(), 'A', Items.APPLE);

		addRecipeAuto(new ItemStack(ModItems.mask_of_infamy, 1), "III", "III", " I ", 'I', IRON.plate());

		addRecipeAuto(new ItemStack(ModItems.cobalt_helmet, 1), "EEE", "E E", 'E', CO.ingot());
		addRecipeAuto(new ItemStack(ModItems.cobalt_plate, 1), "E E", "EEE", "EEE", 'E', CO.ingot());
		addRecipeAuto(new ItemStack(ModItems.cobalt_legs, 1), "EEE", "E E", "E E", 'E', CO.ingot());
		addRecipeAuto(new ItemStack(ModItems.cobalt_boots, 1), "E E", "E E", 'E', CO.ingot());

		addRecipeAuto(new ItemStack(ModItems.t45_helmet, 1), "PPC", "PBP", "IXI", 'P', ModItems.plate_armor_titanium, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'I', ANY_RUBBER.ingot(), 'X', ModItems.gas_mask_m65, 'B', ModItems.titanium_helmet);
		addRecipeAuto(new ItemStack(ModItems.t45_plate, 1), "MPM", "TBT", "PPP", 'M', ModItems.motor, 'P', ModItems.plate_armor_titanium, 'T', ModItems.gas_canister, 'B', ModItems.titanium_plate);
		addRecipeAuto(new ItemStack(ModItems.t45_legs, 1), "MPM", "PBP", "P P", 'M', ModItems.motor, 'P', ModItems.plate_armor_titanium, 'B', ModItems.titanium_legs);
		addRecipeAuto(new ItemStack(ModItems.t45_boots, 1), "P P", "PBP", 'P', ModItems.plate_armor_titanium, 'B', ModItems.titanium_boots);
		addRecipeAuto(new ItemStack(ModItems.bj_helmet, 1), "SBS", " C ", " I ", 'S', Items.STRING, 'B', new ItemStack(Blocks.WOOL, 1, 15), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'I', STAR.ingot());
		addRecipeAuto(new ItemStack(ModItems.bj_plate, 1), "N N", "MSM", "NCN", 'N', ModItems.plate_armor_lunar, 'M', ModItems.motor_desh, 'S', ModItems.starmetal_plate, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR_BOARD));
		addRecipeAuto(new ItemStack(ModItems.bj_plate_jetpack, 1), "NFN", "TPT", "ICI", 'N', ModItems.plate_armor_lunar, 'F', ModItems.fins_quad_titanium, 'T', new IngredientNBT2(ItemFluidTank.getFullTank(ModForgeFluids.XENON)), 'P', ModItems.bj_plate, 'I', ModItems.mp_thruster_10_xenon, 'C', P_RED.crystal());
		addRecipeAuto(new ItemStack(ModItems.bj_legs, 1), "NBN", "MSM", "N N", 'N', ModItems.plate_armor_lunar, 'M', ModItems.motor_desh, 'S', ModItems.starmetal_legs, 'B', STAR.block());
		addRecipeAuto(new ItemStack(ModItems.bj_boots, 1), "N N", "BSB", 'N', ModItems.plate_armor_lunar, 'S', ModItems.starmetal_boots, 'B', STAR.block());

		addRecipeAuto(new ItemStack(ModItems.rpa_helmet, 1), "PXP", "PLP", "KFK", 'L', ModItems.cmb_helmet, 'K', ModItems.plate_kevlar, 'P', ModItems.plate_armor_lunar, 'F', ModItems.gas_mask_m65, 'X', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CONTROLLER_ADVANCED));
		addRecipeAuto(new ItemStack(ModItems.rpa_plate, 1), "MXM", "KLK", "PPP", 'L', ModItems.cmb_plate, 'K', ModItems.plate_kevlar, 'P', ModItems.plate_armor_lunar, 'M', ModItems.motor_desh, 'X', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BISMOID));
		addRecipeAuto(new ItemStack(ModItems.rpa_legs, 1), "MXM", "KLK", "P P", 'L', ModItems.cmb_legs, 'K', ModItems.plate_kevlar, 'P', ModItems.plate_armor_lunar, 'M', ModItems.motor_desh, 'X', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR_TANTALIUM));
		addRecipeAuto(new ItemStack(ModItems.rpa_boots, 1), "P P", "PLP", 'L', ModItems.cmb_boots, 'P', ModItems.plate_armor_lunar);

		addRecipeAuto(new ItemStack(ModItems.ajr_helmet, 1), "PPC", "PBP", "IXI", 'P', ModItems.plate_armor_ajr, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'I', ANY_PLASTIC.ingot(), 'X', ModItems.gas_mask_m65, 'B', ModItems.alloy_helmet);
		addRecipeAuto(new ItemStack(ModItems.ajr_plate, 1), "MPM", "TBT", "PPP", 'M', ModItems.motor_desh, 'P', ModItems.plate_armor_ajr, 'T', ModItems.gas_canister, 'B', ModItems.alloy_plate);
		addRecipeAuto(new ItemStack(ModItems.ajr_legs, 1), "MPM", "PBP", "P P", 'M', ModItems.motor_desh, 'P', ModItems.plate_armor_ajr, 'B', ModItems.alloy_legs);
		addRecipeAuto(new ItemStack(ModItems.ajr_boots, 1), "P P", "PBP", 'P', ModItems.plate_armor_ajr, 'B', ModItems.alloy_boots);
		addShapelessAuto(new ItemStack(ModItems.ajro_helmet, 1), ModItems.ajr_helmet, "dyeRed", "dyeBlack");
		addShapelessAuto(new ItemStack(ModItems.ajro_plate, 1), ModItems.ajr_plate, "dyeRed", "dyeBlack");
		addShapelessAuto(new ItemStack(ModItems.ajro_legs, 1), ModItems.ajr_legs, "dyeRed", "dyeBlack");
		addShapelessAuto(new ItemStack(ModItems.ajro_boots, 1), ModItems.ajr_boots, "dyeRed", "dyeBlack");
		addRecipeAuto(new ItemStack(ModItems.fau_helmet, 1), "PWP", "PBP", "FSF", 'P', ModItems.plate_armor_fau, 'W', new ItemStack(Blocks.WOOL, 1, 14), 'B', ModItems.schrabidium_helmet, 'F', ModItems.gas_mask_filter, 'S', ModItems.pipes_steel);
		addRecipeAuto(new ItemStack(ModItems.fau_plate, 1), "MCM", "PBP", "PSP", 'M', ModItems.motor_desh, 'C', ModItems.demon_core_closed, 'P', ModItems.plate_armor_fau, 'B', ModItems.schrabidium_plate, 'S', ModBlocks.ancient_scrap);
		addRecipeAuto(new ItemStack(ModItems.fau_legs, 1), "MPM", "PBP", "PDP", 'M', ModItems.motor_desh, 'P', ModItems.plate_armor_fau, 'B', ModItems.schrabidium_legs, 'D', PO210.billet());
		addRecipeAuto(new ItemStack(ModItems.fau_boots, 1), "PDP", "PBP", 'P', ModItems.plate_armor_fau, 'D', PO210.billet(), 'B', ModItems.schrabidium_boots);
		addRecipeAuto(new ItemStack(ModItems.dns_helmet, 1), "PCP", "PBP", "PSP", 'P', ModItems.plate_armor_dnt, 'S', ModItems.ingot_chainsteel, 'B', ModItems.bj_helmet, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.QUANTUM));
		addRecipeAuto(new ItemStack(ModItems.dns_plate, 1), "PCP", "PBP", "PSP", 'P', ModItems.plate_armor_dnt, 'S', ModItems.ingot_chainsteel, 'B', ModItems.bj_plate_jetpack, 'C', ModItems.singularity_spark);
		addRecipeAuto(new ItemStack(ModItems.dns_legs, 1), "PCP", "PBP", "PSP", 'P', ModItems.plate_armor_dnt, 'S', ModItems.ingot_chainsteel, 'B', ModItems.bj_legs, 'C', ModItems.coin_worm);
		addRecipeAuto(new ItemStack(ModItems.dns_boots, 1), "PCP", "PBP", "PSP", 'P', ModItems.plate_armor_dnt, 'S', ModItems.ingot_chainsteel, 'B', ModItems.bj_boots, 'C', ModItems.demon_core_closed);

		//Liquidator Suit
		addRecipeAuto(new ItemStack(ModItems.liquidator_helmet, 1), "III", "CBC", "IFI", 'I', ANY_RUBBER.ingot(), 'C', ModItems.cladding_lead, 'B', ModItems.hazmat_helmet_grey, 'F', ModItems.gas_mask_filter_mono);
		addRecipeAuto(new ItemStack(ModItems.liquidator_plate, 1), "ICI", "TBT", "ICI", 'I', ANY_RUBBER.ingot(), 'C', ModItems.cladding_lead, 'B', ModItems.hazmat_plate_grey, 'T', ModItems.gas_canister);
		addRecipeAuto(new ItemStack(ModItems.liquidator_legs, 1), "III", "CBC", "I I", 'I', ANY_RUBBER.ingot(), 'C', ModItems.cladding_lead, 'B', ModItems.hazmat_legs_grey);
		addRecipeAuto(new ItemStack(ModItems.liquidator_boots, 1), "ICI", "IBI", 'I', ANY_RUBBER.ingot(), 'C', ModItems.cladding_lead, 'B', ModItems.hazmat_boots_grey);

		addRecipeAuto(new ItemStack(ModItems.gas_mask_filter, 1), "I", "F", 'F', ModItems.filter_coal, 'I', IRON.plate());
		addRecipeAuto(new ItemStack(ModItems.gas_mask_filter_mono, 1), "ZZZ", "ZCZ", "ZZZ", 'Z', ZR.nugget(), 'C', ModItems.catalyst_clay);
		addRecipeAuto(new ItemStack(ModItems.gas_mask_filter_combo, 1), "ZCZ", "CFC", "ZCZ", 'Z', ZR.ingot(), 'C', ModItems.catalyst_clay, 'F', ModItems.gas_mask_filter);
		addRecipeAuto(new ItemStack(ModItems.gas_mask_filter_radon, 1), "ZCZ", "NFN", "ZCZ", 'Z', ModItems.nugget_verticium, 'N', ModItems.powder_radspice, 'C', ModItems.insert_xsapi, 'F', ModItems.gas_mask_filter_combo);
		addRecipeAuto(new ItemStack(ModItems.gas_mask_filter_rag, 1), "I", "F", 'F', ModItems.rag_damp, 'I', IRON.ingot());
		addRecipeAuto(new ItemStack(ModItems.gas_mask_filter_piss, 1), "I", "F", 'F', ModItems.rag_piss, 'I', IRON.ingot());


	}

	public static void addWeaponCrafting() {
		addRecipeAuto(new ItemStack(ModItems.redstone_sword, 1), "R", "R", "S", 'R', Blocks.REDSTONE_BLOCK, 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.big_sword, 1), "QIQ", "QIQ", "GSG", 'G', Items.GOLD_INGOT, 'S', Items.STICK, 'I', Items.IRON_INGOT, 'Q', Items.QUARTZ);

		addRecipeAuto(new ItemStack(ModItems.gun_rpg, 1), "SSW", " MW", 'S', ModItems.hull_small_steel, 'W', IRON.plate(), 'M', ModItems.mechanism_launcher_1);
		addRecipeAuto(new ItemStack(ModItems.gun_panzerschreck, 1), "SSS", " MW", 'S', ModItems.hull_small_steel, 'W', CU.plate(), 'M', ModItems.mechanism_launcher_1);
		addRecipeAuto(new ItemStack(ModItems.gun_karl, 1), "SSW", " MW", 'S', ModItems.hull_small_steel, 'W', ALLOY.plate(), 'M', ModItems.mechanism_launcher_2);
		addRecipeAuto(new ItemStack(ModItems.gun_quadro, 1), "SSS", "SSS", "CM ", 'S', ModItems.hull_small_steel, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'M', ModItems.mechanism_launcher_2);
		addRecipeAuto(new ItemStack(ModItems.gun_hk69, 1), "SSI", " MB", 'S', ModItems.hull_small_steel, 'I', IRON.ingot(), 'M', ModItems.mechanism_launcher_1, 'B', new ItemStack(ModItems.bolt, 1, MAT_STEEL.id));
		addRecipeAuto(new ItemStack(ModItems.gun_stinger, 1), "SSW", "CMW", 'S', STEEL.plate(), 'W', TI.plate(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CRT_TUBE), 'M', ModItems.mechanism_launcher_2);
		addRecipeAuto(new ItemStack(ModItems.gun_stinger_ammo, 4), "SS ", "STI", " IR", 'S', STEEL.plate(), 'T', Item.getItemFromBlock(Blocks.TNT), 'I', AL.plate(), 'R', REDSTONE.dust());
		addRecipeAuto(new ItemStack(ModItems.gun_revolver, 1), "SSM", " RW", 'S', STEEL.plate(), 'W', KEY_PLANKS, 'R', new ItemStack(ModItems.wire, 1, MAT_ALUMINIUM.id), 'M', ModItems.mechanism_revolver_1);
		addRecipeAuto(new ItemStack(ModItems.gun_revolver_saturnite, 1), "SSM", " RW", 'S', BIGMT.plate(), 'W', KEY_PLANKS, 'R', new ItemStack(ModItems.wire, 1, MAT_TUNGSTEN.id), 'M', ModItems.mechanism_revolver_2);
		addRecipeAuto(new ItemStack(ModItems.gun_revolver_iron, 1), "SSM", " RW", 'S', IRON.plate(), 'W', KEY_PLANKS, 'R', new ItemStack(ModItems.wire, 1, MAT_ALUMINIUM.id), 'M', ModItems.mechanism_revolver_1);
		addRecipeAuto(new ItemStack(ModItems.gun_revolver_gold, 1), "SSM", " RW", 'S', GOLD.plate(), 'W', GOLD.ingot(), 'R', new ItemStack(ModItems.wire, 1, MAT_GOLD.id), 'M', ModItems.mechanism_revolver_1);
		addRecipeAuto(new ItemStack(ModItems.gun_revolver_lead, 1), "SSM", " RW", 'S', PB.plate(), 'W', W.ingot(), 'R', new ItemStack(ModItems.wire, 1, MAT_TUNGSTEN.id), 'M', ModItems.mechanism_revolver_2);
		addRecipeAuto(new ItemStack(ModItems.gun_revolver_schrabidium, 1), "SSM", " RW", 'S', SA326.block(), 'W', W.ingot(), 'R', new ItemStack(ModItems.wire, 1, MAT_SCHRABIDIUM.id), 'M', ModItems.mechanism_special);
		addRecipeAuto(new ItemStack(ModItems.gun_deagle, 1), "PPM", " BI", 'P', STEEL.plate(), 'B', new ItemStack(ModItems.bolt, 1, MAT_STEEL.id), 'I', ANY_PLASTIC.ingot(), 'M', ModItems.mechanism_rifle_1);
		addRecipeAuto(new ItemStack(ModItems.gun_revolver_cursed, 1), "TTM", "SRI", 'S', STEEL.plate(), 'I', STEEL.ingot(), 'R', new ItemStack(ModItems.wire, 1, MAT_MINGRADE.id), 'T', TI.plate(), 'M', ModItems.mechanism_revolver_2);
		addRecipeAuto(new ItemStack(ModItems.gun_revolver_nightmare, 1), "SEM", " RW", 'S', STEEL.plate(), 'W', KEY_PLANKS, 'R', new ItemStack(ModItems.wire, 1, MAT_ALUMINIUM.id), 'E', ModItems.powder_power, 'M', ModItems.mechanism_revolver_2);
		addRecipeAuto(new ItemStack(ModItems.gun_revolver_nightmare2, 1), "SSM", "RRW", 'S', getReflector(), 'W', W.ingot(), 'R', new ItemStack(ModItems.wire, 1, MAT_GOLD.id), 'M', ModItems.mechanism_special);
		addRecipeAuto(new ItemStack(ModItems.gun_fatman, 1), "SSI", "IIM", "WPH", 'S', STEEL.plate(), 'I', STEEL.ingot(), 'W', new ItemStack(ModItems.wire, 1, MAT_ALUMINIUM.id), 'H', ModItems.hull_small_steel, 'P', Item.getItemFromBlock(Blocks.PISTON), 'M', ModItems.mechanism_launcher_2);

		addRecipeAuto(new ItemStack(ModItems.gun_mirv, 1), "LLL", "WFW", "SSS", 'S', STEEL.plate(), 'L', PB.plate(), 'W', new ItemStack(ModItems.wire, 1, MAT_GOLD.id), 'F', ModItems.gun_fatman);
		addRecipeAuto(new ItemStack(ModItems.gun_proto, 1), "LLL", "WFW", "SSS", 'S', ANY_RUBBER.ingot(), 'L', ModItems.plate_desh, 'W', new ItemStack(ModItems.wire, 1, MAT_TUNGSTEN.id), 'F', ModItems.gun_fatman);
		//addRecipeAuto(new ItemStack(ModItems.gun_bf, 1), new Object[] { "LLL", "WFW", "SSS", 'S', ModItems.plate_paa, 'L', OreDictManager.getReflector(), 'W', new ItemStack(ModItems.wire, 1, MAT_ALLOY.id), 'F', ModItems.gun_mirv });
		addRecipeAuto(new ItemStack(ModItems.gun_mp40, 1), "IIM", " SW", " S ", 'S', STEEL.plate(), 'I', STEEL.ingot(), 'W', KEY_PLANKS, 'M', ModItems.mechanism_rifle_2);
		addRecipeAuto(new ItemStack(ModItems.gun_thompson, 1), "IIM", " SW", " S ", 'S', IRON.plate(), 'I', STEEL.plate(), 'W', KEY_PLANKS, 'M', ModItems.mechanism_rifle_2);
		addRecipeAuto(new ItemStack(ModItems.gun_flechette, 1), "PPM", "TIS", "G  ", 'P', STEEL.plate(), 'M', ModItems.mechanism_rifle_2, 'T', ModItems.hull_small_steel, 'I', STEEL.ingot(), 'S', ANY_PLASTIC.ingot(), 'G', ModItems.mechanism_launcher_1);
		addRecipeAuto(new ItemStack(ModItems.gun_uboinik, 1), "IIM", "SPW", 'P', STEEL.plate(), 'I', STEEL.ingot(), 'W', KEY_PLANKS, 'S', Items.STICK, 'M', ModItems.mechanism_revolver_2);
		addRecipeAuto(new ItemStack(ModItems.gun_xvl1456, 1), "PBB", "ACC", "PRY", 'P', STEEL.plate(), 'R', ModItems.redcoil_capacitor, 'A', ModItems.coil_advanced_alloy, 'B', ModItems.battery_generic, 'C', ModItems.coil_advanced_torus, 'Y', ModItems.mechanism_special);
		addRecipeAuto(new ItemStack(ModItems.gun_osipr, 1), "CCT", "WWI", "MCC", 'C', CMB.plate(), 'T', W.ingot(), 'W', new ItemStack(ModItems.wire, 1, MAT_MAGTUNG.id), 'I', ModItems.mechanism_rifle_2, 'M', ModItems.coil_magnetized_tungsten);
		addRecipeAuto(new ItemStack(ModItems.gun_immolator, 1), "WCC", "PMT", "WAA", 'W', new ItemStack(ModItems.wire, 1, MAT_GOLD.id), 'C', CU.plate(), 'P', ALLOY.plate(), 'M', ModItems.mechanism_launcher_1, 'T', ModItems.tank_steel, 'A', STEEL.plate());
		addRecipeAuto(new ItemStack(ModItems.gun_cryolator, 1), "SSS", "IWL", "LMI", 'S', STEEL.plate(), 'I', IRON.plate(), 'L', Items.LEATHER, 'M', ModItems.mechanism_launcher_1, 'W', new ItemStack(ModItems.wire, 1, MAT_ALUMINIUM.id));
		addRecipeAuto(new ItemStack(ModItems.gun_jack, 1), "WW ", "TSD", " TT", 'W', WEIDANIUM.ingot(), 'T', ModItems.toothpicks, 'S', ModItems.gun_uboinik, 'D', ModItems.ducttape);
		addRecipeAuto(new ItemStack(ModItems.gun_euthanasia, 1), "TDT", "AAS", " T ", 'A', AUSTRALIUM.ingot(), 'T', ModItems.toothpicks, 'S', ModItems.gun_mp40, 'D', ModItems.ducttape);
		addRecipeAuto(new ItemStack(ModItems.gun_spark, 1), "TTD", "AAS", "  T", 'A', DAFFERGON.ingot(), 'T', ModItems.toothpicks, 'S', ModItems.gun_rpg, 'D', ModItems.ducttape);
		addRecipeAuto(new ItemStack(ModItems.gun_skystinger, 1), "TTT", "AAS", " D ", 'A', UNOBTAINIUM.ingot(), 'T', ModItems.toothpicks, 'S', ModItems.gun_stinger, 'D', ModItems.ducttape);
		addRecipeAuto(new ItemStack(ModItems.gun_hp, 1), "TDT", "ASA", " T ", 'A', REIIUM.ingot(), 'T', ModItems.toothpicks, 'S', ModItems.gun_xvl1456, 'D', ModItems.ducttape);
		addRecipeAuto(new ItemStack(ModItems.gun_lever_action, 1), "PPI", "SWD", 'P', IRON.plate(), 'I', ModItems.mechanism_rifle_1, 'S', Items.STICK, 'D', KEY_PLANKS, 'W', new ItemStack(ModItems.wire, 1, MAT_ALUMINIUM.id));
		addRecipeAuto(new ItemStack(ModItems.gun_lever_action_dark, 1), "PPI", "SWD", 'P', STEEL.plate(), 'I', ModItems.mechanism_rifle_1, 'S', Items.STICK, 'D', KEY_PLANKS, 'W', new ItemStack(ModItems.wire, 1, MAT_ALUMINIUM.id));
		addRecipeAuto(new ItemStack(ModItems.gun_bolt_action, 1), "PPI", "SWD", 'P', STEEL.plate(), 'I', ModItems.mechanism_rifle_1, 'S', Items.STICK, 'D', KEY_PLANKS, 'W', new ItemStack(ModItems.wire, 1, MAT_COPPER.id));
		addRecipeAuto(new ItemStack(ModItems.gun_bolt_action_green, 1), "PPI", "SWD", 'P', IRON.plate(), 'I', ModItems.mechanism_rifle_1, 'S', Items.STICK, 'D', KEY_PLANKS, 'W', new ItemStack(ModItems.wire, 1, MAT_COPPER.id));
		addRecipeAuto(new ItemStack(ModItems.gun_bolt_action_saturnite, 1), "PPI", "SWD", 'P', BIGMT.plate(), 'I', ModItems.mechanism_rifle_1, 'S', Items.STICK, 'D', KEY_PLANKS, 'W', new ItemStack(ModItems.wire, 1, MAT_TUNGSTEN.id));
		addRecipeAuto(new ItemStack(ModItems.gun_b92, 1), "DDD", "SSC", "  R", 'D', ModItems.plate_dineutronium, 'S', STAR.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BISMOID), 'R', ModItems.gun_revolver_schrabidium);
		addRecipeAuto(new ItemStack(ModItems.gun_uzi, 1), "SMS", " PB", " P ", 'S', STEEL.ingot(), 'M', ModItems.mechanism_rifle_2, 'P', STEEL.plate(), 'B', new ItemStack(ModItems.bolt, 1, MAT_DURA.id));
		addRecipeAuto(new ItemStack(ModItems.gun_uzi_silencer, 1), "P  ", " P ", "  U", 'P', ANY_PLASTIC.ingot(), 'U', ModItems.gun_uzi);
		addRecipeAuto(new ItemStack(ModItems.gun_uzi_saturnite, 1), "SMS", " PB", " P ", 'S', BIGMT.ingot(), 'M', ModItems.mechanism_rifle_2, 'P', BIGMT.plate(), 'B', new ItemStack(ModItems.bolt, 1, MAT_STEEL.id));
		addRecipeAuto(new ItemStack(ModItems.gun_uzi_saturnite_silencer, 1), "P  ", " P ", "  U", 'P', ANY_PLASTIC.ingot(), 'U', ModItems.gun_uzi_saturnite);
		addRecipeAuto(new ItemStack(ModItems.gun_bolter, 1), "SSM", "PIP", " I ", 'S', BIGMT.plate(), 'I', BIGMT.ingot(), 'M', ModItems.mechanism_special, 'P', ANY_PLASTIC.ingot());
		addRecipeAuto(new ItemStack(ModItems.gun_vortex, 1), "AS ", "SIP", " SC", 'S', ModItems.plate_armor_lunar, 'I', ModItems.gun_xvl1456, 'A', ModItems.levitation_unit, 'P', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CONTROLLER_ADVANCED), 'C', ModItems.crystal_trixite);

		addRecipeAuto(new ItemStack(ModItems.jshotgun, 1), "LPP", "SSW", "PPD", 'S', ModItems.gun_uboinik, 'P', STEEL.plate(), 'D', new ItemStack(Items.DYE, 1, EnumDyeColor.GREEN.getDyeDamage()), 'L', ModBlocks.spinny_light, 'W', ModItems.mechanism_rifle_2);
		addRecipeAuto(new ItemStack(ModItems.gun_revolver_pip, 1), " G ", "SSP", " TI", 'G', KEY_ANYPANE, 'S', STEEL.plate(), 'P', ModItems.mechanism_revolver_2, 'T', new ItemStack(ModItems.wire, 1, MAT_TUNGSTEN.id), 'I', ANY_PLASTIC.ingot());
		addRecipeAuto(new ItemStack(ModItems.gun_revolver_nopip, 1), "SSP", " TI", 'S', STEEL.plate(), 'P', ModItems.mechanism_revolver_2, 'T', new ItemStack(ModItems.wire, 1, MAT_TUNGSTEN.id), 'I', ANY_PLASTIC.ingot());
		addRecipeAuto(new ItemStack(ModItems.gun_revolver_silver, 1), "SSP", " TI", 'S', AL.plate(), 'P', ModItems.mechanism_revolver_2, 'T', new ItemStack(ModItems.wire, 1, MAT_TUNGSTEN.id), 'I', KEY_PLANKS);
		addRecipeAuto(new ItemStack(ModItems.gun_revolver_blackjack, 1), "SSP", " TI", 'S', STEEL.plate(), 'P', ModItems.mechanism_revolver_2, 'T', new ItemStack(ModItems.wire, 1, MAT_TUNGSTEN.id), 'I', KEY_PLANKS);
		addRecipeAuto(new ItemStack(ModItems.gun_revolver_red, 1), "R ", " B", 'R', ModItems.key_red, 'B', ModItems.gun_revolver_blackjack);
		addRecipeAuto(new ItemStack(ModItems.gun_calamity, 1), " PI", "BBM", " PI", 'P', IRON.plate(), 'B', ModItems.pipes_steel, 'M', ModItems.mechanism_rifle_1, 'I', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.gun_calamity_dual, 1), "BBM", " PI", "BBM", 'P', IRON.plate(), 'B', ModItems.pipes_steel, 'M', ModItems.mechanism_rifle_1, 'I', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.gun_minigun, 1), "PIB", "PCM", "PIB", 'P', ModItems.pipes_steel, 'B', STEEL.block(), 'I', ANY_PLASTIC.ingot(), 'C', ModItems.mechanism_rifle_2, 'M', ModItems.motor);
		addRecipeAuto(new ItemStack(ModItems.gun_avenger, 1), "PIB", "PCM", "PIB", 'P', ModItems.pipes_steel, 'B', BE.block(), 'I', DESH.ingot(), 'C', ModItems.mechanism_rifle_2, 'M', ModItems.motor);
		addRecipeAuto(new ItemStack(ModItems.gun_lacunae, 1), "TIT", "ILI", "PRP", 'T', ModItems.syringe_taint, 'I', STAR.ingot(), 'L', ModItems.gun_minigun, 'P', ModItems.pellet_rtg, 'R', ModBlocks.machine_rtg_grey);
		addRecipeAuto(new ItemStack(ModItems.gun_ks23, 1), "PPM", "SWL", 'P', STEEL.plate(), 'M', ModItems.mechanism_rifle_1, 'S', Items.STICK, 'W', new ItemStack(ModItems.wire, 1, MAT_TUNGSTEN.id), 'L', KEY_LOG);
		addShapelessAuto(new ItemStack(ModItems.gun_sauer, 1), ModItems.ducttape, ModItems.gun_ks23, Blocks.LEVER, ModItems.gun_ks23);
		addRecipeAuto(new ItemStack(ModItems.gun_flamer, 1), "WPP", "SCT", "WMI", 'W', new ItemStack(ModItems.wire, 1, MAT_GOLD.id), 'P', ModItems.pipes_steel, 'S', ModItems.hull_small_steel, 'C', ModItems.coil_tungsten, 'T', ModItems.tank_steel, 'M', ModItems.mechanism_launcher_1, 'I', STEEL.ingot());

	}

	public static void addConcreteCrafting(){
		addRecipeAuto(new ItemStack(ModBlocks.concrete, 4), "CC", "CC", 'C', ModBlocks.concrete_smooth);

		addShapelessAuto(new ItemStack(ModBlocks.concrete_white, 8), ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeWhite");
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), ModBlocks.concrete_white);

		addShapelessAuto(new ItemStack(ModBlocks.concrete_orange, 8), ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeOrange");
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), ModBlocks.concrete_orange);

		addShapelessAuto(new ItemStack(ModBlocks.concrete_black, 8), ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeBlack");
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), ModBlocks.concrete_black);

		addShapelessAuto(new ItemStack(ModBlocks.concrete_blue, 8), ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeBlue");
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), ModBlocks.concrete_blue);

		addShapelessAuto(new ItemStack(ModBlocks.concrete_brown, 8), ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeBrown");
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), ModBlocks.concrete_brown);

		addShapelessAuto(new ItemStack(ModBlocks.concrete_cyan, 8), ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeCyan");
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), ModBlocks.concrete_cyan);

		addShapelessAuto(new ItemStack(ModBlocks.concrete_gray, 8), ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeGray");
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), ModBlocks.concrete_gray);

		addShapelessAuto(new ItemStack(ModBlocks.concrete_green, 8), ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeGreen");
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), ModBlocks.concrete_green);

		addShapelessAuto(new ItemStack(ModBlocks.concrete_light_blue, 8), ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeLightBlue");
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), ModBlocks.concrete_light_blue);

		addShapelessAuto(new ItemStack(ModBlocks.concrete_lime, 8), ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeLime");
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), ModBlocks.concrete_lime);

		addShapelessAuto(new ItemStack(ModBlocks.concrete_magenta, 8), ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeMagenta");
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), ModBlocks.concrete_magenta);

		addShapelessAuto(new ItemStack(ModBlocks.concrete_pink, 8), ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyePink");
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), ModBlocks.concrete_pink);

		addShapelessAuto(new ItemStack(ModBlocks.concrete_purple, 8), ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyePurple");
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), ModBlocks.concrete_purple);

		addShapelessAuto(new ItemStack(ModBlocks.concrete_silver, 8), ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeLightGray");
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), ModBlocks.concrete_silver);

		addShapelessAuto(new ItemStack(ModBlocks.concrete_red, 8), ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeRed");
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), ModBlocks.concrete_red);

		addShapelessAuto(new ItemStack(ModBlocks.concrete_yellow, 8), ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, ModBlocks.concrete_smooth, "dyeYellow");
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth), ModBlocks.concrete_yellow);

		addRecipeAuto(new ItemStack(ModBlocks.concrete_hazard, 6), "CCC", "1 2", "CCC", 'C', ModBlocks.concrete_smooth, '1', "dyeYellow", '2', "dyeBlack");
		addRecipeAuto(new ItemStack(ModBlocks.concrete_hazard, 6), "CCC", "2 1", "CCC", 'C', ModBlocks.concrete_smooth, '1', "dyeYellow", '2', "dyeBlack");
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth, 6), ModBlocks.concrete_hazard, ModBlocks.concrete_hazard, ModBlocks.concrete_hazard, ModBlocks.concrete_hazard, ModBlocks.concrete_hazard, ModBlocks.concrete_hazard);

		addRecipeAuto(new ItemStack(ModBlocks.concrete_pillar, 8), "CBC", "CBC", "CBC", 'C', ModBlocks.concrete_smooth, 'B', Blocks.IRON_BARS);
		addRecipeAuto(new ItemStack(ModBlocks.brick_concrete, 4), " C ", "CBC", " C ", 'C', ModBlocks.concrete_smooth, 'B', Items.CLAY_BALL);
		addRecipeAuto(new ItemStack(ModBlocks.brick_concrete, 4), " C ", "CBC", " C ", 'C', ModBlocks.concrete, 'B', Items.CLAY_BALL);
		addRecipeAuto(new ItemStack(ModBlocks.brick_concrete_mossy, 8), "CCC", "CVC", "CCC", 'C', ModBlocks.brick_concrete, 'V', Blocks.VINE);
		addShapelessAuto(new ItemStack(ModBlocks.brick_concrete_cracked, 6), ModBlocks.brick_concrete, ModBlocks.brick_concrete, ModBlocks.brick_concrete, ModBlocks.brick_concrete);
		addShapelessAuto(new ItemStack(ModBlocks.brick_concrete_broken, 6), ModBlocks.brick_concrete_cracked, ModBlocks.brick_concrete_cracked, ModBlocks.brick_concrete_cracked, ModBlocks.brick_concrete_cracked);
		addShapelessAuto(new ItemStack(ModBlocks.brick_concrete, 4), ModBlocks.brick_concrete_cracked, ModBlocks.brick_concrete_cracked, ModBlocks.brick_concrete_cracked, ModBlocks.brick_concrete_cracked, ModBlocks.brick_concrete_cracked, ModBlocks.brick_concrete_cracked);
		addShapelessAuto(new ItemStack(ModBlocks.brick_concrete_cracked, 4), ModBlocks.brick_concrete_broken, ModBlocks.brick_concrete_broken, ModBlocks.brick_concrete_broken, ModBlocks.brick_concrete_broken, ModBlocks.brick_concrete_broken, ModBlocks.brick_concrete_broken);
	}

	public static void addAmmoCrafting() {
		addShapelessAuto(new ItemStack(ModItems.gun_revolver_iron_ammo, 24), new ItemStack(ModItems.clip_revolver_iron));
		addShapelessAuto(new ItemStack(ModItems.gun_revolver_ammo, 12), new ItemStack(ModItems.clip_revolver));
		addShapelessAuto(new ItemStack(ModItems.gun_revolver_gold_ammo, 12), new ItemStack(ModItems.clip_revolver_gold));
		addShapelessAuto(new ItemStack(ModItems.gun_revolver_lead_ammo, 12), new ItemStack(ModItems.clip_revolver_lead));
		addShapelessAuto(new ItemStack(ModItems.gun_revolver_schrabidium_ammo, 12), new ItemStack(ModItems.clip_revolver_schrabidium));
		addShapelessAuto(new ItemStack(ModItems.gun_revolver_cursed_ammo, 17), new ItemStack(ModItems.clip_revolver_cursed));
		addShapelessAuto(new ItemStack(ModItems.gun_revolver_nightmare_ammo, 6), new ItemStack(ModItems.clip_revolver_nightmare));
		addShapelessAuto(new ItemStack(ModItems.gun_revolver_nightmare2_ammo, 6), new ItemStack(ModItems.clip_revolver_nightmare2));
		addShapelessAuto(new ItemStack(ModItems.ammo_44_pip, 6), new ItemStack(ModItems.clip_revolver_pip));
		addShapelessAuto(new ItemStack(ModItems.ammo_44, 12), new ItemStack(ModItems.clip_revolver_nopip));
		addShapelessAuto(new ItemStack(ModItems.ammo_rocket, 6), new ItemStack(ModItems.clip_rpg));
		addShapelessAuto(new ItemStack(ModItems.gun_stinger_ammo, 6), new ItemStack(ModItems.clip_stinger));
		addShapelessAuto(new ItemStack(ModItems.ammo_nuke, 6), new ItemStack(ModItems.clip_fatman));
		addShapelessAuto(new ItemStack(ModItems.ammo_mirv, 3), new ItemStack(ModItems.clip_mirv));
		addShapelessAuto(new ItemStack(ModItems.gun_bf_ammo, 2), new ItemStack(ModItems.clip_bf));
		addShapelessAuto(new ItemStack(ModItems.ammo_9mm, 32), new ItemStack(ModItems.clip_mp40));
		addShapelessAuto(new ItemStack(ModItems.ammo_22lr, 32), new ItemStack(ModItems.clip_uzi));
		addShapelessAuto(new ItemStack(ModItems.ammo_12gauge, 24), new ItemStack(ModItems.clip_uboinik));
		addShapelessAuto(new ItemStack(ModItems.ammo_20gauge, 24), new ItemStack(ModItems.clip_lever_action));
		addShapelessAuto(new ItemStack(ModItems.ammo_20gauge_slug, 24), new ItemStack(ModItems.clip_bolt_action));
		addShapelessAuto(new ItemStack(ModItems.gun_osipr_ammo, 30), new ItemStack(ModItems.clip_osipr));
		addShapelessAuto(new ItemStack(ModItems.gun_immolator_ammo, 64), new ItemStack(ModItems.clip_immolator));
		addShapelessAuto(new ItemStack(ModItems.gun_cryolator_ammo, 64), new ItemStack(ModItems.clip_cryolator));
		addShapelessAuto(new ItemStack(ModItems.ammo_566_gold, 40), new ItemStack(ModItems.clip_mp));
		addShapelessAuto(new ItemStack(ModItems.gun_xvl1456_ammo, 64), new ItemStack(ModItems.clip_xvl1456));
		addShapelessAuto(new ItemStack(ModItems.gun_emp_ammo, 6), new ItemStack(ModItems.clip_emp));
		addShapelessAuto(new ItemStack(ModItems.gun_jack_ammo, 12), new ItemStack(ModItems.clip_jack));
		addShapelessAuto(new ItemStack(ModItems.gun_spark_ammo, 4), new ItemStack(ModItems.clip_spark));
		addShapelessAuto(new ItemStack(ModItems.gun_hp_ammo, 12), new ItemStack(ModItems.clip_hp));
		addShapelessAuto(new ItemStack(ModItems.gun_euthanasia_ammo, 16), new ItemStack(ModItems.clip_euthanasia));
		addShapelessAuto(new ItemStack(ModItems.gun_defabricator_ammo, 16), new ItemStack(ModItems.clip_defabricator));
		addRecipeAuto(new ItemStack(ModItems.gun_bf_ammo, 1), " S ", "EBE", " S ", 'S', ModItems.hull_small_steel, 'E', ModItems.powder_power, 'B', ModItems.egg_balefire_shard);
		addRecipeAuto(new ItemStack(ModItems.gun_xvl1456_ammo, 64), "SSS", "SRS", "SSS", 'S', STEEL.plate(), 'R', ModItems.rod_quad_uranium_fuel_depleted);
		addRecipeAuto(new ItemStack(ModItems.gun_xvl1456_ammo, 32), " S ", "SRS", " S ", 'S', STEEL.plate(), 'R', ModItems.rod_dual_uranium_fuel_depleted);
		addRecipeAuto(new ItemStack(ModItems.gun_xvl1456_ammo, 16), " S ", " R ", " S ", 'S', STEEL.plate(), 'R', ModItems.rod_uranium_fuel_depleted);
		addRecipeAuto(new ItemStack(ModItems.gun_xvl1456_ammo, 16), "SRS", 'S', STEEL.plate(), 'R', ModItems.rod_uranium_fuel_depleted);
		addRecipeAuto(new ItemStack(ModItems.gun_xvl1456_ammo, 16), " S ", " R ", " S ", 'S', STEEL.plate(), 'R', U238.ingot());
		addRecipeAuto(new ItemStack(ModItems.gun_xvl1456_ammo, 16), "SRS", 'S', STEEL.plate(), 'R', U238.ingot());
		addRecipeAuto(new ItemStack(ModItems.gun_immolator_ammo, 16), "SPS", "PCP", "SPS", 'S', STEEL.plate(), 'C', COAL.dust(), 'P',P_RED.dust());
		addRecipeAuto(new ItemStack(ModItems.gun_immolator_ammo, 16), " F ", "SFS", " F ", 'S', STEEL.plate(), 'F', new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.DIESEL)));
		addRecipeAuto(new ItemStack(ModItems.gun_immolator_ammo, 16), " F ", "SFS", " F ", 'S', STEEL.plate(), 'F', new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.KEROSENE)));
		addRecipeAuto(new ItemStack(ModItems.gun_immolator_ammo, 24), " F ", "SFS", " F ", 'S', STEEL.plate(), 'F', ModItems.canister_napalm);
		addRecipeAuto(new ItemStack(ModItems.gun_immolator_ammo, 32), " F ", "SFS", " F ", 'S', STEEL.plate(), 'F', new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.NITAN)));
		addRecipeAuto(new ItemStack(ModItems.gun_cryolator_ammo, 16), "SPS", "PCP", "SPS", 'S', STEEL.plate(), 'C', KNO.dust(), 'P', Items.SNOWBALL);
		addRecipeAuto(new ItemStack(ModItems.gun_cryolator_ammo, 16), " F ", "SFS", " F ", 'S', STEEL.plate(), 'F', ModItems.powder_ice);
		addRecipeAuto(new ItemStack(ModItems.gun_mp, 1), "EEE", "SSM", "III", 'E', EUPH.ingot(), 'S', STEEL.plate(), 'I', STEEL.ingot(), 'M', ModItems.mechanism_rifle_2);
		addRecipeAuto(new ItemStack(ModItems.gun_emp, 1), "CPG", "CMF", "CPI", 'C', ModItems.coil_copper, 'P', PB.plate(), 'G', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CONTROLLER), 'M', ModItems.magnetron, 'I', W.ingot(), 'F', ModItems.mechanism_special);
		addRecipeAuto(new ItemStack(ModItems.gun_emp_ammo, 8), "IGI", "IPI", "IPI", 'G', GOLD.plate(), 'I', IRON.plate(), 'P', ModItems.powder_power);
		addShapelessAuto(new ItemStack(ModItems.gun_jack_ammo, 1), ModItems.ammo_12gauge, ModItems.ammo_12gauge, ModItems.ammo_12gauge, ModItems.ammo_12gauge);
		addRecipeAuto(new ItemStack(ModItems.gun_euthanasia_ammo, 12), "P", "S", "N", 'P', ModItems.powder_poison, 'N', KNO.dust(), 'S', ModItems.syringe_metal_empty);
		addRecipeAuto(new ItemStack(ModItems.gun_spark_ammo, 4), "PCP", "DDD", "PCP", 'P', PB.plate(), 'C', ModItems.coil_gold, 'D', ModItems.powder_power);
		addRecipeAuto(new ItemStack(ModItems.gun_hp_ammo, 8), " R ", "BSK", " Y ", 'S', STEEL.plate(), 'K', new ItemStack(Items.DYE, 1, 0), 'R', new ItemStack(Items.DYE, 1, 1), 'B', new ItemStack(Items.DYE, 1, 4), 'Y', new ItemStack(Items.DYE, 1, 11));
		addRecipeAuto(new ItemStack(ModItems.gun_defabricator_ammo, 16), "PCP", "DDD", "PCP", 'P', STEEL.plate(), 'C', ModItems.coil_copper, 'D', LI.dust());
		addRecipeAuto(new ItemStack(ModItems.gun_b92_ammo, 1), "PSP", "ESE", "PSP", 'P', STEEL.plate(), 'S', STAR.ingot(), 'E', ModItems.powder_spark_mix);
		addShapelessAuto(new ItemStack(ModItems.weaponized_starblaster_cell, 1), new IngredientNBT2(ItemFluidTank.getFullTank(ModForgeFluids.ACID)), new IngredientNBT2(GunB92Cell.getFullCell()), new ItemStack(ModItems.wire, 1, MAT_COPPER.id));

		addRecipeAuto(new ItemStack(ModItems.pellet_flechette, 1), " L ", " L ", "LLL", 'L', PB.nugget());
		addRecipeAuto(new ItemStack(ModItems.assembly_iron, 24), " I", "GC", " P", 'I', IRON.ingot(), 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357);
		addRecipeAuto(new ItemStack(ModItems.assembly_iron, 24), " I", "GC", " P", 'I', IRON.ingot(), 'G', ModItems.ballistite, 'C', ModItems.casing_357, 'P', ModItems.primer_357);
		addRecipeAuto(new ItemStack(ModItems.assembly_steel, 24), " I", "GC", " P", 'I', PB.ingot(), 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357);
		addRecipeAuto(new ItemStack(ModItems.assembly_steel, 24), " I", "GC", " P", 'I', PB.ingot(), 'G', ModItems.ballistite, 'C', ModItems.casing_357, 'P', ModItems.primer_357);
		addRecipeAuto(new ItemStack(ModItems.assembly_lead, 24), " I", "GC", " P", 'I', U235.ingot(), 'G', ModItems.cordite, 'C', "paneGlassColorless", 'P', ModItems.primer_357);
		addRecipeAuto(new ItemStack(ModItems.assembly_lead, 24), " I", "GC", " P", 'I', PU239.ingot(), 'G', ModItems.cordite, 'C', "paneGlassColorless", 'P', ModItems.primer_357);
		addRecipeAuto(new ItemStack(ModItems.assembly_lead, 24), " I", "GC", " P", 'I', ModItems.trinitite, 'G', ModItems.cordite, 'C', "paneGlassColorless", 'P', ModItems.primer_357);
		addRecipeAuto(new ItemStack(ModItems.assembly_lead, 24), " I", "GC", " P", 'I', ModItems.nuclear_waste_tiny, 'G', ModItems.cordite, 'C', "paneGlassColorless", 'P', ModItems.primer_357);
		addRecipeAuto(new ItemStack(ModItems.assembly_gold, 24), " I", "GC", " P", 'I', GOLD.ingot(), 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357);
		addRecipeAuto(new ItemStack(ModItems.assembly_schrabidium, 6), " I ", "GCN", " P ", 'I', SA326.ingot(), 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357, 'N', "netherStar");
		addRecipeAuto(new ItemStack(ModItems.assembly_nightmare, 24), " I", "GC", " P", 'I', W.ingot(), 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357);
		addRecipeAuto(new ItemStack(ModItems.assembly_desh, 24), " I", "GC", " P", 'I', DESH.ingot(), 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357);
		addRecipeAuto(new ItemStack(ModItems.assembly_smg, 32), " I", "GC", " P", 'I', PB.ingot(), 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_9, 'P', ModItems.primer_9);
		addRecipeAuto(new ItemStack(ModItems.assembly_556, 32), " I", "GC", " P", 'I', STEEL.ingot(), 'G', ModItems.cordite, 'C', ModItems.casing_9, 'P', ModItems.primer_9);
		addRecipeAuto(new ItemStack(ModItems.ammo_556_k, 32), "G", "C", "P", 'G', ANY_GUNPOWDER.dust(), 'C', ModItems.casing_9, 'P', ModItems.primer_9);
		addRecipeAuto(new ItemStack(ModItems.assembly_uzi, 32), " I", "GC", " P", 'I', IRON.ingot(), 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_9, 'P', ModItems.primer_9);
		addRecipeAuto(new ItemStack(ModItems.assembly_lacunae, 32), " I", "GC", " P", 'I', CU.ingot(), 'G', ModItems.cordite, 'C', ModItems.casing_9, 'P', ModItems.primer_9);
		addRecipeAuto(new ItemStack(ModItems.assembly_nopip, 24), " I", "GC", " P", 'I', PB.ingot(), 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_44, 'P', ModItems.primer_44);
		addRecipeAuto(new ItemStack(ModItems.ammo_12gauge, 12), " I ", "GCL", " P ", 'I', ModItems.pellet_buckshot, 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', ANY_RUBBER.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_20gauge, 12), " I ", "GCL", " P ", 'I', ModItems.pellet_buckshot, 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', CU.plate());
		addRecipeAuto(new ItemStack(ModItems.ammo_20gauge_slug, 12), " I ", "GCL", " P ", 'I', PB.ingot(), 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', CU.plate());
		addRecipeAuto(new ItemStack(ModItems.ammo_20gauge_explosive, 12), " I ", "GCL", " P ", 'I', ModItems.pellet_cluster, 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', CU.plate());
		addRecipeAuto(new ItemStack(ModItems.ammo_20gauge_flechette, 12), " I ", "GCL", " P ", 'I', ModItems.pellet_flechette, 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', CU.plate());
		addRecipeAuto(new ItemStack(ModItems.gun_revolver_nightmare2_ammo, 12), "I", "C", "P", 'I', ModItems.powder_power, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot);
		addRecipeAuto(new ItemStack(ModItems.assembly_calamity, 12), " I ", "GCG", " P ", 'I', PB.ingot(), 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50);
		addRecipeAuto(new ItemStack(ModItems.assembly_actionexpress, 12), " I", "GC", " P", 'I', PB.ingot(), 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50);
		addRecipeAuto(new ItemStack(ModItems.ammo_dart, 16), "IPI", "ICI", "IPI", 'I', ModItems.plate_polymer, 'P', IRON.plate(), 'C', new IngredientNBT2(ItemFluidTank.getFullBarrel(ModForgeFluids.MUD_FLUID)));

		addRecipeAuto(new ItemStack(ModItems.ammo_12gauge_incendiary, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_12gauge, 'A',P_RED.dust());
		addRecipeAuto(new ItemStack(ModItems.ammo_12gauge_shrapnel, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_12gauge, 'A', ModBlocks.gravel_obsidian);
		addRecipeAuto(new ItemStack(ModItems.ammo_12gauge_du, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_12gauge, 'A', U238.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_12gauge_sleek, 64), "BBB", "BAB", "BBB", 'B', ModItems.ammo_12gauge, 'A', ModItems.coin_maskman);
		addRecipeAuto(new ItemStack(ModItems.ammo_20gauge_incendiary, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_20gauge, 'A',P_RED.dust());
		addRecipeAuto(new ItemStack(ModItems.ammo_20gauge_shrapnel, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_20gauge, 'A', ModBlocks.gravel_obsidian);
		addRecipeAuto(new ItemStack(ModItems.ammo_20gauge_caustic, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_20gauge, 'A', ModItems.powder_poison);
		addRecipeAuto(new ItemStack(ModItems.ammo_20gauge_shock, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_20gauge, 'A', DIAMOND.dust());
		addRecipeAuto(new ItemStack(ModItems.ammo_20gauge_wither, 4), "BCB", "CAC", "BCB", 'B', ModItems.ammo_20gauge, 'A', Blocks.SOUL_SAND, 'C', COAL.dust());
		addRecipeAuto(new ItemStack(ModItems.ammo_20gauge_sleek, 64), "BBB", "BAB", "BBB", 'B', ModItems.ammo_20gauge, 'A', ModItems.coin_maskman);
		addRecipeAuto(new ItemStack(ModItems.ammo_4gauge_sleek, 64), "BBB", "BAB", "BBB", 'B', ModItems.ammo_4gauge, 'A', ModItems.coin_maskman);
		addRecipeAuto(new ItemStack(ModItems.ammo_4gauge_flechette_phosphorus, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_4gauge_flechette, 'A', P_WHITE.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_4gauge_balefire, 4), " B ", "BAB", " B ", 'B', ModItems.ammo_4gauge_explosive, 'A', ModItems.egg_balefire_shard);
		addRecipeAuto(new ItemStack(ModItems.ammo_4gauge_kampf, 2), "G", "R", 'G', ModItems.ammo_rocket, 'R', ModItems.ammo_4gauge_explosive);
		addRecipeAuto(new ItemStack(ModItems.ammo_44_ap, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_44, 'A', DURA.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_44_du, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_44, 'A', U238.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_44_star, 4), " B ", "BAB", " B ", 'B', ModItems.ammo_44_du, 'A', STAR.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_5mm_explosive, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_5mm, 'A', ANY_PLASTICEXPLOSIVE.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_5mm_du, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_5mm, 'A', U238.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_5mm_star, 4), " B ", "BAB", " B ", 'B', ModItems.ammo_5mm_du, 'A', STAR.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_9mm_ap, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_9mm, 'A', DURA.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_9mm_du, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_9mm, 'A', U238.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_22lr_ap, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_22lr, 'A', DURA.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_50bmg_incendiary, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_50bmg, 'A',P_RED.dust());
		addRecipeAuto(new ItemStack(ModItems.ammo_50bmg_explosive, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_50bmg, 'A', ANY_PLASTICEXPLOSIVE.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_50bmg_ap, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_50bmg, 'A', DURA.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_50bmg_du, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_50bmg, 'A', U238.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_50bmg_star, 4), " B ", "BAB", " B ", 'B', ModItems.ammo_50bmg_du, 'A', STAR.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_50bmg_sleek, 64), "BBB", "BAB", "BBB", 'B', ModItems.ammo_50bmg, 'A', ModItems.coin_maskman);
		addRecipeAuto(new ItemStack(ModItems.ammo_50ae_ap, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_50ae, 'A', DURA.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_50ae_du, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_50ae, 'A', U238.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_50ae_star, 4), " B ", "BAB", " B ", 'B', ModItems.ammo_50ae_du, 'A', STAR.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_556_phosphorus, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_556, 'A', P_WHITE.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_556_ap, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_556, 'A', DURA.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_556_du, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_556, 'A', U238.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_556_star, 4), " B ", "BAB", " B ", 'B', ModItems.ammo_556_du, 'A', STAR.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_556_sleek, 64), "BBB", "BAB", "BBB", 'B', ModItems.ammo_556, 'A', ModItems.coin_maskman);
		addRecipeAuto(new ItemStack(ModItems.ammo_556_tracer, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_556, 'A', Items.REDSTONE);
		addRecipeAuto(new ItemStack(ModItems.ammo_556_flechette, 4), " B ", "BAB", " B ", 'B', ModItems.ammo_556, 'A', ModItems.pellet_flechette);
		addRecipeAuto(new ItemStack(ModItems.ammo_556_flechette_incendiary, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_556_flechette, 'A',P_RED.dust());
		addRecipeAuto(new ItemStack(ModItems.ammo_556_flechette_phosphorus, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_556_flechette, 'A', P_WHITE.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_556_flechette_du, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_556_flechette, 'A', U238.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_556_flechette_sleek, 64), "BBB", "BAB", "BBB", 'B', ModItems.ammo_556_flechette, 'A', ModItems.coin_maskman);

		addRecipeAuto(new ItemStack(ModItems.folly_bullet, 1), " S ", "STS", "SMS", 'S', STAR.ingot(), 'T', ModItems.powder_magic, 'M', ModBlocks.block_meteor);
		addRecipeAuto(new ItemStack(ModItems.folly_bullet_nuclear, 1), " N ", "UTU", "UTU", 'N', ModItems.ammo_nuke, 'U', IRON.ingot(), 'T', W.block());
		addRecipeAuto(new ItemStack(ModItems.folly_bullet_du, 1), " U ", "UDU", "UTU", 'U', U238.block(), 'D', DESH.block(), 'T', W.block());
		addRecipeAuto(new ItemStack(ModItems.folly_shell, 1), "IPI", "IPI", "IMI", 'I', IRON.ingot(), 'P', IRON.plate(), 'M', ModItems.primer_50);
		addRecipeAuto(new ItemStack(ModItems.ammo_folly, 1), " B ", "MEM", " S ", 'B', ModItems.folly_bullet, 'M', ModItems.powder_magic, 'E', ModItems.powder_power, 'S', ModItems.folly_shell);
		addRecipeAuto(new ItemStack(ModItems.ammo_folly_nuclear, 1), " B ", "EEE", " S ", 'B', ModItems.folly_bullet_nuclear, 'E', ModBlocks.det_charge, 'S', ModItems.folly_shell);
		addRecipeAuto(new ItemStack(ModItems.ammo_folly_du, 1), " B ", "EEE", " S ", 'B', ModItems.folly_bullet_du, 'E', ModBlocks.det_charge, 'S', ModItems.folly_shell);

		addRecipeAuto(new ItemStack(ModItems.ammo_rocket, 1), " T ", "GCG", " P ", 'T', Blocks.TNT, 'G', ModItems.rocket_fuel, 'C', ModItems.casing_50, 'P', ModItems.primer_50);
		addRecipeAuto(new ItemStack(ModItems.ammo_rocket, 2), " T ", "GCG", " P ", 'T', ANY_PLASTICEXPLOSIVE.ingot(), 'G', ModItems.rocket_fuel, 'C', ModItems.casing_50, 'P', ModItems.primer_50);
		addRecipeAuto(new ItemStack(ModItems.ammo_rocket_he, 1), "G", "R", 'G', ANY_PLASTICEXPLOSIVE.ingot(), 'R', ModItems.ammo_rocket);
		addRecipeAuto(new ItemStack(ModItems.ammo_rocket_incendiary, 1), "G", "R", 'G',P_RED.dust(), 'R', ModItems.ammo_rocket);
		addRecipeAuto(new ItemStack(ModItems.ammo_rocket_emp, 1), "G", "R", 'G', DIAMOND.dust(), 'R', ModItems.ammo_rocket);
		addRecipeAuto(new ItemStack(ModItems.ammo_rocket_shrapnel, 1), "G", "R", 'G', ModItems.pellet_buckshot, 'R', ModItems.ammo_rocket);
		addRecipeAuto(new ItemStack(ModItems.ammo_rocket_glare, 1), "GGG", "GRG", "GGG", 'G', Items.REDSTONE, 'R', ModItems.ammo_rocket);
		addRecipeAuto(new ItemStack(ModItems.ammo_rocket_toxic, 1), "G", "R", 'G', ModItems.pellet_gas, 'R', ModItems.ammo_rocket);
		addRecipeAuto(new ItemStack(ModItems.ammo_rocket_nuclear, 1), " P ", "NRN", " P ", 'P', PU239.nugget(), 'N', OreDictManager.getReflector(), 'R', ModItems.ammo_rocket);
		addRecipeAuto(new ItemStack(ModItems.ammo_rocket_rpc, 2), "BP ", "CBH", " DR", 'B', ModItems.blades_steel, 'P', STEEL.plate(), 'C', new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.DIESEL)), 'H', ModItems.hull_small_steel, 'D', ModItems.piston_selenium, 'R', ModItems.ammo_rocket);
		addRecipeAuto(new ItemStack(ModItems.ammo_rocket_rpc, 2), "BP ", "CBH", " DR", 'B', ModItems.blades_steel, 'P', STEEL.plate(), 'C', new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.PETROIL)), 'H', ModItems.hull_small_steel, 'D', ModItems.piston_selenium, 'R', ModItems.ammo_rocket);
		addRecipeAuto(new ItemStack(ModItems.ammo_rocket_rpc, 2), "BP ", "CBH", " DR", 'B', ModItems.blades_steel, 'P', STEEL.plate(), 'C', new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.BIOFUEL)), 'H', ModItems.hull_small_steel, 'D', ModItems.piston_selenium, 'R', ModItems.ammo_rocket);
		addRecipeAuto(new ItemStack(ModItems.ammo_rocket_sleek, 64), "GGG", "GRG", "GGG", 'G', ModItems.ammo_rocket, 'R', ModItems.coin_maskman);

		addRecipeAuto(new ItemStack(ModItems.ammo_grenade, 2), " T ", "GCI", 'T', ANY_HIGHEXPLOSIVE.ingot(), 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_50, 'I', IRON.plate());
		addRecipeAuto(new ItemStack(ModItems.ammo_grenade_tracer, 2), " T ", "GCI", " P ", 'T', LAPIS.dust(), 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'I', IRON.plate());
		addRecipeAuto(new ItemStack(ModItems.ammo_grenade_he, 2), "GIG", 'G', ModItems.ammo_grenade, 'I', ANY_PLASTICEXPLOSIVE.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_grenade_incendiary, 2), "GIG", 'G', ModItems.ammo_grenade, 'I',P_RED.dust());
		addRecipeAuto(new ItemStack(ModItems.ammo_grenade_toxic, 2), "GIG", 'G', ModItems.ammo_grenade, 'I', ModItems.powder_poison);
		addRecipeAuto(new ItemStack(ModItems.ammo_grenade_concussion, 2), "GIG", 'G', ModItems.ammo_grenade, 'I', Items.GLOWSTONE_DUST);
		addRecipeAuto(new ItemStack(ModItems.ammo_grenade_nuclear, 2), " P ", "GIG", " P ", 'G', ModItems.ammo_grenade, 'I', ModItems.neutron_reflector, 'P', PU239.nugget());
		addRecipeAuto(new ItemStack(ModItems.ammo_grenade_finned, 1), "G", "R", 'G', Items.FEATHER, 'R', ModItems.ammo_grenade);
		addRecipeAuto(new ItemStack(ModItems.ammo_grenade_kampf, 2), "G", "R", 'G', ModItems.ammo_rocket, 'R', ModItems.ammo_grenade);
		addRecipeAuto(new ItemStack(ModItems.ammo_grenade_sleek, 64), "GGG", "GRG", "GGG", 'G', ModItems.ammo_grenade, 'R', ModItems.coin_maskman);

		addRecipeAuto(new ItemStack(ModItems.turret_light_ammo, 1), " L ", "IGI", "ICI", 'L', PB.plate(), 'I', IRON.plate(), 'C', CU.plate(), 'G', Items.GUNPOWDER);
		addRecipeAuto(new ItemStack(ModItems.turret_heavy_ammo, 1), "LGC", "LGC", "LGC", 'L', PB.plate(), 'C', CU.plate(), 'G', Items.GUNPOWDER);
		addRecipeAuto(new ItemStack(ModItems.turret_rocket_ammo, 1), "TS ", "SGS", " SR", 'T', Blocks.TNT, 'S', STEEL.plate(), 'G', Items.GUNPOWDER, 'R', REDSTONE.dust());
		addRecipeAuto(new ItemStack(ModItems.turret_flamer_ammo, 1), "FSF", "FPF", "FPF", 'F', ModItems.gun_immolator_ammo, 'S', ModItems.pipes_steel, 'P', CU.plate());
		addRecipeAuto(new ItemStack(ModItems.turret_tau_ammo, 1), "AAA", "AUA", "AAA", 'A', ModItems.gun_xvl1456_ammo, 'U', U238.block());
		addRecipeAuto(new ItemStack(ModItems.turret_spitfire_ammo, 1), "CP ", "PTP", " PR", 'P', STEEL.plate(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'T', Blocks.TNT, 'R', REDSTONE.dust());
		addRecipeAuto(new ItemStack(ModItems.turret_cwis_ammo, 1), "LLL", "GGG", "IGI", 'L', PB.plate(), 'I', IRON.plate(), 'G', Items.GUNPOWDER);
		addRecipeAuto(new ItemStack(ModItems.turret_cheapo_ammo, 1), "ILI", "IGI", "ICI", 'L', PB.plate(), 'I', STEEL.plate(), 'C', CU.plate(), 'G', Items.GUNPOWDER);

		addRecipeAuto(new ItemStack(ModItems.ammo_dgk, 1), "LLL", "GGG", "CCC", 'L', PB.plate(), 'G', ANY_SMOKELESS.dust(), 'C', CU.ingot());
		addRecipeAuto(new ItemStack(ModItems.pellet_claws, 1), " X ", "X X", " XX", 'X', STEEL.plate());
		addRecipeAuto(new ItemStack(ModItems.ammo_4gauge, 12), " I ", "GCL", " P ", 'I', ModItems.pellet_buckshot, 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ANY_RUBBER.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_4gauge_slug, 12), " I ", "GCL", " P ", 'I', STEEL.ingot(), 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ANY_RUBBER.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_4gauge_flechette, 12), " I ", "GCL", " P ", 'I', ModItems.pellet_flechette, 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ANY_RUBBER.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_4gauge_explosive, 4), " I ", "GCL", " P ", 'I', Blocks.TNT, 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ANY_RUBBER.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_4gauge_explosive, 6), " I ", "GCL", " P ", 'I', ANY_PLASTICEXPLOSIVE.ingot(), 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ANY_RUBBER.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_4gauge_semtex, 4), " I ", "GCL", " P ", 'I', ModBlocks.det_miner, 'G', ANY_SMOKELESS.dust(), 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ANY_RUBBER.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_fuel, 1), " P ", "BDB", " P ", 'P', STEEL.plate(), 'B', new ItemStack(ModItems.bolt, 1, MAT_STEEL.id), 'D', new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.DIESEL)));
		addRecipeAuto(new ItemStack(ModItems.ammo_fuel_napalm, 1), " P ", "BDB", " P ", 'P', STEEL.plate(), 'B', new ItemStack(ModItems.bolt, 1, MAT_STEEL.id), 'D', ModItems.canister_napalm);
		addRecipeAuto(new ItemStack(ModItems.ammo_fuel_phosphorus, 1), "CPC", "CDC", "CPC", 'C', COAL.dust(), 'P', P_WHITE.ingot(), 'D', ModItems.ammo_fuel);
		addRecipeAuto(new ItemStack(ModItems.ammo_fuel_gas, 1), "PDP", "BDB", "PDP", 'P', STEEL.plate(), 'B', new ItemStack(ModItems.bolt, 1, MAT_STEEL.id), 'D', ModItems.pellet_gas);
		addRecipeAuto(new ItemStack(ModItems.ammo_fuel_vaporizer, 1), "PSP", "SNS", "PSP", 'P', P_WHITE.ingot(), 'S', ModItems.crystal_sulfur, 'N', ModItems.ammo_fuel_napalm);
		addRecipeAuto(new ItemStack(ModItems.ammo_shell, 4), " T ", "GHG", "CCC", 'T', Blocks.TNT, 'G', Items.GUNPOWDER, 'H', ModItems.hull_small_steel, 'C', CU.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_shell, 5), " T ", "GHG", "CCC", 'T', Blocks.TNT, 'G', ModItems.ballistite, 'H', ModItems.hull_small_steel, 'C', CU.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_shell, 6), " T ", "GHG", "CCC", 'T', Blocks.TNT, 'G', ModItems.cordite, 'H', ModItems.hull_small_steel, 'C', CU.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_shell_explosive, 4), " T ", "GHG", "CCC", 'T', ANY_PLASTICEXPLOSIVE.ingot(), 'G', Items.GUNPOWDER, 'H', ModItems.hull_small_steel, 'C', CU.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_shell_explosive, 5), " T ", "GHG", "CCC", 'T', ANY_PLASTICEXPLOSIVE.ingot(), 'G', ModItems.ballistite, 'H', ModItems.hull_small_steel, 'C', CU.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_shell_explosive, 6), " T ", "GHG", "CCC", 'T', ANY_PLASTICEXPLOSIVE.ingot(), 'G', ModItems.cordite, 'H', ModItems.hull_small_steel, 'C', CU.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_shell_apfsds_t, 4), " I ", "GIG", "CCC", 'I', W.ingot(), 'G', Items.GUNPOWDER, 'C', CU.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_shell_apfsds_t, 5), " I ", "GIG", "CCC", 'I', W.ingot(), 'G', ModItems.ballistite, 'C', CU.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_shell_apfsds_t, 6), " I ", "GIG", "CCC", 'I', W.ingot(), 'G', ModItems.cordite, 'C', CU.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_shell_apfsds_du, 4), " I ", "GIG", "CCC", 'I', U238.ingot(), 'G', Items.GUNPOWDER, 'C', CU.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_shell_apfsds_du, 5), " I ", "GIG", "CCC", 'I', U238.ingot(), 'G', ModItems.ballistite, 'C', CU.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_shell_apfsds_du, 6), " I ", "GIG", "CCC", 'I', U238.ingot(), 'G', ModItems.cordite, 'C', CU.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_shell_w9, 1), " P ", "NSN", " P ", 'P', PU239.nugget(), 'N', OreDictManager.getReflector(), 'S', ModItems.ammo_shell_explosive);
		addRecipeAuto(new ItemStack(ModItems.ammo_4gauge_canister, 4), " B ", "BAB", " B ", 'B', ModItems.ammo_4gauge_kampf, 'A', ModItems.pellet_canister);
		addRecipeAuto(new ItemStack(ModItems.ammo_44_chlorophyte, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_44, 'A', ModItems.pellet_chlorophyte);
		addRecipeAuto(new ItemStack(ModItems.ammo_5mm_chlorophyte, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_5mm, 'A', ModItems.pellet_chlorophyte);
		addRecipeAuto(new ItemStack(ModItems.ammo_9mm_chlorophyte, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_9mm, 'A', ModItems.pellet_chlorophyte);
		addRecipeAuto(new ItemStack(ModItems.ammo_22lr_chlorophyte, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_22lr, 'A', ModItems.pellet_chlorophyte);
		addRecipeAuto(new ItemStack(ModItems.ammo_50bmg_chlorophyte, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_50bmg, 'A', ModItems.pellet_chlorophyte);
		addRecipeAuto(new ItemStack(ModItems.ammo_50ae_chlorophyte, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_50ae, 'A', ModItems.pellet_chlorophyte);
		addRecipeAuto(new ItemStack(ModItems.ammo_556_chlorophyte, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_556, 'A', ModItems.pellet_chlorophyte);
		addRecipeAuto(new ItemStack(ModItems.ammo_556_flechette_chlorophyte, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_556_flechette, 'A', ModItems.pellet_chlorophyte);
		addRecipeAuto(new ItemStack(ModItems.ammo_rocket_canister, 1), "G", "R", 'G', ModItems.pellet_canister, 'R', ModItems.ammo_rocket);
		addRecipeAuto(new ItemStack(ModItems.ammo_4gauge_claw, 4), " B ", "BAB", " B ", 'B', ModItems.ammo_4gauge, 'A', ModItems.pellet_claws);
		addRecipeAuto(new ItemStack(ModItems.ammo_4gauge_vampire, 4), "ABA", "BAB", "ABA", 'B', ModItems.ammo_4gauge, 'A', ModItems.toothpicks);
		addRecipeAuto(new ItemStack(ModItems.ammo_4gauge_void, 4), " B ", "BAB", " B ", 'B', ModItems.ammo_4gauge, 'A', ModItems.pellet_charged);
		addRecipeAuto(new ItemStack(ModItems.ammo_nuke, 1), "P", "S", "P", 'P', PU239.nugget(), 'S', ModItems.assembly_nuke);
		addRecipeAuto(new ItemStack(ModItems.ammo_nuke_low, 1), "P", "S", 'P', PU239.nugget(), 'S', ModItems.assembly_nuke);
		addRecipeAuto(new ItemStack(ModItems.ammo_nuke_high, 1), "PPP", "PSP", "PPP", 'P', PU239.nugget(), 'S', ModItems.assembly_nuke);
		addRecipeAuto(new ItemStack(ModItems.ammo_nuke_tots, 1), "PPP", "PIP", "PPP", 'P', ModItems.pellet_cluster, 'I', PU239.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_nuke_safe, 1), "G", "N", 'G', Items.GLOWSTONE_DUST, 'N', ModItems.ammo_nuke_low);
		addRecipeAuto(new ItemStack(ModItems.ammo_nuke_pumpkin, 1), " T ", "TST", " T ", 'T', Blocks.TNT, 'S', ModItems.assembly_nuke);
		addShapelessAuto(new ItemStack(ModItems.ammo_nuke, 6), ModItems.ammo_mirv);
		addShapelessAuto(new ItemStack(ModItems.ammo_nuke_low, 6), ModItems.ammo_mirv_low);
		addShapelessAuto(new ItemStack(ModItems.ammo_nuke_high, 6), ModItems.ammo_mirv_high);
		addShapelessAuto(new ItemStack(ModItems.ammo_nuke_safe, 6), ModItems.ammo_mirv_safe);
		addRecipeAuto(new ItemStack(ModItems.ammo_mirv, 1), "NNN", "CDS", "NNN", 'N', ModItems.ammo_nuke, 'C', ModItems.cap_aluminium, 'D', ModBlocks.det_cord, 'S', ModItems.hull_small_steel);
		addRecipeAuto(new ItemStack(ModItems.ammo_mirv_low, 1), "NNN", "CDS", "NNN", 'N', ModItems.ammo_nuke_low, 'C', ModItems.cap_aluminium, 'D', ModBlocks.det_cord, 'S', ModItems.hull_small_steel);
		addRecipeAuto(new ItemStack(ModItems.ammo_mirv_high, 1), "NNN", "CDS", "NNN", 'N', ModItems.ammo_nuke_high, 'C', ModItems.cap_aluminium, 'D', ModBlocks.det_cord, 'S', ModItems.hull_small_steel);
		addRecipeAuto(new ItemStack(ModItems.ammo_mirv_safe, 1), "NNN", "CDS", "NNN", 'N', ModItems.ammo_nuke_safe, 'C', ModItems.cap_aluminium, 'D', ModBlocks.det_cord, 'S', ModItems.hull_small_steel);
		//since the milk part of the recipe isn't realy present in the MIRV's effect, it might as well be replaced with something more sensible, i.e. duct tape
		addRecipeAuto(new ItemStack(ModItems.ammo_mirv_special, 1), "CBC", "MCM", "CBC", 'C', ModItems.canned_jizz, 'B', ModItems.gun_bf_ammo, 'M', ModItems.ammo_mirv);
		addRecipeAuto(new ItemStack(ModItems.ammo_44_phosphorus, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_44, 'A', P_WHITE.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_50bmg_phosphorus, 8), "BBB", "BAB", "BBB", 'B', ModItems.ammo_50bmg, 'A', P_WHITE.ingot());
		addRecipeAuto(new ItemStack(ModItems.ammo_rocket_phosphorus, 1), "G", "R", 'G', P_WHITE.ingot(), 'R', ModItems.ammo_rocket);
		addRecipeAuto(new ItemStack(ModItems.ammo_grenade_phosphorus, 2), "GIG", 'G', ModItems.ammo_grenade, 'I', P_WHITE.ingot());

	}

	public static void addBioCrafting(){
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), Items.MELON, Items.MELON, Items.MELON, Items.MELON, Items.MELON, Items.MELON, Items.MELON);
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), Items.APPLE, Items.APPLE, Items.APPLE, Items.APPLE, Items.APPLE, Items.APPLE, Items.APPLE, Items.APPLE, Items.APPLE);
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), Items.REEDS, Items.REEDS, Items.REEDS, Items.REEDS, Items.REEDS, Items.REEDS, Items.REEDS, Items.REEDS, Items.REEDS);
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), Items.ROTTEN_FLESH, Items.ROTTEN_FLESH, Items.ROTTEN_FLESH, Items.ROTTEN_FLESH, Items.ROTTEN_FLESH, Items.ROTTEN_FLESH, Items.ROTTEN_FLESH);
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), Items.CARROT, Items.CARROT, Items.CARROT, Items.CARROT, Items.CARROT, Items.CARROT, Items.CARROT, Items.CARROT, Items.CARROT);
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO);
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), Items.POISONOUS_POTATO, Items.POISONOUS_POTATO, Items.POISONOUS_POTATO, Items.POISONOUS_POTATO, Items.POISONOUS_POTATO, Items.POISONOUS_POTATO, Items.POISONOUS_POTATO, Items.POISONOUS_POTATO, Items.POISONOUS_POTATO);
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), Items.BEETROOT, Items.BEETROOT, Items.BEETROOT, Items.BEETROOT, Items.BEETROOT, Items.BEETROOT, Items.BEETROOT);
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), "treeSapling", "treeSapling", "treeSapling", "treeSapling", "treeSapling", "treeSapling", "treeSapling");
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), "treeLeaves", "treeLeaves", "treeLeaves", "treeLeaves", "treeLeaves");
		addShapelessAuto(new ItemStack(ModItems.biomass, 8), Blocks.PUMPKIN, Blocks.PUMPKIN, Blocks.PUMPKIN, Blocks.PUMPKIN, Blocks.PUMPKIN, Blocks.PUMPKIN);
		addShapelessAuto(new ItemStack(ModItems.biomass, 6), KEY_LOG, KEY_LOG, KEY_LOG);
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), KEY_PLANKS, KEY_PLANKS, KEY_PLANKS, KEY_PLANKS, KEY_PLANKS, KEY_PLANKS, KEY_PLANKS, KEY_PLANKS, KEY_PLANKS);
		addShapelessAuto(new ItemStack(ModItems.biomass, 8), Blocks.HAY_BLOCK, Blocks.HAY_BLOCK);
		addShapelessAuto(new ItemStack(ModItems.biomass, 1), Items.WHEAT_SEEDS, Items.WHEAT_SEEDS, Items.WHEAT_SEEDS, Items.WHEAT_SEEDS, Items.WHEAT_SEEDS, Items.WHEAT_SEEDS, Items.WHEAT_SEEDS, Items.WHEAT_SEEDS, Items.WHEAT_SEEDS);
		addShapelessAuto(new ItemStack(ModItems.biomass, 2), Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS, Items.PUMPKIN_SEEDS);
		addShapelessAuto(new ItemStack(ModItems.biomass, 2), Items.MELON_SEEDS, Items.MELON_SEEDS, Items.MELON_SEEDS, Items.MELON_SEEDS, Items.MELON_SEEDS, Items.MELON_SEEDS, Items.MELON_SEEDS, Items.MELON_SEEDS, Items.MELON_SEEDS);
		addShapelessAuto(new ItemStack(ModItems.biomass, 2), Items.BEETROOT_SEEDS, Items.BEETROOT_SEEDS, Items.BEETROOT_SEEDS, Items.BEETROOT_SEEDS, Items.BEETROOT_SEEDS, Items.BEETROOT_SEEDS, Items.BEETROOT_SEEDS, Items.BEETROOT_SEEDS, Items.BEETROOT_SEEDS);
		addShapelessAuto(new ItemStack(ModItems.bio_wafer, 1), ModItems.biomass_compressed, ModItems.biomass_compressed);

	}

	public static void addStairSlabCrafting(){
		addSlabStair(ModBlocks.reinforced_brick_slab, ModBlocks.reinforced_brick_stairs, ModBlocks.reinforced_brick);
		addSlabStair(ModBlocks.reinforced_sand_slab, ModBlocks.reinforced_sand_stairs, ModBlocks.reinforced_sand);
		addSlabStair(ModBlocks.reinforced_stone_slab, ModBlocks.reinforced_stone_stairs, ModBlocks.reinforced_stone);
		addSlabStair(ModBlocks.brick_concrete_slab, ModBlocks.brick_concrete_stairs, ModBlocks.brick_concrete);
		addSlabStair(ModBlocks.brick_concrete_mossy_slab, ModBlocks.brick_concrete_mossy_stairs, ModBlocks.brick_concrete_mossy);
		addSlabStair(ModBlocks.brick_concrete_cracked_slab, ModBlocks.brick_concrete_cracked_stairs, ModBlocks.brick_concrete_cracked);
		addSlabStair(ModBlocks.brick_concrete_broken_slab, ModBlocks.brick_concrete_broken_stairs, ModBlocks.brick_concrete_broken);
		addSlabStair(ModBlocks.brick_compound_slab, ModBlocks.brick_compound_stairs, ModBlocks.brick_compound);
		addSlabStair(ModBlocks.brick_fire_slab, ModBlocks.brick_fire_stairs, ModBlocks.brick_fire);
		addSlabStair(ModBlocks.brick_light_slab, ModBlocks.brick_light_stairs, ModBlocks.brick_light);
		addSlabStair(ModBlocks.brick_asbestos_slab, ModBlocks.brick_asbestos_stairs, ModBlocks.brick_asbestos);
		addSlabStair(ModBlocks.brick_obsidian_slab, ModBlocks.brick_obsidian_stairs, ModBlocks.brick_obsidian);
		addSlabStair(ModBlocks.cmb_brick_reinforced_slab, ModBlocks.cmb_brick_reinforced_stairs, ModBlocks.cmb_brick_reinforced);
		addSlabStair(ModBlocks.concrete_slab, ModBlocks.concrete_stairs, ModBlocks.concrete);
		addSlabStair(ModBlocks.concrete_smooth_slab, ModBlocks.concrete_smooth_stairs, ModBlocks.concrete_smooth);
		addSlabStair(ModBlocks.concrete_white_slab, ModBlocks.concrete_white_stairs, ModBlocks.concrete_white);
		addSlabStair(ModBlocks.concrete_orange_slab, ModBlocks.concrete_orange_stairs, ModBlocks.concrete_orange);
		addSlabStair(ModBlocks.concrete_magenta_slab, ModBlocks.concrete_magenta_stairs, ModBlocks.concrete_magenta);
		addSlabStair(ModBlocks.concrete_light_blue_slab, ModBlocks.concrete_light_blue_stairs, ModBlocks.concrete_light_blue);
		addSlabStair(ModBlocks.concrete_yellow_slab, ModBlocks.concrete_yellow_stairs, ModBlocks.concrete_yellow);
		addSlabStair(ModBlocks.concrete_lime_slab, ModBlocks.concrete_lime_stairs, ModBlocks.concrete_lime);
		addSlabStair(ModBlocks.concrete_pink_slab, ModBlocks.concrete_pink_stairs, ModBlocks.concrete_pink);
		addSlabStair(ModBlocks.concrete_gray_slab, ModBlocks.concrete_gray_stairs, ModBlocks.concrete_gray);
		addSlabStair(ModBlocks.concrete_silver_slab, ModBlocks.concrete_silver_stairs, ModBlocks.concrete_silver);
		addSlabStair(ModBlocks.concrete_cyan_slab, ModBlocks.concrete_cyan_stairs, ModBlocks.concrete_cyan);
		addSlabStair(ModBlocks.concrete_purple_slab, ModBlocks.concrete_purple_stairs, ModBlocks.concrete_purple);
		addSlabStair(ModBlocks.concrete_blue_slab, ModBlocks.concrete_blue_stairs, ModBlocks.concrete_blue);
		addSlabStair(ModBlocks.concrete_brown_slab, ModBlocks.concrete_brown_stairs, ModBlocks.concrete_brown);
		addSlabStair(ModBlocks.concrete_green_slab, ModBlocks.concrete_green_stairs, ModBlocks.concrete_green);
		addSlabStair(ModBlocks.concrete_red_slab, ModBlocks.concrete_red_stairs, ModBlocks.concrete_red);
		addSlabStair(ModBlocks.concrete_black_slab, ModBlocks.concrete_black_stairs, ModBlocks.concrete_black);
		addSlabStair(ModBlocks.concrete_asbestos_slab, ModBlocks.concrete_asbestos_stairs, ModBlocks.concrete_asbestos);
		addSlabStair(ModBlocks.ducrete_smooth_slab, ModBlocks.ducrete_smooth_stairs, ModBlocks.ducrete_smooth);
		addSlabStair(ModBlocks.ducrete_slab, ModBlocks.ducrete_stairs, ModBlocks.ducrete);
		addSlabStair(ModBlocks.ducrete_brick_slab, ModBlocks.ducrete_brick_stairs, ModBlocks.ducrete_brick);
		addSlabStair(ModBlocks.ducrete_reinforced_slab, ModBlocks.ducrete_reinforced_stairs, ModBlocks.ducrete_reinforced);
		addSlabStair(ModBlocks.tile_lab_slab, ModBlocks.tile_lab_stairs, ModBlocks.tile_lab);
		addSlabStair(ModBlocks.tile_lab_cracked_slab, ModBlocks.tile_lab_cracked_stairs, ModBlocks.tile_lab_cracked);
		addSlabStair(ModBlocks.tile_lab_broken_slab, ModBlocks.tile_lab_broken_stairs, ModBlocks.tile_lab_broken);
		addSlabStair(ModBlocks.pink_slab, ModBlocks.pink_stairs, ModBlocks.pink_planks);
	}

	public static void addPartsCrafting() {
		addRecipeAuto(new ItemStack(ModItems.coil_copper, 1), "WWW", "WIW", "WWW", 'W', new ItemStack(ModItems.wire, 1, MAT_MINGRADE.id), 'I', IRON.ingot());
		addRecipeAuto(new ItemStack(ModItems.coil_advanced_alloy, 1), "WWW", "WIW", "WWW", 'W', new ItemStack(ModItems.wire, 1, MAT_ALLOY.id), 'I', IRON.ingot());
		addRecipeAuto(new ItemStack(ModItems.coil_gold, 1), "WWW", "WIW", "WWW", 'W', new ItemStack(ModItems.wire, 1, MAT_GOLD.id), 'I', IRON.ingot());
		addRecipeAuto(new ItemStack(ModItems.coil_copper_torus, 2), " C ", "CPC", " C ", 'P', IRON.plate(), 'C', ModItems.coil_copper);
		addRecipeAuto(new ItemStack(ModItems.coil_advanced_torus, 2), " C ", "CPC", " C ", 'P', IRON.plate(), 'C', ModItems.coil_advanced_alloy);
		addRecipeAuto(new ItemStack(ModItems.coil_gold_torus, 2), " C ", "CPC", " C ", 'P', IRON.plate(), 'C', ModItems.coil_gold);
		addRecipeAuto(new ItemStack(ModItems.coil_tungsten, 1), "WWW", "WIW", "WWW", 'W', new ItemStack(ModItems.wire, 1, MAT_TUNGSTEN.id), 'I', IRON.ingot());
		addRecipeAuto(new ItemStack(ModItems.coil_magnetized_tungsten, 1), "WWW", "WIW", "WWW", 'W', new ItemStack(ModItems.wire, 1, MAT_MAGTUNG.id), 'I', IRON.ingot());
		addRecipeAuto(new ItemStack(ModItems.tank_steel, 2), "STS", "S S", "STS", 'S', STEEL.plate(), 'T', TI.plate());
		addRecipeAuto(new ItemStack(ModItems.motor, 2), " R ", "ICI", "ITI", 'R', new ItemStack(ModItems.wire, 1, MAT_MINGRADE.id), 'T', ModItems.coil_copper_torus, 'I', IRON.plate(), 'C', ModItems.coil_copper);
		addRecipeAuto(new ItemStack(ModItems.motor, 2), " R ", "ICI", " T ", 'R', new ItemStack(ModItems.wire, 1, MAT_MINGRADE.id), 'T', ModItems.coil_copper_torus, 'I', STEEL.plate(), 'C', ModItems.coil_copper);
		addRecipeAuto(new ItemStack(ModItems.motor_desh, 1), "PCP", "DMD", "PCP", 'P', ANY_PLASTIC.ingot(), 'C', ModItems.coil_gold_torus, 'D', DESH.ingot(), 'M', ModItems.motor);
		addRecipeAuto(new ItemStack(ModItems.deuterium_filter, 1), "TST", "SCS", "TST", 'T', ANY_RESISTANTALLOY.ingot(), 'S', S.dust(), 'C', ModItems.catalyst_clay);
		addRecipeAuto(new ItemStack(ModItems.thermo_unit_endo, 1), "EEE", "ETE", "EEE", 'E', Item.getItemFromBlock(Blocks.ICE), 'T', ModItems.thermo_unit_empty);
		addRecipeAuto(new ItemStack(ModItems.thermo_unit_exo, 1), "LLL", "LTL", "LLL", 'L', Items.LAVA_BUCKET, 'T', ModItems.thermo_unit_empty);
		addRecipeAuto(new ItemStack(ModItems.catalytic_converter, 1), "PCP", "PBP", "PCP", 'P', ANY_HARDPLASTIC.ingot(), 'C', CO.dust(), 'B', ANY_BISMOID.ingot());
		addShapelessAuto(new ItemStack(ModItems.fuse, 1), STEEL.plate(), ModItems.plate_polymer, W.wire());

	}

    public static void addChemDyeCrafting(){
        //Unleash the colores
        addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.GRAY, 3), DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BLACK), DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.WHITE), DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN), DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN));
        addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.SILVER, 3), DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.GRAY), DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.WHITE), DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN), DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN));
        addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.ORANGE, 3), DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.RED), DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.YELLOW), DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN), DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN));
        addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.LIME, 3), DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.GREEN), DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.WHITE), DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN), DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN));
        addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.CYAN, 3), DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BLUE), DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.GREEN), DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN), DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN));
        addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.PURPLE, 3), DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.RED), DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BLUE), DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN), DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN));
        addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BROWN, 3), DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.ORANGE), DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BLACK), DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN), DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN));
        addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.MAGENTA, 3), DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.PINK), DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.PURPLE), DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN), DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN));
        addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.LIGHTBLUE, 3), DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BLUE), DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.WHITE), DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN), DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN));
        addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.PINK, 3), DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.RED), DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.WHITE), DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN), DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN));
        addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.GREEN, 3), DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BLUE), DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.YELLOW), DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN), DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN));

    }

	public static void addCrafting() {
		//NEWCRAFT
		addPartsCrafting();
		addCircuitCrafting();
		addWatzPellets();
		addConveyorCrafting();
		addPureCrafting();
		addRawFuelCrafting();
		addRodCrafting();
		addPowderCrafting();
		addGearCrafting();
		addWeaponCrafting();
		addAmmoCrafting();
		addConcreteCrafting();
		addBioCrafting();
		addStairSlabCrafting();
        addChemDyeCrafting();

		addRecipeAuto(new ItemStack(ModBlocks.struct_watz_core, 1), "CBC", "BHB", "CBC", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'B', ANY_RESISTANTALLOY.plateCast(), 'H', ModBlocks.watz_cooler);
		addRecipeAuto(new ItemStack(ModBlocks.watz_pump, 1), "MPM", "PCP", "PSP", 'M', ModItems.motor_desh, 'P', ANY_RESISTANTALLOY.plateCast(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP_BISMOID), 'S', ModItems.pipes_steel);


		addRecipeAuto(new ItemStack(ModItems.egg_balefire_shard, 1), "##", "##", '#', ModItems.powder_balefire);
		addRecipeAuto(new ItemStack(ModItems.hazmat_cloth_red, 1), "C", "R", "C", 'C', ModItems.hazmat_cloth, 'R', BAKELITE.dust());
		addRecipeAuto(new ItemStack(ModItems.hazmat_cloth_grey, 1), "DPD", "ICI", "DLD", 'C', ModItems.hazmat_cloth_red, 'P', IRON.plate(), 'L', PB.plate(), 'I', ANY_RUBBER.ingot(), 'D', I.dustTiny());
		addRecipeAuto(new ItemStack(ModItems.asbestos_cloth, 8), "SCS", "CPC", "SCS", 'S', Items.STRING, 'P', BR.dust(), 'C', Blocks.WOOL);
		addRecipeAuto(new ItemStack(ModItems.pipes_steel, 1), "B", "B", "B", 'B', STEEL.block());
		addRecipeAuto(new ItemStack(ModItems.bolt, 4, MAT_STEEL.id), "D", "D", 'D', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.bolt, 4, MAT_TUNGSTEN.id), "D", "D", 'D', W.ingot());
		addRecipeAuto(new ItemStack(ModItems.bolt, 4, MAT_DURA.id), "D", "D", 'D', DURA.ingot());
		addRecipeAuto(new ItemStack(ModItems.pellet_coal, 1), "PFP", "FOF", "PFP", 'P', COAL.dust(), 'F', Items.FLINT, 'O', ModBlocks.gravel_obsidian);

		addRecipeAuto(new ItemStack(ModBlocks.marker_structure, 1), "L", "G", "R", 'L', LAPIS.dust(), 'G', Items.GLOWSTONE_DUST, 'R', Blocks.REDSTONE_TORCH);




		addRecipeAuto(new ItemStack(ModItems.cell, 6), "SSS", "G G", "SSS", 'S', STEEL.plate(), 'G', KEY_ANYPANE);
		addRecipeAuto(ItemCell.getFullCell(ModForgeFluids.DEUTERIUM, 8), "DDD", "DTD", "DDD", 'D', new IngredientNBT2(new ItemStack(ModItems.cell)), 'T', ModItems.mike_deut);

		addRecipeAuto(new ItemStack(ModItems.canister_generic, 2), "S ", "AA", "AA", 'S', STEEL.plate(), 'A', AL.plate());
		addRecipeAuto(new ItemStack(ModBlocks.yellow_barrel, 1), " D ", "LTL", 'D', ModBlocks.block_waste, 'T', ModItems.tank_steel, 'L', PB.plate());
		addShapelessAuto(new ItemStack(ModItems.nuclear_waste, 9), ModBlocks.yellow_barrel);
		addRecipeAuto(new ItemStack(ModItems.gas_canister, 2), "S ", "AA", "AA", 'A', STEEL.plate(), 'S', CU.plate());
		addRecipeAuto(new ItemStack(ModBlocks.vitrified_barrel, 1), "LSL", "PWP", "LSL", 'L', PB.plate(), 'S', Blocks.SAND, 'P', ANY_PLASTIC.ingot(), 'W', ModBlocks.block_waste);
		addShapelessAuto(new ItemStack(ModBlocks.block_waste_painted, 1), "dyeYellow", ModBlocks.block_waste);
		addRecipeAuto(new ItemStack(ModBlocks.field_disturber), "ABA", "CDC", "ABA", 'A', STAR.ingot(), 'B', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BISMOID), 'C', ModBlocks.hadron_coil_magtung, 'D', ModItems.pellet_rtg_gold);

		addRecipeAuto(new ItemStack(Blocks.TNT, 4), " S ", "S#S", " S ", '#', ModItems.ball_tnt, 'S', "sand");

		addRecipeAuto(new ItemStack(ModBlocks.block_meteor_cobble, 1), "##", "##", '#', ModItems.fragment_meteorite);


		addRecipeAuto(new ItemStack(ModBlocks.block_schrabidium_cluster, 1), "#S#", "SAS", "#S#", '#', SA326.ingot(), 'A', SBD.ingot(), 'S', STAR.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.block_niter_reinforced, 1), "TCT", "CNC", "TCT", 'T', TCALLOY.ingot(), 'C', ModBlocks.concrete, 'N', KNO.block());

		addRecipeAuto(new ItemStack(ModBlocks.hazmat, 8), "###", "# #", "###", '#', ModItems.hazmat_cloth);
		addRecipeAuto(new ItemStack(ModItems.hazmat_cloth, 1), "#", '#', ModBlocks.hazmat);


		addShapelessAuto(new ItemStack(ModBlocks.deco_aluminium), AL.ingot(), ModBlocks.steel_scaffold);
		addShapelessAuto(new ItemStack(ModBlocks.deco_beryllium), BE.ingot(), ModBlocks.steel_scaffold);
		addShapelessAuto(new ItemStack(ModBlocks.deco_lead), PB.ingot(), ModBlocks.steel_scaffold);
		addShapelessAuto(new ItemStack(ModBlocks.deco_red_copper), MINGRADE.ingot(), ModBlocks.steel_scaffold);
		addShapelessAuto(new ItemStack(ModBlocks.deco_steel), STEEL.ingot(), ModBlocks.steel_scaffold);
		addShapelessAuto(new ItemStack(ModBlocks.deco_titanium), TI.ingot(), ModBlocks.steel_scaffold);
		addShapelessAuto(new ItemStack(ModBlocks.deco_tungsten), W.ingot(), ModBlocks.steel_scaffold);

		//Drillgon200: Gone, reduced.


		addRecipeAuto(new ItemStack(ModItems.cap_aluminium, 1), "PIP", 'P', AL.plate(), 'I', AL.ingot());
		addRecipeAuto(new ItemStack(ModItems.hull_small_steel, 1), "PPP", "   ", "PPP", 'P', STEEL.plate());
		addRecipeAuto(new ItemStack(ModItems.hull_small_aluminium, 1), "PPP", "   ", "PPP", 'P', AL.plate());
		addRecipeAuto(new ItemStack(ModItems.hull_big_steel, 1), "III", "   ", "III", 'I', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.hull_big_aluminium, 1), "III", "   ", "III", 'I', AL.ingot());
		addRecipeAuto(new ItemStack(ModItems.hull_big_titanium, 1), "III", "   ", "III", 'I', TI.ingot());
		addRecipeAuto(new ItemStack(ModItems.fins_flat, 1), "IP", "PP", "IP", 'P', STEEL.plate(), 'I', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.fins_small_steel, 1), " PP", "PII", " PP", 'P', STEEL.plate(), 'I', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.fins_big_steel, 1), " PI", "III", " PI", 'P', STEEL.plate(), 'I', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.fins_tri_steel, 1), " PI", "IIB", " PI", 'P', STEEL.plate(), 'I', STEEL.ingot(), 'B', STEEL.block());
		addRecipeAuto(new ItemStack(ModItems.fins_quad_titanium, 1), " PP", "III", " PP", 'P', TI.plate(), 'I', TI.ingot());
		addRecipeAuto(new ItemStack(ModItems.sphere_steel, 1), "PIP", "I I", "PIP", 'P', STEEL.plate(), 'I', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.pedestal_steel, 1), "P P", "P P", "III", 'P', STEEL.plate(), 'I', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.lemon, 1), " D ", "DSD", " D ", 'D', new ItemStack(Items.DYE, 1, 11), 'S', "stone");
		addRecipeAuto(new ItemStack(ModItems.blade_titanium, 2), "TP", "TP", "TT", 'P', TI.plate(), 'T', TI.ingot());
		addRecipeAuto(new ItemStack(ModItems.turbine_titanium, 1), "BBB", "BSB", "BBB", 'B', ModItems.blade_titanium, 'S', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.rotor_steel, 3), "CCC", "SSS", "CCC", 'C', ModItems.coil_gold, 'S', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.generator_steel, 1), "RRR", "CCC", "SSS", 'C', ModItems.coil_gold_torus, 'S', STEEL.ingot(), 'R', ModItems.rotor_steel);
		addRecipeAuto(new ItemStack(ModItems.shimmer_head, 1), "SSS", "DTD", "SSS", 'S', STEEL.ingot(), 'D', DESH.block(), 'T', W.block());
		addRecipeAuto(new ItemStack(ModItems.shimmer_axe_head, 1), "PII", "PBB", "PII", 'P', STEEL.plate(), 'B', DESH.block(), 'I', W.ingot());
		addRecipeAuto(new ItemStack(ModItems.shimmer_handle, 1), "GP", "GP", "GP", 'G', GOLD.plate(), 'P', ANY_PLASTIC.ingot());
		addRecipeAuto(new ItemStack(ModItems.shimmer_sledge, 1), "H", "G", "G", 'G', ModItems.shimmer_handle, 'H', ModItems.shimmer_head);
		addRecipeAuto(new ItemStack(ModItems.shimmer_axe, 1), "H", "G", "G", 'G', ModItems.shimmer_handle, 'H', ModItems.shimmer_axe_head);
		addRecipeAuto(new ItemStack(ModItems.definitelyfood, 1), "DDD", "SDS", "DDD", 'D', Blocks.DIRT, 'S', STEEL.plate());
		addRecipeAuto(new ItemStack(ModItems.blade_tungsten, 2), "IP", "TP", "TI", 'P', TI.plate(), 'T', TI.ingot(), 'I', W.ingot());
		addRecipeAuto(new ItemStack(ModItems.turbine_tungsten, 1), "BBB", "BSB", "BBB", 'B', ModItems.blade_tungsten, 'S', DURA.ingot());
		addRecipeAuto(new ItemStack(ModItems.ring_starmetal, 1), " S ", "S S", " S ", 'S', STAR.ingot());

		addRecipeAuto(new ItemStack(ModItems.wrench, 1), " S ", " IS", "I  ", 'S', STEEL.ingot(), 'I', IRON.ingot());
		addRecipeAuto(new ItemStack(ModItems.wrench_flipped, 1), "S", "D", "W", 'S', Items.IRON_SWORD, 'D', ModItems.ducttape, 'W', ModItems.wrench);
		addRecipeAuto(new ItemStack(ModItems.memespoon, 1), "CGC", "PSP", "IAI", 'C', ModItems.powder_cloud, 'G', ModBlocks.block_thorium, 'P', ModItems.photo_panel, 'S', ModItems.steel_shovel, 'I', ModItems.plate_polymer, 'A', AUSTRALIUM.ingot());
		addShapelessAuto(new ItemStack(ModItems.cbt_device, 1), new ItemStack(ModItems.bolt, 1, MAT_TUNGSTEN.id), ModItems.wrench);

		addShapelessAuto(new ItemStack(ModItems.toothpicks, 3), Items.STICK, Items.STICK, Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.ducttape, 6), "FSF", "SPS", "FSF", 'F', Items.STRING, 'S', Items.SLIME_BALL, 'P', Items.PAPER);
		addRecipeAuto(new ItemStack(ModItems.ball_resin), "DD", "DD", 'D', Blocks.YELLOW_FLOWER);
		addRecipeAuto(new ItemStack(ModItems.mold_base), " B ", "BIB", " B ", 'B', ModItems.ingot_firebrick, 'I', IRON.ingot());
		
		addRecipeAuto(new ItemStack(ModBlocks.foundry_basin), "B B", "B B", "BSB", 'B', ModItems.ingot_firebrick, 'S', Blocks.STONE_SLAB);
		addRecipeAuto(new ItemStack(ModBlocks.foundry_mold), "B B", "BSB", 'B', ModItems.ingot_firebrick, 'S', Blocks.STONE_SLAB);
		addRecipeAuto(new ItemStack(ModBlocks.foundry_channel, 4), "B B", " S ", 'B', ModItems.ingot_firebrick, 'S', Blocks.STONE_SLAB);
		// addRecipeAuto(new ItemStack(ModBlocks.foundry_tank), new Object[] { "B B", "I I", "BSB", 'B', ModItems.ingot_firebrick, 'I', STEEL.ingot(), 'S', Blocks.STONE_SLAB });
		addShapelessAuto(new ItemStack(ModBlocks.foundry_outlet), ModBlocks.foundry_channel, STEEL.plate());
		// addShapelessAuto(new ItemStack(ModBlocks.foundry_slagtap), new Object[] { ModBlocks.foundry_channel, Blocks.stonebrick });
		
		addShapelessAuto(new ItemStack(ModItems.missile_taint, 1), ModItems.missile_assembly, new IngredientNBT2(FluidUtil.getFilledBucket(new FluidStack(ModForgeFluids.MUD_FLUID, 1000))), ModItems.powder_spark_mix, ModItems.powder_magic);
		addShapelessAuto(new ItemStack(ModItems.missile_micro, 1), ModItems.missile_assembly, ModItems.ducttape, ModItems.ammo_nuke);
		addShapelessAuto(new ItemStack(ModItems.missile_bhole, 1), ModItems.missile_assembly, ModItems.ducttape, ModItems.grenade_black_hole);
		addShapelessAuto(new ItemStack(ModItems.missile_schrabidium, 1), ModItems.missile_assembly, ModItems.ducttape, ModItems.grenade_aschrab);
		addShapelessAuto(new ItemStack(ModItems.missile_schrabidium, 1), ModItems.missile_assembly, ModItems.ducttape, new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.SAS3)), PVC.ingot());
		addShapelessAuto(new ItemStack(ModItems.missile_emp, 1), ModItems.missile_assembly, ModItems.ducttape, ModBlocks.emp_bomb);

		addRecipeAuto(new ItemStack(ModBlocks.machine_difurnace_ext, 1), " C ", "BGB", "BGB", 'C', CU.plate(), 'B', ModItems.ingot_firebrick, 'G', ModBlocks.steel_grate);
		addRecipeAuto(new ItemStack(ModBlocks.machine_nuke_furnace_off, 1), "SSS", "LFL", "CCC", 'S', STEEL.plate(), 'C', CU.plateWelded(), 'L', PB.plate(), 'F', Item.getItemFromBlock(Blocks.FURNACE));
		addRecipeAuto(new ItemStack(ModBlocks.machine_electric_furnace_off, 1), "BBB", "WFW", "RRR", 'B', BE.ingot(), 'R', ModItems.coil_tungsten, 'W', CU.plateCast(), 'F', Item.getItemFromBlock(Blocks.FURNACE));
		addRecipeAuto(new ItemStack(ModBlocks.machine_arc_furnace_off, 1), "ITI", "PFP", "ITI", 'I', W.ingot(), 'T', ModBlocks.machine_transformer, 'P', CU.plateCast(), 'F', Blocks.FURNACE);
		addRecipeAuto(new ItemStack(ModBlocks.red_wire_coated, 16), "WRW", "RIR", "WRW", 'W', ModItems.plate_polymer, 'I', MINGRADE.ingot(), 'R', new ItemStack(ModItems.wire, 1, MAT_MINGRADE.id));
		addShapelessAuto(new ItemStack(ModBlocks.red_wire_sealed, 1), ModBlocks.red_wire_coated, ModBlocks.brick_compound);
		addRecipeAuto(new ItemStack(ModBlocks.cable_switch, 1), "S", "W", 'S', Blocks.LEVER, 'W', ModBlocks.red_wire_coated);
		addRecipeAuto(new ItemStack(ModBlocks.cable_detector, 1), "S", "W", 'S', REDSTONE.dust(), 'W', ModBlocks.red_wire_coated);
		addRecipeAuto(new ItemStack(ModBlocks.cable_diode, 1), " Q ", "CAC", " Q ", 'Q', NETHERQUARTZ.gem(), 'C', ModBlocks.red_cable, 'A', AL.ingot());
		addShapelessAuto(new ItemStack(ModBlocks.red_cable_gauge), ModBlocks.red_cable, ModBlocks.concrete, DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC));
		addRecipeAuto(new ItemStack(ModBlocks.radio_torch_sender, 8), "PMP", "RTR", "PMP", 'P', TI.plate(), 'M', ModItems.magnetron, 'R', Items.COMPARATOR, 'T', ModItems.pellet_rtg);
		addRecipeAuto(new ItemStack(ModBlocks.radio_torch_receiver, 8), "PMP", "RTR", "PMP", 'P', STEEL.plate(), 'M', ModItems.magnetron, 'R', Items.REPEATER, 'T', ModItems.pellet_rtg);
		addRecipeAuto(new ItemStack(ModBlocks.red_cable, 16), " W ", "RRR", " W ", 'W', ModItems.plate_polymer, 'R', new ItemStack(ModItems.wire, 1, MAT_MINGRADE.id));
		addRecipeAuto(new ItemStack(ModBlocks.red_connector, 4), "C", "I", "S", 'C', ModItems.coil_copper, 'I', ModItems.plate_polymer, 'S', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.red_pylon, 4), "CWC", "PWP", " T ", 'C', ModItems.coil_copper_torus, 'W', KEY_PLANKS, 'P', ModItems.plate_polymer, 'T', ModBlocks.red_wire_coated);
		addRecipeAuto(new ItemStack(ModBlocks.machine_battery_potato, 1), "PCP", "WRW", "PCP", 'P', ItemBattery.getEmptyBattery(ModItems.battery_potato), 'C', CU.ingot(), 'R', Blocks.REDSTONE_BLOCK, 'W', KEY_PLANKS);
		addRecipeAuto(new ItemStack(ModBlocks.machine_coal_off, 1), "STS", "SCS", "SFS", 'S', STEEL.ingot(), 'T', ModItems.tank_steel, 'C', MINGRADE.ingot(), 'F', Blocks.FURNACE);
		addRecipeAuto(new ItemStack(ModBlocks.machine_boiler_off, 1), "SPS", "TFT", "SPS", 'S', STEEL.ingot(), 'P', CU.plateWelded(), 'T', ModItems.tank_steel, 'F', Blocks.FURNACE);
		addRecipeAuto(new ItemStack(ModBlocks.machine_boiler_electric_off, 1), "SPS", "TFT", "SPS", 'S', DESH.ingot(), 'P', CU.plateWelded(), 'T', ModItems.tank_steel, 'F', ModBlocks.machine_electric_furnace_off);
		addRecipeAuto(new ItemStack(ModBlocks.machine_boiler_rtg_off, 1), "SPS", "TFT", "SPS", 'S', STAR.ingot(), 'P', CU.plateWelded(), 'T', ModItems.tank_steel, 'F', ModBlocks.machine_rtg_furnace_off);
		addRecipeAuto(new ItemStack(ModBlocks.machine_turbine, 1), "PTP", "BMB", "PTP", 'P', TI.plate(), 'T', ModItems.turbine_titanium, 'B', ModItems.tank_steel, 'M', ModItems.motor);
		addRecipeAuto(new ItemStack(ModBlocks.machine_converter_he_rf, 1), "SSS", "CRB", "SSS", 'S', STEEL.ingot(), 'C', ModItems.coil_copper, 'R', ModItems.coil_copper_torus, 'B', REDSTONE.block());
		addRecipeAuto(new ItemStack(ModBlocks.machine_converter_rf_he, 1), "SSS", "BRC", "SSS", 'S', BE.ingot(), 'C', ModItems.coil_copper, 'R', ModItems.coil_copper_torus, 'B', REDSTONE.block());
		addRecipeAuto(new ItemStack(ModBlocks.crate_iron, 1), "PPP", "I I", "III", 'P', IRON.plate(), 'I', IRON.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.crate_steel, 1), "PPP", "I I", "III", 'P', STEEL.plate(), 'I', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.crate_desh, 1), " P ", "PAP", " P ", 'P', ModItems.plate_desh, 'A', ModBlocks.crate_steel);
		addRecipeAuto(new ItemStack(ModBlocks.crate_tungsten, 1), "BPB", "PCP", "BPB", 'B', W.block(), 'P', CU.plateCast(), 'C', ModBlocks.crate_steel);
		addRecipeAuto(new ItemStack(ModBlocks.safe, 1), "LAL", "ACA", "LAL", 'L', PB.plate(), 'A', ALLOY.plate(), 'C', ModBlocks.crate_steel);
		addRecipeAuto(new ItemStack(ModBlocks.machine_waste_drum, 1), "LRL", "BRB", "LRL", 'L', PB.ingot(), 'B', Blocks.IRON_BARS, 'R', ModItems.rod_quad_empty);
        addRecipeAuto(new ItemStack(ModBlocks.press_preheater, 1), "INI", "ILI", "INI", 'I', IRON.ingot(), 'N', Blocks.NETHERRACK, 'L', Items.LAVA_BUCKET);
        addRecipeAuto(new ItemStack(ModBlocks.machine_press, 1), "IRI", "IPI", "IBI", 'I', IRON.ingot(), 'R', Blocks.FURNACE, 'B', IRON.block(), 'P', Blocks.PISTON);
		addRecipeAuto(new ItemStack(ModBlocks.machine_siren, 1), "SIS", "ICI", "SRS", 'S', STEEL.plate(), 'I', ANY_RUBBER.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), 'R', REDSTONE.dust());
		addRecipeAuto(new ItemStack(ModBlocks.machine_microwave, 1), "III", "SGM", "IDI", 'I', ModItems.plate_polymer, 'S', STEEL.plate(), 'G', KEY_ANYPANE, 'M', ModItems.magnetron, 'D', ModItems.motor);

		addRecipeAuto(new ItemStack(ModBlocks.muffler, 1), "III", "IWI", "III", 'I', ANY_RUBBER.ingot(), 'W', Blocks.WOOL);

		addRecipeAuto(new ItemStack(ModBlocks.factory_titanium_hull, 1), "PIP", "I I", "PIP", 'P', TI.plate(), 'I', TI.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.factory_titanium_furnace, 1), "HMH", "MFM", "HMH", 'H', Item.getItemFromBlock(ModBlocks.factory_titanium_hull), 'M', ModItems.motor, 'F', Item.getItemFromBlock(Blocks.FURNACE));
		addRecipeAuto(new ItemStack(ModBlocks.factory_titanium_conductor, 1), "SWS", "FFF", "SWS", 'S', TI.ingot(), 'W', Item.getItemFromBlock(ModBlocks.red_wire_coated), 'F', ModItems.fuse);
		addRecipeAuto(new ItemStack(ModBlocks.factory_titanium_core, 1), "HPH", "PCP", "HPH", 'H', Item.getItemFromBlock(ModBlocks.factory_titanium_hull), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'P', Item.getItemFromBlock(Blocks.PISTON));
		addRecipeAuto(new ItemStack(ModItems.factory_core_titanium, 1), "BRB", "RHR", "BRB", 'B', ItemBattery.getEmptyBattery(ModItems.battery_generic), 'R', Item.getItemFromBlock(Blocks.REDSTONE_BLOCK), 'H', Item.getItemFromBlock(ModBlocks.factory_titanium_hull));
		addRecipeAuto(new ItemStack(ModItems.factory_core_advanced, 1), "BLB", "SHS", "BLB", 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced), 'S', S.block(), 'L', PB.block(), 'H', Item.getItemFromBlock(ModBlocks.factory_advanced_hull));
		addRecipeAuto(new ItemStack(ModItems.factory_core_advanced, 1), "BSB", "LHL", "BSB", 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced), 'S', S.block(), 'L', PB.block(), 'H', Item.getItemFromBlock(ModBlocks.factory_advanced_hull));

		addRecipeAuto(new ItemStack(ModItems.arc_electrode, 1), "C", "T", "C", 'C', COAL.dust(), 'T', new ItemStack(ModItems.bolt, 1, MAT_STEEL.id));
		addRecipeAuto(new ItemStack(ModItems.arc_electrode_desh, 1), "C", "T", "C", 'C', DESH.dust(), 'T', ModItems.arc_electrode);
		addRecipeAuto(new ItemStack(ModItems.detonator, 1), "C", "S", 'S', STEEL.plate(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC));
		addShapelessAuto(new ItemStack(ModItems.detonator_multi, 1), ModItems.detonator, DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED));
		addShapelessAuto(new ItemStack(ModItems.detonator_laser, 1), ModItems.detonator, DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), RUBBER.ingot(), GOLD.wireDense());
		addShapelessAuto(new ItemStack(ModItems.detonator_deadman, 1), ModItems.detonator, ModItems.defuser, ModItems.ducttape);
		addRecipeAuto(new ItemStack(ModItems.detonator_de, 1), "T", "D", "T", 'T', Blocks.TNT, 'D', ModItems.detonator_deadman);

		addRecipeAuto(ItemBombCaller.getStack(EnumCallerType.CARPET), "TTT", "TRT", "TTT", 'T', Blocks.TNT, 'R', ModItems.detonator_laser);
		addRecipeAuto(ItemBombCaller.getStack(EnumCallerType.NAPALM), "TTT", "TRT", "TTT", 'T', ModItems.grenade_gascan, 'R', ModItems.detonator_laser);
		addRecipeAuto(ItemBombCaller.getStack(EnumCallerType.POISON), "TTT", "TRT", "TTT", 'T', ModItems.pellet_gas, 'R', ModItems.detonator_laser);
		addRecipeAuto(ItemBombCaller.getStack(EnumCallerType.ORANGE), "TRT", 'T', ModItems.grenade_cloud, 'R', ModItems.detonator_laser);
		addRecipeAuto(ItemBombCaller.getStack(EnumCallerType.ATOMIC), "TRT", 'T', ModItems.ammo_nuke, 'R', ModItems.detonator_laser);

		addRecipeAuto(new ItemStack(ModItems.crystal_xen, 1), "EEE", "EIE", "EEE", 'E', ModItems.powder_power, 'I', EUPH.ingot());

		addRecipeAuto(new ItemStack(ModItems.screwdriver, 1), "  I", " I ", "S  ", 'S', STEEL.ingot(), 'I', IRON.ingot());
		addRecipeAuto(new ItemStack(ModItems.screwdriver_desh, 1), "  I", " I ", "S  ", 'S', ANY_PLASTIC.ingot(), 'I', DESH.ingot());
		addShapelessAuto(new ItemStack(ModItems.overfuse, 1), ModItems.screwdriver, NP237.dust(), I.dust(), TH232.dust(), AT.dust(), ND.dust(), CU.plateWelded(), ModItems.singularity_spark, CS.dust());
		addShapelessAuto(new ItemStack(ModItems.overfuse, 1), ModItems.screwdriver, SR.dust(), BR.dust(), CO.dust(), TS.dust(), NB.dust(), CU.plateWelded(), ModItems.singularity_spark, CE.dust());
		
		addRecipeAuto(new ItemStack(ModItems.blades_aluminum, 1), " P ", "PIP", " P ", 'P', AL.plate(), 'I', AL.ingot());
		addRecipeAuto(new ItemStack(ModItems.blades_gold, 1), " P ", "PIP", " P ", 'P', GOLD.plate(), 'I', GOLD.ingot());
		addRecipeAuto(new ItemStack(ModItems.blades_iron, 1), " P ", "PIP", " P ", 'P', IRON.plate(), 'I', IRON.ingot());
		addRecipeAuto(new ItemStack(ModItems.blades_steel, 1), " P ", "PIP", " P ", 'P', STEEL.plate(), 'I', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.blades_titanium, 1), " P ", "PIP", " P ", 'P', TI.plate(), 'I', TI.ingot());
		addRecipeAuto(new ItemStack(ModItems.blades_advanced_alloy, 1), " P ", "PIP", " P ", 'P', ALLOY.plate(), 'I', ALLOY.ingot());
		addRecipeAuto(new ItemStack(ModItems.blades_combine_steel, 1), " P ", "PIP", " P ", 'P', CMB.plate(), 'I', CMB.ingot());
		addRecipeAuto(new ItemStack(ModItems.blades_schrabidium, 1), " P ", "PIP", " P ", 'P', SA326.plate(), 'I', SA326.ingot());
		addRecipeAuto(new ItemStack(ModItems.blades_desh, 1), "NPN", "PBP", "NPN", 'N', SA326.nugget(), 'P', ModItems.plate_desh, 'B', ModItems.blades_combine_steel);

		addRecipeAuto(new ItemStack(ModItems.blades_aluminum, 1), "PIP", 'P', AL.plate(), 'I', new ItemStack(ModItems.blades_aluminum, 1, OreDictionary.WILDCARD_VALUE));
		addRecipeAuto(new ItemStack(ModItems.blades_gold, 1), "PIP", 'P', GOLD.plate(), 'I', new ItemStack(ModItems.blades_gold, 1, OreDictionary.WILDCARD_VALUE));
		addRecipeAuto(new ItemStack(ModItems.blades_iron, 1), "PIP", 'P', IRON.plate(), 'I', new ItemStack(ModItems.blades_iron, 1, OreDictionary.WILDCARD_VALUE));
		addRecipeAuto(new ItemStack(ModItems.blades_steel, 1), "PIP", 'P', STEEL.plate(), 'I', new ItemStack(ModItems.blades_steel, 1, OreDictionary.WILDCARD_VALUE));
		addRecipeAuto(new ItemStack(ModItems.blades_titanium, 1), "PIP", 'P', TI.plate(), 'I', new ItemStack(ModItems.blades_titanium, 1, OreDictionary.WILDCARD_VALUE));
		addRecipeAuto(new ItemStack(ModItems.blades_advanced_alloy, 1), "PIP", 'P', ALLOY.plate(), 'I', new ItemStack(ModItems.blades_advanced_alloy, 1, OreDictionary.WILDCARD_VALUE));
		addRecipeAuto(new ItemStack(ModItems.blades_combine_steel, 1), "PIP", 'P', CMB.plate(), 'I', new ItemStack(ModItems.blades_combine_steel, 1, OreDictionary.WILDCARD_VALUE));
		addRecipeAuto(new ItemStack(ModItems.blades_schrabidium, 1), "PIP", 'P', SA326.plate(), 'I', new ItemStack(ModItems.blades_schrabidium, 1, OreDictionary.WILDCARD_VALUE));

		addRecipeAuto(new ItemStack(ModItems.laser_crystal_nano, 1), "QPQ", "ACA", "QPQ", 'Q', ModBlocks.glass_quartz, 'P', GRAPHITE.ingot(), 'A', ANY_PLASTIC.dust(), 'C', ModItems.filter_coal);
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_pentacene, 1), "QSQ", "AEA", "QSQ", 'Q', ModBlocks.glass_quartz, 'A', ALLOY.dust(), 'S', CE.ingot(), 'E', new IngredientNBT2(ItemFluidTank.getFullTank(ModForgeFluids.AROMATICS)));
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_co2, 1), "QDQ", "NCN", "QDQ", 'Q', ModBlocks.glass_quartz, 'D', DESH.ingot(), 'N', NB.ingot(), 'C', ModItems.part_carbon);
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_bismuth, 1), "QUQ", "BCB", "QTQ", 'Q', ModBlocks.glass_quartz, 'U', U.ingot(), 'T', TH232.ingot(), 'B', ANY_BISMOID.nugget(), 'C', ModItems.crystal_rare);
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_cmb, 1), "QBQ", "CSC", "QBQ", 'Q', ModBlocks.glass_quartz, 'B', STAR.ingot(), 'C', PB209.nugget(), 'S', XE135.dust());
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_dem, 1), "QDQ", "SBS", "QDQ", 'Q', ModBlocks.glass_quartz, 'D', CMB.ingot(), 'B', ModItems.demon_core_open, 'S', GH336.nugget());
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_bale, 1), "QDQ", "SBS", "QDQ", 'Q', ModBlocks.glass_trinitite, 'D', ModItems.ingot_verticium, 'B', ModItems.rbmk_pellet_balefire, 'S', ModItems.nugget_radspice);
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_digamma, 1), "QUQ", "UEU", "QUQ", 'Q', ModBlocks.glass_ash, 'U', ModItems.undefined, 'E', ModItems.ingot_electronium);


		addRecipeAuto(new ItemStack(ModItems.stamp_stone_flat, 1), " R ", "III", "SSS", 'R', REDSTONE.dust(), 'I', "ingotBrick", 'S', "stone");
		addRecipeAuto(new ItemStack(ModItems.stamp_iron_flat, 1), " R ", "III", "SSS", 'R', REDSTONE.dust(), 'I', "ingotBrick", 'S', IRON.ingot());
		addRecipeAuto(new ItemStack(ModItems.stamp_steel_flat, 1), " R ", "III", "SSS", 'R', REDSTONE.dust(), 'I', "ingotBrick", 'S', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.stamp_titanium_flat, 1), " R ", "III", "SSS", 'R', REDSTONE.dust(), 'I', "ingotBrick", 'S', TI.ingot());
		addRecipeAuto(new ItemStack(ModItems.stamp_obsidian_flat, 1), " R ", "III", "SSS", 'R', REDSTONE.dust(), 'I', "ingotBrick", 'S', Blocks.OBSIDIAN);
		addRecipeAuto(new ItemStack(ModItems.stamp_schrabidium_flat, 1), " R ", "III", "SSS", 'R', REDSTONE.dust(), 'I', "ingotBrick", 'S', SA326.ingot());
		addRecipeAuto(new ItemStack(ModItems.stamp_desh_flat, 1), "RIR", "ISI", "RIR", 'S', ModItems.stamp_schrabidium_flat, 'I', ModItems.plate_desh, 'R', ModBlocks.cmb_brick_reinforced);


		addRecipeAuto(new ItemStack(ModItems.mechanism_revolver_1, 1), " II", "ICA", "IKW", 'I', IRON.plate(), 'C', CU.ingot(), 'A', AL.ingot(), 'K', new ItemStack(ModItems.wire, 1, MAT_COPPER.id), 'W', new ItemStack(ModItems.wire, 1, MAT_ALUMINIUM.id));
		addRecipeAuto(new ItemStack(ModItems.mechanism_revolver_2, 1), " II", "ICA", "IKW", 'I', ALLOY.plate(), 'C', DURA.ingot(), 'A', W.ingot(), 'K', new ItemStack(ModItems.bolt, 1, MAT_DURA.id), 'W', new ItemStack(ModItems.bolt, 1, MAT_STEEL.id));
		addRecipeAuto(new ItemStack(ModItems.mechanism_rifle_1, 1), "ICI", "CMA", "IAM", 'I', IRON.plate(), 'C', CU.ingot(), 'A', AL.ingot(), 'M', ModItems.mechanism_revolver_1);
		addRecipeAuto(new ItemStack(ModItems.mechanism_rifle_2, 1), "ICI", "CMA", "IAM", 'I', ALLOY.plate(), 'C', DURA.ingot(), 'A', W.ingot(), 'M', ModItems.mechanism_revolver_2);
		addRecipeAuto(new ItemStack(ModItems.mechanism_launcher_1, 1), "TTT", "SSS", "BBI", 'T', TI.plate(), 'S', STEEL.ingot(), 'B', new ItemStack(ModItems.bolt, 1, MAT_STEEL.id), 'I', MINGRADE.ingot());
		addRecipeAuto(new ItemStack(ModItems.mechanism_launcher_2, 1), "TTT", "SSS", "BBI", 'T', ALLOY.plate(), 'S', ANY_PLASTIC.ingot(), 'B', new ItemStack(ModItems.bolt, 1, MAT_DURA.id), 'I', DESH.ingot());

		addRecipeAuto(new ItemStack(ModBlocks.fwatz_cooler, 2), "III", "IPI", "III", 'I', SBD.ingot(), 'P', ModItems.thermo_unit_endo);
		addRecipeAuto(new ItemStack(ModBlocks.fwatz_tank, 1), "CGC", "GGG", "CGC", 'C', CMB.plate(), 'G', KEY_ANYPANE);
		addRecipeAuto(new ItemStack(ModBlocks.fwatz_scaffold, 2), "IPI", "P P", "IPI", 'I', W.ingot(), 'P', getReflector());
		addRecipeAuto(new ItemStack(ModBlocks.fwatz_conductor, 2), "IPI", "PFP", "IPI", 'I', TI.plate(), 'P', ModItems.coil_magnetized_tungsten, 'F', ModBlocks.hadron_coil_neodymium);

		addRecipeAuto(new ItemStack(ModBlocks.reinforced_stone, 4), "FBF", "BFB", "FBF", 'F', Blocks.COBBLESTONE, 'B', Blocks.STONE);
		addRecipeAuto(new ItemStack(ModBlocks.brick_fire, 4), "CC", "CC", 'C', ModItems.ingot_firebrick);
		addRecipeAuto(new ItemStack(ModBlocks.brick_light, 4), "FBF", "BFB", "FBF", 'F', "fenceWood", 'B', Blocks.BRICK_BLOCK);
		addRecipeAuto(new ItemStack(ModBlocks.brick_asbestos, 2), " A ", "ABA", " A ", 'B', ModBlocks.brick_light, 'A', ASBESTOS.ingot());

		addRecipeAuto(new ItemStack(ModBlocks.brick_obsidian, 4), "FBF", "BFB", "FBF", 'F', Blocks.IRON_BARS, 'B', Blocks.OBSIDIAN);
		addRecipeAuto(new ItemStack(ModBlocks.meteor_polished, 4), "CC", "CC", 'C', ModBlocks.block_meteor_broken);
		addRecipeAuto(new ItemStack(ModBlocks.meteor_pillar, 2), "C", "C", 'C', ModBlocks.meteor_polished);
		addRecipeAuto(new ItemStack(ModBlocks.meteor_brick, 4), "CC", "CC", 'C', ModBlocks.meteor_polished);
		addRecipeAuto(new ItemStack(ModBlocks.meteor_brick_mossy, 8), "CCC", "CVC", "CCC", 'C', ModBlocks.meteor_brick, 'V', Blocks.VINE);
		addRecipeAuto(new ItemStack(ModBlocks.meteor_brick_cracked, 6), " C ", "C C", " C ", 'C', ModBlocks.meteor_brick);
		addRecipeAuto(new ItemStack(ModBlocks.meteor_battery, 1), "MSM", "MWM", "MSM", 'M', ModBlocks.meteor_polished, 'S', STAR.block(), 'W', new ItemStack(ModItems.wire, 1, MAT_SCHRABIDIUM.id));
		addRecipeAuto(new ItemStack(ModBlocks.tile_lab, 4), "CBC", "CBC", "CBC", 'C', Items.BRICK, 'B', ASBESTOS.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.tile_lab_cracked, 6), " C ", "C C", " C ", 'C', ModBlocks.tile_lab);
		addRecipeAuto(new ItemStack(ModBlocks.tile_lab_broken, 6), " C ", "C C", " C ", 'C', ModBlocks.tile_lab_cracked);

        addRecipeAuto(new ItemStack(ModBlocks.brick_dungeon, 4), "CC", "CC", 'C', ModBlocks.brick_dungeon_flat);
        addRecipeAuto(new ItemStack(ModBlocks.brick_dungeon_tile, 4), "CC", "CC", 'C', ModBlocks.brick_dungeon);
        addRecipeAuto(new ItemStack(ModBlocks.brick_dungeon_circle, 1), "CCC", "C C", "CCC", 'C', ModBlocks.brick_dungeon_tile);


        addRecipeAuto(new ItemStack(ModBlocks.ducrete_smooth, 4), "DD", "DD", 'D', ModBlocks.ducrete);
		addRecipeAuto(new ItemStack(ModBlocks.ducrete_brick, 4), "CDC", "DLD", "CDC", 'D', ModBlocks.ducrete_smooth, 'C', Items.CLAY_BALL, 'L', PB.plate());
		addRecipeAuto(new ItemStack(ModBlocks.ducrete_brick, 4), "CDC", "DLD", "CDC", 'D', ModBlocks.ducrete, 'C', Items.CLAY_BALL, 'L', PB.plate());
		addRecipeAuto(new ItemStack(ModBlocks.ducrete_reinforced, 4), "DSD", "SUS", "DSD", 'D', ModBlocks.ducrete_brick, 'S', STEEL.plate(), 'U', U238.billet());


		

		addRecipeAuto(new ItemStack(ModBlocks.reinforced_brick, 8), "FBF", "BFB", "FBF", 'F', Blocks.IRON_BARS, 'B', ModBlocks.brick_concrete);
		addRecipeAuto(new ItemStack(ModBlocks.brick_compound, 8), "FBF", "BFB", "FBF", 'F', new ItemStack(ModItems.bolt, 1, MAT_STEEL.id), 'B', ModBlocks.reinforced_brick);
		addRecipeAuto(new ItemStack(ModBlocks.reinforced_glass, 8), "FBF", "BFB", "FBF", 'F', Blocks.IRON_BARS, 'B', "blockGlassColorless");
		addRecipeAuto(new ItemStack(ModBlocks.reinforced_light, 1), "FFF", "FBF", "FFF", 'F', Blocks.IRON_BARS, 'B', "glowstone");
		addRecipeAuto(new ItemStack(ModBlocks.reinforced_lamp_off, 1), "FFF", "FBF", "FFF", 'F', Blocks.IRON_BARS, 'B', Blocks.REDSTONE_LAMP);
		addRecipeAuto(new ItemStack(ModBlocks.reinforced_sand, 8), "FBF", "BFB", "FBF", 'F', Blocks.IRON_BARS, 'B', Blocks.SANDSTONE);

		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire, 16), "AIA", "I I", "AIA", 'A', new ItemStack(ModItems.wire, 1, MAT_STEEL.id), 'I', IRON.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_fire, 8), "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I',P_RED.dust());
		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_poison, 8), "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', ModItems.powder_poison);
		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_acid, 8), "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', new IngredientNBT2(ItemFluidTank.getFullTank(ModForgeFluids.ACID)));
		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_wither, 8), "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', new ItemStack(Items.SKULL, 1, 1));
		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_ultradeath, 4), "BCB", "CIC", "BCB", 'B', ModBlocks.barbed_wire, 'C', ModItems.powder_cloud, 'I', ModItems.nuclear_waste);

		addRecipeAuto(new ItemStack(ModBlocks.tape_recorder, 4), "TST", "SSS", 'T', W.ingot(), 'S', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.steel_poles, 16), "S S", "SSS", "S S", 'S', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.pole_top, 1), "T T", "TRT", "BBB", 'T', W.ingot(), 'B', BE.ingot(), 'R', MINGRADE.ingot());
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.pole_satellite_receiver), 1), "SS ", "SCR", "SS ", 'S', STEEL.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), 'R', MINGRADE.wire());
		addRecipeAuto(new ItemStack(ModBlocks.steel_beam, 8), "S", "S", "S", 'S', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.steel_wall, 4), "SSS", "SSS", 'S', STEEL.ingot());
		addShapelessAuto(new ItemStack(ModBlocks.steel_corner), Item.getItemFromBlock(ModBlocks.steel_wall), Item.getItemFromBlock(ModBlocks.steel_wall));
		addRecipeAuto(new ItemStack(ModBlocks.steel_roof, 2), "SSS", 'S', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.steel_scaffold, 8), "SSS", " S ", "SSS", 'S', STEEL.ingot());

		reg2();
	}

	public static void reg2(){


		addRecipeAuto(new ItemStack(ModItems.stamp_357, 1), "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_357);
		addRecipeAuto(new ItemStack(ModItems.stamp_44, 1), "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_44);
		addRecipeAuto(new ItemStack(ModItems.stamp_9, 1), "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_9);
		addRecipeAuto(new ItemStack(ModItems.stamp_50, 1), "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_50);

		addRecipeAuto(new ItemStack(ModItems.stamp_desh_357, 1), "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_desh_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_357);
		addRecipeAuto(new ItemStack(ModItems.stamp_desh_44, 1), "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_desh_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_44);
		addRecipeAuto(new ItemStack(ModItems.stamp_desh_9, 1), "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_desh_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_9);
		addRecipeAuto(new ItemStack(ModItems.stamp_desh_50, 1), "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_desh_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_50);

		addRecipeAuto(new ItemStack(ModItems.primer_357, 1), "R", "P", 'P', IRON.plate(), 'R', REDSTONE.dust());
		addRecipeAuto(new ItemStack(ModItems.primer_44, 1), "P", "R", 'P', IRON.plate(), 'R', REDSTONE.dust());
		addRecipeAuto(new ItemStack(ModItems.primer_9, 1), "R", "P", 'P', AL.plate(), 'R', REDSTONE.dust());
		addRecipeAuto(new ItemStack(ModItems.primer_50, 1), "P", "R", 'P', AL.plate(), 'R', REDSTONE.dust());
		addRecipeAuto(new ItemStack(ModItems.primer_buckshot, 1), "R", "P", 'P', CU.plate(), 'R', REDSTONE.dust());


		addRecipeAuto(new ItemStack(ModItems.grenade_generic, 4), "RS ", "ITI", " I ", 'I', IRON.plate(), 'R', new ItemStack(ModItems.wire, 1, MAT_MINGRADE.id), 'S', STEEL.plate(), 'T', Item.getItemFromBlock(Blocks.TNT));
		addRecipeAuto(new ItemStack(ModItems.grenade_strong, 2), " G ", "SGS", " S ", 'G', ModItems.grenade_generic, 'S', Items.GUNPOWDER);
		addRecipeAuto(new ItemStack(ModItems.grenade_frag, 2), " G ", "WGW", " K ", 'G', ModItems.grenade_generic, 'W', Item.getItemFromBlock(Blocks.PLANKS), 'K', Item.getItemFromBlock(Blocks.GRAVEL));
		addRecipeAuto(new ItemStack(ModItems.grenade_poison, 2), " G ", "PGP", " P ", 'G', ModItems.grenade_generic, 'P', ModItems.powder_poison);
		addRecipeAuto(new ItemStack(ModItems.grenade_gas, 2), " G ", "CGC", " C ", 'G', ModItems.grenade_generic, 'C', ModItems.pellet_gas);
		addRecipeAuto(new ItemStack(ModItems.grenade_aschrab, 1), "RS ", "ITI", " S ", 'I', "paneGlassColorless", 'R', new ItemStack(ModItems.wire, 1, MAT_MINGRADE.id), 'S', STEEL.plate(), 'T', new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.ASCHRAB)));
		addRecipeAuto(new ItemStack(ModItems.grenade_mk2, 2), " G ", "SGS", " S ", 'G', ModItems.grenade_strong, 'S', Items.GUNPOWDER);
		addShapelessAuto(new ItemStack(ModItems.grenade_gascan, 1), new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.DIESEL)), Items.FLINT);
		addShapelessAuto(new ItemStack(ModItems.grenade_gascan, 1), new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.BIOFUEL)), Items.FLINT);
		addShapelessAuto(new ItemStack(ModItems.grenade_gascan, 1), new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.PETROIL)), Items.FLINT);
		addShapelessAuto(new ItemStack(ModItems.grenade_gascan, 1), new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.KEROSENE)), Items.FLINT);
		addShapelessAuto(new ItemStack(ModItems.grenade_lemon, 1), ModItems.lemon, ModItems.grenade_strong);
		addShapelessAuto(new ItemStack(ModItems.gun_moist_nugget, 12), Items.BREAD, Items.WHEAT, Items.COOKED_CHICKEN, Items.EGG);
		addRecipeAuto(new ItemStack(ModItems.grenade_smart, 4), " A ", "ACA", " A ", 'A', ModItems.grenade_strong, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP));
		addRecipeAuto(new ItemStack(ModItems.grenade_mirv, 1), "GGG", "GCG", "GGG", 'G', ModItems.grenade_smart, 'C', ModItems.grenade_generic);
		addRecipeAuto(new ItemStack(ModItems.grenade_breach, 1), "G", "G", "P", 'G', ModItems.grenade_smart, 'P', BIGMT.plate());
		addRecipeAuto(new ItemStack(ModItems.grenade_burst, 1), "GGG", "GCG", "GGG", 'G', ModItems.grenade_breach, 'C', ModItems.grenade_generic);

		addRecipeAuto(new ItemStack(ModItems.grenade_if_generic, 1), " C ", "PTP", " P ", 'C', ModItems.coil_tungsten, 'P', STEEL.plate(), 'T', Blocks.TNT);
		addRecipeAuto(new ItemStack(ModItems.grenade_if_he, 1), "A", "G", "A", 'G', ModItems.grenade_if_generic, 'A', Items.GUNPOWDER);
		addRecipeAuto(new ItemStack(ModItems.grenade_if_bouncy, 1), "G", "A", 'G', ModItems.grenade_if_generic, 'A', ANY_RUBBER.ingot());
		addRecipeAuto(new ItemStack(ModItems.grenade_if_sticky, 1), "G", "A", 'G', ModItems.grenade_if_generic, 'A', Items.SLIME_BALL);
		addRecipeAuto(new ItemStack(ModItems.grenade_if_impact, 1), "G", "A", 'G', ModItems.grenade_if_generic, 'A', Items.REDSTONE);
		addRecipeAuto(new ItemStack(ModItems.grenade_if_concussion, 1), "G", "A", 'G', ModItems.grenade_if_generic, 'A', Items.GLOWSTONE_DUST);
		addRecipeAuto(new ItemStack(ModItems.grenade_if_toxic, 1), "G", "A", 'G', ModItems.grenade_if_generic, 'A', ModItems.powder_poison);
		addRecipeAuto(new ItemStack(ModItems.grenade_if_incendiary, 1), "G", "A", 'G', ModItems.grenade_if_generic, 'A',P_RED.dust());
		addRecipeAuto(new ItemStack(ModItems.grenade_if_brimstone, 1), "R", "G", "A", 'G', ModItems.grenade_if_generic, 'R', REDSTONE.dust(), 'A', ModItems.powder_power);
		addRecipeAuto(new ItemStack(ModItems.grenade_if_mystery, 1), " A ", "BGB", " A ", 'G', ModItems.grenade_if_generic, 'A', ModItems.powder_magic, 'B', ModItems.powder_cloud);
		addShapelessAuto(new ItemStack(ModItems.grenade_if_hopwire, 1), ModItems.grenade_if_generic, ModItems.singularity_counter_resonant);
		addShapelessAuto(new ItemStack(ModItems.grenade_if_spark, 1), ModItems.grenade_if_generic, ModItems.singularity_spark);
		addShapelessAuto(new ItemStack(ModItems.grenade_if_null, 1), ModItems.grenade_if_generic, ModItems.undefined);

		addRecipeAuto(new ItemStack(ModItems.bomb_waffle, 1), "WEW", "MPM", "WEW", 'W', Items.WHEAT, 'E', Items.EGG, 'M', Items.MILK_BUCKET, 'P', ModItems.demon_core_open);
		addRecipeAuto(new ItemStack(ModItems.schnitzel_vegan, 3), "RWR", "WPW", "RWR", 'W', ModItems.nuclear_waste, 'R', Items.REEDS, 'P', Items.PUMPKIN_SEEDS);
		addRecipeAuto(new ItemStack(ModItems.cotton_candy, 2), " S ", "SPS", " H ", 'P', PU239.nugget(), 'S', Items.SUGAR, 'H', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.apple_schrabidium, 1, 0), "SSS", "SAS", "SSS", 'S', SA326.nugget(), 'A', Items.APPLE);
		addRecipeAuto(new ItemStack(ModItems.apple_schrabidium1, 1, 0), "SSS", "SAS", "SSS", 'S', SA326.ingot(), 'A', Items.APPLE);
		addRecipeAuto(new ItemStack(ModItems.apple_schrabidium2, 1, 0), "SSS", "SAS", "SSS", 'S', SA326.block(), 'A', Items.APPLE);
		addRecipeAuto(new ItemStack(ModItems.apple_lead, 1, 0), "SSS", "SAS", "SSS", 'S', PB.nugget(), 'A', Items.APPLE);
		addRecipeAuto(new ItemStack(ModItems.apple_lead1, 1, 0), "SSS", "SAS", "SSS", 'S', PB.ingot(), 'A', Items.APPLE);
		addRecipeAuto(new ItemStack(ModItems.apple_lead2, 1, 0), "SSS", "SAS", "SSS", 'S', PB.block(), 'A', Items.APPLE);
		addShapelessAuto(new ItemStack(ModItems.tem_flakes, 1, 0), GOLD.nugget(), Items.PAPER);
		addShapelessAuto(new ItemStack(ModItems.tem_flakes1, 1, 0), GOLD.nugget(), GOLD.nugget(), GOLD.nugget(), Items.PAPER);
		addShapelessAuto(new ItemStack(ModItems.tem_flakes2, 1, 0), Items.GOLD_INGOT, Items.GOLD_INGOT, GOLD.nugget(), GOLD.nugget(), Items.PAPER);
		addShapelessAuto(new ItemStack(ModItems.glowing_stew, 1), Items.BOWL, Item.getItemFromBlock(ModBlocks.mush), Item.getItemFromBlock(ModBlocks.mush));
		addShapelessAuto(new ItemStack(ModItems.balefire_scrambled, 1), Items.BOWL, ModItems.egg_balefire, ModItems.powder_radspice);
		addShapelessAuto(new ItemStack(ModItems.balefire_and_ham, 1), ModItems.balefire_scrambled, Items.COOKED_BEEF, ModItems.powder_radspice);
		addShapelessAuto(new ItemStack(ModItems.med_ipecac, 1), Items.GLASS_BOTTLE, Items.NETHER_WART);
		addShapelessAuto(new ItemStack(ModItems.med_ptsd, 1), ModItems.med_ipecac);
		addShapelessAuto(new ItemStack(ModItems.pancake, 1), REDSTONE.dust(), DIAMOND.dust(), Items.WHEAT, new ItemStack(ModItems.bolt, 1, MAT_STEEL.id), new ItemStack(ModItems.wire, 1, MAT_COPPER.id), STEEL.plate());
		addShapelessAuto(new ItemStack(ModItems.pancake, 1), REDSTONE.dust(), EMERALD.dust(), Items.WHEAT, new ItemStack(ModItems.bolt, 1, MAT_STEEL.id), new ItemStack(ModItems.wire, 1, MAT_COPPER.id), STEEL.plate());

		addRecipeAuto(new ItemStack(ModItems.can_empty, 1), "P", "P", 'P', AL.plate());
		addShapelessAuto(new ItemStack(ModItems.can_smart, 1), ModItems.can_empty, Items.POTIONITEM, Items.SUGAR, KNO.dust());
		addShapelessAuto(new ItemStack(ModItems.can_creature, 1), ModItems.can_empty, Items.POTIONITEM, Items.SUGAR, new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.DIESEL)));
		addShapelessAuto(new ItemStack(ModItems.can_redbomb, 1), ModItems.can_empty, Items.POTIONITEM, Items.SUGAR, ModItems.pellet_cluster);
		addShapelessAuto(new ItemStack(ModItems.can_mrsugar, 1), ModItems.can_empty, Items.POTIONITEM, Items.SUGAR, F.dust());
		addShapelessAuto(new ItemStack(ModItems.can_overcharge, 1), ModItems.can_empty, Items.POTIONITEM, Items.SUGAR, S.dust());
		addShapelessAuto(new ItemStack(ModItems.can_luna, 1), ModItems.can_empty, Items.POTIONITEM, Items.SUGAR, ModItems.powder_meteorite_tiny);

		addRecipeAuto(new ItemStack(ModItems.canteen_13, 1), "O", "P", 'O', Items.POTIONITEM, 'P', STEEL.plate());
		addRecipeAuto(new ItemStack(ModItems.canteen_vodka, 1), "O", "P", 'O', Items.POTATO, 'P', STEEL.plate());

		addRecipeAuto(new ItemStack(ModItems.bottle_empty, 6), " G ", "G G", "GGG", 'G', KEY_ANYPANE);
		addShapelessAuto(new ItemStack(ModItems.bottle_nuka, 1), ModItems.bottle_empty, Items.POTIONITEM, Items.SUGAR, COAL.dust());
		addShapelessAuto(new ItemStack(ModItems.bottle_cherry, 1), ModItems.bottle_empty, Items.POTIONITEM, Items.SUGAR, Items.REDSTONE);
		addShapelessAuto(new ItemStack(ModItems.bottle_quantum, 1), ModItems.bottle_empty, Items.POTIONITEM, Items.SUGAR, ModItems.trinitite);
		addRecipeAuto(new ItemStack(ModItems.bottle2_empty, 6), " G ", "G G", "G G", 'G', KEY_ANYPANE);
		addShapelessAuto(new ItemStack(ModItems.bottle2_korl, 1), ModItems.bottle2_empty, Items.POTIONITEM, Items.SUGAR, CU.dust());
		addShapelessAuto(new ItemStack(ModItems.bottle2_fritz, 1), ModItems.bottle2_empty, Items.POTIONITEM, Items.SUGAR, W.dust());
		addShapelessAuto(new ItemStack(ModItems.bottle2_korl_special, 1), ModItems.bottle2_empty, Items.POTIONITEM, Items.SUGAR, CU.dust(), SR.dust());
		addShapelessAuto(new ItemStack(ModItems.bottle2_fritz_special, 1), ModItems.bottle2_empty, Items.POTIONITEM, Items.SUGAR, W.dust(), TH232.dust());
		addShapelessAuto(new ItemStack(ModItems.bottle2_sunset, 1), ModItems.bottle2_empty, Items.POTIONITEM, Items.SUGAR, GOLD.dust());

		addRecipeAuto(new ItemStack(ModItems.syringe_empty, 6), "P", "C", "B", 'B', Item.getItemFromBlock(Blocks.IRON_BARS), 'C', new IngredientNBT2(new ItemStack(ModItems.cell)), 'P', IRON.plate());
		addRecipeAuto(new ItemStack(ModItems.syringe_antidote, 6), "SSS", "PMP", "SSS", 'S', ModItems.syringe_empty, 'P', Items.PUMPKIN_SEEDS, 'M', Items.MILK_BUCKET);
		addRecipeAuto(new ItemStack(ModItems.syringe_antidote, 6), "SPS", "SMS", "SPS", 'S', ModItems.syringe_empty, 'P', Items.PUMPKIN_SEEDS, 'M', Items.MILK_BUCKET);
		addRecipeAuto(new ItemStack(ModItems.syringe_antidote, 6), "SSS", "PMP", "SSS", 'S', ModItems.syringe_empty, 'P', Items.PUMPKIN_SEEDS, 'M', Items.REEDS);
		addRecipeAuto(new ItemStack(ModItems.syringe_antidote, 6), "SPS", "SMS", "SPS", 'S', ModItems.syringe_empty, 'P', Items.PUMPKIN_SEEDS, 'M', Items.REEDS);
		addRecipeAuto(new ItemStack(ModItems.syringe_poison, 1), "SLS", "LCL", "SLS", 'C', ModItems.syringe_empty, 'S', Items.SPIDER_EYE, 'L', PB.dust());
		addRecipeAuto(new ItemStack(ModItems.syringe_poison, 1), "SLS", "LCL", "SLS", 'C', ModItems.syringe_empty, 'S', Items.SPIDER_EYE, 'L', ModItems.powder_poison);
		addRecipeAuto(new ItemStack(ModItems.syringe_awesome, 1), "SPS", "NCN", "SPS", 'C', ModItems.syringe_empty, 'S', S.dust(), 'P', PU239.nugget(), 'N', PU238.nugget());
		addRecipeAuto(new ItemStack(ModItems.syringe_awesome, 1), "SNS", "PCP", "SNS", 'C', ModItems.syringe_empty, 'S', S.dust(), 'P', PU239.nugget(), 'N', PU238.nugget());
		addRecipeAuto(new ItemStack(ModItems.syringe_metal_empty, 6), "P", "C", "B", 'B', Item.getItemFromBlock(Blocks.IRON_BARS), 'C', ModItems.rod_empty, 'P', IRON.plate());
		addRecipeAuto(new ItemStack(ModItems.syringe_metal_stimpak, 1), " N ", "NSN", " N ", 'N', Items.NETHER_WART, 'S', ModItems.syringe_metal_empty);
		addRecipeAuto(new ItemStack(ModItems.syringe_metal_medx, 1), " N ", "NSN", " N ", 'N', Items.QUARTZ, 'S', ModItems.syringe_metal_empty);
		addRecipeAuto(new ItemStack(ModItems.syringe_metal_psycho, 1), " N ", "NSN", " N ", 'N', Items.GLOWSTONE_DUST, 'S', ModItems.syringe_metal_empty);
		addRecipeAuto(new ItemStack(ModItems.pill_iodine, 8), "IF", 'I', I.dust(), 'F', F.dust());
		addRecipeAuto(new ItemStack(ModItems.plan_c, 1), "PFP", 'P', ModItems.powder_poison, 'F', F.dust());
		addShapelessAuto(new ItemStack(ModItems.radx, 1), COAL.dust(), COAL.dust(), F.dust());
		addRecipeAuto(new ItemStack(ModItems.med_bag, 1), "LLL", "SIS", "LLL", 'L', Items.LEATHER, 'S', ModItems.syringe_metal_stimpak, 'I', ModItems.syringe_antidote);
		addRecipeAuto(new ItemStack(ModItems.med_bag, 1), "LLL", "SIS", "LLL", 'L', Items.LEATHER, 'S', ModItems.syringe_metal_stimpak, 'I', ModItems.pill_iodine);
		addRecipeAuto(new ItemStack(ModItems.med_bag, 1), "LL", "SI", "LL", 'L', Items.LEATHER, 'S', ModItems.syringe_metal_super, 'I', ModItems.radaway);
		addRecipeAuto(new ItemStack(ModItems.med_bag, 1), "LLL", "SIS", "LLL", 'L', ANY_RUBBER.ingot(), 'S', ModItems.syringe_metal_stimpak, 'I', ModItems.syringe_antidote);
		addRecipeAuto(new ItemStack(ModItems.med_bag, 1), "LLL", "SIS", "LLL", 'L', ANY_RUBBER.ingot(), 'S', ModItems.syringe_metal_stimpak, 'I', ModItems.pill_iodine);
		addRecipeAuto(new ItemStack(ModItems.med_bag, 1), "LL", "SI", "LL", 'L', ANY_RUBBER.ingot(), 'S', ModItems.syringe_metal_super, 'I', ModItems.radaway);
		addRecipeAuto(new ItemStack(ModItems.syringe_metal_super, 1), " N ", "PSP", "L L", 'N', ModItems.bottle_nuka, 'P', STEEL.plate(), 'S', ModItems.syringe_metal_stimpak, 'L', Items.LEATHER);
		addRecipeAuto(new ItemStack(ModItems.syringe_metal_super, 1), " N ", "PSP", "L L", 'N', ModItems.bottle_nuka, 'P', STEEL.plate(), 'S', ModItems.syringe_metal_stimpak, 'L', ANY_RUBBER.ingot());
		addRecipeAuto(new ItemStack(ModItems.syringe_metal_super, 1), " N ", "PSP", "L L", 'N', ModItems.bottle_cherry, 'P', STEEL.plate(), 'S', ModItems.syringe_metal_stimpak, 'L', Items.LEATHER);
		addRecipeAuto(new ItemStack(ModItems.syringe_metal_super, 1), " N ", "PSP", "L L", 'N', ModItems.bottle_cherry, 'P', STEEL.plate(), 'S', ModItems.syringe_metal_stimpak, 'L', ANY_RUBBER.ingot());
		addRecipeAuto(new ItemStack(ModItems.iv_empty, 4), "A", "B", "A", 'A', ANY_RUBBER.ingot(), 'B', IRON.plate());
		addShapelessAuto(new ItemStack(ModItems.iv_xp_empty, 1), ModItems.iv_empty, ModItems.powder_magic);
		addShapelessAuto(new ItemStack(ModItems.radaway, 1), ModItems.iv_blood, COAL.dust(), Items.PUMPKIN_SEEDS);
		addShapelessAuto(new ItemStack(ModItems.radaway_strong, 1), ModItems.radaway, ModBlocks.mush);
		addShapelessAuto(new ItemStack(ModItems.radaway_flush, 1), ModItems.radaway_strong, I.dust());

		addRecipeAuto(new ItemStack(ModItems.stealth_boy, 1), " B", "LI", "LC", 'B', Item.getItemFromBlock(Blocks.STONE_BUTTON), 'L', Items.LEATHER, 'I', STEEL.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC));

		addRecipeAuto(new ItemStack(ModBlocks.sat_dock, 1), "SSS", "PCP", 'S', STEEL.ingot(), 'P', ANY_PLASTIC.ingot(), 'C', ModBlocks.crate_iron);
		addRecipeAuto(new ItemStack(ModBlocks.book_guide, 1), "IBI", "LBL", "IBI", 'B', Items.BOOK, 'I', new ItemStack(Items.DYE, 1, 0), 'L', new ItemStack(Items.DYE, 1, 4));

		addRecipeAuto(new ItemStack(ModItems.book_guide, 1, 1), "G", "B", "C", 'B', Items.BOOK, 'G', ModItems.rbmk_lid_glass, 'C', ModItems.rbmk_lid);
		addShapelessAuto(new ItemStack(ModItems.book_guide, 1, 2), Items.BOOK, ModItems.powder_meteorite);
		addShapelessAuto(new ItemStack(ModItems.book_guide, 1, 3), Items.BOOK, ModItems.fuse);

		addRecipeAuto(new ItemStack(ModBlocks.rail_highspeed, 16), "S S", "SIS", "S S", 'S', STEEL.ingot(), 'I', IRON.plate());
		addRecipeAuto(new ItemStack(ModBlocks.rail_booster, 6), "S S", "CIC", "SRS", 'S', STEEL.ingot(), 'I', IRON.plate(), 'R', MINGRADE.ingot(), 'C', ModItems.coil_copper);

		addRecipeAuto(new ItemStack(ModBlocks.bomb_multi, 1), "AAD", "CHF", "AAD", 'A', new ItemStack(ModItems.wire, 1, MAT_ALUMINIUM.id), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'H', ModItems.hull_small_aluminium, 'F', ModItems.fins_quad_titanium, 'D', new ItemStack(Items.DYE, 1, 15));
		addShapelessAuto(new ItemStack(ModItems.pill_herbal), COAL.dust(), Items.POISONOUS_POTATO, Items.NETHER_WART, Items.BEETROOT);
		addShapelessAuto(new ItemStack(ModItems.pellet_gas, 2), Items.WATER_BUCKET, "dustGlowstone", STEEL.plate(), ModItems.ingot_iodine);

		addRecipeAuto(new ItemStack(ModItems.flame_pony, 1), " O ", "DPD", " O ", 'D', new ItemStack(Items.DYE, 1, 11), 'O', new ItemStack(Items.DYE, 1, 9), 'P', Items.PAPER);
		addRecipeAuto(new ItemStack(ModItems.flame_conspiracy, 1), " S ", "STS", " S ", 'S', new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.DIESEL)), 'T', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.flame_politics, 1), " I ", "IPI", " I ", 'P', Items.PAPER, 'I', new ItemStack(Items.DYE, 1, 0));
		addRecipeAuto(new ItemStack(ModItems.flame_opinion, 1), " R ", "RPR", " R ", 'P', Items.PAPER, 'R', new ItemStack(Items.DYE, 1, 1));

		addRecipeAuto(new ItemStack(ModItems.solid_fuel_presto, 1), " P ", "SRS", " P ", 'P', Items.PAPER, 'S', ModItems.solid_fuel, 'R', REDSTONE.dust());
		addShapelessAuto(new ItemStack(ModItems.solid_fuel_presto_triplet, 1), ModItems.solid_fuel_presto, ModItems.solid_fuel_presto, ModItems.solid_fuel_presto, ModItems.ball_dynamite);

		addRecipeAuto(new ItemStack(ModItems.solid_fuel_presto_bf, 1), " P ", "SRS", " P ", 'P', Items.PAPER, 'S', ModItems.solid_fuel_bf, 'R', REDSTONE.dust());
		addShapelessAuto(new ItemStack(ModItems.solid_fuel_presto_triplet_bf, 1), ModItems.solid_fuel_presto_bf, ModItems.solid_fuel_presto_bf, ModItems.solid_fuel_presto_bf, ModItems.ball_dynamite);

		addRecipeAuto(new ItemStack(ModBlocks.flame_war, 1), "WHW", "CTP", "WOW", 'W', Item.getItemFromBlock(Blocks.PLANKS), 'T', Item.getItemFromBlock(Blocks.TNT), 'H', ModItems.flame_pony, 'C', ModItems.flame_conspiracy, 'P', ModItems.flame_politics, 'O', ModItems.flame_opinion);
		addRecipeAuto(new ItemStack(ModBlocks.emp_bomb, 1), "LML", "LCL", "LML", 'L', PB.plate(), 'M', ModItems.magnetron, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED));

		addRecipeAuto(new ItemStack(ModItems.gadget_explosive8, 1), "EEE", "EPE", "EEE", 'E', ModItems.gadget_explosive, 'P', AL.plate());

		addRecipeAuto(new ItemStack(ModItems.man_explosive8, 1), "EEE", "ESE", "EEE", 'E', ModItems.man_explosive, 'S', STEEL.plate());

		addRecipeAuto(new ItemStack(ModItems.n2_charge, 1), " D ", "ERE", " D ", 'D', ModItems.ducttape, 'E', ModBlocks.det_charge, 'R', REDSTONE.block());

		addRecipeAuto(new ItemStack(ModItems.custom_tnt, 1), " C ", "TIT", "TIT", 'C', CU.plate(), 'I', IRON.plate(), 'T', ANY_HIGHEXPLOSIVE.ingot());
		addRecipeAuto(new ItemStack(ModItems.custom_nuke, 1), " C ", "LUL", "LUL", 'C', CU.plate(), 'L', PB.plate(), 'U', U235.ingot());
		addRecipeAuto(new ItemStack(ModItems.custom_hydro, 1), " C ", "LTL", "LIL", 'C', CU.plate(), 'L', PB.plate(), 'I', IRON.plate(), 'T', new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.TRITIUM)));
		addRecipeAuto(new ItemStack(ModItems.custom_amat, 1), " C ", "MMM", "AAA", 'C', CU.plate(), 'A', AL.plate(), 'M', new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.AMAT)));
		addRecipeAuto(new ItemStack(ModItems.custom_dirty, 1), " C ", "WLW", "WLW", 'C', CU.plate(), 'L', PB.plate(), 'W', ModItems.nuclear_waste);
		addRecipeAuto(new ItemStack(ModItems.custom_schrab, 1), " C ", "LUL", "LUL", 'C', CU.plate(), 'L', PB.plate(), 'U', SA326.ingot());
		addRecipeAuto(new ItemStack(ModItems.custom_sol, 1), " C ", "LUL", "LUL", 'C', CU.plate(), 'L', PB.plate(), 'U', SA327.ingot());
		addRecipeAuto(new ItemStack(ModItems.custom_euph, 1), " C ", "LUL", "LUL", 'C', CU.plate(), 'L', PB.plate(), 'U', EUPH.ingot());

		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_generic), " A ", "PRP", "PRP", 'A', new ItemStack(ModItems.wire, 1, MAT_ALUMINIUM.id), 'P', AL.plate(), 'R', REDSTONE.dust());
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced), " A ", "PSP", "PLP", 'A', new ItemStack(ModItems.wire, 1, MAT_MINGRADE.id), 'P', CU.plate(), 'S', S.dust(), 'L', PB.dust());
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced), " A ", "PLP", "PSP", 'A', new ItemStack(ModItems.wire, 1, MAT_MINGRADE.id), 'P', CU.plate(), 'S', S.dust(), 'L', PB.dust());
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium), "A A", "PSP", "PLP", 'A', new ItemStack(ModItems.wire, 1, MAT_GOLD.id), 'P', TI.plate(), 'S', LI.dust(), 'L', CO.dust());
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium), "A A", "PLP", "PSP", 'A', new ItemStack(ModItems.wire, 1, MAT_GOLD.id), 'P', TI.plate(), 'S', LI.dust(), 'L', CO.dust());
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium), " A ", "PNP", "PSP", 'A', new ItemStack(ModItems.wire, 1, MAT_SCHRABIDIUM.id), 'P', SA326.plate(), 'S', SA326.dust(), 'N', NP237.dust());
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium), " A ", "PSP", "PNP", 'A', new ItemStack(ModItems.wire, 1, MAT_SCHRABIDIUM.id), 'P', SA326.plate(), 'S', SA326.dust(), 'N', NP237.dust());
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_trixite), " A ", "PSP", "PTP", 'A', new ItemStack(ModItems.wire, 1, MAT_ALUMINIUM.id), 'P', AL.plate(), 'S', ModItems.powder_power, 'T', ModItems.crystal_trixite);
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_trixite), " A ", "PTP", "PSP", 'A', new ItemStack(ModItems.wire, 1, MAT_ALUMINIUM.id), 'P', AL.plate(), 'S', ModItems.powder_power, 'T', ModItems.crystal_trixite);
		addRecipeAuto(ItemBattery.getFullBattery(ModItems.energy_core), "PCW", "TRD", "PCW", 'P', ALLOY.plate(), 'C', ModItems.coil_advanced_alloy, 'W', new ItemStack(ModItems.wire, 1, MAT_ALLOY.id), 'R', new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.TRITIUM)), 'D', new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.DEUTERIUM)), 'T', W.ingot());
		addRecipeAuto(ItemBattery.getFullBattery(ModItems.energy_core), "PCW", "TDR", "PCW", 'P', ALLOY.plate(), 'C', ModItems.coil_advanced_alloy, 'W', new ItemStack(ModItems.wire, 1, MAT_ALLOY.id), 'R', new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.TRITIUM)), 'D', new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.DEUTERIUM)), 'T', W.ingot());

		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_red_cell), "WBW", "PBP", "WBW", 'W', new ItemStack(ModItems.wire, 1, MAT_ALUMINIUM.id), 'P', AL.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_generic));
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell), "WBW", "PBP", "WBW", 'W', new ItemStack(ModItems.wire, 1, MAT_MINGRADE.id), 'P', CU.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced));
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell), "WBW", "PBP", "WBW", 'W', new ItemStack(ModItems.wire, 1, MAT_GOLD.id), 'P', TI.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium));
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell), "WBW", "PBP", "WBW", 'W', new ItemStack(ModItems.wire, 1, MAT_SCHRABIDIUM.id), 'P', SA326.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium));
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_red_cell_6), "BBB", "WPW", "BBB", 'W', new ItemStack(ModItems.wire, 1, MAT_ALUMINIUM.id), 'P', AL.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_red_cell));
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_4), "BWB", "WPW", "BWB", 'W', new ItemStack(ModItems.wire, 1, MAT_MINGRADE.id), 'P', CU.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell));
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_3), "WPW", "BBB", "WPW", 'W', new ItemStack(ModItems.wire, 1, MAT_GOLD.id), 'P', TI.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell));
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_2), "WPW", "BWB", "WPW", 'W', new ItemStack(ModItems.wire, 1, MAT_SCHRABIDIUM.id), 'P', SA326.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell));
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_red_cell_24), "BWB", "WPW", "BWB", 'W', new ItemStack(ModItems.wire, 1, MAT_ALUMINIUM.id), 'P', AL.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_red_cell_6));
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_12), "WPW", "BBB", "WPW", 'W', new ItemStack(ModItems.wire, 1, MAT_MINGRADE.id), 'P', CU.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_4));
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_6), "WPW", "BWB", "WPW", 'W', new ItemStack(ModItems.wire, 1, MAT_GOLD.id), 'P', TI.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_3));
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_4), "WPW", "BWB", "WPW", 'W', new ItemStack(ModItems.wire, 1, MAT_SCHRABIDIUM.id), 'P', SA326.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_2));
		
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark), "P", "S", "S", 'P', ModItems.plate_dineutronium, 'S', ModItems.powder_spark_mix);
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_6), "BW", "PW", "BW", 'W', new ItemStack(ModItems.wire, 1, MAT_MAGTUNG.id), 'P', ModItems.powder_spark_mix, 'B', ItemBattery.getEmptyBattery(ModItems.battery_spark));
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_25), "W W", "SCS", "PSP", 'W', new ItemStack(ModItems.wire, 1, MAT_MAGTUNG.id), 'P', ModItems.plate_dineutronium, 'S', ModItems.powder_spark_mix, 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_6));
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_100), "W W", "BPB", "SSS", 'W', new ItemStack(ModItems.wire, 1, MAT_MAGTUNG.id), 'P', ModItems.plate_dineutronium, 'S', ModItems.powder_spark_mix, 'B', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_25));
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_1000), "PCP", "CSC", "PCP", 'S', ModItems.singularity_spark, 'P', ModItems.powder_spark_mix, 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_100));
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_2500), "SCS", "CVC", "SCS", 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_100), 'V', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_1000), 'S', ModItems.powder_spark_mix);
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_10000), "OPO", "VSV", "OPO", 'S', ModItems.singularity_spark, 'V', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_2500), 'O', ModItems.ingot_osmiridium, 'P', ModItems.plate_dineutronium);
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_power), "YPY", "CSC", "YPY", 'S', ModItems.singularity_spark, 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_10000), 'Y', ModItems.billet_yharonite, 'P', ModItems.plate_dineutronium);

		addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su), "P", "R", "C", 'P', Items.PAPER, 'R', REDSTONE.dust(), 'C', COAL.dust());
		addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su), "P", "C", "R", 'P', Items.PAPER, 'R', REDSTONE.dust(), 'C', COAL.dust());
		addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su_l), " W ", "CPC", "RPR", 'W', new ItemStack(ModItems.wire, 1, MAT_ALUMINIUM.id), 'P', Items.PAPER, 'R', REDSTONE.dust(), 'C', COAL.dust());
		addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su_l), " W ", "RPR", "CPC", 'W', new ItemStack(ModItems.wire, 1, MAT_ALUMINIUM.id), 'P', Items.PAPER, 'R', REDSTONE.dust(), 'C', COAL.dust());
		addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su_l), " W ", "CPC", "RPR", 'W', new ItemStack(ModItems.wire, 1, MAT_COPPER.id), 'P', Items.PAPER, 'R', REDSTONE.dust(), 'C', COAL.dust());
		addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su_l), " W ", "RPR", "CPC", 'W', new ItemStack(ModItems.wire, 1, MAT_COPPER.id), 'P', Items.PAPER, 'R', REDSTONE.dust(), 'C', COAL.dust());
		addShapelessAuto(ItemBattery.getFullBattery(ModItems.battery_potato), Items.POTATO, new ItemStack(ModItems.wire, 1, MAT_ALUMINIUM.id), new ItemStack(ModItems.wire, 1, MAT_COPPER.id));
		addShapelessAuto(ItemBattery.getFullBattery(ModItems.battery_potatos), ItemBattery.getFullBattery(ModItems.battery_potato), ModItems.turret_chip, REDSTONE.dust());
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_steam), "PMP", "ISI", "PCP", 'P', CU.plate(), 'M', ModItems.motor, 'C', ModItems.coil_tungsten, 'S', new IngredientNBT2(ItemFluidTank.getFullTank(FluidRegistry.WATER)), 'I', ANY_RUBBER.ingot());
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_steam_large), "MPM", "ISI", "CPC", 'P', CU.plateWelded(), 'M', ModItems.motor, 'C', ModItems.coil_tungsten, 'S', new IngredientNBT2(ItemFluidTank.getFullBarrel(FluidRegistry.WATER)), 'I', ANY_PLASTIC.ingot());


		addRecipeAuto(new ItemStack(ModItems.wiring_red_copper, 1), "PPP", "PIP", "PPP", 'P', MINGRADE.wire(), 'I', STEEL.ingot());

		addShapelessAuto(new ItemStack(ModItems.gadget_kit, 1), ModBlocks.nuke_gadget, ModItems.gadget_explosive8, ModItems.gadget_explosive8, ModItems.gadget_explosive8, ModItems.gadget_explosive8, ModItems.gadget_wireing, ModItems.gadget_core, ModItems.hazmat_kit);
		addShapelessAuto(new ItemStack(ModItems.boy_kit, 1), ModBlocks.nuke_boy, ModItems.boy_shielding, ModItems.boy_target, ModItems.boy_bullet, ModItems.boy_propellant, ModItems.boy_igniter, ModItems.hazmat_kit);
		addShapelessAuto(new ItemStack(ModItems.man_kit, 1), ModBlocks.nuke_man, ModBlocks.det_nuke, ModItems.man_igniter, ModItems.hazmat_kit);
		addShapelessAuto(new ItemStack(ModItems.mike_kit, 1), ModBlocks.nuke_mike, ModBlocks.det_nuke, ModItems.mike_core, ModItems.mike_deut, ModItems.mike_cooling_unit, ModItems.hazmat_red_kit);
		addShapelessAuto(new ItemStack(ModItems.tsar_kit, 1), ModBlocks.nuke_tsar, ModBlocks.det_nuke, ModItems.mike_core, ModItems.mike_deut, ModItems.mike_core, ModItems.mike_deut, ModItems.hazmat_grey_kit);


		addRecipeAuto(new ItemStack(ModItems.designator, 1), "  A", "#B#", "#B#", '#', IRON.plate(), 'A', STEEL.plate(), 'B', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC));
		addRecipeAuto(new ItemStack(ModItems.designator_range, 1), "RRD", "PIC", "  P", 'P', STEEL.plate(), 'R', Items.REDSTONE, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'D', ModItems.designator, 'I', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.designator_manual, 1), "  A", "#C#", "#B#", '#', ANY_PLASTIC.ingot(), 'A', PB.plate(), 'B', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'C', ModItems.designator);
		addRecipeAuto(new ItemStack(ModItems.linker, 1), "I I", "ICI", "GGG", 'I', IRON.plate(), 'G', GOLD.plate(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED));
		addRecipeAuto(new ItemStack(ModItems.oil_detector, 1), "W I", "WCI", "PPP", 'W', new ItemStack(ModItems.wire, 1, MAT_GOLD.id), 'I', CU.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ANALOG), 'P', STEEL.plate());
		addRecipeAuto(new ItemStack(ModItems.turret_chip, 1), "WWW", "CPC", "WWW", 'W', new ItemStack(ModItems.wire, 1, MAT_GOLD.id), 'P', ANY_PLASTIC.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED));
		addRecipeAuto(new ItemStack(ModItems.turret_biometry, 1), "CC ", "GGS", "SSS", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'S', STEEL.plate(), 'G', GOLD.plate());
		addRecipeAuto(new ItemStack(ModItems.survey_scanner, 1), "SWS", " G ", "PCP", 'W', new ItemStack(ModItems.wire, 1, MAT_GOLD.id), 'P', ANY_PLASTIC.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'S', STEEL.plate(), 'G', GOLD.ingot());
		addRecipeAuto(new ItemStack(ModItems.geiger_counter, 1), "GPP", "WCS", "WBB", 'W', new ItemStack(ModItems.wire, 1, MAT_GOLD.id), 'P', ANY_PLASTIC.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'G', GOLD.ingot(), 'S', STEEL.plate(), 'B', BE.ingot());
		addRecipeAuto(new ItemStack(ModItems.dosimeter, 1), "WGW", "WCW", "WBW", 'W', KEY_PLANKS, 'G', KEY_ANYPANE, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), 'B', BE.ingot());
		addRecipeAuto(new ItemStack(ModItems.digamma_diagnostic), "GPP", "WCS", "WBB", 'W', new ItemStack(ModItems.wire, 1, MAT_TUNGSTEN.id), 'P', REIIUM.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP_QUANTUM), 'G', CS.ingot(), 'S', ModItems.plate_desh, 'B', I131.ingot());
		addRecipeAuto(new ItemStack(ModItems.lung_diagnostic, 1), "WGW", "WCW", "WBW", 'W', AL.ingot(), 'G', ModItems.gas_mask_filter, 'C', ASBESTOS.ingot(), 'B', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ANALOG));
		

		addRecipeAuto(new ItemStack(ModItems.key, 1), "  B", " B ", "P  ", 'P', STEEL.plate(), 'B', new ItemStack(ModItems.bolt, 1, MAT_STEEL.id));
		addRecipeAuto(new ItemStack(ModItems.key_kit, 1), "PKP", "DTD", "PKP", 'P', GOLD.plate(), 'K', ModItems.key, 'D', DIAMOND.dust(), 'T', ModItems.screwdriver);
		addRecipeAuto(new ItemStack(ModItems.key_red, 1), "DSC", "SMS", "KSD", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP_QUANTUM), 'M', "netherStar", 'K', ModItems.key, 'D', DESH.dust(), 'S', BIGMT.plate());
		addRecipeAuto(new ItemStack(ModItems.pin, 1), "W ", " W", " W", 'W', new ItemStack(ModItems.wire, 1, MAT_COPPER.id));
		addRecipeAuto(new ItemStack(ModItems.padlock_rusty, 1), "I", "B", "I", 'I', IRON.ingot(), 'B', new ItemStack(ModItems.bolt, 1, MAT_STEEL.id));
		addRecipeAuto(new ItemStack(ModItems.padlock, 1), " P ", "PBP", "PPP", 'P', STEEL.plate(), 'B', new ItemStack(ModItems.bolt, 1, MAT_STEEL.id));
		addRecipeAuto(new ItemStack(ModItems.padlock_reinforced, 1), " P ", "PBP", "PDP", 'P', ALLOY.plate(), 'D',ModItems.plate_desh, 'B', new ItemStack(ModItems.bolt, 1, MAT_DURA.id));
		addRecipeAuto(new ItemStack(ModItems.padlock_unbreakable, 1), " P ", "PBP", "PDP", 'P', BIGMT.plate(), 'D', DIAMOND.gem(), 'B', new ItemStack(ModItems.bolt, 1, MAT_DURA.id));

		addRecipeAuto(new ItemStack(ModItems.euphemium_stopper, 1), "I", "S", "S", 'I', EUPH.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.matchstick, 16), "I", "S", 'I', S.dust(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.matchstick, 16), "I", "S", 'I', S.dust(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.matchstick, 24), "I", "S", 'I',P_RED.dust(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.crowbar, 1), "II", " I", " I", 'I', STEEL.ingot());


		addRecipeAuto(ItemFluidCanister.getFullCanister(ModForgeFluids.PETROIL, 9), "RRR", "RLR", "RRR", 'R', new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.RECLAIMED)), 'L', new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.LUBRICANT)));

		addRecipeAuto(new ItemStack(ModItems.record_lc, 1), " S ", "SDS", " S ", 'S', POLYMER.ingot(), 'D', LAPIS.dust());
		addRecipeAuto(new ItemStack(ModItems.record_ss, 1), " S ", "SDS", " S ", 'S', POLYMER.ingot(), 'D', ALLOY.dust());
		addRecipeAuto(new ItemStack(ModItems.record_vc, 1), " S ", "SDS", " S ", 'S', POLYMER.ingot(), 'D', CMB.dust());


		addRecipeAuto(new ItemStack(ModItems.saw, 1), "IIL", "PP ", 'P', STEEL.plate(), 'I', STEEL.ingot(), 'L', Items.LEATHER);
		addRecipeAuto(new ItemStack(ModItems.bat, 1), "P", "P", "S", 'S', STEEL.plate(), 'P', KEY_PLANKS);
		addShapelessAuto(new ItemStack(ModItems.bat_nail, 1), ModItems.bat, STEEL.plate());
		addRecipeAuto(new ItemStack(ModItems.golf_club, 1), "IP", " P", " P", 'P', STEEL.plate(), 'I', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.pipe_rusty, 1), "II", " I", " I", 'I', IRON.ingot());
		addRecipeAuto(new ItemStack(ModItems.pipe_lead, 1), "II", " I", " I", 'I', PB.ingot());

		addRecipeAuto(new ItemStack(ModItems.bottle_opener, 1), "S", "P", 'S', STEEL.plate(), 'P', KEY_PLANKS);

		addRecipeAuto(new ItemStack(ModItems.polaroid, 1), " C ", "RPY", " B ", 'B', LAPIS.dust(), 'C', COAL.dust(), 'R', ALLOY.dust(), 'Y', GOLD.dust(), 'P', Items.PAPER);

		addRecipeAuto(new ItemStack(ModItems.ullapool_caber, 1), "ITI", " S ", " S ", 'I', IRON.plate(), 'T', Blocks.TNT, 'S', Items.STICK);
		addShapelessAuto(new ItemStack(ModItems.chocolate_milk, 1), KEY_ANYPANE, new ItemStack(Items.DYE, 1, 3), Items.MILK_BUCKET, KNO.block(), S.dust(), S.dust(), S.dust(),P_RED.dust());

		addShapelessAuto(new ItemStack(ModItems.crystal_horn, 1), NP237.dust(), I.dust(), TH232.dust(), AT.dust(), ND.dust(), CS.dust(), ModBlocks.block_meteor, ModBlocks.gravel_obsidian, Items.WATER_BUCKET);
		addShapelessAuto(new ItemStack(ModItems.crystal_charred, 1), SR.dust(), CO.dust(), BR.dust(), NB.dust(), TS.dust(), CE.dust(), ModBlocks.block_meteor, AL.block(), Items.WATER_BUCKET);
		addRecipeAuto(new ItemStack(ModBlocks.crystal_virus, 1), "STS", "THT", "STS", 'S', ModItems.particle_strange, 'T', W.dust(), 'H', ModItems.crystal_horn);
		addRecipeAuto(new ItemStack(ModBlocks.crystal_pulsar, 32), "STS", "THT", "STS", 'S', new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.UF6)), 'T', AL.dust(), 'H', ModItems.crystal_charred);

		addRecipeAuto(new ItemStack(ModBlocks.fluid_duct_mk2, 8), "SAS", "   ", "SAS", 'S', STEEL.plate(), 'A', AL.plate());
		addRecipeAuto(new ItemStack(ModBlocks.fluid_duct_solid, 8), "SAS", "ADA", "SAS", 'S', STEEL.ingot(), 'A', AL.plate(), 'D', ModItems.ducttape);
		addShapelessAuto(new ItemStack(ModBlocks.fluid_duct_solid_sealed, 1), ModBlocks.fluid_duct_solid, ModBlocks.brick_compound);
		addRecipeAuto(new ItemStack(ModBlocks.machine_assembler, 1), "WWW", "MCM", "ISI", 'W', KEY_ANYPANE, 'M', ModItems.motor, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), 'I', CU.block(), 'S', STEEL.block());
        addRecipeAuto(new ItemStack(ModBlocks.machine_autocrafter, 1), "SCS", "MWM", "SCS", 'S', STEEL.plate(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), 'M', ModItems.motor, 'W', Blocks.CRAFTING_TABLE);
        addRecipeAuto(new ItemStack(ModItems.template_folder, 1), "LPL", "BPB", "LPL", 'P', Items.PAPER, 'L', "dyeBlue", 'B', "dyeWhite");
		addRecipeAuto(new ItemStack(ModItems.turret_control, 1), "R12", "PPI", "  I", 'R', Items.REDSTONE, '1', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR_BOARD), '2', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'P', STEEL.plate(), 'I', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.pellet_antimatter, 1), "###", "###", "###", '#', new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.AMAT)));
		addRecipeAuto(new ItemStack(ModItems.fluid_tank_full, 8), "121", "1 1", "121", '1', AL.plate(), '2', IRON.plate());
		addRecipeAuto(new ItemStack(ModItems.fluid_tank_lead_full, 2), "LUL", "LTL", "LUL", 'L', PB.plate(), 'U', U238.billet(), 'T', ModItems.fluid_tank_full);
		addRecipeAuto(new ItemStack(ModItems.fluid_barrel_full, 2), "121", "1 1", "121", '1', STEEL.plate(), '2', AL.plate());
		addRecipeAuto(new ItemStack(ModItems.inf_water, 1), "222", "131", "222", '1', Items.WATER_BUCKET, '2', AL.plate(), '3', Items.DIAMOND);

		//not so Temporary Crappy Recipes
		addRecipeAuto(new ItemStack(ModItems.plate_dineutronium, 4), "PIP", "IDI", "PIP", 'P', ModItems.powder_spark_mix, 'I', DNT.ingot(), 'D', DESH.ingot());
		addRecipeAuto(new ItemStack(ModItems.plate_desh, 4), "PIP", "IDI", "PIP", 'P', ANY_PLASTIC.dust(), 'I', DESH.ingot(), 'D', DURA.ingot());
		addRecipeAuto(new ItemStack(ModItems.piston_selenium, 1), "SSS", "STS", " D ", 'S', STEEL.plate(), 'T', W.ingot(), 'D', new ItemStack(ModItems.bolt, 1, MAT_DURA.id));
		addShapelessAuto(new ItemStack(ModItems.catalyst_clay), IRON.dust(), Items.CLAY_BALL);
		addRecipeAuto(new ItemStack(ModItems.ams_core_sing, 1), "EAE", "ASA", "EAE", 'E', ModItems.plate_euphemium, 'A', new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.ASCHRAB)), 'S', ModItems.singularity);
		addRecipeAuto(new ItemStack(ModItems.ams_core_wormhole, 1), "DPD", "PSP", "DPD", 'D', ModItems.plate_dineutronium, 'P', ModItems.powder_spark_mix, 'S', ModItems.singularity);
		addRecipeAuto(new ItemStack(ModItems.ams_core_eyeofharmony, 1), "ALA", "LSL", "ALA", 'A', ModItems.plate_dalekanium, 'L', new IngredientNBT2(ItemFluidTank.getFullBarrel(FluidRegistry.LAVA)), 'S', ModItems.black_hole);
		addRecipeAuto(new ItemStack(ModItems.ams_core_thingy), "GGG", "N N", " S ", 'N', GOLD.nugget(), 'G', GOLD.ingot(), 'S', ModItems.battery_spark_cell_10000);
		addRecipeAuto(new ItemStack(ModItems.photo_panel), " G ", "IPI", " C ", 'G', KEY_ANYPANE, 'I', ModItems.plate_polymer, 'P', NETHERQUARTZ.dust(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.PCB));
		addRecipeAuto(new ItemStack(ModBlocks.machine_satlinker), "PSP", "SCS", "PSP", 'P', STEEL.plate(), 'S', STAR.ingot(), 'C', ModItems.sat_chip);
		addRecipeAuto(new ItemStack(ModBlocks.machine_telelinker), "PSP", "SCS", "PSP", 'P', STEEL.plate(), 'S', ALLOY.ingot(), 'C', ModItems.turret_biometry);
		addRecipeAuto(new ItemStack(ModBlocks.machine_keyforge), "PCP", "WSW", "WSW", 'P', STEEL.plate(), 'S', W.ingot(), 'C', ModItems.padlock, 'W', KEY_PLANKS);
		addRecipeAuto(new ItemStack(ModItems.sat_chip), "WWW", "CIC", "WWW", 'W', new ItemStack(ModItems.wire, 1, MAT_MINGRADE.id), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'I', ANY_PLASTIC.ingot());
		addShapelessAuto(new ItemStack(ModItems.sat_mapper), ModBlocks.sat_mapper);
		addShapelessAuto(new ItemStack(ModItems.sat_scanner), ModBlocks.sat_scanner);
		addShapelessAuto(new ItemStack(ModItems.sat_radar), ModBlocks.sat_radar);
		addShapelessAuto(new ItemStack(ModItems.sat_laser), ModBlocks.sat_laser);
		addShapelessAuto(new ItemStack(ModItems.sat_resonator), ModBlocks.sat_resonator);
		addShapelessAuto(new ItemStack(ModItems.sat_foeq), ModBlocks.sat_foeq);
		addShapelessAuto(new ItemStack(ModItems.geiger_counter), ModBlocks.geiger);
		addShapelessAuto(new ItemStack(ModBlocks.sat_mapper), ModItems.sat_mapper);
		addShapelessAuto(new ItemStack(ModBlocks.sat_scanner), ModItems.sat_scanner);
		addShapelessAuto(new ItemStack(ModBlocks.sat_radar), ModItems.sat_radar);
		addShapelessAuto(new ItemStack(ModBlocks.sat_laser), ModItems.sat_laser);
		addShapelessAuto(new ItemStack(ModBlocks.sat_resonator), ModItems.sat_resonator);
		addShapelessAuto(new ItemStack(ModBlocks.sat_foeq), ModItems.sat_foeq);
		addShapelessAuto(new ItemStack(ModBlocks.geiger), ModItems.geiger_counter);
		addRecipeAuto(new ItemStack(ModItems.sat_interface), "ISI", "PCP", "PAP", 'I', STEEL.ingot(), 'S', STAR.ingot(), 'P', ModItems.plate_polymer, 'C', ModItems.sat_chip, 'A', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED));
		addRecipeAuto(new ItemStack(ModItems.sat_coord), "SII", "SCA", "SPP", 'I', STEEL.ingot(), 'S', STAR.ingot(), 'P', ModItems.plate_polymer, 'C', ModItems.sat_chip, 'A', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED));
		addRecipeAuto(new ItemStack(ModBlocks.machine_spp_bottom), "MDM", "LCL", "LWL", 'M', MAGTUNG.ingot(), 'D',ModItems.plate_desh, 'L', PB.plate(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'W', ModItems.coil_magnetized_tungsten);
		addRecipeAuto(new ItemStack(ModBlocks.machine_spp_top), "LWL", "LCL", "MDM", 'M', MAGTUNG.ingot(), 'D',ModItems.plate_desh, 'L', PB.plate(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'W', ModItems.coil_magnetized_tungsten);
		addShapelessAuto(new ItemStack(ModBlocks.machine_spp_bottom), ModBlocks.machine_spp_top);
		addShapelessAuto(new ItemStack(ModBlocks.machine_spp_top), ModBlocks.machine_spp_bottom);
		addRecipeAuto(new ItemStack(ModItems.gun_b93), "PCE", "SEB", "PCE", 'P', ModItems.plate_dineutronium, 'C', ModItems.weaponized_starblaster_cell, 'E', ModItems.component_emitter, 'B', ModItems.gun_b92, 'S', ModItems.singularity_spark);
		addRecipeAuto(new ItemStack(ModBlocks.machine_transformer), "SDS", "MCM", "MCM", 'S', IRON.ingot(), 'D', MINGRADE.ingot(), 'M', ModItems.coil_advanced_alloy, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR));
		addRecipeAuto(new ItemStack(ModBlocks.machine_transformer_20), "SDS", "MCM", "MCM", 'S', IRON.ingot(), 'D', MINGRADE.ingot(), 'M', ModItems.coil_copper, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR));
		addRecipeAuto(new ItemStack(ModBlocks.machine_transformer_dnt), "SDS", "MCM", "MCM", 'S', STAR.ingot(), 'D', DESH.ingot(), 'M', ModBlocks.fwatz_conductor, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP_QUANTUM));
		addRecipeAuto(new ItemStack(ModBlocks.machine_transformer_dnt_20), "SDS", "MCM", "MCM", 'S', STAR.ingot(), 'D', DESH.ingot(), 'M', ModBlocks.fusion_conductor, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP_QUANTUM));
		addShapelessAuto(new ItemStack(ModItems.bottle_sparkle), ModItems.bottle_nuka, Items.CARROT, GOLD.nugget());
		addShapelessAuto(new ItemStack(ModItems.bottle_rad), ModItems.bottle_quantum, Items.CARROT, GOLD.nugget(), ModItems.powder_radspice);
		addRecipeAuto(new ItemStack(ModItems.grenade_nuke), "CGC", "CGC", "PAP", 'C', ModBlocks.det_charge, 'G', ModItems.grenade_mk2, 'P', ALLOY.plate(), 'A', Blocks.ANVIL);
		addRecipeAuto(new ItemStack(ModBlocks.radiobox), "PLP", "PSP", "PLP", 'P', STEEL.plate(), 'S', ModItems.ring_starmetal, 'L', OreDictManager.getReflector());
		addRecipeAuto(new ItemStack(ModBlocks.radiorec), "  W", "PCP", "PIP", 'W', new ItemStack(ModItems.wire, 1, MAT_COPPER.id), 'P', STEEL.plate(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), 'I', ANY_PLASTIC.ingot());
		addRecipeAuto(new ItemStack(ModItems.jackt), "S S", "LIL", "LIL", 'S', STEEL.plate(), 'L', Items.LEATHER, 'I', ANY_RUBBER.ingot());
		addRecipeAuto(new ItemStack(ModItems.jackt2), "S S", "LIL", "III", 'S', STEEL.plate(), 'L', Items.LEATHER, 'I', ANY_RUBBER.ingot());
		addRecipeAuto(new ItemStack(ModItems.grenade_cloud), "SPS", "CAC", "SPS", 'S', S.dust(), 'P', ModItems.powder_poison, 'C', CU.dust(), 'A', new IngredientNBT2(ItemFluidTank.getFullTank(ModForgeFluids.ACID)));
		addRecipeAuto(new ItemStack(ModItems.grenade_pink_cloud), " S ", "ECE", " E ", 'S', ModItems.powder_spark_mix, 'E', ModItems.powder_magic, 'C', ModItems.grenade_cloud);
		addRecipeAuto(new ItemStack(ModBlocks.vent_chlorine), "IGI", "ICI", "IDI", 'I', IRON.plate(), 'G', Blocks.IRON_BARS, 'C', ModItems.pellet_gas, 'D', Blocks.DISPENSER);
		addRecipeAuto(new ItemStack(ModBlocks.vent_chlorine_seal), "ISI", "SCS", "ISI", 'I', BIGMT.ingot(), 'S', STAR.ingot(), 'C', ModItems.chlorine_pinwheel);
		addRecipeAuto(new ItemStack(ModBlocks.vent_cloud), "IGI", "ICI", "IDI", 'I', IRON.plate(), 'G', Blocks.IRON_BARS, 'C', ModItems.grenade_cloud, 'D', Blocks.DISPENSER);
		addRecipeAuto(new ItemStack(ModBlocks.vent_pink_cloud), "IGI", "ICI", "IDI", 'I', IRON.plate(), 'G', Blocks.IRON_BARS, 'C', ModItems.grenade_pink_cloud, 'D', Blocks.DISPENSER);
		addRecipeAuto(new ItemStack(ModBlocks.spikes, 4), "FFF", "BBB", "TTT", 'F', Items.FLINT, 'B', new ItemStack(ModItems.bolt, 1, MAT_STEEL.id), 'T', W.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.mine_ap, 4), "I", "C", "S", 'I', ModItems.plate_polymer, 'C', ANY_SMOKELESS.dust(), 'S', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.mine_shrap, 1), "L", "M", 'M', ModBlocks.mine_ap, 'L', ModItems.pellet_buckshot);
		addRecipeAuto(new ItemStack(ModBlocks.mine_he, 1), " C ", "PTP", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'P', STEEL.plate(), 'T', ANY_HIGHEXPLOSIVE.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.mine_fat, 1), "CDN", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ANALOG), 'D', ModItems.ducttape, 'N', ModItems.ammo_nuke);

		addRecipeAuto(new ItemStack(ModItems.defuser, 1), " PS", "P P", " P ", 'P', ANY_PLASTIC.ingot(), 'S', STEEL.plate());
		addRecipeAuto(new ItemStack(ModItems.defuser_desh), " SD", "S S", " S ", 'D', DESH.ingot(), 'S', POLYMER.ingot());
		addShapelessAuto(new ItemStack(ModItems.syringe_taint), ModItems.bottle2_empty, ModItems.syringe_metal_empty, ModItems.ducttape, ModItems.powder_magic, SA326.nugget(), Items.POTIONITEM);
		addShapelessAuto(new ItemStack(ModItems.loops), ModItems.flame_pony, Items.WHEAT, Items.SUGAR);
		addShapelessAuto(new ItemStack(ModItems.loop_stew), ModItems.loops, ModItems.can_smart, Items.BOWL);
		addRecipeAuto(new ItemStack(ModBlocks.machine_controller, 1), "TDT", "DCD", "TDT", 'T', ANY_RESISTANTALLOY.ingot(), 'D', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CRT_TUBE), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED));
		addRecipeAuto(new ItemStack(ModItems.custom_fall, 1), "IIP", "CHW", "IIP", 'I', ANY_RUBBER.ingot(), 'P', BIGMT.plate(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'H', ModItems.hull_small_steel, 'W', ModItems.coil_copper);
//		addRecipeAuto(new ItemStack(ModItems.containment_box, 1), new Object[] { "LLL", "LCL", "LLL", 'L', PB.plate(), 'C', Blocks.CHEST });

		addRecipeAuto(new ItemStack(ModBlocks.absorber, 1), "ICI", "CPC", "ICI", 'I', CU.ingot(), 'C', COAL.dust(), 'P', PB.dust());
		addRecipeAuto(new ItemStack(ModBlocks.absorber_red, 1), "ICI", "CPC", "ICI", 'I', TI.ingot(), 'C', COAL.dust(), 'P', ModBlocks.absorber);
		addRecipeAuto(new ItemStack(ModBlocks.absorber_green, 1), "ICI", "CPC", "ICI", 'I', ANY_PLASTIC.ingot(), 'C', ModItems.powder_desh_mix, 'P', ModBlocks.absorber_red);
		addRecipeAuto(new ItemStack(ModBlocks.absorber_pink, 1), "ICI", "CPC", "ICI", 'I', BIGMT.ingot(), 'C', ModItems.powder_nitan_mix, 'P', ModBlocks.absorber_green);
		addRecipeAuto(new ItemStack(ModBlocks.decon, 1), "BGB", "SAS", "BSB", 'B', BE.ingot(), 'G', Blocks.IRON_BARS, 'S', STEEL.ingot(), 'A', ModBlocks.absorber);
		addRecipeAuto(new ItemStack(ModBlocks.decon_digamma, 1), "BGB", "SAS", "BTB", 'S', ModItems.billet_flashlead, 'G', ModItems.fmn, 'B',ModItems.plate_desh, 'A', ModBlocks.decon, 'T', ModItems.xanax);
		addRecipeAuto(new ItemStack(ModBlocks.radsensor, 1), "IGI", "LCL", "IRI", 'I', CE.ingot(), 'L', PB.plate(), 'G', ModItems.geiger_counter, 'C', Blocks.OBSERVER, 'R', Items.REDSTONE);
		addRecipeAuto(new ItemStack(ModBlocks.machine_amgen, 1), "ITI", "PCP", "ITI", 'I', DURA.ingot(), 'T', ModItems.thermo_element, 'P', BIGMT.plateCast(), 'C', ModBlocks.absorber);
		addRecipeAuto(new ItemStack(ModBlocks.machine_geo, 1), "ITI", "PAP", "ITI", 'I', ALLOY.ingot(), 'T', ModItems.thermo_element, 'P', CU.plateCast(), 'A', ModBlocks.red_wire_coated);
		addRecipeAuto(new ItemStack(ModBlocks.machine_minirtg, 1), "CRC", "CPC", "TAT", 'C', TI.plateCast(), 'R', ModItems.rtg_unit, 'P', ModItems.pellet_rtg, 'T', ModBlocks.brick_compound, 'A', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC));
        addRecipeAuto(new ItemStack(ModBlocks.machine_rtg, 1), "CRC", "CPC", "TAT", 'C', STEEL.plateCast(), 'R', ModItems.rtg_unit, 'P', ModItems.pellet_rtg_actinium, 'T', ModBlocks.brick_compound, 'A', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED));
        addRecipeAuto(new ItemStack(ModBlocks.machine_powerrtg, 1), "CRC", "CPC", "TAT", 'C', W.plateWelded(), 'R', ModItems.rtg_unit, 'P', ModItems.pellet_rtg_polonium, 'T', ModBlocks.brick_compound, 'A', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BISMOID));

		addShapelessAuto(new ItemStack(ModItems.pellet_rtg), PU238.billet(), PU238.billet(), PU238.billet(), IRON.plate());
		addShapelessAuto(new ItemStack(ModItems.pellet_rtg_radium), RA226.billet(), RA226.billet(), RA226.billet(), IRON.plate());
		addShapelessAuto(new ItemStack(ModItems.pellet_rtg_weak), U238.billet(), U238.billet(), PU238.billet(), IRON.plate());
		addShapelessAuto(new ItemStack(ModItems.pellet_rtg_strontium), SR90.billet(), SR90.billet(), SR90.billet(), IRON.plate());
		addShapelessAuto(new ItemStack(ModItems.pellet_rtg_cobalt), CO60.billet(), CO60.billet(), CO60.billet(), IRON.plate());
		addShapelessAuto(new ItemStack(ModItems.pellet_rtg_actinium), AC227.billet(), AC227.billet(), AC227.billet(), IRON.plate());
		addShapelessAuto(new ItemStack(ModItems.pellet_rtg_polonium), PO210.billet(), PO210.billet(), PO210.billet(), IRON.plate());
		addShapelessAuto(new ItemStack(ModItems.pellet_rtg_lead), PB209.billet(), PB209.billet(), PB209.billet(), IRON.plate());
		addShapelessAuto(new ItemStack(ModItems.pellet_rtg_gold), AU198.billet(), AU198.billet(), AU198.billet(), IRON.plate());
		addShapelessAuto(new ItemStack(ModItems.pellet_rtg_americium), AM241.billet(), AM241.billet(), AM241.billet(), IRON.plate());
		addShapelessAuto(new ItemStack(ModItems.pellet_rtg_balefire), ModItems.egg_balefire, ModItems.egg_balefire, ModItems.egg_balefire, IRON.plate());

		addShapelessAuto(new ItemStack(ModItems.billet_bismuth, 3), new ItemStack(ModItems.pellet_rtg_depleted_bismuth));
		addShapelessAuto(new ItemStack(ModItems.ingot_lead, 2), new ItemStack(ModItems.pellet_rtg_depleted_lead));
		addShapelessAuto(new ItemStack(ModItems.nugget_mercury, 12), new ItemStack(ModItems.pellet_rtg_depleted_mercury));
		addShapelessAuto(new ItemStack(ModItems.billet_neptunium, 3), new ItemStack(ModItems.pellet_rtg_depleted_neptunium));
		addShapelessAuto(new ItemStack(ModItems.billet_zirconium, 3), new ItemStack(ModItems.pellet_rtg_depleted_zirconium));

		addRecipeAuto(new ItemStack(ModBlocks.pink_planks, 4), "W", 'W', ModBlocks.pink_log);
		addRecipeAuto(new ItemStack(ModItems.decontamination_module, 1), "GAG", "WTW", "GAG", 'W', AC.ingot(), 'T', ModBlocks.decon, 'G', RA226.nugget(), 'A', TCALLOY.ingot());

		addRecipeAuto(new ItemStack(ModItems.door_metal, 1), "II", "SS", "II", 'I', IRON.plate(), 'S', STEEL.plate());
		addRecipeAuto(new ItemStack(ModItems.door_office, 1), "II", "SS", "II", 'I', KEY_PLANKS, 'S', IRON.plate());
		addRecipeAuto(new ItemStack(ModItems.door_bunker, 1), "II", "SS", "II", 'I', STEEL.plate(), 'S', PB.plate());

		addShapelessAuto(new ItemStack(Items.PAPER, 1), new ItemStack(ModItems.assembly_template, 1, OreDictionary.WILDCARD_VALUE));
		addShapelessAuto(new ItemStack(Items.PAPER, 1), new ItemStack(ModItems.chemistry_template, 1, OreDictionary.WILDCARD_VALUE));

		for(Entry<String, Fluid> entry : FluidRegistry.getRegisteredFluids().entrySet()) {
			Fluid fluid = entry.getValue();
			addShapelessAuto(ItemFFFluidDuct.getStackFromFluid(fluid), new ItemStack(ModBlocks.fluid_duct_mk2, 1), new IngredientNBT2(ItemForgeFluidIdentifier.getStackFromFluid(entry.getValue())));

			addShapelessAuto(ItemFFFluidDuct.getStackFromFluid(fluid, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new ItemStack(ModBlocks.fluid_duct_mk2, 8), new IngredientNBT2(ItemForgeFluidIdentifier.getStackFromFluid(entry.getValue())));
		// No more old pipe crafting
		// 	addShapelessAuto(ItemFFFluidDuct.getStackFromFluid(fluid), new Object[] { new ItemStack(ModItems.ff_fluid_duct, 1, OreDictionary.WILDCARD_VALUE), new IngredientNBT2(ItemForgeFluidIdentifier.getStackFromFluid(entry.getValue())) });
		// 	addShapelessAuto(ItemFFFluidDuct.getStackFromFluid(fluid, 8), new Object[] { new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.ff_fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new IngredientNBT2(ItemForgeFluidIdentifier.getStackFromFluid(entry.getValue())) });
		}

		addShapelessAuto(new ItemStack(ModBlocks.fluid_duct_mk2, 1), new ItemStack(ModItems.ff_fluid_duct, 1, OreDictionary.WILDCARD_VALUE));

		addShapelessAuto(new ItemStack(ModItems.redstone_depleted, 1), new ItemStack(ModItems.battery_su, 1, OreDictionary.WILDCARD_VALUE));
		addShapelessAuto(new ItemStack(ModItems.redstone_depleted, 2), new ItemStack(ModItems.battery_su_l, 1, OreDictionary.WILDCARD_VALUE));
		addShapelessAuto(new ItemStack(Items.REDSTONE, 1), ModItems.redstone_depleted, ModItems.redstone_depleted);

		addShapelessAuto(new ItemStack(ModItems.bobmazon_materials), Items.BOOK, GOLD.nugget(), Items.STRING);
		addShapelessAuto(new ItemStack(ModItems.bobmazon_machines), Items.BOOK, GOLD.nugget(), new ItemStack(Items.DYE, 1, 1));
		addShapelessAuto(new ItemStack(ModItems.bobmazon_weapons), Items.BOOK, GOLD.nugget(), new ItemStack(Items.DYE, 1, 8));
		addShapelessAuto(new ItemStack(ModItems.bobmazon_tools), Items.BOOK, GOLD.nugget(), new ItemStack(Items.DYE, 1, 2));

		addRecipeAuto(new ItemStack(Blocks.TORCH, 3), "L", "S", 'L', ModItems.lignite, 'S', Items.STICK);
		addRecipeAuto(new ItemStack(Blocks.TORCH, 6), "L", "S", 'L', ModItems.briquette_lignite, 'S', Items.STICK);
		addRecipeAuto(new ItemStack(Blocks.TORCH, 8), "L", "S", 'L', ANY_COKE.gem(), 'S', Items.STICK);

		addRecipeAuto(new ItemStack(ModBlocks.machine_missile_assembly, 1), "PWP", "SSS", "CCC", 'P', ModItems.pedestal_steel, 'W', ModItems.wrench, 'S', STEEL.plate(), 'C', ModBlocks.steel_scaffold);
		addRecipeAuto(new ItemStack(ModBlocks.struct_launcher, 1), "PPP", "SDS", "CCC", 'P', STEEL.plate(), 'S', ModBlocks.steel_scaffold, 'D', ModItems.pipes_steel, 'C', ModBlocks.concrete_smooth);
		addRecipeAuto(new ItemStack(ModBlocks.struct_launcher, 1), "PPP", "SDS", "CCC", 'P', STEEL.plate(), 'S', ModBlocks.steel_scaffold, 'D', ModItems.pipes_steel, 'C', ModBlocks.concrete);
		addRecipeAuto(new ItemStack(ModBlocks.struct_scaffold, 1), "SSS", "DCD", "SSS", 'S', ModBlocks.steel_scaffold, 'D', ModBlocks.fluid_duct_mk2, 'C', ModBlocks.red_cable);

		addRecipeAuto(new ItemStack(ModItems.mp_stability_10_flat, 1), "PSP", "P P", 'P', STEEL.plate(), 'S', ModBlocks.steel_scaffold);
		addRecipeAuto(new ItemStack(ModItems.mp_stability_10_cruise, 1), "ASA", " S ", "PSP", 'A', TI.plate(), 'P', STEEL.plate(), 'S', ModBlocks.steel_scaffold);
		addRecipeAuto(new ItemStack(ModItems.mp_stability_10_space, 1), "ASA", "PSP", 'A', AL.plate(), 'P', STEEL.ingot(), 'S', ModBlocks.steel_scaffold);
		addRecipeAuto(new ItemStack(ModItems.mp_stability_15_flat, 1), "ASA", "PSP", 'A', AL.plate(), 'P', STEEL.plate(), 'S', ModBlocks.steel_scaffold);
		addRecipeAuto(new ItemStack(ModItems.mp_stability_15_thin, 1), "A A", "PSP", "PSP", 'A', AL.plate(), 'P', STEEL.plate(), 'S', ModBlocks.steel_scaffold);

		addRecipeAuto(new ItemStack(ModItems.mp_thruster_15_balefire_large_rad, 1), "CCC", "CTC", "CCC", 'C', CU.plateWelded(), 'T', ModItems.mp_thruster_15_balefire_large);
		
		addRecipeAuto(new ItemStack(ModItems.mp_fuselage_10_kerosene_insulation, 1), "CCC", "CTC", "CCC", 'C', ANY_RUBBER.ingot(), 'T', ModItems.mp_fuselage_10_kerosene);
		addRecipeAuto(new ItemStack(ModItems.mp_fuselage_10_long_kerosene_insulation, 1), "CCC", "CTC", "CCC", 'C', ANY_RUBBER.ingot(), 'T', ModItems.mp_fuselage_10_long_kerosene);
		addRecipeAuto(new ItemStack(ModItems.mp_fuselage_15_kerosene_insulation, 1), "CCC", "CTC", "CCC", 'C', ANY_RUBBER.ingot(), 'T', ModItems.mp_fuselage_15_kerosene);
		addRecipeAuto(new ItemStack(ModItems.mp_fuselage_10_solid_insulation, 1), "CCC", "CTC", "CCC", 'C', ANY_RUBBER.ingot(), 'T', ModItems.mp_fuselage_10_solid);
		addRecipeAuto(new ItemStack(ModItems.mp_fuselage_10_long_solid_insulation, 1), "CCC", "CTC", "CCC", 'C', ANY_RUBBER.ingot(), 'T', ModItems.mp_fuselage_10_long_solid);
		addRecipeAuto(new ItemStack(ModItems.mp_fuselage_15_solid_insulation, 1), "CCC", "CTC", "CCC", 'C', ANY_RUBBER.ingot(), 'T', ModItems.mp_fuselage_15_solid);
		addRecipeAuto(new ItemStack(ModItems.mp_fuselage_15_solid_desh, 1), "CCC", "CTC", "CCC", 'C', DESH.ingot(), 'T', ModItems.mp_fuselage_15_solid);
		addRecipeAuto(new ItemStack(ModItems.mp_fuselage_10_kerosene_metal, 1), "ICI", "CTC", "ICI", 'C', STEEL.plate(), 'I', IRON.plate(), 'T', ModItems.mp_fuselage_10_kerosene);
		addRecipeAuto(new ItemStack(ModItems.mp_fuselage_10_long_kerosene_metal, 1), "ICI", "CTC", "ICI", 'C', STEEL.plate(), 'I', IRON.plate(), 'T', ModItems.mp_fuselage_10_long_kerosene);
		addRecipeAuto(new ItemStack(ModItems.mp_fuselage_15_kerosene_metal, 1), "ICI", "CTC", "ICI", 'C', STEEL.plate(), 'I', IRON.plate(), 'T', ModItems.mp_fuselage_15_kerosene);

		addRecipeAuto(new ItemStack(ModItems.mp_warhead_15_boxcar, 1), "SNS", "CBC", "SFS", 'S', STAR.ingot(), 'N', ModBlocks.det_nuke, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'B', ModBlocks.boxcar, 'F', ModItems.tritium_deuterium_cake);

		addRecipeAuto(new ItemStack(ModItems.mp_chip_1, 1), "P", "C", "S", 'P', ANY_RUBBER.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), 'S', ModBlocks.steel_scaffold);
		addRecipeAuto(new ItemStack(ModItems.mp_chip_2, 1), "P", "C", "S", 'P', ANY_RUBBER.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ANALOG), 'S', ModBlocks.steel_scaffold);
		addRecipeAuto(new ItemStack(ModItems.mp_chip_3, 1), "P", "C", "S", 'P', ANY_RUBBER.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'S', ModBlocks.steel_scaffold);
		addRecipeAuto(new ItemStack(ModItems.mp_chip_4, 1), "P", "C", "S", 'P', ANY_RUBBER.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'S', ModBlocks.steel_scaffold);
		addRecipeAuto(new ItemStack(ModItems.mp_chip_5, 1), "P", "C", "S", 'P', ANY_RUBBER.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BISMOID), 'S', ModBlocks.steel_scaffold);

		addRecipeAuto(new ItemStack(ModItems.seg_10, 1), "P", "S", "B", 'P', AL.plate(), 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.steel_beam);
		addRecipeAuto(new ItemStack(ModItems.seg_15, 1), "PP", "SS", "BB", 'P', TI.plate(), 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.steel_beam);
		addRecipeAuto(new ItemStack(ModItems.seg_20, 1), "PGP", "SSS", "BBB", 'P', STEEL.plate(), 'G', GOLD.plate(), 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.steel_beam);


		addRecipeAuto(new ItemStack(ModBlocks.fence_metal, 6), "BIB", "BIB", 'B', Blocks.IRON_BARS, 'I', Items.IRON_INGOT);

		addShapelessAuto(new ItemStack(ModBlocks.waste_trinitite), new ItemStack(Blocks.SAND, 1, 0), ModItems.trinitite);
		addShapelessAuto(new ItemStack(ModBlocks.waste_trinitite_red), new ItemStack(Blocks.SAND, 1, 1), ModItems.trinitite);
		addShapelessAuto(new ItemStack(ModBlocks.sand_uranium), "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", U.dust());
		addShapelessAuto(new ItemStack(ModBlocks.sand_polonium), "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", PO210.dust());
		addShapelessAuto(new ItemStack(ModBlocks.sand_boron, 8), "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", B.dust());
		addShapelessAuto(new ItemStack(ModBlocks.sand_lead, 8), "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", PB.dust());
		addShapelessAuto(new ItemStack(ModBlocks.sand_quartz, 1), "sand", "sand", NETHERQUARTZ.dust(), NETHERQUARTZ.dust());

		addRecipeAuto(new ItemStack(ModItems.rune_blank, 1), "PSP", "SDS", "PSP", 'P', ModItems.powder_magic, 'S', STAR.ingot(), 'D', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BISMOID));
		addShapelessAuto(new ItemStack(ModItems.rune_isa, 1), ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity_counter_resonant);
		addShapelessAuto(new ItemStack(ModItems.rune_dagaz, 1), ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.black_hole);
		addShapelessAuto(new ItemStack(ModItems.rune_hagalaz, 1), ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity_super_heated);
		addShapelessAuto(new ItemStack(ModItems.rune_jera, 1), ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity_spark);
		addShapelessAuto(new ItemStack(ModItems.rune_thurisaz, 1), ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity);
		addRecipeAuto(new ItemStack(ModItems.ams_focus_blank, 1), "PAP", "GBG", "PCP", 'P', ModItems.plate_dineutronium, 'G', ModBlocks.reinforced_glass, 'A', ModItems.rune_thurisaz, 'B', ModItems.hull_big_aluminium, 'C', ModItems.rune_jera);
		addRecipeAuto(new ItemStack(ModItems.ams_lens, 1), "PFP", "GEG", "PFP", 'P', ModItems.rune_dagaz, 'G', ModItems.ams_focus_blank, 'E', ModItems.upgrade_overdrive_3, 'F', ModItems.fusion_shield_tungsten);
		addRecipeAuto(new ItemStack(ModItems.ams_focus_omega, 1), "PFP", "REG", "PFP", 'P', ModBlocks.dfc_stabilizer, 'R', ModItems.ams_focus_limiter, 'G', ModItems.ams_focus_booster, 'E', ModItems.laser_crystal_digamma, 'F', ModItems.fusion_shield_vaporwave);
		addRecipeAuto(new ItemStack(ModItems.ams_focus_booster, 1), "PFP", "GEG", "PFP", 'P', ModItems.rune_hagalaz, 'G', ModItems.ams_lens, 'E', ModItems.upgrade_screm, 'F', ModItems.fusion_shield_desh);
		addRecipeAuto(new ItemStack(ModItems.ams_focus_limiter, 1), "PFP", "GEG", "PFP", 'P', ModItems.rune_isa, 'G', ModItems.ams_focus_blank, 'E', ModItems.upgrade_power_3, 'F', ModItems.inf_water_mk4);
		addRecipeAuto(new ItemStack(ModItems.ams_catalyst_blank, 1), "TET", "ETE", "TET", 'T', TS.dust(), 'E', EUPH.ingot());
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_iron, 1), ModItems.ams_catalyst_blank, ModItems.rune_thurisaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, IRON.dust(), IRON.dust(), IRON.dust(), IRON.dust());
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_copper, 1), ModItems.ams_catalyst_blank, ModItems.rune_thurisaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, ModItems.rune_isa, CU.dust(), CU.dust(), CU.dust(), CU.dust());
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_aluminium, 1), ModItems.ams_catalyst_blank, ModItems.rune_thurisaz, ModItems.rune_thurisaz, ModItems.rune_isa, ModItems.rune_isa, AL.dust(), AL.dust(), AL.dust(), AL.dust());
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_lithium, 1), ModItems.ams_catalyst_blank, ModItems.rune_thurisaz, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_isa, LI.dust(), LI.dust(), LI.dust(), LI.dust());
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_beryllium, 1), ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_dagaz, BE.dust(), BE.dust(), BE.dust(), BE.dust());
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_tungsten, 1), ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_dagaz, ModItems.rune_dagaz, W.dust(), W.dust(), W.dust(), W.dust());
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_cobalt, 1), ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_dagaz, CO.dust(), CO.dust(), CO.dust(), CO.dust());
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_niobium, 1), ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_hagalaz, NB.dust(), NB.dust(), NB.dust(), NB.dust());
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_cerium, 1), ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_hagalaz, ModItems.rune_hagalaz, CE.dust(), CE.dust(), CE.dust(), CE.dust());
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_thorium, 1), ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_hagalaz, TH232.dust(), TH232.dust(), TH232.dust(), TH232.dust());
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_strontium, 1), ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_hagalaz, SR.dust(), SR.dust(), SR.dust(), SR.dust());
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_caesium, 1), ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_jera, CS.dust(), CS.dust(), CS.dust(), CS.dust());
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_schrabidium, 1), ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_jera, SA326.dust(), SA326.dust(), SA326.dust(), SA326.dust());
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_euphemium, 1), ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_jera, ModItems.rune_jera, EUPH.dust(), EUPH.dust(), EUPH.dust(), EUPH.dust());
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_dineutronium, 1), ModItems.ams_catalyst_blank, ModItems.rune_jera, ModItems.rune_jera, ModItems.rune_jera, ModItems.rune_jera, DNT.dust(), DNT.dust(), DNT.dust(), DNT.dust());
		addRecipeAuto(new ItemStack(ModBlocks.dfc_core, 1), "DLD", "LML", "DLD", 'D', ModItems.ingot_bismuth, 'L', DNT.block(), 'M', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BISMOID));
		addRecipeAuto(new ItemStack(ModBlocks.dfc_emitter, 1), "SDS", "TXL", "SDS", 'S', OSMIRIDIUM.plateWelded(), 'D', ModItems.plate_desh, 'T', ModBlocks.machine_transformer_dnt, 'X', ModItems.crystal_xen, 'L', ModItems.sat_head_laser);
		addRecipeAuto(new ItemStack(ModBlocks.dfc_receiver, 1), "SDS", "TXL", "SDS", 'S', OSMIRIDIUM.plateWelded(), 'D', ModItems.plate_desh, 'T', ModBlocks.machine_transformer_dnt, 'X', ModBlocks.block_dineutronium, 'L', STEEL.shell());
		addRecipeAuto(new ItemStack(ModBlocks.dfc_injector, 1), "SDS", "TXL", "SDS", 'S', OSMIRIDIUM.plateWelded(), 'D', CMB.plate(), 'T', ModBlocks.machine_fluidtank, 'X', ModItems.motor, 'L', ModItems.pipes_steel);
		addRecipeAuto(new ItemStack(ModBlocks.dfc_stabilizer, 1), "SDS", "TXL", "SDS", 'S', OSMIRIDIUM.plateWelded(), 'D', ModItems.plate_desh, 'T', ModItems.singularity_spark, 'X', ModBlocks.fusion_conductor, 'L', ModItems.crystal_xen);


		addRecipeAuto(new ItemStack(ModBlocks.barrel_plastic, 1), "IPI", "I I", "IPI", 'I', ModItems.plate_polymer, 'P', AL.plate());
		addRecipeAuto(new ItemStack(ModBlocks.barrel_iron, 1), "IPI", "I I", "IPI", 'I', IRON.plate(), 'P', IRON.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.barrel_steel, 1), "IPI", "IOI", "IPI", 'I', STEEL.plate(), 'P', STEEL.ingot(), 'O', ModItems.oil_tar);
		addRecipeAuto(new ItemStack(ModBlocks.barrel_antimatter, 1), "IPI", "IPI", "IPI", 'I', BIGMT.plate(), 'P', ModItems.coil_advanced_torus);
		addRecipeAuto(new ItemStack(ModBlocks.tesla, 1), "CCC", "PIP", "WTW", 'C', ModItems.coil_copper, 'I', IRON.ingot(), 'P', ANY_PLASTIC.ingot(), 'T', ModBlocks.machine_transformer, 'W', KEY_PLANKS);

		addRecipeAuto(new ItemStack(ModItems.bottle_mercury, 1), "###", "#B#", "###", '#', ModItems.nugget_mercury, 'B', Items.GLASS_BOTTLE);
		addRecipeAuto(new ItemStack(ModItems.nugget_mercury, 8), "#", '#', ModItems.bottle_mercury);
		addRecipeAuto(new ItemStack(ModItems.egg_balefire, 1), "###", "###", "###", '#', ModItems.egg_balefire_shard);
		addRecipeAuto(new ItemStack(ModItems.egg_balefire_shard, 9), "#", '#', ModItems.egg_balefire);

		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 9), "#", '#', ModBlocks.block_insulator);
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 8), "DD", 'D', ANY_PLASTIC.ingot());
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 8), "DD", 'D', ANY_RUBBER.ingot());
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 16), "DD", 'D', FIBER.ingot());
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 16), "DD", 'D', ASBESTOS.ingot());
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 4), "SWS", 'S', Items.STRING, 'W', Blocks.WOOL);
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 4), "BB", 'B', "ingotBrick");
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 4), "BB", 'B', "ingotNetherBrick");

		addRecipeAuto(new ItemStack(ModItems.mechanism_special, 1), "PCI", "ISS", "PCI", 'P',ModItems.plate_desh, 'C', ModItems.coil_advanced_alloy, 'I', STAR.ingot(), 'S', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED));
		addRecipeAuto(new ItemStack(ModBlocks.det_cord, 8), "TNT", "NGN", "TNT", 'T', STEEL.plate(), 'N', KNO.dust(), 'G', ANY_GUNPOWDER.dust());
		addRecipeAuto(new ItemStack(ModBlocks.det_charge, 1), "PDP", "DTD", "PDP", 'P', STEEL.plate(), 'D', ModBlocks.det_cord, 'T', ANY_PLASTICEXPLOSIVE.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.det_n2, 1), "PDT", "DDD", "PDP", 'P', STEEL.plateCast(), 'D', ModItems.n2_charge, 'T', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC));
		addRecipeAuto(new ItemStack(ModBlocks.det_nuke, 1), "PFP", "DCD", "PFP", 'P', DESH.plateCast(), 'D', ModBlocks.det_charge, 'C', ModItems.man_core, 'F', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CONTROLLER));
		addRecipeAuto(new ItemStack(ModBlocks.det_bale, 1), "DAP", "DCD", "DBD", 'D', TI.plateCast(), 'A', ModItems.powder_power, 'B', ModItems.powder_magic, 'C', ModItems.egg_balefire, 'P', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CONTROLLER_ADVANCED));
		addRecipeAuto(new ItemStack(ModBlocks.det_miner, 3), "FFF", "ITI", "ITI", 'F', Items.FLINT, 'I', IRON.plate(), 'T', Blocks.TNT);
		addRecipeAuto(new ItemStack(ModBlocks.det_miner, 12), "FFF", "ITI", "ITI", 'F', Items.FLINT, 'I', STEEL.plate(), 'T', ANY_PLASTICEXPLOSIVE.ingot());

		reg3();
	}

	public static void reg3(){
		addRecipeAuto(new ItemStack(ModBlocks.ladder_sturdy, 8), "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', KEY_PLANKS);
		addRecipeAuto(new ItemStack(ModBlocks.ladder_iron, 8), "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', IRON.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.ladder_gold, 8), "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', GOLD.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.ladder_aluminium, 8), "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', AL.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.ladder_copper, 8), "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', CU.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.ladder_titanium, 8), "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', TI.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.ladder_lead, 8), "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', PB.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.ladder_cobalt, 8), "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', CO.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.ladder_steel, 8), "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.ladder_tungsten, 8), "LLL", "L#L", "LLL", 'L', Blocks.LADDER, '#', W.ingot());

		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe, 6), "PPP", 'P', ModItems.hull_small_steel);
		addShapelessAuto(new ItemStack(ModBlocks.deco_pipe, 1), ModBlocks.deco_pipe_rim);
		addShapelessAuto(new ItemStack(ModBlocks.deco_pipe, 1), ModBlocks.deco_pipe_framed);
		addShapelessAuto(new ItemStack(ModBlocks.deco_pipe, 1), ModBlocks.deco_pipe_quad);
		addShapelessAuto(new ItemStack(ModBlocks.deco_rbmk, 8), ModBlocks.rbmk_blank);
		addShapelessAuto(new ItemStack(ModBlocks.deco_rbmk_smooth, 1), ModBlocks.deco_rbmk);
		

		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', STEEL.plate());
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad, 4), "PP", "PP", 'P', ModBlocks.deco_pipe);
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', Blocks.IRON_BARS);
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', Blocks.IRON_BARS);

		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rusted, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', IRON.dust());
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_rusted, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', IRON.dust());
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_rusted, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad, 'C', IRON.dust());
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_rusted, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed, 'C', IRON.dust());
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_green, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', "dyeGreen");
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_green, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', "dyeGreen");
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_green, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad, 'C', "dyeGreen");
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_green, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed, 'C', "dyeGreen");
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_green_rusted, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_green, 'C', IRON.dust());
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_green_rusted, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim_green, 'C', IRON.dust());
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_green_rusted, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad_green, 'C', IRON.dust());
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_green_rusted, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed_green, 'C', IRON.dust());
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_red, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', "dyeRed");
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_red, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', "dyeRed");
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_red, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad, 'C', "dyeRed");
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_red, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed, 'C', "dyeRed");
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_marked, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_green, 'C', "dyeGreen");
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_marked, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim_green, 'C', "dyeGreen");
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_marked, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad_green, 'C', "dyeGreen");
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_marked, 8), "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed_green, 'C', "dyeGreen");



		addShapelessAuto(new ItemStack(ModItems.ingot_smore), Items.WHEAT, new ItemStack(ModItems.marshmallow_roasted, 1), new ItemStack(Items.DYE, 1, 3));
		addShapelessAuto(new ItemStack(ModItems.marshmallow), Items.STICK, Items.SUGAR, Items.WHEAT_SEEDS);

		addRecipeAuto(new ItemStack(ModItems.coltass, 1), "ACA", "CXC", "ACA", 'A', ALLOY.ingot(), 'C', ModItems.cinnebar, 'X', Items.COMPASS);
		addRecipeAuto(new ItemStack(ModItems.bismuth_tool, 1), "TBT", "SRS", "SCS", 'T',TA.nugget(), 'B', ANY_BISMOID.nugget(), 'S', TCALLOY.ingot(), 'R', ModItems.reacher, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP));
		addRecipeAuto(new ItemStack(ModItems.reacher, 1), "BIB", "P P", "B B", 'B', new ItemStack(ModItems.bolt, 1, MAT_STEEL.id), 'I', W.ingot(), 'P', ANY_RUBBER.ingot());

		addRecipeAuto(new ItemStack(ModBlocks.depth_brick, 4), "CC", "CC", 'C', ModBlocks.stone_depth);
		addRecipeAuto(new ItemStack(ModBlocks.depth_tiles, 4), "CC", "CC", 'C', ModBlocks.depth_brick);
		addRecipeAuto(new ItemStack(ModBlocks.depth_nether_brick, 4), "CC", "CC", 'C', ModBlocks.stone_depth_nether);
		addRecipeAuto(new ItemStack(ModBlocks.depth_nether_tiles, 4), "CC", "CC", 'C', ModBlocks.depth_nether_brick);
		addRecipeAuto(new ItemStack(ModBlocks.basalt_polished, 4), "CC", "CC", 'C', ModBlocks.basalt_smooth);
		addRecipeAuto(new ItemStack(ModBlocks.basalt_brick, 4), "CC", "CC", 'C', ModBlocks.basalt_polished);
		addRecipeAuto(new ItemStack(ModBlocks.basalt_tiles, 4), "CC", "CC", 'C', ModBlocks.basalt_brick);
		addRecipeAuto(new ItemStack(ModBlocks.barrel_tcalloy, 1), "IPI", "I I", "IPI", 'I', TCALLOY.ingot(), 'P', TI.plate());



		addRecipeAuto(new ItemStack(ModItems.inf_water_mk2, 1), "BPB", "PTP", "BPB", 'B', ModItems.inf_water, 'P', ModBlocks.fluid_duct_mk2, 'T', ModBlocks.barrel_steel);
		addRecipeAuto(new ItemStack(ModItems.inf_water_mk3, 1), "BPB", "PTP", "BPB", 'B', ModItems.inf_water_mk2, 'P', ModBlocks.fluid_duct_mk2, 'T', ModBlocks.machine_fluidtank);
		addRecipeAuto(new ItemStack(ModItems.inf_water_mk4, 1), "BPB", "PTP", "BPB", 'B', ModItems.inf_water_mk3, 'P', ModBlocks.fluid_duct_mk2, 'T', ModBlocks.machine_bat9000);

		addRecipeAuto(new ItemStack(ModBlocks.machine_condenser), "SIS", "ICI", "SIS", 'S', STEEL.ingot(), 'I', IRON.plate(), 'C', CU.plateCast());
		addRecipeAuto(new ItemStack(ModBlocks.machine_storage_drum), "LLL", "L#L", "LLL", 'L', PB.plate(), '#', ModItems.tank_steel);

		addRecipeAuto(new ItemStack(ModItems.battery_sc_uranium), "NBN", "PCP", "NBN", 'N', GOLD.nugget(), 'B', U238.billet(), 'P', PB.plate(), 'C', ModItems.thermo_element);
		addRecipeAuto(new ItemStack(ModItems.battery_sc_technetium), "NBN", "PCP", "NBN", 'N', GOLD.nugget(), 'B',TC99.billet(), 'P', PB.plate(), 'C', ModItems.battery_sc_uranium);
		addRecipeAuto(new ItemStack(ModItems.battery_sc_plutonium), "NBN", "PCP", "NBN", 'N', TC99.nugget(), 'B', PU238.billet(), 'P', PB.plate(), 'C', ModItems.battery_sc_technetium);
		addRecipeAuto(new ItemStack(ModItems.battery_sc_polonium), "NBN", "PCP", "NBN", 'N', TC99.nugget(), 'B', PO210.billet(), 'P', ANY_PLASTIC.ingot(), 'C', ModItems.battery_sc_plutonium);
		addRecipeAuto(new ItemStack(ModItems.battery_sc_gold), "NBN", "PCP", "NBN", 'N',TA.nugget(), 'B', AU198.billet(), 'P', ANY_PLASTIC.ingot(), 'C', ModItems.battery_sc_polonium);
		addRecipeAuto(new ItemStack(ModItems.battery_sc_lead), "NBN", "PCP", "NBN", 'N',TA.nugget(), 'B', PB209.billet(), 'P', ANY_PLASTIC.ingot(), 'C', ModItems.battery_sc_gold);
		addRecipeAuto(new ItemStack(ModItems.battery_sc_americium), "NBN", "PCP", "NBN", 'N',TA.nugget(), 'B', AM241.billet(), 'P', ANY_PLASTIC.ingot(), 'C', ModItems.battery_sc_lead);
		addRecipeAuto(new ItemStack(ModItems.battery_sc_balefire), "NBN", "PCP", "NBN", 'N', ModItems.nugget_radspice, 'B', ModItems.pellet_rtg_balefire, 'P', ANY_PLASTIC.ingot(), 'C', ModItems.battery_sc_americium);
		addRecipeAuto(new ItemStack(ModItems.battery_sc_schrabidium), "NBN", "PCP", "NBN", 'N', ModItems.nugget_unobtainium_greater, 'B', SA326.billet(), 'P', ANY_PLASTIC.ingot(), 'C', ModItems.battery_sc_balefire);
		addRecipeAuto(new ItemStack(ModItems.battery_sc_yharonite), "NBN", "PCP", "NBN", 'N', DNT.nugget(), 'B', ModItems.billet_yharonite, 'P', ANY_PLASTIC.ingot(), 'C', ModItems.battery_sc_schrabidium);
		addRecipeAuto(new ItemStack(ModItems.battery_sc_electronium), "NBN", "PCP", "NBN", 'N', ModItems.nugget_u238m2, 'B', ModItems.glitch, 'P', ModItems.ingot_electronium, 'C', ModItems.battery_sc_yharonite);

		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_alloy, 1), "WW", "WW", 'W', new ItemStack(ModItems.wire_dense, 1, MAT_ALLOY.id));
		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_gold, 1), "WG", "GW", 'W', new ItemStack(ModItems.wire_dense, 1, MAT_ALLOY.id), 'G', new ItemStack(ModItems.wire_dense, 1, MAT_GOLD.id));
		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_neodymium, 1), "WG", "GW", 'W', new ItemStack(ModItems.wire_dense, 1, MAT_NEODYMIUM.id), 'G', new ItemStack(ModItems.wire_dense, 1, MAT_GOLD.id));
		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_magtung, 1), "WG", "GW", 'W', new ItemStack(ModItems.wire_dense, 1, MAT_MAGTUNG.id), 'G', new ItemStack(ModItems.wire_dense, 1, MAT_BSCCO.id));
		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_schrabidium, 1), "WG", "GW", 'W', new ItemStack(ModItems.wire_dense, 1, MAT_MAGTUNG.id), 'G', new ItemStack(ModItems.wire_dense, 1, MAT_SCHRABIDIUM.id));
		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_schrabidate, 1), "WG", "GW", 'W', new ItemStack(ModItems.wire_dense, 1, MAT_SCHRABIDATE.id), 'G', new ItemStack(ModItems.wire_dense, 1, MAT_SCHRABIDIUM.id));
		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_starmetal, 1), "WG", "GW", 'W', new ItemStack(ModItems.wire_dense, 1, MAT_SCHRABIDATE.id), 'G', new ItemStack(ModItems.wire_dense, 1, MAT_STAR.id));
		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_chlorophyte, 1), "WG", "GW", 'W', new ItemStack(ModItems.wire_dense, 1, MAT_TUNGSTEN.id), 'G', ModItems.powder_chlorophyte);

		addRecipeAuto(new ItemStack(ModItems.flywheel_beryllium, 1), "IBI", "BTB", "IBI", 'B', BE.block(), 'I', IRON.plateCast(), 'T', DURA.pipe());

		addShapelessAuto(new ItemStack(ModItems.siox, 8), COAL.dust(), ASBESTOS.dust(), ANY_BISMOID.nugget());
		addShapelessAuto(new ItemStack(ModItems.xanax, 1), COAL.dust(), KNO.dust(), BR.dust());
		addShapelessAuto(new ItemStack(ModItems.fmn, 1), COAL.dust(), PO210.dust(), SR.dust());
		addShapelessAuto(new ItemStack(ModItems.five_htp, 1), COAL.dust(), EUPH.dust(), ModItems.canteen_fab);

		addRecipeAuto(new ItemStack(ModBlocks.steel_grate, 4), "SS", "SS", 'S', ModBlocks.steel_beam);

		addRecipeAuto(new ItemStack(ModItems.pile_rod_uranium, 1), " U ", "PUP", " U ", 'P', IRON.plate(), 'U', U.billet());
		addRecipeAuto(new ItemStack(ModItems.pile_rod_plutonium, 1), " U ", "PUP", " U ", 'P', IRON.plate(), 'U', PU.billet());
		addRecipeAuto(new ItemStack(ModItems.pile_rod_source, 1), " U ", "PUP", " U ", 'P', IRON.plate(), 'U', ModItems.billet_ra226be);
		addRecipeAuto(new ItemStack(ModItems.pile_rod_boron, 1), "B", "W", "B", 'B', B.ingot(), 'W', KEY_PLANKS);

		addRecipeAuto(new ItemStack(ModItems.rbmk_fuel_empty, 1), "ZRZ", "Z Z", "ZRZ", 'Z', ZR.ingot(), 'R', ModItems.rod_quad_empty);
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
		addRBMKRod(ModItems.billet_unobtainium, ModItems.rbmk_fuel_unobtainium);
		addRBMKRod(ModItems.egg_balefire_shard, ModItems.rbmk_fuel_balefire);
		addRBMKRod(ModItems.billet_les, ModItems.rbmk_fuel_les);
		addRBMKRod(ModItems.billet_schrabidium_fuel, ModItems.rbmk_fuel_mes);
		addRBMKRod(ModItems.billet_hes, ModItems.rbmk_fuel_hes);
		addRBMKRod(ModItems.billet_balefire_gold, ModItems.rbmk_fuel_balefire_gold);
		addRBMKRod(ModItems.billet_flashlead, ModItems.rbmk_fuel_flashlead);
		addRBMKRod(ModItems.billet_zfb_bismuth, ModItems.rbmk_fuel_zfb_bismuth);
		addRBMKRod(ModItems.billet_zfb_pu241, ModItems.rbmk_fuel_zfb_pu241);
		addRBMKRod(ModItems.billet_zfb_am_mix, ModItems.rbmk_fuel_zfb_am_mix);
		addShapelessAuto(new ItemStack(ModItems.rbmk_fuel_drx, 1), ModItems.rbmk_fuel_balefire, ModItems.particle_digamma);

		addRecipeAuto(new ItemStack(ModItems.rbmk_lid, 4), "PPP", "CCC", "PPP", 'P', STEEL.plate(), 'C', ModBlocks.concrete_asbestos);
		addRecipeAuto(new ItemStack(ModItems.rbmk_lid_glass, 4), "LLL", "BBB", "P P", 'P', STEEL.plate(), 'L', ModBlocks.glass_lead, 'B', ModBlocks.glass_boron);
		addRecipeAuto(new ItemStack(ModItems.rbmk_lid_glass, 4), "BBB", "LLL", "P P", 'P', STEEL.plate(), 'L', ModBlocks.glass_lead, 'B', ModBlocks.glass_boron);

		addRecipeAuto(new ItemStack(ModBlocks.rbmk_moderator, 1), " G ", "GRG", " G ", 'G', ModBlocks.block_graphite, 'R', ModBlocks.rbmk_blank);
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_absorber, 1), "GGG", "GRG", "GGG", 'G', B.ingot(), 'R', ModBlocks.rbmk_blank);
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_reflector, 1), "GGG", "GRG", "GGG", 'G', ModItems.neutron_reflector, 'R', ModBlocks.rbmk_blank);
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_control, 1), " B ", "GRG", " B ", 'G', GRAPHITE.ingot(), 'B', ModItems.motor, 'R', ModBlocks.rbmk_absorber);
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_control_mod, 1), "BGB", "GRG", "BGB", 'G', ModBlocks.block_graphite, 'R', ModBlocks.rbmk_control, 'B', ANY_BISMOID.nugget());
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_control_auto, 1), "C", "R", "D", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'R', ModBlocks.rbmk_control, 'D', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CRT_TUBE));
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_rod_reasim, 1), "ZCZ", "ZRZ", "ZCZ", 'C', ModItems.hull_small_steel, 'R', ModBlocks.rbmk_blank, 'Z', ZR.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_rod_reasim_mod, 1), "BGB", "GRG", "BGB", 'G', ModBlocks.block_graphite, 'R', ModBlocks.rbmk_rod_reasim, 'B', TCALLOY.ingot());
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_outgasser, 1), "GHG", "GRG", "GTG", 'G', ModBlocks.steel_grate, 'H', Blocks.HOPPER, 'T', ModItems.tank_steel, 'R', ModBlocks.rbmk_blank);
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_storage, 1), "C", "R", "C", 'C', ModBlocks.crate_steel, 'R', ModBlocks.rbmk_blank);
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_loader, 1), "SCS", "CBC", "SCS", 'S', STEEL.plate(), 'C', CU.ingot(), 'B', ModItems.tank_steel);
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_steam_inlet, 1), "SCS", "CBC", "SCS", 'S', STEEL.ingot(), 'C', IRON.plate(), 'B', ModItems.tank_steel);
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_steam_outlet, 1), "SCS", "CBC", "SCS", 'S', STEEL.ingot(), 'C', CU.plate(), 'B', ModItems.tank_steel);
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_cooler, 1), "IGI", "GCG", "IGI", 'C', ModBlocks.rbmk_blank, 'I', ANY_RUBBER.ingot(), 'G', ModBlocks.steel_grate);
		
		addRecipeAuto(new ItemStack(ModBlocks.anvil_iron, 1), "III", " B ", "III", 'I', IRON.ingot(), 'B', IRON.block());
		addRecipeAuto(new ItemStack(ModBlocks.anvil_lead, 1), "III", " B ", "III", 'I', PB.ingot(), 'B', PB.block());
		addRecipeAuto(new ItemStack(ModBlocks.machine_fraction_tower), "S", "G", "S", 'S', STEEL.plateWelded(), 'G', ModBlocks.steel_grate);
		addRecipeAuto(new ItemStack(ModBlocks.fraction_spacer), "BHB", 'H', ModItems.hull_big_steel, 'B', Blocks.IRON_BARS);
		addRecipeAuto(new ItemStack(ModBlocks.furnace_iron), "III", "IFI", "BBB", 'I', IRON.ingot(), 'F', Blocks.FURNACE, 'B', Blocks.STONEBRICK);
		addRecipeAuto(new ItemStack(ModBlocks.machine_mixer), "PIP", "GCG", "PMP", 'P', STEEL.plate(), 'I', DURA.ingot(), 'G', KEY_ANYPANE, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), 'M', ModItems.motor);

		//Cladding
		addShapelessAuto(new ItemStack(ModItems.cladding_paint, 1), PB.nugget(), PB.nugget(), PB.nugget(), PB.nugget(), Items.CLAY_BALL, Items.GLASS_BOTTLE);
		addRecipeAuto(new ItemStack(ModItems.cladding_rubber, 1), "RCR", "CDC", "RCR", 'R', ANY_RUBBER.ingot(), 'C', COAL.dust(), 'D', ModItems.ducttape);
		addRecipeAuto(new ItemStack(ModItems.cladding_lead, 1), "DPD", "PRP", "DPD", 'R', ModItems.cladding_rubber, 'P', PB.plate(), 'D', ModItems.ducttape);
		addRecipeAuto(new ItemStack(ModItems.cladding_desh, 1), "DPD", "PRP", "DPD", 'R', ModItems.cladding_lead, 'P',ModItems.plate_desh, 'D', ModItems.ducttape);
		addRecipeAuto(new ItemStack(ModItems.cladding_paa, 1), "DPD", "PRP", "DPD", 'R', ModItems.cladding_desh, 'P', ModItems.plate_paa, 'D', ModItems.ducttape);
		addRecipeAuto(new ItemStack(ModItems.cladding_ghiorsium, 1), "DPD", "PRP", "DPD", 'R', ModItems.cladding_paa, 'P', GH336.ingot(), 'D', ModItems.ducttape);
		addRecipeAuto(new ItemStack(ModItems.cladding_euphemium, 1), "DPD", "PRP", "DPD", 'R', ModItems.cladding_ghiorsium, 'P', ModItems.plate_euphemium, 'D', ModItems.ducttape);
		addRecipeAuto(new ItemStack(ModItems.cladding_di, 1), "DPD", "PRP", "DPD", 'R', ModItems.cladding_euphemium, 'P', ModItems.plate_dineutronium, 'D', ModItems.ducttape);
		addRecipeAuto(new ItemStack(ModItems.cladding_electronium, 1), "DPD", "PRP", "DPD", 'R', ModItems.cladding_di, 'P', ModItems.ingot_electronium, 'D', ModItems.ducttape);
		addRecipeAuto(new ItemStack(ModItems.cladding_obsidian, 1), "OOO", "PDP", "OOO", 'O', Blocks.OBSIDIAN, 'P', STEEL.plate(), 'D', ModItems.ducttape);
		addRecipeAuto(new ItemStack(ModItems.cladding_iron, 1), "OOO", "PDP", "OOO", 'O', IRON.plate(), 'P', ANY_RUBBER.ingot(), 'D', ModItems.ducttape);

		//Inserts
		addRecipeAuto(new ItemStack(ModItems.insert_steel, 1), "DPD", "PSP", "DPD", 'D', ModItems.ducttape, 'P', IRON.plate(), 'S', STEEL.block());
		addRecipeAuto(new ItemStack(ModItems.insert_du, 1), "DPD", "PSP", "DPD", 'D', ModItems.ducttape, 'P', IRON.plate(), 'S', U238.block());
		addRecipeAuto(new ItemStack(ModItems.insert_ferrouranium, 1), "PIP", "IDI", "PIP", 'D', ModItems.insert_kevlar, 'P', ModItems.ducttape, 'I', FERRO.ingot());
		addRecipeAuto(new ItemStack(ModItems.insert_polonium, 1), "DPD", "PSP", "DPD", 'D', ModItems.ducttape, 'P', IRON.plate(), 'S', PO210.block());
		addRecipeAuto(new ItemStack(ModItems.insert_era, 1), "DPD", "PSP", "DPD", 'D', ModItems.ducttape, 'P', IRON.plate(), 'S', ANY_PLASTICEXPLOSIVE.ingot());
		addRecipeAuto(new ItemStack(ModItems.insert_kevlar, 1), "KIK", "IDI", "KIK", 'K', ModItems.plate_kevlar, 'I', ANY_RUBBER.ingot(), 'D', ModItems.ducttape);
		addRecipeAuto(new ItemStack(ModItems.insert_sapi, 1), "PKP", "DPD", "PKP", 'P', ANY_PLASTIC.ingot(), 'K', ModItems.insert_kevlar, 'D', ModItems.ducttape);
		addRecipeAuto(new ItemStack(ModItems.insert_esapi, 1), "PKP", "DSD", "PKP", 'P', ANY_PLASTIC.ingot(), 'K', ModItems.insert_sapi, 'D', ModItems.ducttape, 'S', BIGMT.plate());
		addRecipeAuto(new ItemStack(ModItems.insert_xsapi, 1), "PKP", "DSD", "PKP", 'P', ASBESTOS.ingot(), 'K', ModItems.insert_esapi, 'D', ModItems.ducttape, 'S', ModItems.ingot_meteorite_forged);
		addRecipeAuto(new ItemStack(ModItems.insert_ghiorsium, 1), "PKP", "KSK", "PKP", 'P', ModItems.ducttape, 'K', GH336.ingot(), 'S', ModItems.insert_kevlar);
		addRecipeAuto(new ItemStack(ModItems.insert_di, 1), "PKP", "KSK", "PKP", 'P', ModItems.ducttape, 'K', ModItems.plate_dineutronium, 'S', ModItems.insert_ghiorsium);
		addRecipeAuto(new ItemStack(ModItems.insert_yharonite, 1), "YIY", "IYI", "YIY", 'Y', ModItems.billet_yharonite, 'I', ModItems.insert_du);

		//Servos
		addRecipeAuto(new ItemStack(ModItems.servo_set, 1), "MBM", "PBP", "MBM", 'M', ModItems.motor, 'B', new ItemStack(ModItems.bolt, 1, MAT_STEEL.id), 'P', IRON.plate());
		addRecipeAuto(new ItemStack(ModItems.servo_set_desh, 1), "MBM", "PSP", "MBM", 'M', ModItems.motor_desh, 'B', new ItemStack(ModItems.bolt, 1, MAT_DURA.id), 'P', ALLOY.plate(), 'S', ModItems.servo_set);

		//Helmet Mods
		addRecipeAuto(new ItemStack(ModItems.attachment_mask, 1), "DID", "IGI", " F ", 'D', ModItems.ducttape, 'I', ANY_RUBBER.ingot(), 'G', KEY_ANYPANE, 'F', IRON.plate());
		addRecipeAuto(new ItemStack(ModItems.attachment_mask_mono, 1), " D ", "DID", " F ", 'D', ModItems.ducttape, 'I', ANY_RUBBER.ingot(), 'F', IRON.plate());

		//Boot Mods
		addRecipeAuto(new ItemStack(ModItems.pads_rubber, 1), "P P", "IDI", "P P", 'P', ANY_RUBBER.ingot(), 'I', IRON.plate(), 'D', ModItems.ducttape);
		addRecipeAuto(new ItemStack(ModItems.pads_slime, 1), "SPS", "DSD", "SPS", 'S', KEY_SLIME, 'P', ModItems.pads_rubber, 'D', ModItems.ducttape);
		addRecipeAuto(new ItemStack(ModItems.pads_static, 1), "CDC", "ISI", "CDC", 'C', CU.plate(), 'D', ModItems.ducttape, 'I', ModItems.plate_polymer, 'S', ModItems.pads_slime);

		//Batteries
		addRecipeAuto(new ItemStack(ModItems.armor_battery, 1), "W W", "PCP", "PCP", 'W', GOLD.wire(), 'P', AL.plateCast(), 'C', ModBlocks.machine_desh_battery);
		addRecipeAuto(new ItemStack(ModItems.armor_battery_mk2, 1), "W W", "PCP", "PCP", 'W', GOLD.wire(), 'P', STEEL.plateCast(), 'C', ModBlocks.machine_saturnite_battery);
		addRecipeAuto(new ItemStack(ModItems.armor_battery_mk3, 1), "W W", "PCP", "PCP", 'W', GOLD.wire(), 'P', GOLD.plateCast(), 'C', ModBlocks.machine_schrabidium_battery);
		addRecipeAuto(new ItemStack(ModItems.armor_battery_mk4, 1), "W W", "PCP", "PCP", 'W', GOLD.wire(), 'P', ModItems.plate_euphemium, 'C', ModBlocks.machine_euphemium_battery);

		//Special Mods
		addRecipeAuto(new ItemStack(ModItems.horseshoe_magnet, 1), "L L", "I I", "ILI", 'L', ModItems.lodestone, 'I', IRON.ingot());
		addRecipeAuto(new ItemStack(ModItems.industrial_magnet, 1), "SMS", " B ", "SMS", 'S', STEEL.ingot(), 'M', ModItems.horseshoe_magnet, 'B', ModBlocks.fusion_conductor);
		addRecipeAuto(new ItemStack(ModItems.heart_container, 1), "HAH", "ACA", "HAH", 'H', ModItems.heart_piece, 'A', AL.ingot(), 'C', ModItems.coin_creeper);
		addRecipeAuto(new ItemStack(ModItems.heart_booster, 1), "GHG", "MCM", "GHG", 'G', GOLD.ingot(), 'H', ModItems.heart_container, 'M', ModItems.morning_glory, 'C', ModItems.coin_maskman);
		addRecipeAuto(new ItemStack(ModItems.heart_fab, 1), "GHG", "MCM", "GHG", 'G', PO210.billet(), 'H', ModItems.heart_booster, 'M', ModItems.ingot_euphemium, 'C', ModItems.coin_worm);
		addRecipeAuto(new ItemStack(ModItems.ink, 1), "FPF", "PIP", "FPF", 'F', new ItemStack(Blocks.RED_FLOWER, 1, OreDictionary.WILDCARD_VALUE), 'P', ModItems.armor_polish, 'I', "dyeBlack");
		addRecipeAuto(new ItemStack(ModItems.bathwater_mk2, 1), "MWM", "WBW", "MWM", 'M', ModItems.bottle_mercury, 'W', ModItems.nuclear_waste, 'B', ModItems.bathwater);
		addRecipeAuto(new ItemStack(ModItems.bathwater_mk3, 1), "MWM", "WBW", "MWM", 'M', ModBlocks.block_corium_cobble, 'W', ModItems.powder_radspice, 'B', ModItems.bathwater_mk2);
		addRecipeAuto(new ItemStack(ModItems.back_tesla, 1), "DGD", "GTG", "DGD", 'D', ModItems.ducttape, 'G', new ItemStack(ModItems.wire, 1, MAT_GOLD.id), 'T', ModBlocks.tesla);
		addRecipeAuto(new ItemStack(ModItems.medal_liquidator, 1), "GBG", "BFB", "GBG", 'G',AU198.nugget(), 'B', B.ingot(), 'F', ModItems.debris_fuel);
		addRecipeAuto(new ItemStack(ModItems.medal_ghoul, 1), "GEG", "BFB", "GEG", 'G',ModItems.nugget_u238m2, 'B', ModBlocks.pribris_digamma, 'E', ModItems.glitch, 'F', ModItems.medal_liquidator);
		addShapelessAuto(new ItemStack(ModItems.injector_5htp, 1), ModItems.five_htp, DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), BIGMT.plate());
		addShapelessAuto(new ItemStack(ModItems.injector_knife, 1), ModItems.injector_5htp, Items.IRON_SWORD);
		addRecipeAuto(new ItemStack(ModItems.shackles, 1), "CIC", "C C", "I I", 'I', ModItems.ingot_chainsteel, 'C', ModBlocks.chain);
		addRecipeAuto(new ItemStack(ModItems.black_diamond, 1), "NIN", "IGI", "NIN", 'N',AU198.nugget(), 'I', ModItems.ink, 'G', VOLCANIC.gem());
		addRecipeAuto(new ItemStack(ModItems.protection_charm, 1), " M ", "MDM", " M ", 'M', ModItems.fragment_meteorite, 'D', DIAMOND.gem());
		addRecipeAuto(new ItemStack(ModItems.meteor_charm, 1), " M ", "MDM", " M ", 'M', ModItems.fragment_meteorite, 'D', VOLCANIC.gem());
		addRecipeAuto(new ItemStack(ModItems.neutrino_lens, 1), "PSP", "SCS", "PSP", 'P', ANY_PLASTIC.ingot(), 'S', STAR.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BISMOID));
		addRecipeAuto(new ItemStack(ModItems.pocket_ptsd, 1), " R ", "PBP", "PSP", 'R', ModBlocks.machine_radar, 'P', ANY_PLASTIC.ingot(), 'B', ModItems.battery_sc_polonium, 'S', ModBlocks.machine_siren);
        addRecipeAuto(new ItemStack(ModItems.gas_sensor, 1), "A", "B", "C", 'A', GOLD.plate(), 'B', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), 'C', IRON.plate());
        addShapelessAuto(new ItemStack(ModItems.cladding_paint, 1), PB.dust(), Items.CLAY_BALL, Items.GLASS_BOTTLE);
		addRecipeAuto(new ItemStack(ModItems.cladding_rubber, 1), "RCR", "CDC", "RCR", 'R', ANY_RUBBER.ingot(), 'C', COAL.dust(), 'D', ModItems.ducttape);
		addRecipeAuto(new ItemStack(ModItems.cladding_lead, 1), "DPD", "PRP", "DPD", 'R', ModItems.cladding_rubber, 'P', PB.plate(), 'D', ModItems.ducttape);
		addRecipeAuto(new ItemStack(ModItems.cladding_desh, 1), "DPD", "PRP", "DPD", 'R', ModItems.cladding_lead, 'P',ModItems.plate_desh, 'D', ModItems.ducttape);
		addRecipeAuto(new ItemStack(ModBlocks.struct_plasma_core, 1), "CBC", "BHB", "CBC", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'B', ModBlocks.machine_desh_battery, 'H', ModBlocks.fusion_heater);

		addRecipeAuto(new ItemStack(ModItems.drax, 1), "BDS", "CDC", "FMF", 'B', ModItems.starmetal_pickaxe, 'S', ModItems.starmetal_shovel, 'C', CO.ingot(), 'F', ModItems.fusion_core, 'D', DESH.ingot(), 'M', ModItems.motor_desh);
		addRecipeAuto(new ItemStack(ModItems.drax_mk2, 1), "SCS", "IDI", "FEF", 'S', STAR.ingot(), 'C', ModItems.crystal_trixite, 'I', BIGMT.ingot(), 'D', ModItems.drax, 'F', ModItems.fusion_core, 'E', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR_BOARD));
		addRecipeAuto(new ItemStack(ModItems.drax_mk3, 1), "ECE", "CDC", "SBS", 'E', ModBlocks.block_euphemium_cluster, 'C', SA326.crystal(), 'D', ModItems.drax_mk2, 'S', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.QUANTUM), 'B', ItemBattery.getFullBattery(ModItems.battery_spark));

		addRecipeAuto(new ItemStack(ModItems.cobalt_decorated_sword, 1), " I ", " I ", "SBS", 'I', CO.ingot(), 'S', ModItems.ingot_meteorite_forged, 'B', ModItems.cobalt_sword);
		addRecipeAuto(new ItemStack(ModItems.cobalt_decorated_pickaxe, 1), "III", " B ", " S ", 'I', CO.ingot(), 'S', ModItems.ingot_meteorite_forged, 'B', ModItems.cobalt_pickaxe);
		addRecipeAuto(new ItemStack(ModItems.cobalt_decorated_axe, 1), "II", "IB", " S", 'I', CO.ingot(), 'S', ModItems.ingot_meteorite_forged, 'B', ModItems.cobalt_axe);
		addRecipeAuto(new ItemStack(ModItems.cobalt_decorated_shovel, 1), "I", "B", "S", 'I', CO.ingot(), 'S', ModItems.ingot_meteorite_forged, 'B', ModItems.cobalt_shovel);
		addRecipeAuto(new ItemStack(ModItems.cobalt_decorated_hoe, 1), "II", " B", " S", 'I', CO.ingot(), 'S', ModItems.ingot_meteorite_forged, 'B', ModItems.cobalt_hoe);
		addRecipeAuto(new ItemStack(ModItems.starmetal_sword, 1), " I ", " I ", "SBS", 'I', STAR.ingot(), 'S', CO.ingot(), 'B', ModItems.cobalt_decorated_sword);
        addRecipeAuto(new ItemStack(ModItems.starmetal_shield, 1), "IAI", "III", " I ", 'I', STAR.ingot(), 'A', CO.ingot());
        addRecipeAuto(new ItemStack(ModItems.starmetal_pickaxe, 1), "III", " B ", " S ", 'I', STAR.ingot(), 'S', CO.ingot(), 'B', ModItems.cobalt_decorated_pickaxe);
		addRecipeAuto(new ItemStack(ModItems.starmetal_axe, 1), "II", "IB", " S", 'I', STAR.ingot(), 'S', CO.ingot(), 'B', ModItems.cobalt_decorated_axe);
		addRecipeAuto(new ItemStack(ModItems.starmetal_shovel, 1), "I", "B", "S", 'I', STAR.ingot(), 'S', CO.ingot(), 'B', ModItems.cobalt_decorated_shovel);
		addRecipeAuto(new ItemStack(ModItems.starmetal_hoe, 1), "II", " B", " S", 'I', STAR.ingot(), 'S', CO.ingot(), 'B', ModItems.cobalt_decorated_hoe);

		addRecipeAuto(new ItemStack(ModItems.chlorophyte_pickaxe, 1), " SD", "APS", "FA ", 'S', ModItems.blades_steel, 'D', ModItems.powder_chlorophyte, 'A', FIBER.ingot(), 'P', ModItems.bismuth_pickaxe, 'F', new ItemStack(ModItems.bolt, 1, MAT_DURA.id));
		addRecipeAuto(new ItemStack(ModItems.chlorophyte_pickaxe, 1), " SD", "APS", "FA ", 'S', ModItems.blades_steel, 'D', ModItems.powder_chlorophyte, 'A', FIBER.ingot(), 'P', ModItems.volcanic_pickaxe, 'F', new ItemStack(ModItems.bolt, 1, MAT_DURA.id));
		addRecipeAuto(new ItemStack(ModItems.bismuth_pickaxe, 1), " BM", "BPB", "TB ", 'B', ANY_BISMOID.ingot(), 'M', ModItems.ingot_meteorite, 'P', ModItems.starmetal_pickaxe, 'T', new ItemStack(ModItems.bolt, 1, MAT_STEEL.id));
		addRecipeAuto(new ItemStack(ModItems.volcanic_pickaxe, 1), " BM", "BPB", "TB ", 'B', VOLCANIC.gem(), 'M', ModItems.ingot_meteorite, 'P', ModItems.starmetal_pickaxe, 'T', new ItemStack(ModItems.bolt, 1, MAT_STEEL.id));
		addRecipeAuto(new ItemStack(ModItems.mese_pickaxe, 1), " SD", "APS", "FA ", 'S', ModItems.blades_desh, 'D', DNT.dust(), 'A', ModItems.plate_paa, 'P', ModItems.chlorophyte_pickaxe, 'F', ModItems.shimmer_handle);

		addRecipeAuto(new ItemStack(ModItems.upgrade_template, 1), "WIW", "PCP", "WIW", 'W', CU.wire(), 'I', IRON.plate(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ANALOG), 'P', ModItems.plate_polymer);
		addRecipeAuto(new ItemStack(ModItems.upgrade_template, 1), "WIW", "PCP", "WIW", 'W', CU.wire(), 'I', ANY_PLASTIC.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'P', ModItems.plate_polymer);

		addRecipeAuto(new ItemStack(ModItems.upgrade_nullifier, 1), "SPS", "PUP", "SPS", 'S', STEEL.plate(), 'P', ModItems.powder_fire, 'U', ModItems.upgrade_template);
		addRecipeAuto(new ItemStack(ModItems.upgrade_smelter, 1), "PHP", "CUC", "DTD", 'P', CU.plate(), 'H', Blocks.HOPPER, 'C', ModItems.coil_tungsten, 'U', ModItems.upgrade_template, 'D', ModItems.coil_copper, 'T', ModBlocks.machine_transformer);
		addRecipeAuto(new ItemStack(ModItems.upgrade_shredder, 1), "PHP", "CUC", "DTD", 'P', ModItems.motor, 'H', Blocks.HOPPER, 'C', ModItems.blades_advanced_alloy, 'U', ModItems.upgrade_smelter, 'D', TI.plate(), 'T', ModBlocks.machine_transformer);
		addRecipeAuto(new ItemStack(ModItems.upgrade_centrifuge, 1), "PHP", "PUP", "DTD", 'P', ModItems.centrifuge_element, 'H', Blocks.HOPPER, 'U', ModItems.upgrade_shredder, 'D', ANY_PLASTIC.ingot(), 'T', ModBlocks.machine_transformer);
		addRecipeAuto(new ItemStack(ModItems.upgrade_crystallizer, 1), "PHP", "CUC", "DTD", 'P', new IngredientNBT2(ItemFluidTank.getFullBarrel(ModForgeFluids.ACID)), 'H', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'C', ModBlocks.barrel_steel, 'U', ModItems.upgrade_centrifuge, 'D', ModItems.motor, 'T', ModBlocks.machine_transformer);
		addRecipeAuto(new ItemStack(ModItems.upgrade_screm, 1), "SUS", "SCS", "SUS", 'S', STEEL.plate(), 'U', ModItems.upgrade_template, 'C', ModItems.crystal_xen);
		
		addRecipeAuto(new ItemStack(ModItems.upgrade_stack_1, 1), " C ", "PUP", " C ", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), 'P', ModItems.piston_pneumatic, 'U', ModItems.upgrade_template);
		addRecipeAuto(new ItemStack(ModItems.upgrade_stack_2, 1), " C ", "PUP", " C ", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR), 'P', ModItems.piston_hydraulic, 'U', new ItemStack(ModItems.upgrade_stack_1));
		addRecipeAuto(new ItemStack(ModItems.upgrade_stack_3, 1), " C ", "PUP", " C ", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP), 'P', ModItems.piston_electro, 'U', new ItemStack(ModItems.upgrade_stack_2));
		addRecipeAuto(new ItemStack(ModItems.upgrade_ejector_1, 1), " C ", "PUP", " C ", 'C', ModItems.plate_copper, 'P', ModItems.motor, 'U', ModItems.upgrade_template);
		addRecipeAuto(new ItemStack(ModItems.upgrade_ejector_2, 1), " C ", "PUP", " C ", 'C', ModItems.plate_gold, 'P', ModItems.motor, 'U', new ItemStack(ModItems.upgrade_ejector_1));
		addRecipeAuto(new ItemStack(ModItems.upgrade_ejector_3, 1), " C ", "PUP", " C ", 'C', ModItems.plate_saturnite, 'P', ModItems.motor, 'U', new ItemStack(ModItems.upgrade_ejector_2));
		
		addRecipeAuto(new ItemStack(ModItems.piston_pneumatic, 4), " I ", "CPC", " I ", 'I', IRON.ingot(), 'C', CU.ingot(), 'P', IRON.plate());
		addRecipeAuto(new ItemStack(ModItems.piston_hydraulic, 4), " I ", "CPC", " I ", 'I', STEEL.ingot(), 'C', TI.ingot(), 'P', new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.LUBRICANT)));
		addRecipeAuto(new ItemStack(ModItems.piston_electro, 4), " I ", "CPC", " I ", 'I', ANY_RESISTANTALLOY.ingot(), 'C', ANY_PLASTIC.ingot(), 'P', ModItems.motor);

		addRecipeAuto(new ItemStack(ModItems.charge_railgun), "PDP", "DDD", "PDP", 'P', STEEL.plate(), 'D', new IngredientNBT2(ItemFluidTank.getFullTank(ModForgeFluids.DEUTERIUM)));


		addShapelessAuto(new ItemStack(ModBlocks.fusion_heater), ModBlocks.fusion_hatch);
		addRecipeAuto(new ItemStack(ModItems.fusion_core), "PAA", "PFC", "PAA", 'P', POLYMER.ingot(), 'F', ModItems.energy_core, 'A', ModItems.plate_paa, 'C', new ItemStack(ModItems.circuit, 1, EnumCircuitType.ADVANCED.ordinal()));
        addShapelessAuto(new ItemStack(ModItems.energy_core), ModItems.fusion_core, ModItems.fuse);

		addRecipeAuto(new ItemStack(ModItems.plate_armor_titanium, 1), "NPN", "PIP", "NPN", 'N', new ItemStack(ModItems.bolt, 1, MAT_STEEL.id), 'P', TI.plate(), 'I', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.plate_armor_lunar, 1), "NPN", "PIP", "NPN", 'N', new ItemStack(ModItems.wire, 1, MAT_MAGTUNG.id), 'P', OreDictManager.getReflector(), 'I', ModItems.plate_armor_hev);

		addRecipeAuto(new ItemStack(ModItems.wood_gavel, 1), "SWS", " R ", " R ", 'S', KEY_SLAB, 'W', KEY_LOG, 'R', "stickWood");
		addRecipeAuto(new ItemStack(ModItems.lead_gavel, 1), "PIP", "IGI", "PIP", 'P', ModItems.pellet_buckshot, 'I', PB.ingot(), 'G', ModItems.wood_gavel);

		addRecipeAuto(new ItemStack(ModItems.mech_key, 1), "MCM", "MKM", "MMM", 'M', ModItems.ingot_meteorite_forged, 'C', ModItems.coin_maskman, 'K', ModItems.key);
		addRecipeAuto(new ItemStack(ModItems.spawn_ufo, 1), "MMM", "DCD", "MMM", 'M', ModItems.ingot_meteorite, 'D', DNT.ingot(), 'C', ModItems.coin_worm);

		addRecipeAuto(new ItemStack(ModItems.particle_empty, 2), "STS", "G G", "STS", 'S', PB.plateCast(), 'T', ModItems.coil_gold, 'G', KEY_ANYPANE);
		addShapelessAuto(new ItemStack(ModItems.particle_copper, 1), ModItems.particle_empty, CU.dust(), ModItems.pellet_charged);
		addShapelessAuto(new ItemStack(ModItems.particle_lead, 1), ModItems.particle_empty, PB.dust(), ModItems.pellet_charged);
		addShapelessAuto(ItemCell.getFullCell(ModForgeFluids.AMAT), ModItems.particle_aproton, ModItems.particle_aelectron, new IngredientNBT2(new ItemStack(ModItems.cell)));
		addShapelessAuto(new ItemStack(ModItems.particle_amat, 1), ModItems.particle_aproton, ModItems.particle_aelectron, ModItems.particle_empty);
		addShapelessAuto(ItemCell.getFullCell(ModForgeFluids.ASCHRAB), ModItems.particle_aschrab, new IngredientNBT2(new ItemStack(ModItems.cell)));
		addShapelessAuto(new ItemStack(ModItems.particle_aschrab), new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.ASCHRAB)), ModItems.particle_empty);
		addShapelessAuto(new ItemStack(ModItems.particle_amat), new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.AMAT)), ModItems.particle_empty);
		addRecipeAuto(new ItemStack(ModItems.capsule_empty, 1), "STS", "GXG", "STS", 'S', ModItems.plate_armor_lunar, 'T', ModItems.coil_advanced_torus, 'G', GH336.ingot(), 'X', ModItems.particle_empty);
		addShapelessAuto(new ItemStack(ModItems.capsule_xen), ModItems.capsule_empty, ModItems.crystal_xen);

		ItemStack infinity = new ItemStack(Items.ENCHANTED_BOOK);
		EnchantmentUtil.addEnchantment(infinity, Enchantments.INFINITY, 1);
		addRecipeAuto(infinity, "SBS", "BDB", "SBS", 'S', ModItems.ammo_50bmg_star, 'B', ModItems.ammo_5mm_star, 'D', ModItems.powder_magic);
		ItemStack unbreaking = new ItemStack(Items.ENCHANTED_BOOK);
		EnchantmentUtil.addEnchantment(unbreaking, Enchantments.UNBREAKING, 3);
		addRecipeAuto(unbreaking, "SBS", "BDB", "SBS", 'S', BIGMT.ingot(), 'B', ModItems.plate_armor_lunar, 'D', ModItems.powder_magic);
		ItemStack thorns = new ItemStack(Items.ENCHANTED_BOOK);
		EnchantmentUtil.addEnchantment(thorns, Enchantments.THORNS, 3);
		addRecipeAuto(thorns, "SBS", "BDB", "SBS", 'S', ModBlocks.barbed_wire, 'B', ModBlocks.spikes, 'D', ModItems.powder_magic);

		addRecipeAuto(new ItemStack(ModBlocks.hadron_core, 1), "CCC", "DSD", "CCC", 'C', ModBlocks.hadron_coil_alloy, 'D', ModBlocks.hadron_diode, 'S', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CONTROLLER_QUANTUM));
		addRecipeAuto(new ItemStack(ModBlocks.hadron_diode, 1), "CIC", "ISI", "CIC", 'C', ModBlocks.hadron_coil_alloy, 'I', STEEL.ingot(), 'S', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED));

		addRecipeAuto(new ItemStack(ModBlocks.hadron_plating, 3), "WW", "WW", 'W', STEEL.plateCast());
		addRecipeAuto(new ItemStack(ModBlocks.hadron_plating, 12), "WW", "WW", 'W', STEEL.plateWelded());
		addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_blue, 1), ModBlocks.hadron_plating, "dyeBlue");
		addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_black, 1), ModBlocks.hadron_plating, "dyeBlack");
		addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_yellow, 1), ModBlocks.hadron_plating, "dyeYellow");
		addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_striped, 1), ModBlocks.hadron_plating, "dyeBlack", "dyeYellow");
		addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_glass, 1), ModBlocks.hadron_plating, KEY_ANYGLASS);
		addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_voltz, 1), ModBlocks.hadron_plating, "dyeRed");
		addRecipeAuto(new ItemStack(ModBlocks.hadron_power, 1), "SDS", "CPC", "SUS", 'S', BIGMT.ingot(), 'U', ModBlocks.machine_transformer, 'D', ModBlocks.machine_transformer_20, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'P', ModBlocks.hadron_plating_blue);
		addRecipeAuto(new ItemStack(ModBlocks.hadron_analysis, 1), "IPI", "PCP", "IPI", 'I', TI.ingot(), 'P', OreDictManager.getReflector(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED));
		addShapelessAuto(new ItemStack(ModBlocks.hadron_analysis_glass, 1), ModBlocks.hadron_analysis, KEY_ANYGLASS);
		addRecipeAuto(new ItemStack(ModBlocks.hadron_access, 1), "IGI", "CRC", "IPI", 'I', ModItems.plate_polymer, 'G', KEY_ANYPANE, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ATOMIC_CLOCK), 'R', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CONTROLLER_ADVANCED), 'P', ModBlocks.hadron_plating_blue);

		addRecipeAuto(new ItemStack(ModItems.coil_copper_torus, 2), " C ", "CPC", " C ", 'P', STEEL.plate(), 'C', ModItems.coil_copper);
		addRecipeAuto(new ItemStack(ModItems.coil_advanced_torus, 2), " C ", "CPC", " C ", 'P', STEEL.plate(), 'C', ModItems.coil_advanced_alloy);
		addRecipeAuto(new ItemStack(ModItems.coil_gold_torus, 2), " C ", "CPC", " C ", 'P', STEEL.plate(), 'C', ModItems.coil_gold);
		addRecipeAuto(new ItemStack(ModBlocks.machine_solar_boiler), "SHS", "DHD", "SHS", 'S', STEEL.ingot(), 'H', ModItems.hull_big_steel, 'D', "dyeBlack");
		addRecipeAuto(new ItemStack(ModBlocks.solar_mirror, 3), "AAA", " B ", "SSS", 'A', AL.plate(), 'B', ModBlocks.steel_beam, 'S', STEEL.ingot());
		addRecipeAuto(new ItemStack(ModItems.mirror_tool), " A ", " IA", "I  ", 'A', AL.ingot(), 'I', IRON.ingot());
		addRecipeAuto(new ItemStack(ModItems.rbmk_tool), " A ", " IA", "I  ", 'A', PB.ingot(), 'I', IRON.ingot());
		addRecipeAuto(new ItemStack(ModItems.hand_drill), " D", "S ", " S", 'D', DURA.ingot(), 'S', Items.STICK);
		addRecipeAuto(new ItemStack(ModItems.hand_drill_desh), " D", "S ", " S", 'D', DESH.ingot(), 'S', ANY_PLASTIC.ingot());
		addRecipeAuto(new ItemStack(ModItems.boltgun), "DPS", " RD", " D ", 'D', DURA.ingot(), 'P', ModItems.piston_pneumatic, 'R', RUBBER.ingot(), 'S', STEEL.shell());

		addRecipeAuto(new ItemStack(ModItems.hev_helmet, 1), "PCP", "PBP", "IFI", 'P', ModItems.plate_armor_hev, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'B', ModItems.cobalt_helmet, 'I', ANY_RUBBER.ingot(), 'F', ModItems.gas_mask_filter);
		addRecipeAuto(new ItemStack(ModItems.hev_plate, 1), "MPM", "IBI", "PPP", 'P', ModItems.plate_armor_hev, 'B', ModItems.cobalt_plate, 'I', ANY_PLASTIC.ingot(), 'M', ModItems.motor_desh);
		addRecipeAuto(new ItemStack(ModItems.hev_legs, 1), "MPM", "IBI", "P P", 'P', ModItems.plate_armor_hev, 'B', ModItems.cobalt_legs, 'I', ANY_PLASTIC.ingot(), 'M', ModItems.motor_desh);
		addRecipeAuto(new ItemStack(ModItems.hev_boots, 1), "P P", "PBP", 'P', ModItems.plate_armor_hev, 'B', ModItems.cobalt_boots);
		addRecipeAuto(new ItemStack(ModItems.plate_armor_hev, 1), "NPN", "AIA", "NPN", 'N', new ItemStack(ModItems.wire, 1, MAT_TUNGSTEN.id), 'P', ALLOY.plate(), 'I', ModItems.plate_armor_ajr, 'A', ModItems.plate_paa);

		addRecipeAuto(new ItemStack(ModBlocks.machine_detector, 1), "IRI", "CTC", "IRI", 'I', ModItems.plate_polymer, 'R', Items.REDSTONE, 'C', new ItemStack(ModItems.wire, 1, MAT_MINGRADE.id), 'T', ModItems.coil_tungsten);
		addRecipeAuto(new ItemStack(ModItems.meteorite_sword, 1), "  B", "GB ", "SG ", 'B', ModItems.blade_meteorite, 'G', GOLD.plate(), 'S', Items.STICK);
		addShapelessAuto(new ItemStack(ModBlocks.block_tritium), new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.TRITIUM)), new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.TRITIUM)), new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.TRITIUM)), new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.TRITIUM)), new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.TRITIUM)), new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.TRITIUM)), new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.TRITIUM)), new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.TRITIUM)), new IngredientNBT2(ItemCell.getFullCell(ModForgeFluids.TRITIUM)));
		addShapelessAuto(ItemCell.getFullCell(ModForgeFluids.TRITIUM, 9), ModBlocks.block_tritium);
		addRecipeAuto(new ItemStack(ModItems.canteen_fab, 1), "VMV", "MVM", "VMV", 'V', ModItems.canteen_vodka, 'M', ModItems.powder_magic);
		addRecipeAuto(new ItemStack(ModBlocks.fireworks, 1), "PPP", "PPP", "WIW", 'P', Items.PAPER, 'W', KEY_PLANKS, 'I', IRON.ingot());


		addRecipeAuto(new ItemStack(ModItems.hev_battery, 4), " W ", "IEI", "ICI", 'W', new ItemStack(ModItems.wire, 1, MAT_GOLD.id), 'I', ModItems.plate_polymer, 'E', ModItems.powder_power, 'C', CO.dust());
		addRecipeAuto(new ItemStack(ModItems.hev_battery, 4), " W ", "ICI", "IEI", 'W', new ItemStack(ModItems.wire, 1, MAT_GOLD.id), 'I', ModItems.plate_polymer, 'E', ModItems.powder_power, 'C', CO.dust());
		addRecipeAuto(new ItemStack(ModBlocks.chain, 8), "S", "S", "S", 'S', ModBlocks.steel_beam);

		addRecipeAuto(new ItemStack(ModBlocks.spinny_light), " G ", "GFG", "SRS", 'G', KEY_ANYGLASS, 'F', ModItems.fuse, 'S', new ItemStack(Blocks.STONE_SLAB, 1, 0), 'R', REDSTONE.dust());

		addRecipeAuto(new ItemStack(ModItems.jetpack_glider), "CSC", "DJD", "T T", 'J', ModItems.jetpack_boost, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.QUANTUM), 'D', ModItems.plate_desh, 'T', ModItems.thruster_nuclear, 'S', ModItems.motor);

		addRecipeAuto(new ItemStack(ModItems.sliding_blast_door_skin0), "SPS", "DPD", "SPS", 'P', Items.PAPER, 'D', "dye", 'S', STEEL.plate());
		addShapelessAuto(new ItemStack(ModItems.sliding_blast_door_skin1), ModItems.sliding_blast_door_skin0);
		addShapelessAuto(new ItemStack(ModItems.sliding_blast_door_skin2), ModItems.sliding_blast_door_skin1);
		addShapelessAuto(new ItemStack(ModItems.sliding_blast_door_skin0), ModItems.sliding_blast_door_skin2);

		addRecipeAuto(new ItemStack(Items.NAME_TAG), "SB ", "BPB", " BP", 'S', Items.STRING, 'B', KEY_SLIME, 'P', Items.PAPER);
		addRecipeAuto(new ItemStack(Items.NAME_TAG), "SB ", "BPB", " BP", 'S', Items.STRING, 'B', ANY_TAR.any(), 'P', Items.PAPER);
		addRecipeAuto(new ItemStack(ModItems.rag, 4), "SW", "WS", 'S', Items.STRING, 'W', Blocks.WOOL);

        addShapelessAuto(new ItemStack(ModItems.mold_base), new ItemStack(ModItems.mold, 1, OreDictionary.WILDCARD_VALUE));

		//Peas
		addRecipeAuto(new ItemStack(ModItems.peas), " S ", "SNS", " S ", 'S', Items.WHEAT_SEEDS, 'N', GOLD.nugget());


		addRecipeAuto(new ItemStack(ModBlocks.machine_armor_table, 1), "PPP", "TCT", "TST", 'P', STEEL.plate(), 'T', W.ingot(), 'C', Blocks.CRAFTING_TABLE, 'S', STEEL.block());

		addShapelessAuto(new ItemStack(ModItems.ingot_steel_dusted, 1), STEEL.ingot(), COAL.dust());

		addRecipeAuto(new ItemStack(ModItems.demon_core_open, 1), "PRP", " CS", "PRP", 'P', TI.plate(), 'R', OreDictManager.getReflector(), 'C', ModItems.man_core, 'S', ModItems.screwdriver);
		addShapelessAuto(new ItemStack(ModItems.demon_core_open, 1), ModItems.demon_core_closed, ModItems.screwdriver);
		addRecipeAuto(new ItemStack(ModBlocks.lamp_demon, 1), " D ", "S S", 'D', ModItems.demon_core_closed, 'S', STEEL.ingot());

		addRecipeAuto(new ItemStack(ModItems.crucible, 1, 3), "MEM", "YDY", "YCY", 'M', ModItems.ingot_meteorite_forged, 'E', EUPH.ingot(), 'Y', ModItems.billet_yharonite, 'D', ModItems.demon_core_closed, 'C', ModItems.ingot_chainsteel);
		addRecipeAuto(new ItemStack(ModItems.hf_sword), "MEM", "YDY", "YCY", 'M', ModItems.blade_meteorite, 'E', ModItems.ingot_radspice, 'Y', UNOBTAINIUM.billet(), 'D', ModItems.particle_strange, 'C', ModItems.ingot_chainsteel);
		addRecipeAuto(new ItemStack(ModItems.hs_sword), "MEM", "YDY", "YCY", 'M', ModItems.blade_meteorite, 'E', GH336.ingot(), 'Y', ModItems.billet_gh336, 'D', ModItems.particle_dark, 'C', ModItems.ingot_chainsteel);

		for(int i = 0; i < ItemWasteLong.WasteClass.values().length; i++) {
			add1To9PairSameMeta(ModItems.nuclear_waste_long, ModItems.nuclear_waste_long_tiny, i);
			add1To9PairSameMeta(ModItems.nuclear_waste_long_depleted, ModItems.nuclear_waste_long_depleted_tiny, i);
		}

		for(int i = 0; i < ItemWasteShort.WasteClass.values().length; i++) {
			add1To9PairSameMeta(ModItems.nuclear_waste_short, ModItems.nuclear_waste_short_tiny, i);
			add1To9PairSameMeta(ModItems.nuclear_waste_short_depleted, ModItems.nuclear_waste_short_depleted_tiny, i);
		}

		addRecipeAuto(new ItemStack(ModBlocks.fallout, 2), "##", '#', ModItems.fallout);

		addRecipeAuto(new ItemStack(ModItems.ashglasses, 1), "I I", "GPG", 'I', ModItems.plate_polymer, 'G', ModBlocks.glass_ash, 'P', ANY_PLASTIC.ingot());





		addShapelessAuto(new ItemStack(ModBlocks.ladder_red), "dyeRed", ModBlocks.ladder_steel);
		addShapelessAuto(new ItemStack(ModBlocks.ladder_red_top), ModBlocks.ladder_red);
		addRecipeAuto(new ItemStack(ModBlocks.railing_normal), "   ", "SSS", "S S", 'S', ModBlocks.steel_beam);
		addRecipeAuto(new ItemStack(ModBlocks.railing_bend), "   ", "S  ", " S ", 'S', ModBlocks.railing_normal);
		addRecipeAuto(new ItemStack(ModBlocks.railing_end_self, 2), "   ", " SS", "   ", 'S', ModBlocks.railing_normal);
		addRecipeAuto(new ItemStack(ModBlocks.railing_end_floor, 2), "   ", " S ", " S ", 'S', ModBlocks.railing_normal);
		addShapelessAuto(new ItemStack(ModBlocks.railing_end_flipped_self), ModBlocks.railing_end_self);
		addShapelessAuto(new ItemStack(ModBlocks.railing_end_flipped_floor), ModBlocks.railing_end_floor);

		addRecipeAuto(new ItemStack(ModItems.assembly_nuke, 1), " WP", "SEC", " WP", 'W', new ItemStack(ModItems.wire, 1, MAT_ALUMINIUM.id), 'P', STEEL.plate(), 'S', ModItems.hull_small_steel, 'E', ANY_HIGHEXPLOSIVE.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CONTROLLER))	;
		
		//Mini Nuke

		addShapelessAuto(new ItemStack(ModItems.ball_fireclay, 4), Items.CLAY_BALL, Items.CLAY_BALL, Items.CLAY_BALL, AL.dust());
		addShapelessAuto(new ItemStack(ModItems.ball_fireclay, 4), Items.CLAY_BALL, Items.CLAY_BALL, Items.CLAY_BALL, AL.ore());


		if(!GeneralConfig.enable528) {
			addRecipeAuto(new ItemStack(ModBlocks.struct_launcher_core, 1), "SCS", "SIS", "BEB", 'S', ModBlocks.steel_scaffold, 'I', Blocks.IRON_BARS, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'B', ModBlocks.struct_launcher, 'E', ModBlocks.machine_battery);
			addRecipeAuto(new ItemStack(ModBlocks.struct_launcher_core_large, 1), "SIS", "ICI", "BEB", 'S', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'I', Blocks.IRON_BARS, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'B', ModBlocks.struct_launcher, 'E', ModBlocks.machine_battery);
			addRecipeAuto(new ItemStack(ModBlocks.struct_soyuz_core, 1), "CUC", "TST", "TBT", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'U', ModItems.upgrade_power_3, 'T', ModBlocks.barrel_steel, 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.machine_lithium_battery);
			addRecipeAuto(new ItemStack(ModItems.reactor_sensor, 1), "WPW", "CMC", "PPP", 'W', W.wire(), 'P', PB.plate(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'M', ModItems.magnetron);
            addRecipeAuto(new ItemStack(ModBlocks.reactor_ejector, 1), new Object[] { "CLC", "MHM", "CLC", 'C', ModBlocks.brick_concrete, 'L', PB.plateCast(), 'M', ModItems.motor, 'H', ModBlocks.reactor_hatch });
            addRecipeAuto(new ItemStack(ModBlocks.reactor_inserter, 1), new Object[] { "CLC", "MHM", "CLC", 'C', ModBlocks.brick_concrete, 'L', ALLOY.plateCast(), 'M', ModItems.motor, 'H', ModBlocks.reactor_hatch });
            addRecipeAuto(new ItemStack(ModBlocks.rbmk_console, 1), "BBB", "DGD", "DCD", 'B', B.ingot(), 'D', ModBlocks.deco_rbmk, 'G', KEY_ANYPANE, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ANALOG));
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_crane_console, 1), "BCD", "DDD", 'B', B.ingot(), 'D', ModBlocks.deco_rbmk, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ANALOG));
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_rod, 1), "C", "R", "C", 'C', STEEL.shell(), 'R', ModBlocks.rbmk_blank);
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_rod_mod, 1), "BGB", "GRG", "BGB", 'G', GRAPHITE.block(), 'R', ModBlocks.rbmk_rod, 'B', ModItems.nugget_bismuth);
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_boiler, 1), "CPC", "CRC", "CPC", 'C', CU.pipe(), 'P', CU.shell(), 'R', ModBlocks.rbmk_blank);
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_heater, 1), "CIC", "PRP", "CIC", 'C', CU.pipe(), 'P', STEEL.shell(), 'R', ModBlocks.rbmk_blank, 'I', ANY_PLASTIC.ingot());
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_cooler, 1), "IGI", "GCG", "IGI", 'C', ModBlocks.rbmk_blank, 'I', ModItems.plate_polymer, 'G', ModBlocks.steel_grate);
		}

		if(GeneralConfig.enableBabyMode) {
            addRecipeAuto(new ItemStack(ModBlocks.struct_launcher_core, 1), "SCS", "SIS", "BEB", 'S', ModBlocks.steel_scaffold, 'I', Blocks.IRON_BARS, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'B', ModBlocks.struct_launcher, 'E', ModBlocks.machine_battery);
            addRecipeAuto(new ItemStack(ModBlocks.struct_launcher_core_large, 1), "SIS", "ICI", "BEB", 'S', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'I', Blocks.IRON_BARS, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'B', ModBlocks.struct_launcher, 'E', ModBlocks.machine_battery);
            addRecipeAuto(new ItemStack(ModBlocks.struct_soyuz_core, 1), "CUC", "TST", "TBT", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'U', ModItems.upgrade_power_1, 'T', ModBlocks.barrel_steel, 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.machine_lithium_battery);
            addShapelessAuto(new ItemStack(ModItems.cordite, 3), ModItems.ballistite, Items.GUNPOWDER, new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE));
			addShapelessAuto(new ItemStack(ModItems.ingot_semtex, 3), Items.SLIME_BALL, Blocks.TNT, KNO.dust());
			addShapelessAuto(ItemFluidCanister.getFullCanister(ModForgeFluids.DIESEL), new IngredientNBT2(ItemFluidCanister.getFullCanister(ModForgeFluids.OIL)), Items.REDSTONE);

			addShapelessAuto(new ItemStack(ModBlocks.ore_uranium, 1), ModBlocks.ore_uranium_scorched, Items.WATER_BUCKET);
			addRecipeAuto(new ItemStack(ModBlocks.ore_uranium, 8), "OOO", "OBO", "OOO", 'O', ModBlocks.ore_uranium_scorched, 'B', Items.WATER_BUCKET);
			addShapelessAuto(new ItemStack(ModBlocks.ore_nether_uranium, 1), ModBlocks.ore_nether_uranium_scorched, Items.WATER_BUCKET);
			addRecipeAuto(new ItemStack(ModBlocks.ore_nether_uranium, 8), "OOO", "OBO", "OOO", 'O', ModBlocks.ore_nether_uranium_scorched, 'B', Items.WATER_BUCKET);
			addShapelessAuto(new ItemStack(ModBlocks.ore_gneiss_uranium, 1), ModBlocks.ore_gneiss_uranium_scorched, Items.WATER_BUCKET);
			addRecipeAuto(new ItemStack(ModBlocks.ore_gneiss_uranium, 8), "OOO", "OBO", "OOO", 'O', ModBlocks.ore_gneiss_uranium_scorched, 'B', Items.WATER_BUCKET);

			addRecipeAuto(new ItemStack(ModItems.plate_iron, 4), "##", "##", '#', IRON.ingot());
			addRecipeAuto(new ItemStack(ModItems.plate_gold, 4), "##", "##", '#', GOLD.ingot());
			addRecipeAuto(new ItemStack(ModItems.plate_aluminium, 4), "##", "##", '#', AL.ingot());
			addRecipeAuto(new ItemStack(ModItems.plate_titanium, 4), "##", "##", '#', TI.ingot());
			addRecipeAuto(new ItemStack(ModItems.plate_copper, 4), "##", "##", '#', CU.ingot());
			addRecipeAuto(new ItemStack(ModItems.plate_lead, 4), "##", "##", '#', PB.ingot());
			addRecipeAuto(new ItemStack(ModItems.plate_steel, 4), "##", "##", '#', STEEL.ingot());
			addRecipeAuto(new ItemStack(ModItems.plate_schrabidium, 4), "##", "##", '#', SA326.ingot());
			addRecipeAuto(new ItemStack(ModItems.plate_advanced_alloy, 4), "##", "##", '#', ALLOY.ingot());
			addRecipeAuto(new ItemStack(ModItems.plate_saturnite, 4), "##", "##", '#', BIGMT.ingot());
			addRecipeAuto(new ItemStack(ModItems.plate_combine_steel, 4), "##", "##", '#', CMB.ingot());
			addRecipeAuto(new ItemStack(ModItems.neutron_reflector, 4), "##", "##", '#', W.ingot());

			addRecipeAuto(new ItemStack(ModItems.wire, 16, MAT_ALUMINIUM.id), "###", '#', AL.ingot());
			addRecipeAuto(new ItemStack(ModItems.wire, 16, MAT_COPPER.id), "###", '#', CU.ingot());
			addRecipeAuto(new ItemStack(ModItems.wire, 16, MAT_TUNGSTEN.id), "###", '#', W.ingot());
			addRecipeAuto(new ItemStack(ModItems.wire, 16, MAT_MINGRADE.id), "###", '#', MINGRADE.ingot());
			addRecipeAuto(new ItemStack(ModItems.wire, 16, MAT_ALLOY.id), "###", '#', ALLOY.ingot());
			addRecipeAuto(new ItemStack(ModItems.wire, 16, MAT_GOLD.id), "###", '#', GOLD.ingot());
			addRecipeAuto(new ItemStack(ModItems.wire, 16, MAT_SCHRABIDIUM.id), "###", '#', SA326.ingot());

			addRecipeAuto(new ItemStack(ModItems.book_of_), "BGB", "GAG", "BGB", 'B', ModItems.egg_balefire_shard, 'G', GOLD.ingot(), 'A', Items.BOOK);
		}

		addShapelessAuto(new ItemStack(ModItems.drillbit_dnt_diamond, 1), ModItems.drillbit_dnt, new ItemStack(ModItems.ore_bedrock, 1, 3));
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
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_cinnebar), new ItemStack(ModItems.cinnebar), 1.0F);

		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_iron), new ItemStack(Items.IRON_INGOT), 5.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_gold), new ItemStack(Items.GOLD_INGOT), 5.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_uranium), new ItemStack(ModItems.ingot_uranium), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_uranium_scorched), new ItemStack(ModItems.ingot_uranium), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_copper), new ItemStack(ModItems.ingot_copper), 5F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_lithium), new ItemStack(ModItems.lithium), 10F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_schrabidium), new ItemStack(ModItems.ingot_schrabidium), 256.0F);

		GameRegistry.addSmelting(ModItems.casing_357, new ItemStack(ModItems.ingot_copper), 0.1F);
		GameRegistry.addSmelting(ModItems.casing_44, new ItemStack(ModItems.ingot_copper), 0.1F);
		GameRegistry.addSmelting(ModItems.casing_9, new ItemStack(ModItems.ingot_copper), 0.1F);
		GameRegistry.addSmelting(ModItems.casing_50, new ItemStack(ModItems.ingot_copper), 0.1F);
		GameRegistry.addSmelting(ModItems.casing_buckshot, new ItemStack(ModItems.ingot_copper), 0.1F);


		GameRegistry.addSmelting(ModItems.ball_resin, new ItemStack(ModItems.ingot_biorubber), 0.1F);

		GameRegistry.addSmelting(ModItems.rag_damp, new ItemStack(ModItems.rag), 0.1F);
		GameRegistry.addSmelting(ModItems.rag_piss, new ItemStack(ModItems.rag), 0.1F);
		GameRegistry.addSmelting(ModItems.mask_damp, new ItemStack(ModItems.mask_rag), 0.3F);
		GameRegistry.addSmelting(ModItems.mask_piss, new ItemStack(ModItems.mask_rag), 0.3F);
		
		GameRegistry.addSmelting(ModItems.powder_coal, new ItemStack(ModItems.coke), 1.0F);
		GameRegistry.addSmelting(ModItems.briquette_lignite, new ItemStack(ModItems.coke), 1.0F);
		GameRegistry.addSmelting(ModItems.oil_tar, new ItemStack(ModItems.powder_coal), 1.0F);

		GameRegistry.addSmelting(ModItems.combine_scrap, new ItemStack(ModItems.ingot_combine_steel), 1.0F);

		GameRegistry.addSmelting(Items.BONE, new ItemStack(Items.SLIME_BALL, 3), 0.0F);
		GameRegistry.addSmelting(new ItemStack(Items.DYE, 1, 15), new ItemStack(Items.SLIME_BALL, 1), 0.0F);
		GameRegistry.addSmelting(new ItemStack(Blocks.GRAVEL, 1), new ItemStack(Blocks.COBBLESTONE, 1), 0.0F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.gravel_obsidian), new ItemStack(Blocks.OBSIDIAN), 0.0F);

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
		GameRegistry.addSmelting(ModItems.crystal_cinnebar, new ItemStack(ModItems.cinnebar, 2), 2.0F);
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

		GameRegistry.addSmelting(ModItems.ball_fireclay, new ItemStack(ModItems.ingot_firebrick, 1), 0.1F);
	}

	public static void addBedrockOreSmelting(){
		for(Integer oreMeta : BedrockOreRegistry.oreIndexes.keySet()) {
            if(ItemBedrockOre.getOutType(oreMeta) != 1) {
                GameRegistry.addSmelting(new ItemStack(ModItems.ore_bedrock, 1, oreMeta), ItemBedrockOre.getOut(oreMeta, 1), 2F);
                GameRegistry.addSmelting(new ItemStack(ModItems.ore_bedrock_centrifuged, 1, oreMeta), ItemBedrockOre.getOut(oreMeta, 1), 2F);
                GameRegistry.addSmelting(new ItemStack(ModItems.ore_bedrock_separated, 1, oreMeta), ItemBedrockOre.getOut(oreMeta, 1), 2F);
                GameRegistry.addSmelting(new ItemStack(ModItems.ore_bedrock_purified, 1, oreMeta), ItemBedrockOre.getOut(oreMeta, 1), 2F);
                GameRegistry.addSmelting(new ItemStack(ModItems.ore_bedrock_nitrocrystalline, 1, oreMeta), ItemBedrockOre.getOut(oreMeta, 1), 2F);
                GameRegistry.addSmelting(new ItemStack(ModItems.ore_bedrock_exquisite, 1, oreMeta), ItemBedrockOre.getOut(oreMeta, 1), 2F);
                GameRegistry.addSmelting(new ItemStack(ModItems.ore_bedrock_enriched, 1, oreMeta), ItemBedrockOre.getOut(oreMeta, 1), 2F);
            }
		}
	}

	public static void addSlabStair(Block slab, Block stair, Block block){
		addRecipeAuto(new ItemStack(slab, 6), "###", '#', block);
		addRecipeAuto(new ItemStack(stair, 8), "#  ","## ","###", '#', block);
		addShapelessAuto(new ItemStack(block, 3), stair, stair, stair, stair);
		addRecipeAuto(new ItemStack(stair, 4), "#  ","## ","###", '#', slab);
		addShapelessAuto(new ItemStack(block, 1), slab, slab);
	}

	public static void addPellet(DictFrame mat, EnumWatzType num) {
		addRecipeAuto(new ItemStack(ModItems.watz_pellet, 1, num.ordinal()), " I ", "IGI", " I ", 'I', mat.ingot(), 'G', GRAPHITE.ingot());
	}

	public static void addBillet(Item billet, Item nugget){
		addRecipeAuto(new ItemStack(billet), "###", "###", '#', nugget);
		addShapelessAuto(new ItemStack(nugget, 6), billet);
	}

	//Fill rods with one billet
	public static void addRodBillet(Item billet, Item out){
		addShapelessAuto(new ItemStack(out), ModItems.rod_empty, billet);
	}

	public static void addBilletByIngot(Item billet, Item ingot){
		addShapelessAuto(new ItemStack(billet, 3), ingot, ingot);
		addShapelessAuto(new ItemStack(ingot, 2), billet, billet, billet);
	}

	//Fill rods with two billets
	public static void addDualRodBillet(Item billet, Item out){
		addShapelessAuto(new ItemStack(out), ModItems.rod_dual_empty, billet, billet);
	}

	//Fill rods with three billets
	public static void addQuadRodBillet(Item billet, Item out){
		addShapelessAuto(new ItemStack(out), ModItems.rod_quad_empty, billet, billet, billet, billet);
	}

	//Fill rods with one billet + unload
	public static void addRodBilletUnload(Item billet, Item out){
		addShapelessAuto(new ItemStack(out), ModItems.rod_empty, billet);
		addShapelessAuto(new ItemStack(billet, 1), new ItemStack(out, 1, 0));
	}

	//Fill rods with two billets + unload
	public static void addDualRodBilletUnload(Item billet, Item out){
		addShapelessAuto(new ItemStack(out), ModItems.rod_dual_empty, billet, billet);
		addShapelessAuto(new ItemStack(billet, 2), new ItemStack(out, 1, 0));
	}

	//Fill rods with three billets + unload
	public static void addQuadRodBilletUnload(Item billet, Item out){
		addShapelessAuto(new ItemStack(out), ModItems.rod_quad_empty, billet, billet, billet, billet);
		addShapelessAuto(new ItemStack(billet, 4), new ItemStack(out, 1, 0));
	}

	//Fill rods with 6 nuggets
	public static void addRBMKRod(Item billet, Item out){
		addShapelessAuto(new ItemStack(out), ModItems.rbmk_fuel_empty, billet, billet, billet, billet, billet, billet, billet, billet);
	}

	//Bundled 1/9 recipes
	public static void add1To9Pair(Object one, Object nine){
		add1To9(one, nine);
		add9To1(nine, one);
	}

	public static void add1To9PairSameMeta(Item one, Item nine, int meta){
		add1To9SameMeta(one, nine, meta);
		add9To1SameMeta(nine, one, meta);
	}

	public static void addBillet(Item billet, Item nugget, DictFrame mat){
		addRecipeAuto(new ItemStack(billet), "###", "###", '#', mat.nugget());
		addShapelessAuto(new ItemStack(nugget, 6), mat.billet());
	}

	public static void addBilletByIngot(Item billet, Item ingot, DictFrame mat){
		addShapelessAuto(new ItemStack(billet, 3), mat.ingot(), mat.ingot());
		addShapelessAuto(new ItemStack(ingot, 2), mat.billet(), mat.billet(), mat.billet());
	}

	public static ItemStack getFirst(NonNullList<ItemStack> l){
		if(l.isEmpty()) return null;
		return l.get(0);
	}

	//Full set of dust, tinyDust, nugget, billet, ingot and block
	public static void addAll(DictFrame mat){
        if(mat == QUARTZ || mat == NETHERQUARTZ) return;
		ItemStack dustTiny = getFirst(OreDictionary.getOres(mat.dustTiny()));
		ItemStack dust = getFirst(OreDictionary.getOres(mat.dust()));
		ItemStack nugget = getFirst(OreDictionary.getOres(mat.nugget()));
		ItemStack wire = getFirst(OreDictionary.getOres(mat.wire()));
		ItemStack ingot = getFirst(OreDictionary.getOres(mat.ingot()));
		ItemStack gem = getFirst(OreDictionary.getOres(mat.gem()));
		ItemStack block = getFirst(OreDictionary.getOres(mat.block()));
		ItemStack billet = getFirst(OreDictionary.getOres(mat.billet()));

		if(dustTiny != null && dust != null){
			add1To9(mat.dust(), dustTiny);
			add9To1(mat.dustTiny(), dust);
		}
		if(nugget != null && ingot != null){
			add1To9(mat.ingot(), nugget);
			add9To1(mat.nugget(), ingot);
		}
		if(ingot != null && block != null){
			add1To9(mat.block(), ingot);
			add9To1(mat.ingot(), block);
		}
        if(ingot == null && block != null) {
            if(gem != null) {
                add1To9(mat.block(), gem);
                add9To1(mat.gem(), block);
            } else if(dust != null) {
                add1To9(mat.block(), dust);
                add9To1(mat.dust(), block);
            }
        }
		if(ingot != null && wire != null){
			add9To1(mat.wire(), ingot);
		}
		if(nugget != null && billet != null){
			addBillet(billet.getItem(), nugget.getItem(), mat);
		}
		if(ingot != null && billet != null){
			addBilletByIngot(billet.getItem(), ingot.getItem(), mat);
		}
		if(dust != null && ingot != null){
			GameRegistry.addSmelting(dust.getItem(), ingot.copy(), 1.0F);
		}
		if(dustTiny != null && nugget != null){
			GameRegistry.addSmelting(dustTiny.getItem(), nugget.copy(), 1.0F);
		}
	}

	//Compress nine items into one
	public static void add9To1(Object nine, Object one){
		ItemStack output = null;
		if(one instanceof Block)
			output = new ItemStack((Block) one);
		else if(one instanceof Item)
			output = new ItemStack((Item) one);
		else if(one instanceof ItemStack)
			output = ((ItemStack) one).copy();
		if(output != null) addRecipeAuto(output, "###", "###", "###", '#', nine);
	}

	public static void add9To1SameMeta(Item nine, Item one, int meta){
		addRecipeAuto(new ItemStack(one, 1, meta), "###", "###", "###", '#', new ItemStack(nine, 1, meta));
	}


	//Decompress one item into nine
	public static void add1To9(Object one, Object nine){
		ItemStack output = null;
		if(nine instanceof Block)
			output = new ItemStack((Block) nine, 9);
		else if(nine instanceof Item)
			output = new ItemStack((Item) nine, 9);
		else if(nine instanceof ItemStack){
			output = ((ItemStack) nine).copy();
			output.setCount(9);
		}
		if(output != null) addShapelessAuto(output, one);
	}

	public static void add1To9SameMeta(Item one, Item nine, int meta){
		addShapelessAuto(new ItemStack(nine, 9, meta), new ItemStack(one, 1, meta));
	}


	//Fill rods with 6 nuggets
	public static void addRod(Item out, Object nugget){
		addShapelessAuto(new ItemStack(out), ModItems.rod_empty, nugget, nugget, nugget, nugget, nugget, nugget);
	}

	//Fill rods with 12 nuggets
	public static void addDualRod(Item out, Object ingot, Object nugget){
		addShapelessAuto(new ItemStack(out), ModItems.rod_dual_empty, ingot, nugget, nugget, nugget);
	}

	//Fill rods with 24 nuggets
	public static void addQuadRod(Item out, Object ingot, Object nugget){
		addShapelessAuto(new ItemStack(out), ModItems.rod_quad_empty, ingot, ingot, nugget, nugget, nugget, nugget, nugget, nugget);
	}

	public static void addSword(Item ingot, Item sword){
		addRecipeAuto(new ItemStack(sword), "I", "I", "S", 'I', ingot, 'S', Items.STICK);
	}

	public static void addPickaxe(Item ingot, Item pick){
		addRecipeAuto(new ItemStack(pick), "III", " S ", " S ", 'I', ingot, 'S', Items.STICK);
	}

	public static void addAxe(Item ingot, Item axe){
		addRecipeAuto(new ItemStack(axe), "II", "IS", " S", 'I', ingot, 'S', Items.STICK);
	}

	public static void addShovel(Item ingot, Item shovel){
		addRecipeAuto(new ItemStack(shovel), "I", "S", "S", 'I', ingot, 'S', Items.STICK);
	}

	public static void addHoe(Item ingot, Item hoe){
		addRecipeAuto(new ItemStack(hoe), "II", " S", " S", 'I', ingot, 'S', Items.STICK);
	}

	public static void addRecipeAuto(ItemStack output, Object... args){

		boolean shouldUseOD = false;
		boolean patternEnded = false;
        for (Object arg : args) {
            if (arg instanceof String) {
                if (patternEnded) {
                    shouldUseOD = true;
                    break;
                }
            } else {
                patternEnded = true;
            }
        }

		ResourceLocation loc = getRecipeName(output);
		IRecipe recipe;
		if(shouldUseOD){
			recipe = new ShapedOreRecipe(loc, output, args);
		}else {
			CraftingHelper.ShapedPrimer primer = CraftingHelper.parseShaped(args);
			recipe = new ShapedRecipes(output.getItem().getRegistryName().toString(), primer.width, primer.height, primer.input, output);
		}
		recipe.setRegistryName(loc);
		hack.getRegistry().register(recipe);
	}

	public static void addShapelessAuto(ItemStack output, Object... args) {

		boolean shouldUseOD = false;

        for (Object arg : args) {
            if (arg instanceof String) {
                shouldUseOD = true;
                break;
            }
        }

		ResourceLocation loc = getRecipeName(output);
		IRecipe recipe;
		if(shouldUseOD){
			recipe = new ShapelessOreRecipe(loc, output, args);
		}else{
			recipe = new ShapelessRecipes(loc.getNamespace(), output, buildInput(args));
		}
		recipe.setRegistryName(loc);
		hack.getRegistry().register(recipe);
	}

	public static ResourceLocation getRecipeName(ItemStack output){
		ResourceLocation loc = new ResourceLocation(RefStrings.MODID, output.getItem().getRegistryName().getPath());
		int i = 0;
		ResourceLocation r_loc = loc;
		while(net.minecraft.item.crafting.CraftingManager.REGISTRY.containsKey(r_loc)) {
			i++;
			r_loc = new ResourceLocation(RefStrings.MODID, loc.getPath() + "_" + i);
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

	//B r u h why wasn't the constructor visible in the first place?
	public static class IngredientNBT2 extends IngredientNBT {

		public IngredientNBT2(ItemStack stack){
			super(stack);
		}
	}
}
