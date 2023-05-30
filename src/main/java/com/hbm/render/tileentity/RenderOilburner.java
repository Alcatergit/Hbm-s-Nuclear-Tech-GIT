package com.hbm.render.tileentity;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityHeaterOilburner;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class RenderOilburner extends TileEntitySpecialRenderer<TileEntityHeaterOilburner> {
    @Override
    public boolean isGlobalRenderer(TileEntityHeaterOilburner te) {
        return true;
    }

    @Override
    public void render(TileEntityHeaterOilburner te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);

        GL11.glShadeModel(GL11.GL_SMOOTH);
        bindTexture(ResourceManager.heater_oilburner_tex);
        ResourceManager.heater_oilburner.renderAll();
        GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glPopMatrix();
    }
}
