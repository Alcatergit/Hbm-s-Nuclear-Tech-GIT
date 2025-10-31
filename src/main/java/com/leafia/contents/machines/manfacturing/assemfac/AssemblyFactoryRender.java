package com.leafia.contents.machines.manfacturing.assemfac;

import com.hbm.blocks.BlockDummyable;
import com.leafia.contents.machines.manfacturing.assemfac.AssemblyFactoryTE.AssemblerArm;
import com.leafia.dev.blockitems.LeafiaQuickModel;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class AssemblyFactoryRender extends TileEntitySpecialRenderer<TileEntity> {
	@Override
	public boolean isGlobalRenderer(TileEntity te) {
		return true;
	}
	@Override
	public void render(TileEntity te,double x,double y,double z,float interp,int destroyStage,float alpha) {
		LeafiaGls.pushMatrix();
		LeafiaGls.translate(x+0.5,y,z+0.5);/*
		switch(te.getBlockMetadata() - 10) {
			case 2:
				GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 3:
				GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 4:
				GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 5:
				GL11.glRotatef(0, 0F, 1F, 0F); break;
		}*/
		switch(te.getBlockMetadata() - BlockDummyable.offset) {
			case 2: LeafiaGls.rotate(180, 0F, 1F, 0F); break;
			case 4: LeafiaGls.rotate(270, 0F, 1F, 0F); break;
			case 3: LeafiaGls.rotate(0, 0F, 1F, 0F); break;
			case 5: LeafiaGls.rotate(90, 0F, 1F, 0F); break;
		}
		LeafiaGls.translate(-0.5,0,-0.5);

		LeafiaQuickModel mdl = (LeafiaQuickModel)te;
		bindTexture(mdl.__getTexture());
		mdl.__getModel().renderPart("Factory");
		AssemblyFactoryTE fac = (AssemblyFactoryTE)te;
		double hOff;
		double sOff;

		for(int i = 0; i < fac.arms.length; i++) {

			AssemblerArm arm = fac.arms[i];
			double pivotRot = arm.prevAngles[0] + (arm.angles[0] - arm.prevAngles[0]) * interp;
			double armRot = arm.prevAngles[1] + (arm.angles[1] - arm.prevAngles[1]) * interp;
			double pistonRot = arm.prevAngles[2] + (arm.angles[2] - arm.prevAngles[2]) * interp;
			double striker = arm.prevAngles[3] + (arm.angles[3] - arm.prevAngles[3]) * interp;

			int side = i < 3 ? 1 : -1;
			int index = i + 1;

			GL11.glPushMatrix();

			hOff = 1.875D;
			sOff = 2D * side;
			GL11.glTranslated(sOff, hOff, sOff);
			GL11.glRotated(pivotRot * side, 1, 0, 0);
			GL11.glTranslated(-sOff, -hOff, -sOff);
			mdl.__getModel().renderPart("Pivot" + index);

			hOff = 3.375D;
			sOff = 2D * side;
			GL11.glTranslated(sOff, hOff, sOff);
			GL11.glRotated(armRot * side, 1, 0, 0);
			GL11.glTranslated(-sOff, -hOff, -sOff);
			mdl.__getModel().renderPart("Arm" + index);

			hOff = 3.375D;
			sOff = 0.625D * side;
			GL11.glTranslated(sOff, hOff, sOff);
			GL11.glRotated(pistonRot * side, 1, 0, 0);
			GL11.glTranslated(-sOff, -hOff, -sOff);
			mdl.__getModel().renderPart("Piston" + index);
			GL11.glTranslated(0, -striker, 0);
			mdl.__getModel().renderPart("Striker" + index);

			GL11.glPopMatrix();
		}

		LeafiaGls.popMatrix();
	}
}
