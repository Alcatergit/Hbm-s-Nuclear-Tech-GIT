package com.hbm.particle;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleManager {

	private static Random rand = new Random();
	
	public static void spawnParticles(double x, double y, double z, int count) {
		for (int i = 0; i < count; i++) {
			ParticleDSmokeFX fx = new ParticleDSmokeFX(Minecraft.getMinecraft().world, x, y, z, 0.0, 0.0, 0.0);
			// fx.posX = x;
			// fx.posY = y;
			// fx.posZ = z;
			double motionY = rand.nextGaussian() * (1 + (count / 50));
			double motionX = rand.nextGaussian() * (1 + (count / 150));
			double motionZ = rand.nextGaussian() * (1 + (count / 150));
			fx.setMotion(motionX, motionY, motionZ);
			Minecraft.getMinecraft().effectRenderer.addEffect(fx);
		}
	}
}
