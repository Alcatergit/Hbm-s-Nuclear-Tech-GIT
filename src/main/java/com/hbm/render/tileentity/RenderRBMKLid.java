package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.blocks.machine.rbmk.RBMKRod;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.tileentity.machine.rbmk.RBMKDials;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBase;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBoiler;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKRod;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class RenderRBMKLid extends TileEntitySpecialRenderer<TileEntityRBMKBase> {

	private ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_blank.png");
	private ResourceLocation texture_glass = new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_blank_glass.png");
	private static final ResourceLocation texture_rods = new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_element.png");
	
	@Override
	public boolean isGlobalRenderer(TileEntityRBMKBase te){
		return true;
	}
	
	@Override
	public void render(TileEntityRBMKBase control, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
		boolean hasRod = false;
		boolean cherenkov = false;
		
		if(control instanceof TileEntityRBMKRod) {
			
			TileEntityRBMKRod rod = (TileEntityRBMKRod) control;
			
			if(rod.hasRod)
				hasRod = true;
			
			if(rod.fluxFast + rod.fluxSlow > 5)
				cherenkov = true;
		}

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		Tessellator tess = Tessellator.getInstance();
		BufferBuilder buf = tess.getBuffer();
		
		RBMKBase block = (RBMKBase)control.getBlockType();
		IModelCustom columnModel = ResourceManager.rbmk_reflector;
		if(block == ModBlocks.rbmk_boiler)
			columnModel = ResourceManager.rbmk_rods;
		else if(block instanceof RBMKRod)
			columnModel = ResourceManager.rbmk_element;
		bindTexture(block.columnTexture);
		com.hbm.render.amlfrom1710.Tessellator tes = com.hbm.render.amlfrom1710.Tessellator.instance;
		tes.startDrawing(GL11.GL_TRIANGLES);
		for(int i = 0; i < TileEntityRBMKBase.rbmkHeight+1; i ++){
			columnModel.tessellatePart(tes, "Column");
			tes.addTranslation(0, 1, 0);
		}
		tes.draw();
		
		
		int offset = 1;
		
		for(int o = 1; o < 16; o++) {
			
			if(control.getWorld().getBlockState(new BlockPos(control.getPos().getX(), control.getPos().getY() + o, control.getPos().getZ())).getBlock() == control.getBlockType()) {
				offset = o;
				
				int meta = control.getWorld().getBlockState(new BlockPos(control.getPos().getX(), control.getPos().getY() + o, control.getPos().getZ())).getValue(BlockDummyable.META);
				
				if(meta > 5 && meta < 12)
					break;
				
			} else {
				break;
			}
		}
		
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		
		if(control.hasLid()) {
			GL11.glPushMatrix();
			GL11.glTranslated(0, offset, 0);
			
			int meta = control.getBlockMetadata() - RBMKBase.offset;
			
			if(meta == RBMKBase.DIR_GLASS_LID.ordinal()) {
				bindTexture(texture_glass);
			} else {
				
				if(control.getBlockType() instanceof RBMKBase) {
					bindTexture(((RBMKBase)control.getBlockType()).coverTexture);
				} else {
					bindTexture(texture);
				}
				cherenkov = false;
			}
			
			if(control instanceof TileEntityRBMKBoiler && meta != RBMKBase.DIR_GLASS_LID.ordinal())
				ResourceManager.rbmk_rods.renderPart("Lid");
			ResourceManager.rbmk_element.renderPart("Lid");
			GL11.glPopMatrix();
		}
		
		if(hasRod) {

			GL11.glPushMatrix();
			bindTexture(texture_rods);
			
			for(int j = 0; j <= offset; j++) {

				ResourceManager.rbmk_element.renderPart("Rods");
				GL11.glTranslated(0, 1, 0);
			}
			GL11.glPopMatrix();
		}
		
		if(cherenkov) {
			
			GL11.glTranslated(0, 0.75, 0);

			GlStateManager.disableCull();
			GlStateManager.disableLighting();
			GlStateManager.enableBlend();
			GlStateManager.disableTexture2D();
			GlStateManager.disableAlpha();
			GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
			
			buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
			
			for(double j = 0; j <= offset; j += 0.25) {
				buf.pos(-0.5, j, -0.5).color(0.4F, 0.9F, 1.0F, 0.1F).endVertex();
				buf.pos(-0.5, j, 0.5).color(0.4F, 0.9F, 1.0F, 0.1F).endVertex();
				buf.pos(0.5, j, 0.5).color(0.4F, 0.9F, 1.0F, 0.1F).endVertex();
				buf.pos(0.5, j, -0.5).color(0.4F, 0.9F, 1.0F, 0.1F).endVertex();
			}
			tess.draw();

			GlStateManager.enableAlpha();
			GlStateManager.enableTexture2D();
			GlStateManager.disableBlend();
			GlStateManager.enableLighting();
			GlStateManager.enableCull();
		}

		GL11.glPopMatrix();
	}
}
