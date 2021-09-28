package com.hbm.blocks.generic;

import com.hbm.blocks.BlockBase;

import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockFlammable extends BlockBase {

	public int encouragement;
	public int flammability;
	
	public BlockFlammable(Material m, int en, int flam, String s){
		super(m, s);
		this.encouragement = en;
		this.flammability = flam;
	}
	
	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face){
		return flammability;
	}
	
	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face){
		return encouragement;
	}

}
