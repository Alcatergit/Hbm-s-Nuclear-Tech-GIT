package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.RenderSparks;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.tileentity.bomb.TileEntityRailgun;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderRailgun extends TileEntitySpecialRenderer<TileEntityRailgun> {

	@Override
	public void render(TileEntityRailgun gun, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

        bindTexture(ResourceManager.railgun_base_tex);
        ResourceManager.railgun_base.renderAll();
        
        float yaw = gun.yaw;
        float pitch = gun.pitch;
        
        if(System.currentTimeMillis() < gun.startTime + TileEntityRailgun.cooldownDurationMillis) {
        	float interpolation = (float)(System.currentTimeMillis() - gun.startTime) / (float)TileEntityRailgun.cooldownDurationMillis * 100F;
        	
        	float yi = (gun.yaw - gun.lastYaw) * interpolation / 100F;
        	yaw = gun.lastYaw + yi;
        	
        	float pi = (gun.pitch - gun.lastPitch) * interpolation / 100F;
        	pitch = gun.lastPitch + pi;
        }
        
        int max = 5;
        int count = max - (int)(((gun.fireTime + TileEntityRailgun.cooldownDurationMillis) - System.currentTimeMillis()) * max / TileEntityRailgun.cooldownDurationMillis);
		
        if(System.currentTimeMillis() < gun.fireTime + TileEntityRailgun.cooldownDurationMillis) {
			Vec3 vec = Vec3.createVectorHelper(5.375, 0, 0);
			vec.rotateAroundZ((float) (pitch * Math.PI / 180D));
			vec.rotateAroundY((float) (yaw * Math.PI / 180D));
	
			double fX = vec.xCoord;
			double fY = 1 + vec.yCoord;
			double fZ = vec.zCoord;
			GL11.glRotatef(180, 0F, 1F, 0F);
			for(int i = 0; i < count; i++)
				RenderSparks.renderSpark((int) System.currentTimeMillis() / 100 + i * 10000, fX, fY, fZ, 0.75F, 5, 6, 0x0088FF, 0xDFDFFF);
			for(int i = 0; i < count; i++)
				RenderSparks.renderSpark((int) System.currentTimeMillis() / 50 + i * 10000, fX, fY, fZ, 0.75F, 5, 6, 0x0088FF, 0xDFDFFF);
			GL11.glRotatef(180, 0F, 1F, 0F);
        }
        
        GL11.glRotatef(yaw, 0, 1, 0);
        bindTexture(ResourceManager.railgun_rotor_tex);
        ResourceManager.railgun_rotor.renderAll();
        
        GL11.glTranslatef(0, 1F, 0);
        GL11.glRotatef(pitch, 0, 0, 1);
        GL11.glTranslatef(0, -1F, 0);
        bindTexture(ResourceManager.railgun_main_tex);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        ResourceManager.railgun_main.renderAll();
        GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glPopMatrix();
	}
}
