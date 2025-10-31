package com.hbm.render.factories;

import com.hbm.entity.particle.EntitySSmokeFX;
import com.hbm.render.entity.SSmokeRenderer;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderSSmokeFactory implements IRenderFactory<EntitySSmokeFX> {

	Item renderItem;
	
	public RenderSSmokeFactory(Item item) {
		renderItem = item;
	}
	@Override
	public Render<? super EntitySSmokeFX> createRenderFor(RenderManager manager) {
		return new SSmokeRenderer(manager, renderItem);
	}

}
