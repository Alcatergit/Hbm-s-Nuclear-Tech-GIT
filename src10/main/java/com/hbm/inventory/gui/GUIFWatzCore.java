package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import java.io.IOException;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.container.ContainerFWatzCore;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityFWatzCore;

import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIFWatzCore extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_fwatz_multiblock.png");
	private TileEntityFWatzCore fwatz;

	public GUIFWatzCore(InventoryPlayer invPlayer, TileEntityFWatzCore tedf) {
		super(new ContainerFWatzCore(invPlayer, tedf));
		fwatz = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 53, guiTop + 85, 70, 18, fwatz.tanks[0], fwatz.tankTypes[0]);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 7, guiTop + 17, 18, 72, fwatz.tanks[1], fwatz.tankTypes[1]);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 151, guiTop + 17, 18, 72, fwatz.tanks[2], fwatz.tankTypes[2]);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 29, guiTop + 107, 118, 18, fwatz.power, TileEntityFWatzCore.maxPower);
		super.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.fwatz.hasCustomInventoryName() ? this.fwatz.getInventoryName() : I18n.format(this.fwatz.getInventoryName());
		
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int i) throws IOException {
		super.mouseClicked(mouseX, mouseY, i);
		//toggle column selection
		if(guiLeft + 29 <= mouseX && guiLeft + 29 + 18 > mouseX && guiTop + 89 < mouseY && guiTop + 89 + 18 >= mouseY) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(new NBTTagCompound(), this.fwatz.getPos()));
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int k = (int)fwatz.getPowerScaled(116);
		drawTexturedModalRect(guiLeft + 30, guiTop + 108, 0, 222, k, 16);
		
		if(fwatz.isRunning())
			drawTexturedModalRect(guiLeft + 64, guiTop + 29, 176, 24, 48, 48);

		if(fwatz.isOn)
			drawTexturedModalRect(guiLeft + 29, guiTop + 89, 192, 0, 18, 18);
		
		int m = fwatz.getSingularityType();
		drawTexturedModalRect(guiLeft + 80, guiTop + 20, 176, 4 * m, 16, 4);
		
		FFUtils.drawLiquid(fwatz.tanks[0], guiLeft, guiTop, zLevel, 68, 16, 54, 130);
		FFUtils.drawLiquid(fwatz.tanks[1], guiLeft, guiTop, zLevel, 16, 70, 8, 116);
		FFUtils.drawLiquid(fwatz.tanks[2], guiLeft, guiTop, zLevel, 16, 70, 152, 116);
	}
}