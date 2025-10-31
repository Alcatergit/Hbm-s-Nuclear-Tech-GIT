package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.turret.TileEntityTurretJeremy;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;

public class RenderTurretJeremy extends RenderTurretBase<TileEntityTurretJeremy> {

	@Override
	public void render(TileEntityTurretJeremy turret, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
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
		bindTexture(ResourceManager.turret_carriage_tex);
		ResourceManager.turret_chekhov.renderPart("Carriage");
		
		GL11.glTranslated(0, 1.5, 0);
		GL11.glRotated(pitch, 0, 0, 1);
		GL11.glTranslated(0, -1.5, 0);
		bindTexture(ResourceManager.turret_jeremy_tex);
		ResourceManager.turret_jeremy.renderPart("Gun");

		GlStateManager.shadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
