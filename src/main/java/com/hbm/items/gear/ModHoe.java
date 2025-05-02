package com.hbm.items.gear;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.item.ItemHoe;

public class ModHoe extends ItemHoe {
	public ModHoe(ToolMaterial t, String s){
		
		super(t);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		ModItems.ALL_ITEMS.add(this);
	}
}
