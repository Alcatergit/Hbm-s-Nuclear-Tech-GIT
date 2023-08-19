package com.hbm.blocks.generic;

import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHazardFuel extends BlockHazard {

	private int burntime;
	public int encouragement;
	public int flammability;
	
	public BlockHazardFuel(Material m, String s, int en, int flam, int burntime){
		super(m, s);
		this.encouragement = en;
		this.flammability = flam;
		this.burntime = burntime;
	}

	public int getBurnTime(){
		return burntime;
	}
	
	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face){
		return flammability;
	}
	
	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face){
		return encouragement;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face){
		return true;
	}

	@Override
	public boolean isFireSource(World world, BlockPos pos, EnumFacing side){
		return true;
	}
}
