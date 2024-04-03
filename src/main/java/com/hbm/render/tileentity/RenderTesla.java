package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.render.misc.BeamPronter;
import com.hbm.render.misc.BeamPronter.EnumBeamType;
import com.hbm.render.misc.BeamPronter.EnumWaveType;
import com.hbm.tileentity.machine.TileEntityTesla;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderTesla extends TileEntitySpecialRenderer<TileEntityTesla> {

	@Override
	public boolean isGlobalRenderer(TileEntityTesla te) {
		return true;
	}
	
	@Override
	public void render(TileEntityTesla tesla, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
		GL11.glRotatef(180, 0F, 1F, 0F);

        bindTexture(ResourceManager.tesla_tex);
        ResourceManager.tesla.renderAll();
        GlStateManager.enableCull();
        

        double sx = tesla.getPos().getX() + 0.5D;
        double sy = tesla.getPos().getY() + TileEntityTesla.offset;
        double sz = tesla.getPos().getZ() + 0.5D;

        GL11.glTranslated(0.0D, TileEntityTesla.offset, 0.0D);
        for(double[] target : tesla.targets) {
        	
        	double length = Math.sqrt(Math.pow(target[0] - sx, 2) + Math.pow(target[1] - sy, 2) + Math.pow(target[2] - sz, 2));
        	
	        BeamPronter.prontBeam(Vec3.createVectorHelper(-target[0] + sx, target[1] - sy, -target[2] + sz), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x0051C4, 0x606060, (int)tesla.getWorld().getTotalWorldTime() % 1000 + 1, (int) (length * 5), 0.125F, 2, 0.03125F);

        }

        GL11.glPopMatrix();
	}
}
