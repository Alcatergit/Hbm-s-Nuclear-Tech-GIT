package com.hbm.render;

import java.nio.FloatBuffer;

import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import com.hbm.entity.missile.EntityCarrier;
import com.hbm.entity.missile.EntityMissileAntiBallistic;
import com.hbm.entity.missile.EntityMissileBaseAdvanced;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
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
	
	
	public static TextureAtlasSprite getItemTexture(Item item, int meta){
		return getItemTexture(new ItemStack(item, 1, meta));
	}
	
	public static TextureAtlasSprite getItemTexture(Item item){
		return getItemTexture(item, 0);
	}
	
	public static TextureAtlasSprite getItemTexture(ItemStack item){
		return Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(item, null, null).getParticleTexture();
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

	public static void startDrawingColored(int i) {
		Tessellator.getInstance().getBuffer().begin(i, DefaultVertexFormats.POSITION_COLOR);
	}
	
	public static void addVertexColor(double x, double y, double z, int red, int green, int blue, int alpha){
		Tessellator.getInstance().getBuffer().pos(x, y, z).color(red, green, blue, alpha).endVertex();;
	}


	public static void renderAll(IBakedModel boxcar) {
		Tessellator tes = Tessellator.getInstance();
		BufferBuilder buf = tes.getBuffer();
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
    	for(BakedQuad quad : boxcar.getQuads(null, null, 0)){
    		buf.addVertexData(quad.getVertexData());
    	}
    	tes.draw();
		
	}
	
	/**
	 * Helper method for getting the real render position from a missile, which updates its position more than once per game tick.
	 * @param missile - the missile to get the actual render pos from
	 * @param partialTicks - render partial ticks
	 * @return A three element double array, containing the render pos x at index 0, y at index 1, and z at index 2
	 */
	public static double[] getRenderPosFromMissile(EntityMissileBaseAdvanced missile, float partialTicks){
		if(missile.prevPosX2 == 0){
			missile.prevPosX2 = missile.posX;
		}
		if(missile.prevPosY2 == 0){
			missile.prevPosY2 = missile.posY;
		}
		if(missile.prevPosZ2 == 0){
			missile.prevPosZ2 = missile.posZ;
		}
		double d0 = missile.prevPosX2 + (missile.posX - missile.prevPosX2) * (double) partialTicks;
		double d1 = missile.prevPosY2 + (missile.posY - missile.prevPosY2) * (double) partialTicks;
		double d2 = missile.prevPosZ2 + (missile.posZ - missile.prevPosZ2) * (double) partialTicks;
		Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
		double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
		double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
		double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;
		
		return new double[]{d0 - d3, d1 - d4, d2 - d5};
	}
	
	/**
	 * Helper method for getting the real render position from a missile, which updates its position more than once per game tick.
	 * @param missile - the missile to get the actual render pos from
	 * @param partialTicks - render partial ticks
	 * @return A three element double array, containing the render pos x at index 0, y at index 1, and z at index 2
	 */
	public static double[] getRenderPosFromMissile(EntityMissileAntiBallistic missile, float partialTicks){
		if(missile.prevPosX2 == 0){
			missile.prevPosX2 = missile.posX;
		}
		if(missile.prevPosY2 == 0){
			missile.prevPosY2 = missile.posY;
		}
		if(missile.prevPosZ2 == 0){
			missile.prevPosZ2 = missile.posZ;
		}
		double d0 = missile.prevPosX2 + (missile.posX - missile.prevPosX2) * (double) partialTicks;
		double d1 = missile.prevPosY2 + (missile.posY - missile.prevPosY2) * (double) partialTicks;
		double d2 = missile.prevPosZ2 + (missile.posZ - missile.prevPosZ2) * (double) partialTicks;
		Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
		double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
		double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
		double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;
		
		return new double[]{d0 - d3, d1 - d4, d2 - d5};
	}
	
	/**
	 * Helper method for getting the real render position from a missile, which updates its position more than once per game tick.
	 * @param missile - the missile to get the actual render pos from
	 * @param partialTicks - render partial ticks
	 * @return A three element double array, containing the render pos x at index 0, y at index 1, and z at index 2
	 */
	public static double[] getRenderPosFromMissile(EntityCarrier missile, float partialTicks){
		if(missile.prevPosX2 == 0){
			missile.prevPosX2 = missile.posX;
		}
		if(missile.prevPosY2 == 0){
			missile.prevPosY2 = missile.posY;
		}
		if(missile.prevPosZ2 == 0){
			missile.prevPosZ2 = missile.posZ;
		}
		double d0 = missile.prevPosX2 + (missile.posX - missile.prevPosX2) * (double) partialTicks;
		double d1 = missile.prevPosY2 + (missile.posY - missile.prevPosY2) * (double) partialTicks;
		double d2 = missile.prevPosZ2 + (missile.posZ - missile.prevPosZ2) * (double) partialTicks;
		Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
		double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
		double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
		double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;
		
		return new double[]{d0 - d3, d1 - d4, d2 - d5};
	}
	
}
