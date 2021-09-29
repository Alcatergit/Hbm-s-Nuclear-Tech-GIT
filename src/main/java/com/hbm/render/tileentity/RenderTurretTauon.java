package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.render.misc.BeamPronter;
import com.hbm.render.misc.BeamPronter.EnumBeamType;
import com.hbm.render.misc.BeamPronter.EnumWaveType;
import com.hbm.tileentity.turret.TileEntityTurretTauon;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.math.Vec3d;

public class RenderTurretTauon extends RenderTurretBase<TileEntityTurretTauon> {

	@Override
	public void render(TileEntityTurretTauon te, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
		TileEntityTurretTauon turret = (TileEntityTurretTauon)te;
		Vec3d off = turret.getHorizontalOffset();

		GL11.glPushMatrix();
		GL11.glTranslated(x + off.x, y, z + off.z);
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		
		this.renderConnectors(turret, true, false, null);

		bindTexture(ResourceManager.turret_base_tex);
		ResourceManager.turret_chekhov.renderPart("Base");
		
		double yaw = -Math.toDegrees(turret.lastRotationYaw + (turret.rotationYaw - turret.lastRotationYaw) * partialTicks) - 90D;
		double pitch = Math.toDegrees(turret.lastRotationPitch + (turret.rotationPitch - turret.lastRotationPitch) * partialTicks);
		
		GL11.glRotated(yaw, 0, 1, 0);
		bindTexture(ResourceManager.turret_carriage_tex);
		ResourceManager.turret_chekhov.renderPart("Carriage");
		
		GL11.glTranslated(0, 1.5, 0);
		GL11.glRotated(pitch, 0, 0, 1);
		GL11.glTranslated(0, -1.5, 0);
		bindTexture(ResourceManager.turret_tauon_tex);
		ResourceManager.turret_tauon.renderPart("Cannon");

		if(turret.beam > 0) {
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			GL11.glTranslated(0, 1.5D, 0);
			BeamPronter.prontBeam(new Vec3(new Vec3d(turret.lastDist, 0, 0)), EnumWaveType.RANDOM, EnumBeamType.LINE, 0xffa200, 0xffd000, (int)te.getWorld().getTotalWorldTime() / 5 % 360, (int)turret.lastDist + 1, 0.1F, 0, 0);
			GL11.glPopAttrib();
			GL11.glPopMatrix();
		}
		
		float rot = turret.lastSpin + (turret.spin - turret.lastSpin) * partialTicks;
		GL11.glTranslated(0, 1.375, 0);
		GL11.glRotated(rot, -1, 0, 0);
		GL11.glTranslated(0, -1.375, 0);
		ResourceManager.turret_tauon.renderPart("Rotor");

		GlStateManager.shadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
