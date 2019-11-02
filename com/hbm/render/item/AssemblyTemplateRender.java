package com.hbm.render.item;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.MachineRecipes;
import com.hbm.items.tool.ItemAssemblyTemplate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;

public class AssemblyTemplateRender extends TileEntityItemStackRenderer {

	public static final AssemblyTemplateRender INSTANCE = new AssemblyTemplateRender();

	public TransformType type;
	public IBakedModel itemModel;

	@Override
	public void renderByItem(ItemStack stack) {

		if (stack.getItem() instanceof ItemAssemblyTemplate && type == TransformType.GUI) {
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				GL11.glTranslated(0.5, 0.5, 0);
				Minecraft.getMinecraft().getRenderItem().renderItem(MachineRecipes.getOutputFromTempate(stack), Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(MachineRecipes.getOutputFromTempate(stack), Minecraft.getMinecraft().world, Minecraft.getMinecraft().player));
			} else {
				GL11.glTranslated(0.5, 0.5, 0);
				Minecraft.getMinecraft().getRenderItem().renderItem(stack, itemModel);
			}
		} else {
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, itemModel);
		}
		super.renderByItem(stack);
	}
}
