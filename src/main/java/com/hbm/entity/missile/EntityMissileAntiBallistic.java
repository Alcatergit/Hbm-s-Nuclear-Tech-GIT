package com.hbm.entity.missile;

import java.util.List;

import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionLarge;

import api.hbm.entity.IRadarDetectable;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMissileAntiBallistic extends Entity implements IRadarDetectable {

	int activationTimer;
	public double prevPosX2;
    public double prevPosY2;
    public double prevPosZ2;

	public EntityMissileAntiBallistic(World p_i1582_1_) {
		super(p_i1582_1_);
	}
	
	@Override
    public void onUpdate() {
		
		this.prevPosX2 = this.posX;
        this.prevPosY2 = this.posY;
        this.prevPosZ2 = this.posZ;
		if(activationTimer < 40) {
			activationTimer++;
			
			motionY = 1.5D;

			this.setLocationAndAngles(posX + this.motionX, posY + this.motionY, posZ + this.motionZ, 0, 0);
	        this.rotation();
	
			if(!this.world.isRemote)
				this.world.spawnEntity(new EntitySmokeFX(this.world, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0));
			
		} else {
			
			if(activationTimer == 40) {
				ExplosionLarge.spawnParticlesRadial(world, posX, posY, posZ, 15);
				activationTimer = 100;
			}

			for(int i = 0; i < 5; i++) {

				targetMissile();

				this.setLocationAndAngles(posX + this.motionX, posY + this.motionY, posZ + this.motionZ, 0, 0);
		        this.rotation();
		    	
				if(!this.world.isRemote)
					this.world.spawnEntity(new EntitySmokeFX(this.world, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0));

				List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(posX - 5, posY - 5, posZ - 5, posX + 5, posY + 5, posZ + 5));

				for(Entity e : list) {
					if(e instanceof EntityMissileBaseAdvanced || e instanceof EntityMissileCustom) {
						ExplosionLarge.explode(world, posX, posY, posZ, 15F, true, false, true);
						this.setDead();
						return;
					}
				}
			}
		}

		BlockPos pos = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        if(this.world.getBlockState(pos).getBlock() != Blocks.AIR && 
    			this.world.getBlockState(pos).getBlock() != Blocks.WATER && 
    			this.world.getBlockState(pos).getBlock() != Blocks.FLOWING_WATER) {
    	
			if(!this.world.isRemote)
			{
				ExplosionLarge.explode(world, posX, posY, posZ, 10F, true, true, true);
			}
			this.setDead();
			return;
    	}

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
	
	private void targetMissile() {
		
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(posX - 500, 0, posZ - 500, posX + 500, 5000, posZ + 500));
		
		Entity target = null;
		double closest = 1000D;
		
		for(Entity e : list) {
			if(e instanceof EntityMissileBaseAdvanced || e instanceof EntityMissileCustom) {
				double dis = Math.sqrt(Math.pow(e.posX - posX, 2) + Math.pow(e.posY - posY, 2) + Math.pow(e.posZ - posZ, 2));
				
				if(dis < closest) {
					closest = dis;
					target = e;
				}
			}
		}
		
		if(target != null) {
			
			Vec3d vec = new Vec3d(target.posX - posX, target.posY - posY, target.posZ - posZ);

			vec.normalize();
			
			this.motionX = vec.x * 0.065D;
			this.motionY = vec.y * 0.065D;
			this.motionZ = vec.z * 0.065D;
		}
	}

	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 500000;
    }

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MISSILE_AB;
	}
}
