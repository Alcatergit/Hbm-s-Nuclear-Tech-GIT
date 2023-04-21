package com.hbm.handler.crt;

import java.util.List;
import java.util.ArrayList;

import com.hbm.inventory.RBMKOutgasserRecipes;
import crafttweaker.IAction;
import crafttweaker.CraftTweakerAPI;

public class NTMCraftTweaker {
	public static final List<IAction> postInitActions = new ArrayList<>();

	public static void applyPostInitActions(){
		postInitActions.forEach( CraftTweakerAPI::apply );
	}
}
//NTMCraftTweaker.postInitActions.add(IAction);