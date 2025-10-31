package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.ModelEMPRay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderEMPRay extends TEISRBase {

	protected ModelEMPRay emp;
	protected ResourceLocation emp_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelEMPRay.png");
	
	public ItemRenderEMPRay() {
		emp = new ModelEMPRay();
	}
	
	@Override
	public void renderByItem(ItemStack itemStackIn) {
		Minecraft.getMinecraft().renderEngine.bindTexture(emp_rl);
		GlStateManager.enableCull();
		float f = 0;
		if(this.entity instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) this.entity;
			f = player.getActiveItemStack().getItemUseAction() == EnumAction.BOW ? 0.15F : 0F;
			if(f == 0.15F && player.getHeldItemMainhand().getItem() == itemStackIn.getItem() && player.getHeldItemOffhand().getItem() == itemStackIn.getItem()){
				f = 0.075F;
			}
		}
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
			GL11.glTranslated(0, 0, -0.2);
		case FIRST_PERSON_RIGHT_HAND:
			GL11.glScaled(0.25, 0.25, 0.25);
			GL11.glTranslated(2.0, 2.0, 2.5);
			if(type == TransformType.FIRST_PERSON_RIGHT_HAND){
				GL11.glRotated(0, 0, 1, 0);
				GL11.glRotated(-40, 0, 0, 1);
				GL11.glRotated(180, 1, 0, 0);
				GL11.glRotated(-10, 0, 0, 1);
			} else {
				GL11.glRotated(180, 0, 1, 0);
				GL11.glRotated(180, 1, 0, 0);
				GL11.glRotated(35, 0, 0, 1);
			}
			
			emp.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, f);
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			GL11.glScaled(0.5, 0.5, 0.5);
			if(type == TransformType.THIRD_PERSON_LEFT_HAND)
				GL11.glTranslated(0.4, 0, 0);
			if(type == TransformType.GROUND)
				GL11.glTranslated(0.05, 0, 0);
			GL11.glTranslated(0.8, 1.1, 0.5);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(180, 1, 0, 0);
			emp.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, f);
			break;
		default:
			break;
		}
	}
}
