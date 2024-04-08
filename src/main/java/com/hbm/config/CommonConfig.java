package com.hbm.config;

import java.util.HashMap;
import java.util.HashSet;

import com.hbm.main.MainRegistry;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class CommonConfig {

	public static boolean createConfigBool(Configuration config, String category, String name, String comment, boolean def) {
	
	    Property prop = config.get(category, name, def);
	    prop.setComment(comment);
	    return prop.getBoolean();
	}
	
	public static String createConfigString(Configuration config, String category, String name, String comment, String def) {

		Property prop = config.get(category, name, def);
		prop.setComment(comment);
		return prop.getString();
	}

	public static String[] createConfigStringList(Configuration config, String category, String name, String comment, String[] defaultValues) {

		Property prop = config.get(category, name, defaultValues);
		prop.setComment(comment);
		return prop.getStringList();
	}

	public static HashMap createConfigHashMap(Configuration config, String category, String name, String comment, String keyType, String valueType, String[] defaultValues, String splitReg) {
		HashMap<Object, Object> configDictionary = new HashMap<>();
		Property prop = config.get(category, name, defaultValues);
		prop.setComment(comment);
		for(String entry: prop.getStringList()){
			String[] pairs = entry.split(splitReg, 0);
			configDictionary.put(parseType(pairs[0], keyType), parseType(pairs[1], valueType));
		}
		return configDictionary;
	}

	public static HashSet createConfigHashSet(Configuration config, String category, String name, String comment, String valueType, String[] defaultValues) {
		HashSet<Object> configSet = new HashSet<>();
		Property prop = config.get(category, name, defaultValues);
		prop.setComment(comment);
		for(String entry: prop.getStringList()){
			configSet.add(parseType(entry, valueType));
		}
		return configSet;
	}

	private static Object parseType(String value, String type){
		if(type == "Float"){
			return Float.parseFloat(value);
		}
		if(type == "Int"){
			return Integer.parseInt(value);
		}
		if(type == "Long"){
			return Float.parseFloat(value);
		}
		if(type == "Double"){
			return Double.parseDouble(value);
		}
		return value;
	}

	public static int createConfigInt(Configuration config, String category, String name, String comment, int def) {
	
	    Property prop = config.get(category, name, def);
	    prop.setComment(comment);
	    return prop.getInt();
	}

	public static double createConfigDouble(Configuration config, String category, String name, String comment, double def) {
	
	    Property prop = config.get(category, name, def);
	    prop.setComment(comment);
	    return prop.getDouble();
	}

	public static int setDefZero(int value, int def) {

		if(value < 0) {
			MainRegistry.logger.error("Fatal error config: Randomizer value has been below zero, despite bound having to be positive integer!");
			MainRegistry.logger.error(String.format("Errored value will default back to %d, PLEASE REVIEW CONFIGURATION DESCRIPTION BEFORE MEDDLING WITH VALUES!", def));
			return def;
		}

		return value;
	}
	
	public static int setDef(int value, int def) {
	
		if(value <= 0) {
			MainRegistry.logger.error("Fatal error config: Randomizer value has been set to zero, despite bound having to be positive integer!");
			MainRegistry.logger.error(String.format("Errored value will default back to %d, PLEASE REVIEW CONFIGURATION DESCRIPTION BEFORE MEDDLING WITH VALUES!", def));
			return def;
		}
	
		return value;
	}

}
