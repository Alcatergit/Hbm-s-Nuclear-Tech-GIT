package com.hbm.render.item;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemChemistryTemplate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;

public class ChemTemplateRender extends TileEntityItemStackRenderer {

	public static final ChemTemplateRender INSTANCE = new ChemTemplateRender();
	public IBakedModel itemModel;
	public TransformType type;

	@Override
	public void renderByItem(ItemStack stack) {
		try{
			if (stack.getItem() instanceof ItemChemistryTemplate) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					if (type == TransformType.GUI) {
						GL11.glTranslated(0.5, 0.5, 0);
						try {
							ItemStack renderStack = new ItemStack(ModItems.chemistry_icon, 1, stack.getItemDamage());
							if (!renderStack.isEmpty()) {
								Minecraft.getMinecraft().getRenderItem().renderItem(renderStack, Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(renderStack, Minecraft.getMinecraft().world, Minecraft.getMinecraft().player));
								return;
							}
						} catch (Exception ignored) {
						}
					} else if (type != null) {
						GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
						RenderHelper.disableStandardItemLighting();
						RenderHelper.enableGUIStandardItemLighting();
						try {
							ItemStack renderStack = new ItemStack(ModItems.chemistry_icon, 1, stack.getItemDamage());
							if (!renderStack.isEmpty()) {
								IBakedModel recipeModel = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(renderStack, Minecraft.getMinecraft().world, Minecraft.getMinecraft().player);
								if (recipeModel.isBuiltInRenderer()) {
									renderStack.getItem().getTileEntityItemStackRenderer().renderByItem(renderStack);
								} else {
									GL11.glTranslated(0.5, 0.5, 0.5);
									Minecraft.getMinecraft().getRenderItem().renderItem(renderStack, recipeModel);
								}
								GL11.glPopAttrib();
								return;
							}
						} catch (Exception ignored) {
						}
						GL11.glPopAttrib();
					}
				}
			}

			GL11.glTranslated(0.5, 0.5, 0);
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, itemModel);
		} catch(Exception ignored){
		}
		super.renderByItem(stack);
	}
}
