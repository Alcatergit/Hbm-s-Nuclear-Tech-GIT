package com.hbm.inventory;

import java.util.LinkedHashMap;
import com.hbm.util.Tuple.Pair;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.items.ModItems;
import com.hbm.items.ItemEnums.EnumTarType;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class RefineryRecipes {

	public static LinkedHashMap<Fluid, Pair<FluidStack[], ItemStack>> refineryRecipesMap = new LinkedHashMap<>();
	
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
			DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRACK)));

		refineryRecipesMap.put(ModForgeFluids.HOTOIL_DS, new Pair(new FluidStack[]{
			new FluidStack(ModForgeFluids.HEAVYOIL,		30),
			new FluidStack(ModForgeFluids.NAPHTHA_DS,	35),
			new FluidStack(ModForgeFluids.LIGHTOIL_DS,	20),
			new FluidStack(ModForgeFluids.UNSATURATEDS,	15) },
			DictFrame.fromOne(ModItems.oil_tar, EnumTarType.PARAFFIN)));
		
		refineryRecipesMap.put(ModForgeFluids.HOTCRACKOIL_DS, new Pair(new FluidStack[]{
			new FluidStack(ModForgeFluids.NAPHTHA_DS,		35),
			new FluidStack(ModForgeFluids.LIGHTOIL_DS,		35),
			new FluidStack(ModForgeFluids.AROMATICS,		15),
			new FluidStack(ModForgeFluids.UNSATURATEDS,		15)},
			DictFrame.fromOne(ModItems.oil_tar, EnumTarType.PARAFFIN)));

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
}