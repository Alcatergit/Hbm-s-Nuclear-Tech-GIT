package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.RenderHelper;
import com.hbm.tileentity.conductor.TileEntityFFOilDuct;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class RenderOilDuct extends TileEntitySpecialRenderer<TileEntityFFOilDuct> {

	public ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/blocks/oil_duct_alt.png");
	float pixel = 1F/16F;
	float textureP = 1F / 32F;
	
	@Override
	public void render(TileEntityFFOilDuct tileentity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GlStateManager.disableLighting();
		this.bindTexture(texture);
		drawCore(tileentity);
		TileEntityFFOilDuct cable = (TileEntityFFOilDuct) tileentity;
		for(int i = 0; i < cable.connections.length; i++)
		{
			if(cable.connections[i] != null)
			{
				drawConnection(cable.connections[i]);
			}
		}
		GL11.glPopMatrix();
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
	
	public void drawConnection(EnumFacing direction) {
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
