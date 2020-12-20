package com.hbm.render.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderEmpty extends Render<Entity> {

	public static final IRenderFactory<Entity> FACTORY = (RenderManager man) -> {return new RenderEmpty(man);};
	
	protected RenderEmpty(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {}

	@Override
	public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}

}
