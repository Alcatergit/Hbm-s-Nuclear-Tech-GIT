package com.hbm.handler.crt;

import com.hbm.inventory.RBMKOutgasserRecipes;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.ntm.IrradiationChannel")
public class IrradiationChannel {
	
	private static class ActionAddRecipe implements IAction{
		private ItemStack input;
		private ItemStack output;
		private int flux = 0;
		public ActionAddRecipe(IItemStack input, IItemStack output, int flux){
			this.input = CraftTweakerMC.getItemStack(input);
			this.output = CraftTweakerMC.getItemStack(output);
			this.flux = flux;
		}
		@Override
		public void apply(){
			if(this.input == null || this.input.isEmpty()){
				CraftTweakerAPI.logError("ERROR IrradiationChannel recipe input item can not be an empty/air stack!");
				return;
			}
			if(this.output == null || this.output.isEmpty()){
				CraftTweakerAPI.logError("ERROR IrradiationChannel recipe output item can not be an empty/air stack!");
				return;
			}
			if(this.flux < 1){
				CraftTweakerAPI.logError("ERROR IrradiationChannel recipe flux must be >0 not "+this.flux+"!");
				return;
			}
			RBMKOutgasserRecipes.addRecipe(this.flux, this.input, this.output);
		}
		@Override
		public String describe(){
			return "Adding NTM irradiation channel recipe ("+this.input+" + "+this.flux+" flux -> "+this.output+")";
		}
	}

	@ZenMethod
	public static void addRecipe(IItemStack input, IItemStack output, int flux){
		NTMCraftTweaker.postInitActions.add(new ActionAddRecipe(input, output, flux));
	}



	public static class ActionRemoveRecipe implements IAction{
		private ItemStack input;

		public ActionRemoveRecipe(IItemStack input){
			this.input = CraftTweakerMC.getItemStack(input);
		}
		@Override
		public void apply(){
			if(this.input == null || this.input.isEmpty()){
				CraftTweakerAPI.logError("ERROR IrradiationChannel input item can not be an empty/air stack!");
				return;
			}
			RBMKOutgasserRecipes.removeRecipe(this.input);
		}
		@Override
		public String describe(){
			return "Removing NTM irradiation channel recipe for input "+this.input;
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
