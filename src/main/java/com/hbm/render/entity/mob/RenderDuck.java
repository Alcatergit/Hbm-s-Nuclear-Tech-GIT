package com.hbm.render.entity.mob;

import com.hbm.entity.mob.EntityDuck;
import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderDuck extends RenderChicken {

	public static final IRenderFactory<EntityDuck> FACTORY = man -> new RenderDuck(man);
	
	public static final ResourceLocation ducc = new ResourceLocation(RefStrings.MODID, "textures/entity/duck.png");
	
	public RenderDuck(RenderManager p_i47211_1_) {
		super(p_i47211_1_);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityChicken entity) {
		return ducc;
	}

}
