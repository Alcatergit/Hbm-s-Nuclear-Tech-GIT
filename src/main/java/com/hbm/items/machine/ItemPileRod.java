package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemPileRod extends Item {
	
	public ItemPileRod(String s){
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn){
		tooltip.add(TextFormatting.YELLOW + "Use on drilled graphite to insert");
		tooltip.add(TextFormatting.YELLOW + "Use screwdriver to extract");
		tooltip.add("");
		
		if(this == ModItems.pile_rod_uranium) {
			tooltip.add(TextFormatting.GREEN + "[Reactive Fuel]");
			tooltip.add(TextFormatting.YELLOW + "Use hand drill to take core sample");
		}
		
		if(this == ModItems.pile_rod_boron) {
			tooltip.add(TextFormatting.BLUE + "[Neutron Absorber]");
			tooltip.add(TextFormatting.YELLOW + "Click to toggle");
		}
		
		if(this == ModItems.pile_rod_source || this == ModItems.pile_rod_plutonium) {
			tooltip.add(TextFormatting.LIGHT_PURPLE + "[Neutron Source]");
		}
	}
	
}