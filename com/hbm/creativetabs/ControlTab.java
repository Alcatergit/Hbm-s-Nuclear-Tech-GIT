package com.hbm.creativetabs;

import com.hbm.items.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ControlTab extends CreativeTabs {

	public ControlTab(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack getTabIconItem() {
		if(ModItems.rod_uranium_fuel != null){
			return new ItemStack(ModItems.rod_uranium_fuel);
		}
		return new ItemStack(Items.IRON_PICKAXE, 1);
	}
}
