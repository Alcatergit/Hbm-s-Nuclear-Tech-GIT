package com.hbm.handler.crt;

import crafttweaker.IAction;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import com.hbm.inventory.ChemplantRecipes;
import com.hbm.inventory.RecipesCommon.ComparableStack;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidRegistry;

@ZenRegister
@ZenClass("mods.ntm.Chemplant")
public class Chemplant {
	
	private static class ActionAddChemplantRecipe implements IAction{
		private int index;
		private String name;
		private ItemStack[] inputItems;
		private String[] inputFluids;
		private Integer[] inputAmounts; 
		private String[] outputFluids; 
		private Integer[] outputAmounts; 
		private ItemStack[] outputItems;
		private int duration;
		public ActionAddChemplantRecipe(
			int index,
			String name,
			IItemStack[] inputItems, 
			String[] inputFluids, 
			Integer[] inputAmounts, 
			IItemStack[] outputItems, 
			String[] outputFluids, 
			Integer[] outputAmounts, 
			int duration){

			this.index = index;
			this.name = name;

			if(inputItems != null){
				this.inputItems = new ItemStack[Math.min(4, inputItems.length)];
				for(int i = 0; i < Math.min(4, inputItems.length); i++)
					this.inputItems[i] = CraftTweakerMC.getItemStack(inputItems[i]);
			} else {
				this.inputItems = null;
			}

			this.inputFluids = inputFluids;
			this.inputAmounts = inputAmounts;

			this.outputFluids = outputFluids;
			this.outputAmounts = outputAmounts;

			if(outputItems != null){
				this.outputItems = new ItemStack[Math.min(4, outputItems.length)];
				for(int i = 0; i < Math.min(4, outputItems.length); i++)
					this.outputItems[i] = CraftTweakerMC.getItemStack(outputItems[i]);
			} else {
				this.outputItems = null;
			}

			this.duration = duration;
		}

		private boolean checkFluidArray(String[] fluids, Integer[] amounts){
			if(fluids == null || amounts == null){
				CraftTweakerAPI.logInfo("ERROR Fluid arrays can not be null/empty!");
				return false;
			}
			if(fluids.length != amounts.length){
				CraftTweakerAPI.logInfo("ERROR Fluid arrays must have the same length!");
				return false;
			}
			if(fluids.length == 0 || fluids.length > 2){
				CraftTweakerAPI.logInfo("ERROR Fluid arrays must have a length of 1-2!");
				return false;
			}
			for(int i = 0; i < fluids.length; i++){
				if(!checkFluid(fluids[i], amounts[i])) return false;
			}
			return true;
		}

		private boolean checkFluid(String fluid, Integer amount){
			if(fluid == null || fluid.trim().isEmpty()){
				CraftTweakerAPI.logInfo("ERROR Fluid can not be null/empty!");
				return false;
			}
			if(!FluidRegistry.isFluidRegistered(fluid)){
				CraftTweakerAPI.logInfo("ERROR Fluid ("+fluid+") does not exist!");
				return false;
			}
			if(amount < 1){
				CraftTweakerAPI.logInfo("ERROR Fluid Amount can not be < 1!");
				return false;
			}
			if(amount > 32000){
				CraftTweakerAPI.logInfo("ERROR Fluid Amount can not be > 32000!");
				return false;
			}
			return true;
		}

		@Override
		public void apply(){
			if(this.name == null || this.name.trim().isEmpty()){
				CraftTweakerAPI.logInfo("ERROR Chemplant Recipe name can not be null/empty!");
				return;
			}

			if(inputItems == null && inputFluids == null){
				CraftTweakerAPI.logInfo("ERROR Chemplant Recipe must have inputs");
				return;
			}

			if(outputItems == null && outputFluids == null){
				CraftTweakerAPI.logInfo("ERROR Chemplant Recipe must have outputs");
				return;
			}

			if(inputFluids == null && outputFluids == null){
				CraftTweakerAPI.logInfo("ERROR Chemplant Recipe must use fluids");
				return;
			}

			if(inputFluids != null && inputAmounts != null && !checkFluidArray(inputFluids, inputAmounts)){
				CraftTweakerAPI.logInfo("ERROR Chemplant Recipe must have correct input fluids");
				return;
			}

			if(outputFluids != null && outputAmounts != null && !checkFluidArray(outputFluids, outputAmounts)){
				CraftTweakerAPI.logInfo("ERROR Chemplant Recipe must have correct output fluids");
				return;
			}

			if(this.duration < 1){
				CraftTweakerAPI.logInfo("ERROR Chemplant recipe duraction must be >=1 not "+this.duration+"!");
				return;
			}

			ChemplantRecipes.makeRecipe(this.index, 
				this.name, 
				convertToCom(this.inputItems), 
				convertToFlu(this.inputFluids, this.inputAmounts), 
				convertToCom(this.outputItems), 
				convertToFlu(this.outputFluids, this.outputAmounts), 
				this.duration
			);
		}

		public FluidStack[] convertToFlu(String[] fluids, Integer[] amounts){
			if(fluids == null || fluids.length == 0 || amounts == null || amounts.length == 0 || fluids.length != amounts.length) return null;
			
			FluidStack[] flu = new FluidStack[Math.min(2, fluids.length)];
			for(int i = 0; i < Math.min(2, fluids.length); i++)
				flu[i] = new FluidStack(FluidRegistry.getFluid(fluids[i]), amounts[i]);
			return flu;
		}

		public ComparableStack[] convertToCom(ItemStack[] inputs){
			if(inputs == null || inputs.length == 0) return null;

			ComparableStack[] compInputs = new ComparableStack[Math.min(4, inputs.length)];
			for(int i = 0; i < Math.min(4, inputs.length); i++)
				compInputs[i] = new ComparableStack(inputs[i]);
			return compInputs;
		}

		@Override
		public String describe(){
			return "Adding NTM chemplant recipe ("+this.index+" "+this.name+" "+convertToCom(this.inputItems)+" "+ 
				convertToFlu(this.inputFluids, this.inputAmounts)+" "+ 
				convertToCom(this.outputItems)+" "+ 
				convertToFlu(this.inputFluids, this.inputAmounts)+")";
		}
	}

	@ZenMethod
	public static void addChemplantRecipe(int index, String name, IItemStack[] inputItems, String[] inputFluids, Integer[] inputAmounts, IItemStack[] outputItems, String[] outputFluids, Integer[] outputAmounts, int duration){
		CraftTweakerAPI.apply(new ActionAddChemplantRecipe(index, name, inputItems, inputFluids, inputAmounts, outputItems, outputFluids, outputAmounts, duration));
	}

	//
	private static class ActionRemoveChemplantRecipe implements IAction{
		private int index;
		public ActionRemoveChemplantRecipe(int index){
			this.index = index;
		}

		@Override
		public void apply(){
			ChemplantRecipes.removeRecipe(this.index);
		}

		@Override
		public String describe(){
			return "Removing NTM Chemplant recipe ("+this.index+")";
		}
	}

	@ZenMethod
	public static void removeChemplantRecipe(int index){
		NTMCraftTweaker.postInitActions.add(new ActionRemoveChemplantRecipe(index));
	}
}
