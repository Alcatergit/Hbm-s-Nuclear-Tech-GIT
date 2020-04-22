package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;

public class ItemRenderShim extends TEISRBase {
	
	public void renderByItem(ItemStack stack) {
		switch(type) {
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			if(stack.getItem() == ModItems.shimmer_sledge)
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.shimmer_sledge_tex);
			if(stack.getItem() == ModItems.shimmer_axe)
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.shimmer_axe_tex);
			GL11.glScalef(1.5F, 1.5F, 1.5F);
			if(type == TransformType.FIRST_PERSON_RIGHT_HAND){
				GL11.glTranslatef(0.2F, 0.1F, 0.5F);
				GL11.glRotated(-45, 0, 0, 1);
				GL11.glRotated(180, 0, 1, 0);
			} else {
				GL11.glTranslatef(0.5F, 0.1F, 0.5F);
				GL11.glRotated(45, 0, 0, 1);
			}
			if(stack.getItem() == ModItems.shimmer_sledge)
				ResourceManager.shimmer_sledge.renderAll();
			if(stack.getItem() == ModItems.shimmer_axe)
				ResourceManager.shimmer_axe.renderAll();
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case GROUND:
			GL11.glTranslated(0.3, 0, 0.3);
		case FIXED:
		case HEAD:
			if(stack.getItem() == ModItems.shimmer_sledge)
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.shimmer_sledge_tex);
			if(stack.getItem() == ModItems.shimmer_axe)
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.shimmer_axe_tex);
			GL11.glScaled(2.5, 2.5, 2.5);
			GL11.glTranslated(0.1, -0.1, 0.15);
			GL11.glRotated(90, 0, 1, 0);
			if(stack.getItem() == ModItems.shimmer_sledge)
				ResourceManager.shimmer_sledge.renderAll();
			if(stack.getItem() == ModItems.shimmer_axe)
				ResourceManager.shimmer_axe.renderAll();
			break;
		default:
			break;
		}
	}
}
