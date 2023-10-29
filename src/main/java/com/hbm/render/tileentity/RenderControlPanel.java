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

		switch (te.panelType) {
			case CUSTOM_PANEL: renderCustomPanel(te, x, y, z, partialTicks, destroyStage, alpha); break;
			case FRONT_PANEL: renderFrontPanel(te, x, y, z, partialTicks, destroyStage, alpha); break;
		}

		GlStateManager.enableRescaleNormal();
		GL11.glPushMatrix();
			GL11.glTranslated(-0.5, 0, -0.5);
			te.panel.transform.store(ClientProxy.AUX_GL_BUFFER);
			ClientProxy.AUX_GL_BUFFER.rewind();
			GL11.glMultMatrix(ClientProxy.AUX_GL_BUFFER);
			te.updateTransform();
			te.panel.render();
		GL11.glPopMatrix();
		GlStateManager.disableRescaleNormal();
		GL11.glPopMatrix();
	}

	public void renderCustomPanel(TileEntityControlPanel te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glTranslated(x+.5, y, z+.5);
		GL11.glPushMatrix();
		GL11.glRotatef(-90, 0, 0, 1);

		switch ((te.getBlockMetadata() & 3) + 2) {
			case 4:
				GL11.glRotatef(-180, 1, 0, 0); break;
			case 2:
				GL11.glRotatef(-90, 1, 0, 0); break;
			case 5:
				GL11.glRotatef(-0, 1, 0, 0); break;
			case 3:
				GL11.glRotatef(-270, 1, 0, 0); break;
		}

		boolean isUp = ((te.getBlockMetadata() >> 2) == 1);
		boolean isDown = ((te.getBlockMetadata() >> 3) == 1);

		if (isUp) {
			GL11.glRotatef(-180, 1, 0, 0);
			GL11.glRotatef(-90, 0, 0, 1);
			GL11.glTranslated(0, -1, 0);
		} else if (isDown) {
			GL11.glRotatef(90, 0, 0, 1);
		} else {
			GL11.glTranslated(-.5, -.5, 0);
		}

		GlStateManager.enableRescaleNormal();
//		bindTexture(ResourceManager.control_panel_custom_tex);

		GL11.glPushMatrix();
		GL11.glTranslated(-0.5, 0, -0.5);

		float a_off = te.panel.a_off;
		float b_off = te.panel.b_off;
		float c_off = te.panel.c_off;
		float d_off = te.panel.d_off;
		float height = te.panel.height;
		float angle = te.panel.angle;

		float height1 = ControlPanel.getSlopeHeightFromZ(1-c_off, height, -angle);
		float height0 = ControlPanel.getSlopeHeightFromZ(a_off, height, -angle);

		if (height != 0) {
			GlStateManager.disableLighting();
			ResourceLocation texxy = new ResourceLocation(RefStrings.MODID + ":textures/models/misc/control_panel.png");
			bindTexture(texxy);
			net.minecraft.client.renderer.Tessellator tess = net.minecraft.client.renderer.Tessellator.getInstance();
			BufferBuilder buf = tess.getBuffer();
			// back
			buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			buf.pos(a_off, 0, 1 - d_off).tex(1, 1).endVertex();
			buf.pos(a_off, height0, 1 - d_off).tex(1, 0).endVertex();
			buf.pos(a_off, height0, b_off).tex(0, 0).endVertex();
			buf.pos(a_off, 0, b_off).tex(0, 1).endVertex();
			// left
			buf.pos(1 - c_off, 0, 1 - d_off).tex(1, 1).endVertex();
			buf.pos(1 - c_off, height1, 1 - d_off).tex(1, 0).endVertex();
			buf.pos(a_off, height0, 1 - d_off).tex(0, 0).endVertex();
			buf.pos(a_off, 0, 1 - d_off).tex(0, 1).endVertex();
			// right
			buf.pos(a_off, 0, b_off).tex(1, 1).endVertex();
			buf.pos(a_off, height0, b_off).tex(1, 0).endVertex();
			buf.pos(1 - c_off, height1, b_off).tex(0, 0).endVertex();
			buf.pos(1 - c_off, 0, b_off).tex(0, 1).endVertex();
			// front
			buf.pos(1 - c_off, 0, b_off).tex(1, 1).endVertex();
			buf.pos(1 - c_off, height1, b_off).tex(1, 0).endVertex();
			buf.pos(1 - c_off, height1, 1 - d_off).tex(0, 0).endVertex();
			buf.pos(1 - c_off, 0, 1 - d_off).tex(0, 1).endVertex();
//			// top
			buf.pos(1 - c_off, height1, b_off).tex(1, 1).endVertex();
			buf.pos(a_off, height0, b_off).tex(1, 0).endVertex();
			buf.pos(a_off, height0, 1 - d_off).tex(0, 0).endVertex();
			buf.pos(1 - c_off, height1, 1 - d_off).tex(0, 1).endVertex();
			// bottom
			buf.pos(1 - c_off, 0, 1 - d_off).tex(0, 1).endVertex();
			buf.pos(a_off, 0, 1 - d_off).tex(0, 0).endVertex();
			buf.pos(a_off, 0, b_off).tex(1, 0).endVertex();
			buf.pos(1 - c_off, 0, b_off).tex(1, 1).endVertex();
			tess.draw();
		}

		GlStateManager.enableLighting();
		GlStateManager.disableRescaleNormal();
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	public void renderFrontPanel(TileEntityControlPanel te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GL11.glTranslated(x+.5F, y, z+.5F);
        GL11.glPushMatrix();

        switch(te.getBlockMetadata()) {
            case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
            case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
            case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
            case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
        }

		GL11.glPushMatrix();
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/control_panel/control_panel_front.png"));
            ResourceManager.control_panel_front.renderAll();
        GL11.glPopMatrix();

        GL11.glPopMatrix();
	}

}
