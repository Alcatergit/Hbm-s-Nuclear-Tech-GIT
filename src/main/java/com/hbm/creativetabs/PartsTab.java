package com.hbm.creativetabs;

import com.hbm.items.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class PartsTab extends CreativeTabs {

	public PartsTab(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack getTabIconItem() {
		if(ModItems.ingot_uranium != null){
			return new ItemStack(ModItems.ingot_uranium);
		}
		return new ItemStack(Items.IRON_PICKAXE);
	}

}
