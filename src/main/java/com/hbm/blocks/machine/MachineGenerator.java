package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class MachineGenerator extends Block {

	
	
	public MachineGenerator(Material m, String s) {
		super(m);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.machineTab);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ModItems.circuit_targeting_tier3;
	}
	
}
