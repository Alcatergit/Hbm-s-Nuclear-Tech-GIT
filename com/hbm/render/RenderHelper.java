package com.hbm.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class RenderHelper {
	
	/**
	 * 
	 * @param lb
	 * @param rb
	 * @param rt
	 * @param lt
	 * @return left-bottom-right-top
	 */
	public static float[] getScreenAreaFromQuad(Vec3d lb, Vec3d rb, Vec3d rt, Vec3d lt){
		FloatBuffer mmatrix = GLAllocation.createDirectFloatBuffer(16);
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, mmatrix);
		FloatBuffer pmatrix = GLAllocation.createDirectFloatBuffer(16);
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, pmatrix);
		IntBuffer vport = GLAllocation.createDirectIntBuffer(16);
		GL11.glGetInteger(GL11.GL_VIEWPORT, vport);
		
		FloatBuffer[] points = new FloatBuffer[4];
		FloatBuffer buf0 = GLAllocation.createDirectFloatBuffer(3);
		Project.gluProject((float)lb.x, (float)lb.y, (float)lb.z, mmatrix, pmatrix, vport, buf0);
		points[0] = buf0;
		FloatBuffer buf1 = GLAllocation.createDirectFloatBuffer(3);
		Project.gluProject((float)rb.x, (float)rb.y, (float)rb.z, mmatrix, pmatrix, vport, buf1);
		points[1] = buf1;
		FloatBuffer buf2 = GLAllocation.createDirectFloatBuffer(3);
		Project.gluProject((float)rt.x, (float)rt.y, (float)rt.z, mmatrix, pmatrix, vport, buf2);
		points[2] = buf2;
		FloatBuffer buf3 = GLAllocation.createDirectFloatBuffer(3);
		Project.gluProject((float)lt.x, (float)lt.y, (float)lt.z, mmatrix, pmatrix, vport, buf3);
		points[3] = buf3;
		
		float top = buf0.get(1);
		float bottom = buf0.get(1);
		float left = buf0.get(0);
		float right = buf0.get(0);
		
		for(FloatBuffer buf : points){
			if(buf.get(0) > right){
				right = buf.get(0);
			}
			if(buf.get(0) < left){
				left = buf.get(0);
			}
			if(buf.get(1) > top){
				top = buf.get(1);
			}
			if(buf.get(1) < bottom){
				bottom = buf.get(1);
			}
		}
		//System.out.println(top);
		if(bottom < 0)
			bottom = 0;
		if(top > Minecraft.getMinecraft().displayHeight)
			top = Minecraft.getMinecraft().displayHeight;
		if(left < 0)
			left = 0;
		if(right > Minecraft.getMinecraft().displayWidth)
			right = Minecraft.getMinecraft().displayWidth;
		
		if(right <= 0 || top <= 0 || bottom >= Minecraft.getMinecraft().displayHeight || left >= Minecraft.getMinecraft().displayWidth)
			return null;
		//System.out.println(right);
		return new float[]{left, bottom, right, top};
	}
	
	public static void addVertexWithUV(double x, double y, double z, double u, double v){
		addVertexWithUV(x, y, z, u, v, Tessellator.getInstance());
	}
	public static void addVertex(double x, double y, double z){
		Tessellator.getInstance().getBuffer().pos(x, y, z).endVertex();
	}
	
	public static void addVertexWithUV(double x, double y, double z, double u, double v, Tessellator tes){
		BufferBuilder buf = tes.getBuffer();
		buf.pos(x, y, z).tex(u, v).endVertex();
	}
	public static void startDrawingTexturedQuads(){
		startDrawingTexturedQuads(Tessellator.getInstance());
	}
	public static void startDrawingQuads(){
		Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
	}
	public static void startDrawingTexturedQuads(Tessellator tes){
		tes.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
	}
	public static void draw(){
		draw(Tessellator.getInstance());
	}
	public static void draw(Tessellator tes){
		tes.draw();
	}
	
	public static void bindTexture(ResourceLocation resource){
		Minecraft.getMinecraft().renderEngine.bindTexture(resource);
	}
	public static void bindBlockTexture(){
		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
	}
	
	//Drillgon200: using GLStateManager for this because it caches color values
	public static void setColor(int color) {

		float red = (float) (color >> 16 & 255) / 255.0F;
		float green = (float) (color >> 8 & 255) / 255.0F;
		float blue = (float) (color & 255) / 255.0F;
		GlStateManager.color(red, green, blue, 1.0F);
	}
	public static void resetColor(){
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	
}
