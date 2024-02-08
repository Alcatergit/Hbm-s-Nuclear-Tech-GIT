package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.ModelMP40;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderMP40 extends TEISRBase {

	protected ModelMP40 swordModel;
	protected ResourceLocation mp40_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelMP40.png");
	
	public ItemRenderMP40() {
		swordModel = new ModelMP40();
	}
	
	@Override
	public void renderByItem(ItemStack itemStackIn) {
		GlStateManager.enableCull();
		Minecraft.getMinecraft().renderEngine.bindTexture(mp40_rl);
		
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			
			if (type == TransformType.FIRST_PERSON_RIGHT_HAND) {
				GL11.glTranslated(-0.2, 1.1, 0.8);
				GL11.glRotated(-00, 0, 1, 0);
				GL11.glRotated(-25, 0, 0, 1);
				GL11.glRotated(180, 1, 0, 0);
			} else {
				GL11.glTranslated(1.8, 1.1, 1.2);
				GL11.glRotated(180, 1, 0, 0);
				GL11.glRotated(180, 0, 1, 0);
				GL11.glRotated(25, 0, 0, 1);
			}
			
			swordModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case GROUND:
		case FIXED:
		case HEAD:
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glTranslated(1.0, 1, 0.7);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(180, 1, 0, 0);

			swordModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			
			break;
		default:
			break;
		}
	}
}
