package com.hbm.handler.jei;

import com.hbm.handler.jei.JeiRecipes.FoundrySmeltRecipe;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.util.ResourceLocation;

public class FoundrySmeltRecipeHandler implements IRecipeCategory<FoundrySmeltRecipe> {

	public static ResourceLocation gui_rl = new ResourceLocation(RefStrings.MODID + ":textures/gui/jei/gui_nei_crucible_smelting.png");
	protected final IDrawable background;

	public FoundrySmeltRecipeHandler(IGuiHelper help) {
		background = help.createDrawable(gui_rl, 16, 16, 126, 54);
	}

	@Override
	public String getUid() {
		return JEIConfig.FOUNDRYSMELT;
	}

	@Override
	public String getTitle() {
		return I18nUtil.resolveKey("container.foundrySmelting");
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
	public void setRecipe(IRecipeLayout recipeLayout, FoundrySmeltRecipe recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 18, 18);
		guiItemStacks.init(1, false, 72, 0);
		guiItemStacks.init(2, false, 90, 0);
		guiItemStacks.init(3, false, 108, 0);

		guiItemStacks.init(4, false, 72, 18);
		guiItemStacks.init(5, false, 90, 18);
		guiItemStacks.init(6, false, 108, 18);

		guiItemStacks.init(7, false, 70, 36);
		guiItemStacks.init(8, false, 90, 36);
		guiItemStacks.init(9, false, 108, 36);

		guiItemStacks.set(ingredients);
	}
}
