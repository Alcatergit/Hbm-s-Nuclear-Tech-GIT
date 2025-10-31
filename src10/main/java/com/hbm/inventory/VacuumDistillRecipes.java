package com.hbm.inventory;

import java.util.HashMap;
import java.util.Map;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.util.Tuple.Quartet;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class VacuumDistillRecipes {

	public static Map<Fluid, Quartet<FluidStack, FluidStack, FluidStack, FluidStack>> vacuum = new HashMap<>();

	public static void registerRecipes() {
		vacuum.put(ModForgeFluids.OIL, new Quartet<>(
				new FluidStack(ModForgeFluids.HEAVYOIL_VACUUM,	40),
				new FluidStack(ModForgeFluids.REFORMATE,		25),
				new FluidStack(ModForgeFluids.LIGHTOIL_VACUUM,	20),
				new FluidStack(ModForgeFluids.SOURGAS,			15)
		));
		vacuum.put(ModForgeFluids.OIL_DS, new Quartet<>(
				new FluidStack(ModForgeFluids.HEAVYOIL_VACUUM,	40),
				new FluidStack(ModForgeFluids.REFORMATE,		25),
				new FluidStack(ModForgeFluids.LIGHTOIL_VACUUM,	20),
				new FluidStack(ModForgeFluids.REFORMGAS,		15)
		));
	}

	public static Quartet<FluidStack, FluidStack, FluidStack, FluidStack> getVacuum(Fluid oil) {
		return vacuum.get(oil);
	}
}