package com.hbm.render.entity;

import com.hbm.entity.mob.botprime.EntityBOTPrimeHead;
import com.hbm.main.ResourceManager;
import com.hbm.render.model.ModelWormHead;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderWormHead extends RenderLiving<EntityBOTPrimeHead> {

	public static final IRenderFactory<EntityBOTPrimeHead> FACTORY = man -> new RenderWormHead(man);
	
	public RenderWormHead(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelWormHead(), 1.0F);
		this.shadowOpaque = 0.0F;
	}
	
	@Override
	public void doRender(EntityBOTPrimeHead entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBOTPrimeHead entity) {
		return ResourceManager.universal;
	}

}
