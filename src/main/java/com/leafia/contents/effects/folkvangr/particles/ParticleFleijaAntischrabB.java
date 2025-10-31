package com.leafia.contents.effects.folkvangr.particles;

import com.hbm.lib.RefStrings;
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
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class ParticleFleijaAntischrabB extends Particle {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/particle_base.png");

	public ParticleFleijaAntischrabB(World world,double x,double y,double z,float mx,float mz) {
		super(world,x,y,z);
		this.particleScale = 1;
		this.particleRed = 1;
		this.particleGreen = 0.149f;
		this.particleBlue = 0;
		this.canCollide = false;
		this.motionX = mx;
		this.motionY = 0;
		this.motionZ = mz;
		//this.particleAngle = rand.nextFloat()*360;   not worth the effort
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
	}

	@Override
	public void onUpdate(){
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionX *= rand.nextFloat()*0.1+0.9;
		this.motionY *= rand.nextFloat()*0.1+0.9;
		this.motionZ *= rand.nextFloat()*0.1+0.9;
		this.move(this.motionX, this.motionY, this.motionZ);
		if (this.particleAge >= 10) {
			this.particleAlpha = (float)Math.pow(1-Math.min((this.particleAge-10)/40f,1),2);
		}

		if(++this.particleAge >= 50) {
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
