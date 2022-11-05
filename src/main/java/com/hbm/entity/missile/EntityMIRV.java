package com.hbm.entity.missile;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.entity.particle.EntitySmokeFX;

import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMIRV extends EntityThrowable {

	public EntityMIRV(World worldIn) {
		super(worldIn);
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
		
		this.motionY -= 0.03;
        
        this.rotation();
        
        if(this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ)).getBlock() != Blocks.AIR)
        {
    		if(!this.world.isRemote)
    		{
    	    	world.spawnEntity(EntityNukeExplosionMK4.statFac(world, BombConfig.mirvRadius, posX, posY, posZ));
    	    	int maxLifetime = (int)Math.max(100, 5 * 48 * (Math.pow(BombConfig.mirvRadius, 3)/Math.pow(48, 3)));
    			EntityNukeCloudSmall entity2 = new EntityNukeCloudSmall(this.world, maxLifetime, BombConfig.mirvRadius * 0.005F);
    	    	entity2.posX = this.posX;
    	    	entity2.posY = this.posY;
    	    	entity2.posZ = this.posZ;
    	    	this.world.spawnEntity(entity2);
    		}
    		this.setDead();
        }
        
        this.world.spawnEntity(new EntitySmokeFX(this.world, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0));
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
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 500000;
    }
	
	@Override
	protected void onImpact(RayTraceResult result) {
		
	}

}
