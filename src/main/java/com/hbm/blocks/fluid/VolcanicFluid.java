package com.hbm.blocks.fluid;

import com.hbm.lib.RefStrings;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import java.awt.*;

public class VolcanicFluid extends Fluid {

	public VolcanicFluid() {
		super("volcanic_lava_fluid", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/volcanic_lava_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/volcanic_lava_flowing"), Color.white);
	}
}
