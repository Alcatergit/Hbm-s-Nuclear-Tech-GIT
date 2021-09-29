package com.hbm.blocks.fluid;

import java.awt.Color;

import com.hbm.lib.RefStrings;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class VolcanicFluid extends Fluid {

	public VolcanicFluid() {
		super("volcanic_lava_fluid", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/volcanic_lava_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/volcanic_lava_flowing"), Color.white);
	}
}
