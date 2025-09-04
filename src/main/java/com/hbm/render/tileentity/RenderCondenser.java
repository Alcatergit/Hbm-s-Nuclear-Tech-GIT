package com.hbm.render.tileentity;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityCondenserPowered;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class RenderCondenser extends TileEntitySpecialRenderer<TileEntityCondenserPowered> {
    @Override
    public void render(TileEntityCondenserPowered condenser, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();

        switch(condenser.getBlockMetadata() - 10) {
            case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
            case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
            case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
            case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
        }

        GL11.glShadeModel(GL11.GL_SMOOTH);
        bindTexture(ResourceManager.condenser_tex);
        ResourceManager.condenser.renderPart("Condenser");

        float rot = condenser.lastSpin + (condenser.spin - condenser.lastSpin) * partialTicks;

        GL11.glPushMatrix();
        GL11.glTranslated(0,1.5, 0);
        GL11.glRotatef(rot, 1, 0, 0);
        GL11.glTranslated(0, -1.5, 0);
        ResourceManager.condenser.renderPart("Fan1");
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslated(0,1.5, 0);
        GL11.glRotatef(rot, -1, 0, 0);
        GL11.glTranslated(0, -1.5, 0);
        ResourceManager.condenser.renderPart("Fan2");
        GL11.glPopMatrix();

        GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
    }
}
