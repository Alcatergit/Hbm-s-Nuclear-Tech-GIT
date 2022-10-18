package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachinePumpjack;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class RenderPumpjack extends TileEntitySpecialRenderer<TileEntityMachinePumpjack> {
	
	int rotation;
	
	@Override
	public boolean isGlobalRenderer(TileEntityMachinePumpjack te) {
		return true;
	}
	
	@Override
	public void render(TileEntityMachinePumpjack te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y, z + 0.5);
		switch(te.getBlockMetadata())
		{
		case 2:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		float rotation = te.rotation;

		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		this.bindTexture(ResourceManager.pumpjack_tex);
		ResourceManager.pumpjack.renderPart("Base");

		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.5, -5.5);
		GL11.glRotatef(rotation - 90, 1, 0, 0);
		GL11.glTranslated(0, -1.5, 5.5);
		ResourceManager.pumpjack.renderPart("Rotor");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, 3.5, -3.5);
		GL11.glRotated(Math.toDegrees(Math.sin(Math.toRadians(rotation))) * 0.25, 1, 0, 0);
		GL11.glTranslated(0, -3.5, 3.5);
		ResourceManager.pumpjack.renderPart("Head");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, -Math.sin(Math.toRadians(rotation)), 0);
		ResourceManager.pumpjack.renderPart("Carriage");
		GL11.glPopMatrix();
		
		renderTileEntityAt4(rotation);
		GlStateManager.enableCull();
	}
    
	public void renderTileEntityAt4(float rotation)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(0, 1, 0);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();

		float j = (float) Math.sin((rotation / (180 / Math.PI))) * 15;
		float t = (float) Math.sin((rotation / (180 / Math.PI))) * 0.5F;
		float u = (float) Math.sin(((rotation + 90) / (180 / Math.PI)));
		float v = (float) Math.sin((j / (180 / Math.PI)));
		float w = (float) Math.sin(((j + 90) / (180 / Math.PI)));
		drawConnection(0.53125, 0.5 + t, -5.4 - u, 0.53125, 2.5 + v, -2.5 - w);
		drawConnection(-0.53125, 0.5 + t, -5.4 - u, -0.53125, 2.5 + v, -2.5 - w);

		double height = -Math.sin(Math.toRadians(rotation));
		drawConnection(0.0D, -0.75D, 0.0D, 0.0D, height + 1D, 0.0D);

		drawConnection(0.25D, height + 1D, 0.0D, 0.25D, height + 2.5D, 0.0D);
		drawConnection(-0.25D, height + 1D, 0.0D, -0.25D, height + 2.5D, 0.0D);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
	
	public void drawConnection(double x, double y, double z, double a, double b, double c) {
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buf = tessellator.getBuffer();
        buf.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR);
        buf.pos(x + 0.05F, y, z).color(0.6F, 0.6F, 0.6F, 1.0F).endVertex();
        buf.pos(x - 0.05F, y, z).color(0.6F, 0.6F, 0.6F, 1.0F).endVertex();
        buf.pos(a + 0.05F, b, c).color(0.6F, 0.6F, 0.6F, 1.0F).endVertex();
        buf.pos(a - 0.05F, b, c).color(0.6F, 0.6F, 0.6F, 1.0F).endVertex();
        tessellator.draw();
        buf.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR);
        buf.pos(x, y, z + 0.05F).color(0.6F, 0.6F, 0.6F, 1.0F).endVertex();
        buf.pos(x, y, z - 0.05F).color(0.6F, 0.6F, 0.6F, 1.0F).endVertex();
        buf.pos(a, b, c + 0.05F).color(0.6F, 0.6F, 0.6F, 1.0F).endVertex();
        buf.pos(a, b, c - 0.05F).color(0.6F, 0.6F, 0.6F, 1.0F).endVertex();
        tessellator.draw();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.enableCull();
	}
}
