package com.hbm.config.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.config.ConfigGuiType;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiConfigEntries.IConfigEntry;
import net.minecraftforge.fml.client.config.GuiEditArray;
import net.minecraftforge.fml.client.config.GuiEditArrayEntries.IArrayEntry;
import net.minecraftforge.fml.client.config.IConfigElement;

class NameTransformedConfigElement implements IConfigElement {

	private final IConfigElement delegate;
	private final String transformedName;

	NameTransformedConfigElement(IConfigElement delegate) {
		this.delegate = delegate;
		this.transformedName = HbmConfigGui.transformName(delegate.getName());
	}

	@Override
	public boolean isProperty() {
		return delegate.isProperty();
	}

	@Override
	public Class<? extends IConfigEntry> getConfigEntryClass() {
		Class<? extends IConfigEntry> cls = delegate.getConfigEntryClass();
		if (cls == GuiConfigEntries.ArrayEntry.class) {
			return HbmArrayEntry.class;
		}
		if (cls == GuiConfigEntries.CategoryEntry.class) {
			return HbmCategoryEntry.class;
		}
		if (cls == null) {
			if (delegate.isProperty() && delegate.isList()) {
				return HbmArrayEntry.class;
			}
			if (!delegate.isProperty() && delegate.getType() == ConfigGuiType.CONFIG_CATEGORY) {
				return HbmCategoryEntry.class;
			}
		}
		return cls;
	}

	@Override
	public Class<? extends IArrayEntry> getArrayEntryClass() {
		Class<? extends IArrayEntry> cls = delegate.getArrayEntryClass();
		if (cls != null) {
			return cls;
		}
		ConfigGuiType type = delegate.getType();
		if (type == ConfigGuiType.STRING || type == ConfigGuiType.INTEGER || type == ConfigGuiType.DOUBLE) {
			return AbstractConfigElement.DimmedStringEntry.class;
		}
		return null;
	}

	@Override
	public String getName() {
		return transformedName;
	}

	@Override
	public String getQualifiedName() {
		return delegate.getQualifiedName();
	}

	@Override
	public String getLanguageKey() {
		return delegate.getLanguageKey();
	}

	@Override
	public String getComment() {
		return delegate.getComment();
	}

	@Override
	public List<IConfigElement> getChildElements() {
		List<IConfigElement> children = delegate.getChildElements();
		if (children == null)
			return null;
		List<IConfigElement> wrapped = new ArrayList<>();
		for (IConfigElement child : children) {
			wrapped.add(new NameTransformedConfigElement(child));
		}
		return wrapped;
	}

	@Override
	public ConfigGuiType getType() {
		return delegate.getType();
	}

	@Override
	public boolean isList() {
		return delegate.isList();
	}

	@Override
	public boolean isListLengthFixed() {
		return delegate.isListLengthFixed();
	}

	@Override
	public int getMaxListLength() {
		return delegate.getMaxListLength();
	}

	@Override
	public boolean isDefault() {
		return delegate.isDefault();
	}

	@Override
	public Object getDefault() {
		return delegate.getDefault();
	}

	@Override
	public Object[] getDefaults() {
		return delegate.getDefaults();
	}

	@Override
	public void setToDefault() {
		delegate.setToDefault();
	}

	@Override
	public boolean requiresWorldRestart() {
		return delegate.requiresWorldRestart();
	}

	@Override
	public boolean showInGui() {
		return delegate.showInGui();
	}

	@Override
	public boolean requiresMcRestart() {
		return delegate.requiresMcRestart();
	}

	@Override
	public Object get() {
		return delegate.get();
	}

	@Override
	public Object[] getList() {
		return delegate.getList();
	}

	@Override
	public void set(Object value) {
		delegate.set(value);
	}

	@Override
	public void set(Object[] aVal) {
		delegate.set(aVal);
	}

	@Override
	public String[] getValidValues() {
		return delegate.getValidValues();
	}

	@Override
	public Object getMinValue() {
		return delegate.getMinValue();
	}

	@Override
	public Object getMaxValue() {
		return delegate.getMaxValue();
	}

	@Override
	public Pattern getValidationPattern() {
		return delegate.getValidationPattern();
	}

	public static class HbmArrayEntry extends GuiConfigEntries.ArrayEntry {

		public HbmArrayEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
				IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight,
				int mouseX, int mouseY, boolean isSelected, float partial) {
			super.drawEntry(slotIndex, x, y, listWidth, slotHeight, mouseX, mouseY, isSelected, partial);
			this.btnValue.enabled = true;
			this.btnValue.drawButton(this.mc, mouseX, mouseY, partial);
		}

		@Override
		public void valueButtonPressed(int slotIndex) {
			boolean editEnabled = !(owningScreen.allRequireWorldRestart && owningScreen.isWorldRunning);
			Minecraft.getMinecraft().displayGuiScreen(new GuiEditArray(
					this.owningScreen, configElement, slotIndex, currentValues, editEnabled) {
				@Override
				public void initGui() {
					super.initGui();
					Keyboard.enableRepeatEvents(true);
				}
			});
		}
	}
}