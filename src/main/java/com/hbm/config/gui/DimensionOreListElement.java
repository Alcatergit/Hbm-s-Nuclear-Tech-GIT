package com.hbm.config.gui;

import java.util.HashSet;

import com.hbm.config.BedrockOreJsonConfig;

import net.minecraftforge.fml.client.config.ConfigGuiType;
import net.minecraftforge.fml.client.config.GuiConfigEntries.IConfigEntry;
import net.minecraftforge.fml.client.config.GuiEditArrayEntries.IArrayEntry;

public class DimensionOreListElement extends AbstractConfigElement {

	private final int[] dimRef;
	private final String displayName;

	DimensionOreListElement(int[] dimRef, String displayName) {
		this.dimRef = dimRef;
		this.displayName = displayName;
	}

	@Override
	public boolean isProperty() {
		return false;
	}

	@Override
	public Class<? extends IConfigEntry> getConfigEntryClass() {
		return DimensionOreCategoryEntry.class;
	}

	@Override
	public Class<? extends IArrayEntry> getArrayEntryClass() {
		return AbstractConfigElement.DimmedStringEntry.class;
	}

	@Override
	public String getName() {
		return displayName;
	}

	@Override
	public String getQualifiedName() {
		return "bedrockOres_" + dimRef[0];
	}

	@Override
	public String getLanguageKey() {
		return "bedrockOres_" + dimRef[0];
	}

	@Override
	public ConfigGuiType getType() {
		return ConfigGuiType.STRING;
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
		set(new String[0]);
	}

	@Override
	public Object get() {
		return null;
	}

	@Override
	public Object[] getList() {
		if (JsonRootConfigElement.dimDrafts != null) {
			JsonRootConfigElement.DimDraft draft = JsonRootConfigElement.dimDrafts.get(dimRef[0]);
			if (draft != null)
				return draft.bedrockOres.toArray(new String[0]);
		}
		HashSet<String> ores = BedrockOreJsonConfig.dimOres.get(dimRef[0]);
		if (ores == null)
			return new String[0];
		return ores.toArray(new String[0]);
	}

	@Override
	public void set(Object[] aVal) {
		JsonRootConfigElement.DimDraft draft = JsonRootConfigElement.getOrCreateDraft(dimRef[0]);
		draft.bedrockOres.clear();
		for (Object val : aVal) {
			draft.bedrockOres.add(val.toString());
		}
	}
}