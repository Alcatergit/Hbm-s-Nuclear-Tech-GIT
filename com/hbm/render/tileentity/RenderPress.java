package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachinePress;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderPress extends TileEntitySpecialRenderer<TileEntityMachinePress> {
	
	public RenderPress() {
		super();
	}
	@Override
	public void render(TileEntityMachinePress te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glRotatef(180, 0F, 1F, 0F);
		this.bindTexture(ResourceManager.press_body_tex);
		
		ResourceManager.press_body.renderAll();
			
	GL11.glPopMatrix();
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
	}
	

}
