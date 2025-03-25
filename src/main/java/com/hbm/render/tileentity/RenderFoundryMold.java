package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityFoundryMold;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderFoundryMold extends TileEntitySpecialRenderer<TileEntityFoundryMold> {

	@Override
	public void render(TileEntityFoundryMold te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		RenderFoundryLib.renderFoundry(te, x, y, z, partialTicks);
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
	}
}
