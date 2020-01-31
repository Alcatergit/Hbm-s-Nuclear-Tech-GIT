package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelRevolver;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GunRevolverRender extends TileEntityItemStackRenderer {

	public static final GunRevolverRender INSTANCE = new GunRevolverRender();

	public IBakedModel revolverModel;
	public TransformType type;
	protected ModelRevolver swordModel;

	public GunRevolverRender() {
		swordModel = new ModelRevolver();
	}

	@Override
	public void renderByItem(ItemStack item) {
		GL11.glPushMatrix();
		switch (type) {
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:

			if (item.getItem() == ModItems.gun_revolver)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/ModelRevolver.png"));
			// if(item.getItem() == ModItems.gun_revolver_saturnite)
			// Minecraft.getMinecraft().renderEngine.bindTexture(new
			// ResourceLocation(RefStrings.MODID
			// +":textures/models/ModelRevolverSaturnite.png"));
			// GL11.glRotatef(-135.0F, 0.0F, 0.0F, 1.0F);
			// GL11.glTranslatef(-0.5F, 0.0F, -0.2F);
			// GL11.glScalef(2.0F, 2.0F, 2.0F);
			// GL11.glScalef(0.5F, 0.5F, 0.5F);
			if (type == TransformType.FIRST_PERSON_RIGHT_HAND) {
				GL11.glTranslated(-0.2, 0.2, 0.2);
				GL11.glRotated(-10, 0, 1, 0);
				GL11.glRotated(-20, 0, 0, 1);
				GL11.glRotated(180, 1, 0, 0);
			} else {
				GL11.glTranslated(1.2, 0.2, 0.0);
				GL11.glRotated(180, 1, 0, 0);
				GL11.glRotated(180, 0, 1, 0);
				GL11.glRotated(23, 0, 0, 1);
			}
			
			// ((EntityPlayer)data[1]).isSwingInProgress = false;

			swordModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case GROUND:
			if (item.getItem() == ModItems.gun_revolver)
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/ModelRevolver.png"));
			// if(item.getItem() == ModItems.gun_revolver_saturnite)
			// Minecraft.getMinecraft().renderEngine.bindTexture(new
			// ResourceLocation(RefStrings.MODID
			// +":textures/models/ModelRevolverSaturnite.png"));
			//GL11.glRotated(180, 1, 0, 0);
			GL11.glTranslated(0.45, 0.25, 0.5);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(180, 1, 0, 0);
			// GL11.glScalef(2.0F, 2.0F, 2.0F);
			swordModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
		default:
			break;
		}
	}
}
