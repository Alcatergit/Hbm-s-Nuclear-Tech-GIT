package com.hbm.particle;

import com.hbm.lib.RefStrings;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleMukeCloudBF extends ParticleMukeCloud {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/explosion_bf.png");
	
	public ParticleMukeCloudBF(World world, double x, double y, double z, double mx, double my, double mz) {
		super(world, x, y, z, mx, my, mz);
	}
	
	public static ResourceLocation getTexture() {
		return texture;
	}
}
