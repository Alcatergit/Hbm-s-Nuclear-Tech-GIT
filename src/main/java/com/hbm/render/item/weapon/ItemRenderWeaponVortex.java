package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.item.TEISRBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class ItemRenderWeaponVortex extends TEISRBase {

	@Override
	public void renderByItem(ItemStack itemStackIn) {
		GL11.glTranslated(0.5, 0.5, 0.5);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.vortex_tex);
		GlStateManager.enableCull();
		
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			double[] recoil = HbmAnimations.getRelevantTransformation("VORTEX_RECOIL", type == TransformType.FIRST_PERSON_LEFT_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
			//Scaled up by 10 from the regular scale amount so the item bobbing affects the gun less.
			GL11.glScaled(0.5, 0.4, 0.5);
			if(type == TransformType.FIRST_PERSON_RIGHT_HAND){
				GL11.glRotated(178, 0, 1, 0);
				GL11.glRotated(27+recoil[1], 0, 0, 1);
				GL11.glTranslated(14, -16, 3);
			} else {
				GL11.glRotated(27+recoil[1], 0, 0, 1);
				GL11.glRotated(2, 0, 1, 0);
				GL11.glTranslated(13, -16, -4);
			}
			
			GL11.glTranslated(recoil[2], 0, 0);
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			GL11.glTranslated(0, -0.65, -0.3);
			//This scale makes it a little bit shorter and longer, I think it looks better like that personally.
			GL11.glScaled(0.08, 0.05, 0.06);
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
		ResourceManager.vortex.renderAll();
	}
}
