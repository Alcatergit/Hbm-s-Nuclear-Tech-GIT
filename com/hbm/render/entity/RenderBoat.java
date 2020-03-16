package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityDuchessGambit;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderBoat extends Render<EntityDuchessGambit> {

	public static final IRenderFactory<EntityDuchessGambit> FACTORY = (RenderManager man) -> {return new RenderBoat(man);};
	
	protected RenderBoat(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(EntityDuchessGambit entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GlStateManager.enableLighting();

		GL11.glTranslatef(0, 0, -1.0F);
        
        bindTexture(ResourceManager.duchessgambit_tex);
        ResourceManager.duchessgambit.renderAll();

		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityDuchessGambit entity) {
		return ResourceManager.boxcar_tex;
	}
}
