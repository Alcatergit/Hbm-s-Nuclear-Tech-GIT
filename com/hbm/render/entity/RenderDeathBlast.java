package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.logic.EntityDeathBlast;
import com.hbm.interfaces.IConstantRenderer;
import com.hbm.lib.RefStrings;
import com.hbm.main.ClientProxy;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderDeathBlast extends Render<EntityDeathBlast> {

	public static final IRenderFactory<EntityDeathBlast> FACTORY = (RenderManager man) -> {return new RenderDeathBlast(man);};
	
	private static final IModelCustom sphere = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sphere.obj"));
	
	protected RenderDeathBlast(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityDeathBlast entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if(!ClientProxy.renderingConstant)
			return;
		GL11.glPushMatrix();
    	GL11.glTranslatef((float)x, (float)y, (float)z);
    	GlStateManager.disableLighting();
        GlStateManager.enableCull();
        GlStateManager.disableTexture2D();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.depthMask(false);

		GL11.glPushMatrix();
			//GL11.glRotatef((entity.ticksExisted % 360) * 10, 0, 1, 0);
	        GlStateManager.enableBlend();
	        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	        //GL11.glDisable(GL11.GL_ALPHA_TEST);
	        
	        GlStateManager.color(1.0F, 0, 0);
	        
	        Vec3 vector = Vec3.createVectorHelper(0.5D, 0, 0);
	
	        Tessellator tessellator = Tessellator.getInstance();
	        BufferBuilder buf = tessellator.getBuffer();
			RenderHelper.disableStandardItemLighting();
			
	        for(int i = 0; i < 8; i++) {
	            buf.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
	            buf.pos(vector.xCoord, 250.0D, vector.zCoord).color(1.0F, 0, 0, 1.0F).endVertex();
	            buf.pos(vector.xCoord, 0.0D, vector.zCoord).color(1.0F, 0, 0, 1.0F).endVertex();
	        	vector.rotateAroundY(45);
	            buf.pos(vector.xCoord, 0.0D, vector.zCoord).color(1.0F, 0, 0, 1.0F).endVertex();
	            buf.pos(vector.xCoord, 250.0D, vector.zCoord).color(1.0F, 0, 0, 1.0F).endVertex();
	            tessellator.draw();
	        }

	        GlStateManager.color(1.0F, 0, 1.0F);
	        
	        for(int i = 0; i < 8; i++) {
	        	buf.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
	        	buf.pos(vector.xCoord / 2, 250.0D, vector.zCoord / 2).color(1.0F, 0, 1.0F, 1.0F).endVertex();
	        	buf.pos(vector.xCoord / 2, 0.0D, vector.zCoord / 2).color(1.0F, 0, 1.0F, 1.0F).endVertex();
	        	vector.rotateAroundY(45);
	        	buf.pos(vector.xCoord / 2, 0.0D, vector.zCoord / 2).color(1.0F, 0, 1.0F, 1.0F).endVertex();
	        	buf.pos(vector.xCoord / 2, 250.0D, vector.zCoord / 2).color(1.0F, 0, 1.0F, 1.0F).endVertex();
	            tessellator.draw();
	        }
	    GL11.glPopMatrix();
        
        GlStateManager.depthMask(true);
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        
        GL11.glPopMatrix();
        
        renderOrb(entity, x, y, z, entityYaw, partialTicks);
	}
	
	public void renderOrb(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
		GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GlStateManager.disableLighting();
        GlStateManager.enableCull();
        GlStateManager.disableTexture2D();
		
		double scale = 15 - 15D * (((double)entity.ticksExisted+p_76986_9_) / ((double)EntityDeathBlast.maxAge));
		float alpha = ((float)(entity.ticksExisted+p_76986_9_) / (float)(EntityDeathBlast.maxAge));
		
		if(scale < 0)
			scale = 0;
        GL11.glColor4d(1.0, 0, 1.0, alpha);

		GlStateManager.enableBlend();
        GL11.glScaled(scale, scale, scale);
        //OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        sphere.renderAll();

        GlStateManager.color(1.0F, 0, 0, alpha);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glScaled(1.5, 1.5, 1.5);
        sphere.renderAll();
        
		GlStateManager.disableBlend();
        GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
		GlStateManager.disableCull();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        
        GL11.glPopMatrix();
	}
	
	@Override
	public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {}

	@Override
	protected ResourceLocation getEntityTexture(EntityDeathBlast entity) {
		return null;
	}

	
	
}
