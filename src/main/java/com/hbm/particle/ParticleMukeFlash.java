package com.hbm.particle;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleMukeFlash extends Particle {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/flare.png");
	
	public ParticleMukeFlash(World worldIn, double posXIn, double posYIn, double posZIn) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.particleMaxAge = 20;
	}
	
	@Override
	public int getFXLayer() {
		return 3;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float x, float y, float z, float tx, float tz) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0);
		GlStateManager.depthMask(false);
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);

		this.particleAlpha = MathHelper.clamp(1 - (((float)this.particleAge + partialTicks) / (float)this.particleMaxAge), 0, 1);
		float scale = (this.particleAge + partialTicks) * 0.75F + 5;

		float dX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - interpPosX);
		float dY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - interpPosY);
		float dZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - interpPosZ);

		Random rand = new Random();
		
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);

		for(int i = 0; i < 16; i++) {

			rand.setSeed(i * 31 + 1);

			float pX = (float) (dX + rand.nextDouble() * 5 - 2.5);
			float pY = (float) (dY + rand.nextDouble() * 3 - 1.5);
			float pZ = (float) (dZ + rand.nextDouble() * 5 - 2.5);

			buffer.pos((double) (pX - x * scale - tx * scale), (double) (pY - y * scale), (double) (pZ - z * scale - tz * scale)).tex(1, 1).color(1.0F, 0.9F, 0.75F, this.particleAlpha * 0.5F).lightmap(240, 240).endVertex();
			buffer.pos((double) (pX - x * scale + tx * scale), (double) (pY + y * scale), (double) (pZ - z * scale + tz * scale)).tex(1, 0).color(1.0F, 0.9F, 0.75F, this.particleAlpha * 0.5F).lightmap(240, 240).endVertex();
			buffer.pos((double) (pX + x * scale + tx * scale), (double) (pY + y * scale), (double) (pZ + z * scale + tz * scale)).tex(0, 0).color(1.0F, 0.9F, 0.75F, this.particleAlpha * 0.5F).lightmap(240, 240).endVertex();
			buffer.pos((double) (pX + x * scale - tx * scale), (double) (pY - y * scale), (double) (pZ + z * scale - tz * scale)).tex(0, 1).color(1.0F, 0.9F, 0.75F, this.particleAlpha * 0.5F).lightmap(240, 240).endVertex();
		}
		Tessellator.getInstance().draw();

		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		GlStateManager.enableLighting();
		GlStateManager.disableBlend();
	}

}
