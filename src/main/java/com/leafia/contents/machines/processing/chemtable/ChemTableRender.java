package com.leafia.contents.machines.processing.chemtable;

import com.hbm.blocks.BlockDummyable;
import com.leafia.dev.blockitems.LeafiaQuickModel;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class ChemTableRender extends TileEntitySpecialRenderer<TileEntity> {
	@Override
	public void render(TileEntity te,double x,double y,double z,float partialTicks,int destroyStage,float alpha) {
		LeafiaGls.pushMatrix();
		LeafiaGls.translate(x + 0.5, y, z + 0.5);
		switch(te.getBlockMetadata() - BlockDummyable.offset) {
			case 2: LeafiaGls.rotate(90, 0F, 1F, 0F); break;
			case 4: LeafiaGls.rotate(180, 0F, 1F, 0F); break;
			case 3: LeafiaGls.rotate(270, 0F, 1F, 0F); break;
			case 5: LeafiaGls.rotate(0, 0F, 1F, 0F); break;
		}
		LeafiaGls.translate(0,0,-0.5);
		LeafiaQuickModel mdl = (LeafiaQuickModel)te;
		bindTexture(mdl.__getTexture());
		LeafiaGls.enableBlend();
		LeafiaGls.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		mdl.__getModel().renderPart("Table");
		LeafiaGls.shadeModel(GL11.GL_SMOOTH);
		GL11.glAlphaFunc(GL11.GL_ALWAYS, 0);
		mdl.__getModel().renderPart("Bottles");
		mdl.__getModel().renderPart("Outlines");
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		LeafiaGls.shadeModel(GL11.GL_FLAT);
		LeafiaGls.disableBlend();
		LeafiaGls.popMatrix();
	}
}
