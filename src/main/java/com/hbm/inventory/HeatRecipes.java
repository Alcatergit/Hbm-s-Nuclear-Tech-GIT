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

public class HeatRecipes {
	
	public static HashMap<Fluid, Fluid> hotFluids = new HashMap<Fluid, Fluid>();
	public static HashMap<Fluid, Integer> requiredTU = new HashMap<Fluid, Integer>();
	public static HashMap<Fluid, Integer> inputAmountHot = new HashMap<Fluid, Integer>();
	public static HashMap<Fluid, Integer> outputAmountHot = new HashMap<Fluid, Integer>();

	public static HashMap<Fluid, Fluid> coolFluids = new HashMap<Fluid, Fluid>();
	public static HashMap<Fluid, Integer> resultingTU = new HashMap<Fluid, Integer>();
	public static HashMap<Fluid, Integer> inputAmountCold = new HashMap<Fluid, Integer>();
	public static HashMap<Fluid, Integer> outputAmountCold = new HashMap<Fluid, Integer>();

	//for 100 mb
	public static void registerHeatRecipes() {
		addBoilRecipe(new FluidStack(FluidRegistry.WATER, 1), new FluidStack(ModForgeFluids.steam, 100), 100);
		addCoolRecipe(new FluidStack(ModForgeFluids.steam, 100), new FluidStack(ModForgeFluids.spentsteam, 1), 100);
		
		addBoilAndCoolRecipe(new FluidStack(ModForgeFluids.steam, 10), new FluidStack(ModForgeFluids.hotsteam, 1), 15);
		addBoilAndCoolRecipe(new FluidStack(ModForgeFluids.hotsteam, 10), new FluidStack(ModForgeFluids.superhotsteam, 1), 30);
		addBoilAndCoolRecipe(new FluidStack(ModForgeFluids.superhotsteam, 10), new FluidStack(ModForgeFluids.ultrahotsteam, 1), 120);
		addBoilAndCoolRecipe(new FluidStack(ModForgeFluids.oil, 1), new FluidStack(ModForgeFluids.hotoil, 1), 300);
		addBoilAndCoolRecipe(new FluidStack(ModForgeFluids.crackoil, 1), new FluidStack(ModForgeFluids.hotcrackoil, 1), 300);
		addBoilAndCoolRecipe(new FluidStack(ModForgeFluids.coolant, 1), new FluidStack(ModForgeFluids.hotcoolant, 1), 500);

		//Compat
		addBoilRecipe("crude_oil", 1, "hotoil", 1, 300); //thermalfoundation
		addBoilRecipe("oil_medium", 1, "hotoil", 1, 300);
		addBoilRecipe("oilgc", 1, "hotoil", 1, 300); //galacticraft
		addBoilRecipe("biofuel", 1, "fuel", 1, 100); //galacticraft & industrialforegoing
		addBoilRecipe("petroil", 1, "fuel", 1, 100); //galacticraft
		addBoilRecipe("refined_fuel", 1, "petroil", 1, 100); //thermalfoundation
		addBoilRecipe("sulphuricacid", 1, "sulfuric_acid", 1, 100); //galacticraft
		addBoilRecipe("sulfuricacid", 1, "sulfuric_acid", 1, 100); //mekanism 
		addBoilAndCoolRecipe("liquidoxygen", 1, "oxygen", 1, 1); //mekanism
		addBoilAndCoolRecipe("liquidtritium", 1, "tritium", 1, 1); //mekanism
		addBoilAndCoolRecipe("liquiddeuterium", 1, "deuterium", 1, 1); //mekanism
		addBoilAndCoolRecipe("liquidhydrogen", 1, "hydrogen", 1, 1); //mekanism
		addBoilRecipe("refined_biofuel", 1, "biofuel", 1, 10); //thermalfoundation
		addBoilAndCoolRecipe("ic2coolant", 1, "ic2hot_coolant", 1, 450); //mekanism", 1, 1); //IC2
	}

	public static void setFluidsForRBMKLoader(){
		HashSet<Fluid> fluids = new HashSet<Fluid>();
		for(Map.Entry<Fluid, Fluid> entry : hotFluids.entrySet()) {
			fluids.add(entry.getKey());
			fluids.add(entry.getValue());
		}
		((BlockBaseVisualFluidConnectable)ModBlocks.rbmk_loader).addFluids(fluids.toArray(new Fluid[0]));
	}

	public static Fluid getBoilFluid(Fluid f){
		if(f != null)
			return hotFluids.get(f);
		return null;
	}

	public static int getRequiredHeat(Fluid f){
		Integer heat = requiredTU.get(f);
		if(heat != null)
			return heat;
		return -1;
	}

	public static int getInputAmountHot(Fluid f){
		Integer heat = inputAmountHot.get(f);
		if(heat != null)
			return heat;
		return -1;
	}

	public static int getOutputAmountHot(Fluid f){
		Integer heat = outputAmountHot.get(f);
		if(heat != null)
			return heat;
		return -1;
	}

	public static Fluid getCoolFluid(Fluid f){
		if(f != null)
			return coolFluids.get(f);
		return null;
	}

	public static int getResultingHeat(Fluid f){
		Integer heat = resultingTU.get(f);
		if(heat != null)
			return heat;
		return -1;
	}

	public static int getInputAmountCold(Fluid f){
		Integer heat = inputAmountCold.get(f);
		if(heat != null)
			return heat;
		return -1;
	}

	public static int getOutputAmountCold(Fluid f){
		Integer heat = outputAmountCold.get(f);
		if(heat != null)
			return heat;
		return -1;
	}

	public static void addBoilAndCoolRecipe(FluidStack cold, FluidStack hot, int heat){
		addBoilRecipe(cold, hot, heat);
		addCoolRecipe(hot, cold, heat);
	}

	public static void addBoilRecipe(FluidStack cold, FluidStack hot, int heat){
		hotFluids.put(cold.getFluid(), hot.getFluid());
		requiredTU.put(cold.getFluid(), heat);
		inputAmountHot.put(cold.getFluid(), cold.amount);
		outputAmountHot.put(cold.getFluid(), hot.amount);
	}

	public static void addCoolRecipe(FluidStack hot, FluidStack cold, int heat){
		coolFluids.put(hot.getFluid(), cold.getFluid());
		resultingTU.put(hot.getFluid(), heat);
		inputAmountCold.put(hot.getFluid(), hot.amount);
		outputAmountCold.put(hot.getFluid(), cold.amount);
	}

	public static boolean hasBoilRecipe(Fluid cold){
		return hotFluids.containsKey(cold);
	}

	public static boolean hasCoolRecipe(Fluid hot){
		return coolFluids.containsKey(hot);
	}

	public static void addBoilRecipe(String cold, int coldAmount, String hot, int hotAmount, int heat){
		if(FluidRegistry.isFluidRegistered(hot) && FluidRegistry.isFluidRegistered(cold)){
			addBoilRecipe(new FluidStack(FluidRegistry.getFluid(cold), coldAmount), new FluidStack(FluidRegistry.getFluid(hot), hotAmount), heat);
		}
	}

	public static void addCoolRecipe(String hot, int hotAmount, String cold, int coldAmount, int heat){
		if(FluidRegistry.isFluidRegistered(hot) && FluidRegistry.isFluidRegistered(cold)){
			addCoolRecipe(new FluidStack(FluidRegistry.getFluid(hot), hotAmount), new FluidStack(FluidRegistry.getFluid(cold), coldAmount), heat);
		}
	}

	public static void addBoilAndCoolRecipe(String cold, int coldAmount, String hot, int hotAmount, int heat){
		if(FluidRegistry.isFluidRegistered(hot) && FluidRegistry.isFluidRegistered(cold)){
			addBoilRecipe(new FluidStack(FluidRegistry.getFluid(cold), coldAmount), new FluidStack(FluidRegistry.getFluid(hot), hotAmount), heat);
			addCoolRecipe(new FluidStack(FluidRegistry.getFluid(hot), hotAmount), new FluidStack(FluidRegistry.getFluid(cold), coldAmount), heat);
		}
	}

	public static void removeBoilRecipe(String cold){
		if(FluidRegistry.isFluidRegistered(cold)){
			Fluid f = FluidRegistry.getFluid(cold);
			hotFluids.remove(f);
			requiredTU.remove(f);
			inputAmountHot.remove(f);
			outputAmountHot.remove(f);
		}
	}

	public static void removeCoolRecipe(String hot){
		if(FluidRegistry.isFluidRegistered(hot)){
			Fluid f = FluidRegistry.getFluid(hot);
			coolFluids.remove(f);
			resultingTU.remove(f);
			inputAmountCold.remove(f);
			outputAmountCold.remove(f);
		}
	}

	// return: FluidType, amount produced, amount required, heat required (°C * 100)
	public static Object[] getBoilerOutput(Fluid type) {
		if(hasBoilRecipe(type)){
			return new Object[] { getBoilFluid(type), getOutputAmountHot(type), getInputAmountHot(type), (getBoilFluid(type).getTemperature()-273) * 100 };
		}
		return null;
	}
}