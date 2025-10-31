package com.leafia.contents.network.fluid;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.tileentity.conductor.TileEntityFFDuctBaseMk2;
import com.leafia.contents.network.fluid.gauges.FluidDuctGaugeTE;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class FluidDuctEquipmentRender extends TileEntitySpecialRenderer<FluidDuctEquipmentTE> {
	static IModelCustom mdlGauge = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID+":models/leafia/pipes/gauge.obj"));
	static ResourceLocation texGauge = new ResourceLocation(RefStrings.MODID+":textures/models/leafia/pipes/gauge.png");
	@Override
	public void render(FluidDuctEquipmentTE te,double x,double y,double z,float partialTicks,int destroyStage,float alpha) {
		LeafiaGls.pushMatrix();
		LeafiaGls.translate(x+0.5,y+0.5,z+0.5);
		IBlockState state = te.getWorld().getBlockState(te.getPos());
		if (state.getBlock() instanceof FluidDuctEquipmentBase) {
			LeafiaGls.rotate(180-te.direction.getHorizontalIndex()*90,0,1,0);
			LeafiaGls.rotate(-90*te.face,1,0,0);
			if (te.vertical)
				LeafiaGls.rotate(-90,0,0,1);
			bindTexture(ResourceManager.pipe_neo_tex);
			if(te.getType() != null)
				FFUtils.setRGBFromHex(ModForgeFluids.getFluidColor(te.getType()));
			mdlGauge.renderPart("pipe");
			LeafiaGls.color(1,1,1);
			bindTexture(texGauge);
			mdlGauge.renderPart("body");
			if (te instanceof FluidDuctGaugeTE) {
				FluidDuctGaugeTE gauge = (FluidDuctGaugeTE)te;
				LeafiaGls.rotate(-gauge.local_fillPerSec/100_000f*360,0,0,1);
				mdlGauge.renderPart("needle");
			}
		}
		LeafiaGls.popMatrix();
	}
}
