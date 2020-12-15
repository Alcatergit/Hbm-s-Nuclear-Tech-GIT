package com.hbm.render.factories;

import com.hbm.entity.projectile.EntityBurningFOEQ;
import com.hbm.render.entity.RenderFOEQ;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderBurningFOEQFactory implements IRenderFactory<EntityBurningFOEQ> {

	@Override
	public Render<? super EntityBurningFOEQ> createRenderFor(RenderManager manager) {
		return new RenderFOEQ(manager);
	}

}
