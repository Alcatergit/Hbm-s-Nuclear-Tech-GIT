package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.grenade.EntityGrenadeMk2;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderGrenade extends Render<EntityGrenadeMk2> {

	public static final IRenderFactory<EntityGrenadeMk2> FACTORY = man -> new RenderGrenade(man);
	
	protected RenderGrenade(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(EntityGrenadeMk2 entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y + 0.125F, (float)z);
        GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);

        GL11.glRotatef(90, 0F, 1F, 0F);
        GL11.glScaled(0.125, 0.125, 0.125);
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
		bindTexture(ResourceManager.grenade_mk2);
		ResourceManager.grenade_frag.renderAll();
        
		GL11.glPopMatrix();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityGrenadeMk2 entity) {
		return ResourceManager.grenade_mk2;
	}

}
