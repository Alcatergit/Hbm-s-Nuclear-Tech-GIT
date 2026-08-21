package com.hbm.config.gui;

import java.util.List;
import java.lang.reflect.Field;

import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiEditArray;
import net.minecraftforge.fml.client.config.GuiEditArrayEntries;
import net.minecraftforge.fml.client.config.IConfigElement;

import org.lwjgl.input.Keyboard;

public class DimensionArrayEntry extends GuiEditArrayEntries.BaseEntry {

	private final GuiTextField textFieldValue;
	private final GuiButtonExt btnSelectDimension;
	private final boolean enabled;
	private boolean isEditing;

	public DimensionArrayEntry(GuiEditArray owningScreen, GuiEditArrayEntries owningEntryList,
			IConfigElement configElement, Object value) {
		super(owningScreen, owningEntryList, configElement);
		this.enabled = true;
		String val = value.toString().trim();
		this.isEditing = val.isEmpty();

		this.textFieldValue = new GuiTextField(0, owningEntryList.getMC().fontRenderer,
				0, 0, 298, 16);
		this.textFieldValue.setMaxStringLength(10);
		this.textFieldValue.setText(val);
		this.textFieldValue.setFocused(this.isEditing);

		this.btnSelectDimension = new GuiButtonExt(0, 0, 0, 0, 0, "");
		this.btnSelectDimension.enabled = this.enabled && !this.isEditing;
	}

	@Override
	public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight,
			int mouseX, int mouseY, boolean isSelected, float partial) {

		if (this.isEditing) {
			this.textFieldValue.setVisible(true);
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
			int half = listWidth / 2;
			int btnWidth = 300;
			int plusButtonX = half + ((half / 2) - 44);
			this.textFieldValue.x = plusButtonX - 6 - btnWidth + 1;
			this.textFieldValue.y = y + 1;
			this.textFieldValue.drawTextBox();
		} else {
			this.textFieldValue.setVisible(false);

			String dimLabel = "Dimension: " + this.textFieldValue.getText();
			int half = listWidth / 2;
			int btnWidth = 300;
			int btnHeight = 18;

			int plusButtonX = half + ((half / 2) - 44);
			int dimButtonX = plusButtonX - 6 - btnWidth;

			this.btnSelectDimension.x = dimButtonX;
			this.btnSelectDimension.y = y;
			this.btnSelectDimension.width = btnWidth;
			this.btnSelectDimension.height = btnHeight;
			this.btnSelectDimension.displayString = dimLabel;
			this.btnSelectDimension.enabled = this.enabled;
			this.btnSelectDimension.drawButton(owningEntryList.getMC(), mouseX, mouseY, partial);
		}

		super.drawEntry(slotIndex, x, y, listWidth, slotHeight, mouseX, mouseY, isSelected, partial);
	}

	@Override
	public void keyTyped(char eventChar, int eventKey) {
		if (this.isEditing) {
			if (eventKey == Keyboard.KEY_RETURN || eventKey == Keyboard.KEY_NUMPADENTER) {
				String text = this.textFieldValue.getText().trim();
				if (!text.isEmpty()) {
					this.isEditing = false;
					this.btnSelectDimension.enabled = this.enabled;
					this.textFieldValue.setFocused(false);
					syncCurrentValues();
				}
				return;
			}
			this.textFieldValue.textboxKeyTyped(eventChar, eventKey);
		}
	}

	private void syncCurrentValues() {
		String val = this.textFieldValue.getText().trim();
		int myIndex = owningEntryList.listEntries.indexOf(this);
		if (myIndex < 0)
			return;
		Object[] cv = owningEntryList.currentValues;
		if (myIndex >= cv.length) {
			Object[] newCV = new Object[myIndex + 1];
			System.arraycopy(cv, 0, newCV, 0, cv.length);
			for (int i = cv.length; i < newCV.length; i++) {
				newCV[i] = "";
			}
			owningEntryList.currentValues = newCV;
			try {
				Field[] fields = GuiEditArray.class.getDeclaredFields();
				Field cvField = null;
				boolean foundFirst = false;
				for(Field field : fields) {
					if(field.getType() == Object[].class) {
						if(foundFirst) {
							cvField = field;
							break;
						}
						foundFirst = true;
					}
				}
				if(cvField != null) {
					cvField.setAccessible(true);
					cvField.set(owningScreen, newCV);
				}
			} catch (Exception ignored) {
			}
		}
		owningEntryList.currentValues[myIndex] = val;
	}

	@Override
	public void updateCursorCounter() {
		if (this.isEditing) {
			this.textFieldValue.updateCursorCounter();
		}
	}

	@Override
	public void mouseClicked(int x, int y, int mouseEvent) {
		if (this.isEditing) {
			this.textFieldValue.mouseClicked(x, y, mouseEvent);
		}
	}

	@Override
	public boolean mousePressed(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
		if (!this.isEditing && this.btnSelectDimension.mousePressed(owningEntryList.getMC(), x, y)) {
			this.btnSelectDimension.playPressSound(owningEntryList.getMC().getSoundHandler());
			syncCurrentValues();
			int dimID;
			try {
				dimID = Integer.parseInt(this.textFieldValue.getText().trim());
			} catch (NumberFormatException e) {
				dimID = 0;
			}
			List<IConfigElement> subElements = HbmConfigGui.buildDimensionSubElements(dimID,
					owningEntryList.currentValues, index);
			String newTitleLine2 = configElement.getLanguageKey() + " > Dimension: " + dimID;
			Minecraft.getMinecraft().displayGuiScreen(new GuiConfig(
					owningScreen, subElements,
					RefStrings.MODID,
					HbmConfigGui.instance.allRequireWorldRestart,
					HbmConfigGui.instance.allRequireMcRestart,
					"§e" + RefStrings.NAME + "§r",
					newTitleLine2));
			return true;
		}
		return super.mousePressed(index, x, y, mouseEvent, relativeX, relativeY);
	}

	@Override
	public boolean isValueSavable() {
		if (this.isEditing)
			return false;
		String val = this.textFieldValue.getText().trim();
		if (val.isEmpty())
			return false;
		int myIndex = owningEntryList.listEntries.indexOf(this);
		for (int i = 0; i < owningEntryList.currentValues.length; i++) {
			if (i != myIndex && val.equals(String.valueOf(owningEntryList.currentValues[i]))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Object getValue() {
		return this.textFieldValue.getText().trim();
	}

	public void setValue(Object value) {
		this.textFieldValue.setText(value.toString());
		if (this.textFieldValue.getText().trim().isEmpty()) {
			this.isEditing = true;
			this.textFieldValue.setFocused(true);
		}
	}

	@Override
	public void drawToolTip(int mouseX, int mouseY) {
		super.drawToolTip(mouseX, mouseY);
	}
}