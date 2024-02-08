package com.hbm.potion;

import java.util.HashSet;

import com.hbm.config.PotionConfig;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public class HbmDetox {

	public static HashSet blacklistedPotions = new HashSet<String>();
	public static Potion viral;

	public static void init(){
		for(String s : PotionConfig.potionBlacklist){
			Potion p = Potion.getPotionFromResourceLocation(s);
			if(p != null)
				blacklistedPotions.add(p);
		}
	}

	public static boolean isBlacklisted(Potion p){
		return blacklistedPotions.contains(p);
	}
}
