package com.leafia.contents.machines.powercores.dfc.particles;

import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.leafia.dev.math.FiaMatrix;
import com.leafia.dev.math.FiaMatrix.RotationOrder;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class ParticleEyeOfHarmony extends Particle {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/leafia/particle_balefire.png");

	float curPercentage = 0;
	float speed = 1/30f;
	float flyAngle = 180;
	float flyHeight = 2;
	FiaMatrix mat;

	public ParticleEyeOfHarmony(World worldIn,BlockPos pos,float r,float g,float b,float scale){
		super(worldIn,pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5);
		mat = new FiaMatrix(new Vec3d(pos).add(0.5,0.5,0.5)).rotate(RotationOrder.YXZ,worldIn.rand.nextGaussian()*15,worldIn.rand.nextDouble()*360,worldIn.rand.nextGaussian()*15);
		this.particleRed = r;
		this.particleGreen = g;
		this.particleBlue = b;
		this.particleScale = this.particleScale * 0.08F;
		flyHeight = (worldIn.rand.nextFloat()*0.5f+0.75f)*scale;
		flyAngle = worldIn.rand.nextFloat()*180+90;
		speed = 1/(world.rand.nextFloat()*20+20);
		this.canCollide = false;
	}

	public FiaMatrix calculate(float perc) {
		return mat.rotateY(perc*flyAngle).translate(0,0,-flyHeight*Math.sin(perc*Math.PI));
	}

	@Override
	public void onUpdate() {
		curPercentage += speed;
		if (curPercentage >= 1)
			this.setExpired();
	}

	@Override
	public int getFXLayer(){
		return 3;
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ){
		com.hbm.render.RenderHelper.resetParticleInterpPos(entityIn, partialTicks);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.white);

		float f = ((float)this.particleAge + partialTicks) / (float)this.particleMaxAge;

		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);
		GlStateManager.depthMask(false);
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		LeafiaGls.pushMatrix();
		LeafiaGls.color(particleRed,particleGreen,particleBlue);
		LeafiaGls.translate(calculate(curPercentage+partialTicks*speed).position.subtract(interpPosX,interpPosY,interpPosZ));
		LeafiaGls.scale(0.2);
		ResourceManager.sphere_ruv.renderAll();
		LeafiaGls.popMatrix();
		LeafiaGls.color(1,1,1);

		GlStateManager.enableLighting();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
	}

}
