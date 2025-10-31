package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.misc.MissilePronter;
import com.hbm.tileentity.bomb.TileEntityCompactLauncher;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderCompactLauncher extends TileEntitySpecialRenderer<TileEntityCompactLauncher> {

	@Override
	public boolean isGlobalRenderer(TileEntityCompactLauncher te) {
		return true;
	}
	
	@Override
	public void render(TileEntityCompactLauncher launcher, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		bindTexture(ResourceManager.compact_launcher_tex);
		ResourceManager.compact_launcher.renderAll();
		
		GL11.glTranslatef(0F, 1.0625F, 0F);
		
		/// DRAW MISSILE START
		GL11.glPushMatrix();
		if(launcher.load != null && launcher.clearingTimer == 0) {
			//ItemStack custom = launcher.getStackInSlot(0);
			
			//missile = ItemCustomMissile.getMultipart(custom);
			
			MissilePronter.prontMissile(launcher.load.multipart(), Minecraft.getMinecraft().getTextureManager());
			//
		}
		
		GL11.glPopMatrix();
		/// DRAW MISSILE END

		GL11.glPopMatrix();
	}
}
