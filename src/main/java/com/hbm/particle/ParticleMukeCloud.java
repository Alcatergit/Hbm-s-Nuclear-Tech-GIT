package com.hbm.particle;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleMukeCloud extends Particle {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/explosion.png");
	
	private float friction;
	
	public ParticleMukeCloud(World world, double x, double y, double z, double mx, double my, double mz) {
		super(world, x, y, z);
		this.motionX = mx;
		this.motionY = my;
		this.motionZ = mz;
		
		if(motionY > 0) {
			this.friction = 0.9F;
			
			if(motionY > 0.1)
				this.particleMaxAge = 92 + rand.nextInt(11) + (int)(motionY * 20);
			else
				this.particleMaxAge = 72 + rand.nextInt(11);
			
		} else if (motionY == 0) {
			
			this.friction = 0.95F;
			this.particleMaxAge = 52 + rand.nextInt(11);
			
		} else {
			
			this.friction = 0.85F;
			this.particleMaxAge = 122 + rand.nextInt(31);
			this.particleAge = 80;
		}
	}
	
	@Override
	public int getFXLayer() {
		return 3;
	}
	
	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge - 2) {
            this.setExpired();
        }

        this.motionY -= 0.04D * (double)this.particleGravity;
        this.move(this.motionX, this.motionY, this.motionZ);
        this.motionX *= friction;
        this.motionY *= friction;
        this.motionZ *= friction;

        if (this.onGround) {
            this.motionX *= 0.7D;
            this.motionZ *= 0.7D;
        }
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(getTexture());
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0);
		GlStateManager.depthMask(false);
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderHelper.disableStandardItemLighting();
		com.hbm.render.RenderHelper.resetParticleInterpPos(entityIn, partialTicks);
		if(this.particleAge > this.particleMaxAge)
			this.particleAge = this.particleMaxAge;
		int texIndex = this.particleAge * 25 / this.particleMaxAge;
		float f0 = 1F / 5F;
		
		float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
        float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
        float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);

		float uMin = texIndex % 5 * f0;
		float uMax = uMin + f0;
		float vMin = texIndex / 5 * f0;
		float vMax = vMin + f0;
		
		this.particleAlpha = 1F;
		this.particleScale = 3;
		Vec3d[] avec3d = new Vec3d[] {new Vec3d((double)(-rotationX * particleScale - rotationXY * particleScale), (double)(-rotationZ * particleScale), (double)(-rotationYZ * particleScale - rotationXZ * particleScale)), new Vec3d((double)(-rotationX * particleScale + rotationXY * particleScale), (double)(rotationZ * particleScale), (double)(-rotationYZ * particleScale + rotationXZ * particleScale)), new Vec3d((double)(rotationX * particleScale + rotationXY * particleScale), (double)(rotationZ * particleScale), (double)(rotationYZ * particleScale + rotationXZ * particleScale)), new Vec3d((double)(rotationX * particleScale - rotationXY * particleScale), (double)(-rotationZ * particleScale), (double)(rotationYZ * particleScale - rotationXZ * particleScale))};

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
        Tessellator tes = Tessellator.getInstance();
		BufferBuilder buf = tes.getBuffer();
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		
        buffer.pos((double)f5 + avec3d[0].x, (double)f6 + avec3d[0].y, (double)f7 + avec3d[0].z).tex(uMax, vMax).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(240, 240).endVertex();
        buffer.pos((double)f5 + avec3d[1].x, (double)f6 + avec3d[1].y, (double)f7 + avec3d[1].z).tex(uMax, vMin).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(240, 240).endVertex();
        buffer.pos((double)f5 + avec3d[2].x, (double)f6 + avec3d[2].y, (double)f7 + avec3d[2].z).tex(uMin, vMin).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(240, 240).endVertex();
        buffer.pos((double)f5 + avec3d[3].x, (double)f6 + avec3d[3].y, (double)f7 + avec3d[3].z).tex(uMin, vMax).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(240, 240).endVertex();
		
		tes.draw();
		
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		GlStateManager.enableLighting();
	}
	
	public static ResourceLocation getTexture() {
		return texture;
	}

}
