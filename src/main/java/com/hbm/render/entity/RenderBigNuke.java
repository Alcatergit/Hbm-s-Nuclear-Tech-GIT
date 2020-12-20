package com.hbm.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.effect.EntityNukeCloudBig;
import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderBigNuke extends Render<EntityNukeCloudBig> {

	public static final IRenderFactory<EntityNukeCloudBig> FACTORY = (RenderManager man) -> {return new RenderBigNuke(man);};
	
	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/NukeCloudHuge.obj");
	private IModelCustom blastModel;
    private ResourceLocation blastTexture;
	private static final ResourceLocation ringModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/Ring.obj");
	private IModelCustom ringModel;
    private ResourceLocation ringTexture;
	private static final ResourceLocation ringBigModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/RingBig.obj");
	private IModelCustom ringBigModel;
    private ResourceLocation ringBigTexture;
    public float scale = 0;
    public float ring = 0;
	
	protected RenderBigNuke(RenderManager renderManager) {
		super(renderManager);
		blastModel = AdvancedModelLoader.loadModel(objTesterModelRL);
    	blastTexture = new ResourceLocation(RefStrings.MODID, "textures/models/NukeCloudFire.png");
    	ringModel = AdvancedModelLoader.loadModel(ringModelRL);
    	ringTexture = new ResourceLocation(RefStrings.MODID, "textures/models/Ring2.png");
    	ringBigModel = AdvancedModelLoader.loadModel(ringBigModelRL);
    	ringBigTexture = new ResourceLocation(RefStrings.MODID, "textures/models/Ring2.png");
    	scale = 0;
    	ring = 0;
	}
	
	@Override
	public void doRender(EntityNukeCloudBig entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if(entity.age > 100)
		{
			this.renderMush(entity, x, y, z, entityYaw, partialTicks);
			this.renderCloud(entity, x, y, z, entityYaw, partialTicks);
		} else {
			this.renderFlare(entity, x, y, z, entityYaw, partialTicks);
		}
		this.renderRing(entity, x, y, z, entityYaw, partialTicks);
		GlStateManager.enableCull();
	}
	
	public void renderMush(EntityNukeCloudBig p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {

		GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        if(p_76986_1_.age < 150)
        {
        	//GL11.glTranslatef(0.0F, -60F + ((p_76986_1_.age - 100) * 60 / 50), 0.0F);
        	GL11.glTranslatef(0.0F, p_76986_1_.height, 0.0F);
        }
        GL11.glScalef(10.0F, 10.0F, 10.0F);
        
        bindTexture(blastTexture);
        blastModel.renderAll();
        GL11.glPopMatrix();
	}
	
	public void renderCloud(EntityNukeCloudBig p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {

		GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
    	GL11.glTranslatef(0.0F, 80F, 0.0F);
        /*if(scale < 1.5)
        {
        	scale += 0.02;
        }*/
        GL11.glScalef(p_76986_1_.scale, 1.0F, p_76986_1_.scale);
        GL11.glScalef(125F, 25.0F, 125F);
        
        bindTexture(ringBigTexture);
        ringBigModel.renderAll();
        GL11.glPopMatrix();
	}
	
	public void renderRing(EntityNukeCloudBig p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {

		GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
    	GL11.glTranslatef(0.0F, 23F, 0.0F);
    	//ring += 0.1F;
        GL11.glScalef(p_76986_1_.ring * 10, 50F, p_76986_1_.ring * 10);
        
        bindTexture(ringTexture);
        ringModel.renderAll();
        GL11.glPopMatrix();
	}
	
	public void renderFlare(EntityNukeCloudBig p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buf = tessellator.getBuffer();
		RenderHelper.disableStandardItemLighting();
        float f1 = (p_76986_1_.ticksExisted + 2.0F) / 200.0F;
        float f2 = 0.0F;
        int count = 250;
        
        if(p_76986_1_.ticksExisted < 250)
        {
        	count = p_76986_1_.ticksExisted * 3;
        }

        if (f1 > 0.8F)
        {
            f2 = (f1 - 0.8F) / 0.2F;
        }

        Random random = new Random(432L);
        GlStateManager.disableTexture2D();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GlStateManager.disableAlpha();
        GlStateManager.enableCull();
        GlStateManager.depthMask(false);
        GL11.glPushMatrix();
        //GL11.glTranslatef(0.0F, -1.0F, -2.0F);
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_ + 15, (float)p_76986_6_);
        GL11.glScalef(7.5F, 7.5F, 7.5F);
        
        //for (int i = 0; (float)i < (f1 + f1 * f1) / 2.0F * 60.0F; ++i)
        for(int i = 0; i < count; i++)
        {
            GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F + f1 * 90.0F, 0.0F, 0.0F, 1.0F);
            buf.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
            float f3 = random.nextFloat() * 20.0F + 5.0F + f2 * 10.0F;
            float f4 = random.nextFloat() * 2.0F + 1.0F + f2 * 2.0F;
            
            buf.pos(0.0D, 0.0D, 0.0D).color(0.53725490196F, 0.54509803921F, 0.2F, 1.0F - f2).endVertex();
            buf.pos(-0.866D * f4, f3, -0.5F * f4).color(0.53725490196F, 0.54509803921F, 0.27843137254F, 0).endVertex();
            buf.pos(0.866D * f4, f3, -0.5F * f4).color(0.53725490196F, 0.54509803921F, 0.27843137254F, 0).endVertex();
            buf.pos(0.0D, f3, 1.0F * f4).color(0.53725490196F, 0.54509803921F, 0.27843137254F, 0).endVertex();
            buf.pos(-0.866D * f4, f3, -0.5F * f4).color(0.53725490196F, 0.54509803921F, 0.27843137254F, 0).endVertex();
            
            tessellator.draw();
        }

        GL11.glPopMatrix();
        GlStateManager.depthMask(true);
        //GlStateManager.disableCull();
        GlStateManager.disableBlend();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        RenderHelper.enableStandardItemLighting();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityNukeCloudBig entity) {
		return null;
	}

	
}
