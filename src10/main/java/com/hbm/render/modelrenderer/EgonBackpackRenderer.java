package com.hbm.render.modelrenderer;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;

public class EgonBackpackRenderer extends ModelRenderer {

	public static boolean showBackpack = false;
	
	public EgonBackpackRenderer(ModelBase model) {
		super(model);
	}
	
	@Override
	public void render(float scale) {
		if(!showBackpack)
			return;
		GL11.glPushMatrix();
		//Oh neat, bob made the model so it would fit perfectly without screwing around with mostly right translations.
		GL11.glTranslated(0, 0.75F, 0);
		GL11.glScaled(0.0625, 0.0625, 0.0625);
		GL11.glRotated(180, 0, 0, 1);
		GL11.glRotated(90, 0, 1, 0);
		int tex = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.egon_backpack_tex);
		int prevShadeModel = GL11.glGetInteger(GL11.GL_SHADE_MODEL);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		ResourceManager.egon_backpack.renderAll();
		GlStateManager.shadeModel(prevShadeModel);
		GlStateManager.bindTexture(tex);
		GL11.glPopMatrix();
	}

}
