package com.hbm.handler.crt;

import crafttweaker.IAction;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import com.hbm.inventory.ShredderRecipes;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@ZenRegister
@ZenClass("mods.ntm.Shredder")
public class Shredder {
	
	private static class ActionAddRecipe implements IAction{
		private ItemStack input;
		private ItemStack output;
		public ActionAddRecipe(IItemStack input, IItemStack output){
			this.input = CraftTweakerMC.getItemStack(input);
			this.output = CraftTweakerMC.getItemStack(output);
		}
		@Override
		public void apply(){
			if(this.input == null || this.input.isEmpty()){
				CraftTweakerAPI.logError("ERROR Shredder recipe input item can not be an empty/air stack!");
				return;
			}
			if(this.output == null || this.output.isEmpty()){
				CraftTweakerAPI.logError("ERROR Shredder recipe output item can not be an empty/air stack!");
				return;
			}
			ShredderRecipes.setRecipe(this.input, this.output);
		}
		@Override
		public String describe(){
			return "Adding NTM shredder recipe ("+this.input+" -> "+this.output+")";
		}
	}

	@ZenMethod
	public static void addRecipe(IItemStack input, IItemStack output){
		NTMCraftTweaker.postInitActions.add(new ActionAddRecipe(input, output));
	}



	public static class ActionRemoveRecipe implements IAction{
		private ItemStack input;

		public ActionRemoveRecipe(IItemStack input){
			this.input = CraftTweakerMC.getItemStack(input);
		}
		@Override
		public void apply(){
			if(this.input == null || this.input.isEmpty()){
				CraftTweakerAPI.logError("ERROR Shredder input item can not be an empty/air stack!");
				return;
			}
			ShredderRecipes.removeRecipe(this.input);
		}
		@Override
		public String describe(){
			return "Removing NTM shredder recipe for input "+this.input;
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
