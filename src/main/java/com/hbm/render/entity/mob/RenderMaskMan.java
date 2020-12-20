package com.hbm.render.entity.mob;

import com.hbm.entity.mob.EntityMaskMan;
import com.hbm.main.ResourceManager;
import com.hbm.render.model.ModelMaskMan;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMaskMan extends RenderLiving<EntityMaskMan> {

	public static final IRenderFactory<EntityMaskMan> FACTORY = man -> new RenderMaskMan(man);
	
	public RenderMaskMan(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelMaskMan(), 1.0F);
		this.shadowOpaque = 0;
	}
	
	@Override
	public void doRender(EntityMaskMan entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityMaskMan entity) {
		return ResourceManager.maskman_tex;
	}

}
