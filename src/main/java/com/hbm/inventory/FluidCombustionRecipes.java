package com.hbm.inventory;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;

import java.util.HashMap;

public class FluidCombustionRecipes {
	
	public static HashMap<FluidType, Integer> resultingTU = new HashMap<>();
	//for 1000 mb
	public static void registerFluidCombustionRecipes() {
		addBurnableFluid(Fluids.HYDROGEN, 5);
		addBurnableFluid(Fluids.DEUTERIUM, 5);
		addBurnableFluid(Fluids.TRITIUM, 5);

		addBurnableFluid(Fluids.OIL, 10);
		addBurnableFluid(Fluids.HOTOIL, 10);
		addBurnableFluid(Fluids.CRACKOIL, 10);
		addBurnableFluid(Fluids.HOTCRACKOIL, 10);

		addBurnableFluid(Fluids.GAS, 10);
		addBurnableFluid(Fluids.FISHOIL, 15);
		addBurnableFluid(Fluids.LUBRICANT, 20);
		addBurnableFluid(Fluids.AROMATICS, 25);
		addBurnableFluid(Fluids.PETROLEUM, 25);
		addBurnableFluid(Fluids.BIOGAS, 25);
		addBurnableFluid(Fluids.BITUMEN, 35);
		addBurnableFluid(Fluids.HEAVYOIL, 50);
		addBurnableFluid(Fluids.SMEAR, 50);
		addBurnableFluid(Fluids.ETHANOL, 75);
		addBurnableFluid(Fluids.RECLAIMED, 100);
		addBurnableFluid(Fluids.PETROIL, 125);
		addBurnableFluid(Fluids.NAPHTHA, 125);
		addBurnableFluid(Fluids.HEATINGOIL, 150);
		addBurnableFluid(Fluids.BIOFUEL, 150);
		addBurnableFluid(Fluids.DIESEL, 200);
		addBurnableFluid(Fluids.LIGHTOIL, 200);
		addBurnableFluid(Fluids.KEROSENE, 300);
		addBurnableFluid(Fluids.GASOLINE, 800);

		// why are we registering it twice?..
		//addBurnableFluid(Fluids.BALEFIRE, 1_000);
		addBurnableFluid(Fluids.UNSATURATEDS, 1_000);
		addBurnableFluid(Fluids.NITAN, 2_000);
		addBurnableFluid(Fluids.BALEFIRE, 10_000);

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

	public static int getFlameEnergy(FluidType f){
		Integer heat = resultingTU.get(f);
		if(heat != null)
			return heat;
		return 0;
	}

	public static boolean hasFuelRecipe(FluidType fluid){
		return resultingTU.containsKey(fluid);
	}

	public static void addBurnableFluid(FluidType fluid, int heatPerMiliBucket) {
		resultingTU.put(fluid, heatPerMiliBucket);
	}

	public static void addBurnableFluid(String fluid, int heatPerMiliBucket){
		if(Fluids.fromName(fluid) != Fluids.NONE){
			addBurnableFluid(Fluids.fromName(fluid), heatPerMiliBucket);
		}
	}

	public static void removeBurnableFluid(FluidType fluid){
		resultingTU.remove(fluid);
	}

	public static void removeBurnableFluid(String fluid){
		if(Fluids.fromName(fluid) != Fluids.NONE){
			resultingTU.remove(Fluids.fromName(fluid));
		}
	}
}