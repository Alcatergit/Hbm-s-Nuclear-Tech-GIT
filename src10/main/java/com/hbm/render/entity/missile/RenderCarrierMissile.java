package com.hbm.render.entity.missile;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.missile.EntityCarrier;
import com.hbm.main.ResourceManager;
import com.hbm.render.RenderHelper;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderCarrierMissile extends Render<EntityCarrier> {

	public static final IRenderFactory<EntityCarrier> FACTORY = (RenderManager man) -> {return new RenderCarrierMissile(man);};
	
	protected RenderCarrierMissile(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityCarrier rocket, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		double[] renderPos = RenderHelper.getRenderPosFromMissile(rocket, partialTicks);
		x = renderPos[0];
		y = renderPos[1];
		z = renderPos[2];
		GL11.glTranslated(x, y, z);
		GL11.glScalef(1.0F, 1.0F, 1.0F);
		
        GlStateManager.enableLighting();
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glScalef(2F, 2F, 2F);
		bindTexture(ResourceManager.missileCarrier_tex);
		ResourceManager.missileCarrier.renderAll();
		
		if(rocket.getDataManager().get(EntityCarrier.HASBOOSTERS)) {
	        GL11.glTranslated(0.0D, 0.5D, 0.0D);
	        GL11.glTranslated(1.25D, 0.0D, 0.0D);
			bindTexture(ResourceManager.missileBooster_tex);
			ResourceManager.missileBooster.renderAll();
	        GL11.glTranslated(-2.5D, 0.0D, 0.0D);
			ResourceManager.missileBooster.renderAll();
	        GL11.glTranslated(1.25D, 0.0D, 0.0D);
	        GL11.glTranslated(0.0D, 0.0D, 1.25D);
			ResourceManager.missileBooster.renderAll();
	        GL11.glTranslated(0.0D, 0.0D, -2.5D);
			ResourceManager.missileBooster.renderAll();
	        GL11.glTranslated(0.0D, 0.0D, 1.25D);
		}

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCarrier entity) {
		return ResourceManager.missileCarrier_tex;
	}

}
