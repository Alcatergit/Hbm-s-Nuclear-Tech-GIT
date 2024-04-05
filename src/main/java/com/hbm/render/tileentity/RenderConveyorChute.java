package com.hbm.render.tileentity;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.network.TileEntityConveyorChute;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class RenderConveyorChute<T extends TileEntityConveyorChute> extends TileEntitySpecialRenderer<T> {
    @Override
    public void render(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if(te.getBlockType() == ModBlocks.conveyor_chute)
            return;
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_SMOOTH);

        boolean pX = te.connections[3] != null;
        boolean nX = te.connections[5] != null;
        boolean pY = te.connections[0] != null;
        boolean nY = te.connections[1] != null;
        boolean pZ = te.connections[4] != null;
        boolean nZ = te.connections[2] != null;

        int mask = 0 + (pX ? 32 : 0) + (nX ? 16 : 0) + (pY ? 8 : 0) + (nY ? 4 : 0) + (pZ ? 2 : 0) + (nZ ? 1 : 0);

        GL11.glTranslated(x + 0.5F, y + 0.5F, z + 0.5F);

        if(te instanceof TileEntityConveyorChute){
        }
        if(mask == 0) {
            bindTexture(ResourceManager.cvr4_tex);
            ResourceManager.conveyor_chute.renderPart("o1");
            ResourceManager.conveyor_chute.renderPart("o2");
            ResourceManager.conveyor_chute.renderPart("o3");
            ResourceManager.conveyor_chute.renderPart("o4");
            bindTexture(ResourceManager.cvr2_tex);
            ResourceManager.conveyor_chute.renderPart("cvr");
            ResourceManager.conveyor_chute.renderPart("cvr1");
            ResourceManager.conveyor_chute.renderPart("cvr2");
            bindTexture(ResourceManager.cvr1_tex);
            ResourceManager.conveyor_chute.renderPart("r1");
            ResourceManager.conveyor_chute.renderPart("r2");
            ResourceManager.conveyor_chute.renderPart("r3");
            ResourceManager.conveyor_chute.renderPart("r4");
        } else if(mask == 0b100000 || mask == 0b010000) {
            bindTexture(ResourceManager.cvr1_tex);
            ResourceManager.conveyor_chute.renderPart("r1");
            ResourceManager.conveyor_chute.renderPart("r2");
        } else if(mask == 0b001000 || mask == 0b000100) {
            bindTexture(ResourceManager.cvr1_tex);
            ResourceManager.conveyor_chute.renderPart("r3");
            ResourceManager.conveyor_chute.renderPart("r4");
        } else if(mask == 0b000010 || mask == 0b000001) {
            bindTexture(ResourceManager.cvr1_tex);
            ResourceManager.conveyor_chute.renderPart("r1");
            ResourceManager.conveyor_chute.renderPart("r3");
        } else {

            if(pX) bindTexture(ResourceManager.cvr1_tex); ResourceManager.conveyor_chute.renderPart("r1");
            if(nX) bindTexture(ResourceManager.cvr1_tex); ResourceManager.conveyor_chute.renderPart("r2");
            if(pY) bindTexture(ResourceManager.cvr1_tex); ResourceManager.conveyor_chute.renderPart("r3");
            if(nY) bindTexture(ResourceManager.cvr1_tex); ResourceManager.conveyor_chute.renderPart("r4");
            if(pZ) bindTexture(ResourceManager.cvr4_tex); ResourceManager.conveyor_chute.renderPart("o1");
            if(nZ) bindTexture(ResourceManager.cvr4_tex); ResourceManager.conveyor_chute.renderPart("o2");

        }
        GL11.glEnable(GL11.GL_FLAT);
        GL11.glTranslated(-x - 0.5F, -y - 0.5F, -z - 0.5F);
        GL11.glPopMatrix();
    }
}
