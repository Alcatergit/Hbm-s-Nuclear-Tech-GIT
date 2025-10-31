package com.hbm.render.item;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import java.lang.IndexOutOfBoundsException;

import com.hbm.inventory.CrucibleRecipes;
import com.hbm.items.machine.ItemCrucibleTemplate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;

public class CrucibleTemplateRender extends TileEntityItemStackRenderer {

	public static final CrucibleTemplateRender INSTANCE = new CrucibleTemplateRender();

	public TransformType type;
	public IBakedModel itemModel;

	@Override
	public void renderByItem(ItemStack stack) {
		try{
			if (stack.getItem() instanceof ItemCrucibleTemplate && type == TransformType.GUI) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					GL11.glPushMatrix();
					GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
					GL11.glTranslated(0.5, 0.5, 0);
					GlStateManager.enableLighting();
					ItemStack item = CrucibleRecipes.getIcon(stack);
					IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(item, Minecraft.getMinecraft().world, Minecraft.getMinecraft().player);
					model = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(model, TransformType.GUI, false);
					Minecraft.getMinecraft().getRenderItem().renderItem(item, model);
					GL11.glPopAttrib();
					GL11.glPopMatrix();
				} else {
					GL11.glTranslated(0.5, 0.5, 0);
					Minecraft.getMinecraft().getRenderItem().renderItem(stack, itemModel);
				}
			} else {
				Minecraft.getMinecraft().getRenderItem().renderItem(stack, itemModel);
			}
		} catch(IndexOutOfBoundsException e){

		}
		super.renderByItem(stack);
	}
}
