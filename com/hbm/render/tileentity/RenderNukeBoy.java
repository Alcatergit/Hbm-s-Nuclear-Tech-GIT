package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.tileentity.bomb.TileEntityNukeBoy;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderNukeBoy extends TileEntitySpecialRenderer<TileEntityNukeBoy> {

	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/LilBoy1.obj");
	private IModelCustom boyModel;
    private ResourceLocation boyTexture;
	
	public RenderNukeBoy() {
		boyModel = AdvancedModelLoader.loadModel(objTesterModelRL);
		boyTexture = new ResourceLocation(RefStrings.MODID, "textures/models/LilBoy2.png");
	}
	
	@Override
	public boolean isGlobalRenderer(TileEntityNukeBoy te) {
		return true;
	}
	
	@Override
	public void render(TileEntityNukeBoy te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
		switch(te.getBlockMetadata())
		{
		case 2:
			GL11.glRotatef(0, 0F, 1F, 0F);
	        GL11.glTranslated(-2.0D, 0.0D, 0.0D); break;
		case 4:
			GL11.glRotatef(90, 0F, 1F, 0F);
	        GL11.glTranslated(-2.0D, 0.0D, 0.0D); break;
		case 3:
			GL11.glRotatef(180, 0F, 1F, 0F);
	        GL11.glTranslated(-2.0D, 0.0D, 0.0D); break;
		case 5:
			GL11.glRotatef(-90, 0F, 1F, 0F);
	        GL11.glTranslated(-2.0D, 0.0D, 0.0D); break;
		}

        bindTexture(boyTexture);
        boyModel.renderAll();
        
        GlStateManager.enableCull();

        GL11.glPopMatrix();
	}
}
