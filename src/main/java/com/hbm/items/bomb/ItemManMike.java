package com.hbm.items.bomb;

import com.hbm.items.ModItems;
import com.hbm.items.special.ItemHazard;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;
import com.leafia.dev.MultiRad.RadiationType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemManMike extends ItemHazard {

	public ItemManMike(float radiation, String s) {
		super(RadiationType.ALPHA, radiation, s);
		this.setCreativeTab(MainRegistry.nukeTab);
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn) {
		list.add(I18nUtil.resolveKey("desc.usedin"));
		if(this == ModItems.man_core)
			list.add(" "+ I18nUtil.resolveKey("tile.nuke_man.name"));
		list.add(" "+ I18nUtil.resolveKey("tile.nuke_mike.name"));
		list.add(" "+ I18nUtil.resolveKey("tile.nuke_tsar.name"));
		super.addInformation(stack, world, list, flagIn);
	}
}
