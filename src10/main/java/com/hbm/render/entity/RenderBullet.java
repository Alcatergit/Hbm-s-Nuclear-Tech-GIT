package com.hbm.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityBullet;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelBullet;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderBullet extends Render<EntityBullet> {

	public static final IRenderFactory<EntityBullet> FACTORY = (RenderManager manager) -> {return new RenderBullet(manager);};
	
	private ModelBullet miniNuke;
	
	protected RenderBullet(RenderManager renderManager) {
		super(renderManager);
		miniNuke = new ModelBullet();
	}

	@Override
	public void doRender(EntityBullet rocket, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(rocket.prevRotationYaw + (rocket.rotationYaw - rocket.prevRotationYaw) * partialTicks - 90.0F,
				0.0F, 1.0F, 0.0F);
		GL11.glRotatef(rocket.prevRotationPitch + (rocket.rotationPitch - rocket.prevRotationPitch) * partialTicks + 180,
				0.0F, 0.0F, 1.0F);
		GL11.glScalef(1.5F, 1.5F, 1.5F);
		

		GL11.glRotatef(new Random(rocket.getEntityId()).nextInt(360),
				1.0F, 0.0F, 0.0F);

		if (rocket instanceof EntityBullet && ((EntityBullet) rocket).getIsChopper()) {
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/projectiles/emplacer.png"));
		} else if (rocket instanceof EntityBullet && ((EntityBullet) rocket).getIsCritical()) {
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/projectiles/tau.png"));
		} else if (rocket instanceof EntityBullet) {
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/projectiles/bullet.png"));
		}
		miniNuke.renderAll(0.0625F);
		
		//renderFlechette();
		//renderDart();
		
		GL11.glPopMatrix();
	}
	
	@Override
	public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityBullet entity) {
		if (entity.getIsChopper()) {
			return new ResourceLocation(RefStrings.MODID + ":textures/models/projectiles/emplacer.png");
		} else if (entity.getIsCritical()) {
			return new ResourceLocation(RefStrings.MODID + ":textures/models/projectiles/tau.png");
		} else {
			return new ResourceLocation(RefStrings.MODID + ":textures/models/projectiles/bullet.png");
		}
	}

}
