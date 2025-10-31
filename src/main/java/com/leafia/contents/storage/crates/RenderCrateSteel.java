package com.leafia.contents.storage.crates;

import com.hbm.tileentity.machine.TileEntityCrateSteel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.Arrays;
import java.util.List;

public class RenderCrateSteel extends TileEntitySpecialRenderer<TileEntityCrateSteel> {
	
	protected static final float surfaceOffset = 0.501F;

	void renderTxt(double x, double y, double z,float rx,float ry,float rz,float yoffset,int alignment,float scale,String text,boolean vertical) {
		GL11.glPushMatrix();
		if (vertical)
			yoffset = -yoffset;
		GL11.glTranslated(x + 0.5F, y + 0.5F, z + 0.5F);
		GL11.glRotatef(rx, 1F, 0F, 0F);
		GL11.glRotatef(ry, 0F, 1F, 0F);
		GL11.glRotatef(rz, 0F, 0F, 1F);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GlStateManager.depthMask(false);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GlStateManager.color(1, 1, 1, 1);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		GL11.glTranslated(surfaceOffset, 0, 0);
		GL11.glTranslated(0F,yoffset,0F);

		FontRenderer font = Minecraft.getMinecraft().fontRenderer;

		int width = font.getStringWidth(text);
		int height = font.FONT_HEIGHT-1;

		float pix = 1F/7F/16F;

		float f3 = pix*scale;
		if (!vertical)
			GL11.glScalef(f3, -f3, f3);
		else
			GL11.glScalef(-f3, f3, -f3);
		GL11.glNormal3f(0.0F, 0.0F, -1.0F);
		GL11.glRotatef(90, 0, 1, 0);
		font.drawString(text, -width / 2, -height / 2 * alignment, 0xFFFFFF);

		GlStateManager.depthMask(true);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	@Override
	public void render(TileEntityCrateSteel te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		String labelUpper = "handle with care";
		String labelMiddle = "1";
		String labelLower = "handle with care";
		List<String> labelVertical = Arrays.asList(
				"machine parts",
				"",
				"machine parts",
				""
		);
		float middleScale = 3F;
		if (labelMiddle.length() == 3)
			middleScale = 4F;
		if (labelMiddle.length() < 3)
			middleScale = 5F;
		float vRot = 0F;
		float offs = 0.5F-(1F/16F*0.75F)/2F;
		int orientation = 0;
		for (int rot = 0;rot < 358;rot+=90) {
			renderTxt(x, y, z, 0F, rot, 0F, offs, 0, 1.25F, labelUpper,false);
			renderTxt(x, y, z, 0F, rot, 0F, 0F, 1, middleScale, labelMiddle,false);
			renderTxt(x, y, z, 0F, rot, 0F, -offs, 2, 1.25F, labelLower,false);

			renderTxt(x, y, z, rot, 0F, 90F, -offs, 2, 1.25F, labelVertical.get(orientation),true);
			renderTxt(x, y, z, rot, 0F, -90F, -offs, 2, 1.25F, labelVertical.get(orientation),true);
			orientation++;
		}
		renderTxt(x, y, z, vRot, 0F, 90F, 0F, 1, middleScale, labelMiddle,true);
		renderTxt(x, y, z, vRot, 0F, -90F, 0F, 1, middleScale, labelMiddle,true);
		/*
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5F, y + 0.5F, z + 0.5F);
		switch(te.getBlockMetadata()) {
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		}
		GL11.glTranslated(surfaceOffset, 0, 0);
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
		GL11.glPopMatrix();*/
	}
}
