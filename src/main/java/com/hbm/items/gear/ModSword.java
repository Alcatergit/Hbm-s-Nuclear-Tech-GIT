package com.hbm.items.gear;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class ModSword extends ItemSword {

	public ModSword(ToolMaterial t, String s){
		super(t);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(this == ModItems.saw)
			tooltip.add("Prepare for your examination!");
		if(this == ModItems.bat)
			tooltip.add("Do you like hurting other people?");
		if(this == ModItems.bat_nail)
			tooltip.add("Or is it a classic?");
		if(this == ModItems.golf_club)
			tooltip.add("Property of Miami Beach Golf Club.");
		if(this == ModItems.pipe_rusty)
			tooltip.add("Ouch! Ouch! Ouch!");
		if(this == ModItems.pipe_lead)
			tooltip.add("Manually override anything by smashing it with this pipe.");
			//list.add("I'm going to attempt a manual override on this wall.");
		if(this == ModItems.reer_graar) {
			tooltip.add("Call now!");
			tooltip.add("555-10-3728-ZX7-INFINITE");
		}
	}
}
