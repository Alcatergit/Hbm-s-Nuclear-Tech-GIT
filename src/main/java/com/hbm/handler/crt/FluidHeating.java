package com.hbm.handler.crt;

import crafttweaker.IAction;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import com.hbm.inventory.HeatRecipes;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

@ZenRegister
@ZenClass("mods.ntm.FluidHeating")
public class FluidHeating {
	
	private static class ActionAddBoilRecipe implements IAction{
		private String inputFluid;
		private int inputAmount;
		private String outputFluid;
		private int outputAmount;
		private int heatCapacity;
		public ActionAddBoilRecipe(String inputFluid, int inputAmount, String outputFluid, int outputAmount, int heatCapacity){
			this.inputFluid = inputFluid;
			this.inputAmount = inputAmount;
			this.outputFluid = outputFluid;
			this.outputAmount = outputAmount;
			this.heatCapacity = heatCapacity;
		}
		@Override
		public void apply(){
			if(this.inputFluid == null || this.inputFluid.trim().isEmpty()){
				CraftTweakerAPI.logError("ERROR Input Fluid can not be null/empty!");
				return;
			}
			if(!FluidRegistry.isFluidRegistered(this.inputFluid)){
				CraftTweakerAPI.logError("ERROR Input Fluid ("+this.inputFluid+") does not exist!");
				return;
			}
			if(this.inputAmount < 1){
				CraftTweakerAPI.logError("ERROR Input Fluid Amount can not be < 1!");
				return;
			}
			if(this.outputFluid == null || this.outputFluid.trim().isEmpty()){
				CraftTweakerAPI.logError("ERROR Output Fluid can not be null/empty!");
				return;
			}
			if(!FluidRegistry.isFluidRegistered(this.outputFluid)){
				CraftTweakerAPI.logError("ERROR Output Fluid ("+this.outputFluid+") does not exist!");
				return;
			}
			if(this.outputAmount < 1){
				CraftTweakerAPI.logError("ERROR Output Fluid Amount can not be < 1!");
				return;
			}
			if(this.heatCapacity < 1){
				CraftTweakerAPI.logError("ERROR Heat Capactiy can not be < 1!");
				return;
			}
			HeatRecipes.addBoilRecipe(this.inputFluid, this.inputAmount, this.outputFluid, this.outputAmount, this.heatCapacity);
		}
		@Override
		public String describe(){
			return "Adding NTM fluid heating recipe ("+this.inputAmount+"mb "+this.inputFluid+" + "+this.heatCapacity+"TU -> "+this.outputAmount+"mb "+this.outputFluid+")";
		}
	}

	@ZenMethod
	public static void addBoilRecipe(String inputFluid, int inputAmount, String outputFluid, int outputAmount, int heatCapacity){
		NTMCraftTweaker.postInitActions.add(new ActionAddBoilRecipe(inputFluid, inputAmount, outputFluid, outputAmount, heatCapacity));
	}

	//
	private static class ActionAddCoolRecipe implements IAction{
		private String inputFluid;
		private int inputAmount;
		private String outputFluid;
		private int outputAmount;
		private int heatCapacity;
		public ActionAddCoolRecipe(String inputFluid, int inputAmount, String outputFluid, int outputAmount, int heatCapacity){
			this.inputFluid = inputFluid;
			this.inputAmount = inputAmount;
			this.outputFluid = outputFluid;
			this.outputAmount = outputAmount;
			this.heatCapacity = heatCapacity;
		}
		@Override
		public void apply(){
			if(this.inputFluid == null || this.inputFluid.trim().isEmpty()){
				CraftTweakerAPI.logError("ERROR Input Fluid can not be null/empty!");
				return;
			}
			if(!FluidRegistry.isFluidRegistered(this.inputFluid)){
				CraftTweakerAPI.logError("ERROR Input Fluid ("+this.inputFluid+") does not exist!");
				return;
			}
			if(this.inputAmount < 1){
				CraftTweakerAPI.logError("ERROR Input Fluid Amount can not be < 1!");
				return;
			}
			if(this.outputFluid == null || this.outputFluid.trim().isEmpty()){
				CraftTweakerAPI.logError("ERROR Output Fluid can not be null/empty!");
				return;
			}
			if(!FluidRegistry.isFluidRegistered(this.outputFluid)){
				CraftTweakerAPI.logError("ERROR Output Fluid ("+this.outputFluid+") does not exist!");
				return;
			}
			if(this.outputAmount < 1){
				CraftTweakerAPI.logError("ERROR Output Fluid Amount can not be < 1!");
				return;
			}
			if(this.heatCapacity < 1){
				CraftTweakerAPI.logError("ERROR Heat Capactiy can not be < 1!");
				return;
			}
			HeatRecipes.addCoolRecipe(this.inputFluid, this.inputAmount, this.outputFluid, this.outputAmount, this.heatCapacity);
		}
		@Override
		public String describe(){
			return "Adding NTM fluid cooling recipe ("+this.inputAmount+"mb "+this.inputFluid+" -> "+this.outputAmount+"mb "+this.outputFluid+" + "+this.heatCapacity+"TU)";
		}
	}

	@ZenMethod
	public static void addCoolRecipe(String inputFluid, int inputAmount, String outputFluid, int outputAmount, int heatCapacity){
		NTMCraftTweaker.postInitActions.add(new ActionAddCoolRecipe(inputFluid, inputAmount, outputFluid, outputAmount, heatCapacity));
	}

	private static class ActionAddBoilAndCoolRecipe implements IAction{
		private String inputFluid;
		private int inputAmount;
		private String outputFluid;
		private int outputAmount;
		private int heatCapacity;
		public ActionAddBoilAndCoolRecipe(String inputFluid, int inputAmount, String outputFluid, int outputAmount, int heatCapacity){
			this.inputFluid = inputFluid;
			this.inputAmount = inputAmount;
			this.outputFluid = outputFluid;
			this.outputAmount = outputAmount;
			this.heatCapacity = heatCapacity;
		}
		@Override
		public void apply(){
			if(this.inputFluid == null || this.inputFluid.trim().isEmpty()){
				CraftTweakerAPI.logError("ERROR Input Fluid can not be null/empty!");
				return;
			}
			if(!FluidRegistry.isFluidRegistered(this.inputFluid)){
				CraftTweakerAPI.logError("ERROR Input Fluid ("+this.inputFluid+") does not exist!");
				return;
			}
			if(this.inputAmount < 1){
				CraftTweakerAPI.logError("ERROR Input Fluid Amount can not be < 1!");
				return;
			}
			if(this.outputFluid == null || this.outputFluid.trim().isEmpty()){
				CraftTweakerAPI.logError("ERROR Output Fluid can not be null/empty!");
				return;
			}
			if(!FluidRegistry.isFluidRegistered(this.outputFluid)){
				CraftTweakerAPI.logError("ERROR Output Fluid ("+this.outputFluid+") does not exist!");
				return;
			}
			if(this.outputAmount < 1){
				CraftTweakerAPI.logError("ERROR Output Fluid Amount can not be < 1!");
				return;
			}
			if(this.heatCapacity < 1){
				CraftTweakerAPI.logError("ERROR Heat Capactiy can not be < 1!");
				return;
			}
			HeatRecipes.addBoilAndCoolRecipe(this.inputFluid, this.inputAmount, this.outputFluid, this.outputAmount, this.heatCapacity);
		}
		@Override
		public String describe(){
			return "Adding NTM fluid heating recipe pair ("+this.inputAmount+"mb "+this.inputFluid+" + "+this.heatCapacity+"TU -> "+this.outputAmount+"mb "+this.outputFluid+")";
		}
	}

	@ZenMethod
	public static void addBoilAndCoolRecipe(String inputFluid, int inputAmount, String outputFluid, int outputAmount, int heatCapacity){
		NTMCraftTweaker.postInitActions.add(new ActionAddBoilAndCoolRecipe(inputFluid, inputAmount, outputFluid, outputAmount, heatCapacity));
	}

	private static class ActionRemoveBoilRecipe implements IAction{
		private String inputFluid;
		public ActionRemoveBoilRecipe(String inputFluid){
			this.inputFluid = inputFluid;
		}
		@Override
		public void apply(){
			if(this.inputFluid == null || this.inputFluid.trim().isEmpty()){
				CraftTweakerAPI.logError("ERROR Input Fluid can not be null/empty!");
				return;
			}
			if(!FluidRegistry.isFluidRegistered(this.inputFluid)){
				CraftTweakerAPI.logError("ERROR Input Fluid ("+this.inputFluid+") does not exist!");
				return;
			}
			HeatRecipes.removeBoilRecipe(this.inputFluid);
		}
		@Override
		public String describe(){
			return "Removing NTM fluid heating recipe for ("+this.inputFluid+")";
		}
	}

	@ZenMethod
	public static void removeBoilRecipe(String inputFluid){
		NTMCraftTweaker.postInitActions.add(new ActionRemoveBoilRecipe(inputFluid));
	}

	private static class ActionRemoveCoolRecipe implements IAction{
		private String inputFluid;
		public ActionRemoveCoolRecipe(String inputFluid){
			this.inputFluid = inputFluid;
		}
		@Override
		public void apply(){
			if(this.inputFluid == null || this.inputFluid.trim().isEmpty()){
				CraftTweakerAPI.logError("ERROR Input Fluid can not be null/empty!");
				return;
			}
			if(!FluidRegistry.isFluidRegistered(this.inputFluid)){
				CraftTweakerAPI.logError("ERROR Input Fluid ("+this.inputFluid+") does not exist!");
				return;
			}
			HeatRecipes.removeCoolRecipe(this.inputFluid);
		}
		@Override
		public String describe(){
			return "Removing NTM fluid cooling recipe for ("+this.inputFluid+")";
		}
	}

	@ZenMethod
	public static void removeCoolRecipe(String inputFluid){
		NTMCraftTweaker.postInitActions.add(new ActionRemoveCoolRecipe(inputFluid));
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
