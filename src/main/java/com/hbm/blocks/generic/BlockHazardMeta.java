package com.hbm.blocks.generic;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.SoundType;
import net.minecraft.world.World;

public class BlockHazardMeta extends BlockHazard {
	
	public static final PropertyInteger META = PropertyInteger.create("meta", 0, 15);
	
	public BlockHazardMeta(Material m, String s){
		super(m, s);
	}

	public BlockHazardMeta(Material mat, SoundType type, String s) {
		super(mat, type, s);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{META});
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(META);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(META, meta);
	}
}
