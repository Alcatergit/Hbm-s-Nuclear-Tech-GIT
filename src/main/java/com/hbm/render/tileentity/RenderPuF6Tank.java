package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachinePuF6Tank;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderPuF6Tank extends TileEntitySpecialRenderer<TileEntityMachinePuF6Tank> {
	
	@Override
	public boolean isGlobalRenderer(TileEntityMachinePuF6Tank te) {
		return true;
	}
	
	@Override
	public void render(TileEntityMachinePuF6Tank te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
		switch(te.getBlockMetadata())
		{
		case 4:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 2:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		bindTexture(ResourceManager.puf6_tex);
        ResourceManager.tank.renderAll();

        GL11.glPopMatrix();
	}
}
