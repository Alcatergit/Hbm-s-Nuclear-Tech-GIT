package com.hbm.items.special;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemHotDusted extends ItemHot {

	public ItemHotDusted(int heat, String s){
		super(heat, s);
		setHasSubtypes(true);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn){
		tooltip.add("Forged " + stack.getItemDamage() + " time(s)");
	}
	
	public static int getMaxHeat(ItemStack stack) {
		return heat - stack.getItemDamage() * 10;
	}
}
