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
    	blastTexture = new ResourceLocation(RefStrings.MODID, "textures/models/explosion/BlastFleija.png");
    	scale = 0;
	}
	
	@Override
	public void doRender(EntityCloudFleija cloud, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GlStateManager.disableLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GlStateManager.enableCull();
       // GlStateManager.shadeModel(GL11.GL_SMOOTH);
       // GlStateManager.enableBlend();
        //GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
       // GlStateManager.disableAlpha();
        
        float s = cloud.age+partialTicks;
        GL11.glScalef(s, s, s);
        
        
        bindTexture(blastTexture);
        blastModel.renderAll();
       /* ResourceManager.normal_fadeout.use();
        GL20.glUniform4f(GL20.glGetUniformLocation(ResourceManager.normal_fadeout.getShaderId(), "color"), 0.2F*2, 0.92F*2, 0.83F*2, 1F);
        GL20.glUniform1f(GL20.glGetUniformLocation(ResourceManager.normal_fadeout.getShaderId(), "fadeout_mult"), 2.5F);
        ResourceManager.sphere_hq.renderAll();
        GL11.glScalef(1.5F, 1.5F, 1.5F);
        GL20.glUniform1f(GL20.glGetUniformLocation(ResourceManager.normal_fadeout.getShaderId(), "fadeout_mult"), 0.5F);
        ResourceManager.sphere_hq.renderAll();
        HbmShaderManager2.releaseShader();*/
        
        //GlStateManager.enableAlpha();
       // GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GL11.glEnable(GL11.GL_LIGHTING);
       // GlStateManager.shadeModel(GL11.GL_FLAT);
        GL11.glPopMatrix();
	}
	
	@Override
	public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {}

	@Override
	protected ResourceLocation getEntityTexture(EntityCloudFleija entity) {
		return blastTexture;
	}

}
