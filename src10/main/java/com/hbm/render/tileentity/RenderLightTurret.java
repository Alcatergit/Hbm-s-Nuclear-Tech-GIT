package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.turret.TileEntityTurretLight;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderLightTurret extends TileEntitySpecialRenderer<TileEntityTurretLight> {

	@Override
	public void render(TileEntityTurretLight te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		
		double yaw = te.rotationYaw;
		double pitch = -te.rotationPitch;
        
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
        this.bindTexture(ResourceManager.turret_mg_tex);
        ResourceManager.turret_mg.renderPart("base");

        GlStateManager.shadeModel(GL11.GL_FLAT);
        GL11.glPopMatrix();
        
        renderTileEntityAt2(te, x, y, z, partialTicks, yaw, pitch);
	}
	
	public void renderTileEntityAt2(TileEntity tileEntity, double x, double y, double z, float f, double yaw, double pitch)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

		GL11.glRotated(yaw + 180, 0F, -1F, 0F);

		GlStateManager.shadeModel(GL11.GL_SMOOTH);
        this.bindTexture(ResourceManager.turret_mg_tex);
        ResourceManager.turret_mg.renderPart("rotor");

        GlStateManager.shadeModel(GL11.GL_FLAT);
        GL11.glPopMatrix();
        
        renderTileEntityAt3(tileEntity, x, y, z, f, yaw, pitch);
    }
    
	public void renderTileEntityAt3(TileEntity tileEntity, double x, double y, double z, float f, double yaw, double pitch)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 0.75D, z + 0.5D);
        GlStateManager.enableLighting();
        GL11.glDisable(GL11.GL_CULL_FACE);
		
		GL11.glRotated(yaw + 180, 0F, -1F, 0F);
		GL11.glRotated(pitch, 1F, 0F, 0F);
		GL11.glTranslated(0, -0.75D, 0);

		GlStateManager.shadeModel(GL11.GL_SMOOTH);
        this.bindTexture(ResourceManager.turret_mg_tex);
        ResourceManager.turret_mg.renderPart("gun");

        GlStateManager.shadeModel(GL11.GL_FLAT);
        GL11.glPopMatrix();

        renderTileEntityAt4(tileEntity, x, y, z, f, yaw, pitch);
    }

    public void renderTileEntityAt4(TileEntity tileEntity, double x, double y, double z, float f, double yaw, double pitch)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 0.75D, z + 0.5D);
        GlStateManager.enableLighting();
        GL11.glDisable(GL11.GL_CULL_FACE);
        
		GL11.glRotated(yaw + 180, 0F, -1F, 0F);
		GL11.glRotated(pitch, 1F, 0F, 0F);

        GL11.glTranslated(0, 0.125D, 0);
		GL11.glRotated(((TileEntityTurretLight)tileEntity).rotation, 0F, 0F, 1F);
        GL11.glTranslated(0, -0.875D, 0);

		GlStateManager.shadeModel(GL11.GL_SMOOTH);
        this.bindTexture(ResourceManager.turret_mg_tex);
        ResourceManager.turret_mg.renderPart("barrels");

        GlStateManager.shadeModel(GL11.GL_FLAT);
        GL11.glPopMatrix();
    }
}
