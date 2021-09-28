package com.hbm.handler.jei;

import com.hbm.handler.jei.JeiRecipes.SILEXRecipe;
import com.hbm.lib.RefStrings;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public class SILEXRecipeHandler implements IRecipeCategory<SILEXRecipe> {

	public static final ResourceLocation gui_rl = new ResourceLocation(RefStrings.MODID, "textures/gui/jei/gui_nei_silex.png");
	
	protected final IDrawable background;
	
	private SILEXRecipe currentDrawHack;
	
	public SILEXRecipeHandler(IGuiHelper help){
		background = help.createDrawable(gui_rl, 3, 3, 170, 80);
	}
	
	@Override
	public String getUid(){
		return JEIConfig.SILEX;
	}

	@Override
	public String getTitle(){
		return "SILEX";
	}

	@Override
	public String getModName(){
		return RefStrings.MODID;
	}

	@Override
	public IDrawable getBackground(){
		return background;
	}

	@Override
	public void drawExtras(Minecraft minecraft){
		if(currentDrawHack != null){
			FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

			int sep = currentDrawHack.outputs.size() > 4 ? 3 : 2;
			for(int i = 0; i < currentDrawHack.outputs.size(); i ++){
				double chance = currentDrawHack.chances.get(i);
				if(i < sep) {
					fontRenderer.drawString(((int)(chance * 10D) / 10D)+"%", 88, 33 + i * 18 - 9 * ((Math.min(currentDrawHack.outputs.size(), sep) + 1) / 2), 0x404040);
				} else {
					fontRenderer.drawString(((int)(chance * 10D) / 10D)+"%", 136, 33 + (i - sep) * 18 - 9 * ((Math.min(currentDrawHack.outputs.size() - sep, sep) + 1)/2), 0x404040);
				}
			}
			
			String am = ((int)(currentDrawHack.produced * 10D) / 10D) + "x";
			fontRenderer.drawString(am, 55 - fontRenderer.getStringWidth(am) / 2, 51, 0x404040);
		}
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SILEXRecipe recipeWrapper, IIngredients ingredients){
		currentDrawHack = recipeWrapper;
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		guiItemStacks.init(0, true, 13, 31);
		
		int sep = recipeWrapper.outputs.size() > 4 ? 3 : 2;
		for(int i = 0; i < recipeWrapper.outputs.size(); i ++){
			if(i < sep) {
				guiItemStacks.init(i+1, false, 71, 27 + i * 18 - 9 * ((Math.min(recipeWrapper.outputs.size(), sep) + 1) / 2));
			} else {
				guiItemStacks.init(i+1, false, 119, 27 + (i - sep) * 18 - 9 * ((Math.min(recipeWrapper.outputs.size() - sep, sep) + 1)/2));
			}
		}
		
		guiItemStacks.set(ingredients);
	}

}
