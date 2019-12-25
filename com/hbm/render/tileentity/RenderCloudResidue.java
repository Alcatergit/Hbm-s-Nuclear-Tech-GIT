package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.tileentity.generic.TileEntityCloudResidue;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RenderCloudResidue extends TileEntitySpecialRenderer<TileEntityCloudResidue> {

	public RenderCloudResidue() {
		super();
	}

	@Override
	public void render(TileEntityCloudResidue te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buf = tessellator.getBuffer();
		World world = te.getWorld();
		GL11.glTranslated(x, y, z);
		boolean ceil = world.getBlockState(te.getPos().up()).isNormalCube();
		boolean floor = world.getBlockState(te.getPos().down()).isNormalCube();
		boolean side1 = world.getBlockState(te.getPos().south()).isNormalCube();
		boolean side2 = world.getBlockState(te.getPos().west()).isNormalCube();
		boolean side3 = world.getBlockState(te.getPos().north()).isNormalCube();
		boolean side4 = world.getBlockState(te.getPos().east()).isNormalCube();
		float f = 0;
		float f1 = 1;
		float f2 = 0;
		float f3 = 1;
		double d2 = 0.05000000074505806D;
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("hbm:textures/blocks/residue.png"));
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		float f6 = world.getLightBrightness(te.getPos());
		GL11.glDisable(GL11.GL_LIGHTING);
		if (side2) {

			buf.pos(0.0F + d2, 0.0F, 0.0D).tex(f, f3).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(0.0F + d2, 0.0F, 1.0D).tex(f1, f3).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(0.0F + d2, 1.0D, 1.0D).tex(f1, f2).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(0.0F + d2, 1.0D, 0.0D).tex(f, f2).color(f6, f6, f6, 1.0F).endVertex();

			buf.pos(0.0F + d2, 1.0F, 0.0D).tex(f, f2).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(0.0F + d2, 1.0F, 1.0D).tex(f1, f2).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(0.0F + d2, 0.0F, 1.0D).tex(f1, f3).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(0.0F + d2, 0.0F, 0.0D).tex(f, f3).color(f6, f6, f6, 1.0F).endVertex();

		}

		if (side4) {
			
			buf.pos(1.0F - d2, 0.0F, 0.0D).tex(f, f3).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(1.0F - d2, 0.0F, 1.0D).tex(f1, f3).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(1.0F - d2, 1.0D, 1.0D).tex(f1, f2).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(1.0F - d2, 1.0D, 0.0D).tex(f, f2).color(f6, f6, f6, 1.0F).endVertex();

			buf.pos(1.0F - d2, 1.0F, 0.0D).tex(f, f2).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(1.0F - d2, 1.0F, 1.0D).tex(f1, f2).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(1.0F - d2, 0.0F, 1.0D).tex(f1, f3).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(1.0F - d2, 0.0F, 0.0D).tex(f, f3).color(f6, f6, f6, 1.0F).endVertex();
		}

		if (side3) {

			buf.pos(0.0F, 0.0F, 0.0D + d2).tex(f, f3).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(1.0F, 0.0F, 0.0D + d2).tex(f1, f3).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(1.0F, 1.0D, 0.0D + d2).tex(f1, f2).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(0.0F, 1.0D, 0.0D + d2).tex(f, f2).color(f6, f6, f6, 1.0F).endVertex();

			buf.pos(0.0F, 1.0F, 0.0D + d2).tex(f, f2).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(1.0F, 1.0F, 0.0D + d2).tex(f1, f2).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(1.0F, 0.0F, 0.0D + d2).tex(f1, f3).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(0.0F, 0.0F, 0.0D + d2).tex(f, f3).color(f6, f6, f6, 1.0F).endVertex();
		}

		if (side1) {
			
			buf.pos(0.0F, 0.0F, 1.0D - d2).tex(f, f3).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(1.0F, 0.0F, 1.0D - d2).tex(f1, f3).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(1.0F, 1.0D, 1.0D - d2).tex(f1, f2).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(0.0F, 1.0D, 1.0D - d2).tex(f, f2).color(f6, f6, f6, 1.0F).endVertex();

			buf.pos(0.0F, 1.0F, 1.0D - d2).tex(f, f2).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(1.0F, 1.0F, 1.0D - d2).tex(f1, f2).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(1.0F, 0.0F, 1.0D - d2).tex(f1, f3).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(0.0F, 0.0F, 1.0D - d2).tex(f, f3).color(f6, f6, f6, 1.0F).endVertex();
		}

		if (ceil) {
			
			buf.pos(0.0F, 1.0F - d2, 0.0D).tex(f, f3).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(1.0F, 1.0F - d2, 0.0D).tex(f1, f3).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(1.0F, 1.0F - d2, 1.0D).tex(f1, f2).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(0.0F, 1.0F - d2, 1.0D).tex(f, f2).color(f6, f6, f6, 1.0F).endVertex();
			
			
		}

		if (floor) {
			
			buf.pos(0.0F, 0.0F + d2, 1.0D).tex(f, f2).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(1.0F, 0.0F + d2, 1.0D).tex(f1, f2).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(1.0F, 0.0F + d2, 0.0D).tex(f1, f3).color(f6, f6, f6, 1.0F).endVertex();
			buf.pos(0.0F, 0.0F + d2, 0.0D).tex(f, f3).color(f6, f6, f6, 1.0F).endVertex();
		}
		tessellator.draw();

		GL11.glPopMatrix();
	}
}
