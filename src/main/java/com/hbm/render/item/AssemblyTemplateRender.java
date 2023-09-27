package com.hbm.render.item;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import java.lang.IndexOutOfBoundsException;

import com.hbm.inventory.AssemblerRecipes;
import com.hbm.items.machine.ItemAssemblyTemplate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;

public class AssemblyTemplateRender extends TileEntityItemStackRenderer {

	public static final AssemblyTemplateRender INSTANCE = new AssemblyTemplateRender();

	public TransformType type;
	public IBakedModel itemModel;

	@Override
	public void renderByItem(ItemStack stack) {
		try{
			if (stack.getItem() instanceof ItemAssemblyTemplate && type == TransformType.GUI) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					GL11.glPushMatrix();
					GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
					GL11.glTranslated(0.5, 0.5, 0);
					GlStateManager.enableLighting();
					ItemStack item = AssemblerRecipes.recipeList.get(ItemAssemblyTemplate.getRecipeIndex(stack)).toStack();
					IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(item, Minecraft.getMinecraft().world, Minecraft.getMinecraft().player);
					model = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GUI, false);
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
