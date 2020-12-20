package com.hbm.render.factories;

import com.hbm.entity.particle.EntityDSmokeFX;
import com.hbm.render.entity.DSmokeRenderer;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderDSmokeFXFactory implements IRenderFactory<EntityDSmokeFX> {

	@Override
	public Render<? super EntityDSmokeFX> createRenderFor(RenderManager manager) {
		return new DSmokeRenderer(manager);
	}

}
