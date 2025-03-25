package com.hbm.items.bomb;

import java.util.List;

import com.hbm.items.ItemBase;
import com.hbm.main.MainRegistry;

import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMan extends ItemBase {

	public ItemMan(String s){
		super(s);
		this.setCreativeTab(MainRegistry.nukeTab);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add(I18nUtil.resolveKey("desc.usedin"));
		list.add(" "+ I18nUtil.resolveKey("tile.nuke_man.name"));
		super.addInformation(stack, worldIn, list, flagIn);
	}
}
