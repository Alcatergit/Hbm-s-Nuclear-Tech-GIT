package com.hbm.handler.jei;

import com.hbm.inventory.CrystallizerRecipes.CrystallizerRecipe;
import com.hbm.lib.RefStrings;

import com.hbm.util.I18nUtil;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.util.ResourceLocation;

public class CrystallizerRecipeHandler implements IRecipeCategory<CrystallizerRecipe> {

	public static ResourceLocation gui_rl = new ResourceLocation(RefStrings.MODID + ":textures/gui/jei/gui_nei_three_to_one.png");
	
	protected final IDrawable background;
	
	public CrystallizerRecipeHandler(IGuiHelper help) {
		background = help.createDrawable(gui_rl, 52, 34, 90, 18);
	}
	
	@Override
	public String getUid() {
		return JEIConfig.CRYSTALLIZER;
	}

	@Override
	public String getTitle() {
		return I18nUtil.resolveKey("tile.machine_crystallizer.name");
	}

	@Override
	public String getModName() {
		return RefStrings.MODID;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CrystallizerRecipe recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		guiItemStacks.init(0, true, 0, 0);
		guiItemStacks.init(1, true, 18, 0);
		guiItemStacks.init(2, false, 72, 0);
		
		guiItemStacks.set(ingredients);
	}

}
