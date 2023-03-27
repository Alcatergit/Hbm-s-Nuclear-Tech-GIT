package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;
import java.lang.Math;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineReactorSmall;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class RenderSmallReactor extends TileEntitySpecialRenderer<TileEntityMachineReactorSmall>{
	
	@Override
	public boolean isGlobalRenderer(TileEntityMachineReactorSmall te) {
		return true;
	}
	
	@Override
	public void render(TileEntityMachineReactorSmall reactor, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
		GL11.glRotatef(180, 0F, 1F, 0F);
		

        bindTexture(ResourceManager.reactor_small_base_tex);
        ResourceManager.reactor_small_base.renderAll();
        
        
        GL11.glPushMatrix();
        GL11.glTranslated(0.0D, reactor.rods / 100D, 0.0D);
        bindTexture(ResourceManager.reactor_small_rods_tex);
        ResourceManager.reactor_small_rods.renderAll();
        GL11.glPopMatrix();
        
        if(reactor.coreHeat > 00 && reactor.isSubmerged()) {

	        GlStateManager.disableTexture2D();
	        GlStateManager.enableBlend();
	        GlStateManager.disableLighting();
			GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
	        GlStateManager.disableAlpha();

	        Tessellator tess = Tessellator.getInstance();
	        BufferBuilder buf = tess.getBuffer();

	        for(double d = 0.285; d < 0.7; d += 0.025) {

		        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		        float intensity = 0.002F + (0.003F * reactor.coreHeat / 1000F);
		        double top = 1.375;
		        double bottom = 1.375;

		        buf.pos(d, bottom - d, -d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();
		        buf.pos(d, top + d, -d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();
		        buf.pos(d, top + d, d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();
		        buf.pos(d, bottom - d, d).color(0.4F, 0.9F, 1.0F,intensity).endVertex();

		        buf.pos(-d, bottom - d, -d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();
		        buf.pos(-d, top + d, -d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();
		        buf.pos(-d, top + d, d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();
		        buf.pos(-d, bottom - d, d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();

		        buf.pos(-d, bottom - d, d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();
		        buf.pos(-d, top + d, d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();
		        buf.pos(d, top + d, d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();
		        buf.pos(d, bottom - d, d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();

		        buf.pos(-d, bottom - d, -d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();
		        buf.pos(-d, top + d, -d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();
		        buf.pos(d, top + d, -d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();
		        buf.pos(d, bottom - d, -d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();

		        buf.pos(-d, top + d, -d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();
		        buf.pos(-d, top + d, d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();
		        buf.pos(d, top + d, d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();
		        buf.pos(d, top + d, -d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();

		        buf.pos(-d, bottom - d, -d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();
		        buf.pos(-d, bottom - d, d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();
		        buf.pos(d, bottom - d, d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();
		        buf.pos(d, bottom - d, -d).color(0.4F, 0.9F, 1.0F, intensity).endVertex();

		        tess.draw();
	        }

	        GlStateManager.enableLighting();
	        GlStateManager.disableBlend();
	        GlStateManager.enableTexture2D();
        }

        GlStateManager.enableCull();

        GL11.glPopMatrix();
	}
}
