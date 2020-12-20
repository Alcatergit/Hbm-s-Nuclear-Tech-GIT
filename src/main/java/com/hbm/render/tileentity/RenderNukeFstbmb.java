package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.RenderMiscEffects;
import com.hbm.tileentity.bomb.TileEntityNukeBalefire;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderNukeFstbmb extends TileEntitySpecialRenderer<TileEntityNukeBalefire> {

	@Override
	public boolean isGlobalRenderer(TileEntityNukeBalefire te) {
		return true;
	}
	
	@Override
	public void render(TileEntityNukeBalefire bf, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
		switch(bf.getBlockMetadata())
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

        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        bindTexture(ResourceManager.fstbmb_tex);
        ResourceManager.fstbmb.renderPart("Body");
        ResourceManager.fstbmb.renderPart("Balefire");


        if(bf.loaded) {
	        bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/misc/glintBF.png"));
	        RenderMiscEffects.renderClassicGlint(bf.getWorld(), partialTicks, ResourceManager.fstbmb, "Balefire", 0.0F, 0.8F, 0.15F, 5, 2F);

	        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
	        float f3 = 0.04F;
	        GL11.glTranslatef(0.815F, 0.9275F, 0.5F);
	        GL11.glScalef(f3, -f3, f3);
	        GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);
	        GL11.glRotatef(90, 0, 1, 0);
	        GlStateManager.depthMask(false);
	        GL11.glTranslatef(0, 1, 0);
	        font.drawString(bf.getMinutes() + ":" + bf.getSeconds(), 0, 0, 0xff0000);
	        GlStateManager.depthMask(true);
        }

        GlStateManager.shadeModel(GL11.GL_FLAT);

        GL11.glPopMatrix();
	}
}
