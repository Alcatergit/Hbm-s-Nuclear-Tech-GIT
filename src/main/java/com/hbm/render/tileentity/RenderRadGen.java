package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineRadGen;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderRadGen extends TileEntitySpecialRenderer<TileEntityMachineRadGen> {

	@Override
	public boolean isGlobalRenderer(TileEntityMachineRadGen te) {
		return true;
	}
	
	@Override
	public void render(TileEntityMachineRadGen te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
        GlStateManager.enableLighting();
        GL11.glDisable(GL11.GL_CULL_FACE);
		switch(te.getBlockMetadata()){
		case 2:
			GL11.glTranslated(x + 1.5D, y, z + 0.5D);
			GL11.glRotatef(180, 0F, 1F, 0F); // North
			break;
		case 4:
			GL11.glTranslated(x + 0.5D, y, z - 0.5D);
			GL11.glRotatef(270, 0F, 1F, 0F); //West
			break;
		case 3:
			GL11.glTranslated(x - 0.5D, y, z + 0.5D);
			GL11.glRotatef(0, 0F, 1F, 0F); //South
			break;
		case 5:
			GL11.glTranslated(x + 0.5D, y, z + 1.5D);
			GL11.glRotatef(90, 0F, 1F, 0F); //East
			break;
		}

        bindTexture(ResourceManager.radgen_body_tex);
        
        ResourceManager.radgen_body.renderPart("Base");
        GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(0F, 1F, 0F);
        ResourceManager.radgen_body.renderPart("Light");
        GL11.glColor3f(1F, 1F, 1F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glPushMatrix();
	        if(te.fuel > 0){
	        	GL11.glTranslated(0D, 1.5D, 0D);
				GL11.glRotatef((System.currentTimeMillis() * te.strength/te.maxStrength) % 360, 1F, 0F, 0F);
				GL11.glTranslated(0D, -1.5D, 0D);
			}
	        ResourceManager.radgen_body.renderPart("Rotor");
        GL11.glPopMatrix();

        GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDepthMask(false);

			GL11.glColor4f(0.5F, 0.75F, 1F, 0.8F);
			ResourceManager.radgen_body.renderPart("Glass");
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			GL11.glDepthMask(true);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
	}
}
