package com.hbm.config.gui;

import java.util.List;
import java.lang.reflect.Field;
import java.util.regex.Pattern;

import net.minecraftforge.fml.client.config.GuiEditArray;
import net.minecraftforge.fml.client.config.GuiEditArrayEntries;
import net.minecraftforge.fml.client.config.IConfigElement;

public abstract class AbstractConfigElement implements IConfigElement {

	@Override
	public String getComment() {
		return "";
	}

	@Override
	public List<IConfigElement> getChildElements() {
		return null;
	}

	@Override
	public String[] getValidValues() {
		return null;
	}

	@Override
	public Object getMinValue() {
		return null;
	}

	@Override
	public Object getMaxValue() {
		return null;
	}

	@Override
	public Pattern getValidationPattern() {
		return null;
	}

	@Override
	public boolean requiresWorldRestart() {
		return false;
	}

	@Override
	public boolean showInGui() {
		return true;
	}

	@Override
	public boolean requiresMcRestart() {
		return false;
	}

	@Override
	public boolean isDefault() {
		return false;
	}

	@Override
	public Object getDefault() {
		return null;
	}

	@Override
	public Object[] getDefaults() {
		return null;
	}

	@Override
	public void set(Object value) {
	}

	@Override
	public void set(Object[] aVal) {
	}

	public static class DimmedStringEntry extends GuiEditArrayEntries.StringEntry {

		public DimmedStringEntry(GuiEditArray owningScreen, GuiEditArrayEntries owningEntryList,
				IConfigElement configElement, Object value) {
			super(owningScreen, owningEntryList, configElement, value);
		}

		@Override
		public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight,
				int mouseX, int mouseY, boolean isSelected, float partial) {
			try {
				Field[] fields = GuiEditArray.class.getDeclaredFields();
				Field enabledField = null;
				for(Field field : fields) {
					if(field.getType() == boolean.class) {
						enabledField = field;
						break;
					}
				}
				if(enabledField != null) {
					enabledField.setAccessible(true);
					this.textFieldValue.setEnabled(enabledField.getBoolean(owningScreen));
				}
			} catch (Exception ignored) {
			}
			super.drawEntry(slotIndex, x, y, listWidth, slotHeight, mouseX, mouseY, isSelected, partial);
		}
	}
}