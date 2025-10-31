package com.hbm.creativetabs;

import com.hbm.items.ModItems.Materials.Ingots;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class PartsTab extends CreativeTabs {

	public PartsTab(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack createIcon() {
		if(Ingots.ingot_uranium != null){
			return new ItemStack(Ingots.ingot_uranium);
		}
		return new ItemStack(Items.IRON_PICKAXE);
	}

}
