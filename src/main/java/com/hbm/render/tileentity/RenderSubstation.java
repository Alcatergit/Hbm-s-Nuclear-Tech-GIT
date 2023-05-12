package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.network.energy.TileEntitySubstation;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderSubstation extends TileEntitySpecialRenderer<TileEntitySubstation> {

	@Override
	public void render(TileEntitySubstation sub, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
			GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
			switch(sub.getBlockMetadata() - BlockDummyable.offset) {
			case 4:
			case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
			case 2:
			case 3: GL11.glRotatef(90, 0F, 1F, 0F); break;
			}
			GL11.glEnable(GL11.GL_CULL_FACE);
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
			bindTexture(ResourceManager.substation_tex);
			ResourceManager.substation.renderAll();
			GlStateManager.shadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();

		RenderPylon.renderPowerLines(sub, x, y, z);
	}
}
