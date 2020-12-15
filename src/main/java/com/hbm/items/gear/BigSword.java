package com.hbm.items.gear;

import com.hbm.items.ModItems;

import net.minecraft.item.ItemSword;

public class BigSword extends ItemSword {

	public BigSword(ToolMaterial material, String s) {
		super(material);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}

}
