package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelTestRender;
import com.hbm.tileentity.deco.TileEntityTestRender;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderTestRender extends TileEntitySpecialRenderer<TileEntityTestRender> {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/models/misc/modeltestrender.png");
	
	private ModelTestRender model;
	
	public RenderTestRender(){
		this.model = new ModelTestRender();
	}
	
	@Override
	public void render(TileEntityTestRender te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
			GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
			GL11.glRotatef(180, 0F, 0F, 1F);
		
			this.bindTexture(texture);
		
			GL11.glPushMatrix();
			this.model.renderModel(0.0625F);
			GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
	
}
