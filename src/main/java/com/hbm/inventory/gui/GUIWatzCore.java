package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.container.ContainerWatzCore;
import com.hbm.lib.RefStrings;
import com.hbm.lib.Library;
import com.hbm.tileentity.machine.TileEntityWatzCore;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIWatzCore extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_watz_multiblock.png");
	private TileEntityWatzCore diFurnace;

	public GUIWatzCore(EntityPlayer invPlayer, TileEntityWatzCore tedf) {
		super(new ContainerWatzCore(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		//this.ySize = 222;
		this.ySize = 256;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 17, 16, 70, diFurnace.tank, diFurnace.tankType);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 17, 16, 70, diFurnace.power, TileEntityWatzCore.maxPower);
		super.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2 - 34, 4210752);
		this.fontRenderer.drawString(Library.getShortNumber(diFurnace.powerList * 20) + " HE/s", 8, this.ySize - 50 + 2 + 13, 4210752);
		this.fontRenderer.drawString(String.valueOf(diFurnace.heatList + " heat"), 8, this.ySize - 50 + 2 + 22, 4210752);
		this.fontRenderer.drawString(Library.getShortNumber((long)(diFurnace.decayMultiplier * diFurnace.heat * 0.002D)) + " mB/s", 8, this.ySize - 50 + 2 + 31, 4210752);
		this.fontRenderer.drawString(String.valueOf(diFurnace.powerMultiplier + "% power"), 100, this.ySize - 50 + 2 + 13, 4210752);
		this.fontRenderer.drawString(String.valueOf(diFurnace.heatMultiplier + "% heat"), 100, this.ySize - 50 + 2 + 22, 4210752);
		this.fontRenderer.drawString(String.valueOf(diFurnace.decayMultiplier + "% decay"), 100, this.ySize - 50 + 2 + 31, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int l = (int)diFurnace.getPowerScaled(70);
		drawTexturedModalRect(guiLeft + 152, guiTop + 106 - 18 - l, 192, 70 - l, 16, l);
		
		FFUtils.drawLiquid(diFurnace.tank, guiLeft, guiTop, zLevel, 16, 70, 134, 116);
	}
}