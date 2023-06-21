package com.hbm.handler.crt;

import crafttweaker.IAction;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import com.hbm.inventory.CentrifugeRecipes;
import com.hbm.inventory.RecipesCommon.ComparableStack;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@ZenRegister
@ZenClass("mods.ntm.Centrifuge")
public class Centrifuge {
	
	private static class ActionAddRecipe implements IAction{
		private ItemStack[] outputs;
		private ItemStack input;
		public ActionAddRecipe(IItemStack input, IItemStack[] outputs){
			this.outputs = new ItemStack[outputs.length];
			for(int i = 0; i < outputs.length; i++)
				this.outputs[i] = CraftTweakerMC.getItemStack(outputs[i]);
			this.input = CraftTweakerMC.getItemStack(input);
		}
		@Override
		public void apply(){
			if(this.outputs == null || this.outputs.length == 0){
				CraftTweakerAPI.logError("ERROR Centrifuge recipe output items can not be an empty array!");
				return;
			}
			if(this.outputs.length > 4){
				CraftTweakerAPI.logError("ERROR Centrifuge recipe output item count must be <=4 not "+this.outputs.length+"!");
				return;
			}
			for(ItemStack i: this.outputs){
				if(i == null || i.isEmpty()){
					CraftTweakerAPI.logError("ERROR Centrifuge recipe output items can not include an empty/air stack!");
					return;
				}
			}
			if(this.input == null || this.input.isEmpty()){
				CraftTweakerAPI.logError("ERROR Centrifuge recipe input item can not be an empty/air stack!");
				return;
			}
			CentrifugeRecipes.addRecipe(this.input, this.outputs);
		}
		@Override
		public String describe(){
			return "Adding NTM Centrifuge recipe ("+this.input+" -> "+this.outputs+")";
		}
	}

	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack[] inputs){
		NTMCraftTweaker.postInitActions.add(new ActionAddRecipe(output, inputs));
	}



	public static class ActionRemoveRecipe implements IAction{
		private ItemStack input;

		public ActionRemoveRecipe(IItemStack input){
			this.input = CraftTweakerMC.getItemStack(input);
		}
		@Override
		public void apply(){
			if(this.input == null || this.input.isEmpty()){
				CraftTweakerAPI.logError("ERROR Centrifuge input item can not be an empty/air stack!");
				return;
			}
			CentrifugeRecipes.removeRecipe(this.input);
		}
		@Override
		public String describe(){
			return "Removing NTM Centrifuge recipe for input "+this.input;
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
