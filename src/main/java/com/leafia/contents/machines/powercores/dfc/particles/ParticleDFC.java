package com.leafia.contents.machines.powercores.dfc.particles;

import com.hbm.main.ResourceManager;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class ParticleDFC extends Particle {

	public ParticleDFC(World worldIn,double posXIn,double posYIn,double posZIn,float red,float green,float blue){
		super(worldIn, posXIn, posYIn, posZIn);
		this.motionX = (double)(this.rand.nextFloat() - 0.5F)*3;
		this.motionY = (double)(this.rand.nextFloat() - 0.5F)*3;
		this.motionZ = (double)(this.rand.nextFloat() - 0.5F)*3;
		particleRed = red;
		particleGreen = green;
		particleBlue = blue;
		this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
		this.canCollide = false;
	}

	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.particleAge++ >= this.particleMaxAge)
		{
			this.setExpired();
		}

		float f = (float)this.particleAge / (float)this.particleMaxAge;

		posX += motionX;
		posY += motionY;
		posZ += motionZ;
	}
	
	@Override
	public int getFXLayer(){
		return 3;
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ){
		com.hbm.render.RenderHelper.resetParticleInterpPos(entityIn, partialTicks);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.white);

		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);
		GlStateManager.depthMask(false);
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		LeafiaGls.pushMatrix();
		LeafiaGls.color(particleRed,particleGreen,particleBlue);
		float pX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - interpPosX);
		float pY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - interpPosY);
		float pZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - interpPosZ);
		LeafiaGls.translate(pX,pY,pZ);
		LeafiaGls.scale(0.2);
		ResourceManager.sphere_ruv.renderAll();
		LeafiaGls.popMatrix();
		LeafiaGls.color(1,1,1);

		GlStateManager.enableLighting();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
	}

}
