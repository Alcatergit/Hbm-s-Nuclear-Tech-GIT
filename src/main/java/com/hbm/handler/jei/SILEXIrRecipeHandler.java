package com.hbm.handler.jei;

import com.hbm.handler.jei.SILEXRecipeHandler;

import mezz.jei.api.IGuiHelper;

public class SILEXIrRecipeHandler extends SILEXRecipeHandler {

	public SILEXIrRecipeHandler(IGuiHelper help){
		super(help);
	}

	@Override
	public String getUid(){
		return JEIConfig.SILEX_IR;
	}

	@Override
	public String getTitle(){
		return "SILEX Infrared Recipes";
	}
}
