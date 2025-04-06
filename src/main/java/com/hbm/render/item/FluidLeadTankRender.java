package com.hbm.render.item;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.machine.ItemFluidTank;
import com.hbm.render.NTMRenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class FluidLeadTankRender extends TEISRBase {
    public static final double HALF_A_PIXEL = 0.03125;
    public static final double PIX = 0.0625;

    public static final FluidLeadTankRender INSTANCE = new FluidLeadTankRender();
    public TextureAtlasSprite overlaySprite;

    @Override
    public void renderByItem(ItemStack stack) {
        GL11.glPushMatrix();
        GL11.glTranslated(0.5, 0.5, 0.5);
        Minecraft.getMinecraft().getRenderItem().renderItem(stack, itemModel);
        if(stack.getItem() instanceof ItemFluidTank){
            GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
            int color = Fluids.fromID(stack.getMetadata()).getColor();
            if(overlaySprite == null){
                overlaySprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("hbm:items/fluid_tank_lead_overlay");
            }

            GL11.glTranslated(-0.5, -0.5, -HALF_A_PIXEL);
            NTMRenderHelper.setColor(color);
            NTMRenderHelper.startDrawingTexturedQuads();
            NTMRenderHelper.drawFullTexture(overlaySprite, 0, 0, 1, 1, 0, false);
            NTMRenderHelper.drawFullTexture(overlaySprite, 0, 0, 1, 1, PIX, true);
            NTMRenderHelper.draw();
            NTMRenderHelper.resetColor();
            GL11.glPopAttrib();
        }
        GL11.glPopMatrix();
        super.renderByItem(stack);
    }
}
