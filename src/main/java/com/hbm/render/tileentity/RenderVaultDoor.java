package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityVaultDoor;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderVaultDoor extends TileEntitySpecialRenderer<TileEntityVaultDoor> {

	@Override
	public boolean isGlobalRenderer(TileEntityVaultDoor te) {
		return true;
	}
	
	@Override
	public void render(TileEntityVaultDoor te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
		GL11.glRotatef(180, 0F, 1F, 0F);
		
		switch(te.getBlockMetadata())
		{
		case 2:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		}

        bindTexture(ResourceManager.vault_frame_tex);
        ResourceManager.vault_frame.renderAll();
        GL11.glTranslated(-1.0D, 0.0D, 0.0D);
        bindTexture(ResourceManager.universal);
        ResourceManager.vault_teeth.renderAll();
        GL11.glTranslated(1.0D, 0.0D, 0.0D);


        GL11.glTranslated(0.0D, -2.5D, 0.0D);

        double[] timer;
        
        if(te.state == 0)
        	timer = new double[] { 0, 0, 0, 0, 0 };
        else if(te.state == 2)
        	timer = getAnimationFromSysTime((2000 + 800 * 5));
        else if(te.isOpening)
        	timer = getAnimationFromSysTime(System.currentTimeMillis() - te.sysTime);
        else
        	timer = getAnimationFromSysTime(te.sysTime + (2000 + 800 * 5) - System.currentTimeMillis());

        GL11.glTranslated(-timer[0], 0, timer[1]);
        
        GL11.glTranslated(0.0D, 5D, 0.0D);
        GL11.glRotated(timer[2], 1, 0, 0);
        GL11.glTranslated(0.0D, -2.5D, 0.0D);

        
        switch(te.type) {
        case 1:
        case 2: bindTexture(ResourceManager.vault_cog_tex); break;
        case 3:
        case 4: bindTexture(ResourceManager.stable_cog_tex); break;
        case 5:
        case 6: bindTexture(ResourceManager.vault4_cog_tex); break;
        default: bindTexture(ResourceManager.vault_cog_tex); break;
        }
        ResourceManager.vault_cog.renderAll();
        
        switch(te.type) {
        case 1: bindTexture(ResourceManager.vault_label_87_tex); break;
        case 2: bindTexture(ResourceManager.vault_label_106_tex); break;
        case 3: bindTexture(ResourceManager.stable_label_tex); break;
        case 4: bindTexture(ResourceManager.stable_label_99_tex); break;
        case 5: bindTexture(ResourceManager.vault4_label_111_tex); break;
        case 6: bindTexture(ResourceManager.vault4_label_81_tex); break;
        default: bindTexture(ResourceManager.vault_label_101_tex); break;
        }
        ResourceManager.vault_label.renderAll();

        GlStateManager.enableCull();
        GL11.glPopMatrix();
	}
	
	//x, z, roll
    private static double[] getAnimationFromSysTime(long time) {
    	
    	double pullOutDuration = 2000D;
    	double slideDuration = 800D;
    	
    	//dura total = pullout + slide * 5
    	
    	double diameter = 4.5D;
    	double circumference = diameter * Math.PI;
    	
    	double x = (time) / pullOutDuration;

    	if(x > 1)
    		x = 1;
    	if(x < 0)
    		x = 0;
    	
    	double z = (time - pullOutDuration) / slideDuration;
    	
    	if(time < pullOutDuration)
    		z = 0;
    	
    	if(z > 5)
    		z = 5;
    	if(z < 0)
    		z = 0;
    	
    	double roll = z / circumference * 360;
    	
    	return new double[] { x + 0.0005D, z, roll };
    }
}
