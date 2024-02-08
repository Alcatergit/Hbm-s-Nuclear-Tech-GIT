package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.ModelStinger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderStinger extends TEISRBase {

	protected ModelStinger stinger;
	protected static ResourceLocation stinger_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelStinger.png");
	protected static ResourceLocation skystinger_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelSkyStinger.png");
	
	public ItemRenderStinger() {
		stinger = new ModelStinger();
	}
	
	@Override
	public void renderByItem(ItemStack itemStackIn) {
		GL11.glPushMatrix();
		if(itemStackIn.getItem() == ModItems.gun_stinger)
			Minecraft.getMinecraft().renderEngine.bindTexture(stinger_rl);
		if(itemStackIn.getItem() == ModItems.gun_skystinger)
			Minecraft.getMinecraft().renderEngine.bindTexture(skystinger_rl);
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			GL11.glScaled(0.5, 0.5, 0.5);
			GL11.glTranslated(0.5, 0.5, 0.5);
			if(type == TransformType.FIRST_PERSON_RIGHT_HAND){
				GL11.glTranslated(1.2, -0.1, 0.8);
				GL11.glRotated(180, 1, 0, 0);
				GL11.glRotated(45, 0, 0, 1);
			} else {
				GL11.glTranslated(-0.2, -0.15, 1.0);
				GL11.glRotated(180, 0, 0, 1);
				GL11.glRotated(50, 0, 0, 1);
			}
			
			stinger.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			GL11.glScalef(1.5F, 1.5F, 1.5F);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glTranslated(0.65, 0.6, 1.0);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(180, 1, 0, 0);
			stinger.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		default:
			break;
		}
		GL11.glPopMatrix();
	}
}
