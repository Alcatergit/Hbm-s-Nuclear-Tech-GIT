package com.hbm.inventory;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.ModItems;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.Tuple.Quartet;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

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

	public static HashMap<Fluid, Pair<FluidStack[], ItemStack>> refineryRecipesMap = new HashMap<>();
	
	public static void registerRefineryRecipes() {
		refineryRecipesMap.put(ModForgeFluids.HOTOIL, new Pair(new FluidStack[]{
			new FluidStack(ModForgeFluids.HEAVYOIL, 50),
			new FluidStack(ModForgeFluids.NAPHTHA, 25),
			new FluidStack(ModForgeFluids.LIGHTOIL, 15),
			new FluidStack(ModForgeFluids.PETROLEUM, 10) },
			new ItemStack(ModItems.sulfur, 1)));
		
		refineryRecipesMap.put(ModForgeFluids.HOTCRACKOIL, new Pair(new FluidStack[]{
			new FluidStack(ModForgeFluids.NAPHTHA, 40),
			new FluidStack(ModForgeFluids.LIGHTOIL, 30),
			new FluidStack(ModForgeFluids.AROMATICS, 15),
			new FluidStack(ModForgeFluids.UNSATURATEDS, 15)	},
			new ItemStack(ModItems.oil_tar, 1)));

		refineryRecipesMap.put(ModForgeFluids.TOXIC_FLUID, new Pair(new FluidStack[]{
			new FluidStack(ModForgeFluids.WASTEFLUID, 50),
			new FluidStack(ModForgeFluids.WASTEGAS, 40),
			new FluidStack(ModForgeFluids.CORIUM_FLUID, 4),
			new FluidStack(ModForgeFluids.MUD_FLUID, 1)},
			new ItemStack(ModItems.nuclear_waste_tiny, 1)));
	}

	public static Pair<FluidStack[], ItemStack> getRecipe(Fluid f){
		if(f != null)
			return refineryRecipesMap.get(f);
		return null;
	}
	
	public static void registerFractions() {
		fractions.put(ModForgeFluids.HEAVYOIL, new Quartet<>(ModForgeFluids.BITUMEN, ModForgeFluids.SMEAR, heavy_frac_bitu, heavy_frac_smear));
		fractions.put(ModForgeFluids.SMEAR, new Quartet<>(ModForgeFluids.HEATINGOIL, ModForgeFluids.LUBRICANT, smear_frac_heat, smear_frac_lube));
		fractions.put(ModForgeFluids.NAPHTHA, new Quartet<>(ModForgeFluids.HEATINGOIL, ModForgeFluids.DIESEL, napht_frac_heat, napht_frac_diesel));
		fractions.put(ModForgeFluids.LIGHTOIL, new Quartet<>(ModForgeFluids.DIESEL, ModForgeFluids.KEROSENE, light_frac_diesel, light_frac_kero));
	}
	
	public static Quartet<Fluid, Fluid, Integer, Integer> getFractions(Fluid oil) {
		return fractions.get(oil);
	}
}