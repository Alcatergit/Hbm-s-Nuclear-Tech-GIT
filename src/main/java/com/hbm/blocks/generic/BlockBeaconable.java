package com.hbm.blocks.generic;

import com.hbm.blocks.BlockBase;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockBeaconable extends BlockBase {

	public BlockBeaconable(Material m, String s){
		super(m, s);
	}
	
	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon){
		return true;
	}

}
