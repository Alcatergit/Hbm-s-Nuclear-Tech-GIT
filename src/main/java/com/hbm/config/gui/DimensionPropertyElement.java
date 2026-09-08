package com.hbm.config.gui;

import com.hbm.config.BedrockOreJsonConfig;

import net.minecraftforge.fml.client.config.ConfigGuiType;
import net.minecraftforge.fml.client.config.GuiConfigEntries.IConfigEntry;
import net.minecraftforge.fml.client.config.GuiEditArrayEntries.IArrayEntry;

public class DimensionPropertyElement extends AbstractConfigElement {

	private final int[] dimRef;
	private final String propName;
	private final ConfigGuiType type;
	private final Object[] currentValues;
	private final int index;

	DimensionPropertyElement(int[] dimRef, String propName, ConfigGuiType type,
			Object[] currentValues, int index) {
		this.dimRef = dimRef;
		this.propName = propName;
		this.type = type;
		this.currentValues = currentValues;
		this.index = index;
	}

	@Override
	public boolean isProperty() {
		return true;
	}

	@Override
	public Class<? extends IConfigEntry> getConfigEntryClass() {
		return null;
	}

	@Override
	public Class<? extends IArrayEntry> getArrayEntryClass() {
		return null;
	}

	@Override
	public String getName() {
		return HbmConfigGui.transformName(propName);
	}

	@Override
	public String getQualifiedName() {
		return propName + "_" + dimRef[0];
	}

	@Override
	public String getLanguageKey() {
		return propName + "_" + dimRef[0];
	}

	@Override
	public ConfigGuiType getType() {
		return type;
	}

	@Override
	public boolean isList() {
		return false;
	}

	@Override
	public boolean isListLengthFixed() {
		return false;
	}

	@Override
	public int getMaxListLength() {
		return 0;
	}

	@Override
	public Object getDefault() {
		switch (propName) {
			case "dimID":
				return dimRef[0];
			case "oreRarity":
				return 0;
			default:
				return false;
		}
	}

	@Override
	public void setToDefault() {
		set(getDefault());
	}

	@Override
	public Object get() {
		switch (propName) {
			case "dimID":
				return dimRef[0];
			case "oreRarity":
				if (JsonRootConfigElement.dimDrafts != null) {
					JsonRootConfigElement.DimDraft draft = JsonRootConfigElement.dimDrafts.get(dimRef[0]);
					if (draft != null)
						return draft.oreRarity;
				}
				return BedrockOreJsonConfig.dimOreRarity.getOrDefault(dimRef[0], 0);
			case "isWhiteList":
				if (JsonRootConfigElement.dimDrafts != null) {
					JsonRootConfigElement.DimDraft draft = JsonRootConfigElement.dimDrafts.get(dimRef[0]);
					if (draft != null)
						return draft.isWhiteList;
				}
				return BedrockOreJsonConfig.dimWhiteList.getOrDefault(dimRef[0], false);
			default:
				return null;
		}
	}

	@Override
	public Object[] getList() {
		return null;
	}

	@Override
	public void set(Object value) {
		switch (propName) {
			case "dimID": {
				int newID = ((Number) value).intValue();
				String newIDStr = String.valueOf(newID);
				for (int i = 0; i < currentValues.length; i++) {
					if (i != index && newIDStr.equals(String.valueOf(currentValues[i]))) {
						return;
					}
				}
				int oldID = dimRef[0];
				dimRef[0] = newID;
				currentValues[index] = newIDStr;
				if (JsonRootConfigElement.dimDrafts != null && oldID != newID) {
					JsonRootConfigElement.DimDraft draft = JsonRootConfigElement.dimDrafts.remove(oldID);
					if (draft != null)
						JsonRootConfigElement.dimDrafts.put(newID, draft);
				}
				break;
			}
			case "oreRarity": {
				JsonRootConfigElement.DimDraft draft = JsonRootConfigElement.getOrCreateDraft(dimRef[0]);
				draft.oreRarity = ((Number) value).intValue();
				break;
			}
			case "isWhiteList": {
				JsonRootConfigElement.DimDraft draft = JsonRootConfigElement.getOrCreateDraft(dimRef[0]);
				draft.isWhiteList = (Boolean) value;
				break;
			}
		}
	}

	@Override
	public Object getMinValue() {
		if (type == ConfigGuiType.INTEGER)
			return Integer.MIN_VALUE;
		return null;
	}

	@Override
	public Object getMaxValue() {
		if (type == ConfigGuiType.INTEGER)
			return Integer.MAX_VALUE;
		return null;
	}
}