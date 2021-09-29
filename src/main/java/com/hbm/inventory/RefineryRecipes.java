package com.hbm.inventory;

import java.util.HashMap;
import java.util.Map;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.util.Tuple.Quartet;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public class RefineryRecipes {

	/// fractions in percent ///
	public static final int oil_frac_heavy = 50;
	public static final int oil_frac_naph = 25;
	public static final int oil_frac_light = 15;
	public static final int oil_frac_petro = 10;

	public static final int heavy_frac_bitu = 30;
	public static final int heavy_frac_smear = 70;
	public static final int smear_frac_heat = 60;
	public static final int smear_frac_lube = 40;
	public static final int napht_frac_heat = 40;
	public static final int napht_frac_diesel = 60;
	public static final int light_frac_diesel = 40;
	public static final int light_frac_kero = 60;
	
	private static Map<Fluid, Quartet<Fluid, Fluid, Integer, Integer>> fractions = new HashMap<>();
	
	public static Map<Object, Object[]> getRefineryRecipe() {

		Map<Object, Object[]> recipes = new HashMap<Object, Object[]>();
		
		recipes.put(ItemFluidIcon.getStackWithQuantity(ModForgeFluids.hotoil, 1000),
				new ItemStack[] {
						ItemFluidIcon.getStackWithQuantity(ModForgeFluids.heavyoil, oil_frac_heavy * 10),
						ItemFluidIcon.getStackWithQuantity(ModForgeFluids.heavyoil, oil_frac_naph * 10),
						ItemFluidIcon.getStackWithQuantity(ModForgeFluids.heavyoil, oil_frac_light * 10),
						ItemFluidIcon.getStackWithQuantity(ModForgeFluids.heavyoil, oil_frac_petro * 10),
						new ItemStack(ModItems.sulfur, 1) });
		
		return recipes;
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