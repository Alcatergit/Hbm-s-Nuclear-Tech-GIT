package com.hbm.render.tileentity;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityHeaterHeatex;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class RenderHeaterHeatex extends TileEntitySpecialRenderer<TileEntityHeaterHeatex> {
    @Override
    public boolean isGlobalRenderer(TileEntityHeaterHeatex te) {
        return true;
    }

    @Override
    public void render(TileEntityHeaterHeatex tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);

        switch (tile.getBlockMetadata() - BlockDummyable.offset) {
            case 3:
                GL11.glRotatef(270, 0F, 1F, 0F);
                break;
            case 5:
                GL11.glRotatef(0, 0F, 1F, 0F);
                break;
            case 2:
                GL11.glRotatef(90, 0F, 1F, 0F);
                break;
            case 4:
                GL11.glRotatef(180, 0F, 1F, 0F);
                break;
        }

        GL11.glShadeModel(GL11.GL_SMOOTH);
        bindTexture(ResourceManager.heater_heatex_tex);
        ResourceManager.heater_heatex.renderAll();
        GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glPopMatrix();
    }
}
