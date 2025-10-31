package com.hbm.render;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.client.renderer.GlStateManager;

public class RenderSparks {

	public static void renderSpark(int seed, double x, double y, double z, float length, int min, int max, int color1, int color2) {

		float r1 = (color1 >> 16 & 255)/255F;
		float g1 = (color1 >> 8 & 255)/255F;
		float b1 = (color1 & 255)/255F;
		
		float r2 = (color2 >> 16 & 255)/255F;
		float g2 = (color2 >> 8 & 255)/255F;
		float b2 = (color2 & 255)/255F;
		
		GL11.glPushMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.glLineWidth(3F);
		

		Random rand = new Random(seed);
		Vec3 vec = Vec3.createVectorHelper(rand.nextDouble() - 0.5, rand.nextDouble() - 0.5, rand.nextDouble() - 0.5);
		vec = vec.normalize();

		double prevX;
		double prevY;
		double prevZ;

		for(int i = 0; i < min + rand.nextInt(max); i++) {

			prevX = x;
			prevY = y;
			prevZ = z;

			Vec3 dir = vec.normalize();
			dir.xCoord *= length * rand.nextFloat();
			dir.yCoord *= length * rand.nextFloat();
			dir.zCoord *= length * rand.nextFloat();

			x = prevX + dir.xCoord;
			y = prevY + dir.yCoord;
			z = prevZ + dir.zCoord;

			//Drillgon200: Neither tessellator nor buffer builder worked, and I'm too lazy to try and fix it.
			GL11.glLineWidth(5F);
			GL11.glBegin(3);
			GL11.glColor4f(r1, g1, b1, 1.0F);
			GL11.glVertex3d(prevX, prevY, prevZ);
			GL11.glVertex3d(x, y, z);
			GL11.glEnd();
			GL11.glLineWidth(2F);
			GL11.glBegin(3);
			GL11.glColor4f(r2, g2, b2, 1.0F);
			GL11.glVertex3d(prevX, prevY, prevZ);
			GL11.glVertex3d(x, y, z);
			GL11.glEnd();
			
			
		}

		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
		GL11.glPopMatrix();
	}

}
