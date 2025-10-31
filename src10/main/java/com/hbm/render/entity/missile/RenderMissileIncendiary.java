package com.hbm.render.entity.missile;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.missile.EntityMissileIncendiary;
import com.hbm.main.ResourceManager;
import com.hbm.render.RenderHelper;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMissileIncendiary extends Render<EntityMissileIncendiary> {

	public static final IRenderFactory<EntityMissileIncendiary> FACTORY = (RenderManager man) -> {return new RenderMissileIncendiary(man);};
	
	protected RenderMissileIncendiary(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityMissileIncendiary missile, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GlStateManager.enableLighting();
		double[] pos = RenderHelper.getRenderPosFromMissile(missile, partialTicks);
		x = pos[0];
		y = pos[1];
		z = pos[2];
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(missile.prevRotationYaw + (missile.rotationYaw - missile.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(missile.prevRotationPitch + (missile.rotationPitch - missile.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
        
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        bindTexture(ResourceManager.missileV2_IN_tex);
        ResourceManager.missileV2.renderAll();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMissileIncendiary entity) {
		return ResourceManager.missileV2_IN_tex;
	}

}
