package com.hbm.particle;

import java.lang.reflect.Field;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.HbmShaderManager2;
import com.hbm.main.ClientProxy;
import com.hbm.main.ResourceManager;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ParticleHeatDistortion extends Particle {

	private static final Minecraft mc = Minecraft.getMinecraft();
	private static Field VIEW_MAT_FIELD = null; // lazy init
	private final float[] quadCoords = new float[12];

	public float heatAmount;
	public float timeOffset;
	public boolean local;

	public ParticleHeatDistortion(World worldIn, double posXIn, double posYIn, double posZIn, float scale, float heatAmount, int lifetime, float timeOffset) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.particleMaxAge = lifetime;
		this.particleScale = scale;
		this.heatAmount = heatAmount;
		this.timeOffset = timeOffset;
	}

	public ParticleHeatDistortion motion(float mX, float mY, float mZ) {
		this.motionX = mX;
		this.motionY = mY;
		this.motionZ = mZ;
		return this;
	}

	public ParticleHeatDistortion enableLocalSpaceCorrection() {
		local = true;
		return this;
	}

	@Override
	public void onUpdate() {
		if (++this.particleAge >= this.particleMaxAge) {
			setExpired();
			return;
		}
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
	}

	@Override
	public int getFXLayer() {
		return 3;
	}

	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		GlStateManager.pushMatrix();

		final float timeScale = (this.particleAge + partialTicks) / (float) this.particleMaxAge;
		final float halfScale = 0.1F * this.particleScale;

		GlStateManager.depthMask(false);

		if (local) {
			final float transX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks);
			final float transY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks);
			final float transZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks);

			GlStateManager.translate(transX, transY, transZ);

			if (VIEW_MAT_FIELD == null) {
				try {
					VIEW_MAT_FIELD = ReflectionHelper.findField(ActiveRenderInfo.class, "MODELVIEW", "field_178812_b");
					VIEW_MAT_FIELD.setAccessible(true);
				} catch (Exception e) {
					VIEW_MAT_FIELD = null;
				}
			}

			if (VIEW_MAT_FIELD != null) {
				try {
					FloatBuffer viewMat = (FloatBuffer) VIEW_MAT_FIELD.get(null);
					if (viewMat != null) {
						viewMat.rewind();
						GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, ClientProxy.AUX_GL_BUFFER);
						for (int i = 0; i < 12; i++) {
							ClientProxy.AUX_GL_BUFFER.put(i, viewMat.get(i));
						}
						ClientProxy.AUX_GL_BUFFER.rewind();
						GL11.glLoadMatrix(ClientProxy.AUX_GL_BUFFER);
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		} else {
			final double entPosX = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
			final double entPosY = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
			final double entPosZ = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;

			final float f5 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - entPosX);
			final float f6 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - entPosY);
			final float f7 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - entPosZ);
			GlStateManager.translate(f5, f6, f7);
		}

		// v0
		quadCoords[0] = -rotationX * halfScale - rotationXY * halfScale;
		quadCoords[1] = -rotationZ * halfScale;
		quadCoords[2] = -rotationYZ * halfScale - rotationXZ * halfScale;
		// v1
		quadCoords[3] = -rotationX * halfScale + rotationXY * halfScale;
		quadCoords[4] = rotationZ * halfScale;
		quadCoords[5] = -rotationYZ * halfScale + rotationXZ * halfScale;
		// v2
		quadCoords[6] = rotationX * halfScale + rotationXY * halfScale;
		quadCoords[7] = rotationZ * halfScale;
		quadCoords[8] = rotationYZ * halfScale + rotationXZ * halfScale;
		// v3
		quadCoords[9] = rotationX * halfScale - rotationXY * halfScale;
		quadCoords[10] = -rotationZ * halfScale;
		quadCoords[11] = rotationYZ * halfScale - rotationXZ * halfScale;

		mc.getTextureManager().bindTexture(ResourceManager.fresnel_ms);
		final float heat_fade = MathHelper.clamp(1 - BobMathUtil.remap((float) MathHelper.clamp(timeScale, 0.8F, 1F), 0.8F, 1F, 0F, 1.1F), 0F, 1F)
				* MathHelper.clamp(BobMathUtil.remap((float) MathHelper.clamp(timeScale, 0F, 0.2F), 0F, 0.2F, 0F, 1.1F), 0F, 1F);

		// Activate shader and set uniforms
		ResourceManager.heat_distortion.use();
		ResourceManager.heat_distortion.uniform1f("amount", heatAmount * heat_fade * 0.15F);
		float time = (System.currentTimeMillis() % 10000000) / 1000F;
		ResourceManager.heat_distortion.uniform1f("time", time + timeOffset);

		// Render the quad
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(quadCoords[0], quadCoords[1], quadCoords[2]).tex(1, 1).endVertex();
		buffer.pos(quadCoords[3], quadCoords[4], quadCoords[5]).tex(1, 0).endVertex();
		buffer.pos(quadCoords[6], quadCoords[7], quadCoords[8]).tex(0, 0).endVertex();
		buffer.pos(quadCoords[9], quadCoords[10], quadCoords[11]).tex(0, 1).endVertex();
		Tessellator.getInstance().draw();

		HbmShaderManager2.releaseShader();

		GlStateManager.depthMask(true);
		GlStateManager.popMatrix();
	}

}