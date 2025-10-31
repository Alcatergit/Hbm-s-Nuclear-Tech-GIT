package com.hbm.items.machine;

import com.hbm.items.special.ItemHazard;

public class ItemBlades extends ItemHazard {
	public ItemBlades(String s, int i){
		super(s);
		this.setMaxStackSize(1);
		this.setTranslationKey(s);
		this.setMaxDamage(i);
		this.addSharp(40);
	}
}
