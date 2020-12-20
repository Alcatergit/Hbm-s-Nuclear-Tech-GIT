package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.item.TEISRBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class ItemRenderWeaponThompson extends TEISRBase {

	@Override
	public void renderByItem(ItemStack itemStackIn) {
		GL11.glTranslated(0.5, 0.5, 0.5);
		GlStateManager.enableCull();
		GlStateManager.enableRescaleNormal();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.thompson_tex);
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			if(type == TransformType.FIRST_PERSON_RIGHT_HAND){
				//GL11.glTranslated(0, -0.8, 0.5);
				//GL11.glRotated(80, 0, 1, 0);
				//GL11.glRotated(20, 1, 0, 0);
				double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL", type == TransformType.FIRST_PERSON_LEFT_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
				
				GL11.glTranslated(0+recoil[1]*0.2F, -0.4-recoil[1]*0.1F, 0.4);
				GL11.glRotated(180, 0, 1, 0);
				GL11.glRotated(23, 0, 0, 1);
			} else {
				GL11.glTranslated(0, -0.8, 0.5);
				GL11.glRotated(-80, 0, 1, 0);
				GL11.glRotated(20, 1, 0, 0);
			}
			GL11.glScaled(0.25, 0.25, 0.25);
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			GL11.glTranslated(0, -1.35, 0.05);
			GL11.glScaled(0.3, 0.3, 0.3);
			break;
		case GUI:
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glTranslated(-0.25, -0.25, 0);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glRotated(40, 1, 0, 0);
			GL11.glScaled(0.12, 0.12, 0.12);
			break;
		case NONE:
			break;
		}
		//ResourceManager.thompson.renderAll();
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.turbofan_blades_tex);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		ResourceManager.cat.renderAll();
		GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.disableRescaleNormal();
	}
}
