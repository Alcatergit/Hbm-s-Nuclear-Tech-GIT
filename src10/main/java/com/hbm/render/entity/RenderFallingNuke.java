package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityFallingNuke;
import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFallingNuke extends Render<EntityFallingNuke> {

	public static final IRenderFactory<EntityFallingNuke> FACTORY = (RenderManager man) -> {return new RenderFallingNuke(man);};
	
	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/bombs/LilBoy.obj");
	private IModelCustom boyModel;
    private ResourceLocation boyTexture;
    private static final ResourceLocation gadget_rl = new ResourceLocation(RefStrings.MODID +":textures/models/bombs/gadget.png");
	
	protected RenderFallingNuke(RenderManager renderManager) {
		super(renderManager);
		boyModel = AdvancedModelLoader.loadModel(objTesterModelRL);
		boyTexture = new ResourceLocation(RefStrings.MODID, "textures/models/bombs/CustomNuke.png");
	}

	@Override
	public void doRender(EntityFallingNuke entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        
		switch(entity.getDataManager().get(EntityFallingNuke.FACING))
		{
		case NORTH:
			GL11.glRotatef(0, 0F, 1F, 0F);
	        GL11.glTranslated(-2.0D, 0.0D, 0.0D); break;
		case WEST:
			GL11.glRotatef(90, 0F, 1F, 0F);
	        GL11.glTranslated(-2.0D, 0.0D, 0.0D); break;
		case SOUTH:
			GL11.glRotatef(180, 0F, 1F, 0F);
	        GL11.glTranslated(-2.0D, 0.0D, 0.0D); break;
		case EAST:
			GL11.glRotatef(-90, 0F, 1F, 0F);
	        GL11.glTranslated(-2.0D, 0.0D, 0.0D); break;
	    default:
	       	break;
		}
        
		float f = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
		
		if(f < -80)
			f = 0;
		
        GL11.glRotatef(f, 0.0F, 0.0F, 1.0F);

        bindTexture(boyTexture);
        boyModel.renderAll();
        
		GL11.glPopMatrix();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityFallingNuke entity) {
		return gadget_rl;
	}

}
