package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Quaternion;

import com.hbm.tileentity.deco.TileEntityObjTester;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderObjTester extends TileEntitySpecialRenderer<TileEntityObjTester> {
	
	@Override
	public boolean isGlobalRenderer(TileEntityObjTester te) {
		return true;
	}
	
	@Override
	public void render(TileEntityObjTester te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        
        //Drillgon200: Aha! I finally started using bob's tester block!
        Quaternion identity = new Quaternion();
        Quaternion q = new Quaternion(0, 0.7071068F, 0, 0.7071068F);
        Quaternion a = identity;
        Quaternion b = q;
        float t = 0.5F;
        
        Quaternion r = new Quaternion();
		float t_ = 1 - t;
		float Wa, Wb;
		float theta = (float) Math.acos(Quaternion.dot(a, b));
		
		float sn = (float) Math.sin(theta);
		Wa = (float) (Math.sin(t_*theta) / sn);
		Wb = (float) (Math.sin(t*theta) / sn);
		if(sn == 0)
			Wa = Wb = 1;
		
		r.x = Wa*a.x + Wb*b.x;
		r.y = Wa*a.y + Wb*b.y;
		r.z = Wa*a.z + Wb*b.z;
		r.w = Wa*a.w + Wb*b.w;
		
		r.normalise(r);
		//System.out.println(r);
        
        //System.out.println(q);

        GL11.glPopMatrix();
	}
}
