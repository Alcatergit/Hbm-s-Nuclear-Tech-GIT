package com.hbm.render.factories;

import com.hbm.entity.mob.EntityTaintedCreeper;
import com.hbm.render.entity.RenderTaintedCreeper;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderTaintedCreeperFactory implements IRenderFactory<EntityTaintedCreeper> {

	@Override
	public Render<EntityTaintedCreeper> createRenderFor(RenderManager manager) {
		return new RenderTaintedCreeper(manager);
	}

}
