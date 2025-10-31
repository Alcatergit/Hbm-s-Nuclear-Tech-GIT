package com.hbm.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

public abstract class ParticleFirstPerson extends Particle {

	public ParticleFirstPerson(World worldIn, double posXIn, double posYIn, double posZIn) {
		super(worldIn, posXIn, posYIn, posZIn);
	}
	
	public abstract ParticleType getType();
	
	public static enum ParticleType {
		TAU,
		GLUON,
		CRUCIBLE;
	}

}
