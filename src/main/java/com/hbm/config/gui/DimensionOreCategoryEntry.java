package com.hbm.config.gui;

import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;

public class DimensionOreCategoryEntry extends TextFileCategoryEntry {

	public DimensionOreCategoryEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
			IConfigElement configElement) {
		super(owningScreen, owningEntryList, configElement);
	}

	@Override
	public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight,
			int mouseX, int mouseY, boolean isSelected, float partial) {
		this.btnSelectCategory.x = owningEntryList.controlX;
		this.btnSelectCategory.width = owningEntryList.controlWidth;
		this.btnSelectCategory.y = y;
		this.btnSelectCategory.enabled = true;
		this.btnSelectCategory.drawButton(this.mc, mouseX, mouseY, partial);
	}
}