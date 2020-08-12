package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.ModelXVL1456;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderXVL1456 extends TEISRBase {

	protected ModelXVL1456 swordModel;
	protected static ResourceLocation tau_rl = new ResourceLocation(RefStrings.MODID +":textures/models/ModelXVL1456.png");
	
	public ItemRenderXVL1456() {
		swordModel = new ModelXVL1456();
	}
	
	
	
	@Override
	public void renderByItem(ItemStack itemStackIn) {
		GL11.glPopMatrix();
		GlStateManager.enableCull();
		Minecraft.getMinecraft().renderEngine.bindTexture(tau_rl);
		float f = 0;
		if(this.entity instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) this.entity;
			f = ((EntityPlayer)this.entity).getActiveItemStack().getItemUseAction() == EnumAction.BOW ? 0.05F : 0F;
			if(f == 0.05F && player.getHeldItemMainhand().getItem() == itemStackIn.getItem() && player.getHeldItemOffhand().getItem() == itemStackIn.getItem()){
				f = 0.025F;
			}
		}
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
			GL11.glTranslated(0.5, 0, -0.4);
		case FIRST_PERSON_RIGHT_HAND:
			GL11.glScaled(0.3, 0.3, 0.3);
			GL11.glTranslated(-0.5, 0.4, 0.6);
			GL11.glRotated(180, 1, 0, 0);
			GL11.glRotated(40, 0, 0, 1);
			if(type == TransformType.FIRST_PERSON_LEFT_HAND){
				GL11.glRotated(100, 0, 0, 1);
				GL11.glRotated(180, 1, 0, 0);
			}
			swordModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, f);
			break;
		case THIRD_PERSON_RIGHT_HAND:
		case THIRD_PERSON_LEFT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			GL11.glTranslated(-0.25, 0.1, -0.4);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glRotated(180, 0, 0, 1);
			GL11.glScaled(0.75, 0.75, 0.75);
			swordModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, f);
			break;
		default:
			break;
		}
		GL11.glPushMatrix();
	}
}
