package com.leafia.contents.effects.folkvangr.particles;

import com.hbm.lib.RefStrings;
import com.leafia.contents.effects.folkvangr.visual.EntityCloudFleija;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class ParticleFleijaCloud extends Particle {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/particle_base.png");
	EntityCloudFleija cloud;
	Vec3d lookVector;
	Vec3d rightVector;
	double mySize = -1;
	double mySpeed = 1;
	void updatePosition() {
		double s = cloud.scale*1.4;
		this.posX = cloud.posX+lookVector.x*s+rightVector.x*s;
		this.posY = cloud.posY+lookVector.y*s+rightVector.y*s;
		this.posZ = cloud.posZ+lookVector.z*s+rightVector.z*s;
	}
	public ParticleFleijaCloud(World world,Vec3d forward,Vec3d right,EntityCloudFleija cloud) {
		super(world, cloud.posX, cloud.posY, cloud.posZ);
		this.particleScale = 3;
		this.particleRed = this.particleGreen = this.particleBlue = 0.9F + world.rand.nextFloat() * 0.05F;
		this.canCollide = false;
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		this.cloud = cloud;
		this.lookVector = forward;
		this.rightVector = right;
		updatePosition();
		//this.particleAngle = rand.nextFloat()*360;   not worth the effort
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.particleAlpha = 0;
	}

	@Override
	public void onUpdate() {
		if (cloud.isDead) {
			// again, not worth the effort      world.spawnParticle(new ParticleFleijaVacuum(world,posX,posY,posZ,3,rand.nextFloat()*0.1f+0.1f,new VacuumInstance()));
			this.setExpired();
			return;
		}
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		//this.move(this.motionX, this.motionY, this.motionZ);
		updatePosition();
		if (this.particleAge <= 10) {
			this.particleAlpha = this.particleAge/10f;
		}
		if (this.particleAge >= 20*2) {
			this.particleAlpha = (float)Math.pow(1-Math.min((this.particleAge-20*2)/20f,1),1.5);
		}

		if(++this.particleAge >= 20*3) {
			this.setExpired();
		}
	}
	
	@Override
	public int getFXLayer(){
		return 3;
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ){
		com.hbm.render.RenderHelper.resetParticleInterpPos(entityIn, partialTicks);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);
		GlStateManager.depthMask(false);
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		RenderHelper.disableStandardItemLighting();
		Tessellator tes = Tessellator.getInstance();
		BufferBuilder buf = tes.getBuffer();

		int i = this.getBrightnessForRender(partialTicks);
        int j = i >> 16 & 65535;
        int k = i & 65535;
		
		GlStateManager.glNormal3f(0, 1, 0);
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);

		float scale = this.particleScale;
		float pX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - interpPosX);
		float pY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - interpPosY);
		float pZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - interpPosZ);

		buf.pos((double) (pX - rotationX * scale - rotationXY * scale), (double) (pY - rotationZ * scale), (double) (pZ - rotationYZ * scale - rotationXZ * scale)).tex(1, 1).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
		buf.pos((double) (pX - rotationX * scale + rotationXY * scale), (double) (pY + rotationZ * scale), (double) (pZ - rotationYZ * scale + rotationXZ * scale)).tex(1, 0).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
		buf.pos((double) (pX + rotationX * scale + rotationXY * scale), (double) (pY + rotationZ * scale), (double) (pZ + rotationYZ * scale + rotationXZ * scale)).tex(0, 0).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
		buf.pos((double) (pX + rotationX * scale - rotationXY * scale), (double) (pY - rotationZ * scale), (double) (pZ + rotationYZ * scale - rotationXZ * scale)).tex(0, 1).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
		tes.draw();

		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);

		GlStateManager.enableLighting();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
	}

}
