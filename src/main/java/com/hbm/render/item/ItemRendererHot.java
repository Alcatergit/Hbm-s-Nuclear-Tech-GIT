package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.items.special.ItemHot;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;

public class ItemRendererHot extends TEISRBase {

	@Override
	public void renderByItem(ItemStack stack) {
		GL11.glPushMatrix();
		GL11.glTranslated(0.5, 0.5, 0);
		//RenderHelper.enableGUIStandardItemLighting();
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		
		Minecraft.getMinecraft().getRenderItem().renderItem(stack, itemModel);
		
		double h = ItemHot.getHeat(stack);
		if(h > 0) {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GlStateManager.color(1F, 1F, 1F, (float) h);
            //RenderItem.getInstance().renderIcon(0, 0, ((ItemHot)stack.getItem()).hotIcon, 16, 16);
            IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(new ItemStack(stack.getItem(), 1, 1), Minecraft.getMinecraft().world, null);
			model = ForgeHooksClient.handleCameraTransforms(model, type, false);
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
		}
		GL11.glPopMatrix();
	}
}
