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
		private IIngredient[] inputs;

		private ItemStack[] output;

		private int tier;

		public ActionAddRecipe(IItemStack[] output, IIngredient[] inputs, int tier) {
			this.output = CraftTweakerMC.getItemStacks(output);
			this.inputs = inputs;
			this.tier = tier;
		}

		/**
		 * Executes what the action is supposed to do. This method can be called
		 * again if undo() has been called in between.
		 */
		@Override
		public void apply() {
			RecipesCommon.AStack[] compInputs = new RecipesCommon.AStack[this.inputs.length];
			for(int i = 0; i < this.inputs.length; i++){
				if(this.inputs[i] instanceof IOreDictEntry){
					compInputs[i] = new RecipesCommon.OreDictStack(((IOreDictEntry) this.inputs[i]).getName());
					continue;
				}

				if(this.inputs[i] instanceof IngredientStack){
					IIngredient  ingredient = (IIngredient) this.inputs[i].getInternal();
					if(ingredient instanceof IOreDictEntry){
						compInputs[i] = new RecipesCommon.OreDictStack(((IOreDictEntry) ingredient).getName(), this.inputs[i].getAmount());
						continue;
					}
				}
				if(this.inputs[i] instanceof IItemStack){
					compInputs[i] = new RecipesCommon.ComparableStack(CraftTweakerMC.getItemStack((IItemStack) this.inputs[i]));
					continue;
				}
				CraftTweakerAPI.logError("ERROR  Input "+this.inputs[i].toString());
				return;
			}
			AnvilRecipes.addConstructionRecipe(compInputs, this.output, this.tier);
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
			return "add anvil Recipe";
		}
	}

	public static class ActionRemoveRecipe implements IAction{
		private ItemStack[] output;
		private ItemStack[] inputs;

		public ActionRemoveRecipe(IItemStack[] output){
			this.output = CraftTweakerMC.getItemStacks(output);
		}

		public ActionRemoveRecipe(IItemStack[] input, IItemStack[] output){
			this.inputs = CraftTweakerMC.getItemStacks(input);
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
		CraftTweakerAPI.apply(new ActionAddRecipe(output, inputs, tier));
	}

	@ZenMethod
	public static void addRecipe(IItemStack[] output, IIngredient inputs, int tier){
		// inputs to array
		CraftTweakerAPI.apply(new ActionAddRecipe(output, new IIngredient[]{inputs}, tier));
	}

	@ZenMethod
	public static void addRecipe(IItemStack output,  IIngredient[] inputs, int tier){
		CraftTweakerAPI.apply(new ActionAddRecipe(new IItemStack[]{output}, inputs, tier));
	}

	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack inputs, int tier){
		CraftTweakerAPI.apply(new ActionAddRecipe(new IItemStack[]{output}, new IIngredient[]{inputs}, tier));
	}

	@ZenMethod
	public static void removeRecipe(IItemStack[] output){
		CraftTweakerAPI.logInfo("start remove recipe"+ Arrays.toString(output));
		NTMCraftTweaker.postInitActions.add(new Anvil.ActionRemoveRecipe(output));
	}

	@ZenMethod
	public static void removeRecipeByInput(IItemStack[] input){
		CraftTweakerAPI.logInfo("start remove recipe"+ Arrays.toString(input));
		NTMCraftTweaker.postInitActions.add(new Anvil.ActionRemoveRecipe(input, null));
	}
}
