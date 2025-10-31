package com.hbm.inventory;


import java.util.HashMap;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.ItemEnums;
import com.hbm.items.ModItems;
import com.hbm.util.Tuple.Pair;

import net.minecraftforge.fluids.Fluid;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

public class SolidificationRecipes {

    public static final int SF_OIL =		200;
    public static final int SF_CRACK =		200;
    public static final int SF_HEAVY =		150;
    public static final int SF_BITUMEN =	100;
    public static final int SF_SMEAR =		100;
    public static final int SF_HEATING =	50;
    public static final int SF_RECLAIMED =	100;
    public static final int SF_PETROIL =	125;
    public static final int SF_LUBE =		100;
    public static final int SF_NAPH =		150;
    public static final int SF_DIESEL =		200;
    public static final int SF_LIGHT =		225;
    public static final int SF_KEROSENE =	275;
    public static final int SF_GAS =		375;
    public static final int SF_PETROLEUM =	300;
    public static final int SF_LPG =		150;
    public static final int SF_BIOGAS =		1750;
    public static final int SF_BIOFUEL =	750;
    public static final int SF_COALOIL =	200;
    public static final int SF_CREOSOTE =	200;
    public static final int SF_WOOD =		1000;
    //mostly for alternate chemistry, dump into SF if not desired
    public static final int SF_AROMA =		1000;
    public static final int SF_UNSAT =		1000;
    //in the event that these compounds are STILL too useless, add unsat + gas -> kerosene recipe for all those missile junkies
    //aromatics can be idfk wax or soap or sth, perhaps artificial lubricant?
    //on that note, add more leaded variants

    public static HashMap<Fluid, Pair<Integer, ItemStack>> recipes = new HashMap<>();


    public static void registerDefaults() {

        registerRecipe(FluidRegistry.WATER,		    1000,			Blocks.ICE);
        registerRecipe(FluidRegistry.LAVA,		    1000,			Blocks.OBSIDIAN);
        registerRecipe(ModForgeFluids.MERCURY,		125,			ModItems.nugget_mercury);
        registerRecipe(ModForgeFluids.BIOGAS,		250,			ModItems.biomass_compressed);
//        registerRecipe(SALIENT,		1280,			new ItemStack(ModItems.bio_wafer, 8)); //4 (food val) * 2 (sat mod) * 2 (constant) * 10 (quanta) * 8 (batch size)
        registerRecipe(ModForgeFluids.ENDERJUICE,	250,			Items.ENDER_PEARL);
//        registerRecipe(REDMUD,		1000,			Items.IRON_INGOT);
//        registerRecipe(SLOP,		250,			ModBlocks.ore_oil_sand);

        registerRecipe(ModForgeFluids.OIL,				SF_OIL,			OreDictManager.DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.CRUDE));
        registerRecipe(ModForgeFluids.CRACKOIL,		    SF_CRACK,		OreDictManager.DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.CRACK));
        registerRecipe(ModForgeFluids.COALOIL,			SF_COALOIL,		OreDictManager.DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.COAL));
        registerRecipe(ModForgeFluids.HEAVYOIL,		    SF_HEAVY,		OreDictManager.DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.CRUDE));
        registerRecipe(ModForgeFluids.HEAVYOIL_VACUUM,	SF_HEAVY,		OreDictManager.DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.CRUDE));
        registerRecipe(ModForgeFluids.BITUMEN,			SF_BITUMEN,		OreDictManager.DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.CRUDE));
        registerRecipe(ModForgeFluids.COALCREOSOTE,	    SF_CREOSOTE,	OreDictManager.DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.COAL));
        registerRecipe(ModForgeFluids.WOODOIL,			SF_WOOD,		OreDictManager.DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.WOOD));
        registerRecipe(ModForgeFluids.LUBRICANT,		SF_LUBE,		OreDictManager.DictFrame.fromOne(ModItems.oil_tar, ItemEnums.EnumTarType.PARAFFIN));

        registerSFAuto(ModForgeFluids.SMEAR);
        registerSFAuto(ModForgeFluids.HEATINGOIL);
        registerSFAuto(ModForgeFluids.HEATINGOIL_VACUUM);
        registerSFAuto(ModForgeFluids.RECLAIMED);
        registerSFAuto(ModForgeFluids.PETROIL);
        registerSFAuto(ModForgeFluids.NAPHTHA);
        registerSFAuto(ModForgeFluids.NAPHTHA_CRACK);
        registerSFAuto(ModForgeFluids.DIESEL);
        registerSFAuto(ModForgeFluids.DIESEL_REFORM);
        registerSFAuto(ModForgeFluids.DIESEL_CRACK);
        registerSFAuto(ModForgeFluids.DIESEL_CRACK_REFORM);
        registerSFAuto(ModForgeFluids.LIGHTOIL);
        registerSFAuto(ModForgeFluids.LIGHTOIL_CRACK);
        registerSFAuto(ModForgeFluids.LIGHTOIL_VACUUM);
        registerSFAuto(ModForgeFluids.KEROSENE);
        registerSFAuto(ModForgeFluids.KEROSENE_REFORM);
        registerSFAuto(ModForgeFluids.GAS);
        registerSFAuto(ModForgeFluids.SOURGAS);
        registerSFAuto(ModForgeFluids.REFORMGAS);
        registerSFAuto(ModForgeFluids.SYNGAS);
        registerSFAuto(ModForgeFluids.PETROLEUM);
//        registerSFAuto(LPG);
        registerSFAuto(ModForgeFluids.BIOGAS);
        registerSFAuto(ModForgeFluids.BIOGAS);
        registerSFAuto(ModForgeFluids.AROMATICS);
        registerSFAuto(ModForgeFluids.UNSATURATEDS);
        registerSFAuto(ModForgeFluids.REFORMATE);
        registerSFAuto(ModForgeFluids.XYLENE);
        registerSF(ModForgeFluids.BALEFIRE, 24_000L, ModItems.solid_fuel_bf); //holy shit this is energy dense*/
        registerRecipe(ModForgeFluids.MUD_FLUID,16_000,	ModItems.ingot_mud);

    }

    private static void registerSFAuto(Fluid fluid) {
        registerSF(fluid, 900L, ModItems.solid_fuel); //3200 burntime * 1.5 burntime bonus * 300 TU/t
    }
    private static void registerSF(Fluid fluid, long tuPerSF, Item fuel) {
        long tuPermBucket = FluidFlameRecipes.getHeatEnergy(fluid);
        if(tuPermBucket == 0) return;
        double penalty = 1.25D;

        int mB = (int) (1000L * tuPerSF * penalty / tuPermBucket);

        if(mB > 10_000) mB -= (mB % 1000);
        else if(mB > 1_000) mB -= (mB % 100);
        else if(mB > 100) mB -= (mB % 10);

        mB = Math.max(mB, 1);
        if(mB > 24000) return;
        registerRecipe(fluid, mB, fuel);
    }

    private static void registerRecipe(Fluid type, int quantity, Item output) { registerRecipe(type, quantity, new ItemStack(output)); }
    private static void registerRecipe(Fluid type, int quantity, Block output) { registerRecipe(type, quantity, new ItemStack(output)); }
    private static void registerRecipe(Fluid type, int quantity, ItemStack output) {
        recipes.put(type, new Pair<Integer, ItemStack>(quantity, output));
    }

    public static boolean hasRecipe(Fluid type){
        return getOutput(type) != null;
    }

    public static Pair<Integer, ItemStack> getOutput(Fluid type) {
        return recipes.get(type);
    }

//    public static HashMap<ItemStack, ItemStack> getRecipes() {
//
//        HashMap<ItemStack, ItemStack> recipes = new HashMap<ItemStack, ItemStack>();
//
//        for(Map.Entry<Fluid, Pair<Integer, ItemStack>> entry : SolidificationRecipes.recipes.entrySet()) {
//
//            Fluid type = entry.getKey();
//            int amount = entry.getValue().getKey();
//            ItemStack out = entry.getValue().getValue().copy();
//
//            recipes.put(ItemFluidIcon.make(type, amount), out);
//        }
//
//        return recipes;
//    }
}
