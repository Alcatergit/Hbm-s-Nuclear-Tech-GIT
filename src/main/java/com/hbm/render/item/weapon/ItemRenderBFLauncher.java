package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.ModelBFLauncher;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderBFLauncher extends TEISRBase {

	protected ModelBFLauncher swordModel;
	protected static ResourceLocation bf_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/BFLauncher.png");
	
	public ItemRenderBFLauncher() {
		swordModel = new ModelBFLauncher();
	}
	
	@Override
	public void renderByItem(ItemStack itemStackIn) {
		GL11.glPopMatrix();
		GlStateManager.enableCull();
		Minecraft.getMinecraft().renderEngine.bindTexture(bf_rl);
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
			GL11.glTranslated(0.0, 0, -0.2);
		case FIRST_PERSON_RIGHT_HAND:
			GL11.glTranslated(0, -0.2, 0.2);
			GL11.glRotated(180, 1, 0, 0);
			GL11.glRotated(40, 0, 0, 1);
			if(type == TransformType.FIRST_PERSON_LEFT_HAND){
				GL11.glRotated(100, 0, 0, 1);
				GL11.glRotated(180, 1, 0, 0);
			}
			swordModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		case THIRD_PERSON_RIGHT_HAND:
		case THIRD_PERSON_LEFT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			GL11.glTranslated(-0.1, -0.1, 0.6);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glRotated(180, 0, 0, 1);
			GL11.glScaled(1.5, 1.5, 1.5);
			swordModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		default:
			break;
		}
		GL11.glPushMatrix();
	}
}
