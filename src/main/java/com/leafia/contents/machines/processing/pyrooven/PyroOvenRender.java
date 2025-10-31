package com.leafia.contents.machines.processing.pyrooven;

import com.hbm.blocks.BlockDummyable;
import com.hbm.util.BobMathUtil;
import com.leafia.dev.blockitems.LeafiaQuickModel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class PyroOvenRender extends TileEntitySpecialRenderer<TileEntity> {

	@Override
	public boolean isGlobalRenderer(TileEntity te) {
		return true;
	}

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);

		switch(te.getBlockMetadata() - BlockDummyable.offset) {
			case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
		}

		PyroOvenTE pyro = (PyroOvenTE)te;
		float anim = pyro.prevAnim + (pyro.anim - pyro.prevAnim) * partialTicks;

		GL11.glShadeModel(GL11.GL_SMOOTH);
		LeafiaQuickModel mdl = (LeafiaQuickModel)te;
		bindTexture(mdl.__getTexture());
		mdl.__getModel().renderPart("Oven");

		GL11.glPushMatrix();
		GL11.glTranslated(BobMathUtil.sps(anim * 0.125) / 2 - 0.5, 0, 0);
		mdl.__getModel().renderPart("Slider");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(1.5, 0, 1.5);
		GL11.glRotated(anim * -15D % 360D, 0, 1, 0);
		GL11.glTranslated(-1.5, 0, -1.5);
		mdl.__getModel().renderPart("Fan");
		GL11.glPopMatrix();

		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}
}
