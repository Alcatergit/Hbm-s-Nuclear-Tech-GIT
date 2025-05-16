package com.hbm.inventory;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.ItemEnums;
import com.hbm.items.ModItems;
import com.hbm.util.Tuple.Triplet;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;

public class CokerRecipes {

    public static HashMap<Fluid, Triplet<Integer, ItemStack, FluidStack>> recipes = new HashMap();

    public static void registerDefaults() {

        registerAuto(ModForgeFluids.HEAVYOIL,				ModForgeFluids.OIL_COKER);
        registerAuto(ModForgeFluids.HEAVYOIL_VACUUM,		ModForgeFluids.REFORMATE);
        registerAuto(ModForgeFluids.COALCREOSOTE,			ModForgeFluids.NAPHTHA_COKER);
        registerAuto(ModForgeFluids.SMEAR,					ModForgeFluids.OIL_COKER);
        registerAuto(ModForgeFluids.HEATINGOIL,			    ModForgeFluids.OIL_COKER);
        registerAuto(ModForgeFluids.HEATINGOIL_VACUUM,	    ModForgeFluids.OIL_COKER);
        registerAuto(ModForgeFluids.RECLAIMED,			    ModForgeFluids.NAPHTHA_COKER);
        registerAuto(ModForgeFluids.NAPHTHA,				ModForgeFluids.NAPHTHA_COKER);
        registerAuto(ModForgeFluids.NAPHTHA_DS,			    ModForgeFluids.NAPHTHA_COKER);
        registerAuto(ModForgeFluids.NAPHTHA_CRACK,		    ModForgeFluids.NAPHTHA_COKER);
        registerAuto(ModForgeFluids.DIESEL,				    ModForgeFluids.NAPHTHA_COKER);
        registerAuto(ModForgeFluids.DIESEL_REFORM,		    ModForgeFluids.NAPHTHA_COKER);
        registerAuto(ModForgeFluids.DIESEL_CRACK,			ModForgeFluids.GAS_COKER);
        registerAuto(ModForgeFluids.DIESEL_CRACK_REFORM,	ModForgeFluids.GAS_COKER);
        registerAuto(ModForgeFluids.LIGHTOIL,				ModForgeFluids.GAS_COKER);
        registerAuto(ModForgeFluids.LIGHTOIL_DS,			ModForgeFluids.GAS_COKER);
        registerAuto(ModForgeFluids.LIGHTOIL_CRACK,		    ModForgeFluids.GAS_COKER);
        registerAuto(ModForgeFluids.LIGHTOIL_VACUUM,		ModForgeFluids.GAS_COKER);
        registerAuto(ModForgeFluids.BIOFUEL,				ModForgeFluids.GAS_COKER);
        registerAuto(ModForgeFluids.AROMATICS,				ModForgeFluids.GAS_COKER);
        registerAuto(ModForgeFluids.REFORMATE,				ModForgeFluids.GAS_COKER);
        registerAuto(ModForgeFluids.XYLENE,				ModForgeFluids.GAS_COKER);
        registerAuto(ModForgeFluids.FISHOIL,				ModForgeFluids.MERCURY);
        registerAuto(ModForgeFluids.SUNFLOWEROIL,			ModForgeFluids.GAS_COKER);

        registerSFAuto(ModForgeFluids.WOODOIL, 340_000L, new ItemStack(Items.COAL, 1, 1), ModForgeFluids.GAS_COKER);

//        registerRecipe(REDMUD, 1_000, new ItemStack(Items.IRON_INGOT, 1), new FluidStack(MERCURY, 50));
        registerRecipe(ModForgeFluids.BITUMEN, 16_000, OreDictManager.DictFrame.fromOne(ModItems.coke, ItemEnums.EnumCokeType.PETROLEUM), new FluidStack(ModForgeFluids.OIL_COKER, 1_600));
        registerRecipe(ModForgeFluids.LUBRICANT, 12_000, OreDictManager.DictFrame.fromOne(ModItems.coke, ItemEnums.EnumCokeType.PETROLEUM), new FluidStack(ModForgeFluids.OIL_COKER, 1_200));
        //only cookable gas to extract sulfur content
        registerRecipe(ModForgeFluids.SOURGAS, 1_000, new ItemStack(ModItems.sulfur), new FluidStack(ModForgeFluids.GAS_COKER, 150));
//        registerRecipe(VITRIOL, 4000, new ItemStack(ModItems.powder_iron), new FluidStack(SULFURIC_ACID, 500));
    }

    private static void registerAuto(Fluid fluid, Fluid outputFluid) {
        registerSFAuto(fluid, 820_000L, OreDictManager.DictFrame.fromOne(ModItems.coke, ItemEnums.EnumCokeType.PETROLEUM), outputFluid); //3200 burntime * 1.25 burntime bonus * 200 TU/t + 20000TU per operation
    }
    private static void registerSFAuto(Fluid fluid, long tuPerSF, ItemStack fuel, Fluid outputFluid) {
        long tuFlammable = FluidFlameRecipes.getHeatEnergy(fluid) * 1000L;
        long tuCombustible = FluidCombustionRecipes.getCombustionEnergy(fluid);

        double tuPerBucket = Math.max(tuFlammable, tuCombustible);

        int mB = (int) ( 1000 * tuPerSF / tuPerBucket);

        if(mB > 10_000) mB -= (mB % 1000);
        else if(mB > 1_000) mB -= (mB % 100);
        else if(mB > 100) mB -= (mB % 10);

        FluidStack byproduct = outputFluid == null ? null : new FluidStack(outputFluid, Math.max(10, mB / 10));

        if(mB > 16000) return;

        registerRecipe(fluid, mB, fuel, byproduct);
    }

    private static void registerRecipe(Fluid fluid, int quantity, ItemStack output, FluidStack byproduct) {

        recipes.put(fluid, new Triplet<Integer, ItemStack, FluidStack>(quantity, output, byproduct));
    }

    public static Triplet<Integer, ItemStack, FluidStack> getOutput(Fluid type) {
        return recipes.get(type);
    }

//    public static HashMap<ItemStack, ItemStack[]> getRecipes() {
//
//        HashMap<ItemStack, ItemStack[]> recipes = new HashMap<ItemStack, ItemStack[]>();
//
//        for(Map.Entry<FluidType, Tuple.Triplet<Integer, ItemStack, FluidStack>> entry : CokerRecipes.recipes.entrySet()) {
//
//            FluidType type = entry.getKey();
//            int amount = entry.getValue().getX();
//            ItemStack out = entry.getValue().getY().copy();
//            FluidStack byproduct = entry.getValue().getZ();
//
//
//            if(out != null && byproduct != null) recipes.put(ItemFluidIcon.make(type, amount), new ItemStack[] {out, ItemFluidIcon.make(byproduct)});
//            if(out != null && byproduct == null) recipes.put(ItemFluidIcon.make(type, amount), new ItemStack[] {out});
//            if(out == null && byproduct != null) recipes.put(ItemFluidIcon.make(type, amount), new ItemStack[] {ItemFluidIcon.make(byproduct)});
//        }
//
//        return recipes;
//    }
}
