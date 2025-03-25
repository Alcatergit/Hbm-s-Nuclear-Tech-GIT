package com.hbm.blocks.items;

import com.hbm.blocks.generic.BlockFuel;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemFuelBlock extends ItemBlock {

	private int burntime = 0;
	
	public ItemFuelBlock(Block block) {
		super(block);
		if(block instanceof BlockFuel) {
			this.burntime = ((BlockFuel)block).getBurnTime();
		}
	}
	
	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return burntime;
	}
}
