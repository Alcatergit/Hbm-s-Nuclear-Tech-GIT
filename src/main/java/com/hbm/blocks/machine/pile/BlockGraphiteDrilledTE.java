package com.hbm.blocks.machine.pile;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockGraphiteDrilledTE extends BlockGraphiteDrilledBase implements ITileEntityProvider {

	public BlockGraphiteDrilledTE(String s) {
		super(s);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state){
		return true;
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){
		super.onBlockAdded(worldIn, pos, state);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}
}
