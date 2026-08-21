package com.hbm.render.item;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemChemistryTemplate;

import net.minecraft.client.Minecraft;
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
			if (stack.getItem() instanceof ItemChemistryTemplate && type == TransformType.GUI) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					GL11.glTranslated(0.5, 0.5, 0);

					// Safely create a rendering item stack
					try {
						ItemStack renderStack = new ItemStack(ModItems.chemistry_icon, 1, stack.getItemDamage());
						if (!renderStack.isEmpty()) {
							Minecraft.getMinecraft().getRenderItem().renderItem(renderStack, Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(renderStack, Minecraft.getMinecraft().world, Minecraft.getMinecraft().player));
							return;
						}
					} catch (Exception e) {
						// Recipe error, keep icon status
					}
				}
			}
			
			// The default rendering template icon (including cases where the shortcut key is not pressed and the recipe is incorrect).
			GL11.glTranslated(0.5, 0.5, 0);
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, itemModel);
		} catch(Exception e){
			// Capture all exceptions to ensure the rendering process is not interrupted.
		}
		super.renderByItem(stack);
	}
}
