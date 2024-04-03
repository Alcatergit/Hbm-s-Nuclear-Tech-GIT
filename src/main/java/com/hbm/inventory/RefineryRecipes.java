package com.hbm.inventory;

import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;
import com.hbm.util.Tuple.Pair;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.util.Tuple.Quartet;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class RefineryRecipes {

	public static final int heavy_frac_bitu = 30;
	public static final int heavy_frac_smear = 70;
	public static final int smear_frac_heat = 60;
	public static final int smear_frac_lube = 40;
	public static final int napht_frac_heat = 40;
	public static final int napht_frac_diesel = 60;
	public static final int light_frac_diesel = 40;
	public static final int light_frac_kero = 60;
	
	public static Map<Fluid, Quartet<Fluid, Fluid, Integer, Integer>> fractions = new HashMap<>();

	public static LinkedHashMap<Fluid, Pair<FluidStack[], ItemStack>> refineryRecipesMap = new LinkedHashMap<>();
	
	public static void registerRefineryRecipes() {
		refineryRecipesMap.put(ModForgeFluids.hotoil, new Pair(new FluidStack[]{ 
			new FluidStack(ModForgeFluids.heavyoil, 50), 
			new FluidStack(ModForgeFluids.naphtha, 25),
			new FluidStack(ModForgeFluids.lightoil, 15),
			new FluidStack(ModForgeFluids.petroleum, 10) }, 
			new ItemStack(ModItems.sulfur, 1)));
		
		refineryRecipesMap.put(ModForgeFluids.hotcrackoil, new Pair(new FluidStack[]{ 
			new FluidStack(ModForgeFluids.naphtha, 40), 
			new FluidStack(ModForgeFluids.lightoil, 30),
			new FluidStack(ModForgeFluids.aromatics, 15),
			new FluidStack(ModForgeFluids.unsaturateds, 15)	}, 
			new ItemStack(ModItems.oil_tar, 1)));

		refineryRecipesMap.put(ModForgeFluids.toxic_fluid, new Pair(new FluidStack[]{ 
			new FluidStack(ModForgeFluids.wastefluid, 50),
			new FluidStack(ModForgeFluids.wastegas, 40),
			new FluidStack(ModForgeFluids.corium_fluid, 4), 
			new FluidStack(ModForgeFluids.watz, 1)}, 
			new ItemStack(ModItems.nuclear_waste_tiny, 1)));
	}

	public static Pair<FluidStack[], ItemStack> getRecipe(Fluid f){
		if(f != null)
			return refineryRecipesMap.get(f);
		return null;
	}
	
	public static void registerFractions() {
		fractions.put(ModForgeFluids.heavyoil, new Quartet<>(ModForgeFluids.bitumen, ModForgeFluids.smear, heavy_frac_bitu, heavy_frac_smear));
		fractions.put(ModForgeFluids.smear, new Quartet<>(ModForgeFluids.heatingoil, ModForgeFluids.lubricant, smear_frac_heat, smear_frac_lube));
		fractions.put(ModForgeFluids.naphtha, new Quartet<>(ModForgeFluids.heatingoil, ModForgeFluids.diesel, napht_frac_heat, napht_frac_diesel));
		fractions.put(ModForgeFluids.lightoil, new Quartet<>(ModForgeFluids.diesel, ModForgeFluids.kerosene, light_frac_diesel, light_frac_kero));
	}
	
	public static Quartet<Fluid, Fluid, Integer, Integer> getFractions(Fluid oil) {
		return fractions.get(oil);
	}
}