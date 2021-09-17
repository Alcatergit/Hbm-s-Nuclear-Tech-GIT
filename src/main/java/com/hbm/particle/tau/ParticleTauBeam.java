package com.hbm.particle.tau;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.RenderHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleTauBeam extends Particle {

	float randU;
	Vec3d[] hitPositions;
	
	public ParticleTauBeam(World worldIn, Vec3d[] hitPositions, float width) {
		super(worldIn, 0, 0, 0);
		this.randU = rand.nextFloat();
		this.hitPositions = hitPositions;
		this.particleScale = width;
	}
	
	public ParticleTauBeam color(float r, float g, float b, float a){
		this.particleRed = r;
		this.particleGreen = g;
		this.particleBlue = b;
		this.particleAlpha = a;
		return this;
	}
	
	public ParticleTauBeam lifetime(int life){
		this.particleMaxAge = life;
		return this;
	}
	
	@Override
	public void onUpdate() {
		this.particleAge ++;
		if(this.particleAge >= this.particleMaxAge){
			this.setExpired();
			return;
		}
	}
	
	@Override
	public int getFXLayer() {
		return 3;
	}
	
	@Override
	public boolean shouldDisableDepth() {
		return true;
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		GlStateManager.disableLighting();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
		GlStateManager.enableBlend();
		GlStateManager.disableCull();
		GlStateManager.disableAlpha();
		GlStateManager.depthMask(false);
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.tau_beam_tex);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		float lifeN = (float)(particleAge+partialTicks)/(float)particleMaxAge;
		float fade = MathHelper.clamp(2.5F-lifeN*2.5F, 0, 1);
		GlStateManager.color(particleRed, particleGreen, particleBlue, particleAlpha*fade);
		
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		for(int i = 0; i < hitPositions.length-1; i ++){
			Vec3d current = hitPositions[i].subtract(interpPosX, interpPosY, interpPosZ);
			Vec3d next = hitPositions[i+1].subtract(interpPosX, interpPosY, interpPosZ);
			Vec3d axis = next.subtract(current);
			Vec3d toPlayer = current.subtract(0, entityIn.getEyeHeight(), 0);
			Vec3d pos1 = axis.crossProduct(toPlayer).normalize().scale(particleScale*Math.max(fade, 0.75));
			Vec3d pos2 = pos1.scale(-1);
			buffer.pos(pos1.x + current.x, pos1.y + current.y, pos1.z + current.z).tex(randU, 0).endVertex();
			buffer.pos(pos2.x + current.x, pos2.y + current.y, pos2.z + current.z).tex(randU, 1).endVertex();
			buffer.pos(pos2.x + next.x, pos2.y + next.y, pos2.z + next.z).tex(randU, 1).endVertex();
			buffer.pos(pos1.x + next.x, pos1.y + next.y, pos1.z + next.z).tex(randU, 0).endVertex();
		}
		Tessellator.getInstance().draw();
		
		RenderHelper.resetColor();
		GlStateManager.enableCull();
		GlStateManager.enableAlpha();
		GlStateManager.depthMask(true);
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
	}

	
}
