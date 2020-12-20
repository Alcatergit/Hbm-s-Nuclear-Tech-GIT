package com.hbm.items.machine;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.item.Item;

public class ItemBlades extends Item {
	public ItemBlades(String s, int i){
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		this.setMaxStackSize(1);
		this.setMaxDamage(i);
		ModItems.ALL_ITEMS.add(this);
	}

}
