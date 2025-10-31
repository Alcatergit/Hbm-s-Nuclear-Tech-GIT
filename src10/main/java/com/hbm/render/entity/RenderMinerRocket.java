package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.missile.EntityMinerRocket;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMinerRocket extends Render<EntityMinerRocket> {

	public static final IRenderFactory<EntityMinerRocket> FACTORY = (RenderManager man) -> {return new RenderMinerRocket(man);};
	
	protected RenderMinerRocket(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityMinerRocket entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
        //GL11.glRotated(180, 0, 0, 1);
        GlStateManager.disableCull();
        
        if(entity.getRocketType() == 1){
			bindTexture(ResourceManager.minerRocketGerald_tex);
        } else {
        	bindTexture(ResourceManager.minerRocket_tex);
        }
        
        ResourceManager.minerRocket.renderAll();
        
        GlStateManager.enableCull();
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMinerRocket entity) {
		if(entity.getRocketType() == 1)
			return ResourceManager.minerRocketGerald_tex;
		else
			return ResourceManager.minerRocket_tex;
	}
}