package com.hbm.render.tileentity;

import com.hbm.blocks.machine.WatzPump;
import com.hbm.main.ResourceManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class RenderWatzPump extends TileEntitySpecialRenderer<WatzPump.TileEntityWatzPump> {

	@Override
	public void render(WatzPump.TileEntityWatzPump te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

		GL11.glPushMatrix();

		GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);

		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.watz_pump_tex);
		ResourceManager.watz_pump.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
