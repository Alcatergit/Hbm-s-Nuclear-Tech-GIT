package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.container.ContainerMachineGasFlare;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.oil.TileEntityMachineGasFlare;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineGasFlare extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_gasFlare.png");
	private TileEntityMachineGasFlare flare;
	
	public GUIMachineGasFlare(InventoryPlayer invPlayer, TileEntityMachineGasFlare tedf) {
		super(new ContainerMachineGasFlare(invPlayer, tedf));
		flare = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 69 - 52, 34, 52, flare.tank, flare.tankType);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 69 - 52, 16, 52, flare.power, TileEntityMachineGasFlare.maxPower);
		super.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.flare.hasCustomInventoryName() ? this.flare.getInventoryName() : I18n.format(this.flare.getInventoryName());
		
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int j = (int)flare.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 8, guiTop + 69 - j, 176, 52 - j, 16, j);
		
		FFUtils.drawLiquid(flare.tank, guiLeft, guiTop, zLevel, 34, 52, 80, 97);
	}
}
