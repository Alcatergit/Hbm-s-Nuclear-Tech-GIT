package com.hbm.inventory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.blocks.BlockEnums.EnumStoneType;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.material.NTMMaterial.SmeltingBehavior;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMold;
import com.hbm.items.machine.ItemMold.Mold;
import com.hbm.util.Compat;
import com.hbm.items.machine.ItemScraps;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CrucibleRecipes {

	public static HashMap<Integer, CrucibleRecipe> recipes = new HashMap<Integer, CrucibleRecipe>();
	
	/*
	 * IMPORTANT: crucibles do not have stack size checks for the recipe's result, meaning that they can overflow if the resulting stacks are
	 * bigger than the input stacks, so make sure that material doesn't "expand". very few things do that IRL when alloying anyway.
	 */
	
	public static void registerDefaults() {

		int n = MaterialShapes.NUGGET.q(1);
		int i = MaterialShapes.INGOT.q(1);

		recipes.put(0, new CrucibleRecipe(0, "crucible.hematite", 6, new ItemStack(ModBlocks.ore_hematite))
				.inputs(new MaterialStack(Mats.MAT_HEMATITE, i * 2), new MaterialStack(Mats.MAT_FLUX, n * 2))
				.outputs(new MaterialStack(Mats.MAT_IRON, i), new MaterialStack(Mats.MAT_SLAG, n * 3)));

		recipes.put(1, new CrucibleRecipe(1, "crucible.malachite", 6, new ItemStack(ModBlocks.ore_malachite))
				.inputs(new MaterialStack(Mats.MAT_MALACHITE, i * 2), new MaterialStack(Mats.MAT_FLUX, n * 2))
				.outputs(new MaterialStack(Mats.MAT_COPPER, i), new MaterialStack(Mats.MAT_SLAG, n * 3)));
		
		recipes.put(2, new CrucibleRecipe(2, "crucible.steel", 2, new ItemStack(ModItems.ingot_steel))
				.inputs(new MaterialStack(Mats.MAT_IRON, n), new MaterialStack(Mats.MAT_CARBON, n))
				.outputs(new MaterialStack(Mats.MAT_STEEL, n * 2)));
		
		// if(Compat.isModLoaded(Compat.MOD_GT6)) {
		// 	recipes.put(new CrucibleRecipe(9, "crucible.steelWrought", 2, new ItemStack(ModItems.ingot_steel))
		// 			.inputs(new MaterialStack(Mats.MAT_WROUGHTIRON, n * 2), new MaterialStack(Mats.MAT_CARBON, n))
		// 			.outputs(new MaterialStack(Mats.MAT_STEEL, n * 2)));
		// 	recipes.put(new CrucibleRecipe(10, "crucible.steelPig", 2, new ItemStack(ModItems.ingot_steel))
		// 			.inputs(new MaterialStack(Mats.MAT_PIGIRON, n * 2), new MaterialStack(Mats.MAT_CARBON, n))
		// 			.outputs(new MaterialStack(Mats.MAT_STEEL, n * 2)));
		// 	recipes.put(new CrucibleRecipe(11, "crucible.steelMeteoric", 2, new ItemStack(ModItems.ingot_steel))
		// 			.inputs(new MaterialStack(Mats.MAT_METEORICIRON, n * 2), new MaterialStack(Mats.MAT_CARBON, n))
		// 			.outputs(new MaterialStack(Mats.MAT_STEEL, n * 2)));
		// }
		
		recipes.put(3, new CrucibleRecipe(3, "crucible.redcopper", 2, new ItemStack(ModItems.ingot_red_copper))
				.inputs(new MaterialStack(Mats.MAT_COPPER, n), new MaterialStack(Mats.MAT_REDSTONE, n))
				.outputs(new MaterialStack(Mats.MAT_MINGRADE, n * 2)));
		
		recipes.put(4, new CrucibleRecipe(4, "crucible.aa", 2, new ItemStack(ModItems.ingot_advanced_alloy))
				.inputs(new MaterialStack(Mats.MAT_STEEL, n), new MaterialStack(Mats.MAT_MINGRADE, n))
				.outputs(new MaterialStack(Mats.MAT_ALLOY, n * 2)));
		
		recipes.put(5, new CrucibleRecipe(5, "crucible.hss", 9, new ItemStack(ModItems.ingot_dura_steel))
				.inputs(new MaterialStack(Mats.MAT_STEEL, n * 5), new MaterialStack(Mats.MAT_TUNGSTEN, n * 3), new MaterialStack(Mats.MAT_COBALT, n))
				.outputs(new MaterialStack(Mats.MAT_DURA, n * 9)));
		
		recipes.put(6, new CrucibleRecipe(6, "crucible.ferro", 3, new ItemStack(ModItems.ingot_ferrouranium))
				.inputs(new MaterialStack(Mats.MAT_STEEL, n * 2), new MaterialStack(Mats.MAT_U238, n))
				.outputs(new MaterialStack(Mats.MAT_FERRO, n * 3)));
		
		recipes.put(7, new CrucibleRecipe(7, "crucible.tcalloy", 9, new ItemStack(ModItems.ingot_tcalloy))
				.inputs(new MaterialStack(Mats.MAT_STEEL, n * 8), new MaterialStack(Mats.MAT_TECHNIETIUM, n))
				.outputs(new MaterialStack(Mats.MAT_TCALLOY, i)));
		
		recipes.put(8, new CrucibleRecipe(8, "crucible.cdalloy", 9, new ItemStack(ModItems.ingot_cdalloy))
				.inputs(new MaterialStack(Mats.MAT_STEEL, n * 8), new MaterialStack(Mats.MAT_CADMIUM, n))
				.outputs(new MaterialStack(Mats.MAT_CDALLOY, i)));

		recipes.put(9, new CrucibleRecipe(9, "crucible.star", 3, new ItemStack(ModItems.ingot_starmetal))
				.inputs(new MaterialStack(Mats.MAT_SATURN, n), new MaterialStack(Mats.MAT_METEOR, n))
				.outputs(new MaterialStack(Mats.MAT_STAR, n * 2)));

		recipes.put(10, new CrucibleRecipe(10, "crucible.magtung", 3, new ItemStack(ModItems.ingot_magnetized_tungsten))
				.inputs(new MaterialStack(Mats.MAT_TUNGSTEN, i), new MaterialStack(Mats.MAT_SCHRABIDIUM, n))
				.outputs(new MaterialStack(Mats.MAT_MAGTUNG, i)));
		
		recipes.put(11, new CrucibleRecipe(11, "crucible.cmb", 3, new ItemStack(ModItems.ingot_combine_steel))
		 		.inputs(new MaterialStack(Mats.MAT_MUD, i), new MaterialStack(Mats.MAT_MAGTUNG, n * 6), new MaterialStack(Mats.MAT_ALLOY, n * 3))
		 		.outputs(new MaterialStack(Mats.MAT_CMB, i)));

		recipes.put(12, new CrucibleRecipe(12, "crucible.bscco", 3, new ItemStack(ModItems.ingot_bscco))
				.inputs(new MaterialStack(Mats.MAT_BISMUTH, n * 2), new MaterialStack(Mats.MAT_STRONTIUM, n * 2), new MaterialStack(Mats.MAT_CALCIUM, n * 2), new MaterialStack(Mats.MAT_COPPER, n * 3))
				.outputs(new MaterialStack(Mats.MAT_BSCCO, i)));
	}

	public static String getName(ItemStack stack){
		CrucibleRecipe recipe = recipes.get(stack.getItemDamage());
		if(recipe != null) return recipe.getName();
		return "";
	}

	public static ItemStack getIcon(ItemStack stack){
		CrucibleRecipe recipe = recipes.get(stack.getItemDamage());
		if(recipe != null) return recipe.icon.copy();
		return null;
	}

	public static class CrucibleRecipe {
		public MaterialStack[] input;
		public MaterialStack[] output;
		private int id;
		private String name;
		public int frequency = 1;
		public ItemStack icon;
		
		public CrucibleRecipe(int id, String name, int frequency, ItemStack icon) {
			this.id = id;
			this.name = name;
			this.frequency = frequency;
			this.icon = icon;
		}
		
		public CrucibleRecipe inputs(MaterialStack... input) {
			this.input = input;
			return this;
		}
		
		public CrucibleRecipe outputs(MaterialStack... output) {
			this.output = output;
			return this;
		}
		
		public int getId() {
			return this.id;
		}
		
		public String getName() {
			return this.name;
		}
		
		public int getInputAmount() {
			
			int content = 0;
			
			for(MaterialStack stack : input) {
				content += stack.amount;
			}
			
			return content;
		}
	}
	
	/** Returns a map containing all recipes where an item becomes a liquid material in the crucible. */
//	public static HashMap<AStack, List<ItemStack>> getSmeltingRecipes() {
//		HashMap<AStack, List<ItemStack>> map = new HashMap();
//
//		for(NTMMaterial material : Mats.orderedList) {
//			int in = material.convIn;
//			int out = material.convOut;
//			NTMMaterial convert = material.smeltsInto;
//			for(MaterialShapes shape : MaterialShapes.allShapes) {
//				//TODO: buffer these
//
//				String name = shape.name() + material.names[0];
//				List<ItemStack> ores = OreDictionary.getOres(name);
//
//				if(!ores.isEmpty()) {
//					List<ItemStack> stacks = new ArrayList<ItemStack>();
//					stacks.add(ItemScraps.create(new MaterialStack(convert, (int) (shape.q(1) * out / in)), true));
//					map.put(new OreDictStack(name), stacks);
//				}
//			}
//		}
//
//		for(Entry<String, List<MaterialStack>> entry : Mats.materialOreEntries.entrySet()) {
//			List<ItemStack> stacks = new ArrayList<ItemStack>();
//			for(MaterialStack mat : entry.getValue()) {
//				stacks.add(ItemScraps.create(mat, true));
//			}
//			map.put(new OreDictStack(entry.getKey()), stacks);
//		}
//
//		for(Entry<ComparableStack, List<MaterialStack>> entry : Mats.materialEntries.entrySet()) {
//			List<ItemStack> stacks = new ArrayList<ItemStack>();
//			for(MaterialStack mat : entry.getValue()) {
//				stacks.add(ItemScraps.create(mat, true));
//			}
//			map.put(entry.getKey().copy(), stacks);
//		}
//
//		return map;
//	}

}
