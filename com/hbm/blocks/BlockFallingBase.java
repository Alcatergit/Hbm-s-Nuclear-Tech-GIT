package com.hbm.blocks;

import com.hbm.main.MainRegistry;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockFallingBase extends BlockFalling {
	
	public BlockFallingBase(Material m, String s, SoundType type){
		super(m);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		this.setSoundType(type);
		ModBlocks.ALL_BLOCKS.add(this);
	}
	

}
