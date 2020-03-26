package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.bomb.TileEntityNukeN45;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderNukeN45 extends TileEntitySpecialRenderer<TileEntityNukeN45> {
	
	@Override
	public boolean isGlobalRenderer(TileEntityNukeN45 te) {
		return true;
	}
	
	@Override
	public void render(TileEntityNukeN45 te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		boolean standing = te.getWorld().getBlockState(te.getPos().down()).isNormalCube();
    	
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
	    bindTexture(ResourceManager.universal);

        GL11.glPushMatrix();
        
        	//GL11.glScaled(2.0D, 2.0D, 2.0D);
        
        	if(standing) {
        		ResourceManager.n45_stand.renderAll();
        	}
        	
        	double d = 0.25;
        	
        	if(te.primed)
        		d /= 4D;
        
            GL11.glTranslated(0, standing ? 1D : 0.5D, 0);

	        ResourceManager.n45_globe.renderAll();
	        
        	GL11.glRotated(90, 1, 0, 0);
        	
	        for(int i = 0; i < 8; i++) {
	            ResourceManager.n45_knob.renderAll();
	            GL11.glTranslated(0, -d, 0);
	            ResourceManager.n45_rod.renderAll();
	            GL11.glTranslated(0, d, 0);
	        	GL11.glRotated(45, 0, 0, 1);
	        }

        	GL11.glRotated(45, 0, 0, 1);
        	
	        for(int i = 0; i < 4; i++) {
	        	GL11.glRotated(-45, 1, 0, 0);
	            ResourceManager.n45_knob.renderAll();
	            GL11.glTranslated(0, -d, 0);
	            ResourceManager.n45_rod.renderAll();
	            GL11.glTranslated(0, d, 0);
	        	GL11.glRotated(45, 1, 0, 0);
	        	GL11.glRotated(90, 0, 0, 1);
	        }

        	GL11.glRotated(-90, 0, 0, 1);
        	
	        for(int i = 0; i < 4; i++) {
	        	GL11.glRotated(45, 1, 0, 0);
	            ResourceManager.n45_knob.renderAll();
	            GL11.glTranslated(0, -d, 0);
	            ResourceManager.n45_rod.renderAll();
	            GL11.glTranslated(0, d, 0);
	        	GL11.glRotated(-45, 1, 0, 0);
	        	GL11.glRotated(90, 0, 0, 1);
	        }

        	GL11.glRotated(45, 0, 0, 1);
        	GL11.glRotated(-90, 1, 0, 0);

            ResourceManager.n45_knob.renderAll();
            GL11.glTranslated(0, -d, 0);
            ResourceManager.n45_rod.renderAll();
            GL11.glTranslated(0, d, 0);
            
            if(!standing) {
            	int depth = 0;
            	
            	for(int i = 0; i < 51; i++) {
            		
            		if(!te.getWorld().getBlockState(te.getPos().add(0, -i - 1, 0)).isNormalCube()) {
            			depth++;
            		} else {
            			break;
            		}
            	}
            	
            	if(depth != 0 && depth < 51) {

                    GL11.glTranslated(0, -1D, 0);
                    
                	for(int i = 0; i < depth + 1; i++) {

                        ResourceManager.n45_chain.renderAll();
                        GL11.glTranslated(0, -1, 0);
                	}
            	}
            }
	        
        GL11.glPopMatrix();
        
        GlStateManager.enableCull();
        GL11.glPopMatrix();
	}
}
