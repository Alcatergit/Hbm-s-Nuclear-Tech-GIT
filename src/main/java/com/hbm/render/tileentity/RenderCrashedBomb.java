package com.hbm.render.tileentity;

import java.util.Random;
import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.bomb.TileEntityCrashedBomb;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderCrashedBomb extends TileEntitySpecialRenderer<TileEntityCrashedBomb> {
    
    public static Random rand = new Random();

    @Override
    public boolean isGlobalRenderer(TileEntityCrashedBomb te) {
    	return true;
    }
    
    @Override
    public void render(TileEntityCrashedBomb te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    	GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.disableCull();
        GlStateManager.enableLighting();
		rand.setSeed((long)te.getPos().hashCode() + ((long)te.getBlockMetadata())<<32);
		
		float yaw = rand.nextFloat() * 360;
		float pitch = rand.nextFloat() * 45 + 45;
		float roll = rand.nextFloat() * 360;
		double offset = rand.nextDouble() * 3 - 1;

		GL11.glRotatef(yaw, 0F, 1F, 0F);
		GL11.glRotatef(pitch, 1F, 0F, 0F);
		GL11.glRotatef(roll, 0F, 0F, 1F);
		GL11.glTranslated(0, 0, -offset);

		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.dud_tex);
	    ResourceManager.dud.renderAll();
	    GlStateManager.shadeModel(GL11.GL_FLAT);

        GlStateManager.enableCull();
        GL11.glPopMatrix();
    }
}
