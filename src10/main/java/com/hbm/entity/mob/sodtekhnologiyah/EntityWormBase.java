package com.hbm.entity.mob.sodtekhnologiyah;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public abstract class EntityWormBase extends EntityBurrowing {

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
	private int uniqueWormID;
	private int partID;
	protected boolean didCheck;
	protected double bodySpeed;
	protected double maxBodySpeed;
	protected double segmentDistance;
	protected double knockbackDivider;
	protected int attackTick;

	public static final Predicate<Entity> wormSelector = target -> target instanceof EntityWormBase;


	public EntityWormBase(World world) {
		super(world);
		this.setSize(1.0F, 1.0F);
		this.surfaceY = 60;
	}

	public int getPartID() {
		return this.partID;
	}

	public void setPartID(int par1) {
		this.partID = par1;
	}

	public int getUniqueWormID() {
		return this.uniqueWormID;
	}

	public void setUniqueWormID(int id) {
		this.uniqueWormID = id;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		System.out.println(source);
		if(this.isEntityInvulnerable(source) || source == DamageSource.DROWN || source == DamageSource.IN_WALL ||
				((source.getImmediateSource() instanceof EntityWormBase) && ((EntityWormBase) source.getImmediateSource()).uniqueWormID == this.uniqueWormID)) {
			return false;
		} else {
			this.setLastAttackedEntity(source.getImmediateSource());
			return false;
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
		if((getIsHead()) && (this.targetedEntity != null) && ((this.targetedEntity instanceof EntityPlayer))) {
			this.idleTime = 0;
		}
		if(this.posY < -10.0D) {
			setPositionAndUpdate(this.posX, 128.0D, this.posZ);
			this.motionY = 0.0D;
		} else if(this.posY < 3.0D) {
			this.motionY = 0.3D;
		}
		this.attackTick = Math.max(this.attackTick - 1, 0);
		if(this.attackTick == 0) {
			this.attackTick = 10;

			attackEntitiesInList(this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().grow(0.5D, 0.5D, 0.5D), Predicates.and(EntitySelectors.NOT_SPECTATING, e -> e instanceof EntityLivingBase)));
		}
	}
	
	protected void attackEntitiesInList(List<Entity> targets) {

		for(Entity var3 : targets) {
			if(((var3 instanceof EntityLivingBase)) && (canAttackClass(((EntityLivingBase)var3).getClass()))
					&& ((!(var3 instanceof EntityWormBase)) || (((EntityWormBase) var3).getUniqueWormID() != getUniqueWormID()))) {
				attackEntityAsMob(var3);
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
			double ty = (this.getEntityBoundingBox().minZ + this.getEntityBoundingBox().maxZ) / 2.0D;
			double tz = (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0D;
			double deltaX = target.posX - tx;
			double deltaY = target.posZ - ty;
			double deltaZ = target.posY - tz;
			double knockback = this.knockbackDivider * (deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ + 0.1D);
			target.addVelocity(deltaX / knockback, deltaY / knockback, deltaZ / knockback);
		}

		return var2;
	}
	
	public abstract float getAttackStrength(Entity paramsa);
	
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
		compound.setInteger("wormID", this.getUniqueWormID());
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		setUniqueWormID(compound.getInteger("wormID"));
	}
}