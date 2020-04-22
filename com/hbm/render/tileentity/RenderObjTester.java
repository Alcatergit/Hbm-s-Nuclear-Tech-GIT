package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.render.misc.SoyuzPronter;
import com.hbm.tileentity.deco.TileEntityObjTester;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderObjTester extends TileEntitySpecialRenderer<TileEntityObjTester> {
	
	@Override
	public boolean isGlobalRenderer(TileEntityObjTester te) {
		return true;
	}
	
	@Override
	public void render(TileEntityObjTester te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        
        SoyuzPronter.prontSoyuz();

        GL11.glPopMatrix();
	}
}
