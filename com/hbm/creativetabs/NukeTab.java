package com.hbm.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class NukeTab extends CreativeTabs {

	public NukeTab(int index, String label) {
		super(index, label);
		this.setBackgroundImageName("nuke.png");
	}

	@Override
	public ItemStack getTabIconItem() {
		//TODO bomb
	//	if(ModBlocks.float_bomb != null){
		//	return new ItemStack(Item.getItemFromBlock(ModBlocks.float_bomb));
	//	}
		return new ItemStack(Items.IRON_PICKAXE);
	}

}
