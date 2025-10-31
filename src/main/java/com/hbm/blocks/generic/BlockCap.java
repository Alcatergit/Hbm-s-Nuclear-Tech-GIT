package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems.Foods;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockCap extends BlockRotatablePillar {

	public BlockCap(Material materialIn, String s) {
		super(materialIn, s);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if(this == ModBlocks.block_cap_nuka)
			return Foods.cap_nuka;
		if(this == ModBlocks.block_cap_quantum)
			return Foods.cap_quantum;
		if(this == ModBlocks.block_cap_sparkle)
			return Foods.cap_sparkle;
		if(this == ModBlocks.block_cap_rad)
			return Foods.cap_rad;
		if(this == ModBlocks.block_cap_korl)
			return Foods.cap_korl;
		if(this == ModBlocks.block_cap_fritz)
			return Foods.cap_fritz;
		if(this == ModBlocks.block_cap_sunset)
			return Foods.cap_sunset;
		if(this == ModBlocks.block_cap_star)
			return Foods.cap_star;
		return Items.AIR;
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return 128;
	}

}
