package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.turret.TileEntityTurretHoward;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;

public class RenderTurretHoward extends RenderTurretBase<TileEntityTurretHoward> {

	@Override
	public void render(TileEntityTurretHoward turret, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
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
		
		GL11.glTranslated(0, 2.25, 0);
		GL11.glRotated(pitch, 0, 0, 1);
		GL11.glTranslated(0, -2.25, 0);
		bindTexture(ResourceManager.turret_howard_tex);
		ResourceManager.turret_howard.renderPart("Body");
		
		float rot = turret.lastSpin + (turret.spin - turret.lastSpin) * partialTicks;

		bindTexture(ResourceManager.turret_howard_barrels_tex);
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 2.5, 0);
		GL11.glRotated(rot, -1, 0, 0);
		GL11.glTranslated(0, -2.5, 0);
		ResourceManager.turret_howard.renderPart("BarrelsTop");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 2, 0);
		GL11.glRotated(rot, 1, 0, 0);
		GL11.glTranslated(0, -2, 0);
		ResourceManager.turret_howard.renderPart("BarrelsBottom");
		GL11.glPopMatrix();

		GlStateManager.shadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
