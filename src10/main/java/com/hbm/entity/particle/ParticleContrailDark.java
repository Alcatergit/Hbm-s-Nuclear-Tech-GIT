package com.hbm.entity.particle;

import com.hbm.entity.particle.ParticleContrail;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.World;

public class ParticleContrailDark extends ParticleContrail {

	public ParticleContrailDark(TextureManager manage, World worldIn, double posXIn, double posYIn, double posZIn) {
		super(manage, worldIn, posXIn, posYIn, posZIn, 0.4F, 0.4F, 0.4F, 1F);
		this.flameRed = 1F;
		this.flameGreen = 0.75F;
		this.flameBlue = 0F;
		this.doFlames = true;
	}
}
