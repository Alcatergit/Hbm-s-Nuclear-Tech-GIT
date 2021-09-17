package com.hbm.particle.rocket;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ClientProxy;
import com.hbm.particle.ParticleLayerBase;
import com.hbm.particle.ParticleRenderLayer;
import com.hbm.render.misc.ColorGradient;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ParticleRocketPlasma extends ParticleLayerBase {

	public ColorGradient color;
	
	public ParticleRocketPlasma(World worldIn, double posXIn, double posYIn, double posZIn, float scale, ColorGradient color) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.color = color;
		this.particleMaxAge = 5;
		this.particleScale = scale;
	}

	public ParticleRocketPlasma motion(float mX, float mY, float mZ){
		this.motionX = mX;
		this.motionY = mY;
		this.motionZ = mZ;
		return this;
	}
	
	@Override
	public void onUpdate() {
		this.particleAge++;
		if(this.particleAge >= this.particleMaxAge){
			setExpired();
			return;
		}
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		posX += motionX;
		posY += motionY;
		posZ += motionZ;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		GL11.glPushMatrix();
		float timeScale = (this.particleAge+partialTicks)/(float)this.particleMaxAge;
		float[] currentCol = color.getColor(timeScale);
		
		float f4 = (float) (0.1F * this.particleScale * (1-Math.pow(timeScale, 2)));
        
        float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks);
        float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks);
        float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks);
        GL11.glTranslated(f5, f6, f7);
        if(BobMathUtil.r_viewMat == null){
			BobMathUtil.r_viewMat = ReflectionHelper.findField(ActiveRenderInfo.class, "MODELVIEW", "field_178812_b");
		}
		try {
			FloatBuffer view_mat = (FloatBuffer) BobMathUtil.r_viewMat.get(null);
			view_mat.rewind();
			GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, ClientProxy.AUX_GL_BUFFER);
			for(int i = 0; i < 12; i ++){
				ClientProxy.AUX_GL_BUFFER.put(i, view_mat.get(i));
			}
			ClientProxy.AUX_GL_BUFFER.rewind();
			GL11.glLoadMatrix(ClientProxy.AUX_GL_BUFFER);
		} catch(IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
        Vec3d[] avec3d = new Vec3d[] {new Vec3d((double)(-rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(-rotationYZ * f4 - rotationXZ * f4)), new Vec3d((double)(-rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(-rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double)(rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double)(rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(rotationYZ * f4 - rotationXZ * f4))};
        
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        buffer.pos(avec3d[3].x, avec3d[3].y, avec3d[3].z).tex(0, 1).color(currentCol[0], currentCol[1], currentCol[2], currentCol[3]).lightmap(240, 240).endVertex();
        buffer.pos(avec3d[2].x, avec3d[2].y, avec3d[2].z).tex(0, 0).color(currentCol[0], currentCol[1], currentCol[2], currentCol[3]).lightmap(240, 240).endVertex();
        buffer.pos(avec3d[1].x, avec3d[1].y, avec3d[1].z).tex(1, 0).color(currentCol[0], currentCol[1], currentCol[2], currentCol[3]).lightmap(240, 240).endVertex();
        buffer.pos(avec3d[0].x, avec3d[0].y, avec3d[0].z).tex(1, 1).color(currentCol[0], currentCol[1], currentCol[2], currentCol[3]).lightmap(240, 240).endVertex();
        
        
        Tessellator.getInstance().draw();
        GL11.glPopMatrix();
	}
	
	@Override
	public ParticleRenderLayer getRenderLayer() {
		return ParticleRenderLayer.ADDITIVE_FRESNEL;
	}

}
