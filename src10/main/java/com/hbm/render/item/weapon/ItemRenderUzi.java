package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.ModelUzi;
import com.hbm.render.model.ModelUziBarrel;
import com.hbm.render.model.ModelUziSilencer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderUzi extends TEISRBase {

	protected ModelUzi uzi;
	protected ModelUziBarrel barrel;
	protected ModelUziSilencer silencer;
	
	protected static ResourceLocation uzi_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelUzi.png");
	protected static ResourceLocation sat_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelUziSaturnite.png");
	protected static ResourceLocation barrel_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelUziBarrel.png");
	protected static ResourceLocation silencer_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelUziSilencer.png");
	
	public ItemRenderUzi() {
		uzi = new ModelUzi();
		barrel = new ModelUziBarrel();
		silencer = new ModelUziSilencer();
	}
	
	@Override
	public void renderByItem(ItemStack item) {
		GlStateManager.enableCull();
		if(item.getItem() == ModItems.gun_uzi/* || item.getItem() == ModItems.gun_lever_action_sonata*/)
			Minecraft.getMinecraft().renderEngine.bindTexture(uzi_rl);
		if(item.getItem() == ModItems.gun_uzi_silencer)
			Minecraft.getMinecraft().renderEngine.bindTexture(uzi_rl);
		if(item.getItem() == ModItems.gun_uzi_saturnite)
			Minecraft.getMinecraft().renderEngine.bindTexture(sat_rl);
		if(item.getItem() == ModItems.gun_uzi_saturnite_silencer)
			Minecraft.getMinecraft().renderEngine.bindTexture(sat_rl);
		
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			
			if (type == TransformType.FIRST_PERSON_RIGHT_HAND) {
				GL11.glTranslated(-0.2, 1.1, 0.4);
				GL11.glRotated(-00, 0, 1, 0);
				GL11.glRotated(-25, 0, 0, 1);
				GL11.glRotated(180, 1, 0, 0);
			} else {
				GL11.glTranslated(1.8, 1.1, 0.8);
				GL11.glRotated(180, 1, 0, 0);
				GL11.glRotated(180, 0, 1, 0);
				GL11.glRotated(25, 0, 0, 1);
			}
			
			uzi.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_uzi_silencer || item.getItem() == ModItems.gun_uzi_saturnite_silencer){
				Minecraft.getMinecraft().renderEngine.bindTexture(silencer_rl);
				silencer.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			}
			if(item.getItem() == ModItems.gun_uzi || item.getItem() == ModItems.gun_uzi_saturnite){
				Minecraft.getMinecraft().renderEngine.bindTexture(barrel_rl);
				barrel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			}
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case GROUND:
		case FIXED:
		case HEAD:
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glTranslated(1.0, 0.9, 1.3);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(180, 1, 0, 0);

			uzi.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_uzi_silencer || item.getItem() == ModItems.gun_uzi_saturnite_silencer){
				Minecraft.getMinecraft().renderEngine.bindTexture(silencer_rl);
				silencer.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			}
			if(item.getItem() == ModItems.gun_uzi || item.getItem() == ModItems.gun_uzi_saturnite){
				Minecraft.getMinecraft().renderEngine.bindTexture(barrel_rl);
				barrel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			}
			break;
		default:
			break;
		}
	}
}
