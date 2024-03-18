package com.hbm.handler.crt;

import java.util.List;
import java.util.ArrayList;

import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.main.MainRegistry;
import com.hbm.inventory.RBMKOutgasserRecipes;

import crafttweaker.IAction;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;

import javax.annotation.Nullable;

public class NTMCraftTweaker {
	public static final List<IAction> postInitActions = new ArrayList<>();

	public static void applyPostInitActions(){
		try{
			postInitActions.forEach( CraftTweakerAPI::apply );
		} catch( final Throwable t ){
			MainRegistry.logger.info("CraftTweaker integration decativated");
		}
	}

	@Nullable
	public static AStack IIngredientToAStack(IIngredient ingredient){
		if(ingredient instanceof IItemStack){
			return new RecipesCommon.ComparableStack(CraftTweakerMC.getItemStack((IItemStack) ingredient));
		}
		if(ingredient instanceof IOreDictEntry){
			return new RecipesCommon.OreDictStack(((IOreDictEntry) ingredient).getName());
		}
		if(ingredient instanceof IngredientStack){
			IIngredient  ingredient2 = (IIngredient) ingredient.getInternal();
			if(ingredient2 instanceof IItemStack){
				return new RecipesCommon.ComparableStack(CraftTweakerMC.getItemStack((IItemStack) ingredient2));
			}
			if(ingredient2 instanceof IOreDictEntry){
				return new RecipesCommon.OreDictStack(((IOreDictEntry) ingredient2).getName(), ingredient.getAmount());
			}
		}
		CraftTweakerAPI.logError("ERROR: Unknown ingredient type: " + ingredient.getClass().getName());
		return null;
	}

	public static AStack[] IIngredientsToAStack(IIngredient[] ingredients){
		AStack[] result = new AStack[ingredients.length];
		for(int i = 0; i < ingredients.length; i++){
			result[i] = IIngredientToAStack(ingredients[i]);
		}
		return result;
	}
}