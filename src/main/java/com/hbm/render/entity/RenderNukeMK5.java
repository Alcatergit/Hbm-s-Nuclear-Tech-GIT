package com.hbm.render.entity;

import com.hbm.entity.logic.EntityNukeExplosionMK5;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderNukeMK5 extends Render<EntityNukeExplosionMK5> {

	public static final IRenderFactory<EntityNukeExplosionMK5> FACTORY = (RenderManager man) -> {return new RenderNukeMK5(man);};
	
	protected RenderNukeMK5(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityNukeExplosionMK5 entity, double x, double y, double z, float entityYaw, float partialTicks) {}

	@Override
	public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityNukeExplosionMK5 entity) {
		return null;
	}

}
