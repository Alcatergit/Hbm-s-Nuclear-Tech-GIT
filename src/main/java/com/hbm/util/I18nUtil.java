package com.hbm.util;

import net.minecraft.client.resources.I18n;

public class I18nUtil {

	public static String resolveKey(String s, Object... args) {
		return I18n.format(s, args);
	}

	public static String[] resolveKeyArray(String s, Object... args) {
		return resolveKey(s, args).split("\\$");
	}

	public static class leafia {
		public static String[] statusDecimals(String template,double value,int decimals) {
			double mul = Math.pow(10,decimals);
			return resolveKeyArray(template,String.format("%01."+decimals+"f",Math.floor(value*mul+0.5)/mul));
		}
	}
}
