package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.tileentity.machine.TileEntityMachineRefinery;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderRefinery extends TileEntitySpecialRenderer<TileEntityMachineRefinery> {

	private static final ResourceLocation body = new ResourceLocation(RefStrings.MODID, "models/refinery.obj");
	private IModelCustom genModel;
    private ResourceLocation genTexture;
	
	public RenderRefinery() {
		genModel = AdvancedModelLoader.loadModel(body);
		genTexture = new ResourceLocation(RefStrings.MODID, "textures/models/refinery.png");
	}
	
	@Override
	public boolean isGlobalRenderer(TileEntityMachineRefinery te) {
		return true;
	}
	
	@Override
	public void render(TileEntityMachineRefinery te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

        bindTexture(genTexture);
        
        genModel.renderAll();

        GL11.glPopMatrix();
	}
}
