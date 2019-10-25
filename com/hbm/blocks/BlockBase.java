package com.hbm.blocks;

import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockBase extends Block {
	
	public BlockBase(Material m, String s){
		super(m);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.tabTest);
		ModBlocks.ALL_BLOCKS.add(this);
	}

	public Block setSoundType(SoundType sound){
		return super.setSoundType(sound);
	}
}
