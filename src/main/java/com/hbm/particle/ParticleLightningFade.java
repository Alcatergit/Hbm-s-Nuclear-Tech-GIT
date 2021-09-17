package com.hbm.particle;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.hbm.handler.HbmShaderManager2;
import com.hbm.handler.LightningGenerator;
import com.hbm.handler.LightningGenerator.LightningGenInfo;
import com.hbm.handler.LightningGenerator.LightningNode;
import com.hbm.main.ResourceManager;
import com.hbm.particle.lightning_test.TrailRenderer2.IColorGetter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleLightningFade extends Particle {

	float width;
	LightningNode node;
	
	public ParticleLightningFade(World worldIn, double posXIn, double posYIn, double posZIn, double hitX, double hitY, double hitZ, float width, LightningGenInfo i) {
		super(worldIn, posXIn, posYIn, posZIn);
		node = LightningGenerator.generateLightning(new Vec3d(posXIn, posYIn, posZIn), new Vec3d(hitX, hitY, hitZ), i);
		this.particleMaxAge = 60;
		this.width = width;
	}
	
	@Override
	public void onUpdate() {
		this.particleAge ++;
		if(this.particleAge > this.particleMaxAge){
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
		GL11.glPushMatrix();
		GlStateManager.disableCull();
		GlStateManager.disableAlpha();
		GlStateManager.depthMask(false);
		double entPosX = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX)*partialTicks;
        double entPosY = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY)*partialTicks;
        double entPosZ = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ)*partialTicks;
        
        interpPosX = entPosX;
        interpPosY = entPosY;
        interpPosZ = entPosZ;
        
        GL11.glTranslated(-interpPosX, -interpPosY, -interpPosZ);
        
        ResourceManager.lightning.use();
        ResourceManager.lightning.uniform4f("duck", 1F, 1F, 1F, 1F);
	    float ageN = ((float)this.particleAge+partialTicks)/((float)this.particleMaxAge);
	    ResourceManager.lightning.uniform1f("fadeoverride", ageN);
        //ResourceManager.test_trail.use();
        //GL20.glUniform4f(GL20.glGetUniformLocation(ResourceManager.test_trail.getShaderId(), "duck"), 1F, 1, 1F, 1F);
	    IColorGetter cg = pos -> {
	    	float a = MathHelper.clamp((1-pos)-0.5F+ageN*50, 0, 1);
	    	return new float[]{1, 1, 1, a};
	    };
	    GlStateManager.enableBlend();
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
        LightningGenerator.render(node, new Vec3d(entPosX, entPosY+entityIn.getEyeHeight(), entPosZ), width, 0, 0, 0, true, cg);
        HbmShaderManager2.bloomData.bindFramebuffer(false);
        //GL20.glUniform4f(GL20.glGetUniformLocation(ResourceManager.test_trail.getShaderId(), "duck"), 0.6F, 0.8F, 1F, 1F);
        ResourceManager.lightning.uniform4f("duck", 0.6F, 0.8F, 1F, 1F);
        
        LightningGenerator.render(node, new Vec3d(entPosX, entPosY+entityIn.getEyeHeight(), entPosZ), width, 0, 0, 0, true, cg);
        LightningGenerator.render(node, new Vec3d(entPosX, entPosY+entityIn.getEyeHeight(), entPosZ), width, 0, 0, 0, true, cg);
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableBlend();
        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(false);
        HbmShaderManager2.releaseShader();
        GlStateManager.depthMask(true);
        GlStateManager.enableAlpha();
        GL11.glPopMatrix();
	}
}
