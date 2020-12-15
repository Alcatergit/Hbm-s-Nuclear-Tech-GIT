package com.hbm.render.util;

import com.hbm.render.RenderHelper;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class SmallBlockPronter {

	static float pixel = 1F/16F;

	/**
	 * Bind the required texture yourself bruh
	 * @param loc
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void renderSmolBlockAt(TextureAtlasSprite loc, float x, float y, float z) {
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMaxU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMinU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMinU(), loc.getMaxV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMaxU(), loc.getMaxV());

		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc.getMaxU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMinU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMinU(), loc.getMaxV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc.getMaxU(), loc.getMaxV());

		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc.getMaxU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc.getMinU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc.getMinU(), loc.getMaxV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc.getMaxU(), loc.getMaxV());

		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMaxU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc.getMinU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc.getMinU(), loc.getMaxV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMaxU(), loc.getMaxV());

		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc.getMaxU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc.getMinU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMinU(), loc.getMaxV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMaxU(), loc.getMaxV());

		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc.getMaxU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc.getMinU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMinU(), loc.getMaxV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMaxU(), loc.getMaxV());

	}
}
