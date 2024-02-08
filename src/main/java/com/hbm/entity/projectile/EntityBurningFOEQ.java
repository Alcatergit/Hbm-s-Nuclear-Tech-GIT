package com.hbm.entity.projectile;

import com.hbm.config.CompatibilityConfig;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.interfaces.IConstantRenderer;

import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBurningFOEQ extends EntityThrowable implements IConstantRenderer {
	
	public EntityBurningFOEQ(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		
	}
	
	@Override
	public void onUpdate() {
		this.lastTickPosX = this.prevPosX = posX;
		this.lastTickPosY = this.prevPosY = posY;
		this.lastTickPosZ = this.prevPosZ = posZ;
		this.setPosition(posX + this.motionX, posY + this.motionY, posZ + this.motionZ);
        
		if(motionY > -4)
			motionY -= 0.1;
		
        this.rotation();
        
        if(this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ)).getBlock() != Blocks.AIR)
        {
    		if(!this.world.isRemote && CompatibilityConfig.isWarDim(world)) {
    			for(int i = 0; i < 25; i++)
    				ExplosionLarge.explode(world, this.posX + 0.5F + rand.nextGaussian() * 5, this.posY + 0.5F + rand.nextGaussian() * 5, this.posZ + 0.5F + rand.nextGaussian() * 5, 10.0F, rand.nextBoolean(), false, false);
    			ExplosionNukeGeneric.waste(world, (int)this.posX, (int)this.posY, (int)this.posZ, 35);
    		}
    		this.setDead();
        }
	}
	
	public void rotation() {
        float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for (this.rotationPitch = (float)(Math.atan2(this.motionY, f2) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
        {
            ;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
        {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
        {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
        {
            this.prevRotationYaw += 360.0F;
        }
	}

    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 100000;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    @Override
	public float getBrightness()
    {
        return 1.0F;
    }

	@Override
	protected void onImpact(RayTraceResult result) {
	}
}