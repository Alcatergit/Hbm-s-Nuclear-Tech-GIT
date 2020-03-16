package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ClientProxy;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.deco.TileEntityDecoBlock;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderDecoBlock extends TileEntitySpecialRenderer<TileEntityDecoBlock> {

	@Override
	public boolean isGlobalRenderer(TileEntityDecoBlock te) {
		return te.getWorld().getBlockState(te.getPos()).getBlock() == ModBlocks.boxcar || te.getWorld().getBlockState(te.getPos()).getBlock() == ModBlocks.boat;
	}
	
	@Override
	public void render(TileEntityDecoBlock te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotatef(180, 0F, 0F, 1F);

		GlStateManager.enableLighting();
		if (te.getWorld().getBlockState(te.getPos()).getBlock() == ModBlocks.boxcar) {
			GL11.glTranslatef(0, 0, -1.5F);
			GL11.glRotated(90, 1, 0, 0);

			GL11.glDisable(GL11.GL_CULL_FACE);
			bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			// ResourceManager.boxcar.renderAll();
			//RenderHelper.renderAll(ClientProxy.boxcar);
			GL11.glCallList(ClientProxy.boxcarCalllist);

			GL11.glEnable(GL11.GL_CULL_FACE);
		}
		if (te.getWorld().getBlockState(te.getPos()).getBlock() == ModBlocks.boat) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glTranslatef(0, 0, -1.5F);
			GL11.glTranslatef(0, 0.5F, 0);

            GL11.glEnable(GL11.GL_CULL_FACE);
        	bindTexture(ResourceManager.duchessgambit_tex);
        	ResourceManager.duchessgambit.renderAll();
		}
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}
}
