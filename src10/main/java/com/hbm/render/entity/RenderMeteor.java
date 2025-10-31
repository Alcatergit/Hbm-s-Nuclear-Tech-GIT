package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityMeteor;
import com.hbm.lib.RefStrings;
import com.hbm.render.RenderHelper;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMeteor extends Render<EntityMeteor> {

	public static final IRenderFactory<EntityMeteor> FACTORY = (RenderManager man) -> {return new RenderMeteor(man);};
	
	private static final ResourceLocation block_rl = new ResourceLocation(RefStrings.MODID + ":textures/blocks/block_meteor_molten.png");
	
	protected RenderMeteor(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityMeteor rocket, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glScalef(1.0F, 1.0F, 1.0F);
		GL11.glRotatef(180, 1, 0, 0);
		GL11.glRotatef(((rocket.ticksExisted % 360) + partialTicks) * 10, 1, 1, 1);
		

		GlStateManager.disableCull();
		GL11.glScalef(5.0F, 5.0F, 5.0F);
		bindTexture(this.getEntityTexture(rocket));
		GlStateManager.disableLighting();
		renderBlock(0, 0, 0);
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		
		GL11.glPopMatrix();
	}
	
	public void renderBlock(double x, double y, double z) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glRotatef(180, 0F, 0F, 1F);
		RenderHelper.startDrawingTexturedQuads();
		
			RenderHelper.addVertexWithUV(-0.5, -0.5, -0.5, 1, 0);
			RenderHelper.addVertexWithUV(+0.5, -0.5, -0.5, 0, 0);
			RenderHelper.addVertexWithUV(+0.5, +0.5, -0.5, 0, 1);
			RenderHelper.addVertexWithUV(-0.5, +0.5, -0.5, 1, 1);

			RenderHelper.addVertexWithUV(-0.5, -0.5, +0.5, 1, 0);
			RenderHelper.addVertexWithUV(-0.5, -0.5, -0.5, 0, 0);
			RenderHelper.addVertexWithUV(-0.5, +0.5, -0.5, 0, 1);
			RenderHelper.addVertexWithUV(-0.5, +0.5, +0.5, 1, 1);

			RenderHelper.addVertexWithUV(+0.5, -0.5, +0.5, 1, 0);
			RenderHelper.addVertexWithUV(-0.5, -0.5, +0.5, 0, 0);
			RenderHelper.addVertexWithUV(-0.5, +0.5, +0.5, 0, 1);
			RenderHelper.addVertexWithUV(+0.5, +0.5, +0.5, 1, 1);

			RenderHelper.addVertexWithUV(+0.5, -0.5, -0.5, 1, 0);
			RenderHelper.addVertexWithUV(+0.5, -0.5, +0.5, 0, 0);
			RenderHelper.addVertexWithUV(+0.5, +0.5, +0.5, 0, 1);
			RenderHelper.addVertexWithUV(+0.5, +0.5, -0.5, 1, 1);

			RenderHelper.addVertexWithUV(-0.5, -0.5, +0.5, 1, 0);
			RenderHelper.addVertexWithUV(+0.5, -0.5, +0.5, 0, 0);
			RenderHelper.addVertexWithUV(+0.5, -0.5, -0.5, 0, 1);
			RenderHelper.addVertexWithUV(-0.5, -0.5, -0.5, 1, 1);

			RenderHelper.addVertexWithUV(+0.5, +0.5, +0.5, 1, 0);
			RenderHelper.addVertexWithUV(-0.5, +0.5, +0.5, 0, 0);
			RenderHelper.addVertexWithUV(-0.5, +0.5, -0.5, 0, 1);
			RenderHelper.addVertexWithUV(+0.5, +0.5, -0.5, 1, 1);
		RenderHelper.draw();
		GL11.glPopMatrix();
		
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMeteor entity) {
		return block_rl;
	}

}
