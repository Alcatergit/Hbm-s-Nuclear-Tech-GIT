package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemModCladding extends ItemArmorMod {
	
	public double rad;
	
	public ItemModCladding(double rad, String s) {
		super(ArmorModHandler.cladding, true, true, true, true, s);
		this.rad = rad;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn){
		list.add(TextFormatting.YELLOW + "+" + rad + " rad-resistance");
		list.add("");
		super.addInformation(stack, worldIn, list, flagIn);
	}

	@Override
	public void addDesc(List<String> list, ItemStack stack, ItemStack armor) {
		list.add(TextFormatting.YELLOW + "  " + stack.getDisplayName() + " (+" + rad + " radiation resistence)");
	}
}
