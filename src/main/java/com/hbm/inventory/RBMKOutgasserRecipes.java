package com.hbm.inventory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.blocks.ModBlocks;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemHazard;
import com.hbm.items.machine.ItemFluidIcon;

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

public class RBMKOutgasserRecipes {

	public static LinkedHashMap<ComparableStack, Object[]> rbmkOutgasserRecipes = new LinkedHashMap<ComparableStack, Object[]>();
	public static List<RBMKOutgasserRecipe> jeiRBMKOutgasserRecipes = null;
	
	public static void registerOverrides() {
		addRecipe(240,  LI.dustTiny(), ItemFluidIcon.getStackWithQuantity(ModForgeFluids.tritium, 120));
		addRecipe(1200, LI.dust(), ItemFluidIcon.getStackWithQuantity(ModForgeFluids.tritium, 800));
		addRecipe(1500, LI.ingot(), ItemFluidIcon.getStackWithQuantity(ModForgeFluids.tritium, 800));
		addRecipe(10000, LI.block(), ItemFluidIcon.getStackWithQuantity(ModForgeFluids.tritium, 8000));
		addRecipe(6000, Blocks.BROWN_MUSHROOM, new ItemStack(ModBlocks.mush));
		addRecipe(6000, Blocks.RED_MUSHROOM, new ItemStack(ModBlocks.mush));
		addRecipe(18000, Items.MUSHROOM_STEW, new ItemStack(ModItems.glowing_stew));
		
		addRecipe(360000 * ItemHazard.nugget, GOLD.nugget(), new ItemStack(ModItems.nugget_au198));
		addRecipe(360000 * ItemHazard.powder_tiny, GOLD.dustTiny(), new ItemStack(ModItems.powder_au198_tiny));
		addRecipe(360000, GOLD.ingot(), new ItemStack(ModItems.ingot_au198));
		addRecipe(360000 * ItemHazard.powder, GOLD.dust(), new ItemStack(ModItems.powder_au198));
		addRecipe(360000 * ItemHazard.block, GOLD.block(), new ItemStack(ModBlocks.block_au198));
		addRecipe(360000 * ItemHazard.powder * ItemHazard.block, ModBlocks.sand_gold, new ItemStack(ModBlocks.sand_gold198));
		
		addRecipe(90000 * ItemHazard.nugget, TH232.nugget(), new ItemStack(ModItems.nugget_thorium_fuel));
		addRecipe(90000 * ItemHazard.billet, TH232.billet(), new ItemStack(ModItems.billet_thorium_fuel));
		addRecipe(90000, TH232.ingot(), new ItemStack(ModItems.ingot_thorium_fuel));
		addRecipe(90000 * ItemHazard.block, TH232.block(), new ItemStack(ModBlocks.block_thorium_fuel));
		
		addRecipe(60000 * ItemHazard.nugget, U233.nugget(), new ItemStack(ModItems.nugget_u235));
		addRecipe(60000 * ItemHazard.billet, U233.billet(), new ItemStack(ModItems.billet_u235));
		addRecipe(60000, U233.ingot(), new ItemStack(ModItems.ingot_u235));
		addRecipe(60000 * ItemHazard.block, U233.block(), new ItemStack(ModBlocks.block_u235));
		
		addRecipe(100000 * ItemHazard.nugget, U235.nugget(), new ItemStack(ModItems.nugget_neptunium_fuel));
		addRecipe(100000 * ItemHazard.billet, U235.billet(), new ItemStack(ModItems.billet_neptunium_fuel));
		addRecipe(100000, U235.ingot(), new ItemStack(ModItems.ingot_neptunium_fuel));
		
		addRecipe(170000 * ItemHazard.nugget, NP237.nugget(), new ItemStack(ModItems.nugget_pu238));
		addRecipe(170000 * ItemHazard.billet, NP237.billet(), new ItemStack(ModItems.billet_pu238));
		addRecipe(170000, NP237.ingot(), new ItemStack(ModItems.ingot_pu238));
		addRecipe(170000 * ItemHazard.block, NP237.block(), new ItemStack(ModBlocks.block_pu238));
		
		addRecipe(190000 * ItemHazard.nugget, U238.nugget(), new ItemStack(ModItems.nugget_pu239));
		addRecipe(190000 * ItemHazard.billet, U238.billet(), new ItemStack(ModItems.billet_pu239));
		addRecipe(190000, U238.ingot(), new ItemStack(ModItems.ingot_pu239));
		addRecipe(190000 * ItemHazard.block, U238.block(), new ItemStack(ModBlocks.block_pu239));
		
		addRecipe(150000 * ItemHazard.nugget, PU238.nugget(), new ItemStack(ModItems.nugget_pu239));
		addRecipe(150000 * ItemHazard.billet, PU238.billet(), new ItemStack(ModItems.billet_pu239));
		addRecipe(150000, PU238.ingot(), new ItemStack(ModItems.ingot_pu239));
		addRecipe(150000 * ItemHazard.block, PU238.block(), new ItemStack(ModBlocks.block_pu239));
		
		addRecipe(210000 * ItemHazard.nugget, PU239.nugget(), new ItemStack(ModItems.nugget_pu240));
		addRecipe(210000 * ItemHazard.billet, PU239.billet(), new ItemStack(ModItems.billet_pu240));
		addRecipe(210000, PU239.ingot(), new ItemStack(ModItems.ingot_pu240));
		addRecipe(210000 * ItemHazard.block, PU239.block(), new ItemStack(ModBlocks.block_pu240));
		
		addRecipe(2000000 * ItemHazard.nugget, PU240.nugget(), new ItemStack(ModItems.nugget_pu241));
		addRecipe(2000000 * ItemHazard.billet, PU240.billet(), new ItemStack(ModItems.billet_pu241));
		addRecipe(2000000, PU240.ingot(), new ItemStack(ModItems.ingot_pu241));
		
		addRecipe(6000000 * ItemHazard.nugget, PU241.nugget(), new ItemStack(ModItems.nugget_am241));
		addRecipe(6000000 * ItemHazard.billet, PU241.billet(), new ItemStack(ModItems.billet_am241));
		addRecipe(6000000, PU241.ingot(), new ItemStack(ModItems.ingot_am241));
		
		addRecipe(750000 * ItemHazard.nugget, AM241.nugget(), new ItemStack(ModItems.nugget_am242));
		addRecipe(750000 * ItemHazard.billet, AM241.billet(), new ItemStack(ModItems.billet_am242));
		addRecipe(750000, AM241.ingot(), new ItemStack(ModItems.ingot_am242));
		
		addRecipe(690000 * ItemHazard.nugget, SA326.nugget(), new ItemStack(ModItems.nugget_solinium));
		addRecipe(690000 * ItemHazard.billet, SA326.billet(), new ItemStack(ModItems.billet_solinium));
		addRecipe(690000, SA326.ingot(), new ItemStack(ModItems.ingot_solinium));
		addRecipe(690000 * ItemHazard.block, SA326.block(), new ItemStack(ModBlocks.block_solinium));
		
		addRecipe(50000 * ItemHazard.nugget,CO.nugget(), new ItemStack(ModItems.nugget_co60));
		addRecipe(50000 * ItemHazard.powder_tiny, CO.dustTiny(), new ItemStack(ModItems.powder_co60_tiny));
		addRecipe(50000, CO.ingot(), new ItemStack(ModItems.ingot_co60));
		addRecipe(50000 * ItemHazard.powder, CO.dust(), new ItemStack(ModItems.powder_co60));
		
		addRecipe(55000 * ItemHazard.nugget, SR.nugget(), new ItemStack(ModItems.nugget_sr90));
		addRecipe(55000, SR.ingot(), new ItemStack(ModItems.ingot_sr90));
		addRecipe(55000 * ItemHazard.powder, SR.dust(), new ItemStack(ModItems.powder_sr90));

		addRecipe(45000 * ItemHazard.powder_tiny, I.dustTiny(), new ItemStack(ModItems.powder_i131_tiny));
		addRecipe(45000, I.ingot(), new ItemStack(ModItems.ingot_i131));
		addRecipe(45000 * ItemHazard.powder, I.dust(), new ItemStack(ModItems.powder_i131));
		
		addRecipe(450000 * ItemHazard.nugget, AC.nugget(), new ItemStack(ModItems.nugget_ac227));
		addRecipe(450000, AC.ingot(), new ItemStack(ModItems.ingot_ac227));
		addRecipe(450000 * ItemHazard.powder, AC.dust(), new ItemStack(ModItems.powder_ac227));
		
		addRecipe(80000, CS.dust(), new ItemStack(ModItems.powder_cs137));
		addRecipe(120000, AT.dust(), new ItemStack(ModItems.powder_at209));
		
		addRecipe(120000 * ItemHazard.nugget, ModItems.nugget_australium, new ItemStack(ModItems.nugget_australium_lesser));
		addRecipe(120000 * ItemHazard.billet, ModItems.billet_australium, new ItemStack(ModItems.billet_australium_lesser));
		
		addRecipe(14000000 * ItemHazard.nugget, PB.nugget(), new ItemStack(ModItems.nugget_pb209));
		addRecipe(14000000 * ItemHazard.powder_tiny, PB.dustTiny(), new ItemStack(ModItems.powder_pb209_tiny));
		addRecipe(14000000, PB.ingot(), new ItemStack(ModItems.ingot_pb209));
		addRecipe(14000000 * ItemHazard.powder, PB.dust(), new ItemStack(ModItems.powder_pb209));
		
		addRecipe(1800000, NB.ingot(), new ItemStack(ModItems.ingot_technetium));
		addRecipe(32000, ModItems.nugget_unobtainium_lesser, new ItemStack(ModItems.nugget_unobtainium));
		addRecipe(300000, ModItems.scrap, new ItemStack(ModItems.fallout));
		addRecipe(3000000, ModBlocks.block_scrap, new ItemStack(ModBlocks.block_fallout));
		addRecipe(2000, Blocks.STONE, new ItemStack(ModBlocks.sellafield_slaked));
		addRecipe(4000, ModBlocks.sellafield_slaked, new ItemStack(ModBlocks.sellafield_0));
		addRecipe(8000, ModBlocks.sellafield_0, new ItemStack(ModBlocks.sellafield_1));
		addRecipe(16000, ModBlocks.sellafield_1, new ItemStack(ModBlocks.sellafield_2));
		addRecipe(32000, ModBlocks.sellafield_2, new ItemStack(ModBlocks.sellafield_3));
		addRecipe(64000, ModBlocks.sellafield_3, new ItemStack(ModBlocks.sellafield_4));
		addRecipe(128000, ModBlocks.sellafield_4, new ItemStack(ModBlocks.sellafield_core));
		addRecipe(500000, ModBlocks.block_corium_cobble, new ItemStack(ModBlocks.block_corium));
		addRecipe(1000000, ModItems.meteorite_sword_bred, new ItemStack(ModItems.meteorite_sword_irradiated));
	}

	public static void addRecipe(int requiredFlux, ItemStack in, ItemStack out) {
		rbmkOutgasserRecipes.put(new ComparableStack(in), new Object[] {requiredFlux, out});
	}

	public static void addRecipe(int requiredFlux, Item in, ItemStack out) {
		rbmkOutgasserRecipes.put(new ComparableStack(in), new Object[] {requiredFlux, out});
	}
	
	public static void addRecipe(int requiredFlux, Block in, ItemStack out) {
		rbmkOutgasserRecipes.put(new ComparableStack(in), new Object[] {requiredFlux, out});
	}

	public static void addRecipe(int requiredFlux, String in, ItemStack out) {
		if(!OreDictionary.getOres(in).isEmpty() && OreDictionary.getOres(in).get(0) != null && !OreDictionary.getOres(in).get(0).isEmpty())
			rbmkOutgasserRecipes.put(new ComparableStack(OreDictionary.getOres(in).get(0)), new Object[] {requiredFlux, out});
	}

	public static void addRecipe(float requiredFlux, String in, ItemStack out) {
		addRecipe((int)requiredFlux, in, out);
	}

	public static void addRecipe(float requiredFlux, Item in, ItemStack out) {
		addRecipe((int)requiredFlux, in, out);
	}

	public static void addRecipe(float requiredFlux, Block in, ItemStack out) {
		addRecipe((int)requiredFlux, in, out);
	}
	
	public static void removeRecipe(ItemStack in) {
		rbmkOutgasserRecipes.remove(new ComparableStack(in));
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