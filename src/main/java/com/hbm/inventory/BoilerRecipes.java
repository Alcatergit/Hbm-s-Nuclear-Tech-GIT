package com.hbm.inventory;

import java.util.HashMap;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.Spaghetti;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

@Spaghetti("everything")
public class BoilerRecipes {

    public static HashMap<Fluid, FluidStack> recipeFluids = new HashMap<>();

    public static void registerRecipes() {
        //Given 100mb input how much output does it produce? for water it is 1:100
        makeRecipe(FluidRegistry.WATER, new FluidStack(ModForgeFluids.steam, 10_000));
        makeRecipe(ModForgeFluids.oil, new FluidStack(ModForgeFluids.hotoil, 100));
        makeRecipe(ModForgeFluids.crackoil, new FluidStack(ModForgeFluids.hotcrackoil, 100));

        // makeRecipe(new Fluid(), new FluidStack());
    }

    public static void makeRecipe(Fluid inputFluid, FluidStack outputFluids) {
        if(inputFluid != null && outputFluids != null)
            recipeFluids.put(inputFluid, outputFluids);
    }

    public static FluidStack getOutputsFromFluid(Fluid fluid) {
        if (fluid == null)
            return null;
        return recipeFluids.get(fluid);
    }

    public static boolean hasRecipe(Fluid fluid) {
        return recipeFluids.containsKey(fluid);
    }
}
