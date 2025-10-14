package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.render.misc.SoyuzLauncherPronter;
import com.hbm.render.misc.SoyuzPronter;
import com.hbm.tileentity.machine.TileEntitySoyuzLauncher;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderSoyuzLauncher extends TileEntitySpecialRenderer<TileEntitySoyuzLauncher> {

	@Override
	public boolean isGlobalRenderer(TileEntitySoyuzLauncher te) {
		return true;
	}
	
	@Override
	public void render(TileEntitySoyuzLauncher te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y-4, (float) z + 0.5F);

        double open = 45D;
		int timer = 20;
		
		double rot = open;
		
		if(((TileEntitySoyuzLauncher)te).rocketType >=0)
			rot = 0;
		
		if(((TileEntitySoyuzLauncher)te).starting && ((TileEntitySoyuzLauncher)te).countdown < timer) {
			
			rot = (timer - ((TileEntitySoyuzLauncher)te).countdown + partialTicks) * open / timer;
		}
		
		SoyuzLauncherPronter.prontLauncher(rot);
		
		if(((TileEntitySoyuzLauncher)te).rocketType >= 0) {
			GL11.glTranslatef(0.0F, 5.0F, 0.0F);
			SoyuzPronter.prontSoyuz(((TileEntitySoyuzLauncher)te).rocketType);
		}
		
		GL11.glPopMatrix();
	}
}
