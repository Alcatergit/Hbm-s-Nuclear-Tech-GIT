package com.hbm.config;

import java.util.HashSet;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class PotionConfig {

	public static boolean doJumpBoost = true;
	public static int potionSickness = 0;
	public static HashSet<String> potionBlacklist;

	public static void loadFromConfig(Configuration config){
		final String CATEGORY_POTION = "08_potion_effects";
		potionBlacklist = CommonConfig.createConfigHashSet(config, CATEGORY_POTION, "08.01_hazmatPotionBlacklist", "List of Potions that get blocked while wearing a hazmat suit with bacteria protection - <potion> (String)", "String", new String[]{
			"srparasites:coth",
			"srparasites:viral"
		});
		doJumpBoost = CommonConfig.createConfigBool(config, CATEGORY_POTION, "8.02_doJumpBoost", "Whether Servos and Armors should give Jumpboost", true);
		String s = CommonConfig.createConfigString(config, CATEGORY_POTION, "8.03_potionSickness", "Valid configs include \"NORMAL\" and \"TERRARIA\", otherwise potion sickness is turned off", "OFF");
		if("normal".equals(s.toLowerCase()))
			potionSickness = 1;
		if("terraria".equals(s.toLowerCase()))
			potionSickness = 2;

	}
	
}
