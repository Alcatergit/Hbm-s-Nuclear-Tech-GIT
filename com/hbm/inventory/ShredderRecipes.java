package com.hbm.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ShredderRecipes {

	public static HashMap<ComparableStack, ItemStack> shredderRecipes = new HashMap<ComparableStack, ItemStack>();
	public static List<ShredderRecipe> jeiShredderRecipes = null;
	
	public static void registerShredder() {
		
		String[] names = OreDictionary.getOreNames();
		
		for(int i = 0; i < names.length; i++) {
			
			String name = names[i];
			
			//if the dict contains invalid names, skip
			if(name == null || name.isEmpty())
				continue;
			
			List<ItemStack> matches = OreDictionary.getOres(name);
			
			//if the name isn't assigned to an ore, also skip
			if(matches == null || matches.isEmpty())
				continue;

			if(name.length() > 5 && name.substring(0, 5).equals("ingot")) {
				ItemStack dust = getDustByName(name.substring(5));
				
				if(dust != null && dust.getItem() != ModItems.scrap) {

					for(ItemStack stack : matches) {
						shredderRecipes.put(new ComparableStack(stack), dust);
					}
				}
			} else if(name.length() > 3 && name.substring(0, 3).equals("ore")) {
				ItemStack dust = getDustByName(name.substring(3));
				
				if(dust != null && dust.getItem() != ModItems.scrap) {
					
					dust.setCount(2);;

					for(ItemStack stack : matches) {
						shredderRecipes.put(new ComparableStack(stack), dust);
					}
				}
			} else if(name.length() > 5 && name.substring(0, 5).equals("block")) {
				ItemStack dust = getDustByName(name.substring(5));
				
				if(dust != null && dust.getItem() != ModItems.scrap) {
					
					dust.setCount(9);;

					for(ItemStack stack : matches) {
						shredderRecipes.put(new ComparableStack(stack), dust);
					}
				}
			} else if(name.length() > 3 && name.substring(0, 3).equals("gem")) {
				ItemStack dust = getDustByName(name.substring(3));
				
				if(dust != null && dust.getItem() != ModItems.scrap) {

					for(ItemStack stack : matches) {
						shredderRecipes.put(new ComparableStack(stack), dust);
					}
				}
			} else if(name.length() > 3 && name.substring(0, 4).equals("dust")) {

				for(ItemStack stack : matches) {
					shredderRecipes.put(new ComparableStack(stack), new ItemStack(ModItems.dust));
				}
			}
		}
	}
	
	public static void registerOverrides() {

		ShredderRecipes.setRecipe(ModItems.scrap, new ItemStack(ModItems.dust));
		ShredderRecipes.setRecipe(ModItems.dust, new ItemStack(ModItems.dust));
		ShredderRecipes.setRecipe(Blocks.GLOWSTONE, new ItemStack(Items.GLOWSTONE_DUST, 4));
		ShredderRecipes.setRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0), new ItemStack(ModItems.powder_quartz, 4));
		ShredderRecipes.setRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 1), new ItemStack(ModItems.powder_quartz, 4));
		ShredderRecipes.setRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 2), new ItemStack(ModItems.powder_quartz, 4));
		ShredderRecipes.setRecipe(Blocks.QUARTZ_STAIRS, new ItemStack(ModItems.powder_quartz, 3));
		ShredderRecipes.setRecipe(new ItemStack(Blocks.STONE_SLAB, 1, 7), new ItemStack(ModItems.powder_quartz, 2));
		ShredderRecipes.setRecipe(Items.QUARTZ, new ItemStack(ModItems.powder_quartz));
		ShredderRecipes.setRecipe(Blocks.QUARTZ_ORE, new ItemStack(ModItems.powder_quartz, 2));
		ShredderRecipes.setRecipe(ModBlocks.ore_nether_fire, new ItemStack(ModItems.powder_fire, 6));
		ShredderRecipes.setRecipe(Blocks.PACKED_ICE, new ItemStack(ModItems.powder_ice, 1));
		ShredderRecipes.setRecipe(ModBlocks.brick_light, new ItemStack(Items.CLAY_BALL, 4));
		ShredderRecipes.setRecipe(ModBlocks.concrete, new ItemStack(Blocks.GRAVEL, 1));
		ShredderRecipes.setRecipe(ModBlocks.concrete_smooth, new ItemStack(Blocks.GRAVEL, 1));
		ShredderRecipes.setRecipe(ModBlocks.brick_concrete, new ItemStack(Blocks.GRAVEL, 1));
		ShredderRecipes.setRecipe(ModBlocks.brick_concrete_mossy, new ItemStack(Blocks.GRAVEL, 1));
		ShredderRecipes.setRecipe(ModBlocks.brick_concrete_cracked, new ItemStack(Blocks.GRAVEL, 1));
		ShredderRecipes.setRecipe(ModBlocks.brick_concrete_broken, new ItemStack(Blocks.GRAVEL, 1));
		ShredderRecipes.setRecipe(ModBlocks.brick_obsidian, new ItemStack(ModBlocks.gravel_obsidian, 1));
		ShredderRecipes.setRecipe(Blocks.OBSIDIAN, new ItemStack(ModBlocks.gravel_obsidian, 1));
		ShredderRecipes.setRecipe(Blocks.STONE, new ItemStack(Blocks.GRAVEL, 1));
		ShredderRecipes.setRecipe(Blocks.COBBLESTONE, new ItemStack(Blocks.GRAVEL, 1));
		ShredderRecipes.setRecipe(Blocks.STONEBRICK, new ItemStack(Blocks.GRAVEL, 1));
		ShredderRecipes.setRecipe(Blocks.GRAVEL, new ItemStack(Blocks.SAND, 1));
		ShredderRecipes.setRecipe(Blocks.SAND, new ItemStack(ModItems.dust, 2));
		ShredderRecipes.setRecipe(Blocks.BRICK_BLOCK, new ItemStack(Items.CLAY_BALL, 4));
		ShredderRecipes.setRecipe(Blocks.BRICK_STAIRS, new ItemStack(Items.CLAY_BALL, 3));
		ShredderRecipes.setRecipe(Items.FLOWER_POT, new ItemStack(Items.CLAY_BALL, 3));
		ShredderRecipes.setRecipe(Items.BRICK, new ItemStack(Items.CLAY_BALL, 1));
		ShredderRecipes.setRecipe(Blocks.SANDSTONE, new ItemStack(Blocks.SAND, 4));
		ShredderRecipes.setRecipe(Blocks.SANDSTONE_STAIRS, new ItemStack(Blocks.SAND, 6));
		ShredderRecipes.setRecipe(Blocks.CLAY, new ItemStack(Items.CLAY_BALL, 4));
		ShredderRecipes.setRecipe(Blocks.HARDENED_CLAY, new ItemStack(Items.CLAY_BALL, 4));
		ShredderRecipes.setRecipe(Blocks.TNT, new ItemStack(Items.GUNPOWDER, 5));
		ShredderRecipes.setRecipe(ModItems.powder_quartz, new ItemStack(ModItems.powder_lithium_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.powder_lapis, new ItemStack(ModItems.powder_cobalt_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_neodymium, new ItemStack(ModItems.powder_neodymium_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_cobalt, new ItemStack(ModItems.powder_cobalt_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_niobium, new ItemStack(ModItems.powder_niobium_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_cerium, new ItemStack(ModItems.powder_cerium_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_lanthanium, new ItemStack(ModItems.powder_lanthanium_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_actinium, new ItemStack(ModItems.powder_actinium_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_meteorite, new ItemStack(ModItems.powder_meteorite_tiny, 1));
		ShredderRecipes.setRecipe(ModBlocks.block_meteor, new ItemStack(ModItems.powder_meteorite, 10));
		ShredderRecipes.setRecipe(Items.ENCHANTED_BOOK, new ItemStack(ModItems.powder_magic, 1));
		ShredderRecipes.setRecipe(ModItems.arc_electrode_burnt, new ItemStack(ModItems.powder_coal, 1));
		ShredderRecipes.setRecipe(ModItems.arc_electrode_desh, new ItemStack(ModItems.powder_desh, 2));
		ShredderRecipes.setRecipe(ModBlocks.meteor_polished, new ItemStack(ModItems.powder_meteorite, 1));
		ShredderRecipes.setRecipe(ModBlocks.meteor_brick, new ItemStack(ModItems.powder_meteorite, 1));
		ShredderRecipes.setRecipe(ModBlocks.meteor_brick_mossy, new ItemStack(ModItems.powder_meteorite, 1));
		ShredderRecipes.setRecipe(ModBlocks.meteor_brick_cracked, new ItemStack(ModItems.powder_meteorite, 1));
		ShredderRecipes.setRecipe(ModBlocks.meteor_brick_chiseled, new ItemStack(ModItems.powder_meteorite, 1));
		ShredderRecipes.setRecipe(ModBlocks.meteor_pillar, new ItemStack(ModItems.powder_meteorite, 1));
		ShredderRecipes.setRecipe(ModBlocks.ore_rare, new ItemStack(ModItems.powder_desh_mix, 1));

		ShredderRecipes.setRecipe(ModItems.crystal_iron, new ItemStack(ModItems.powder_iron, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_gold, new ItemStack(ModItems.powder_iron, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_redstone, new ItemStack(Items.REDSTONE, 8));
		ShredderRecipes.setRecipe(ModItems.crystal_uranium, new ItemStack(ModItems.powder_uranium, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_plutonium, new ItemStack(ModItems.powder_plutonium, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_thorium, new ItemStack(ModItems.powder_thorium, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_titanium, new ItemStack(ModItems.powder_titanium, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_sulfur, new ItemStack(ModItems.sulfur, 8));
		ShredderRecipes.setRecipe(ModItems.crystal_niter, new ItemStack(ModItems.niter, 8));
		ShredderRecipes.setRecipe(ModItems.crystal_copper, new ItemStack(ModItems.powder_copper, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_tungsten, new ItemStack(ModItems.powder_tungsten, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_aluminium, new ItemStack(ModItems.powder_aluminium, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_fluorite, new ItemStack(ModItems.fluorite, 8));
		ShredderRecipes.setRecipe(ModItems.crystal_beryllium, new ItemStack(ModItems.powder_beryllium, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_lead, new ItemStack(ModItems.powder_lead, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_schrabidium, new ItemStack(ModItems.powder_schrabidium, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_rare, new ItemStack(ModItems.powder_desh_mix, 2));
		ShredderRecipes.setRecipe(ModItems.crystal_phosphorus, new ItemStack(ModItems.powder_fire, 8));
		ShredderRecipes.setRecipe(ModItems.crystal_trixite, new ItemStack(ModItems.powder_plutonium, 6));
		ShredderRecipes.setRecipe(ModItems.crystal_lithium, new ItemStack(ModItems.powder_lithium, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_starmetal, new ItemStack(ModItems.powder_dura_steel, 6));

		for(int i = 0; i < 16; i++) {
			ShredderRecipes.setRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, i), new ItemStack(Items.CLAY_BALL, 4));
			ShredderRecipes.setRecipe(new ItemStack(Blocks.WOOL, 1, i), new ItemStack(Items.STRING, 4));
		}
	}
	
	public static ItemStack getDustByName(String name) {
		
		List<ItemStack> matches = OreDictionary.getOres("dust" + name);
		
		if(matches != null && !matches.isEmpty())
			return matches.get(0).copy();
		
		return new ItemStack(ModItems.scrap);
	}
	
	public static void setRecipe(Item in, ItemStack out) {
		
		shredderRecipes.put(new ComparableStack(in), out);
	}
	
	public static void setRecipe(Block in, ItemStack out) {
		
		shredderRecipes.put(new ComparableStack(in), out);
	}
	
	public static void setRecipe(ItemStack in, ItemStack out) {
		
		shredderRecipes.put(new ComparableStack(in), out);
	}
	
	public static List<ShredderRecipe> getShredderRecipes() {
		
		if(jeiShredderRecipes == null){
			jeiShredderRecipes = new ArrayList<ShredderRecipe>();
			for(Entry<ComparableStack, ItemStack> e : shredderRecipes.entrySet()){
				jeiShredderRecipes.add(new ShredderRecipe(e.getKey().toStack(), e.getValue()));
			}
		}
		
		return jeiShredderRecipes;
	}
	
	public static ItemStack getShredderResult(ItemStack stack) {
		
		if(stack == null || stack.getItem() == null || stack.isEmpty())
			return new ItemStack(ModItems.scrap);
		
		ItemStack sta = shredderRecipes.get(new ComparableStack(stack).makeSingular());
		
		/*if(sta != null)
			System.out.println(stack.getDisplayName() + " resulted " + sta.getDisplayName());
		else
			System.out.println(stack.getDisplayName() + " resulted null");*/
		
		return sta == null ? new ItemStack(ModItems.scrap) : sta;
	}
	
	public static class ShredderRecipe implements IRecipeWrapper {
		
		private final ItemStack input;
		private final ItemStack output;
		
		public ShredderRecipe(ItemStack input, ItemStack output) {
			this.input = input;
			this.output = output; 
		}
		
		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInput(VanillaTypes.ITEM, input);
			ingredients.setOutput(VanillaTypes.ITEM, output);
		}
		
	}
}
