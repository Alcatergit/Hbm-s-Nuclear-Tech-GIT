package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachinePumpjack;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class RenderPumpjack extends TileEntitySpecialRenderer<TileEntityMachinePumpjack> {
	
	int i;
	
	@Override
	public boolean isGlobalRenderer(TileEntityMachinePumpjack te) {
		return true;
	}
	
	@Override
	public void render(TileEntityMachinePumpjack te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y, z + 0.5);
		switch(te.getBlockMetadata())
		{
		case 2:
			GL11.glRotatef(-90, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		}
		
    	i= te.rotation;
        GL11.glPushMatrix();
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
		GL11.glRotatef(180, 0F, 1F, 0F);
        
		this.bindTexture(ResourceManager.pumpjack_base_tex);
        ResourceManager.pumpjack_base.renderAll();

        GL11.glPopMatrix();
        
        renderTileEntityAt2();
        GlStateManager.enableCull();
	}
	
	public void renderTileEntityAt2()
    {
        GL11.glPushMatrix();
        GL11.glTranslated(0, 1.5, 5.5);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
		GL11.glRotatef(180, 0F, 1F, 0F);

		this.bindTexture(ResourceManager.pumpjack_rotor_tex);
		GL11.glRotated(i - 90, 1F, 0F, 0F);
		
        ResourceManager.pumpjack_rotor.renderAll();

        GL11.glPopMatrix();
        
        renderTileEntityAt3();
    }
    
	public void renderTileEntityAt3()
    {
        GL11.glPushMatrix();
        GL11.glTranslated(0, 1, 0);
        GL11.glTranslated(0, 2.5, 2.5);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
		GL11.glRotatef(180, 0F, 1F, 0F);

		this.bindTexture(ResourceManager.pumpjack_head_tex);
		float t = (float) Math.sin((i / (180 / Math.PI))) * 15;
		GL11.glRotatef(t, 1F, 0F, 0F);
        ResourceManager.pumpjack_head.renderAll();

        GL11.glPopMatrix();

        renderTileEntityAt4();
    }
    
	public void renderTileEntityAt4()
    {
        GL11.glPushMatrix();
        GL11.glTranslated(0, 1, 0);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
		GL11.glRotatef(180, 0F, 1F, 0F);

		float j = (float) Math.sin((i / (180 / Math.PI))) * 15;
		float t = (float) Math.sin((i / (180 / Math.PI)));
		float u = (float) Math.sin(((i + 90) / (180 / Math.PI)));
		float v = (float) Math.sin((j / (180 / Math.PI))) * 3;
		float w = (float) Math.sin(((j + 90) / (180 / Math.PI))) * 3;
		drawConnection(0.55, 0.5 + t, -5.5 - u, 0.55, 2.5 + v, -2.5 - w);
		drawConnection(-0.55, 0.5 + t, -5.5 - u, -0.55, 2.5 + v, -2.5 - w);

        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
	
	public void drawConnection(double x, double y, double z, double a, double b, double c) {
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buf = tessellator.getBuffer();
        buf.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR);
        buf.pos(x + 0.05F, y, z).color(0.6F, 0.6F, 0.6F, 1.0F).endVertex();
        buf.pos(x - 0.05F, y, z).color(0.6F, 0.6F, 0.6F, 1.0F).endVertex();
        buf.pos(a + 0.05F, b, c).color(0.6F, 0.6F, 0.6F, 1.0F).endVertex();
        buf.pos(a - 0.05F, b, c).color(0.6F, 0.6F, 0.6F, 1.0F).endVertex();
        tessellator.draw();
        buf.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR);
        buf.pos(x, y, z + 0.05F).color(0.6F, 0.6F, 0.6F, 1.0F).endVertex();
        buf.pos(x, y, z - 0.05F).color(0.6F, 0.6F, 0.6F, 1.0F).endVertex();
        buf.pos(a, b, c + 0.05F).color(0.6F, 0.6F, 0.6F, 1.0F).endVertex();
        buf.pos(a, b, c - 0.05F).color(0.6F, 0.6F, 0.6F, 1.0F).endVertex();
        tessellator.draw();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.enableCull();
	}
}
