package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.lib.Library;
import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.network.energy.TileEntityCableBaseNT;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderCable extends TileEntitySpecialRenderer<TileEntityCableBaseNT> {
	
	@Override
	public void render(TileEntityCableBaseNT te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if(te.getBlockType() != ModBlocks.red_cable)
			return;
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5F, y + 0.5F, z + 0.5F);
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		bindTexture(ResourceManager.cable_neo_tex);

		boolean pX = Library.canConnect(te.getWorld(), te.getPos().add(1, 0, 0), Library.POS_X);
		boolean nX = Library.canConnect(te.getWorld(), te.getPos().add(-1, 0, 0), Library.NEG_X);
		boolean pY = Library.canConnect(te.getWorld(), te.getPos().add(0, 1, 0), Library.POS_Y);
		boolean nY = Library.canConnect(te.getWorld(), te.getPos().add(0, -1, 0), Library.NEG_Y);
		boolean pZ = Library.canConnect(te.getWorld(), te.getPos().add(0, 0, 1), Library.POS_Z);
		boolean nZ = Library.canConnect(te.getWorld(), te.getPos().add(0, 0, -1), Library.NEG_Z);

		if(pX && nX && !pY && !nY && !pZ && !nZ)
			ResourceManager.cable_neo.renderPart("CX");
		else if(!pX && !nX && pY && nY && !pZ && !nZ)
			ResourceManager.cable_neo.renderPart("CY");
		else if(!pX && !nX && !pY && !nY && pZ && nZ)
			ResourceManager.cable_neo.renderPart("CZ");
		else{
			ResourceManager.cable_neo.renderPart("Core");
			if(pX) ResourceManager.cable_neo.renderPart("posX");
			if(nX) ResourceManager.cable_neo.renderPart("negX");
			if(pY) ResourceManager.cable_neo.renderPart("posY");
			if(nY) ResourceManager.cable_neo.renderPart("negY");
			if(pZ) ResourceManager.cable_neo.renderPart("negZ");
			if(nZ) ResourceManager.cable_neo.renderPart("posZ");
		}

		GL11.glTranslated(-x - 0.5F, -y - 0.5F, -z - 0.5F);
		GL11.glPopMatrix();
	}
	
	// Bob: Muehsam muss ich hier im BSH meine genialen Mods schreiben, obwohl ich die Zeit eigentlich doch besser nutzen koennte.
	// Da mir das aber Spass macht, wird auch in Zukunft gutes Zeug von mir geben (und damit meine ich NICHT Drogen, etc.)
	// Danke.
	
	// Drill: I didn't write this, but I'm gonna leave it there.
	
	// Alcater: Bob alle Achtung, ich ziehe meinen Hut vor Dir.
}
