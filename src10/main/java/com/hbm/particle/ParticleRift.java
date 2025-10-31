package com.hbm.particle;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleRift extends Particle {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/hadron.png");
	
	public ParticleRift(World worldIn, double posXIn, double posYIn, double posZIn){
		super(worldIn, posXIn, posYIn, posZIn);
		this.particleMaxAge = 10;
	}

	@Override
	public int getFXLayer(){
		return 3;
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ){
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		com.hbm.render.RenderHelper.resetParticleInterpPos(entityIn, partialTicks);
		
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0);
		GlStateManager.depthMask(false);
		GlStateManager.blendFunc(SourceFactor.ONE_MINUS_DST_COLOR, DestFactor.ONE_MINUS_SRC_COLOR);
		RenderHelper.disableStandardItemLighting();
		
		float pX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - interpPosX);
		float pY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - interpPosY);
		float pZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - interpPosZ);
		
		float scale = (this.particleAge + partialTicks) * 0.5F;
		
		GlStateManager.disableTexture2D();
		/*tess.startDrawingQuads();
		
		tess.setNormal(0.0F, 1.0F, 0.0F);
		tess.setBrightness(240);
		
		this.particleAlpha = 1 - (((float)this.particleAge + interp) / (float)this.particleMaxAge);
		float scale = (this.particleAge + interp) * 0.05F;
		
		tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, this.particleAlpha);
		
		float pX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) interp - interpPosX);
		float pY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) interp - interpPosY);
		float pZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) interp - interpPosZ);
  
		tess.addVertexWithUV((double)(pX - x * scale - tx * scale), (double)(pY - y * scale), (double)(pZ - z * scale - tz * scale), 1, 1);
		tess.addVertexWithUV((double)(pX - x * scale + tx * scale), (double)(pY + y * scale), (double)(pZ - z * scale + tz * scale), 1, 0);
		tess.addVertexWithUV((double)(pX + x * scale + tx * scale), (double)(pY + y * scale), (double)(pZ + z * scale + tz * scale), 0, 0);
		tess.addVertexWithUV((double)(pX + x * scale - tx * scale), (double)(pY - y * scale), (double)(pZ + z * scale - tz * scale), 0, 1);
		tess.draw();*/
		
		GL11.glPushMatrix();
		GL11.glTranslated(pX, pY, pZ);
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.enableCull();
		GL11.glScalef(scale, scale, scale);
		ResourceManager.sphere_uv.renderAll();
		GL11.glScalef(1.02F, 1.02F, 1.02F);
		ResourceManager.sphere_uv.renderAll();
		GL11.glScalef(1.05F, 1.05F, 1.05F);
		ResourceManager.sphere_uv.renderAll();
		GL11.glScalef(1.02F, 1.02F, 1.02F);
		ResourceManager.sphere_uv.renderAll();
		GL11.glScalef(1.05F, 1.05F, 1.05F);
		ResourceManager.sphere_uv.renderAll();
		GL11.glPopMatrix();

		GlStateManager.enableTexture2D();
		GlStateManager.doPolygonOffset(0, 0);
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		GlStateManager.enableLighting();
	}
}
