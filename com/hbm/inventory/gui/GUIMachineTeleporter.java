package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineTeleporter;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineTeleporter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineTeleporter extends GuiContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_teleporter.png");
	private TileEntityMachineTeleporter diFurnace;

	public GUIMachineTeleporter(InventoryPlayer invPlayer, TileEntityMachineTeleporter tedf) {
		super(new ContainerMachineTeleporter(invPlayer, tedf));
		diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 86;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		super.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format("container.teleporter");

		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString("Power: " + diFurnace.power + "HE/" + TileEntityMachineTeleporter.maxPower + "HE", 10, 20, 13882323);
		this.fontRenderer.drawString("Mode: " + (diFurnace.mode ? "Send" : "Receive"), 10, 29, 13882323);
		if(diFurnace.mode && diFurnace.target != null) {
			this.fontRenderer.drawString("Destination X: " + diFurnace.target.getX(), 10, 38, 13882323);
			this.fontRenderer.drawString("Destination Y: " + diFurnace.target.getY(), 10, 47, 13882323);
			this.fontRenderer.drawString("Destination Z: " + diFurnace.target.getZ(), 10, 56, 13882323);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
