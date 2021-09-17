package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.tileentity.TileEntityKeypadBase;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderKeypadBase extends TileEntitySpecialRenderer<TileEntityKeypadBase> {

	@Override
	public void render(TileEntityKeypadBase te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslated(x+0.5, y, z+0.5);
		te.keypad.client().render();
		GL11.glPopMatrix();
	}
}
