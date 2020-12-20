package com.hbm.tileentity.generic;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;

public class TileEntityTaint extends TileEntity {
	
	//Drillgon200: Well, this is hacky, but heck if I'm writing multiple thousands of lines of json for it.
	@Override
	public boolean shouldRenderInPass(int pass) {
		if(super.shouldRenderInPass(pass)){
			TileEntityRendererDispatcher.instance.render(this, Minecraft.getMinecraft().getRenderPartialTicks(), -1);
		}
		return false;
	}
	
	@Override
	public boolean hasFastRenderer() {
		return super.hasFastRenderer();
	}
	
	@Override
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
