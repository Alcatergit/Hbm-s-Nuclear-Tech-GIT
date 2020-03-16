package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.tileentity.machine.TileEntityMachineOilWell;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderDerrick extends TileEntitySpecialRenderer<TileEntityMachineOilWell> {

	private static final ResourceLocation body = new ResourceLocation(RefStrings.MODID, "models/derrick.obj");
	private IModelCustom genModel;
    private ResourceLocation genTexture;
    
    @Override
    public boolean isGlobalRenderer(TileEntityMachineOilWell te) {
    	return true;
    }
    
    public RenderDerrick() {
    	genModel = AdvancedModelLoader.loadModel(body);
		genTexture = new ResourceLocation(RefStrings.MODID, "textures/models/derrick.png");
	}
	
	@Override
	public void render(TileEntityMachineOilWell te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GlStateManager.enableLighting();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GlStateManager.enableCull();
		GL11.glRotatef(180, 0F, 1F, 0F);

        bindTexture(genTexture);
        
        genModel.renderAll();

        GL11.glPopMatrix();
	}
}
