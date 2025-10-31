package com.hbm.particle.bfg;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleBFGShockwave extends Particle {

	float prevScale;
	float prevAlpha;
	float speed;
	float scaleSpeed;
	float resistance;
	
	public ParticleBFGShockwave(World worldIn, double posXIn, double posYIn, double posZIn, float speed, int age, float scaleSpeed, float resistance) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.canCollide = false;
		this.particleMaxAge = age;
		
		this.particleScale = 0F;
		this.prevScale = 0f;
		
		this.particleAlpha = 0F;
		this.prevAlpha = 0F;
		
		this.speed = speed;
		this.scaleSpeed = scaleSpeed;
		this.resistance = resistance;
	}
	
	@Override
	public void onUpdate() {
		this.particleAge++;
		if(particleAge >= particleMaxAge)
			this.setExpired();
		float timeScale = this.particleAge/(float)this.particleMaxAge;
		
		//this.prevScale = this.particleScale;
		//this.particleScale += 0.5;
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.posZ -= speed;
		
		speed *= resistance;
		
		this.prevScale = this.particleScale;
		this.particleScale += scaleSpeed;
		
		this.prevAlpha = particleAlpha;
		this.particleAlpha = MathHelper.clamp(1-BobMathUtil.remap((float)MathHelper.clamp(timeScale, 0.4, 1), 0.4F, 1F, 0F, 1.1F), 0, 1);
		this.particleAlpha *= MathHelper.clamp(BobMathUtil.remap((float)MathHelper.clamp(timeScale, 0, 0.2), 0F, 0.2F, 0F, 1.1F), 0, 1);
		this.particleAlpha *= 0.5;
	}
	
	@Override
	public int getFXLayer() {
		return 3;
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		GL11.glPushMatrix();
		
		double d0 = this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks;
		double d1 = this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks;
		double d2 = this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks;
		
		double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
		double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
		double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;

		GL11.glTranslated(d0 - d3, d1 - d4, d2 - d5);
		
		GlStateManager.disableCull();
        GlStateManager.disableAlpha();
        GlStateManager.depthMask(false);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
        float alpha = prevAlpha + (particleAlpha-prevAlpha)*partialTicks;
        GlStateManager.color(0.5F, 1F, 0.5F, alpha);

        float scale = this.prevScale + (this.particleScale-this.prevScale)*partialTicks;
        GL11.glTranslated(0, 0, -1.5);
        GL11.glScaled(scale, scale, scale);
        GL11.glTranslated(0, 0, 1.5);
        GL11.glRotated(-90, 1, 0, 0);
        
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.fresnel_ms);
        ResourceManager.hemisphere_uv.renderAll();
        
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        
        GL11.glPopMatrix();
	}

}
