package com.hbm.explosion.vanillant.interfaces;

import com.hbm.explosion.vanillant.ExplosionVNT;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public interface IBlockMutator {

	public void mutatePre(ExplosionVNT explosion, IBlockState blockState, BlockPos pos);
	public void mutatePost(ExplosionVNT explosion, BlockPos pos);
}
