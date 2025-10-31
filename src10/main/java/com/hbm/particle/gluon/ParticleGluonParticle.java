package com.hbm.particle.gluon;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ClientProxy;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleGluonParticle extends Particle {

	float workingAlpha;
	
	protected ParticleGluonParticle(World worldIn, double posXIn, double posYIn, double posZIn, float scale, int maxAge) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.particleMaxAge = maxAge;
		this.particleScale = scale;
		this.particleRed = 0.4F;
		this.particleGreen = 0.7F;
	}

	@Override
	public void onUpdate() {
		this.particleAge++;
		if(particleAge >= particleMaxAge){
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
	
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, FloatBuffer mat) {
		GL11.glPushMatrix();
		float timeScale = (this.particleAge+partialTicks)/(float)this.particleMaxAge;
		float shrink = MathHelper.clamp(1-BobMathUtil.remap((float)MathHelper.clamp(timeScale, 0, 1), 0.6F, 1F, 0.6F, 1F), 0, 1);
		this.workingAlpha = shrink*particleAlpha*0.9F;
		
		float f4 = 0.1F * (this.particleScale+shrink*particleScale*4);
        
        float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks);
        float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks);
        float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks);
        
        GL11.glTranslated(f5, f6, f7);
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, ClientProxy.AUX_GL_BUFFER2);
		ClientProxy.AUX_GL_BUFFER2.rewind();
		float[] trans = new float[3];
		ClientProxy.AUX_GL_BUFFER2.position(12);
		ClientProxy.AUX_GL_BUFFER2.get(trans);
		ClientProxy.AUX_GL_BUFFER2.rewind();
		mat.position(12);
		mat.put(trans);
		mat.rewind();
		GL11.glLoadMatrix(mat);
		
        Vec3d[] avec3d = new Vec3d[] {new Vec3d((double)(-rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(-rotationYZ * f4 - rotationXZ * f4)), new Vec3d((double)(-rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(-rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double)(rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double)(rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(rotationYZ * f4 - rotationXZ * f4))};
        //I can't figure out a way to batch these particles without screwing up the hacky rotation fixes I'm doing.
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        buffer.pos(avec3d[0].x, avec3d[0].y, avec3d[0].z).tex(1, 1).color(this.particleRed, this.particleGreen, this.particleBlue, this.workingAlpha).lightmap(240, 240).endVertex();
        buffer.pos(avec3d[1].x, avec3d[1].y, avec3d[1].z).tex(1, 0).color(this.particleRed, this.particleGreen, this.particleBlue, this.workingAlpha).lightmap(240, 240).endVertex();
        buffer.pos(avec3d[2].x, avec3d[2].y, avec3d[2].z).tex(0, 0).color(this.particleRed, this.particleGreen, this.particleBlue, this.workingAlpha).lightmap(240, 240).endVertex();
        buffer.pos(avec3d[3].x, avec3d[3].y, avec3d[3].z).tex(0, 1).color(this.particleRed, this.particleGreen, this.particleBlue, this.workingAlpha).lightmap(240, 240).endVertex();
        Tessellator.getInstance().draw();
        GL11.glPopMatrix();
	}
	
}
