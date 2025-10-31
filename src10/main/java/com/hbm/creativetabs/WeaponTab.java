package com.hbm.creativetabs;

import com.hbm.items.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class WeaponTab extends CreativeTabs {

	public WeaponTab(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack createIcon() {
		if(ModItems.gun_lever_action != null){
			return new ItemStack(ModItems.gun_lever_action);
		}
		return new ItemStack(Items.IRON_PICKAXE);
	}

}
