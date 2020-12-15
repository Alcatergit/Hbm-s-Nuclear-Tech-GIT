package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelHunterChopper;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderHunterChopper extends Render<EntityHunterChopper> {

	public static final IRenderFactory<EntityHunterChopper> FACTORY = (RenderManager man) -> {return new RenderHunterChopper(man);};
	
	public static final ResourceLocation chopper_rl = new ResourceLocation(RefStrings.MODID + ":textures/entity/chopper.png");
	
	//ProtoCopter mine;
	ModelHunterChopper mine2;
	
	protected RenderHunterChopper(RenderManager renderManager) {
		super(renderManager);
		//mine = new ProtoCopter();
		mine2 = new ModelHunterChopper();
	}
	
	@Override
	public void doRender(EntityHunterChopper rocket, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glTranslatef(0.0625F * 0, 0.0625F * 32, 0.0625F * 0);
		GL11.glTranslatef(0.0625F * 0, 0.0625F * 12, 0.0625F * 0);
		GL11.glScalef(4F, 4F, 4F);
		GL11.glRotatef(180, 1, 0, 0);

		GL11.glRotatef(rocket.prevRotationYaw + (rocket.rotationYaw - rocket.prevRotationYaw) * partialTicks - 90.0F, 0, 1.0F, 0);
		GL11.glRotatef(rocket.prevRotationPitch + (rocket.rotationPitch - rocket.prevRotationPitch) * partialTicks, 0, 0, 1.0F);
		
		bindTexture(getEntityTexture(rocket));
		
        //if(rocket instanceof EntityHunterChopper)
        //	mine2.setGunRotations((EntityHunterChopper)rocket, yaw, pitch);
		
		mine2.renderAll(0.0625F);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityHunterChopper entity) {
		return chopper_rl;
	}

	
}
