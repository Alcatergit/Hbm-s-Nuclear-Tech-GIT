package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.ModelHP;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderGunHP extends TEISRBase {

	protected ModelHP hppLaserjet;
	protected ResourceLocation hp_loc = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelHP.png");
	
	public ItemRenderGunHP() {
		hppLaserjet = new ModelHP();
	}
	
	@Override
	public void renderByItem(ItemStack stack) {
		Minecraft.getMinecraft().renderEngine.bindTexture(hp_loc);
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
			GL11.glTranslated(-0.25, 0, 0);
		case FIRST_PERSON_RIGHT_HAND:
			GL11.glScaled(0.5, 0.5, 0.5);
			GL11.glTranslated(1.3, 0.8, 1.3);
			if(type == TransformType.FIRST_PERSON_RIGHT_HAND){
				GL11.glRotated(-10, 0, 1, 0);
				GL11.glRotated(-40, 0, 0, 1);
				GL11.glRotated(180, 1, 0, 0);
			} else {
				GL11.glRotated(180, 0, 1, 0);
				GL11.glRotated(180, 1, 0, 0);
				GL11.glRotated(40, 0, 0, 1);
			}
			
			hppLaserjet.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			GL11.glTranslated(0.4, 0.2, 0.2);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(180, 1, 0, 0);
			hppLaserjet.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		default:
			break;
		}
	}
}
