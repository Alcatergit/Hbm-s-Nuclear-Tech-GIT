package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineTurbofan;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderTurbofan extends TileEntitySpecialRenderer<TileEntityMachineTurbofan> {

	@Override
	public void render(TileEntityMachineTurbofan turbo, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.disableCull();
		switch(turbo.getBlockMetadata() - 10) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		float spin = turbo.lastSpin + (turbo.spin - turbo.lastSpin) * partialTicks; 
		GlStateManager.disableLighting();
		GL11.glShadeModel(GL11.GL_SMOOTH);
        bindTexture(ResourceManager.turbofan_tex);
        
        GL11.glPushMatrix();
        GL11.glTranslated(0, 1.5, 0);
        GL11.glRotatef(spin, 0F, 0F, -1F);
        GL11.glTranslated(0, -1.5, 0);
        ResourceManager.turbofan.renderPart("Blades");
		GL11.glPopMatrix();
		
		GlStateManager.enableLighting();
        ResourceManager.turbofan.renderPart("Body");
		if(turbo.afterburner == 0)
			bindTexture(ResourceManager.turbofan_back_tex);
		else
			bindTexture(ResourceManager.turbofan_afterburner_tex);

		ResourceManager.turbofan.renderPart("Afterburner");
		GL11.glShadeModel(GL11.GL_FLAT);

        GlStateManager.enableCull();
        GL11.glPopMatrix();
    }
}
