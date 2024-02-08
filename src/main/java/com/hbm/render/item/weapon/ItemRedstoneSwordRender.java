package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelSword;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRedstoneSwordRender extends TileEntityItemStackRenderer {
	
	public static final ItemRedstoneSwordRender INSTANCE = new ItemRedstoneSwordRender();
	
	public TransformType type;
	public IBakedModel itemModel;
	
	public ModelSword swordModel;
	
	public ItemRedstoneSwordRender(){
		swordModel = new ModelSword();
	}
	@Override
	public void renderByItem(ItemStack stack) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
			if(type != TransformType.GUI){
				if(type == TransformType.GROUND){
					//GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
					//GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
					GL11.glScalef(0.4F, 0.4F, 0.4F);
					GL11.glTranslatef(0.7F, 0.8F, 0.7F);
				}
				if(type == TransformType.FIRST_PERSON_LEFT_HAND || type == TransformType.FIRST_PERSON_RIGHT_HAND){
					GL11.glScalef(0.6F, 0.6F, 0.6F);
					GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
					GL11.glTranslatef(0.2F, 1.0F, 0.1F);
					if(type == TransformType.FIRST_PERSON_LEFT_HAND){
						GL11.glTranslatef(0.25F, 0.0F, 0.0F);
					}
				}
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelSwordRedstone.png"));
				GL11.glRotatef(180.0F, 0.0F, 0.0F, 0.0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(0.5F, -0.2F, -0.5F);
				swordModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			} else {
				GL11.glTranslated(0.5, 0.5, 0);
				Minecraft.getMinecraft().getRenderItem().renderItem(stack, itemModel);
			}
		GL11.glPopMatrix();
		super.renderByItem(stack);
	}
	
	
	
}
