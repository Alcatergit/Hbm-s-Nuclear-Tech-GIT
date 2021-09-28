package com.hbm.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class PotionConfig {

	public static int taintID = 62;
	public static int radiationID = 63;
	public static int bangID = 64;
	public static int mutationID = 65;
	public static int radxID = 66;
	public static int leadID = 67;
	
	public static int potionSickness = 0;
	
	public static void loadFromConfig(Configuration config){
		final String CATEGORY_POTION = "08_potion_effects";
		Property propTaintID = config.get(CATEGORY_POTION, "8.00_taintPotionID", 62);
		propTaintID.setComment("What potion ID the taint effect will have");
		taintID = propTaintID.getInt();
		Property propRadiationID = config.get(CATEGORY_POTION, "8.01_radiationPotionID", 63);
		propRadiationID.setComment("What potion ID the radiation effect will have");
		radiationID = propRadiationID.getInt();
		Property propBangID = config.get(CATEGORY_POTION, "8.02_bangPotionID", 64);
		propBangID.setComment("What potion ID the B93 timebomb effect will have");
		bangID = propBangID.getInt();
		Property propMutationID = config.get(CATEGORY_POTION, "8.03_mutationPotionID", 65);
		propMutationID.setComment("What potion ID the taint mutation effect will have");
		mutationID = propMutationID.getInt();
		Property propRadxID = config.get(CATEGORY_POTION, "8.04_radxPotionID", 66);
		propRadxID.setComment("What potion ID the Rad-X effect will have");
		radxID = propRadxID.getInt();
		Property propLeadID = config.get(CATEGORY_POTION, "8.05_leadPotionID", 67);
		propLeadID.setComment("What potion ID the lead poisoning effect will have");
		leadID = propLeadID.getInt();
		
		String s = CommonConfig.createConfigString(config, CATEGORY_POTION, "8.S0_potionSickness", "Valid configs include \"NORMAL\" and \"TERRARIA\", otherwise potion sickness is turned off", "OFF");

		if("normal".equals(s.toLowerCase()))
			potionSickness = 1;
		if("terraria".equals(s.toLowerCase()))
			potionSickness = 2;
	}
	
}
