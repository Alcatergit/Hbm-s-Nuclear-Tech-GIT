package com.hbm.inventory;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.BlockBaseVisualFluidConnectable;
import com.hbm.forgefluid.ModForgeFluids;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidRegistry;

public class EngineRecipes {
	
	public static HashMap<Fluid, Long> combustionEnergies = new HashMap<Fluid, Long>();
	public static HashMap<Fluid, FuelGrade> fuelGrades = new HashMap<Fluid, FuelGrade>();

	//for 1000 mb
	public static void registerEngineRecipes() {
		addFuel(ModForgeFluids.hydrogen, FuelGrade.HIGH, 10_000);
		addFuel(ModForgeFluids.deuterium, FuelGrade.HIGH, 10_000);
		addFuel(ModForgeFluids.tritium, FuelGrade.HIGH, 10_000);
		addFuel(ModForgeFluids.heavyoil, FuelGrade.LOW, 25_000);
		addFuel(ModForgeFluids.heatingoil, FuelGrade.LOW, 100_000);
		addFuel(ModForgeFluids.reclaimed, FuelGrade.LOW, 200_000);
		addFuel(ModForgeFluids.petroil, FuelGrade.MEDIUM, 300_000);
		addFuel(ModForgeFluids.naphtha, FuelGrade.MEDIUM, 200_000);
		addFuel(ModForgeFluids.diesel, FuelGrade.HIGH, 500_000);
		addFuel(ModForgeFluids.lightoil, FuelGrade.MEDIUM, 500_000);
		addFuel(ModForgeFluids.kerosene, FuelGrade.AERO, 1_250_000);
		addFuel(ModForgeFluids.biogas, FuelGrade.AERO, 500_000);
		addFuel(ModForgeFluids.biofuel, FuelGrade.HIGH, 400_000);
		addFuel(ModForgeFluids.nitan, FuelGrade.HIGH, 5_000_000);
		addFuel(ModForgeFluids.balefire, FuelGrade.HIGH, 2_500_000);
		addFuel(ModForgeFluids.gasoline, FuelGrade.HIGH, 1_000_000);
		addFuel(ModForgeFluids.ethanol, FuelGrade.HIGH, 200_000);
		addFuel(ModForgeFluids.fishoil, FuelGrade.LOW, 50_000);
		addFuel(ModForgeFluids.sunfloweroil, FuelGrade.LOW, 80_000);
		addFuel(ModForgeFluids.gas, FuelGrade.GAS, 100_000);
		addFuel(ModForgeFluids.petroleum, FuelGrade.GAS, 300_000);
		addFuel(ModForgeFluids.aromatics, FuelGrade.GAS, 150_000);
		addFuel(ModForgeFluids.unsaturateds, FuelGrade.GAS, 250_000);

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