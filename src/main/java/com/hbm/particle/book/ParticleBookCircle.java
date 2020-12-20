package com.hbm.particle.book;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.RenderHelper;
import com.hbm.tileentity.machine.TileEntityBlackBook;

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

public class ParticleBookCircle extends Particle {

	public TileEntityBlackBook te;
	
	public ParticleBookCircle(TileEntityBlackBook te, double posXIn, double posYIn, double posZIn, float scale) {
		super(te.getWorld(), posXIn, posYIn, posZIn);
		this.te = te;
		this.particleScale = scale;
		this.particleMaxAge = 100;
	}

	@Override
	public void onUpdate() {
		this.particleAge++;
		if(this.particleAge >= this.particleMaxAge){
			this.setExpired();
			return;
		}
		if(te.end){
			this.particleAge = Math.max(particleAge, particleMaxAge - 20);
		}
	}
	
	@Override
	public int getFXLayer() {
		return 3;
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		GL11.glPushMatrix();
		GlStateManager.disableLighting();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
		GlStateManager.enableBlend();
		GlStateManager.disableCull();
		GlStateManager.disableAlpha();
		GlStateManager.depthMask(false);
		GlStateManager.enablePolygonOffset();
		GlStateManager.doPolygonOffset(-1, -10);
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.SRC_COLOR);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.circle_big);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		float lifeN = (float)(particleAge+partialTicks)/(float)particleMaxAge;
		float fade = MathHelper.clamp(1F-lifeN*1.2F, 0, 1);
		GlStateManager.color(1F, 0.1F, 0.1F, 1.2F*fade);
		
        double entPosX = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX)*partialTicks;
        double entPosY = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY)*partialTicks;
        double entPosZ = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ)*partialTicks;
        
        interpPosX = entPosX;
        interpPosY = entPosY;
        interpPosZ = entPosZ;
        
		float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - entPosX);
        float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - entPosY);
        float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - entPosZ);
        GL11.glTranslated(f5, f6, f7);
        
        float scale = particleScale;
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(-0.5*scale, 0, -0.5*scale).tex(0, 0).endVertex();
		buffer.pos(0.5*scale, 0, -0.5*scale).tex(1, 0).endVertex();
		buffer.pos(0.5*scale, 0, 0.5*scale).tex(1, 1).endVertex();
		buffer.pos(-0.5*scale, 0, 0.5*scale).tex(0, 1).endVertex();
		Tessellator.getInstance().draw();
		
		GlStateManager.disablePolygonOffset();
		RenderHelper.resetColor();
		GlStateManager.enableCull();
		GlStateManager.enableAlpha();
		GlStateManager.depthMask(true);
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GL11.glPopMatrix();
	}
}
