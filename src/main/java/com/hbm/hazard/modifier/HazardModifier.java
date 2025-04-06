package com.hbm.hazard.modifier;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public abstract class HazardModifier {

	public abstract float modify(ItemStack stack, EntityLivingBase holder, float level);
	
	/**
	 * Returns the level after applying all modifiers to it, in order.
	 * @param stack
	 * @param entity nullable
	 * @param level
	 * @param mods
	 * @return
	 */
	public static float evalAllModifiers(final ItemStack stack, final EntityLivingBase entity, float level, final List<HazardModifier> mods) {
		
		for(final HazardModifier mod : mods) {
			level = mod.modify(stack, entity, level);
		}
		
		return level;
	}
}
