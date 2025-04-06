package com.hbm.render.entity.missile;

import com.hbm.entity.missile.EntityMissileBaseNT;
import com.hbm.entity.missile.EntityMissileTier4;
import com.hbm.main.ResourceManager;
import com.hbm.render.NTMRenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;
// PRACTICALLY - TIER 4 MISSILES
public class RenderMissileNuclear extends Render<EntityMissileBaseNT> {

	public static final IRenderFactory<EntityMissileBaseNT> FACTORY = (RenderManager man) -> {return new RenderMissileNuclear(man);};
	
	protected RenderMissileNuclear(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityMissileBaseNT missile, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GlStateManager.enableLighting();
        double[] renderPos = NTMRenderHelper.getRenderPosFromMissile(missile, partialTicks);
        x = renderPos[0];
        y = renderPos[1];
        z = renderPos[2];
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(missile.prevRotationYaw + (missile.rotationYaw - missile.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(missile.prevRotationPitch + (missile.rotationPitch - missile.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
        GL11.glScalef(1.5F, 1.5F, 1.5F);

        if(missile instanceof EntityMissileTier4.EntityMissileVolcano)
			bindTexture(ResourceManager.missileVolcano_tex);
		else if(missile instanceof EntityMissileTier4.EntityMissileNuclear)
			bindTexture(ResourceManager.missileNuclear_tex);
		else if(missile instanceof EntityMissileTier4.EntityMissileMirv)
			bindTexture(ResourceManager.missileMIRV_tex);
		else
			bindTexture(ResourceManager.missileN2_tex);
        ResourceManager.missileNuclear.renderAll();
        GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMissileBaseNT entity) {
		return ResourceManager.missileNuclear_tex;
	}

}
