package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityBombletZeta;
import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderBombletZeta extends Render<EntityBombletZeta> {

	public static final IRenderFactory<EntityBombletZeta> FACTORY = (RenderManager man) -> {return new RenderBombletZeta(man);};
	
	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/bombletTheta.obj");
	private IModelCustom boyModel;
    private ResourceLocation boyTexture;
	
	protected RenderBombletZeta(RenderManager renderManager) {
		super(renderManager);
		boyModel = AdvancedModelLoader.loadModel(objTesterModelRL);
		boyTexture = new ResourceLocation(RefStrings.MODID, "textures/models/projectiles/bombletZetaTexture.png");
	}
	
	@Override
	public void doRender(EntityBombletZeta entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
        
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        bindTexture(new ResourceLocation(RefStrings.MODID, "textures/models/projectiles/bombletZetaTexture.png"));
        
        boyModel.renderAll();
		GL11.glPopMatrix();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityBombletZeta entity) {
		return boyTexture;
	}
    
    
}
