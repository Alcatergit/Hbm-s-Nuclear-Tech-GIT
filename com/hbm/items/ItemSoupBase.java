package com.hbm.items;

import net.minecraft.item.ItemSoup;

public class ItemSoupBase extends ItemSoup {

	public ItemSoupBase(int i, String s) {
		super(i);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
}
