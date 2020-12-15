package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.container.ContainerCoreReceiver;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityCoreReceiver;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GUICoreReceiver extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/dfc/gui_receiver.png");
	private TileEntityCoreReceiver receiver;
	
	public GUICoreReceiver(EntityPlayer invPlayer, TileEntityCoreReceiver tedf) {
		super(new ContainerCoreReceiver(invPlayer, tedf));
		receiver = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 17, 16, 52, receiver.tank, ModForgeFluids.cryogel);
		super.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.receiver.hasCustomInventoryName() ? this.receiver.getInventoryName() : I18n.format(this.receiver.getInventoryName());
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);

		this.fontRenderer.drawString("Input:", 40, 25, 0xFF7F7F);
		this.fontRenderer.drawString(Library.getShortNumber(receiver.joules) + "Spk", 50, 35, 0xFF7F7F);
		this.fontRenderer.drawString("Output:", 40, 45, 0xFF7F7F);
		this.fontRenderer.drawString(Library.getShortNumber(receiver.joules * 5000) + "HE", 50, 55, 0xFF7F7F);
		
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		FFUtils.drawLiquid(receiver.tank, guiLeft, guiTop, zLevel, 16, 52, 8, 97);
	}
}