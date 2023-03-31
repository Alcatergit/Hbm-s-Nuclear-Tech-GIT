package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.items.machine.ItemFluidIcon;
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

public class ItemRenderFluidIcon extends TEISRBase {

	private static final double HALF_A_PIXEL = 0.03125;
	private static final double PIX = 0.0625;

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

		Fluid f = ItemFluidIcon.getFluid(stack);
		TextureAtlasSprite lava = null;
		if (f != null)
			lava = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(f.getStill().toString());

		if (lava != null) {
			RenderHelper.setColor(f.getColor(new FluidStack(f, 1000)));
			GlStateManager.disableLighting();

			GL11.glTranslated(0, 0, 0.5 + HALF_A_PIXEL);
			buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

			drawRect(buf, lava, 5, 2, 11, 9);
			drawRect(buf, lava, 6, 1, 10, 2);
			drawRect(buf, lava, 4, 3, 5, 7);
			drawRect(buf, lava, 11, 3, 12, 7);
			drawRect(buf, lava, 6, 9, 10, 12);
			drawRect(buf, lava, 7, 12, 9, 15);

			tes.draw();
			GlStateManager.enableLighting();
			
		}
		GL11.glPopAttrib();
		GL11.glPopMatrix();
		super.renderByItem(stack);
	}

	private void drawRect(BufferBuilder buf, TextureAtlasSprite texture, int x1, int y1, int x2, int y2){
		float maxU = texture.getInterpolatedU(x2);
		float minU = texture.getInterpolatedU(x1);
		float maxV = texture.getInterpolatedV(y2);
		float minV = texture.getInterpolatedV(y1);
		
		buf.pos(x1 * PIX, y1 * PIX, 0).tex(minU, minV).endVertex();
		buf.pos(x2 * PIX, y1 * PIX, 0).tex(maxU, minV).endVertex();
		buf.pos(x2 * PIX, y2 * PIX, 0).tex(maxU, maxV).endVertex();
		buf.pos(x1 * PIX, y2 * PIX, 0).tex(minU, maxV).endVertex();

		buf.pos(x2 * PIX, y1 * PIX, -PIX).tex(maxU, minV).endVertex();
		buf.pos(x1 * PIX, y1 * PIX, -PIX).tex(minU, minV).endVertex();
		buf.pos(x1 * PIX, y2 * PIX, -PIX).tex(minU, maxV).endVertex();
		buf.pos(x2 * PIX, y2 * PIX, -PIX).tex(maxU, maxV).endVertex();
	}
}
