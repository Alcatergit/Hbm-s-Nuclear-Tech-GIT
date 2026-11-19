package com.hbm.render.item;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.CrucibleRecipes;
import com.hbm.items.machine.ItemCrucibleTemplate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;

public class CrucibleTemplateRender extends TileEntityItemStackRenderer {

	public static final CrucibleTemplateRender INSTANCE = new CrucibleTemplateRender();

	public TransformType type;
	public IBakedModel itemModel;

	@Override
	public void renderByItem(ItemStack stack) {
		try{
			if (stack.getItem() instanceof ItemCrucibleTemplate) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					if (type == TransformType.GUI) {
						GL11.glPushMatrix();
						GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
						GL11.glTranslated(0.5, 0.5, 0);
						GlStateManager.enableLighting();
						try {
							ItemStack item = CrucibleRecipes.getIcon(stack);
							if (item != null && !item.isEmpty()) {
								IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(item, Minecraft.getMinecraft().world, Minecraft.getMinecraft().player);
								model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GUI, false);
								Minecraft.getMinecraft().getRenderItem().renderItem(item, model);
								GL11.glPopAttrib();
								GL11.glPopMatrix();
								return;
							}
						} catch (Exception ignored) {
						}
						GL11.glPopAttrib();
						GL11.glPopMatrix();
					} else if (type != null) {
						GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
						RenderHelper.disableStandardItemLighting();
						RenderHelper.enableGUIStandardItemLighting();
						try {
							ItemStack item = CrucibleRecipes.getIcon(stack);
							if (item != null && !item.isEmpty()) {
								IBakedModel recipeModel = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(item, Minecraft.getMinecraft().world, Minecraft.getMinecraft().player);
								if (recipeModel.isBuiltInRenderer()) {
									item.getItem().getTileEntityItemStackRenderer().renderByItem(item);
								} else {
									GL11.glTranslated(0.5, 0.5, 0.5);
									Minecraft.getMinecraft().getRenderItem().renderItem(item, recipeModel);
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
