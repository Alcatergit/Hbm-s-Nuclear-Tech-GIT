package com.leafia.dev.fluids.ntmtraits;

import com.hbm.lib.RefStrings;
import com.leafia.dev.fluids.LeafiaFluidTrait;
import net.minecraft.util.ResourceLocation;

public abstract class NTMTraitBase extends LeafiaFluidTrait {
	public NTMTraitBase(int iconX,int iconY) {
		super();
		iconU0 = iconX/256F;
		iconV0 = iconY/256F;
		iconU1 = (iconX+59)/256F;
		iconV1 = (iconY+59)/256F;
		iconPriority = 0;
		iconTexture = new ResourceLocation(RefStrings.MODID + ":textures/models/misc/danger_diamond.png");
	}
}
