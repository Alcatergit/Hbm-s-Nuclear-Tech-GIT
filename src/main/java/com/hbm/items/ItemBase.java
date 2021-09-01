package com.hbm.items;

import com.hbm.main.MainRegistry;

import net.minecraft.item.Item;

public class ItemBase extends Item {

	//Drillgon200: Aw man, I really should have used a helper method instead of this. Too late now.
	//Maybe if I update it to an even later version one day...
	public ItemBase(String s){
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		
		ModItems.ALL_ITEMS.add(this);
	}

}
