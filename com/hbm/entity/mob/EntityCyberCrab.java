package com.hbm.entity.mob;

import com.hbm.entity.projectile.EntityBullet;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityCyberCrab extends EntityMob implements IRangedAttackMob {

	private EntityAIAttackRanged aiArrowAttack = new EntityAIAttackRanged(this, 0.5D, 60, 80, 15.0F);
	
	public EntityCyberCrab(World worldIn) {
		super(worldIn);
		this.setSize(0.75F, 0.35F);
        this.tasks.addTask(0, new EntityAIPanic(this, 0.75D));
        this.tasks.addTask(1, new EntityAIWanderAvoidWater(this, 0.5F));
        //this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityPlayer.class, 3, 0.75D, 1.0D));
        this.tasks.addTask(4, this.aiArrowAttack);
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 3, true, false, null));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5F);
	}
	
	@Override
	public boolean isAIDisabled() {
		return false;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
        
        if(this.isInWater() || this.isWet() || this.isBurning())
        	this.attackEntityFrom(DamageSource.GENERIC, 10F);
        
        if(this.getHealth() <= 0) {
        	this.setDead();
        	world.createExplosion(this, this.posX, this.posY, this.posZ, 0.1F, true);
        }
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_CREEPER_HURT;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_CREEPER_DEATH;
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		return true;
	}
	
	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
		EntityBullet bullet = new EntityBullet(world, this, target, 1.6F, 2);
		bullet.setIsCritical(true);
		bullet.setTau(true);
		bullet.damage = 2;
        this.world.spawnEntity(bullet);
	}

	@Override
	public void setSwingingArms(boolean swingingArms) {
	}

}
