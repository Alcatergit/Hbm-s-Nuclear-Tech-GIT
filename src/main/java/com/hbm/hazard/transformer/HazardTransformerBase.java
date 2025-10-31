package com.hbm.hazard.transformer;

import com.hbm.hazard.HazardEntry;
import net.minecraft.item.ItemStack;

import java.util.List;

public abstract class HazardTransformerBase {

	public abstract void transformPre(ItemStack stack, List<HazardEntry> entries);
	public abstract void transformPost(ItemStack stack, List<HazardEntry> entries);
}
