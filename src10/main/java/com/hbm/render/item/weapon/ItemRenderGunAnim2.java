package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.ModelB92;
import com.hbm.render.model.ModelB93;
import com.hbm.render.model.ModelBoltAction;
import com.hbm.render.model.ModelLeverAction;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

public class ItemRenderGunAnim2 extends TEISRBase {

	protected ModelLeverAction leveraction;
	protected ModelBoltAction boltaction;
	protected ModelB92 b92;
	protected ModelB93 b93;
	protected ResourceLocation leverActionLoc = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelLeverAction.png");
	protected ResourceLocation leverActionDark_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelLeverActionDark.png");
	protected ResourceLocation leverBoltAction_green = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelBoltActionGreen.png");
	protected ResourceLocation leverBoltAction = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelBoltActionDark.png");
	
	public ItemRenderGunAnim2() {
		leveraction = new ModelLeverAction();
		boltaction = new ModelBoltAction();
		b92 = new ModelB92();
		b93 = new ModelB93();
	}
	
	@Override
	public void renderByItem(ItemStack item) {
		
		float lever = 0;
		
		if(item.getItem() == ModItems.gun_lever_action/* || item.getItem() == ModItems.gun_lever_action_sonata*/)
			Minecraft.getMinecraft().renderEngine.bindTexture(leverActionLoc);
		if(item.getItem() == ModItems.gun_lever_action_dark)
			Minecraft.getMinecraft().renderEngine.bindTexture(leverActionDark_rl);
		if(item.getItem() == ModItems.gun_bolt_action)
			Minecraft.getMinecraft().renderEngine.bindTexture(leverBoltAction);
		if(item.getItem() == ModItems.gun_bolt_action_green)
			Minecraft.getMinecraft().renderEngine.bindTexture(leverBoltAction_green);
		
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
			
			if(item.getItem() == ModItems.gun_lever_action || item.getItem() == ModItems.gun_lever_action_dark) {

				double[] recoil = HbmAnimations.getRelevantTransformation("LEVER_RECOIL", type == TransformType.FIRST_PERSON_LEFT_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
				GL11.glTranslated(recoil[0], recoil[1] * 4, recoil[2]);
				
				GL11.glTranslatef(-1.5F, 0, 0);
				double[] rotation = HbmAnimations.getRelevantTransformation("LEVER_ROTATE", type == TransformType.FIRST_PERSON_LEFT_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
				GL11.glRotated(rotation[2], 0.0, 0.0, 1.0);
				lever = (float) Math.toRadians(rotation[2] * 2);
				GL11.glTranslatef(1.5F, 0, 0);
			}
			
			if(item.getItem() == ModItems.gun_lever_action || item.getItem() == ModItems.gun_lever_action_dark)
				leveraction.renderAnim(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, lever);
			if(item.getItem() == ModItems.gun_bolt_action)
				boltaction.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_bolt_action_green)
				boltaction.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case GROUND:
		case FIXED:
		case HEAD:
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glTranslated(1.0, 1, 1);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(180, 1, 0, 0);
			if(item.getItem() == ModItems.gun_lever_action)
				leveraction.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_lever_action_dark)
				leveraction.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_bolt_action)
				boltaction.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(item.getItem() == ModItems.gun_bolt_action_green)
				boltaction.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		default:
			break;
		}
	}
}
