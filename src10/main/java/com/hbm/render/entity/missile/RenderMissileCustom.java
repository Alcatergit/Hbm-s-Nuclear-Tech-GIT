package com.hbm.render.entity.missile;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.missile.EntityMissileCustom;
import com.hbm.main.ResourceManager;
import com.hbm.render.RenderHelper;
import com.hbm.render.misc.MissilePronter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMissileCustom extends Render<EntityMissileCustom> {

	public static final IRenderFactory<EntityMissileCustom> FACTORY = (RenderManager man) -> {return new RenderMissileCustom(man);};
	
	protected RenderMissileCustom(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityMissileCustom entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		double[] pos = RenderHelper.getRenderPosFromMissile(entity, partialTicks);
		x = pos[0];
		y = pos[1];
		z = pos[2];
		GL11.glTranslated(x, y, z);
        GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
        
        MissilePronter.prontMissile(entity.getDataManager().get(EntityMissileCustom.TEMPLATE).multipart(), Minecraft.getMinecraft().getTextureManager());
        
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMissileCustom entity) {
		return ResourceManager.universal;
	}

}
