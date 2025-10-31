package com.hbm.entity.mob;

import com.hbm.lib.HBMSoundEvents;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityDuck extends EntityChicken {

	public EntityDuck(World worldIn) {
		super(worldIn);
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return HBMSoundEvents.ducc;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return HBMSoundEvents.ducc;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return HBMSoundEvents.ducc;
	}
	
	@Override
	public EntityChicken createChild(EntityAgeable ageable) {
		return new EntityDuck(this.world);
	}
	
	@Override
	protected boolean canDespawn() {
		return false;
	}
	
}
