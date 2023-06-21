package com.hbm.handler.crt;

import crafttweaker.IAction;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import com.hbm.inventory.SILEXRecipes;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@ZenRegister
@ZenClass("mods.ntm.SILEX")
public class SILEX {
	
	private static class ActionAddRecipe implements IAction{
		private int wavelength = -1;
		private int solution = 0;
		private int consumption = 0;
		private ItemStack input;
		private ItemStack[] outputs;
		private int[] outputWeights;

		public ActionAddRecipe(int wavelength, int solution, int consumption, IItemStack input, IItemStack[] outputItems, int[] outputWeights){
			this.wavelength = wavelength;
			this.solution = solution;
			this.consumption = consumption;

			this.input = CraftTweakerMC.getItemStack(input);
			this.outputs = new ItemStack[outputItems.length];
			for(int i = 0; i < outputItems.length; i++)
				this.outputs[i] = CraftTweakerMC.getItemStack(outputItems[i]);
			
			this.outputWeights = outputWeights;
		}
		@Override
		public void apply(){
			if(this.wavelength < 0 || this.wavelength > 8){
				CraftTweakerAPI.logError("ERROR SILEX recipe wavelength id must be between 0-8 not "+this.wavelength+"!");
				return;
			}
			if(this.solution < 0 || this.solution > 16000){
				CraftTweakerAPI.logError("ERROR SILEX recipe produced solution must be between 0-16k mb not "+this.solution+"mb!");
				return;
			}
			if(this.consumption < 0 || this.consumption > 16000){
				CraftTweakerAPI.logError("ERROR SILEX recipe consumption must be between 0-16k mb not "+this.consumption+"mb!");
				return;
			}
			if(this.input == null || this.input.isEmpty()){
				CraftTweakerAPI.logError("ERROR SILEX recipe output item can not be an empty/air stack!");
				return;
			}

			if(this.outputs == null || this.outputs.length == 0){
				CraftTweakerAPI.logError("ERROR SILEX recipe output items can not be an empty array!");
				return;
			}
			if(this.outputs.length > 6){
				CraftTweakerAPI.logError("ERROR SILEX recipe output item count must be <=6 not "+this.outputs.length+"!");
				return;
			}
			for(ItemStack i: this.outputs){
				if(i == null || i.isEmpty()){
					CraftTweakerAPI.logError("ERROR SILEX recipe output items can not include an empty/air stack!");
					return;
				}
			}
			
			if(this.outputWeights == null || this.outputs.length == 0){
				CraftTweakerAPI.logError("ERROR SILEX recipe weights can not be an empty array!");
				return;
			}
			if(this.outputWeights.length != this.outputs.length){
				CraftTweakerAPI.logError("ERROR SILEX recipe output item and weight arrays do not match in size!");
				return;
			}
			for(int i: this.outputWeights){
				if(i <= 0){
					CraftTweakerAPI.logError("ERROR SILEX recipe weights can not be negative so "+i+" is invalid!");
					return;
				}
			}

			SILEXRecipes.addRecipe(this.wavelength, this.solution, this.consumption, this.input, this.outputs, this.outputWeights);
		}
		@Override
		public String describe(){
			return "Adding NTM SILEX recipe ("+this.input+" -> "+this.outputs+")";
		}
	}

	@ZenMethod
	public static void addRecipe(int wavelength, int solution, int consumption, IItemStack input, IItemStack[] outputItems, int[] outputWeights){
		NTMCraftTweaker.postInitActions.add(new ActionAddRecipe(wavelength, solution, consumption, input, outputItems, outputWeights));
	}



	public static class ActionRemoveRecipe implements IAction{
		private ItemStack input;

		public ActionRemoveRecipe(IItemStack input){
			this.input = CraftTweakerMC.getItemStack(input);
		}
		@Override
		public void apply(){
			if(this.input == null || this.input.isEmpty()){
				CraftTweakerAPI.logError("ERROR SILEX input item can not be an empty/air stack!");
				return;
			}
			SILEXRecipes.removeRecipe(this.input);
		}
		@Override
		public String describe(){
			return "Removing NTM SILEX recipe for input "+this.input;
		}
	}

	@ZenMethod
	public static void removeRecipe(IItemStack input){
		NTMCraftTweaker.postInitActions.add(new ActionRemoveRecipe(input));
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
