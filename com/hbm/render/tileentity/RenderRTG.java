package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.Library;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineRTG;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderRTG extends TileEntitySpecialRenderer<TileEntity> {

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
		GL11.glRotatef(180, 0F, 1F, 0F);

        if(te.getBlockType() == ModBlocks.machine_rtg_grey){
            bindTexture(ResourceManager.rtg_tex);
            ResourceManager.rtg.renderAll();
        } else {
            bindTexture(ResourceManager.rtg_cell_tex);
        }

        int ix = te.getPos().getX();
        int iy = te.getPos().getY();
        int iz = te.getPos().getZ();

        if(Library.checkCableConnectables(te.getWorld(), ix + 1, iy, iz))
            ResourceManager.rtg_connector.renderAll();

        if(Library.checkCableConnectables(te.getWorld(), ix - 1, iy, iz)) {
    		GL11.glRotatef(180, 0F, 1F, 0F);
    		ResourceManager.rtg_connector.renderAll();
    		GL11.glRotatef(-180, 0F, 1F, 0F);
        }

        if(Library.checkCableConnectables(te.getWorld(), ix, iy, iz - 1)) {
    		GL11.glRotatef(90, 0F, 1F, 0F);
    		ResourceManager.rtg_connector.renderAll();
    		GL11.glRotatef(-90, 0F, 1F, 0F);
        }

        if(Library.checkCableConnectables(te.getWorld(), ix, iy, iz + 1)) {
    		GL11.glRotatef(-90, 0F, 1F, 0F);
    		ResourceManager.rtg_connector.renderAll();
    		GL11.glRotatef(90, 0F, 1F, 0F);
        }

        GL11.glPopMatrix();
	}
}
