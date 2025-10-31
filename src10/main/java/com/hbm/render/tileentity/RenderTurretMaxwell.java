package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.render.misc.BeamPronter;
import com.hbm.render.misc.BeamPronter.EnumBeamType;
import com.hbm.render.misc.BeamPronter.EnumWaveType;
import com.hbm.tileentity.turret.TileEntityTurretMaxwell;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.math.Vec3d;

public class RenderTurretMaxwell extends RenderTurretBase<TileEntityTurretMaxwell> {

	@Override
	public boolean isGlobalRenderer(TileEntityTurretMaxwell te) {
		return te.beam > 0;
	}
	
	@Override
	public void render(TileEntityTurretMaxwell turret, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
		Vec3d pos = turret.getHorizontalOffset();

		GL11.glPushMatrix();
		GL11.glTranslated(x + pos.x, y, z + pos.z);
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		
		this.renderConnectors(turret, true, false, null);

		bindTexture(ResourceManager.turret_base_tex);
		ResourceManager.turret_chekhov.renderPart("Base");
		double yaw = -Math.toDegrees(turret.lastRotationYaw + (turret.rotationYaw - turret.lastRotationYaw) * partialTicks) - 90D;
		double pitch = Math.toDegrees(turret.lastRotationPitch + (turret.rotationPitch - turret.lastRotationPitch) * partialTicks);
		
		GL11.glRotated(yaw, 0, 1, 0);
		bindTexture(ResourceManager.turret_carriage_ciws_tex);
		ResourceManager.turret_howard.renderPart("Carriage");
		
		GL11.glTranslated(0, 1.5, 0);
		GL11.glRotated(pitch, 0, 0, 1);
		GL11.glTranslated(0, -1.5, 0);
		bindTexture(ResourceManager.turret_maxwell_tex);
		ResourceManager.turret_maxwell.renderPart("Microwave");

		if(turret.beam > 0) {
			
			double offset = 1;
			double length = turret.lastDist - turret.getBarrelLength()+offset+0.2;
			
			GL11.glPushMatrix();
			GlStateManager.color(1, 1, 1, 1);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			GL11.glTranslated(turret.getBarrelLength()-offset, 2D, 0);
			
			long worldTime = turret.getWorld().getTotalWorldTime();
			// for(int i = 0; i < 8; i++)
			// 	BeamPronter.prontBeam(Vec3.createVectorHelper(length, 0, 0), EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0x2020ff, 0xFFFFFF, (int)((worldTime + partialTicks) * -50 + i * 45) % 360, (int)((turret.lastDist + 1)), 0.375F, 2, 0.05F);
			// // int color = 0xff0000;

			BeamPronter.prontBeam(Vec3.createVectorHelper(length, 0, 0), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x00487F, 0xFFFFFF, (int)(worldTime) % 1000, (int)length, 0.125F, 2, 0.0625F);
			GL11.glTranslated(offset, 0, 0);
			BeamPronter.prontBeam(Vec3.createVectorHelper(length-offset, 0, 0), EnumWaveType.STRAIGHT, EnumBeamType.SOLID, 0x002038, 0x002038, 0, 1, 0, 3, 0.0625F*5F);
			GL11.glPopMatrix();
		}

		GlStateManager.shadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
