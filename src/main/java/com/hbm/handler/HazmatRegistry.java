package com.hbm.handler;

import java.util.HashMap;
import java.util.Map;

import com.hbm.potion.HbmPotion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HazmatRegistry {
	private static Map<Item, Float> entries = new HashMap<Item, Float>();
	
	public static void registerHazmat(Item item, float resistance) {
		
		entries.put(item, resistance);
	}
	
	public static float getResistance(ItemStack stack) {
		
		if(stack == null)
			return 0;
		
		float cladding = getCladding(stack);
		
		Float f = entries.get(stack.getItem());
		
		if(f != null)
			return f + cladding;
		
		return cladding;
	}
	
	public static float getCladding(ItemStack stack) {

		if(stack.hasTagCompound() && stack.getTagCompound().getFloat("hfr_cladding") > 0)
			return stack.getTagCompound().getFloat("hfr_cladding");
		
		return 0;
	}
	
	public static float getResistance(EntityLivingBase player) {
		float res = 0.0F;
		
		for(ItemStack stack : player.getArmorInventoryList()) {
			if(!stack.isEmpty()) {
				res += getResistance(stack);
			}
		}
		
		if(player.isPotionActive(HbmPotion.radx))
			res += 0.4F;
		
		return res;
		
	}
}
