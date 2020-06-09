package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.render.RenderHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class FluidTankRender extends TileEntityItemStackRenderer {

	public static final FluidTankRender INSTANCE = new FluidTankRender();

	public TransformType type;
	public IBakedModel itemModel;

	public FluidTankRender() {

	}

	@Override
	public void renderByItem(ItemStack stack) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		RenderHelper.bindBlockTexture();

		Tessellator tes = Tessellator.getInstance();
		BufferBuilder buf = Tessellator.getInstance().getBuffer();
		GL11.glPushMatrix();
		GL11.glTranslated(0.5, 0.5, 0.5);
		Minecraft.getMinecraft().getRenderItem().renderItem(stack, itemModel);
		GL11.glPopMatrix();

		final double HALF_A_PIXEL = 0.03125;
		final double PIX = 0.0625;
		FluidStack f = FluidUtil.getFluidContained(stack);
		TextureAtlasSprite lava = null;
		if (f != null)
			lava = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(f.getFluid().getStill().toString());

		if (lava != null) {
			RenderHelper.setColor(f.getFluid().getColor(f));
			GlStateManager.disableLighting();
			float maxU = lava.getInterpolatedU(9);
			float minU = lava.getInterpolatedU(7);
			float maxV = lava.getInterpolatedV(11);
			float minV = lava.getInterpolatedV(5);

			GL11.glTranslated(0, 0, 0.5 + HALF_A_PIXEL);
			buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			buf.pos(7 * PIX, 5 * PIX, 0).tex(minU, minV).endVertex();
			buf.pos(9 * PIX, 5 * PIX, 0).tex(maxU, minV).endVertex();
			buf.pos(9 * PIX, 11 * PIX, 0).tex(maxU, maxV).endVertex();
			buf.pos(7 * PIX, 11 * PIX, 0).tex(minU, maxV).endVertex();

			buf.pos(9 * PIX, 5 * PIX, -PIX).tex(maxU, minV).endVertex();
			buf.pos(7 * PIX, 5 * PIX, -PIX).tex(minU, minV).endVertex();
			buf.pos(7 * PIX, 11 * PIX, -PIX).tex(minU, maxV).endVertex();
			buf.pos(9 * PIX, 11 * PIX, -PIX).tex(maxU, maxV).endVertex();
			
			tes.draw();
			GlStateManager.enableLighting();
			
		}
		GL11.glPopAttrib();
		GL11.glPopMatrix();
		super.renderByItem(stack);
	}
}
