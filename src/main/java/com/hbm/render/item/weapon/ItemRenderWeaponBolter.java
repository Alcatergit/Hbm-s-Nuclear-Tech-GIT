package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.ItemGunBase;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.item.TEISRBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class ItemRenderWeaponBolter extends TEISRBase {

	@Override
	public void renderByItem(ItemStack itemStackIn) {
		GlStateManager.enableCull();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.bolter_tex);
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			
			
			if(type == TransformType.FIRST_PERSON_RIGHT_HAND) {
				GL11.glTranslated(0.3, 0.9, -0.3);
				GL11.glRotated(205, 0, 0, 1);
				GL11.glTranslated(-0.2, 1.1, 0.8);
				GL11.glRotated(-25, 0, 0, 1);
				GL11.glRotated(180, 1, 0, 0);
			} else {
				GL11.glTranslated(0, 0, 0.9);
				GL11.glRotated(0, 0, 0, 1);
			}
			
			double s0 = 0.25D;
			GL11.glRotated(25, 0, 0, 1);
			GL11.glTranslated(1.25, -0.25, -0.25);
			GL11.glRotated(-95, 0, 1, 0);
			GL11.glScaled(s0, s0, s0);
			
			if(type == TransformType.FIRST_PERSON_LEFT_HAND) {
				GL11.glRotated(18, 0, 1, 0);
				GL11.glRotated(-2, 0, 0, 1);
			}
			
			EnumHand hand = type == TransformType.FIRST_PERSON_LEFT_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;

			double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL", hand);
			GL11.glRotated(recoil[0] * 5, 1, 0, 0);
			GL11.glTranslated(0, 0, recoil[0]);

			double[] tilt = HbmAnimations.getRelevantTransformation("TILT", hand);
			GL11.glTranslated(0, tilt[0], 3);
			GL11.glRotated(tilt[0] * 35, 1, 0, 0);
			GL11.glTranslated(0, 0, -3);

			ResourceManager.bolter.renderPart("Body");

			double[] mag = HbmAnimations.getRelevantTransformation("MAG", hand);
			GL11.glPushMatrix();
			GL11.glTranslated(0, 0, 5);
			GL11.glRotated(mag[0] * 60 * (mag[2] == 1 ? 2.5 : 1), -1, 0, 0);
			GL11.glTranslated(0, 0, -5);
			ResourceManager.bolter.renderPart("Mag");
			if(mag[2] != 1)
				ResourceManager.bolter.renderPart("Bullet");
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			double[] casing = HbmAnimations.getRelevantTransformation("EJECT", hand);
			GL11.glTranslated(casing[2] * 5, casing[2] * 2, 0);
			GL11.glRotated(casing[2] * 60, 1, 0, 0);
			ResourceManager.bolter.renderPart("Casing");
			GL11.glPopMatrix();
			
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case GROUND:
		case FIXED:
		case HEAD:
			GL11.glScalef(0.3F, 0.3F, 0.3F);
			GL11.glTranslated(1.75, -0.5, 0.4);
			break;
		case GUI:
			GL11.glTranslated(0.4, 0.35, 0);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glRotated(45, 1, 0, 0);
			GL11.glScaled(0.15, 0.15, 0.15);
			break;
		default:
			break;
		}
		
		if(type != TransformType.FIRST_PERSON_LEFT_HAND && type != TransformType.FIRST_PERSON_RIGHT_HAND) {
			ResourceManager.bolter.renderAll();
		}
		GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
        GL11.glDisable(GL11.GL_LIGHTING);
        GlStateManager.disableCull();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        float lastX = OpenGlHelper.lastBrightnessX;
        float lastY = OpenGlHelper.lastBrightnessY;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);

        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        String s = ItemGunBase.getMag(itemStackIn) + "";
        float f3 = 0.04F;
        GL11.glTranslatef(0.025F -(font.getStringWidth(s) / 2) * 0.04F, 2.11F, 2.91F);
        GL11.glScalef(f3, -f3, f3);
        GL11.glRotatef(45, 1, 0, 0);
        GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);
        font.drawString(s, 0, 0, 0xff0000);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopAttrib();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastX, lastY);

		GL11.glPopMatrix();
	}
}
