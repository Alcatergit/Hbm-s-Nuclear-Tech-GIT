package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;

public class ItemRenderWeaponObj extends TEISRBase {

	@Override
	public void renderByItem(ItemStack item) {
		GL11.glPopMatrix();
		GlStateManager.disableCull();
		if(item.getItem() == ModItems.gun_hk69)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.hk69_tex);
		if(item.getItem() == ModItems.gun_deagle)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.turbofan_blades_tex);
		if(item.getItem() == ModItems.gun_supershotgun)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.turbofan_blades_tex);
		if(item.getItem() == ModItems.gun_ks23)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.ks23_tex);
		if(item.getItem() == ModItems.gun_flamer)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.flamer_tex);
		
		switch(type) {
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			if(type == TransformType.FIRST_PERSON_LEFT_HAND) {
				if(item.getItem() == ModItems.gun_hk69) {
					GL11.glTranslated(2.0, 0, -0.4);
					GL11.glRotated(10, 0, 1, 0);
					GL11.glTranslated(-1.0, 0.0, -0.2);
					GL11.glRotated(-180, 0, 1, 0);
					GL11.glRotated(-90, 0, 1, 0);
					GL11.glRotated(-25, 1, 0, 0);
					GL11.glRotated(-5, 0, 1, 0);
				} else if(item.getItem() == ModItems.gun_supershotgun) {
					GL11.glTranslated(-0.81, -0.7, 0.4);
					GL11.glRotated(4, 1, 0, 0);
					GL11.glRotatef(23F, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(5F, 0.0F, 1.0F, 0.0F);
					GL11.glScaled(1, 1.5, 1.5);
				} else if(item.getItem() == ModItems.gun_deagle){
					GL11.glTranslated(0, 0, 0.2);
					GL11.glRotated(20, 0, 0, 1);
					GL11.glRotated(95, 0, 1, 0);
					GL11.glScaled(0.2, 0.2, 0.2);
				} else if(item.getItem() == ModItems.gun_ks23){
					GL11.glTranslated(-0.1, 0.25, 0.5);
					GL11.glRotated(30, 0, 0, 1);
					GL11.glRotated(95, 0, 1, 0);
				} else if(item.getItem() == ModItems.gun_flamer){
					GL11.glTranslated(0.3, -0.6, 0);
					GL11.glRotated(26, 0, 0, 1);
					GL11.glRotated(95, 0, 1, 0);
					GL11.glScaled(0.5, 0.5, 0.5);
				}
			} else {
				if(item.getItem() == ModItems.gun_hk69) {
					GL11.glTranslated(-1.0, 0.0, -0.2);
					GL11.glRotated(-90, 0, 1, 0);
					GL11.glRotated(-25, 1, 0, 0);
					GL11.glRotated(-5, 0, 1, 0);
				} else if(item.getItem() == ModItems.gun_supershotgun) {
					GL11.glTranslated(0.8, -0.7, 0.6);
					if(this.entity != null && this.entity.isSneaking()){
						GL11.glTranslated(0, 0.20, 0.43);
						GL11.glRotated(-4, 1, 0, 0);
						GL11.glRotated(5, 0, 1, 0);
						GL11.glRotated(-4, 0, 0, 1);
					}
					GL11.glRotated(4, 1, 0, 0);
					GL11.glRotatef(-23F, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(175F, 0.0F, 1.0F, 0.0F);
					GL11.glScaled(1, 1.5, 1.5);
				} else if(item.getItem() == ModItems.gun_deagle){
					GL11.glTranslated(0, 0, 0.2);
					if(this.entity != null && this.entity.isSneaking()){
						GL11.glTranslated(0, 0.20, 0.72);
						GL11.glRotated(10, 0, 1, 0);
					}
					GL11.glRotated(260, 0, 1, 0);
					GL11.glRotated(-20, 1, 0, 0);
					GL11.glScaled(0.2, 0.2, 0.2);
				} else if(item.getItem() == ModItems.gun_ks23){
					GL11.glTranslated(-0.1, 0.3, 0.4);
					if(this.entity != null && this.entity.isSneaking()){
						GL11.glTranslated(-0.2, 0.25, 0.53);
						GL11.glRotated(10, 0, 1, 0);
					}
					GL11.glRotated(260, 0, 1, 0);
					GL11.glRotated(-25, 1, 0, 0);
				} else if(item.getItem() == ModItems.gun_flamer){
					GL11.glTranslated(-0.5, -0.5, 0);
					GL11.glRotated(265, 0, 1, 0);
					GL11.glRotated(-25, 1, 0, 0);
					GL11.glScaled(0.5, 0.5, 0.5);
				}
			}
			break;
		case HEAD:
		case FIXED:
		case GROUND:
			if(item.getItem() == ModItems.gun_hk69){
				GL11.glTranslated(0.0, -0.2, 0.5);
			} else if(item.getItem() == ModItems.gun_supershotgun){
				GL11.glTranslated(0.0, -0.2, 0.5);
			} else if(item.getItem() == ModItems.gun_deagle){
				
			} else if(item.getItem() == ModItems.gun_ks23){
				
			} else if(item.getItem() == ModItems.gun_flamer){
				
			}
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
			if(item.getItem() == ModItems.gun_hk69) {
				GL11.glTranslated(0.0, -0.3, -0.5);
				GL11.glRotated(180, 0, 1, 0);
			} else if (item.getItem() == ModItems.gun_supershotgun){
				GL11.glTranslated(0.0, -0.65, 0.5);
				GL11.glRotated(90, 0, 1, 0);
				GL11.glScaled(1.5, 1.5, 1.5);
			} else if(item.getItem() == ModItems.gun_deagle){
				GL11.glTranslated(0, -0.3, 0);
				GL11.glRotated(180, 0, 1, 0);
				GL11.glScaled(0.2, 0.2, 0.2);
			} else if(item.getItem() == ModItems.gun_ks23){
				GL11.glTranslated(0, -0.15, -1.3);
				GL11.glRotated(180, 0, 1, 0);
				GL11.glScaled(1.25, 1.25, 1.25);
			} else if(item.getItem() == ModItems.gun_flamer){
				GL11.glTranslated(0, -0.62, 0);
				GL11.glRotated(180, 0, 1, 0);
				GL11.glScaled(0.35, 0.35, 0.35);
			}
			break;
		case GUI:
			if(item.getItem() == ModItems.gun_hk69){
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glTranslatef(-0.2F, -0.1F, 0.0F);
				GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-40F, 1.0F, 0.0F, 0.0F);
			} else if(item.getItem() == ModItems.gun_ks23){
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glTranslatef(-0.5F, 0.6F, 0.0F);
				GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-40F, 1.0F, 0.0F, 0.0F);
			} else if(item.getItem() == ModItems.gun_flamer){
				GL11.glScaled(0.12, 0.12, 0.12);
				GL11.glTranslatef(-0.2F, -1.2F, 0.0F);
				GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-40F, 1.0F, 0.0F, 0.0F);
			}
			break;
		default:
			break;
		}
		if(item.getItem() == ModItems.gun_hk69) {
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
			ResourceManager.hk69.renderAll();
			GlStateManager.shadeModel(GL11.GL_FLAT);
		}

		if(item.getItem() == ModItems.gun_deagle) {
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
			ResourceManager.deagle.renderAll();
			GlStateManager.shadeModel(GL11.GL_FLAT);
		}

		if(item.getItem() == ModItems.gun_supershotgun) {
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
			ResourceManager.shotty.renderAll();
			GlStateManager.shadeModel(GL11.GL_FLAT);
		}
		
		if(item.getItem() == ModItems.gun_ks23) {
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
			ResourceManager.ks23.renderAll();
			GlStateManager.shadeModel(GL11.GL_FLAT);
		} 
		
		if(item.getItem() == ModItems.gun_flamer){
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
			ResourceManager.flamer.renderAll();
			GlStateManager.shadeModel(GL11.GL_FLAT);
		}
		
		GlStateManager.enableCull();
		GL11.glPushMatrix();
	}
}
