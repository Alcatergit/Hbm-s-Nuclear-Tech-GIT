package com.hbm.render.entity;

import com.hbm.entity.effect.EntityFalloutUnderGround;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFalloutUnderground extends Render<EntityFalloutUnderGround> {

	public static final IRenderFactory<EntityFalloutUnderGround> FACTORY = (RenderManager man) -> {return new RenderFalloutUnderground(man);};
	
	protected RenderFalloutUnderground(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityFalloutUnderGround entity, double x, double y, double z, float entityYaw, float partialTicks) {}

	@Override
	public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityFalloutUnderGround entity) {
		return null;
	}

}
