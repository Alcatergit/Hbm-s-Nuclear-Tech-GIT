package com.hbm.blocks.fluid;

import java.awt.Color;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class RadWaterFluid extends Fluid {

	public RadWaterFluid(String name){
		super(name, new ResourceLocation("blocks/water_still"), new ResourceLocation("blocks/water_flow"), Color.white);
	}
	
}
