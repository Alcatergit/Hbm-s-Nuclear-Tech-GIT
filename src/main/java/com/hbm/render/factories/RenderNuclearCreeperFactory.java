package com.hbm.render.factories;

import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.render.entity.RenderNuclearCreeper;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderNuclearCreeperFactory implements IRenderFactory<EntityNuclearCreeper> {

	@Override
	public Render<? super EntityNuclearCreeper> createRenderFor(RenderManager manager) {
		return new RenderNuclearCreeper(manager);
	}

}
