package com.hbm.render.entity.effect;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.effect.EntityCloudTom;
import com.hbm.main.ClientProxy;
import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.Tessellator;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderCloudTom extends Render<EntityCloudTom> {

	public static final IRenderFactory<EntityCloudTom> FACTORY = man -> new RenderCloudTom(man);
	
	protected RenderCloudTom(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(EntityCloudTom entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if(!ClientProxy.renderingConstant)
			return;
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);

		EntityCloudTom blast = (EntityCloudTom)entity;

		double scale = blast.age + partialTicks;

		int segments = 16;
		float angle = (float) Math.toRadians(360D/segments);
		int height = 20;
		int depth = 20;

		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();

		bindTexture(this.getEntityTexture(blast));

        GlStateManager.matrixMode(GL11.GL_TEXTURE);
        GlStateManager.loadIdentity();

    	float movement = -(Minecraft.getMinecraft().player.ticksExisted + partialTicks) * 0.005F * 10;
    	GL11.glTranslatef(0, movement, 0);

        GlStateManager.matrixMode(GL11.GL_MODELVIEW);

		for(int i = 0; i < segments; i++) {

			for(int j = 0; j < 5; j++) {

				double mod = 1 - j * 0.025;
				double h = height + j * 10;
				double off = 1D / j;

				Vec3 vec = Vec3.createVectorHelper(scale, 0, 0);
				vec.rotateAroundY(angle * i);
				double x0 = vec.xCoord * mod;
				double z0 = vec.zCoord * mod;

				tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, 0.0F);
				tess.addVertexWithUV(x0, h, z0, 0, 1 + off);
				tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
				tess.addVertexWithUV(x0, -depth, z0, 0, 0 + off);

				vec.rotateAroundY(angle);
				x0 = vec.xCoord * mod;
				z0 = vec.zCoord * mod;

				tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
				tess.addVertexWithUV(x0, -depth, z0, 1, 0 + off);
				tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, 0.0F);
				tess.addVertexWithUV(x0, h, z0, 1, 1 + off);
			}
		}

		tess.draw();

        GlStateManager.matrixMode(GL11.GL_TEXTURE);
        GlStateManager.depthMask(true);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);

        GlStateManager.depthMask(true);
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.enableAlpha();
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
		GL11.glPopMatrix();
	};
	
	@Override
	protected ResourceLocation getEntityTexture(EntityCloudTom entity) {
		return ResourceManager.tomblast;
	}

}
