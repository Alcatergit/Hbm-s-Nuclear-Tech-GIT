package com.hbm.inventory.gui;

import com.hbm.util.I18nUtil;
import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.container.ContainerMachineDiesel;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineDiesel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineDiesel extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/GUIDiesel.png");
	private TileEntityMachineDiesel diFurnace;

	public GUIMachineDiesel(InventoryPlayer invPlayer, TileEntityMachineDiesel tedf) {
		super(new ContainerMachineDiesel(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 69 - 52, 16, 52, diFurnace.tank, diFurnace.tankType);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 69 - 52, 16, 52, diFurnace.power, diFurnace.powerCap);

		String[] text1 = I18nUtil.resolveKeyArray("desc.guimachinediesel1");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 16, text1);
		
		if(!diFurnace.hasAcceptableFuel()) {
			
			String[] text2 = I18nUtil.resolveKeyArray("desc.guimachinediesel2");
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 32, 16, 16, guiLeft - 8, guiTop + 36 + 16 + 32, text2);
		}
		super.renderHoveredToolTip(mouseX, mouseY);
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
		
		if(diFurnace.power > 0) {
			int i = (int)diFurnace.getPowerScaled(52);
			drawTexturedModalRect(guiLeft + 152, guiTop + 69 - i, 176, 52 - i, 16, i);
		}
		
		if(diFurnace.tank.getFluidAmount() > 0 && diFurnace.hasAcceptableFuel())
		{
			drawTexturedModalRect(guiLeft + 43 + 18 * 4, guiTop + 34, 208, 0, 18, 18);
		}

		this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 16, 16, 16, 3);
		
		if(!diFurnace.hasAcceptableFuel())
			this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 32, 16, 16, 6);
		
		FFUtils.drawLiquid(diFurnace.tank, guiLeft, guiTop, this.zLevel, 16, 52, 80, 97);
	}
}
