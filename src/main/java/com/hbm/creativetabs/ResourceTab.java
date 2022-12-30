package com.hbm.creativetabs;

import com.hbm.blocks.ModBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ResourceTab extends CreativeTabs {

	public ResourceTab(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack getTabIconItem() {
		if(ModBlocks.ore_uranium != null){
			return new ItemStack(Item.getItemFromBlock(ModBlocks.ore_uranium));
		}
		return new ItemStack(Items.IRON_PICKAXE);
	}

}
