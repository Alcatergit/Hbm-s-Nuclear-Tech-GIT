package com.hbm.items.machine;

import com.hbm.items.special.ItemCustomLore;

public class ItemBlades extends ItemCustomLore {
	public ItemBlades(String s, int i){
		super(s);
		this.setMaxStackSize(1);
		this.setTranslationKey(s);
		this.setMaxDamage(i);
	}
}
