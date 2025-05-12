package com.hbm.inventory;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.util.Tuple.Triplet;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;

public class HydrotreatingRecipes {

    public static HashMap<Fluid, Triplet<FluidStack, FluidStack, FluidStack>> recipes = new HashMap<Fluid, Triplet<FluidStack, FluidStack, FluidStack>>();

    public static void registerDefaults() {

        recipes.put(ModForgeFluids.OIL, new Triplet<>(
                new FluidStack(ModForgeFluids.HYDROGEN, 5),
                new FluidStack(ModForgeFluids.OIL_DS, 90),
                new FluidStack(ModForgeFluids.SOURGAS, 15)
        ));

        recipes.put(ModForgeFluids.CRACKOIL, new Triplet<>(
                new FluidStack(ModForgeFluids.HYDROGEN, 5),
                new FluidStack(ModForgeFluids.CRACKOIL_DS, 90),
                new FluidStack(ModForgeFluids.SOURGAS, 15)
        ));

        recipes.put(ModForgeFluids.GAS, new Triplet<>(
                new FluidStack(ModForgeFluids.HYDROGEN, 5),
                new FluidStack(ModForgeFluids.PETROLEUM, 80),
                new FluidStack(ModForgeFluids.SOURGAS, 15)
        ));

        recipes.put(ModForgeFluids.DIESEL_CRACK, new Triplet<>(
                new FluidStack(ModForgeFluids.HYDROGEN, 10),
                new FluidStack(ModForgeFluids.DIESEL, 80),
                new FluidStack(ModForgeFluids.SOURGAS, 30)
        ));

        recipes.put(ModForgeFluids.DIESEL_CRACK_REFORM, new Triplet<>(
                new FluidStack(ModForgeFluids.HYDROGEN, 10),
                new FluidStack(ModForgeFluids.DIESEL_REFORM, 80),
                new FluidStack(ModForgeFluids.SOURGAS, 30)
        ));

        recipes.put(ModForgeFluids.COALOIL, new Triplet<>(
                new FluidStack(ModForgeFluids.HYDROGEN, 10),
                new FluidStack(ModForgeFluids.COALGAS, 80),
                new FluidStack(ModForgeFluids.SOURGAS, 15)
        ));
    }

    public static Triplet<FluidStack, FluidStack, FluidStack> getOutput(Fluid type) {
        return recipes.get(type);
    }

//    public static HashMap<Object, Object[]> getRecipes() {
//
//        HashMap<Object, Object[]> map = new HashMap<Object, Object[]>();
//
//        for(Map.Entry<Fluid, Triplet<FluidStack, FluidStack, FluidStack>> recipe : recipes.entrySet()) {
//            map.put(new ItemStack[] {
//                            ItemFluidIcon.make(recipe.getKey(), 1000),
//                            ItemFluidIcon.make(recipe.getValue().getX().type,	recipe.getValue().getX().fill * 10, 1) },
//                    new ItemStack[] {
//                            ItemFluidIcon.make(recipe.getValue().getY().type,	recipe.getValue().getY().fill * 10),
//                            ItemFluidIcon.make(recipe.getValue().getZ().type,	recipe.getValue().getZ().fill * 10) });
//        }
//
//        return map;
//    }
}
