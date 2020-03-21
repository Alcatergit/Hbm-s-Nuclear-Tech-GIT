package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class ReinforcedBlock extends Block {

	private boolean ignoreSimilarity;
	
	public ReinforcedBlock(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();

        //TODO reinforced glass
        if (/*this == ModBlocks.reinforced_glass || */this == ModBlocks.fwatz_tank)
        {
            if (blockState != iblockstate)
            {
                return true;
            }

            if (block == this)
            {
                return false;
            }
        }

        return !this.ignoreSimilarity && block == this ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

}
