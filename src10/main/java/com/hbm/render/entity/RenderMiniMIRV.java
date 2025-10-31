package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityMiniMIRV;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelMIRV;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMiniMIRV extends Render<EntityMiniMIRV> {

	public static final IRenderFactory<EntityMiniMIRV> FACTORY = (RenderManager man) -> {return new RenderMiniMIRV(man);};
	
	private ModelMIRV miniNuke;
	private static ResourceLocation mirv_rl = new ResourceLocation(RefStrings.MODID + ":textures/models/projectiles/Mirv.png");
	
	protected RenderMiniMIRV(RenderManager renderManager) {
		super(renderManager);
		miniNuke = new ModelMIRV();
	}
	
	@Override
	public void doRender(EntityMiniMIRV entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
        GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks + 180, 0.0F, 0.0F, 1.0F);
        GL11.glScalef(1.5F, 1.5F, 1.5F);
        
        bindTexture(mirv_rl);
        miniNuke.renderAll(0.0625F);
		GL11.glPopMatrix();
	}
	
	@Override
	public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {}

	@Override
	protected ResourceLocation getEntityTexture(EntityMiniMIRV entity) {
		return mirv_rl;
	}

}
