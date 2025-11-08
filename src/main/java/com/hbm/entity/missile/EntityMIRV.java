package com.hbm.entity.missile;

import api.hbm.entity.IRadarDetectable;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityChunky;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.interfaces.IConstantRenderer;

import net.minecraft.init.Blocks;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMIRV extends EntityChunky implements IConstantRenderer, IRadarDetectable {
	public static final DataParameter<Integer> HEALTH = EntityDataManager.createKey(EntityMIRV.class, DataSerializers.VARINT);
	public int health = 25;

    @Override
	public boolean attackEntityFrom(DamageSource damageSource, float dmg) {
        if(!this.isDead && !this.world.isRemote) {
            health -= (int) dmg;

            if(this.health <= 0) {
                this.setDead();
                this.killMissile();
            }
        }
        return true;
    }

	private void killMissile() {
		ExplosionLarge.explode(world, posX, posY, posZ, 5, true, false, true);
		ExplosionLarge.spawnShrapnelShower(world, posX, posY, posZ, motionX, motionY, motionZ, 15, 0.075);
	}
	public EntityMIRV(World worldIn) {
		super(worldIn);
		this.ignoreFrustumCheck = true;
	}
	@Override
	protected void entityInit() {
		super.entityInit();
		this.getDataManager().register(HEALTH, this.health);
	}

	@Override
	public void onUpdate() {
        super.onUpdate();
        this.getDataManager().set(HEALTH, this.health);
		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;

        this.motionY -= 0.00075;
        
        this.rotation();
        if(this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ)).getBlock() != Blocks.AIR) {
    		if(!this.world.isRemote) {
    	    	world.spawnEntity(EntityNukeExplosionMK5.statFac(world, BombConfig.mirvRadius, posX, posY, posZ));
    	    	if(BombConfig.enableNukeClouds) {
					EntityNukeTorex.statFac(world, posX, posY, posZ, BombConfig.mirvRadius);
				}
    		}
    		this.setDead();
        }
        if(this.world.isRemote && this.posY < 500) this.world.spawnEntity(new EntitySmokeFX(this.world, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0));
	}
	
	protected void rotation() {
        float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for(this.rotationPitch = (float)(Math.atan2(this.motionY, f2) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
        }
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 25000;
    }

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MIRVLET;
	}
}
