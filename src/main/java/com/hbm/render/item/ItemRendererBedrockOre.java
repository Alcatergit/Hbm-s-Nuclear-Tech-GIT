package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.items.special.ItemBedrockOre;
import com.hbm.render.RenderHelper;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.ItemStack;

public class ItemRendererBedrockOre extends TEISRBase {

	public static final double HALF_A_PIXEL = 0.03125;
	public static final double PIX = 0.0625;
	public TextureAtlasSprite layerTex;

	private int dirtyColor = 0;
	private float cleanliness = 0F;

	public ItemRendererBedrockOre(int dirtyColor, float cleanliness){
		this.dirtyColor = dirtyColor;
		this.cleanliness = cleanliness;
	}

	@Override
	public void renderByItem(ItemStack stack) {
		GL11.glPushMatrix();
		GL11.glTranslated(0.5, 0.5, 0.5);
		Minecraft.getMinecraft().getRenderItem().renderItem(stack, itemModel);
		if(stack.getItem() instanceof ItemBedrockOre){
			ItemBedrockOre oreItem = (ItemBedrockOre)stack.getItem();
			int color = BobMathUtil.interpolateColor(this.dirtyColor, ItemBedrockOre.getColor(stack), this.cleanliness);
			
			if(layerTex == null){
				layerTex = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("hbm:items/ore_bedrock_layer");
			}

			GL11.glTranslated(-0.5, -0.5, -HALF_A_PIXEL);
			RenderHelper.setColor(color);
        	RenderHelper.startDrawingTexturedQuads();
			RenderHelper.drawFullTexture(layerTex, 0, 0, 1, 1, 0, false);
			RenderHelper.drawFullTexture(layerTex, 0, 0, 1, 1, PIX, true);
			RenderHelper.draw();
        	RenderHelper.resetColor();
		}
		GL11.glPopMatrix();
		super.renderByItem(stack);
	}
}
