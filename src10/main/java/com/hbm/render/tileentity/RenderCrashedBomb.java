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
        int type = te.getBlockMetadata();
		rand.setSeed((long)te.getPos().hashCode() + ((long)type)<<32);
		
		float yaw = rand.nextFloat() * 360;
		float pitch = rand.nextFloat() * 85 + 5;
		float roll = rand.nextFloat() * 360;
		double offset = rand.nextDouble() * 4 - 2;
        if(type == 2) offset = rand.nextDouble() * 3 - 2;
        else if(type == 3) offset = rand.nextDouble() * 3 - 2;

		GL11.glRotatef(yaw, 0F, 1F, 0F);
		GL11.glRotatef(pitch, 1F, 0F, 0F);
		GL11.glRotatef(roll, 0F, 0F, 1F);
		GL11.glTranslated(0, 0, -offset);

		GlStateManager.shadeModel(GL11.GL_SMOOTH);
        if(type == 0) {
            bindTexture(ResourceManager.dud_balefire_tex);
            ResourceManager.dud_balefire.renderAll();
        } else if(type == 1) {
            bindTexture(ResourceManager.dud_conventional_tex);
            ResourceManager.dud_conventional.renderAll();
        } else if(type == 2) {
            bindTexture(ResourceManager.dud_nuke_tex);
            ResourceManager.dud_nuke.renderAll();
        } else if(type == 3) {
            bindTexture(ResourceManager.dud_salted_tex);
            ResourceManager.dud_salted.renderAll();
        }
	    GlStateManager.shadeModel(GL11.GL_FLAT);

        GlStateManager.enableCull();
        GL11.glPopMatrix();
    }
}
