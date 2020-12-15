package com.hbm.render.tileentity;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import com.hbm.main.ResourceManager;
import com.hbm.render.RenderHelper;
import com.hbm.tileentity.machine.TileEntityAMSBase;
import com.hbm.tileentity.machine.TileEntityAMSEmitter;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderAMSEmitter extends TileEntitySpecialRenderer<TileEntityAMSEmitter> {

	Random rand = new Random();
	
	@Override
	public boolean isGlobalRenderer(TileEntityAMSEmitter te) {
		return true;
	}
	
	@Override
	public void render(TileEntityAMSEmitter te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
		GL11.glRotatef(180, 0F, 1F, 0F);


        if(te.locked)
        	bindTexture(ResourceManager.ams_destroyed_tex);
        else
        	bindTexture(ResourceManager.ams_emitter_tex);

        if(te.locked)
            ResourceManager.ams_emitter_destroyed.renderAll();
        else
        	ResourceManager.ams_emitter.renderAll();

        GL11.glPopMatrix();
        renderTileEntityAt2(te, x, y, z, partialTicks);
        GlStateManager.enableCull();
	}
	
	public void renderTileEntityAt2(TileEntity tileEntity, double x, double y, double z, float f)
    {
		float radius = 0.04F;
		int distance = 1;
		int layers = 3;

		GL11.glPushMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.disableCull();
		GlStateManager.enableBlend();
		GlStateManager.enableLighting();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glTranslatef((float) x + 0.5F, (float) y - 7, (float) z + 0.5F);

		TileEntityAMSEmitter emitter = (TileEntityAMSEmitter)tileEntity;
		
		if(emitter.getWorld().getTileEntity(emitter.getPos().add(0, -9, 0)) instanceof TileEntityAMSBase && !emitter.locked) {
			
			RenderHelper.startDrawingColored(GL11.GL_QUADS);
			
			if(emitter.efficiency > 0) {
				
				double lastPosX = 0;
				double lastPosZ = 0;
				
				
				
				for(int i = 7; i > 0; i -= distance) {
					
					double posX = rand.nextDouble() - 0.5;
					double posZ = rand.nextDouble() - 0.5;
					
					for(int j = 1; j <= layers; j++) {
		
						RenderHelper.addVertexColor(lastPosX + (radius * j), i, lastPosZ + (radius * j), 1, 0.5F, 0, 1f);
						RenderHelper.addVertexColor(lastPosX + (radius * j), i, lastPosZ - (radius * j), 1, 0.5F, 0, 1f);
						RenderHelper.addVertexColor(posX + (radius * j), i - distance, posZ - (radius * j), 1, 0.5F, 0, 1f);
						RenderHelper.addVertexColor(posX + (radius * j), i - distance, posZ + (radius * j), 1, 0.5F, 0, 1f);
						
						RenderHelper.addVertexColor(lastPosX - (radius * j), i, lastPosZ + (radius * j), 1, 0.5F, 0, 1f);
						RenderHelper.addVertexColor(lastPosX - (radius * j), i, lastPosZ - (radius * j), 1, 0.5F, 0, 1f);
						RenderHelper.addVertexColor(posX - (radius * j), i - distance, posZ - (radius * j), 1, 0.5F, 0, 1f);
						RenderHelper.addVertexColor(posX - (radius * j), i - distance, posZ + (radius * j), 1, 0.5F, 0, 1f);
						
						RenderHelper.addVertexColor(lastPosX + (radius * j), i, lastPosZ + (radius * j), 1, 0.5F, 0, 1f);
						RenderHelper.addVertexColor(lastPosX - (radius * j), i, lastPosZ + (radius * j), 1, 0.5F, 0, 1f);
						RenderHelper.addVertexColor(posX - (radius * j), i - distance, posZ + (radius * j), 1, 0.5F, 0, 1f);
						RenderHelper.addVertexColor(posX + (radius * j), i - distance, posZ + (radius * j), 1, 0.5F, 0, 1f);
						
						RenderHelper.addVertexColor(lastPosX + (radius * j), i, lastPosZ - (radius * j), 1, 0.5F, 0, 1f);
						RenderHelper.addVertexColor(lastPosX - (radius * j), i, lastPosZ - (radius * j), 1, 0.5F, 0, 1f);
						RenderHelper.addVertexColor(posX - (radius * j), i - distance, posZ - (radius * j), 1, 0.5F, 0, 1f);
						RenderHelper.addVertexColor(posX + (radius * j), i - distance, posZ - (radius * j), 1, 0.5F, 0, 1f);
					}
		
					lastPosX = posX;
					lastPosZ = posZ;
				}
			}
			
			for(int j = 1; j <= 2; j++) {
				RenderHelper.addVertexColor(0 + (radius * j), 7, 0 + (radius * j), 1, 1, 0, 1f);
				RenderHelper.addVertexColor(0 + (radius * j), 7, 0 - (radius * j), 1, 1, 0, 1f);
				RenderHelper.addVertexColor(0 + (radius * j), 0, 0 - (radius * j), 1, 1, 0, 1f);
				RenderHelper.addVertexColor(0 + (radius * j), 0, 0 + (radius * j), 1, 1, 0, 1f);
				
				RenderHelper.addVertexColor(0 - (radius * j), 7, 0 + (radius * j), 1, 1, 0, 1f);
				RenderHelper.addVertexColor(0 - (radius * j), 7, 0 - (radius * j), 1, 1, 0, 1f);
				RenderHelper.addVertexColor(0 - (radius * j), 0, 0 - (radius * j), 1, 1, 0, 1f);
				RenderHelper.addVertexColor(0 - (radius * j), 0, 0 + (radius * j), 1, 1, 0, 1f);
				
				RenderHelper.addVertexColor(0 + (radius * j), 7, 0 + (radius * j), 1, 1, 0, 1f);
				RenderHelper.addVertexColor(0 - (radius * j), 7, 0 + (radius * j), 1, 1, 0, 1f);
				RenderHelper.addVertexColor(0 - (radius * j), 0, 0 + (radius * j), 1, 1, 0, 1f);
				RenderHelper.addVertexColor(0 + (radius * j), 0, 0 + (radius * j), 1, 1, 0, 1f);
				
				RenderHelper.addVertexColor(0 + (radius * j), 7, 0 - (radius * j), 1, 1, 0, 1f);
				RenderHelper.addVertexColor(0 - (radius * j), 7, 0 - (radius * j), 1, 1, 0, 1f);
				RenderHelper.addVertexColor(0 - (radius * j), 0, 0 - (radius * j), 1, 1, 0, 1f);
				RenderHelper.addVertexColor(0 + (radius * j), 0, 0 - (radius * j), 1, 1, 0, 1f);
			}
			RenderHelper.draw();
		}
		
		
		GlStateManager.disableBlend();
		GlStateManager.enableTexture2D();
		GL11.glPopMatrix();
    }
}
