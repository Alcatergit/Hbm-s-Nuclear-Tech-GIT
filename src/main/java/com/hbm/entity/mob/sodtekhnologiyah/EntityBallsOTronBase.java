package com.hbm.entity.mob.sodtekhnologiyah;

import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class EntityBallsOTronBase extends EntityWormBase {

	public int attackCounter = 0;

	protected final Predicate<Entity> selector = ent -> {
			if(ent instanceof EntityWormBase && ((EntityWormBase)ent).getUniqueWormID() == EntityBallsOTronBase.this.getUniqueWormID())
				return false;

			return true;
		};
	
	public EntityBallsOTronBase(World world) {
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
	public boolean canEntityBeSeen(Entity entityIn) {
		return this.world.rayTraceBlocks(new Vec3d(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ), new Vec3d(entityIn.posX, entityIn.posY + (double)entityIn.getEyeHeight(), entityIn.posZ), false, true, false) == null;
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5000.0D);
	}

	@Override
	public void setPartID(int id) {

	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

}