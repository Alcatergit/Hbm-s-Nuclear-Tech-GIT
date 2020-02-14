package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;

public class ItemRenderWeaponObj extends TEISRBase {

	@Override
	public void renderByItem(ItemStack itemStackIn) {
		GL11.glPopMatrix();
		GlStateManager.disableCull();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.hk69_tex);
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
			GL11.glTranslated(2.0, 0, -0.4);
			GL11.glRotated(10, 0, 1, 0);
		case FIRST_PERSON_RIGHT_HAND:
			GL11.glTranslated(-1.0, 0.0, -0.2);
			if(type == TransformType.FIRST_PERSON_LEFT_HAND){
				GL11.glRotated(-180, 0, 1, 0);
			}
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(-25, 1, 0, 0);
			GL11.glRotated(-5, 0, 1, 0);
			break;
		case HEAD:
		case FIXED:
		case GROUND:
			GL11.glTranslated(0.0, -0.2, 0.5);
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
			GL11.glTranslated(0.0, -0.3, -0.5);
			GL11.glRotated(180, 0, 1, 0);
			break;
		default:
			break;
		}
		ResourceManager.hk69.renderAll();
		GlStateManager.enableCull();
		GL11.glPushMatrix();
	}
}
