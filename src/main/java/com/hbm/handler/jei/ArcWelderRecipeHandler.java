package com.hbm.handler.jei;

import com.hbm.handler.jei.JeiRecipes.ArcWelderRecipe;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.util.ResourceLocation;

public class ArcWelderRecipeHandler implements IRecipeCategory<ArcWelderRecipe> {

	public static ResourceLocation gui_rl = new ResourceLocation(RefStrings.MODID + ":textures/gui/jei/gui_nei_arc_welder.png");
	protected final IDrawable background;

	public ArcWelderRecipeHandler(IGuiHelper help) {
		background = help.createDrawable(gui_rl, 16, 16, 126, 54);
	}

	@Override
	public String getUid() {
		return JEIConfig.ARCWELDER;
	}

	@Override
	public String getTitle() {
		return I18nUtil.resolveKey("container.machineArcWelder");
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
	public void setRecipe(IRecipeLayout recipeLayout, ArcWelderRecipe recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		int index = 0;
		for(int i = 0; i < recipeWrapper.itemLen; i++) {
			guiItemStacks.init(index+i, true, i * 18, 18);
		}
		index += recipeWrapper.itemLen;
		guiItemStacks.init(index, true, 18, 36);
		guiItemStacks.init(index+1, false, 90, 18);

		guiItemStacks.set(ingredients);
	}
}
