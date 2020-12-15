package com.hbm.render.factories;

import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.render.entity.RenderFallout;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFalloutRainFactory implements IRenderFactory<EntityFalloutRain> {

	@Override
	public Render<? super EntityFalloutRain> createRenderFor(RenderManager manager) {
		return new RenderFallout(manager);
	}

}
