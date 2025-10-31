package com.hbm.creativetabs;

import com.hbm.blocks.ModBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockTab extends CreativeTabs {

	public BlockTab(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack createIcon() {
		if(ModBlocks.ore_uranium != null){
			return new ItemStack(Item.getItemFromBlock(ModBlocks.brick_concrete));
		}
		return new ItemStack(Items.IRON_PICKAXE);
	}

}
