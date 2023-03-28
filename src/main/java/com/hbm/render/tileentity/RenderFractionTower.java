package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.oil.TileEntityMachineFractionTower;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderFractionTower extends TileEntitySpecialRenderer<TileEntityMachineFractionTower> {

	@Override
	public boolean isGlobalRenderer(TileEntityMachineFractionTower te){
		return true;
	}
	
	@Override
	public void render(TileEntityMachineFractionTower te, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GlStateManager.enableLighting();
		GlStateManager.disableCull();
		
		bindTexture(ResourceManager.fraction_tower_tex);
		ResourceManager.fraction_tower.renderAll();
		
		GL11.glPopMatrix();
	}
}
