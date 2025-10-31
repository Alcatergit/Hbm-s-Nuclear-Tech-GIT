package com.leafia.dev.gui;

import com.hbm.lib.HBMSoundEvents;
import com.leafia.dev.LeafiaDebug;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.SoundEvents;

import java.io.IOException;

public class GuiScreenLeafia extends GuiScreen {
	public int xSize = 176;
	public int ySize = 166;
	public int guiLeft;
	public int guiTop;
	protected void playClick(float pitch) {
		mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK,pitch));
	}
	protected void playDenied() {
		mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(HBMSoundEvents.UI_BUTTON_INVALID,1));
	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width-this.xSize)/2;
		this.guiTop = (this.height-this.ySize)/2;
	}
	@Override
	public void drawScreen(int mouseX,int mouseY,float partialTicks) {
		drawGuiScreenBackgroundLayer(partialTicks,mouseX,mouseY);
		super.drawScreen(mouseX,mouseY,partialTicks);
		LeafiaGls.pushMatrix();
		LeafiaGls.translate(guiLeft,guiTop,0);
		drawGuiScreenForegroundLayer(mouseX,mouseY);
		LeafiaGls.popMatrix();
	}
	protected void drawGuiScreenForegroundLayer(int mouseX,int mouseY) {}
	protected void drawGuiScreenBackgroundLayer(float partialTicks,int mouseX,int mouseY) {}

	@Override
	protected void keyTyped(char typedChar,int keyCode) throws IOException {
		if (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode)) {
			this.mc.player.closeScreen();
		}
	}
	@Override
	public boolean doesGuiPauseGame() { return false; }
}
