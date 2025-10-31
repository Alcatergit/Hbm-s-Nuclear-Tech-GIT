package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.ModelBigSword;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderBigSword extends TEISRBase {

	protected ModelBigSword bigSwordModel;

	public ItemRenderBigSword() {
		bigSwordModel = new ModelBigSword();
	}

	@Override
	public void renderByItem(ItemStack itemStackIn) {
		switch(type) {
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/weapons/ModelBigSwordTexture.png"));
			if(type == TransformType.FIRST_PERSON_RIGHT_HAND){
				GL11.glTranslated(0.2, 0.2, 0.5);
				GL11.glRotated(135, 0, 0, 1);
				GL11.glRotated(90, 0, 1, 0);
			} else {
				GL11.glTranslated(0.7, 0.2, 0.7);
				GL11.glRotated(225, 0, 0, 1);
				GL11.glRotated(90, 0, 1, 0);
			}
			bigSwordModel.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case GROUND:
		case FIXED:
		case HEAD:
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/weapons/ModelBigSwordTexture.png"));
			GL11.glScaled(1.5, 1.5, 1.5);
			GL11.glRotatef(-180.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotated(5, 1, 0, 0);
			GL11.glTranslatef(0.3F, 0F, -0.5F);
			bigSwordModel.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		default:
			break;
		}
	}
}
