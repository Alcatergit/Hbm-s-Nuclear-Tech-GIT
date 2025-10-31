package com.hbm.render.util;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;

public class TomPronter {

	
	public static void prontTom(int type) {
		GL11.glPushMatrix();

		GlStateManager.disableCull();
		GlStateManager.disableLighting();
		GL11.glRotatef(180F, 1F, 0F, 0F);
		GL11.glScalef(2F, 4F, 2F);
		
		TextureManager tex = Minecraft.getMinecraft().getTextureManager();
		
		tex.bindTexture(ResourceManager.tom_main_tex);
		ResourceManager.tom_main.renderAll();
		
    	HmfController.setMod(50000D, 2500D);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GlStateManager.disableAlpha();

        float rot = -System.currentTimeMillis() / 10 % 360;
		//GL11.glScalef(1.2F, 2F, 1.2F);
		GL11.glScalef(0.8F, 5F, 0.8F);
		
		Random rand = new Random(0);

		if(type == 0)
			tex.bindTexture(ResourceManager.tom_flame_tex);
		if(type == 1)
			tex.bindTexture(ResourceManager.tom_flame_o_tex);
		
        for(int i = 0; i < 20/*10*/; i++) {
			
			int r = rand.nextInt(90);
			
			GL11.glRotatef(rot + r, 0, 1, 0);
			
			ResourceManager.tom_flame.renderAll();
			
			GL11.glRotatef(rot, 0, -1, 0);
			
			GL11.glScalef(-1.015F, 0.9F, 1.015F);
        }
		
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();

		GlStateManager.enableCull();
		GlStateManager.enableLighting();
        HmfController.resetMod();
		
		GL11.glPopMatrix();
	}

}