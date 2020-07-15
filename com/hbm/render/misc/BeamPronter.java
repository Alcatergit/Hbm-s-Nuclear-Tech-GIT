package com.hbm.render.misc;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.render.amlfrom1710.Tessellator;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;

public class BeamPronter {
	
	public static enum EnumWaveType {
		RANDOM,
		SPIRAL
	}
	
	public static enum EnumBeamType {
		SOLID,
		LINE
	}
	
	public static void prontBeam(Vec3 skeleton, EnumWaveType wave, EnumBeamType beam, int outerColor, int innerColor, int start, int segments, float size, int layers, float thickness) {

		GL11.glPushMatrix();
		
		float sYaw = (float)(Math.atan2(skeleton.xCoord, skeleton.zCoord) * 180F / Math.PI);
        float sqrt = MathHelper.sqrt(skeleton.xCoord * skeleton.xCoord + skeleton.zCoord * skeleton.zCoord);
		float sPitch = (float)(Math.atan2(skeleton.yCoord, (double)sqrt) * 180F / Math.PI);

		GL11.glRotatef(180, 0, 1F, 0);
		GL11.glRotatef(sYaw, 0, 1F, 0);
		GL11.glRotatef(sPitch - 90, 1F, 0, 0);
		
		GL11.glPushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();

		if(beam == EnumBeamType.SOLID) {
			GlStateManager.disableCull();
			GlStateManager.enableBlend();
			GlStateManager.depthMask(false);
			GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		}
        
		Tessellator tessellator = Tessellator.instance;
		
		if(beam == EnumBeamType.LINE) {
			net.minecraft.client.renderer.Tessellator.getInstance().getBuffer().begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
		} else if (beam == EnumBeamType.SOLID){
			net.minecraft.client.renderer.Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		}

		Vec3 unit = Vec3.createVectorHelper(0, 1, 0);
		Random rand = new Random(start);
		double length = skeleton.lengthVector();
		double segLength = length / segments;
		double lastX = 0;
		double lastY = 0;
		double lastZ = 0;
		
		for(int i = 0; i <= segments; i++) {
			
			Vec3 spinner = Vec3.createVectorHelper(size, 0, 0);
			
			if(wave == EnumWaveType.SPIRAL) {
				spinner.rotateAroundY((float)Math.PI * (float)start / 180F);
				spinner.rotateAroundY((float)Math.PI * 45F / 180F * i);
			} else if(wave == EnumWaveType.RANDOM) {
				spinner.rotateAroundY((float)Math.PI * 2 * rand.nextFloat());
			}
			
			//spinner.rotateAroundX(sPitch + (float)Math.PI * 0.5F);
			//spinner.rotateAroundY(sYaw);

			double pX = unit.xCoord * segLength * i + spinner.xCoord;
			double pY = unit.yCoord * segLength * i + spinner.yCoord;
			double pZ = unit.zCoord * segLength * i + spinner.zCoord;
			
			if(beam == EnumBeamType.LINE && i > 0) {
	            tessellator.setColorOpaque_I(outerColor);
	            tessellator.addVertex(pX, pY, pZ);
	            tessellator.addVertex(lastX, lastY, lastZ);
			}
			
			if(beam == EnumBeamType.SOLID && i > 0) {
				
				float radius = thickness / layers;

				for(int j = 1; j <= layers; j++) {
					
					float inter = (float)(j - 1) / (float)(layers - 1);
					int color = (int) (outerColor + (innerColor - outerColor) * inter);
					tessellator.setColorOpaque_I(color);
					
					tessellator.addVertex(lastX + (radius * j), lastY, lastZ + (radius * j));
					tessellator.addVertex(lastX + (radius * j), lastY, lastZ - (radius * j));
					tessellator.addVertex(pX + (radius * j), pY, pZ - (radius * j));
					tessellator.addVertex(pX + (radius * j), pY, pZ + (radius * j));
					
					tessellator.addVertex(lastX - (radius * j), lastY, lastZ + (radius * j));
					tessellator.addVertex(lastX - (radius * j), lastY, lastZ - (radius * j));
					tessellator.addVertex(pX - (radius * j), pY, pZ - (radius * j));
					tessellator.addVertex(pX - (radius * j), pY, pZ + (radius * j));
					
					tessellator.addVertex(lastX + (radius * j), lastY, lastZ + (radius * j));
					tessellator.addVertex(lastX - (radius * j), lastY, lastZ + (radius * j));
					tessellator.addVertex(pX - (radius * j), pY, pZ + (radius * j));
					tessellator.addVertex(pX + (radius * j), pY, pZ + (radius * j));
					
					tessellator.addVertex(lastX + (radius * j), lastY, lastZ - (radius * j));
					tessellator.addVertex(lastX - (radius * j), lastY, lastZ - (radius * j));
					tessellator.addVertex(pX - (radius * j), pY, pZ - (radius * j));
					tessellator.addVertex(pX + (radius * j), pY, pZ - (radius * j));
				}
			}
			
			lastX = pX;
			lastY = pY;
			lastZ = pZ;
		}
		
		if(beam == EnumBeamType.LINE) {
            tessellator.setColorOpaque_I(innerColor);
            tessellator.addVertex(0, 0, 0);
            tessellator.addVertex(0, skeleton.lengthVector(), 0);
		}

		
		
		tessellator.draw();
		
		if(beam == EnumBeamType.SOLID) {
			GlStateManager.disableBlend();
			GlStateManager.depthMask(true);
			GlStateManager.enableTexture2D();
		}
		
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
		GL11.glPopMatrix();

		GL11.glPopMatrix();
	}
	
	//Drillgon200: Yeah, I don't know what to do about fluid colors so I'm just going butcher it and try my best to use the middle pixel of the icon
	public static void prontBeamWithIcon(Vec3 skeleton, EnumWaveType wave, EnumBeamType beam, TextureAtlasSprite icon, int innerColor, int start, int segments, float size, int layers, float thickness) {
		GL11.glPushMatrix();
		
		float u = icon.getInterpolatedU(8);
		float v = icon.getInterpolatedV(8);
		
		float sYaw = (float)(Math.atan2(skeleton.xCoord, skeleton.zCoord) * 180F / Math.PI);
        float sqrt = MathHelper.sqrt(skeleton.xCoord * skeleton.xCoord + skeleton.zCoord * skeleton.zCoord);
		float sPitch = (float)(Math.atan2(skeleton.yCoord, (double)sqrt) * 180F / Math.PI);

		GL11.glRotatef(180, 0, 1F, 0);
		GL11.glRotatef(sYaw, 0, 1F, 0);
		GL11.glRotatef(sPitch - 90, 1F, 0, 0);
		
		GL11.glPushMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();

		if(beam == EnumBeamType.SOLID) {
			GlStateManager.disableCull();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		}
        
		Tessellator tessellator = Tessellator.instance;
		
		

		Vec3 unit = Vec3.createVectorHelper(0, 1, 0);
		Random rand = new Random(start);
		double length = skeleton.lengthVector();
		double segLength = length / segments;
		double lastX = 0;
		double lastY = 0;
		double lastZ = 0;
		if(beam == EnumBeamType.LINE) {
			net.minecraft.client.renderer.Tessellator.getInstance().getBuffer().begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_TEX);
		} else if (beam == EnumBeamType.SOLID){
			net.minecraft.client.renderer.Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		}
		for(int i = 0; i <= segments; i++) {
			
			Vec3 spinner = Vec3.createVectorHelper(size, 0, 0);
			
			if(wave == EnumWaveType.SPIRAL) {
				spinner.rotateAroundY((float)Math.PI * (float)start / 180F);
				spinner.rotateAroundY((float)Math.PI * 45F / 180F * i);
			} else if(wave == EnumWaveType.RANDOM) {
				spinner.rotateAroundY((float)Math.PI * 2 * rand.nextFloat());
			}
			
			//spinner.rotateAroundX(sPitch + (float)Math.PI * 0.5F);
			//spinner.rotateAroundY(sYaw);

			double pX = unit.xCoord * segLength * i + spinner.xCoord;
			double pY = unit.yCoord * segLength * i + spinner.yCoord;
			double pZ = unit.zCoord * segLength * i + spinner.zCoord;
			
			if(beam == EnumBeamType.LINE && i > 0) {
	            tessellator.addVertexWithUV(pX, pY, pZ, u, v);
	            tessellator.addVertexWithUV(lastX, lastY, lastZ, u, v);
			}
			
			if(beam == EnumBeamType.SOLID && i > 0) {
				
				float radius = thickness / layers;

				for(int j = 1; j <= layers; j++) {
					
					tessellator.addVertexWithUV(lastX + (radius * j), lastY, lastZ + (radius * j), u, v);
					tessellator.addVertexWithUV(lastX + (radius * j), lastY, lastZ - (radius * j), u, v);
					tessellator.addVertexWithUV(pX + (radius * j), pY, pZ - (radius * j), u, v);
					tessellator.addVertexWithUV(pX + (radius * j), pY, pZ + (radius * j), u, v);
					
					tessellator.addVertexWithUV(lastX - (radius * j), lastY, lastZ + (radius * j), u, v);
					tessellator.addVertexWithUV(lastX - (radius * j), lastY, lastZ - (radius * j), u, v);
					tessellator.addVertexWithUV(pX - (radius * j), pY, pZ - (radius * j), u, v);
					tessellator.addVertexWithUV(pX - (radius * j), pY, pZ + (radius * j), u, v);
					
					tessellator.addVertexWithUV(lastX + (radius * j), lastY, lastZ + (radius * j), u, v);
					tessellator.addVertexWithUV(lastX - (radius * j), lastY, lastZ + (radius * j), u, v);
					tessellator.addVertexWithUV(pX - (radius * j), pY, pZ + (radius * j), u, v);
					tessellator.addVertexWithUV(pX + (radius * j), pY, pZ + (radius * j), u, v);
					
					tessellator.addVertexWithUV(lastX + (radius * j), lastY, lastZ - (radius * j), u, v);
					tessellator.addVertexWithUV(lastX - (radius * j), lastY, lastZ - (radius * j), u, v);
					tessellator.addVertexWithUV(pX - (radius * j), pY, pZ - (radius * j), u, v);
					tessellator.addVertexWithUV(pX + (radius * j), pY, pZ - (radius * j), u, v);
				}
			}
			
			lastX = pX;
			lastY = pY;
			lastZ = pZ;
			
		}
		tessellator.draw();
		GlStateManager.disableTexture2D();
		
		if(beam == EnumBeamType.LINE) {
			net.minecraft.client.renderer.Tessellator.getInstance().getBuffer().begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
		} else if (beam == EnumBeamType.SOLID){
			net.minecraft.client.renderer.Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		}
		
		if(beam == EnumBeamType.LINE) {
            tessellator.setColorOpaque_I(innerColor);
            tessellator.addVertex(0, 0, 0);
            tessellator.addVertex(0, skeleton.lengthVector(), 0);
		}

		
		
		tessellator.draw();
		
		if(beam == EnumBeamType.SOLID) {
			GlStateManager.disableBlend();
			GlStateManager.enableTexture2D();
		}
		
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
		GL11.glPopMatrix();

		GL11.glPopMatrix();
	}

}