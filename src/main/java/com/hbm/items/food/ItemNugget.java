package com.hbm.items.food;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemNugget extends ItemFood {

	public ItemNugget(int amount, boolean isWolfFood, String s) {
		super(amount, isWolfFood);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(this == ModItems.gun_moist_nugget){
			tooltip.add("A Mosin-Na...no wait, it's");
			tooltip.add("just a moist nugget.");
		}
	}
	
}
