package com.hbm.handler.jei;

import com.hbm.handler.jei.JeiRecipes.GasCentRecipe;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import com.hbm.util.I18nUtil;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GasCentrifugeRecipeHandler implements IRecipeCategory<GasCentRecipe> {

	public static final ResourceLocation gui_rl = new ResourceLocation(RefStrings.MODID, "textures/gui/jei/gui_nei_gas_cent.png");
	
	protected final IDrawable background;
	
	public GasCentrifugeRecipeHandler(IGuiHelper help) {
		background = help.createDrawable(gui_rl, 23, 22, 121, 40);

	}
	
	@Override
	public String getUid() {
		return JEIConfig.GAS_CENT;
	}

	@Override
	public String getTitle() {
		return I18nUtil.resolveKey("tile.machine_gascent.name");
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
	public void setRecipe(IRecipeLayout recipeLayout, GasCentRecipe recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		guiItemStacks.init(0, true, 6, 6);
		
		guiItemStacks.init(1, false, 83, 2);
		guiItemStacks.init(2, false, 101, 2);
        guiItemStacks.init(3, false, 83, 20);
        guiItemStacks.init(4, false, 101, 20);

        if(recipeWrapper.isUpgraded) guiItemStacks.init(5, true, 47, 2);

		guiItemStacks.set(ingredients);
        if(recipeWrapper.isUpgraded) {
            List<ItemStack> u = new ArrayList<ItemStack>();
            u.add(new ItemStack(ModItems.upgrade_gc_speed));
            guiItemStacks.set(5, u);
        }
    }
}