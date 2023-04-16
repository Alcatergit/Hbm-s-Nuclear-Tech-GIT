package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemFusionShield extends Item {

	public long maxDamage;
	public int maxTemp;

	public ItemFusionShield(long maxDamage, int maxTemp, String s) {
		this.maxDamage = maxDamage;
		this.maxTemp = maxTemp;
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}

	public static long getShieldDamage(ItemStack stack) {

		if(!stack.hasTagCompound()) {
			return 0;
		}

		return stack.getTagCompound().getLong("damage");
	}
	
	public static void setShieldDamage(ItemStack stack, long damage) {

		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}

		stack.getTagCompound().setLong("damage", damage);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		long damage = getShieldDamage(stack);
		int percent = (int) ((maxDamage - damage) * 100 / maxDamage);

		tooltip.add("Durability: " + (maxDamage - damage) + "/" + maxDamage + " (" + percent + "%)");

		tooltip.add("Maximum Plasma Heat: " + TextFormatting.RED + "" + maxTemp + "°C");
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return (double)getShieldDamage(stack) / (double)maxDamage;
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getDurabilityForDisplay(stack) != 0;
	}
}
