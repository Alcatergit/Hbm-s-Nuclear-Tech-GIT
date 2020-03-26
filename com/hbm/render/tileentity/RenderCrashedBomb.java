package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.tileentity.bomb.TileEntityCrashedBomb;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderCrashedBomb extends TileEntitySpecialRenderer<TileEntityCrashedBomb> {

	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/BalefireCrashed.obj");
	private IModelCustom manModel;
    private ResourceLocation manTexture;
    
    public RenderCrashedBomb() {
    	manModel = AdvancedModelLoader.loadModel(objTesterModelRL);
		manTexture = new ResourceLocation(RefStrings.MODID, "textures/models/BalefireCrashed.png");
	}
    
    @Override
    public boolean isGlobalRenderer(TileEntityCrashedBomb te) {
    	return true;
    }
    
    @Override
    public void render(TileEntityCrashedBomb te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    	GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.disableCull();
        GL11.glEnable(GL11.GL_LIGHTING);
        GlStateManager.enableLighting();
		switch(te.getBlockMetadata())
		{
		case 5:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 2:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(-90, 0F, 1F, 0F); break;
		}

        bindTexture(manTexture);
        manModel.renderAll();

        GlStateManager.enableCull();
        GL11.glPopMatrix();
    }
}
