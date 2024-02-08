package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityExplosiveBeam;
import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderBeam5 extends Render<EntityExplosiveBeam> {

	public static final IRenderFactory<EntityExplosiveBeam> FACTORY = (RenderManager manage) -> {return new RenderBeam5(manage);};
	
	protected RenderBeam5(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityExplosiveBeam rocket, double x, double y, double z, float entityYaw, float partialTicks) {
		float radius = 0.175F;
		int distance = 2;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buf = tessellator.getBuffer();
		
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glTranslatef((float) x, (float) y, (float) z);

		GL11.glRotatef(rocket.rotationYaw, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-rocket.rotationPitch, 1.0F, 0.0F, 0.0F);
		boolean red = false;
		boolean green = false;
		boolean blue = true;
		for (float o = 0; o <= radius; o += radius / 8) {
			float color = 1f - (o * 8.333f);
			if (color < 0)
				color = 0;
			buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
			GlStateManager.color(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f);
			buf.pos(0 + o, 0 - o, 0).endVertex();;
			buf.pos(0 + o, 0 + o, 0).endVertex();;
			buf.pos(0 + o, 0 + o, 0 + distance).endVertex();;
			buf.pos(0 + o, 0 - o, 0 + distance).endVertex();;
			tessellator.draw();
			
			buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
			GlStateManager.color(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f);
			buf.pos(0 - o, 0 - o, 0).endVertex();
			buf.pos(0 + o, 0 - o, 0).endVertex();
			buf.pos(0 + o, 0 - o, 0 + distance).endVertex();
			buf.pos(0 - o, 0 - o, 0 + distance).endVertex();
			tessellator.draw();
			buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
			GlStateManager.color(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f);
			buf.pos(0 - o, 0 + o, 0).endVertex();
			buf.pos(0 - o, 0 - o, 0).endVertex();
			buf.pos(0 - o, 0 - o, 0 + distance).endVertex();
			buf.pos(0 - o, 0 + o, 0 + distance).endVertex();
			tessellator.draw();
			buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
			GlStateManager.color(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f);
			buf.pos(0 + o, 0 + o, 0).endVertex();
			buf.pos(0 - o, 0 + o, 0).endVertex();
			buf.pos(0 - o, 0 + o, 0 + distance).endVertex();
			buf.pos(0 + o, 0 + o, 0 + distance).endVertex();
			tessellator.draw();
		}
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}
	
	@Override
	public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {}

	@Override
	protected ResourceLocation getEntityTexture(EntityExplosiveBeam entity) {
		return new ResourceLocation(RefStrings.MODID + ":textures/models/projectiles/PlasmaBeam.png");
	}

}
