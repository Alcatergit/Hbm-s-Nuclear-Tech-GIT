package com.hbm.blocks.turret;

import com.hbm.entity.projectile.EntityBullet;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.tileentity.turret.TileEntityTurretCheapo;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TurretCheapo extends TurretBase {

	public TurretCheapo(Material materialIn, String s) {
		super(materialIn, s);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTurretCheapo();
	}

	@Override
	public boolean executeHoldAction(World world, int i, double yaw, double pitch, BlockPos pos) {
		boolean flag = false;
		if (pitch < -30)
			pitch = -30;
		if (pitch > 15)
			pitch = 15;

		TileEntityTurretCheapo te = (TileEntityTurretCheapo) world.getTileEntity(pos);

		if (i == 0 && te.spin < 10)
			world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.weaponSpinUp, SoundCategory.BLOCKS, 1.0F, 1.0F);

		if (te.spin < 35)
			te.spin += 5;

		if (te.spin > 25 && i % 2 == 0) {
			Vec3d vector = new Vec3d(-Math.sin(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI), -Math.sin(pitch / 180.0F * (float) Math.PI), Math.cos(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI));

			vector.normalize();

			if (!world.isRemote) {
				EntityBullet bullet = new EntityBullet(world);
				bullet.posX = pos.getX() + vector.x * 1.5 + 0.5;
				bullet.posY = pos.getY() + vector.y * 1.5 + 1.5;
				bullet.posZ = pos.getZ() + vector.z * 1.5 + 0.5;

				bullet.motionX = vector.x * 3;
				bullet.motionY = vector.y * 3;
				bullet.motionZ = vector.z * 3;

				bullet.damage = rand.nextInt(3) + 3;
				world.spawnEntity(bullet);
			}
			world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.sawShoot, SoundCategory.BLOCKS, 3.0F, 1.0F);

			flag = true;
		}

		return flag;
	}

	@Override
	public void executeReleaseAction(World world, int i, double yaw, double pitch, BlockPos pos) {
		TileEntityTurretCheapo te = (TileEntityTurretCheapo) world.getTileEntity(pos);

		if (te.spin > 10)
			world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.weaponSpinDown, SoundCategory.BLOCKS, 1.0F, 1.0F);

	}

}
