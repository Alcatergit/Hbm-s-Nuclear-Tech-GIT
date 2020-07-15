package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.render.RenderHelper;
import com.hbm.render.util.IconUtil;
import com.hbm.render.util.SmallBlockPronter;
import com.hbm.tileentity.machine.TileEntityITERStruct;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderITERMultiblock extends TileEntitySpecialRenderer<TileEntityITERStruct> {

	@Override
	public boolean isGlobalRenderer(TileEntityITERStruct te) {
		return true;
	}

	@Override
	public void render(TileEntityITERStruct te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();

		GL11.glTranslatef((float)x-1, (float)y, (float)z);

		GlStateManager.enableBlend();
		GlStateManager.disableLighting();
		GlStateManager.enableCull();
		GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
        GlStateManager.disableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(false);

		/*GlStateManager.shadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.iter_glass);
		ResourceManager.iter.renderPart("Windows");
		bindTexture(ResourceManager.iter_motor);
		ResourceManager.iter.renderPart("Motors");
		bindTexture(ResourceManager.iter_rails);
		ResourceManager.iter.renderPart("Rails");
		bindTexture(ResourceManager.iter_toroidal);
		ResourceManager.iter.renderPart("Toroidal");
		bindTexture(ResourceManager.iter_torus);
		ResourceManager.iter.renderPart("Torus");

		GL11.glPushMatrix();
		GL11.glRotated(System.currentTimeMillis() / 5D % 360, 0, 1, 0);
		bindTexture(ResourceManager.iter_solenoid);
		ResourceManager.iter.renderPart("Solenoid");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glRotated(System.currentTimeMillis() / 50D % 360, 0, 1, 0);
		GlStateManager.disableLighting();
		bindTexture(ResourceManager.iter_plasma);
		ResourceManager.iter.renderPart("Plasma");
		GlStateManager.shadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();*/
        
        TextureAtlasSprite magnet = RenderStructureMarker.fusion[0][1];
        TextureAtlasSprite solenoid = RenderStructureMarker.fusion[4][1];
        TextureAtlasSprite motor = RenderStructureMarker.fusion[3][0];
        TextureAtlasSprite glass = IconUtil.getTextureFromBlock(ModBlocks.reinforced_glass);
        
        TextureAtlasSprite active = magnet;
        
        RenderHelper.bindBlockTexture();
        RenderHelper.startDrawingTexturedQuads();

        int[][][] layout = TileEntityITERStruct.layout;

        for(int iy = -2; iy <= 2; iy ++) {

        	int iny = 2 - Math.abs(iy);

	        for(int ix = 0; ix < layout[0].length; ix++) {

	            for(int iz = 0; iz < layout[0][0].length; iz++) {

	            	int block = layout[iny][ix][iz];

	            	switch(block) {
	            	case 0: continue;
	            	case 1: active = magnet; break;
	            	case 2: active = solenoid; break;
	            	case 3: active = motor; break;
	            	case 4: active = glass; break;
	            	}

	            	SmallBlockPronter.renderSmolBlockAt(active, ix - 6F, iy + 3, iz - 7F);
	            }
	        }
        }

        RenderHelper.draw();
        
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.depthMask(true);

		GlStateManager.enableLighting();
		GL11.glPopMatrix();
	}
}
