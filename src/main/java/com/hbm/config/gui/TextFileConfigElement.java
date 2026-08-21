package com.hbm.config.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraftforge.fml.client.config.ConfigGuiType;
import net.minecraftforge.fml.client.config.GuiConfigEntries.IConfigEntry;
import net.minecraftforge.fml.client.config.GuiEditArrayEntries.IArrayEntry;

public class TextFileConfigElement extends AbstractConfigElement {

	private final File file;
	private final String displayName;
	private final String originalName;
	private final String fallbackHeader;
	private final List<String> lines = new ArrayList<>();
	private String header = "";

	TextFileConfigElement(File file, String displayName, String originalName, String fallbackHeader) {
		this.file = file;
		this.displayName = displayName;
		this.originalName = originalName;
		this.fallbackHeader = fallbackHeader;
		reloadFromFile();
	}

	private void reloadFromFile() {
		lines.clear();
		header = fallbackHeader;
		if (!file.exists())
			return;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			StringBuilder headerBuilder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("#")) {
					headerBuilder.append(line).append("\n");
				} else if (!line.trim().isEmpty()) {
					lines.add(line.trim());
				}
			}
			if (headerBuilder.length() > 0) {
				header = headerBuilder.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveToFile() {
		try (FileWriter writer = new FileWriter(file)) {
			writer.write(header);
			for (String line : lines) {
				writer.write(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isProperty() {
		return false;
	}

	@Override
	public Class<? extends IConfigEntry> getConfigEntryClass() {
		return TextFileCategoryEntry.class;
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
		return originalName;
	}

	@Override
	public String getLanguageKey() {
		return originalName;
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
	public boolean isDefault() {
		return lines.isEmpty();
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
		lines.clear();
		saveToFile();
	}

	@Override
	public Object get() {
		return null;
	}

	@Override
	public Object[] getList() {
		return lines.toArray(new String[0]);
	}

	@Override
	public void set(Object value) {
		if (value instanceof String[]) {
			lines.clear();
			Collections.addAll(lines, (String[]) value);
			saveToFile();
		}
	}

	@Override
	public void set(Object[] aVal) {
		lines.clear();
		for (Object o : aVal) {
			lines.add(String.valueOf(o));
		}
		saveToFile();
	}
}