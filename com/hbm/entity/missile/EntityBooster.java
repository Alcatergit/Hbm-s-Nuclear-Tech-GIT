package com.hbm.entity.missile;

import com.hbm.entity.particle.EntityTSmokeFX;
import com.hbm.explosion.ExplosionLarge;

import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBooster extends EntityThrowable {

	public EntityBooster(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
	}
	
	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		
		this.motionY -= 0.015;
		
		if(motionY < -1.5F)
			motionY = -1.5F;
        
        this.rotation();
        
        if(this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ)).getBlock() != Blocks.AIR)
        {
    		if(!this.world.isRemote)
    		{
    	    	ExplosionLarge.explodeFire(world, posX, posY, posZ, 10F, true, false, true);
    		}
    		this.setDead();
        }
        
        if(!world.isRemote) {
			for(int i = 0; i < 2; i++) {
				EntityTSmokeFX fx1 = new EntityTSmokeFX(world);
				fx1.posY = posY - 0.25D;
				fx1.posX = posX + rand.nextGaussian() * 0.25D;
				fx1.posZ = posZ + rand.nextGaussian() * 0.25D;
				fx1.motionY = -0.2D;
				
				world.spawnEntity(fx1);
			}
        }

        this.motionX *= 0.995;
        this.motionZ *= 0.995;
	}
	
	protected void rotation() {
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
	protected void onImpact(RayTraceResult p_70184_1_) {
		
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 500000;
    }
}
