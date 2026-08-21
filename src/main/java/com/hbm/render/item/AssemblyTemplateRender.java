package com.hbm.render.item;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

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
					
					// Securely obtain recipe indexes and items
					int recipeIndex = ItemAssemblyTemplate.getRecipeIndex(stack);

					if (recipeIndex >= 0 && recipeIndex < AssemblerRecipes.recipeList.size()) {
						RecipesCommon.ComparableStack recipe = AssemblerRecipes.recipeList.get(recipeIndex);
						if (recipe != null) {
							try {
								ItemStack item = recipe.toStack();
								if (!item.isEmpty()) {
									IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(item, Minecraft.getMinecraft().world, Minecraft.getMinecraft().player);
									model = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GUI, false);
									Minecraft.getMinecraft().getRenderItem().renderItem(item, model);
									GL11.glPopAttrib();
									GL11.glPopMatrix();
									return;
								}
							} catch (Exception e) {
								// Recipe error, keep icon status
							}
						}
					}

					// If the recipe rendering fails or the shortcut key is not pressed, the icon for rendering the template itself will be displayed.
					GL11.glPopAttrib();
					GL11.glPopMatrix();
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
