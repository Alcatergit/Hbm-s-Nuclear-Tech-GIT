package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.lib.RefStrings;
import com.hbm.render.RenderHelper;
import com.hbm.tileentity.conductor.TileEntityFFFluidDuctMk2;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class RenderFluidDuctMk2 extends TileEntitySpecialRenderer<TileEntityFFFluidDuctMk2> {

	public ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/blocks/fluid_duct.png");
	float pixel = 1F / 16F;
	float textureP = 1F / 32F;
	
	@Override
	public void render(TileEntityFFFluidDuctMk2 te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glTranslated(x, y, z);
		GlStateManager.disableLighting();
		this.bindTexture(texture);
		drawCore(te);
		for (int i = 0; i < te.connections.length; i++) {
			if (te.connections[i] != null) {
				drawConnection(te.connections[i], te.getType());
			}
		}
		GL11.glTranslated(-x, -y, -z);
		GlStateManager.enableLighting();
	}
	
	public void drawCore(TileEntity tileentity) {
		RenderHelper.startDrawingTexturedQuads();
		RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 5 * textureP, 5 * textureP);
		RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 5 * textureP, 0 * textureP);
		RenderHelper.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 0 * textureP, 0 * textureP);
		RenderHelper.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 0 * textureP, 5 * textureP);

		RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 5 * textureP);
		RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 0 * textureP);
		RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 0 * textureP, 0 * textureP);
		RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 0 * textureP, 5 * textureP);

		RenderHelper.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 5 * textureP);
		RenderHelper.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 0 * textureP);
		RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 0 * textureP, 0 * textureP);
		RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2, 11 * pixel / 2, 0 * textureP, 5 * textureP);

		RenderHelper.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 5 * textureP, 5 * textureP);
		RenderHelper.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 5 * textureP, 0 * textureP);
		RenderHelper.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 0 * textureP, 0 * textureP);
		RenderHelper.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 11 * pixel / 2, 0 * textureP, 5 * textureP);

		RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 5 * textureP, 5 * textureP);
		RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 0 * textureP);
		RenderHelper.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 0 * textureP, 0 * textureP);
		RenderHelper.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 0 * textureP, 5 * textureP);

		RenderHelper.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 5 * textureP, 5 * textureP);
		RenderHelper.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 0 * textureP);
		RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2, 11 * pixel / 2, 0 * textureP, 0 * textureP);
		RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 0 * textureP, 5 * textureP);
		RenderHelper.draw();
	}
	
	public void drawConnection(EnumFacing direction, Fluid type) {
		RenderHelper.startDrawingTexturedQuads();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		if (direction.equals(EnumFacing.UP)) {

		}
		if (direction.equals(EnumFacing.DOWN)) {
			GL11.glRotatef(180, 1, 0, 0);
		}
		if (direction.equals(EnumFacing.NORTH)) {
			GL11.glRotatef(270, 1, 0, 0);
		}
		if (direction.equals(EnumFacing.SOUTH)) {
			GL11.glRotatef(90, 1, 0, 0);
		}
		if (direction.equals(EnumFacing.EAST)) {
			GL11.glRotatef(270, 0, 0, 1);
		}
		if (direction.equals(EnumFacing.WEST)) {
			GL11.glRotatef(90, 0, 0, 1);
		}
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 5 * textureP, 5 * textureP);
		RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 1, 1 - 11 * pixel / 2, 10 * textureP, 5 * textureP);
		RenderHelper.addVertexWithUV(11 * pixel / 2, 1, 1 - 11 * pixel / 2, 10 * textureP, 0 * textureP);
		RenderHelper.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 5 * textureP, 0 * textureP);

		RenderHelper.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 5 * textureP);
		RenderHelper.addVertexWithUV(11 * pixel / 2, 1, 11 * pixel / 2, 10 * textureP, 5 * textureP);
		RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 1, 11 * pixel / 2, 10 * textureP, 0 * textureP);
		RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 0 * textureP);

		RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 5 * textureP);
		RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 1, 11 * pixel / 2, 10 * textureP, 5 * textureP);
		RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 1, 1 - 11 * pixel / 2, 10 * textureP, 0 * textureP);
		RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 5 * textureP, 0 * textureP);

		RenderHelper.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 5 * textureP, 5 * textureP);
		RenderHelper.addVertexWithUV(11 * pixel / 2, 1, 1 - 11 * pixel / 2, 10 * textureP, 5 * textureP);
		RenderHelper.addVertexWithUV(11 * pixel / 2, 1, 11 * pixel / 2, 10 * textureP, 0 * textureP);
		RenderHelper.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 0 * textureP);
		RenderHelper.draw();

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);
		float p = 0.01F;
		float n = -0.01F;
		int r = 169;
		int g = 169;
		int b = 169;
		if (type == null || FFUtils.getTextureFromFluid(type) == null) {
			RenderHelper.startDrawingColored(5);
				RenderHelper.addVertexColor(11 * pixel / 2 + p, 1, 1 - 11 * pixel / 2 + n, r, g, b, 255);
				RenderHelper.addVertexColor(11 * pixel / 2 + p, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2 + n, r, g, b, 255);
				RenderHelper.addVertexColor(1 - 11 * pixel / 2 + n, 1, 1 - 11 * pixel / 2 + n, r, g, b, 255);
				RenderHelper.addVertexColor(1 - 11 * pixel / 2 + n, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2 + n, r, g, b, 255);

				RenderHelper.addVertexColor(11 * pixel / 2 + p, 1, 11 * pixel / 2 + p, r, g, b, 255);
				RenderHelper.addVertexColor(11 * pixel / 2 + p, 1 - 11 * pixel / 2, 11 * pixel / 2 + p, r, g, b, 255);
				RenderHelper.addVertexColor(1 - 11 * pixel / 2 + n, 1, 11 * pixel / 2 + p, r, g, b, 255);
				RenderHelper.addVertexColor(1 - 11 * pixel / 2 + n, 1 - 11 * pixel / 2, 11 * pixel / 2 + p, r, g, b, 255);

				RenderHelper.addVertexColor(1 - 11 * pixel / 2 + n, 1, 11 * pixel / 2 + p, r, g, b, 255);
				RenderHelper.addVertexColor(1 - 11 * pixel / 2 + n, 1 - 11 * pixel / 2, 11 * pixel / 2 + p, r, g, b, 255);
				RenderHelper.addVertexColor(1 - 11 * pixel / 2 + n, 1, 1 - 11 * pixel / 2 + n, r, g, b, 255);
				RenderHelper.addVertexColor(1 - 11 * pixel / 2 + n, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2 + n, r, g, b, 255);

				RenderHelper.addVertexColor(11 * pixel / 2 + p, 1, 11 * pixel / 2 + p, r, g, b, 255);
				RenderHelper.addVertexColor(11 * pixel / 2 + p, 1 - 11 * pixel / 2, 11 * pixel / 2 + p, r, g, b, 255);
				RenderHelper.addVertexColor(11 * pixel / 2 + p, 1, 1 - 11 * pixel / 2 + n, r, g, b, 255);
				RenderHelper.addVertexColor(11 * pixel / 2 + p, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2 + n, r, g, b, 255);
			RenderHelper.draw();
		} else {
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			TextureAtlasSprite icon = FFUtils.getTextureFromFluid(type);
			float iconMaxU = icon.getInterpolatedU(10);
			float iconMinU = icon.getInterpolatedU(7);
			float iconMaxV = icon.getInterpolatedV(13);
			float iconMinV = icon.getInterpolatedV(12);
		//	bindTexture(Minecraft.getMinecraft().getTextureManager(), type.getSpriteNumber());
			RenderHelper.bindBlockTexture();

			RenderHelper.startDrawingTexturedQuads();
			
				
				RenderHelper.addVertexWithUV(7*pixel-1*pixel/2,13*pixel-1*pixel/2+0.013, 0+5*pixel+1*pixel/2, iconMaxU, iconMinV);
				RenderHelper.addVertexWithUV(10*pixel-1*pixel/2,13*pixel-1*pixel/2+0.013, 0+5*pixel+1*pixel/2, iconMinU, iconMinV);
				RenderHelper.addVertexWithUV(10*pixel-1*pixel/2,12*pixel-1*pixel/2+0.006, 0+5*pixel+1*pixel/2, iconMinU, iconMaxV);
				RenderHelper.addVertexWithUV(7*pixel-1*pixel/2,12*pixel-1*pixel/2+0.006, 0+5*pixel+1*pixel/2, iconMaxU, iconMaxV);
				
				RenderHelper.addVertexWithUV(7*pixel-1*pixel/2,13*pixel-1*pixel/2+0.013, 0+5*pixel+1*pixel/2 + 5*pixel, iconMaxU, iconMinV);
				RenderHelper.addVertexWithUV(10*pixel-1*pixel/2,13*pixel-1*pixel/2+0.013, 0+5*pixel+1*pixel/2 + 5*pixel, iconMinU, iconMinV);
				RenderHelper.addVertexWithUV(10*pixel-1*pixel/2,12*pixel-1*pixel/2+0.006, 0+5*pixel+1*pixel/2 + 5*pixel, iconMinU, iconMaxV);
				RenderHelper.addVertexWithUV(7*pixel-1*pixel/2,12*pixel-1*pixel/2+0.006, 0+5*pixel+1*pixel/2 + 5*pixel, iconMaxU, iconMaxV);
				
				RenderHelper.addVertexWithUV(11*pixel-1*pixel/2,13*pixel-1*pixel/2+0.013, 0+1*pixel+1*pixel/2 + 5*pixel, iconMaxU, iconMinV);
				RenderHelper.addVertexWithUV(11*pixel-1*pixel/2,13*pixel-1*pixel/2+0.013, 0+4*pixel+1*pixel/2 + 5*pixel, iconMinU, iconMinV);
				RenderHelper.addVertexWithUV(11*pixel-1*pixel/2,12*pixel-1*pixel/2+0.006, 0+4*pixel+1*pixel/2 + 5*pixel, iconMinU, iconMaxV);
				RenderHelper.addVertexWithUV(11*pixel-1*pixel/2,12*pixel-1*pixel/2+0.006, 0+1*pixel+1*pixel/2 + 5*pixel, iconMaxU, iconMaxV);
				
				RenderHelper.addVertexWithUV(6*pixel-1*pixel/2,13*pixel-1*pixel/2+0.013, 0+1*pixel+1*pixel/2 + 5*pixel, iconMaxU, iconMinV);
				RenderHelper.addVertexWithUV(6*pixel-1*pixel/2,13*pixel-1*pixel/2+0.013, 0+4*pixel+1*pixel/2 + 5*pixel, iconMinU, iconMinV);
				RenderHelper.addVertexWithUV(6*pixel-1*pixel/2,12*pixel-1*pixel/2+0.006, 0+4*pixel+1*pixel/2 + 5*pixel, iconMinU, iconMaxV);
				RenderHelper.addVertexWithUV(6*pixel-1*pixel/2,12*pixel-1*pixel/2+0.006, 0+1*pixel+1*pixel/2 + 5*pixel, iconMaxU, iconMaxV);

			RenderHelper.draw();
			
	/*	RenderHelper.startDrawingQuads();
			RenderHelper.addVertexWithUV(1 - 11 * pixel / 2 + n, 1 + 1, 1 - 11 * pixel / 2 + n, iconMaxU, iconMinV);
			RenderHelper.addVertexWithUV(1 - 11 * pixel / 2 + n, 1 - 11 * pixel / 2 + 1, 1 - 11 * pixel / 2 + n, iconMinU, iconMinV);
			RenderHelper.addVertexWithUV(11 * pixel / 2 + p, 1 + 1, 1 - 11 * pixel / 2 + n, iconMinU, iconMaxV);
			RenderHelper.addVertexWithUV(11 * pixel / 2 + p, 1 - 11 * pixel / 2 + 1, 1 - 11 * pixel / 2 + n, iconMaxU, iconMaxV);
			
		RenderHelper.draw();

		RenderHelper.startDrawingQuads();
			RenderHelper.addVertexWithUV(11 * pixel / 2 + p, 1, 11 * pixel / 2 + p, iconMinU, iconMaxV);
			RenderHelper.addVertexWithUV(11 * pixel / 2 + p, 1 - 11 * pixel / 2, 11 * pixel / 2 + p, iconMaxU, iconMaxV);
			RenderHelper.addVertexWithUV(1 - 11 * pixel / 2 + n, 1, 11 * pixel / 2 + p, iconMaxU, iconMinV);
			RenderHelper.addVertexWithUV(1 - 11 * pixel / 2 + n, 1 - 11 * pixel / 2, 11 * pixel / 2 + p, iconMinU, iconMinV);
		RenderHelper.draw();

		RenderHelper.startDrawingQuads();
			RenderHelper.addVertexWithUV(1 - 11 * pixel / 2 + n, 1, 11 * pixel / 2 + p, iconMinU, iconMaxV);
			RenderHelper.addVertexWithUV(1 - 11 * pixel / 2 + n, 1 - 11 * pixel / 2, 11 * pixel / 2 + p, iconMaxU, iconMaxV);
			RenderHelper.addVertexWithUV(1 - 11 * pixel / 2 + n, 1, 1 - 11 * pixel / 2 + n, iconMaxU, iconMinV);
			RenderHelper.addVertexWithUV(1 - 11 * pixel / 2 + n, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2 + n, iconMinU, iconMinV);
		RenderHelper.draw();

		RenderHelper.startDrawingQuads();
			RenderHelper.addVertexWithUV(11 * pixel / 2 + p, 1, 11 * pixel / 2 + p, iconMinU, iconMaxV);
			RenderHelper.addVertexWithUV(11 * pixel / 2 + p, 1 - 11 * pixel / 2, 11 * pixel / 2 + p, iconMaxU, iconMaxV);
			RenderHelper.addVertexWithUV(11 * pixel / 2 + p, 1, 1 - 11 * pixel / 2 + n, iconMaxU, iconMinV);
			RenderHelper.addVertexWithUV(11 * pixel / 2 + p, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2 + n, iconMinU, iconMinV);
		RenderHelper.draw(); */
			
			this.bindTexture(texture);

		}
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		if (direction.equals(EnumFacing.UP)) {

		}
		if (direction.equals(EnumFacing.DOWN)) {
			GL11.glRotatef(-180, 1, 0, 0);
		}
		if (direction.equals(EnumFacing.NORTH)) {
			GL11.glRotatef(-270, 1, 0, 0);
		}
		if (direction.equals(EnumFacing.SOUTH)) {
			GL11.glRotatef(-90, 1, 0, 0);
		}
		if (direction.equals(EnumFacing.EAST)) {
			GL11.glRotatef(-270, 0, 0, 1);
		}
		if (direction.equals(EnumFacing.WEST)) {
			GL11.glRotatef(-90, 0, 0, 1);
		}
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
	}
	
}
