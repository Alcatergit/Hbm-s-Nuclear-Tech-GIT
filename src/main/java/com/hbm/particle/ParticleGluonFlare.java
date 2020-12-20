package com.hbm.particle;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.misc.LensVisibilityHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleGluonFlare extends Particle {

	int visibilityId = -1;
	float rot;
	
	public ParticleGluonFlare(World worldIn, double posXIn, double posYIn, double posZIn) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.particleScale = 7;
		this.particleMaxAge = 3+worldIn.rand.nextInt(3);
		rot = rand.nextFloat()*0.1F + 0.1F;
		this.particleAngle = rand.nextFloat()*6F;
		this.prevParticleAngle = this.particleAngle;
		this.particleGreen = 0.7F;
		this.particleRed = 0.5F;
	}
	
	@Override
	public void onUpdate() {
		this.particleAge ++;
		this.prevParticleAngle = this.particleAngle;
		this.particleAngle += rot;
		if(particleAge >= particleMaxAge){
			setExpired();
			LensVisibilityHandler.delete(visibilityId);
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
		GL11.glPushMatrix();
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.flare2);
		GlStateManager.disableDepth();
		GlStateManager.disableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		
		double entPosX = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX)*partialTicks;
        double entPosY = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY)*partialTicks;
        double entPosZ = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ)*partialTicks;
        
        interpPosX = entPosX;
        interpPosY = entPosY;
        interpPosZ = entPosZ;
		
		float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
        float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
        float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);
        
        GL11.glTranslated(f5, f6, f7);
		if(visibilityId == -1){
			FloatBuffer mat = GLAllocation.createDirectFloatBuffer(16);
			GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, mat);
			visibilityId = LensVisibilityHandler.generate(mat);
		}
		FloatBuffer mat = LensVisibilityHandler.getMatrixBuf(visibilityId);
		mat.rewind();
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, mat);
		float visibility = LensVisibilityHandler.getVisibility(visibilityId);
		visibility *= visibility;
		
		float ageN = (float)(this.particleAge+partialTicks)/(float)this.particleMaxAge;
		float scale = MathHelper.clamp(ageN*2, 0, 1)* MathHelper.clamp(2-ageN*2+0.1F, 0, 1);
		float f4 = 0.1F * this.particleScale * visibility*scale;
        
        Vec3d[] avec3d = new Vec3d[] {new Vec3d((double)(-rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(-rotationYZ * f4 - rotationXZ * f4)), new Vec3d((double)(-rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(-rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double)(rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double)(rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(rotationYZ * f4 - rotationXZ * f4))};
        f4*=3;
        Vec3d[] avec3d2 = new Vec3d[] {new Vec3d((double)(-rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(-rotationYZ * f4 - rotationXZ * f4)), new Vec3d((double)(-rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(-rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double)(rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double)(rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(rotationYZ * f4 - rotationXZ * f4))};

        if (this.particleAngle != 0.0F)
        {
            float f8 = this.particleAngle + (this.particleAngle - this.prevParticleAngle) * partialTicks;
            float f9 = MathHelper.cos(f8 * 0.5F);
            float f10 = MathHelper.sin(f8 * 0.5F) * (float)cameraViewDir.x;
            float f11 = MathHelper.sin(f8 * 0.5F) * (float)cameraViewDir.y;
            float f12 = MathHelper.sin(f8 * 0.5F) * (float)cameraViewDir.z;
            Vec3d vec3d = new Vec3d((double)f10, (double)f11, (double)f12);

            for (int l = 0; l < 4; ++l)
            {
                avec3d[l] = vec3d.scale(2.0D * avec3d[l].dotProduct(vec3d)).add(avec3d[l].scale((double)(f9 * f9) - vec3d.dotProduct(vec3d))).add(vec3d.crossProduct(avec3d[l]).scale((double)(2.0F * f9)));
            }
        }

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        buffer.pos(avec3d[0].x, avec3d[0].y, avec3d[0].z).tex(1, 1).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha*visibility*visibility*0.8F).lightmap(240, 240).endVertex();
        buffer.pos(avec3d[1].x, avec3d[1].y, avec3d[1].z).tex(1, 0).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha*visibility*visibility*0.8F).lightmap(240, 240).endVertex();
        buffer.pos(avec3d[2].x, avec3d[2].y, avec3d[2].z).tex(0, 0).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha*visibility*visibility*0.8F).lightmap(240, 240).endVertex();
        buffer.pos(avec3d[3].x, avec3d[3].y, avec3d[3].z).tex(0, 1).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha*visibility*visibility*0.8F).lightmap(240, 240).endVertex();
		Tessellator.getInstance().draw();
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.fresnel_ms);
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		buffer.pos(avec3d2[0].x, avec3d2[0].y, avec3d2[0].z).tex(1, 1).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha*visibility*0.1F).lightmap(240, 240).endVertex();
        buffer.pos(avec3d2[1].x, avec3d2[1].y, avec3d2[1].z).tex(1, 0).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha*visibility*0.1F).lightmap(240, 240).endVertex();
        buffer.pos(avec3d2[2].x, avec3d2[2].y, avec3d2[2].z).tex(0, 0).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha*visibility*0.1F).lightmap(240, 240).endVertex();
        buffer.pos(avec3d2[3].x, avec3d2[3].y, avec3d2[3].z).tex(0, 1).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha*visibility*0.1F).lightmap(240, 240).endVertex();
        Tessellator.getInstance().draw();
		GlStateManager.disableBlend();
		GlStateManager.enableDepth();
		GlStateManager.enableAlpha();
		GL11.glPopMatrix();
	}
	
}
