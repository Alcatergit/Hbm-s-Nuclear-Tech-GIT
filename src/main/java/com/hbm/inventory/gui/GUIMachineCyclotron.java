package com.hbm.inventory.gui;

import java.io.IOException;

import com.hbm.util.I18nUtil;
import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.container.ContainerMachineCyclotron;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineCyclotron;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

public class GUIMachineCyclotron extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_cyclotron.png");
	private TileEntityMachineCyclotron cyclotron;

	public GUIMachineCyclotron(InventoryPlayer invPlayer, TileEntityMachineCyclotron tile) {
		super(new ContainerMachineCyclotron(invPlayer, tile));
		cyclotron = tile;

		this.xSize = 176;
		this.ySize = 222;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 72, 7, 52, cyclotron.power, TileEntityMachineCyclotron.maxPower);

		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 53, guiTop + 72, 7, 52, cyclotron.coolant, ModForgeFluids.coolant);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 90, 7, 34, cyclotron.amat, ModForgeFluids.amat);

		String[] text = I18nUtil.resolveKeyArray("desc.guiacceptupgrades2");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 21, guiTop + 75, 8, 8, mouseX, mouseY, text);
		super.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.cyclotron.hasCustomInventoryName() ? this.cyclotron.getInventoryName() : I18n.format(this.cyclotron.getInventoryName());

		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);

    	if(guiLeft + 97 <= mouseX && guiLeft + 97 + 18 > mouseX && guiTop + 107 < mouseY && guiTop + 107 + 18 >= mouseY) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(cyclotron.getPos(), 0, 0));
    	}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int k = (int)cyclotron.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 80, guiTop + 124 - k, 212, 52 - k, 7, k);

		int l = cyclotron.getProgressScaled(36);
		drawTexturedModalRect(guiLeft + 52, guiTop + 26, 176, 0, l, 36);

		if(cyclotron.isOn)
			drawTexturedModalRect(guiLeft + 97, guiTop + 107, 219, 0, 18, 18);
		
		this.drawInfoPanel(guiLeft + 21, guiTop + 75, 8, 8, 8);

		FFUtils.drawLiquid(cyclotron.coolant, guiLeft, guiTop, zLevel, 7, 52, 53, 152);
		FFUtils.drawLiquid(cyclotron.amat, guiLeft, guiTop, zLevel, 7, 34, 134, 152);
	}
}
