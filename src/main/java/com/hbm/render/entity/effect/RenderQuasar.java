package com.hbm.render.entity.effect;

import com.hbm.entity.effect.EntityQuasar;
import com.hbm.render.entity.RenderBlackHole;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderQuasar extends Render<EntityQuasar> {

	public static final IRenderFactory<EntityQuasar> FACTORY = man -> new RenderQuasar(man);

	public RenderQuasar(RenderManager renderManager){
		super(renderManager);
	}

	@Override
	public void doRender(EntityQuasar entity, double x, double y, double z, float entityYaw, float partialTicks){
		RenderBlackHole.doRender(6, entity.getEntityId(), entity.ticksExisted,
				entity.getDataManager().get(EntityQuasar.SIZE), x, y, z, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityQuasar entity){
		return RenderBlackHole.hole;
	}
}
