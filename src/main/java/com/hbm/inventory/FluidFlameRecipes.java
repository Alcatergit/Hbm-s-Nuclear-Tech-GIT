package com.hbm.inventory;

import java.util.HashMap;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidFlameRecipes {
	
	public static HashMap<Fluid, Long> resultingTU = new HashMap<Fluid, Long>();
	//for 1000 mb
	public static void registerFluidFlameRecipes() {

		//Compat
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

	public static long getHeatEnergy(Fluid f){
		Long heat = resultingTU.get(f);
		if(heat != null)
			return heat;
		return 0;
	}

	public static boolean hasFuelRecipe(Fluid fluid){
		return resultingTU.containsKey(fluid);
	}

	public static void addBurnableFluid(Fluid fluid, long heatPerMiliBucket) {
		resultingTU.put(fluid, heatPerMiliBucket);
	}

	public static void addBurnableFluid(String fluid, long heatPerMiliBucket){
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