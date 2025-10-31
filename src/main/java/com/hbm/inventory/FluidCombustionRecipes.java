package com.hbm.inventory;

import com.hbm.forgefluid.ModForgeFluids;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.HashMap;

public class FluidCombustionRecipes {
	
	public static HashMap<Fluid, Integer> resultingTU = new HashMap<Fluid, Integer>();
	//for 1000 mb
	public static void registerFluidCombustionRecipes() {
		addBurnableFluid(ModForgeFluids.HYDROGEN, 5);
		addBurnableFluid(ModForgeFluids.DEUTERIUM, 5);
		addBurnableFluid(ModForgeFluids.TRITIUM, 5);

		addBurnableFluid(ModForgeFluids.OIL, 10);
		addBurnableFluid(ModForgeFluids.HOTOIL, 10);
		addBurnableFluid(ModForgeFluids.CRACKOIL, 10);
		addBurnableFluid(ModForgeFluids.HOTCRACKOIL, 10);

		addBurnableFluid(ModForgeFluids.GAS, 10);
		addBurnableFluid(ModForgeFluids.FISHOIL, 15);
		addBurnableFluid(ModForgeFluids.LUBRICANT, 20);
		addBurnableFluid(ModForgeFluids.AROMATICS, 25);
		addBurnableFluid(ModForgeFluids.PETROLEUM, 25);
		addBurnableFluid(ModForgeFluids.BIOGAS, 25);
		addBurnableFluid(ModForgeFluids.BITUMEN, 35);
		addBurnableFluid(ModForgeFluids.HEAVYOIL, 50);
		addBurnableFluid(ModForgeFluids.SMEAR, 50);
		addBurnableFluid(ModForgeFluids.ETHANOL, 75);
		addBurnableFluid(ModForgeFluids.RECLAIMED, 100);
		addBurnableFluid(ModForgeFluids.PETROIL, 125);
		addBurnableFluid(ModForgeFluids.NAPHTHA, 125);
		addBurnableFluid(ModForgeFluids.HEATINGOIL, 150);
		addBurnableFluid(ModForgeFluids.BIOFUEL, 150);
		addBurnableFluid(ModForgeFluids.DIESEL, 200);
		addBurnableFluid(ModForgeFluids.LIGHTOIL, 200);
		addBurnableFluid(ModForgeFluids.KEROSENE, 300);
		addBurnableFluid(ModForgeFluids.GASOLINE, 800);

		addBurnableFluid(ModForgeFluids.BALEFIRE, 1_000);
		addBurnableFluid(ModForgeFluids.UNSATURATEDS, 1_000);
		addBurnableFluid(ModForgeFluids.NITAN, 2_000);
		addBurnableFluid(ModForgeFluids.BALEFIRE, 10_000);
		addBurnableFluid(ModForgeFluids.UU_MATTER, 50_000);

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