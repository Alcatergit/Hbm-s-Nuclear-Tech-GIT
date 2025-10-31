package com.hbm.particle.bfg;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleBFGParticle extends Particle {

	float prevAlpha;
	Vec3 targetPoint;
	
	public ParticleBFGParticle(World worldIn, double posXIn, double posYIn, double posZIn) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.canCollide = false;
		this.particleScale = 2;
		this.particleMaxAge = 20;
		
		this.particleAlpha = 0F;
		this.prevAlpha = 0F;
		
	}
	
	public ParticleBFGParticle(World worldIn, double posXIn, double posYIn, double posZIn, double mX, double mY, double mZ) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.canCollide = false;
		this.particleScale = 2;
		this.particleMaxAge = 20;
		
		this.particleAlpha = 0F;
		this.prevAlpha = 0F;
		
		this.motionX = mX;
		this.motionY = mY;
		this.motionZ = mZ;
	}
	
	public ParticleBFGParticle(World worldIn, double posXIn, double posYIn, double posZIn, double mX, double mY, double mZ, Vec3 targetPoint) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.canCollide = false;
		this.particleScale = 2;
		this.particleMaxAge = 15;
		
		this.particleAlpha = 0F;
		this.prevAlpha = 0F;
		
		this.motionX = mX;
		this.motionY = mY;
		this.motionZ = mZ;
		this.targetPoint = targetPoint;
	}
	
	@Override
	public void onUpdate() {
		this.particleAge++;
		if(particleAge >= particleMaxAge)
			this.setExpired();
		float timeScale = this.particleAge/(float)this.particleMaxAge;
		
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		
		Vec3 attraction = null;
		if(targetPoint != null){
			attraction = targetPoint.subtract(Vec3.createVectorHelper(this.posX, this.posY, this.posZ)).normalize().mult(0.5F*timeScale);
		} else {
			attraction = Vec3.createVectorHelper(0, 0, 0);
		}
		
		this.posX += attraction.xCoord;
		this.posY += attraction.yCoord;
		this.posZ += attraction.zCoord;
		
		this.prevAlpha = particleAlpha;
		this.particleAlpha = MathHelper.clamp(1-BobMathUtil.remap((float)MathHelper.clamp(timeScale, 0.8, 1), 0.8F, 1F, 0F, 1.1F), 0, 1);
		this.particleAlpha *= MathHelper.clamp(BobMathUtil.remap((float)MathHelper.clamp(timeScale, 0, 0.2), 0F, 0.2F, 0F, 1.1F), 0, 1);
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
        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
        float alpha = prevAlpha + (particleAlpha-prevAlpha)*partialTicks;
        GlStateManager.color(0.5F, 1F, 0.5F, alpha);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.bfg_particle);
		Tessellator tes = Tessellator.getInstance();
        BufferBuilder buf = tes.getBuffer();
        
        
        float f = 0;
        float f1 = 1;
        float f2 = 0;
        float f3 = 1;
        float f4 = 0.1F * this.particleScale;

        Vec3d[] avec3d = new Vec3d[] {new Vec3d((double)(-rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(-rotationYZ * f4 - rotationXZ * f4)), new Vec3d((double)(-rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(-rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double)(rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double)(rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(rotationYZ * f4 - rotationXZ * f4))};

        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(avec3d[0].x, avec3d[0].y, avec3d[0].z).tex((double)f1, (double)f3).endVertex();
        buffer.pos(avec3d[1].x, avec3d[1].y, avec3d[1].z).tex((double)f1, (double)f2).endVertex();
        buffer.pos(avec3d[2].x, avec3d[2].y, avec3d[2].z).tex((double)f, (double)f2).endVertex();
        buffer.pos(avec3d[3].x, avec3d[3].y, avec3d[3].z).tex((double)f, (double)f3).endVertex();
        tes.draw();
        
        
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        
        GL11.glPopMatrix();
	}

}
