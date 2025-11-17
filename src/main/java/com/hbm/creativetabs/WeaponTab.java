package com.hbm.creativetabs;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class WeaponTab extends CreativeTabs {

	public WeaponTab(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack createIcon() {
		if(ModBlocks.turret_friendly != null){
			return new ItemStack(Item.getItemFromBlock(ModBlocks.turret_friendly));
		}
		return new ItemStack(Items.IRON_PICKAXE);
	}

}
