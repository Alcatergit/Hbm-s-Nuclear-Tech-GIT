package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderCloudFleija extends Render<EntityCloudFleija> {

	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/Sphere.obj");
	private IModelCustom blastModel;
    private ResourceLocation blastTexture;
    public float scale = 0;
    public float ring = 0;
    
    public static final IRenderFactory<EntityCloudFleija> FACTORY = (RenderManager man) -> {return new RenderCloudFleija(man);};
	
	protected RenderCloudFleija(RenderManager renderManager) {
		super(renderManager);
		blastModel = AdvancedModelLoader.loadModel(objTesterModelRL);
    	blastTexture = new ResourceLocation(RefStrings.MODID, "textures/models/BlastFleija.png");
    	scale = 0;
	}
	
	@Override
	public void doRender(EntityCloudFleija cloud, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GlStateManager.disableLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GlStateManager.disableCull();
        GL11.glDisable(GL11.GL_CULL_FACE);
        
        GL11.glScalef(cloud.age, cloud.age, cloud.age);
        
        bindTexture(blastTexture);
        blastModel.renderAll();
        GlStateManager.enableCull();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GlStateManager.enableLighting();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
	}
	
	@Override
	public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {}

	@Override
	protected ResourceLocation getEntityTexture(EntityCloudFleija entity) {
		return blastTexture;
	}

}
