package com.hbm.inventory;

import java.util.LinkedHashMap;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.Spaghetti;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

//TODO: clean this shit up
@Spaghetti("everything")
public class CrackRecipes {

	public static LinkedHashMap<Fluid, FluidStack[]> recipeFluids = new LinkedHashMap<>();

	public static void registerRecipes() {
		makeRecipe(ModForgeFluids.oil, new FluidStack[]{ new FluidStack(ModForgeFluids.crackoil, 80), new FluidStack(ModForgeFluids.petroleum, 20) });
		makeRecipe(ModForgeFluids.bitumen, new FluidStack[]{ new FluidStack(ModForgeFluids.oil, 80), new FluidStack(ModForgeFluids.aromatics, 20) });
		makeRecipe(ModForgeFluids.smear, new FluidStack[]{ new FluidStack(ModForgeFluids.naphtha, 60), new FluidStack(ModForgeFluids.petroleum, 40) });
		makeRecipe(ModForgeFluids.gas, new FluidStack[]{ new FluidStack(ModForgeFluids.petroleum, 30), new FluidStack(ModForgeFluids.unsaturateds, 20) });
		makeRecipe(ModForgeFluids.diesel, new FluidStack[]{ new FluidStack(ModForgeFluids.kerosene, 40), new FluidStack(ModForgeFluids.petroleum, 30) });
		makeRecipe(ModForgeFluids.kerosene, new FluidStack[]{ new FluidStack(ModForgeFluids.petroleum, 60) });
		
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
