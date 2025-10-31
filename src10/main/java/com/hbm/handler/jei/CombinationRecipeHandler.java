package com.hbm.handler.jei;

import com.hbm.handler.jei.JeiRecipes.CombinationFurnaceRecipe;

import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.util.ResourceLocation;

public class CombinationRecipeHandler implements IRecipeCategory<CombinationFurnaceRecipe> {

	public static ResourceLocation gui_rl = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_furnace_combination.png");
	protected final IDrawable background;

	public CombinationRecipeHandler(IGuiHelper help) {
		background = help.createDrawable(gui_rl, 25, 35, 115, 18);
	}

	@Override
	public String getUid() {
		return JEIConfig.COMBINATION;
	}

	@Override
	public String getTitle() {
		return I18nUtil.resolveKey("container.furnaceCombination");
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
	public void setRecipe(IRecipeLayout recipeLayout, CombinationFurnaceRecipe recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 0, 0);
		guiItemStacks.init(1, false, 63, 0);
		guiItemStacks.init(2, false, 92, 0);

		guiItemStacks.set(ingredients);
	}
}
