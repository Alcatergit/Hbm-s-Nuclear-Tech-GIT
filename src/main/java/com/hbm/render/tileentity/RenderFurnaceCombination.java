package com.hbm.render.tileentity;

import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.Tessellator;
import com.hbm.tileentity.machine.TileEntityFurnaceCombination;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderFurnaceCombination extends TileEntitySpecialRenderer<TileEntityFurnaceCombination> {

	public static final ResourceLocation texture_fire = new ResourceLocation(RefStrings.MODID + ":textures/particle/rbmk_fire.png");

	@Override
	public void render(TileEntityFurnaceCombination furnace, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		bindTexture(ResourceManager.combination_oven_tex);
		ResourceManager.combination_oven.renderAll();

		GL11.glPopMatrix();
	}
}
