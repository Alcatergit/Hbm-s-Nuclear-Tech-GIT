package com.hbm.tileentity.bomb;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderNukeMike extends TileEntitySpecialRenderer<TileEntityNukeMike> {

	@Override
	public boolean isGlobalRenderer(TileEntityNukeMike te) {
		return true;
	}
	
	@Override
	public void render(TileEntityNukeMike te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
		switch(te.getBlockMetadata())
		{
		case 3:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 2:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(-90, 0F, 1F, 0F); break;
		}

		bindTexture(ResourceManager.bomb_mike_tex);
        ResourceManager.bomb_mike.renderAll();

        GlStateManager.enableCull();
        GL11.glPopMatrix();
	}
}
