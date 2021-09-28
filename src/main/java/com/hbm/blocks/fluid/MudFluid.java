package com.hbm.blocks.fluid;

import java.awt.Color;

import com.hbm.lib.RefStrings;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class MudFluid extends Fluid {

	public MudFluid(){
		super("mud_fluid", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/mud_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/mud_flowing"), Color.white);
	}

}
