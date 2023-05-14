package com.hbm.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public class EnchantmentUtil {

	public static void addEnchantment(ItemStack stack, Enchantment enchantment, int level) {

		stack.addEnchantment(enchantment, level);
	}

	public static void removeEnchantment(ItemStack stack, Enchantment enchantment) {
		if(stack.getEnchantmentTagList() == null || !stack.hasTagCompound())
			return;
		
		int i = 0;
		for( ; i < stack.getEnchantmentTagList().tagCount(); i++) {
			if(stack.getEnchantmentTagList().getCompoundTagAt(i).getShort("id") == Enchantment.getEnchantmentID(enchantment))
				break;
		}

		if(i < stack.getEnchantmentTagList().tagCount())
			stack.getEnchantmentTagList().removeTag(i);

		if(stack.getEnchantmentTagList().tagCount() == 0)
			stack.getTagCompound().removeTag("ench");
	}

	public static int xpBarCap(int level) {
        return level >= 30 ? 62 + (level - 30) * 7 : (level >= 15 ? 17 + (level - 15) * 3 : 17);
    }

    public static int getLevelForExperience(int xp) {
    	
		int level = 0;
		
		while (true) {
			
			int xpCap = xpBarCap(level);
			
			if (xp < xpCap)
				return level;
			
			xp -= xpCap;
			level++;
		}
	}

	public static void addExperience(EntityPlayer player, int xp, boolean silent) {

		int j = Integer.MAX_VALUE - player.experienceTotal;

		if(xp > j) {
			xp = j;
		}

		player.experience += (float) xp / (float) player.xpBarCap();

		for(player.experienceTotal += xp; player.experience >= 1.0F; player.experience /= (float) player.xpBarCap()) {
			player.experience = (player.experience - 1.0F) * (float) player.xpBarCap();

			if(silent)
				addExperienceLevelSilent(player, 1);
			else
				player.addExperienceLevel(1);
		}
	}

	public static void setExperience(EntityPlayer player, int xp) {

		player.experienceLevel = 0;
		player.experience = 0.0F;
		player.experienceTotal = 0;

		addExperience(player, xp, true);
	}

	public static void addExperienceLevelSilent(EntityPlayer player, int level) {
		player.experienceLevel += level;

		if(player.experienceLevel < 0) {
			player.experienceLevel = 0;
			player.experience = 0.0F;
			player.experienceTotal = 0;
		}
	}

	/** Fun fact: experienceTotal lies in 1.7.10 and has no actual purpose other than misleading people! */
	/** Fun fact: experienceTotal lies no more in 1.12.2 yay */
	public static int getTotalExperience(EntityPlayer player) {
		return player.experienceTotal;
	}
}