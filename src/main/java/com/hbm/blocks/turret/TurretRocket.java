package com.hbm.blocks.turret;

import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.entity.projectile.EntityRocket;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.tileentity.turret.TileEntityTurretRocket;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TurretRocket extends TurretBase {

	public TurretRocket(Material materialIn, String s) {
		super(materialIn, s);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTurretRocket();
	}

	@Override
	public boolean executeHoldAction(World world, int i, double yaw, double pitch, BlockPos pos) {
		boolean flag = false;
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		if(pitch < -60)
			pitch = -60;
		if(pitch > 30)
			pitch = 30;
		
		if(i != 0 && (i % 100 == 60 || i % 100 == 70 || i % 100 == 80 || i % 100 == 90)) {
			Vec3d vector = new Vec3d(
					-Math.sin(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI),
					-Math.sin(pitch / 180.0F * (float) Math.PI),
					Math.cos(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI));
			
			vector.normalize();
			
			if(!world.isRemote) {
				EntityRocket bullet = new EntityRocket(world);
				bullet.posX = x + vector.x * 1 + 0.5;
				bullet.posY = y + vector.y * 1 + 1;
				bullet.posZ = z + vector.z * 1 + 0.5;
				
				bullet.motionX = vector.x * 3;
				bullet.motionY = vector.y * 3;
				bullet.motionZ = vector.z * 3;
				
				world.spawnEntity(bullet);
				
				EntityGasFlameFX fx = new EntityGasFlameFX(world);
				fx.posX = x + vector.x * 1 + 0.5;
				fx.posY = y + vector.y * 1 + 1;
				fx.posZ = z + vector.z * 1 + 0.5;
				world.spawnEntity(fx);
			}

			world.playSound(null, x + 0.5, y + 0.5, z + 0.5, HBMSoundHandler.rpgShoot, SoundCategory.BLOCKS, 1.0F, 0.75F);
			
			flag = true;
		}
		
		return flag;
	}

	@Override
	public void executeReleaseAction(World world, int i, double yaw, double pitch, BlockPos pos) {}

}
