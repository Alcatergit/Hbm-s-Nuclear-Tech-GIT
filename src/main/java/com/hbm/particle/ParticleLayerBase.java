package com.hbm.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

public abstract class ParticleLayerBase extends Particle {
	
	public ParticleLayerBase(World worldIn, double posXIn, double posYIn, double posZIn) {
		super(worldIn, posXIn, posYIn, posZIn);
	}

	public abstract ParticleRenderLayer getRenderLayer();
}
