package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.ForgeDirection;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockNuclearWaste extends BlockHazard {

	public BlockNuclearWaste(String s){
		super(s);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
		ForgeDirection dir = ForgeDirection.getOrientation(rand.nextInt(6));
		
		if(rand.nextInt(2) == 0 && world.getBlockState(new BlockPos(pos.getX() + dir.offsetX, pos.getY() + dir.offsetY, pos.getZ() + dir.offsetZ)) == Blocks.AIR) {
			world.setBlockState(new BlockPos(pos.getX() + dir.offsetX, pos.getY() + dir.offsetY, pos.getZ() + dir.offsetZ), ModBlocks.gas_radon_dense.getDefaultState());
		}
		super.updateTick(world, pos, state, rand);
	}
	
}
