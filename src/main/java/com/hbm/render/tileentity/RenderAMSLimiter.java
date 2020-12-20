package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.RenderHelper;
import com.hbm.tileentity.machine.TileEntityAMSBase;
import com.hbm.tileentity.machine.TileEntityAMSLimiter;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderAMSLimiter extends TileEntitySpecialRenderer<TileEntityAMSLimiter> {

	@Override
	public boolean isGlobalRenderer(TileEntityAMSLimiter te) {
		return true;
	}
	
	@Override
	public void render(TileEntityAMSLimiter te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
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

		if(te.locked)
        	bindTexture(ResourceManager.ams_destroyed_tex);
        else
        	bindTexture(ResourceManager.ams_limiter_tex);
        
        if(te.locked)
            ResourceManager.ams_limiter_destroyed.renderAll();
        else
        	ResourceManager.ams_limiter.renderAll();

        GL11.glPopMatrix();
        renderTileEntityAt2(te, x, y, z, partialTicks);
        GlStateManager.enableCull();
	}
	
	public void renderTileEntityAt2(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
		GL11.glRotatef(180, 0F, 1F, 0F);
		GL11.glRotatef(-90, 0F, 1F, 0F);
		
		switch(tileEntity.getBlockMetadata())
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

       // bindTexture(ResourceManager.universal);
        
        TileEntityAMSLimiter limiter = (TileEntityAMSLimiter)tileEntity;

        int meta = tileEntity.getBlockMetadata();
        boolean flag = false;
		double maxSize = 5;
		double minSize = 0.5;
        if(meta == 2 && tileEntity.getWorld().getTileEntity(tileEntity.getPos().add(0, 0, -6)) instanceof TileEntityAMSBase && !limiter.locked) {
        	flag = true;
        	TileEntityAMSBase base = (TileEntityAMSBase)tileEntity.getWorld().getTileEntity(tileEntity.getPos().add(0, 0, -6));
        	maxSize += ((((double)base.tanks[2].getFluidAmount()) / ((double)base.tanks[2].getCapacity())) + (((double)base.tanks[3].getFluidAmount()) / ((double)base.tanks[3].getCapacity()))) * ((maxSize - minSize) / 2);
        }
        if(meta == 3 && tileEntity.getWorld().getTileEntity(tileEntity.getPos().add(0, 0, 6)) instanceof TileEntityAMSBase && !limiter.locked) {
        	flag = true;
        	TileEntityAMSBase base = (TileEntityAMSBase)tileEntity.getWorld().getTileEntity(tileEntity.getPos().add(0, 0, 6));
        	maxSize += ((((double)base.tanks[2].getFluidAmount()) / ((double)base.tanks[2].getCapacity())) + (((double)base.tanks[3].getFluidAmount()) / ((double)base.tanks[3].getCapacity()))) * ((maxSize - minSize) / 2);
        }
        if(meta == 4 && tileEntity.getWorld().getTileEntity(tileEntity.getPos().add(-6, 0, 0)) instanceof TileEntityAMSBase && !limiter.locked) {
        	flag = true;
        	TileEntityAMSBase base = (TileEntityAMSBase)tileEntity.getWorld().getTileEntity(tileEntity.getPos().add(-6, 0, 0));
        	maxSize += ((((double)base.tanks[2].getFluidAmount()) / ((double)base.tanks[2].getCapacity())) + (((double)base.tanks[3].getFluidAmount()) / ((double)base.tanks[3].getCapacity()))) * ((maxSize - minSize) / 2);
        }
        if(meta == 5 && tileEntity.getWorld().getTileEntity(tileEntity.getPos().add(6, 0, 0)) instanceof TileEntityAMSBase && !limiter.locked) {
        	flag = true;
        	TileEntityAMSBase base = (TileEntityAMSBase)tileEntity.getWorld().getTileEntity(tileEntity.getPos().add(6, 0, 0));
        	maxSize += ((((double)base.tanks[2].getFluidAmount()) / ((double)base.tanks[2].getCapacity())) + (((double)base.tanks[3].getFluidAmount()) / ((double)base.tanks[3].getCapacity()))) * ((maxSize - minSize) / 2);
        }
        
        if(flag) {
        	
			GL11.glRotatef(-90, 0F, 1F, 0F);
        	
        	double posX = 0;
        	double posY = 0;
        	double posZ = 0;
        	double length = 4;
        	double radius = 0.12;
            GL11.glTranslated(2.5, 5.5, 0);

            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
            GlStateManager.disableTexture2D();
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            GlStateManager.disableAlpha();
	        GlStateManager.disableCull();
            GlStateManager.depthMask(false);
            
        	RenderHelper.startDrawingColored(GL11.GL_QUADS);
        	
			RenderHelper.addVertexColor(posX + length, posY - radius, posZ - radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 0f);
			RenderHelper.addVertexColor(posX + length, posY - radius, posZ + radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 0f);
			RenderHelper.addVertexColor(posX, posY - radius, posZ + radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 1);
			RenderHelper.addVertexColor(posX, posY - radius, posZ - radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 1);
			
			RenderHelper.addVertexColor(posX + length, posY + radius, posZ + radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 0f);
			RenderHelper.addVertexColor(posX + length, posY + radius, posZ - radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 0f);
			RenderHelper.addVertexColor(posX, posY + radius, posZ - radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 1);
			RenderHelper.addVertexColor(posX, posY + radius, posZ + radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 1);
			
			RenderHelper.addVertexColor(posX + length, posY - radius, posZ - radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 0f);
			RenderHelper.addVertexColor(posX + length, posY + radius, posZ - radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 0f);
			RenderHelper.addVertexColor(posX, posY + radius, posZ - radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 1);
			RenderHelper.addVertexColor(posX, posY - radius, posZ - radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 1);
			
			RenderHelper.addVertexColor(posX + length, posY - radius, posZ + radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 0f);
			RenderHelper.addVertexColor(posX + length, posY + radius, posZ + radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 0f);
			RenderHelper.addVertexColor(posX, posY + radius, posZ + radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 1);
			RenderHelper.addVertexColor(posX, posY - radius, posZ + radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 1);
	        
	        if(limiter.efficiency > 0) {

	        	radius *= 2;
	        	net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
	            GlStateManager.disableTexture2D();
	            GlStateManager.shadeModel(GL11.GL_SMOOTH);
	            GlStateManager.enableBlend();
	            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	            GlStateManager.disableAlpha();
		        GlStateManager.disableCull();
	            GlStateManager.depthMask(false);
	            
				RenderHelper.addVertexColor(posX + length, posY - radius, posZ - radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 0f);
				RenderHelper.addVertexColor(posX + length, posY - radius, posZ + radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 0f);
				RenderHelper.addVertexColor(posX, posY - radius, posZ + radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 1);
				RenderHelper.addVertexColor(posX, posY - radius, posZ - radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 1);
				
				RenderHelper.addVertexColor(posX + length, posY + radius, posZ + radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 0f);
				RenderHelper.addVertexColor(posX + length, posY + radius, posZ - radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 0f);
				RenderHelper.addVertexColor(posX, posY + radius, posZ - radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 1);
				RenderHelper.addVertexColor(posX, posY + radius, posZ + radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 1);
				
				RenderHelper.addVertexColor(posX + length, posY - radius, posZ - radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 0f);
				RenderHelper.addVertexColor(posX + length, posY + radius, posZ - radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 0f);
				RenderHelper.addVertexColor(posX, posY + radius, posZ - radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 1);
				RenderHelper.addVertexColor(posX, posY - radius, posZ - radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 1);
				
				RenderHelper.addVertexColor(posX + length, posY - radius, posZ + radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 0f);
				RenderHelper.addVertexColor(posX + length, posY + radius, posZ + radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 0f);
				RenderHelper.addVertexColor(posX, posY + radius, posZ + radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 1);
				RenderHelper.addVertexColor(posX, posY - radius, posZ + radius, 0.408F - 0.175F, 0.686F - 0.175F, 0.686F - 0.175F, 1);
	        }
	        
	        RenderHelper.draw();
			
	        GlStateManager.disableBlend();
	        GlStateManager.enableAlpha();
	        GlStateManager.shadeModel(GL11.GL_FLAT);
	        net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
	        GlStateManager.depthMask(true);
            GlStateManager.enableCull();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	        GlStateManager.enableTexture2D();
        }

        GL11.glPopMatrix();
    }
}
