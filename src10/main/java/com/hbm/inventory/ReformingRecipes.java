package com.hbm.inventory;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.util.Tuple.Triplet;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;

public class ReformingRecipes {

    public static HashMap<Fluid, Triplet<FluidStack, FluidStack, FluidStack>> recipes = new HashMap();

    public static void registerDefaults() {
        recipes.put(ModForgeFluids.HEATINGOIL, new Triplet<>(
                new FluidStack(ModForgeFluids.NAPHTHA, 50),
                new FluidStack(ModForgeFluids.PETROLEUM, 15),
                new FluidStack(ModForgeFluids.HYDROGEN, 10)
        ));
        recipes.put(ModForgeFluids.NAPHTHA, new Triplet<>(
                new FluidStack(ModForgeFluids.REFORMATE, 50),
                new FluidStack(ModForgeFluids.PETROLEUM, 15),
                new FluidStack(ModForgeFluids.HYDROGEN, 10)
        ));
        recipes.put(ModForgeFluids.NAPHTHA_CRACK, new Triplet<>(
                new FluidStack(ModForgeFluids.REFORMATE, 50),
                new FluidStack(ModForgeFluids.AROMATICS, 10),
                new FluidStack(ModForgeFluids.HYDROGEN, 5)
        ));
        recipes.put(ModForgeFluids.NAPHTHA_COKER, new Triplet<>(
                new FluidStack(ModForgeFluids.REFORMATE, 50),
                new FluidStack(ModForgeFluids.REFORMGAS, 10),
                new FluidStack(ModForgeFluids.HYDROGEN, 5)
        ));
        recipes.put(ModForgeFluids.LIGHTOIL, new Triplet<>(
                new FluidStack(ModForgeFluids.AROMATICS, 50),
                new FluidStack(ModForgeFluids.REFORMGAS, 10),
                new FluidStack(ModForgeFluids.HYDROGEN, 15)
        ));
        recipes.put(ModForgeFluids.LIGHTOIL_CRACK, new Triplet<>(
                new FluidStack(ModForgeFluids.AROMATICS, 50),
                new FluidStack(ModForgeFluids.REFORMGAS, 5),
                new FluidStack(ModForgeFluids.HYDROGEN, 20)
        ));
        recipes.put(ModForgeFluids.PETROLEUM, new Triplet<>(
                new FluidStack(ModForgeFluids.UNSATURATEDS, 85),
                new FluidStack(ModForgeFluids.REFORMGAS, 10),
                new FluidStack(ModForgeFluids.HYDROGEN, 5)
        ));
        recipes.put(ModForgeFluids.SOURGAS, new Triplet<>(
                new FluidStack(ModForgeFluids.SULFURIC_ACID, 75),
                new FluidStack(ModForgeFluids.PETROLEUM, 10),
                new FluidStack(ModForgeFluids.HYDROGEN, 15)
        ));
//        recipes.put(ModForgeFluids.CHOLESTEROL, new Triplet(
//                new FluidStack(ModForgeFluids.ESTRADIOL, 50),
//                new FluidStack(ModForgeFluids.REFORMGAS, 35),
//                new FluidStack(ModForgeFluids.HYDROGEN, 15)
//        ));
    }

    public static Triplet<FluidStack, FluidStack, FluidStack> getOutput(Fluid type) {
        return recipes.get(type);
    }
}
