package com.hbm.blocks.fluid;

import com.hbm.lib.RefStrings;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import java.awt.*;

public class RadWaterFluid extends Fluid {

	public RadWaterFluid(String name){
		super(name, new ResourceLocation(RefStrings.MODID,"blocks/forgefluid/irradiated_still"), new ResourceLocation(RefStrings.MODID,"blocks/forgefluid/irradiated_flow"), Color.white);
	}
	
}
