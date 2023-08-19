package com.hbm.render.tileentity;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.items.machine.ItemFELCrystal.EnumWavelengths;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityFEL;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.render.misc.BeamPronter;
import com.hbm.render.misc.BeamPronter.EnumBeamType;
import com.hbm.render.misc.BeamPronter.EnumWaveType;

public class RenderFEL extends TileEntitySpecialRenderer<TileEntityFEL> {

	@Override
	public boolean isGlobalRenderer(TileEntityFEL fel) {
		return true;
	}

	@Override
	public void render(TileEntityFEL fel, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GlStateManager.enableLighting();
        GlStateManager.disableCull();
		
		switch(fel.getBlockMetadata() - BlockDummyable.offset) {
		case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		bindTexture(ResourceManager.fel_tex);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		ResourceManager.fel.renderAll();
		GlStateManager.shadeModel(GL11.GL_FLAT);

		int color = 0xffffff;
		
		if(fel.mode.renderedBeamColor == 0) {
			color = Color.HSBtoRGB(fel.getWorld().getTotalWorldTime() / 2000.0F, 0.5F, 0.1F) & 16777215;
		} else {
			color = fel.mode.renderedBeamColor;
		}
		int length = fel.distance - 3;
		GL11.glTranslated(0, 1.5, -1.5);
		if(fel.power > fel.powerReq * Math.pow(4, fel.mode.ordinal()) && fel.isOn && !(fel.mode == EnumWavelengths.NULL) && length > 0) {
			BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, -length - 1), EnumWaveType.STRAIGHT, EnumBeamType.SOLID, color, 0xFFFFFF, 0, 1, 0, 3, 0.0625F);
		}

		GL11.glPopMatrix();
	}
}