package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.effect.EntityCloudSolinium;
import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderCloudSolinium extends Render<EntityCloudSolinium> {

	public static final IRenderFactory<EntityCloudSolinium> FACTORY = (RenderManager man) -> {return new RenderCloudSolinium(man);};
	
	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/Sphere.obj");
	private IModelCustom blastModel;
    private ResourceLocation blastTexture;
    public float scale = 0;
    public float ring = 0;
    
	protected RenderCloudSolinium(RenderManager renderManager) {
		super(renderManager);
		blastModel = AdvancedModelLoader.loadModel(objTesterModelRL);
    	blastTexture = new ResourceLocation(RefStrings.MODID, "textures/models/explosion/BlastSolinium.png");
    	scale = 0;
	}
	
	@Override
	public void doRender(EntityCloudSolinium cloud, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
        GL11.glDisable(GL11.GL_LIGHTING);
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        
        GL11.glScalef(cloud.age, cloud.age, cloud.age);
        
        bindTexture(blastTexture);
        blastModel.renderAll();
        GlStateManager.enableCull();
        GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCloudSolinium entity) {
		return null;
	}

}
