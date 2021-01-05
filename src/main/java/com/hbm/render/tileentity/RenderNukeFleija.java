package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.bomb.TileEntityNukeFleija;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderNukeFleija extends TileEntitySpecialRenderer<TileEntityNukeFleija> {
    
    @Override
    public void render(TileEntityNukeFleija te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    	GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
		switch(te.getBlockMetadata())
		{
		case 5:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 2:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		bindTexture(ResourceManager.bomb_fleija_tex);
        ResourceManager.bomb_fleija.renderAll();
        GL11.glPopMatrix();
    }
}
