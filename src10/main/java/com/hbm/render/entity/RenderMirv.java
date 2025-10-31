package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.missile.EntityMIRV;
import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMirv extends Render<EntityMIRV> {

	public static final IRenderFactory<EntityMIRV> FACTORY = (RenderManager man) -> {return new RenderMirv(man);};
	
	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/Mirv.obj");
	private IModelCustom boyModel;
    private ResourceLocation boyTexture;
	
	protected RenderMirv(RenderManager renderManager) {
		super(renderManager);
		boyModel = AdvancedModelLoader.loadModel(objTesterModelRL);
		boyTexture = new ResourceLocation(RefStrings.MODID, "textures/models/misc/universaldark.png");
	}

	@Override
	public void doRender(EntityMIRV entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
        GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
        
        bindTexture(boyTexture);
        boyModel.renderAll();
		GL11.glPopMatrix();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityMIRV entity) {
		return boyTexture;
	}

}
