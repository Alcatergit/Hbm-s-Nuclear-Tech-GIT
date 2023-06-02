package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.item.TEISRBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class ItemRenderWeaponAR15 extends TEISRBase {

	@Override
	public void renderByItem(ItemStack stack){
		GL11.glPushMatrix();
		
		GlStateManager.enableCull();

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.ar15_tex);
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
			GL11.glTranslated(1.95, 0.4, 0.5);
			GL11.glScaled(0.25, 0.25, 0.25);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glRotated(-30, 1, 0, 0);
			break;
		case FIRST_PERSON_RIGHT_HAND:
			GL11.glTranslated(-1, 0.5, 0.5);
			GL11.glScaled(0.25, 0.25, 0.25);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(-30, 1, 0, 0);
			GL11.glRotated(-5, 0, 1, 0);
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			GL11.glTranslated(0.5, -0.2, -0.2);
			GL11.glScaled(0.125, 0.125, 0.125);
			GL11.glRotated(180, 0, 1, 0);
			break;
		case GUI:
			GlStateManager.enableLighting();
			GL11.glTranslated(0.4, 0.4, 0);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(-40, 1, 0, 0);
			GL11.glScaled(0.05, 0.05, 0.05);
			break;
		case NONE:
			break;
		}
		
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		ResourceManager.ar15.renderAll();
		GlStateManager.shadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
