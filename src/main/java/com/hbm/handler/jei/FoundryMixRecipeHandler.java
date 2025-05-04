package com.hbm.handler.jei;

import com.hbm.handler.jei.JeiRecipes.FoundryMixRecipe;

import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.util.ResourceLocation;

public class FoundryMixRecipeHandler implements IRecipeCategory<FoundryMixRecipe> {

	public static ResourceLocation gui_rl = new ResourceLocation(RefStrings.MODID + ":textures/gui/jei/gui_nei_crucible_mixing.png");
	protected final IDrawable background;

	public FoundryMixRecipeHandler(IGuiHelper help) {
		background = help.createDrawable(gui_rl, 16, 16, 108, 36);
	}

	@Override
	public String getUid() {
		return JEIConfig.FOUNDRYMIX;
	}

	@Override
	public String getTitle() {
		return I18nUtil.resolveKey("container.foundryMixing");
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
	public void setRecipe(IRecipeLayout recipeLayout, FoundryMixRecipe recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 45, 0);

		guiItemStacks.init(1, true, 0, 0);
		guiItemStacks.init(2, true, 18, 0);
		guiItemStacks.init(3, true, 0, 18);
		guiItemStacks.init(4, true, 18, 18);

		guiItemStacks.init(5, false, 72, 0);
		guiItemStacks.init(6, false, 90, 0);
		guiItemStacks.init(7, false, 72, 18);
		guiItemStacks.init(8, false, 90, 18);

		guiItemStacks.set(ingredients);
	}
}
