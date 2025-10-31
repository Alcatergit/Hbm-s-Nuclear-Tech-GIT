package com.hbm.render.tileentity;

import com.hbm.tileentity.network.energy.TileEntityPylonBase;
import com.leafia.dev.blockitems.LeafiaQuickModel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class RenderPylonConnector extends TileEntitySpecialRenderer<TileEntity> {

	@Override
	public boolean isGlobalRenderer(TileEntity te) {
		return true;
	}

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
		switch(te.getBlockMetadata()) {
			case 0: GL11.glRotated(180, 1, 0, 0); break;
			case 1: break;
			case 2: GL11.glRotated(90, 1, 0, 0); GL11.glRotated(180, 0, 0, 1); break;
			case 3: GL11.glRotated(90, 1, 0, 0); break;
			case 4: GL11.glRotated(90, 1, 0, 0); GL11.glRotated(90, 0, 0, 1); break;
			case 5: GL11.glRotated(90, 1, 0, 0); GL11.glRotated(270, 0, 0, 1); break;
		}
		GL11.glTranslated(0, -0.5F, 0);
		LeafiaQuickModel mdl = (LeafiaQuickModel)te;
		bindTexture(mdl.__getTexture());
		mdl.__getModel().renderAll();
		GL11.glPopMatrix();

		RenderPylon.renderPowerLines((TileEntityPylonBase)te, x, y, z);
	}
}