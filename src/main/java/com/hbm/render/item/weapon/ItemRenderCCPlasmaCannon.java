package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.item.TEISRBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class ItemRenderCCPlasmaCannon extends TEISRBase {

	@Override
	public void renderByItem(ItemStack itemStackIn) {
		GL11.glTranslated(0.5, 0.5, 0.5);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.cc_plasma_cannon_tex);
		GlStateManager.enableCull();
		
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL", type == TransformType.FIRST_PERSON_LEFT_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
			//Scaled up by 10 from the regular scale amount so the item bobbing affects the gun less.
			GL11.glScaled(0.5, 0.4, 0.5);
			if(type == TransformType.FIRST_PERSON_RIGHT_HAND){
				GL11.glRotated(178, 0, 1, 0);
				GL11.glRotated(27+recoil[1]*2F, 0, 0, 1);
				GL11.glTranslated(4, -0.2, 0);
				GL11.glRotated(7, 0, 0, 1);
				GL11.glRotated(180, 0, 1, 0);
			} else {
				GL11.glRotated(180, 0, 1, 0);
				GL11.glRotated(140+recoil[1]*2F, 0, 0, 1);
				GL11.glTranslated(4, 1, 1);
				GL11.glRotated(7, 0, 0, 1);
				GL11.glRotated(170, 0, 1, 0);
				GL11.glRotated(180, 1, 0, 0);
			}
			
			GL11.glTranslated(-recoil[2]*0.15F, 0, 0);
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			GL11.glTranslated(0, -0.1, -0.8);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glScaled(0.3, 0.3, 0.3);
			break;
		case GUI:
			GL11.glTranslated(-0.175, 0.2, 0);
			GL11.glScaled(0.1, 0.1, 0.1);
			GL11.glRotated(180, 0, 1, 0);
			GL11.glRotated(45, 0, 0, 1);
			GL11.glRotated(180, 0, 1, 0);
			break;
		case NONE:
			break;
		}
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		boolean prevBlend = GL11.glGetBoolean(GL11.GL_BLEND);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		ResourceManager.cc_plasma_cannon.renderAll();
		if(!prevBlend)
			GlStateManager.disableBlend();
		GlStateManager.shadeModel(GL11.GL_FLAT);
	}
}
