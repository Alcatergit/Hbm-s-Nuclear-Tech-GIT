package com.hbm.explosion;

import java.util.Random;

import com.hbm.entity.particle.EntityDSmokeFX;

import net.minecraft.world.World;

public class ExplosionLarge {

	static Random rand = new Random();

	public static void spawnParticles(World world, double x, double y, double z, int count) {
		
		for(int i = 0; i < count; i++) {
			EntityDSmokeFX fx = new EntityDSmokeFX(world, x, y, z, 0.0, 0.0, 0.0);
			//fx.posX = x;
			//fx.posY = y;
			//fx.posZ = z;
			fx.motionY = rand.nextGaussian() * (1 + (count / 50));
			fx.motionX = rand.nextGaussian() * (1 + (count / 150));
			fx.motionZ = rand.nextGaussian() * (1 + (count / 150));
			world.spawnEntity(fx);
		}
	}
}
