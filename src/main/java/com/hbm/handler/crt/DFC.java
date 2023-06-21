package com.hbm.handler.crt;

import crafttweaker.IAction;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import com.hbm.inventory.DFCRecipes;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@ZenRegister
@ZenClass("mods.ntm.DFC")
public class DFC {
	
	private static class ActionAddRecipe implements IAction{
		private ItemStack input;
		private ItemStack output;
		private long spark = 0;
		public ActionAddRecipe(IItemStack input, IItemStack output, long spark){
			this.input = CraftTweakerMC.getItemStack(input);
			this.output = CraftTweakerMC.getItemStack(output);
			this.spark = spark;
		}
		@Override
		public void apply(){
			if(this.input == null || this.input.isEmpty()){
				CraftTweakerAPI.logError("ERROR DFC recipe input item can not be an empty/air stack!");
				return;
			}
			if(this.output == null || this.output.isEmpty()){
				CraftTweakerAPI.logError("ERROR DFC recipe output item can not be an empty/air stack!");
				return;
			}
			if(this.spark < 1){
				CraftTweakerAPI.logError("ERROR DFC recipe spark must be >0 not "+this.spark+"!");
				return;
			}
			DFCRecipes.setRecipe(this.spark, this.input, this.output);
		}
		@Override
		public String describe(){
			return "Adding NTM dfc recipe ("+this.input+" + "+this.spark+" spark -> "+this.output+")";
		}
	}

	@ZenMethod
	public static void addRecipe(IItemStack input, IItemStack output, long spark){
		NTMCraftTweaker.postInitActions.add(new ActionAddRecipe(input, output, spark));
	}



	public static class ActionRemoveRecipe implements IAction{
		private ItemStack input;

		public ActionRemoveRecipe(IItemStack input){
			this.input = CraftTweakerMC.getItemStack(input);
		}
		@Override
		public void apply(){
			if(this.input == null || this.input.isEmpty()){
				CraftTweakerAPI.logError("ERROR DFC input item can not be an empty/air stack!");
				return;
			}
			DFCRecipes.removeRecipe(this.input);
		}
		@Override
		public String describe(){
			return "Removing NTM dfc recipe for input "+this.input;
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
