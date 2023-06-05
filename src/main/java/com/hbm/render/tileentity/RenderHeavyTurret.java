package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.turret.TileEntityTurretHeavy;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderHeavyTurret extends TileEntitySpecialRenderer<TileEntityTurretHeavy> {

	@Override
	public void render(TileEntityTurretHeavy te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		
		double yaw = te.rotationYaw;
		double pitch = -te.rotationPitch;
		
        
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		this.bindTexture(ResourceManager.turret_cannon_tex);
        ResourceManager.turret_cannon.renderPart("base");

        GlStateManager.shadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
        
        renderTileEntityAt2(te, x, y, z, partialTicks, yaw, pitch);
	}
	
	public void renderTileEntityAt2(TileEntityTurretHeavy tileEntity, double x, double y, double z, float f, double yaw, double pitch)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GL11.glDisable(GL11.GL_CULL_FACE);
		
		GL11.glRotated(yaw + 180, 0F, -1F, 0F);

		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		this.bindTexture(ResourceManager.turret_cannon_tex);
        ResourceManager.turret_cannon.renderPart("rotor");

        GlStateManager.shadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
        
        renderTileEntityAt3(tileEntity, x, y, z, f, yaw, pitch);
    }
    
	public void renderTileEntityAt3(TileEntityTurretHeavy tileEntity, double x, double y, double z, float f, double yaw, double pitch)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 0.75D, z + 0.5D);
        GlStateManager.enableLighting();
        GL11.glDisable(GL11.GL_CULL_FACE);

		GL11.glRotated(yaw + 180, 0F, -1F, 0F);
		GL11.glRotated(pitch, 1F, 0F, 0F);
		GL11.glTranslated(0, -0.75D, 0);
        
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		this.bindTexture(ResourceManager.turret_cannon_tex);
        ResourceManager.turret_cannon.renderPart("gun");
		GL11.glTranslated(0, 0, tileEntity.recoil);
        ResourceManager.turret_cannon.renderPart("barrel");

        GlStateManager.shadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
    }
}
