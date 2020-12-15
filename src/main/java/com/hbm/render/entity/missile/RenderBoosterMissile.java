package com.hbm.render.entity.missile;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.missile.EntityBooster;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderBoosterMissile extends Render<EntityBooster> {

	public static final IRenderFactory<EntityBooster> FACTORY = (RenderManager man) -> {return new RenderBoosterMissile(man);};
	
	protected RenderBoosterMissile(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityBooster entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
        GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);

        GlStateManager.enableLighting();
        GlStateManager.disableCull();
        GL11.glScalef(2F, 2F, 2F);
        
        bindTexture(ResourceManager.missileBooster_tex);
        ResourceManager.missileBooster.renderAll();

        GlStateManager.enableCull();
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBooster entity) {
		return ResourceManager.missileBooster_tex;
	}

}
