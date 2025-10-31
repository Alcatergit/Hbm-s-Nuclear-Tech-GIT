package com.leafia.contents.machines.processing.mixingvat;

import com.hbm.blocks.BlockDummyable;
import com.hbm.lib.RefStrings;
import com.leafia.dev.blockitems.LeafiaQuickModel;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class MixingVatRender extends TileEntitySpecialRenderer<TileEntity> {
	@Override
	public boolean isGlobalRenderer(TileEntity te) {
		return true;
	}
	static final ResourceLocation rsc = new ResourceLocation(RefStrings.MODID+":textures/models/xenoulexi/mixingvat_fluid.png");
	@Override
	public void render(TileEntity te,double x,double y,double z,float partialTicks,int destroyStage,float alpha) {
		LeafiaGls.pushMatrix();
		LeafiaGls.translate(x+0.5,y,z+0.5);
		switch(te.getBlockMetadata() - BlockDummyable.offset) {
			case 2: LeafiaGls.rotate(180, 0F, 1F, 0F); break;
			case 4: LeafiaGls.rotate(270, 0F, 1F, 0F); break;
			case 3: LeafiaGls.rotate(0, 0F, 1F, 0F); break;
			case 5: LeafiaGls.rotate(90, 0F, 1F, 0F); break;
		}
		LeafiaGls.translate(0.5,0,-1);
		LeafiaQuickModel mdl = (LeafiaQuickModel)te;
		bindTexture(mdl.__getTexture());
		mdl.__getModel().renderPart("Base");
		mdl.__getModel().renderPart("MixingBlade");
		mdl.__getModel().renderPart("VatGlass");
		LeafiaGls.shadeModel(GL11.GL_SMOOTH);
		mdl.__getModel().renderPart("Vat");
		mdl.__getModel().renderPart("Tanks");
		bindTexture(rsc);
		mdl.__getModel().renderPart("VatLiquid");
		LeafiaGls.shadeModel(GL11.GL_FLAT);
		LeafiaGls.popMatrix();
	}
}