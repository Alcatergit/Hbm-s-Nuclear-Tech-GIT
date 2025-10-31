package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.ModelBaleflare;
import com.hbm.render.model.ModelMP40;
import com.hbm.render.model.ModelPip;
import com.hbm.render.model.ModelSpark;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderBullshit extends TEISRBase {

	protected ModelSpark sparkPlug;
	protected ModelPip pip;
	protected ModelMP40 mp40;
	protected ModelBaleflare bomb;
	protected static ResourceLocation mp40_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelMP40.png");
	protected static ResourceLocation spark_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelSpark.png");
	protected static ResourceLocation pip_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelPip.png");
	protected static ResourceLocation bomb_rl = new ResourceLocation(RefStrings.MODID +":textures/models/projectiles/BaleFlare.png");
	
	public ItemRenderBullshit() {
		sparkPlug = new ModelSpark();
		pip = new ModelPip();
		mp40 = new ModelMP40();
		bomb = new ModelBaleflare();
	}
	
	@Override
	public void renderByItem(ItemStack itemStackIn) {
		GlStateManager.enableCull();
		switch (type) {
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			if (type == TransformType.FIRST_PERSON_RIGHT_HAND) {
				GL11.glTranslated(0.8, 1.3, 1.5);
				//GL11.glRotated(0, 0, 1, 0);
				GL11.glRotated(-45, 0, 0, 1);
				GL11.glRotated(180, 1, 0, 0);
			} else {
				GL11.glTranslated(1.3, 1.3, 1.4);
				GL11.glRotated(180, 1, 0, 0);
				GL11.glRotated(180, 0, 1, 0);
				GL11.glRotated(45, 0, 0, 1);
			}

			GL11.glTranslatef(0.6F, 0.0F, 0.0F);

			this.renderWhatever(itemStackIn);
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case GROUND:
		case FIXED:
		case HEAD:
			GL11.glTranslated(0.5, 0.5, 0.3);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(180, 1, 0, 0);

			this.renderWhatever(itemStackIn);
			break;
		default:
			break;
		}
	}
	
	private void renderWhatever(ItemStack item) {
		Minecraft.getMinecraft().renderEngine.bindTexture(mp40_rl);
		GL11.glScalef(0.75F, 0.75F, 0.75F);
		mp40.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		Minecraft.getMinecraft().renderEngine.bindTexture(spark_rl);
		GL11.glScalef(4/3F, 4/3F, 4/3F);
		GL11.glTranslatef(-0.5F, 0.0F, 0.0F);
		if(type == TransformType.FIRST_PERSON_LEFT_HAND || type == TransformType.FIRST_PERSON_RIGHT_HAND)
			sparkPlug.renderingInFirstPerson = true;
		sparkPlug.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		Minecraft.getMinecraft().renderEngine.bindTexture(pip_rl);
		GL11.glTranslatef(0.0F, 0.2F, 0.0F);
		GL11.glTranslatef(0.5F, 0.0F, 0.0F);
		GL11.glScalef(0.75F, 0.75F, 0.75F);
		pip.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		Minecraft.getMinecraft().renderEngine.bindTexture(bomb_rl);
		GL11.glScalef(4/3F, 4/3F, 4/3F);
		GL11.glTranslatef(-1.5F, 0.0F, 0.0F);
		bomb.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
	}
}
