package com.hbm.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.blocks.ModBlocks;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;

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

public class RBMKOutgasserRecipes {

	public static HashMap<ComparableStack, Object[]> rbmkOutgasserRecipes = new HashMap<ComparableStack, Object[]>();
	public static List<RBMKOutgasserRecipe> jeiRBMKOutgasserRecipes = null;
	
	public static void registerOverrides() {
		RBMKOutgasserRecipes.setRecipe(9000, "blockLithium", ItemFluidIcon.getStackWithQuantity(ModForgeFluids.tritium, 8000));
		RBMKOutgasserRecipes.setRecipe(1500, "ingotLithium", ItemFluidIcon.getStackWithQuantity(ModForgeFluids.tritium, 800));
		RBMKOutgasserRecipes.setRecipe(1200, "dustLithium", ItemFluidIcon.getStackWithQuantity(ModForgeFluids.tritium, 800));
		RBMKOutgasserRecipes.setRecipe(240, "dustSmallLithium", ItemFluidIcon.getStackWithQuantity(ModForgeFluids.tritium, 120));
		RBMKOutgasserRecipes.setRecipe(160000, "ingotGold", new ItemStack(ModItems.ingot_au198));
		RBMKOutgasserRecipes.setRecipe(30000, "nuggetGold", new ItemStack(ModItems.nugget_au198));
		RBMKOutgasserRecipes.setRecipe(150000, "dustGold", new ItemStack(ModItems.powder_au198));
		RBMKOutgasserRecipes.setRecipe(600, Blocks.BROWN_MUSHROOM, new ItemStack(ModBlocks.mush));
		RBMKOutgasserRecipes.setRecipe(600, Blocks.RED_MUSHROOM, new ItemStack(ModBlocks.mush));
		RBMKOutgasserRecipes.setRecipe(1800, Items.MUSHROOM_STEW, new ItemStack(ModItems.glowing_stew));
		RBMKOutgasserRecipes.setRecipe(9000, "ingotTh232", new ItemStack(ModItems.ingot_u233));
		RBMKOutgasserRecipes.setRecipe(6000, "ingotU233", new ItemStack(ModItems.ingot_u235));
		RBMKOutgasserRecipes.setRecipe(10000, "ingotU235", new ItemStack(ModItems.ingot_neptunium));
		RBMKOutgasserRecipes.setRecipe(17000, "ingotNp237", new ItemStack(ModItems.ingot_pu238));
		RBMKOutgasserRecipes.setRecipe(12000, "ingotU238", new ItemStack(ModItems.ingot_pu239));
		RBMKOutgasserRecipes.setRecipe(15000, "ingotPu238", new ItemStack(ModItems.ingot_pu239));
		RBMKOutgasserRecipes.setRecipe(21000, "ingotPu239", new ItemStack(ModItems.ingot_pu240));
		RBMKOutgasserRecipes.setRecipe(800000, "ingotPu240", new ItemStack(ModItems.ingot_pu241));
		RBMKOutgasserRecipes.setRecipe(6000000, "ingotPu241", new ItemStack(ModItems.ingot_am241));
		RBMKOutgasserRecipes.setRecipe(75000, "ingotAm241", new ItemStack(ModItems.ingot_am242));
		RBMKOutgasserRecipes.setRecipe(69000, "ingotSchrabidium", new ItemStack(ModItems.ingot_solinium));
		RBMKOutgasserRecipes.setRecipe(690000, "ingotSolinium", new ItemStack(ModItems.nugget_euphemium));
		RBMKOutgasserRecipes.setRecipe(6900000, "ingotEuphemium", new ItemStack(ModItems.nugget_dineutronium));
		RBMKOutgasserRecipes.setRecipe(5000, ModItems.ingot_cobalt, new ItemStack(ModItems.ingot_co60));
		RBMKOutgasserRecipes.setRecipe(9000, "dustCobalt", new ItemStack(ModItems.powder_co60));
		RBMKOutgasserRecipes.setRecipe(45000, ModItems.ingot_iodine, new ItemStack(ModItems.ingot_i131));
		RBMKOutgasserRecipes.setRecipe(35000, "dustIodine", new ItemStack(ModItems.powder_i131));
		RBMKOutgasserRecipes.setRecipe(80000, "dustCaesium", new ItemStack(ModItems.powder_cs137));
		RBMKOutgasserRecipes.setRecipe(120000, "dustAstatine", new ItemStack(ModItems.powder_at209));
		RBMKOutgasserRecipes.setRecipe(500000, "dustAt209", new ItemStack(ModItems.ingot_bismuth));
		RBMKOutgasserRecipes.setRecipe(1400000, ModItems.ingot_lead, new ItemStack(ModItems.ingot_pb209));
		RBMKOutgasserRecipes.setRecipe(900000, ModItems.powder_lead, new ItemStack(ModItems.powder_pb209));
		RBMKOutgasserRecipes.setRecipe(18000, ModItems.ingot_niobium, new ItemStack(ModItems.ingot_technetium));
		RBMKOutgasserRecipes.setRecipe(250000, "blockU238", new ItemStack(ModItems.nugget_ra226));
		RBMKOutgasserRecipes.setRecipe(20000000, ModItems.powder_spark_mix, new ItemStack(ModItems.pellet_charged));
		RBMKOutgasserRecipes.setRecipe(16000, "nuggetReiium", new ItemStack(ModItems.nugget_weidanium));
		RBMKOutgasserRecipes.setRecipe(16000, "nuggetWeidanium", new ItemStack(ModItems.nugget_australium));
		RBMKOutgasserRecipes.setRecipe(16000, "nuggetAustralium", new ItemStack(ModItems.nugget_verticium));
		RBMKOutgasserRecipes.setRecipe(16000, "nuggetVerticium", new ItemStack(ModItems.nugget_unobtainium));
		RBMKOutgasserRecipes.setRecipe(16000, "nuggetUnobtainium", new ItemStack(ModItems.nugget_daffergon));
		RBMKOutgasserRecipes.setRecipe(3000, ModBlocks.block_scrap, new ItemStack(ModBlocks.block_fallout));
		RBMKOutgasserRecipes.setRecipe(2000, Blocks.STONE, new ItemStack(ModBlocks.sellafield_slaked));
		RBMKOutgasserRecipes.setRecipe(4000, ModBlocks.sellafield_slaked, new ItemStack(ModBlocks.sellafield_0));
		RBMKOutgasserRecipes.setRecipe(8000, ModBlocks.sellafield_0, new ItemStack(ModBlocks.sellafield_1));
		RBMKOutgasserRecipes.setRecipe(16000, ModBlocks.sellafield_1, new ItemStack(ModBlocks.sellafield_2));
		RBMKOutgasserRecipes.setRecipe(32000, ModBlocks.sellafield_2, new ItemStack(ModBlocks.sellafield_3));
		RBMKOutgasserRecipes.setRecipe(64000, ModBlocks.sellafield_3, new ItemStack(ModBlocks.sellafield_4));
		RBMKOutgasserRecipes.setRecipe(128000, ModBlocks.sellafield_4, new ItemStack(ModBlocks.sellafield_core));
		RBMKOutgasserRecipes.setRecipe(7800000, ModBlocks.block_corium_cobble, new ItemStack(ModBlocks.block_corium));
	}

	public static void setRecipe(int requiredFlux, ItemStack in, ItemStack out) {
		rbmkOutgasserRecipes.put(new ComparableStack(in), new Object[] {requiredFlux, out});
	}

	public static void setRecipe(int requiredFlux, Item in, ItemStack out) {
		rbmkOutgasserRecipes.put(new ComparableStack(in), new Object[] {requiredFlux, out});
	}
	
	public static void setRecipe(int requiredFlux, Block in, ItemStack out) {
		rbmkOutgasserRecipes.put(new ComparableStack(in), new Object[] {requiredFlux, out});
	}

	public static void setRecipe(int requiredFlux, String in, ItemStack out) {
		rbmkOutgasserRecipes.put(new ComparableStack(OreDictionary.getOres(in).get(0)), new Object[] {requiredFlux, out});
	}

	public static int getRequiredFlux(ItemStack stack) {
		
		if(stack == null || stack.isEmpty())
			return -1;
		
		ComparableStack comp = new ComparableStack(stack).makeSingular();
		if(rbmkOutgasserRecipes.containsKey(comp)){
			return (int)rbmkOutgasserRecipes.get(comp)[0];
		}

		String[] dictKeys = comp.getDictKeys();
		
		for(String key : dictKeys) {
			if(rbmkOutgasserRecipes.containsKey(key)){
				return (int)rbmkOutgasserRecipes.get(key)[1];
			}
		}
		return -1;
	}

	public static ItemStack getOutput(ItemStack stack) {
		
		if(stack == null || stack.getItem() == null)
			return null;

		ComparableStack comp = new ComparableStack(stack).makeSingular();
		if(rbmkOutgasserRecipes.containsKey(comp)){
			return (ItemStack)rbmkOutgasserRecipes.get(comp)[1];
		}
		
		String[] dictKeys = comp.getDictKeys();
		
		for(String key : dictKeys) {
			
			if(rbmkOutgasserRecipes.containsKey(key)){
				return (ItemStack)rbmkOutgasserRecipes.get(key)[1];
			}
		}
		return null;
	}

	public static List<RBMKOutgasserRecipe> getRBMKOutgasserRecipes() {
		if(jeiRBMKOutgasserRecipes == null){
			jeiRBMKOutgasserRecipes = new ArrayList<RBMKOutgasserRecipe>();
			for(Entry<ComparableStack, Object[]> e : rbmkOutgasserRecipes.entrySet()){
				jeiRBMKOutgasserRecipes.add(new RBMKOutgasserRecipe(e.getKey().toStack(), (int)e.getValue()[0], (ItemStack)e.getValue()[1]));
			}
		}
		return jeiRBMKOutgasserRecipes;
	}
	
	public static class RBMKOutgasserRecipe implements IRecipeWrapper {
		
		private final ItemStack input;
		private final int requiredFlux;
		private final ItemStack output;
		
		public RBMKOutgasserRecipe(ItemStack input, int requiredFlux, ItemStack output) {
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
	    	
	    	fontRenderer.drawString("Flux", 21-12, 33-17, 4210752);
	    	fontRenderer.drawString(""+requiredFlux, 123-12-fontRenderer.getStringWidth(""+requiredFlux), 34-17, 0x46EA00);
	    	GlStateManager.color(1, 1, 1, 1);
		}
	}
}