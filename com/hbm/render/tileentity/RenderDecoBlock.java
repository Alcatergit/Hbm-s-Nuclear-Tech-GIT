package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ClientProxy;
import com.hbm.main.ResourceManager;
import com.hbm.render.RenderHelper;
import com.hbm.tileentity.deco.TileEntityDecoBlock;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class RenderDecoBlock extends TileEntitySpecialRenderer<TileEntityDecoBlock> {

	@Override
	public void render(TileEntityDecoBlock te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotatef(180, 0F, 0F, 1F);
		
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glTranslatef(0, 0, -1.5F);
    	GL11.glRotated(90, 1, 0, 0);

        GL11.glDisable(GL11.GL_CULL_FACE);
    	bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
    	//ResourceManager.boxcar.renderAll();
    	RenderHelper.renderAll(ClientProxy.boxcar);
    	
        GL11.glEnable(GL11.GL_CULL_FACE);
        
        GL11.glPopAttrib();
        GL11.glPopMatrix();
	}
}
