package com.hbm.blocks.turret;

import com.hbm.entity.projectile.EntityBullet;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.tileentity.turret.TileEntityTurretTau;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TurretTau extends TurretBase {

	public TurretTau(Material materialIn, String s) {
		super(materialIn, s);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTurretTau();
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
		
		if(i != 0 && i % 4 == 0) {
			Vec3d vector = new Vec3d(
					-Math.sin(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI),
					-Math.sin(pitch / 180.0F * (float) Math.PI),
					Math.cos(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI));
			
			vector.normalize();
			
			if(!world.isRemote) {
				EntityBullet bullet = new EntityBullet(world);
				bullet.setIsCritical(true);
				
				bullet.posX = x + vector.x * 1 + 0.5;
				bullet.posY = y + vector.y * 1 + 1;
				bullet.posZ = z + vector.z * 1 + 0.5;
				
				bullet.motionX = vector.x * 3;
				bullet.motionY = vector.y * 3;
				bullet.motionZ = vector.z * 3;

				bullet.setDamage(10 + rand.nextInt(5));
				
				world.spawnEntity(bullet);
			}

			world.playSound(null, x + 0.5, y + 0.5, z + 0.5, HBMSoundHandler.tauShoot, SoundCategory.BLOCKS, 1.0F, 0.5F);
			
			flag = true;
		}
		
		return flag;
	}

	@Override
	public void executeReleaseAction(World world, int i, double yaw, double pitch, BlockPos pos) {}

}
