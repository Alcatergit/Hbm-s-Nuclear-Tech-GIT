package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineTurbofan;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderTurbofan extends TileEntitySpecialRenderer<TileEntityMachineTurbofan> {

	@Override
	public void render(TileEntityMachineTurbofan te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
		GL11.glRotatef(180, 0F, 1F, 0F);
		GL11.glRotatef(270, 0F, 1F, 0F);
		switch(te.getBlockMetadata())
		{
		case 2:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(-90, 0F, 1F, 0F); break;
		}

        bindTexture(ResourceManager.turbofan_body_tex);
        
        ResourceManager.turbofan_body.renderAll();

        GlStateManager.enableCull();
        GL11.glPopMatrix();
        
        renderTileEntityAt2(te, x, y, z, partialTicks);
    }
    
	public void renderTileEntityAt2(TileEntityMachineTurbofan tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 1.5D, z + 0.5D);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
		GL11.glRotatef(180, 0F, 1F, 0F);
		GL11.glRotatef(270, 0F, 1F, 0F);
		switch(tileEntity.getBlockMetadata())
		{
		case 2:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(-90, 0F, 1F, 0F); break;
		}
		
		GL11.glRotatef(tileEntity.spin, 0F, 0F, -1F);

        bindTexture(ResourceManager.turbofan_blades_tex);
        ResourceManager.turbofan_blades.renderAll();

        GlStateManager.enableCull();
        GL11.glPopMatrix();
	}
}
