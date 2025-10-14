package com.hbm.inventory;

import java.util.HashMap;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidCombustionRecipes {
	
	public static HashMap<Fluid, Long> combustionEnergies = new HashMap<Fluid, Long>();
	public static HashMap<Fluid, FuelGrade> fuelGrades = new HashMap<Fluid, FuelGrade>();

	//for 1000 mb
	public static void registerFluidCombustionRecipes() {

		//Compat
		addFuel("biofuel", FuelGrade.HIGH, 400_000); //galacticraft & industrialforegoing
		addFuel("petroil", FuelGrade.MEDIUM, 300_000); //galacticraft
		addFuel("refined_fuel", FuelGrade.HIGH, 1_000_000); //thermalfoundation
		addFuel("refined_biofuel", FuelGrade.HIGH, 400_000); //thermalfoundation
	
	}

	public enum FuelGrade {
		LOW("trait.fuelgrade.low"),			//heating and industrial oil				< star engine, iGen
		MEDIUM("trait.fuelgrade.medium"),	//petroil									< diesel generator
		HIGH("trait.fuelgrade.high"),		//diesel, gasoline							< HP engine
		AERO("trait.fuelgrade.aero"),	//kerosene and other light aviation fuels	< turbofan
		GAS("trait.fuelgrade.gas");		//fuel gasses like NG, PG and syngas		< gas turbine
		
		private final String grade;
		
		FuelGrade(String grade) {
			this.grade = grade;
		}
		
		public String getGrade() {
			return this.grade;
		}
	}

	public static long getCombustionEnergy(Fluid f){
		if(f != null && combustionEnergies.get(f) != null)
			return combustionEnergies.get(f);
		return 0;
	}

	public static FuelGrade getFuelGrade(Fluid f){
		if(f != null)
			return fuelGrades.get(f);
		return null;
	}

	public static boolean isAero(Fluid f){
		return getFuelGrade(f) == FuelGrade.AERO;
	}

	public static void addFuel(Fluid f, FuelGrade g, long power){
		if(f != null && power > 0){
			combustionEnergies.put(f, power);
			fuelGrades.put(f, g);
		}
	}

	public static boolean hasFuelRecipe(Fluid f){
		if(f == null) return false;
		return combustionEnergies.containsKey(f);
	}

	public static void addFuel(String f, FuelGrade g, long power){
		if(FluidRegistry.isFluidRegistered(f)){
			addFuel(FluidRegistry.getFluid(f), g, power);
		}
	}

	public static void removeFuel(Fluid f){
		if(f != null){
			combustionEnergies.remove(f);
			fuelGrades.remove(f);
		}
	}

	public static void removeFuel(String f){
		if(FluidRegistry.isFluidRegistered(f)){
			removeFuel(FluidRegistry.getFluid(f));
		}
	}
}