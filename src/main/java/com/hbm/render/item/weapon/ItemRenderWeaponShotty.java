package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.animloader.AnimationWrapper;
import com.hbm.config.GeneralConfig;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.item.TEISRBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;

public class ItemRenderWeaponShotty extends TEISRBase {

	@Override
	public void renderByItem(ItemStack item) {
		GL11.glPopMatrix();
		GlStateManager.disableCull();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.universal);
		switch(type) {
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			if(GeneralConfig.ssgAnim) {
				if(type == TransformType.FIRST_PERSON_LEFT_HAND) {
					GL11.glTranslated(-0.81, -0.7, 0.4);
					GL11.glRotated(4, 1, 0, 0);
					GL11.glRotatef(23F, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(5F, 0.0F, 1.0F, 0.0F);
					GL11.glScaled(1, 1.5, 1.5);
				} else {
					GL11.glTranslated(0, -7, -0.6);
					double[] recoil = HbmAnimations.getRelevantTransformation("MEATHOOK_RECOIL", EnumHand.MAIN_HAND);
					GL11.glTranslated(recoil[2], recoil[1], 0);
					if(this.entity != null && this.entity.isSneaking()) {
						GL11.glTranslated(0, 0.20, 0.43);
						GL11.glRotated(-4, 1, 0, 0);
						GL11.glRotated(5, 0, 1, 0);
						GL11.glRotated(-4, 0, 0, 1);
					}
					GL11.glRotated(4, 1, 0, 0);
					GL11.glRotatef(-23F, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(175F, 0.0F, 1.0F, 0.0F);
					GL11.glScaled(10, 10, 10);
					GL11.glScaled(1, 1.5, 1.5);
				}
				GlStateManager.shadeModel(GL11.GL_SMOOTH);
				if(item.getTagCompound() != null && item.getTagCompound().hasKey("animation")) {
					NBTTagCompound anim = item.getTagCompound().getCompoundTag("animation");
					if(anim.getInteger("id") == 0)
						ResourceManager.supershotgun.controller.setAnim(new AnimationWrapper(anim.getLong("time"), anim.getFloat("mult"), ResourceManager.ssg_reload));
					else
						ResourceManager.supershotgun.controller.setAnim(AnimationWrapper.EMPTY);
					ResourceManager.supershotgun.renderAnimated(System.currentTimeMillis());
				} else {
					ResourceManager.supershotgun.render();
				}
				GlStateManager.shadeModel(GL11.GL_FLAT);
				GlStateManager.enableCull();
				GL11.glPushMatrix();
			} else {
				EnumHand hand = type == TransformType.FIRST_PERSON_RIGHT_HAND ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
				double[] recoil = HbmAnimations.getRelevantTransformation("SHOTTY_RECOIL", hand);
				double[] eject = HbmAnimations.getRelevantTransformation("SHOTTY_BREAK", hand);
				double[] ejectShell = HbmAnimations.getRelevantTransformation("SHOTTY_EJECT", hand);
				double[] insertShell = HbmAnimations.getRelevantTransformation("SHOTTY_INSERT", hand);
				
				GL11.glTranslated(-5, -0.3, -2.5);
				GL11.glRotated(4, 1, 0, 0);
				GL11.glRotatef(-23F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(175F, 0.0F, 1.0F, 0.0F);
				GL11.glScaled(2, 2, 2);
				
				if(entity.isSneaking()) {
					GL11.glTranslatef(0F, 1.0F, -1.8F);
					GL11.glRotatef(3.5F, 0.0F, 1.0F, 0.0F);
				} else {
					GL11.glRotated(-eject[2] * 0.25, 0, 0, 1);
				}

				GL11.glTranslated(-recoil[0] * 2, 0, 0);
				GL11.glRotated(recoil[0] * 5, 0, 0, 1);
				
				GL11.glPushMatrix();
				GL11.glRotated(-eject[2] * 0.8, 0, 0, 1);
				ResourceManager.shotty.renderPart("Barrel");
				
				GL11.glPushMatrix();
				GL11.glRotated(ejectShell[0] * 90, 0, 0, 1);
				GL11.glTranslated(-ejectShell[0] * 10, 0, 0);
				ResourceManager.shotty.renderPart("Shells");
				GL11.glPopMatrix();
				
				GL11.glPushMatrix();
				GL11.glTranslated(-insertShell[0], insertShell[2] * -2, insertShell[2] * -1);
				ResourceManager.shotty.renderPart("Shells");
				GL11.glPopMatrix();
				
				GL11.glPopMatrix();
				
				ResourceManager.shotty.renderPart("Handle");
				GlStateManager.enableCull();
				GL11.glPushMatrix();
			}
			return;
		case HEAD:
		case FIXED:
		case GROUND:
		case THIRD_PERSON_LEFT_HAND:
			GL11.glTranslated(0.0, -0.2, 0.5);
		case THIRD_PERSON_RIGHT_HAND:
			GL11.glTranslated(0.0, -0.35, 0);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glScaled(0.5, 0.5, 0.5);
			break;
		case GUI:
			break;
		default:
			break;
		}
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		ResourceManager.shotty.renderAll();
		GlStateManager.shadeModel(GL11.GL_FLAT);

		GlStateManager.enableCull();
		GL11.glPushMatrix();
	}
}
