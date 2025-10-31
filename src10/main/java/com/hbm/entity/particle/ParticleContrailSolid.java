package com.hbm.entity.particle;

import com.hbm.entity.particle.ParticleContrail;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.World;

public class ParticleContrailSolid extends ParticleContrail {

	public ParticleContrailSolid(TextureManager manage, World worldIn, double posXIn, double posYIn, double posZIn) {
		super(manage, worldIn, posXIn, posYIn, posZIn, 0.98F, 0.8F, 0.47F, 1F);
		this.flameRed = 1F;
		this.flameGreen = 0.5F;
		this.flameBlue = 0F;
		this.doFlames = true;
	}
}
