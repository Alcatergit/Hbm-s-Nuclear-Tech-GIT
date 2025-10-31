package com.leafia.contents.blockfluids.fluoride;

import com.hbm.lib.RefStrings;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import java.awt.*;

public class FluorideFluid extends Fluid {

	public FluorideFluid(String name){
		super(name, new ResourceLocation(RefStrings.MODID,"blocks/forgefluid/fluoride_still"), new ResourceLocation(RefStrings.MODID,"blocks/forgefluid/fluoride_flow"), Color.white);
	}
	
}
