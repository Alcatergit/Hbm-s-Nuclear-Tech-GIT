package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.GunB93;
import com.hbm.lib.RefStrings;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.ModelB93;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderGunB93 extends TEISRBase {

	protected ModelB93 b93;
	protected ResourceLocation b93_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelB93.png");
	
	public RenderGunB93(){
		b93 = new ModelB93();
	}
	
	@Override
	public void renderByItem(ItemStack item) {
		GL11.glPopMatrix();
		switch(type) {
		case FIRST_PERSON_LEFT_HAND:
			GL11.glTranslated(0.1, 0, 0);
		case FIRST_PERSON_RIGHT_HAND:
			GL11.glPushMatrix();
			
			
			
				GL11.glEnable(GL11.GL_CULL_FACE);

				Minecraft.getMinecraft().renderEngine.bindTexture(b93_rl);
				//GL11.glRotatef(-135.0F, 0.0F, 0.0F, 1.0F);
				//GL11.glTranslatef(-0.5F, 0.0F, -0.2F);
				//GL11.glScalef(0.5F, 0.5F, 0.5F);
				//GL11.glScalef(0.5F, 0.5F, 0.5F);
				//GL11.glTranslatef(-0.2F, -0.1F, -0.1F);
				
				//GL11.glRotated(180, 0, 0, 1);
				//GL11.glRotated(-90, 0, 1, 0);
				//GL11.glRotated(20, 0, 0, 1);
				//GL11.glTranslated(-0.05, -0.0, 0.1);
				GL11.glScaled(0.25D, 0.25D, 0.25D);
				GL11.glRotated(180, 1, 0, 0);
				
				//GL11.glRotated(90, 0, 1, 0);
				GL11.glRotated(40, 0, 0, 1);
				GL11.glTranslated(0, -0.5, -0.7);
				if(type == TransformType.FIRST_PERSON_RIGHT_HAND){
					GL11.glTranslated(0, 0.5, 0);
				}
				
				if(type == TransformType.FIRST_PERSON_LEFT_HAND){
					GL11.glTranslated(0.0, 0.7, 0.5);
					GL11.glRotated(180, 1, 0, 0);
					GL11.glRotated(-90, 0, 0, 1);
				}
				
				if(item.getItem() == ModItems.gun_b93 && GunB93.getRotationFromAnim(item, Minecraft.getMinecraft().getRenderPartialTicks()) > 0) {
					float off = GunB93.getRotationFromAnim(item, Minecraft.getMinecraft().getRenderPartialTicks()) * 2;
					GL11.glRotatef(GunB93.getRotationFromAnim(item, Minecraft.getMinecraft().getRenderPartialTicks()) * -90, 0.0F, 0.0F, 1.0F);
					//b92Ani.apply();
					GL11.glTranslatef(off * -0.5F, off * -0.5F, 0.0F);
				}

				if(item.getItem() == ModItems.gun_b93)
					b93.renderAnim(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, GunB93.getTransFromAnim(item, Minecraft.getMinecraft().getRenderPartialTicks()));
				
			GL11.glPopMatrix();
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			GL11.glPushMatrix();
			Minecraft.getMinecraft().renderEngine.bindTexture(b93_rl);
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glRotated(180, 1, 0, 0);
				GL11.glRotated(90, 0, 1, 0);
				GL11.glTranslated(-0.2, 0, 0);


				if(item.getItem() == ModItems.gun_b93)
					b93.renderAnim(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, GunB93.getTransFromAnim(item, Minecraft.getMinecraft().getRenderPartialTicks()));
			GL11.glPopMatrix();
		default: break;
		}
		GL11.glPushMatrix();
	}
}
