package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.container.ContainerMachineTurbofan;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineTurbofan;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineTurbofan extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/generators/gui_turbofan.png");
	private TileEntityMachineTurbofan turbofan;

	public GUIMachineTurbofan(InventoryPlayer invPlayer, TileEntityMachineTurbofan tedf) {
		super(new ContainerMachineTurbofan(invPlayer, tedf));
		turbofan = tedf;
		
		this.xSize = 176;
		this.ySize = 203;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 35, guiTop + 16, 34, 52, turbofan.tank);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 143, guiTop + 16, 16, 52, turbofan.power, turbofan.maxPower);

		super.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.turbofan.hasCustomInventoryName() ? this.turbofan.getInventoryName() : I18n.format(this.turbofan.getInventoryName());
		
		this.fontRenderer.drawString(name, 43 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(turbofan.power > 0) {
			int i = (int)turbofan.getPowerScaled(52);
			drawTexturedModalRect(guiLeft + 152 - 9, guiTop + 69 - i, 176 + 16, 52 - i, 16, i);
		}
		
		if(turbofan.afterburner > 0) {
			int a = Math.min(turbofan.afterburner, 6);
			drawTexturedModalRect(guiLeft + 98, guiTop + 44, 176, (a - 1) * 16, 16, 16);
		}
		
		FFUtils.drawLiquid(turbofan.tank, guiLeft, guiTop, zLevel, 34, 52, 35, 53+44);
	}
}
