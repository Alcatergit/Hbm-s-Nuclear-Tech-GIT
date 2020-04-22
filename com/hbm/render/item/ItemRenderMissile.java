package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.ItemCustomMissile;
import com.hbm.render.misc.MissileMultipart;
import com.hbm.render.misc.MissilePronter;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderMissile extends TEISRBase {

	@Override
	public void renderByItem(ItemStack item) {
		MissileMultipart missile = MissileMultipart.loadFromStruct(ItemCustomMissile.getStruct(item));
		if(missile == null)
			return;
		GL11.glPushMatrix();
		//GL11.glTranslated(0.5, 0.5, 0.5);
		switch(type) {
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
		case GROUND:
		case FIXED:
		case HEAD:
			
			double s = 0.2;
			GL11.glScaled(s, s, s);
			GL11.glTranslated(2, 0, 0);
			
			MissilePronter.prontMissile(missile, Minecraft.getMinecraft().renderEngine);
			
			break;
			
		case GUI:
			
			double height = missile.getHeight();
			
			if(height == 0D)
				height = 4D;
			
			double size = 20;
			double scale = size / height;
			
			GL11.glTranslated(height / 2 * scale, 0, 0);
			GL11.glTranslated(-9.2, 0.2, 0);
			//System.out.println(scale/14.285714285714285);
			GL11.glRotated(45, 0, 0, 1);
			GL11.glRotated(45, 1, 0, 0);
			
			//Drillgon200: This number is what I got when I found a decent scale number (0.14) for one part, then divided scale by it.
			//It seems to work pretty well
			GL11.glScaled(scale/14.285714285714285, scale/14.285714285714285, scale/14.285714285714285);
			//GL11.glRotated(135, 0, 0, 1);
			//GL11.glRotated(215, 1, 0, 0);
			
			
			//GL11.glScaled(-scale, -scale, -scale);
			
			/*if(part.type.name().equals(PartType.FINS.name())) {
				GL11.glTranslated(0, 0, 0);
				//GL11.glRotated(-45, 1, 0, 0);
			}*/

			GL11.glRotatef(System.currentTimeMillis() / 25 % 360, 0, -1, 0);
			MissilePronter.prontMissile(missile, Minecraft.getMinecraft().renderEngine);
			
			break;
		default: break;
		}
		
		GL11.glPopMatrix();
	}
}
