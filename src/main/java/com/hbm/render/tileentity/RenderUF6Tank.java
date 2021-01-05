package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineUF6Tank;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderUF6Tank extends TileEntitySpecialRenderer<TileEntityMachineUF6Tank> {
	
	@Override
	public boolean isGlobalRenderer(TileEntityMachineUF6Tank te) {
		return true;
	}
	
	@Override
	public void render(TileEntityMachineUF6Tank te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
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

		bindTexture(ResourceManager.uf6_tex);
        ResourceManager.tank.renderAll();

        GL11.glPopMatrix();
	}
}
