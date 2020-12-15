package com.hbm.entity.mob.botprime;

import java.util.List;

import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public abstract class EntityWormBaseNT extends EntityBurrowingNT {

	public int aggroCooldown = 0;
	public int courseChangeCooldown = 0;
	public double waypointX;
	public double waypointY;
	public double waypointZ;
	protected Entity targetedEntity = null;
	protected boolean canFly = false;
	protected int dmgCooldown = 0;
	protected boolean wasNearGround;
	protected BlockPos spawnPoint = new BlockPos(0, 0, 0);
	protected double attackRange;
	protected double maxSpeed;
	protected double fallSpeed;
	protected double rangeForParts;
	protected EntityLivingBase followed;
	protected int surfaceY;
	private int headID;
	private int partNum;
	protected boolean didCheck;
	protected double bodySpeed;
	protected double maxBodySpeed;
	protected double segmentDistance;
	protected double knockbackDivider;

	public static final Predicate<Entity> wormSelector = target -> {
			return target instanceof EntityWormBaseNT;
		};

	public EntityWormBaseNT(World world) {
		super(world);
		this.setSize(1.0F, 1.0F);
		this.surfaceY = 60;
	}
	
	public int getPartNumber() {
		return this.partNum;
	}

	public void setPartNumber(int num) {
		this.partNum = num;
	}

	public int getHeadID() {
		return this.headID;
	}

	public void setHeadID(int id) {
		this.headID = id;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(this.isEntityInvulnerable(source) || source == DamageSource.DROWN || source == DamageSource.IN_WALL || ((source.getImmediateSource() instanceof EntityWormBaseNT) && ((EntityWormBaseNT) source.getImmediateSource()).getHeadID() == this.getHeadID())) {
			return false;
		} else {
			this.setLastAttackedEntity(source.getTrueSource());
			return super.attackEntityFrom(source, amount);
		}
	}
	
	@Override
	public void onLivingUpdate() {
		if((!this.world.isRemote) && (this.world.getDifficulty() == EnumDifficulty.PEACEFUL)) {
			setDead();
		}
		if((this.targetedEntity != null) && (this.targetedEntity.isDead)) {
			this.targetedEntity = null;
		}
		/*if((getIsHead()) && (this.targetedEntity != null) && ((this.targetedEntity instanceof EntityPlayer))) {
			this.entityAge = 0;
		}*/
		if(this.posY < -10.0D) {
			setPositionAndUpdate(this.posX, 128.0D, this.posZ);
			this.motionY = 0.0D;
		} else if(this.posY < 3.0D) {
			this.motionY = 0.3D;
		}

		if(this.ticksExisted % 10 == 0) {
			attackEntitiesInList(this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(0.5D, 0.5D, 0.5D)));
		}
	}
	
	protected void attackEntitiesInList(List<Entity> targets) {

		for(Entity target : targets) {
			if(((target instanceof EntityLivingBase)) && (canAttackClass(((EntityLivingBase)target).getClass())) && ((!(target instanceof EntityWormBaseNT)) || (((EntityWormBaseNT) target).getHeadID() != this.getHeadID()))) {
				attackEntityAsMob(target);
			}
		}
	}
	
	@Override
	public boolean canAttackClass(Class<? extends EntityLivingBase> cls) {
		return true;
	}
	
	@Override
	public boolean attackEntityAsMob(Entity target) {
		boolean var2 = target.attackEntityFrom(DamageSource.causeMobDamage(this), getAttackStrength(target));

		if(var2) {
			this.idleTime = 0;
			double tx = (this.getEntityBoundingBox().minX + this.getEntityBoundingBox().maxX) / 2.0D;
			double tz = (this.getEntityBoundingBox().minZ + this.getEntityBoundingBox().maxZ) / 2.0D;
			double ty = (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0D;
			double deltaX = target.posX - tx;
			double deltaZ = target.posZ - tz;
			double deltaY = target.posY - ty;
			double knockback = this.knockbackDivider * (deltaX * deltaX + deltaZ * deltaZ + deltaY * deltaY + 0.1D);
			target.addVelocity(deltaX / knockback, deltaY / knockback, deltaZ / knockback);
		}

		return var2;
	}
	
	public abstract float getAttackStrength(Entity paramsa);
	
	@Override
	public void addVelocity(double x, double y, double z) {}
	
	@Override
	public void faceEntity(Entity entityIn, float maxYawIncrease, float maxPitchIncrease) {
	}
	
	protected boolean isCourseTraversable() {
		return (this.canFly) || (isEntityInsideOpaqueBlock());
	}
	
	@Override
	protected float getSoundVolume() {
		return 5.0F;
	}
	
	@Override
	public void setDead() {
		playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
		super.setDead();
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("wormID", this.getHeadID());
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		setHeadID(compound.getInteger("wormID"));
	}
}
