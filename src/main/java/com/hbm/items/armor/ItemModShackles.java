package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemModShackles extends ItemArmorMod {

	public ItemModShackles(String s) {
		super(ArmorModHandler.extra, false, false, true, false, s);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn){
		list.add(TextFormatting.RED + "You will speak when I ask you to.");
		list.add(TextFormatting.RED + "You will eat when I tell you to.");
		list.add(TextFormatting.RED + "" + TextFormatting.BOLD + "You will die when I allow you to.");
		
		list.add("");
		list.add(TextFormatting.GOLD + "∞ revives left");
		list.add("");
		super.addInformation(stack, worldIn, list, flagIn);
	}
	
	@Override
	public void addDesc(List<String> list, ItemStack stack, ItemStack armor) {
		list.add(TextFormatting.GOLD + "  " + stack.getDisplayName() + " (∞ revives left)");
	}
}
