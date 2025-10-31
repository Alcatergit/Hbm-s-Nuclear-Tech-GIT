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
		makeRecipe(ModForgeFluids.OIL,				new FluidStack[]{ new FluidStack(ModForgeFluids.CRACKOIL,		80),	new FluidStack(ModForgeFluids.PETROLEUM,	20)});
		makeRecipe(ModForgeFluids.BITUMEN,			new FluidStack[]{ new FluidStack(ModForgeFluids.OIL,			80),	new FluidStack(ModForgeFluids.AROMATICS,	20)});
		makeRecipe(ModForgeFluids.SMEAR,			new FluidStack[]{ new FluidStack(ModForgeFluids.NAPHTHA,		60),	new FluidStack(ModForgeFluids.PETROLEUM,	40)});
		makeRecipe(ModForgeFluids.GAS,				new FluidStack[]{ new FluidStack(ModForgeFluids.PETROLEUM,		30),	new FluidStack(ModForgeFluids.UNSATURATEDS,	20)});
		makeRecipe(ModForgeFluids.DIESEL,			new FluidStack[]{ new FluidStack(ModForgeFluids.KEROSENE,		30),	new FluidStack(ModForgeFluids.PETROLEUM,	40)});
		makeRecipe(ModForgeFluids.DIESEL_CRACK,		new FluidStack[]{ new FluidStack(ModForgeFluids.KEROSENE,		40),	new FluidStack(ModForgeFluids.PETROLEUM,	30)});
		makeRecipe(ModForgeFluids.KEROSENE,			new FluidStack[]{ new FluidStack(ModForgeFluids.PETROLEUM,		60)});
		makeRecipe(ModForgeFluids.WOODOIL,			new FluidStack[]{ new FluidStack(ModForgeFluids.HEATINGOIL,		40),	new FluidStack(ModForgeFluids.AROMATICS,	10)});
		makeRecipe(ModForgeFluids.XYLENE,			new FluidStack[]{ new FluidStack(ModForgeFluids.AROMATICS,		80),	new FluidStack(ModForgeFluids.PETROLEUM,	20)});
		makeRecipe(ModForgeFluids.HEATINGOIL_VACUUM,new FluidStack[]{ new FluidStack(ModForgeFluids.HEATINGOIL,		80),	new FluidStack(ModForgeFluids.REFORMGAS,	20)});
		makeRecipe(ModForgeFluids.REFORMATE,		new FluidStack[]{ new FluidStack(ModForgeFluids.UNSATURATEDS,	40),	new FluidStack(ModForgeFluids.REFORMGAS,	60)});

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
