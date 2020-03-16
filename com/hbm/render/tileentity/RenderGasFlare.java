package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.tileentity.machine.TileEntityMachineGasFlare;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderGasFlare extends TileEntitySpecialRenderer<TileEntityMachineGasFlare> {

	private static final ResourceLocation body = new ResourceLocation(RefStrings.MODID, "models/oilFlare.obj");
	private IModelCustom genModel;
    private ResourceLocation genTexture;
	
    public RenderGasFlare() {
    	genModel = AdvancedModelLoader.loadModel(body);
		genTexture = new ResourceLocation(RefStrings.MODID, "textures/models/oilFlareTexture.png");
	}
    
	@Override
	public boolean isGlobalRenderer(TileEntityMachineGasFlare te) {
		return true;
	}
	
	@Override
	public void render(TileEntityMachineGasFlare te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
		GL11.glRotatef(180, 0F, 1F, 0F);

        bindTexture(genTexture);
        
        genModel.renderAll();

        GlStateManager.enableCull();
        GL11.glPopMatrix();
	}
}
