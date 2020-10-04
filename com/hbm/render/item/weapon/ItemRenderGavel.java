package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.TEISRBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;

public class ItemRenderGavel extends TEISRBase {

	@Override
	public void renderByItem(ItemStack item) {
		GL11.glTranslated(0.5, 0.5, 0.5);
		
		if(item.getItem() == ModItems.wood_gavel)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.gavel_wood);
		if(item.getItem() == ModItems.lead_gavel)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.gavel_lead);
		if(item.getItem() == ModItems.diamond_gavel)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.gavel_diamond);
		
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			GL11.glTranslated(-0.6, 0, 0);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glRotated(45, 1, 0, 0);
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			break;
		case GUI:
			break;
		case NONE:
			break;
		}
		
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		ResourceManager.gavel.renderAll();
		GlStateManager.shadeModel(GL11.GL_FLAT);
	}
}
