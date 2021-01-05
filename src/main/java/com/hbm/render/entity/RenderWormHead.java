package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.mob.botprime.EntityBOTPrimeHead;
import com.hbm.hfr.render.loader.HFRWavefrontObject;
import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.IModelCustom;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderWormHead extends Render<EntityBOTPrimeHead> {

	public static final IRenderFactory<EntityBOTPrimeHead> FACTORY = man -> new RenderWormHead(man);
	
	public static final IModelCustom body = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/mobs/bot_prime_head.obj"));
	public static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/entity/mark_zero_head.png");
	
	public RenderWormHead(RenderManager rendermanagerIn) {
		super(rendermanagerIn);
		this.shadowOpaque = 0.0F;
	}
	
	@Override
	public void doRender(EntityBOTPrimeHead entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);

		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks - 90, 0.0F, 0.0F, 1.0F);

		this.bindEntityTexture(entity);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		GlStateManager.disableCull();
		body.renderAll();
		GlStateManager.enableCull();
		GlStateManager.shadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBOTPrimeHead entity) {
		return texture;
	}

}
