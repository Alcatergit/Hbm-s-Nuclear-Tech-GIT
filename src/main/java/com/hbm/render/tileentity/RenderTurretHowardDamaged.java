package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.turret.TileEntityTurretHowardDamaged;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;

public class RenderTurretHowardDamaged extends RenderTurretBase<TileEntityTurretHowardDamaged> {

	@Override
	public void render(TileEntityTurretHowardDamaged turret, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
		Vec3d pos = turret.getHorizontalOffset();

		GL11.glPushMatrix();
		GL11.glTranslated(x + pos.x, y, z + pos.z);
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);

		bindTexture(ResourceManager.turret_base_rusted);
		ResourceManager.turret_chekhov.renderPart("Base");
		double yaw = -Math.toDegrees(turret.lastRotationYaw + (turret.rotationYaw - turret.lastRotationYaw) * partialTicks) - 90D;
		double pitch = Math.toDegrees(turret.lastRotationPitch + (turret.rotationPitch - turret.lastRotationPitch) * partialTicks);
		
		GL11.glRotated(yaw, 0, 1, 0);
		bindTexture(ResourceManager.turret_carriage_ciws_rusted);
		ResourceManager.turret_howard_damaged.renderPart("Carriage");
		
		GL11.glTranslated(0, 2.25, 0);
		GL11.glRotated(pitch, 0, 0, 1);
		GL11.glTranslated(0, -2.25, 0);
		bindTexture(ResourceManager.turret_howard_rusted);
		ResourceManager.turret_howard_damaged.renderPart("Body");
		
		float rot = turret.lastSpin + (turret.spin - turret.lastSpin) * partialTicks;

		bindTexture(ResourceManager.turret_howard_barrels_rusted);
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 2.5, 0);
		GL11.glRotated(rot, -1, 0, 0);
		GL11.glTranslated(0, -2.5, 0);
		ResourceManager.turret_howard_damaged.renderPart("BarrelsTop");
		GL11.glPopMatrix();
		
		ResourceManager.turret_howard_damaged.renderPart("BarrelsBottom");

		GlStateManager.shadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
