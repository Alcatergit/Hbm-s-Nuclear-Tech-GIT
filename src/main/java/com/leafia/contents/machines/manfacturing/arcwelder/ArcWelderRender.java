package com.leafia.contents.machines.manfacturing.arcwelder;

import com.hbm.blocks.BlockDummyable;
import com.leafia.dev.blockitems.LeafiaQuickModel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class ArcWelderRender extends TileEntitySpecialRenderer<TileEntity> {

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
			case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		GL11.glTranslated(-0.5, 0, 0);

		LeafiaQuickModel mdl = (LeafiaQuickModel)te;
		bindTexture(mdl.__getTexture());
		mdl.__getModel().renderAll();

		ArcWelderTE welder = (ArcWelderTE)te;
		if(welder.display != null) {
			GL11.glPushMatrix();
			GL11.glTranslated(0.0625D * 2.5D, 1.125D, 0D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glRotatef(90, 0F, 1F, 0F);
			GL11.glRotatef(-90, 1F, 0F, 0F);

			if(welder.display != null) {
				ItemStack stack = welder.display.copy();
				/*
				EntityItem item = new EntityItem(null, 0.0D, 0.0D, 0.0D, stack);
				item.getEntityItem().stackSize = 1;
				item.hoverStart = 0.0F;

				RenderItem.renderInFrame = true;
				GL11.glScaled(1.5, 1.5, 1.5);
				this.itemRenderer.doRender(item, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
				RenderItem.renderInFrame = false;*/
			}
			GL11.glPopMatrix();
		}

		GL11.glPopMatrix();
	}
}
