package com.hbm.inventory;

import com.hbm.items.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.LinkedHashMap;

import static com.hbm.inventory.RecipesCommon.*;

public class WasteDrumRecipes {

	public static LinkedHashMap<ComparableStack, ItemStack> recipes = new LinkedHashMap<>();
	private static HashSet<ItemStack> outputs = new HashSet<>();
	
	public static void registerRecipes() {

		//input, output
		addRecipe(ModItems.waste_uranium_hot, new ItemStack(ModItems.waste_uranium_legacy, 1));
		addRecipe(ModItems.waste_thorium_hot, new ItemStack(ModItems.waste_thorium_legacy, 1));
		addRecipe(ModItems.waste_plutonium_hot, new ItemStack(ModItems.waste_plutonium_legacy, 1));
		addRecipe(ModItems.waste_mox_hot, new ItemStack(ModItems.waste_mox_legacy, 1));
		addRecipe(ModItems.waste_schrabidium_hot, new ItemStack(ModItems.waste_schrabidium_legacy, 1));
	}

	public static void addRecipe(ComparableStack input, ItemStack output){ //Dude what fucking moron made everything work on Item oppose to ItemStack
		recipes.put(input, output);
		outputs.add(output);
	}

	public static void addRecipe(Item input, ItemStack output){
		recipes.put(new ComparableStack(input,1), output);
		outputs.add(output);
	}
	
	public static ItemStack getOutput(Item item) {
		
		if(item == null)
			return null;
		
		return recipes.get(item);
	}

	public static ItemStack getOutput(ItemStack item) {

		if(item == null)
			return null;

		return recipes.get(item);
	}

	public static boolean isCold(ItemStack item){
		return outputs.contains(item);
	}
}
