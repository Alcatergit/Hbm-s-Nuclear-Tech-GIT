package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.RenderHelper;
import com.hbm.tileentity.conductor.TileEntityCable;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class RenderCable extends TileEntitySpecialRenderer<TileEntityCable> {

	public ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/blocks/red_cable.png");
	float pixel = 1F/16F;
	float textureP = 1F / 32F;
	
	@Override
	public void render(TileEntityCable te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glTranslated(x, y, z);
		GlStateManager.disableLighting();
		this.bindTexture(texture);
		drawCore(te);
		;
		for(int i = 0; i < te.connections.length; i++)
		{
			if(te.connections[i] != null)
			{
				drawConnection(te.connections[i]);
			}
		}
		GL11.glTranslated(-x, -y, -z);
		GlStateManager.enableLighting();
	}
	
	public void drawCore(TileEntity tileentity) {
		RenderHelper.startDrawingTexturedQuads();
			RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2,  1 - 11 * pixel / 2, 5 * textureP, 5 * textureP);
			RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 5 * textureP, 0 * textureP);
			RenderHelper.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 0 * textureP, 0 * textureP);
			RenderHelper.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 0 * textureP, 5 * textureP);

			RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2,  11 * pixel / 2, 5 * textureP, 5 * textureP);
			RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 0 * textureP);
			RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 0 * textureP, 0 * textureP);
			RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 0 * textureP, 5 * textureP);

			RenderHelper.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 5 * textureP);
			RenderHelper.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 0 * textureP);
			RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 0 * textureP, 0 * textureP);
			RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2,  11 * pixel / 2, 0 * textureP, 5 * textureP);

			RenderHelper.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 5 * textureP, 5 * textureP);
			RenderHelper.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 5 * textureP, 0 * textureP);
			RenderHelper.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 0 * textureP, 0 * textureP);
			RenderHelper.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2,  11 * pixel / 2, 0 * textureP, 5 * textureP);

			RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 5 * textureP, 5 * textureP);
			RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 0 * textureP);
			RenderHelper.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 0 * textureP, 0 * textureP);
			RenderHelper.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2,  1 - 11 * pixel / 2, 0 * textureP, 5 * textureP);

			RenderHelper.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2,  1 - 11 * pixel / 2, 5 * textureP, 5 * textureP);
			RenderHelper.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 0 * textureP);
			RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2, 11 * pixel / 2, 0 * textureP, 0 * textureP);
			RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 0 * textureP, 5 * textureP);
		RenderHelper.draw();
		
		// Muehsam muss ich hier im BSH meine genialen Mods schreiben, obwohl ich die Zeit eigentlich doch besser nutzen koennte.
		// Da mir das aber Spass macht, wird auch in Zukunft gutes Zeug von mir geben (und damit meine ich NICHT Drogen, etc.)
		// Danke.
		
		//I didn't write this, but I'm gonna leave it there.
	}
	
	public void drawConnection(EnumFacing direction)
	{
		RenderHelper.startDrawingTexturedQuads();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			if(direction.equals(EnumFacing.UP))
			{
		
			}
			if(direction.equals(EnumFacing.DOWN))
			{
				GL11.glRotatef(180, 1, 0, 0);
			}
			if(direction.equals(EnumFacing.NORTH))
			{
				GL11.glRotatef(270, 1, 0, 0);
			}
			if(direction.equals(EnumFacing.SOUTH))
			{
				GL11.glRotatef(90, 1, 0, 0);
			}
			if(direction.equals(EnumFacing.EAST))
			{
				GL11.glRotatef(270, 0, 0, 1);
			}
			if(direction.equals(EnumFacing.WEST))
			{
				GL11.glRotatef(90, 0, 0, 1);
			}
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			
			RenderHelper.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2,  1 - 11 * pixel / 2, 5 * textureP, 5 * textureP);
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
		if(direction.equals(EnumFacing.UP))
		{
		
		}
		if(direction.equals(EnumFacing.DOWN))
		{
			GL11.glRotatef(-180, 1, 0, 0);
		}
		if(direction.equals(EnumFacing.NORTH))
		{
			GL11.glRotatef(-270, 1, 0, 0);
		}
		if(direction.equals(EnumFacing.SOUTH))
		{
			GL11.glRotatef(-90, 1, 0, 0);
		}
		if(direction.equals(EnumFacing.EAST))
		{
			GL11.glRotatef(-270, 0, 0, 1);
		}
		if(direction.equals(EnumFacing.WEST))
		{
			GL11.glRotatef(-90, 0, 0, 1);
		}
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
	}
	
}
