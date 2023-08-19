package com.hbm.items.bomb;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.items.special.ItemHazard;
import com.hbm.main.MainRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemManMike extends ItemHazard {

	public ItemManMike(float radiation, String s) {
		super(radiation, s);
		this.setCreativeTab(MainRegistry.nukeTab);
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn) {
		list.add("Used in:");
		if(this == ModItems.man_core)
			list.add(" Fat Man");
		list.add(" Ivy Mike");
		list.add(" Tsar Bomba");
		super.addInformation(stack, world, list, flagIn);
	}
}
