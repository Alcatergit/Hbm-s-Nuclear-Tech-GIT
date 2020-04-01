package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.render.misc.SoyuzLauncherPronter;
import com.hbm.tileentity.machine.TileEntitySoyuzLauncher;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderSoyuzLauncher extends TileEntitySpecialRenderer<TileEntitySoyuzLauncher> {

	@Override
	public boolean isGlobalRenderer(TileEntitySoyuzLauncher te) {
		return true;
	}
	
	@Override
	public void render(TileEntitySoyuzLauncher te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
		SoyuzLauncherPronter.prontLauncher();
		GL11.glPopMatrix();
	}
}
