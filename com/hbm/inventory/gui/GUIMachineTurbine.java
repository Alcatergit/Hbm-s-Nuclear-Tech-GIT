package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.container.ContainerMachineTurbine;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineTurbine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineTurbine extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_turbine.png");
	private TileEntityMachineTurbine diFurnace;
	
	public GUIMachineTurbine(InventoryPlayer invPlayer, TileEntityMachineTurbine tedf) {
		super(new ContainerMachineTurbine(invPlayer, tedf));
		diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 62, guiTop + 69 - 52, 16, 52, diFurnace.tanks[0], diFurnace.tankTypes[0]);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 69 - 52, 16, 52, diFurnace.tanks[1], diFurnace.tankTypes[1]);
		
	//	if(diFurnace.tanks[1].getTankType().name().equals(FluidType.NONE.name())) {
	//		
	//		String[] text2 = new String[] { "Error: Invalid fluid!" };
	//		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 32, 16, 16, guiLeft - 8, guiTop + 36 + 16 + 32, text2);
	//	}
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 123, guiTop + 69 - 34, 7, 34, diFurnace.power, TileEntityMachineTurbine.maxPower);
		this.renderHoveredToolTip(mouseX, mouseY);
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

		if(diFurnace.tankTypes[0] == ModForgeFluids.steam) {
			drawTexturedModalRect(guiLeft + 99, guiTop + 18, 183, 0, 14, 14);
		}
		if(diFurnace.tankTypes[0] == ModForgeFluids.hotsteam) {
			drawTexturedModalRect(guiLeft + 99, guiTop + 18, 183, 14, 14, 14);
		}
		if(diFurnace.tankTypes[0] == ModForgeFluids.superhotsteam) {
			drawTexturedModalRect(guiLeft + 99, guiTop + 18, 183, 28, 14, 14);
		}
		if(diFurnace.tankTypes[0] == ModForgeFluids.ultrahotsteam) {
			drawTexturedModalRect(guiLeft + 99, guiTop + 18, 183, 42, 14, 14);
		}

		int i = (int)diFurnace.getPowerScaled(34);
		drawTexturedModalRect(guiLeft + 123, guiTop + 69 - i, 176, 34 - i, 7, i);
		
	//	if(diFurnace.tanks[1].getTankType().name().equals(FluidType.NONE.name())) {
	//		this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 32, 16, 16, 6);
	//	}
		
		
		FFUtils.drawLiquid(diFurnace.tanks[0], guiLeft, guiTop, zLevel, 16, 52, 62, 97);
		FFUtils.drawLiquid(diFurnace.tanks[1], guiLeft, guiTop, zLevel, 16, 52, 134, 97);
	}
}
