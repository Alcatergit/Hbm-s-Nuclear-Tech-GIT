package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.BlockLadder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockNTMLadder extends BlockLadder {

	public BlockNTMLadder(String s){
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side){
		return this == ModBlocks.ladder_red_top || super.canPlaceBlockOnSide(worldIn, pos, side);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
		if(this == ModBlocks.ladder_red_top){
			return super.getBoundingBox(state, source, pos).setMaxY(0.25);
		}
		return super.getBoundingBox(state, source, pos);
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
		if(this == ModBlocks.ladder_red_top){
			return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
		}
		return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
	}
}
