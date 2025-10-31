package com.hbm.inventory.gui;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.container.ContainerMachineGasCent;
import com.hbm.lib.RefStrings;
import com.leafia.contents.machines.processing.gascent.GasCentTE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GUIMachineGasCent extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_centrifuge_gas.png");
	private GasCentTE diFurnace;
	
	public GUIMachineGasCent(InventoryPlayer invPlayer, GasCentTE tedf) {
		super(new ContainerMachineGasCent(invPlayer, tedf));
		diFurnace = tedf;

		this.xSize = 206;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 16, guiTop + 16, 22, 52, diFurnace.tank0);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 138, guiTop + 16, 22, 52, diFurnace.tank1);

		//this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 92, guiTop + 15, 28, 54, mouseX, mouseY, new String[] {String.valueOf((int)((double)diFurnace.progress / (double)TileEntityMachineGasCent.processingSpeed * 100D)) + "%"});
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 182, guiTop + 17, 16, 52, diFurnace.power, GasCentTE.maxPower);
		super.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		//String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		//this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int i = (int)diFurnace.getPowerRemainingScaled(52);
		drawTexturedModalRect(guiLeft + 182, guiTop + 69 - i, 206, 52 - i, 16, i);

		int j = (int)diFurnace.getCentrifugeProgressScaled(36);
		drawTexturedModalRect(guiLeft + 70, guiTop + 35, 206,52, j, 13);
		

		FFUtils.drawLiquid(diFurnace.tank0, guiLeft, guiTop, zLevel, 6, 52, 16, 16+80);
		FFUtils.drawLiquid(diFurnace.tank0, guiLeft, guiTop, zLevel, 6, 52, 32, 16+80);
		FFUtils.drawLiquid(diFurnace.tank1, guiLeft, guiTop, zLevel, 6, 52, 138, 16+80);
		FFUtils.drawLiquid(diFurnace.tank1, guiLeft, guiTop, zLevel, 6, 52, 154, 16+80);
	}
}
