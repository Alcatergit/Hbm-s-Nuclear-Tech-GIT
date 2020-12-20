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

public class FluidBarrelRender extends TileEntityItemStackRenderer {

	public static final FluidBarrelRender INSTANCE = new FluidBarrelRender();

	public TransformType type;
	public IBakedModel itemModel;
	
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
		/*buf.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
		for (EnumFacing enumfacing : EnumFacing.values()) {
			Minecraft.getMinecraft().getRenderItem().renderQuads(buf,
					itemModel.getQuads((IBlockState) null, enumfacing, 0L), -1, stack);
		}
		Minecraft.getMinecraft().getRenderItem().renderQuads(buf,
				itemModel.getQuads((IBlockState) null, (EnumFacing) null, 0L), -1, stack);
		tes.draw();*/
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
			float maxV = lava.getInterpolatedV(9);
			float minV = lava.getInterpolatedV(3);

			GL11.glTranslated(0, 0, 0.5 + HALF_A_PIXEL);
			buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			buf.pos(7 * PIX, 3 * PIX, 0).tex(minU, minV).endVertex();
			buf.pos(9 * PIX, 3 * PIX, 0).tex(maxU, minV).endVertex();
			buf.pos(9 * PIX, 9 * PIX, 0).tex(maxU, maxV).endVertex();
			buf.pos(7 * PIX, 9 * PIX, 0).tex(minU, maxV).endVertex();

			buf.pos(9 * PIX, 3 * PIX, -PIX).tex(maxU, minV).endVertex();
			buf.pos(7 * PIX, 3 * PIX, -PIX).tex(minU, minV).endVertex();
			buf.pos(7 * PIX, 9 * PIX, -PIX).tex(minU, maxV).endVertex();
			buf.pos(9 * PIX, 9 * PIX, -PIX).tex(maxU, maxV).endVertex();
			
			
			maxU = lava.getInterpolatedU(10);
			minU = lava.getInterpolatedU(9);
			maxV = lava.getInterpolatedV(12);
			minV = lava.getInterpolatedV(11);
			
			buf.pos(9 * PIX, 11 * PIX, 0).tex(minU, minV).endVertex();
			buf.pos(10 * PIX, 11 * PIX, 0).tex(maxU, minV).endVertex();
			buf.pos(10 * PIX, 12 * PIX, 0).tex(maxU, maxV).endVertex();
			buf.pos(9 * PIX, 12 * PIX, 0).tex(minU, maxV).endVertex();

			buf.pos(10 * PIX, 11 * PIX, -PIX).tex(maxU, minV).endVertex();
			buf.pos(9 * PIX, 11 * PIX, -PIX).tex(minU, minV).endVertex();
			buf.pos(9 * PIX, 12 * PIX, -PIX).tex(minU, maxV).endVertex();
			buf.pos(10 * PIX, 12 * PIX, -PIX).tex(maxU, maxV).endVertex();
			
			
			maxU = lava.getInterpolatedU(9);
			minU = lava.getInterpolatedU(8);
			maxV = lava.getInterpolatedV(13);
			minV = lava.getInterpolatedV(12);
			
			buf.pos(8 * PIX, 12 * PIX, 0).tex(minU, minV).endVertex();
			buf.pos(9 * PIX, 12 * PIX, 0).tex(maxU, minV).endVertex();
			buf.pos(9 * PIX, 13 * PIX, 0).tex(maxU, maxV).endVertex();
			buf.pos(8 * PIX, 13 * PIX, 0).tex(minU, maxV).endVertex();

			buf.pos(9 * PIX, 12 * PIX, -PIX).tex(maxU, minV).endVertex();
			buf.pos(8 * PIX, 12 * PIX, -PIX).tex(minU, minV).endVertex();
			buf.pos(8 * PIX, 13 * PIX, -PIX).tex(minU, maxV).endVertex();
			buf.pos(9 * PIX, 13 * PIX, -PIX).tex(maxU, maxV).endVertex();
			
			
			tes.draw();
			GlStateManager.enableLighting();
			
		}
		
		GL11.glPopAttrib();
		GL11.glPopMatrix();
		super.renderByItem(stack);
	}
}
