package com.hbm.items.bomb;

import java.util.List;

import com.hbm.items.ModItems;

import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemN2 extends Item {

	public ItemN2(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18nUtil.resolveKey("desc.usedin"));
		tooltip.add(" "+ I18nUtil.resolveKey("tile.nuke_n2.name"));
	}
}
