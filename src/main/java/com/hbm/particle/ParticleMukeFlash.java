package com.hbm.particle;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleMukeFlash extends Particle {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/flare.png");
	
	boolean bf;
	
	public ParticleMukeFlash(World world, double x, double y, double z, boolean bf) {
		super(world, x, y, z);
		this.particleMaxAge = 20;
		this.bf = bf;
	}
	
	@Override
	public int getFXLayer() {
		return 3;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(this.particleAge == 15) {
    		
    		//Stem
    		for(double d = 0.0D; d <= 1.8D; d += 0.1) {
	    		ParticleMukeCloud cloud = getCloud(world, posX, posY, posZ, rand.nextGaussian() * 0.05, d + rand.nextGaussian() * 0.02, rand.nextGaussian() * 0.05);
	    		Minecraft.getMinecraft().effectRenderer.addEffect(cloud);
    		}
    		
    		//Ground
    		for(int i = 0; i < 100; i++) {
	    		ParticleMukeCloud cloud = getCloud(world, posX, posY + 0.5, posZ, rand.nextGaussian() * 0.5, rand.nextInt(5) == 0 ? 0.02 : 0, rand.nextGaussian() * 0.5);
	    		Minecraft.getMinecraft().effectRenderer.addEffect(cloud);
    		}
    		
    		//Mush
    		for(int i = 0; i < 75; i++) {
    			double x = rand.nextGaussian() * 0.5;
    			double z = rand.nextGaussian() * 0.5;
    			
    			if(x * x + z * z > 1.5) {
    				x *= 0.5;
    				z *= 0.5;
    			}
    			
    			double y = 1.8 + (rand.nextDouble() * 3 - 1.5) * (0.75 - (x * x + z * z)) * 0.5;
    			
	    		ParticleMukeCloud cloud = getCloud(world, posX, posY, posZ, x, y + rand.nextGaussian() * 0.02, z);
	    		Minecraft.getMinecraft().effectRenderer.addEffect(cloud);
    		}
    	}
	}
	
	private ParticleMukeCloud getCloud(World world, double x, double y, double z, double mx, double my, double mz) {
    	
    	if(this.bf) {
    		return new ParticleMukeCloudBF(world, x, y, z, mx, my, mz);
    	} else {
    		return new ParticleMukeCloud(world, x, y, z, mx, my, mz);
    	}
    }
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float x, float y, float z, float tx, float tz) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0);
		GlStateManager.depthMask(false);
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);

		this.particleAlpha = MathHelper.clamp(1 - (((float)this.particleAge + partialTicks) / (float)this.particleMaxAge), 0, 1);
		float scale = (this.particleAge + partialTicks) * 0.75F + 5;

		float dX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - interpPosX);
		float dY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - interpPosY);
		float dZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - interpPosZ);

		Random rand = new Random();
		
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);

		for(int i = 0; i < 24; i++) {

			rand.setSeed(i * 31 + 1);

			float pX = (float) (dX + rand.nextDouble() * 15 - 7.5);
			float pY = (float) (dY + rand.nextDouble() * 7.5 - 3.75);
			float pZ = (float) (dZ + rand.nextDouble() * 15 - 7.5);

			buffer.pos((double) (pX - x * scale - tx * scale), (double) (pY - y * scale), (double) (pZ - z * scale - tz * scale)).tex(1, 1).color(1.0F, 0.9F, 0.75F, this.particleAlpha * 0.5F).lightmap(240, 240).endVertex();
			buffer.pos((double) (pX - x * scale + tx * scale), (double) (pY + y * scale), (double) (pZ - z * scale + tz * scale)).tex(1, 0).color(1.0F, 0.9F, 0.75F, this.particleAlpha * 0.5F).lightmap(240, 240).endVertex();
			buffer.pos((double) (pX + x * scale + tx * scale), (double) (pY + y * scale), (double) (pZ + z * scale + tz * scale)).tex(0, 0).color(1.0F, 0.9F, 0.75F, this.particleAlpha * 0.5F).lightmap(240, 240).endVertex();
			buffer.pos((double) (pX + x * scale - tx * scale), (double) (pY - y * scale), (double) (pZ + z * scale - tz * scale)).tex(0, 1).color(1.0F, 0.9F, 0.75F, this.particleAlpha * 0.5F).lightmap(240, 240).endVertex();
		}
		Tessellator.getInstance().draw();

		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		GlStateManager.enableLighting();
		GlStateManager.disableBlend();
	}

}
