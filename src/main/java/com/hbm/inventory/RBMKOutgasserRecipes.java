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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RBMKOutgasserRecipes {

	public static HashMap<ComparableStack, ItemStack> rbmkOutgasserRecipes = new HashMap<ComparableStack, ItemStack>();
	public static List<RBMKOutgasserRecipe> jeiRBMKOutgasserRecipes = null;
	
	public static void registerOverrides() {
		RBMKOutgasserRecipes.setRecipe("blockLithium", ItemFluidIcon.getStackWithQuantity(ModForgeFluids.tritium, 8000));
		RBMKOutgasserRecipes.setRecipe("ingotLithium", ItemFluidIcon.getStackWithQuantity(ModForgeFluids.tritium, 1000));
		RBMKOutgasserRecipes.setRecipe("dustLithium", ItemFluidIcon.getStackWithQuantity(ModForgeFluids.tritium, 1000));
		RBMKOutgasserRecipes.setRecipe("dustSmallLithium", ItemFluidIcon.getStackWithQuantity(ModForgeFluids.tritium, 120));
		RBMKOutgasserRecipes.setRecipe("ingotGold", new ItemStack(ModItems.ingot_au198));
		RBMKOutgasserRecipes.setRecipe("nuggetGold", new ItemStack(ModItems.nugget_au198));
		RBMKOutgasserRecipes.setRecipe("dustGold", new ItemStack(ModItems.powder_au198));
		RBMKOutgasserRecipes.setRecipe(Blocks.BROWN_MUSHROOM, new ItemStack(ModBlocks.mush));
		RBMKOutgasserRecipes.setRecipe(Blocks.RED_MUSHROOM, new ItemStack(ModBlocks.mush));
		RBMKOutgasserRecipes.setRecipe(Items.MUSHROOM_STEW, new ItemStack(ModItems.glowing_stew));
		RBMKOutgasserRecipes.setRecipe("ingotTh232", new ItemStack(ModItems.ingot_u233));
		RBMKOutgasserRecipes.setRecipe("ingotU233", new ItemStack(ModItems.ingot_u235));
		RBMKOutgasserRecipes.setRecipe("ingotU235", new ItemStack(ModItems.ingot_u238));
		RBMKOutgasserRecipes.setRecipe("ingotAm241", new ItemStack(ModItems.ingot_am242));
		RBMKOutgasserRecipes.setRecipe("ingotU235", new ItemStack(ModItems.ingot_u238));
		RBMKOutgasserRecipes.setRecipe("ingotU238", new ItemStack(ModItems.ingot_pu239));
		RBMKOutgasserRecipes.setRecipe("ingotPu238", new ItemStack(ModItems.ingot_pu239));
		RBMKOutgasserRecipes.setRecipe("ingotPu239", new ItemStack(ModItems.ingot_pu240));
		RBMKOutgasserRecipes.setRecipe("nuggetPu240", new ItemStack(ModItems.nugget_pu241));
		RBMKOutgasserRecipes.setRecipe("ingotSolinium", new ItemStack(ModItems.ingot_schrabidium));
		RBMKOutgasserRecipes.setRecipe("ingotSchrabidium", new ItemStack(ModItems.nugget_euphemium));
		RBMKOutgasserRecipes.setRecipe("ingotEuphemium", new ItemStack(ModItems.nugget_dineutronium));
		RBMKOutgasserRecipes.setRecipe("ingotCobalt", new ItemStack(ModItems.ingot_co60));
		RBMKOutgasserRecipes.setRecipe("dustCobalt", new ItemStack(ModItems.powder_co60));
		RBMKOutgasserRecipes.setRecipe("dustIodine", new ItemStack(ModItems.powder_i131));
		RBMKOutgasserRecipes.setRecipe("dustI131", new ItemStack(ModItems.powder_xe135));
		RBMKOutgasserRecipes.setRecipe("dustCaesium", new ItemStack(ModItems.powder_cs137));
		RBMKOutgasserRecipes.setRecipe("dustAstatine", new ItemStack(ModItems.powder_at209));
		RBMKOutgasserRecipes.setRecipe("dustAt209", new ItemStack(ModItems.ingot_bismuth));
		RBMKOutgasserRecipes.setRecipe("ingotNiobium", new ItemStack(ModItems.ingot_technetium));
		RBMKOutgasserRecipes.setRecipe("blockU238", new ItemStack(ModItems.nugget_ra226));
		RBMKOutgasserRecipes.setRecipe(ModItems.powder_spark_mix, new ItemStack(ModItems.pellet_charged));
		RBMKOutgasserRecipes.setRecipe("nuggetReiium", new ItemStack(ModItems.nugget_weidanium));
		RBMKOutgasserRecipes.setRecipe("nuggetWeidanium", new ItemStack(ModItems.nugget_australium));
		RBMKOutgasserRecipes.setRecipe("nuggetAustralium", new ItemStack(ModItems.nugget_verticium));
		RBMKOutgasserRecipes.setRecipe("nuggetVerticium", new ItemStack(ModItems.nugget_unobtainium));
		RBMKOutgasserRecipes.setRecipe("nuggetUnobtainium", new ItemStack(ModItems.nugget_daffergon));
		RBMKOutgasserRecipes.setRecipe(ModBlocks.block_scrap, new ItemStack(ModBlocks.block_fallout));
	}

	public static void setRecipe(ItemStack in, ItemStack out) {
		
		rbmkOutgasserRecipes.put(new ComparableStack(in), out);
	}
	public static void setRecipe(Item in, ItemStack out) {
		
		rbmkOutgasserRecipes.put(new ComparableStack(in), out);
	}
	
	public static void setRecipe(Block in, ItemStack out) {
		
		rbmkOutgasserRecipes.put(new ComparableStack(in), out);
	}

	public static void setRecipe(String in, ItemStack out) {
		
		rbmkOutgasserRecipes.put(new ComparableStack(OreDictionary.getOres(in).get(0)), out);
	}

	public static ItemStack getOutput(ItemStack stack) {
		
		if(stack == null || stack.getItem() == null)
			return null;

		ComparableStack comp = new ComparableStack(stack).makeSingular();

		if(rbmkOutgasserRecipes.containsKey(comp))
			return rbmkOutgasserRecipes.get(comp);
		
		String[] dictKeys = comp.getDictKeys();
		
		for(String key : dictKeys) {
			
			if(rbmkOutgasserRecipes.containsKey(key))
				return rbmkOutgasserRecipes.get(key);
		}
		
		return null;
	}

	public static List<RBMKOutgasserRecipe> getRBMKOutgasserRecipes() {
		if(jeiRBMKOutgasserRecipes == null){
			jeiRBMKOutgasserRecipes = new ArrayList<RBMKOutgasserRecipe>();
			for(Entry<ComparableStack, ItemStack> e : rbmkOutgasserRecipes.entrySet()){
				jeiRBMKOutgasserRecipes.add(new RBMKOutgasserRecipe(e.getKey().toStack(), e.getValue()));
			}
		}
		
		return jeiRBMKOutgasserRecipes;
	}
	
	public static class RBMKOutgasserRecipe implements IRecipeWrapper {
		
		private final ItemStack input;
		private final ItemStack output;
		
		public RBMKOutgasserRecipe(ItemStack input, ItemStack output) {
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

