package com.hbm.inventory;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems.RetroRods;
import com.hbm.items.ModItems.ToolSets;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BreederRecipes {

	private static HashMap<ComparableStack, BreederRecipe> recipes = new HashMap<>();
	private static HashMap<ComparableStack, int[]> fuels = new HashMap<>();
	//for the int array: [0] => level (1-4) [1] => amount of operations
	
	public static void registerRecipes() {

		//lithium and impure rods
		addRecipe(new ComparableStack(RetroRods.rod_lithium), RetroRods.rod_tritium, 1);
		addRecipe(new ComparableStack(RetroRods.rod_dual_lithium), RetroRods.rod_dual_tritium, 1);
		addRecipe(new ComparableStack(RetroRods.rod_quad_lithium), RetroRods.rod_quad_tritium, 1);
		addRecipe(new ComparableStack(RetroRods.rod_uranium), RetroRods.rod_plutonium, 4);
		addRecipe(new ComparableStack(RetroRods.rod_dual_uranium), RetroRods.rod_dual_plutonium, 4);
		addRecipe(new ComparableStack(RetroRods.rod_quad_uranium), RetroRods.rod_quad_plutonium, 4);
		addRecipe(new ComparableStack(RetroRods.rod_plutonium), RetroRods.rod_waste, 4);
		addRecipe(new ComparableStack(RetroRods.rod_dual_plutonium), RetroRods.rod_dual_waste, 4);
		addRecipe(new ComparableStack(RetroRods.rod_quad_plutonium), RetroRods.rod_quad_waste, 4);
		
		//isotopes
		addRecipe(new ComparableStack(RetroRods.rod_th232), RetroRods.rod_thorium_fuel, 2);
		addRecipe(new ComparableStack(RetroRods.rod_dual_th232), RetroRods.rod_dual_thorium_fuel, 2);
		addRecipe(new ComparableStack(RetroRods.rod_quad_th232), RetroRods.rod_quad_thorium_fuel, 2);
		addRecipe(new ComparableStack(RetroRods.rod_u233), RetroRods.rod_u235, 2);
		addRecipe(new ComparableStack(RetroRods.rod_dual_u233), RetroRods.rod_dual_u235, 2);
		addRecipe(new ComparableStack(RetroRods.rod_quad_u233), RetroRods.rod_quad_u235, 2);
		addRecipe(new ComparableStack(RetroRods.rod_u235), RetroRods.rod_neptunium, 3);
		addRecipe(new ComparableStack(RetroRods.rod_dual_u235), RetroRods.rod_dual_neptunium, 3);
		addRecipe(new ComparableStack(RetroRods.rod_quad_u235), RetroRods.rod_quad_neptunium, 3);
		addRecipe(new ComparableStack(RetroRods.rod_u238), RetroRods.rod_pu239, 3);
		addRecipe(new ComparableStack(RetroRods.rod_dual_u238), RetroRods.rod_dual_pu239, 3);
		addRecipe(new ComparableStack(RetroRods.rod_quad_u238), RetroRods.rod_quad_pu239, 3);
		addRecipe(new ComparableStack(RetroRods.rod_neptunium), RetroRods.rod_pu238, 3);
		addRecipe(new ComparableStack(RetroRods.rod_dual_neptunium), RetroRods.rod_dual_pu238, 3);
		addRecipe(new ComparableStack(RetroRods.rod_quad_neptunium), RetroRods.rod_quad_pu238, 3);
		addRecipe(new ComparableStack(RetroRods.rod_pu238), RetroRods.rod_pu239, 2);
		addRecipe(new ComparableStack(RetroRods.rod_dual_pu238), RetroRods.rod_dual_pu239, 2);
		addRecipe(new ComparableStack(RetroRods.rod_quad_pu238), RetroRods.rod_quad_pu239, 2);
		addRecipe(new ComparableStack(RetroRods.rod_pu239), RetroRods.rod_pu240, 2);
		addRecipe(new ComparableStack(RetroRods.rod_dual_pu239), RetroRods.rod_dual_pu240, 2);
		addRecipe(new ComparableStack(RetroRods.rod_quad_pu239), RetroRods.rod_quad_pu240, 2);
		addRecipe(new ComparableStack(RetroRods.rod_pu240), RetroRods.rod_waste, 3);
		addRecipe(new ComparableStack(RetroRods.rod_dual_pu240), RetroRods.rod_dual_waste, 3);
		addRecipe(new ComparableStack(RetroRods.rod_quad_pu240), RetroRods.rod_quad_waste, 3);

		//NEW
		addRecipe(new ComparableStack(RetroRods.rod_cobalt), RetroRods.rod_co60, 2);
		addRecipe(new ComparableStack(RetroRods.rod_dual_cobalt), RetroRods.rod_dual_co60, 2);
		addRecipe(new ComparableStack(RetroRods.rod_quad_cobalt), RetroRods.rod_quad_co60, 2);
		addRecipe(new ComparableStack(RetroRods.rod_ra226), RetroRods.rod_ac227, 4);
		addRecipe(new ComparableStack(RetroRods.rod_dual_ra226), RetroRods.rod_dual_ac227, 4);
		addRecipe(new ComparableStack(RetroRods.rod_quad_ra226), RetroRods.rod_quad_ac227, 4);

		//advanced
		addRecipe(new ComparableStack(RetroRods.rod_schrabidium), RetroRods.rod_solinium, 3);
		addRecipe(new ComparableStack(RetroRods.rod_dual_schrabidium), RetroRods.rod_dual_solinium, 3);
		addRecipe(new ComparableStack(RetroRods.rod_quad_schrabidium), RetroRods.rod_quad_solinium, 3);
		addRecipe(new ComparableStack(RetroRods.rod_balefire), RetroRods.rod_balefire_blazing, 4);
		addRecipe(new ComparableStack(RetroRods.rod_dual_balefire), RetroRods.rod_dual_balefire_blazing, 4);
		addRecipe(new ComparableStack(RetroRods.rod_quad_balefire), RetroRods.rod_quad_balefire_blazing, 4);

		//rocks
		addRecipe(new ComparableStack(Blocks.STONE), new ItemStack(ModBlocks.sellafield_slaked), 1);
		addRecipe(new ComparableStack(ModBlocks.sellafield_slaked), new ItemStack(ModBlocks.sellafield_0), 2);
		addRecipe(new ComparableStack(ModBlocks.sellafield_0), new ItemStack(ModBlocks.sellafield_1), 2);
		addRecipe(new ComparableStack(ModBlocks.sellafield_1), new ItemStack(ModBlocks.sellafield_2), 3);
		addRecipe(new ComparableStack(ModBlocks.sellafield_2), new ItemStack(ModBlocks.sellafield_3), 3);
		
		addRecipe(new ComparableStack(ToolSets.meteorite_sword_etched), new ItemStack(ToolSets.meteorite_sword_bred), 4);
	}
	
	public static void registerFuels() {
		addFuel(new ComparableStack(RetroRods.rod_u233), 2, 2);
		addFuel(new ComparableStack(RetroRods.rod_dual_u233), 2, 4);
		addFuel(new ComparableStack(RetroRods.rod_quad_u233), 2, 8);
		
		addFuel(new ComparableStack(RetroRods.rod_u235), 2, 3);
		addFuel(new ComparableStack(RetroRods.rod_dual_u235), 2, 6);
		addFuel(new ComparableStack(RetroRods.rod_quad_u235), 2, 12);
		
		addFuel(new ComparableStack(RetroRods.rod_u238), 1, 1);
		addFuel(new ComparableStack(RetroRods.rod_dual_u238), 1, 2);
		addFuel(new ComparableStack(RetroRods.rod_quad_u238), 1, 4);
		
		addFuel(new ComparableStack(RetroRods.rod_neptunium), 2, 3);
		addFuel(new ComparableStack(RetroRods.rod_dual_neptunium), 2, 6);
		addFuel(new ComparableStack(RetroRods.rod_quad_neptunium), 2, 12);
		
		addFuel(new ComparableStack(RetroRods.rod_pu238), 1, 2);
		addFuel(new ComparableStack(RetroRods.rod_dual_pu238), 1, 4);
		addFuel(new ComparableStack(RetroRods.rod_quad_pu238), 1, 8);
		
		addFuel(new ComparableStack(RetroRods.rod_pu239), 3, 5);
		addFuel(new ComparableStack(RetroRods.rod_dual_pu239), 3, 10);
		addFuel(new ComparableStack(RetroRods.rod_quad_pu239), 3, 20);
		
		addFuel(new ComparableStack(RetroRods.rod_pu240), 1, 2);
		addFuel(new ComparableStack(RetroRods.rod_dual_pu240), 1, 4);
		addFuel(new ComparableStack(RetroRods.rod_quad_pu240), 1, 8);
		
		addFuel(new ComparableStack(RetroRods.rod_schrabidium), 3, 10);
		addFuel(new ComparableStack(RetroRods.rod_dual_schrabidium), 3, 20);
		addFuel(new ComparableStack(RetroRods.rod_quad_schrabidium), 3, 40);
		
		addFuel(new ComparableStack(RetroRods.rod_solinium), 3, 15);
		addFuel(new ComparableStack(RetroRods.rod_dual_solinium), 3, 30);
		addFuel(new ComparableStack(RetroRods.rod_quad_solinium), 3, 60);
		
		addFuel(new ComparableStack(RetroRods.rod_polonium), 4, 2);
		addFuel(new ComparableStack(RetroRods.rod_dual_polonium), 4, 4);
		addFuel(new ComparableStack(RetroRods.rod_quad_polonium), 4, 8);
		
		addFuel(new ComparableStack(RetroRods.rod_tritium), 1, 1);
		addFuel(new ComparableStack(RetroRods.rod_dual_tritium), 1, 2);
		addFuel(new ComparableStack(RetroRods.rod_quad_tritium), 1, 4);
		
		addFuel(new ComparableStack(RetroRods.rod_balefire), 2, 150);
		addFuel(new ComparableStack(RetroRods.rod_dual_balefire), 2, 300);
		addFuel(new ComparableStack(RetroRods.rod_quad_balefire), 2, 600);
		
		addFuel(new ComparableStack(RetroRods.rod_balefire_blazing), 4, 75);
		addFuel(new ComparableStack(RetroRods.rod_dual_balefire_blazing), 4, 150);
		addFuel(new ComparableStack(RetroRods.rod_quad_balefire_blazing), 4, 300);
	}

	public static void addRecipe(ComparableStack input, Item output, int heatLvl){
		addRecipe(input, new ItemStack(output), heatLvl);
	}
	public static void addRecipe(ComparableStack input, ItemStack output, int heatLvl){
		recipes.put(input, new BreederRecipe(output, heatLvl));
	}

	public static void removeRecipe(ComparableStack input){
		recipes.remove(input);
	}

	public static void addFuel(ComparableStack input, int heatLvl, int usesInNuclearFurnace){
		fuels.put(input, new int[] {heatLvl, usesInNuclearFurnace});
	}

	public static void removeFuel(ComparableStack input){
		fuels.remove(input);
	}
	
	public static HashMap<ItemStack, BreederRecipe> getAllRecipes() {
		
		HashMap<ItemStack, BreederRecipe> map = new HashMap<>();
		
		for(Map.Entry<ComparableStack, BreederRecipe> recipe : recipes.entrySet()) {
			map.put(recipe.getKey().toStack(), recipe.getValue());
		}
		
		return map;
	}
	
	public static List<ItemStack> getAllFuelsFromHEAT(int heat) {
		
		List<ItemStack> list = new ArrayList<>();
		
		for(Map.Entry<ComparableStack, int[]> fuel : fuels.entrySet()) {
			
			if(fuel.getValue()[0] >= heat) {
				list.add(fuel.getKey().toStack());
			}
		}
		
		return list;
	}
	
	public static BreederRecipe getOutput(ItemStack stack) {
		
		if(stack == null)
			return null;
		
		ComparableStack sta = new ComparableStack(stack.getItem(), 1, stack.getItemDamage());
		return BreederRecipes.recipes.get(sta);
	}
	
	/**
	 * Returns an integer array of the fuel value of a certain stack
	 * @param stack
	 * @return an integer array (possibly null) with two fields, the HEAT value and the amount of operations
	 */
	public static int[] getFuelValue(ItemStack stack) {
		
		if(stack == null)
			return null;
		
		ComparableStack sta = new ComparableStack(stack.getItem(), 1, stack.getItemDamage());
		int[] ret = BreederRecipes.fuels.get(sta);
		
		return ret;
	}
	
	public static String getHEATString(String string, int heat) {

		if(heat == 1)
			string =  TextFormatting.GREEN + string;
		if(heat == 2)
			string = TextFormatting.YELLOW + string;
		if(heat == 3)
			string = TextFormatting.GOLD + string;
		if(heat == 4)
			string = TextFormatting.RED + string;
		
		return string; //strings are reference types I GET IT
	}
	
	//nicer than opaque object arrays
	public static class BreederRecipe {
		
		public ItemStack output;
		public int heat;
		
		public BreederRecipe() { }
		
		public BreederRecipe(Item output, int heat) {
			this(new ItemStack(output), heat);
		}
		
		public BreederRecipe(ItemStack output, int heat) {
			this.output = output;
			this.heat = heat;
		}
	}
}
