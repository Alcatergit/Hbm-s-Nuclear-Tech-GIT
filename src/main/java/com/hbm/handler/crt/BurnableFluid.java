package com.hbm.handler.crt;

import crafttweaker.IAction;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import com.hbm.inventory.MachineRecipes;

import net.minecraftforge.fluids.FluidRegistry;

@ZenRegister
@ZenClass("mods.ntm.BurnableFluid")
public class BurnableFluid {
	
	private static class ActionAddBurnableFluid implements IAction {
		private String inputFluid;
		private int heatPerBucket;
		public ActionAddBurnableFluid(String inputFluid, int heatPerBucket){
			this.inputFluid = inputFluid;
			this.heatPerBucket = heatPerBucket;
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
			if(this.heatPerBucket < 1){
				CraftTweakerAPI.logError("ERROR Heat per bucket can not be < 1!");
				return;
			}
			MachineRecipes.addBurnableFluid(this.inputFluid, this.heatPerBucket);
		}
		@Override
		public String describe(){
			return "Adding NTM burnable fluid (" + this.inputFluid + " -> " + this.heatPerBucket + "TU/b)";
		}
	}

	@ZenMethod
	public static void addBurnableFluid(String inputFluid, int heatPerBucket) {
		NTMCraftTweaker.postInitActions.add(new ActionAddBurnableFluid(inputFluid, heatPerBucket));
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
			MachineRecipes.removeBurnableFluid(this.inputFluid);
		}
		@Override
		public String describe(){
			return "Removing NTM burnable fluid ("+this.inputFluid+")";
		}
	}

	@ZenMethod
	public static void removeBurnableFluid(String inputFluid) {
		NTMCraftTweaker.postInitActions.add(new ActionRemoveBurnableFluid(inputFluid));
	}
}
