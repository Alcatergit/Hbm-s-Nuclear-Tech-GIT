package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.turret.TileEntityTurretRichard;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;

public class RenderTurretRichard extends RenderTurretBase<TileEntityTurretRichard> {

	@Override
	public void render(TileEntityTurretRichard turret, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
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
		bindTexture(ResourceManager.turret_richard_tex);
		ResourceManager.turret_richard.renderPart("Launcher");
		
		GL11.glTranslated(0, 0.375, 0.1875);
		
		for(int i = 0; i < turret.loaded; i++) {
			ResourceManager.turret_richard.renderPart("MissileLoaded");
			
			if(i == 2 || i == 6 || i == 9 || i == 13) {
				GL11.glTranslated(0, -0.1875, 0.1875 * 2 + 0.09375);
			} else {

				GL11.glTranslated(0, 0, -0.1875);
			}
		}

		GlStateManager.shadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
