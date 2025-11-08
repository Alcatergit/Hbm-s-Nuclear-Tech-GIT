package com.hbm.entity.projectile;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityChunky;
import com.hbm.entity.logic.EntityNukeExplosionMK5;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityRailgunBlast extends EntityChunky {

	public EntityRailgunBlast(World w) {
		super(w);
	}

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.setLocationAndAngles(posX + this.motionX, posY + this.motionY, posZ + this.motionZ, 0, 0);
		rotation();

		Vec3d vec3 = new Vec3d(this.posX, this.posY, this.posZ);
		Vec3d vec31 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		RayTraceResult movingobjectposition = this.world.rayTraceBlocks(vec3, vec31);

		if(movingobjectposition != null) {

			if(!this.world.isRemote) {
				this.setLocationAndAngles(movingobjectposition.getBlockPos().getX(), movingobjectposition.getBlockPos().getY(), movingobjectposition.getBlockPos().getZ(), 0, 0);
				
				world.spawnEntity(EntityNukeExplosionMK5.statFac(world, BombConfig.missileRadius>>1, posX, posY, posZ));
				if(BombConfig.enableNukeClouds) {
					EntityNukeTorex.statFac(world, posX, posY, posZ, BombConfig.missileRadius>>1);
				}
			}
			this.setDead();
			return;
		}

		// gravity needs the sec/tick converter squared since it's in seconds
		// squared
		motionY -= 9.81D * 0.05 * 0.05;
	}

	public void rotation() {
		float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

		for(this.rotationPitch = (float) (Math.atan2(this.motionY, f2) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
        }

		while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}
	}

	@Override
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 500000;
	}
}
