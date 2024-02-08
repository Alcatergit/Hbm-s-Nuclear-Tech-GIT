package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.machine.Radiobox;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelRadio;
import com.hbm.tileentity.machine.TileEntityRadiobox;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderRadiobox extends TileEntitySpecialRenderer<TileEntityRadiobox> {

	private static final ResourceLocation texture7 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/turrets/ModelRadio.png");
	private ModelRadio model7;
	
	public RenderRadiobox() {
		this.model7 = new ModelRadio();
	}
	
	@Override
	public void render(TileEntityRadiobox te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotatef(180, 0F, 0F, 1F);
		GlStateManager.enableLighting();
		GL11.glEnable(GL11.GL_LIGHTING);
		this.bindTexture(texture7);
		switch(te.getWorld().getBlockState(te.getPos()).getValue(Radiobox.FACING))
		{
		case WEST:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case NORTH:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		case EAST:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case SOUTH:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		default:
			break;
		}
		GL11.glTranslatef(0, 0, 1);
		this.model7.renderModel(0.0625F, te.getWorld().getBlockState(te.getPos()).getValue(Radiobox.STATE) ? 160 : 20);
		GL11.glPopMatrix();
	}
}
