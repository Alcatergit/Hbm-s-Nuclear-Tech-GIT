package com.hbm.items;

import com.hbm.main.MainRegistry;

import net.minecraft.item.Item;

public class ItemBase extends Item {

	public ItemBase(String s){
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		ModItems.ALL_ITEMS.add(this);
		
	}

}
