package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.FluidTypeHandler;
import com.hbm.forgefluid.FluidTypeHandler.FluidProperties;
import com.hbm.main.ResourceManager;
import com.hbm.render.misc.DiamondPronter;
import com.hbm.render.misc.EnumSymbol;
import com.hbm.tileentity.machine.TileEntityMachineBAT9000;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fluids.Fluid;

public class RenderBAT9000 extends TileEntitySpecialRenderer<TileEntityMachineBAT9000> {

	@Override
	public boolean isGlobalRenderer(TileEntityMachineBAT9000 te){
		return true;
	}

	@Override
	public void render(TileEntityMachineBAT9000 bat, double x, double y, double z, float partialTicks, int destroyStage, float alpha){

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GlStateManager.enableLighting();
		GlStateManager.disableCull();

		bindTexture(ResourceManager.bat9000_tex);

		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		ResourceManager.bat9000.renderAll();
		GlStateManager.shadeModel(GL11.GL_FLAT);

		Fluid type = null;
		if(bat.tank.getFluid() != null)
			type = bat.tank.getFluid().getFluid();

		if(type != null) {

			RenderHelper.disableStandardItemLighting();
			GL11.glPushMatrix();
			FluidProperties props = FluidTypeHandler.getProperties(type);
			int poison = props.poison;
			int flammability = props.flammability;
			int reactivity = props.reactivity;
			EnumSymbol symbol = props.symbol;

			GL11.glRotatef(45, 0, 1, 0);

			for(int j = 0; j < 4; j++) {

				GL11.glPushMatrix();
				GL11.glTranslated(2.5, 2.25, 0);
				GL11.glScalef(1.0F, 0.75F, 0.75F);
				DiamondPronter.pront(poison, flammability, reactivity, symbol);
				GL11.glPopMatrix();
				GL11.glRotatef(90, 0, 1, 0);
			}
			GL11.glPopMatrix();
			RenderHelper.enableStandardItemLighting();

			GlStateManager.disableCull();
			GlStateManager.disableLighting();
			FFUtils.setColorFromFluid(type);
			
			float scale = (float)bat.tank.getFluidAmount()/bat.tank.getCapacity();
			
			float lby = OpenGlHelper.lastBrightnessY;
			float lbx = OpenGlHelper.lastBrightnessX;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (int)(15*type.getLuminosity())+15, lby);
			
			TextureAtlasSprite sprite = FFUtils.getTextureFromFluid(type);
			float u = sprite.getMinU();
			float v = sprite.getMinV();
			float mU = sprite.getMaxU();
			float mV = sprite.getInterpolatedV(scale*16);
			bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			Tessellator tess = Tessellator.getInstance();
			BufferBuilder buf = tess.getBuffer();

			double height = bat.tank.getFluidAmount() * 1.5D / bat.tank.getCapacity();
			double off = 2.2;

			buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

			buf.pos(-off, 1.5, -0.5).tex(u, v).endVertex();
			buf.pos(-off, 1.5 + height, -0.5).tex(u, mV).endVertex();
			buf.pos(-off, 1.5 + height, 0.5).tex(mU, mV).endVertex();
			buf.pos(-off, 1.5, 0.5).tex(mU, v).endVertex();

			buf.pos(off, 1.5, -0.5).tex(u, v).endVertex();
			buf.pos(off, 1.5 + height, -0.5).tex(u, mV).endVertex();
			buf.pos(off, 1.5 + height, 0.5).tex(mU, mV).endVertex();
			buf.pos(off, 1.5, 0.5).tex(mU, v).endVertex();

			buf.pos(-0.5, 1.5, -off).tex(u, v).endVertex();
			buf.pos(-0.5, 1.5 + height, -off).tex(u, mV).endVertex();
			buf.pos(0.5, 1.5 + height, -off).tex(mU, mV).endVertex();
			buf.pos(0.5, 1.5, -off).tex(mU, v).endVertex();

			buf.pos(-0.5, 1.5, off).tex(u, v).endVertex();
			buf.pos(-0.5, 1.5 + height, off).tex(u, mV).endVertex();
			buf.pos(0.5, 1.5 + height, off).tex(mU, mV).endVertex();
			buf.pos(0.5, 1.5, off).tex(mU, v).endVertex();

			tess.draw();

			GlStateManager.color(1, 1, 1, 1);
			GlStateManager.enableLighting();
			
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbx, lby);
		}
		GL11.glPopMatrix();
	}
}
