package com.hbm.render.tileentity;

import com.hbm.tileentity.machine.TileEntityHeatBoiler;
import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderHeatBoiler extends TileEntitySpecialRenderer<TileEntityHeatBoiler> {

    @Override
    public boolean isGlobalRenderer(TileEntityHeatBoiler te) {
        return true;
    }

    @Override
    public void render(TileEntityHeatBoiler te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();

        switch(te.getBlockMetadata() - BlockDummyable.offset)
        {
            case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
            case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
            case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
            case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
        }

        bindTexture(ResourceManager.heat_boiler_tex);

        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        ResourceManager.heat_boiler.renderAll();
        GlStateManager.shadeModel(GL11.GL_FLAT);

        GL11.glPopMatrix();
    }
}
