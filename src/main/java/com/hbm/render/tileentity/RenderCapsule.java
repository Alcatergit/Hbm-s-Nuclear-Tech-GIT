package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntitySoyuzCapsule;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderCapsule extends TileEntitySpecialRenderer<TileEntitySoyuzCapsule> {

	@Override
	public boolean isGlobalRenderer(TileEntitySoyuzCapsule te) {
		return true;
	}
	
	@Override
	public void render(TileEntitySoyuzCapsule te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y, z + 0.5);
        GlStateManager.enableLighting();

        GL11.glTranslatef(0.0F, -0.25F, 0.0F);
        GL11.glRotatef(-25, 0, 1, 0);
        GL11.glRotatef(15, 0, 0, 1);
        
        GlStateManager.enableCull();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        if(te.getBlockMetadata() > 0)
        	bindTexture(ResourceManager.soyuz_lander_rust_tex);
        else
        	bindTexture(ResourceManager.soyuz_lander_tex);
        ResourceManager.soyuz_lander.renderPart("Capsule");
        GlStateManager.shadeModel(GL11.GL_FLAT);
        
        GL11.glPopMatrix();
	}
}
