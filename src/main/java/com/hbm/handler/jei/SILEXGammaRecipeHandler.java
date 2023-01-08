package com.hbm.handler.jei;

import com.hbm.handler.jei.SILEXRecipeHandler;

import mezz.jei.api.IGuiHelper;

public class SILEXGammaRecipeHandler extends SILEXRecipeHandler {

	public SILEXGammaRecipeHandler(IGuiHelper help){
		super(help);
	}

	@Override
	public String getUid(){
		return JEIConfig.SILEX_GAMMA;
	}

	@Override
	public String getTitle(){
		return "SILEX Gamma Recipes";
	}
}
