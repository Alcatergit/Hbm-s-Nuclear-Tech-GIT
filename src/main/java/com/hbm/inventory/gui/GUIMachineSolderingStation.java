package com.hbm.inventory.gui;

import com.hbm.forgefluid.FFUtils;
import com.hbm.packet.NBTControlPacket;
import net.minecraft.init.SoundEvents;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineSolderingStation;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineSolderingStation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GUIMachineSolderingStation extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_soldering_station.png");
	private TileEntityMachineSolderingStation solderer;

	public GUIMachineSolderingStation(InventoryPlayer playerInv, TileEntityMachineSolderingStation tile) {
		super(new ContainerMachineSolderingStation(playerInv, tile));
		
		this.solderer = tile;
		this.xSize = 176;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		FFUtils.renderTankInfo(this, x, y, guiLeft + 17, guiTop + 62, 52, 16, solderer.tank);
		this.drawElectricityInfo(this, x, y, guiLeft + 152, guiTop + 17, 16, 52, solderer.getPower(), solderer.getMaxPower());
		
//		this.drawCustomInfoStat(x, y, guiLeft + 78, guiTop + 67, 8, 8, guiLeft + 78, guiTop + 67, this.getUpgradeInfo(solderer));
		

		this.drawCustomInfoStat(x, y, guiLeft + 74, guiTop + 65, 10, 10, x, y, new String[]{
				"Recipe Collision Prevention: " + (solderer.collisionPrevention ? "§aON" : "§cOFF"),
				"Prevents no-fluid recipes from being processed",
				"when fluid is present."});
		super.renderHoveredToolTip(x, y);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) throws IOException {
		super.mouseClicked(x, y, i);

		if(guiLeft + 74 <= x && guiLeft + 74 + 10 > x && guiTop + 65 < y && guiTop + 65 + 10 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("collision", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, solderer.getPos().getX(), solderer.getPos().getY(), solderer.getPos().getZ()));
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.solderer.hasCustomInventoryName() ? this.solderer.getInventoryName() : I18n.format(this.solderer.getInventoryName());
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2 - 18, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int p = (int) (solderer.power * 52 / Math.max(solderer.getMaxPower(), 1));
		drawTexturedModalRect(guiLeft + 152, guiTop + 70 - p, 176, 52 - p, 16, p);

		int i = solderer.progress * 33 / Math.max(solderer.processTime, 1);
		drawTexturedModalRect(guiLeft + 72, guiTop + 28, 192, 0, i, 14);
		
		if(solderer.power >= solderer.consumption) {
			drawTexturedModalRect(guiLeft + 156, guiTop + 4, 176, 52, 9, 12);
		}

		if(solderer.collisionPrevention) {
			drawTexturedModalRect(guiLeft + 74, guiTop + 66, 192, 14, 10, 10);
		}
//		this.drawInfoPanel(guiLeft + 78, guiTop + 67, 8, 8, 8);
		FFUtils.drawLiquid(solderer.tank, guiLeft, guiTop, this.zLevel, 52, 16, 17, 107);
	}
}
