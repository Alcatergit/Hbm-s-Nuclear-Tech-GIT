package com.hbm.inventory;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.Tuple.Quartet;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class FractionRecipes {

    public static Map<Fluid, Pair<FluidStack, FluidStack>> fractions = new HashMap();
    
    public static void registerDefaults() {
        fractions.put(ModForgeFluids.HEAVYOIL,			new Pair(new FluidStack(ModForgeFluids.BITUMEN,					30),		new FluidStack(ModForgeFluids.SMEAR,				70)));
        fractions.put(ModForgeFluids.HEAVYOIL_VACUUM,	new Pair(new FluidStack(ModForgeFluids.SMEAR,					40),		new FluidStack(ModForgeFluids.HEATINGOIL_VACUUM,	60)));
        fractions.put(ModForgeFluids.SMEAR,				new Pair(new FluidStack(ModForgeFluids.HEATINGOIL,				60),		new FluidStack(ModForgeFluids.LUBRICANT,			40)));
        fractions.put(ModForgeFluids.NAPHTHA,			new Pair(new FluidStack(ModForgeFluids.HEATINGOIL,				40),		new FluidStack(ModForgeFluids.DIESEL,				60)));
        fractions.put(ModForgeFluids.NAPHTHA_DS,		new Pair(new FluidStack(ModForgeFluids.XYLENE,					60),		new FluidStack(ModForgeFluids.DIESEL_REFORM,		40)));
        fractions.put(ModForgeFluids.NAPHTHA_CRACK,		new Pair(new FluidStack(ModForgeFluids.HEATINGOIL,				30),		new FluidStack(ModForgeFluids.DIESEL_CRACK,			70)));
        fractions.put(ModForgeFluids.LIGHTOIL,			new Pair(new FluidStack(ModForgeFluids.DIESEL,					40),		new FluidStack(ModForgeFluids.KEROSENE,				60)));
        fractions.put(ModForgeFluids.LIGHTOIL_DS,		new Pair(new FluidStack(ModForgeFluids.DIESEL_REFORM,			60),		new FluidStack(ModForgeFluids.KEROSENE_REFORM,		40)));
        fractions.put(ModForgeFluids.LIGHTOIL_CRACK,	new Pair(new FluidStack(ModForgeFluids.KEROSENE,				70),		new FluidStack(ModForgeFluids.PETROLEUM,			30)));
        fractions.put(ModForgeFluids.COALOIL,			new Pair(new FluidStack(ModForgeFluids.COALGAS,					30),		new FluidStack(ModForgeFluids.OIL,					70)));
        fractions.put(ModForgeFluids.COALCREOSOTE,		new Pair(new FluidStack(ModForgeFluids.COALOIL,					10),		new FluidStack(ModForgeFluids.BITUMEN,				90)));
        fractions.put(ModForgeFluids.REFORMATE,			new Pair(new FluidStack(ModForgeFluids.AROMATICS,				40),		new FluidStack(ModForgeFluids.XYLENE,				60)));
        fractions.put(ModForgeFluids.LIGHTOIL_VACUUM,	new Pair(new FluidStack(ModForgeFluids.KEROSENE,				70),		new FluidStack(ModForgeFluids.REFORMGAS,			30)));
//        fractions.put(ModForgeFluids.EGG,				new Pair(new FluidStack(ModForgeFluids.CHOLESTEROL,				50),		new FluidStack(ModForgeFluids.RADIOSOLVENT,			50)));
        fractions.put(ModForgeFluids.OIL_COKER,			new Pair(new FluidStack(ModForgeFluids.CRACKOIL,				30),		new FluidStack(ModForgeFluids.HEATINGOIL,			70)));
        fractions.put(ModForgeFluids.NAPHTHA_COKER,		new Pair(new FluidStack(ModForgeFluids.NAPHTHA_CRACK,			75),		new FluidStack(ModForgeFluids.LIGHTOIL_CRACK,		25)));
//        fractions.put(ModForgeFluids.GAS_COKER,			new Pair(new FluidStack(ModForgeFluids.AROMATICS,				25),		new FluidStack(ModForgeFluids.CARBONDIOXIDE,		75)));
//        fractions.put(ModForgeFluids.CHLOROCALCITE_MIX, new Pair(new FluidStack(ModForgeFluids.CHLOROCALCITE_CLEANED,	50),		new FluidStack(ModForgeFluids.COLLOID,				50)));
    }

    public static Quartet<Fluid, Fluid, Integer, Integer> getFractions(Fluid oil) {
        Pair<FluidStack, FluidStack> result = fractions.get(oil);
        if(result != null) return new Quartet<Fluid, Fluid, Integer, Integer>(result.getKey().getFluid(), result.getValue().getFluid(), result.getKey().amount, result.getValue().amount);
        return null;
    }

//    public static HashMap<Object, Object> getFractionRecipesForNEI() {
//
//        HashMap<Object, Object> recipes = new HashMap();
//
//        for(Map.Entry<FluidType, Tuple.Pair<FluidStack, FluidStack>> recipe : fractions.entrySet()) {
//            ItemStack[] out = new ItemStack[] {
//                    ItemFluidIcon.make(recipe.getValue().getKey()),
//                    ItemFluidIcon.make(recipe.getValue().getValue())
//            };
//
//            recipes.put(ItemFluidIcon.make(recipe.getKey(), 100), out);
//        }
//
//        return recipes;
//    }
}
