package com.hbm.render.factories;

import com.hbm.entity.particle.EntityFogFX;
import com.hbm.render.entity.FogRenderer;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class EntityFogRenderFactory implements IRenderFactory<EntityFogFX> {

	@Override
	public Render<EntityFogFX> createRenderFor(RenderManager manager) {
		return new FogRenderer(manager);
	}

}
