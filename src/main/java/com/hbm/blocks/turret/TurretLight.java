package com.hbm.blocks.turret;

import com.hbm.entity.projectile.EntityBullet;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.tileentity.turret.TileEntityTurretLight;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TurretLight extends TurretBase {

	public TurretLight(Material materialIn, String s) {
		super(materialIn, s);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTurretLight();
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

		TileEntityTurretLight te = (TileEntityTurretLight)world.getTileEntity(pos);
		
		if(i == 0 && te.spin < 10)
			world.playSound(null, x + 0.5, y + 0.5, z + 0.5, HBMSoundHandler.ciwsSpinup, SoundCategory.BLOCKS, 1.0F, 1.0F);
		
		if(te.spin < 50)
			te.spin += 5;
		
		if(te.spin > 25 && i != 0 && i % 2 == 0) {
			Vec3d vector = new Vec3d(
					-Math.sin(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI),
					-Math.sin(pitch / 180.0F * (float) Math.PI),
					Math.cos(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI));
			
			vector.normalize();
			
			if(!world.isRemote) {
				EntityBullet bullet = new EntityBullet(world);
				bullet.posX = x + vector.x * 1.4 + 0.5;
				bullet.posY = y + vector.y * 1.4 + 0.87;
				bullet.posZ = z + vector.z * 1.4 + 0.5;
				
				bullet.motionX = vector.x * 3;
				bullet.motionY = vector.y * 3;
				bullet.motionZ = vector.z * 3;
				
				bullet.damage = rand.nextInt(2) + 2;
				
				world.spawnEntity(bullet);
			}

			world.playSound(null, x + 0.5, y + 0.5, z + 0.5, HBMSoundHandler.rifleShoot, SoundCategory.BLOCKS, 1.0F, 0.5F + rand.nextFloat() * 0.25F);
			
			flag = true;
		}
		
		return flag;
	}

	@Override
	public void executeReleaseAction(World world, int i, double yaw, double pitch, BlockPos pos) {
		TileEntityTurretLight te = (TileEntityTurretLight)world.getTileEntity(pos);
		
		if(te.spin > 10)
			world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.ciwsSpindown, SoundCategory.BLOCKS, 1.0F, 1.0F);
	}
}
