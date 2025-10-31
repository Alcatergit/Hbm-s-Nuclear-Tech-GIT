package com.hbm.blocks.fluid;

import com.hbm.lib.RefStrings;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import java.awt.*;

public class ToxicFluid extends Fluid {

	public ToxicFluid(String name){
		super(name, new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/toxic_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/toxic_flowing"), Color.white);
	}
	
}
