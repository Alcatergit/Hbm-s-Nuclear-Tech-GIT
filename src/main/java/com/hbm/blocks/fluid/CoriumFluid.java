package com.hbm.blocks.fluid;

import java.awt.Color;

import com.hbm.lib.RefStrings;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class CoriumFluid extends Fluid {
	
	public CoriumFluid(){
		super("corium_fluid", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/corium_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/corium_flowing"), Color.white);
	}

}
