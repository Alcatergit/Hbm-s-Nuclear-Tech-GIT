package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.render.RenderHelper;
import com.hbm.render.util.SmallBlockPronter;
import com.hbm.tileentity.machine.TileEntityPlasmaStruct;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderPlasmaMultiblock extends TileEntitySpecialRenderer<TileEntityPlasmaStruct> {

	@Override
	public boolean isGlobalRenderer(TileEntityPlasmaStruct te) {
		return true;
	}
	
	@Override
	public void render(TileEntityPlasmaStruct te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();

		GL11.glTranslatef((float)x + 0.5F, (float)y-1, (float)z + 0.5F);

		switch(te.getBlockMetadata()) {
		case 2: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(180, 0F, 1F, 0F); break;
		}

		GL11.glTranslatef(-1.5F, 0, -0.5F);

		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.enableCull();
        GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
        GlStateManager.disableAlpha();
        GlStateManager.depthMask(false);
		
		RenderHelper.bindBlockTexture();
		RenderHelper.startDrawingTexturedQuads();
		
        for(int iy = 1; iy < 6; iy ++) {

	        for(int ix = 0; ix < 10; ix++) {

	            for(int iz = -1; iz < 2; iz++) {

	            	if(iy == 5 && ix > 3)
	            		break;
	            	
	            	SmallBlockPronter.renderSmolBlockAt(RenderStructureMarker.fusion[1][1], ix, iy, iz);
	            }
	        }
        }
        
        for(int i = 10; i <= 11; i++)
            for(int j = 2; j <= 3; j++)
            	SmallBlockPronter.renderSmolBlockAt(RenderStructureMarker.fusion[1][1], i, j, 0);
        
        RenderHelper.draw();

		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.depthMask(true);

        GlStateManager.enableLighting();
		GL11.glPopMatrix();
	}
}
