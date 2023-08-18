package com.hbm.render.tileentity;

import com.hbm.inventory.control_panel.ControlPanel;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import com.hbm.main.ClientProxy;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityControlPanel;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderControlPanel extends TileEntitySpecialRenderer<TileEntityControlPanel> {

	@Override
	public void render(TileEntityControlPanel te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslated(x+0.5, y, z+0.5);
		GL11.glPushMatrix();
		switch (te.getBlockMetadata()) {
			case 4:
				GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 2:
				GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 5:
				GL11.glRotatef(0, 0F, 1F, 0F); break;
			case 3:
				GL11.glRotatef(270, 0F, 1F, 0F); break;
		}
		GlStateManager.enableRescaleNormal();
		bindTexture(ResourceManager.control_panel0_tex);
//		ResourceManager.control_panel0.renderAll();

		GL11.glPushMatrix();
		GL11.glTranslated(-0.5, 0, -0.5);

//		TODO:
//		------------/ TEMP /-------------
//		float a_off = 0;
//		float b_off = 0;
//		float c_off = 0;
//		float d_off = 0;
//		float height = 0.5F;
//		float angle = (float) (Math.PI/8F); // RADIANS
//		---------------------------------

		// lazy
		float a_off = te.panel.a_off;
		float b_off = te.panel.b_off;
		float c_off = te.panel.c_off;
		float d_off = te.panel.d_off;
		float height = te.panel.height;
		float angle = te.panel.angle;

		float height1 = ControlPanel.getSlopeHeightFromZ(1-c_off, height, -angle);
		float height0 = ControlPanel.getSlopeHeightFromZ(a_off, height, -angle);

		GlStateManager.disableLighting();
		ResourceLocation texxy = new ResourceLocation(RefStrings.MODID + ":textures/models/misc/control_panel.png");
		bindTexture(texxy);
		net.minecraft.client.renderer.Tessellator tess = net.minecraft.client.renderer.Tessellator.getInstance();
		BufferBuilder buf = tess.getBuffer();
		// back
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buf.pos(a_off, 0, 1-d_off).tex(1, 1).endVertex();
		buf.pos(a_off, height0, 1-d_off).tex(1, 0).endVertex();
		buf.pos(a_off, height0, b_off).tex(0, 0).endVertex();
		buf.pos(a_off, 0, b_off).tex(0, 1).endVertex();
		// left
		buf.pos(1-c_off, 0, 1-d_off).tex(1, 1).endVertex();
		buf.pos(1-c_off, height1, 1-d_off).tex(1, 0).endVertex();
		buf.pos(a_off, height0, 1-d_off).tex(0, 0).endVertex();
		buf.pos(a_off, 0, 1-d_off).tex(0, 1).endVertex();
		// right
		buf.pos(a_off, 0, b_off).tex(1, 1).endVertex();
		buf.pos(a_off, height0, b_off).tex(1, 0).endVertex();
		buf.pos(1-c_off, height1, b_off).tex(0, 0).endVertex();
		buf.pos(1-c_off, 0, b_off).tex(0, 1).endVertex();
		// front
		buf.pos(1-c_off, 0, b_off).tex(1, 1).endVertex();
		buf.pos(1-c_off, height1, b_off).tex(1, 0).endVertex();
		buf.pos(1-c_off, height1, 1-d_off).tex(0, 0).endVertex();
		buf.pos(1-c_off, 0, 1-d_off).tex(0, 1).endVertex();
		// top
		buf.pos(1-c_off, height1, b_off).tex(1, 1).endVertex();
		buf.pos(a_off, height0, b_off).tex(1, 0).endVertex();
		buf.pos(a_off, height0, 1-d_off).tex(0, 0).endVertex();
		buf.pos(1-c_off, height1, 1-d_off).tex(0, 1).endVertex();
		tess.draw();
		GlStateManager.enableLighting();




		GL11.glPopMatrix();





		GL11.glPopMatrix();

		GL11.glTranslated(-0.5, 0, -0.5);
		te.panel.transform.store(ClientProxy.AUX_GL_BUFFER);
		ClientProxy.AUX_GL_BUFFER.rewind();
		GL11.glMultMatrix(ClientProxy.AUX_GL_BUFFER);
		te.updateTransform();
		te.panel.render();
		GlStateManager.disableRescaleNormal();
		GL11.glPopMatrix();
	}
}
