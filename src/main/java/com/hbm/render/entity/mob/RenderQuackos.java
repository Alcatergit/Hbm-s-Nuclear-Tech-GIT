package com.hbm.render.entity.mob;

import com.hbm.interfaces.IConstantRenderer;
import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderQuackos extends RenderChicken implements IConstantRenderer {

	public static final ResourceLocation ducc = new ResourceLocation(RefStrings.MODID, "textures/entity/duck.png");
	
	public static final IRenderFactory<EntityChicken> FACTORY = man -> new RenderQuackos(man);
	
	public RenderQuackos(RenderManager p_i47211_1_) {
		super(p_i47211_1_);
	}
	
	@Override
	public void doRender(EntityChicken entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityChicken entity) {
		return ducc;
	}

}
