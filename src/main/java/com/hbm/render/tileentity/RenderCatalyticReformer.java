package com.hbm.render.tileentity;

import com.hbm.blocks.BlockDummyable;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.oil.TileEntityMachineCatalyticReformer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderCatalyticReformer extends TileEntitySpecialRenderer<TileEntityMachineCatalyticReformer> {

    private static ResourceLocation extra = new ResourceLocation(RefStrings.MODID, "textures/models/horse/dyx.png");

    @Override
    public void render(TileEntityMachineCatalyticReformer tile, double x, double y, double z, float interp, int destroyStage, float alpha) {

        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y, z + 0.5);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);

        switch(tile.getBlockMetadata() - BlockDummyable.offset) {
            case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
            case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
            case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
            case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
        }

        GL11.glShadeModel(GL11.GL_SMOOTH);
        bindTexture(ResourceManager.catalytic_reformer_tex);
        ResourceManager.catalytic_reformer.renderAll();
        GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
    }
}
