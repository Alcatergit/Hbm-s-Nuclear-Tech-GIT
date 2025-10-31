package com.leafia.contents.machines.reactors.pwr.blocks.components.vent.outlet;

import com.hbm.hfr.render.loader.HFRWavefrontObject;
import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.leafia.contents.machines.reactors.pwr.blocks.components.vent.PWRVentBlockBase;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class PWRVentOutletRender extends TileEntitySpecialRenderer<PWRVentOutletTE> {

	static final ResourceLocation concrete =
			new ResourceLocation(RefStrings.MODID, "textures/blocks/concrete_gray.png");
	static final ResourceLocation hazard =
			new ResourceLocation(RefStrings.MODID, "textures/blocks/hadron_plating_striped.png");
	static final ResourceLocation grate =
			new ResourceLocation(RefStrings.MODID, "textures/blocks/scaffold_steel.png");
	static final IModelCustom mesh = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/leafia/pwrventexhaust.obj"));
	@Override
	public boolean isGlobalRenderer(PWRVentOutletTE entity) {
		return true;
	}
	@Override
	public void render(PWRVentOutletTE entity,double x,double y,double z,float partialTicks,int destroyStage,float alpha) {
		LeafiaGls.pushMatrix();
		LeafiaGls._push();
		LeafiaGls.translate(x+0.5,y+0.5,z+0.5);
		IBlockState state = entity.getWorld().getBlockState(entity.getPos());
		if (state.getBlock() instanceof PWRVentBlockBase) {
			EnumFacing face = state.getValue(PWRVentBlockBase.FACING);
			if (face.getYOffset() != 0) {
				if (face.equals(EnumFacing.DOWN)) LeafiaGls.rotate(-180,1,0,0);
			} else {
				LeafiaGls.rotate(180-face.getHorizontalAngle(),0,1,0);
				LeafiaGls.rotate(-90,1,0,0);
			}
		}
		LeafiaGls.enableLighting();
		LeafiaGls.disableCull();
		bindTexture(concrete);
		mesh.renderPart("Bottom");
		mesh.renderPart("Case");
		bindTexture(hazard);
		mesh.renderPart("Stripes");
		bindTexture(grate);
		mesh.renderPart("Grate");
		LeafiaGls.disableTexture2D();
		LeafiaGls.shadeModel(GL11.GL_SMOOTH);

		LeafiaGls.pushMatrix();
		LeafiaGls.rotate(0,0,1,0);
		LeafiaGls.color(0.8f,0.8f,0.8f,1);
		mesh.renderPart("alignedfan");
		LeafiaGls.popMatrix();

		LeafiaGls.shadeModel(GL11.GL_FLAT);
		LeafiaGls.color(0,0,0,1);
		mesh.renderPart("DarkPlaneB");
		LeafiaGls.enableAlpha();
		LeafiaGls.enableBlend();
		LeafiaGls.blendFunc(SourceFactor.ZERO,DestFactor.ONE_MINUS_SRC_ALPHA);
		LeafiaGls.color(0,0,0,0.45f);
		mesh.renderPart("DarkPlaneA");
		LeafiaGls.enableTexture2D();
		LeafiaGls.enableCull();
		LeafiaGls._pop();
		LeafiaGls.popMatrix();
	}
}