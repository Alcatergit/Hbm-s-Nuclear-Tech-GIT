package com.hbm.render.factories;

import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.render.entity.RenderSmallNukeMK3;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderSmallNukeMK3Factory implements IRenderFactory<EntityNukeCloudSmall> {

	@Override
	public Render<? super EntityNukeCloudSmall> createRenderFor(RenderManager manager) {
		return new RenderSmallNukeMK3(manager);
	}

}
