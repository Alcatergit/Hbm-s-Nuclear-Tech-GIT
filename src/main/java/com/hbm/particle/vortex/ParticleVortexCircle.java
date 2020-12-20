package com.hbm.particle.vortex;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleVortexCircle extends Particle {

	public float workingAlpha;
	
	public ParticleVortexCircle(World worldIn, double posXIn, double posYIn, double posZIn, float scale) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.particleScale = scale;
	}
	
	public ParticleVortexCircle color(float colR, float colG, float colB, float colA){
		this.particleRed = colR;
		this.particleGreen = colG;
		this.particleBlue = colB;
		this.particleAlpha = colA;
		workingAlpha = colA;
		return this;
	}
	
	public ParticleVortexCircle lifetime(int lifetime){
		this.particleMaxAge = lifetime;
		return this;
	}
	
	@Override
	public void onUpdate() {
		this.particleAge ++;
		if(this.particleAge >= this.particleMaxAge){
			this.setExpired();
		}
	}
	
	@Override
	public boolean shouldDisableDepth() {
		return true;
	}
	
	@Override
	public int getFXLayer() {
		return 3;
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.vortex_beam_circle_2);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GlStateManager.disableAlpha();
		GlStateManager.depthMask(false);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		float timeScale = (this.particleAge+partialTicks)/(float)this.particleMaxAge;
		this.workingAlpha = MathHelper.clamp(1-BobMathUtil.remap((float)MathHelper.clamp(timeScale, 0.6, 1), 0.6F, 1F, 0F, 1F), 0, 1)*particleAlpha;
		
		float f4 = 0.1F * (this.particleScale+timeScale*0.5F);
        
        float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
        float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
        float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);
        Vec3d[] avec3d = new Vec3d[] {new Vec3d((double)(-rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(-rotationYZ * f4 - rotationXZ * f4)), new Vec3d((double)(-rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(-rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double)(rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double)(rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(rotationYZ * f4 - rotationXZ * f4))};

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        
        buffer.pos((double)f5 + avec3d[0].x, (double)f6 + avec3d[0].y, (double)f7 + avec3d[0].z).tex(1, 1).color(this.particleRed, this.particleGreen, this.particleBlue, this.workingAlpha).lightmap(240, 240).endVertex();
        buffer.pos((double)f5 + avec3d[1].x, (double)f6 + avec3d[1].y, (double)f7 + avec3d[1].z).tex(1, 0).color(this.particleRed, this.particleGreen, this.particleBlue, this.workingAlpha).lightmap(240, 240).endVertex();
        buffer.pos((double)f5 + avec3d[2].x, (double)f6 + avec3d[2].y, (double)f7 + avec3d[2].z).tex(0, 0).color(this.particleRed, this.particleGreen, this.particleBlue, this.workingAlpha).lightmap(240, 240).endVertex();
        buffer.pos((double)f5 + avec3d[3].x, (double)f6 + avec3d[3].y, (double)f7 + avec3d[3].z).tex(0, 1).color(this.particleRed, this.particleGreen, this.particleBlue, this.workingAlpha).lightmap(240, 240).endVertex();
	
        Tessellator.getInstance().draw();
        
        GlStateManager.enableAlpha();
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
	}

}
