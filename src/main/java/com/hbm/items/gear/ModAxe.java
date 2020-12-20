package com.hbm.items.gear;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.item.ItemAxe;

public class ModAxe extends ItemAxe {

	public ModAxe(ToolMaterial t, String s){
		
		super(t, 6.0F, -3.2F);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		ModItems.ALL_ITEMS.add(this);
	}
	public ModAxe(ToolMaterial t, String s, float damage, float speed){
		
		super(t, t.getAttackDamage(), speed);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		this.setMaxStackSize(1);
		ModItems.ALL_ITEMS.add(this);
	}
}
