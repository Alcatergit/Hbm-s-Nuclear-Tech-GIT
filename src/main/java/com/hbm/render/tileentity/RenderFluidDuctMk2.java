package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.forgefluid.FFUtils;
import com.hbm.main.ResourceManager;
import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.conductor.TileEntityFFDuctBaseMk2;
import com.hbm.tileentity.conductor.TileEntityFFFluidSuccMk2;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;

public class RenderFluidDuctMk2<T extends TileEntityFFDuctBaseMk2> extends TileEntitySpecialRenderer<T> {
	
	@Override
	public void render(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if(te.getBlockType() == ModBlocks.fluid_duct_solid)
			return;
		GL11.glPushMatrix();
		GlStateManager.enableLighting();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);

		boolean pX = te.connections[3] != null;
		boolean nX = te.connections[5] != null;
		boolean pY = te.connections[0] != null;
		boolean nY = te.connections[1] != null;
		boolean pZ = te.connections[4] != null;
		boolean nZ = te.connections[2] != null;
		
		int mask = 0 + (pX ? 32 : 0) + (nX ? 16 : 0) + (pY ? 8 : 0) + (nY ? 4 : 0) + (pZ ? 2 : 0) + (nZ ? 1 : 0);
		
		GL11.glTranslated(x + 0.5F, y + 0.5F, z + 0.5F);
		
		if(te instanceof TileEntityFFDuctBaseMk2){
			if(te.getType() != null){
				FFUtils.setRGBFromHex(ModForgeFluids.getFluidColor(te.getType()));
			}
		}
		if(te instanceof TileEntityFFFluidSuccMk2){
			bindTexture(ResourceManager.pipe_neo_succ_tex);
		} else {
			bindTexture(ResourceManager.pipe_neo_tex);
		}
		if(mask == 0) {
			ResourceManager.pipe_neo.renderPart("pX");
			ResourceManager.pipe_neo.renderPart("nX");
			ResourceManager.pipe_neo.renderPart("pY");
			ResourceManager.pipe_neo.renderPart("nY");
			ResourceManager.pipe_neo.renderPart("pZ");
			ResourceManager.pipe_neo.renderPart("nZ");
		} else if(mask == 0b100000 || mask == 0b010000) {
			ResourceManager.pipe_neo.renderPart("pX");
			ResourceManager.pipe_neo.renderPart("nX");
		} else if(mask == 0b001000 || mask == 0b000100) {
			ResourceManager.pipe_neo.renderPart("pY");
			ResourceManager.pipe_neo.renderPart("nY");
		} else if(mask == 0b000010 || mask == 0b000001) {
			ResourceManager.pipe_neo.renderPart("pZ");
			ResourceManager.pipe_neo.renderPart("nZ");
		} else {
	
			if(pX) ResourceManager.pipe_neo.renderPart("pX");
			if(nX) ResourceManager.pipe_neo.renderPart("nX");
			if(pY) ResourceManager.pipe_neo.renderPart("pY");
			if(nY) ResourceManager.pipe_neo.renderPart("nY");
			if(pZ) ResourceManager.pipe_neo.renderPart("nZ");
			if(nZ) ResourceManager.pipe_neo.renderPart("pZ");
	
			if(!pX && !pY && !pZ) ResourceManager.pipe_neo.renderPart("ppn");
			if(!pX && !pY && !nZ) ResourceManager.pipe_neo.renderPart("ppp");
			if(!nX && !pY && !pZ) ResourceManager.pipe_neo.renderPart("npn");
			if(!nX && !pY && !nZ) ResourceManager.pipe_neo.renderPart("npp");
			if(!pX && !nY && !pZ) ResourceManager.pipe_neo.renderPart("pnn");
			if(!pX && !nY && !nZ) ResourceManager.pipe_neo.renderPart("pnp");
			if(!nX && !nY && !pZ) ResourceManager.pipe_neo.renderPart("nnn");
			if(!nX && !nY && !nZ) ResourceManager.pipe_neo.renderPart("nnp");

		}
		GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.color(1, 1, 1, 1);
		GL11.glTranslated(-x - 0.5F, -y - 0.5F, -z - 0.5F);
		GL11.glPopMatrix();
	}	
}