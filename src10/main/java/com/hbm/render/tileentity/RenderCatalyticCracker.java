package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.oil.TileEntityMachineCatalyticCracker;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderCatalyticCracker extends TileEntitySpecialRenderer<TileEntityMachineCatalyticCracker> {
	
	@Override
	public void render(TileEntityMachineCatalyticCracker te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

		GlStateManager.pushMatrix();

        GlStateManager.translate(x + 0.5D, y, z + 0.5D);
		GlStateManager.enableLighting();
        GlStateManager.disableCull();

		switch(te.getBlockMetadata() - BlockDummyable.offset) {
		case 2:
			GlStateManager.rotate(90, 0F, 1F, 0F);
			break;
		case 4:
			GlStateManager.rotate(180, 0F, 1F, 0F);
			break;
		case 3:
			GlStateManager.rotate(270, 0F, 1F, 0F);
			break;
		case 5:
			GlStateManager.rotate(0, 0F, 1F, 0F);
			break;
		}
		
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.cracking_tower_tex);
		ResourceManager.cracking_tower.renderAll();
		GlStateManager.shadeModel(GL11.GL_FLAT);

		GlStateManager.enableCull();
        GlStateManager.popMatrix();
	}
}
