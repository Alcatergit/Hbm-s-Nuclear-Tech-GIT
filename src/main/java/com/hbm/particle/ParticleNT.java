package com.hbm.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleNT extends Particle {

	private ParticleDefinition definition;
	
	protected ParticleNT(World worldIn, double posXIn, double posYIn, double posZIn, ParticleDefinition definition) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.definition = definition;
	}
	
	public ParticleNT(World world, double x, double y, double z, double moX, double moY, double moZ, ParticleDefinition definition) {
		this(world, x, y, z, definition);
		this.motionX = moX;
		this.motionY = moY;
		this.motionZ = moZ;
	}

}
