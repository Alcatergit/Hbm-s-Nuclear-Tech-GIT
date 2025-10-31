package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.GunLeverActionS;
import com.hbm.lib.RefStrings;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.ModelLeverAction;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderGunSonata extends TEISRBase {

	private static ResourceLocation sonata_rl = new ResourceLocation(RefStrings.MODID + ":textures/models/weapons/ModelLeverAction.png");
	protected ModelLeverAction leveraction;

	public ItemRenderGunSonata() {
		leveraction = new ModelLeverAction();
	}

	@Override
	public void renderByItem(ItemStack itemStackIn) {
		Minecraft.getMinecraft().renderEngine.bindTexture(sonata_rl);
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
				GL11.glTranslated(1.8, 1.5, 0.8);
				GL11.glRotated(180, 1, 0, 0);
				GL11.glRotated(180, 0, 1, 0);
				GL11.glRotated(23, 0, 0, 1);
			}

			GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(15F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(2.3F, 0.2F, 0.8F);

			if (GunLeverActionS.getRotationFromAnim(itemStackIn) > 0) {
				GL11.glRotatef(GunLeverActionS.getRotationFromAnim(itemStackIn) * -25, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(GunLeverActionS.getOffsetFromAnim(itemStackIn) * 1.5F, 0.0F, 0.0F);
				GL11.glTranslatef(0.0F, GunLeverActionS.getOffsetFromAnim(itemStackIn) * -1.5F, 0.0F);
			}
			leveraction.renderAnim(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, GunLeverActionS.getRotationFromAnim(itemStackIn));
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case GROUND:
		case FIXED:
		case HEAD:
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glTranslated(1.0, 0.5, 0.5);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(180, 1, 0, 0);

			GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(25F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(2.3F, 0.2F, 0.8F);

			leveraction.renderAnim(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, GunLeverActionS.getRotationFromAnim(itemStackIn));
			break;
		default:
			break;
		}
	}

}
