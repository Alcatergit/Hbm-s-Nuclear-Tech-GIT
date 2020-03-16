package com.hbm.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityBurningFOEQ;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderFOEQ extends Render<EntityBurningFOEQ> {

	public RenderFOEQ(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityBurningFOEQ e, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_CURRENT_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_LIGHTING_BIT);
        GL11.glTranslatef((float)x, (float)y - 10, (float)z);
        GL11.glRotatef(e.prevRotationYaw + (e.rotationYaw - e.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180, 0F, 0F, 1F);
        GL11.glRotatef(e.prevRotationPitch + (e.rotationPitch - e.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
        
        GlStateManager.enableLighting();
		//GL11.glScaled(5, 5, 5);
        GL11.glEnable(GL11.GL_CULL_FACE);
		bindTexture(ResourceManager.sat_foeq_burning_tex);
		ResourceManager.sat_foeq_burning.renderAll();
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		
		Random rand = new Random(System.currentTimeMillis() / 50);

        GL11.glScaled(1.15, 0.75, 1.15);
        GL11.glTranslated(0, -0.5, 0.3);
        GL11.glDisable(GL11.GL_CULL_FACE);
		for(int i = 0; i < 10; i++) {
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glColor3d(1, 0.75, 0.25);
	        GL11.glRotatef(rand.nextInt(360), 0F, 1F, 0F);
			ResourceManager.sat_foeq_fire.renderAll();
	        GL11.glTranslated(0, 2, 0);
			GL11.glColor3d(1, 0.5, 0);
	        GL11.glRotatef(rand.nextInt(360), 0F, 1F, 0F);
			ResourceManager.sat_foeq_fire.renderAll();
	        GL11.glTranslated(0, 2, 0);
			GL11.glColor3d(1, 0.25, 0);
	        GL11.glRotatef(rand.nextInt(360), 0F, 1F, 0F);
			ResourceManager.sat_foeq_fire.renderAll();
	        GL11.glTranslated(0, 2, 0);
			GL11.glColor3d(1, 0.15, 0);
	        GL11.glRotatef(rand.nextInt(360), 0F, 1F, 0F);
			ResourceManager.sat_foeq_fire.renderAll();
			
	        GL11.glTranslated(0, -3.8, 0);
	        
	        GL11.glScaled(0.95, 1.2, 0.95);
		}
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBurningFOEQ entity) {
		return ResourceManager.sat_foeq_tex;
	}

}
