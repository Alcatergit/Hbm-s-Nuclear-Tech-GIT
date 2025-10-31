package com.hbm.handler.jei;

import com.hbm.handler.jei.JeiRecipes.SolderingRecipe;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.util.ResourceLocation;

public class SolderingRecipeHandler implements IRecipeCategory<SolderingRecipe> {

	public static ResourceLocation gui_rl = new ResourceLocation(RefStrings.MODID + ":textures/gui/jei/gui_nei_soldering_station.png");
	protected final IDrawable background;

	public SolderingRecipeHandler(IGuiHelper help) {
		background = help.createDrawable(gui_rl, 16, 16, 126, 54);
	}

	@Override
	public String getUid() {
		return JEIConfig.SOLDERING;
	}

	@Override
	public String getTitle() {
		return I18nUtil.resolveKey("tile.machine_soldering.name");
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
	public void setRecipe(IRecipeLayout recipeLayout, SolderingRecipe recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		int index = 0;
		for(int i = 0; i < recipeWrapper.toppingCount; i++) {
			guiItemStacks.init(index+i, true, i * 18, 0);
		}
		index += recipeWrapper.toppingCount;
		for(int i = 0; i < recipeWrapper.pcbCount; i++) {
			guiItemStacks.init(index+i, true, i * 18, 18);
		}
		index += recipeWrapper.pcbCount;
		if(0 < recipeWrapper.solderCount) {
			guiItemStacks.init(index, true, 36, 18);
			index++;
		}
		guiItemStacks.init(index, true, 18, 36);
		guiItemStacks.init(index+1, false, 90, 18);

		guiItemStacks.set(ingredients);
	}
}
