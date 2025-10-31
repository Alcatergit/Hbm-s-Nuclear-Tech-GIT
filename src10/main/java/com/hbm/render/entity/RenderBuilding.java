package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityBuilding;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderBuilding extends Render<EntityBuilding> {

	public static final IRenderFactory<EntityBuilding> FACTORY = (RenderManager man) -> {return new RenderBuilding(man);};
	
	protected RenderBuilding(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(EntityBuilding entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GlStateManager.disableCull();
        
        bindTexture(ResourceManager.building_tex);
        ResourceManager.building.renderAll();
        
        GlStateManager.enableCull();
        GL11.glPopMatrix();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityBuilding entity) {
		return ResourceManager.building_tex;
	}

}
