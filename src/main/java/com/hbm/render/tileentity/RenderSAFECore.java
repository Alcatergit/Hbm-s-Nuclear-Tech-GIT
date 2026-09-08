package com.hbm.render.tileentity;

import com.hbm.render.entity.RenderBlackHole;
import com.hbm.tileentity.machine.TileEntityFWatzCore;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderSAFECore extends TileEntitySpecialRenderer<TileEntityFWatzCore> {

	@Override
	public void render(TileEntityFWatzCore te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		int type = te.getType();
		if(type == 0) return;
		int absType = Math.abs(type);
		float size = (float) (absType / 5D + 1D) * (type < 0 ? 0.1F : 0.5F);
		float speed = te.isDoingSomething ? 2F : 1F;
		int age = (int) (te.getWorld().getTotalWorldTime() * speed);
		double rx = x + 0.5, ry = y + 2.5, rz = z + 0.5;
		float pt = partialTicks * speed;

		RenderBlackHole.doRender(absType, 45, age, size, rx, ry, rz, pt);
	}
}
