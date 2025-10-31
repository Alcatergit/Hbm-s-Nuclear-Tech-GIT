package com.leafia.contents.machines.processing.assemtable;

import com.hbm.blocks.BlockDummyable;
import com.leafia.dev.blockitems.LeafiaQuickModel;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class AssemTableRender extends TileEntitySpecialRenderer<TileEntity> {
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
		mdl.__getModel().renderAllExcept("Handle");
		LeafiaGls.translate(0.5,1.0625,-0.75);
		LeafiaGls.rotate((float)te.getWorld().rand.nextGaussian()*180,1,0,0);
		LeafiaGls.translate(-0.5,-1.0625,0.75);
		mdl.__getModel().renderPart("Handle");
		LeafiaGls.popMatrix();
	}
}
