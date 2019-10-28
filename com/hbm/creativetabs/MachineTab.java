package com.hbm.creativetabs;

import com.hbm.blocks.ModBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MachineTab extends CreativeTabs {

	public MachineTab(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack getTabIconItem() {
		//TODO this machine
		//if(ModBlocks.machine_generator != null){
		//	return new ItemStack(Item.getItemFromBlock(ModBlocks.machine_rtg_grey));
	//	}
		return new ItemStack(Items.IRON_PICKAXE);
	}

}
