package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.tileentity.bomb.TileEntityNukeFleija;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;



public class RenderNukeFleija extends TileEntitySpecialRenderer<TileEntityNukeFleija> {

	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/Fleija.obj");
	//private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/Prototype.obj");
	private IModelCustom manModel;
    private ResourceLocation manTexture;
	
    public RenderNukeFleija()
    {
		manModel = AdvancedModelLoader.loadModel(objTesterModelRL);
		manTexture = new ResourceLocation(RefStrings.MODID, "textures/models/Fleija.png");
		//manTexture = new ResourceLocation(RefStrings.MODID, "textures/models/Prototype.png");
    }
    
    @Override
    public void render(TileEntityNukeFleija te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    	GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
		switch(te.getBlockMetadata())
		{
		case 5:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 2:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

        bindTexture(manTexture);
        manModel.renderAll();
        GL11.glPopMatrix();
    }
}
