package com.hbm.render.factories;

import com.hbm.entity.projectile.EntityShrapnel;
import com.hbm.render.entity.RenderShrapnel;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class ShrapnelRendererFactory implements IRenderFactory<EntityShrapnel> {

	@Override
	public Render<? super EntityShrapnel> createRenderFor(RenderManager manager) {
		return new RenderShrapnel(manager);
	}

}
