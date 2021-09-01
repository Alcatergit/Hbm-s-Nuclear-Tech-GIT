package com.hbm.entity.mob.sodtekhnologiyah;

import com.hbm.entity.mob.EntityAINearestAttackableTargetNT;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityBallsOTronHead extends EntityBallsOTronBase {

	private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS));
	
	/*   ___   _   _    _    ___           ___           _____ ___  ___ _  _
	 *  | _ ) /_\ | |  | |  / __|   ___   |   |   ___   |_   _| _ )|   | \| |
	 *  | _ \/ _ \| |__| |__\__ \  |___|  | | |  |___|    | | |   \| | |    |
	 *  |___/_/ \_\____|____|___/         |___|           |_| |_|\_\___|_|\_|
	 */
	
	private final WormMovementHead movement = new WormMovementHead(this);
	
	public EntityBallsOTronHead(World world) {
		super(world);
		this.experienceValue = 1000;
		this.wasNearGround = false;
		this.attackRange = 150.0D;
		this.setSize(3.0F, 3.0F);
		this.maxSpeed = 1.0D;
		this.fallSpeed = 0.006D;
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTargetNT(this, EntityPlayer.class, 0, false, false, null, 128.0D));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTargetNT(this, Entity.class, 0, false, false, this.selector, 50.0D));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15D);
	}
	
	@Override
	public boolean getIsHead() {
		return true;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(super.attackEntityFrom(source, amount)) {
			this.dmgCooldown = 4;
			return true;
		}

		return false;
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		/*setUniqueWormID(this.rand.nextInt(4096));

    	int x = MathHelper.floor(this.posX);
        int y = MathHelper.floor(this.posY);
        int z = MathHelper.floor(this.posZ);

        for (int o = 0; o < 119; o++) {

          EntityBallsOTronSegment bodyPart = new EntityBallsOTronSegment(this.world);
          bodyPart.setPartID(o);
          bodyPart.setPosition(x, y, z);
          bodyPart.setUniqueWormID(getUniqueWormID());
          this.world.spawnEntity(bodyPart);
        }

        setPosition(x, y, z);
        this.spawnPoint = new BlockPos(x, y, z);

        this.aggroCooldown = 60;
        return super.onInitialSpawn(difficulty, livingdata);*/
		//TODO: unlock this

    	this.setDead();

    	return livingdata;
	}
	
	@Override
	protected void updateAITasks() {
		super.updateAITasks();

	    this.movement.updateMovement();

	    if ((getHealth() < getMaxHealth()) && (this.ticksExisted % 6 == 0)) {
	      if (this.targetedEntity != null) {
	        heal(1.0F);
	      } else if (this.recentlyHit == 0) {
	        heal(4.0F);
	      }
	    }
	    if ((this.targetedEntity != null) && (this.targetedEntity.getDistanceSq(this) < this.attackRange * this.attackRange))
	    {
	      if (canEntityBeSeen(this.targetedEntity))
	      {
	        this.attackCounter += 1;
	        if (this.attackCounter == 10)
	        {
	          //useLaser(this.targetedEntity, true);

	          this.attackCounter = -20;
	        }
	      }
	      else if (this.attackCounter > 0)
	      {
	        this.attackCounter -= 1;
	      }
	    }
	    else if (this.attackCounter > 0) {
	      this.attackCounter -= 1;
	    }
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
	}

	@Override
	public void addTrackingPlayer(EntityPlayerMP player) {
		super.addTrackingPlayer(player);
		bossInfo.addPlayer(player);
	}
	
	@Override
	public void removeTrackingPlayer(EntityPlayerMP player) {
		super.removeTrackingPlayer(player);
		bossInfo.removePlayer(player);
	}
	
	@Override
	public float getAttackStrength(Entity target) {
		if(target instanceof EntityLivingBase) {
			return ((EntityLivingBase) target).getHealth() * 0.75F;
		}

		return 100;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("AggroCD", this.aggroCooldown);
		compound.setInteger("CenterX", this.spawnPoint.getX());
		compound.setInteger("CenterY", this.spawnPoint.getY());
		compound.setInteger("CenterZ", this.spawnPoint.getZ());
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
	    this.aggroCooldown = compound.getInteger("AggroCD");
	    this.spawnPoint = new BlockPos(compound.getInteger("CenterX"), compound.getInteger("CenterY"), compound.getInteger("CenterZ"));
	}

}