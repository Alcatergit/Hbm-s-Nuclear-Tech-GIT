package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityChopperMine;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelChopperMine;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderChopperMine extends Render<EntityChopperMine> {

	public static final IRenderFactory<EntityChopperMine> FACTORY = (RenderManager man) -> {return new RenderChopperMine(man);};
	
	public static final ResourceLocation mine_rl = new ResourceLocation(RefStrings.MODID + ":textures/models/projectiles/chopperBomb.png");
	
	ModelChopperMine mine;
	
	protected RenderChopperMine(RenderManager renderManager) {
		super(renderManager);
		mine = new ModelChopperMine();
	}
	
	@Override
	public void doRender(EntityChopperMine entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glScalef(1.5F, 1.5F, 1.5F);
		GL11.glRotatef(180, 1, 0, 0);
		
		bindTexture(getEntityTexture(entity));
		
		mine.renderAll(0.0625F);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityChopperMine entity) {
		return mine_rl;
	}

}
