package com.hbm.handler.jei;

import com.hbm.handler.jei.JeiRecipes.FoundryPourRecipe;

import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.util.ResourceLocation;

public class FoundryPourRecipeHandler implements IRecipeCategory<FoundryPourRecipe> {

	public static ResourceLocation gui_rl = new ResourceLocation(RefStrings.MODID + ":textures/gui/jei/gui_nei_crucible_casting.png");
	protected final IDrawable background;

	public FoundryPourRecipeHandler(IGuiHelper help) {
		background = help.createDrawable(gui_rl, 16, 16, 126, 54);
	}

	@Override
	public String getUid() {
		return JEIConfig.FOUNDRYPOUR;
	}

	@Override
	public String getTitle() {
		return I18nUtil.resolveKey("container.foundryCasting");
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
	public void setRecipe(IRecipeLayout recipeLayout, FoundryPourRecipe recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 54, 32);
		guiItemStacks.init(1, true, 54, 4);
		guiItemStacks.init(2, true, 18, 18);
		guiItemStacks.init(3, false, 90, 18);

		guiItemStacks.set(ingredients);
	}
}
