package com.hbm.blocks.fluid;

import java.awt.Color;

import com.hbm.lib.RefStrings;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class RadWaterFluid extends Fluid {

	public RadWaterFluid(String name){
		super(name, new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/radwater_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/radwater_flowing"), Color.white);
	}
	
}
