package com.hbm.inventory;

import java.util.HashSet;
import java.util.HashMap;

import com.hbm.forgefluid.ModForgeFluids;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidCombustionRecipes {
	
	public static HashMap<Fluid, Integer> resultingTU = new HashMap<Fluid, Integer>();
	//for 1000 mb
	public static void registerFluidCombustionRecipes() {
		addBurnableFluid(ModForgeFluids.hydrogen, 5);
		addBurnableFluid(ModForgeFluids.deuterium, 5);
		addBurnableFluid(ModForgeFluids.tritium, 5);

		addBurnableFluid(ModForgeFluids.oil, 10);
		addBurnableFluid(ModForgeFluids.hotoil, 10);
		addBurnableFluid(ModForgeFluids.crackoil, 10);
		addBurnableFluid(ModForgeFluids.hotcrackoil, 10);

		addBurnableFluid(ModForgeFluids.gas, 10);
		addBurnableFluid(ModForgeFluids.fishoil, 15);
		addBurnableFluid(ModForgeFluids.lubricant, 20);
		addBurnableFluid(ModForgeFluids.aromatics, 25);
		addBurnableFluid(ModForgeFluids.petroleum, 25);
		addBurnableFluid(ModForgeFluids.biogas, 25);
		addBurnableFluid(ModForgeFluids.bitumen, 35);
		addBurnableFluid(ModForgeFluids.heavyoil, 50);
		addBurnableFluid(ModForgeFluids.smear, 50);
		addBurnableFluid(ModForgeFluids.ethanol, 75);
		addBurnableFluid(ModForgeFluids.reclaimed, 100);
		addBurnableFluid(ModForgeFluids.petroil, 125);
		addBurnableFluid(ModForgeFluids.naphtha, 125);
		addBurnableFluid(ModForgeFluids.heatingoil, 150);
		addBurnableFluid(ModForgeFluids.biofuel, 150);
		addBurnableFluid(ModForgeFluids.diesel, 200);
		addBurnableFluid(ModForgeFluids.lightoil, 200);
		addBurnableFluid(ModForgeFluids.kerosene, 300);
		addBurnableFluid(ModForgeFluids.gasoline, 800);

		addBurnableFluid(ModForgeFluids.balefire, 1_000);
		addBurnableFluid(ModForgeFluids.unsaturateds, 1_000);
		addBurnableFluid(ModForgeFluids.nitan, 2_000);
		addBurnableFluid(ModForgeFluids.balefire, 10_000);
		addBurnableFluid(ModForgeFluids.uu_matter, 50_000);

		addBurnableFluid("liquidhydrogen", 5);
		addBurnableFluid("liquiddeuterium", 5);
		addBurnableFluid("liquidtritium", 5);
		addBurnableFluid("crude_oil", 10);
		addBurnableFluid("oilgc", 10);
		addBurnableFluid("fuel", 120);
		addBurnableFluid("refined_biofuel", 150);
		addBurnableFluid("pyrotheum", 1_500);
		addBurnableFluid("ethanol", 30);
		addBurnableFluid("plantoil", 50);
		addBurnableFluid("acetaldehyde", 80);
		addBurnableFluid("biodiesel", 175);
		
	}

	public static int getFlameEnergy(Fluid f){
		Integer heat = resultingTU.get(f);
		if(heat != null)
			return heat;
		return 0;
	}

	public static boolean hasFuelRecipe(Fluid fluid){
		return resultingTU.containsKey(fluid);
	}

	public static void addBurnableFluid(Fluid fluid, int heatPerMiliBucket) {
		resultingTU.put(fluid, heatPerMiliBucket);
	}

	public static void addBurnableFluid(String fluid, int heatPerMiliBucket){
		if(FluidRegistry.isFluidRegistered(fluid)){
			addBurnableFluid(FluidRegistry.getFluid(fluid), heatPerMiliBucket);
		}
	}

	public static void removeBurnableFluid(Fluid fluid){
		resultingTU.remove(fluid);
	}

	public static void removeBurnableFluid(String fluid){
		if(FluidRegistry.isFluidRegistered(fluid)){
			resultingTU.remove(FluidRegistry.getFluid(fluid));
		}
	}
}