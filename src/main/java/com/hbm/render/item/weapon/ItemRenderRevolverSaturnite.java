package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.ModelRevolver;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderRevolverSaturnite extends TEISRBase {

	protected ModelRevolver swordModel;
	protected static ResourceLocation sat_rl = new ResourceLocation(RefStrings.MODID +":textures/models/ModelRevolverSaturnite.png");
	
	public ItemRenderRevolverSaturnite() {
		swordModel = new ModelRevolver();
	}
	
	@Override
	public void renderByItem(ItemStack itemStackIn) {
		Minecraft.getMinecraft().renderEngine.bindTexture(sat_rl);
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			
			if (type == TransformType.FIRST_PERSON_RIGHT_HAND) {
				GL11.glTranslated(-0.2, 0.2, 0.2);
				GL11.glRotated(-10, 0, 1, 0);
				GL11.glRotated(-20, 0, 0, 1);
				GL11.glRotated(180, 1, 0, 0);
			} else {
				GL11.glTranslated(1.2, 0.2, 0.0);
				GL11.glRotated(180, 1, 0, 0);
				GL11.glRotated(180, 0, 1, 0);
				GL11.glRotated(23, 0, 0, 1);
			}
			swordModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case GROUND:
		case FIXED:
		case HEAD:
			GL11.glTranslated(0.45, 0.25, 0.5);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(180, 1, 0, 0);
			swordModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		default:
			break;
		}
	}
}
