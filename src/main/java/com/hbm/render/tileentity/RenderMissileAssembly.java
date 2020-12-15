package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.misc.MissileMultipart;
import com.hbm.render.misc.MissilePart;
import com.hbm.render.misc.MissilePronter;
import com.hbm.tileentity.machine.TileEntityMachineMissileAssembly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderMissileAssembly extends TileEntitySpecialRenderer<TileEntityMachineMissileAssembly> {

	@Override
	public boolean isGlobalRenderer(TileEntityMachineMissileAssembly te) {
		return true;
	}
	
	@Override
	public void render(TileEntityMachineMissileAssembly te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		
		GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
		GlStateManager.disableCull();
		
		switch(te.getBlockMetadata()) {
		case 2:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		}

		bindTexture(ResourceManager.missile_assembly_tex);
		ResourceManager.missile_assembly.renderAll();

		MissileMultipart missile = MissileMultipart.loadFromStruct(te.load);
		
		if(missile != null) {
		
			if(te.inventory.getStackInSlot(1) != null)
				missile.warhead = MissilePart.getPart(te.inventory.getStackInSlot(1).getItem());
			
			if(te.inventory.getStackInSlot(2) != null)
				missile.fuselage = MissilePart.getPart(te.inventory.getStackInSlot(2).getItem());
			
			if(te.inventory.getStackInSlot(3) != null)
				missile.fins = MissilePart.getPart(te.inventory.getStackInSlot(3).getItem());
			
			if(te.inventory.getStackInSlot(4) != null)
				missile.thruster = MissilePart.getPart(te.inventory.getStackInSlot(4).getItem());
			
			int range = (int) (missile.getHeight() / 2 - 1);
			
			int step = 1;
			
			if(range >= 2)
				step = 2;
			
			for(int i = -range; i <= range; i += step) {
	
				if(i != 0) {
					GL11.glTranslatef(i, 0F, 0F);
					bindTexture(ResourceManager.strut_tex);
					ResourceManager.strut.renderAll();
					GL11.glTranslatef(-i, 0F, 0F);
				}
			}
	
			GL11.glTranslatef(0F, 1.5F, 0F);
			GL11.glRotatef(180, 0F, 0F, 1F);
			
			GL11.glTranslated(-missile.getHeight() / 2, 0, 0);
			//GL11.glScaled(scale, scale, scale);
			
			GL11.glRotatef(-90, 1, 0, 0);
			GL11.glRotatef(-90, 0, 0, 1);
			GL11.glScalef(1, 1, 1);
	
			GlStateManager.enableCull();
			MissilePronter.prontMissile(missile, Minecraft.getMinecraft().getTextureManager());
		}
		
		GlStateManager.enableCull();
		GL11.glPopMatrix();
	}
}
