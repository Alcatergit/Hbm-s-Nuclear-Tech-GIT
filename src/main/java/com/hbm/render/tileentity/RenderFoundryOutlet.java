package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityFoundryOutlet;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderFoundryOutlet extends TileEntitySpecialRenderer<TileEntityFoundryOutlet> {

	@Override
	public void render(TileEntityFoundryOutlet te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
	}
}
