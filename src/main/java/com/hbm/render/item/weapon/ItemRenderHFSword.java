package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.animloader.AnimationWrapper;
import com.hbm.items.weapon.ItemSwordCutter;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.anim.HbmAnimations.Animation;
import com.hbm.render.item.TEISRBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;

public class ItemRenderHFSword extends TEISRBase {

	@Override
	public void renderByItem(ItemStack itemStackIn) {
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			EnumHand hand = type == TransformType.FIRST_PERSON_RIGHT_HAND ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
			GL11.glScaled(5, 5, 5);
			GL11.glTranslated(0.3, -1.45, 0.55);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(-20, 1, 0, 0);
			GL11.glRotated(5, 0, 0, 1);
			
			Animation anim = HbmAnimations.getRelevantAnim(hand);
			if(ItemSwordCutter.startPos != null && anim != null && (ItemSwordCutter.clicked || anim.animation != null)){
				double[] swing_rot = HbmAnimations.getRelevantTransformation("SWING", hand);
				EntityPlayer p = Minecraft.getMinecraft().player;
				Vec3d v = ItemSwordCutter.startPos.rotateYaw((float) Math.toRadians(p.rotationYaw+180)).rotatePitch((float) Math.toRadians(-p.rotationPitch));
				double angle = Math.toDegrees(Math.atan2(v.y, v.x))-80;
				float oX = 0.4F;
				float oY = -1.55F;
				float oZ = 0;
				boolean flag = false;
				if(anim.animation != null){
					angle = ItemSwordCutter.prevAngle;
					long time = System.currentTimeMillis() - anim.startMillis;
					if(anim.animation.getDuration()-time < 400){
						flag = true;
					}
				} else {
					ItemSwordCutter.prevAngle = angle;
				}
				if(!flag){
					GL11.glTranslated(0.3F, -0.1F, 0);
					GL11.glTranslated(-oX, -oY, -oZ);
					GL11.glRotated(-angle, 0, 0, 1);
					GL11.glTranslated(oX, oY, oZ);
					GL11.glTranslated(0F, -0.2F, 0F);
					GL11.glRotated(10, 0, 1, 0);
				}
				GL11.glRotated(swing_rot[0], 1, 0, 0);
			}
			AnimationWrapper w = HbmAnimations.getRelevantBlenderAnim(hand);
			if(w == AnimationWrapper.EMPTY){
				GlStateManager.shadeModel(GL11.GL_FLAT);
				return;
			}
			
			ResourceManager.hf_sword.controller.setAnim(w);
			
			Minecraft.getMinecraft().getTextureManager().bindTexture(Minecraft.getMinecraft().player.getLocationSkin());
			ResourceManager.hf_sword.renderAnimated(System.currentTimeMillis(), (prevFrame, currentFrame, model, diffN, modelName) -> {
				if(modelName.equals("Sword")){
					Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.hf_sword_tex);
					return false;
				}
				return diffN >= 1;
			});
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			GL11.glRotated(10, 0, 0, 1);
			GL11.glRotated(20, 0, 1, 0);
			GL11.glRotated(-10, 0, 1, 0);
			GL11.glTranslated(-1.1, -3.8, 3.2);
			GL11.glScaled(3, 3, 3);
			GL11.glRotated(180, 0, 1, 0);
			Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.hf_sword_tex);
			ResourceManager.hf_sword.render((prevFrame, currentFrame, model, diffN, modelName) -> {
				if(modelName.equals("rightArm"))
					return true;
				return false;
			});
			break;
		case GUI:
			GL11.glTranslated(-0.6, 0.1, 0);
			GL11.glRotated(-135+90, 0, 0, 1);
			GL11.glRotated(90, 0, 1, 0);
			double scale = 0.6D;
			GL11.glScaled(scale, scale, scale);

			Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.hf_sword_tex);
			ResourceManager.hf_sword.render((prevFrame, currentFrame, model, diffN, modelName) -> {
				if(modelName.equals("rightArm"))
					return true;
				return false;
			});
			break;
		case NONE:
			break;
		}
		GlStateManager.shadeModel(GL11.GL_FLAT);
	}
}
