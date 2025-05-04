package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.oil.TileEntityMachineOilWell;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderDerrick extends TileEntitySpecialRenderer<TileEntityMachineOilWell> {
    
    @Override
    public boolean isGlobalRenderer(TileEntityMachineOilWell te) {
    	return true;
    }
	
	@Override
	public void render(TileEntityMachineOilWell te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
		GL11.glRotatef(180, 0F, 1F, 0F);

        GlStateManager.shadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.derrick_tex);
        ResourceManager.derrick.renderAll();
        GlStateManager.shadeModel(GL11.GL_FLAT);

        GL11.glPopMatrix();
	}
}
