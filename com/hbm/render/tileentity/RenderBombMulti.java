package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.tileentity.bomb.TileEntityBombMulti;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderBombMulti extends TileEntitySpecialRenderer<TileEntityBombMulti> {

	private static final ResourceLocation bombModel = new ResourceLocation(RefStrings.MODID, "models/BombGeneric.obj");
	private IModelCustom bombModelC;
    private ResourceLocation bombTexture;
    
    public RenderBombMulti() {
    	bombModelC = AdvancedModelLoader.loadModel(bombModel);
		bombTexture = new ResourceLocation(RefStrings.MODID, "textures/models/BombGeneric.png");
	}
    
    @Override
    public boolean isGlobalRenderer(TileEntityBombMulti te) {
    	return true;
    }
    
    @Override
    public void render(TileEntityBombMulti te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    	GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
        GL11.glRotatef(180, 1F, 0F, 0F);
        
		switch(te.getBlockMetadata())
		{
		case 5:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 2:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		}

        bindTexture(bombTexture);
        bombModelC.renderAll();

        GlStateManager.enableCull();
        GL11.glPopMatrix();
    }
}
