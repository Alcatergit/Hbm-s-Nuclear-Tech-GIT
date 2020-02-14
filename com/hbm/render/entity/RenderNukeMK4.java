package com.hbm.render.entity;

import com.hbm.entity.logic.EntityNukeExplosionMK4;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderNukeMK4 extends Render<EntityNukeExplosionMK4> {

	public static final IRenderFactory<EntityNukeExplosionMK4> FACTORY = (RenderManager man) -> {return new RenderNukeMK4(man);};
	
	protected RenderNukeMK4(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityNukeExplosionMK4 entity, double x, double y, double z, float entityYaw, float partialTicks) {}

	@Override
	public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityNukeExplosionMK4 entity) {
		return null;
	}

}
