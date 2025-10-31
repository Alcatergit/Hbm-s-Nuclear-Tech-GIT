package com.leafia.contents.effects.folkvangr.particles;

import com.hbm.lib.RefStrings;
import com.leafia.contents.effects.folkvangr.EntityNukeFolkvangr.VacuumInstance;
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

import static com.leafia.contents.effects.folkvangr.EntityNukeFolkvangr.vacuumEase;
import static com.leafia.contents.effects.folkvangr.EntityNukeFolkvangr.vacuumForceMultiplier;

public class ParticleFleijaVacuum extends Particle {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/particle_base.png");
	private float baseScale;
	VacuumInstance instance;
	double startDistance;
	float baseAlpha;

	public ParticleFleijaVacuum(World worldIn,double posXIn,double posYIn,double posZIn,float scale,float alpha,VacuumInstance instance){
		super(worldIn, posXIn, posYIn, posZIn);
		this.particleScale = scale;
		this.baseScale = scale;
		this.particleRed = this.particleGreen = this.particleBlue = 0.9F + world.rand.nextFloat() * 0.05F;
		this.canCollide = false;
		this.particleAlpha = alpha;
		this.baseAlpha = alpha;
		this.instance = instance;
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		this.startDistance = new Vec3d(this.posX,this.posY,this.posZ).distanceTo(instance.pos);
	}
	
	@Override
	public void onUpdate(){
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		Vec3d posVec = new Vec3d(this.posX,this.posY,this.posZ);
		double distance = posVec.distanceTo(instance.pos);
		double ratio = Math.max(0,(distance-instance.vacuumStart/2)/(startDistance-instance.vacuumStart/2));

		this.particleScale = baseScale * (float)Math.pow(ratio,0.1);
		this.particleAlpha = baseAlpha*(float)Math.pow(1-ratio,0.5);

		double force = vacuumEase.get((distance-instance.vacuumStart)/(instance.vacuumEnd-instance.vacuumStart),1,0,true);
		Vec3d pullVec = instance.pos.subtract(posVec).normalize();
		this.motionX += pullVec.x*instance.vacuumForce*vacuumForceMultiplier*force;
		this.motionY += pullVec.y*instance.vacuumForce*vacuumForceMultiplier*force;
		this.motionZ += pullVec.z*instance.vacuumForce*vacuumForceMultiplier*force;
		this.move(this.motionX, this.motionY, this.motionZ);

		if(++this.particleAge >= 20*10 || (distance < instance.vacuumStart/2)) {
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
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
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

		GlStateManager.enableLighting();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
	}

}
