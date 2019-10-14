package com.hbm.items.special;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.item.Item;

public class ItemBlades extends Item {
	public ItemBlades(String s){
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.tabTest);
		ModItems.ALL_ITEMS.add(this);
	}
}
