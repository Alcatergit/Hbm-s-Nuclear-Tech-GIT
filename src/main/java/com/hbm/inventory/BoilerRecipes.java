package com.hbm.inventory;

import java.util.HashMap;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.Spaghetti;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

@Spaghetti("everything")
public class BoilerRecipes {

    public static HashMap<Fluid, FluidStack[]> recipeFluids = new HashMap<>();

    public static void registerRecipes() {
        makeRecipe(FluidRegistry.WATER, new FluidStack[]{ new FluidStack(ModForgeFluids.steam, 100) });
        makeRecipe(ModForgeFluids.oil, new FluidStack[]{ new FluidStack(ModForgeFluids.hotoil, 100) });
        makeRecipe(ModForgeFluids.crackoil, new FluidStack[]{ new FluidStack(ModForgeFluids.hotcrackoil, 100) });

        // makeRecipe(new Fluid(), new FluidStack[]{ new FluidStack() });
    }

    public static void makeRecipe(Fluid inputFluid, FluidStack[] outputFluids) {
        if(inputFluid != null && outputFluids != null)
            recipeFluids.put(inputFluid, outputFluids);
    }

    public static FluidStack[] getOutputsFromFluid(Fluid fluid) {
        if (fluid == null)
            return null;
        return recipeFluids.get(fluid);
    }

    public static boolean hasRecipe(Fluid fluid) {
        return recipeFluids.containsKey(fluid);
    }
}
