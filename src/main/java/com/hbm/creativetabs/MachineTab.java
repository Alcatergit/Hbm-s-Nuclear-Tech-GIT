package com.hbm.creativetabs;

import com.hbm.blocks.ModBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MachineTab extends CreativeTabs {

	public MachineTab(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack getTabIconItem() {
		if(ModBlocks.reactor_element != null)
			return new ItemStack(Item.getItemFromBlock(ModBlocks.reactor_element));
		return new ItemStack(Items.IRON_PICKAXE);
	}

}
