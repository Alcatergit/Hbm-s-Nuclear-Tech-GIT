package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.ModelCalBarrel;
import com.hbm.render.model.ModelCalDualStock;
import com.hbm.render.model.ModelCalStock;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderCalamity extends TEISRBase {

	protected ModelCalBarrel barrel;
	protected ModelCalStock stock;
	protected ModelCalDualStock saddle;
	
	protected ResourceLocation dualStock_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelCalDualStock.png");
	protected ResourceLocation barrel_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelCalBarrel.png");
	protected ResourceLocation stock_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelCalStock.png");
	
	public ItemRenderCalamity() {
		barrel = new ModelCalBarrel();
		stock = new ModelCalStock();
		saddle = new ModelCalDualStock();
	}
	
	@Override
	public void renderByItem(ItemStack item) {
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
			
			if(item.getItem() == ModItems.gun_calamity) {
				GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(5.0F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(-0.2F, 0.0F, -0.2F);
				
				Minecraft.getMinecraft().renderEngine.bindTexture(barrel_rl);
				barrel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				Minecraft.getMinecraft().renderEngine.bindTexture(stock_rl);
				stock.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			}
			if(item.getItem() == ModItems.gun_calamity_dual) {
				GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(-5.0F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(-0.5F, 0F, -0.2F);
				
				Minecraft.getMinecraft().renderEngine.bindTexture(dualStock_rl);
				saddle.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				Minecraft.getMinecraft().renderEngine.bindTexture(barrel_rl);
		        GL11.glTranslated(1D/16D * -2, 0, 0);
		        GL11.glTranslated(0, 0, 0.35);
				barrel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		        GL11.glTranslated(0, 0, -0.7);
				barrel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			}
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case HEAD:
			GL11.glTranslated(0.4, 0.4, -0.3);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(180, 1, 0, 0);
			if(item.getItem() == ModItems.gun_calamity) {
				GL11.glScalef(0.75F, 0.75F, 0.75F);
				GL11.glTranslatef(0.5F, 0.0F, 0.0F);
			}
			if(item.getItem() == ModItems.gun_calamity_dual) {
				GL11.glScalef(0.75F, 0.75F, 0.75F);
				GL11.glTranslatef(0.5F, 0.0F, 0.0F);
			}
			if(item.getItem() == ModItems.gun_calamity) {
				Minecraft.getMinecraft().renderEngine.bindTexture(barrel_rl);
				barrel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				Minecraft.getMinecraft().renderEngine.bindTexture(stock_rl);
				stock.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			}
			if(item.getItem() == ModItems.gun_calamity_dual) {
				Minecraft.getMinecraft().renderEngine.bindTexture(dualStock_rl);
				saddle.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				Minecraft.getMinecraft().renderEngine.bindTexture(barrel_rl);
		        GL11.glTranslated(1D/16D * -2, 0, 0);
		        GL11.glTranslated(0, 0, 0.35);
				barrel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		        GL11.glTranslated(0, 0, -0.7);
				barrel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			}
			break;
		case FIXED:
		case GROUND:
			GL11.glTranslated(0.5, 0.5, 0.5);
			GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
			if(item.getItem() == ModItems.gun_calamity) {
				GL11.glScalef(0.75F, 0.75F, 0.75F);
			}
			if(item.getItem() == ModItems.gun_calamity_dual) {
				GL11.glScalef(0.75F, 0.75F, 0.75F);
			}
			if(item.getItem() == ModItems.gun_calamity) {
				Minecraft.getMinecraft().renderEngine.bindTexture(barrel_rl);
				barrel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				Minecraft.getMinecraft().renderEngine.bindTexture(stock_rl);
				stock.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			}
			if(item.getItem() == ModItems.gun_calamity_dual) {
				Minecraft.getMinecraft().renderEngine.bindTexture(dualStock_rl);
				saddle.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				Minecraft.getMinecraft().renderEngine.bindTexture(barrel_rl);
		        GL11.glTranslated(1D/16D * -2, 0, 0);
		        GL11.glTranslated(0, 0, 0.35);
				barrel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		        GL11.glTranslated(0, 0, -0.7);
				barrel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			}
			break;
		default:
			break;
		}
	}
}
