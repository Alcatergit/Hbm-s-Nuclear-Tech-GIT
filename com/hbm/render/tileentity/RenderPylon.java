package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelPylon;
import com.hbm.tileentity.machine.TileEntityPylonRedWire;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderPylon extends TileEntitySpecialRenderer<TileEntityPylonRedWire> {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/ModelPylon.png");

	private ModelPylon pylon;

	public RenderPylon() {
		this.pylon = new ModelPylon();
	}

	@Override
	public boolean isGlobalRenderer(TileEntityPylonRedWire te) {
		return true;
	}

	@Override
	public void render(TileEntityPylonRedWire pyl, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F - ((1F / 16F) * 14F), (float) z + 0.5F);
		GL11.glRotatef(180, 0F, 0F, 1F);
		GlStateManager.disableLighting();
		GlStateManager.enableLighting();
		bindTexture(texture);
		this.pylon.renderAll(0.0625F);
		GL11.glPopMatrix();
		GL11.glPushMatrix();

		for (int i = 0; i < pyl.connected.size(); i++) {

			TileEntityPylonRedWire wire = pyl.connected.get(i);

			float wX = (wire.getPos().getX() - pyl.getPos().getX()) / 2F;
			float wY = (wire.getPos().getY() - pyl.getPos().getY()) / 2F;
			float wZ = (wire.getPos().getZ() - pyl.getPos().getZ()) / 2F;

			float count = 10;
			for (float j = 0; j < count; j++) {

				float k = j + 1;

				drawPowerLine(x + 0.5 + (wX * j / count), 
						y + 5.4 + (wY * j / count) - Math.sin(j / count * Math.PI * 0.5), z + 0.5 + (wZ * j / count),
						x + 0.5 + (wX * k / count), 
						y + 5.4 + (wY * k / count) - Math.sin(k / count * Math.PI * 0.5), 
						z + 0.5 + (wZ * k / count));
			}
		}
		GL11.glPopMatrix();
	}

	public void drawPowerLine(double x, double y, double z, double a, double b, double c) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GlStateManager.disableLighting();
		GL11.glDisable(GL11.GL_CULL_FACE);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buf = tessellator.getBuffer();
		buf.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR);
		// tessellator.setColorRGBA_F(0.683F, 0.089F, 0.0F, 1.0F);
		buf.pos(x, y + 0.05F, z).color(0.73333333333F, 0.08235294117F, 0.06666666666F, 1.0F).endVertex();
		buf.pos(x, y - 0.05F, z).color(0.73333333333F, 0.08235294117F, 0.06666666666F, 1.0F).endVertex();
		buf.pos(a, b + 0.05F, c).color(0.73333333333F, 0.08235294117F, 0.06666666666F, 1.0F).endVertex();
		buf.pos(a, b - 0.05F, c).color(0.73333333333F, 0.08235294117F, 0.06666666666F, 1.0F).endVertex();
		tessellator.draw();
		buf.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR);
		// tessellator.setColorRGBA_F(0.683F, 0.089F, 0.0F, 1.0F);
		buf.pos(x + 0.05F, y, z).color(0.73333333333F, 0.08235294117F, 0.06666666666F, 1.0F).endVertex();
		buf.pos(x - 0.05F, y, z).color(0.73333333333F, 0.08235294117F, 0.06666666666F, 1.0F).endVertex();
		buf.pos(a + 0.05F, b, c).color(0.73333333333F, 0.08235294117F, 0.06666666666F, 1.0F).endVertex();
		buf.pos(a - 0.05F, b, c).color(0.73333333333F, 0.08235294117F, 0.06666666666F, 1.0F).endVertex();
		tessellator.draw();
		buf.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR);
		// tessellator.setColorRGBA_F(0.683F, 0.089F, 0.0F, 1.0F);
		buf.pos(x, y, z + 0.05F).color(0.73333333333F, 0.08235294117F, 0.06666666666F, 1.0F).endVertex();
		buf.pos(x, y, z - 0.05F).color(0.73333333333F, 0.08235294117F, 0.06666666666F, 1.0F).endVertex();
		buf.pos(a, b, c + 0.05F).color(0.73333333333F, 0.08235294117F, 0.06666666666F, 1.0F).endVertex();
		buf.pos(a, b, c - 0.05F).color(0.73333333333F, 0.08235294117F, 0.06666666666F, 1.0F).endVertex();
		tessellator.draw();
		GlStateManager.enableLighting();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}
}
