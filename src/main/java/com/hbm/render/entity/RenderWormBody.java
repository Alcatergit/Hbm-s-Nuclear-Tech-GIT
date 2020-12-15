package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.mob.botprime.EntityBOTPrimeBody;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderWormBody extends Render<EntityBOTPrimeBody> {

	public static final IRenderFactory<EntityBOTPrimeBody> FACTORY = man -> new RenderWormBody(man);
	
	public static final IModelCustom body = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/mobs/bot_prime_body.obj"));

	protected RenderWormBody(RenderManager renderManager) {
		super(renderManager);
		this.shadowOpaque = 0.0F;
	}
	
	@Override
	public void doRender(EntityBOTPrimeBody entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks - 90, 0.0F, 0.0F, 1.0F);

		this.bindEntityTexture(entity);
		body.renderAll();

		GL11.glPopMatrix();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityBOTPrimeBody entity) {
		return ResourceManager.universal;
	}
	
}
