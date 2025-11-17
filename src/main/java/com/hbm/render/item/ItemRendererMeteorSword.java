package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.render.util.RenderMiscEffects;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class ItemRendererMeteorSword extends TEISRBase {

    float r;
    float g;
    float b;

    public ItemRendererMeteorSword(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public void renderByItem(ItemStack stack) {
        // Preserve original state
        boolean origBlend = GL11.glGetBoolean(GL11.GL_BLEND);
        boolean origAlpha = GL11.glGetBoolean(GL11.GL_ALPHA_TEST);
        boolean origDepthMask = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK);
        int origDepthFunc = GL11.glGetInteger(GL11.GL_DEPTH_FUNC);
        int origBlendSrc = GL11.glGetInteger(GL11.GL_BLEND_SRC);
        int origBlendDst = GL11.glGetInteger(GL11.GL_BLEND_DST);

        GL11.glTranslated(0.5, 0.5, 0.5);

        Minecraft mc = Minecraft.getMinecraft();
        Minecraft.getMinecraft().getRenderItem().renderItem(stack, itemModel);

        mc.renderEngine.bindTexture(RenderMiscEffects.glint);

        GlStateManager.depthFunc(GL11.GL_EQUAL);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();

        for (int j1 = 0; j1 < 2; ++j1) {
            GlStateManager.blendFunc(GlStateManager.SourceFactor.DST_ALPHA, GlStateManager.DestFactor.ONE);
            float f = (float)(Minecraft.getSystemTime() % (long)(3000 + j1 * 1873)) / (3000.0F + (float)(j1 * 1873))/8F;
            Tessellator tessellator = Tessellator.getInstance();

            float in = 0.36F;

            GlStateManager.color(r * in, g * in, b * in, 1.0F);

            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glPushMatrix();
            GL11.glScaled(8, 8, 8);
            GL11.glTranslated(f, 0, 0);
            GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);

            GlStateManager.pushMatrix();
            GlStateManager.translate(-0.5F, -0.5F, -0.5F);

            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.ITEM);

            int color = (0xFF << 24) | ((byte)((r * in) * 255) << 16) | ((byte)((g * in) * 255) << 8) | ((byte)((b * in) * 255));

            for (EnumFacing enumfacing : EnumFacing.values())
            {
                Minecraft.getMinecraft().getRenderItem().renderQuads(bufferbuilder, itemModel.getQuads((IBlockState)null, enumfacing, 0L), color, stack);
            }

            Minecraft.getMinecraft().getRenderItem().renderQuads(bufferbuilder, itemModel.getQuads((IBlockState)null, (EnumFacing)null, 0L), color, stack);
            tessellator.draw();

            GL11.glPopMatrix();

            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.depthMask(true);
        GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.enableLighting();
        GlStateManager.depthFunc(GL11.GL_LEQUAL);

        // Restore to original state
        if (origDepthMask) {
            GlStateManager.depthMask(true);
        } else {
            GlStateManager.depthMask(false);
        }

        if (origBlend) {
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(origBlendSrc, origBlendDst);
        } else {
            GlStateManager.disableBlend();
        }

        if (origAlpha) {
            GlStateManager.enableAlpha();
        } else {
            GlStateManager.disableAlpha();
        }

        GlStateManager.enableLighting();
        GlStateManager.depthFunc(origDepthFunc);
    }
}
