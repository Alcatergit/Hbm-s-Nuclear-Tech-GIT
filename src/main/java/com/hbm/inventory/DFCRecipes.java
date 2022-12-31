package com.hbm.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.lib.Library;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

import static com.hbm.inventory.OreDictManager.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class DFCRecipes {

	public static HashMap<ComparableStack, Object[]> dfcRecipes = new HashMap<ComparableStack, Object[]>();
	public static List<DFCRecipe> jeiDFCRecipes = null;
	
	public static void register() {
		DFCRecipes.setRecipe(10000000, ModItems.billet_polonium, new ItemStack(ModItems.billet_yharonite));
		DFCRecipes.setRecipe(100000000, ModItems.meteorite_sword_warped, new ItemStack(ModItems.meteorite_sword_demonic));
		DFCRecipes.setRecipe(20000000, ModItems.nugget_radspice, new ItemStack(ModItems.egg_balefire));
		
		DFCRecipes.setRecipe(9000000, CO.dust(), new ItemStack(ModItems.powder_co60));
		DFCRecipes.setRecipe(5900000, SR.dust(), new ItemStack(ModItems.powder_sr90));
		DFCRecipes.setRecipe(3500000, I.dust(), new ItemStack(ModItems.powder_i131));
		DFCRecipes.setRecipe(8000000, CS.dust(), new ItemStack(ModItems.powder_cs137));
		DFCRecipes.setRecipe(15000000, GOLD.dust(), new ItemStack(ModItems.powder_au198));
		DFCRecipes.setRecipe(12000000, AT.dust(), new ItemStack(ModItems.powder_at209));
		DFCRecipes.setRecipe(90000000, PB.dust(), new ItemStack(ModItems.powder_pb209));
		DFCRecipes.setRecipe(35000000, AC.dust(), new ItemStack(ModItems.powder_ac227));
		DFCRecipes.setRecipe(5000000, U.dust(), new ItemStack(ModItems.powder_ra226));

	}

	public static void setRecipe(int requiredFlux, ItemStack in, ItemStack out) {
		dfcRecipes.put(new ComparableStack(in), new Object[] {requiredFlux, out});
	}

	public static void setRecipe(int requiredFlux, Item in, ItemStack out) {
		dfcRecipes.put(new ComparableStack(in), new Object[] {requiredFlux, out});
	}
	
	public static void setRecipe(int requiredFlux, Block in, ItemStack out) {
		dfcRecipes.put(new ComparableStack(in), new Object[] {requiredFlux, out});
	}

	public static void setRecipe(int requiredFlux, String in, ItemStack out) {
		dfcRecipes.put(new ComparableStack(OreDictionary.getOres(in).get(0)), new Object[] {requiredFlux, out});
	}

	public static int getRequiredFlux(ItemStack stack) {
		
		if(stack == null || stack.isEmpty())
			return -1;
		
		ComparableStack comp = new ComparableStack(stack).makeSingular();
		if(dfcRecipes.containsKey(comp)){
			return (int)dfcRecipes.get(comp)[0];
		}

		String[] dictKeys = comp.getDictKeys();
		
		for(String key : dictKeys) {
			if(dfcRecipes.containsKey(key)){
				return (int)dfcRecipes.get(key)[1];
			}
		}
		return -1;
	}

	public static ItemStack getOutput(ItemStack stack) {
		
		if(stack == null || stack.getItem() == null)
			return null;

		ComparableStack comp = new ComparableStack(stack).makeSingular();
		if(dfcRecipes.containsKey(comp)){
			return (ItemStack)dfcRecipes.get(comp)[1];
		}
		
		String[] dictKeys = comp.getDictKeys();
		
		for(String key : dictKeys) {
			
			if(dfcRecipes.containsKey(key)){
				return (ItemStack)dfcRecipes.get(key)[1];
			}
		}
		return null;
	}

	public static List<DFCRecipe> getDFCRecipes() {
		if(jeiDFCRecipes == null){
			jeiDFCRecipes = new ArrayList<DFCRecipe>();
			for(Entry<ComparableStack, Object[]> e : dfcRecipes.entrySet()){
				jeiDFCRecipes.add(new DFCRecipe(e.getKey().toStack(), (int)e.getValue()[0], (ItemStack)e.getValue()[1]));
			}
		}
		return jeiDFCRecipes;
	}
	
	public static class DFCRecipe implements IRecipeWrapper {
		
		private final ItemStack input;
		private final int requiredFlux;
		private final ItemStack output;
		
		public DFCRecipe(ItemStack input, int requiredFlux, ItemStack output) {
			this.input = input;
			this.requiredFlux = requiredFlux;
			this.output = output;
		}
		
		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInput(VanillaTypes.ITEM, input);
			ingredients.setOutput(VanillaTypes.ITEM, output);
		}

		@Override
		public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
			FontRenderer fontRenderer = minecraft.fontRenderer;
	    	
	    	fontRenderer.drawString("Spark", 8, 8, 4210752);
	    	String number = Library.getShortNumber(requiredFlux);
	    	fontRenderer.drawString(number, 80-fontRenderer.getStringWidth(number), 8, 0xa82a0e);
	    	GlStateManager.color(1, 1, 1, 1);
		}
	}
}