package com.hbm.hazard;

import java.util.ArrayList;
import java.util.List;

import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.hazard.type.HazardTypeBase;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class HazardEntry {

	HazardTypeBase type;
	float baseLevel;
	
	/*
	 * Modifiers are evaluated in the order they're being applied to the entry.
	 */
	List<HazardModifier> mods = new ArrayList();
	
	public HazardEntry(final HazardTypeBase type) {
		this(type, 1F);
	}
	
	public HazardEntry(final HazardTypeBase type, final float level) {
		this.type = type;
		this.baseLevel = level;
	}
	
	public HazardEntry addMod(final HazardModifier mod) {
		this.mods.add(mod);
		return this;
	}
	
	public void applyHazard(final ItemStack stack, final EntityLivingBase entity) {
		type.onUpdate(entity, HazardModifier.evalAllModifiers(stack, entity, baseLevel, mods), stack);
	}
	
	public HazardTypeBase getType() {
		return this.type;
	}
	
	public HazardEntry clone() {
		return clone(1F);
	}
	
	public HazardEntry clone(final float mult) {
		final HazardEntry clone = new HazardEntry(type, baseLevel * mult);
		clone.mods = this.mods;
		return clone;
	}
}
