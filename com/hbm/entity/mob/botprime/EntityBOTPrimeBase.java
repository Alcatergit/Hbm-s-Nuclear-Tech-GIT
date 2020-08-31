package com.hbm.entity.mob.botprime;

import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class EntityBOTPrimeBase extends EntityWormBaseNT {

	public int attackCounter = 0;

	protected final Predicate<Entity> selector = ent -> {

		if(ent instanceof EntityWormBaseNT && ((EntityWormBaseNT) ent).getHeadID() == EntityBOTPrimeBase.this.getHeadID())
			return false;

		return true;
	};

	public EntityBOTPrimeBase(World world) {
		super(world);
		this.setSize(2.0F, 2.0F);
		this.isImmuneToFire = true;
		this.isAirBorne = true;
		this.noClip = true;
		this.dragInAir = 0.995F;
		this.dragInGround = 0.98F;
		this.knockbackDivider = 1.0D;
	}

	@Override
	public boolean canEntityBeSeen(Entity p_70685_1_) {
		return this.world.rayTraceBlocks(new Vec3d(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ), new Vec3d(p_70685_1_.posX, p_70685_1_.posY + (double)p_70685_1_.getEyeHeight(), p_70685_1_.posZ), false, true, false) == null;
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5000.0D);
	}
	
	@Override
	public boolean isAIDisabled() {
		return false;
	}
	
	@Override
	protected boolean canDespawn() {
		return false;
	}

}
