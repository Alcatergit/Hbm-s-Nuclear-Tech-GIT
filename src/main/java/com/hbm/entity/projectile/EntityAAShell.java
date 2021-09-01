package com.hbm.entity.projectile;

import java.util.List;

import com.hbm.explosion.ExplosionLarge;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityAAShell extends Entity {

	public int speedOverride = 1;
	public int fuse = 5;
	public int dFuse = 30;

	public EntityAAShell(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void entityInit() {

	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {

	}

	@Override
	public void onUpdate() {
		if(fuse > 0)
			fuse--;

		if(dFuse > 0) {
			dFuse--;
		} else {
			explode();
			return;
		}

		this.lastTickPosX = this.prevPosX = this.posX;
		this.lastTickPosY = this.prevPosY = this.posY;
		this.lastTickPosZ = this.prevPosZ = this.posZ;

		for(int i = 0; i < 5; i++) {

			this.setPosition(posX + this.motionX, posY + this.motionY, posZ + this.motionZ);

			rotation();

			if(fuse == 0) {
				List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(this.posX - 5, this.posY - 5, this.posZ - 5, this.posX + 5, this.posY + 5, this.posZ + 5));
				for(Entity e : list) {
					float size = e.width * e.width * e.height;
					if(size >= 0.5) {
						explode();
						return;
					}

				}
			}

			if(world.getBlockState(new BlockPos((int) posX, (int) posY, (int) posZ)).getMaterial() != Material.AIR) {
				explode();
				return;
			}
		}
	}

	public void rotation() {
		float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

		for(this.rotationPitch = (float) (Math.atan2(this.motionY, f2) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
			;
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

	private void explode() {
		if(!world.isRemote) {
			world.createExplosion(null, posX, posY, posZ, 20, true);
			ExplosionLarge.spawnParticlesRadial(world, posX, posY, posZ, 35);
			if(rand.nextInt(15) == 0)
				ExplosionLarge.spawnShrapnels(world, posX, posY, posZ, 5 + rand.nextInt(11));
			else if(rand.nextInt(15) == 0)
				ExplosionLarge.spawnShrapnelShower(world, posX, posY, posZ, motionX * 2, motionY * 2, motionZ * 2, 5 + rand.nextInt(11), 0.15);
			this.setDead();
		}
	}
}
