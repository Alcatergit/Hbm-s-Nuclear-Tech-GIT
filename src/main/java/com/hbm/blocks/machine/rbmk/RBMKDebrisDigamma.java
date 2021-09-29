package com.hbm.blocks.machine.rbmk;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.ForgeDirection;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RBMKDebrisDigamma extends RBMKDebris {

	public RBMKDebrisDigamma(String s){
		super(s);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
		if(!world.isRemote) {
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				Block b = world.getBlockState(new BlockPos(pos.getX() + dir.offsetX, pos.getY() + dir.offsetY, pos.getZ() + dir.offsetZ)).getBlock();
				if((b instanceof RBMKDebris  && b != this) || b == ModBlocks.corium_block || b == ModBlocks.block_corium)
					world.setBlockState(new BlockPos(pos.getX() + dir.offsetX, pos.getY() + dir.offsetY, pos.getZ() + dir.offsetZ), this.getDefaultState());
			}
		}
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){
		super.onBlockAdded(worldIn, pos, state);
		worldIn.scheduleUpdate(pos, this, 2);
	}
	
}
