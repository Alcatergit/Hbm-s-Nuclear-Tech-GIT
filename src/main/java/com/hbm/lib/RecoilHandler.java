package com.hbm.lib;

import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RecoilHandler {

	private static long lastRenderTime;
	public static float verticalVelocity;
	public static float verticalRecoil;
	
	public static void modifiyCamera(EntityViewRenderEvent.CameraSetup e){
		long currentTime = System.currentTimeMillis();
		float scale = (currentTime-lastRenderTime)/1000F;
		final float settle = 20F*MathHelper.clamp(verticalRecoil/4, 0, 200);
		
		verticalRecoil = Math.max(0, verticalRecoil - scale*settle + verticalVelocity);
		verticalVelocity *=0.35*scale;
		e.setPitch(e.getPitch()-verticalRecoil);
		lastRenderTime = currentTime;
	}
}
