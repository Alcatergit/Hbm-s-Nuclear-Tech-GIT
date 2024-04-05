package com.hbm.handler.crt;


import com.hbm.inventory.RecipesCommon;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;


import com.hbm.inventory.AnvilRecipes;

import java.util.Arrays;

@ZenRegister
@ZenClass("mods.ntm.Anvil")
public class Anvil {
	private static class ActionAddRecipe implements IAction {
		private RecipesCommon.AStack[] inputs;

		private ItemStack[] output;

		private int tier;

		public ActionAddRecipe(IItemStack[] output, IIngredient[] inputs, int tier) {
			this.output = CraftTweakerMC.getItemStacks(output);
			this.inputs = NTMCraftTweaker.IIngredientsToAStack(inputs);
			this.tier = tier;
		}

		/**
		 * Executes what the action is supposed to do. This method can be called
		 * again if undo() has been called in between.
		 */
		@Override
		public void apply() {
			AnvilRecipes.addConstructionRecipe(this.inputs, this.output, this.tier);
		}

		/**
		 * Describes, in a single human-readable sentence, what this specific action
		 * is doing. Used in logging messages, lists, ...
		 * <p>
		 * Try to be as descriptive as possible without being too verbose.
		 * <p>
		 * Examples: - Adding Peach planks to the woodPlanks ore dictionary entry -
		 * Removing a recipe for Iron Ore
		 *
		 * @return the description of this action
		 */
		@Override
		public String describe() {
			return "add anvil Recipe for " + Arrays.toString(this.output) + " with inputs " + Arrays.toString(this.inputs) + " with tier " + this.tier;
		}
	}

	public static class ActionRemoveRecipe implements IAction{
		private ItemStack[]  output;
		private RecipesCommon.AStack[] inputs;

		public ActionRemoveRecipe(IItemStack[] output){
			this.output = CraftTweakerMC.getItemStacks(output);
		}

		public ActionRemoveRecipe(IIngredient[] input, IItemStack[] output){
			this.inputs = NTMCraftTweaker.IIngredientsToAStack(input);
			this.output = CraftTweakerMC.getItemStacks(output);
		}
		@Override
		public void apply(){
			if(this.inputs != null && this.output == null ){
				AnvilRecipes.removeConstructionRecipeByInput(this.inputs);
				return;
			}
			if(this.output == null ){
				CraftTweakerAPI.logError("ERROR Anvil output item can not be an empty/air stack!");
				return;
			}
			AnvilRecipes.removeConstructionRecipe(this.output);
		}
		@Override
		public String describe(){
			return "Removing NTM Anvil recipe for output "+ Arrays.toString(this.output);
		}
	}

	@ZenMethod
	public static void addRecipe(IItemStack[] output, IIngredient[] inputs, int tier){
		NTMCraftTweaker.postInitActions.add(new ActionAddRecipe(output, inputs, tier));
	}

	@ZenMethod
	public static void addRecipe(IItemStack[] output, IIngredient inputs, int tier){
		// inputs to array
		NTMCraftTweaker.postInitActions.add(new ActionAddRecipe(output, new IIngredient[]{inputs}, tier));
	}

	@ZenMethod
	public static void addRecipe(IItemStack output,  IIngredient[] inputs, int tier){
		NTMCraftTweaker.postInitActions.add(new ActionAddRecipe(new IItemStack[]{output}, inputs, tier));
	}

	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient inputs, int tier){
		NTMCraftTweaker.postInitActions.add(new ActionAddRecipe(new IItemStack[]{output}, new IIngredient[]{inputs}, tier));
	}

	@ZenMethod
	public static void removeRecipe(IItemStack[] outputs){
		CraftTweakerAPI.logInfo("start remove recipe"+ Arrays.toString(outputs));
		NTMCraftTweaker.postInitActions.add(new Anvil.ActionRemoveRecipe(outputs));
	}

	@ZenMethod
	public static void removeRecipeByInput(IIngredient[] inputs){
		CraftTweakerAPI.logInfo("start remove recipe"+ Arrays.toString(inputs));
		NTMCraftTweaker.postInitActions.add(new Anvil.ActionRemoveRecipe(inputs, null));
	}

	@ZenMethod
	public static void replaceRecipe(IItemStack[] oldRecipe, IIngredient inputs, int tier){
		NTMCraftTweaker.postInitActions.add(new Anvil.ActionRemoveRecipe(oldRecipe));
		NTMCraftTweaker.postInitActions.add(new Anvil.ActionAddRecipe(oldRecipe, new IIngredient[]{inputs}, tier));
	}

}
