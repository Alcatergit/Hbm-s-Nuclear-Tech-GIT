package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.render.misc.BeamPronter;
import com.hbm.render.misc.BeamPronter.EnumBeamType;
import com.hbm.render.misc.BeamPronter.EnumWaveType;
import com.hbm.tileentity.machine.TileEntityCharger;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderCharger extends TileEntitySpecialRenderer<TileEntityCharger> {
	
	@Override
	public boolean isGlobalRenderer(TileEntityCharger te) {
		return te.isOn;
	}

	@Override
	public void render(TileEntityCharger te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);

		if(te.isOn){
			GL11.glPushMatrix();
	        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
	        GlStateManager.enableLighting();
			GlStateManager.enableCull();
			GlStateManager.color(1, 1, 1, 1);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			 
	        BeamPronter.prontBeam(Vec3.createVectorHelper(0, te.pointingUp ? te.range + 0.5 : -te.range - 0.5, 0), EnumWaveType.STRAIGHT, EnumBeamType.SOLID, 0x002038, 0x002038, 0, 1, 0F, 1, 0.499F);
	        
	        GL11.glPopMatrix();
       	}
	}
}
