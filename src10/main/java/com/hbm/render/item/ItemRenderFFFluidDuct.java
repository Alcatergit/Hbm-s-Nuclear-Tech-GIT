package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.items.machine.ItemFFFluidDuct;
import com.hbm.render.RenderHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class ItemRenderFFFluidDuct extends TEISRBase {

	@Override
	public void renderByItem(ItemStack stack) {
		GL11.glPushMatrix();
		GlStateManager.enableAlpha();
		RenderHelper.bindBlockTexture();

		Tessellator tes = Tessellator.getInstance();
		BufferBuilder buf = Tessellator.getInstance().getBuffer();
		GL11.glPushMatrix();
		GL11.glTranslated(0.5, 0.5, 0.5);
		Minecraft.getMinecraft().getRenderItem().renderItem(stack, itemModel);
		GL11.glPopMatrix();

		final double HALF_A_PIXEL = 0.03125;
		final double PIX = 0.0625;
		Fluid f = ItemFFFluidDuct.getFluidFromStack(stack);
		TextureAtlasSprite lava = null;
		if (f != null)
			lava = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(f.getStill().toString());

		if (lava != null) {
			RenderHelper.setColor(f.getColor(new FluidStack(f, 1000)));
			GlStateManager.disableLighting();
			float maxU = lava.getInterpolatedU(13);
			float minU = lava.getInterpolatedU(3);
			float maxV = lava.getInterpolatedV(7);
			float minV = lava.getInterpolatedV(9);

			GL11.glTranslated(0, 0, 0.5 + HALF_A_PIXEL);
			buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			buf.pos(3 * PIX, 7 * PIX, 0).tex(minU, minV).endVertex();
			buf.pos(13 * PIX, 7 * PIX, 0).tex(maxU, minV).endVertex();
			buf.pos(13 * PIX, 9 * PIX, 0).tex(maxU, maxV).endVertex();
			buf.pos(3 * PIX, 9 * PIX, 0).tex(minU, maxV).endVertex();

			buf.pos(13 * PIX, 7 * PIX, -PIX).tex(maxU, minV).endVertex();
			buf.pos(3 * PIX, 7 * PIX, -PIX).tex(minU, minV).endVertex();
			buf.pos(3 * PIX, 9 * PIX, -PIX).tex(minU, maxV).endVertex();
			buf.pos(13 * PIX, 9 * PIX, -PIX).tex(maxU, maxV).endVertex();
			
			tes.draw();
			GlStateManager.enableLighting();
			
		}
		GL11.glPopMatrix();
		super.renderByItem(stack);
	}
}
