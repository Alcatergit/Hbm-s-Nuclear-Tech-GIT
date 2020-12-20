package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachinePlasmaHeater;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderPlasmaHeater extends TileEntitySpecialRenderer<TileEntityMachinePlasmaHeater> {

	@Override
	public boolean isGlobalRenderer(TileEntityMachinePlasmaHeater te) {
		return true;
	}
	
	@Override
	public void render(TileEntityMachinePlasmaHeater te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + 0.5F, (float)y, (float)z + 0.5F);

		GlStateManager.enableCull();
		GlStateManager.enableLighting();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);

		switch(te.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
		}

		GL11.glTranslatef(0, 0, 18);

        bindTexture(ResourceManager.iter_microwave);
        ResourceManager.iter.renderPart("Microwave");

        GlStateManager.shadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
