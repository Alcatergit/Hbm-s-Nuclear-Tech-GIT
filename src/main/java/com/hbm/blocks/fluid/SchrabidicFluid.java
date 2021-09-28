package com.hbm.blocks.fluid;

import java.awt.Color;

import com.hbm.lib.RefStrings;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class SchrabidicFluid extends Fluid {

	public SchrabidicFluid(String name){
		super(name, new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/schrabidic_acid_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/schrabidic_acid_flowing"), Color.white);
	}
	
}
