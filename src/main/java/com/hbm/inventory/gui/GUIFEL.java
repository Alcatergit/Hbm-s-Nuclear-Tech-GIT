package com.hbm.inventory.gui;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerFEL;
import com.hbm.items.machine.ItemFELCrystal.EnumWavelengths;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityFEL;
import com.hbm.render.amlfrom1710.Tessellator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

public class GUIFEL extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_fel.png");
	private TileEntityFEL fel;

	public GUIFEL(InventoryPlayer invPlayer, TileEntityFEL laser) {
		super(new ContainerFEL(invPlayer, laser));
		fel = laser;

		this.xSize = 203;
		this.ySize = 169;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 182, guiTop + 27, 16, 113, fel.power, fel.maxPower);
	}

	protected void mouseClicked(int mouseX, int mouseY, int i) throws IOException {
		super.mouseClicked(mouseX, mouseY, i);

		if(guiLeft + 142 <= mouseX && guiLeft + 142 + 29 > mouseX && guiTop + 41 < mouseY && guiTop + 41 + 17 >= mouseY) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(fel.getPos(), 0, 2));
		}
		
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.fel.hasCustomInventoryName() ? this.fel.getInventoryName() : I18n.format(this.fel.getInventoryName());

		this.fontRenderer.drawString(name, 90 + this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 7, 0xffffff);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 98, 4210752);
		
		if(fel.missingValidSilex && fel.isOn) {
			this.fontRenderer.drawString(I18n.format("ERR."), 55 + this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 9, 0xFF0000);
		} else if(fel.isOn) {
			this.fontRenderer.drawString(I18n.format("LIVE"), 54 + this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 9, 0x00FF00);
		}
		
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(fel.isOn)
			drawTexturedModalRect(guiLeft + 142, guiTop + 41, 203, 0, 29, 17);
		
		int k = (int)fel.getPowerScaled(113);
		drawTexturedModalRect(guiLeft + 182, guiTop + 27 + 113 - k, 203, 17 + 113 - k, 16, k);
		
		int color = !(fel.mode == EnumWavelengths.VISIBLE) ? fel.mode.guiColor : Color.HSBtoRGB(fel.getWorld().getTotalWorldTime() / 50.0F, 0.5F, 1F) & 16777215;
		
		if(fel.power > fel.powerReq * Math.pow(2, fel.mode.ordinal()) && fel.isOn && !(fel.mode == EnumWavelengths.NULL) && fel.distance > 0) {
			
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glLineWidth(5F);
			
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawing(1);
			tessellator.setColorOpaque_I(color);
			
			tessellator.addVertex(guiLeft + 113, guiTop + 31.5F, this.zLevel);
			tessellator.addVertex(guiLeft + 135, guiTop + 31.5F, this.zLevel);
			tessellator.draw();
			
			tessellator.startDrawing(1);
			tessellator.setColorOpaque_I(color);
			
			tessellator.addVertex(0, guiTop + 31.5F, this.zLevel);
			tessellator.addVertex(guiLeft + 4, guiTop + 31.5F, this.zLevel);
			tessellator.draw();
			
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();
		}
	}	
}