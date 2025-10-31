package com.hbm.render.entity.missile;

import org.lwjgl.opengl.GL11;

import com.hbm.render.tileentity.RenderLaunchPadTier1;
import com.hbm.entity.missile.EntityMissileBurst;
import com.hbm.main.ResourceManager;
import com.hbm.render.RenderHelper;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMissileBurst extends Render<EntityMissileBurst> {

	public static final IRenderFactory<EntityMissileBurst> FACTORY = (RenderManager man) -> {return new RenderMissileBurst(man);};
	
	protected RenderMissileBurst(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityMissileBurst missile, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GlStateManager.enableLighting();
		double[] renderPos = RenderHelper.getRenderPosFromMissile(missile, partialTicks);
		x = renderPos[0];
		y = renderPos[1];
		z = renderPos[2];
		GL11.glTranslated(x, y, z);
        GL11.glScalef(RenderLaunchPadTier1.h_3, RenderLaunchPadTier1.h_3, RenderLaunchPadTier1.h_3);
        GL11.glRotatef(missile.prevRotationYaw + (missile.rotationYaw - missile.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(missile.prevRotationPitch + (missile.rotationPitch - missile.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);

        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        bindTexture(ResourceManager.missileHuge_HE_tex);
        ResourceManager.missileHuge.renderAll();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMissileBurst entity) {
		return ResourceManager.missileHuge_HE_tex;
	}

}
