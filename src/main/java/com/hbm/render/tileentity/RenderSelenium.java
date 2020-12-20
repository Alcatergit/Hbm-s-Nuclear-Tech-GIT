package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineSeleniumEngine;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderSelenium extends TileEntitySpecialRenderer<TileEntityMachineSeleniumEngine> {

	@Override
	public void render(TileEntityMachineSeleniumEngine te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GlStateManager.enableLighting();
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		GL11.glRotatef(-90, 0F, 1F, 0F);
		
		switch(te.getBlockMetadata())
		{
		case 2:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

        bindTexture(ResourceManager.selenium_body_tex);
        ResourceManager.selenium_body.renderAll();
        
        GL11.glTranslated(0.0D, 1.0D, 0.0D);
        
        int count = ((TileEntityMachineSeleniumEngine)te).pistonCount;
        
        float rot = 360F / count;

        bindTexture(ResourceManager.selenium_piston_tex);
        for(int i = 0; i < count; i++) {
            ResourceManager.selenium_piston.renderAll();
    		GL11.glRotatef(rot, 0, 0, 1);
        }
		
        if(count > 2 && ((TileEntityMachineSeleniumEngine)te).hasAcceptableFuel() && ((TileEntityMachineSeleniumEngine)te).tank.getFluidAmount() > 0)
        	GL11.glRotatef((System.currentTimeMillis() / 2) % 360, 0F, 0F, -1F);

        bindTexture(ResourceManager.selenium_rotor_tex);
        ResourceManager.selenium_rotor.renderAll();

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
	}
}
