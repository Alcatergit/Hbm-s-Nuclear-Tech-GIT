package com.leafia.contents.effects.folkvangr.visual;

import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.leafia.transformer.LeafiaGls;
import com.llib.technical.LeafiaEase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;

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
    	blastTexture = ResourceManager.solid_e;//new ResourceLocation(RefStrings.MODID, "textures/solid_emissive.png");//models/explosion/BlastFleija.png");
    	scale = 0;
	}
	LeafiaEase shrinkEase = new LeafiaEase(LeafiaEase.Ease.EXPO,LeafiaEase.Direction.I);
	float lastTicks = 0;
	@Override
	public void doRender(EntityCloudFleija cloud, double x, double y, double z, float entityYaw, float partialTicks) {
		if (cloud instanceof EntityCloudFleijaRainbow) return;
		GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GlStateManager.disableLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GlStateManager.enableCull();
       // GlStateManager.shadeModel(GL11.GL_SMOOTH);
       // GlStateManager.enableBlend();
        //GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
       // GlStateManager.disableAlpha();
        
        float s = (float)(cloud.scale+(/*cloud.remoteTicks+*/partialTicks)*cloud.tickrate);
        GL11.glScalef(s, s, s);
        
        
        //bindTexture(blastTexture);
		LeafiaGls._push();
		//LeafiaGls.disableTexture2D();
		bindTexture(blastTexture); // Shader fix
		LeafiaGls.enableBlend();
		LeafiaGls.disableCull();
		LeafiaGls.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA,GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		float alpha = 1;
		if (cloud.getDataManager().get(EntityCloudFleija.FINISHED)) {
			LeafiaGls.pushMatrix(); // multiplying by 0 might be irreversible so
			float s3 = (float)(shrinkEase.get((cloud.ticksExisted+partialTicks-lastTicks)/(10+Math.pow(cloud.getMaxAge(),0.5)),1,0,true));
			alpha = (float)(Math.pow(MathHelper.clampedLerp(1,0,(cloud.ticksExisted+partialTicks-lastTicks)/20),2));
			LeafiaGls.color((float)(Math.pow(0.20,1-Math.pow(alpha,2)*0.75)*Math.pow(alpha,2)),(float)(Math.pow(0.92f,1-Math.pow(alpha,2)*0.75)*Math.pow(alpha,2)),(float)(Math.pow(0.83f,1-Math.pow(alpha,2)*0.75)*Math.pow(alpha,2)));
			LeafiaGls.scale(s3,s3,s3);
			blastModel.renderAll();
			LeafiaGls.popMatrix();
		} else {
			lastTicks = cloud.ticksExisted+partialTicks;
			LeafiaGls.color((float) Math.pow(0.20,0.25),(float) Math.pow(0.92f,0.25),(float) Math.pow(0.83f,0.25));
			blastModel.renderAll();
		}
		LeafiaGls.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA,GlStateManager.DestFactor.ONE);
		for (int i = 1; i <= 3; i++) {
			LeafiaGls.color(0.20f,0.92f,0.83f,(float)Math.pow(1-i/3f,1.5)*alpha);
			float s2 = 1+(i*0.1f);
			LeafiaGls.scale(s2,s2,s2);
			blastModel.renderAll();
			LeafiaGls.scale(1/s2,1/s2,1/s2);
		}
		if (alpha >= 1) {
			LeafiaGls.scale(-1,-1,-1);
			LeafiaGls.color((float) Math.pow(0.20,0.5),(float) Math.pow(0.92f,0.5),(float) Math.pow(0.83f,0.5));
			LeafiaGls.scale(-1,-1,-1);
			blastModel.renderAll();
		}
		if (cloud.isAntischrab) {
			LeafiaGls.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA,GlStateManager.DestFactor.ONE);
			float t = cloud.ticksExisted+partialTicks;
			LeafiaGls.color(1,0.149f,0,(float)Math.pow(1-MathHelper.clamp(t-5,0,20)/20,2));
			GL11.glScalef(4,4,4);
			blastModel.renderAll();
			GL11.glScalef(-1,-1,-1);
			blastModel.renderAll();
			GL11.glScalef(-1,-1,-1);
			GL11.glScalef(0.25f,0.25f,0.25f);
		}
		LeafiaGls.disableBlend();
		LeafiaGls.enableTexture2D();
		LeafiaGls._pop();
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
		LeafiaGls.color(1,1,1);
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
