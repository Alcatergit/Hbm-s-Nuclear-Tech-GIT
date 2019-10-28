package com.hbm.items.gear;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class AxeSchrabidium extends ItemAxe {
	public AxeSchrabidium(ToolMaterial t, String s){
		super(t, t.getAttackDamage(), -3.2F);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		this.setMaxStackSize(1);
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.RARE;
	}
}
