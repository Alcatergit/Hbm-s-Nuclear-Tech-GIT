package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.lib.Library;
import com.hbm.blocks.network.energy.BlockCableGauge.TileEntityCableGauge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderCableGauge extends TileEntitySpecialRenderer<TileEntityCableGauge> {
	
	protected static final float fontOffset = 0.501F;

	@Override
	public void render(TileEntityCableGauge te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5F, y + 0.5F, z + 0.5F);
		switch(te.getBlockMetadata()) {
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		}
		GL11.glTranslated(fontOffset, 0, 0);
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GlStateManager.depthMask(false);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GlStateManager.color(1, 1, 1, 1);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			
		String text = Library.getShortNumber(te.deltaLastSecond);
		if(text != null && ! text.isEmpty()) {

			int width = font.getStringWidth(text);
			int height = font.FONT_HEIGHT;
			
			float f3 = Math.min(0.03F, 0.8F / Math.max(width, 1));
			GL11.glScalef(f3, -f3, f3);
			GL11.glNormal3f(0.0F, 0.0F, -1.0F);
			GL11.glRotatef(90, 0, 1, 0);
			
			font.drawString(text, -width / 2, -height / 2, 0xff8000);
		}
		GlStateManager.depthMask(true);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
}
