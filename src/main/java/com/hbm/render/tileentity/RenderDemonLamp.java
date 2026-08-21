package com.hbm.render.tileentity;

import net.minecraft.block.state.IBlockState;
import org.lwjgl.opengl.GL11;

import com.hbm.hfr.render.loader.HFRWavefrontObject;
import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.tileentity.machine.TileEntityDemonLamp;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class RenderDemonLamp extends TileEntitySpecialRenderer<TileEntityDemonLamp> {

	public static final IModelCustom demon_lamp = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/blocks/demon_lamp.obj"));
	public static final ResourceLocation tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/demon_lamp.png");

	@Override
	public boolean isGlobalRenderer(TileEntityDemonLamp te){
		return true;
	}

	@Override
	public void render(TileEntityDemonLamp te, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);

		// ==================== Modification: Rotate rendering based on the block's orientation ====================
		EnumFacing facing = EnumFacing.NORTH; // Default facing north
		if(te != null && te.getWorld() != null && te.getPos() != null) {
			IBlockState state = te.getWorld().getBlockState(te.getPos());
			if(state.getBlock() instanceof com.hbm.blocks.machine.DemonLamp) {
				facing = state.getValue(com.hbm.blocks.machine.DemonLamp.FACING);
			}
		}

		// According to the orientation rotation model and rays
		switch(facing) {
			case SOUTH: GL11.glRotatef(180F, 0F, 1F, 0F); break;
			case WEST: GL11.glRotatef(90F, 0F, 1F, 0F); break;
			case EAST: GL11.glRotatef(270F, 0F, 1F, 0F); break;
			case NORTH:
			default: break; // The north does not need to rotate.
		}

		GlStateManager.enableLighting();
		GlStateManager.enableCull();

		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		bindTexture(tex);
		demon_lamp.renderAll();

		Tessellator tess = Tessellator.getInstance();
		BufferBuilder buf = tess.getBuffer();
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

		// ==================== Modification: Change the initial direction of the rays from east to north ====================
		Vec3 vec = Vec3.createVectorHelper(0, 0, -1); // Change to face north (negative Z direction)

		GlStateManager.depthMask(false);
		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);

		double near = 0.375D;
		double far = 15D;
		//whereeeeeeever you are

		for(int j = 0; j < 2; j++) {

			double h = 0.5;
			double height = j == 0 ? -h : h;

			for(int i = 0; i < 16; i++) {

				buf.pos(vec.xCoord * near, 0.5D + j * 0.125D, vec.zCoord * near).color(0F, 0.75F, 1F, 0.25F).endVertex();
				buf.pos(vec.xCoord * far, 0.5D + j * 0.125D + height, vec.zCoord * far).color(0F, 0.75F, 1F, 0F).endVertex();

				vec.rotateAroundY((float)Math.PI * 2F / 16F);

				buf.pos(vec.xCoord * far, 0.5D + j * 0.125D + height, vec.zCoord * far).color(0F, 0.75F, 1F, 0F).endVertex();
				buf.pos(vec.xCoord * near, 0.5D + j * 0.125D, vec.zCoord * near).color(0F, 0.75F, 1F, 0.25F).endVertex();
			}
		}
		tess.draw();

		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		GlStateManager.disableBlend();
		GlStateManager.enableCull();
		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
		GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.depthMask(true);

		GL11.glPopMatrix();
	}
}
