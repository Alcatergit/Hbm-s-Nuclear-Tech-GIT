package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.container.ContainerMachineGasCent;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineGasCent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineGasCent extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/centrifuge_gas.png");
	private TileEntityMachineGasCent diFurnace;
	
	public GUIMachineGasCent(InventoryPlayer invPlayer, TileEntityMachineGasCent tedf) {
		super(new ContainerMachineGasCent(invPlayer, tedf));
		diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 34, guiTop + 15, 22, 52, diFurnace.tank);

		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 16, 16, 52, diFurnace.power, TileEntityMachineGasCent.maxPower);
		super.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int i = (int)diFurnace.getPowerRemainingScaled(52);
		drawTexturedModalRect(guiLeft + 152, guiTop + 69 - i, 176, 52 - i, 16, i);

		int j = (int)diFurnace.getCentrifugeProgressScaled(37);
		drawTexturedModalRect(guiLeft + 88, guiTop + 36, 192, 0, j, 13);
		

		FFUtils.drawLiquid(diFurnace.tank, guiLeft, guiTop, zLevel, 6, 52, 34, 96);
        FFUtils.drawLiquid(diFurnace.tank, guiLeft, guiTop, zLevel, 6, 52, 50, 96);
	}
}
