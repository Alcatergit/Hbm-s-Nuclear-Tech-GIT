package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerCoreAdvanced;
import com.hbm.lib.RefStrings;
import com.hbm.lib.Library;
import com.hbm.tileentity.machine.TileEntityCoreAdvanced;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUICoreAdvanced extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/factory_advanced.png");
	private TileEntityCoreAdvanced diFurnace;

	public GUICoreAdvanced(InventoryPlayer invPlayer, TileEntityCoreAdvanced tedf) {
		super(new ContainerCoreAdvanced(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 0x222222);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 0x222222);
		String thing = "0 HE/s";
		if(diFurnace.power < diFurnace.getMaxPower())
			thing = Library.getShortNumber(diFurnace.progressStep * TileEntityCoreAdvanced.powerPerStep * 20) + " HE/s";
			this.fontRenderer.drawString(thing, this.xSize-60-this.fontRenderer.getStringWidth(thing), 41, 0x222222);

		this.fontRenderer.drawString("Speed:", 60, 95, 0x222222);
		this.fontRenderer.drawString(diFurnace.progressStep+"", this.xSize-60-this.fontRenderer.getStringWidth(diFurnace.progressStep+""), 95, 0x222222);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		String[] text = new String[] { "Basically just a larger furnace with a queue and extreme speed.", "Use a factory cluster to keep the current speed." };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, text);
		
		String[] text1 = new String[] { "Max production: 80/s at 16M HE/s and speed 400" };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 16, text1);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 62, guiTop + 71, 52, 16, diFurnace.power, diFurnace.getMaxPower());
		super.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(diFurnace.power > 0) {
			int i = (int)diFurnace.getPowerScaled(52);
			drawTexturedModalRect(guiLeft + 62, guiTop + 72, 0, 240, i, 16);
		}
		
		if(diFurnace.progress > 0) {
			int j = diFurnace.getProgressScaled(90);
			drawTexturedModalRect(guiLeft + 43, guiTop + 53, 0, 222, j, 18);
		}

		this.drawInfoPanel(guiLeft - 16, guiTop + 36, 16, 16, 2);
		this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 16, 16, 16, 3);
	}
}