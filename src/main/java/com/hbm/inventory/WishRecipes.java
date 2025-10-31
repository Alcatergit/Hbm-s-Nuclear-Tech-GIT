package com.hbm.inventory;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.NbtComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;
import com.hbm.items.ModItems.Materials.Ingots;
import com.hbm.items.ModItems.Materials.Nuggies;
import com.hbm.items.ModItems.Materials.Powders;
import com.hbm.items.ModItems.ToolSets;
import com.hbm.items.tool.ItemFluidCanister;
import com.hbm.util.Tuple.Pair;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static com.hbm.inventory.OreDictManager.*;
import static net.minecraft.item.ItemStack.areItemStacksEqual;

public class WishRecipes {

	public static HashMap<AStack, ItemStack> diRecipes = new HashMap<>();
	public static void registerRecipes(){
		addRecipe(new OreDictStack("sand"),new ItemStack(Nuggies.nugget_silicon, 1));
		addRecipe(new ComparableStack(Blocks.SAND),new ItemStack(Nuggies.nugget_silicon, 1));
	}


	public static void addRecipe(AStack input, ItemStack output){
		diRecipes.put(input,output);
	}

	public static void removeRecipe(AStack input){
		diRecipes.remove(input);
	}

	public static void removeRecipe(ItemStack output){
		diRecipes.values().removeIf(value -> areItemStacksEqual(value,output));;
	}
	public static ItemStack getFurnaceProcessingResult(ItemStack stack) {
		if (stack == null || stack.isEmpty())
			return null;
		ItemStack item = stack.copy();
		item.setCount(1);
		ItemStack outputItem;

		outputItem = diRecipes.get(new ComparableStack(stack).makeSingular());
		if(outputItem != null)
			return outputItem;
		return null;
	}

	public static int toInt(Integer i){
		if(i == null)
			return 0;
		return i;
	}

}
