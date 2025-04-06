package com.hbm.render.util;

import com.hbm.render.NTMRenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.lwjgl.opengl.GL11;

public class SmallBlockPronter {

    static float pixel = 1F / 16F;


    public static void startDrawing() {
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.enableCull();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
        GlStateManager.disableAlpha();
    }

    /**
     * Renders a simple block at the given coordinates with the given texture. Uses single texture for all faces
     *
     * @param loc
     * @param x
     * @param y
     * @param z
     */
    public static void renderSimpleBlockAt(TextureAtlasSprite loc, float x, float y, float z) {
        //Front Face
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMaxU(), loc.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMinU(), loc.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMinU(), loc.getMaxV());
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMaxU(), loc.getMaxV());

        //Left Face
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc.getMaxU(), loc.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMinU(), loc.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMinU(), loc.getMaxV());
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc.getMaxU(), loc.getMaxV());

        //Right Face
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc.getMaxU(), loc.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc.getMinU(), loc.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc.getMinU(), loc.getMaxV());
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc.getMaxU(), loc.getMaxV());

        //Back Face
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMaxU(), loc.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc.getMinU(), loc.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc.getMinU(), loc.getMaxV());
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMaxU(), loc.getMaxV());

        //Bottom Face
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc.getMaxU(), loc.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc.getMinU(), loc.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMinU(), loc.getMaxV());
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMaxU(), loc.getMaxV());

        //Top Face
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc.getMaxU(), loc.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc.getMinU(), loc.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMinU(), loc.getMaxV());
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMaxU(), loc.getMaxV());

    }


    /**
     * Renders a simple block at the given coordinates with the given texture. Uses end texture for top and bottom, side texture for sides
     *
     * @param end
     * @param side
     * @param x
     * @param y
     * @param z
     */

    public static void renderPilarBlockAt(TextureAtlasSprite end, TextureAtlasSprite side, float x, float y, float z) {
        // Front face
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, side.getMaxU(), side.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, side.getMinU(), side.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, side.getMinU(), side.getMaxV());
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, side.getMaxU(), side.getMaxV());

        // Left face
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, side.getMaxU(), side.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, side.getMinU(), side.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, side.getMinU(), side.getMaxV());
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, side.getMaxU(), side.getMaxV());

        // Right face
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, side.getMaxU(), side.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, side.getMinU(), side.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, side.getMinU(), side.getMaxV());
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, side.getMaxU(), side.getMaxV());

        // Back face
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, side.getMaxU(), side.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, side.getMinU(), side.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, side.getMinU(), side.getMaxV());
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, side.getMaxU(), side.getMaxV());

        // Bottom face
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, end.getMaxU(), end.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, end.getMinU(), end.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, end.getMinU(), end.getMaxV());
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, end.getMaxU(), end.getMaxV());

        // Top face
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, end.getMaxU(), end.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, end.getMinU(), end.getMinV());
        NTMRenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, end.getMinU(), end.getMaxV());
        NTMRenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, end.getMaxU(), end.getMaxV());
    }

}
