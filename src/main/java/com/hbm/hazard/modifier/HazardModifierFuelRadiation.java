package com.hbm.hazard.modifier;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class HazardModifierFuelRadiation extends HazardModifier {
	
float target;
	
	public HazardModifierFuelRadiation(final float target) {
		this.target = target;
	}

	@Override
	public float modify(final ItemStack stack, final EntityLivingBase holder, float level) {
		final double depletion = Math.pow(stack.getItem().getDurabilityForDisplay(stack), 0.4D);
		level = (float) (level + (this.target - level) * depletion);
		
		return level;
	}
}
