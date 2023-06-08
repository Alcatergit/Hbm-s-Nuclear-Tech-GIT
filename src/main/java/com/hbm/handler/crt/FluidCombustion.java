package com.hbm.handler.crt;

import crafttweaker.IAction;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import com.hbm.inventory.FluidCombustionRecipes;

import net.minecraftforge.fluids.FluidRegistry;

@ZenRegister
@ZenClass("mods.ntm.FluidCombustion")
public class FluidCombustion {
	
	private static class ActionAddBurnableFluid implements IAction {
		private String inputFluid;
		private int heatPerMiliBucket;
		public ActionAddBurnableFluid(String inputFluid, int heatPerMiliBucket){
			this.inputFluid = inputFluid;
			this.heatPerMiliBucket = heatPerMiliBucket;
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
			if(this.heatPerMiliBucket < 1){
				CraftTweakerAPI.logError("ERROR Heat per mB can not be < 1!");
				return;
			}
			if(this.heatPerMiliBucket > 100_000){
				CraftTweakerAPI.logError("ERROR Heat per mB can not be > 100,000!");
				return;
			}
			FluidCombustionRecipes.addBurnableFluid(this.inputFluid, this.heatPerMiliBucket);
		}
		@Override
		public String describe(){
			return "Adding NTM fluid combustion recipe (" + this.inputFluid + " -> " + this.heatPerMiliBucket + "TU/mB)";
		}
	}

	@ZenMethod
	public static void addBurnableFluid(String inputFluid, int heatPerMiliBucket) {
		NTMCraftTweaker.postInitActions.add(new ActionAddBurnableFluid(inputFluid, heatPerMiliBucket));
	}

	private static class ActionRemoveBurnableFluid implements IAction{
		private String inputFluid;
		public ActionRemoveBurnableFluid(String inputFluid){
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
			FluidCombustionRecipes.removeBurnableFluid(this.inputFluid);
		}
		@Override
		public String describe(){
			return "Removing NTM fluid combustion recipe for ("+this.inputFluid+")";
		}
	}

	@ZenMethod
	public static void removeBurnableFluid(String inputFluid) {
		NTMCraftTweaker.postInitActions.add(new ActionRemoveBurnableFluid(inputFluid));
	}
}
