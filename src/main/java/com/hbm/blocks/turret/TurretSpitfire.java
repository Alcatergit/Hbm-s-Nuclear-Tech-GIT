package com.hbm.blocks.turret;

import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.entity.projectile.EntityAAShell;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.tileentity.turret.TileEntityTurretSpitfire;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TurretSpitfire extends TurretBase {

	public TurretSpitfire(Material materialIn, String s) {
		super(materialIn, s);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTurretSpitfire();
	}

	@Override
	public boolean executeHoldAction(World world, int i, double yaw, double pitch, BlockPos pos) {
		boolean flag = false;
		
		if(pitch < -60)
			pitch = -60;
		if(pitch > 30)
			pitch = 30;
		
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		if(i != 0 && i % 35 == 0) {
			Vec3d vector = new Vec3d(
					-Math.sin(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI),
					-Math.sin(pitch / 180.0F * (float) Math.PI),
					Math.cos(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI));
			
			vector.normalize();
			
			if(!world.isRemote) {
				EntityAAShell bullet = new EntityAAShell(world);
				bullet.posX = x + vector.x * 2.75 + 0.5;
				bullet.posY = y + vector.y * 2.75 + 1.5;
				bullet.posZ = z + vector.z * 2.75 + 0.5;
				
				bullet.motionX = vector.x;
				bullet.motionY = vector.y;
				bullet.motionZ = vector.z;
				
				bullet.speedOverride = 3;
				
				bullet.rotation();
				
				world.spawnEntity(bullet);
				
				EntityGasFlameFX smoke = new EntityGasFlameFX(world);
				smoke.posX = x + vector.z * 4 + 0.5;
				smoke.posY = y + vector.y * 4 + 1;
				smoke.posZ = z + vector.z * 4 + 0.5;
				
				smoke.motionX = vector.x * 0.25;
				smoke.motionY = vector.y * 0.25;
				smoke.motionZ = vector.z * 0.25;
				
				world.spawnEntity(smoke);
			}

			world.playSound(null, x + 0.5, y + 0.5, z + 0.5, HBMSoundHandler.oldExplosion, SoundCategory.BLOCKS, 1.0F, 0.5F);
			
			flag = true;
		}
		
		return flag;
	}

	@Override
	public void executeReleaseAction(World world, int i, double yaw, double pitch, BlockPos pos) {}

}
