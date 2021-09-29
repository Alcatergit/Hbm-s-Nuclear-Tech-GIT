package com.hbm.items.food;

import com.hbm.items.ModItems;

import net.minecraft.item.ItemFood;

public class ItemFoodBase extends ItemFood {

	public ItemFoodBase(int amount, float saturation, boolean isWolfFood, String s){
		super(amount, saturation, isWolfFood);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}

}
