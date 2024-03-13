package com.hbm.handler.crt;


import com.hbm.inventory.RecipesCommon;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;


import com.hbm.inventory.AnvilRecipes;

import java.util.Arrays;

@ZenRegister
@ZenClass("mods.ntm.Anvil")
public class Anvil {
	private static class ActionAddRecipe implements IAction {

		/**
		 * Executes what the action is supposed to do. This method can be called
		 * again if undo() has been called in between.
		 */
		@Override
		public void apply() {

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

		public ActionRemoveRecipe(IItemStack[] output){
			this.output = CraftTweakerMC.getItemStacks(output);
		}
		@Override
		public void apply(){
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
	public static void removeRecipe(IItemStack[] output){
		CraftTweakerAPI.logInfo("start remove recipe"+ Arrays.toString(output));
		NTMCraftTweaker.postInitActions.add(new Anvil.ActionRemoveRecipe(output));
	}
}
