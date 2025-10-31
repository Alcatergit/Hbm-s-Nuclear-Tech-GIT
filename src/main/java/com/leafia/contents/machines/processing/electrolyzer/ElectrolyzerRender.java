package com.leafia.contents.machines.processing.electrolyzer;

import com.hbm.blocks.BlockDummyable;
import com.leafia.dev.blockitems.LeafiaQuickModel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class ElectrolyzerRender extends TileEntitySpecialRenderer<TileEntity> {

	@Override
	public boolean isGlobalRenderer(TileEntity te) {
		return true;
	}

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);

		switch(te.getBlockMetadata() - BlockDummyable.offset) {
			case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		GL11.glRotated(180, 0, 1, 0);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		LeafiaQuickModel mdl = (LeafiaQuickModel)te;
		bindTexture(mdl.__getTexture());
		mdl.__getModel().renderAll();

		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}
}
