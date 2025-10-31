package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.ModelGustav;
import com.hbm.render.model.ModelPanzerschreck;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderRpg extends TEISRBase {

	protected ModelGustav swordModel;
	protected ModelPanzerschreck panz;
	
	protected static ResourceLocation gustav_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelGustav.png");
	protected static ResourceLocation karl_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelGustavYellow.png");
	protected static ResourceLocation panzer_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelPanzerschreck.png");
	
	public ItemRenderRpg() {
		swordModel = new ModelGustav();
		panz = new ModelPanzerschreck();
	}
	
	@Override
	public void renderByItem(ItemStack item) {
		GL11.glPopMatrix();
		if(item.getItem() == ModItems.gun_rpg)
			Minecraft.getMinecraft().renderEngine.bindTexture(gustav_rl);
		if(item.getItem() == ModItems.gun_karl)
			Minecraft.getMinecraft().renderEngine.bindTexture(karl_rl);
		if(item.getItem() == ModItems.gun_panzerschreck)
			Minecraft.getMinecraft().renderEngine.bindTexture(panzer_rl);
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
			GL11.glTranslated(-1.5, 0.0, 0.5);
		case FIRST_PERSON_RIGHT_HAND:
			//GL11.glRotatef(-135.0F, 0.0F, 0.0F, 1.0F);
			//GL11.glScalef(0.5F, 0.5F, 0.5F);
			//GL11.glTranslatef(0.4F, -1.0F, -0.7F);
			GL11.glScaled(0.75, 0.75, 0.75);
			if(item.getItem() == ModItems.gun_panzerschreck) {
				GL11.glTranslated(0, 0.2, 0);
				if(Minecraft.getMinecraft().player.isSneaking() && type == TransformType.FIRST_PERSON_RIGHT_HAND){
					GL11.glTranslated(0.5, 0.1, 0.82);
				}
			}
			GL11.glTranslated(1, -0.5, 0.1);
			GL11.glRotated(180, 1, 0, 0);
			GL11.glRotated(20, 0, 0, 1);
			if(type == TransformType.FIRST_PERSON_LEFT_HAND){
				GL11.glRotated(180, 0, 1, 0);
				GL11.glRotated(40, 0, 0, 1);
				GL11.glRotated(-10, 0, 1, 0);
			}
			
			if(item.getItem() == ModItems.gun_panzerschreck) {
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				if(Minecraft.getMinecraft().player.isSneaking()){
					GL11.glRotated(10, 0, 0, 1);
				}

			} else {
				GL11.glTranslatef(0F, -0.1F, -0.4F);
			}
			
			//GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
			//GL11.glRotatef(5.0F, 0.0F, 1.0F, 0.0F);
			//GL11.glTranslatef(-0.2F, 0.0F, -0.2F);
			
			if(item.getItem() == ModItems.gun_rpg)
				swordModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_karl)
				swordModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_panzerschreck)
				panz.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			
			if(item.getItem() == ModItems.gun_panzerschreck){
				GL11.glScaled(1.7, 1.7, 1.7);
				GL11.glTranslated(0, 0.2, -0.5);
			}
			GL11.glTranslated(0.0, -0.25, 1.0);
			
			GL11.glRotated(180, 0, 0, 1);
			GL11.glRotated(-90, 0, 1, 0);
			
			if(item.getItem() == ModItems.gun_rpg)
				swordModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_karl)
				swordModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_panzerschreck)
				panz.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		default:
			break;
		}
		GL11.glPushMatrix();
	}
}
