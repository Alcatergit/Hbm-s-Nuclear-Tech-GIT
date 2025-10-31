package com.hbm.render.entity.missile;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.missile.EntitySoyuzCapsule;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderSoyuzCapsule extends Render<EntitySoyuzCapsule> {

	public static final IRenderFactory<EntitySoyuzCapsule> FACTORY = (RenderManager man) -> {return new RenderSoyuzCapsule(man);};
	
	protected RenderSoyuzCapsule(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntitySoyuzCapsule entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		
        GL11.glTranslated(x, y, z);
        
        double time = (entity.world.getTotalWorldTime());
        double sine = Math.sin(time * 0.05) * 5;
        double sin3 = Math.sin(time * 0.05 + Math.PI * 0.5) * 5;
        int height = 7;
        GL11.glTranslated(0.0F, height, 0.0F);
        GL11.glRotated(sine, 0, 0, 1);
        GL11.glRotated(sin3, 1, 0, 0);
        GL11.glTranslated(0.0F, -height, 0.0F);
        
        GlStateManager.enableCull();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        bindTexture(ResourceManager.soyuz_lander_tex);
        ResourceManager.soyuz_lander.renderPart("Capsule");
        bindTexture(ResourceManager.soyuz_chute_tex);
        ResourceManager.soyuz_lander.renderPart("Chute");
        GlStateManager.shadeModel(GL11.GL_FLAT);
        
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySoyuzCapsule entity) {
		return ResourceManager.soyuz_lander_tex;
	}

}
