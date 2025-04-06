package com.hbm.blocks.fluid;

import com.hbm.lib.RefStrings;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import java.awt.*;

public class MudFluid extends Fluid {

	public MudFluid(){
		super("mud_fluid", new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/mud_still"), new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/mud_flowing"), Color.white);
	}

}
