package com.hbm.particle;

import org.lwjgl.opengl.GL20;

import com.hbm.handler.HbmShaderManager2;
import com.hbm.handler.LightningGenerator;
import com.hbm.handler.LightningGenerator.LightningGenInfo;
import com.hbm.handler.LightningGenerator.LightningNode;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.weapon.ItemRenderCrucible;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleCrucibleLightning extends ParticleFirstPerson {

	public LightningNode node;
	
	public ParticleCrucibleLightning(World worldIn, double posXIn, double posYIn, double posZIn) {
		super(worldIn, posXIn, posYIn, posZIn);
		LightningGenInfo i = new LightningGenInfo();
		i.randAmount = 0.03F;
		i.forkRandAmount = 5F;
		i.forkChance = 0.3F;
		i.forkLengthRandom = 2;
		i.forkConeDegrees = 10;
		Vec3d start = new Vec3d(posXIn, posYIn, posZIn);
		Vec3d end = start.add(0, 0, -1F);
		if(worldIn.rand.nextBoolean()){
			Vec3d tmp = start;
			start = end;
			end = tmp;
		}
		node = LightningGenerator.generateLightning(start, end, i);
	}
	
	@Override
	public int getFXLayer() {
		return 3;
	}
	
	public ParticleCrucibleLightning lifetime(int lifetime){
		this.particleMaxAge = lifetime;
		return this;
	}
	
	@Override
	public void onUpdate() {
		this.particleAge ++;
		if(particleAge > this.particleMaxAge){
			setExpired();
		}
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		ResourceManager.crucible_lightning.use();
		//Purposefully low frame rate for an artistic feel
		ResourceManager.crucible_lightning.uniform1f("time", (particleAge/2)*2);
		LightningGenerator.render(node, ItemRenderCrucible.playerPos, 0.0015F, 0, 0, 0, true, null);
		HbmShaderManager2.releaseShader();
	}
	
	@Override
	public ParticleType getType() {
		return ParticleType.CRUCIBLE;
	}

}
