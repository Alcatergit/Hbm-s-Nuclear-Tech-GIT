package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityFoundryBasin;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderFoundryBasin extends TileEntitySpecialRenderer<TileEntityFoundryBasin> {

	@Override
	public void render(TileEntityFoundryBasin te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		RenderFoundryLib.renderFoundry(te, x, y, z, partialTicks);
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
	}
}
