package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FluidTypeHandler;
import com.hbm.render.misc.DiamondPronter;
import com.hbm.tileentity.machine.TileEntityBarrel;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidStack;

public class RenderFluidBarrel extends TileEntitySpecialRenderer<TileEntityBarrel> {

	@Override
	public void render(TileEntityBarrel barrel, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
		GlStateManager.disableLighting();

		if(barrel.tank.getFluid() != null) {
			FluidStack type = barrel.tank.getFluid();
			FluidTypeHandler.FluidProperties p = FluidTypeHandler.getProperties(type);

			for(int j = 0; j < 4; j++) {

				GL11.glPushMatrix();
				GL11.glTranslated(0.4, 0.25, -0.15);
				GL11.glScalef(1.0F, 0.35F, 0.35F);
				DiamondPronter.pront(p.poison, p.flammability, p.reactivity, p.symbol);
				GL11.glPopMatrix();
				GL11.glRotatef(90, 0, 1, 0);
			}
		}

		GL11.glPopMatrix();
	}
}
