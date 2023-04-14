package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineFENSU;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderFENSU extends TileEntitySpecialRenderer<TileEntityMachineFENSU> {

	@Override
	public void render(TileEntityMachineFENSU te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();

		GL11.glTranslatef((float)x + 0.5F, (float)y, (float)z + 0.5F);

		GlStateManager.enableCull();
		GlStateManager.enableLighting();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);

		switch(te.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

        

        TileEntityMachineFENSU fensu = (TileEntityMachineFENSU)te;
        bindTexture(ResourceManager.fensu_tex[fensu.color.getMetadata()]);
        ResourceManager.fensu.renderPart("Base");
        float rot = fensu.prevRotation + (fensu.rotation - fensu.prevRotation) * partialTicks;

        GL11.glTranslated(0, 2.5, 0);
        GL11.glRotated(rot, 1, 0, 0);
        GL11.glTranslated(0, -2.5, 0);
        ResourceManager.fensu.renderPart("Disc");

        GL11.glPushMatrix();

	        GlStateManager.disableLighting();
	        GlStateManager.disableCull();
	        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
	        ResourceManager.fensu.renderPart("Lights");
	        GlStateManager.enableCull();
	        GlStateManager.enableLighting();

        GL11.glPopMatrix();

		GlStateManager.shadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}
}
