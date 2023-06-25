package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMixer;
import com.hbm.lib.RefStrings;
import com.hbm.forgefluid.FFUtils;
import com.hbm.tileentity.machine.TileEntityMachineMixer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMixer extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_mixer.png");
	private TileEntityMachineMixer mixer;

	public GUIMixer(InventoryPlayer player, TileEntityMachineMixer mixer) {
		super(new ContainerMixer(player, mixer));
		this.mixer = mixer;
		
		this.xSize = 176;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 23, guiTop + 22, 16, 52, mixer.getPower(), mixer.getMaxPower());

		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 43, guiTop + 22, 7, 52, mixer.tanks[0]);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 52, guiTop + 22, 7, 52, mixer.tanks[1]);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 117, guiTop + 22, 16, 52, mixer.tanks[2], mixer.outputFluid);
		super.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(mixer.getName());
		
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = (int) (mixer.getPower() * 53 / mixer.getMaxPower());
		drawTexturedModalRect(guiLeft + 23, guiTop + 75 - i, 176, 52 - i, 16, i);
		
		if(mixer.processTime > 0 && mixer.progress > 0) {
			int j = mixer.progress * 53 / mixer.processTime;
			drawTexturedModalRect(guiLeft + 62, guiTop + 36, 192, 0, j, 44);
		}
		
		FFUtils.drawLiquid(mixer.tanks[0], guiLeft, guiTop, zLevel, 7, 52, 43, 103, mixer.uuMixer);
		FFUtils.drawLiquid(mixer.tanks[1], guiLeft, guiTop, zLevel, 7, 52, 52, 103, mixer.uuMixer);
		FFUtils.drawLiquid(mixer.tanks[2], guiLeft, guiTop, zLevel, 16, 52, 117, 103, mixer.uuMixer);
	}
}
