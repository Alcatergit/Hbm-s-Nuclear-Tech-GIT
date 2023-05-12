package com.hbm.render.entity.mob;

import com.hbm.entity.mob.EntityGlowingOne;
import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderGlowingOne extends RenderZombie {

	public static final IRenderFactory<EntityGlowingOne> FACTORY = man -> new RenderGlowingOne(man);
	
	public static final ResourceLocation glowingone = new ResourceLocation(RefStrings.MODID, "textures/entity/glowingone.png");
	
	public RenderGlowingOne(RenderManager p_i47211_1_) {
		super(p_i47211_1_);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityZombie entity) {
		return glowingone;
	}

}
