package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ClientProxy;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityControlPanel;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderControlPanel extends TileEntitySpecialRenderer<TileEntityControlPanel> {

	@Override
	public void render(TileEntityControlPanel te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslated(x+0.5, y, z+0.5);
		GlStateManager.enableRescaleNormal();
		bindTexture(ResourceManager.control_panel0_tex);
		ResourceManager.control_panel0.renderAll();
		te.panel.transform.store(ClientProxy.AUX_GL_BUFFER);
		ClientProxy.AUX_GL_BUFFER.rewind();
		GL11.glMultMatrix(ClientProxy.AUX_GL_BUFFER);
		te.panel.render();
		GlStateManager.disableRescaleNormal();
		GL11.glPopMatrix();
	}
}
