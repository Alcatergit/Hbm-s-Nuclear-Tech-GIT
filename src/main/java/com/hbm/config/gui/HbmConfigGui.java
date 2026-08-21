package com.hbm.config.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.hbm.config.BedrockOreJsonConfig;
import com.hbm.config.BombConfig;
import com.hbm.config.CompatibilityConfig;
import com.hbm.config.GeneralConfig;
import com.hbm.config.MachineConfig;
import com.hbm.config.MobConfig;
import com.hbm.config.PotionConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.config.ToolConfig;
import com.hbm.config.WeaponConfig;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.inventory.AssemblerRecipes;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.ConfigGuiType;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class HbmConfigGui extends GuiConfig {

	private static final List<Configuration> configs = new ArrayList<>();
	private static final List<TextFileConfigElement> textFileConfigs = new ArrayList<>();
	static GuiConfig instance;

	static String getTitle() {
		return "§e§l" + RefStrings.NAME + "§r§r";
	}

	public HbmConfigGui(GuiScreen parent) {
		super(parent, getConfigElements(), RefStrings.MODID, true, false,
				getTitle(), null);
		instance = this;
	}

	static void saveAllConfigs() {
		for (Configuration config : configs) {
			if (config != null) {
				config.save();
			}
		}
		for (TextFileConfigElement elem : textFileConfigs) {
			elem.saveToFile();
		}
		BedrockOreJsonConfig.writeToJson();
		if (!instance.allRequireWorldRestart || !instance.isWorldRunning) {
			MainRegistry.reloadConfig();
			AssemblerRecipes.loadRecipes();
		}
	}

	@Override
	public void onGuiClosed() {
		saveAllConfigs();
		super.onGuiClosed();
	}

	private static final Map<String, Consumer<Configuration>> CFG_LOADERS = new HashMap<>();
	static {
		CFG_LOADERS.put("hbm.cfg", config -> {
			GeneralConfig.loadFromConfig(config);
			MachineConfig.loadFromConfig(config);
			BombConfig.loadFromConfig(config);
			RadiationConfig.loadFromConfig(config);
			PotionConfig.loadFromConfig(config);
			ToolConfig.loadFromConfig(config);
			WeaponConfig.loadFromConfig(config);
			MobConfig.loadFromConfig(config);
		});
		CFG_LOADERS.put("hbm_dimensions.cfg", config -> {
			CompatibilityConfig.loadFromConfig(config);
		});
	}

	private static final Map<String, String> TEXT_FILE_FALLBACK_HEADERS = new HashMap<>();
	static {
		TEXT_FILE_FALLBACK_HEADERS.put("solinium.cfg",
				ExplosionNukeGeneric.SOLINIUM_CONFIG_HEADER);
		TEXT_FILE_FALLBACK_HEADERS.put("assemblerConfig.cfg",
				AssemblerRecipes.ASSEMBLER_CONFIG_HEADER);
	}

	private static List<IConfigElement> getConfigElements() {
		configs.clear();
		List<IConfigElement> list = new ArrayList<>();

		File configDir = new File(MainRegistry.proxy.getDataDir().getPath() + "/config/hbm");
		File[] files = configDir.listFiles();
		if (files == null)
			return list;

		for (File file : files) {
			String name = file.getName();
			String displayName = transformRootName(name);
			if (name.endsWith(".cfg")) {
				if (TEXT_FILE_FALLBACK_HEADERS.containsKey(name)) {
					TextFileConfigElement elem = new TextFileConfigElement(file, displayName, name,
							TEXT_FILE_FALLBACK_HEADERS.get(name));
					textFileConfigs.add(elem);
					list.add(elem);
				} else {
					Configuration config = new Configuration(file);
					config.load();
					Consumer<Configuration> loader = CFG_LOADERS.get(name);
					if (loader != null) {
						loader.accept(config);
					}
					if (config.getCategoryNames().isEmpty()) {
						list.add(new HbmCategoryElement(displayName, name, new ArrayList<>()));
					} else {
						configs.add(config);
						list.add(buildCfgRootCategory(displayName, name, config));
					}
				}
			} else if (name.endsWith(".json")) {
				BedrockOreJsonConfig.loadFromJson();
				list.add(buildJsonRootCategory(displayName, name));
			}
		}

		return list;
	}

	private static IConfigElement buildCfgRootCategory(String displayName, String originalName, Configuration config) {
		List<IConfigElement> children = new ArrayList<>();
		for (String categoryName : config.getCategoryNames()) {
			if (config.getCategory(categoryName).getOrderedValues().isEmpty())
				continue;
			ConfigElement catElement = new ConfigElement(config.getCategory(categoryName));
			List<IConfigElement> wrappedChildren = new ArrayList<>();
			for (IConfigElement child : catElement.getChildElements()) {
				wrappedChildren.add(new NameTransformedConfigElement(child));
			}
			children.add(new HbmCategoryElement(
					transformName(categoryName), categoryName, wrappedChildren));
		}
		return new HbmCategoryElement(displayName, originalName, children);
	}

	private static IConfigElement buildJsonRootCategory(String displayName, String originalName) {
		return new JsonRootConfigElement(displayName, originalName);
	}

	static String transformName(String raw) {
		raw = raw.replaceFirst("^\\d+(\\.[A-Za-z0-9]+)*_", "");
		raw = raw.replaceAll("([a-z])([A-Z])", "$1 $2");
		raw = raw.replaceAll("([A-Z])([A-Z][a-z])", "$1 $2");
		raw = raw.replace('_', ' ');
		if (raw.length() > 0) {
			raw = Character.toUpperCase(raw.charAt(0)) + raw.substring(1);
		}
		return raw;
	}

	static String transformRootName(String fileName) {
		int dotIndex = fileName.lastIndexOf('.');
		if (dotIndex > 0) {
			fileName = fileName.substring(0, dotIndex);
		}
		String transformed = transformName(fileName);
		String[] words = transformed.split(" ");
		StringBuilder sb = new StringBuilder();
		for (String word : words) {
			if (word.length() > 0) {
				sb.append(Character.toUpperCase(word.charAt(0)));
				if (word.length() > 1) {
					sb.append(word.substring(1));
				}
				sb.append(' ');
			}
		}
		return sb.toString().trim();
	}

	static List<IConfigElement> buildDimensionSubElements(int dimID,
			Object[] currentValues, int index) {
		int[] dimRef = new int[] { dimID };
		List<IConfigElement> children = new ArrayList<>();
		children.add(new DimensionPropertyElement(dimRef, "dimID", ConfigGuiType.INTEGER,
				currentValues, index));
		children.add(new DimensionPropertyElement(dimRef, "oreRarity", ConfigGuiType.INTEGER,
				currentValues, index));
		children.add(new DimensionPropertyElement(dimRef, "isWhiteList", ConfigGuiType.BOOLEAN,
				currentValues, index));
		children.add(new DimensionOreListElement(dimRef, "Bedrock Ores"));
		return children;
	}
}
