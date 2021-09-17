package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.render.RenderHelper;
import com.hbm.tileentity.machine.TileEntityMultiblock;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderMultiblock extends TileEntitySpecialRenderer<TileEntityMultiblock> {

	public static final float pixel = 1F/16F;
	public static TextureAtlasSprite structLauncher;
	public static TextureAtlasSprite structScaffold;
	
	@Override
	public boolean isGlobalRenderer(TileEntityMultiblock te) {
		return true;
	}
	
	@Override
	public void render(TileEntityMultiblock te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		
		GL11.glTranslated(x, y, z);

		GlStateManager.enableBlend();
		GlStateManager.disableLighting();
		GlStateManager.enableCull();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
        GlStateManager.disableAlpha();
		
        Block b = te.getBlockType();
        RenderHelper.bindBlockTexture();
        
        RenderHelper.startDrawingTexturedQuads();
        
        if(b == ModBlocks.struct_launcher_core)
        	renderCompactLauncher();
        
        if(b == ModBlocks.struct_launcher_core_large)
        	renderLaunchTable();
        
        RenderHelper.draw();
        
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.enableAlpha();
		
		GL11.glPopMatrix();
	}
	
	private void renderCompactLauncher() {
		
		for(int i = -1; i <= 1; i++)
			for(int j = -1; j <= 1; j++)
				if(i != 0 || j != 0)
					renderSmolBlockAt(structLauncher, i, 0, j);
	}
	
	private void renderLaunchTable() {
		for(int i = -4; i <= 4; i++)
			for(int j = -4; j <= 4; j++)
				if(i != 0 || j != 0)
					renderSmolBlockAt(structLauncher, i, 0, j);
        
		switch((int)(System.currentTimeMillis() % 4000 / 1000)) {
		case 0:
			for(int k = 1; k < 12; k++)
				renderSmolBlockAt(structScaffold, 3, k, 0);
			break;
			
		case 1:
			for(int k = 1; k < 12; k++)
				renderSmolBlockAt(structScaffold, 0, k, 3);
			break;
			
		case 2:
			for(int k = 1; k < 12; k++)
				renderSmolBlockAt(structScaffold, -3, k, 0);
			break;
			
		case 3:
			for(int k = 1; k < 12; k++)
				renderSmolBlockAt(structScaffold, 0, k, -3);
			break;
		}
	}
	
	public void renderSmolBlockAt(TextureAtlasSprite loc, int x, int y, int z) {
		// GL11.glTranslatef(x, y, z);
		//GL11.glRotatef(180, 0F, 0F, 1F);
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMaxU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMinU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMinU(), loc.getMaxV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMaxU(), loc.getMaxV());

		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc.getMaxU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMinU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMinU(), loc.getMaxV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc.getMaxU(), loc.getMaxV());

		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc.getMaxU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc.getMinU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc.getMinU(), loc.getMaxV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc.getMaxU(), loc.getMaxV());

		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMaxU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc.getMinU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc.getMinU(), loc.getMaxV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMaxU(), loc.getMaxV());

		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc.getMaxU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc.getMinU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMinU(), loc.getMaxV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMaxU(), loc.getMaxV());

		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc.getMaxU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc.getMinU(), loc.getMinV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMinU(), loc.getMaxV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc.getMaxU(), loc.getMaxV());

	}
}
