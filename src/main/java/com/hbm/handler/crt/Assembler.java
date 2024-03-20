package com.hbm.handler.crt;

import com.hbm.inventory.RecipesCommon;
import crafttweaker.IAction;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IngredientStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.api.oredict.IngredientOreDict;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import com.hbm.inventory.AssemblerRecipes;
import com.hbm.inventory.RecipesCommon.ComparableStack;

import net.minecraft.item.ItemStack;

import java.util.Arrays;

@ZenRegister
@ZenClass("mods.ntm.Assembler")
public class Assembler {
	
	private static class ActionAddRecipe implements IAction{
		private IIngredient[] inputs;
		private ItemStack output;
		private int duration = 0;
		public ActionAddRecipe(IItemStack output, IIngredient[] inputs, int duration){
			this.inputs = inputs;
			this.output = CraftTweakerMC.getItemStack(output);
			this.duration = duration;
		}
		@Override
		public void apply(){
			if(this.inputs == null || this.inputs.length == 0){
				CraftTweakerAPI.logError("ERROR Assembler recipe input items can not be an empty array!");
				return;
			}
			if(this.inputs.length > 12){
				CraftTweakerAPI.logError("ERROR Assembler recipe input item count must be <=12 not "+this.inputs.length+"!");
				return;
			}
			for(IIngredient i: this.inputs){
				if(i == null ){
					CraftTweakerAPI.logError("ERROR Assembler recipe input items can not include an empty/air stack!");
					return;
				}
			}
			if(this.output == null || this.output.isEmpty()){
				CraftTweakerAPI.logError("ERROR Assembler recipe output item can not be an empty/air stack!");
				return;
			}
			if(this.duration < 1){
				CraftTweakerAPI.logError("ERROR Assembler recipe duraction must be >=1 not "+this.duration+"!");
				return;
			}
			RecipesCommon.AStack[] compInputs = NTMCraftTweaker.IIngredientsToAStack(this.inputs);
			AssemblerRecipes.makeRecipe(new ComparableStack(this.output), compInputs, this.duration);
		}
		@Override
		public String describe(){
			return "Adding NTM assembler recipe ("+ Arrays.toString(this.inputs) +" + "+this.duration+" ticks -> "+this.output+")";
		}
	}

	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient[] inputs, int duration){
		CraftTweakerAPI.apply(new ActionAddRecipe(output, inputs, duration));
	}

	@ZenMethod
	public static void replaceRecipe(IItemStack output, IIngredient[] inputs, int duration){
		NTMCraftTweaker.postInitActions.add(new ActionRemoveRecipe(output));
		NTMCraftTweaker.postInitActions.add(new ActionAddRecipe(output, inputs, duration));
	}



	public static class ActionRemoveRecipe implements IAction{
		private ItemStack output;

		public ActionRemoveRecipe(IItemStack output){
			this.output = CraftTweakerMC.getItemStack(output);
		}
		@Override
		public void apply(){
			if(this.output == null || this.output.isEmpty()){
				CraftTweakerAPI.logError("ERROR Assembler output item can not be an empty/air stack!");
				return;
			}
			AssemblerRecipes.removeRecipe(new ComparableStack(this.output));
		}
		@Override
		public String describe(){
			return "Removing NTM assembler recipe for output "+this.output;
		}
	}

	@ZenMethod
	public static void removeRecipe(IItemStack output){
		NTMCraftTweaker.postInitActions.add(new ActionRemoveRecipe(output));
	}

	//TEMPLATE
	// public static class ActionAddFuel implements IAction{
	// 	@Override
	// 	public void apply(){
		// if(){
		// 		CraftTweakerAPI.logError();
		// 		return;
		// 	}
	// 	}
	// 	@Override
	// 	public String describe(){
		// return "";
	// }
}
