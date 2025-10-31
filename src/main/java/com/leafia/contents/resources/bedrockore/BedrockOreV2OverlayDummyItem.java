package com.leafia.contents.resources.bedrockore;

import com.hbm.items.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

// lovely name
public class BedrockOreV2OverlayDummyItem extends Item {
	public BedrockOreV2OverlayDummyItem(String s) {
		this.setRegistryName(s);
		ModItems.ALL_ITEMS.add(this);
	}
	@Override
	public void getSubItems(CreativeTabs tab,NonNullList<ItemStack> items) { }
}
