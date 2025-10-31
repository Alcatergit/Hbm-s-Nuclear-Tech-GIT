package com.hbm.inventory;

import com.hbm.forgefluid.ModForgeFluids;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.HashMap;

public class EngineRecipes {
	
	public static HashMap<Fluid, Long> combustionEnergies = new HashMap<Fluid, Long>();
	public static HashMap<Fluid, FuelGrade> fuelGrades = new HashMap<Fluid, FuelGrade>();

	//for 1000 mb
	public static void registerEngineRecipes() {
		addFuel(ModForgeFluids.HYDROGEN, FuelGrade.HIGH, 10_000);
		addFuel(ModForgeFluids.DEUTERIUM, FuelGrade.HIGH, 10_000);
		addFuel(ModForgeFluids.TRITIUM, FuelGrade.HIGH, 10_000);
		addFuel(ModForgeFluids.HEAVYOIL, FuelGrade.LOW, 25_000);
		addFuel(ModForgeFluids.HEATINGOIL, FuelGrade.LOW, 100_000);
		addFuel(ModForgeFluids.RECLAIMED, FuelGrade.LOW, 200_000);
		addFuel(ModForgeFluids.PETROIL, FuelGrade.MEDIUM, 300_000);
		addFuel(ModForgeFluids.NAPHTHA, FuelGrade.MEDIUM, 200_000);
		addFuel(ModForgeFluids.DIESEL, FuelGrade.HIGH, 500_000);
		addFuel(ModForgeFluids.LIGHTOIL, FuelGrade.MEDIUM, 500_000);
		addFuel(ModForgeFluids.KEROSENE, FuelGrade.AERO, 1_250_000);
		addFuel(ModForgeFluids.BIOGAS, FuelGrade.AERO, 500_000);
		addFuel(ModForgeFluids.BIOFUEL, FuelGrade.HIGH, 400_000);
		addFuel(ModForgeFluids.NITAN, FuelGrade.HIGH, 5_000_000);
		addFuel(ModForgeFluids.BALEFIRE, FuelGrade.HIGH, 2_500_000);
		addFuel(ModForgeFluids.GASOLINE, FuelGrade.HIGH, 1_000_000);
		addFuel(ModForgeFluids.ETHANOL, FuelGrade.HIGH, 200_000);
		addFuel(ModForgeFluids.FISHOIL, FuelGrade.LOW, 50_000);
		addFuel(ModForgeFluids.SUNFLOWEROIL, FuelGrade.LOW, 80_000);
		addFuel(ModForgeFluids.GAS, FuelGrade.GAS, 100_000);
		addFuel(ModForgeFluids.PETROLEUM, FuelGrade.GAS, 300_000);
		addFuel(ModForgeFluids.AROMATICS, FuelGrade.GAS, 150_000);
		addFuel(ModForgeFluids.UNSATURATEDS, FuelGrade.GAS, 250_000);

		//Compat
		addFuel("biofuel", FuelGrade.HIGH, 400_000); //galacticraft & industrialforegoing
		addFuel("petroil", FuelGrade.MEDIUM, 300_000); //galacticraft
		addFuel("refined_fuel", FuelGrade.HIGH, 1_000_000); //thermalfoundation
		addFuel("refined_biofuel", FuelGrade.HIGH, 400_000); //thermalfoundation
	
	}

	public static enum FuelGrade {
		LOW("trait.fuelgrade.low"),			//heating and industrial oil				< star engine, iGen
		MEDIUM("trait.fuelgrade.medium"),	//petroil									< diesel generator
		HIGH("trait.fuelgrade.high"),		//diesel, gasoline							< HP engine
		AERO("trait.fuelgrade.aero"),	//kerosene and other light aviation fuels	< turbofan
		GAS("trait.fuelgrade.gas");		//fuel gasses like NG, PG and syngas		< gas turbine
		
		private String grade;
		
		private FuelGrade(String grade) {
			this.grade = grade;
		}
		
		public String getGrade() {
			return this.grade;
		}
	}

	public static long getEnergy(Fluid f){
		if(f != null)
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