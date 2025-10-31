package com.hbm.inventory;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MixerRecipes {

	public static Map<String, FluidStack[]> recipesFluidInputs = new HashMap<>();
	public static Map<String, Integer> recipesFluidOutputAmount = new HashMap<>();
	public static Map<String, Integer> recipesDurations = new HashMap<>();
	public static Map<String, AStack> recipesItemInputs = new HashMap<>();

	private static final List<String> recipeOutputFluidNames = new ArrayList<>();

	public static void copyChemplantRecipes() {
		if (ChemplantRecipes.recipeNames != null) {
			for (Integer i : ChemplantRecipes.recipeNames.keySet()) {
				FluidStack[] fStacks = ChemplantRecipes.recipeFluidOutputs.get(i);
				if (!(fStacks != null && fStacks.length == 1)) {
					continue;
				}
				AStack[] itemOut = ChemplantRecipes.recipeItemOutputs.get(i);
				if (itemOut != null && itemOut.length > 0)
					continue;
				AStack[] itemInputs = ChemplantRecipes.recipeItemInputs.get(i);
				AStack itemInput = null;
				if (itemInputs != null)
					if (itemInputs.length != 1) {
						continue;
					} else {
						itemInput = itemInputs[0];
					}
				if (fStacks[0] != null) {
					addRecipe(fStacks[0], ChemplantRecipes.recipeFluidInputs.get(i), itemInput, ChemplantRecipes.recipeDurations.get(i));
				}
			}
		}
	}

	public static void registerRecipes() {
		addRecipe(new FluidStack(ModForgeFluids.ETHANOL, 100), new FluidStack[]{ new FluidStack(FluidRegistry.WATER, 500)}, new ComparableStack(Items.SUGAR), 200);
		addRecipe(new FluidStack(ModForgeFluids.COLLOID, 500), new FluidStack[]{ new FluidStack(FluidRegistry.WATER, 500)}, new ComparableStack(ModItems.dust), 20);
		addRecipe(new FluidStack(ModForgeFluids.FISHOIL, 100), null, new ComparableStack(Items.FISH, 1, OreDictionary.WILDCARD_VALUE), 50);
		addRecipe(new FluidStack(ModForgeFluids.SUNFLOWEROIL, 100), null, new ComparableStack(Blocks.DOUBLE_PLANT, 1, 0), 50);
		addRecipe(new FluidStack(ModForgeFluids.NITROGLYCERIN, 1000), new FluidStack[]{ new FluidStack(ModForgeFluids.PETROLEUM, 1000), new FluidStack(ModForgeFluids.NITRIC_ACID, 1000)}, null, 20);
		addRecipe(new FluidStack(ModForgeFluids.BIOFUEL, 250), new FluidStack[]{ new FluidStack(ModForgeFluids.FISHOIL, 500), new FluidStack(ModForgeFluids.SUNFLOWEROIL, 500)}, null, 20);
		addRecipe(new FluidStack(ModForgeFluids.LUBRICANT, 1000), new FluidStack[]{ new FluidStack(ModForgeFluids.ETHANOL, 200), new FluidStack(ModForgeFluids.SUNFLOWEROIL, 800)}, null, 20);
	}

	public static void addRecipe(FluidStack output, FluidStack[] inputs, AStack inputItem, int duration) {
		if (output == null || output.getFluid() == null) {
			return;
		}
		Fluid f = output.getFluid();
		String fluidName = f.getName();

		if (!recipeOutputFluidNames.contains(fluidName)) {
			recipeOutputFluidNames.add(fluidName);
		}

		if (inputs != null)
			recipesFluidInputs.put(fluidName, inputs);
		recipesFluidOutputAmount.put(fluidName, output.amount);
		recipesDurations.put(fluidName, duration > 0 ? duration : 100);
		if (inputItem != null)
			recipesItemInputs.put(fluidName, inputItem);
	}

	public static int getFluidOutputAmount(Fluid output) {
		if (output == null) return 1;
		Integer x = recipesFluidOutputAmount.get(output.getName());
		if (x == null) return 1;
		return x;
	}

	public static int getRecipeDuration(Fluid output) {
		if (output == null) return 20;
		Integer x = recipesDurations.get(output.getName());
		if (x == null) return 20;
		return x;
	}

	public static boolean hasMixerRecipe(Fluid output) {
		if (output == null) return false;
		return recipesDurations.containsKey(output.getName());
	}

	public static FluidStack[] getInputFluidStacks(Fluid output) {
		if (output == null) return null;
		return recipesFluidInputs.get(output.getName());
	}

	public static boolean matchesInputItem(Fluid output, ItemStack inputItem) {
		if (output == null) return false;
		AStack in = recipesItemInputs.get(output.getName());
		if (in == null) return true;
		return inputItem != null && in.matchesRecipe(inputItem, true);
	}

	public static int getInputItemCount(Fluid output) {
		if (output == null) return 0;
		AStack in = recipesItemInputs.get(output.getName());
		if (in == null) return 0;
		return in.count();
	}

	public static AStack getInputItem(Fluid output) {
		if (output == null) return null;
		return recipesItemInputs.get(output.getName());
	}

	public static Fluid[] getInputFluids(Fluid output) {
		if (output == null) return null;
		FluidStack[] f = recipesFluidInputs.get(output.getName());
		if (f == null) return null;
		Fluid[] inputFluids = new Fluid[f.length];
		for (int i = 0; i < f.length; i++) {
			if (f[i] != null) {
				inputFluids[i] = f[i].getFluid();
			}
		}
		return inputFluids;
	}

	public static List<String> getRecipeOutputFluidNames() {
		return new ArrayList<>(recipeOutputFluidNames);
	}
}
