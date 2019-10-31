package com.hbm.items.bomb;

import java.util.List;

import com.hbm.items.special.ItemRadioactive;
import com.hbm.main.MainRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemManMike extends ItemRadioactive {

	public ItemManMike(String s) {
		super(s);
		this.setCreativeTab(MainRegistry.nukeTab);
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn) {
		list.add("Used in:");
		list.add("Fat Man");
		list.add("Ivy Mike");
		list.add("Tsar Bomba");
	}
}
