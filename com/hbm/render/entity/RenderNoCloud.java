package com.hbm.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.effect.EntityNukeCloudNoShroom;
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

public class RenderNoCloud extends Render<EntityNukeCloudNoShroom> {

	public static final IRenderFactory<EntityNukeCloudNoShroom> FACTORY = (RenderManager man) -> {return new RenderNoCloud(man);};
	
	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/NukeCloudSmall.obj");
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
	
	protected RenderNoCloud(RenderManager renderManager) {
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
	public void doRender(EntityNukeCloudNoShroom entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if(entity.age <= 100)
			this.renderFlare(entity, x, y, z, entityYaw, partialTicks);
		this.renderRing(entity, x, y, z, entityYaw, partialTicks);
	}

	private void renderRing(EntityNukeCloudNoShroom entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GlStateManager.disableLighting();
        GL11.glDisable(GL11.GL_CULL_FACE);
    	GL11.glTranslatef(0.0F, 18F, 0.0F);
    	//ring += 0.1F;
        
        bindTexture(ringTexture);
        ringModel.renderAll();
        GL11.glPopMatrix();
		
	}

	private void renderFlare(EntityNukeCloudNoShroom entity, double x, double y, double z, float entityYaw, float partialTicks) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buf = tessellator.getBuffer();
		RenderHelper.disableStandardItemLighting();
        float f1 = (entity.ticksExisted + 2.0F) / 200.0F;
        float f2 = 0.0F;
        int count = 250;
        
        if(entity.ticksExisted < 250)
        {
        	count = entity.ticksExisted * 3;
        }

        if (f1 > 0.8F)
        {
            f2 = (f1 - 0.8F) / 0.2F;
        }

        Random random = new Random(432L);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(false);
        GL11.glPushMatrix();
        //GL11.glTranslatef(0.0F, -1.0F, -2.0F);
        GL11.glTranslatef((float)x, (float)y + 15, (float)z);
        GL11.glScalef(5F, 5F, 5F);
        
        //for (int i = 0; (float)i < (f1 + f1 * f1) / 2.0F * 60.0F; ++i)
        for(int i = 0; i < count; i++)
        {
            GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F + f1 * 90.0F, 0.0F, 0.0F, 1.0F);
            buf.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
            
            float f3 = random.nextFloat() * 20.0F + 5.0F + f2 * 10.0F;
            float f4 = random.nextFloat() * 2.0F + 1.0F + f2 * 2.0F;
            //tessellator.setColorRGBA_I(16777215, (int)(255.0F * (1.0F - f2)));
         //   tessellator.setColorRGBA_I(59345715, (int)(255.0F * (1.0F - f2)));
            
            buf.pos(0.0D, 0.0D, 0.0D).endVertex();
            //tessellator.setColorRGBA_I(16711935, 0);
            //tessellator.setColorRGBA_I(59345735, 0);
            buf.pos(-0.866D * f4, f3, -0.5F * f4).endVertex();
            buf.pos(0.866D * f4, f3, -0.5F * f4).endVertex();
            buf.pos(0.0D, f3, 1.0F * f4).endVertex();
            buf.pos(-0.866D * f4, f3, -0.5F * f4).endVertex();
            tessellator.draw();
        }

        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        RenderHelper.enableStandardItemLighting();
		
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityNukeCloudNoShroom entity) {
		return null;
	}

}
