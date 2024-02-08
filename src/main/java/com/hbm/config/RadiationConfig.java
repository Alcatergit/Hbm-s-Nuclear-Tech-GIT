package com.hbm.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class RadiationConfig {

	public static int rain = 0;
	public static int cont = 0;
	public static int fogRad = 100;
	public static int fogCh = 50;
	public static int worldRad = 10;
	public static int worldRadThreshold = 20;
	public static boolean worldRadEffects = true;
	public static int blocksFallCh = 100;
	
	//Drillgon200: Not sure why I put these here, but oh well.
	public static int railgunDamage = 1000;
	public static int railgunBuffer = 500000000;
	public static int railgunUse = 250000000;
	public static int fireDuration = 4 * 20;
	public static boolean neutronActivation = true;
	public static int neutronActivationThreshold = 15;

	public static int geigerX = 16;
	public static int geigerY = 2;
	public static int digammaX = 16;
	public static int digammaY = 18;
	
	public static void loadFromConfig(Configuration config) {
		final String CATEGORY_NUKE = "06_explosions";
		final String CATEGORY_RAD = "07_radiation";
		// afterrain duration
		Property radRain = config.get(CATEGORY_NUKE, "6.06_falloutRainDuration", 2000);
		radRain.setComment("Duration of the thunderstorm after fallout in ticks (only large explosions)");
		rain = radRain.getInt();
		// afterrain radiation
		Property rainCont = config.get(CATEGORY_NUKE, "6.07_falloutRainRadiation", 1000);
		rainCont.setComment("Radiation in 100th RADs created by fallout rain");
		cont = rainCont.getInt();
		// fog threshold
		Property fogThresh = config.get(CATEGORY_NUKE, "6.08_fogThreshold", 100);
		fogThresh.setComment("Radiation in RADs required for fog to spawn");
		fogRad = fogThresh.getInt();
		// fog chance
		Property fogChance = config.get(CATEGORY_NUKE, "6.09_fogChance", 50);
		fogChance.setComment("1:n chance of fog spawning every second - default 1/50");
		fogCh = fogChance.getInt();
		worldRad = CommonConfig.createConfigInt(config, CATEGORY_NUKE, "6.10_worldRadCount", "How many block operations radiation can perform per tick", 10);
		worldRadThreshold = CommonConfig.createConfigInt(config, CATEGORY_NUKE, "6.11_worldRadThreshold", "The least amount of RADs required for block modification to happen", 40);
		worldRadEffects = CommonConfig.createConfigBool(config, CATEGORY_NUKE, "6.12_worldRadEffects", "Whether high radiation levels should perform changes in the world", true);
		blocksFallCh = CommonConfig.createConfigInt(config, CATEGORY_NUKE, "6.13_blocksFallingChance", "The chance (in percentage form) that a block with low blast resistance will fall down. -1 Disables falling", 100);
		// railgun
		Property railDamage = config.get(CATEGORY_NUKE, "6.11_railgunDamage", 1000);
		railDamage.setComment("How much damage a railgun death blast does per tick");
		railgunDamage = railDamage.getInt();
		Property railBuffer = config.get(CATEGORY_NUKE, "6.12_railgunBuffer", 500000000);
		railBuffer.setComment("How much RF the railgun can store");
		railgunDamage = railBuffer.getInt();
		Property railUse = config.get(CATEGORY_NUKE, "6.13_railgunConsumption", 250000000);
		railUse.setComment("How much RF the railgun requires per shot");
		railgunDamage = railUse.getInt();
		Property fireDurationP = config.get(CATEGORY_NUKE, "6.14_fireDuration", 15 * 20);
		fireDurationP.setComment("How long the fire blast will last in ticks");
		fireDuration = fireDurationP.getInt();
		
		fogCh = CommonConfig.setDef(RadiationConfig.fogCh, 20);

		neutronActivation = CommonConfig.createConfigBool(config, CATEGORY_RAD, "7.01_itemContamination", "Whether high radiation levels should radiate items in inventory", true);
		neutronActivationThreshold = CommonConfig.createConfigInt(config, CATEGORY_RAD, "7.01_itemContaminationThreshold", "Minimum recieved Rads/s threshold at which items get irradiated", 15);
		
		geigerX = CommonConfig.createConfigInt(config, CATEGORY_RAD, "7.02_geigerX", "X Coordinate of the geiger counter gui (x=0 is on the right)", 16);
		geigerY = CommonConfig.createConfigInt(config, CATEGORY_RAD, "7.03_geigerY", "Y Coordinate of the geiger counter gui (y=0 is on the bottom)", 2);
		digammaX = CommonConfig.createConfigInt(config, CATEGORY_RAD, "7.04_digammaX", "X Coordinate of the digamma diagnostic gui (x=0 is on the right)", 16);
		digammaY = CommonConfig.createConfigInt(config, CATEGORY_RAD, "7.05_digammaY", "Y Coordinate of the digamma diagnostic gui (y=0 is on the bottom)", 18);
	}

}
