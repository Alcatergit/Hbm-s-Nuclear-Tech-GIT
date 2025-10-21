package com.hbm.inventory;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.Tuple.Quartet;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class FractionRecipes {

    public static Map<String, Pair<FluidStack, FluidStack>> fractions = new HashMap<>();
    
    public static void registerDefaults() {
        addRecipe(ModForgeFluids.HEAVYOIL,			ModForgeFluids.BITUMEN,			30,		ModForgeFluids.SMEAR,				70);
        addRecipe(ModForgeFluids.HEAVYOIL_VACUUM,	ModForgeFluids.SMEAR,			40,		ModForgeFluids.HEATINGOIL_VACUUM,	60);
        addRecipe(ModForgeFluids.SMEAR,				ModForgeFluids.HEATINGOIL,		60,		ModForgeFluids.LUBRICANT,			40);
        addRecipe(ModForgeFluids.NAPHTHA,			ModForgeFluids.HEATINGOIL,		40,		ModForgeFluids.DIESEL,				60);
        addRecipe(ModForgeFluids.NAPHTHA_DS,		ModForgeFluids.XYLENE,			60,		ModForgeFluids.DIESEL_REFORM,		40);
        addRecipe(ModForgeFluids.NAPHTHA_CRACK,		ModForgeFluids.HEATINGOIL,		30,		ModForgeFluids.DIESEL_CRACK,		70);
        addRecipe(ModForgeFluids.LIGHTOIL,			ModForgeFluids.DIESEL,			40,		ModForgeFluids.KEROSENE,			60);
        addRecipe(ModForgeFluids.LIGHTOIL_DS,		ModForgeFluids.DIESEL_REFORM,	60,		ModForgeFluids.KEROSENE_REFORM,		40);
        addRecipe(ModForgeFluids.LIGHTOIL_CRACK,	ModForgeFluids.KEROSENE,		70,		ModForgeFluids.PETROLEUM,			30);
        addRecipe(ModForgeFluids.COALOIL,			ModForgeFluids.COALGAS,			30,		ModForgeFluids.OIL,					70);
        addRecipe(ModForgeFluids.COALCREOSOTE,		ModForgeFluids.COALOIL,			10,		ModForgeFluids.BITUMEN,				90);
        addRecipe(ModForgeFluids.REFORMATE,			ModForgeFluids.AROMATICS,		40,		ModForgeFluids.XYLENE,				60);
        addRecipe(ModForgeFluids.LIGHTOIL_VACUUM,	ModForgeFluids.KEROSENE,		70,		ModForgeFluids.REFORMGAS,			30);
//      addRecipe(ModForgeFluids.EGG,				ModForgeFluids.CHOLESTEROL,		        50,		ModForgeFluids.RADIOSOLVENT,			50);
        addRecipe(ModForgeFluids.OIL_COKER,			ModForgeFluids.CRACKOIL,		30,		ModForgeFluids.HEATINGOIL,			70);
        addRecipe(ModForgeFluids.NAPHTHA_COKER,		ModForgeFluids.NAPHTHA_CRACK,	75,		ModForgeFluids.LIGHTOIL_CRACK,		25);
//      addRecipe(ModForgeFluids.GAS_COKER,			ModForgeFluids.AROMATICS,			25,		ModForgeFluids.CARBONDIOXIDE,		75);
//      addRecipe(ModForgeFluids.CHLOROCALCITE_MIX, ModForgeFluids.CHLOROCALCITE_CLEANED,	50,		ModForgeFluids.COLLOID,				50);
    }

    public static void addRecipe(Fluid in, Fluid out1, int amount1, Fluid out2, int amount2){
        fractions.put(in.getName(), new Pair<>(new FluidStack(out1,	amount1), new FluidStack(out2, amount2)));
    }

    public static Quartet<Fluid, Fluid, Integer, Integer> getFractions(Fluid oil) {
        Pair<FluidStack, FluidStack> result = fractions.get(oil.getName());
        if(result != null) return new Quartet<>(result.getKey().getFluid(), result.getValue().getFluid(), result.getKey().amount, result.getValue().amount);
        return null;
    }

    public static Quartet<Fluid, Fluid, Integer, Integer> getFractions(String oil) {
        Pair<FluidStack, FluidStack> result = fractions.get(oil);
        if(result != null) return new Quartet<>(result.getKey().getFluid(), result.getValue().getFluid(), result.getKey().amount, result.getValue().amount);
        return null;
    }
}
