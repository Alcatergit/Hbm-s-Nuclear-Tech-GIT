package com.hbm.config.gui;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiEditArray;
import net.minecraftforge.fml.client.config.IConfigElement;

public class TextFileCategoryEntry extends GuiConfigEntries.CategoryEntry {

	public TextFileCategoryEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
			IConfigElement configElement) {
		super(owningScreen, owningEntryList, configElement);
	}

	@Override
	protected GuiScreen buildChildScreen() {
		return null;
	}

	@Override
	public boolean enabled() {
		return !(owningScreen.allRequireWorldRestart && owningScreen.isWorldRunning);
	}

	@Override
	public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight,
			int mouseX, int mouseY, boolean isSelected, float partial) {
		super.drawEntry(slotIndex, x, y, listWidth, slotHeight, mouseX, mouseY, isSelected, partial);
		this.btnSelectCategory.enabled = true;
		this.btnSelectCategory.drawButton(this.mc, mouseX, mouseY, partial);
	}

	@Override
	public boolean mousePressed(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
		if (this.btnSelectCategory.mousePressed(this.mc, x, y)) {
			btnSelectCategory.playPressSound(mc.getSoundHandler());
			boolean editEnabled = !(owningScreen.allRequireWorldRestart && owningScreen.isWorldRunning);
			Minecraft.getMinecraft().displayGuiScreen(new GuiEditArray(
					owningScreen, configElement, -1,
					configElement.getList(), editEnabled) {
				@Override
				public void initGui() {
					super.initGui();
					Keyboard.enableRepeatEvents(true);
				}
			});
			return true;
		} else
			return super.mousePressed(index, x, y, mouseEvent, relativeX, relativeY);
	}
}