package com.leafia.contents.machines.processing.advcent.container;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.gui.GuiInfoContainer;
import com.hbm.lib.RefStrings;
import com.leafia.contents.machines.processing.advcent.AdvCentTE;
import com.leafia.contents.machines.processing.gascent.GasCentTE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class AdvCentGUI extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_advcent.png");
	private AdvCentTE diFurnace;
	
	public AdvCentGUI(InventoryPlayer invPlayer,AdvCentTE tedf) {
		super(new AdvCentContainer(invPlayer, tedf));
		diFurnace = tedf;

		this.xSize = 226;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 12, guiTop + 16, 22, 52, diFurnace.in);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 138, guiTop + 16, 22, 52, diFurnace.out0);

		//this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 92, guiTop + 15, 28, 54, mouseX, mouseY, new String[] {String.valueOf((int)((double)diFurnace.progress / (double)TileEntityMachineGasCent.processingSpeed * 100D)) + "%"});
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 186, guiTop + 121, 16, 52, diFurnace.power, GasCentTE.maxPower);
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
		drawTexturedModalRect(guiLeft + 186, guiTop + 173 - i, 226, 52 - i, 16, i);

		int j = (int)diFurnace.getCentrifugeProgressScaled(54);
		drawTexturedModalRect(guiLeft + 57, guiTop + 35, 0,204, j, 13);
		

		FFUtils.drawLiquid(diFurnace.in, guiLeft, guiTop, zLevel, 6, 52, 12, 16+80);
		FFUtils.drawLiquid(diFurnace.in, guiLeft, guiTop, zLevel, 6, 52, 28, 16+80);
		FFUtils.drawLiquid(diFurnace.out0, guiLeft, guiTop, zLevel, 6, 52, 138, 16+80);
		FFUtils.drawLiquid(diFurnace.out0, guiLeft, guiTop, zLevel, 6, 52, 154, 16+80);
	}
}
