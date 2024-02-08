package com.hbm.entity.grenade;

import org.apache.logging.log4j.Level;

import com.hbm.config.CompatibilityConfig;
import com.hbm.config.GeneralConfig;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class EntityGrenadeBase extends EntityThrowable {


    public EntityGrenadeBase(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeBase(World p_i1774_1_, EntityLivingBase p_i1774_2_, EnumHand hand)
    {
    	super(p_i1774_1_);
        this.thrower = p_i1774_2_;
        this.setSize(0.25F, 0.25F);
        this.setLocationAndAngles(p_i1774_2_.posX, p_i1774_2_.posY + (double)p_i1774_2_.getEyeHeight(), p_i1774_2_.posZ, p_i1774_2_.rotationYaw, p_i1774_2_.rotationPitch);
        if(hand == EnumHand.MAIN_HAND){
        	this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
            this.posY -= 0.10000000149011612D;
            this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        } else {
        	this.posX += (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
            this.posY -= 0.10000000149011612D;
            this.posZ += (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        }
        
        this.setPosition(this.posX, this.posY, this.posZ);
        float f = 0.4F;
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        this.motionY = (double)(-MathHelper.sin((this.rotationPitch) / 180.0F * (float)Math.PI) * f);
        this.shoot(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F);
    }

    public EntityGrenadeBase(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }
    
    @Override
    public void onUpdate() {
    	super.onUpdate();
    	
    	this.prevRotationPitch = this.rotationPitch;
        
        this.rotationPitch -= new Vec3d(motionX, motionY, motionZ).lengthVector() * 25;
        
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
        {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
        {
            this.prevRotationYaw += 360.0F;
        }

        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
    }

    @Override
	protected void onImpact(RayTraceResult p_70184_1_) {
        if(!CompatibilityConfig.isWarDim(world)){
            this.setDead();
            return;
        }
        if (p_70184_1_.entityHit != null)
        {
            byte b0 = 0;

            p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), b0);
        }
        
        if(!world.isRemote) {
    		if(GeneralConfig.enableExtendedLogging) {
    			
    			String s = "null";
    			
    			if(getThrower() != null && getThrower() instanceof EntityPlayer)
    				s = ((EntityPlayer)getThrower()).getDisplayName().getUnformattedText();
    			
    			MainRegistry.logger.log(Level.INFO, "[GREN] Set off grenade at " + ((int)posX) + " / " + ((int)posY) + " / " + ((int)posZ) + " by " + s + "!");
    		}
        }
        
        this.explode();
    }
    
    public abstract void explode();
}
