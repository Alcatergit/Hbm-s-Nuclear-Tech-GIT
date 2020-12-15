package com.hbm.render.factories;

import com.hbm.entity.projectile.EntityRubble;
import com.hbm.render.entity.RenderRubble;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderRubbleFactory implements IRenderFactory<EntityRubble> {

	@Override
	public Render<? super EntityRubble> createRenderFor(RenderManager manager) {
		return new RenderRubble(manager);
	}

}
