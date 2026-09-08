package com.hbm.config.gui;

import java.util.List;

import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfigEntries.IConfigEntry;
import net.minecraftforge.fml.client.config.IConfigElement;

class HbmCategoryElement extends DummyConfigElement.DummyCategoryElement {
	HbmCategoryElement(String displayName, String originalName, List<IConfigElement> children) {
		super(displayName, originalName, children);
	}

	@Override
	public String getComment() {
		return "";
	}

	@Override
	public Class<? extends IConfigEntry> getConfigEntryClass() {
		return HbmCategoryEntry.class;
	}
}