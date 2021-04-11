package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.animloader.AnimationWrapper;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.item.TEISRBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class ItemRenderJShotgun extends TEISRBase {

	@Override
	public void renderByItem(ItemStack itemStackIn) {
		GL11.glTranslated(0.5, 0.5, 0.5);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.jshotgun_tex);
		GlStateManager.enableCull();
		
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			EnumHand hand = type == TransformType.FIRST_PERSON_LEFT_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
			AnimationWrapper reload = HbmAnimations.getRelevantBlenderAnim(hand);
			double[] recoil = HbmAnimations.getRelevantTransformation("JS_RECOIL", hand);
			double[] recoil2 = HbmAnimations.getRelevantTransformation("JS_RECOIL2", hand);
			GL11.glScaled(0.5, 0.5, 0.5);
			if(type == TransformType.FIRST_PERSON_RIGHT_HAND){
				GL11.glRotated(178, 0, 1, 0);
				GL11.glRotated(27+recoil[1] + recoil2[1], 0, 0, 1);
				GL11.glTranslated(14, -16, 3);
			} else {
				GL11.glRotated(27+recoil[1] + recoil2[1], 0, 0, 1);
				GL11.glRotated(2, 0, 1, 0);
				GL11.glTranslated(13, -16, -4);
			}
			GL11.glTranslated(recoil[2] + recoil2[2], 0, 0);
			if(reload != null){
				ResourceManager.jshotgun.controller.setAnim(reload);
				ResourceManager.arm_rig.controller.setAnim(reload);
				Minecraft.getMinecraft().getTextureManager().bindTexture(Minecraft.getMinecraft().player.getLocationSkin());
				ResourceManager.arm_rig.renderAnimated(System.currentTimeMillis());
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.jshotgun_tex);
			} else {
				ResourceManager.jshotgun.controller.setAnim(AnimationWrapper.EMPTY);
			}
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			GL11.glTranslated(0, -0.65, -0.3);
			GL11.glRotated(90, 0, 1, 0);
			break;
		case GUI:
			GL11.glTranslated(0, -0.25, 0);
			GL11.glScaled(0.02, 0.02, 0.02);
			GL11.glRotated(180, 0, 1, 0);
			GL11.glRotated(45, 0, 0, 1);
			break;
		case NONE:
			break;
		}
		ResourceManager.jshotgun.renderAnimated(System.currentTimeMillis());
	}
}
