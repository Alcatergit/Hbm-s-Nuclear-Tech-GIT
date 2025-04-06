package com.hbm.explosion.vanillant.standard;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.interfaces.IBlockMutator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class BlockMutatorFire implements IBlockMutator {

	@Override public void mutatePre(ExplosionVNT explosion, IBlockState block,  BlockPos pos) { }

	@Override
	public void mutatePost(ExplosionVNT explosion, BlockPos pos) {

		Block block = explosion.world.getBlockState(pos).getBlock();
		Block block1 = explosion.world.getBlockState(new BlockPos(pos.getX(), pos.getY()-1, pos.getZ())).getBlock();
		if(block.getBlockState().getBaseState().getMaterial() == Material.AIR && block1.isOpaqueCube(block1.getDefaultState()) && explosion.world.rand.nextInt(3) == 0) {
			explosion.world.setBlockState(pos, Blocks.FIRE.getDefaultState());
		}
	}
}
