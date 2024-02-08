package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineRadar;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderRadar extends TileEntitySpecialRenderer<TileEntityMachineRadar> {

	@Override
	public boolean isGlobalRenderer(TileEntityMachineRadar te) {
		return true;
	}
	
	@Override
	public void render(TileEntityMachineRadar radar, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
		GL11.glRotatef(180, 0F, 1F, 0F);

        bindTexture(ResourceManager.radar_base_tex);
        ResourceManager.radar.renderPart("Base");

        if(radar.power > 0)
			GL11.glRotatef((-System.currentTimeMillis() / 10) % 360, 0F, 1F, 0F);
		GL11.glTranslated(-0.125D, 0, 0);

		bindTexture(ResourceManager.radar_dish_tex);
		ResourceManager.radar.renderPart("Dish");

        GL11.glPopMatrix();
        
        GlStateManager.enableCull();
	}
}
