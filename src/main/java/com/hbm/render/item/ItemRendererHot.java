package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.items.special.ItemHot;
import com.hbm.render.RenderHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;

public class ItemRendererHot extends TEISRBase {

	@Override
	public void renderByItem(ItemStack stack) {
		GL11.glPushMatrix();
		GL11.glTranslated(0.5, 0.5, 0);
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		
		Minecraft.getMinecraft().getRenderItem().renderItem(stack, itemModel);
		
		double h = ItemHot.getHeat(stack);
		if(h > 0) {
            GlStateManager.enableBlend();
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);
            GlStateManager.disableLighting();
            GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
			GlStateManager.color(1F, 1F, 1F, (float) h);
            IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(new ItemStack(stack.getItem(), 1, 15), Minecraft.getMinecraft().world, null);
			RenderHelper.bindBlockTexture();
            TextureAtlasSprite icon = model.getParticleTexture();
            float up = icon.getInterpolatedV(16);
    		float down = icon.getInterpolatedV(0);
    		float left = icon.getInterpolatedU(0);
    		float right = icon.getInterpolatedU(16);
    		float posX = -0.5F;
    		float posY = 0.5F;
    		float sizeY = -1;
    		float sizeX = 1;
    		RenderHelper.startDrawingTexturedQuads();
    		RenderHelper.addVertexWithUV(posX, posY + sizeY, 0.065F, left, up);
    		RenderHelper.addVertexWithUV(posX + sizeX, posY + sizeY, 0.065F, right, up);
    		RenderHelper.addVertexWithUV(posX + sizeX, posY, 0.065F, right, down);
    		RenderHelper.addVertexWithUV(posX, posY, 0.065F, left, down);
    		RenderHelper.draw();
    		
            GlStateManager.enableLighting();
            GlStateManager.color(1, 1, 1, 1);
            //GlStateManager.disableBlend();
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		}
		GL11.glPopMatrix();
	}
}
