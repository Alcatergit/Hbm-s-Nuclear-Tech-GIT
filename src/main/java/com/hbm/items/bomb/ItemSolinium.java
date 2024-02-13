package com.hbm.items.bomb;

import java.util.List;

import com.hbm.items.special.ItemHazard;
import com.hbm.main.MainRegistry;

import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSolinium extends ItemHazard {

	public ItemSolinium(float radiation, boolean blinding, String s) {
		super(radiation, false, blinding, s);
		this.setCreativeTab(MainRegistry.nukeTab);
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn) {
		list.add(I18nUtil.resolveKey("desc.usedin"));
		list.add(" " + I18nUtil.resolveKey("trait.soliniumbomb"));
		super.addInformation(stack, world, list, flagIn);
	}
}
