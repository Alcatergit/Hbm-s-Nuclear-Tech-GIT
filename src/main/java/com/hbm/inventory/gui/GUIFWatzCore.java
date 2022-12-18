package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.container.ContainerFWatzCore;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityFWatzCore;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIFWatzCore extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_fwatz_multiblock.png");
	private TileEntityFWatzCore diFurnace;

	public GUIFWatzCore(InventoryPlayer invPlayer, TileEntityFWatzCore tedf) {
		super(new ContainerFWatzCore(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 53, guiTop + 85, 70, 18, diFurnace.tanks[0], diFurnace.tankTypes[0]);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 7, guiTop + 17, 18, 72, diFurnace.tanks[1], diFurnace.tankTypes[1]);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 151, guiTop + 17, 18, 72, diFurnace.tanks[2], diFurnace.tankTypes[2]);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 29, guiTop + 107, 118, 18, diFurnace.power, TileEntityFWatzCore.maxPower);
		super.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int k = (int)diFurnace.getPowerScaled(118);
		drawTexturedModalRect(guiLeft + 30, guiTop + 108, 0, 222, k, 16);
		
		if(diFurnace.isRunning())
			drawTexturedModalRect(guiLeft + 64, guiTop + 29, 176, 24, 48, 48);
		
		int m = diFurnace.getSingularityType();
		drawTexturedModalRect(guiLeft + 80, guiTop + 20, 176, 4 * m, 16, 4);
		
		FFUtils.drawLiquid(diFurnace.tanks[0], guiLeft, guiTop, zLevel, 68, 16, 54, 130);
		FFUtils.drawLiquid(diFurnace.tanks[1], guiLeft, guiTop, zLevel, 16, 70, 8, 116);
		FFUtils.drawLiquid(diFurnace.tanks[2], guiLeft, guiTop, zLevel, 16, 70, 152, 116);
	}
}