package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;

public class ItemRenderBase extends TEISRBase {

	@Override
	public void renderByItem(ItemStack itemStackIn) {
		GL11.glPushMatrix();
		GlStateManager.enableCull();
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			GL11.glScaled(0.4, 0.4, 0.4);
			GL11.glRotated(-90, 0, 1, 0);
			renderNonInv(itemStackIn);
			break;
		case GUI:
			GlStateManager.enableLighting();
			GL11.glRotated(30, 1, 0, 0);
			GL11.glRotated(45+180, 0, 1, 0);
			GL11.glScaled(0.062, 0.062, 0.062);
			GL11.glTranslated(0, 12, -11.3);
			renderInventory(itemStackIn);
			break;
		case NONE:
			break;
		}
		renderCommon(itemStackIn);
		GL11.glPopMatrix();
	}
	
	public void renderNonInv(ItemStack stack) { renderNonInv(); }
	public void renderInventory(ItemStack stack) { renderInventory(); }
	public void renderCommon(ItemStack stack) { renderCommon(); }
	public void renderNonInv() { }
	public void renderInventory() { }
	public void renderCommon() { }
}
