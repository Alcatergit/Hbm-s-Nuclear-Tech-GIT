package com.hbm.render.tileentity;

import com.hbm.render.RenderHelper;
import com.hbm.tileentity.machine.TileEntityWatzStruct;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

import static com.hbm.render.util.SmallBlockPronter.*;

public class RenderWatzMultiblock extends TileEntitySpecialRenderer<TileEntityWatzStruct> {

    public static TextureAtlasSprite coolerSpriteTop;
    public static TextureAtlasSprite coolerSpriteSide;
    public static TextureAtlasSprite elementSpriteTop;
    public static TextureAtlasSprite elementSpriteSide;
    public static TextureAtlasSprite casingSprite;

    @Override
    public boolean isGlobalRenderer(TileEntityWatzStruct te){
        return true;
    }



    @Override
    public void render(TileEntityWatzStruct te, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);

        startDrawing();
        RenderHelper.bindBlockTexture();
        RenderHelper.startDrawingTexturedQuads();

        renderPilarBlockAt(coolerSpriteTop, coolerSpriteSide,  0F, 1F, 0F);
        renderPilarBlockAt(coolerSpriteTop, coolerSpriteSide,  0F, 2F, 0F);

        for(int i = 0; i < 3; i++) {
            renderPilarBlockAt(elementSpriteTop, elementSpriteSide,  1F, i, 0F);
            renderPilarBlockAt(elementSpriteTop, elementSpriteSide,  2F, i, 0F);
            renderPilarBlockAt(elementSpriteTop, elementSpriteSide,  0F, i, 1F);
            renderPilarBlockAt(elementSpriteTop, elementSpriteSide,  0F, i, 2F);
            renderPilarBlockAt(elementSpriteTop, elementSpriteSide,  -1F, i, 0F);
            renderPilarBlockAt(elementSpriteTop, elementSpriteSide,  -2F, i, 0F);
            renderPilarBlockAt(elementSpriteTop, elementSpriteSide,  0F, i, -1F);
            renderPilarBlockAt(elementSpriteTop, elementSpriteSide,  0F, i, -2F);
            renderPilarBlockAt(elementSpriteTop, elementSpriteSide,  1F, i, 1F);
            renderPilarBlockAt(elementSpriteTop, elementSpriteSide,  1F, i, -1F);
            renderPilarBlockAt(elementSpriteTop, elementSpriteSide,  -1F, i, 1F);
            renderPilarBlockAt(elementSpriteTop, elementSpriteSide,  -1F, i, -1F);
            renderPilarBlockAt(coolerSpriteTop,  coolerSpriteSide,2F, i, 1F);
            renderPilarBlockAt(coolerSpriteTop,  coolerSpriteSide,2F, i, -1F);
            renderPilarBlockAt(coolerSpriteTop,  coolerSpriteSide,1F, i, 2F);
            renderPilarBlockAt(coolerSpriteTop,  coolerSpriteSide,-1F, i, 2F);
            renderPilarBlockAt(coolerSpriteTop,  coolerSpriteSide,-2F, i, 1F);
            renderPilarBlockAt(coolerSpriteTop,  coolerSpriteSide,-2F, i, -1F);
            renderPilarBlockAt(coolerSpriteTop,  coolerSpriteSide,1F, i, -2F);
            renderPilarBlockAt(coolerSpriteTop,  coolerSpriteSide,-1F, i, -2F);
            for(int j = -1; j < 2; j++) {
                renderSimpleBlockAt(casingSprite,  3F, i, j);
                renderSimpleBlockAt(casingSprite,  j, i, 3F);
                renderSimpleBlockAt(casingSprite,  -3F, i, j);
                renderSimpleBlockAt(casingSprite,  j, i, -3F);
            }
            renderSimpleBlockAt(casingSprite,  2F, i, 2F);
            renderSimpleBlockAt(casingSprite,  2F, i, -2F);
            renderSimpleBlockAt(casingSprite,  -2F, i, 2F);
            renderSimpleBlockAt(casingSprite,  -2F, i, -2F);
        }

        RenderHelper.draw();
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableLighting();


        GL11.glPopMatrix();
    }



}
