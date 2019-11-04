package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.tileentity.generic.TileEntityTaint;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.world.World;

public class RenderTaint extends TileEntitySpecialRenderer<TileEntityTaint> {

	public RenderTaint() {
		super();
	}

	@Override
	public void render(TileEntityTaint te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		//TODO somehow fix TESR lag
		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buf = tessellator.getBuffer();
		World world = te.getWorld();
		this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		TextureAtlasSprite iicon = Minecraft.getMinecraft().getBlockRendererDispatcher()
				.getModelForState(world.getBlockState(te.getPos())).getParticleTexture();
		GL11.glTranslated(x, y, z);
		// System.out.println(x + " " + y + " " + z);
		boolean ceil = world.getBlockState(te.getPos().up()).isNormalCube();
		boolean floor = world.getBlockState(te.getPos().down()).isNormalCube();
		boolean side1 = world.getBlockState(te.getPos().south()).isNormalCube();
		boolean side2 = world.getBlockState(te.getPos().west()).isNormalCube();
		boolean side3 = world.getBlockState(te.getPos().north()).isNormalCube();
		boolean side4 = world.getBlockState(te.getPos().east()).isNormalCube();
		float f = iicon.getMinU();
		float f1 = iicon.getMaxU();
		float f2 = iicon.getMinV();
		float f3 = iicon.getMaxV();
		double d2 = 0.05000000074505806D;
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		world.getLightBrightness(te.getPos());
		GL11.glDisable(GL11.GL_LIGHTING);
		if (side2) {

			buf.pos(0.0F + d2, 0.0F, 0.0D).tex(f, f3).endVertex();
			buf.pos(0.0F + d2, 0.0F, 1.0D).tex(f1, f3).endVertex();
			buf.pos(0.0F + d2, 1.0D, 1.0D).tex(f1, f2).endVertex();
			buf.pos(0.0F + d2, 1.0D, 0.0D).tex(f, f2).endVertex();

			buf.pos(0.0F + d2, 1.0F, 0.0D).tex(f, f2).endVertex();
			buf.pos(0.0F + d2, 1.0F, 1.0D).tex(f1, f2).endVertex();
			buf.pos(0.0F + d2, 0.0F, 1.0D).tex(f1, f3).endVertex();
			buf.pos(0.0F + d2, 0.0F, 0.0D).tex(f, f3).endVertex();

		}

		if (side4) {
			
			buf.pos(1.0F - d2, 0.0F, 0.0D).tex(f, f3).endVertex();
			buf.pos(1.0F - d2, 0.0F, 1.0D).tex(f1, f3).endVertex();
			buf.pos(1.0F - d2, 1.0D, 1.0D).tex(f1, f2).endVertex();
			buf.pos(1.0F - d2, 1.0D, 0.0D).tex(f, f2).endVertex();

			buf.pos(1.0F - d2, 1.0F, 0.0D).tex(f, f2).endVertex();
			buf.pos(1.0F - d2, 1.0F, 1.0D).tex(f1, f2).endVertex();
			buf.pos(1.0F - d2, 0.0F, 1.0D).tex(f1, f3).endVertex();
			buf.pos(1.0F - d2, 0.0F, 0.0D).tex(f, f3).endVertex();
		}

		if (side3) {

			buf.pos(0.0F, 0.0F, 0.0D + d2).tex(f, f3).endVertex();
			buf.pos(1.0F, 0.0F, 0.0D + d2).tex(f1, f3).endVertex();
			buf.pos(1.0F, 1.0D, 0.0D + d2).tex(f1, f2).endVertex();
			buf.pos(0.0F, 1.0D, 0.0D + d2).tex(f, f2).endVertex();

			buf.pos(0.0F, 1.0F, 0.0D + d2).tex(f, f2).endVertex();
			buf.pos(1.0F, 1.0F, 0.0D + d2).tex(f1, f2).endVertex();
			buf.pos(1.0F, 0.0F, 0.0D + d2).tex(f1, f3).endVertex();
			buf.pos(0.0F, 0.0F, 0.0D + d2).tex(f, f3).endVertex();
		}

		if (side1) {
			
			buf.pos(0.0F, 0.0F, 1.0D - d2).tex(f, f3).endVertex();
			buf.pos(1.0F, 0.0F, 1.0D - d2).tex(f1, f3).endVertex();
			buf.pos(1.0F, 1.0D, 1.0D - d2).tex(f1, f2).endVertex();
			buf.pos(0.0F, 1.0D, 1.0D - d2).tex(f, f2).endVertex();

			buf.pos(0.0F, 1.0F, 1.0D - d2).tex(f, f2).endVertex();
			buf.pos(1.0F, 1.0F, 1.0D - d2).tex(f1, f2).endVertex();
			buf.pos(1.0F, 0.0F, 1.0D - d2).tex(f1, f3).endVertex();
			buf.pos(0.0F, 0.0F, 1.0D - d2).tex(f, f3).endVertex();
		}

		if (ceil) {
			
			buf.pos(0.0F, 1.0F - d2, 0.0D).tex(f, f3).endVertex();
			buf.pos(1.0F, 1.0F - d2, 0.0D).tex(f1, f3).endVertex();
			buf.pos(1.0F, 1.0F - d2, 1.0D).tex(f1, f2).endVertex();
			buf.pos(0.0F, 1.0F - d2, 1.0D).tex(f, f2).endVertex();
			
			
		}

		if (floor) {
			
			buf.pos(0.0F, 0.0F + d2, 1.0D).tex(f, f2).endVertex();
			buf.pos(1.0F, 0.0F + d2, 1.0D).tex(f1, f2).endVertex();
			buf.pos(1.0F, 0.0F + d2, 0.0D).tex(f1, f3).endVertex();
			buf.pos(0.0F, 0.0F + d2, 0.0D).tex(f, f3).endVertex();
		}
		tessellator.draw();

		GL11.glPopMatrix();
	}

}
