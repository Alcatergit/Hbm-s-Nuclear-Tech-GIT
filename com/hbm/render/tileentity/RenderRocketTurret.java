package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.bomb.TileEntityTurretRocket;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderRocketTurret extends TileEntitySpecialRenderer<TileEntityTurretRocket> {

	
	@Override
	public void render(TileEntityTurretRocket te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		//TODO there's a math helper funtion specifically for this. Use it.
		//Drillgon200: Maybe I'll fix this later
		/*if((te.rotationYaw > 0 && te.oldRotationYaw < 0)){
			te.oldRotationYaw = 360+te.oldRotationYaw;
		}
		if((te.rotationYaw < 0 && te.oldRotationYaw > 0)){
			te.oldRotationYaw = te.oldRotationYaw-360;
		}*/
		double yaw = te.rotationYaw/*te.oldRotationYaw + (te.rotationYaw - te.oldRotationYaw)*partialTicks*/;
		double pitch = te.rotationPitch/*te.oldRotationPitch /*+ (te.rotationPitch - te.oldRotationPitch)*partialTicks*/;
		
		this.bindTexture(ResourceManager.turret_heavy_base_tex);
        ResourceManager.turret_heavy_base.renderAll();

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

		this.bindTexture(ResourceManager.turret_rocket_rotor_tex);
        ResourceManager.turret_heavy_rotor.renderAll();

        GL11.glPopMatrix();
        
        renderTileEntityAt3(tileEntity, x, y, z, f, yaw, pitch);
    }
    
	public void renderTileEntityAt3(TileEntity tileEntity, double x, double y, double z, float f, double yaw, double pitch)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 1, z + 0.5D);
        GlStateManager.enableLighting();
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

		GL11.glRotated(yaw + 180, 0F, -1F, 0F);
		GL11.glRotated(pitch, 1F, 0F, 0F);

		this.bindTexture(ResourceManager.turret_rocket_gun_tex);
        ResourceManager.turret_rocket_gun.renderAll();

        GL11.glPopMatrix();
    }
}
