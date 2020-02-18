package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.tileentity.machine.TileEntityMachineUF6Tank;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderUF6Tank extends TileEntitySpecialRenderer<TileEntityMachineUF6Tank> {

	private static final ResourceLocation tankModel = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/tank.obj");
	private IModelCustom tankModelC;
    private ResourceLocation tankTexture;
	
	public RenderUF6Tank() {
		tankModelC = AdvancedModelLoader.loadModel(tankModel);
		tankTexture = new ResourceLocation(RefStrings.MODID, "textures/models/UF6Tank.png");
	}
	
	@Override
	public boolean isGlobalRenderer(TileEntityMachineUF6Tank te) {
		return true;
	}
	
	@Override
	public void render(TileEntityMachineUF6Tank te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
		switch(te.getBlockMetadata())
		{
		case 4:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 2:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

        bindTexture(tankTexture);
        tankModelC.renderAll();

        GL11.glPopMatrix();
	}
}
