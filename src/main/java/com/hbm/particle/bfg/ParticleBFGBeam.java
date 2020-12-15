package com.hbm.particle.bfg;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import com.hbm.handler.HbmShaderManager;
import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleBFGBeam extends Particle {

	float length;
	float prevAlpha;
	
	public ParticleBFGBeam(World worldIn, double posXIn, double posYIn, double posZIn) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.canCollide = false;
		this.particleMaxAge = 50;
		this.prevAlpha = 1F;
		particleAlpha = 1;
		length = 1000;
		this.particleScale = 50;
	}
	
	@Override
	public void onUpdate() {
		this.particleAge++;
		if(particleAge >= particleMaxAge)
			this.setExpired();
		float timeScale = this.particleAge/(float)this.particleMaxAge;
		
		this.prevAlpha = particleAlpha;
		this.particleAlpha = MathHelper.clamp(1-BobMathUtil.remap((float)MathHelper.clamp(timeScale, 0.6, 1), 0.6F, 1F, 0F, 1.1F), 0, 1);
	}
	
	@Override
	public int getFXLayer() {
		return 3;
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		GL11.glPushMatrix();
		
		double d0 = this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks;
		double d1 = this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks;
		double d2 = this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks;
		
		double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
		double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
		double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;

		GL11.glTranslated(d0 - d3, d1 - d4, d2 - d5);
		
		GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
        GlStateManager.disableAlpha();
        GlStateManager.disableFog();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
        float alpha = prevAlpha + (particleAlpha-prevAlpha)*partialTicks;
        GlStateManager.color(0.5F, 1F, 0.5F, alpha/2);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.bfg_beam2);
		//Drillgon200: makes the beam look smoother by removing hard transition lines
		GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL14.GL_MIRRORED_REPEAT);
		
		Tessellator tes = Tessellator.getInstance();
        BufferBuilder buf = tes.getBuffer();
        
        Vec3d look = entity.getPositionEyes(partialTicks).subtract(d0, d1, d2);
        Vec3 point1 = Vec3.createVectorHelper(look.x, look.y, look.z).crossProduct(Vec3.createVectorHelper(0, 0, 1)).normalize().mult((float) (0.5*particleScale));
        Vec3 point2 = point1.mult(-1);
        
        float time = world.getTotalWorldTime() + partialTicks;
        //Drillgon200: The modulus prevents some weird floating point error artifacts.
        time %= 4;
        time *= -0.25;
        
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        
        int len = 5;
        
        for(int i = 0; i < 2*len; i ++){
        	int offset = i*50;
        	//Drillgon200: texFlip makes sure the texture is actually mirrored on each iteration
        	int texFlip = i % 2 == 1 ? 1 : 0;
        	buf.pos(point1.xCoord, point1.yCoord, -offset).tex(0+texFlip+time, 0).endVertex();
            buf.pos(point2.xCoord, point2.yCoord, -offset).tex(0+texFlip+time, 1).endVertex();
            buf.pos(point2.xCoord, point2.yCoord, -50-offset).tex(1+texFlip+time, 1).endVertex();
            buf.pos(point1.xCoord, point1.yCoord, -50-offset).tex(1+texFlip+time, 0).endVertex();
        }
        
        tes.draw();
        
        time *= 2;
        point1 = point1.mult(0.2F);
        point2 = point1.mult(-1);
        
        Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.bfg_beam1);
        GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL14.GL_MIRRORED_REPEAT);
        
        //Drillgon200: Literally all this shader does is multiply the color by 2, which means it adds color instead of taking it away like the normal color mask.
        //Drillgon200: As far as I know, you can't do this with fixed function GL, at least not easily.
        HbmShaderManager.useShader2(HbmShaderManager.bfg_beam);
        GlStateManager.color(0.5F, 1F, 0.5F, alpha);
        
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        for(int i = 0; i < len; i ++){
        	int offset = i*100;
        	int texFlip = i % 2 == 1 ? 1 : 0;
        	
        	buf.pos(point1.xCoord, point1.yCoord, 0-offset).tex(0+texFlip+time, 0).endVertex();
        	buf.pos(point2.xCoord, point2.yCoord, 0-offset).tex(0+texFlip+time, 1).endVertex();
        	buf.pos(point2.xCoord, point2.yCoord, -100-offset).tex(1+texFlip+time, 1).endVertex();
        	buf.pos(point1.xCoord, point1.yCoord, -100-offset).tex(1+texFlip+time, 0).endVertex();
        }
        tes.draw();
        
        HbmShaderManager.releaseShader2();
        
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.enableFog();
        GlStateManager.enableAlpha();
        GlStateManager.depthMask(true);
        
        GL11.glPopMatrix();
	}

}
