package com.hbm.entity.particle;

import com.hbm.entity.particle.ParticleContrail;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.World;

public class ParticleContrailBalefire extends ParticleContrail {

	public ParticleContrailBalefire(TextureManager manage, World worldIn, double posXIn, double posYIn, double posZIn) {
		super(manage, worldIn, posXIn, posYIn, posZIn, 0.15F, 0.2F, 0.1F, 1F);
		this.flameRed = 0.6F;
		this.flameGreen = 1F;
		this.flameBlue = 0.2F;
		this.doFlames = true;
	}
}
