package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.network.energy.TileEntityPylonLarge;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderPylonLarge extends TileEntitySpecialRenderer<TileEntityPylonLarge> {

	@Override
	public boolean isGlobalRenderer(TileEntityPylonLarge te) {
		return true;
	}

	@Override
	public void render(TileEntityPylonLarge pyl, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
			GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
			switch(pyl.getBlockMetadata() - BlockDummyable.offset) {
			case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 4: GL11.glRotatef(135, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(45, 0F, 1F, 0F); break;
			}
			bindTexture(ResourceManager.pylon_large_tex);
			ResourceManager.pylon_large.renderAll();
		GL11.glPopMatrix();

		RenderPylon.renderPowerLines(pyl, x, y, z);
	}
}
