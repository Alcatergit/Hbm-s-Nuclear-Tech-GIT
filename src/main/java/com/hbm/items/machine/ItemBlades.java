package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlades extends Item {
	public ItemBlades(String s, int i){

		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setMaxDamage(i);
		this.setMaxStackSize(1);
		this.setCreativeTab(MainRegistry.controlTab);
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(stack.getMaxDamage() > 0 && stack.getItemDamage() == 0) tooltip.add("Durability: "+ stack.getMaxDamage() + " / " + stack.getMaxDamage());
	}
}
