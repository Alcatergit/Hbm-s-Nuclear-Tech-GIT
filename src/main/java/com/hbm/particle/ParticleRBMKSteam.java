package com.hbm.particle;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleRBMKSteam extends Particle {

	private static final ResourceLocation TEXTURE = new ResourceLocation(RefStrings.MODID + ":textures/particle/rbmk_jet_steam.png");

	public ParticleRBMKSteam(World world, double x, double y, double z) {
		super(world, x, y, z);
		this.particleMaxAge = 20 + rand.nextInt(10);
		this.particleScale = rand.nextFloat() + 1F;
		this.particleAlpha = 1F;
	}

	@Override
	public int getFXLayer() {
		return 3;
	}

	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
		com.hbm.render.RenderHelper.resetParticleInterpPos(entityIn, partialTicks);

		GlStateManager.pushMatrix();
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.enableBlend();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0);
		GlStateManager.depthMask(false);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
		RenderHelper.disableStandardItemLighting();

		if (this.particleAge > this.particleMaxAge) {
			this.particleAge = this.particleMaxAge;
		}

		int texIndex = this.particleAge * 20 / particleMaxAge;
		float f0 = 1F / 20F;

		float uMin = texIndex * f0;
		float uMax = uMin + f0;
		float vMin = 0;
		float vMax = 1;

		Tessellator tes = Tessellator.getInstance();
		BufferBuilder buf = tes.getBuffer();

		GlStateManager.glNormal3f(0, 1, 0);
		GlStateManager.color(1.0F, 1.0F, 1.0F, this.particleAlpha);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);

		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

		this.particleAlpha = 1F;

		if (this.particleAge < 20) {
			this.particleAlpha = this.particleAge / 20F;
		}

		if (this.particleAge > this.particleMaxAge - 20) {
			this.particleAlpha = (this.particleMaxAge - this.particleAge) / 20F;
		}

		this.particleAlpha *= 0.5F;

		float pX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - interpPosX);
		float pY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - interpPosY);
		float pZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - interpPosZ);

		GlStateManager.translate(pX + rotationX, pY + rotationZ, pZ + rotationYZ);
		GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);

		buf.pos((double) (-this.particleScale - 1), (double) 0, (double) 0).tex(uMax, vMax).endVertex();
		buf.pos((double) (-this.particleScale - 1), (double) (this.particleScale * 4), (double) 0).tex(uMax, vMin).endVertex();
		buf.pos((double) (this.particleScale - 1), (double) (this.particleScale * 4), (double) 0).tex(uMin, vMin).endVertex();
		buf.pos((double) (this.particleScale - 1), (double) 0, (double) 0).tex(uMin, vMax).endVertex();

		tes.draw();

		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.doPolygonOffset(0, 0);
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}
}