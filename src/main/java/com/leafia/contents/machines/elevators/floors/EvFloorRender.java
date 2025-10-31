package com.leafia.contents.machines.elevators.floors;

import com.hbm.render.amlfrom1710.IModelCustom;
import com.leafia.contents.machines.elevators.car.ElevatorRender.S6;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

import static com.leafia.contents.machines.elevators.car.ElevatorRender.model;

public class EvFloorRender extends TileEntitySpecialRenderer<EvFloorTE> {
	IModelCustom mdl = model("otis_s6_floor");
	@Override
	public void render(EvFloorTE te,double x,double y,double z,float partialTicks,int destroyStage,float alpha) {
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
		bindTexture(S6.door);
		mdl.renderPart("Frames");
		float door = te.open.get();
		LeafiaGls.pushMatrix();
		LeafiaGls.translate(-0.440625f*door,0,0);
		mdl.renderPart("DoorL");
		LeafiaGls.popMatrix();
		LeafiaGls.pushMatrix();
		LeafiaGls.translate(0.440625f*door,0,0);
		mdl.renderPart("DoorR");
		LeafiaGls.popMatrix();
		LeafiaGls.popMatrix();
	}
}
