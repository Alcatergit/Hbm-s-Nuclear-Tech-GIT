package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.ModelNightmare;
import com.hbm.render.model.ModelNightmare2;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderRevolverNightmare extends TEISRBase {

	protected ModelNightmare n1;
	protected ModelNightmare2 n2;
	protected ResourceLocation n1Loc = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelNightmare.png");
	protected ResourceLocation n2Loc = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelNightmare2.png");
	
	
	public ItemRenderRevolverNightmare() {
		n1 = new ModelNightmare();
		n2 = new ModelNightmare2();
	}
	
	//Drillgon200: push and pop matrix are done in the method calling this one
	@Override
	public void renderByItem(ItemStack stack) {
		if(stack.getItem() == ModItems.gun_revolver_nightmare)
			Minecraft.getMinecraft().renderEngine.bindTexture(n1Loc);
		if(stack.getItem() == ModItems.gun_revolver_nightmare2)
			Minecraft.getMinecraft().renderEngine.bindTexture(n2Loc);
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			
			if (type == TransformType.FIRST_PERSON_RIGHT_HAND) {
				GL11.glTranslated(-0.2, 0.2, 0.2);
				GL11.glRotated(-25, 0, 0, 1);
				GL11.glRotated(180, 1, 0, 0);
			} else {
				GL11.glTranslated(1.2, 0.2, 0.0);
				GL11.glRotated(180, 1, 0, 0);
				GL11.glRotated(180, 0, 1, 0);
				GL11.glRotated(25, 0, 0, 1);
			}
			if(stack.getItem() == ModItems.gun_revolver_nightmare)
				n1.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, stack);
			if(stack.getItem() == ModItems.gun_revolver_nightmare2)
				n2.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, stack);
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case GROUND:
			GL11.glTranslated(0.45, 0.25, 0.5);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(180, 1, 0, 0);
			if(stack.getItem() == ModItems.gun_revolver_nightmare)
				n1.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, stack);
			if(stack.getItem() == ModItems.gun_revolver_nightmare2)
				n2.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, stack);
			break;
		default:
			break;
		}
	}
}
