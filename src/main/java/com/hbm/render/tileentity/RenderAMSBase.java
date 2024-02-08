package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityAMSBase;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderAMSBase extends TileEntitySpecialRenderer<TileEntityAMSBase> {

	@Override
	public boolean isGlobalRenderer(TileEntityAMSBase te) {
		return true;
	}
	
	@Override
	public void render(TileEntityAMSBase base, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
		GL11.glRotatef(180, 0F, 1F, 0F);

        bindTexture(ResourceManager.ams_base_tex);
        ResourceManager.ams_base.renderAll();
        
        GL11.glPopMatrix();

		if(base.color > -1)
			renderTileEntityAt2(base, x, y, z, partialTicks);
		GlStateManager.enableCull();
	}
	
	public void renderTileEntityAt2(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 5.5, z + 0.5D);
        //GL11.glEnable(GL11.GL_LIGHTING);
        GlStateManager.disableLighting();
        GlStateManager.enableCull();
		GL11.glRotatef(180, 0F, 1F, 0F);
		
		int rot = (int) ((System.nanoTime()/100000000)%360) * 3;
		
		TileEntityAMSBase base = (TileEntityAMSBase)tileEntity;
		
		double maxSize = 5;
		double minSize = 0.5;
		double scale = minSize;
		scale += ((((double)base.tanks[2].getFluidAmount()) / ((double)base.tanks[2].getCapacity())) + (((double)base.tanks[3].getFluidAmount()) / ((double)base.tanks[3].getCapacity()))) * ((maxSize - minSize) / 2);
		GL11.glScaled(scale, scale, scale);

		GlStateManager.disableTexture2D();
		
		GL11.glRotatef(rot, 0F, 1F, 0F);
		GL11.glScalef(1.1F, 1.1F, 1.1F);
		GlStateManager.color((byte)(0x20)/255, (byte)(0x20)/255, (byte)(0x40)/255);
		ResourceManager.sphere_iuv.renderAll();
		GL11.glScalef(1/1.1F, 1/1.1F, 1/1.1F);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
        GL11.glPopMatrix();
        
        renderTileEntityAt3(tileEntity, x, y, z, f);
    }

	public void renderTileEntityAt3(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 5.5, z + 0.5D);
        //GL11.glEnable(GL11.GL_LIGHTING);
        GlStateManager.disableLighting();
        GlStateManager.enableCull();
		GL11.glRotatef(180, 0F, 1F, 0F);
		
		TileEntityAMSBase base = (TileEntityAMSBase)tileEntity;
		
		double maxSize = 5;
		double minSize = 0.5;
		double scale = minSize;
		scale += ((((double)base.tanks[2].getFluidAmount()) / ((double)base.tanks[2].getCapacity())) + (((double)base.tanks[3].getFluidAmount()) / ((double)base.tanks[3].getCapacity()))) * ((maxSize - minSize) / 2);
		GL11.glScaled(scale, scale, scale);
		
		//GL11.glColor3ub((byte)((base.color & 0xFF0000) >> 16), (byte)((base.color & 0x00FF00) >> 8), (byte)((base.color & 0x0000FF) >> 0));
        GlStateManager.color(((base.color & 0xFF0000) >> 16)/255F, ((base.color & 0x00FF00) >> 8)/255F, ((base.color & 0x0000FF) >> 0)/255F);
        GlStateManager.disableTexture2D();
		
		int rot = (int) ((System.nanoTime()/100000000)%360) * 3;

			GL11.glRotatef(rot, 0F, 1F, 0F);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			ResourceManager.sphere_ruv.renderAll();
			GL11.glScalef(1/0.5F, 1/0.5F, 1/0.5F);
		
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

			GL11.glRotatef(-rot * 2, 0F, 1F, 0F);
			GL11.glScalef(0.6F, 0.6F, 0.6F);
			ResourceManager.sphere_ruv.renderAll();
			GL11.glScalef(1/0.6F, 1/0.6F, 1/0.6F);
		
			GL11.glRotatef(rot * 2, 0F, 1F, 0F);
			GL11.glScalef(0.7F, 0.7F, 0.7F);
			ResourceManager.sphere_ruv.renderAll();
			GL11.glScalef(1/0.7F, 1/0.7F, 1/0.7F);
		
			GL11.glRotatef(-rot * 2, 0F, 1F, 0F);
			GL11.glScalef(0.8F, 0.8F, 0.8F);
			ResourceManager.sphere_ruv.renderAll();
			GL11.glScalef(1/0.8F, 1/0.8F, 1/0.8F);
		
			GL11.glRotatef(rot * 2, 0F, 1F, 0F);
			GL11.glScalef(0.9F, 0.9F, 0.9F);
			ResourceManager.sphere_ruv.renderAll();
			GL11.glScalef(1/0.9F, 1/0.9F, 1/0.9F);

			GL11.glRotatef(-rot * 2, 0F, 1F, 0F);
			ResourceManager.sphere_ruv.renderAll();
        
		GlStateManager.disableBlend();
        GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
        GlStateManager.disableCull();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        GL11.glPopMatrix();
    }
}
