package com.hbm.handler.crt;

import com.hbm.inventory.WasteDrumRecipes;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.ntm.WasteDrum")
public class WasteDrum {
	
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
				CraftTweakerAPI.logError("ERROR Waste Drum recipe input item can not be an empty/air stack!");
				return;
			}
			if(this.output == null || this.output.isEmpty()){
				CraftTweakerAPI.logError("ERROR Waste Drum recipe output item can not be an empty/air stack!");
				return;
			}
			WasteDrumRecipes.addRecipe(this.input, this.output);
		}
		@Override
		public String describe(){
			return "Adding NTM waste drum recipe ("+this.input+" -> "+this.output+")";
		}
	}

	@ZenMethod
	public static void addRecipe(IItemStack input, IItemStack output){
		NTMCraftTweaker.postInitActions.add(new ActionAddRecipe(input, output));
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
