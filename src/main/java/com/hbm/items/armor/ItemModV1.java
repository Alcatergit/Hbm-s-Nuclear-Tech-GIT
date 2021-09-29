package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemModV1 extends ItemArmorMod {

	public ItemModV1(String s){
		super(ArmorModHandler.extra, false, true, false, false, s);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn){
		list.add(TextFormatting.RED + "BLOOD IS FUEL");
		list.add("");
		super.addInformation(stack, worldIn, list, flagIn);
	}
	
	@Override
	public void addDesc(List<String> list, ItemStack stack, ItemStack armor){
		list.add(TextFormatting.RED + "  " + stack.getDisplayName() + " (BLOOD IS FUEL)");
	}
}
