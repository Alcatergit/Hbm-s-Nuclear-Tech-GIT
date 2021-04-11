package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.render.RenderHelper;
import com.hbm.tileentity.machine.TileEntitySoyuzStruct;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderSoyuzMultiblock extends TileEntitySpecialRenderer<TileEntitySoyuzStruct> {
	
	final float pixel = 1F/16F;
	
	public static TextureAtlasSprite[] blockIcons = new TextureAtlasSprite[]{null, null, null};
	
	@Override
	public boolean isGlobalRenderer(TileEntitySoyuzStruct te) {
		return true;
	}
	
	@Override
	public void render(TileEntitySoyuzStruct te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		
		GL11.glTranslatef((float)x, (float)y, (float)z);

		GlStateManager.enableBlend();
		GlStateManager.enableCull();
        GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
        GlStateManager.disableAlpha();
        GlStateManager.disableLighting();

        RenderHelper.bindBlockTexture();
        RenderHelper.startDrawingTexturedQuads();
        
		TextureAtlasSprite loc;

		loc = blockIcons[0];

		for(int i = -6; i <= 6; i++)
			for(int j = 3; j <= 4; j++)
				for(int k = -6; k <= 6; k++)
					renderSmolBlockAt(loc, i, j, k);

		for(int i = -1; i <= 1; i++)
			for(int j = 3; j <= 4; j++)
				for(int k = -8; k <= -7; k++)
					renderSmolBlockAt(loc, i, j, k);

		for(int i = -2; i <= 2; i++)
			for(int j = 3; j <= 4; j++)
				for(int k = 7; k <= 9; k++)
					renderSmolBlockAt(loc, i, j, k);

		for(int i = -2; i <= 2; i++)
			for(int k = 5; k <= 9; k++)
				renderSmolBlockAt(loc, i, 51, k);

		for(int i = -1; i <= 1; i++)
			for(int k = -8; k <= -6; k++)
				renderSmolBlockAt(loc, i, 38, k);

		loc = blockIcons[1];

		for(int i = 3; i <= 6; i++)
			for(int j = 0; j <= 2; j++)
				for(int k = 3; k <= 6; k++)
					renderSmolBlockAt(loc, i, j, k);

		for(int i = -6; i <= -3; i++)
			for(int j = 0; j <= 2; j++)
				for(int k = 3; k <= 6; k++)
					renderSmolBlockAt(loc, i, j, k);

		for(int i = -6; i <= -3; i++)
			for(int j = 0; j <= 2; j++)
				for(int k = -6; k <= -3; k++)
					renderSmolBlockAt(loc, i, j, k);

		for(int i = 3; i <= 6; i++)
			for(int j = 0; j <= 2; j++)
				for(int k = -6; k <= -3; k++)
					renderSmolBlockAt(loc, i, j, k);

		for(int i = -1; i <= 1; i++)
			for(int j = 0; j <= 2; j++)
				for(int k = -8; k <= -6; k++)
					renderSmolBlockAt(loc, i, j, k);

		for(int i = -2; i <= 2; i++)
			for(int j = 0; j <= 2; j++)
				for(int k = 5; k <= 9; k++)
					renderSmolBlockAt(loc, i, j, k);

		loc = blockIcons[2];

		for(int i = -1; i <= 1; i++)
			for(int j = 5; j <= 50; j++)
				for(int k = 6; k <= 8; k++)
					renderSmolBlockAt(loc, i, j, k);

		for(int j = 5; j <= 37; j++)
			renderSmolBlockAt(loc, 0, j, -7);

		RenderHelper.draw();
		
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableLighting();

		GL11.glPopMatrix();
	}
	
	public void renderSmolBlockAt(TextureAtlasSprite loc1, int x, int y, int z) {
		// GL11.glTranslatef(x, y, z);
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc1.getMaxU(), loc1.getMinV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc1.getMinU(), loc1.getMinV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc1.getMinU(), loc1.getMaxV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc1.getMaxU(), loc1.getMaxV());
		
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc1.getMaxU(), loc1.getMinV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc1.getMinU(), loc1.getMinV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc1.getMinU(), loc1.getMaxV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc1.getMaxU(), loc1.getMaxV());

		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc1.getMaxU(), loc1.getMinV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc1.getMinU(), loc1.getMinV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc1.getMinU(), loc1.getMaxV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc1.getMaxU(), loc1.getMaxV());

		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc1.getMaxU(), loc1.getMinV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc1.getMinU(), loc1.getMinV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc1.getMinU(), loc1.getMaxV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc1.getMaxU(), loc1.getMaxV());

		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc1.getMaxU(), loc1.getMinV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, loc1.getMinU(), loc1.getMinV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc1.getMinU(), loc1.getMaxV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc1.getMaxU(), loc1.getMaxV());

		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc1.getMaxU(), loc1.getMinV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, loc1.getMinU(), loc1.getMinV());
		RenderHelper.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc1.getMinU(), loc1.getMaxV());
		RenderHelper.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, loc1.getMaxU(), loc1.getMaxV());

	}
}
