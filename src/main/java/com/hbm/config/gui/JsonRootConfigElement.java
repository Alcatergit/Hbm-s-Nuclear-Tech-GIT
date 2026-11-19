package com.hbm.config.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import com.hbm.config.BedrockOreJsonConfig;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.config.ConfigGuiType;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiConfigEntries.IConfigEntry;
import net.minecraftforge.fml.client.config.GuiEditArray;
import net.minecraftforge.fml.client.config.GuiEditArrayEntries.IArrayEntry;
import net.minecraftforge.fml.client.config.IConfigElement;

public class JsonRootConfigElement extends AbstractConfigElement {

	static Map<Integer, DimDraft> dimDrafts = null;

	static class DimDraft {
		int oreRarity;
		boolean isWhiteList;
		List<String> bedrockOres;

		DimDraft(int oreRarity, boolean isWhiteList, List<String> bedrockOres) {
			this.oreRarity = oreRarity;
			this.isWhiteList = isWhiteList;
			this.bedrockOres = new ArrayList<>(bedrockOres);
		}
	}

	static DimDraft getOrCreateDraft(int dimID) {
		if (dimDrafts == null)
			dimDrafts = new HashMap<>();
		DimDraft draft = dimDrafts.get(dimID);
		if (draft == null) {
			int rarity = BedrockOreJsonConfig.dimOreRarity.getOrDefault(dimID, 0);
			boolean whiteList = BedrockOreJsonConfig.dimWhiteList.getOrDefault(dimID, false);
			List<String> ores = new ArrayList<>();
			HashSet<String> oreSet = BedrockOreJsonConfig.dimOres.get(dimID);
			if (oreSet != null)
				ores.addAll(oreSet);
			draft = new DimDraft(rarity, whiteList, ores);
			dimDrafts.put(dimID, draft);
		}
		return draft;
	}

	private final String displayName;
	private final String originalName;

	public JsonRootConfigElement(String displayName, String originalName) {
		this.displayName = displayName;
		this.originalName = originalName;
	}

	@Override
	public boolean isProperty() {
		return false;
	}

	@Override
	public Class<? extends IConfigEntry> getConfigEntryClass() {
		return JsonRootCategoryEntry.class;
	}

	@Override
	public Class<? extends IArrayEntry> getArrayEntryClass() {
		return DimensionArrayEntry.class;
	}

	@Override
	public String getName() {
		return displayName;
	}

	@Override
	public String getQualifiedName() {
		return originalName;
	}

	@Override
	public String getLanguageKey() {
		return "§o" + originalName + "§r";
	}

	@Override
	public ConfigGuiType getType() {
		return ConfigGuiType.CONFIG_CATEGORY;
	}

	@Override
	public boolean isList() {
		return true;
	}

	@Override
	public boolean isListLengthFixed() {
		return false;
	}

	@Override
	public int getMaxListLength() {
		return 1000;
	}

	@Override
	public Object getDefault() {
		return "";
	}

	@Override
	public Object[] getDefaults() {
		return new String[0];
	}

	@Override
	public void setToDefault() {
		BedrockOreJsonConfig.clear();
		BedrockOreJsonConfig.setDefaults();
	}

	@Override
	public Object get() {
		return null;
	}

	@Override
	public Object[] getList() {
		dimDrafts = new HashMap<>();
		List<String> entries = new ArrayList<>();
		for (Integer dimID : BedrockOreJsonConfig.dimOres.keySet()) {
			entries.add(dimID.toString());
		}
		Collections.sort(entries, (a, b) -> {
			try {
				return Integer.compare(Integer.parseInt(a), Integer.parseInt(b));
			} catch (NumberFormatException e) {
				return a.compareTo(b);
			}
		});
		return entries.toArray(new String[0]);
	}

	@Override
	public void set(Object[] aVal) {
		if (dimDrafts != null) {
			for (Map.Entry<Integer, DimDraft> entry : dimDrafts.entrySet()) {
				int dimID = entry.getKey();
				DimDraft draft = entry.getValue();
				BedrockOreJsonConfig.dimOreRarity.put(dimID, draft.oreRarity);
				BedrockOreJsonConfig.dimWhiteList.put(dimID, draft.isWhiteList);
				BedrockOreJsonConfig.dimOres.put(dimID, new HashSet<>(draft.bedrockOres));
			}
			dimDrafts = null;
		}

		Map<Integer, Integer> savedRarity = new HashMap<>(BedrockOreJsonConfig.dimOreRarity);
		Map<Integer, Boolean> savedWhiteList = new HashMap<>(BedrockOreJsonConfig.dimWhiteList);
		Map<Integer, HashSet<String>> savedOres = new HashMap<>();
		for (Integer dimID : BedrockOreJsonConfig.dimOres.keySet()) {
			savedOres.put(dimID, new HashSet<>(BedrockOreJsonConfig.dimOres.get(dimID)));
		}

		BedrockOreJsonConfig.clear();
		if (aVal == null)
			return;
		for (Object entry : aVal) {
			String line = entry.toString().trim();
			if (line.isEmpty())
				continue;
			try {
				int dimID = Integer.parseInt(line);
				int rarity = savedRarity.getOrDefault(dimID, 0);
				boolean isWhiteList = savedWhiteList.getOrDefault(dimID, false);
				List<String> ores = new ArrayList<>();
				HashSet<String> savedOreSet = savedOres.get(dimID);
				if (savedOreSet != null)
					ores.addAll(savedOreSet);
				BedrockOreJsonConfig.addEntry(dimID, rarity, ores, isWhiteList);
			} catch (NumberFormatException ignored) {
			}
		}
	}

	public static class JsonRootCategoryEntry extends TextFileCategoryEntry {

		public JsonRootCategoryEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
				IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
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
}
