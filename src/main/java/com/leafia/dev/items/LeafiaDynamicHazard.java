package com.leafia.dev.items;

import com.hbm.modules.ItemHazardModule;
import net.minecraft.item.ItemStack;

public interface LeafiaDynamicHazard {
	ItemHazardModule getHazards(ItemHazardModule hazards,ItemStack stack);
}
