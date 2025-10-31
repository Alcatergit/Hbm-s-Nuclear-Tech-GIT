package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.GunBoltAction;
import com.hbm.lib.RefStrings;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.ModelBoltAction;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderGunSaturnite extends TEISRBase {
	
	protected ModelBoltAction sat;
	protected static ResourceLocation sat_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelBoltActionSaturnite.png");
	
	public ItemRenderGunSaturnite() {
		sat = new ModelBoltAction();
	}
	
	@Override
	public void renderByItem(ItemStack item) {
		Minecraft.getMinecraft().renderEngine.bindTexture(sat_rl);
		switch (type) {
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			if (type == TransformType.FIRST_PERSON_RIGHT_HAND) {
				GL11.glTranslated(0.5, 1.5, 0.8);
				GL11.glRotated(-10, 0, 1, 0);
				GL11.glRotated(-40, 0, 0, 1);
				GL11.glRotated(180, 1, 0, 0);
			} else {
				GL11.glTranslated(1.6, 1.4, 0.8);
				GL11.glRotated(180, 1, 0, 0);
				GL11.glRotated(180, 0, 1, 0);
				GL11.glRotated(40, 0, 0, 1);
			}


			if(GunBoltAction.getRotationFromAnim(item) > 0) {
				GL11.glRotatef(GunBoltAction.getRotationFromAnim(item) * 10, 2.5F, 0.0F, 1.5F);
				GL11.glTranslatef(GunBoltAction.getOffsetFromAnim(item) * -1.75F, 0.0F, 0.0F);
			}
			sat.renderAnim(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, GunBoltAction.getRotationFromAnim(item), GunBoltAction.getTransFromAnim(item));
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case GROUND:
		case FIXED:
		case HEAD:
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glTranslated(1.0, 0.9, 0.9);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(180, 1, 0, 0);

			sat.renderAnim(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, GunBoltAction.getRotationFromAnim(item), GunBoltAction.getTransFromAnim(item));
			break;
		default:
			break;
		}
	}

}
