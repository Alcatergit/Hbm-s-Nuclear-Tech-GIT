package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.blocks.machine.rbmk.RBMKRod;
import com.hbm.items.machine.ItemRBMKRod;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.WavefrontObject;
import com.hbm.tileentity.machine.rbmk.RBMKDials;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBase;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBoiler;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKHeater;
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
	private static final ResourceLocation texture_rods = new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_element_colorable.png");
	
	@Override
	public boolean isGlobalRenderer(TileEntityRBMKBase te){
		return true;
	}
	
	@Override
	public void render(TileEntityRBMKBase control, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
		boolean hasRod = false;
		boolean cherenkov = false;
		float fuelR = 0F;
		float fuelG = 0F;
		float fuelB = 0F;

		float cherenkovR = 0F;
		float cherenkovG = 0F;
		float cherenkovB = 0F;
		float cherenkovA = 0.1F;
		
		if(control instanceof TileEntityRBMKRod) {
			
			TileEntityRBMKRod rod = (TileEntityRBMKRod) control;
			
			if(rod.hasRod){
				hasRod = true;
				fuelR = rod.fuelR;
				fuelG = rod.fuelG;
				fuelB = rod.fuelB;
				cherenkovR = rod.cherenkovR;
				cherenkovG = rod.cherenkovG;
				cherenkovB = rod.cherenkovB;
			}

			if(rod.fluxFast + rod.fluxSlow > 5){
				cherenkov = true;
				cherenkovA = (float)Math.max(0.25F, Math.log(rod.fluxFast + rod.fluxSlow)*0.01);
			}
		}

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		if(!(control.getBlockType() instanceof RBMKBase))
			return;

		RBMKBase block = (RBMKBase)control.getBlockType();
		WavefrontObject columnModel = ResourceManager.rbmk_reflector;
		if(block == ModBlocks.rbmk_boiler || block == ModBlocks.rbmk_heater)
			columnModel = ResourceManager.rbmk_rods;
		else if(block instanceof RBMKRod)
			columnModel = ResourceManager.rbmk_element;

		bindTexture(block.columnTexture);
		com.hbm.render.amlfrom1710.Tessellator tes = com.hbm.render.amlfrom1710.Tessellator.instance;
		tes.startDrawing(GL11.GL_TRIANGLES);
		boolean doJump = control.jumpheight > 0;
		
		columnModel.tessellatePartSplit(tes, "Column", 0.5F, (float)control.jumpheight+TileEntityRBMKBase.rbmkHeight);
			
		tes.draw();
		
		
		int offset = TileEntityRBMKBase.rbmkHeight;
		
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

			if(doJump){
				GL11.glTranslated(0, control.jumpheight, 0);
			}
			
			if((control instanceof TileEntityRBMKBoiler || control instanceof TileEntityRBMKHeater) && meta != RBMKBase.DIR_GLASS_LID.ordinal())
				ResourceManager.rbmk_rods.renderPart("Lid");
			ResourceManager.rbmk_element.renderPart("Lid");
			GL11.glPopMatrix();
		}
		
		if(hasRod) {
			GL11.glPushMatrix();
			GlStateManager.color(fuelR, fuelG, fuelB, 1);
			bindTexture(texture_rods);
			
			com.hbm.render.amlfrom1710.Tessellator tesss = com.hbm.render.amlfrom1710.Tessellator.instance;
			tes.startDrawing(GL11.GL_TRIANGLES);
			
			ResourceManager.rbmk_element.tessellatePartSplit(tesss, "Rods", 0.5F, offset);
			tesss.draw();
			GlStateManager.color(1, 1, 1, 1);
			GL11.glPopMatrix();
		}
		
		if(cherenkov) {
			
			Tessellator tess = Tessellator.getInstance();
			BufferBuilder buf = tess.getBuffer();
			GL11.glTranslated(0, 0.75, 0);

			GlStateManager.disableCull();
			GlStateManager.disableLighting();
			GlStateManager.enableBlend();
			GlStateManager.disableTexture2D();
			GlStateManager.disableAlpha();
			GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
			
			buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
			
			for(double j = 0; j <= offset; j += 0.25) {
				buf.pos(-0.5, j, -0.5).color(cherenkovR, cherenkovG, cherenkovB, cherenkovA).endVertex();
				buf.pos(-0.5, j, 0.5).color(cherenkovR, cherenkovG, cherenkovB, cherenkovA).endVertex();
				buf.pos(0.5, j, 0.5).color(cherenkovR, cherenkovG, cherenkovB, cherenkovA).endVertex();
				buf.pos(0.5, j, -0.5).color(cherenkovR, cherenkovG, cherenkovB, cherenkovA).endVertex();
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
