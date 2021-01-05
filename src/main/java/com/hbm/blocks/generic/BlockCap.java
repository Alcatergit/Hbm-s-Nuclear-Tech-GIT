package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class BlockCap extends BlockRotatablePillar {

	public BlockCap(Material materialIn, String s) {
		super(materialIn, s);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if(this == ModBlocks.block_cap_nuka)
			return ModItems.cap_nuka;
		if(this == ModBlocks.block_cap_quantum)
			return ModItems.cap_quantum;
		if(this == ModBlocks.block_cap_sparkle)
			return ModItems.cap_sparkle;
		if(this == ModBlocks.block_cap_rad)
			return ModItems.cap_rad;
		if(this == ModBlocks.block_cap_korl)
			return ModItems.cap_korl;
		if(this == ModBlocks.block_cap_fritz)
			return ModItems.cap_fritz;
		if(this == ModBlocks.block_cap_sunset)
			return ModItems.cap_sunset;
		if(this == ModBlocks.block_cap_star)
			return ModItems.cap_star;
		return Items.AIR;
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return 128;
	}

}
