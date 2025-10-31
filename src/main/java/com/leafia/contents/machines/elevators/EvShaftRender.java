package com.leafia.contents.machines.elevators;

import com.hbm.render.amlfrom1710.IModelCustom;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

import static com.leafia.contents.machines.elevators.car.ElevatorRender.model;
import static com.leafia.contents.machines.elevators.car.ElevatorRender.support;

public class EvShaftRender extends TileEntitySpecialRenderer<EvShaftTE> {
	static IModelCustom mdl = model("shaft");
	@Override
	public void render(EvShaftTE te,double x,double y,double z,float partialTicks,int destroyStage,float alpha) {
		LeafiaGls.pushMatrix();
		LeafiaGls.translate(x+0.5,y,z+0.5);
		switch(te.getBlockMetadata() - 10) {
			case 2:
				GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 3:
				GL11.glRotatef(0, 0F, 1F, 0F); break;
			case 4:
				GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 5:
				GL11.glRotatef(90, 0F, 1F, 0F); break;
		}
		LeafiaGls.translate(1,0,0);
		bindTexture(support);
		mdl.renderAll();
		LeafiaGls.color(1,1,1);
		LeafiaGls.popMatrix();
	}
}
