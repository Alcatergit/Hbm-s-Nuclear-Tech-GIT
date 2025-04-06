package com.hbm.creativetabs;

import com.hbm.items.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class TemplateTab extends CreativeTabs {

	public TemplateTab(int index, String label) {
		super(index, label);
		this.setBackgroundImageName("item_search.png");
	}

	@Override
	public ItemStack createIcon() {
		if(ModItems.assembly_template != null){
			return new ItemStack(ModItems.assembly_template);
		}
		return new ItemStack(Items.IRON_PICKAXE);
	}
	
	@Override
	public boolean hasSearchBar() {
		return true;
	}

}
