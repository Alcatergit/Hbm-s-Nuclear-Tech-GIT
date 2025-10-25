package com.hbm.inventory;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.BlockBaseVisualFluidConnectable;
import com.hbm.forgefluid.ModForgeFluids;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class HeatRecipes {
	
	public static HashMap<String, Fluid> hotFluids = new HashMap<String, Fluid>();
	public static HashMap<String, Integer> requiredTU = new HashMap<String, Integer>();
	public static HashMap<String, Integer> inputAmountHot = new HashMap<String, Integer>();
	public static HashMap<String, Integer> outputAmountHot = new HashMap<String, Integer>();

	public static HashMap<String, Fluid> coolFluids = new HashMap<String, Fluid>();
	public static HashMap<String, Integer> resultingTU = new HashMap<String, Integer>();
	public static HashMap<String, Integer> inputAmountCold = new HashMap<String, Integer>();
	public static HashMap<String, Integer> outputAmountCold = new HashMap<String, Integer>();
	//for 100 mb
	public static void registerHeatRecipes() {
		addBoilRecipe(FluidRegistry.WATER, 1, ModForgeFluids.STEAM, 100, 100);
		addCoolRecipe(ModForgeFluids.STEAM, 100, ModForgeFluids.SPENTSTEAM, 1, 100);
		
		addBoilAndCoolRecipe(ModForgeFluids.STEAM, 10, ModForgeFluids.HOTSTEAM, 1, 15);
		addBoilAndCoolRecipe(ModForgeFluids.HOTSTEAM, 10, ModForgeFluids.SUPERHOTSTEAM, 1, 30);
		addBoilAndCoolRecipe(ModForgeFluids.SUPERHOTSTEAM, 10, ModForgeFluids.ULTRAHOTSTEAM, 1, 120);
		addBoilAndCoolRecipe(ModForgeFluids.OIL, 1, ModForgeFluids.HOTOIL, 1, 300);
		addBoilAndCoolRecipe(ModForgeFluids.CRACKOIL, 1, ModForgeFluids.HOTCRACKOIL, 1, 300);
		addBoilAndCoolRecipe(ModForgeFluids.OIL_DS, 1, ModForgeFluids.HOTOIL_DS, 1, 300);
		addBoilAndCoolRecipe(ModForgeFluids.CRACKOIL_DS, 1, ModForgeFluids.HOTCRACKOIL_DS, 1, 300);
		addBoilAndCoolRecipe(ModForgeFluids.COOLANT, 1, ModForgeFluids.HOTCOOLANT, 1, 500);

		//Compat
        addBoilRecipe("crude_oil", 1, "hotoil", 1, 300); //thermalfoundation
		addBoilRecipe("oil_medium", 1, "hotoil", 1, 300);
		addBoilRecipe("oilgc", 1, "hotoil", 1, 300); //galacticraft
		addBoilRecipe("biofuel", 1, "fuel", 1, 100); //galacticraft & industrialforegoing
		addBoilRecipe("refined_fuel", 1, "petroil", 1, 100); //thermalfoundation
		addBoilRecipe("sulphuricacid", 1, "sulfuric_acid", 1, 100); //galacticraft
		addBoilRecipe("sulfuricacid", 1, "sulfuric_acid", 1, 100); //mekanism 
		addBoilAndCoolRecipe("liquidoxygen", 1, "oxygen", 1, 1); //mekanism
		addBoilAndCoolRecipe("liquidtritium", 1, "tritium", 1, 1); //mekanism
		addBoilAndCoolRecipe("liquiddeuterium", 1, "deuterium", 1, 1); //mekanism
		addBoilAndCoolRecipe("liquidhydrogen", 1, "hydrogen", 1, 1); //mekanism
		addBoilRecipe("refined_biofuel", 1, "biofuel", 1, 10); //thermalfoundation
		addBoilAndCoolRecipe("ic2coolant", 1, "ic2hot_coolant", 1, 450); //IC2
	}

	public static void setFluidsForRBMKLoader(){
		HashSet<Fluid> fluids = new HashSet<Fluid>();
		for(Map.Entry<String, Fluid> entry : hotFluids.entrySet()) {
			fluids.add(FluidRegistry.getFluid(entry.getKey()));
			fluids.add(entry.getValue());
		}
		((BlockBaseVisualFluidConnectable)ModBlocks.rbmk_loader).addFluids(fluids.toArray(new Fluid[0]));
	}

	public static Fluid getBoilFluid(Fluid f){
		if(f != null)
			return hotFluids.get(f.getName());
		return null;
	}

	public static int getRequiredHeat(Fluid f){
		Integer heat = requiredTU.get(f.getName());
		if(heat != null)
			return heat;
		return -1;
	}

	public static int getInputAmountHot(Fluid f){
		Integer heat = inputAmountHot.get(f.getName());
		if(heat != null)
			return heat;
		return -1;
	}

	public static int getOutputAmountHot(Fluid f){
		Integer heat = outputAmountHot.get(f.getName());
		if(heat != null)
			return heat;
		return -1;
	}

	public static Fluid getCoolFluid(Fluid f){
		if(f != null)
			return coolFluids.get(f.getName());
		return null;
	}

	public static int getResultingHeat(Fluid f){
		Integer heat = resultingTU.get(f.getName());
		if(heat != null)
			return heat;
		return -1;
	}

	public static int getInputAmountCold(Fluid f){
		Integer heat = inputAmountCold.get(f.getName());
		if(heat != null)
			return heat;
		return -1;
	}

	public static int getOutputAmountCold(Fluid f){
		Integer heat = outputAmountCold.get(f.getName());
		if(heat != null)
			return heat;
		return -1;
	}

	public static boolean hasBoilRecipe(Fluid cold){
        if(cold == null) return false;
		return hotFluids.containsKey(cold.getName());
	}

	public static boolean hasCoolRecipe(Fluid hot){
		return coolFluids.containsKey(hot.getName());
	}

    public static void addBoilRecipe(Fluid cold, int coldAmount, Fluid hot, int hotAmount, int heat) {
        addBoilRecipe(cold.getName(), coldAmount, hot.getName(), hotAmount, heat);
    }

	public static void addBoilRecipe(String cold, int coldAmount, String hot, int hotAmount, int heat){
		if(FluidRegistry.isFluidRegistered(hot) && FluidRegistry.isFluidRegistered(cold)){
            hotFluids.put(cold, FluidRegistry.getFluid(hot));
            requiredTU.put(cold, heat);
            inputAmountHot.put(cold, coldAmount);
            outputAmountHot.put(cold, hotAmount);
		}
	}

    public static void  addCoolRecipe(Fluid hot, int hotAmount, Fluid cold, int coldAmount, int heat) {
        addCoolRecipe(hot.getName(), hotAmount, cold.getName(), coldAmount, heat);
    }

	public static void addCoolRecipe(String hot, int hotAmount, String cold, int coldAmount, int heat){
		if(FluidRegistry.isFluidRegistered(hot) && FluidRegistry.isFluidRegistered(cold)){
            coolFluids.put(hot, FluidRegistry.getFluid(cold));
            resultingTU.put(hot, heat);
            inputAmountCold.put(hot, hotAmount);
            outputAmountCold.put(hot, coldAmount);
		}
	}

    public static void addBoilAndCoolRecipe(Fluid cold, int coldAmount, Fluid hot, int hotAmount, int heat) {
        addBoilAndCoolRecipe(cold.getName(), coldAmount, hot.getName(), hotAmount, heat);
    }

    public static void addBoilAndCoolRecipe(String cold, int coldAmount, String hot, int hotAmount, int heat){
		if(FluidRegistry.isFluidRegistered(hot) && FluidRegistry.isFluidRegistered(cold)){
			addBoilRecipe(cold, coldAmount, hot, hotAmount, heat);
			addCoolRecipe(hot, hotAmount, cold, coldAmount, heat);
		}
	}

	public static void removeBoilRecipe(String cold){
		if(FluidRegistry.isFluidRegistered(cold)){
			hotFluids.remove(cold);
			requiredTU.remove(cold);
			inputAmountHot.remove(cold);
			outputAmountHot.remove(cold);
		}
	}

	public static void removeCoolRecipe(String hot){
		if(FluidRegistry.isFluidRegistered(hot)){
			coolFluids.remove(hot);
			resultingTU.remove(hot);
			inputAmountCold.remove(hot);
			outputAmountCold.remove(hot);
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