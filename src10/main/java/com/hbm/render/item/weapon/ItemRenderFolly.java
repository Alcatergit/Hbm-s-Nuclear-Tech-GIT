package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.GunFolly;
import com.hbm.lib.RefStrings;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.ModelFolly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderFolly extends TEISRBase {

	protected ModelFolly folly;
	protected static ResourceLocation folly_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelFolly.png");
	
	public ItemRenderFolly() {
		folly = new ModelFolly();
	}
	
	@Override
	public void renderByItem(ItemStack itemStackIn) {
		Minecraft.getMinecraft().renderEngine.bindTexture(folly_rl);
		switch (type) {
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			if (type == TransformType.FIRST_PERSON_RIGHT_HAND) {
				GL11.glTranslated(0.5, 1.3, 1.6);
				GL11.glRotated(-00, 0, 1, 0);
				GL11.glRotated(-25, 0, 0, 1);
				GL11.glRotated(180, 1, 0, 0);
			} else {
				GL11.glTranslated(1.5, 1.3, 1.4);
				GL11.glRotated(180, 1, 0, 0);
				GL11.glRotated(180, 0, 1, 0);
				GL11.glRotated(25, 0, 0, 1);
			}

			GL11.glTranslatef(0.6F, 0.0F, 0.0F);

			int state = GunFolly.getState(itemStackIn);
			int time = GunFolly.getTimer(itemStackIn);
			folly.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, state, time);
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case GROUND:
		case FIXED:
		case HEAD:
			GL11.glTranslated(0.5, 0.5, 0.3);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(180, 1, 0, 0);

			

			state = GunFolly.getState(itemStackIn);
			time = GunFolly.getTimer(itemStackIn);
			folly.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, state, time);
			break;
		default:
			break;
		}
	}
}
