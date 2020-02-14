package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelRevolver;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderRevolverInverted extends TEISRBase {
	
	protected ModelRevolver swordModel;
	protected static ResourceLocation revolver_rl = new ResourceLocation(RefStrings.MODID +":textures/models/ModelRevolver.png");
	
	public ItemRenderRevolverInverted() {
		swordModel = new ModelRevolver();
	}
	
	@Override
	public void renderByItem(ItemStack itemStackIn) {
		Minecraft.getMinecraft().renderEngine.bindTexture(revolver_rl);
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
			GL11.glTranslated(0, 0, 0);
		case FIRST_PERSON_RIGHT_HAND:
			GL11.glScaled(0.5, 0.5, 0.5);
			GL11.glTranslated(1.0, 1.0, 1.2);
			if(type == TransformType.FIRST_PERSON_RIGHT_HAND){
				
				GL11.glRotated(-40, 0, 0, 1);
				GL11.glRotated(180, 1, 0, 0);
				GL11.glRotated(180, 0, 1, 0);
			} else {
				GL11.glRotated(180, 0, 1, 0);
				GL11.glRotated(180, 1, 0, 0);
				GL11.glRotated(50, 0, 0, 1);
				GL11.glRotated(180, 0, 1, 0);
			}
			
			swordModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			if(type == TransformType.GROUND)
				GL11.glTranslated(0.05, 0, 0);
			GL11.glTranslated(0.55, 0.25, 0.5);
			GL11.glRotated(-90, 0, 1, 0);
			//GL11.glRotated(180, 1, 0, 0);
			GL11.glRotated(180, 0, 0, 1);
			swordModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		default:
			break;
		}
	}
}
