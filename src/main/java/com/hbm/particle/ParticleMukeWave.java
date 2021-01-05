package com.hbm.particle;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleMukeWave extends Particle {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/shockwave.png");
	
	public ParticleMukeWave(World worldIn, double posXIn, double posYIn, double posZIn) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.particleMaxAge = 25;
	}
	
	@Override
	public int getFXLayer() {
		return 3;
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0);
		GlStateManager.depthMask(false);
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		GlStateManager.disableCull();

		this.particleAlpha = MathHelper.clamp(1 - (((float)this.particleAge + partialTicks) / (float)this.particleMaxAge), 0, 1);
		float scale = (this.particleAge + partialTicks) * 2F;

	    float pX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
	    float pY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
	    float pZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);

	    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
	    buffer.pos((double)(pX - 1 * scale), (double)(pY), (double)(pZ - 1 * scale)).tex(1, 1).color(1, 1, 1, particleAlpha).lightmap(240, 240).endVertex();
	    buffer.pos((double)(pX - 1 * scale), (double)(pY), (double)(pZ + 1 * scale)).tex(1, 0).color(1, 1, 1, particleAlpha).lightmap(240, 240).endVertex();
	    buffer.pos((double)(pX + 1 * scale), (double)(pY), (double)(pZ + 1 * scale)).tex(0, 0).color(1, 1, 1, particleAlpha).lightmap(240, 240).endVertex();
	    buffer.pos((double)(pX + 1 * scale), (double)(pY), (double)(pZ - 1 * scale)).tex(0, 1).color(1, 1, 1, particleAlpha).lightmap(240, 240).endVertex();
	    Tessellator.getInstance().draw();
	    
	    GlStateManager.enableCull();
	    GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		GlStateManager.enableLighting();
		GlStateManager.disableBlend();
	}

}
