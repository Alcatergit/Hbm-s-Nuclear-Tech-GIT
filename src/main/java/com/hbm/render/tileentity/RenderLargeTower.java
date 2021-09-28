package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityTowerLarge;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderLargeTower extends TileEntitySpecialRenderer<TileEntityTowerLarge> {

	@Override
	public boolean isGlobalRenderer(TileEntityTowerLarge te){
		return true;
	}
	
	@Override
	public void render(TileEntityTowerLarge te, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GlStateManager.enableLighting();
		GlStateManager.disableCull();
		
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.tower_large_tex);
		ResourceManager.tower_large.renderAll();
		GlStateManager.shadeModel(GL11.GL_FLAT);
		
		GlStateManager.enableCull();
		GL11.glPopMatrix();
	}
}
