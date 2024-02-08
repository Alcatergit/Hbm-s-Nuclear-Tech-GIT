package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.ModelOSIPR;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderOSIPR extends TEISRBase {

	protected ModelOSIPR osipr;
	protected static ResourceLocation osipr_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelOSIPR.png");
	
	public ItemRenderOSIPR() {
		osipr = new ModelOSIPR();
	}
	
	@Override
	public void renderByItem(ItemStack itemStackIn) {
		Minecraft.getMinecraft().renderEngine.bindTexture(osipr_rl);
		GlStateManager.enableCull();
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
			GL11.glTranslated(0, 0, -0.2);
		case FIRST_PERSON_RIGHT_HAND:
			GL11.glScaled(0.5, 0.5, 0.5);
			GL11.glTranslated(1.0, 1.1, 1.5);
			if(type == TransformType.FIRST_PERSON_RIGHT_HAND){
				GL11.glRotated(10, 0, 1, 0);
				GL11.glRotated(-50, 0, 0, 1);
				GL11.glRotated(190, 1, 0, 0);
			} else {
				GL11.glRotated(180, 0, 1, 0);
				GL11.glRotated(180, 1, 0, 0);
				GL11.glRotated(50, 0, 0, 1);
			}
			
			osipr.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			if(type == TransformType.THIRD_PERSON_LEFT_HAND)
				GL11.glTranslated(0.1, 0, 0);
			if(type == TransformType.GROUND)
				GL11.glTranslated(0.05, 0.1, 0);
			GL11.glTranslated(0.4, 0.6, 0.7);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(180, 1, 0, 0);
			osipr.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		default:
			break;
		}
	}
}
