package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.bomb.TileEntityNukeCustom;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderNukeCustom extends TileEntitySpecialRenderer<TileEntityNukeCustom> {

	@Override
	public boolean isGlobalRenderer(TileEntityNukeCustom te) {
		return true;
	}
	
	@Override
	public void render(TileEntityNukeCustom te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
		switch(te.getBlockMetadata())
		{
		case 2:
			GL11.glRotatef(0, 0F, 1F, 0F);
	        GL11.glTranslated(-2.0D, 0.0D, 0.0D); break;
		case 4:
			GL11.glRotatef(90, 0F, 1F, 0F);
	        GL11.glTranslated(-2.0D, 0.0D, 0.0D); break;
		case 3:
			GL11.glRotatef(180, 0F, 1F, 0F);
	        GL11.glTranslated(-2.0D, 0.0D, 0.0D); break;
		case 5:
			GL11.glRotatef(-90, 0F, 1F, 0F);
	        GL11.glTranslated(-2.0D, 0.0D, 0.0D); break;
		}

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.bomb_custom_tex);
        ResourceManager.bomb_boy.renderAll();
        GL11.glShadeModel(GL11.GL_FLAT);

        GlStateManager.enableCull();
        GL11.glPopMatrix();
	}
}
