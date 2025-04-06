package com.hbm.blocks.fluid;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import java.awt.*;

public class RadWaterFluid extends Fluid {

	public RadWaterFluid(String name){
		super(name, new ResourceLocation("blocks/water_still"), new ResourceLocation("blocks/water_flow"), Color.white);
	}
	
}
